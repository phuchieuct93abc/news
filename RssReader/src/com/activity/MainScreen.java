package com.activity;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rssreader.R;
import com.services.main_screen.Tile;
import com.services.main_screen.TileService;

@EActivity(R.layout.first_screen)
public class MainScreen extends Activity {
	Context context = this;
	@ViewById
	LinearLayout row1;

	@AfterViews
	void afterView() {
		List<Tile> tiles = TileService.getList();
		for (Tile tile : tiles) {
			if (tiles.indexOf(tile) > 2) {
				break;
			}
			tile.setOnClick(initialOnClickListener(tile.getUrl()));

			row1.addView(tile.getView(this));
		}
	}

	private OnClickListener initialOnClickListener(final String url) {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity_.intent(context).link(url).start() ;
			}
		};
	}
}
