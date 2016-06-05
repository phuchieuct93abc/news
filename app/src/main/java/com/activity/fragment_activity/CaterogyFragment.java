package com.activity.fragment_activity;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.activity.FragmentEnum;
import com.activity.MainActivityInterface;
import com.model.Category;
import com.phuchieu.news.R;
import com.services.CategoryService;
import com.services.HttpService;
import com.services.main_screen.Tile;
import com.services.main_screen.TileService;
import com.squareup.picasso.Picasso;

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
        Picasso.with(context).load(R.drawable.logo).into(background);

    }

    private void setClickListenerForButton() {

        for (int x = 0; x < table.getChildCount(); x++) {
            TableRow row = (TableRow) table.getChildAt(x);
            for (int y = 0; y < row.getChildCount(); y++) {
                AppCompatButton button = (AppCompatButton) row.getChildAt(y);
                int index = 2 * x + y;
                Tile tile = tiles.get(index);
                button.setText(tile.getTitle());
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
                mainActivityInterface.onCategorySelected(((AppCompatButton) v).getText().toString());

            }
        };
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;

        mainActivityInterface = (MainActivityInterface) activity;

    }

    @Override
    public void onStart() {
        super.onStart();
        mainActivityInterface.setRunningFragment(FragmentEnum.CATEROGY);

    }
}
