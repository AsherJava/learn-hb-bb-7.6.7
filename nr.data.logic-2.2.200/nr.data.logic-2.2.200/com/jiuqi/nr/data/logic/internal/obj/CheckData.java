/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.obj;

import com.jiuqi.nr.data.logic.facade.param.output.CheckRecordData;
import com.jiuqi.nr.data.logic.internal.entity.FmlCheckResultEntity;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckData {
    private final String executeId;
    private final AtomicInteger errorCount = new AtomicInteger(0);
    private final Vector<CheckRecordData> checkRecordData = new Vector();
    private final Vector<FmlCheckResultEntity> checkResultEntities = new Vector();

    public CheckData(String executeId) {
        this.executeId = executeId;
    }

    public String getExecuteId() {
        return this.executeId;
    }

    public AtomicInteger getErrorCount() {
        return this.errorCount;
    }

    public Vector<CheckRecordData> getCheckRecordData() {
        return this.checkRecordData;
    }

    public Vector<FmlCheckResultEntity> getCheckResultEntities() {
        return this.checkResultEntities;
    }
}

