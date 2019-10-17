package com.snowman.mymall.repository;

import com.snowman.mymall.common.jpa.BaseRepository;
import com.snowman.mymall.entity.AttributeEntity;
import com.snowman.mymall.entity.CollectEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 15:42
 * @Version 1.0
 **/
public interface CollectRepository extends BaseRepository<CollectEntity, Integer> {

    @Query(nativeQuery = true,value = "SELECT count(1) FROM collect WHERE user_id=:userId AND " +
            " value_id=:valueId AND type_id=:typeId ")
    Integer queryTotal(@Param(value = "userId") Integer userId,
                            @Param(value = "valueId") Integer valueId,
                            @Param(value = "typeId") Integer typeId);

    @Query(nativeQuery = true,value = "SELECT * FROM collect WHERE user_id=:userId AND " +
            " value_id=:valueId AND type_id=:typeId ")
    List<CollectEntity> queryList(@Param(value = "userId") Integer userId,
                       @Param(value = "valueId") Integer valueId,
                       @Param(value = "typeId") Integer typeId);
}
