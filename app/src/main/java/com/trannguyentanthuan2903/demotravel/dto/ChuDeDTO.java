package com.trannguyentanthuan2903.demotravel.dto;

/**
 * Created by Administrator on 6/6/2017.
 */

public class ChuDeDTO {
    public String TenChuDe;
    public int Hinh;
    public boolean isSelected;

    public ChuDeDTO(String tenChuDe, int hinh, boolean isSelected) {
        TenChuDe = tenChuDe;
        Hinh = hinh;
        this.isSelected = isSelected;
    }
}
