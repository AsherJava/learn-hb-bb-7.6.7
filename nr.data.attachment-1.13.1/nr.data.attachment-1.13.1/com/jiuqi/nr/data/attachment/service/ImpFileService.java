/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.CommonMessage
 *  com.jiuqi.nr.data.common.Message
 *  com.jiuqi.nr.data.common.param.CommonParams
 */
package com.jiuqi.nr.data.attachment.service;

import com.jiuqi.nr.data.attachment.param.ImpParams;
import com.jiuqi.nr.data.common.CommonMessage;
import com.jiuqi.nr.data.common.Message;
import com.jiuqi.nr.data.common.param.CommonParams;
import java.io.IOException;

public interface ImpFileService {
    public Message<CommonMessage> uploadFileds(ImpParams var1, CommonParams var2) throws IOException;

    @Deprecated
    public String uploadFiledsAsync(ImpParams var1, CommonParams var2) throws IOException;
}

