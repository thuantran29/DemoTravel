package com.trannguyentanthuan2903.demotravel.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.trannguyentanthuan2903.demotravel.R;
import com.trannguyentanthuan2903.demotravel.adapter.AdapterDS;
import com.trannguyentanthuan2903.demotravel.dto.DanhSachChiaSeDTO;
import com.trannguyentanthuan2903.demotravel.dto.ShareDTO;
import com.trannguyentanthuan2903.demotravel.recyclerclick.RecyclerItemClickListener;
import com.trannguyentanthuan2903.demotravel.recyclerclick.RecyclerViewClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AHBottomNavigation.OnTabSelectedListener {
    AHBottomNavigation ahBottomNavi;
    FragmentManager fragmentManager;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolBarSearch;
    boolean doubleBack = false;
    RecyclerView recyclerViewShare;
    ArrayList<ShareDTO> arrayShare;
    String st = "Trong 3 ngày từ 2/6 đến 4/6, miền Bắc đón nhận đợt nắng nóng hơn 41 độ C." +
            "Số liệu nhiệt độ đo tại các trạm khí tượng ở Hà Nội ghi nhận mức vượt kỷ lục hơn 40 năm qua." +
            "Mặt đường tại khu vực Mỹ Đình (Hà Nội) như bốc hơi khi ở ngưỡng xấp xỉ 60 độ C";

    private DatabaseReference mDatabase;
    private FirebaseStorage storage;
    ArrayList<DanhSachChiaSeDTO> arrayDS;
    AdapterDS adapterDS;
    String key_bai_viet="";
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MultiDex.install(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        toolBarSearch = (Toolbar) findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolBarSearch);
        getSupportActionBar().setTitle("Bảng tin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBarSearch.setNavigationIcon(R.drawable.icon_list);
        toolBarSearch.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        ahBottomNavi = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();
        ahBottomNavi.setOnTabSelectedListener(this);
        this.createNaviItem();

        arrayDS = new ArrayList<>();
        adapterDS = new AdapterDS(MainActivity.this, arrayDS);

        recyclerViewShare = (RecyclerView) findViewById(R.id.recyclerShare);
        recyclerViewShare.setHasFixedSize(true);
        recyclerViewShare.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutSuKien = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        layoutSuKien.setReverseLayout(true);
        layoutSuKien.setStackFromEnd(true);
        recyclerViewShare.setLayoutManager(layoutSuKien);


        mDatabase.child("DanhSach").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DanhSachChiaSeDTO ds = dataSnapshot.getValue(DanhSachChiaSeDTO.class);
                arrayDS.add(new DanhSachChiaSeDTO(
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
                adapterDS.notifyDataSetChanged();
                recyclerViewShare.setAdapter(adapterDS);
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

        recyclerViewShare.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, recyclerViewShare, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        navigationView.setItemIconTintList(null);
        //chọn các id thuc hiện các lệnh
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuVietBai:
                        startActivity(new Intent(MainActivity.this,WriteShareActivity.class));
                        break;
                    case R.id.menuChiaSeHinh:
                        startActivity(new Intent(MainActivity.this,FolderPhotoActivity.class));
                        break;
                    case R.id.menuChiaSeCamera:
                        startActivity(new Intent(MainActivity.this,ListPhotoActivity.class));
                        break;
                    case R.id.menuThongTin:
                        break;
                    case R.id.menuDoiMatKhau:
                        break;
                    case R.id.menuClose:
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });


    }
    void refreshItems() {
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void createNaviItem() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("", R.drawable.pen);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("", R.drawable.insert);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("", R.drawable.camera);

        ahBottomNavi.addItem(item1);
        ahBottomNavi.addItem(item2);
        ahBottomNavi.addItem(item3);

        ahBottomNavi.setDefaultBackgroundColor(Color.parseColor("#00b4d4"));
        ahBottomNavi.setAccentColor(Color.parseColor("#FFFFFF"));
        ahBottomNavi.setInactiveColor(Color.parseColor("#e9e7e7"));
        ahBottomNavi.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        ahBottomNavi.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        switch (position) {
            case 0:
                startActivity(new Intent(MainActivity.this, WriteShareActivity.class));
                break;
            case 1:
                startActivity(new Intent(MainActivity.this, FolderPhotoActivity.class));
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, ListPhotoActivity.class));
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("Search...");
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                adpater.filter(newText.trim());
                return false;
            }
        });
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popup_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (doubleBack) {
            super.onBackPressed();
            return;
        }

        this.doubleBack = true;
        Toast.makeText(this, "BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBack = false;
            }
        }, 2000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    // on click
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

}