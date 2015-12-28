package com.activity.fragment_activity;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.activity.MainActivityInterface;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapSize;
import com.model.Category;
import com.phuchieu.news.R;
import com.services.CategoryService;
import com.services.HttpService;
import com.services.main_screen.Tile;
import com.services.main_screen.TileService;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.caterogy_grid)
public class CaterogyFragment extends Fragment {
    MainActivityInterface mainActivityInterface;
    Context context;
    @ViewById
    TableLayout table;

    @Bean
    CategoryService categoryService;
    List<Tile> tiles;
    @ViewById
    ImageView background;
    @Bean
    HttpService httpService;

    @AfterViews
    void afterView() {
        setClickListenerForButton();
        randomImage();
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
                button.setRounded(true);
                button.setShowOutline(true);
                button.setBootstrapSize(DefaultBootstrapSize.XL);
                button.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);


//                button.setBootstrapType(tile.getType());
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
                mainActivityInterface.onCategorySelected(((BootstrapButton) v).getText().toString());

            }
        };
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;

        mainActivityInterface = (MainActivityInterface) activity;

    }


}
