package com.example.httpjson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
 
@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {
 
    DownloadManager mDManager;
    DownloadCompleteReceiver mReceiver;
    Handler mHandler;
 
    ImageView mImgShow;
    TextView mTvDetails;
    EditText mEtUrl;
    Button mBtnDownload;
 
    String mLocalImageUri;
    String mTitle;
    String mUri;
    String mMediaType;
    String mTotalSize;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        mBtnDownload = (Button) findViewById(R.id.btn_download);
        mImgShow = (ImageView) findViewById(R.id.img_show);
        mTvDetails = (TextView) findViewById(R.id.tv_details);
        mEtUrl = (EditText) findViewById(R.id.et_url);
 
        mDManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
 
        /** savedInstanceState will not be null, when the activity
        * is re created by configuration changes
        */
        if(savedInstanceState!=null){
            mLocalImageUri =  savedInstanceState.getString("local_image_uri");
            mTitle = savedInstanceState.getString("title");
            mUri = savedInstanceState.getString("uri");
            mMediaType = savedInstanceState.getString("media_type");
            mTotalSize = savedInstanceState.getString("total_size");
 
            Uri local_uri = Uri.parse(mLocalImageUri);
            mImgShow.setImageURI(local_uri);
 
            mTvDetails.setText(
                                "Title : " + mTitle + "\n" +
                                "Uri : " + mUri + "\n" +
                                "Local Image Uri : " + mLocalImageUri + "\n" +
                                "Media Type :" + mMediaType + "\n" +
                                "File Size : " + mTotalSize + " Bytes "
                              );
        }
 
        /** Creating an instance of Handler class,
        * which draws the image and download image details
        * in the MainActivity
        */
        mHandler = new Handler(){
            @Override
            /** This callback method is invoked when sendMessage() is
            * invoked on this handler
            */
            public void handleMessage(Message msg) {
 
                mLocalImageUri = msg.getData().getString("local_image_uri");
                mTitle = msg.getData().getString("title");
                mUri = msg.getData().getString("uri");
                mMediaType = msg.getData().getString("media_type");
                mTotalSize = msg.getData().getString("total_size");
 
                Uri local_uri = Uri.parse(mLocalImageUri);
                mImgShow.setImageURI(local_uri);
 
                mTvDetails.setText(
                                    "Title : " + mTitle + "\n" +
                                    "Uri : " + mUri + "\n" +
                                    "Local Image Uri : " + mLocalImageUri + "\n" +
                                    "Media Type :" + mMediaType + "\n" +
                                    "File Size : " + mTotalSize + " Bytes "
 
                );
                super.handleMessage(msg);
            }
        };
 
        /** Defining a button click listener for the Download button */
        OnClickListener downloadsListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(mEtUrl.getText().toString());
                DownloadManager.Request req = new DownloadManager.Request(uri);
                mDManager.enqueue(req);
            }
        };
 
        mReceiver = new DownloadCompleteReceiver();
        IntentFilter filter = new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE");
        registerReceiver(mReceiver, filter);
 
        /** Setting click event listener for the button */
        mBtnDownload.setOnClickListener(downloadsListener);
 
    }
 
    /** This callback method is called when the configuration
    * change occurs
    */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
 
        outState.putString("local_image_uri", mLocalImageUri);
        outState.putString("title", mTitle);
        outState.putString("uri", mUri);
        outState.putString("media_type", mMediaType);
        outState.putString("total_size", mTotalSize);
 
        super.onSaveInstanceState(outState);
    }
 
    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
 
    /** Defining a broadcast receiver */
    private class DownloadCompleteReceiver extends BroadcastReceiver{
 
        /** Will be executed when the download is completed */
        @Override
        public void onReceive(Context context, Intent intent) {
 
            if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
                Bundle data = intent.getExtras();
                long download_id = data.getLong(DownloadManager.EXTRA_DOWNLOAD_ID );
 
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(download_id);
 
                Cursor c = mDManager.query(query);
 
                if(c.moveToFirst()){
 
                    Bundle d = new Bundle();
                    d.putString("local_image_uri", c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI )));
                    d.putString("title", c.getString(c.getColumnIndex(DownloadManager.COLUMN_TITLE )));
                    d.putString("uri", c.getString(c.getColumnIndex(DownloadManager.COLUMN_URI )));
                    d.putString("media_type", c.getString(c.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE )));
                    d.putString("total_size", c.getString(c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES )));
 
                    Message msg = new Message();
                    msg.setData(d);
 
                    mHandler.sendMessage(msg);
                }
            }
        }
    }
}
