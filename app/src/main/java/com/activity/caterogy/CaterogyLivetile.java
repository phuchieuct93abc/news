package com.activity.caterogy;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.activity.ListFeedView.ListFeed_;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.config.SharePreference;
import com.feed.Category;
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

@EActivity(R.layout.caterogy_grid)
public class CaterogyLivetile extends AppCompatActivity {
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
    void randomImage() {
        randonImageUIThread();
    }

    @UiThread
    void randonImageUIThread() {
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



    private View.OnClickListener initialOnClickListener(final Category category) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryService.setListId(category);
                ListFeed_.intent(context).start();

            }
        };
    }


    @CheckedChange(R.id.darkBackground)
    void checkedChangeOnHelloCheckBox(CompoundButton hello, boolean isChecked) {
        sharePreference.setBoleanValue(SharePreference.DARK_BACKGROUND, isChecked);
    }

}
