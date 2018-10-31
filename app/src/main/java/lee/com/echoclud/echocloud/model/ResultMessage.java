package lee.com.echoclud.echocloud.model;

import java.util.List;

public class ResultMessage {

    /**
     * messages : [{"type":"ai_dialog.play","data":{"audio_enabled":true,"audio_url":"http://voice-pek01.oss-cn-beijing.aliyuncs.com/optimus/tts/eMeCA1DtfcNH6oPoPPDK88107vDRrBeq6GtpQalkE0qjfJQayYK6aBsJVKtrXhIA.mp3","text":"我很会背诗哦，对我说背首唐诗，试试","token":"d5a99b49-b22a-4aaa-b672-be5e6abcfad1"}}]
     * count : 1
     */

    private int count;
    private List<MessagesBean> messages;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MessagesBean> getMessages() {
        return messages;
    }

    public void setMessages(List<MessagesBean> messages) {
        this.messages = messages;
    }

    public static class MessagesBean {
        /**
         * type : ai_dialog.play
         * data : {"audio_enabled":true,"audio_url":"http://voice-pek01.oss-cn-beijing.aliyuncs.com/optimus/tts/eMeCA1DtfcNH6oPoPPDK88107vDRrBeq6GtpQalkE0qjfJQayYK6aBsJVKtrXhIA.mp3","text":"我很会背诗哦，对我说背首唐诗，试试","token":"d5a99b49-b22a-4aaa-b672-be5e6abcfad1"}
         */

        private String type;
        private DataBean data;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * audio_enabled : true
             * audio_url : http://voice-pek01.oss-cn-beijing.aliyuncs.com/optimus/tts/eMeCA1DtfcNH6oPoPPDK88107vDRrBeq6GtpQalkE0qjfJQayYK6aBsJVKtrXhIA.mp3
             * text : 我很会背诗哦，对我说背首唐诗，试试
             * token : d5a99b49-b22a-4aaa-b672-be5e6abcfad1
             */

            private boolean audio_enabled;
            private String audio_url;
            private String text;
            private String token;

            public boolean isAudio_enabled() {
                return audio_enabled;
            }

            public void setAudio_enabled(boolean audio_enabled) {
                this.audio_enabled = audio_enabled;
            }

            public String getAudio_url() {
                return audio_url;
            }

            public void setAudio_url(String audio_url) {
                this.audio_url = audio_url;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }
    }
}
