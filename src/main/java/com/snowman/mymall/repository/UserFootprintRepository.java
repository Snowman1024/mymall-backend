package com.snowman.mymall.repository;

import com.snowman.mymall.common.jpa.BaseRepository;
import com.snowman.mymall.entity.AttributeEntity;
import com.snowman.mymall.entity.UserFootprintEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 15:42
 * @Version 1.0
 **/
public interface UserFootprintRepository extends BaseRepository<UserFootprintEntity, Integer> {


    @Query(nativeQuery = true,value = "SELECT count(1) FROM user_footprint " +
            " WHERE goods_id=:goodsId AND referrer=:referrer ")
    Integer queryTotalByGoodsId(@Param(value = "goodsId")Integer goodsId,
                            @Param(value = "referrer")Integer referrer);
}
