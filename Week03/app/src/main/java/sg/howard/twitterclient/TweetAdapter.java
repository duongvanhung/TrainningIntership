package sg.howard.twitterclient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.twitter.sdk.android.core.models.Tweet;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sg.howard.twitterclient.util.TimelineConverter;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.TweetViewHolder> {

    private List<Tweet> tweets;
    private Context context;
    private ImageListener imageListener;
    private int temp = -1;




    public TweetAdapter(Context context, ImageListener imageListener) {
        tweets =new ArrayList<>();
        this.imageListener = imageListener;
        this.context = context;
    }
    public TweetAdapter(Context context){
        tweets =new ArrayList<>();
        this.context = context;
    }

    class TweetViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage;
        TextView tvName;
        TextView tvUserProfileName;
        TextView tvTimeStamp;
        TextView tvBody;
        ImageView ivMedia;
        SparkButton btnHeart;
        SparkButton btnRetweet;
        TextView tvLikesCount;
        TextView tvRetweetCount;

        TweetViewHolder(View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvUserProfileName = itemView.findViewById(R.id.tvUserProfileName);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
            tvBody = itemView.findViewById(R.id.tvBody);
            ivMedia = itemView.findViewById(R.id.ivMedia);
            btnHeart = itemView.findViewById(R.id.star_button);
            btnRetweet = itemView.findViewById(R.id.retweet_button);
            tvLikesCount = itemView.findViewById(R.id.tvLikeCount);
            tvRetweetCount = itemView.findViewById(R.id.tvRetweetCount);



        }
    }

    public void setData(List<Tweet> data) {
        tweets.clear();
        tweets.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TweetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.content_tweet, parent, false);

        // Return a new holder instance

        return new TweetViewHolder(contactView);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TweetViewHolder holder, int position) {

        Tweet tweet = tweets.get(position);

        int favoriteCount_bef = tweet.favoriteCount;
        int favoriteCount_aft = tweet.favoriteCount+1;

        int retweetCount_bef = tweet.retweetCount;
        int retweetCount_aft = tweet.retweetCount+1;

        holder.tvName.setText(tweet.user.name);
        holder.tvUserProfileName.setText("@" + tweet.user.screenName);
        holder.tvTimeStamp.setText("•" + TimelineConverter.dateToAge(tweet.createdAt, DateTime.now()));
        holder.tvBody.setText(tweet.text);




        String profileImageUrl = tweet.user.profileImageUrl;
        Glide.with(holder.itemView.getContext())
                .load(profileImageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.ivProfileImage);

        if (!tweet.entities.media.isEmpty()){
            String mediaUrl = tweet.entities.media.get(0).mediaUrl;
            Glide.with(holder.itemView.getContext())
                    .load(mediaUrl)
                    .apply(centerCropTransform())
                    .into(holder.ivMedia);

        }
        else
            holder.ivMedia.setVisibility(View.GONE);

        holder.tvRetweetCount.setText(""+ retweetCount_bef);
        holder.tvLikesCount.setText(""+ favoriteCount_bef);

        holder.ivMedia.setOnClickListener(view -> imageListener.onImageClick(holder.ivMedia,tweet.entities.media.get(0).mediaUrl));
        ColorStateList oldColors =  holder.tvRetweetCount.getTextColors();

        holder.btnRetweet.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {

                holder.btnRetweet.playAnimation();


                if (holder.btnRetweet.isChecked()){
                    holder.tvRetweetCount.setText(""+ retweetCount_aft);
                    holder.tvRetweetCount.setTextColor(Color.GREEN);
                    holder.tvRetweetCount.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_in_up));

                }
                else {
                    holder.tvRetweetCount.setText("" + retweetCount_bef);
                    holder.tvRetweetCount.setTextColor(oldColors);
                    holder.tvRetweetCount.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_in_down));

                }
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
            }
        });

        holder.btnHeart.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                holder.btnHeart.playAnimation();
                if (holder.btnHeart.isChecked()){

                    holder.tvLikesCount.setText(""+ favoriteCount_aft);
                    holder.tvLikesCount.setTextColor(Color.RED);
                    holder.tvLikesCount.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_in_up));
                }
                else {

                    holder.tvLikesCount.setText("" + favoriteCount_bef);
                    holder.tvLikesCount.setTextColor(oldColors);
                    holder.tvLikesCount.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_in_down));

                }

            }
            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
            }
            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
            }
        });


        runAnimation(holder.itemView,position);

    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }



    public interface ImageListener {
        void onImageClick(ImageView view, String image_url);
    }



    public void runAnimation(View view, int position) {
        if(position>temp) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);
            view.startAnimation(animation);
            temp = position;
        }
    }
}
