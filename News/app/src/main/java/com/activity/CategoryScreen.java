package com.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.IconTextView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.activity.ListFeedView.ListFeed_;
import com.gc.materialdesign.views.ButtonRectangle;
import com.phuchieu.news.R;
import com.services.CategoryService_JSON;
import com.services.main_screen.Tile;
import com.services.main_screen.TileService;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.caterogy_activity)
public class CategoryScreen extends Activity {
    Context context = this;
    @ViewById
    TableLayout table;
    List<Tile> tiles;
    Typeface font;

    @AfterViews
    void afterView() {
        setClickListenerForButton();
    }

    @AfterInject
    void afterInject() {
        tiles = TileService.getList();
    }

    private void setClickListenerForButton() {
        for (int x = 0; x < table.getChildCount(); x++) {
            TableRow row = (TableRow) table.getChildAt(x);
            for (int y = 0; y < row.getChildCount(); y++) {


                ButtonRectangle button = (ButtonRectangle) row.getChildAt(y);
                int index = 2 * x + y;
                Tile tile = tiles.get(index);
                addIcon(button, tile.getIcon());
                button.setText(tile.getTitle());
                button.setPaddingRelative(10, 0, 0, 0);
                button.setGravity(Gravity.START);
                OnClickListener initialOnClickListener = initialOnClickListener(tile.getId());
                button.setOnClickListener(initialOnClickListener);
            }
        }
    }

    private void addIcon(ButtonRectangle button, String icon) {
        IconTextView iconView = new IconTextView(this);
        iconView.setText("{" + icon + "}");
        iconView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        iconView.setGravity(Gravity.CENTER);
        iconView.setTextColor(Color.WHITE);
        iconView.setPaddingRelative(10, 0, 0, 0);
        iconView.setTextSize(20);
        button.addView(iconView);
    }

    private OnClickListener initialOnClickListener(final int caterogyId) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryService_JSON.setListId(caterogyId);
                ListFeed_.intent(context).start();

            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_test, menu);
        return super.onCreateOptionsMenu(menu);
    }

	/*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_search:
			SearchScreen_.intent(context).start();
			return true;
		case R.id.action_compose:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}*/
}
