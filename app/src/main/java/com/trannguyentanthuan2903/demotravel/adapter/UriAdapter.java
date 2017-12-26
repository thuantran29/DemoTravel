package com.trannguyentanthuan2903.demotravel.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.trannguyentanthuan2903.demotravel.R;

import java.util.List;

/**
 * Created by Administrator on 6/8/2017.
 */

public class UriAdapter extends RecyclerView.Adapter<UriAdapter.UriViewHolder> {
    private Context context;
    private List<Uri> mUris;
    private List<Uri> mPaths;

    public UriAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Uri> uris, List<Uri> paths) {
        mUris = uris;
        mPaths = paths;
        notifyDataSetChanged();
    }

    @Override
    public UriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UriViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ds_hinh, parent, false));
    }

    @Override
    public void onBindViewHolder(UriViewHolder holder, int position) {
        Glide.with(context)
                .load(mPaths.get(position).toString())
                .error(R.drawable.errorstop)
                .into(holder.mPath);
    }

    @Override
    public int getItemCount() {
        return mUris == null ? 0 : mUris.size();
    }

    public class UriViewHolder extends RecyclerView.ViewHolder {

        private ImageView mPath;

        UriViewHolder(View contentView) {
            super(contentView);
            mPath = (ImageView) contentView.findViewById(R.id.path);
        }
    }
}