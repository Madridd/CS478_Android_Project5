package edu.uic.cs478.f2020.madri2.project5funclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.KeyGenerator;

import edu.uic.cs478.f2020.madri2.project5funclient.MediaInterface;

public class MainActivity extends AppCompatActivity {

    Button songButton;
    Button imageButton;
    Button pauseButton;
    Button playButton;
    Button stopButton;

    EditText songPick;
    EditText imagePick;

    ListView imageListView;
    MediaInterface media;

    boolean isBound = false;
    ArrayAdapter<String> arrayAdapter;

    Intent intent = startIntent();

    private Intent startIntent(){
        Intent intent = new Intent("FunCenter");
        intent.setPackage("edu.uic.cs478.f2020.madri2.project5funcenter");
        Intent i = new Intent(KeyGenerator.class.getName());

        // UB:  Stoooopid Android API-21 no longer supports implicit intents
        // to bind to a service #@%^!@..&**!@
        // Must make intent explicit or lower target API level to 20.
        //ResolveInfo info = getPackageManager().resolveService(intent, 0);
        //intent.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));
        return intent;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songButton = findViewById(R.id.button1);
        imageButton = findViewById(R.id.button2);
        pauseButton = findViewById(R.id.button4);
        playButton = findViewById(R.id.button3);
        stopButton = findViewById(R.id.button5);

        songPick = findViewById(R.id.editText1);
        imagePick = findViewById(R.id.editText2);

        imageListView = findViewById(R.id.listView);



        //Intent intent = new Intent(this, MediaInterface.class);
        //Intent intent = new Intent("FunCenter");
        //intent.setPackage("edu.uic.cs478.f2020.madri2.project5funcenter");


        //bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);


//        songButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int songNum = Integer.parseInt(songPick.getText().toString());
//
//            try{
//                media.playSong(songNum);
//            }
//            catch(RemoteException e){
//                //Log.e(TAG, e.toString());
//            }
//
//            }
//        });



        songButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int songNum = Integer.parseInt(songPick.getText().toString());
                try {
                    onClickPlay(songNum);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    media.stopSong();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    media.pauseSong();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    media.resumeSong();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });





    }//end of onCreate()

//    protected void onStart() {
//        super.onStart();
//            checkBindingAndBind();
//
//    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            media = MediaInterface.Stub.asInterface(service);
            isBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            media = null;
            isBound = false;
        }
    };//end serviceConnection

//    public void playSong(View view) {
//        int songNum = Integer.parseInt(songPick.getText().toString());
//
//            try{
//                media.playSong(songNum);
//            }
//            catch(RemoteException e){
//                //Log.e(TAG, e.toString());
//            }
//    }//end playSong()
//    public void playSong (View view) throws RemoteException {
//        int songNum = Integer.parseInt(songPick.getText().toString());
//        media.playSong(songNum);
//    }

    protected void onPause() {
        super.onPause();
    }//end onPause()

    @Override
    protected void onResume() {
        super.onResume();
    }//end onResume()

    protected void onDestroy(){
        super.onDestroy();
        unbindService(this.serviceConnection);
    }//end of onDestroy()

    public void onClickPlay(int songID) throws RemoteException{
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        media.playSong(songID);
    }

//    protected void checkBindingAndBind() {
//        if (!isBound) {
//
//            boolean b = false;
//            Intent i = new Intent(MediaInterface.class.getName());
//
//            // UB:  Stoooopid Android API-21 no longer supports implicit intents
//            // to bind to a service #@%^!@..&**!@
//            // Must make intent explicit or lower target API level to 20.
//            ResolveInfo info = getPackageManager().resolveService(i, 0);
//            i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));
//
//            b = bindService(i, this.serviceConnection, Context.BIND_AUTO_CREATE);
//            if (b) {
//                Log.i("checkingBind", "Ugo says bindService() succeeded!");
//            } else {
//                Log.i("checkingBind", "Ugo says bindService() failed!");
//            }
//        }
//    }



}