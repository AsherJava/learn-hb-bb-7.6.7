/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.ExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.UploadVerifyType;
import java.util.List;
import java.util.Map;

public interface IForceUpload {
    public List<UploadVerifyType> isUpload(ExecuteTaskParam var1);

    public Map<String, List<UploadVerifyType>> isUpload(BatchExecuteTaskParam var1);
}

