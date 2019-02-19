package com.framgia.my_editor_02.data.source.Local;

import com.framgia.my_editor_02.data.source.ImageDataSource;

public class ImageLocalDataSource implements ImageDataSource.ImageLocalDataSource {
    private static  ImageLocalDataSource sInstance;
    public static ImageLocalDataSource getInstance(){
        if (sInstance == null){
            sInstance = new ImageLocalDataSource();
        }
        return sInstance;
    }
}
