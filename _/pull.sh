#!/data/data/com.termux/files/usr/bin/bash
cd "$(dirname "$(readlink -f "${0}")")"
cd "$(dirname "`pwd`")"
echo -e "\e[96m工作目录：\e[37m$(pwd)\e[90m"
trap '' INT

echo -e "\e[92m屏蔽当前仓库 git\e[0m"
mv ".git" ".git_bak"

echo -e "\e[92m启用控制器仓库 git\e[0m"
if [ -d "controllerRepo/.git_bak" ]; then
    mv "controllerRepo/.git_bak" "controllerRepo/.git"
    controllerRepoInit="true"
else
    echo -e "\e[93m控制器仓库尚未初始化，请稍候...\e[0m"
    rm -rf controllerRepo
    git clone "https://github.com/FCL-Team/FCL-Controllers.git" "controllerRepo"
    if [ "$?" != "0" ]; then
        echo -e "\e[91m克隆官方仓库失败\e[0m"
    else
        controllerRepoInit="true"
    fi
fi

if [ "$controllerRepoInit" == "true" ]; then
    echo -e "\e[92m从官方控制器仓库拉取更新\e[0m"
    cd controllerRepo
    git fetch --prune && git reset --hard && git clean -fdX && git pull
    if [ "$?" != "0" ]; then
        echo -e "\e[91m更新失败\e[0m"
    fi
    cd ..
    echo -e "\e[92m屏蔽控制器仓库 git\e[0m"
    mv "controllerRepo/.git" "controllerRepo/.git_bak"
else
    rm -rf "controllerRepo/.git"
fi

echo -e "\e[92m还原当前仓库 git\e[0m"
mv ".git_bak" ".git"
