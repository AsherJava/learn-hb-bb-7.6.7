/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.xlib.utils.StringUtils
 */
package com.jiuqi.nr.form.analysis.dto;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.xlib.utils.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DestDimsParamDTO {
    private String orgEntityId;
    private String periodEntityId;
    private List<String> dimEntityIds;
    private DimensionValueSet dimValueSet;
    private Map<String, String> entityDatasMap;
    private List<String> orgEntityRowIds;
    private Map<String, String> dimNameMap;

    public DestDimsParamDTO(String orgEntityId, String periodEntityId, List<String> dimEntityIds, DimensionValueSet dimValueSet, Map<String, String> dimNameMap, List<String> orgEntityRowIds) {
        this.orgEntityId = orgEntityId;
        this.periodEntityId = periodEntityId;
        this.dimEntityIds = dimEntityIds;
        this.dimValueSet = dimValueSet;
        this.dimNameMap = dimNameMap;
        this.orgEntityRowIds = orgEntityRowIds;
        this.entityDatasMap = new HashMap<String, String>();
        for (Map.Entry<String, String> e : dimNameMap.entrySet()) {
            this.entityDatasMap.put(e.getKey(), (String)dimValueSet.getValue(e.getValue()));
        }
    }

    public String getOrgEntityId() {
        return this.orgEntityId;
    }

    public String getPeriodEntityId() {
        return this.periodEntityId;
    }

    public List<String> getDimEntityIds() {
        return this.dimEntityIds;
    }

    public DimensionValueSet getDimValueSet(String rowId) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet(this.dimValueSet);
        if (StringUtils.hasText((String)rowId)) {
            dimensionValueSet.setValue(this.dimNameMap.get(this.orgEntityId), (Object)rowId);
        }
        return dimensionValueSet;
    }

    public Map<String, String> getEntityDatasMap(String rowId) {
        HashMap<String, String> hashMap = new HashMap<String, String>(this.entityDatasMap);
        if (StringUtils.hasText((String)rowId)) {
            hashMap.put(this.orgEntityId, rowId);
        }
        return hashMap;
    }

    public List<String> getOrgEntityRowIds() {
        return this.orgEntityRowIds;
    }

    public Map<String, String> getDimNameMap() {
        return this.dimNameMap;
    }
}

