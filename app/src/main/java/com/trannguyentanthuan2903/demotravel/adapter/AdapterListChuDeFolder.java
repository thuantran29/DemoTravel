package com.trannguyentanthuan2903.demotravel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trannguyentanthuan2903.demotravel.R;
import com.trannguyentanthuan2903.demotravel.dto.ChuDeDTO;

import java.util.List;

/**
 * Created by Administrator on 6/7/2017.
 */

public class AdapterListChuDeFolder extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ChuDeDTO> arrayChuDe;

    public AdapterListChuDeFolder(Context context, int layout, List<ChuDeDTO> arrayChuDe) {
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
    public class ViewHolder{
        TextView txtTenChuDe;
        ImageView imgView;
        CheckBox cb;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView= view;
        ViewHolder holder= new ViewHolder();
        if (rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView=inflater.inflate(layout,null);

            holder.txtTenChuDe=(TextView) rowView.findViewById(R.id.tenChuDe);
            holder.imgView=(ImageView) rowView.findViewById(R.id.hinhChuDe);
            holder.cb=(CheckBox) rowView.findViewById(R.id.checkboxChuDe);
            rowView.setTag(holder);
        }else {
            holder=(ViewHolder) rowView.getTag();
        }

        ChuDeDTO cd = arrayChuDe.get(i);
        holder.txtTenChuDe.setText(cd.TenChuDe);
        Picasso.with(context).load(cd.Hinh).into(holder.imgView);
        holder.cb.isChecked();
        return rowView;
    }
}
