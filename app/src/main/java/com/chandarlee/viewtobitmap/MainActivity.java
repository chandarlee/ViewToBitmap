package com.chandarlee.viewtobitmap;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;


public class MainActivity extends ActionBarActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void onBtnClick(View view) {
        final ScrollView root  = (ScrollView) findViewById(R.id.phone_test_root);
        final int rootWidth = root.getWidth();
        //To get the height of the ScrollView, the v.getHeight doesn't work, you need to use:
        //v.getChildAt(0).getHeight()
        //Just using v.getHeight just gets the screen height, not the full height of the ScrollView.
        final int rootHeight = root.getChildAt(0).getHeight();

        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.phone_test_relativelayout);
        final int relativeLayoutWidth = relativeLayout.getWidth();
        final int relativeLayoutHeight = relativeLayout.getHeight();

        final int id = view.getId();
        Runnable runnable = null;
        switch (id){
            case R.id.btn0:{
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        ScreenShotHelper.inCanvas(root, relativeLayoutWidth, relativeLayoutHeight);
                    }
                };
                break;
            }
            case R.id.btn1:{
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        ScreenShotHelper.inCanvas(relativeLayout, relativeLayoutWidth, relativeLayoutHeight);
                    }
                };
                break;
            }

            case R.id.btn2:{
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        ScreenShotHelper.inCache(root,rootWidth,rootHeight);
                    }
                };
                break;
            }
            case R.id.btn3:{
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        ScreenShotHelper.inCache(relativeLayout, relativeLayoutWidth, relativeLayoutHeight);
                    }
                };
                break;
            }
        }

        if(runnable != null)
            handler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainActivity","onPause");
    }

    public void onTVClick(View view) {
        final int id = view.getId();
        switch (id){
            case R.id.tv0:
            {
                MainActivity2.start(this);
                MainActivity3.start(this);
                break;
            }

            case R.id.tv1:{
                Intent intent2 = new Intent(this,MainActivity2.class);
                Intent intent3 = new Intent(this,MainActivity3.class);
                startActivities(new Intent[]{intent2,intent3});
                break;
            }
        }

    }
}
