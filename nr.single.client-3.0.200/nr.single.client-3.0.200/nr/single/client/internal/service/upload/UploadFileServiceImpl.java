/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package nr.single.client.internal.service.upload;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import nr.single.client.service.upload.IUploadFileService;
import org.springframework.stereotype.Service;

@Service
public class UploadFileServiceImpl
implements IUploadFileService {
    @Override
    public void completeDeleteMarkFile(String dataSchemeKey, AsyncTaskMonitor monitor) {
    }

    @Override
    public void incrementDeleteMarkFile(String dataSchemeKey, DimensionCombination dimensionCombination, String regionKey, AsyncTaskMonitor monitor) {
    }
}

