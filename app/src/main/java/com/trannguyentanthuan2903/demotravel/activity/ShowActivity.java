package com.trannguyentanthuan2903.demotravel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trannguyentanthuan2903.demotravel.R;
import com.trannguyentanthuan2903.demotravel.adapter.ViewImageAdapter;
import com.trannguyentanthuan2903.demotravel.dto.DanhSachChiaSeDTO;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgBack;
    ArrayList<DanhSachChiaSeDTO> arrayList;
    ViewImageAdapter adapter;
    private DatabaseReference mDatabase;
    String key_id = "";

    private ViewPager mPagers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        imgBack = (ImageView) findViewById(R.id.backShow);
        imgBack.setOnClickListener(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent myIntent = ShowActivity.this.getIntent();
        key_id = myIntent.getStringExtra("key_id");
        Toast.makeText(this, "mã "+key_id, Toast.LENGTH_SHORT).show();

        arrayList = new ArrayList<>();
        mPagers = (ViewPager) findViewById(R.id.viewImagePager);
        adapter = new ViewImageAdapter(ShowActivity.this, arrayList);
        mPagers.setAdapter(adapter);

//        mDatabase.child("DanhSach").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                DanhSachChiaSeDTO ds = dataSnapshot.getValue(DanhSachChiaSeDTO.class);
//                arrayList.add(new DanhSachChiaSeDTO(
//                        dataSnapshot.getKey()
//                        ,ds.Name
//                        ,ds.Title
//                        ,ds.Time
//                        ,ds.Content
//                        ,ds.ImageAvatar
//                        ,ds.ImageReview
//                        ,ds.ViTri
//                        ,ds.lattitude
//                        ,ds.longtitude));
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//      .child(key_id)
        mDatabase.child("DanhSach").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DanhSachChiaSeDTO ds = dataSnapshot.getValue(DanhSachChiaSeDTO.class);
                arrayList.add(new DanhSachChiaSeDTO(
                        dataSnapshot.getKey(),
                        ds.Name,
                        ds.Title,
                        ds.Time,
                        ds.Content,
                        ds.ImageAvatar,
                        ds.ImageReview,
                        ds.ViTri,
                        ds.lattitude,
                        ds.longtitude
                ));
                //cận thận nhớ thêm adapter.notifiDataSetChange
                adapter.notifyDataSetChanged();

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backShow:
                startActivity(new Intent(ShowActivity.this,MainActivity.class));
                break;

        }
    }
}
