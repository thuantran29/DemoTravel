package com.trannguyentanthuan2903.demotravel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.trannguyentanthuan2903.demotravel.recyclerclick.ItemClickListener;
import com.trannguyentanthuan2903.demotravel.R;
import com.trannguyentanthuan2903.demotravel.dto.DSHinhDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 6/6/2017.
 */

public class AdapterDSHinh extends RecyclerView.Adapter<AdapterDSHinh.ViewHolder> {
    private Context context;
    List<DSHinhDTO> arrayHinh;
    public ArrayList<DSHinhDTO> checked = new ArrayList<>();

    public AdapterDSHinh(Context context, List<DSHinhDTO> arrayHinh) {
        this.context = context;
        this.arrayHinh = arrayHinh;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgHinh;
        TextView txTen;
        CheckBox checkBox;
        ItemClickListener itemClickListener;
        public ViewHolder(View itemView) {
            super(itemView);
//            imgHinh= (ImageView) itemView.findViewById(R.id.hinhDS);
//            txTen=(TextView) itemView.findViewById(R.id.tenHinhDS);
//            checkBox=(CheckBox) itemView.findViewById(R.id.checBoxDS);
        }
    }

    @Override
    public AdapterDSHinh.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_ds_hinh,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterDSHinh.ViewHolder viewHolder, int i) {
        DSHinhDTO hinh =arrayHinh.get(i);
//        viewHolder.txTen.setText(hinh.tenHinh);
//        Picasso.with(context).load(hinh.getUri()).into(viewHolder.imgHinh);
    }

    @Override
    public int getItemCount() {
        return arrayHinh.size();
    }
}
