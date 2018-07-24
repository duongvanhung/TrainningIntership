package sg.howard.twitterclient.timeline;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import sg.howard.twitterclient.R;
import sg.howard.twitterclient.TweetAdapter;
import sg.howard.twitterclient.compose.ComposeDialogFragment;
import sg.howard.twitterclient.imagefragment.ImageDialogFragment;
import sg.howard.twitterclient.profileuser.ProfileActivity;
import sg.howard.twitterclient.util.EndlessRecyclerViewScrollListener;

public class TimelineActivity extends AppCompatActivity implements TimelineContract.View,TweetAdapter.ImageListener {
    private static String TAG = TimelineActivity.class.getSimpleName();
    RecyclerView rvTimeline;
    ProgressBar loader;
    FloatingActionButton fab;
    TimelineContract.Presenter presenter;
    TweetAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;

    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timeline);
        rvTimeline = findViewById(R.id.rvTimeline);
        loader = findViewById(R.id.loader);
        fab = findViewById(R.id.fab);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_container);


        presenter = new TimelinePresenter(this, TwitterCore.getInstance().getSessionManager().getActiveSession());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvTimeline.setLayoutManager(mLayoutManager);
        adapter = new TweetAdapter(this, this);
        rvTimeline.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                presenter.loadMore();
            }
        };

        rvTimeline.addOnScrollListener(scrollListener);


        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.white,
                R.color.colorPrimary,
                android.R.color.white);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.start();
            swipeRefreshLayout.setRefreshing(false);
        });


        fab.setOnClickListener(view -> {
            ComposeDialogFragment fragment
                    = ComposeDialogFragment.newInstance(20, 6f, true, false);
            fragment.show(getFragmentManager(), "blur_sample");

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mn_profile:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.start();
    }

    @Override
    public void setPresenter(TimelineContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        loader.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onGetStatusesSuccess(List<Tweet> data) {
        Log.d(TAG, "Loaded " + data.size());
        adapter.setData(data);
        scrollListener.resetState();
    }



    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onImageClick(ImageView view, String image_url) {

        ImageDialogFragment fragment
                = ImageDialogFragment.newInstance(20, 6f, true, false,image_url);
        fragment.show(getFragmentManager(), "blur_sample");
    }

}
