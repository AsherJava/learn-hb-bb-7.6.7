/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl.AbstractBizModelHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import org.springframework.stereotype.Component;

@Component
public class XjllBalanceHandler
extends AbstractBizModelHandler {
    @Override
    public String getBizModelCode() {
        return ComputationModelEnum.XJLLBALANCE.getCode();
    }

    @Override
    public boolean basicCheck(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getSign())) {
            excelRowFetchSettingVO.setErrorLog("\u8fd0\u7b97\u7b26\u4e0d\u80fd\u4e3a\u7a7a");
            return false;
        }
        if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getCashCode())) {
            excelRowFetchSettingVO.setErrorLog("\u73b0\u6d41\u9879\u76ee\u4e0d\u80fd\u4e3a\u7a7a");
            return false;
        }
        return true;
    }

    @Override
    public boolean doCheck(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        return true;
    }
}

