package ctyon.com.logcatproject.echocloud.view;

import ctyon.com.logcatproject.echocloud.presenter.BasePresenter;

public interface BaseView<P extends BasePresenter> {

    void setPresenter(P presenter);

}
