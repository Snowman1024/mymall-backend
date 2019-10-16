package com.snowman.mymall.repository;

import com.snowman.mymall.common.jpa.BaseRepository;
import com.snowman.mymall.entity.AttributeEntity;
import com.snowman.mymall.entity.BrandEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 15:42
 * @Version 1.0
 **/
public interface BrandRepository extends BaseRepository<BrandEntity, Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM brand WHERE id=:id ")
    BrandEntity queryById(@Param(value = "id") Integer id);
}
