package com.trannguyentanthuan2903.demotravel.dto;

/**
 * Created by Administrator on 6/5/2017.
 */

public class CommentDTO {
    public String Id;
    public String Name;
    public String Commnent;
    public String ViTri;
    public String Time;
    public int Hinh;

    public CommentDTO() {
    }

    public CommentDTO(String id, String name, String commnent, String viTri, String time, int hinh) {
        Id = id;
        Name = name;
        Commnent = commnent;
        ViTri = viTri;
        Time = time;
        Hinh = hinh;
    }
}
