package sg.howard.twitterclient.compose;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;
import com.unstoppable.submitbuttonview.SubmitButton;

import androidx.annotation.NonNull;
import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;
import sg.howard.twitterclient.R;

/**
 * Simple fragment with blur effect behind.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ComposeDialogFragment extends BlurDialogFragment implements ComposeContract.View{

    ComposeContract.Presenter presenter;
    ProgressBar loader;
    SubmitButton btnTweet;
    /**
     * Bundle key used to start the blur dialog with a given scale factor (float).
     */
    private static final String BUNDLE_KEY_DOWN_SCALE_FACTOR = "bundle_key_down_scale_factor";

    /**
     * Bundle key used to start the blur dialog with a given blur radius (int).
     */
    private static final String BUNDLE_KEY_BLUR_RADIUS = "bundle_key_blur_radius";

    /**
     * Bundle key used to start the blur dialog with a given dimming effect policy.
     */
    private static final String BUNDLE_KEY_DIMMING = "bundle_key_dimming_effect";

    /**
     * Bundle key used to start the blur dialog with a given debug policy.
     */
    private static final String BUNDLE_KEY_DEBUG = "bundle_key_debug_effect";

    private int mRadius;
    private float mDownScaleFactor;
    private boolean mDimming;
    private boolean mDebug;

    /**
     * Retrieve a new instance of the sample fragment.
     *
     * @param radius          blur radius.
     * @param downScaleFactor down scale factor.
     * @param dimming         dimming effect.
     * @param debug           debug policy.
     * @return well instantiated fragment.
     */
    public static ComposeDialogFragment newInstance(int radius,
                                                    float downScaleFactor,
                                                    boolean dimming,
                                                    boolean debug) {
        ComposeDialogFragment fragment = new ComposeDialogFragment();
        Bundle args = new Bundle();
        args.putInt(
                BUNDLE_KEY_BLUR_RADIUS,
                radius
        );
        args.putFloat(
                BUNDLE_KEY_DOWN_SCALE_FACTOR,
                downScaleFactor
        );
        args.putBoolean(
                BUNDLE_KEY_DIMMING,
                dimming
        );
        args.putBoolean(
                BUNDLE_KEY_DEBUG,
                debug
        );

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Bundle args = getArguments();
        mRadius = args.getInt(BUNDLE_KEY_BLUR_RADIUS);
        mDownScaleFactor = args.getFloat(BUNDLE_KEY_DOWN_SCALE_FACTOR);
        mDimming = args.getBoolean(BUNDLE_KEY_DIMMING);
        mDebug = args.getBoolean(BUNDLE_KEY_DEBUG);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        @SuppressLint("InflateParams") View view = getActivity().getLayoutInflater().inflate(R.layout.compose_fragment, null);
        TextView label = view.findViewById(R.id.etTweet);
        btnTweet = view.findViewById(R.id.btnTweet);
        loader = view.findViewById(R.id.loader);

        presenter = new ComposeTweetPresenter(this, TwitterCore.getInstance().getSessionManager().getActiveSession());

        btnTweet.setOnClickListener(view1 -> {
            presenter.sendTweet(label.getText().toString());
            btnTweet.doResult(true);
        });
        btnTweet.setOnResultEndListener(() -> Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show());

        builder.setView(view);
        return builder.create();
    }

    @Override
    protected boolean isDebugEnable() {
        return mDebug;
    }

    @Override
    protected boolean isDimmingEnable() {
        return mDimming;
    }

    @Override
    protected boolean isActionBarBlurred() {
        return true;
    }

    @Override
    protected float getDownScaleFactor() {
        return mDownScaleFactor;
    }

    @Override
    protected int getBlurRadius() {
        return mRadius;
    }




    @Override
    public void sendTweetSuccess(Result<Tweet> result) {
        btnTweet.doResult(true);

    }

    @Override
    public void setPresenter(ComposeContract.Presenter presenter) {

    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void showError(String message) {

    }
}
