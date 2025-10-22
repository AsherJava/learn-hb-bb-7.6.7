/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl.AbstractBizModelHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import org.springframework.stereotype.Component;

@Component
public class BwAssBalanceHandler
extends AbstractBizModelHandler {
    @Override
    public String getBizModelCode() {
        return "BWASSBALANCE";
    }

    @Override
    public boolean basicCheck(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        if (!super.basicCheck(excelRowFetchSettingVO, bizModelDTO)) {
            return false;
        }
        return this.checkAssItemRequire(excelRowFetchSettingVO, bizModelDTO);
    }

    @Override
    public boolean doCheck(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        return true;
    }
}

