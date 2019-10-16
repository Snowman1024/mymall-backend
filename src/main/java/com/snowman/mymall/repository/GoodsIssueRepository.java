package com.snowman.mymall.repository;

import com.snowman.mymall.common.jpa.BaseRepository;
import com.snowman.mymall.entity.AttributeEntity;
import com.snowman.mymall.entity.GoodsIssueEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 15:42
 * @Version 1.0
 **/
public interface GoodsIssueRepository extends BaseRepository<GoodsIssueEntity, Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM goods_issue order by id desc ")
    List<GoodsIssueEntity> queryGoodsIssueList();
}
