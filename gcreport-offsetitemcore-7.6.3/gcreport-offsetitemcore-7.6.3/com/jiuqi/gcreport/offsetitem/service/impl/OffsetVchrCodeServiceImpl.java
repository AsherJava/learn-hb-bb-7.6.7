/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.offsetitem.service.impl;

import com.jiuqi.gcreport.offsetitem.dao.OffsetVchrCodeDao;
import com.jiuqi.gcreport.offsetitem.entity.GcOffsetVchrFlowEO;
import com.jiuqi.gcreport.offsetitem.service.OffsetVchrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OffsetVchrCodeServiceImpl
implements OffsetVchrCodeService {
    @Autowired
    private OffsetVchrCodeDao vchrCodeDao;

    @Override
    public GcOffsetVchrFlowEO getFlowNumberByDimensions(String dimensions) {
        return this.vchrCodeDao.getFlowNumberByDimensions(dimensions);
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public long updateFlowNumberByDimensions(String dimensions, int n) {
        GcOffsetVchrFlowEO vchrFlowEO = new GcOffsetVchrFlowEO();
        vchrFlowEO.setDimensions(dimensions);
        vchrFlowEO.setFlowNumber(Long.valueOf(n));
        if (this.vchrCodeDao.updateFlow(vchrFlowEO) > 0) {
            GcOffsetVchrFlowEO newflowEO = this.getFlowNumberByDimensions(dimensions);
            return newflowEO.getFlowNumber();
        }
        this.vchrCodeDao.save(vchrFlowEO);
        return n;
    }

    @Override
    public long reUpdateFlowNumberByDimensions(String dimensions, int n) {
        GcOffsetVchrFlowEO vchrFlowEO = new GcOffsetVchrFlowEO();
        vchrFlowEO.setDimensions(dimensions);
        vchrFlowEO.setFlowNumber(Long.valueOf(n));
        if (this.vchrCodeDao.updateFlow(vchrFlowEO) == 1) {
            GcOffsetVchrFlowEO newflowEO = this.getFlowNumberByDimensions(dimensions);
            return newflowEO.getFlowNumber();
        }
        throw new RuntimeException("\u5e76\u53d1\u66f4\u65b0\u62b5\u9500\u5206\u5f55\u7f16\u53f7\u5931\u8d25");
    }
}

