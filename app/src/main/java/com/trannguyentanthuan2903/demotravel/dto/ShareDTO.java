package com.trannguyentanthuan2903.demotravel.dto;

/**
 * Created by Administrator on 6/5/2017.
 */

public class ShareDTO {
    public String Name;
    public String Title;
    public String Time;
    public String Content;
    public int ImageAvatar;
    public int ImageReview;

    public ShareDTO() {
    }

    public ShareDTO(String name, String title, String time, String content, int imageAvatar, int imageReview) {
        Name = name;
        Title = title;
        Time = time;
        Content = content;
        ImageAvatar = imageAvatar;
        ImageReview = imageReview;
    }
}
