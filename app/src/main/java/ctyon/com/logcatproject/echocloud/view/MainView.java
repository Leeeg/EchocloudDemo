package ctyon.com.logcatproject.echocloud.view;

import ctyon.com.logcatproject.echocloud.presenter.MainPresenter;

public interface MainView extends BaseView<MainPresenter>{

    void showSendMessage(String msg);

    void showResultMessage(String msg);

    void resetText();

    void startRotate();

    void stopRotate();

}
