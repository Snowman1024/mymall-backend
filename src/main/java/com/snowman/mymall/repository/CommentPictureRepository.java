package com.snowman.mymall.repository;

import com.snowman.mymall.common.jpa.BaseRepository;
import com.snowman.mymall.entity.CommentEntity;
import com.snowman.mymall.entity.CommentPictureEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 15:42
 * @Version 1.0
 **/
public interface CommentPictureRepository extends BaseRepository<CommentPictureEntity, Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM comment_picture " +
            " WHERE comment_id=:commentId ORDER BY id DESC ")
    List<CommentPictureEntity> queryByCommentId(@Param(value = "commentId") Integer commentId);

}
