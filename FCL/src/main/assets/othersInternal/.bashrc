#!/system/bin/sh
echo "Welcome to use Fold Craft Launcher!"
export FCLSHELL_WORKDIR="$(dirname "$(readlink -f "${0}")")/cmd_tools"
chmod +x "${FCLSHELL_WORKDIR}/bin/"*
export PATH="${FCLSHELL_WORKDIR}/bin":${PATH}
. ${FCLSHELL_WORKDIR}/init.sh
echo "Here is the shell command line!"
echo "pkg: ${FCL_PKG_NAME}"
echo "lang: ${CONF_LANG}"
