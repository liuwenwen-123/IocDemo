package com.example.iocdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mylibrary.InjectLayout;
import com.example.mylibrary.InjectManager;
import com.example.mylibrary.InjectOnLongClick;
import com.example.mylibrary.InjectOnlick;
import com.example.mylibrary.InjectView;


@InjectLayout(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.tv_test)
    Button btn;
    @InjectView(R.id.tv_test1)
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.initJect(this);

       /* textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"666",Toast.LENGTH_LONG).show();
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });*/
    }
    @InjectOnlick(ids = {R.id.tv_test,R.id.tv_test1})
    public  void onClick(View view){
        switch (view.getId()){
            case R.id.tv_test:
                Toast.makeText(MainActivity.this,"666",Toast.LENGTH_LONG).show();
                break;
            case R.id.tv_test1:
                Toast.makeText(MainActivity.this,"666ppp",Toast.LENGTH_LONG).show();
                break;
        }

    }
    @InjectOnLongClick(ids = {R.id.tv_test,R.id.tv_test1})
    public  boolean onLongClick(View view){
        switch (view.getId()){
            case R.id.tv_test:
                Toast.makeText(MainActivity.this,"666ccc",Toast.LENGTH_LONG).show();
                break;
            case R.id.tv_test1:
                Toast.makeText(MainActivity.this,"666llll",Toast.LENGTH_LONG).show();
                break;
        }
        return  false;
    }


}