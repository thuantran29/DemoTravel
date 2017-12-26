package com.trannguyentanthuan2903.demotravel.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trannguyentanthuan2903.demotravel.R;
import com.trannguyentanthuan2903.demotravel.activity.ReplyActivity;
import com.trannguyentanthuan2903.demotravel.dto.CmtDTO;
import com.trannguyentanthuan2903.demotravel.func.CircleTransform;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 6/20/2017.
 */

public class AdapterWriteComment extends BaseAdapter {

    private Context context;
    private int layout;
    private List<CmtDTO> arrayComment;
    String key_cmt;

    public AdapterWriteComment(Context context, int layout, List<CmtDTO> arrayComment) {
        this.context = context;
        this.layout = layout;
        this.arrayComment = arrayComment;
    }

    @Override
    public int getCount() {
        return arrayComment.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayComment.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        TextView txtNameUser, txtContentComment, txtTraLoi;
        ImageView imgAvaUser;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View rowView = view;
        ViewHolder holder = new ViewHolder();
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(layout, null);

            holder.txtNameUser = (TextView) rowView.findViewById(R.id.nameUser);
            holder.txtContentComment = (TextView) rowView.findViewById(R.id.contentComment);
            holder.imgAvaUser = (ImageView) rowView.findViewById(R.id.avaUser);
            holder.txtTraLoi = (TextView) rowView.findViewById(R.id.traLoi);

            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        SharedPreferences prefs = context.getSharedPreferences("comment", MODE_PRIVATE);
        key_cmt = prefs.getString("key_cmt", null);

        final CmtDTO cm = arrayComment.get(i);

        holder.txtNameUser.setText(cm.Ten);
        holder.txtContentComment.setText(cm.Comment);
        Picasso.with(context).load(cm.Ava).transform(new CircleTransform()).into(holder.imgAvaUser);
        holder.txtTraLoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReplyActivity.class);
                intent.putExtra("key_comment", cm.Id);
                context.startActivity(intent);
            }
        });
        return rowView;
    }
}

