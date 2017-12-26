package com.trannguyentanthuan2903.demotravel.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.trannguyentanthuan2903.demotravel.R;
import com.trannguyentanthuan2903.demotravel.activity.MainActivity;
import com.trannguyentanthuan2903.demotravel.adapter.AdapterChuDeFolderPhoto;
import com.trannguyentanthuan2903.demotravel.adapter.UriAdapter;
import com.trannguyentanthuan2903.demotravel.dto.ChuDeDTO;
import com.trannguyentanthuan2903.demotravel.dto.ChuoiHinhDTO;
import com.trannguyentanthuan2903.demotravel.func.CircleTransform;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.trannguyentanthuan2903.demotravel.R.id.lv_languages;
import static com.zhihu.matisse.MimeType.GIF;
import static com.zhihu.matisse.MimeType.JPEG;
import static com.zhihu.matisse.MimeType.PNG;

/**
 * Created by Administrator on 6/16/2017.
 */

public class FragmentFolder extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_folder_photo,container,false);
    }
    private static final int MY_IMPRESSION_REQUEST_LOCATION = 1;
    ImageView imgFolder, imgHinhAva, imgBack, imgSend;
    EditText etStatus;
    final int REQUEST_CODE_CHOOSE = 456;
    UriAdapter mAdapter;
    ArrayList<ChuDeDTO> arrayChuDe;
    LinearLayout txChuDe;
    Toolbar toolbar;
    AdapterChuDeFolderPhoto adapterChuDeFolderPhoto;
    public static BottomSheetDialog bottomSheetDialog;
    public static TextView txTenChuDeFolder;
    public static ImageView imgHinhChuDeFolder;
    ListView lv_ChuDe;
    String lat, lon, vitri;

    private DatabaseReference mDatabase;
    private FirebaseStorage storage;
    StorageReference storageRef;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        anhXa();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://imagelocation-b6d27.appspot.com");

        Picasso.with(getActivity()).load(R.drawable.person).transform(new CircleTransform()).into(imgHinhAva);

        Picasso.with(getActivity()).load(R.drawable.person).transform(new CircleTransform()).into(imgHinhAva);
        Matisse.from(getActivity())
                .choose(MimeType.of(JPEG, PNG, GIF))
                .theme(R.style.Matisse_Dracula)
                .countable(false)
                .maxSelectable(6)
                .imageEngine(new PicassoEngine())
                .forResult(REQUEST_CODE_CHOOSE);
        imgBack.setOnClickListener(this);

        final RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerviewFolder);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layout = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(mAdapter = new UriAdapter(getActivity()));

        init();

        sendDiaChi();

//        imgSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
//                final String now = df.format(new Date());
//                final Calendar calendar = Calendar.getInstance();
//                StorageReference mountainsRef = storageRef.child("DanhSach/image" + calendar.getTimeInMillis() + ".png");
//                BitmapDrawable drawable = (BitmapDrawable) recyclerView.getDrawable();
//                Bitmap bitmap = drawable.getBitmap();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                byte[] data = baos.toByteArray();
//
//                UploadTask uploadTask = mountainsRef.putBytes(data);
//                uploadTask.addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        Log.d("AAA", "Lỗi lưu hình tài khoản: " + exception.toString());
//                    }
//                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                        DanhSachChiaSeDTO sha = new DanhSachChiaSeDTO(
//                                null,
//                                "Anoymous",
//                                txTenChuDeFolder.getText().toString().trim(),
//                                now,
//                                etStatus.getText().toString().trim(),
//                                R.drawable.person,
//                                String.valueOf(downloadUrl),
//                                vitri,
//                                lat, lon
//                        );
//                        mDatabase.child("DanhSach").push().setValue(sha, new DatabaseReference.CompletionListener() {
//                            @Override
//                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                                if (databaseError == null) {
//                                    Toast.makeText(FolderPhotoActivity.this, "Thêm vị trí thành công.", Toast.LENGTH_SHORT).show();
//                                    Intent intentBackMain = new Intent(FolderPhotoActivity.this, MainActivity.class);
//                                    startActivity(intentBackMain);
//                                } else {
//                                    Toast.makeText(FolderPhotoActivity.this, "Lỗi thêm vị trí!", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    }
//                });
//            }
//        });
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void publishProduct(final String id, final String name, final String title, final String time, final String content,
                                final int imageAvatar, final List<String> imageReview,
                                final String viTri, final String lattitude, final String longtitude) {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        final String now = df.format(new Date());

        for (String photo : imageReview) {
            Uri file = Uri.fromFile(new File(photo));
            final Calendar calendar = Calendar.getInstance();
            StorageReference mountainsRef = storageRef.child("DanhSach/image" + calendar.getTimeInMillis() + ".png");
            UploadTask mUploadTask = mountainsRef.putFile(file);

            mUploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("AAA", "Lỗi lưu hình tài khoản: " + exception.toString());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    imageReview.add(String.valueOf(downloadUrl));

                    ChuoiHinhDTO sha = new ChuoiHinhDTO(
                            null,
                            "Anoymous",
                            txTenChuDeFolder.getText().toString().trim(),
                            now,
                            etStatus.getText().toString().trim(),
                            R.drawable.person,
                            imageReview,
                            vitri,
                            lat, lon
                    );
                    mDatabase.child("DanhSach").push().setValue(sha, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                Toast.makeText(getActivity(), "Thêm vị trí thành công.", Toast.LENGTH_SHORT).show();
                                Intent intentBackMain = new Intent(getActivity(), MainActivity.class);
                                startActivity(intentBackMain);
                            } else {
                                Toast.makeText(getActivity(), "Lỗi thêm vị trí!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
    }

    private void sendDiaChi() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_IMPRESSION_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_IMPRESSION_REQUEST_LOCATION);
            }

        } else {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try {
                vitri = (hereLocation(location.getLatitude(), location.getLongitude()));
                lat = String.valueOf(latitude(location.getLatitude(), location.getLongitude()));
                lon = String.valueOf(longtitude(location.getLatitude(), location.getLongitude()));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Không tìm thấy", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_IMPRESSION_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        try {
                            vitri = (hereLocation(location.getLatitude(), location.getLongitude()));
                            lat = String.valueOf(latitude(location.getLatitude(), location.getLongitude()));
                            lon = String.valueOf(longtitude(location.getLatitude(), location.getLongitude()));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Không tìm thấy", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Thiếu permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public String hereLocation(double lat, double lon) {

        String address = "";
        double latStart, logStart;
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocation(lat, lon, 1);
            if (addressList.size() > 0) {
                address = addressList.get(0).getAddressLine(0) + " " + addressList.get(0).getAddressLine(1) + " " +
                        addressList.get(0).getAddressLine(2) + " " + addressList.get(0).getAddressLine(3);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    public double latitude(double lat, double lon) {

        double latStart = 0, logStart;
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocation(lat, lon, 1);
            if (addressList.size() > 0) {

                latStart = addressList.get(0).getLatitude();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latStart;
    }

    public double longtitude(double lat, double lon) {

        double logStart = 0;
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocation(lat, lon, 1);
            if (addressList.size() > 0) {
                logStart = addressList.get(0).getLongitude();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logStart;
    }

    private void init() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        txChuDe = (LinearLayout) getActivity().findViewById(R.id.chonChuDe);
        txChuDe.setOnClickListener(this);
        arrayChuDe = new ArrayList<>();
        addChuDe();
        adapterChuDeFolderPhoto = new AdapterChuDeFolderPhoto(getActivity(), R.layout.row_chu_de, arrayChuDe);
    }

    private void ShowBottomSheet() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_view, null);//phải gọi từ layout khác
        lv_ChuDe = (ListView) view.findViewById(lv_languages);
        lv_ChuDe.setAdapter(adapterChuDeFolderPhoto);
        bottomSheetDialog = new BottomSheetDialog(getActivity());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mAdapter.setData(Matisse.obtainResult(data), Matisse.obtainResult(data));
        }
    }

    private void anhXa() {
        imgHinhAva = (ImageView) getActivity().findViewById(R.id.hinhAvatarFolder);
        imgBack = (ImageView) getActivity().findViewById(R.id.backShare);
        imgSend = (ImageView) getActivity().findViewById(R.id.sendShare);
        imgHinhChuDeFolder = (ImageView) getActivity().findViewById(R.id.hinhChuDeFolder);
        txTenChuDeFolder = (TextView) getActivity().findViewById(R.id.tenChuDeFolder);
        etStatus = (EditText) getActivity().findViewById(R.id.nhapSttFolder);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backShare:
                getActivity().finish();
                break;
            case R.id.chonChuDe:
                ShowBottomSheet();
                break;
            case R.id.chonHinhFolder:
                Matisse.from(getActivity())
                        .choose(MimeType.of(JPEG, PNG, GIF))
                        .theme(R.style.Matisse_Dracula)
                        .countable(false)
                        .maxSelectable(6)
                        .imageEngine(new PicassoEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
                break;
        }
    }
}
