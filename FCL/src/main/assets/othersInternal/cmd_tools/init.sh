#!/system/bin/sh
for pkg in $(pm list packages --uid "$(id -u)");do
  export FCL_PKG_NAME="${pkg/#package:/}"
  unset pkg
  break
done
FCL_PKG_PATH="$(pm path ${FCL_PKG_NAME})"
export FCL_PKG_PATH="${FCL_PKG_PATH/#package:/}"

appCfg="$(unzip -p "${FCL_PKG_PATH}" "assets/config.properties")"
FCL_SHARED_COMMON_DIR="$(echo "${appCfg}" | grep "put-directory")"
export FCL_SHARED_COMMON_DIR="/sdcard/${FCL_SHARED_COMMON_DIR/#put-directory=/}"
CONF_NSa="$(echo "${appCfg}" | grep "primary-nameserver")"
export CONF_NSa="${CONF_NSa/#primary-nameserver=/}"
CONF_NSb="$(echo "${appCfg}" | grep "secondary-nameserver")"
export CONF_NSb="${CONF_NSb/#secondary-nameserver=/}"
unset appCfg

export FCL_INTERNAL_DIR="/data/data/${FCL_PKG_NAME}"


CONF_LANG="$(getprop persist.sys.locale )"
CONF_LANG="${CONF_LANG:0:2}"
if [[ -f "${FCLSHELL_WORKDIR}/res/lang/${CONF_LANG}.sh" ]]; then
  export CONF_LANG="${CONF_LANG}"
else
  export CONF_LANG="en"
fi
export fclshell_load_lang=". ${FCLSHELL_WORKDIR}/lib/lang/load.sh"
${fclshell_load_lang}
