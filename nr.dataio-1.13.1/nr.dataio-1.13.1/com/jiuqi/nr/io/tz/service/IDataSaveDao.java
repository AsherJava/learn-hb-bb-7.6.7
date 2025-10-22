/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.tz.service;

import com.jiuqi.nr.io.tz.TzParams;

public interface IDataSaveDao {
    public void handleAddStateData(TzParams var1);

    public void handleDelStateData2His(TzParams var1);

    public void handleDelStateData(TzParams var1);

    public void handleUpdateRecordStateData2His(TzParams var1);

    public void handleUpdateRecordStateData(TzParams var1);

    public void handleUpdateNotRecordStateData(TzParams var1);

    public void delStateRptData(TzParams var1);

    public void delStateRptDataByRptBizKeyOrder(TzParams var1);

    public void handleAddAllRptData(TzParams var1);

    public void handleRptAddStateData(TzParams var1);

    public void handleRptUpdateStateData(TzParams var1);

    public void delRptUpdateStateData(TzParams var1);

    public void handleRptBizKeyOrderIsNUllData(TzParams var1);
}

