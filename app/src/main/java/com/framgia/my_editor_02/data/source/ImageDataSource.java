package com.framgia.my_editor_02.data.source;

import com.framgia.my_editor_02.data.model.Collection;
import io.reactivex.Single;
import java.util.List;

public interface ImageDataSource {
    interface ImageLocalDataSource {
    }

    interface ImageRemoteDataSource {
        Single<List<Collection>> getListCollections(int page);
    }
}
