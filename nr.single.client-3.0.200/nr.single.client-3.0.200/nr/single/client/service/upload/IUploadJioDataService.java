/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData
 *  com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  nr.single.map.data.SingleFieldFileInfo
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.exception.SingleDataCorpException
 *  nr.single.map.data.exception.SingleDataException
 *  nr.single.map.data.facade.dataset.ReportRegionDataSetList
 */
package nr.single.client.service.upload;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.jtable.params.base.FieldData;
import java.util.List;
import java.util.Map;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.map.data.SingleFieldFileInfo;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.exception.SingleDataCorpException;
import nr.single.map.data.exception.SingleDataException;
import nr.single.map.data.facade.dataset.ReportRegionDataSetList;

public interface IUploadJioDataService {
    public void setImportTypeByParam(TaskDataContext var1, UploadParam var2) throws SingleDataException;

    public FormReadWriteAccessData formAuthCheck(UploadParam var1, Map<String, DimensionValue> var2, ReadWriteAccessCacheManager var3, List<String> var4);

    public List<SingleFieldFileInfo> getFieldFileInfosFromDataSet(TaskDataContext var1, ReportRegionDataSetList var2, Map<String, FieldData> var3);

    public void markDeleteFileDataBeforeImport(TaskDataContext var1, ReportRegionDataSetList var2, SingleFieldFileInfo var3, Map<String, FieldData> var4);

    public void batchMarkDeleteFileDataBeforeImport(TaskDataContext var1, ReportRegionDataSetList var2, SingleFieldFileInfo var3, Map<String, FieldData> var4);

    public void setDataBeforeImport(TaskDataContext var1, ReportRegionDataSetList var2, String var3, SingleFieldFileInfo var4, Map<String, FieldData> var5) throws SingleDataCorpException;

    public void readUnitCount(TaskDataContext var1, UploadParam var2);

    public void recordUnitState(TaskDataContext var1, JIOImportResultObject var2, UploadParam var3);

    public AsyncTaskInfo doDeleteDirs(List<String> var1);

    public AsyncTaskInfo doDeleteNetFjFiles(String var1);

    public AsyncTaskInfo doDeleteNetMarkFiles(String var1);

    public void deleteDirsAsync(AsyncTaskMonitor var1, List<String> var2);

    public void initReportAccessAuthCache(TaskDataContext var1, UploadParam var2, List<String> var3, Map<String, Map<String, IAccessResult>> var4, Map<String, String> var5);

    public void initReportAuthCache(TaskDataContext var1, UploadParam var2, List<String> var3, ReadWriteAccessCacheManager var4, Map<String, FormReadWriteAccessData> var5, Map<String, String> var6);

    public void deleteFloatRegionData(TaskDataContext var1, UploadParam var2, List<String> var3, String var4, String var5, int var6);

    public boolean isCurrentPeriodCanWrite(String var1, String var2) throws SingleDataException;

    public boolean checkNetPeriodValid(String var1, String var2) throws SingleDataException;

    public void uploadSingleFiles(TaskDataContext var1, AsyncTaskMonitor var2);

    public void uploadSingleFiles(TaskDataContext var1, JIOImportResultObject var2, AsyncTaskMonitor var3);
}

