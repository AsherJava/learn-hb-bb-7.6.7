/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.params.DimensionValue
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.exception.SingleDataException
 */
package nr.single.client.internal.service.upload;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.common.params.DimensionValue;
import java.util.Map;
import nr.single.client.internal.service.upload.UploadTypeJioServiceImpl;
import nr.single.client.service.entitycheck.ISingleImportEntityCheckService;
import nr.single.client.service.upload.IUploadEntityCheckService;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.exception.SingleDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadEntityCheckServiceImpl
implements IUploadEntityCheckService {
    private static final Logger logger = LoggerFactory.getLogger(UploadTypeJioServiceImpl.class);
    @Autowired(required=false)
    private ISingleImportEntityCheckService entityCheckImportService;

    @Override
    public void importEntityCheckResult(TaskDataContext context, String taskDataPath, Map<String, DimensionValue> dimensionSet, AsyncTaskMonitor asyncTaskMonitor) throws SingleDataException {
        if (this.entityCheckImportService != null) {
            this.entityCheckImportService.importEntityCheckResult(context, taskDataPath, dimensionSet, asyncTaskMonitor);
        }
    }
}

