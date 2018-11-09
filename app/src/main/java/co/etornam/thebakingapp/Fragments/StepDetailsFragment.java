package co.etornam.thebakingapp.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import co.etornam.thebakingapp.Activities.MainActivity;
import co.etornam.thebakingapp.Common.Constants;
import co.etornam.thebakingapp.Models.Recipe;
import co.etornam.thebakingapp.Models.Steps;
import co.etornam.thebakingapp.R;
import co.etornam.thebakingapp.Utils.PrefUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailsFragment extends Fragment {


    ExoPlayer exoPlayer;
    private ImageView placeholder;
    PlayerView exoPlayerView;
    ImageView playerPlaceholder;
    boolean playState = true;
    private static long playerPosition = C.TIME_UNSET;
    TextView discriptionTV;

    //exoplayer
    MediaSource mediaSource;
    ExtractorsFactory extractorsFactory;
    DefaultHttpDataSourceFactory dataSourceFactory;
    Uri videoURI;
    TrackSelector trackSelector;
    BandwidthMeter bandwidthMeter;

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        discriptionTV = view.findViewById(R.id.textview_StepDetailFragment);
        playerPlaceholder = view.findViewById(R.id.placeholder_of_player);

        exoMediaSetup(view, savedInstanceState, container);

        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // outState.putString("videourl", videoURL);
        outState.putBoolean("playstate", playState);
        outState.putLong("position", playerPosition);

    }

    @Override
    public void onPause() {

        if (exoPlayer != null) {
            playerPosition = exoPlayer.getCurrentPosition();
            exoPlayer.release();
            exoPlayer.stop();
            playState = false;
            exoPlayer=null;
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer.stop();
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (exoPlayer != null) {

            exoPlayer.setPlayWhenReady(false);
            exoPlayer.seekTo(playerPosition);
            exoPlayer.prepare(mediaSource);
            Toast.makeText( getActivity(), "ExoPlayer not null", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText( getActivity(), "ExoPlayer = null", Toast.LENGTH_SHORT).show();
            intializeExoPlayer();
        }

    }

    private void exoMediaSetup(View rootView, Bundle savedInstanceState, ViewGroup container) {

        try {
            exoPlayerView = rootView.findViewById(R.id.exo_player_view);
//------------------------------------------------------------------------------------------------------------------
//tablet
            if (PrefUtil.getPhoneOrTablet(getActivity()) == PrefUtil.TABLET) {
                Recipe recipe = getActivity().getIntent().getExtras().getParcelable(MainActivity.RECIPE_PARC_KEY);
                Steps steps = recipe.getSteps().get(PrefUtil.getPositionfortabletonly(getActivity()));
                String URL = steps.getVideoURL();

                if (steps.getVideoURL().isEmpty()) {
                    handleTHumbnail(steps);
                } else {
                    handleVideo(steps);
                }
                discriptionTV.setText(steps.getDescription());
            }

//phone
            else if (PrefUtil.getPhoneOrTablet(getActivity()) == PrefUtil.PHONE) {
                Steps steps = getActivity().getIntent().getExtras().getParcelable(DetailsFragment.STEP_RECIPE_PARC_KEY);
                if (steps != null) {
                    if (steps.getVideoURL().isEmpty()) {
                        handleTHumbnail(steps);

                    } else {
                        handleVideo(steps);
                    }
                }
                discriptionTV.setText(steps.getDescription());
            }

//--------------------------------------------------------------------------------------------------------------
            if (savedInstanceState != null) {
                playerPosition = savedInstanceState.getLong("position");
                playState = savedInstanceState.getBoolean("playstate");

            }

            intializeExoPlayer();


        } catch (Exception e) {
            Log.e("tag", " exoplayer error " + e.toString());
        }
    }


    private void intializeExoPlayer() {
        bandwidthMeter = new DefaultBandwidthMeter();
        trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        videoURI = Uri.parse(Constants.videoURL);
        dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
        extractorsFactory = new DefaultExtractorsFactory();
        mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);
        exoPlayerView.setPlayer(exoPlayer);
        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(playState);
        if (playerPosition != C.TIME_UNSET) {
            exoPlayer.seekTo(playerPosition);
        }

    }

    private void handleTHumbnail(Steps steps) {
        String thumbnailURL = steps.getThumbnailURL();
        if (!thumbnailURL.isEmpty()) {
            boolean checkEx = thumbnailURL.substring(thumbnailURL.length() - 3, thumbnailURL.length()).equals("jpg");

            if (!thumbnailURL.isEmpty() && checkEx) {
                playerPlaceholder.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(steps.getThumbnailURL())
                        .placeholder(R.drawable.ic_placeholder_video)
                        .into(playerPlaceholder);
            }

        } else {
            Picasso.get()
                    .load(R.drawable.ic_placeholder_video)
                    .into(playerPlaceholder);

        }


    }

    private void handleVideo(Steps steps) {
        String URL = steps.getVideoURL();
        boolean b = URL.substring(URL.length() - 3, URL.length()).equals("mp4");
        if (b) {
            Toast.makeText(getActivity(), "mp4==mp4", Toast.LENGTH_SHORT).show();
            Constants.videoURL = steps.getVideoURL();
            playerPlaceholder.setVisibility(View.GONE);
        } else {

            playerPlaceholder.setVisibility(View.VISIBLE);
        }
    }


    public void displayReceivedData(int position) {
        PrefUtil.setPositionfortabletonly(Objects.requireNonNull(getActivity()), position);
        //getActivity().getFragmentManager().beginTransaction().remove().commit();
    }

}
