package com.chandarlee.viewtobitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2014/11/18.
 */
public class ScreenShotHelper {

    public static Bitmap getDrawingCache(View view,int width,int height){
        /*// 设置是否可以进行绘图缓存
        view.setDrawingCacheEnabled(true);
        // 如果绘图缓存无法，强制构建绘图缓存
        view.buildDrawingCache(true);
        // 返回这个缓存视图
        Bitmap bitmap = view.getDrawingCache(true);

        Log.d("ScreenShotHelper", "bitmap height:" + bitmap.getHeight() + ",bitmap width:" + bitmap.getWidth());
        Log.d("ScreenShotHelper", "view height:" + view.getHeight() + ",view width:" + view.getWidth());

        // 根据坐标点和需要的宽和高创建bitmap
        bitmap=Bitmap.createBitmap(bitmap, 0, 0, view.getWidth(), view.getHeight());*/
        /*view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        Log.d("ScreenShotHelper", "bitmap height:" + bitmap.getHeight() + ",bitmap width:" + bitmap.getWidth());
        Log.d("ScreenShotHelper", "view height:" + view.getHeight() + ",view width:" + view.getWidth());*/

        boolean willNotCache = view.willNotCacheDrawing();
        view.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = view.getDrawingCacheBackgroundColor();
        view.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            view.destroyDrawingCache();
        }
        view.buildDrawingCache();
        Bitmap cacheBitmap = view.getDrawingCache();
        if (cacheBitmap == null) {
            Log.e("ScreenShotHelper", "failed getViewBitmap(" + view + ")", new RuntimeException());
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        view.destroyDrawingCache();
        view.setWillNotCacheDrawing(willNotCache);
        view.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

    /**
     * successfully
     * @param view
     * @param width
     * @param height
     * @return
     */
    public static Bitmap drawViewToCanvas(View view, int width, int height)
    {
        Bitmap b = Bitmap.createBitmap(width ,height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);
        //c.drawColor(Color.parseColor("#F1F1F1"));
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(c);
        else
            //does not have background drawable, then draw white background on the canvas
            c.drawColor(Color.WHITE);
        view.measure(View.MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), View.MeasureSpec.EXACTLY));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.draw(c);
        return b;
    }

    public static Bitmap getBitmapFromViewA(View view,int width,int height) {
        //view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        view.layout(0, 0, width, height);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    public static Bitmap getBitmapFromViewB(View view,int width,int height) {
        //view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
        //view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.draw(canvas);
        return bitmap;
    }


    /**
     * 保存图片到sdcard中
     * @param pBitmap
     */
    private static boolean savePic(Bitmap pBitmap,String strName)
    {
        if(pBitmap == null) return false;
        Log.i("ScreenShotHelper","bitmap size "+pBitmap.getRowBytes());
        FileOutputStream fos=null;
        try {
            fos=new FileOutputStream(strName);
            if(null!=fos)
            {
                pBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                return true;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 截图
     * @param
     * @return 截图并且保存sdcard成功返回true，否则返回false
     */
    public static boolean inCanvas(View view, int width, int height)
    {

        return  savePic(drawViewToCanvas(view, width, height), "sdcard/"+System.currentTimeMillis()+"_in_canvas.jpg");
    }

    public static boolean shot1(View view,int width,int height)
    {

        return  savePic(getBitmapFromViewA(view,width,height), "sdcard/"+System.currentTimeMillis()+"_shot_1.png");
    }

    public static boolean shot2(View view,int width,int height)
    {

        return  savePic(getBitmapFromViewB(view,width,height), "sdcard/"+System.currentTimeMillis()+"_shot_2.png");
    }

    public static boolean inCache(View view, int width, int height)
    {

        return  savePic(getDrawingCache(view, width, height), "sdcard/"+System.currentTimeMillis()+"_in_cache.jpg");
    }
}
