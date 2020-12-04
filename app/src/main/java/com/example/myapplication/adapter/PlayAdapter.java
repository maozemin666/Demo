package com.example.myapplication.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.R;
import com.example.myapplication.player.media.widget.IpCameraView;

import org.jetbrains.annotations.NotNull;

public class PlayAdapter extends BaseQuickAdapter<String , BaseViewHolder> {
    public PlayAdapter() {
        super(R.layout.adapter_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        IpCameraView ipv = baseViewHolder.getView(R.id.ipv);
        ipv.startPlay(s);

    }

    @NotNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = super.onCreateViewHolder(parent, viewType);
        IpCameraView ipCameraView = baseViewHolder.getView(R.id.ipv);
        int screenWidth = getScreenWidth(parent.getContext());
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ipCameraView.getLayoutParams();
        layoutParams.height =screenWidth/3;
        layoutParams.width =screenWidth/3;

        return baseViewHolder;

    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

}

