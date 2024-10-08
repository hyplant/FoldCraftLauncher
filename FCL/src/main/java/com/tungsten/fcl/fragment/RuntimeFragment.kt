package com.tungsten.fcl.fragment

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import com.tungsten.fcl.FCLApplication
import com.tungsten.fcl.R
import com.tungsten.fcl.activity.SplashActivity
import com.tungsten.fcl.databinding.FragmentRuntimeBinding
import com.tungsten.fcl.util.ReadTools
import com.tungsten.fcl.util.RuntimeUtils
import com.tungsten.fclauncher.utils.FCLPath
import com.tungsten.fclcore.task.Schedulers
import com.tungsten.fclcore.util.io.FileUtils
import com.tungsten.fcllibrary.component.FCLFragment
import com.tungsten.fcllibrary.util.LocaleUtils
import java.io.File
import java.io.IOException
import java.util.Locale

class RuntimeFragment : FCLFragment(), View.OnClickListener {
    private lateinit var bind: FragmentRuntimeBinding
    var lwjgl: Boolean = false
    var cacio: Boolean = false
    var cacio11: Boolean = false
    var cacio17: Boolean = false
    var java8: Boolean = false
    var java11: Boolean = false
    var java17: Boolean = false
    var java21: Boolean = false
    var jna: Boolean = false
    var gameResource = false
    var others = false
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var needRestart: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences = requireActivity().getSharedPreferences("launcher", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val view = inflater.inflate(R.layout.fragment_runtime, container, false)
        bind = FragmentRuntimeBinding.bind(view)
        bind.install.setOnClickListener(this)
        Schedulers.defaultScheduler().execute {
            initState()
            Schedulers.androidUIThread().execute {
                refreshDrawables()
                check()
            }
        }
        return view
    }

    private fun initState() {
        try {
            lwjgl = RuntimeUtils.isLatest(
                FCLPath.LWJGL_DIR,
                "/assets/app_runtime/lwjgl"
            ) && RuntimeUtils.isLatest(
                FCLPath.LWJGL_DIR + "-boat",
                "/assets/app_runtime/lwjgl-boat"
            )
            cacio = RuntimeUtils.isLatest(
                FCLPath.CACIOCAVALLO_8_DIR,
                "/assets/app_runtime/caciocavallo"
            )
            cacio11 = RuntimeUtils.isLatest(
                FCLPath.CACIOCAVALLO_11_DIR,
                "/assets/app_runtime/caciocavallo11"
            )
            cacio17 = RuntimeUtils.isLatest(
                FCLPath.CACIOCAVALLO_17_DIR,
                "/assets/app_runtime/caciocavallo17"
            )
            java8 = RuntimeUtils.isLatest(FCLPath.JAVA_8_PATH, "/assets/app_runtime/java/jre8")
            java11 = RuntimeUtils.isLatest(FCLPath.JAVA_11_PATH, "/assets/app_runtime/java/jre11")
            java17 = RuntimeUtils.isLatest(FCLPath.JAVA_17_PATH, "/assets/app_runtime/java/jre17")
            java21 = RuntimeUtils.isLatest(FCLPath.JAVA_21_PATH, "/assets/app_runtime/java/jre21")
            jna = RuntimeUtils.isLatest(FCLPath.JNA_PATH, "/assets/app_runtime/jna")
            gameResource = RuntimeUtils.isLatest(FCLPath.SHARED_COMMON_DIR, "/assets/.minecraft")
            others = RuntimeUtils.isLatest(FCLPath.FILES_DIR, "/assets/othersInternal/files")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun refreshDrawables() {
        if (context != null) {
            val stateUpdate =
                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_update_24)
            val stateDone =
                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_done_24)

            stateUpdate?.setTint(Color.RED)
            stateDone?.setTint(Color.GREEN)

            bind.apply {
                lwjglState.setBackgroundDrawable(if (lwjgl) stateDone else stateUpdate)
                cacioState.setBackgroundDrawable(if (cacio) stateDone else stateUpdate)
                cacio11State.setBackgroundDrawable(if (cacio11) stateDone else stateUpdate)
                cacio17State.setBackgroundDrawable(if (cacio17) stateDone else stateUpdate)
                java8State.setBackgroundDrawable(if (java8) stateDone else stateUpdate)
                java11State.setBackgroundDrawable(if (java11) stateDone else stateUpdate)
                java17State.setBackgroundDrawable(if (java17) stateDone else stateUpdate)
                java21State.setBackgroundDrawable(if (java21) stateDone else stateUpdate)
                jnaState.setBackgroundDrawable(if (jna) stateDone else stateUpdate)
                gameResourceState.setBackgroundDrawable(if (gameResource) stateDone else stateUpdate)
                othersState.setBackgroundDrawable(if (others) stateDone else stateUpdate)
            }
        }
    }

    private val isLatest: Boolean
        get() = lwjgl && cacio && cacio11 && cacio17 && java8 && java11 && java17 && java21 && jna && gameResource

    private fun check() {
        if (isLatest && others) {
            if (needRestart) {
                (activity as SplashActivity).finish()
                System.exit(0)
            } else {
                (activity as SplashActivity).enterLauncher()
            }
        }
    }

    private var installingOthers = false
    private fun checkOthers() {
        if (installingOthers) return
        if (isLatest) {
            installingOthers = true
            bind.apply {
                if (!others) {
                    othersProgress.visibility = View.VISIBLE
                    Thread {
                        try {
                            RuntimeUtils.copyAssetsDirToLocalDir(context, "othersExternal", FCLPath.EXTERNAL_DIR)
                            RuntimeUtils.copyAssetsDirToLocalDir(context, "othersInternal", FCLPath.INTERNAL_DIR)
                            others = true
                            activity?.runOnUiThread {
                                othersState.visibility = View.VISIBLE
                                othersProgress.visibility = View.GONE
                                refreshDrawables()
                                check()
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }.start()
                } else {
                    check()
                }
            }
        }
    }

    private fun setNameServer(targetJavaPath:String) {
        FileUtils.writeText(
            File(targetJavaPath + "/resolv.conf"),
            FCLApplication.appConfig.getProperty("primary-nameserver", "223.5.5.5") + "\n" + FCLApplication.appConfig.getProperty("secondary-nameserver", "8.8.8.8") + "\n"
        )
    }

    private var installing = false

    private fun install() {
        if (installing) return

        bind.apply {
            installing = true
            if (!gameResource) {
                gameResourceState.visibility = View.GONE
                gameResourceProgress.visibility = View.VISIBLE
                Thread {
                    try {
                        var applicationThisGameDirectory = FCLPath.SHARED_COMMON_DIR
                        var applicationThisDataDirectory = FCLPath.EXTERNAL_DIR + "/minecraft"
                        // 先刷新配置
                        RuntimeUtils.reloadConfiguration(context)
                        // 删除旧数据
                        RuntimeUtils.delete(applicationThisGameDirectory)
                        // 在将安装包assets中游戏资源释放到对应目录中
                        RuntimeUtils.copyAssetsDirToLocalDir(context, ".minecraft", applicationThisGameDirectory)
                        RuntimeUtils.copyAssetsDirToLocalDir(context, "minecraft", applicationThisDataDirectory)
                        //设置完成标志
                        gameResource = true
                        activity?.runOnUiThread {
                            gameResourceState.visibility = View.VISIBLE
                            gameResourceProgress.visibility = View.GONE
                            refreshDrawables()
                            checkOthers()
                        }
                    }catch (e: IOException) {
                        e.printStackTrace()
                    }
                }.start()
            }
            if (!lwjgl) {
                lwjglState.visibility = View.GONE
                lwjglProgress.visibility = View.VISIBLE
                Thread {
                    try {
                        RuntimeUtils.install(context, FCLPath.LWJGL_DIR, "app_runtime/lwjgl")
                        RuntimeUtils.install(
                            context,
                            FCLPath.LWJGL_DIR + "-boat",
                            "app_runtime/lwjgl-boat"
                        )
                        lwjgl = true
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    activity?.runOnUiThread {
                        lwjglState.visibility = View.VISIBLE
                        lwjglProgress.visibility = View.GONE
                        refreshDrawables()
                        checkOthers()
                    }
                }.start()
            }
            if (!cacio) {
                cacioState.visibility = View.GONE
                cacioProgress.visibility = View.VISIBLE
                Thread {
                    try {
                        RuntimeUtils.install(
                            context,
                            FCLPath.CACIOCAVALLO_8_DIR,
                            "app_runtime/caciocavallo"
                        )
                        cacio = true
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    activity?.runOnUiThread {
                        cacioState.visibility = View.VISIBLE
                        cacioProgress.visibility = View.GONE
                        refreshDrawables()
                        checkOthers()
                    }
                }.start()
            }
            if (!cacio11) {
                cacio11State.visibility = View.GONE
                cacio11Progress.visibility = View.VISIBLE
                Thread {
                    try {
                        RuntimeUtils.install(
                            context,
                            FCLPath.CACIOCAVALLO_11_DIR,
                            "app_runtime/caciocavallo11"
                        )
                        cacio11 = true
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    activity?.runOnUiThread {
                        cacio11State.visibility = View.VISIBLE
                        cacio11Progress.visibility = View.GONE
                        refreshDrawables()
                        checkOthers()
                    }
                }.start()
            }
            if (!cacio17) {
                cacio17State.visibility = View.GONE
                cacio17Progress.visibility = View.VISIBLE
                Thread {
                    try {
                        RuntimeUtils.install(
                            context,
                            FCLPath.CACIOCAVALLO_17_DIR,
                            "app_runtime/caciocavallo17"
                        )
                        cacio17 = true
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    activity?.runOnUiThread {
                        cacio17State.visibility = View.VISIBLE
                        cacio17Progress.visibility = View.GONE
                        refreshDrawables()
                        checkOthers()
                    }
                }.start()
            }
            if (!java8) {
                java8State.visibility = View.GONE
                java8Progress.visibility = View.VISIBLE
                Thread {
                    try {
                        RuntimeUtils.installJava(
                            context,
                            FCLPath.JAVA_8_PATH,
                            "app_runtime/java/jre8"
                        )
                        setNameServer(FCLPath.JAVA_8_PATH)
                        java8 = true
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    activity?.runOnUiThread {
                        java8State.visibility = View.VISIBLE
                        java8Progress.visibility = View.GONE
                        refreshDrawables()
                        checkOthers()
                    }
                }.start()
            }
            if (!java11) {
                java11State.visibility = View.GONE
                java11Progress.visibility = View.VISIBLE
                Thread {
                    try {
                        RuntimeUtils.installJava(
                            context,
                            FCLPath.JAVA_11_PATH,
                            "app_runtime/java/jre11"
                        )
                        setNameServer(FCLPath.JAVA_11_PATH)
                        java11 = true
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    activity?.runOnUiThread {
                        java11State.visibility = View.VISIBLE
                        java11Progress.visibility = View.GONE
                        refreshDrawables()
                        checkOthers()
                    }
                }.start()
            }
            if (!java17) {
                java17State.visibility = View.GONE
                java17Progress.visibility = View.VISIBLE
                Thread {
                    try {
                        RuntimeUtils.installJava(
                            context,
                            FCLPath.JAVA_17_PATH,
                            "app_runtime/java/jre17"
                        )
                        setNameServer(FCLPath.JAVA_17_PATH)
                        java17 = true
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    activity?.runOnUiThread {
                        java17State.visibility = View.VISIBLE
                        java17Progress.visibility = View.GONE
                        refreshDrawables()
                        checkOthers()
                    }
                }.start()
            }
            if (!java21) {
                java21State.visibility = View.GONE
                java21Progress.visibility = View.VISIBLE
                Thread {
                    try {
                        RuntimeUtils.installJava(
                            context,
                            FCLPath.JAVA_21_PATH,
                            "app_runtime/java/jre21"
                        )
                        setNameServer(FCLPath.JAVA_21_PATH)
                        java21 = true
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    activity?.runOnUiThread {
                        java21State.visibility = View.VISIBLE
                        java21Progress.visibility = View.GONE
                        refreshDrawables()
                        checkOthers()
                    }
                }.start()
            }
            if (!jna) {
                jnaState.visibility = View.GONE
                jnaProgress.visibility = View.VISIBLE
                Thread {
                    try {
                        RuntimeUtils.installJna(
                            context,
                            FCLPath.JNA_PATH,
                            "app_runtime/jna"
                        )
                        jna = true
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    activity?.runOnUiThread {
                        jnaState.visibility = View.VISIBLE
                        jnaProgress.visibility = View.GONE
                        refreshDrawables()
                        checkOthers()
                    }
                }.start()
            }
            if (!others) {
                othersState.visibility = View.GONE
                checkOthers()
            }
        }
    }

    override fun onClick(view: View) {
        if (view === bind.install) {
            needRestart = true
            install()
        }
    }
}
