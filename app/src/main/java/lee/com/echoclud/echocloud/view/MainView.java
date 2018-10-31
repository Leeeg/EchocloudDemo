package lee.com.echoclud.echocloud.view;

import lee.com.echoclud.echocloud.presenter.MainPresenter;

public interface MainView extends BaseView<MainPresenter>{

    void showSendMessage(String msg);

    void showResultMessage(String msg);

    void resetText();

    void startRotate();

    void stopRotate();

}
