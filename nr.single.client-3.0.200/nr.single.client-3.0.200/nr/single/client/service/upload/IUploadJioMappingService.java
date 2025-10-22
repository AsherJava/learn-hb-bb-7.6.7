/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  nr.single.map.data.facade.SingleFileTaskInfo
 */
package nr.single.client.service.upload;

import com.jiuqi.nr.dataentry.bean.UploadParam;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.map.data.facade.SingleFileTaskInfo;

public interface IUploadJioMappingService {
    public JIOImportResultObject queryMappingByFile(String var1, UploadParam var2);

    public JIOImportResultObject queryMappingByTaskDir(String var1, UploadParam var2);

    public void queryMappingByTaskInfo(SingleFileTaskInfo var1, UploadParam var2, JIOImportResultObject var3);
}

