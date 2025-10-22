/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.datachange.service;

import com.jiuqi.nr.system.check.datachange.bean.DataChangeRecord;
import com.jiuqi.nr.system.check.datachange.bean.RepairParam;
import com.jiuqi.nr.system.check.datachange.provider.DataChangeExecutor;
import com.jiuqi.nr.system.check.datachange.service.DataSchemeRepairRecordService;
import com.jiuqi.nr.system.check.datachange.service.IDataChangeRecordService;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataChangeService {
    @Autowired
    private List<DataChangeExecutor> dataChangeExecutor;
    @Autowired
    private IDataChangeRecordService dataChangeRecordService;
    @Autowired
    private DataSchemeRepairRecordService dataSchemeRepairRecordService;

    public void doDataChange(RepairParam repairParam) {
        this.dataChangeExecutor.stream().sorted(Comparator.comparing(DataChangeExecutor::getOrder)).forEach(a -> {
            List<DataChangeRecord> executeRecord = a.execute(repairParam);
            this.dataChangeRecordService.insertRecords(executeRecord);
        });
    }
}

