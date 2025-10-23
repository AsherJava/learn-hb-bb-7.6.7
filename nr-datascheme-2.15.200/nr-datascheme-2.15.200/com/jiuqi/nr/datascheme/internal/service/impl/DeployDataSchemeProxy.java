/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.internal.dao.IDataDimDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.entity.DataDimDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataDimDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class DeployDataSchemeProxy {
    @Autowired
    private IDataDimDao<DesignDataDimDO> dataDimDao;
    @Autowired
    private IDataDimDao<DataDimDO> rtDataDimDao;
    @Autowired
    private IDataFieldDao<DesignDataFieldDO> dataFieldDao;
    @Autowired
    private IDataTableDao<DesignDataTableDO> dataTableDao;

    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void update(Set<DesignDataDimDO> designDataDimDOS, List<DesignDataFieldDO> addFields, List<DesignDataTableDO> updateTables) {
        if (!designDataDimDOS.isEmpty()) {
            this.dataDimDao.batchInsert(new ArrayList<DesignDataDimDO>(designDataDimDOS));
            this.rtDataDimDao.batchInsert(new ArrayList<DesignDataDimDO>(designDataDimDOS));
        }
        if (!CollectionUtils.isEmpty(addFields)) {
            this.dataFieldDao.batchInsert(addFields);
        }
        if (!CollectionUtils.isEmpty(updateTables)) {
            this.dataTableDao.batchUpdate(updateTables);
        }
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void update(Set<DesignDataDimDO> designDataDimDOS, List<DesignDataFieldDO> addFields, List<DesignDataFieldDO> updateFields, List<DesignDataTableDO> updateTables) {
        if (!CollectionUtils.isEmpty(designDataDimDOS)) {
            this.dataDimDao.batchInsert(new ArrayList<DesignDataDimDO>(designDataDimDOS));
        }
        if (!CollectionUtils.isEmpty(addFields)) {
            this.dataFieldDao.batchInsert(addFields);
        }
        if (!CollectionUtils.isEmpty(updateFields)) {
            this.dataFieldDao.batchUpdate(updateFields);
        }
        if (!CollectionUtils.isEmpty(updateTables)) {
            this.dataTableDao.batchUpdate(updateTables);
        }
    }
}

