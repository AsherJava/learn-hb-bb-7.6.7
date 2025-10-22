/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.param.CommonParams
 */
package com.jiuqi.nr.data.text.param;

import com.jiuqi.nr.data.common.param.CommonParams;
import com.jiuqi.nr.data.text.param.TextParams;

public class TextAsyncContext {
    private TextParams params;
    private CommonParams commonParams;

    public TextParams getParams() {
        return this.params;
    }

    public void setParams(TextParams params) {
        this.params = params;
    }

    public CommonParams getCommonParams() {
        return this.commonParams;
    }

    public void setCommonParams(CommonParams commonParams) {
        this.commonParams = commonParams;
    }

    public TextAsyncContext(TextParams params, CommonParams commonParams) {
        this.params = params;
        this.commonParams = commonParams;
    }

    public TextAsyncContext() {
    }
}

