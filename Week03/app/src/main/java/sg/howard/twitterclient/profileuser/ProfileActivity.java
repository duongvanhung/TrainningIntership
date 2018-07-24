package sg.howard.twitterclient.profileuser;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import sg.howard.twitterclient.R;
import sg.howard.twitterclient.TweetAdapter;
import sg.howard.twitterclient.util.EndlessRecyclerViewScrollListener;

public class ProfileActivity extends AppCompatActivity implements ProfileContract.View {
    private static String TAG = ProfileActivity.class.getSimpleName();
    RecyclerView rvReview;
    ProgressBar loader;
    ProfileContract.Presenter presenter;

    ImageView ivCover;
    ImageView ivAvatar;
    Toolbar toolbar;
    TextView tv_screenName_prf;
    TextView tv_name_prf;
    TextView tvFollowing;
    TextView tvFollower;

    SwipeRefreshLayout swipeRefreshLayout;
    EndlessRecyclerViewScrollListener scrollListener;

    TweetAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        ivCover = findViewById(R.id.ivImage);
        ivAvatar = findViewById(R.id.ivAvatar);
        tv_name_prf = findViewById(R.id.tv_name);
        tv_screenName_prf = findViewById(R.id.tv_screenName);
        tvFollower = findViewById(R.id.tvFollower);
        tvFollowing = findViewById(R.id.tvFollowing);

        rvReview = findViewById(R.id.rvReview);
        loader = findViewById(R.id.loader);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);


        presenter = new ProfilePresenter(this, TwitterCore.getInstance().getSessionManager().getActiveSession());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvReview.setLayoutManager(mLayoutManager);
        adapter = new TweetAdapter(this);
        rvReview.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                presenter.loadMore();
            }
        };

        rvReview.addOnScrollListener(scrollListener);


        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.white,
                R.color.colorPrimary,
                android.R.color.white);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.start();
            swipeRefreshLayout.setRefreshing(false);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        loader.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onGetStatusesSuccess(List<Tweet> data) {
        Log.d(TAG, "Loaded " + data.size());
        Tweet tweet = data.get(0);
        Glide.with(this)
                .load(tweet.user.profileBannerUrl)
                .into(ivCover);
        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(ivAvatar);
        tv_screenName_prf.setText(tweet.user.name);
        tv_name_prf.setText("@"+tweet.user.screenName);
        tvFollowing.setText(tweet.user.friendsCount+"");
        tvFollower.setText(tweet.user.followersCount+"");

        adapter.setData(data);
        scrollListener.resetState();
    }



    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



}
