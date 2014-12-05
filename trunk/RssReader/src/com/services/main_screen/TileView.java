package com.services.main_screen;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.rssreader.R;

public class TileView extends LinearLayout {
	Tile tile;
	Button button;
	public void setOnClick(OnClickListener listener){
		
		button.setOnClickListener(listener);
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
		button.setText(tile.getTitle());
		button.setOnClickListener(tile.getOnClick());
	}

	public TileView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public TileView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.tile_view, this, true);
		LinearLayout linearLayout = (LinearLayout) getChildAt(0);

		button = (Button) linearLayout.getChildAt(0);
		
	}

}