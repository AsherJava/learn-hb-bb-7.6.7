/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.datachange.service.impl;

import com.jiuqi.nr.system.check.datachange.bean.DataChangeRecord;
import com.jiuqi.nr.system.check.datachange.dao.IDataChangeRecordDao;
import com.jiuqi.nr.system.check.datachange.service.IDataChangeRecordService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DataChangeRecordServiceImpl
implements IDataChangeRecordService {
    @Autowired
    private IDataChangeRecordDao dataChangeRecordDao;

    @Override
    public void insert(DataChangeRecord dataChangeRecord) {
        if (dataChangeRecord == null) {
            return;
        }
        this.dataChangeRecordDao.insert(dataChangeRecord);
    }

    @Override
    public void insertRecords(List<DataChangeRecord> dataChangeRecords) {
        if (CollectionUtils.isEmpty(dataChangeRecords)) {
            return;
        }
        this.dataChangeRecordDao.insert(dataChangeRecords);
    }

    @Override
    public List<DataChangeRecord> getRecordsByRecordType(String recordType) {
        if (recordType == null || recordType.isEmpty()) {
            return new ArrayList<DataChangeRecord>();
        }
        return this.dataChangeRecordDao.get(recordType);
    }
}

