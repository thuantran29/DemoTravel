package com.trannguyentanthuan2903.demotravel.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.trannguyentanthuan2903.demotravel.adapter.AdapterTakePhoto;
import com.trannguyentanthuan2903.demotravel.dto.ChuDeDTO;
import com.trannguyentanthuan2903.demotravel.dto.DanhSachChiaSeDTO;
import com.trannguyentanthuan2903.demotravel.func.CircleTransform;

import java.io.ByteArrayOutputStream;
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

/**
 * Created by Administrator on 6/16/2017.
 */

public class FragmentCamera extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_list_photo, container, false);
    }

    ImageView imgBack, imgChon, imgAva, imgShare;
    EditText etStatus;
    TextView txtChuDe;
    final int REQUEST_CODE_CAMERA = 123;
    private static final int MY_IMPRESSION_REQUEST_LOCATION = 1;
    AdapterTakePhoto adapterChuDe;
    ArrayList<ChuDeDTO> arrayChuDe;
    public static TextView txTenChuDeBottom;
    public static ImageView imgHinhChuDeBottom;
    Toolbar toolbar;
    LinearLayout txChuDe;
    ListView lv_ChuDe;
    String lat, lon, vitri;
    ProgressDialog progress;

    public static BottomSheetDialog bottomSheetDialog;
    private DatabaseReference mDatabase;
    private FirebaseStorage storage;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        anhXa();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://imagelocation-b6d27.appspot.com");

        xetDialog();
        imgBack.setOnClickListener(this);
        imgChon.setOnClickListener(this);
        Picasso.with(getActivity()).load(R.drawable.person).transform(new CircleTransform()).into(imgAva);
        init();
        sendDiaChi();
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                final String now = df.format(new Date());
                final Calendar calendar = Calendar.getInstance();
                StorageReference mountainsRef = storageRef.child("DanhSach/image" + calendar.getTimeInMillis() + ".png");
                BitmapDrawable drawable = (BitmapDrawable) imgChon.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d("AAA", "Lỗi lưu hình tài khoản: " + exception.toString());
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        DanhSachChiaSeDTO sha = new DanhSachChiaSeDTO(
                                null,
                                "Anoymous",
                                txtChuDe.getText().toString().trim(),
                                now,
                                etStatus.getText().toString().trim(),
                                R.drawable.person,
                                String.valueOf(downloadUrl),
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
        });
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

    private void init() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        txChuDe = (LinearLayout) getActivity().findViewById(R.id.chonChuDe);
        txChuDe.setOnClickListener(this);
        arrayChuDe = new ArrayList<>();
        addChuDe();
        adapterChuDe = new AdapterTakePhoto(getActivity(), R.layout.row_chu_de, arrayChuDe);
    }

    private void ShowBottomSheet() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_view, null);//phải gọi từ layout khác
        lv_ChuDe = (ListView) view.findViewById(lv_languages);
        lv_ChuDe.setAdapter(adapterChuDe);
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

    private void xetDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setTitle("Thông báo");
        builder1.setIcon(R.drawable.icon_a);
        builder1.setMessage("Chọn hình ảnh từ Camera");
        builder1.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
            }
        });
        builder1.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
            }
        });

        builder1.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                } else {
                    Toast.makeText(getActivity(), "Bạn không có quyền truy cập máy ảnh", Toast.LENGTH_SHORT).show();
                }
                return;
            }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");//"data" ->default
            imgChon.setImageBitmap(photo);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void anhXa() {
        etStatus = (EditText) getActivity().findViewById(R.id.nhapSttFolder);
        imgChon = (ImageView) getActivity().findViewById(R.id.hinhPhoto);
        imgBack = (ImageView) getActivity().findViewById(R.id.backShare);
        imgAva = (ImageView) getActivity().findViewById(R.id.hinhAvaPhoto);
        imgShare = (ImageView) getActivity().findViewById(R.id.sendShare);
        txtChuDe = (TextView) getActivity().findViewById(R.id.tenChuDeBottom);
        txTenChuDeBottom = (TextView) getActivity().findViewById(R.id.tenChuDeBottom);
        imgHinhChuDeBottom = (ImageView) getActivity().findViewById(R.id.hinhChuDeBottom);

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
            case R.id.hinhPhoto:
                xetDialog();
                break;
        }
    }
}