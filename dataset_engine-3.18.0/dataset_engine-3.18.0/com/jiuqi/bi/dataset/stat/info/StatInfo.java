/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Metadata
 */
package com.jiuqi.bi.dataset.stat.info;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Metadata;
import java.util.List;
import java.util.Map;

public final class StatInfo {
    public int sys_timekey_index = -1;
    public List<Integer> parentKeyColIdx;
    public List<Integer> parentColIdx;
    public List<Integer> reserveParentColIdx;
    public List<Integer> destDimColIdx;
    public Map<Integer, Integer> dest2srcColMap;
    public List<Integer> srcKeyColIdx;
    public List<Integer> destKeyColIdx;
    public List<Integer> destMsColIdx;
    public Metadata<BIDataSetFieldInfo> metadata;
    public boolean isTimeWasAggr;
}

