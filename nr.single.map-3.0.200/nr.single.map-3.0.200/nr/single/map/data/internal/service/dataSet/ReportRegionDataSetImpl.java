/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.single.core.dbf.IDbfTable
 *  com.jiuqi.nr.single.core.idx.IndexFieldDef
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 */
package nr.single.map.data.internal.service.dataSet;

import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.idx.IndexFieldDef;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nr.single.map.data.DbfUtil;
import nr.single.map.data.facade.dataset.ReportRegionDataSet;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportRegionDataSetImpl
implements ReportRegionDataSet {
    private static final Logger logger = LoggerFactory.getLogger(ReportRegionDataSetImpl.class);
    private String formCode;
    private String tableCode;
    private int floatingIndex;
    private String fileName;
    private List<String> floatCodeFields;
    private List<IndexFieldDef> indexFields;
    private IDbfTable dataSet;
    private DataRow curDataRow;
    private List<DataRow> curDataRows;
    private boolean isVirtualFloat = false;
    private boolean isNewRow;
    private boolean isDataModified;
    private Map<String, DataRow> zdmRowMap;
    private Map<String, List<DataRow>> zdmRowsMap;
    private Map<String, Object> fieldMap;
    private Map<String, Object> mapFieldList;
    private ReportRegionDataSet parentDataSet;
    private Object tableInfo;
    private Object regionInfo;

    @Override
    public String getFormCode() {
        return this.formCode;
    }

    @Override
    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    @Override
    public String getTableCode() {
        return this.tableCode;
    }

    @Override
    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    @Override
    public int getFloatingIndex() {
        return this.floatingIndex;
    }

    @Override
    public void setFloatingIndex(int floatingIndex) {
        this.floatingIndex = floatingIndex;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<String> getFloatCodeFields() {
        return this.floatCodeFields;
    }

    @Override
    public void setFloatCodeFields(List<String> floatCodes) {
        this.floatCodeFields = floatCodes;
    }

    @Override
    public String getFloatCodeValues() {
        String floatCode = "";
        if (null != this.floatCodeFields) {
            for (String codeField : this.floatCodeFields) {
                Object obj = this.curDataRow.getValue(codeField);
                if (null == obj) continue;
                floatCode = floatCode + obj.toString();
            }
        }
        return floatCode;
    }

    @Override
    public Map<String, String> getFloatCodeValueMap() {
        LinkedHashMap<String, String> floatCodeMap = new LinkedHashMap<String, String>();
        if (null != this.floatCodeFields) {
            for (String codeField : this.floatCodeFields) {
                String floatCode = "";
                Object obj = this.curDataRow.getValue(codeField);
                if (null != obj) {
                    floatCode = obj.toString();
                }
                floatCodeMap.put(codeField, floatCode);
            }
        }
        return floatCodeMap;
    }

    @Override
    public List<IndexFieldDef> getIndexFields() {
        return this.indexFields;
    }

    @Override
    public void setIndexFields(List<IndexFieldDef> indexFields) {
        this.indexFields = indexFields;
    }

    @Override
    public int getRowIndex(String code) {
        return 0;
    }

    @Override
    public int getRowIndexByZdm(String zdm) {
        return 0;
    }

    @Override
    public IDbfTable getDataSet() {
        return this.dataSet;
    }

    @Override
    public void setDataSet(IDbfTable dbf) {
        this.dataSet = dbf;
    }

    @Override
    public void buildRecordCache() {
        this.zdmRowMap = new CaseInsensitiveMap<String, DataRow>();
        this.zdmRowsMap = new CaseInsensitiveMap<String, List<DataRow>>();
        List<DataRow> zdmRows = null;
        for (DataRow row : this.dataSet.getTable().getRows()) {
            Object obj = row.getValue(0);
            if (null == obj) continue;
            String zdm = row.getValue(0).toString();
            if (!this.zdmRowMap.containsKey(zdm)) {
                this.zdmRowMap.put(zdm, row);
                zdmRows = new ArrayList<DataRow>();
                zdmRows.add(row);
                this.zdmRowsMap.put(zdm, zdmRows);
                continue;
            }
            zdmRows = this.zdmRowsMap.get(zdm);
            zdmRows.add(row);
        }
    }

    @Override
    public void addDataRow(DataRow row) {
        this.dataSet.getTable().getRows().add((Object)row);
    }

    @Override
    public DataRow getCurDataRow() {
        return this.curDataRow;
    }

    @Override
    public void setCurDataRow(DataRow curDataRow) {
        this.curDataRow = curDataRow;
    }

    @Override
    public DataRow locateDataRowByZdm(String zdm) throws Exception {
        if (!this.dataSet.isHasLoadAllRec()) {
            if (this.curDataRow != null) {
                this.dataSet.clearDataRow(this.curDataRow);
            }
            if (this.curDataRows != null) {
                for (DataRow row : this.curDataRows) {
                    this.dataSet.clearDataRow(row);
                }
            }
        }
        boolean bl = this.isNewRow = !this.zdmRowMap.containsKey(zdm);
        if (this.isNewRow) {
            if (this.dataSet == null || this.dataSet.getTable() == null) {
                logger.info("\u56de\u5199\u56fa\u5b9a\u8868\u51fa\u73b0\u5f02\u5e38");
            }
            this.curDataRow = this.dataSet.getTable().newRow();
            this.curDataRow.setValue(0, (Object)zdm);
            this.curDataRows = new ArrayList<DataRow>();
            this.curDataRows.add(this.curDataRow);
        } else {
            this.curDataRow = this.zdmRowMap.get(zdm);
            this.curDataRows = this.zdmRowsMap.get(zdm);
            if (!this.dataSet.isHasLoadAllRec()) {
                this.dataSet.loadDataRow(this.curDataRow);
                if (this.curDataRows != null) {
                    for (DataRow row : this.curDataRows) {
                        this.dataSet.loadDataRow(row);
                    }
                }
            }
        }
        return this.curDataRow;
    }

    @Override
    public DataRow AppendDataRowByZdm(String zdm) throws Exception {
        this.isNewRow = true;
        this.curDataRow = this.dataSet.getTable().newRow();
        this.curDataRow.setValue(0, (Object)zdm);
        return this.curDataRow;
    }

    @Override
    public boolean getIsNewRow() {
        return this.isNewRow;
    }

    @Override
    public void setIsNewRow(boolean isNewRow) {
        this.isNewRow = isNewRow;
    }

    @Override
    public boolean getIsDataModified() {
        return this.isDataModified;
    }

    @Override
    public void setIsDataModified(boolean isDataModified) {
        this.isDataModified = isDataModified;
    }

    @Override
    public void saveData() throws Exception {
        this.dataSet.saveData();
        DbfUtil.createIndexFileConfigByFields(this.fileName, false, this.dataSet.getDataRowCount(), this.dataSet.getFileSize(), this.indexFields);
    }

    @Override
    public void saveRowData() {
        if (this.isNewRow) {
            this.dataSet.getTable().getRows().add((Object)this.curDataRow);
            String zdm = this.curDataRow.getValue(0).toString();
            List<DataRow> zdmRows = null;
            if (!this.zdmRowMap.containsKey(zdm)) {
                this.zdmRowMap.put(zdm, this.curDataRow);
                zdmRows = new ArrayList<DataRow>();
                zdmRows.add(this.curDataRow);
                this.zdmRowsMap.put(zdm, zdmRows);
            } else {
                zdmRows = this.zdmRowsMap.get(zdm);
                zdmRows.add(this.curDataRow);
            }
        }
        if (!this.dataSet.isHasLoadAllRec()) {
            if (this.curDataRow.getRecordNo() < 0) {
                this.curDataRow.setRecordNo(this.dataSet.getTable().getRows().size() - 1);
            }
            this.dataSet.saveDataRow(this.curDataRow);
            this.curDataRow.setHasLoaded(true);
            this.dataSet.clearDataRow(this.curDataRow, true);
        }
    }

    @Override
    public Map<String, Object> getFieldMap() {
        return this.fieldMap;
    }

    @Override
    public void setFieldMap(Map<String, Object> fieldMap) {
        this.fieldMap = fieldMap;
    }

    @Override
    public List<DataRow> getCurDataRows() {
        return this.curDataRows;
    }

    @Override
    public void setCurDataRows(List<DataRow> curDataRows) {
        this.curDataRows = curDataRows;
    }

    @Override
    public ReportRegionDataSet getParentDataSet() {
        return this.parentDataSet;
    }

    @Override
    public void setParentDataSet(ReportRegionDataSet dataSet) {
        this.parentDataSet = dataSet;
    }

    @Override
    public String getFloatOrder() {
        return this.curDataRow.getValueString("SYS_ORDER");
    }

    @Override
    public void setFloatOrder(String FloatOrder) {
        this.curDataRow.setValue("SYS_ORDER", (Object)FloatOrder);
    }

    @Override
    public Map<String, Object> getMapFieldList() {
        return this.mapFieldList;
    }

    @Override
    public void setMapFieldList(Map<String, Object> mapFieldList) {
        this.mapFieldList = mapFieldList;
    }

    @Override
    public void close() {
        try {
            if (this.dataSet != null) {
                this.dataSet.close();
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public Object getTableInfo() {
        return this.tableInfo;
    }

    @Override
    public void setTableInfo(Object tableInfo) {
        this.tableInfo = tableInfo;
    }

    @Override
    public Object getRegionInfo() {
        return this.regionInfo;
    }

    @Override
    public void setRegionInfo(Object regionInfo) {
        this.regionInfo = regionInfo;
    }

    @Override
    public boolean isVirtualFloat() {
        return this.isVirtualFloat;
    }

    @Override
    public void setVirtualFloat(boolean isVirtualFloat) {
        this.isVirtualFloat = isVirtualFloat;
    }

    @Override
    public Map<String, List<DataRow>> getZdmRowsMap() {
        return this.zdmRowsMap;
    }
}

