package com.aligungor.filedownload;

import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by AliGungor on 22/08/15.
 *
 * Don't forget to add
 * <uses-permission android:name="android.permission.WAKE_LOCK"/>
 * <uses-permission android:name="android.permission.INTERNET"/>
 * to AndroidManifest.xml
 *
 */

public class FileDownloader {

    private Context context;
    private File file;
    private String downloadUrl;
    private PowerManager.WakeLock wakeLock;
    private OnDownloadListener onDownloadListener;

    public FileDownloader(Context context, File file, String downloadUrl) {
        this.context = context;
        this.file = file;
        this.downloadUrl = downloadUrl;
    }

    public void startDownload() {
        new DownloadTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void setOnDownloadListener(OnDownloadListener onDownloadListener) {
        this.onDownloadListener = onDownloadListener;
    }

    private class DownloadTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            wakeLock.acquire();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(downloadUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    System.out.println("Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage());
                    return null;
                }

                int fileLength = connection.getContentLength();

                input = connection.getInputStream();
                output = new FileOutputStream(file.getAbsolutePath());

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    if (fileLength > 0)
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            if (onDownloadListener != null) {
                onDownloadListener.onDownloadProgressChanged(progress[0]);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            wakeLock.release();
            if (onDownloadListener != null) {
                onDownloadListener.onDownloadCompleted();
            }
            super.onPostExecute(aVoid);
        }
    }

    public interface OnDownloadListener {

        public void onDownloadProgressChanged(int progress);

        public void onDownloadCompleted();

    }
}
