package com.androidproject.dailynotesandroid;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class StoreAudioActivity extends AppCompatActivity {

    Button btnRecod, btnStop, btnPlay;
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder ;
    Random random ;
    String RandomAudioFileName = "audio_file";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_audio);

        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnRecod = (Button) findViewById(R.id.btnRecord);
        btnStop = (Button) findViewById(R.id.btnStop);

        btnStop.setEnabled(false);
        btnPlay.setEnabled(false);

        random = new Random();

        btnRecod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {

                    if(checkPermission()) {

                        AudioSavePathInDevice =
                                Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                        CreateRandomAudioFileName(5) + "AudioRecording.3gp";
//                    Toast.makeText(this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
//                            CreateRandomAudioFileName(5) + "AudioRecording.3gp", To)

                        MediaRecorderReady();

                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        btnRecod.setEnabled(true);
                        btnStop.setEnabled(true);


                        Toast.makeText(StoreAudioActivity.this, "Recording started",
                                Toast.LENGTH_LONG).show();
                    } else {
                        requestPermission();
                    }

                }
            }
        });


        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder.stop();
                btnStop.setEnabled(false);
                btnPlay.setEnabled(true);
                btnRecod.setEnabled(true);

                Toast.makeText(StoreAudioActivity.this, "Recording Completed",
                        Toast.LENGTH_LONG).show();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    btnStop.setEnabled(true);
                    btnRecod.setEnabled(false);
                    btnPlay.setEnabled(false);

                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(AudioSavePathInDevice);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mediaPlayer.start();
                    Toast.makeText(StoreAudioActivity.this, "Recording Playing",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


    }

//    public void btnRecordClick(View view) {
//
//        if(checkPermission()) {
//
//            AudioSavePathInDevice =
//                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
//                            CreateRandomAudioFileName(5) + "AudioRecording.3gp";
////                    Toast.makeText(this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
////                            CreateRandomAudioFileName(5) + "AudioRecording.3gp", To)
//
//
//
//            MediaRecorderReady();
//
//            try {
//                mediaRecorder.prepare();
//                mediaRecorder.start();
//            } catch (IllegalStateException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            btnRecod.setEnabled(true);
//            btnStop.setEnabled(false);
//
//            Toast.makeText(StoreAudioActivity.this, "Recording started",
//                    Toast.LENGTH_LONG).show();
//        } else {
//            requestPermission();
//        }
//
//    }

//    public void btnStopClick(View view) {
//        mediaRecorder.stop();
//        btnStop.setEnabled(false);
//        btnPlay.setEnabled(true);
//        btnRecod.setEnabled(true);
//
//        Toast.makeText(StoreAudioActivity.this, "Recording Completed",
//                Toast.LENGTH_LONG).show();
//    }

//    public void btnPlayClick(View view) {
//        btnStop.setEnabled(false);
//        btnRecod.setEnabled(false);
//        btnPlay.setEnabled(true);
//
//        mediaPlayer = new MediaPlayer();
//        try {
//            mediaPlayer.setDataSource(AudioSavePathInDevice);
//            mediaPlayer.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        mediaPlayer.start();
//        Toast.makeText(StoreAudioActivity.this, "Recording Playing",
//                Toast.LENGTH_LONG).show();
//    }


    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));
            i++ ;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(StoreAudioActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(StoreAudioActivity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(StoreAudioActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
}
