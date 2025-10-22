/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.single.core.dbf.IDbfTable
 *  com.jiuqi.nr.single.core.idx.IndexFieldDef
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 */
package nr.single.map.data.facade.dataset;

import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.idx.IndexFieldDef;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.util.List;
import java.util.Map;

public interface ReportRegionDataSet {
    public String getFormCode();

    public void setFormCode(String var1);

    public String getTableCode();

    public void setTableCode(String var1);

    public int getFloatingIndex();

    public void setFloatingIndex(int var1);

    public String getFileName();

    public void setFileName(String var1);

    public List<String> getFloatCodeFields();

    public void setFloatCodeFields(List<String> var1);

    public String getFloatCodeValues();

    public Map<String, String> getFloatCodeValueMap();

    public String getFloatOrder();

    public void setFloatOrder(String var1);

    public List<IndexFieldDef> getIndexFields();

    public void setIndexFields(List<IndexFieldDef> var1);

    public int getRowIndex(String var1);

    public int getRowIndexByZdm(String var1);

    public IDbfTable getDataSet();

    public void setDataSet(IDbfTable var1);

    public void buildRecordCache();

    public void addDataRow(DataRow var1);

    public DataRow getCurDataRow();

    public void setCurDataRow(DataRow var1);

    public List<DataRow> getCurDataRows();

    public void setCurDataRows(List<DataRow> var1);

    public DataRow locateDataRowByZdm(String var1) throws Exception;

    public DataRow AppendDataRowByZdm(String var1) throws Exception;

    public Map<String, List<DataRow>> getZdmRowsMap();

    public boolean isVirtualFloat();

    public void setVirtualFloat(boolean var1);

    public boolean getIsNewRow();

    public void setIsNewRow(boolean var1);

    public boolean getIsDataModified();

    public void setIsDataModified(boolean var1);

    public void saveData() throws Exception;

    public void saveRowData();

    public Map<String, Object> getFieldMap();

    public void setFieldMap(Map<String, Object> var1);

    public Map<String, Object> getMapFieldList();

    public void setMapFieldList(Map<String, Object> var1);

    public Object getTableInfo();

    public void setTableInfo(Object var1);

    public Object getRegionInfo();

    public void setRegionInfo(Object var1);

    public ReportRegionDataSet getParentDataSet();

    public void setParentDataSet(ReportRegionDataSet var1);

    public void close();
}

