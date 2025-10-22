/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.single.core.file.SingleFile
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.exception.SingleDataException
 */
package nr.single.client.service.upload;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.single.core.file.SingleFile;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.exception.SingleDataException;

public interface IUploadJioTaskDataService {
    public JIOImportResultObject uploadJioTaskData(String var1, UploadParam var2, AsyncTaskMonitor var3, double var4, double var6) throws SingleDataException;

    public JIOImportResultObject uploadJioTaskData(TaskDataContext var1, String var2, SingleFile var3, UploadParam var4, AsyncTaskMonitor var5, double var6, double var8) throws SingleDataException;

    public boolean isJioUseCheckData();

    public String getUploadTaskDocDir(UploadParam var1);

    public String getUploadTaskImgDir(UploadParam var1);

    public String getUploadTaskRptDir(UploadParam var1);

    public String getUploadTaskTxtDir(UploadParam var1);
}

