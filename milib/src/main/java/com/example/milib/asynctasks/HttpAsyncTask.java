package com.example.milib.asynctasks;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by guille on 20/1/18.
 */

public class  HttpAsyncTask extends AsyncTask<String, Integer, String[]> {

   public HttpAsyncTask(){
    Log.v("httpasyn","loco");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String[] doInBackground(String... urls) {

     String pathin[]=new String[urls.length];
     int count;
     this.publishProgress(0);

     for(int i = 0; i<urls.length;i++){

      try {
       String root = Environment.getExternalStorageDirectory().toString();

       Log.v("httpasyn","loco");

       URL url = new URL(urls[0]);

       URLConnection conection = url.openConnection();
       conection.connect();
       // getting file length
       int lenghtOfFile = conection.getContentLength();

       // input stream to read file - with 8k buffer
       InputStream input = new BufferedInputStream(url.openStream(), 8192);

       // Output stream to write file

       pathin[i]="/downloadedfile"+i+ ".jpg";
       OutputStream output = new FileOutputStream(pathin[i]);
       byte data[] = new byte[1024];


       long total = 0;


       while ((count = input.read(data)) != -1) {
        total += count;

        // writing data to file
        output.write(data, 0, count);

       }
       this.publishProgress(100);
       // flushing output
       output.flush();

       // closing streams
       output.close();
       input.close();

       this.publishProgress(100);

      }  catch (Exception e) {
       Log.e("Error: ", e.getMessage());
      }

      this.publishProgress(20*(i+1));
     }


     return pathin ;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String[] in) {
    super.onPostExecute(in);
     Log.v("jsonloco","se acabo la tarea "+in[0]+"  "+in[1]);
    }


}
