package com.trannguyentanthuan2903.demotravel.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trannguyentanthuan2903.demotravel.R;
import com.trannguyentanthuan2903.demotravel.adapter.AdapterReply;
import com.trannguyentanthuan2903.demotravel.dto.ReplyDTO;

import java.util.ArrayList;

public class ReplyActivity extends AppCompatActivity implements View.OnClickListener {
    ListView lstReply;
    EditText etReply;
    ImageView imgBack;
    String key;
    AdapterReply adapterRep;
    ArrayList<ReplyDTO> arrayRep;
    private DatabaseReference mDatabase;
    String key_cmt,key_reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        anhXa();
        imgBack.setOnClickListener(this);

        Intent myIntent = ReplyActivity.this.getIntent();
        key_reply = myIntent.getStringExtra("key_comment");

//        StringTokenizer tokens = new StringTokenizer(key, ",");
//        key_cmt= tokens.nextToken();
//        key_reply= tokens.nextToken();

        SharedPreferences prefs = getSharedPreferences("comment", MODE_PRIVATE);
        key_cmt = prefs.getString("key_cmt", null);

        adapterRep = new AdapterReply(ReplyActivity.this, R.layout.row_reply, arrayRep);
        lstReply.setAdapter(adapterRep);
        adapterRep.notifyDataSetChanged();


        etReply.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP)
                    if (event.getRawX() >= (etReply.getRight() - etReply.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        ReplyDTO cmt = new ReplyDTO(null, "Anyone", etReply.getText().toString(), R.drawable.person);
                        mDatabase.child("Comment").child(key_cmt).child(key_reply).push().setValue(cmt);
                        etReply.setText("");
                        return true;
                    }
                return false;
            }
        });

        mDatabase.child("Comment").child(key_cmt).child(key_reply).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ReplyDTO re= dataSnapshot.getValue(ReplyDTO.class);
                arrayRep.add(new ReplyDTO(dataSnapshot.getKey(),
                        re.Name,
                        re.Content,
                        re.Ava));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
     }
       });
    }

    private void anhXa() {
        arrayRep = new ArrayList<>();
        lstReply = (ListView) findViewById(R.id.lv_reply);
        etReply = (EditText) findViewById(R.id.editReply);
        imgBack = (ImageView) findViewById(R.id.previous);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.previous:
                finish();
                break;
        }
    }
}
