package com.services;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ACER on 3/14/2017.
 */

public enum ViewModeEnum {
    UNREAD_FEED(1), READ_FEED(2), ALL_FEED(3);
    int value;
    ViewModeEnum(int i) {
        this.value = i;
    }
    static Map<Integer, ViewModeEnum> map = new HashMap<>();

    static {
        for (ViewModeEnum viewMode : ViewModeEnum.values()) {
            map.put(viewMode.getValue(), viewMode);
        }
    }

    public int getValue() {
        return value;
    }
    public static ViewModeEnum getByCode(int value){
        return map.get(value);
    }
}
