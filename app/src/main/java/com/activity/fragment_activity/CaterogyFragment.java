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
import com.model.Source.Source;
import com.phuchieu.news.R;
import com.services.CategoryService;
import com.services.FeedService;
import com.services.HttpService;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.caterogy_grid)
public class CaterogyFragment extends Fragment {
    MainActivityInterface mainActivityInterface;
    Context context;
    Source source;

    @ViewById
    TableLayout table;

    @Bean
    CategoryService categoryService;
    @Bean
    FeedService feedService;
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
        source = categoryService.getSourceByLanguage("vi");
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
                com.model.Source.Category category = this.source.getCategories().get(index);
                button.setText(category.getName());
                View.OnClickListener initialOnClickListener = initialOnClickListener(category, this.source);
                button.setOnClickListener(initialOnClickListener);

            }
        }
    }


    private View.OnClickListener initialOnClickListener(final com.model.Source.Category link, final Source source) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryService.setSelectedCategory(link);
                feedService.setFeedContentLink(source);
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
