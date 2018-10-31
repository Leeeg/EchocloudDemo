package lee.com.echoclud.echocloud.view;

import lee.com.echoclud.echocloud.presenter.BasePresenter;

public interface BaseView<P extends BasePresenter> {

    void setPresenter(P presenter);

}
