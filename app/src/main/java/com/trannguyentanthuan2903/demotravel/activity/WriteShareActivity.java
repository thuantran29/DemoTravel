package com.trannguyentanthuan2903.demotravel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trannguyentanthuan2903.demotravel.R;
import com.trannguyentanthuan2903.demotravel.adapter.AdapterChuDeWrite;
import com.trannguyentanthuan2903.demotravel.adapter.UriAdapter;
import com.trannguyentanthuan2903.demotravel.dto.ChuDeDTO;
import com.trannguyentanthuan2903.demotravel.func.CircleTransform;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;

import java.util.ArrayList;

import static com.trannguyentanthuan2903.demotravel.R.id.lv_languages;
import static com.zhihu.matisse.MimeType.GIF;
import static com.zhihu.matisse.MimeType.JPEG;
import static com.zhihu.matisse.MimeType.PNG;

public class WriteShareActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgHinhAva, imgChonHinhFolder,imgBack;
    final int REQUEST_CODE_CHOOSE = 123;
    UriAdapter mAdapter;
    ArrayList<ChuDeDTO> arrayChuDe;
    LinearLayout txChuDe;
    Toolbar toolbar;
    AdapterChuDeWrite adapterChuDeFolderPhoto;
    public static BottomSheetDialog bottomSheetDialog;
    public static TextView txTenChuDeWrite;
    public static ImageView imgHinhChuDeWrite;
    ListView lv_ChuDe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_share);
        anhXa();
        Picasso.with(WriteShareActivity.this).load(R.drawable.person).transform(new CircleTransform()).into(imgHinhAva);
        imgChonHinhFolder.setOnClickListener(this);
        imgBack.setOnClickListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.hinhFolderWrite);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layout = new GridLayoutManager(WriteShareActivity.this, 3);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(mAdapter = new UriAdapter(WriteShareActivity.this));

        init();

    }
    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txChuDe = (LinearLayout) findViewById(R.id.chonChuDe);
        txChuDe.setOnClickListener(this);
        arrayChuDe = new ArrayList<>();
        addChuDe();
        adapterChuDeFolderPhoto = new AdapterChuDeWrite(WriteShareActivity.this, R.layout.row_chu_de, arrayChuDe);
    }

    private void ShowBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_view, null);//phải gọi từ layout khác
        lv_ChuDe = (ListView) view.findViewById(lv_languages);
        lv_ChuDe.setAdapter(adapterChuDeFolderPhoto);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }
    private void addChuDe() {
        arrayChuDe.add(new ChuDeDTO("Nhà hàng", R.drawable.hotel, false));
        arrayChuDe.add(new ChuDeDTO("Khách sạn", R.drawable.hotel, false));
        arrayChuDe.add(new ChuDeDTO("Bar", R.drawable.bar, false));
        arrayChuDe.add(new ChuDeDTO("Club", R.drawable.hotel, false));
        arrayChuDe.add(new ChuDeDTO("Thể thao", R.drawable.hotel, false));
        arrayChuDe.add(new ChuDeDTO("Thám hiểm", R.drawable.hotel, false));
        arrayChuDe.add(new ChuDeDTO("Du lịch", R.drawable.hotel, false));
        arrayChuDe.add(new ChuDeDTO("Nhà hàng", R.drawable.hotel, false));
        arrayChuDe.add(new ChuDeDTO("Khách sạn", R.drawable.hotel, false));
        arrayChuDe.add(new ChuDeDTO("Bar", R.drawable.bar, false));
        arrayChuDe.add(new ChuDeDTO("Club", R.drawable.hotel, false));
        arrayChuDe.add(new ChuDeDTO("Thể thao", R.drawable.hotel, false));
        arrayChuDe.add(new ChuDeDTO("Thám hiểm", R.drawable.hotel, false));
        arrayChuDe.add(new ChuDeDTO("Du lịch", R.drawable.hotel, false));
        arrayChuDe.add(new ChuDeDTO("Nhà hàng", R.drawable.hotel, false));
        arrayChuDe.add(new ChuDeDTO("Khách sạn", R.drawable.hotel, false));
        arrayChuDe.add(new ChuDeDTO("Bar", R.drawable.bar, false));
        arrayChuDe.add(new ChuDeDTO("Club", R.drawable.hotel, false));
        arrayChuDe.add(new ChuDeDTO("Thể thao", R.drawable.hotel, false));
        arrayChuDe.add(new ChuDeDTO("Thám hiểm", R.drawable.hotel, false));
        arrayChuDe.add(new ChuDeDTO("Du lịch", R.drawable.hotel, false));
    }
    private void anhXa() {
        imgHinhAva = (ImageView) findViewById(R.id.hinhAvatarWrite);
        imgChonHinhFolder = (ImageView) findViewById(R.id.chonHinhFolder);
        imgBack=(ImageView) findViewById(R.id.backShare);
        imgHinhChuDeWrite =(ImageView) findViewById(R.id.hinhChuDeWrite);
        txTenChuDeWrite=(TextView) findViewById(R.id.tenChuDeWrite);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chonHinhFolder:
                Matisse.from(WriteShareActivity.this)
                        .choose(MimeType.of(JPEG, PNG, GIF))
                        .theme(R.style.Matisse_Dracula)
                        .countable(false)
                        .maxSelectable(6)
                        .imageEngine(new PicassoEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
                break;
            case R.id.backShare:
                finish();
                break;
            case R.id.chonChuDe:
                ShowBottomSheet();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mAdapter.setData(Matisse.obtainResult(data), Matisse.obtainResult(data));
        }
    }
}
