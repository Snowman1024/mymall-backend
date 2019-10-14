package com.snowman.mymall.service.impl;

import com.snowman.mymall.vo.BannerVO;
import com.snowman.mymall.entity.BannerEntity;
import com.snowman.mymall.repository.BannerRepository;
import com.snowman.mymall.service.BannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 15:41
 * @Version 1.0
 **/
@Service
public class BannerServiceImpl implements BannerService {

    private Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private BannerRepository bannerRepository;

    /**
     * 首页查询banner信息
     * @return
     */
    @Override
    public List<BannerVO> queryBannerList(){
        logger.info("首页查询banner信息service开始");

        List<BannerVO> bannerVOList = new ArrayList<>();

        List<BannerEntity> bannerEntityList =  bannerRepository.queryBannerList();
        if(CollectionUtils.isEmpty(bannerEntityList)){
            return bannerVOList;
        }
        BeanCopier copier = BeanCopier.create(BannerEntity.class,BannerVO.class,false);
        for(BannerEntity entity:bannerEntityList){
            BannerVO bannerVO = new BannerVO();
            copier.copy(entity,bannerVO,null);
            bannerVOList.add(bannerVO);
        }
        logger.info("首页查询banner信息service结束");
        return bannerVOList;
    }
}
