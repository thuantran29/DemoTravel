package com.trannguyentanthuan2903.demotravel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.trannguyentanthuan2903.demotravel.R;

/**
 * Created by Administrator on 6/13/2017.
 */

public class AdapterDSHinhShare extends RecyclerView.Adapter<AdapterDSHinhShare.ViewHolder> {

    private Context context;
    String arrayHinh;

    public AdapterDSHinhShare(Context context, String arrayHinh) {
        this.context = context;
        this.arrayHinh = arrayHinh;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinh;
        public ViewHolder(View itemView) {
            super(itemView);
            imgHinh= (ImageView) itemView.findViewById(R.id.hinhDsShare);
        }
    }

    @Override
    public AdapterDSHinhShare.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_ds_hinh_share,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterDSHinhShare.ViewHolder holder, int position) {

        Glide.with(context).load(arrayHinh).centerCrop().into(holder.imgHinh);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

}
