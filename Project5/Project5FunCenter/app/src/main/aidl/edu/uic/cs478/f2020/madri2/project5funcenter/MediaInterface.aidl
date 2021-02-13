// MediaInterface.aidl
package edu.uic.cs478.f2020.madri2.project5funcenter;

// Declare any non-default types here with import statements
import android.graphics.Bitmap;

interface MediaInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    //void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
    //        double aDouble, String aString);

     void playSong(int songNum);
     void pauseSong();
     void resumeSong();
     void stopSong();
     void getPicture(int picNum);



}
