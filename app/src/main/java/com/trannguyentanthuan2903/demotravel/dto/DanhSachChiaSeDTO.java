package com.trannguyentanthuan2903.demotravel.dto;

/**
 * Created by Administrator on 6/12/2017.
 */

public class DanhSachChiaSeDTO  {
    public String Id;
    public String Name;
    public String Title;
    public String Time;
    public String Content;
    public int ImageAvatar;
    public String ImageReview;
    public String ViTri;
    public String lattitude;
    public String longtitude;

    public DanhSachChiaSeDTO() {
    }

    public DanhSachChiaSeDTO(String id, String name, String title, String time,
                             String content, int imageAvatar, String imageReview, String viTri, String lattitude, String longtitude) {
        Id = id;
        Name = name;
        Title = title;
        Time = time;
        Content = content;
        ImageAvatar = imageAvatar;
        ImageReview = imageReview;
        ViTri = viTri;
        this.lattitude = lattitude;
        this.longtitude = longtitude;
    }
}
