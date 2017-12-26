package com.trannguyentanthuan2903.demotravel.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;
import com.trannguyentanthuan2903.demotravel.R;
import com.trannguyentanthuan2903.demotravel.activity.InforActivity;
import com.trannguyentanthuan2903.demotravel.dto.CmtDTO;
import com.trannguyentanthuan2903.demotravel.dto.DanhSachChiaSeDTO;
import com.trannguyentanthuan2903.demotravel.func.CircleTransform;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 6/12/2017.
 */

public class AdapterDS extends RecyclerView.Adapter<AdapterDS.ViewHolder> {

    private Context context;
    final List<DanhSachChiaSeDTO> arrayShare;
    private ArrayList<DanhSachChiaSeDTO> arrayList;
    AdapterDSHinhShare adapterDSHinhShare;
    private DatabaseReference mDatabase;
    ListView listView;
    public BottomSheetDialog bottomSheetDialog;
    ArrayList<CmtDTO> arrayCmt;

    public AdapterDS(Context context, List<DanhSachChiaSeDTO> arrayShare) {
        this.context = context;
        this.arrayShare = arrayShare;

        this.arrayList = new ArrayList<DanhSachChiaSeDTO>();
        this.arrayList.addAll(arrayShare);

    }
    @Override
    public AdapterDS.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_share, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txTitle, txName, txTime, txContent, txXemThem;
        ImageView imgHinh, imgPopup, imgWrite,imgShare;
//        RecyclerView recyclerView;
        public ViewHolder(View itemView) {
            super(itemView);

            txTitle = (TextView) itemView.findViewById(R.id.txTitle);
            txName = (TextView) itemView.findViewById(R.id.txName);
            txTime = (TextView) itemView.findViewById(R.id.txTime);
            txContent = (TextView) itemView.findViewById(R.id.txContent);
            txXemThem = (TextView) itemView.findViewById(R.id.xemThem);

            imgWrite = (ImageView) itemView.findViewById(R.id.imgWriteComment);
            imgPopup = (ImageView) itemView.findViewById(R.id.imagePopupBaiViet);
            imgHinh = (ImageView) itemView.findViewById(R.id.hinhAvatarFolder);
//            recyclerView = (RecyclerView) itemView.findViewById(R.id.imgReview);
            imgShare = (ImageView) itemView.findViewById(R.id.imgReview);
        }
    }


    @Override
    public void onBindViewHolder(final AdapterDS.ViewHolder holder, final int position) {
        final DanhSachChiaSeDTO share = arrayShare.get(position);

        holder.txTitle.setText(share.Title);
        holder.txName.setText(share.Name);
        holder.txContent.setText(share.Content);
        holder.txTime.setText(share.Time);
        holder.txXemThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                context.startActivity(new Intent(context.getApplicationContext(), InforActivity.class));

                String toado = share.lattitude
                        + "," + share.longtitude;
                Toast.makeText(context, toado, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, InforActivity.class);
                intent.putExtra("toa_do", toado);
                context.startActivity(intent);
            }
        });
        Picasso.with(context).load(R.drawable.person).transform(new CircleTransform()).into(holder.imgHinh);
//        Picasso.with(context).load(share.ImageReview).into(holder.imgShare);
        holder.imgPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.imgPopup);
                popupMenu.getMenuInflater().inflate(R.menu.popup_ds, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(context, "Chọn " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        adapterDSHinhShare = new AdapterDSHinhShare(context, share.ImageReview);

//        holder.recyclerView.setHasFixedSize(true);
//        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
//        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(3, 1);
//        layout.setReverseLayout(true);
//        holder.recyclerView.setLayoutManager(layout);
//        holder.recyclerView.setAdapter(adapterDSHinhShare);
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        adapterDSHinhShare.notifyDataSetChanged();
//        holder.recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, holder.recyclerView, new RecyclerViewClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Intent intent = new Intent(context, ShowActivity.class);
//                intent.putExtra("key_id", share.Id);
//                context.startActivity(intent);
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));

        Picasso.with(context).load(share.ImageReview).into(holder.imgShare);

        holder.imgWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowBottomSheet(share.Id);
            }
        });

    }

    private void ShowBottomSheet(final String id) {

        arrayCmt = new ArrayList<>();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bottom_sheet_comment, null);//phải gọi từ layout khác
        listView = (ListView) view.findViewById(R.id.lv_comment);
        final AdapterWriteComment adapter = new AdapterWriteComment((Activity) context,R.layout.row_parent_comment, arrayCmt);
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        final EditText etWriteCmt = (EditText) view.findViewById(R.id.editComment);

        etWriteCmt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP)
                    if (event.getRawX() >= (etWriteCmt.getRight() - etWriteCmt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        CmtDTO cmt = new CmtDTO(null, "Anyone", R.drawable.person, etWriteCmt.getText().toString());
                        mDatabase.child("Comment").child(id).push().setValue(cmt);
                        etWriteCmt.setText("");
                        return true;
                    }
                return false;
            }
        });

        mDatabase.child("Comment").child(id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CmtDTO cmt = dataSnapshot.getValue(CmtDTO.class);
                arrayCmt.add(new CmtDTO(dataSnapshot.getKey(),
                        cmt.Ten,
                        cmt.Ava,
                        cmt.Comment));

                SharedPreferences.Editor editgv = context.getSharedPreferences("comment", MODE_PRIVATE).edit();
                editgv.putString("key_cmt",id);
                editgv.commit();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    @Override
    public int getItemCount() {
        return arrayShare.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        arrayShare.clear();
        if (charText.length() == 0) {// offline tìm ra dc, còn online thi dợi add lại
            arrayShare.addAll(arrayList);
        } else {
            for (DanhSachChiaSeDTO share : arrayList) {
                if (share.Name.toLowerCase(Locale.getDefault()).contains(charText)) {
                    arrayShare.add(share);
                }
            }
        }
        notifyDataSetChanged();
    }

}
