package com.tungsten.fclcore.download.neoforge;

import static com.tungsten.fclcore.util.StringUtils.removePrefix;
import static com.tungsten.fclcore.util.StringUtils.removeSuffix;

import com.tungsten.fclcore.download.DefaultDependencyManager;
import com.tungsten.fclcore.download.LibraryAnalyzer;
import com.tungsten.fclcore.download.VersionMismatchException;
import com.tungsten.fclcore.download.forge.ForgeNewInstallProfile;
import com.tungsten.fclcore.download.forge.ForgeNewInstallTask;
import com.tungsten.fclcore.game.Version;
import com.tungsten.fclcore.task.FileDownloadTask;
import com.tungsten.fclcore.task.Task;
import com.tungsten.fclcore.util.gson.JsonUtils;
import com.tungsten.fclcore.util.io.CompressingUtils;
import com.tungsten.fclcore.util.io.FileUtils;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public final class NeoForgeInstallTask extends Task<Version> {
    private final DefaultDependencyManager dependencyManager;

    private final Version version;

    private final NeoForgeRemoteVersion remoteVersion;

    private Path installer = null;

    private FileDownloadTask dependent;

    private Task<Version> dependency;

    public NeoForgeInstallTask(DefaultDependencyManager dependencyManager, Version version, NeoForgeRemoteVersion remoteVersion) {
        this.dependencyManager = dependencyManager;
        this.version = version;
        this.remoteVersion = remoteVersion;
    }

    @Override
    public boolean doPreExecute() {
        return true;
    }

    @Override
    public void preExecute() throws Exception {
        installer = Files.createTempFile("neoforge-installer", ".jar");

        dependent = new FileDownloadTask(
                dependencyManager.getDownloadProvider().injectURLsWithCandidates(remoteVersion.getUrls()),
                installer.toFile(), null
        );
        dependent.setCacheRepository(dependencyManager.getCacheRepository());
        dependent.setCaching(true);
        dependent.addIntegrityCheckHandler(FileDownloadTask.ZIP_INTEGRITY_CHECK_HANDLER);
    }

    @Override
    public boolean doPostExecute() {
        return true;
    }

    @Override
    public void postExecute() throws Exception {
        Files.deleteIfExists(installer);
        this.setResult(dependency.getResult());
    }

    @Override
    public Collection<? extends Task<?>> getDependents() {
        return Collections.singleton(dependent);
    }

    @Override
    public Collection<? extends Task<?>> getDependencies() {
        return Collections.singleton(dependency);
    }

    @Override
    public void execute() throws Exception {
        dependency = install(dependencyManager, version, installer);
    }

    public static Task<Version> install(DefaultDependencyManager dependencyManager, Version version, Path installer) throws IOException, VersionMismatchException {
        Optional<String> gameVersion = dependencyManager.getGameRepository().getGameVersion(version);
        if (!gameVersion.isPresent()) throw new IOException();
        try (FileSystem fs = CompressingUtils.createReadOnlyZipFileSystem(installer)) {
            String installProfileText = FileUtils.readText(fs.getPath("install_profile.json"));
            Map<?, ?> installProfile = JsonUtils.fromNonNullJson(installProfileText, Map.class);
            if (LibraryAnalyzer.LibraryType.FORGE.getPatchId().equals(installProfile.get("profile")) && (Files.exists(fs.getPath("META-INF/NEOFORGE.RSA")) || installProfileText.contains("neoforge"))) {
                ForgeNewInstallProfile profile = JsonUtils.fromNonNullJson(installProfileText, ForgeNewInstallProfile.class);
                if (!gameVersion.get().equals(profile.getMinecraft()))
                    throw new VersionMismatchException(profile.getMinecraft(), gameVersion.get());
                return new ForgeNewInstallTask(dependencyManager, version, modifyNeoForgeOldVersion(gameVersion.get(), profile.getVersion()), installer).thenApplyAsync(neoForgeVersion -> {
                    if (!neoForgeVersion.getId().equals(LibraryAnalyzer.LibraryType.FORGE.getPatchId()) || neoForgeVersion.getVersion() == null) {
                        throw new IOException("Invalid neoforge version.");
                    }
                    return neoForgeVersion.setId(LibraryAnalyzer.LibraryType.NEO_FORGE.getPatchId())
                            .setVersion(
                                    removePrefix(neoForgeVersion.getVersion().replace(LibraryAnalyzer.LibraryType.FORGE.getPatchId(), ""), "-")
                            );
                });
            } else if (LibraryAnalyzer.LibraryType.NEO_FORGE.getPatchId().equals(installProfile.get("profile")) || "NeoForge".equals(installProfile.get("profile"))) {
                ForgeNewInstallProfile profile = JsonUtils.fromNonNullJson(installProfileText, ForgeNewInstallProfile.class);
                if (!gameVersion.get().equals(profile.getMinecraft()))
                    throw new VersionMismatchException(profile.getMinecraft(), gameVersion.get());
                return new NeoForgeOldInstallTask(dependencyManager, version, modifyNeoForgeNewVersion(profile.getVersion()), installer);
            } else {
                throw new IOException();
            }
        }
    }

    private static String modifyNeoForgeOldVersion(String gameVersion, String version) {
        return removeSuffix(removePrefix(removeSuffix(removePrefix(version.replace(gameVersion, "").trim(), "-"), "-"), "_"), "_");
    }

    private static String modifyNeoForgeNewVersion(String version) {
        return removePrefix(version.replace("neoforge", ""), "-");
    }
}
