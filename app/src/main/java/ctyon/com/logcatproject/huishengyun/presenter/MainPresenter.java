package ctyon.com.logcatproject.huishengyun.presenter;

import ctyon.com.logcatproject.huishengyun.model.Type;

public interface MainPresenter extends BasePresenter{

    void sendNormalMessage(Type type, String text);

    void sendStreamMessage(Type type, String text);

    void startRecord();

    void stopRecord();

    void playVoice();

    void stopVoice();

}
