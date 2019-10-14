package com.snowman.mymall.service.impl;

import com.snowman.mymall.entity.SearchHistoryEntity;
import com.snowman.mymall.repository.SearchHistoryRepository;
import com.snowman.mymall.service.SearchHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 17:13
 * @Version 1.0
 **/
@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {

    private Logger logger = LoggerFactory.getLogger(SearchHistoryService.class);

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    /**
     * 保存搜索历史
     * @param entity
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSearchHistory(SearchHistoryEntity entity) {
        searchHistoryRepository.save(entity);
    }
}
