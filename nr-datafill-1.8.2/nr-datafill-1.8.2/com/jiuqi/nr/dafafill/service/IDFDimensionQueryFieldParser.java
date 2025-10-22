/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 */
package com.jiuqi.nr.dafafill.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.DataFillDimensionTitle;
import com.jiuqi.nr.dafafill.model.DataFillModel;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import java.util.List;
import java.util.Map;

public interface IDFDimensionQueryFieldParser {
    public DimensionValueSet parserGetEntityDimensionValueSet(DataFillContext var1);

    public DimensionValueSet convert(List<DFDimensionValue> var1, DataFillContext var2);

    public List<DFDimensionValue> reverseParser(DimensionValueSet var1);

    public DimensionValueSet entityIdToSqlDimension(DataFillContext var1, DimensionValueSet var2);

    public DimensionValueSet entityIdToSqlDimension(DataFillContext var1, List<DFDimensionValue> var2);

    public DimensionValueSet sqlDimensionToEntityId(DataFillContext var1, DimensionValueSet var2);

    public Map<FieldType, List<QueryField>> getFieldTypeQueryFields(DataFillContext var1);

    public List<QueryField> getAllQueryFields(DataFillContext var1);

    public List<QueryField> getDisplayColsQueryFields(DataFillContext var1);

    public Map<String, QueryField> getQueryFieldsMap(DataFillContext var1);

    public Map<String, QueryField> getSimplifyQueryFieldsMap(DataFillContext var1);

    public Map<String, DataFillDimensionTitle> getDimensionTitle(QueryField var1, List<String> var2, List<String> var3, DataFillModel var4);

    public Map<String, DataFillDimensionTitle> getDwDimensionTitle(QueryField var1, List<String> var2, List<String> var3, DataFillModel var4);

    public String getFormat(QueryField var1);

    public String getDimensionName(String var1);

    public String getDimensionNameByField(QueryField var1);

    public DimensionValueSet dimensionValueIntegration(DataFillContext var1, List<DimensionValueSet> var2);

    public String getSqlDimensionName(QueryField var1);

    public boolean isMasterMultipleVersion(DataFillContext var1);

    public List<DataFillDimensionTitle> getPeriodList(DataFillContext var1);

    public int getDecimal(String var1, String var2);

    public void hideDimensionsProcess(DataFillContext var1, String var2, List<QueryField> var3, List<String> var4);

    public List<IDataRow> hiddenRowKeysProcess(IReadonlyTable var1, String var2, DataFillContext var3);

    public void searchDwHiddenDimensionValue(String var1, DimensionValueSet var2, QueryField var3, QueryField var4, List<QueryField> var5, List<String> var6, boolean var7);

    public List<String> searchReferRelation(String var1, String var2, QueryField var3, DimensionValueSet var4, QueryField var5, QueryField var6, String var7);

    public String getDataSchemeKey(List<QueryField> var1);

    public Map<String, QueryField> getDimensionNameQueryFieldsMap(DataFillContext var1);

    public List<QueryField> getHideQueryFields(String var1);

    public String getDimensionInfoString(DimensionValueSet var1, DataFillContext var2);
}

