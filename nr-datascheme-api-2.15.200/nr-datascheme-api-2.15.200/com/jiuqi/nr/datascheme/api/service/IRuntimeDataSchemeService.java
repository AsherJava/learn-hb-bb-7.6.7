/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DataTableRel;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.core.DataSchemeDeployStatus;
import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface IRuntimeDataSchemeService {
    public DataScheme getDataScheme(String var1);

    public DataScheme getDataSchemeByCode(String var1);

    public List<DataScheme> getDataSchemeByParent(String var1);

    public List<DataScheme> getDataSchemeByPeriodType(PeriodType var1);

    public List<DataScheme> getDataSchemeByDimKey(String ... var1);

    public List<DataScheme> getDataSchemes(List<String> var1);

    public List<DataScheme> getAllDataScheme();

    public DataGroup getDataGroup(String var1);

    public List<DataGroup> getDataGroups(List<String> var1);

    public List<DataGroup> getAllDataGroup(String var1);

    public List<DataGroup> getDataGroupByScheme(String var1);

    public List<DataGroup> getDataGroupByParent(String var1);

    public DataTable getDataTable(String var1);

    public DataTable getDataTableByCode(String var1);

    public DataTable getLatestDataTableByScheme(String var1);

    public List<DataTable> getLatestDataTablesByScheme(String var1);

    public Instant getLatestDataTableUpdateTime(String var1);

    public DataTable getFmDataTableBySchemeAndDimKey(String var1, String var2);

    public List<DataTable> getFmDataTableBySchemeKey(String var1);

    public List<DataTable> getDataTables(List<String> var1);

    public List<DataTable> getAllDataTable(String var1);

    public List<DataTable> getAllDataTableBySchemeAndTypes(String var1, DataTableType ... var2);

    public List<DataTable> getDataTableByGroup(String var1);

    public List<DataTable> getDataTableByScheme(String var1);

    default public DataTable getDataTableForMdInfo(String schemeKey) {
        return this.getAllDataTableBySchemeAndTypes(schemeKey, DataTableType.MD_INFO).stream().findAny().orElse(null);
    }

    public DataField getDataField(String var1);

    public DataField getDataFieldByColumnKey(String var1);

    public DataField getDataFieldByTableKeyAndCode(String var1, String var2);

    public DataField getZbKindDataFieldBySchemeKeyAndCode(String var1, String var2);

    public List<DataField> getFmKindDataFieldsBySchemeAndKeys(String var1, String var2);

    public List<DataField> getFmKindDataFieldsBySchemeAndDimKey(String var1, String var2);

    public List<DataField> getDataFieldByTableCode(String var1);

    public List<DataField> getBizDataFieldByTableKey(String var1);

    public List<DataField> getBizDataFieldByTableCode(String var1);

    public List<DataField> getDataFieldByTableKeyAndType(String var1, DataFieldType ... var2);

    public List<DataField> getDataFieldByTableKeyAndKind(String var1, DataFieldKind ... var2);

    public List<DataField> getDataFieldByTableCodeAndType(String var1, DataFieldType ... var2);

    public List<DataField> getDataFieldByTableCodeAndKind(String var1, DataFieldKind ... var2);

    public List<DataField> getDataFields(List<String> var1);

    default public List<DataField> getDataFields(String dataSchemeKey, List<String> keys) {
        return this.getDataFields(keys);
    }

    public List<DataField> getAllDataField(String var1);

    public List<DataField> getAllDataFieldByKind(String var1, DataFieldKind ... var2);

    public List<DataField> getDataFieldByTable(String var1);

    public List<DataField> getDataFieldByTables(List<String> var1);

    public List<DataDimension> getDataSchemeDimension(String var1);

    public List<DataDimension> getDataSchemeDimension(String var1, DimensionType var2);

    public List<DataDimension> getDataDimensionByDimKey(String var1);

    public List<DataDimension> getReportDimension(String var1);

    @NonNull
    public Boolean enableAdjustPeriod(String var1);

    @Nullable
    public String getDimAttributeByReportDim(String var1, String var2);

    @Deprecated
    public Map<String, DeployStatusEnum> getDataSchemeDeployStatus(String[] var1);

    default public Map<String, DeployStatusEnum> getDataSchemeDeployStatus(List<String> dataSchemeKeys) {
        if (null == dataSchemeKeys) {
            return Collections.emptyMap();
        }
        return this.getDataSchemeDeployStatus(dataSchemeKeys.toArray(new String[0]));
    }

    public DeployStatusEnum getDataSchemeDeployStatus(String var1);

    public DataSchemeDeployStatus getDeployStatus(String var1);

    public Map<String, DataSchemeDeployStatus> getDeployStatus(List<String> var1);

    public boolean dataSchemeCheckData(String var1);

    public boolean dataTableCheckData(String ... var1);

    public boolean dataTableGroupCheckData(String var1);

    public List<DataFieldDeployInfo> getDeployInfoBySchemeKey(String var1);

    public List<DataFieldDeployInfo> getDeployInfoByDataTableKey(String var1);

    default public List<DataFieldDeployInfo> getDeployInfoByDataTableCode(String tableCode) {
        DataTable dataTable = this.getDataTableByCode(tableCode);
        if (null == dataTable) {
            return Collections.emptyList();
        }
        return this.getDeployInfoByDataTableKey(dataTable.getKey());
    }

    @Deprecated
    default public List<DataFieldDeployInfo> getDeployInfoByTableCode(String tableCode) {
        return this.getDeployInfoByDataTableCode(tableCode);
    }

    public List<DataFieldDeployInfo> getDeployInfoByDataFieldKeys(String ... var1);

    default public List<DataFieldDeployInfo> getDeployInfoByDataFieldKey(String dataSchemeKey, String dataFieldKey) {
        return this.getDeployInfoByDataFieldKeys(dataFieldKey);
    }

    default public List<DataFieldDeployInfo> getDeployInfoByDataFieldKeys(String dataSchemeKey, List<String> dataFieldKeys) {
        if (null == dataFieldKeys) {
            return Collections.emptyList();
        }
        return this.getDeployInfoByDataFieldKeys(dataFieldKeys.toArray(new String[0]));
    }

    public List<DataFieldDeployInfo> getDeployInfoByTableModelKey(String var1);

    public List<DataFieldDeployInfo> getDeployInfoByTableName(String var1);

    public DataFieldDeployInfo getDeployInfoByColumnKey(String var1);

    default public DataFieldDeployInfo getDeployInfoByColumnKey(String dataSchemeKey, String columnKey) {
        return this.getDeployInfoByColumnKey(columnKey);
    }

    public List<DataFieldDeployInfo> getDeployInfoByColumnKeys(List<String> var1);

    default public List<DataFieldDeployInfo> getDeployInfoByColumnKeys(String dataSchemeKey, List<String> columnKeys) {
        if (null == columnKeys) {
            return Collections.emptyList();
        }
        return this.getDeployInfoByColumnKeys(columnKeys);
    }

    public boolean dataFieldCheckData(String ... var1);

    public String getDataTableByTableModel(String var1);

    public boolean isDataTable(String var1);

    public DataTableRel getDataTableRelBySrcTable(String var1);

    public List<DataTableRel> getDataTableRelByDesTable(String var1);

    public List<DataField> searchField(FieldSearchQuery var1);

    public List<DataTable> searchBy(String var1, String var2, int var3);

    public List<DataTable> searchBy(List<String> var1, String var2, int var3);

    default public DataField getDataFieldFromMdInfoByCode(String dataSchemeKey, String dataFieldCode) {
        DataTable mdInfo = this.getDataTableForMdInfo(dataSchemeKey);
        if (null == mdInfo) {
            return null;
        }
        List<DataField> fields = this.getDataFieldByTable(mdInfo.getKey());
        if (null == fields) {
            return null;
        }
        return fields.stream().filter(f -> f.getCode().equalsIgnoreCase(dataFieldCode)).findAny().orElse(null);
    }

    default public DataField findDataField(String dataSchemeKey, List<String> dataFieldKeys, Predicate<DataField> predicate) {
        return this.getDataFields(dataSchemeKey, dataFieldKeys).stream().filter(predicate).findFirst().orElse(null);
    }
}

