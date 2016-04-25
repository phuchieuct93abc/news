package com;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.activity.MainActivityInterface;
import com.phuchieu.news.R;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

public class ModalBottomSheet
        extends BottomSheetDialogFragment implements DiscreteSeekBar.OnProgressChangeListener {
    MainActivityInterface mainActivityInterface;
    Boolean isDarkMode;
    int textSize;

     public ModalBottomSheet(Boolean isDarkMode,int textSize){
      this.isDarkMode = isDarkMode;
         this.textSize = textSize;
         Log.i("hieu",isDarkMode+"  "+textSize);
    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(
                R.layout.bottom_sheet_setting, container, false);
        mainActivityInterface = (MainActivityInterface) getActivity();

        Switch switchDarkMode = (Switch) v.findViewById(R.id.switchDarkMode);
        DiscreteSeekBar seekTextsize = (DiscreteSeekBar) v.findViewById(R.id.seekTextsize);
        switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mainActivityInterface.changeColor(isChecked);
            }
        });
        seekTextsize.setIndicatorPopupEnabled(true);

        seekTextsize.setOnProgressChangeListener(this);

        //Default value
        switchDarkMode.setChecked(isDarkMode);
        seekTextsize.setProgress(textSize);
        return v;
    }

    @Override
    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
        mainActivityInterface.changeSize(value);

    }

    @Override
    public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

    }
}