package com.example.android.grabadora;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class grabadora extends AppCompatActivity {

    private MediaRecorder grabacion;
    private String archivoGrabado = null;
    private Button btn_grabar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabadora);

        btn_grabar = (Button)findViewById(R.id.rec);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(grabadora.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }
    }

    public void Grabador (View view){
        if (grabacion == null){
            archivoGrabado = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Grabacion.mp3";
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            grabacion.setAudioEncoder(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setOutputFile(archivoGrabado);

            try{

                grabacion.prepare();
                grabacion.start();
            } catch (IOException e){
            }

            btn_grabar.setBackgroundResource(R.drawable.rec);
            Toast.makeText(this, "Grabando...", Toast.LENGTH_LONG).show();
        }
        else if (grabacion != null){
            grabacion.stop();
            grabacion.release();
            grabacion = null;
            btn_grabar.setBackgroundResource(R.drawable.recbut);
            Toast.makeText(this, "¡Grabación finalizada!", Toast.LENGTH_LONG).show();
        }

    }

    public void reproducir(View view){
        MediaPlayer mediaPlayer = new MediaPlayer();


        try {
            mediaPlayer.setDataSource(archivoGrabado);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        Toast.makeText(this, "Reproduciendo...", Toast.LENGTH_LONG).show();
    }
}
