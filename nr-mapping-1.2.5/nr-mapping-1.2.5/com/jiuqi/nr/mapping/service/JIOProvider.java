/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.service;

import com.jiuqi.nr.mapping.dto.JIOUploadParam;
import com.jiuqi.nr.mapping.web.vo.Result;

public interface JIOProvider {
    public Result execute(byte[] var1, String var2, JIOUploadParam var3);
}

