/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParseException
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.jiuqi.nr.jtable.dataset.IRegionExportDataSet
 *  com.jiuqi.nr.jtable.dataset.IReportExportDataSet
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.facade.SingleFileFieldInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 *  nr.single.map.data.facade.dataset.ReportRegionDataSetList
 */
package nr.single.data.dataout.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jiuqi.nr.jtable.dataset.IRegionExportDataSet;
import com.jiuqi.nr.jtable.dataset.IReportExportDataSet;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.facade.dataset.ReportRegionDataSetList;

public interface ITaskFileExportDataService {
    public void dataOper(TaskDataContext var1, IReportExportDataSet var2, String var3) throws Exception;

    public void exportFixRegion(TaskDataContext var1, IReportExportDataSet var2, IRegionExportDataSet var3, String var4, SingleFileTableInfo var5) throws Exception;

    public void exportFloatRegion(TaskDataContext var1, IReportExportDataSet var2, List<IRegionExportDataSet> var3, String var4, SingleFileTableInfo var5) throws Exception;

    public void setRowValueByFields(TaskDataContext var1, List<FieldData> var2, DataRow var3, Object[] var4, Map<String, SingleFileFieldInfo> var5, ReportRegionDataSetList var6) throws JsonParseException, JsonMappingException, IOException;

    public void setDataAfterExport(TaskDataContext var1, DataRow var2, ReportRegionDataSetList var3, String var4);

    public void updateFormCheckInfo(TaskDataContext var1, String var2, Map<String, String> var3, Map<Map<String, String>, String> var4);
}

