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
public class AssReclassCflBalanceHandler
extends AbstractBizModelHandler {
    @Override
    public String getBizModelCode() {
        return ComputationModelEnum.ASSRECLASSIFYBALANCE.getCode();
    }

    @Override
    public boolean basicCheck(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getSign())) {
            excelRowFetchSettingVO.setErrorLog("\u8fd0\u7b97\u7b26\u4e0d\u80fd\u4e3a\u7a7a");
            return false;
        }
        if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getFetchTypeName())) {
            excelRowFetchSettingVO.setErrorLog("\u53d6\u6570\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
            return false;
        }
        if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getReclassSrcSubjCode())) {
            excelRowFetchSettingVO.setErrorLog("\u91cd\u5206\u7c7b\u524d\u79d1\u76ee\u4e0d\u80fd\u4e3a\u7a7a");
            return false;
        }
        if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getReclassSubjCode())) {
            excelRowFetchSettingVO.setErrorLog("\u91cd\u5206\u7c7b\u540e\u79d1\u76ee\u4e0d\u80fd\u4e3a\u7a7a");
            return false;
        }
        return this.checkAssItemRequire(excelRowFetchSettingVO, bizModelDTO);
    }
}

