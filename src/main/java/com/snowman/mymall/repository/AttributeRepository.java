package com.snowman.mymall.repository;

import com.snowman.mymall.common.jpa.BaseRepository;
import com.snowman.mymall.entity.AttributeEntity;
import com.snowman.mymall.entity.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 15:42
 * @Version 1.0
 **/
public interface AttributeRepository extends BaseRepository<AttributeEntity, Integer> {

    @Query(nativeQuery = true,value = "SELECT a.id,a.attribute_category_id,a.name,a.input_type," +
            " a.value,a.sort_order,ga.value " +
            " FROM attribute a left join goods_attribute ga ON ga.attribute_id=a.id " +
            " WHERE ga.goods_id = :goodsId " +
            " ORDER BY ga.id ASC ")
    List<Object> queryByGoodsId(@Param(value = "goodsId") Integer goodsId);
}
