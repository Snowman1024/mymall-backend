package com.snowman.mymall.repository;

import com.snowman.mymall.common.jpa.BaseRepository;
import com.snowman.mymall.entity.GoodsEntity;
import com.snowman.mymall.entity.GoodsSpecificationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/11 15:05
 * @Version 1.0
 **/
public interface GoodsSpecificationRepository extends BaseRepository<GoodsSpecificationEntity, Integer> {

    @Query(nativeQuery = true,value = "SELECT gs.id,gs.goods_id,gs.specification_id," +
            " gs.value,gs.pic_url,s.name " +
            " FROM goods_specification gs,specification s " +
            " WHERE gs.specification_id= s.id AND gs.goods_id=:goodsId "  +
            " ORDER BY s.sort_order asc ")
    List<Object> queryByGoodsId(@Param(value = "goodsId")Integer goodsId);

}
