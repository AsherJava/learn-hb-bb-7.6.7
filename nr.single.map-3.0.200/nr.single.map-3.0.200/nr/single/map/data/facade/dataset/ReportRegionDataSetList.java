/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.facade.dataset;

import java.util.List;
import java.util.Map;
import nr.single.map.data.facade.dataset.ReportRegionDataSet;

public interface ReportRegionDataSetList {
    public List<ReportRegionDataSet> getDataSetList();

    public void setDataSetList(List<ReportRegionDataSet> var1);

    public Map<String, ReportRegionDataSet> getFieldDataSetMap();

    public void setFieldDataSetMap(Map<String, ReportRegionDataSet> var1);

    public Map<String, ReportRegionDataSet> getFormDataSetMap();

    public void setFormDataSetMap(Map<String, ReportRegionDataSet> var1);

    public Map<String, Object> getFieldMap();

    public void setFieldMap(Map<String, Object> var1);

    public ReportRegionDataSet getDataSetbyFieldName(String var1);

    public ReportRegionDataSet getDataSetbyForm(String var1);

    public void locateDataRowByZdm(String var1) throws Exception;

    public void AppendDataRowByZdm(String var1) throws Exception;

    public boolean getDataRowIsNew();

    public List<String> getZdmList();

    public boolean isDataEmpty();

    public int getFloatRowsCount();

    public void locateDataRowByFloatIndex(int var1) throws Exception;

    public void locateDataRowByFloatCode(String var1) throws Exception;

    public String getFieldValue(String var1);

    public void setFieldValue(String var1, String var2);

    public void setAllFieldValue(String var1, String var2);

    public int getFloatEnumType();

    public void setFloatEnumType(int var1);

    public boolean isVirtualFloat();

    public void BuildVirtualFloatRows();

    public ReportRegionDataSetList getVirtualDatasets();

    public void setVirtualDatasets(ReportRegionDataSetList var1);

    public void saveData() throws Exception;

    public void saveRowData();

    public String getFilePath();

    public void setFilePath(String var1);

    public String getTxtFilePath();

    public void setTxtFilePath(String var1);

    public String getDocFilePath();

    public void setDocFilePath(String var1);

    public String getZdmDocFilePath();

    public void setZdmDocFilePath(String var1);

    public String getImgFilePath();

    public void setImgFilePath(String var1);

    public String getTempFilePath();

    public void setTempFilePath(String var1);

    public void close();
}

