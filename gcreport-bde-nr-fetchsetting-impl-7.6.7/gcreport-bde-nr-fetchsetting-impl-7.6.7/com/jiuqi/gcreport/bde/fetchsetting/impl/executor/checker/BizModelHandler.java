/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;

public interface BizModelHandler {
    public String getBizModelCode();

    public boolean basicCheck(ExcelRowFetchSettingVO var1, BizModelDTO var2);

    public boolean doCheck(ExcelRowFetchSettingVO var1, BizModelDTO var2);

    public ExcelRowFetchSettingVO exportDataHandle(FixedFetchSourceRowSettingVO var1, BizModelDTO var2);

    public ExcelRowFetchSettingVO importDataHandle(ExcelRowFetchSettingVO var1, BizModelDTO var2);
}

