package ctyon.com.logcatproject.huishengyun.model;

public class VoiceMessage {

    private Meta meta;
    private Message message;
    private String nonce;
    private String signature;

    public static class Meta{
        private String channel_uuid;
        private String device_id;

        public String getChannel_uuid() {
            return channel_uuid;
        }

        public void setChannel_uuid(String channel_uuid) {
            this.channel_uuid = channel_uuid;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public Meta(String channel_uuid, String device_id) {
            this.channel_uuid = channel_uuid;
            this.device_id = device_id;
        }
    }

    public static class Message{
        private String type;
        private Data data;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public Message(String type) {
            this.type = type;
        }
    }

    public static class Data{
        String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
