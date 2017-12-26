package com.trannguyentanthuan2903.demotravel.dto;

import android.net.Uri;

/**
 * Created by Administrator on 6/6/2017.
 */

public class DSHinhDTO {
    public String tenHinh;
    public Uri uri;
    public boolean isSelected;

    public String getTenHinh() {
        return tenHinh;
    }

    public void setTenHinh(String tenHinh) {
        this.tenHinh = tenHinh;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
