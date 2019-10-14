package com.snowman.mymall.repository;

import com.snowman.mymall.common.jpa.BaseRepository;
import com.snowman.mymall.entity.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/14 11:02
 * @Version 1.0
 **/
public interface CategotyRepository extends BaseRepository<CategoryEntity,Integer> {

    @Query(nativeQuery = true,value = "SELECT parent_id FROM category WHERE id in (:ids) ")
    List<Integer> queryParentIdByIds(@Param(value = "ids") List<Integer> ids);

    @Query(nativeQuery = true,value = "SELECT * FROM category WHERE id in (:ids) " +
            " ORDER  BY sort_order asc ")
    List<CategoryEntity> queryByIds(@Param(value = "ids") List<Integer> ids);

    @Query(nativeQuery = true,value = "SELECT * FROM category WHERE parent_id=:parentId ")
    List<CategoryEntity> queryByParentId(@Param(value = "parentId")Integer parentId);
}
