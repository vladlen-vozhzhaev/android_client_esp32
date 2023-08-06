package com.example.esp32;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    ImageView rgbImg;
    ImageView lightImg;
    ImageView fanImg;
    ImageView pumpImg;
    boolean rgb = false;
    boolean light = false;
    boolean fan = false;
    boolean pump = false;
    boolean on = false;
    DataOutputStream out;
    DataInputStream in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rgbImg = findViewById(R.id.rgbImg);
        lightImg = findViewById(R.id.lightImg);
        fanImg = findViewById(R.id.fanImg);
        pumpImg = findViewById(R.id.pumpImg);
        rgbImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rgb){
                    rgbImg.setImageResource(R.drawable.rgboff);
                    rgb = false;
                }else{
                    rgbImg.setImageResource(R.drawable.rgbon);
                    rgb = true;
                }
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(rgb) {out.write("client/rgbon".getBytes());}
                            else {out.write("client/rgboff".getBytes());}
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });
        lightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!light){
                    lightImg.setImageResource(R.drawable.lightoff);
                    light = false;
                }else{
                    lightImg.setImageResource(R.drawable.lighton);
                    light = true;
                }
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(light) {out.writeUTF("client/ledstripon");}
                            else {out.writeUTF("client/ledstripoff");}
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });
        fanImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!fan){
                    fanImg.setImageResource(R.drawable.fanoff);
                    fan = false;
                }else{
                    rgbImg.setImageResource(R.drawable.fanon);
                    fan = true;
                }
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(fan) {out.writeUTF("client/ventson");}
                            else {out.writeUTF("client/ventsoff");}
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });
        pumpImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pump){
                    pumpImg.setImageResource(R.drawable.pumpoff);
                    pump = false;
                }else{
                    rgbImg.setImageResource(R.drawable.pumpon);
                    pump = true;
                }
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(pump) {out.writeUTF("client/pumpon");}
                            else {out.writeUTF("client/pumpoff");}
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("192.168.1.12", 9123);
                    out = new DataOutputStream(socket.getOutputStream());
                    in = new DataInputStream(socket.getInputStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        thread.start();
    }
}