#!/system/bin/sh
if [[ $# == 0 ]]; then
  . "${FCLSHELL_WORKDIR}/res/lang/${CONF_LANG}.sh"
elif [[ $# == 1 && -d "${FCLSHELL_WORKDIR}/res/lang/$1" ]]; then
  . "${FCLSHELL_WORKDIR}/res/lang/$1/${CONF_LANG}.sh"
else
  echo "error: bad arguments"
fi