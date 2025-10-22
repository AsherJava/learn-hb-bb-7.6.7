/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.data.logic.facade.param.output;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.internal.impl.cksr.obj.CheckRecordFormInfo;
import com.jiuqi.nr.data.logic.internal.util.CheckResultUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckRecordData {
    private DimensionValueSet dimensionValueSet;
    private String formKey;
    private int status;
    private Map<Integer, Integer> checkTypeCount = new HashMap<Integer, Integer>();

    public static Map<String, CheckRecordData> initCheckRecordData(DimensionValueSet dimensionValueSet, List<CheckRecordFormInfo> formInfos) {
        HashMap<String, CheckRecordData> initMap = new HashMap<String, CheckRecordData>();
        Map<String, DimensionValue> dimensionSet = DimensionUtil.getDimensionSet(dimensionValueSet);
        List<DimensionValueSet> dimensionValueSetList = DimensionUtil.getDimensionValueSetList(dimensionSet);
        for (DimensionValueSet valueSet : dimensionValueSetList) {
            for (CheckRecordFormInfo formInfo : formInfos) {
                CheckRecordData checkRecordData = new CheckRecordData();
                checkRecordData.setDimensionValueSet(new DimensionValueSet(valueSet));
                checkRecordData.setFormKey(formInfo.getFormKey());
                checkRecordData.setStatus(1);
                for (Integer checkType : formInfo.getExeFmlAllCheckTypes()) {
                    checkRecordData.getCheckTypeCount().put(checkType, 0);
                }
                initMap.put(checkRecordData.toString(), checkRecordData);
            }
        }
        return initMap;
    }

    public String toString() {
        return CheckResultUtil.buildCheckRecordMapKey(this.formKey, this.dimensionValueSet);
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<Integer, Integer> getCheckTypeCount() {
        return this.checkTypeCount;
    }

    public void setCheckTypeCount(Map<Integer, Integer> checkTypeCount) {
        this.checkTypeCount = checkTypeCount;
    }
}

