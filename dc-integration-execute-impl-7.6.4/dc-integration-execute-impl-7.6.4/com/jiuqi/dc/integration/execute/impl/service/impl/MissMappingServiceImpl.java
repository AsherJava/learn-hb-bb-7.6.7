/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Table
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  org.springframework.transaction.annotation.Propagation
 */
package com.jiuqi.dc.integration.execute.impl.service.impl;

import com.google.common.collect.Table;
import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.dc.integration.execute.impl.data.DataConvertDim;
import com.jiuqi.dc.integration.execute.impl.missmapping.dao.MissMappingDao;
import com.jiuqi.dc.integration.execute.impl.service.MissMappingService;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

@Service
public class MissMappingServiceImpl
implements MissMappingService {
    @Autowired
    private MissMappingDao missMappingDao;

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void insertMissMappingData(Table<String, String, Set<String>> missMappingTable, DataConvertDim convertDim, DataSchemeDTO dataSchemeDTO, String taskType) {
        this.missMappingDao.insertMissMappingData(missMappingTable, convertDim, dataSchemeDTO, taskType);
    }
}

