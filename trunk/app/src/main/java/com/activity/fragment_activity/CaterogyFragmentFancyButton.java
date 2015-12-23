package com.activity.fragment_activity;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.activity.MainActivityInterface;
import com.feed.Category;
import com.phuchieu.news.R;
import com.services.CategoryService;
import com.services.HttpService;
import com.services.main_screen.Tile;
import com.services.main_screen.TileService;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

@EFragment(R.layout.caterogy_grid_fancy_button)
public class CaterogyFragmentFancyButton extends Fragment {
    MainActivityInterface mainActivityInterface;
    Context context;
    int numberRow = 4, numberColumn = 2;
    @ViewById
    TableLayout table;
    @ViewById
    ImageView background;
    @Bean
    CategoryService categoryService;
    @Bean
    HttpService httpService;
    List<Tile> tiles;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;

        mainActivityInterface = (MainActivityInterface) activity;

    }
    @AfterInject
    void afterInject() {
        tiles = TileService.getList();
    }
    @AfterViews
    void afterView() {
        try {
            for (int row = 0; row < numberRow; row++) {

                TableRow tableRow = (TableRow) table.getChildAt(row);
                for (int column = 0; column < numberColumn; column++) {

                    FancyButton button = (FancyButton) tableRow.getChildAt(column);
                    int index = 2 * row + column;

                    Tile tile = tiles.get(index);
                    button.setText(tile.getTitle());
                    button.setGhost(true);
                    button.setRadius(10000000);

//                    button.setIconResource(tile.getIcon());
                    View.OnClickListener initialOnClickListener = initialOnClickListener(tile.getCaterogi());

                    button.setOnClickListener(initialOnClickListener);
//                    button.setBorderColor(android.R.color.white);
                    button.setFocusBackgroundColor(R.color.white);


                }
            }

                httpService.setRandomImage(background);


        }catch(Exception e){

        }
    }

    private View.OnClickListener initialOnClickListener(final Category category) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryService.setListId(category);
                mainActivityInterface.onCategorySelected(((FancyButton) v).getText().toString());

            }
        };
    }
}
