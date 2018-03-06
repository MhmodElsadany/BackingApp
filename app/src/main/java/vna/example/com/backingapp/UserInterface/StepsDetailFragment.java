package vna.example.com.backingapp.UserInterface;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vna.example.com.backingapp.Models.StepItem;
import vna.example.com.backingapp.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class StepsDetailFragment extends Fragment {
    private SimpleExoPlayer exoplayer;
    TextView descrption;
    ImageView nextVideo, previosVideo, thumbnailURL;
    private SimpleExoPlayerView exoplayerView;
    private long playbackposition;
    String thumbnail = "";
    private int currentwindow;
    private boolean playwhenready = false;
    public String playwhenreadyKey = "playWhenReadyKey";
    ArrayList<StepItem> mStepItems = new ArrayList<>();
    DetailBackingFragment mDetailBackingFragment;
    int position;
    String description = "";
    String descriptionKey = "Description";


    String videoURL = "";
    String videoURLKey = "videoURL";
    String positionKey = "positionKey";
    long positionMin = -1;
    String TAG = "StepsDetailFragment";

    public StepsDetailFragment() {
    }

    Bundle bundle = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_steps_detail, container, false);
        exoplayerView =     (SimpleExoPlayerView) view.findViewById(R.id.player_view);
        nextVideo     =     (ImageView) view.findViewById(R.id.nextVideo);
        previosVideo  =     (ImageView) view.findViewById(R.id.previousVideo);
        thumbnailURL  =     (ImageView) view.findViewById(R.id.img);
        descrption    =     (TextView) view.findViewById(R.id.description);

        if (savedInstanceState != null) {
            Log.i(TAG, "onCreateView playback position" + savedInstanceState.getLong(positionKey) + "");
            bundle = savedInstanceState;
        } else {
            Log.i(TAG, "savedInstance is null");
            bundle = getArguments();
        }

        mDetailBackingFragment = new DetailBackingFragment();
        mStepItems = mDetailBackingFragment.stepsList;
        position = bundle.getInt("id");

        setPosition(position);
        if (mStepItems.get(position).getVideoURL() != null) {

            setVideoURL(mStepItems.get(position).getVideoURL());
            initialzeplayer(mStepItems.get(position).getVideoURL());

        }
        descrption.setText(mStepItems.get(position).getDescription());

        thumbnail = mStepItems.get(position).getThumbnailURL();
        if (!thumbnail.equals("")) {
            Picasso.with(getContext()).load(thumbnail).into(thumbnailURL);
        }


        nextVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position < mStepItems.size() - 1) {
                    position++;
                    releasePlayer();
                    descrption.setText(mStepItems.get(position).getDescription());
                    thumbnail = mStepItems.get(position).getThumbnailURL();
                    if (!thumbnail.equals("")) {
                        Picasso.with(getContext()).load(thumbnail).into(thumbnailURL);
                    }
                    setPosition(position);
                    if (mStepItems.get(position).getVideoURL() != null)
                        initialzeplayer(mStepItems.get(position).getVideoURL());
                } else {

                }
            }

        });
        previosVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position > 0) {
                    position--;
                    releasePlayer();
                    descrption.setText(mStepItems.get(position).getDescription());
                    setPosition(position);
                    thumbnail = mStepItems.get(position).getThumbnailURL();
                    if (!thumbnail.equals("")) {
                        Picasso.with(getContext()).load(thumbnail).into(thumbnailURL);
                    }
                    if (mStepItems.get(position).getVideoURL() != null)
                        initialzeplayer(mStepItems.get(position).getVideoURL());
                } else {

                }
            }
        });

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI();
            exoplayerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            descrption.setVisibility(View.GONE);
            previosVideo.setVisibility(View.GONE);
            nextVideo.setVisibility(View.GONE);
        }
        return view;
    }

    private void hideSystemUI() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void initialzeplayer(String videourl) {
        Log.i(TAG, "initialize player ");

        try {

            BandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(BANDWIDTH_METER));
            exoplayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            Uri videouri = Uri.parse(videourl);
            MediaSource mediaSource = new ExtractorMediaSource(videouri, dataSourceFactory, extractorsFactory, null, null);
            exoplayerView.setPlayer(exoplayer);
            long retrievedPlaybackPosition = bundle.getLong(positionKey, -1);
            boolean retrievedPlayWhenReady = bundle.getBoolean(playwhenreadyKey);
            if (retrievedPlaybackPosition != -1) {

                exoplayer.seekTo(retrievedPlaybackPosition);
                exoplayer.prepare(mediaSource);
                exoplayer.setPlayWhenReady(retrievedPlayWhenReady);

            } else {

                exoplayer.prepare(mediaSource);
                exoplayer.setPlayWhenReady(true);
            }


        } catch (Exception e) {
            System.out.print("error");
        }
    }

    public void releasePlayer() {

        if (exoplayer != null) {
            playbackposition = exoplayer.getCurrentPosition();
            currentwindow = exoplayer.getCurrentWindowIndex();
            playwhenready = exoplayer.getPlayWhenReady();
            exoplayer.stop();
            exoplayer.release();
            exoplayer = null;
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "savedInstanceState play back position " + playbackposition);
        outState.putLong(positionKey, playbackposition);
        outState.putInt("id", getPosition());
        outState.putBoolean(playwhenreadyKey, playwhenready);
    }


    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setDescription(String description) {
        description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
