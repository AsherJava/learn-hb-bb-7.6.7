/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.define.gather;

import com.jiuqi.bde.bizmodel.define.match.IBaseDataMatcher;

public interface IBaseDataMatcherGather {
    public IBaseDataMatcher getSubjectMather();

    public IBaseDataMatcher getAssistMatcher(String var1);
}

