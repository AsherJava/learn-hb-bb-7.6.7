/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.budcustom.service.impl;

import com.jiuqi.gcreport.definition.impl.basic.init.table.budcustom.dao.DimPublishManageDao;
import com.jiuqi.gcreport.definition.impl.basic.init.table.budcustom.service.DimPublishManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DimPublishManageServiceImpl
implements DimPublishManageService {
    @Autowired
    private DimPublishManageDao dimPublishManageDao;

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void insertPublishInfo(String id, String modelCode) {
        this.dimPublishManageDao.insertPublishInfo(id, modelCode);
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public int updatePublishInfo(String id) {
        return this.dimPublishManageDao.updatePublishInfo(id);
    }
}

