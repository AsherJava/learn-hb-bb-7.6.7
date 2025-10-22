/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gc.inspector.domain.InspectResultVO
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 */
package com.jiuqi.gcreport.inputdata.inputdata.inputdatatabledefine.inspect.dto;

import com.jiuqi.gc.inspector.domain.InspectResultVO;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import java.util.List;
import java.util.Map;

public class InputDataInspectResultDTO {
    private InspectResultVO inspectResultVO;
    private Map<DesignDataTable, List<DBIndex>> dbIndexGroupByDataTable;
    private List<String> dataSchemeKeys;

    public InspectResultVO getInspectResultVO() {
        return this.inspectResultVO;
    }

    public void setInspectResultVO(InspectResultVO inspectResultVO) {
        this.inspectResultVO = inspectResultVO;
    }

    public Map<DesignDataTable, List<DBIndex>> getDbIndexGroupByDataTable() {
        return this.dbIndexGroupByDataTable;
    }

    public void setDbIndexGroupByDataTable(Map<DesignDataTable, List<DBIndex>> dbIndexGroupByDataTable) {
        this.dbIndexGroupByDataTable = dbIndexGroupByDataTable;
    }

    public List<String> getDataSchemeKeys() {
        return this.dataSchemeKeys;
    }

    public void setDataSchemeKeys(List<String> dataSchemeKeys) {
        this.dataSchemeKeys = dataSchemeKeys;
    }
}

