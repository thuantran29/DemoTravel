package com.trannguyentanthuan2903.demotravel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trannguyentanthuan2903.demotravel.R;
import com.trannguyentanthuan2903.demotravel.dto.ReplyDTO;
import com.trannguyentanthuan2903.demotravel.func.CircleTransform;

import java.util.List;

/**
 * Created by Administrator on 6/20/2017.
 */

public class AdapterReply extends BaseAdapter {

    private Context context;
    private int layout;
    private List<ReplyDTO> arrayReply;

    public AdapterReply(Context context, int layout, List<ReplyDTO> arrayReply) {
        this.context = context;
        this.layout = layout;
        this.arrayReply = arrayReply;
    }

    @Override
    public int getCount() {
        return arrayReply.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayReply.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder {
        TextView txtNameUserTL, txtContentCommentTL, txtLikeTL;
        ImageView imgAvaUserTL;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = view;
        ViewHolder holder = new ViewHolder();
        if (rowView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(layout,null);

            holder.txtNameUserTL = (TextView) rowView.findViewById(R.id.nameUserTL);
            holder.txtContentCommentTL=(TextView) rowView.findViewById(R.id.contentCommentTL);
            holder.txtLikeTL = (TextView) rowView.findViewById(R.id.likeTL);
            holder.imgAvaUserTL= (ImageView) rowView.findViewById(R.id.avaUserTL);
            rowView.setTag(holder);
        }else {
            holder = (ViewHolder) rowView.getTag();
        }

        ReplyDTO rep = arrayReply.get(i);

        holder.txtNameUserTL.setText(rep.Name);
        holder.txtContentCommentTL.setText(rep.Content);
        Picasso.with(context).load(rep.Ava).transform(new CircleTransform()).into(holder.imgAvaUserTL);

        return rowView;
    }
}
