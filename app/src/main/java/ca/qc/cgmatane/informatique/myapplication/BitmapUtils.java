package ca.qc.cgmatane.informatique.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BitmapUtils extends AsyncTask<String, Void, String> {

    private String url;
    public String stringBitmap;
    private RecyclerViewAdapter activity;
    private TaskCompleted taskCompleted=null;

    private Context mContext;

    public BitmapUtils(TaskCompleted task){
        this.taskCompleted = task;
    }

     @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... src) {

        try {
            URL url = new URL(src[0]);
            Bitmap imageBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            String stringBitmapImage = BitMapToString(imageBitmap);
            this.stringBitmap=stringBitmapImage;
            return stringBitmapImage;
        } catch(IOException e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        taskCompleted.onTaskComplete(s);
    }


    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


}
