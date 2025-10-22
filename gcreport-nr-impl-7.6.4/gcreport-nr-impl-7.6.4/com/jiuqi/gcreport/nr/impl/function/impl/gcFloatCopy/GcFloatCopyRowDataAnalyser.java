/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.np.dataengine.intf.IDataRow
 */
package com.jiuqi.gcreport.nr.impl.function.impl.gcFloatCopy;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.np.dataengine.intf.IDataRow;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class GcFloatCopyRowDataAnalyser {
    private Map<IDataRow, IDataRow> updatedDatas;
    private List<IDataRow> unMatchedDestDatas = CollectionUtils.newArrayList();
    private List<IDataRow> unMatchedSrcDatas;

    public List<IDataRow> getUnMatchedSrcDatas() {
        return this.unMatchedSrcDatas;
    }

    public GcFloatCopyRowDataAnalyser(Map<String, IDataRow> srcRowDatas, Map<String, List<IDataRow>> destRowDatas) {
        destRowDatas.values().forEach(d -> this.unMatchedDestDatas.addAll((Collection<IDataRow>)d));
        this.unMatchedSrcDatas = CollectionUtils.newArrayList();
        this.updatedDatas = CollectionUtils.newHashMap();
        this.analyse(srcRowDatas, destRowDatas);
    }

    public void analyse(Map<String, IDataRow> srcRowDatas, Map<String, List<IDataRow>> destRowDatas) {
        for (Map.Entry<String, IDataRow> entry : srcRowDatas.entrySet()) {
            List<IDataRow> matchedData = this.getSameKeyDestRow(entry.getKey(), destRowDatas);
            if (matchedData != null && matchedData.size() > 0) {
                for (IDataRow row : matchedData) {
                    this.unMatchedDestDatas.remove(row);
                    this.updatedDatas.put(row, entry.getValue());
                }
                continue;
            }
            this.unMatchedSrcDatas.add(entry.getValue());
        }
        for (Map.Entry<String, Object> entry : destRowDatas.entrySet()) {
            if (srcRowDatas.containsKey(entry.getKey())) continue;
            ((List)entry.getValue()).forEach(iDataRow -> this.updatedDatas.put((IDataRow)iDataRow, (IDataRow)null));
        }
    }

    public List<IDataRow> getSameKeyDestRow(String toCheck, Map<String, List<IDataRow>> destRowDatas) {
        if (destRowDatas == null || destRowDatas.size() <= 0) {
            return null;
        }
        return destRowDatas.get(toCheck);
    }

    public List<IDataRow> getUnMatchedDestDatas() {
        return this.unMatchedDestDatas;
    }

    public Map<IDataRow, IDataRow> getUpdatedDatas() {
        return this.updatedDatas;
    }
}

