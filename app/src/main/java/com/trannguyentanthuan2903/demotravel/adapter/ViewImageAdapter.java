package com.trannguyentanthuan2903.demotravel.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.trannguyentanthuan2903.demotravel.R;
import com.trannguyentanthuan2903.demotravel.dto.DanhSachChiaSeDTO;

import java.util.List;

/**
 * Created by Administrator on 6/13/2017.
 */

public class ViewImageAdapter extends PagerAdapter {

    private List<DanhSachChiaSeDTO> arrayHinh;
    private LayoutInflater inflater;
    private Context context;

    public ViewImageAdapter(Context context, List<DanhSachChiaSeDTO> arrayHinh) {
        this.context = context;
        this.arrayHinh = arrayHinh;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return arrayHinh.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        DanhSachChiaSeDTO ds = arrayHinh.get(position);
        View myImageLayout = inflater.inflate(R.layout.slide_share, view, false);
        ImageView myImage = (ImageView) myImageLayout.findViewById(R.id.imageShare);
//        myImage.setImageResource(images.get(position));
        Glide.with(context).load(ds.ImageReview).fitCenter().into(myImage);
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
