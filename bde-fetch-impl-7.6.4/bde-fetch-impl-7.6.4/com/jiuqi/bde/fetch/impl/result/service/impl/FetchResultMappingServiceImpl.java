/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.fetch.impl.result.service.impl;

import com.jiuqi.bde.fetch.impl.result.dao.FetchResultMappingDao;
import com.jiuqi.bde.fetch.impl.result.entity.FetchResultMappingEO;
import com.jiuqi.bde.fetch.impl.result.service.FetchResultMappingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FetchResultMappingServiceImpl
implements FetchResultMappingService {
    @Autowired
    private FetchResultMappingDao fetchResultMappingDao;

    @Override
    public List<Integer> getRouteNumber() {
        return this.fetchResultMappingDao.getRouteNumber();
    }

    @Override
    public FetchResultMappingEO getMappingEOByRouteNum(Integer routeNum) {
        return this.fetchResultMappingDao.getMappingEOByRouteNum(routeNum);
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public int changeRouteStart(Integer routeNum) {
        return this.fetchResultMappingDao.changeRouteStart(routeNum);
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public int changeRouteLock(Integer routeNum) {
        return this.fetchResultMappingDao.changeRouteLock(routeNum);
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public int changeRouteStop(Integer routeNum) {
        return this.fetchResultMappingDao.changeRouteStop(routeNum);
    }

    @Override
    public int updateRouteStatus(FetchResultMappingEO fetchResultMappingEO) {
        return this.fetchResultMappingDao.updateRouteStatus(fetchResultMappingEO);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void insertFetchResultMapping(List<FetchResultMappingEO> fetchResultMappingEOList) {
        this.fetchResultMappingDao.insertFetchResultMapping(fetchResultMappingEOList);
    }
}

