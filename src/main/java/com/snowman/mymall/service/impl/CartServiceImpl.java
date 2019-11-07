package com.snowman.mymall.service.impl;

import com.snowman.mymall.common.enumeration.CouponSentType;
import com.snowman.mymall.common.utils.ConvertUtil;
import com.snowman.mymall.common.utils.Result;
import com.snowman.mymall.entity.CartEntity;
import com.snowman.mymall.entity.GoodsEntity;
import com.snowman.mymall.entity.GoodsSpecificationEntity;
import com.snowman.mymall.repository.CartRepository;
import com.snowman.mymall.repository.GoodsRepository;
import com.snowman.mymall.repository.GoodsSpecificationRepository;
import com.snowman.mymall.service.CartService;
import com.snowman.mymall.service.CouponService;
import com.snowman.mymall.service.ProductService;
import com.snowman.mymall.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/17 11:08
 * @Version 1.0
 **/
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private GoodsSpecificationRepository goodsSpecificationRepository;

    @Autowired
    private CouponService couponService;
    /**
     * 通过userId查购物车信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<CartVO> queryByUserId(Integer userId) {
        List<Object> cartObjList = cartRepository.queryByUserId(userId);
        List<CartVO> voList = entityToVo(cartObjList);
        return voList;
    }


    /**
     * 通过userId,goodsId,productId查询
     *
     * @param userId
     * @param goodsId
     * @param productId
     * @return
     */
    @Override
    public List<CartVO> queryList(Integer userId, Integer goodsId, Integer productId) {
        List<Object> cartObjList = cartRepository.queryList(userId, goodsId, productId);
        List<CartVO> voList = entityToVo(cartObjList);
        return voList;
    }

    /**
     * 获取购物车商品的总件件数
     *
     * @param loginUser
     * @return
     */
    @Override
    public Map<String, Object> goodsCount(UserVO loginUser) {
        //查询列表数据
        List<CartVO> cartList = this.queryByUserId(loginUser.getUserId());

        //获取购物车统计信息
        Integer goodsCount = 0;
        for (CartVO cartItem : cartList) {
            goodsCount += cartItem.getNumber();
        }

        Map<String, Object> resultObj = new HashMap();
        resultObj.put("cartList", cartList);
        resultObj.put("goodsCount", goodsCount);

        return resultObj;
    }

    private List<CartVO> entityToVo(List<Object> cartObjList) {
        List<CartVO> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(cartObjList)) {
            return voList;
        }
        Object[] cartObjArr = cartObjList.toArray();

        for (int i = 0; i < cartObjArr.length; i++) {
            Object[] item = (Object[]) cartObjArr[i];

            CartVO vo = new CartVO();
            vo.setId(ConvertUtil.toInteger(item[0]));
            vo.setUserId(ConvertUtil.toInteger(item[1]));
            vo.setSessionId(ConvertUtil.toString(item[2]));
            vo.setGoodsId(ConvertUtil.toInteger(item[3]));
            vo.setGoodsSn(ConvertUtil.toString(item[4]));
            vo.setProductId(ConvertUtil.toInteger(item[5]));
            vo.setGoodsName(ConvertUtil.toString(item[6]));
            vo.setMarketPrice(ConvertUtil.toBigDecimal(item[7]));
            vo.setRetailPrice(ConvertUtil.toBigDecimal(item[8]));
            vo.setNumber(ConvertUtil.toInteger(item[9]));
            vo.setGoodsSpecifitionNameValue(ConvertUtil.toString(item[10]));
            vo.setGoodsSpecifitionIds(ConvertUtil.toString(item[11]));

            vo.setChecked(ConvertUtil.toInteger(item[12]));
            vo.setListPicUrl(ConvertUtil.toString(item[13]));
            vo.setListPicUrl(ConvertUtil.toString(item[14])); //覆盖了
            vo.setRetailProductPrice(ConvertUtil.toBigDecimal(item[15]));
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 添加商品到购物车
     *
     * @param loginUser
     * @param goodsId
     * @param productId
     * @param number
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> add(UserVO loginUser, Integer goodsId, Integer productId, Integer number) {
        //判断商品是否可以购买
        GoodsEntity goodsEntity = goodsRepository.queryById(goodsId);

        if (null == goodsEntity || goodsEntity.getIsDelete() == 1 || goodsEntity.getIsOnSale() != 1) {
            return Result.error(400, "商品已下架");
        }
        //取得规格的信息,判断规格库存
        ProductVO productVO = productService.queryById(productId);
        if (null == productVO || productVO.getGoodsNumber() < number) {
            return Result.error(400, "库存不足");
        }
        //判断购物车中是否存在此规格商品
        List<CartVO> cartList = this.queryList(loginUser.getUserId(), goodsId, productId);
        CartVO cartVO = CollectionUtils.isEmpty(cartList) ? null : cartList.get(0);

        if (null == cartVO) {
            //添加操作
            //添加规格名和值
            String[] goodsSepcifitionValue = null;
            String goodsSpecificationIds = productVO.getGoodsSpecificationIds();

            if (StringUtils.isNotBlank(goodsSpecificationIds)) {
                List<Integer> idList = getSpecificationIdList(goodsSpecificationIds);
                List<GoodsSpecificationEntity> specificationEntityList = goodsSpecificationRepository.
                        queryByGoodsIdAndIds(goodsId, idList);

                goodsSepcifitionValue = new String[specificationEntityList.size()];
                for (int i = 0; i < specificationEntityList.size(); i++) {
                    goodsSepcifitionValue[i] = specificationEntityList.get(i).getValue();
                }
            }
            CartEntity cartEntity = new CartEntity();
            cartEntity.setGoodsId(goodsId);
            cartEntity.setProductId(productId);
            cartEntity.setGoodsSn(productVO.getGoodsSn());
            cartEntity.setGoodsName(goodsEntity.getName());
            cartEntity.setNumber(number);
            cartEntity.setUserId(loginUser.getUserId());
            cartEntity.setRetailPrice(productVO.getRetailPrice());
            cartEntity.setMarketPrice(productVO.getMarketPrice());
            if (null != goodsSepcifitionValue) {
                cartEntity.setGoodsSpecifitionNameValue(StringUtils.join(goodsSepcifitionValue, ";"));
            }
            cartEntity.setGoodsSpecifitionIds(goodsSpecificationIds);
            cartEntity.setChecked(1);
            cartRepository.save(cartEntity);

        } else {
            //如果已经存在购物车中，则数量增加
            Integer totalNum = number + cartVO.getNumber();
            if (productVO.getGoodsNumber() < totalNum) {
                return Result.error(400, "库存不足");
            }
            cartRepository.updateCartNum(cartVO.getId(),totalNum);
        }
        return getCart(loginUser);
    }


    private List<Integer> getSpecificationIdList(String ids) {
        if (StringUtils.isBlank(ids)) {
            return null;
        }
        List<Integer> idList = new ArrayList<>();
        String[] tempArray = ids.split("_");
        for (int i = 0; i < tempArray.length; i++) {
            idList.add(Integer.valueOf(tempArray[i]));
        }
        return idList;
    }

    /**
     * 获取购物车中的数据
     * @param loginUser
     * @return
     */
    @Override
    public Map<String,Object> getCart(UserVO loginUser){
        //查询列表数据
        List<CartVO> cartList = this.queryByUserId(loginUser.getUserId());
        //获取购物车统计信息
        Integer goodsCount = 0;
        BigDecimal goodsAmount = new BigDecimal(0.00);

        Integer checkedGoodsCount = 0;
        BigDecimal checkedGoodsAmount = new BigDecimal(0.00);

        for (CartVO cartItem : cartList) {
            goodsCount += cartItem.getNumber();
            goodsAmount = goodsAmount.add(cartItem.getRetailPrice().multiply(new BigDecimal(cartItem.getNumber())));
            if (null != cartItem.getChecked() && 1 == cartItem.getChecked()) {
                checkedGoodsCount += cartItem.getNumber();
                checkedGoodsAmount = checkedGoodsAmount.add(cartItem.getRetailPrice().multiply(new BigDecimal(cartItem.getNumber())));
            }
        }
        // 获取优惠信息提示
        List<CouponInfoVO> couponInfoList = new ArrayList();

        List<Integer> sendTypeList = new ArrayList<>();
        sendTypeList.add(CouponSentType.ORDER.getKey());
        sendTypeList.add(CouponSentType.FREE_SHIPPING.getKey());
        List<CouponVO> couponVOList = couponService.queryCouponBySendType(sendTypeList);

        if (!CollectionUtils.isEmpty(couponVOList)) {
            CouponInfoVO fullCutVo = new CouponInfoVO();
            BigDecimal fullCutDec = new BigDecimal(0);
            BigDecimal minAmount = new BigDecimal(100000);

            for (CouponVO couponVo : couponVOList) {
                BigDecimal difDec = couponVo.getMinGoodsAmount().subtract(checkedGoodsAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
                if (couponVo.getSendType() == CouponSentType.ORDER.getKey() && difDec.doubleValue() > 0.0
                        && minAmount.compareTo(couponVo.getMinGoodsAmount()) > 0) {
                    fullCutDec = couponVo.getTypeMoney();
                    minAmount = couponVo.getMinGoodsAmount();
                    fullCutVo.setType(1);
                    fullCutVo.setMsg(couponVo.getName() + "，还差" + difDec + "元");
                } else if (couponVo.getSendType() == CouponSentType.ORDER.getKey() && difDec.doubleValue() < 0.0
                        && fullCutDec.compareTo(couponVo.getTypeMoney()) < 0) {
                    fullCutDec = couponVo.getTypeMoney();
                    fullCutVo.setType(0);
                    fullCutVo.setMsg("可使用满减券" + couponVo.getName());
                }
                if (couponVo.getSendType() == CouponSentType.FREE_SHIPPING.getKey() && difDec.doubleValue() > 0.0) {
                    CouponInfoVO cpVo = new CouponInfoVO();
                    cpVo.setMsg("满￥" + couponVo.getMinAmount() + "元免配送费，还差" + difDec + "元");
                    cpVo.setType(1);
                    couponInfoList.add(cpVo);
                } else if (couponVo.getSendType() == CouponSentType.FREE_SHIPPING.getKey()) {
                    CouponInfoVO cpVo = new CouponInfoVO();
                    cpVo.setMsg("满￥" + couponVo.getMinAmount() + "元免配送费");
                    couponInfoList.add(cpVo);
                }
            }
            if (StringUtils.isNotBlank(fullCutVo.getMsg())) {
                couponInfoList.add(fullCutVo);
            }
        }
        Map<String, Object> resultObj = new HashMap();
        resultObj.put("couponInfoList", couponInfoList);
        resultObj.put("cartList", cartList);
        //
        Map<String, Object> cartTotal = new HashMap();
        cartTotal.put("goodsCount", goodsCount);
        cartTotal.put("goodsAmount", goodsAmount);
        cartTotal.put("checkedGoodsCount", checkedGoodsCount);
        cartTotal.put("checkedGoodsAmount", checkedGoodsAmount);
        //
        resultObj.put("cartTotal", cartTotal);
        return resultObj;
    }
}
