/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.domain.Page
 */
package com.jiuqi.nr.io.record.service.impl;

import com.jiuqi.np.user.domain.Page;
import com.jiuqi.nr.io.record.bean.UnitFailureRecord;
import com.jiuqi.nr.io.record.bean.UnitFailureSubRecord;
import com.jiuqi.nr.io.record.dao.UnitFailureRecordDao;
import com.jiuqi.nr.io.record.dao.UnitFailureSubRecordDao;
import com.jiuqi.nr.io.record.service.UnitFailureService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitFailureServiceImpl
implements UnitFailureService {
    @Autowired
    private UnitFailureRecordDao recordDao;
    @Autowired
    private UnitFailureSubRecordDao subRecordDao;

    @Override
    public void saveFailureRecords(List<UnitFailureRecord> records) {
        this.recordDao.batchInsert(records);
    }

    @Override
    public void saveFailureSubRecords(List<UnitFailureSubRecord> subRecords) {
        this.subRecordDao.batchInsert(subRecords);
    }

    @Override
    public Page<UnitFailureRecord> queryFailureRecords(String recKey, int page, int size) {
        return this.recordDao.queryFailureRecords(recKey, page, size);
    }

    @Override
    public Page<UnitFailureRecord> queryFailureRecordsByFactory(String recKey, String factoryId, int page, int size) {
        return this.recordDao.queryFailureRecordsByFactory(recKey, factoryId, page, size);
    }
}

