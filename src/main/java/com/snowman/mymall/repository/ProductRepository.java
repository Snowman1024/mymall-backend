package com.snowman.mymall.repository;

import com.snowman.mymall.common.jpa.BaseRepository;
import com.snowman.mymall.entity.BannerEntity;
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
public interface ProductRepository extends BaseRepository<ProductEntity, Integer> {

    @Query(nativeQuery = true,value = "SELECT p.id,p.goods_id,p.goods_specification_ids," +
            " p.goods_sn,p.goods_number,p.retail_price,p.market_price, " +
            " g.name ,g.list_pic_url " +
            " FROM product p,left join goods g ON p.goods_id = g.id " +
            " WHERE p.good_id = :goodsId " +
            " ORDER BY p.id DESC ")
    List<Object> queryByGoodsId(@Param(value = "goodsId") Integer goodsId);
}
