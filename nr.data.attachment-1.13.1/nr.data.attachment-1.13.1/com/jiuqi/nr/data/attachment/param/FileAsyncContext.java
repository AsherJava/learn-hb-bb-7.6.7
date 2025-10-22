/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.param.CommonParams
 */
package com.jiuqi.nr.data.attachment.param;

import com.jiuqi.nr.data.attachment.param.ImpParams;
import com.jiuqi.nr.data.common.param.CommonParams;

public class FileAsyncContext {
    private ImpParams params;
    private CommonParams commonParams;

    public ImpParams getParams() {
        return this.params;
    }

    public void setParams(ImpParams params) {
        this.params = params;
    }

    public CommonParams getCommonParams() {
        return this.commonParams;
    }

    public void setCommonParams(CommonParams commonParams) {
        this.commonParams = commonParams;
    }

    public FileAsyncContext(ImpParams params, CommonParams commonParams) {
        this.params = params;
        this.commonParams = commonParams;
    }

    public FileAsyncContext() {
    }
}

