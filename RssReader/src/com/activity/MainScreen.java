package com.activity;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.rssreader.R;
import com.gc.materialdesign.views.ButtonRectangle;
import com.services.main_screen.Tile;
import com.services.main_screen.TileService;

@SuppressLint("Registered")
@EActivity(R.layout.first_screen)
public class MainScreen extends Activity {
	Context context = this;
	@ViewById
	TableLayout table;
	List<Tile> tiles;

	@AfterViews
	void afterView() {
		tiles = TileService.getList();
		setClickListenerForButton();
	
	}
	

	private void setClickListenerForButton() {
		for (int x = 0; x < table.getChildCount(); x++) {
			TableRow row = (TableRow) table.getChildAt(x);
			for (int y = 0; y < row.getChildCount(); y++) {
				ButtonRectangle button = (ButtonRectangle) row.getChildAt(y);
				int index = 2 * x + y;
				String title = tiles.get(index).getTitle();
				button.setText(title);
				OnClickListener initialOnClickListener = initialOnClickListener(tiles.get(index).getUrl());
				button.setOnClickListener(initialOnClickListener);

				
			}
		}
	}

	private OnClickListener initialOnClickListener(final String url) {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {

				MainActivity_.intent(context).link(url)
						.start();
			}
		};
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_search:
	        	SearchScreen_.intent(context).start();
	            return true;
	        case R.id.action_settings:
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
