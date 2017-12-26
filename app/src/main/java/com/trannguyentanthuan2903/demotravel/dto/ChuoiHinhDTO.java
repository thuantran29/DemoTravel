package com.trannguyentanthuan2903.demotravel.dto;

import java.util.List;

/**
 * Created by Administrator on 6/16/2017.
 */

public class ChuoiHinhDTO {
    public String Id;
    public String Name;
    public String Title;
    public String Time;
    public String Content;
    public int ImageAvatar;
    public List<String> ImageReview;
    public String ViTri;
    public String lattitude;
    public String longtitude;

    public ChuoiHinhDTO() {
    }

    public ChuoiHinhDTO(String id, String name, String title, String time, String content,
                        int imageAvatar, List<String> imageReview, String viTri, String lattitude, String longtitude) {
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
