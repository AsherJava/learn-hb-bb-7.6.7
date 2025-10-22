/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.incrementCollect;

import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import java.util.List;

public class FcIncrementCollectParam {
    private List<GcRelatedItemEO> incrementItems;
    private List<GcRelatedItemEO> sourceNotExistData;
    private StringBuilder log;
    private boolean doCheckData;

    public List<GcRelatedItemEO> getIncrementItems() {
        return this.incrementItems;
    }

    public void setIncrementItems(List<GcRelatedItemEO> incrementItems) {
        this.incrementItems = incrementItems;
    }

    public StringBuilder getLog() {
        return this.log;
    }

    public void setLog(StringBuilder log) {
        this.log = log;
    }

    public List<GcRelatedItemEO> getSourceNotExistData() {
        return this.sourceNotExistData;
    }

    public void setSourceNotExistData(List<GcRelatedItemEO> sourceNotExistData) {
        this.sourceNotExistData = sourceNotExistData;
    }

    public boolean isDoCheckData() {
        return this.doCheckData;
    }

    public void setDoCheckData(boolean doCheckData) {
        this.doCheckData = doCheckData;
    }

    private FcIncrementCollectParam(List<GcRelatedItemEO> incrementItems, List<GcRelatedItemEO> sourceNotExistData, boolean doCheckData) {
        this.incrementItems = incrementItems;
        this.sourceNotExistData = sourceNotExistData;
        this.doCheckData = doCheckData;
        this.log = new StringBuilder();
    }

    public static FcIncrementCollectParam newInstance(List<GcRelatedItemEO> incrementItems, List<GcRelatedItemEO> sourceNotExistData) {
        return new FcIncrementCollectParam(incrementItems, sourceNotExistData, false);
    }

    public static FcIncrementCollectParam newInstance(List<GcRelatedItemEO> incrementItems, List<GcRelatedItemEO> sourceNotExistData, boolean doCheckData) {
        return new FcIncrementCollectParam(incrementItems, sourceNotExistData, doCheckData);
    }
}

