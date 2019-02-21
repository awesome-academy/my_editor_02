package com.framgia.my_editor_02.utils;

import android.support.annotation.IntDef;

@IntDef({ ActionType.ACTION_GET_LIST, ActionType.ACTION_SEARCH })
public @interface ActionType {
    int ACTION_GET_LIST = 0;
    int ACTION_SEARCH = 1;
}
