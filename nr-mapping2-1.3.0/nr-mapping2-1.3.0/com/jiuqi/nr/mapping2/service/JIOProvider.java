/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.service;

import com.jiuqi.nr.mapping2.dto.JIOUploadParam;
import com.jiuqi.nr.mapping2.web.vo.Result;

public interface JIOProvider {
    public Result execute(String var1, byte[] var2, String var3, JIOUploadParam var4);
}

