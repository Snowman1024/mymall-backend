package com.snowman.mymall.repository;

import com.snowman.mymall.common.jpa.BaseRepository;
import com.snowman.mymall.entity.AttributeEntity;
import com.snowman.mymall.entity.CartEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 15:42
 * @Version 1.0
 **/
public interface CartRepository extends BaseRepository<CartEntity, Integer> {

    @Query(nativeQuery = true,value = "SELECT c.id,c.user_id,c.session_id,c.goods_id,c.goods_sn," +
            " c.product_id,c.goods_name,c.market_price,c.retail_price,c.number,c.goods_specifition_name_value," +
            " c.goods_specifition_ids,c.checked,c.list_pic_url,g.list_pic_url AS picUrl,p.retail_price AS rprice" +
            " FROM cart c " +
            " LEFT JOIN goods g ON c.goods_id = g.id " +
            " LEFT JOIN product p on p.goods_id = c.goods_id AND p.id = c.product_id " +
            " WHERE c.user_id = :userId " +
            " ORDER BY c.id DESC ")
    List<Object> queryByUserId(@Param(value = "userId") Integer userId);

    @Query(nativeQuery = true,value = "SELECT c.id,c.user_id,c.session_id,c.goods_id,c.goods_sn," +
            " c.product_id,c.goods_name,c.market_price,c.retail_price,c.number,c.goods_specifition_name_value," +
            " c.goods_specifition_ids,c.checked,c.list_pic_url,g.list_pic_url AS picUrl,p.retail_price AS rprice " +
            " FROM cart c " +
            " LEFT JOIN goods g ON c.goods_id = g.id " +
            " LEFT JOIN product p on p.goods_id = c.goods_id AND p.id = c.product_id " +
            " WHERE c.user_id = :userId AND c.goods_id = :goodsId AND c.product_id=:productId " +
            " ORDER BY c.id DESC ")
    List<Object> queryList(@Param(value = "userId") Integer userId,
                           @Param(value = "goodsId") Integer goodsId,
                           @Param(value = "productId") Integer productId);

    @Modifying
    @Query(nativeQuery = true,value = "UPDATE cart SET number=:num WHERE id=:id")
    Integer updateCartNum(@Param(value = "id")Integer id,
                          @Param(value = "num")Integer num);
}
