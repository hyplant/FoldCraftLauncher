# 更改默认配置

大多数设置通过安装“其他文件”时，覆盖本地文件来更新。  
因此需要修改`assets/othersInternal/files/version`中的版本号才能更新。  
这些选项通过覆盖本地配置文件来实现，详见[Extra.md](./Extra.md)。  

其中`config.properties`和`options.txt`每次用到时自动读取，无需手动修改版本号。
而`config.json`在安装“游戏资源”时更新，需要修改`/assets/.minecraft/version`。

<div><br></div>

## config.properties

**APK 中文件位置：**`/assets/config.properties`  
该文件为 FCL 直装版独有的自定义配置文件，用于调整直装版的某些选项。  

### 基本格式

```java-properties
# 安装到公有目录哪个目录下
# 值必须是英文字符，若需要使用中文目录名称请将中文转成unicode码并填写
put-directory=FCL-Server

# 是否开启公告界面，若值不是“true”则不开启
enable-announcement=true

# 公告内容链接[因为公告是在线的所以要一个直链链接读取网页内容]
# 关于格式说明：请严格按照默认公告链接内的伪Json格式书写，若出现格式问题则会提示"无法获取公告，也许是网络问题"
announcement-url=https://mirror.ghproxy.com/https://raw.githubusercontent.com/hyplant/FoldCraftLauncher/doc/announcement/latest.json

# 第一次运行启动器时显示的EULA信息[因为是在线的所以要一个直链链接读取网页内容]
# 值必须是英文字符，不能出现非英文字符
eula-url=https://mirror.ghproxy.com/https://raw.githubusercontent.com/hyplant/FoldCraftLauncher/doc/eula/latest.txt

# 帮助页面文档索引的链接
# 用于在帮助页面显示文档列表
doc-index-url=https://mirror.ghproxy.com/https://raw.githubusercontent.com/hyplant/FoldCraftLauncher/doc/document/index.json

# 帮助页面文档主页的链接
# 点击帮助页面的按钮会前往这个链接
doc-home-url=https://github.com/hyplant/FoldCraftLauncher/tree/doc/document

# QQ群Key
# 去这里申请Key：https://qun.qq.com/#/handy-tool/join-group
# 获得的key前面加前缀：mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26jump_from%3Dwebapi%26k%3D
qq-group-key=about:blank

# Discord链接
# 值必须是英文字符，不能出现非英文字符
discord=about:blank

# 每次打开启动器是否在线检测更新，若值不是“true”则不检测
check-update=true

# 启动器检查更新链接地址，需要先开启check-update选项后该功能才有效
# 若需要更改链接请确保必须和原连接内json格式一致，若格式不一致会导致启动器会闪退！！！
# 值必须是英文字符，不能出现非英文字符
check-update-url=https://mirror.ghproxy.com/https://raw.githubusercontent.com/hyplant/FoldCraftLauncher/doc/version_map/latest.json

# NS服务器地址
primary-nameserver=223.5.5.5
secondary-nameserver=8.8.8.8

# 是否忽略刘海屏，若值不是“true”则不忽略
fullscreen=false
```

<div><br></div>

## config.json

**APK 中文件位置：**`/assets/config.json`  
该文件为启动器的主要配置文件，大多数游戏设置相关的内容都在这里。  

### 基本格式

```json
{
  "accounts": [], //默认添加的账号。但不建议在此处添加，建议使用 accounts.json。
  "authlibInjectorServers": [ //默认添加的自定义第三方登录服务器。
    { //一项服务器配置
      "metadataTimestamp": 0, //上次刷新服务器设置的时间。建议设为0，让启动器立即刷新。
      "url": "https://littleskin.cn/api/yggdrasil/" //该第三方登录服务器的网址。
    }
  ],
  "autoChooseDownloadType": true, //是否自动选择下载源。
  "autoDownloadThreads": true, //是否自动设置下载线程数。
  "commonpath": "", //当前的工作目录，建议留空以自动识别。
  "_version": 0, //配置文件的版本，目前为0。
  "configurations": { //游戏目录。
    "Default Directory": { //一个游戏目录，此项的名称即游戏目录名称。
      "global": { //该目录下实例的默认配置。
        "usesGlobal": true, //该目录下新建实例是否默认使用目录配置。【需要验证】
        "javaArgs": "", //JVM 的额外参数。
        "minecraftArgs": "", //客户端的额外参数。
        "maxMemory": 4106, //最大内存。
        "autoMemory": true, //是否自动设置最大内存。
        "permSize": "", //内存永久保存区大小。
        "serverIp": "", //自动加入服务器的ip
        "java": "Auto", //java 版本，可设为 ["8", "11", "17", "21", "Auto"]
        "scaleFactor": 1.0, //降低分辨率，可设为 [0.25, 1.0] 之间的1~2位小数。
        "notCheckGame": false, //是否跳过检查游戏资源完整性。
        "notCheckJVM": true, //是否跳过检查java版本兼容性。
        "beGesture": false, //是否启用基岩版触控手势。
        "vulkanDriverSystem": false, //vulkan 渲染器是否使用系统驱动。
        "controller": "Default", //默认的控制器。
        "renderer": 4, //默认的渲染，可设为 [0, 5] 之间的整数，对应启动器中的渲染器列表。
        "isolateGameDir": false //是否开启游戏目录隔离。
      },
      "selectedMinecraftVersion": "", //当前选中的游戏版本，建议留空以自动识别。
      "gameDir": "" //游戏目录的路径，建议留空以自动识别。
    }
  },
  "downloadThreads": 64, //下载线程数，可设为 [1, 128] 之间的整数。
  "downloadType": "mojang", //下载源，可设为 ["mojang", "bmclapi"]。
  "last": "Default Directory", //选中的游戏目录。
  "uiVersion": 0, //ui版本，目前为0。
  "versionListSource": "balanced" //自动选择的下载源，可设为 ["official", "balanced", "mirror"]。
}
```

<div><br></div>

## options.txt

**APK 中文件位置：**`/assets/options.txt`  
该文件为客户端的默认配置文件。  

### 基本格式

详见 Minecraft Wiki [客户端选项文件格式](https://zh.minecraft.wiki/w/%E5%AE%A2%E6%88%B7%E7%AB%AF%E9%80%89%E9%A1%B9%E6%96%87%E4%BB%B6%E6%A0%BC%E5%BC%8F)。

<div><br></div>

## accounts.json

**APK 中文件位置：**`/assets/othersInternal/files/accounts.json`  
此文件用于保存启动器中添加的账户。

### 基本格式

所有账户保存在`[]`中，每个账户记录写在一个`{}`中。
```json
[
  {} //一个账户
]
```

### 离线账户

基本格式如下，皮肤配置见下文子章节中。
```json
{
  "uuid": "00000000000000000000000000000000", //玩家的 uuid。
  "username": "Player", //玩家的用户名。
  "skin": {}, //皮肤配置。
  "type": "offline" //账户类型，此处为离线账户。
}
```

#### 默认皮肤

```json
{
  "type": "default", //皮肤类型，此处使用默认皮肤。
  "textureModel": "default" //玩家使用的模型，default 为默认，slim 为纤细。
}
```

#### 经典皮肤

**Steve**
```json
{
  "type": "steve", //使用 Steve 皮肤。
  "textureModel": "default" //使用默认模型。
}
```

**Alex**
```json
{
  "type": "alex", //使用 Alex 皮肤。
  "textureModel": "slim" //使用纤细模型。
}
```

#### 本地皮肤文件

```json
{
  "type": "local_file", //使用本地文件作为皮肤。
  "textureModel": "default",
  "localSkinPath": "/storage/emulated/0/mc/skin/s.png", //皮肤图片文件的路径。
  "localCapePath": "/storage/emulated/0/mc/skin/c.png" //披风图片文件的路径。
}
```

#### Custom Skin Loader

```json
{
  "type": "custom_skin_loader_api",  //使用 Custom Skin Loader Api 加载皮肤。
  "cslApi": "ftp://192.168.1.80:3721/cslApi/", //皮肤网址
  "textureModel": "default"
}
```

### 微软账户

基本格式如下。
```json
{
  "uuid": "00000000000000000000000000000000", //玩家的 minecraft uuid
  "displayName": "Hyplant", //玩家的 minecraft 用户名
  "tokenType": "Bearer", //固定值 Bearer
  "accessToken": "<一些看似乱码的字符>", //访问令牌，用于登录游戏。
  "refreshToken": "M.C105_BAY.-<一些看似乱码的字符>$$", //刷新令牌，用于刷新访问令牌。
  "notAfter": 0, //访问令牌过期的时间，过期后将使用刷新令牌刷新。
  "userid": "00000000-0000-0000-0000-000000000000", //玩家的 xbox uid
  "type": "microsoft" //固定值 microsoft
}
```

<div><br></div>

## menu_setting.json

**APK 中文件位置：**`/assets/othersInternal/files/menu_setting.json`  
此文件用于保存控制器的偏好设置。

### 基本格式

```json
{
  "autoFit": true, //按键自动吸附。
  "autoFitDist": 0, //按键自动吸附间距。
  "lockMenuView": false, //锁定悬浮窗位置。
  "disableSoftKeyAdjust": false, //禁用软件盘自适应。
  "showLog": false, //显示日志屏幕。
  "disableGesture": false, //禁用触控手势。
  "disableBEGesture": false, //禁用基岩板触控手势。
  "gestureMode": 0, //触控模式（0建筑，1战斗）。
  "enableGyroscope": false, //启用陀螺仪控制的。
  "gyroscopeSensitivity": 10, //陀螺仪灵敏度。
  "mouseMoveMode": 0, //鼠标模式（0点击，1拖动）。
  "mouseSensitivity": 1.0, //鼠标灵敏度。
  "mouseSize": 15, //鼠标大小。
  "itemBarScale": 0, //物品栏触控区域缩放。
  "windowScale": 1.0, //窗口分辨率缩小。
  "gamepadDeadzone": 1.0, //游戏手柄摇杆死区。
  "gamepadAimAssistZone": 0.95 //游戏手柄瞄准辅助区。
}
```

<div><br></div>

## .bashrc

**APK 中文件位置：**`/assets/othersInternal/.bashrc`  
每次进入 fcl shell （长按**启动游戏**按钮，或进入应用程序信息中的**应用内设置**）时  
会自动运行此文件中的命令。

### 基本格式

```sh
#!/system/bin/sh
echo "Welcome to use Fold Craft Launcher!"
echo "Here is the shell command line!"


```

<div><br></div>

## global_config.json

**APK 中文件位置：**`/assets/othersInternal/files/global_config.json`  
**该文件作用未知！**

### 基本格式

```json
{}
```
