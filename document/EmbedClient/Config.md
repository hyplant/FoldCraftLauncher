# 更改默认配置

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

## global_config.json


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

## config.properties


<div><br></div>

## options.txt

