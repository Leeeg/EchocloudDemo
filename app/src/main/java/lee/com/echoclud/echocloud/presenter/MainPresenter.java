package lee.com.echoclud.echocloud.presenter;

import lee.com.echoclud.echocloud.model.Type;

public interface MainPresenter extends BasePresenter{

    void sendNormalMessage(Type type, String text);

    void sendStreamMessage(Type type, String text);

    void startRecord();

    void stopRecord();

    void playVoice();

    void stopVoice();

}
