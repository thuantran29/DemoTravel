package com.trannguyentanthuan2903.demotravel.dto;

/**
 * Created by Administrator on 6/20/2017.
 */

public class CmtDTO {

    public String Id;
    public String Ten;
    public int Ava;
    public String Comment;

    public CmtDTO() {
    }

    public CmtDTO(String id, String ten, int ava, String comment) {
        Id = id;
        Ten = ten;
        Ava = ava;
        Comment = comment;
    }
}
