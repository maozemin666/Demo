package com.example.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.PlayAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class PreviewActivity extends AppCompatActivity {
    private static final String TAG = "PreviewActivity";

    public static final String FETCH_STREAM = "fetch_stream";
    public static final String OUT_STREAM = "out_stream";
    public static final String PREVIEW_URLS = "previewURLs";

    static {
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
    }

    private PlayAdapter adapter;

    public static void startPreviewActivity(Context context, ArrayList<String> previewURLs) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putStringArrayListExtra(PREVIEW_URLS, previewURLs);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        final RecyclerView recy = findViewById(R.id.recy);
        recy.setLayoutManager(new GridLayoutManager(this, 3));
        recy.setHasFixedSize(true);
        adapter = new PlayAdapter();
        recy.setAdapter(adapter);
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> strings = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
//                    strings.add("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
//                    strings.add("rtsp://3.84.6.190/vod/mp4:BigBuckBunny_115k.mov");
                    strings.add("rtmp://58.200.131.2:1935/livetv/hunantv");
                }
                adapter.addData(strings);
            }
        });
        loadData();
    }

    private void loadData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        ArrayList<String> urls = intent.getStringArrayListExtra(PREVIEW_URLS);
        if (urls == null || urls.size() <= 0) {
            Toast.makeText(this, "视频预览资源的urls is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        adapter.addData(urls);
    }

    @Override
    protected void onStop() {
        super.onStop();
        IjkMediaPlayer.native_profileEnd();
    }
}
