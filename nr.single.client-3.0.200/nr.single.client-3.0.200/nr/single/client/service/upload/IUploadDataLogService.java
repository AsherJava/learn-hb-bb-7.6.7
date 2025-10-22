/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.JIOUnitImportResult
 *  com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  nr.single.map.data.TaskDataContext
 */
package nr.single.client.service.upload;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.JIOUnitImportResult;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.Map;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.map.data.TaskDataContext;

public interface IUploadDataLogService {
    public void recordFmdmErrors(TaskDataContext var1, JIOImportResultObject var2);

    public void recordFormErrors(TaskDataContext var1, JIOImportResultObject var2, String var3);

    public void recordFmdmSuccess(TaskDataContext var1, JIOImportResultObject var2);

    public void clearSuccessForms(TaskDataContext var1, JIOImportResultObject var2);

    public void recordImportFormsToLog(TaskDataContext var1, JIOImportResultObject var2);

    public String handException(Exception var1);

    public void setAfterImportSingleReportLog(TaskDataContext var1, JIOImportResultObject var2, String var3, Map<String, JIOUnitImportResult> var4, Map<String, JIOUnitImportResult> var5, Map<String, DimensionValue> var6);

    public void addSuccessForm(Map<String, JIOUnitImportResult> var1, Map<String, JIOUnitImportResult> var2, FormDefine var3, String var4);

    public void addErrorForm(Map<String, JIOUnitImportResult> var1, Map<String, JIOUnitImportResult> var2, FormDefine var3, String var4, FormReadWriteAccessData var5, String var6);

    public void addErrorForm(Map<String, JIOUnitImportResult> var1, Map<String, JIOUnitImportResult> var2, FormDefine var3, String var4, String var5);

    public JIOUnitImportResult getErorrItem(TaskDataContext var1, String var2, String var3);
}

