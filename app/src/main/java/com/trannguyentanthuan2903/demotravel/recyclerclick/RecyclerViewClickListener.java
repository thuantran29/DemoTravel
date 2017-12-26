package com.trannguyentanthuan2903.demotravel.recyclerclick;

import android.view.View;

/**
 * Created by Administrator on 5/29/2017.
 */

public interface RecyclerViewClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
