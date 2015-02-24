package sanchez.com.webviewimage;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;



public class MainActivity extends ActionBarActivity {
    private WebView picView;
    private Handler mHandler = new Handler();
    private final int IMG_PICK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        picView = (WebView)findViewById(R.id.webView);
        picView.setBackgroundColor(0);
        String str = "/storage/emulated/0/Pictures/Screenshots/Screenshot_2014-10-20-12-05-25.png";
       // picView.loadDataWithBaseURL("file:///android_res/drawable/", "<img src='tunnel.jpg' />","text/html", "utf-8", null);
        picView.loadData(str, "text/html", "UTF-8");

        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        sb.append("test");


        sb.append("</body></html>");

        picView.loadData(sb.toString(), "text/html", "utf-8");


        String html = "<html><head><title>TITLE!!!</title></head>";
        html += "<body><h1>Image?</h1><img src=file:///storage/emulated/0/Pictures/Screenshots/Screenshot_2014-09-19-10-42-40.png></body></html>";


        picView.loadDataWithBaseURL("file:///android_res/drawable/", html, "text/html", "UTF-8", null);


        // public void loadData(String data, String mimeType, String encoding)

    }

    public void pickPicture(View v) {

        if (v.getId() == R.id.pick_btn) {
            Intent pickIntent = new Intent();
            pickIntent.setType("image/*");
            pickIntent.setAction(Intent.ACTION_GET_CONTENT);
            //we will handle the returned data in onActivityResult
            startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"), IMG_PICK);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMG_PICK) {
                Uri pickedUri = data.getData();
                String imagePath = null;
                String[] imgData = { MediaStore.Images.Media.DATA };
                //Cursor imgCursor = managedQuery(pickedUri, imgData, null, null, null);
                Cursor imgCursor = getContentResolver().query(pickedUri,imgData,null,null,null);
                if(imgCursor.moveToFirst()) {
                    int index = imgCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //imgCursor.moveToFirst();
                    imagePath = imgCursor.getString(index);
                }
                else {
                    imagePath = pickedUri.getPath();
                }

                picView.loadUrl("file:///"+imagePath);

            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
