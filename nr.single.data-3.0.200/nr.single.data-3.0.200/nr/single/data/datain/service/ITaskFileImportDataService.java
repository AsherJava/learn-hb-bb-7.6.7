/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.single.core.file.SingleFile
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.exception.SingleDataException
 *  nr.single.map.data.facade.SingleFileRegionInfo
 */
package nr.single.data.datain.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.single.core.file.SingleFile;
import java.util.List;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.exception.SingleDataException;
import nr.single.map.data.facade.SingleFileRegionInfo;

public interface ITaskFileImportDataService {
    public String exactSingleFileToPath(TaskDataContext var1, String var2, String var3) throws SingleDataException;

    public SingleFile exactSingleFileToPathReturn(TaskDataContext var1, String var2, String var3) throws SingleDataException;

    public SingleFile exactSingleFileToPath2(TaskDataContext var1, String var2, String var3, boolean var4, boolean var5) throws SingleDataException;

    public SingleFile readSingleFileFromPath(TaskDataContext var1, String var2) throws SingleDataException;

    public void importSingleReportData(TaskDataContext var1, String var2) throws SingleDataException;

    public void importSingleReportRegionData(TaskDataContext var1, String var2, SingleFileRegionInfo var3) throws SingleDataException;

    public void importSingleEnityData(TaskDataContext var1, String var2, AsyncTaskMonitor var3) throws SingleDataException;

    public List<String> getSinglePeriods(TaskDataContext var1, String var2) throws SingleDataException;
}

