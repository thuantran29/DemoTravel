package com.trannguyentanthuan2903.demotravel.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.trannguyentanthuan2903.demotravel.R;
import com.trannguyentanthuan2903.demotravel.dto.CommentDTO;
import com.trannguyentanthuan2903.demotravel.func.CircleTransform;

import java.util.List;

/**
 * Created by Administrator on 6/5/2017.
 */

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder> {
    private Context context;
    List<CommentDTO> arrayCmt;
    private boolean like = false;
    public AdapterComment(Context context, List<CommentDTO> arrayCmt) {
        this.context = context;
        this.arrayCmt = arrayCmt;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txName,txViTri,txTime,txCmt;
        ImageView imgDaiDien,imgPopup;
        Button btLike;
        public ViewHolder(View itemView) {
            super(itemView);
            txName= (TextView) itemView.findViewById(R.id.tenNguoiCmt);
            txViTri=(TextView) itemView.findViewById(R.id.vitriCmt);
            txTime= (TextView) itemView.findViewById(R.id.thoiGianCmt);
            txCmt=(TextView) itemView.findViewById(R.id.commentCmt);
            imgDaiDien=(ImageView) itemView.findViewById(R.id.imageAvatar);
            imgPopup=(ImageView) itemView.findViewById(R.id.imageViewPopup);
            btLike =(Button) itemView.findViewById(R.id.buttonLike);
        }
    }
    @Override
    public AdapterComment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_comment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterComment.ViewHolder holder, int position) {
        CommentDTO cmt = arrayCmt.get(position);
        holder.txName.setText(cmt.Name);
        holder.txTime.setText(cmt.Time);
        holder.txViTri.setText(cmt.ViTri);
        holder.txCmt.setText(cmt.Commnent);
        Picasso.with(context).load(cmt.Hinh).transform(new CircleTransform()).into(holder.imgDaiDien);
        holder.imgPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu= new PopupMenu(context,holder.imgPopup);
                popupMenu.getMenuInflater().inflate(R.menu.popup_comment, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(context, "Chọn "+ item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        holder.btLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (like){
                    like=false;
                    holder.btLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart_red,0,0,0);

                }else {
                    like= true;
                    holder.btLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart,0,0,0);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayCmt.size();
    }
}
