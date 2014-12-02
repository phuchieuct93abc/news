package com.activity;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;

import com.example.rssreader.R;
import com.services.main_screen.Tile;
import com.services.main_screen.TileService;
import com.services.main_screen.TileView;

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
			TileView tileView = new TileView(context);
			tileView.setName(tile.getName());
			row1.addView(tileView);
		}
	}
}
