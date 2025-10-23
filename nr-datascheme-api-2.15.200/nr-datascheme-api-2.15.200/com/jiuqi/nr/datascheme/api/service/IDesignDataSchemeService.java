/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.DesignDataTableRel;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IDesignDataSchemeService {
    public DesignDataScheme initDataScheme();

    public String insertDataScheme(DesignDataScheme var1) throws SchemeDataException;

    public String insertDataScheme(DesignDataScheme var1, List<DesignDataDimension> var2) throws SchemeDataException;

    public void deleteDataScheme(String var1);

    public void updateDataScheme(DesignDataScheme var1) throws SchemeDataException;

    public void updateDataScheme(DesignDataScheme var1, List<DesignDataDimension> var2) throws SchemeDataException;

    public void updateSubLevelDataScheme(DesignDataScheme var1);

    public DesignDataScheme getDataScheme(String var1);

    public DesignDataScheme getDataSchemeByCode(String var1);

    public DesignDataScheme getDataSchemeByPrefix(String var1);

    public List<DesignDataScheme> getDataSchemeByParent(String var1);

    public List<DesignDataScheme> getDataSchemeByPeriodType(PeriodType var1);

    public <E extends DesignDataScheme> void insertDataSchemes(List<E> var1) throws SchemeDataException;

    public void deleteDataSchemes(List<String> var1);

    public <E extends DesignDataScheme> void updateDataSchemes(List<E> var1) throws SchemeDataException;

    public List<DesignDataScheme> getDataSchemes(List<String> var1);

    public List<DesignDataScheme> getAllDataScheme();

    public void copyDataScheme(String var1, DesignDataScheme var2, List<DesignDataDimension> var3) throws SchemeDataException;

    public void copyDataScheme(DataSchemeNode var1, List<DataSchemeNode> var2) throws SchemeDataException;

    public DesignDataGroup initDataGroup();

    public String insertDataGroup(DesignDataGroup var1) throws SchemeDataException;

    public void deleteDataGroup(String var1) throws SchemeDataException;

    public void updateDataGroup(DesignDataGroup var1) throws SchemeDataException;

    public DesignDataGroup getDataGroup(String var1);

    public <E extends DesignDataGroup> String[] insertDataGroups(List<E> var1) throws SchemeDataException;

    public void deleteDataGroups(List<String> var1) throws SchemeDataException;

    public <E extends DesignDataGroup> void updateDataGroups(List<E> var1) throws SchemeDataException;

    public List<DesignDataGroup> getDataGroups(List<String> var1);

    public List<DesignDataGroup> getAllDataGroup(String var1);

    public List<DesignDataGroup> getDataGroupByKind(int var1);

    public List<DesignDataGroup> getDataGroupByScheme(String var1);

    public List<DesignDataGroup> getDataGroupByParent(String var1);

    @Deprecated
    default public List<DesignDataGroup> getRootSchemeDataGroup() {
        return this.getDataGroupForNrSchemeRoot();
    }

    public List<DesignDataGroup> getDataGroupForNrSchemeRoot();

    public List<DesignDataGroup> getDataGroupForQuerySchemeRoot();

    public DesignDataTable initDataTable();

    public String insertDataTable(DesignDataTable var1) throws SchemeDataException;

    public String insertDataTable(DesignDataTable var1, boolean var2) throws SchemeDataException;

    public void insertDataTable(DesignDataTable var1, List<DesignDataField> var2, List<DataFieldDeployInfo> var3);

    public void deleteDataTable(String var1);

    public void updateDataTable(DesignDataTable var1) throws SchemeDataException;

    public DesignDataTable getDataTable(String var1);

    @Deprecated
    public DesignDataTable getFmDataTableBySchemeAndDimKey(String var1, String var2);

    public DesignDataTable getDataTableByCode(String var1);

    public DesignDataTable getDataTableBy(String var1, String var2, String var3);

    public <E extends DesignDataTable> String[] insertDataTables(List<E> var1) throws SchemeDataException;

    public void deleteDataTables(List<String> var1);

    public <E extends DesignDataTable> void updateDataTables(List<E> var1) throws SchemeDataException;

    public void refreshDataTableUpdateTime(String var1);

    @Deprecated
    public List<DesignDataTable> getFmDataTableBySchemeKey(String var1);

    public List<DesignDataTable> getDataTables(List<String> var1);

    public List<DesignDataTable> getAllDataTable();

    public List<DesignDataTable> getAllDataTable(String var1);

    public List<DesignDataTable> getDataTableByGroup(String var1);

    public List<DesignDataTable> getDataTableByScheme(String var1);

    public List<DesignDataTable> getAllDataTableBySchemeAndTypes(String var1, DataTableType ... var2);

    public DesignDataTable getDataTableForMdInfo(String var1);

    public DesignDataField initDataField();

    public String insertDataField(DesignDataField var1) throws SchemeDataException;

    public void deleteDataField(String var1);

    default public void deleteDataFieldByTableKey(String tableKey) {
        this.deleteDataFieldByTableKey(tableKey, false);
    }

    public void deleteDataFieldByTableKey(String var1, boolean var2);

    public void updateDataField(DesignDataField var1) throws SchemeDataException;

    public DesignDataField getDataField(String var1);

    public DesignDataField getDataFieldByTableKeyAndCode(String var1, String var2);

    public DesignDataField getZbKindDataFieldBySchemeKeyAndCode(String var1, String var2);

    public List<DesignDataField> getDataFieldByTableCode(String var1);

    public List<DesignDataField> getBizDataFieldByTableKey(String var1);

    public List<DesignDataField> getBizDataFieldByTableCode(String var1);

    public List<DesignDataField> getDataFieldByTableKeyAndType(String var1, DataFieldType ... var2);

    public List<DesignDataField> getDataFieldByTableKeyAndKind(String var1, DataFieldKind ... var2);

    public List<DesignDataField> getDataFieldByTableCodeAndType(String var1, DataFieldType ... var2);

    public List<DesignDataField> getDataFieldByTableCodeAndKind(String var1, DataFieldKind ... var2);

    public <E extends DesignDataField> void insertDataFields(List<E> var1) throws SchemeDataException;

    public <E extends DesignDataField> void insertDataFields(List<E> var1, boolean var2) throws SchemeDataException;

    public void insertDataFields(List<? extends DesignDataField> var1, List<? extends DataFieldDeployInfo> var2);

    public void deleteDataFields(List<String> var1);

    default public void deleteDataFieldByTableKeys(List<String> tableKeys) {
        this.deleteDataFieldByTableKeys(tableKeys, false);
    }

    public void deleteDataFieldByTableKeys(List<String> var1, boolean var2);

    public <E extends DesignDataField> void updateDataFields(List<E> var1) throws SchemeDataException;

    public void updateDataFields(List<? extends DesignDataField> var1, List<DataFieldDeployInfo> var2);

    public List<DesignDataField> getDataFields(List<String> var1);

    public List<DesignDataField> getAllDataField(String var1);

    public List<DesignDataField> getAllDataFieldByKind(String var1, DataFieldKind ... var2);

    public List<DesignDataField> getDataFieldByTable(String var1);

    public List<DesignDataField> getDataFieldByTables(List<String> var1);

    @Deprecated
    public List<DesignDataField> getFmKindDataFieldsBySchemeAndKeys(String var1, String var2);

    @Deprecated
    public List<DesignDataField> getFmKindDataFieldsBySchemeAndDimKey(String var1, String var2);

    public DesignDataDimension initDataSchemeDimension();

    public List<DesignDataDimension> getDataSchemeDimension(String var1);

    public List<DesignDataDimension> getDataSchemeDimension(String var1, DimensionType var2);

    public List<DesignDataDimension> getReportDimension(String var1);

    public Boolean enableAdjustPeriod(String var1);

    public void checkDeployStatus(String var1) throws SchemeDataException;

    public <E extends DesignDataField> void updateGatherFieldKeys(String var1, List<String> var2) throws SchemeDataException;

    public void updateGatherFieldKeys(String var1, List<String> var2, DesignDataTable var3);

    public DesignDataTableRel initDataTableRel();

    public String insertDataTableRel(DesignDataTableRel var1);

    public List<String> insertDataTableRels(List<DesignDataTableRel> var1);

    public void updateDataTableRel(DesignDataTableRel var1);

    public void updateDataTableRels(List<DesignDataTableRel> var1);

    public void deleteDataTableRel(String var1);

    public void deleteDataTableRels(List<String> var1);

    public void deleteDataTableRelsBySrcTable(String var1);

    public void deleteDataTableRelsByDesTable(String var1);

    public DesignDataTableRel getDataTableRelBySrcTable(String var1);

    public List<DesignDataTableRel> getDataTableRelByDesTable(String var1);

    public DataFieldDeployInfo initDataFieldDeployInfo();

    public void setReportDimensionData(DesignDataDimension var1, String var2, String var3);

    public void addPublicDimToDataScheme(String var1, Set<DesignDataDimension> var2, Map<String, String> var3);

    public void addTableDimToTable(String var1, Set<DesignDataField> var2, Map<String, String> var3) throws SQLException;

    public List<DesignDataTable> searchTable(List<String> var1, String var2, int var3);

    public List<DesignDataField> searchField(FieldSearchQuery var1);

    public void clearSchemeGroup(String var1);

    public boolean addDataSchemeDimension(DesignDataDimension var1, String var2);

    public String insertDataTableForMdInfo(String var1, String var2, String var3);

    default public String insertDataTableForMdInfo(String dataSchemeKey) {
        return this.insertDataTableForMdInfo(dataSchemeKey, null, null);
    }

    public boolean enableAccountTable();

    public void addTableDimToTable(DesignDataField var1, String var2);

    public void updateDataSchemeUnitScope(String var1, List<DesignDataDimension> var2, boolean var3);

    public List<DesignDataField> getDataFieldsByEntity(List<String> var1);
}

