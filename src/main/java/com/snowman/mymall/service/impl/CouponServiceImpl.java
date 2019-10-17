package com.snowman.mymall.service.impl;

import com.snowman.mymall.common.enumeration.CouponStatus;
import com.snowman.mymall.common.utils.ConvertUtil;
import com.snowman.mymall.entity.CouponEntity;
import com.snowman.mymall.entity.GoodsGalleryEntity;
import com.snowman.mymall.repository.CouponRepository;
import com.snowman.mymall.repository.UserCouponRepository;
import com.snowman.mymall.service.CouponService;
import com.snowman.mymall.vo.AttributeVO;
import com.snowman.mymall.vo.CouponVO;
import com.snowman.mymall.vo.GoodsGalleryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 15:08
 * @Version 1.0
 **/
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    /**
     * 查询优惠券信息
     * @param userId
     * @param sendType
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CouponVO> queryUserCoupons(Integer userId, Integer sendType) {
        List<Object> couponObjList = couponRepository.queryUserCoupons(userId, sendType);

        List<CouponVO> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(couponObjList)) {
            return voList;
        }
        Object[] couponObjArr = couponObjList.toArray();

        for (int i = 0; i < couponObjArr.length; i++) {
            Object[] item = (Object[]) couponObjArr[i];
            CouponVO vo = new CouponVO();
            vo.setId(ConvertUtil.toInteger(item[0]));
            vo.setName(ConvertUtil.toString(item[1]));
            vo.setTypeMoney(ConvertUtil.toBigDecimal(item[2]));
            vo.setSendType(ConvertUtil.toInteger(item[3]));
            vo.setMinAmount(ConvertUtil.toBigDecimal(item[4]));
            vo.setMaxAmount(ConvertUtil.toBigDecimal(item[5]));
            vo.setSendStartDate(ConvertUtil.toDate(item[6]));
            vo.setSendEndDate(ConvertUtil.toDate(item[7]));
            vo.setUseStartDate(ConvertUtil.toDate(item[8]));
            vo.setUseEndDate(ConvertUtil.toDate(item[9]));
            vo.setMinGoodsAmount(ConvertUtil.toBigDecimal(item[10]));
            vo.setMinTransmitNum(ConvertUtil.toInteger(item[11]));

            vo.setCouponNumber(ConvertUtil.toString(item[12]));
            vo.setUserId(ConvertUtil.toInteger(item[13]));
            vo.setCouponStatus(ConvertUtil.toInteger(item[14]));
            vo.setUserCouponId(ConvertUtil.toInteger(item[15]));
            voList.add(vo);
        }

        for (CouponVO couponVO : voList) {
            if (couponVO.getCouponStatus() == CouponStatus.USUSE.getKey()) {
                // 检查是否过期
                if (couponVO.getUseEndDate().before(new Date())) {
                    couponVO.setCouponStatus(CouponStatus.EXPIRE.getKey());
                    userCouponRepository.updateUserCoupon(couponVO.getUserCouponId(),couponVO.getCouponStatus());
                }
            }
            if (couponVO.getCouponStatus() == CouponStatus.EXPIRE.getKey()) {
                // 检查是否不过期
                if (couponVO.getUseEndDate().after(new Date())) {
                    couponVO.setCouponStatus(CouponStatus.USUSE.getKey());
                    userCouponRepository.updateUserCoupon(couponVO.getUserCouponId(),couponVO.getCouponStatus());
                }
            }
        }
        return voList;
    }

    /**
     * 通过发放方式查询优惠券信息
     * @param sendTypeList
     * @return
     */
    @Override
    public List<CouponVO> queryCouponBySendType(List<Integer> sendTypeList){
       List<CouponEntity> entityList = couponRepository.queryCouponBySendType(sendTypeList);
        List<CouponVO> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(entityList)) {
            return voList;
        }
        BeanCopier copier = BeanCopier.create(CouponEntity.class, CouponVO.class, false);
        for (CouponEntity entity : entityList) {
            CouponVO vo = new CouponVO();
            copier.copy(entity, vo, null);
            voList.add(vo);
        }
        return voList;
    }
}
