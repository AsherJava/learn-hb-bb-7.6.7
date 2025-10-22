/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbrbill.dispatcher.operate;

import com.jiuqi.gcreport.clbrbill.dto.ClbrUpdateDataDTO;

public interface ClbrUpdateBusinessDataOperateHandler {
    public String getOperateType();

    public Object handler(ClbrUpdateDataDTO var1);
}

