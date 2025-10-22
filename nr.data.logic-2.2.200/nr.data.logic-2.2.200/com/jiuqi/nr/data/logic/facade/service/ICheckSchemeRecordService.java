/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.service;

import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.output.CheckSchemeRecordDTO;

public interface ICheckSchemeRecordService {
    public CheckSchemeRecordDTO queryCheckSchemeRecord(String var1);

    public void saveCheckSchemeRecord(CheckResultQueryParam var1);

    public int deleteCheckSchemeRecord(String var1);
}

