package com.snowman.mymall.repository;

import com.snowman.mymall.common.jpa.BaseRepository;
import com.snowman.mymall.entity.GoodsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/11 15:05
 * @Version 1.0
 **/
public interface GoodsRepository extends BaseRepository<GoodsEntity, Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM goods WHERE is_delete=0 AND is_new=1 " +
            " ORDER BY id DESC ")
    List<GoodsEntity> queryNewGoodsList();

    @Query(nativeQuery = true,value = "SELECT * FROM goods WHERE id = :id " +
            " AND is_on_sale=1 AND is_delete=0 ")
    GoodsEntity queryById(@Param(value = "id") Integer id);

    @Query(nativeQuery = true,value = "SELECT * FROM goods WHERE category_id = :categoryId " +
            " AND is_on_sale=1 AND is_delete=0 ORDER BY id DESC ")
    List<GoodsEntity> queryByCategoryId(@Param(value = "categoryId") Integer categoryId);

    @Query(nativeQuery = true,value = "SELECT * FROM goods WHERE is_on_sale=1 AND is_delete=0 " +
            " AND id IN (:goodsIds) ORDER BY id DESC ")
    List<GoodsEntity> queryByGoodsIds(@Param(value = "goodsIds") List<Integer> goodsIds);
}
