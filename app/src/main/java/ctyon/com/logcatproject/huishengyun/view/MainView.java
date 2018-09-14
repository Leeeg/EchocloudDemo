package ctyon.com.logcatproject.huishengyun.view;

import ctyon.com.logcatproject.huishengyun.presenter.MainPresenter;

public interface MainView extends BaseView<MainPresenter>{

    void showSendMessage(String msg);

    void showResultMessage(String msg);

    void resetText();

    void startRotate();

    void stopRotate();

}
