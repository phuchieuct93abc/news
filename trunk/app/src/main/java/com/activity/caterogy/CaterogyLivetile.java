package com.activity.caterogy;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.activity.ListFeedView.ListFeed_;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.config.SharePreference;
import com.feed.Category;
import com.koushikdutta.ion.Ion;
import com.phuchieu.news.R;
import com.services.CategoryService;
import com.services.HttpService;
import com.services.main_screen.Tile;
import com.services.main_screen.TileService;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by phuchieuct on 8/23/2015.
 */
@EActivity(R.layout.caterogy_grid)
public class CaterogyLivetile  extends AppCompatActivity {
    Context context = this;
    @ViewById
    TableLayout table;

    @ViewById(R.id.darkBackground)
    android.widget.CheckBox darkBackground;
    @ViewById
    LinearLayout linearLayout;
    @Bean
    CategoryService categoryService;


    List<Tile> tiles;

    SharePreference sharePreference;
    @ViewById
    ImageView background;
    @Bean
    HttpService httpService;
    @ViewById
    NavigationView navigation_view;
    @ViewById
    DrawerLayout drawer;

    @AfterViews
    void afterView() {
        randomImage();
        setClickListenerForButton();

        sharePreference = new SharePreference(context);
        darkBackground.setChecked(sharePreference.getBooleanValue(SharePreference.DARK_BACKGROUND));

    }

    @AfterInject
    void afterInject() {
        tiles = TileService.getList();
    }
    @Background
    void randomImage(){
        randonImageUIThread();
    }
    @UiThread
    void randonImageUIThread(){
        httpService.setRandomImage(background);

    }

    private void setClickListenerForButton() {
        for (int x = 0; x < table.getChildCount(); x++) {
            TableRow row = (TableRow) table.getChildAt(x);
            for (int y = 0; y < row.getChildCount(); y++) {
                BootstrapButton button = (BootstrapButton) row.getChildAt(y);
                int index = 2 * x + y;
                Tile tile = tiles.get(index);
                button.setText(tile.getTitle());
                button.setLeftIcon(tile.getIcon());
                button.setBootstrapType(tile.getType());
                View.OnClickListener initialOnClickListener = initialOnClickListener(tile.getCaterogi());
                button.setOnClickListener(initialOnClickListener);

            }
        }
    }

//    private void addIcon(Button button, String icon) {
//        IconTextView iconView = new IconTextView(this);
//        iconView.setText("{" + icon + "}");
//        iconView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        iconView.setGravity(Gravity.CENTER);
//        iconView.setTextColor(Color.WHITE);
//        iconView.setPaddingRelative(10, 0, 0, 0);
//        iconView.setTextSize(20);
////        button.setCompoundDrawablesWithIntrinsicBounds( new IconDrawable(this, "I {fa-heart-o} to {fa-code} on {fa-android}"), 0, 0, 0);       // button.addView(iconView);
//    }

    private View.OnClickListener initialOnClickListener(final Category category) {
        return new View.OnClickListener() {
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
