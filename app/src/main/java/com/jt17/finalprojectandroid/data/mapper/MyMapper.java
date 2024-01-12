package com.jt17.finalprojectandroid.data.mapper;

import com.jt17.finalprojectandroid.data.entity.MyEntity;
import com.jt17.finalprojectandroid.domain.model.ItemsModel;

public class MyMapper {

    public static ItemsModel toItemsModel(MyEntity myEntity) {
        return new ItemsModel(myEntity.getId(), myEntity.getName(), myEntity.getSum());
    }

}
