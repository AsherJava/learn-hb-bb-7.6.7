/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.reportparam.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.reportparam.dao.GcReportParamLockDao;
import com.jiuqi.gcreport.reportparam.eo.GcReportParamLockEO;
import com.jiuqi.gcreport.reportparam.service.GcReportParamInitLockService;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcReportParamInitLockServiceImpl
implements GcReportParamInitLockService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final Long LOCK_TIME = 3600000L;
    @Autowired
    private GcReportParamLockDao gcReportParamLockDao;

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public boolean lock() {
        List gcReportParamLockEOS = this.gcReportParamLockDao.loadAll();
        String userName = NpContextHolder.getContext().getUserName();
        if (CollectionUtils.isEmpty((Collection)gcReportParamLockEOS)) {
            GcReportParamLockEO eo = new GcReportParamLockEO();
            eo.setId(UUIDUtils.emptyUUIDStr());
            eo.setLockUser(userName);
            eo.setLockTime(new Date());
            eo.setLocked(1);
            try {
                return this.gcReportParamLockDao.add((BaseEntity)eo) == 1;
            }
            catch (Exception e) {
                this.logger.error("\u521d\u59cb\u5316\u62a5\u8868\u53c2\u6570\u52a0\u9501\u5931\u8d25\uff1a" + e.getMessage(), e);
                return false;
            }
        }
        GcReportParamLockEO dbEo = (GcReportParamLockEO)((Object)gcReportParamLockEOS.get(0));
        if (!Integer.valueOf(1).equals(dbEo.getLocked())) {
            return this.gcReportParamLockDao.updateLocked(userName, dbEo.getLockTime()) == 1;
        }
        if (System.currentTimeMillis() - dbEo.getLockTime().getTime() > LOCK_TIME) {
            return this.gcReportParamLockDao.updateLocked(userName, dbEo.getLockTime()) == 1;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void unLock() {
        this.gcReportParamLockDao.unLock();
    }
}

