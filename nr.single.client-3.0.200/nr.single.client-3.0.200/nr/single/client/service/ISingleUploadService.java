/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  nr.single.map.data.exception.SingleDataException
 */
package nr.single.client.service;

import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import java.io.File;
import nr.single.client.bean.SingleUploadParam;
import nr.single.map.data.exception.SingleDataException;

public interface ISingleUploadService {
    public AsyncTaskInfo upload(File var1, SingleUploadParam var2) throws SingleDataException;
}

