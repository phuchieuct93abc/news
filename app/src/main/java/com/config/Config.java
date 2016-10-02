package com.config;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface Config {
    @DefaultInt(2)
    int textSize();

    @DefaultBoolean(false)
    boolean darkBackground();
}
