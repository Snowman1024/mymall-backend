package com.snowman.mymall.service;

import com.snowman.mymall.vo.BannerVO;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 15:41
 * @Version 1.0
 **/
public interface BannerService {

    public List<BannerVO> queryBannerList();
}
