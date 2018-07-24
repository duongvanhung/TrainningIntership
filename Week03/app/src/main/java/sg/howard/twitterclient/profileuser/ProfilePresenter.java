package sg.howard.twitterclient.profileuser;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import androidx.annotation.NonNull;

public class ProfilePresenter implements ProfileContract.Presenter {
    private TwitterApiClient client = null;
    private ProfileContract.View mView;
    private Integer count = 10;

    public ProfilePresenter(@NonNull ProfileContract.View view, TwitterSession session){
        mView= view;
        mView.setPresenter(this);
        client = new TwitterApiClient(session);

    }

    @Override
    public void start() {
        if (count <11) mView.showLoading(true);
        client.getStatusesService()
                .userTimeline(null, null, count, null, null, null, null,null,null)
                .enqueue(new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        mView.showLoading(false);
                        count = count+10;
                        mView.onGetStatusesSuccess(result.data);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        mView.showLoading(false);
                        mView.showError(exception.getMessage());

                    }
                });
    }

    @Override
    public void loadMore() {
        mView.showLoading(true);
        client.getStatusesService()
                .userTimeline(null, null, count, null, null, null, null,null,null)
                .enqueue(new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        mView.showLoading(false);
                        count = count+10;
                        mView.onGetStatusesSuccess(result.data);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        mView.showLoading(false);
                        mView.showError(exception.getMessage());
                    }
                });
    }
}
