package com.activity.FeedView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.widget.TextView;

import com.phuchieu.news.R;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

class LoadImage extends AsyncTask<Object, Void, Bitmap> {
    TextView textView;
    Context context;
    Point size;

    public LoadImage(TextView textView,Context context,Point size) {
        this.textView = textView;
        this.context = context;
        this.size = size;
    }

    private LevelListDrawable mDrawable;

    @Override
    protected Bitmap doInBackground(Object... params) {
        String source = (String) params[0];
        mDrawable = (LevelListDrawable) params[1];
        try {
            return Picasso.with(context.getApplicationContext()).load(source).error(R.drawable.news).get();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if (bitmap != null) {
            double ratio = (double) bitmap.getWidth() / (double) bitmap.getHeight();
            BitmapDrawable d = new BitmapDrawable(context.getResources(),bitmap);
            mDrawable.addLevel(1, 1, d);
            mDrawable.setBounds(0, 0, size.x , (int)(size.x/ratio));
            mDrawable.setLevel(1);

            CharSequence t = textView.getText();
            textView.setText(t);

        }
    }
}