/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 */
package nr.single.map.data.internal.service.dataSet;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.map.data.facade.dataset.ReportRegionDataSet;
import nr.single.map.data.facade.dataset.ReportRegionDataSetList;

public class ReportRegionDataSetListImpl
implements ReportRegionDataSetList {
    private List<ReportRegionDataSet> dataSetList;
    private Map<String, ReportRegionDataSet> fieldDataSetMap;
    private Map<String, ReportRegionDataSet> formDataSetMap;
    private Map<String, Object> fieldMap;
    private int floatRowCount;
    private int floatEnumType = 0;
    private String filePath;
    private String txtFilePath;
    private String docFilePath;
    private String zdmDocFilePath;
    private String imgFilePath;
    private String tempFilePath;
    private ReportRegionDataSetList virtualDatasets;

    @Override
    public List<ReportRegionDataSet> getDataSetList() {
        if (this.dataSetList == null) {
            this.dataSetList = new ArrayList<ReportRegionDataSet>();
        }
        return this.dataSetList;
    }

    @Override
    public void setDataSetList(List<ReportRegionDataSet> dataSetList) {
        this.dataSetList = dataSetList;
    }

    @Override
    public Map<String, ReportRegionDataSet> getFieldDataSetMap() {
        if (this.fieldDataSetMap == null) {
            this.fieldDataSetMap = new HashMap<String, ReportRegionDataSet>();
        }
        return this.fieldDataSetMap;
    }

    @Override
    public void setFieldDataSetMap(Map<String, ReportRegionDataSet> fieldDataSetMap) {
        this.fieldDataSetMap = fieldDataSetMap;
    }

    @Override
    public ReportRegionDataSet getDataSetbyFieldName(String fieldName) {
        ReportRegionDataSet dataSet = null;
        if (this.fieldDataSetMap.containsKey(fieldName)) {
            dataSet = this.fieldDataSetMap.get(fieldName);
        }
        return dataSet;
    }

    @Override
    public ReportRegionDataSet getDataSetbyForm(String formCode) {
        ReportRegionDataSet dataSet = null;
        if (this.formDataSetMap.containsKey(formCode)) {
            dataSet = this.formDataSetMap.get(formCode);
        }
        return dataSet;
    }

    @Override
    public void locateDataRowByZdm(String zdm) throws Exception {
        this.floatRowCount = 0;
        for (ReportRegionDataSet dataSet : this.dataSetList) {
            dataSet.locateDataRowByZdm(zdm);
            if (dataSet.getCurDataRows() == null || this.floatRowCount >= dataSet.getCurDataRows().size()) continue;
            this.floatRowCount = dataSet.getCurDataRows().size();
        }
    }

    @Override
    public void AppendDataRowByZdm(String zdm) throws Exception {
        for (ReportRegionDataSet dataSet : this.dataSetList) {
            dataSet.AppendDataRowByZdm(zdm);
        }
    }

    @Override
    public boolean getDataRowIsNew() {
        for (ReportRegionDataSet dataSet : this.dataSetList) {
            if (dataSet.getIsNewRow()) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isDataEmpty() {
        if (this.dataSetList != null) {
            for (ReportRegionDataSet dataSet : this.dataSetList) {
                if (dataSet.getDataSet() == null || dataSet.getDataSet().getDataRowCount() <= 0) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public String getFieldValue(String fieldName) {
        ReportRegionDataSet dataSet = this.getDataSetbyFieldName(fieldName);
        String fieldValue = null;
        if (dataSet != null && StringUtils.isEmpty((String)(fieldValue = dataSet.getCurDataRow().getValueString(fieldName))) && StringUtils.isNotEmpty((String)fieldName) && fieldName.contains(".")) {
            String newFieldName = fieldName.substring(fieldName.indexOf(".") + 1);
            fieldValue = dataSet.getCurDataRow().getValueString(newFieldName);
        }
        return fieldValue;
    }

    @Override
    public void setFieldValue(String fieldName, String fieldValue) {
        ReportRegionDataSet dataSet = this.getDataSetbyFieldName(fieldName);
        if (dataSet.getFieldMap().containsKey(fieldName)) {
            // empty if block
        }
        String newFieldName = fieldName;
        if (dataSet != null) {
            if (StringUtils.isNotEmpty((String)fieldName) && fieldName.contains(".")) {
                newFieldName = fieldName.substring(fieldName.indexOf(".") + 1);
            }
            dataSet.getCurDataRow().setValue(newFieldName, (Object)fieldValue);
        }
    }

    @Override
    public void setAllFieldValue(String fieldName, String fieldValue) {
        for (ReportRegionDataSet dataSet : this.dataSetList) {
            dataSet.getCurDataRow().setValue(fieldName, (Object)fieldValue);
        }
    }

    @Override
    public void saveData() throws Exception {
        for (ReportRegionDataSet dataSet : this.dataSetList) {
            dataSet.saveData();
        }
    }

    @Override
    public Map<String, ReportRegionDataSet> getFormDataSetMap() {
        if (this.formDataSetMap == null) {
            this.formDataSetMap = new HashMap<String, ReportRegionDataSet>();
        }
        return this.formDataSetMap;
    }

    @Override
    public void setFormDataSetMap(Map<String, ReportRegionDataSet> formDataSetMap) {
        this.formDataSetMap = formDataSetMap;
    }

    @Override
    public void saveRowData() {
        for (ReportRegionDataSet dataSet : this.dataSetList) {
            if (dataSet.getIsNewRow()) {
                dataSet.saveRowData();
                continue;
            }
            if (dataSet.getDataSet() == null || dataSet.getDataSet().isHasLoadAllRec()) continue;
            dataSet.saveRowData();
        }
    }

    @Override
    public Map<String, Object> getFieldMap() {
        if (this.fieldMap == null) {
            this.fieldMap = new HashMap<String, Object>();
        }
        return this.fieldMap;
    }

    @Override
    public void setFieldMap(Map<String, Object> fieldMap) {
        this.fieldMap = fieldMap;
    }

    @Override
    public int getFloatRowsCount() {
        return this.floatRowCount;
    }

    @Override
    public void locateDataRowByFloatIndex(int index) throws Exception {
        for (ReportRegionDataSet dataSet : this.dataSetList) {
            DataRow row;
            if (dataSet.getCurDataRows() != null && dataSet.getCurDataRows().size() > index) {
                row = dataSet.getCurDataRows().get(index);
                dataSet.setCurDataRow(row);
                continue;
            }
            row = dataSet.AppendDataRowByZdm("");
            dataSet.setCurDataRow(row);
        }
    }

    @Override
    public void locateDataRowByFloatCode(String floatCode) throws Exception {
    }

    @Override
    public String getFilePath() {
        return this.filePath;
    }

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void close() {
        if (this.dataSetList != null) {
            for (ReportRegionDataSet dataSet : this.dataSetList) {
                dataSet.close();
                if (dataSet.getParentDataSet() == null) continue;
                dataSet.getParentDataSet().close();
            }
        }
    }

    @Override
    public void BuildVirtualFloatRows() {
    }

    @Override
    public boolean isVirtualFloat() {
        return this.floatEnumType == 1 || this.floatEnumType == 2;
    }

    @Override
    public ReportRegionDataSetList getVirtualDatasets() {
        return this.virtualDatasets;
    }

    @Override
    public void setVirtualDatasets(ReportRegionDataSetList virtualDatasets) {
        this.virtualDatasets = virtualDatasets;
    }

    @Override
    public int getFloatEnumType() {
        return this.floatEnumType;
    }

    @Override
    public void setFloatEnumType(int floatEnumType) {
        this.floatEnumType = floatEnumType;
    }

    @Override
    public List<String> getZdmList() {
        ArrayList<String> list = new ArrayList<String>();
        HashMap zdmMap = new HashMap();
        for (ReportRegionDataSet dataSet : this.dataSetList) {
            for (String zdm : dataSet.getZdmRowsMap().keySet()) {
                if (zdmMap.containsKey(zdm)) continue;
                list.add(zdm);
            }
        }
        return list;
    }

    @Override
    public String getTxtFilePath() {
        return this.txtFilePath;
    }

    @Override
    public void setTxtFilePath(String txtFilePath) {
        this.txtFilePath = txtFilePath;
    }

    @Override
    public String getDocFilePath() {
        return this.docFilePath;
    }

    @Override
    public void setDocFilePath(String docFilePath) {
        this.docFilePath = docFilePath;
    }

    @Override
    public String getZdmDocFilePath() {
        return this.zdmDocFilePath;
    }

    @Override
    public void setZdmDocFilePath(String zdmDocFilePath) {
        this.zdmDocFilePath = zdmDocFilePath;
    }

    @Override
    public String getImgFilePath() {
        return this.imgFilePath;
    }

    @Override
    public void setImgFilePath(String imgFilePath) {
        this.imgFilePath = imgFilePath;
    }

    @Override
    public String getTempFilePath() {
        return this.tempFilePath;
    }

    @Override
    public void setTempFilePath(String tempFilePath) {
        this.tempFilePath = tempFilePath;
    }
}

