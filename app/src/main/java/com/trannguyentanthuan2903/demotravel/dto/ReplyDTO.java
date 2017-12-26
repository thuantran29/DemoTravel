package com.trannguyentanthuan2903.demotravel.dto;

/**
 * Created by Administrator on 6/20/2017.
 */

public class ReplyDTO {
    public String Id;
    public String Name;
    public String Content;
    public int Ava;

    public ReplyDTO() {
    }

    public ReplyDTO(String id, String name, String content, int ava) {
        Id = id;
        Name = name;
        Content = content;
        Ava = ava;
    }
}
