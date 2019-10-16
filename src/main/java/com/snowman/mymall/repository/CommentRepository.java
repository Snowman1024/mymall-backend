package com.snowman.mymall.repository;

import com.snowman.mymall.common.jpa.BaseRepository;
import com.snowman.mymall.entity.AttributeEntity;
import com.snowman.mymall.entity.CommentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 15:42
 * @Version 1.0
 **/
public interface CommentRepository extends BaseRepository<CommentEntity, Integer> {

    @Query(nativeQuery = true,value = "SELECT  count(1) FROM comment " +
            " WHERE type_id=:typeId AND value_id=:valueId AND status=1 ")
    int queryTotal(@Param(value = "typeId") Integer typeId,
                   @Param(value = "valueId") Integer valueId);

    @Query(nativeQuery = true,value = "SELECT DISTINCT c.* FROM comment c " +
            " WHERE c.type_id=:typeId AND c.value_id=:valueId AND c.status=1 " +
            " order by c.id desc ")
    List<CommentEntity> queryByTypeIdAndValueId(@Param(value = "typeId") Integer typeId,
                                                @Param(value = "valueId") Integer valueId);
}
