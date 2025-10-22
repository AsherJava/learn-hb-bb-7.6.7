/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.CommonMessage
 *  com.jiuqi.nr.data.common.Message
 *  com.jiuqi.nr.data.common.param.CommonParams
 */
package com.jiuqi.nr.data.excel.service;

import com.jiuqi.nr.data.common.CommonMessage;
import com.jiuqi.nr.data.common.Message;
import com.jiuqi.nr.data.common.param.CommonParams;
import com.jiuqi.nr.data.excel.param.UploadParam;

public interface IUploadExcelService {
    public Message<CommonMessage> upload(UploadParam var1, CommonParams var2) throws Exception;
}

