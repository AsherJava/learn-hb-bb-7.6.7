/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.common.DataLinkColumn
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.efdc.extract;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.List;

public class ExtractDataRegion {
    private String floatExpression;
    private boolean isFloat;
    private int rowIndex = -1;
    private int colIndex = -1;
    private List<String> columnExpressions = new ArrayList<String>();
    private List<DataLinkColumn> columns = new ArrayList<DataLinkColumn>();
    private List<String> formulaKeys = new ArrayList<String>();
    private DataRegionDefine regionDefine;
    private String tableName = null;

    public String getFloatExpression() {
        return this.floatExpression;
    }

    public int getColmumCount() {
        return this.columns.size();
    }

    public String getColmumExpression(int index) {
        return this.columnExpressions.get(index);
    }

    public DataLinkColumn getColmum(int index) {
        return this.columns.get(index);
    }

    public FieldDefine getColmumField(int index) {
        return this.columns.get(index).getField();
    }

    public void addColumn(String expression, DataLinkColumn column, String formulaKey) {
        this.columnExpressions.add(expression);
        this.columns.add(column);
        this.formulaKeys.add(formulaKey);
        if (this.isFloat && this.tableName == null) {
            IRuntimeDataSchemeService dataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
            FieldDefine field = column.getField();
            List deployInfos = dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{field.getKey()});
            this.tableName = ((DataFieldDeployInfo)deployInfos.get(0)).getTableName();
        }
    }

    public boolean isFloat() {
        return this.isFloat;
    }

    public void setFloatExpression(String floatExpression) {
        this.floatExpression = floatExpression;
    }

    public void setFloat(boolean isFloat) {
        this.isFloat = isFloat;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public int getColIndex() {
        return this.colIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public FieldDefine getInputOrderField(ExecutorContext eContext) throws ParseException {
        DataModelDefinitionsCache definitionsCache = eContext.getCache().getDataModelDefinitionsCache();
        if (this.tableName != null) {
            TableModelRunInfo tableRunInfo = eContext.getCache().getDataModelDefinitionsCache().getTableInfo(this.tableName);
            if (tableRunInfo == null) {
                return null;
            }
            ColumnModelDefine inputOrderColumn = tableRunInfo.getOrderField();
            FieldDefine inputOrderField = definitionsCache.getFieldDefine(inputOrderColumn);
            return inputOrderField;
        }
        if (this.regionDefine != null && this.regionDefine.getInputOrderFieldKey() != null) {
            ColumnModelDefine column = definitionsCache.findField(this.regionDefine.getInputOrderFieldKey());
            FieldDefine inputOrderField = definitionsCache.getFieldDefine(column);
            return inputOrderField;
        }
        return null;
    }

    public String getRegionKey() {
        if (this.regionDefine != null) {
            return this.regionDefine.getKey();
        }
        return null;
    }

    public DataRegionDefine getRegiondefine() {
        return this.regionDefine;
    }

    public void setRegiondefine(DataRegionDefine regionDefine) {
        this.regionDefine = regionDefine;
    }

    public List<String> getFormulaKeys() {
        return this.formulaKeys;
    }

    public void setFormulaKeys(List<String> formulaKey) {
        this.formulaKeys = formulaKey;
    }
}

