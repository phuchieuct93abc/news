package com.content;

import android.content.Context;
import android.view.View;

public class Content {
    Context context;

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    int textSize = 20;

    public Content(Context context) {
        this.context = context;
    }

    public View toView() {
        return null;
    }


}
