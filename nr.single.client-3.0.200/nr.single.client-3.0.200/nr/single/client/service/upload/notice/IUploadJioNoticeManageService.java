/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  nr.single.map.data.TaskDataContext
 */
package nr.single.client.service.upload.notice;

import com.jiuqi.nr.dataentry.bean.UploadParam;
import java.util.List;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.map.data.TaskDataContext;

public interface IUploadJioNoticeManageService {
    public void doBeforeImportNotice(String var1, String var2, UploadParam var3);

    public void doAfterImportNotice(TaskDataContext var1, JIOImportResultObject var2, List<String> var3);
}

