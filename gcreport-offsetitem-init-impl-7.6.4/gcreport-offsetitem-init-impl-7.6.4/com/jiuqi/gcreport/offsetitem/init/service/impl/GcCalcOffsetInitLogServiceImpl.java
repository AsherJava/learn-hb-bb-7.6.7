/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.offsetitem.init.service.impl;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.offsetitem.init.dao.GcOffsetInitLogInfoDao;
import com.jiuqi.gcreport.offsetitem.init.entity.GcOffsetInitLogInfoEO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcCalcOffsetInitLogServiceImpl {
    @Autowired
    private GcOffsetInitLogInfoDao gcOffsetInitLogInfoDao;
    public static final List<Integer> initOffsetSrcTypeList = new ArrayList<Integer>(){
        {
            this.add(new Integer(5));
            this.add(new Integer(6));
            this.add(new Integer(21));
            this.add(new Integer(22));
            this.add(new Integer(24));
            this.add(new Integer(33));
            this.add(new Integer(34));
        }
    };

    @Transactional(readOnly=true, rollbackFor={Exception.class})
    public GcOffsetInitLogInfoEO queryCalcLogEO(String orgCode, String acctYear) {
        List<GcOffsetInitLogInfoEO> calcLogInfoEO = this.gcOffsetInitLogInfoDao.queryLogInfoByCurrDimension(acctYear, orgCode);
        if (calcLogInfoEO != null && calcLogInfoEO.size() > 0) {
            return calcLogInfoEO.get(0);
        }
        return null;
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public GcOffsetInitLogInfoEO insertOrUpdateCalcLogEO(String orgCode, String acctYear, String logInfo) {
        GcOffsetInitLogInfoEO entity;
        GcOffsetInitLogInfoEO historyLogEo = this.queryCalcLogEO(orgCode, acctYear);
        if (historyLogEo == null) {
            entity = new GcOffsetInitLogInfoEO();
            entity.setId(UUIDUtils.newUUIDStr());
        } else {
            entity = historyLogEo;
        }
        entity.setAcctYear(Integer.parseInt(acctYear));
        entity.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
        entity.setLoginfo(logInfo);
        entity.setOrgCode(orgCode);
        if (historyLogEo == null) {
            this.gcOffsetInitLogInfoDao.save(entity);
        } else {
            this.gcOffsetInitLogInfoDao.update((BaseEntity)entity);
        }
        return entity;
    }
}

