package com.snowman.mymall.repository;

import com.snowman.mymall.common.jpa.BaseRepository;
import com.snowman.mymall.entity.GoodsGalleryEntity;
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
public interface GoodsGalleryRepository extends BaseRepository<GoodsGalleryEntity, Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM  goods_gallery WHERE goods_id=:goodsId" +
            " ORDER BY id DESC ")
    List<GoodsGalleryEntity> queryByGoodsId(@Param(value = "goodsId") Integer goodsId);
}
