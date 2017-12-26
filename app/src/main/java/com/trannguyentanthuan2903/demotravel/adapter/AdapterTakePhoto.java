package com.trannguyentanthuan2903.demotravel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.trannguyentanthuan2903.demotravel.R;
import com.trannguyentanthuan2903.demotravel.activity.ListPhotoActivity;
import com.trannguyentanthuan2903.demotravel.dto.ChuDeDTO;

import java.util.ArrayList;

/**
 * Created by Administrator on 6/9/2017.
 */

public class AdapterTakePhoto extends BaseAdapter {
    private Context context;
    private int layout;
    ArrayList<ChuDeDTO> arrayChuDe;

    public AdapterTakePhoto(Context context, int layout, ArrayList<ChuDeDTO> arrayChuDe) {
        this.context = context;
        this.layout = layout;
        this.arrayChuDe = arrayChuDe;
    }

    @Override
    public int getCount() {
        return arrayChuDe.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayChuDe.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        TextView txtTen;
        ImageView imgHinh;
        CheckBox cb;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = view;
        ViewHolder holder = new ViewHolder();
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(layout, null);
            holder.txtTen = (TextView) rowView.findViewById(R.id.tenChuDe);
            holder.imgHinh = (ImageView) rowView.findViewById(R.id.hinhChuDe);
            holder.cb = (CheckBox) rowView.findViewById(R.id.checkboxChuDe);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        final ChuDeDTO sp = arrayChuDe.get(i);

        holder.txtTen.setText(sp.TenChuDe);
        Picasso.with(context).load(sp.Hinh).
                placeholder(R.drawable.product_icon).
                error(R.drawable.error).
                into(holder.imgHinh);
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListPhotoActivity.txTenChuDeBottom.setText(sp.TenChuDe);
                Glide.with(context).load(sp.Hinh).into(ListPhotoActivity.imgHinhChuDeBottom);
                ListPhotoActivity.bottomSheetDialog.dismiss();
            }
        });
        return rowView;

    }
}
