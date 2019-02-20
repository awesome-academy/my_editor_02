package com.framgia.my_editor_02.data.source;

import com.framgia.my_editor_02.data.model.Collection;
import com.framgia.my_editor_02.data.model.Photo;
import io.reactivex.Single;
import java.util.List;

public interface ImageDataSource {
    interface ImageLocalDataSource {
    }

    interface ImageRemoteDataSource {
        Single<List<Collection>> getListCollections(int page);

        Single<List<Photo>> getListPhotos(int page);

        Single<List<Photo>> searchPhotos(String query, int page);

        Single<List<Collection>> searchCollections(String query, int page);
    }
}
