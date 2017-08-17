package com.example.paldo.Activity;

import android.os.Bundle;

import com.example.paldo.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.example.paldo.Data.Constants;

public class VideoFragment extends YouTubePlayerFragment {

    private String source = "HlVRWlhJw0s";

    public VideoFragment(){

    }
    public static VideoFragment newInstance(String url){
        VideoFragment f = new VideoFragment();

        Bundle b = new Bundle();
        b.putString("url", url);

        f.setArguments(b);
        f.init();

        return f;
    }

    public void setSource(String source){
        this.source = source;
        init();
    }

    private void init(){
        initialize(Constants.YOUTUBE_KEY, new YouTubePlayer.OnInitializedListener(){
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if(!b){

                    youTubePlayer.cueVideo(getArguments().getString("url"));
                }
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }
}
