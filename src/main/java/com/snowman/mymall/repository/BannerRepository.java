package com.snowman.mymall.repository;

import com.snowman.mymall.common.jpa.BaseRepository;
import com.snowman.mymall.entity.BannerEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 15:42
 * @Version 1.0
 **/
public interface BannerRepository extends BaseRepository<BannerEntity, Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM banner WHERE enabled=1" +
            " ORDER BY id DESC ")
    List<BannerEntity> queryBannerList();
}
