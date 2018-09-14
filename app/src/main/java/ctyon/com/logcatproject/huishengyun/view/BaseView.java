package ctyon.com.logcatproject.huishengyun.view;

import ctyon.com.logcatproject.huishengyun.presenter.BasePresenter;

public interface BaseView<P extends BasePresenter> {

    void setPresenter(P presenter);

}
