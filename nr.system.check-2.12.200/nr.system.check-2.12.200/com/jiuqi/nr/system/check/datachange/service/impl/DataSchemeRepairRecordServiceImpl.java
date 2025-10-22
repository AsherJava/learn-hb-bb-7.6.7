/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.datachange.service.impl;

import com.jiuqi.nr.system.check.datachange.bean.DataSchemeRepairRecord;
import com.jiuqi.nr.system.check.datachange.dao.DataSchemeRepairRecordDao;
import com.jiuqi.nr.system.check.datachange.service.DataSchemeRepairRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSchemeRepairRecordServiceImpl
implements DataSchemeRepairRecordService {
    @Autowired
    private DataSchemeRepairRecordDao dataSchemeRepairRecordDao;

    @Override
    public void insertRepairRecord(DataSchemeRepairRecord dataSchemeRepairRecord) {
        this.dataSchemeRepairRecordDao.insert(dataSchemeRepairRecord);
    }

    @Override
    public DataSchemeRepairRecord getDataSchemeRepairRecord(String dataSchemeKey, String repairType) {
        return this.dataSchemeRepairRecordDao.getRecordByKey(dataSchemeKey, repairType);
    }

    @Override
    public DataSchemeRepairRecord getLastRepairRecord(String repairType) {
        return this.dataSchemeRepairRecordDao.getLastRecord(repairType);
    }

    @Override
    public void updateRepairRecord(DataSchemeRepairRecord dataSchemeRepairRecord) {
        this.dataSchemeRepairRecordDao.updateRepairRecord(dataSchemeRepairRecord);
    }

    @Override
    public void repairComplete(String dataSchemeKey, String repairType) {
        this.dataSchemeRepairRecordDao.repairComplete(dataSchemeKey, repairType);
    }
}

