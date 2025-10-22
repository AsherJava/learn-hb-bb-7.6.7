/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.sensitive.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.sensitive.bean.ExportData;
import com.jiuqi.nr.sensitive.bean.viewObject.SensitiveWordViewObject;
import com.jiuqi.nr.sensitive.common.ResponseResult;
import com.jiuqi.nr.sensitive.common.ResultObject;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface SensitiveWordService {
    public List<SensitiveWordViewObject> queryAllSensitiveWord(Integer var1, Integer var2, Integer var3);

    public List<SensitiveWordViewObject> getSensitiveWordWithType(String var1, Integer var2, Integer var3, Integer var4);

    public ResponseResult<Boolean> insertSensitiveWord(SensitiveWordViewObject var1);

    public ResponseResult<Boolean> updateSensitiveWord(SensitiveWordViewObject var1);

    public Boolean deleteSensitiveWord(List<String> var1);

    public ExportData exportAllSensitiveWord();

    public ResultObject importAllSensitiveWord(MultipartFile var1);

    public Integer batchCheckSensitiveWord(String var1, AsyncTaskMonitor var2) throws Exception;
}

