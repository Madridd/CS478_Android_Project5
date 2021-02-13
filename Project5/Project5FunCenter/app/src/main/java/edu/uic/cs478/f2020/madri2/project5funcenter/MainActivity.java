package edu.uic.cs478.f2020.madri2.project5funcenter;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import edu.uic.cs478.f2020.madri2.project5funcenter.MediaInterface;


public class MainActivity extends Service {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "FunCenter";
    //private Notification notification ;
    private MediaPlayer mPlayer;
    private int mStartID;

    //Bitmap[] images = new Bitmap[3];
    ArrayList<Bitmap> pics = new ArrayList<Bitmap>();


    @Override
    public void onCreate() {
        super.onCreate();
        //initialize bitmap with pics
        pics.add(BitmapFactory.decodeResource(getResources(), R.drawable.baby_yoda));
        pics.add(BitmapFactory.decodeResource(getResources(), R.drawable.et));
        pics.add(BitmapFactory.decodeResource(getResources(), R.drawable.gremlin));
        //The channel defines basic properties of
        createNotificationChannel();


        final Intent notificationIntent = new Intent();
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        final Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                                .setSmallIcon(android.R.drawable.ic_media_play)
                                                .setOngoing(true).setContentTitle("Music Playing")
                                                .setContentText("Click to Access Music Player")
                                                .setTicker("Music is playing!")
                                                .setContentIntent(pendingIntent)
                                                .build();

        startForeground(NOTIFICATION_ID, notification);

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Music player notification";
            String description = "The channel for music player notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        // Don't automatically restart this Service if it is killed
        return START_NOT_STICKY;
    }

    private void play(int songNum){
        if (songNum == 1){
            mPlayer = MediaPlayer.create(this, R.raw.thelessiknowthebetter);
        }
        else if (songNum == 2) {
            mPlayer = MediaPlayer.create(this, R.raw.sickomode);
        }
        else if (songNum == 3) {
            mPlayer = MediaPlayer.create(this, R.raw.afterparty);
        }


        if (mPlayer.isPlaying()) {
            // Rewind to beginning of song
            mPlayer.seekTo(0);
        } else {
            // Start playing song
            mPlayer.start();
        }

        if (null != mPlayer) {
            mPlayer.setLooping(false);
            // Stop Service when music has finished playing
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    // stop Service if it was started with this ID
                    // Otherwise let other start commands proceed
                    //mPlayer.seekTo(0);
                    mPlayer.pause();

                }

            });
        }

    }//end of play()


    private void pause(){
        mPlayer.pause();
    }//end Pause()

    private void resume(){
        if(mPlayer.isPlaying()){
            Log.i("isPlaying", "Resume(): something is playing already");
        }
        else {
            mPlayer.start();
        }
    }//end resume()

    private void stop(){
        mPlayer.pause();
    }//end stop()

    private Bitmap getPic(int picNum){
        return pics.get(picNum);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    private final MediaInterface.Stub mBinder = new MediaInterface.Stub(){
        public void playSong(int songNum){
            play(songNum);
        }

        public void pauseSong(){
            pause();
        }

        public void resumeSong(){
            resume();
        }

        public void stopSong(){
            stop();
        }
        public void getPicture(int picNum){
            getPic(picNum);
        }


    };


}