package ctyon.com.logcatproject.echocloud.model;

public enum Type {

    /**
     * 设备事件
     */
    HEART("device.heartbeat")                   //心跳         data:{}
    ,ONLINE("device.online")                    //上线         data:{“firmware_version”: "xxx", “mac_address”: "xxx","reconnect": true}
    ,OFFLINE("device.offline")                  //下线         data:{}
    ,NETCHANGE("bandwidth.changed")             //网络变化      data:{"bandwidth": 100}
    ,VOICECHANGE("volume.changed")              //音量变化      data:{"volume": 80}
    ,LIGHTCHANGE("light.changed")               //灯光变化      data:{"light": 80}
    ,BATTERYCHANGE("battery.changed")           //电量变化      data:{"battery": 80}
    ,CHANGESTART("battery.charge_started")      //开始充电      data:{}
    ,CHANGEEND("battery.charge_ended")          //结束充电      data:{}
    ,MEDIASTART("media_player.started")         //开始播放      data:{"token":"xxx"}
    ,MEDIAPAUSE("media_player.paused")          //暂停播放      data:{"token":"xxx", “offset”:20000}
    ,MEDIARESUME("media_player.resumed")        //继续播放      data:{"token":"xxx"}
    ,MEDIAEND("media_player.finished")          //结束播放      data:{"token":"xxx", “offset”:20000}
    ,MEDIAPROGRSS("media_player.progress_changed")//播放进度    data:{"token":"xxx", “offset”:20000}
    ,AI_TEXT("ai_dialog.input.device")          //发送AI文字命令

    /**
     * stream事件
     */
    ,AI_VOICE("ai_dialog.recognize.device")         //发送AI语音命令

    ;





    private String type;

    Type(String name) {
        this.type = name;
    }

    @Override
    public String toString() {
        return this.type;
    }


}
