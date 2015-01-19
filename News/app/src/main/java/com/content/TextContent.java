package com.content;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.config.Config_;

import org.androidannotations.annotations.sharedpreferences.Pref;

public class TextContent extends Content {
    private static int textSize = 25;
    String text;
    public static void setTextSize(int textSize) {
        TextContent.textSize = textSize;
    }



    public TextContent(String text, Context context) {
        super(context);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public View toView() {
        TextView textView = new TextView(context);
        textView.setTextColor(Color.BLACK);
        textView.setText("    " + toString());
        textView.setTextSize(TextContent.textSize);
        return textView;
    }


}
