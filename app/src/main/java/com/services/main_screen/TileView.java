package com.services.main_screen;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import com.phuchieu.news.R;

public class TileView extends LinearLayout {
    Tile tile;
    Button button;

    public TileView(Context context) {
        this(context, null);
    }

    public TileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tile_view, this, true);
        LinearLayout linearLayout = (LinearLayout) getChildAt(0);

        button = (Button) linearLayout.getChildAt(0);

    }




    public void setTile(Tile tile) {
        this.tile = tile;
        button.setText(tile.getTitle());
        button.setOnClickListener(tile.getOnClick());
    }

}
