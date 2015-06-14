package com.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.IconTextView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.activity.ListFeedView.ListFeed_;
import com.config.SharePreference;
import com.feed.Category;
import com.gc.materialdesign.views.ButtonRectangle;
import com.phuchieu.news.R;
import com.services.CategoryService;
import com.services.main_screen.Tile;
import com.services.main_screen.TileService;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.caterogy_activity)
public class CategoryScreen extends Activity {
    Context context = this;
    @ViewById
    TableLayout table;

    @ViewById(R.id.darkBackground)
    android.widget.CheckBox darkBackground;
    @Bean
    CategoryService categoryService;

    List<Tile> tiles;

    SharePreference sharePreference;

    @AfterViews
    void afterView() {
        setClickListenerForButton();
        sharePreference = new SharePreference(context);
        darkBackground.setChecked(sharePreference.getBooleanValue(SharePreference.DARK_BACKGROUND));
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
                OnClickListener initialOnClickListener = initialOnClickListener(tile.getCaterogi());
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

    private OnClickListener initialOnClickListener(final Category category) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryService.setListId(category);
                ListFeed_.intent(context).start();

            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        return super.onCreateOptionsMenu(menu);
    }

    @CheckedChange(R.id.darkBackground)
    void checkedChangeOnHelloCheckBox(CompoundButton hello, boolean isChecked) {
        sharePreference.setBoleanValue(SharePreference.DARK_BACKGROUND, isChecked);
    }

}
