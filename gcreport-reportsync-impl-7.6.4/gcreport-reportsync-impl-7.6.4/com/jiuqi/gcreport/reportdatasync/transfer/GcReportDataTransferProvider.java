/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.io.datacheck.param.TransferParam
 *  com.jiuqi.nr.io.service.IDataTransfer
 *  com.jiuqi.nr.io.service.IDataTransferProvider
 *  com.jiuqi.nr.io.service.impl.DefaultDataTransferProvider
 */
package com.jiuqi.gcreport.reportdatasync.transfer;

import com.jiuqi.gcreport.reportdatasync.transfer.GcReportTransfer;
import com.jiuqi.gcreport.reportdatasync.transfer.GcTaskOptionTransfer;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.io.datacheck.param.TransferParam;
import com.jiuqi.nr.io.service.IDataTransfer;
import com.jiuqi.nr.io.service.IDataTransferProvider;
import com.jiuqi.nr.io.service.impl.DefaultDataTransferProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcReportDataTransferProvider
extends DefaultDataTransferProvider
implements IDataTransferProvider {
    @Autowired
    private ITaskOptionController taskOptionController;

    public IDataTransfer getDataTransfer(TransferParam param) {
        String value = this.taskOptionController.getValue(param.getTaskKey(), "IllegalDataImport_2132");
        if (value.equals("0")) {
            return new GcReportTransfer(this, param);
        }
        return new GcTaskOptionTransfer(this, param);
    }
}

