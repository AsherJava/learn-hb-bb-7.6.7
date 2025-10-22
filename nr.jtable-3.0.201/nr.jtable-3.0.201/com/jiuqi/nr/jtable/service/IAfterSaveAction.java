/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.RegionDataCommitSet;
import java.util.Map;

public interface IAfterSaveAction {
    default public void afterDeleteSurvey(JtableContext jtableContext, Map<String, RegionDataCommitSet> commitData) {
    }
}

