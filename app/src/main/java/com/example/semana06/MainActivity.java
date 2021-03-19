package com.example.semana06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button act;
    private Button up_;
    private Button right_;
    private Button left_;
    private Button down_;

    private BufferedWriter bWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        act = findViewById(R.id.act);
        up_ = findViewById(R.id.up_);
        right_ = findViewById(R.id.right_);
        left_ = findViewById(R.id.left_);
        down_ = findViewById(R.id.down_);

        act.setOnClickListener(
                (v)->{
                    //enviar color
                    new Thread(
                            ()->{
                                enviarJson("color");
                            }
                    ).start();

                }
        );

        up_.setOnClickListener(
                (v)->{
                    //enviar arriba
                    new Thread(
                            ()->{
                                enviarJson("up");
                            }
                    ).start();

                }
        );

        right_.setOnClickListener(
                (v)->{
                    //enviar derecha
                    new Thread(
                            ()->{
                                enviarJson("right");
                            }
                    ).start();

                }
        );

        left_.setOnClickListener(
                (v)->{
                    //enviar izq
                    new Thread(
                            ()->{
                                enviarJson("left");
                            }
                    ).start();

                }
        );

        down_.setOnClickListener(
                (v)->{
                    //enviar abajo
                    new Thread(
                            ()->{
                                enviarJson("down");
                            }
                    ).start();

                }
        );

        new Thread(
                ()->{
                    try {
                        Socket socket = new Socket("10.0.2.2",5000);

                        OutputStream os = socket.getOutputStream(); //para saber el flujo de datos
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        bWriter = new BufferedWriter(osw);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
         ).start();
    }

     public void enviarJson(String a ){

        Gson gson = new Gson();
        Informacion info = new Informacion(a);

        //Serializacion
        String coordStr = gson.toJson(info);
         new Thread(
                 ()-> {

                     try {
                         bWriter.write(coordStr + "\n");
                         bWriter.flush();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }
    ).start();


    }
}