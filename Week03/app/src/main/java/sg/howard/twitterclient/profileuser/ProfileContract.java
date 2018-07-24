package sg.howard.twitterclient.profileuser;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import sg.howard.twitterclient.base.BasePresenter;
import sg.howard.twitterclient.base.BaseView;

public interface ProfileContract {
    interface View extends BaseView<Presenter> {
        void onGetStatusesSuccess(List<Tweet> data);

    }

    interface Presenter extends BasePresenter {
        void loadMore();

    }


}
