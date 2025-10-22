/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.etl.automation;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DataTableAutomationQueryContext
implements IContext {
    private String dataTableKey;
    private String dataSchemeKey;
    private UnitEntity unitEntity;
    private String maxDate;
    private Boolean isSingleQuery;
    private Boolean isCurrencyID;
    private Boolean isEnableAuthority;
    private Date startDate;
    private Date endDate;
    private DataTable dataTable;
    private DimensionValueSet dimensionValueSet;
    private IPeriodEntity iPeriodEntity;
    private List<String> singleFields;
    private Map<String, String> filedDimensionMap;

    public UnitEntity getUnitEntity() {
        return this.unitEntity;
    }

    public void setUnitEntity(UnitEntity unitEntity) {
        this.unitEntity = unitEntity;
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getMaxDate() {
        return this.maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    public Boolean getSingleQuery() {
        return this.isSingleQuery;
    }

    public void setSingleQuery(Boolean singleQuery) {
        this.isSingleQuery = singleQuery;
    }

    public Boolean getCurrencyID() {
        return this.isCurrencyID;
    }

    public void setCurrencyID(Boolean currencyID) {
        this.isCurrencyID = currencyID;
    }

    public Boolean getEnableAuthority() {
        return this.isEnableAuthority;
    }

    public void setEnableAuthority(Boolean enableAuthority) {
        this.isEnableAuthority = enableAuthority;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public DataTable getDataTable() {
        return this.dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public IPeriodEntity getiPeriodEntity() {
        return this.iPeriodEntity;
    }

    public void setiPeriodEntity(IPeriodEntity iPeriodEntity) {
        this.iPeriodEntity = iPeriodEntity;
    }

    public List<String> getSingleFields() {
        return this.singleFields;
    }

    public void setSingleFields(List<String> singleFields) {
        this.singleFields = singleFields;
    }

    public Map<String, String> getFiledDimensionMap() {
        return this.filedDimensionMap;
    }

    public void setFiledDimensionMap(Map<String, String> filedDimensionMap) {
        this.filedDimensionMap = filedDimensionMap;
    }

    public DataTableAutomationQueryContext(String dataTableKey, String dataSchemeKey, UnitEntity unitEntity, String maxDate, Boolean isSingleQuery, Boolean isCurrencyID, Boolean isEnableAuthority) {
        this.dataTableKey = dataTableKey;
        this.dataSchemeKey = dataSchemeKey;
        this.unitEntity = unitEntity;
        this.maxDate = maxDate;
        this.isSingleQuery = isSingleQuery;
        this.isCurrencyID = isCurrencyID;
        this.isEnableAuthority = isEnableAuthority;
    }

    static class UnitEntity {
        private String entityId;
        private String entityIdL;
        private String unitDimensionName;

        public UnitEntity() {
        }

        public UnitEntity(String entityId, String entityIdL, String unitDimensionName) {
            this.entityId = entityId;
            this.entityIdL = entityIdL;
            this.unitDimensionName = unitDimensionName;
        }

        public String getEntityId() {
            return this.entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getEntityIdL() {
            return this.entityIdL;
        }

        public void setEntityIdL(String entityIdL) {
            this.entityIdL = entityIdL;
        }

        public String getUnitDimensionName() {
            return this.unitDimensionName;
        }

        public void setUnitDimensionName(String unitDimensionName) {
            this.unitDimensionName = unitDimensionName;
        }
    }
}

