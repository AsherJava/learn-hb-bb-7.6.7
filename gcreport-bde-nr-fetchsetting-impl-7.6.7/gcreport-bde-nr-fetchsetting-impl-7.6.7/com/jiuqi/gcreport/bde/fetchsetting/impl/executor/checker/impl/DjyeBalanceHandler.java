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
public class DjyeBalanceHandler
extends AbstractBizModelHandler {
    @Override
    public String getBizModelCode() {
        return ComputationModelEnum.DJYEBALANCE.getCode();
    }

    @Override
    public boolean basicCheck(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        if (!super.basicCheck(excelRowFetchSettingVO, bizModelDTO)) {
            return false;
        }
        if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getDimTypeName())) {
            excelRowFetchSettingVO.setErrorLog("\u62b5\u51cf\u7ef4\u5ea6\u4e0d\u80fd\u4e3a\u7a7a");
            return false;
        }
        if (!excelRowFetchSettingVO.getSubjectCode().contains(",")) {
            excelRowFetchSettingVO.setErrorLog("\u62b5\u51cf\u4f59\u989d\u79d1\u76ee\u4e0d\u80fd\u4e3a\u5355\u9009\u6216\u8303\u56f4\uff0c\u4ec5\u652f\u6301\u591a\u503c");
            return false;
        }
        return this.checkAssItemExist(excelRowFetchSettingVO.getDimTypeName(), bizModelDTO) && this.checkAssItemRequire(excelRowFetchSettingVO, bizModelDTO);
    }

    @Override
    public boolean doCheck(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        return true;
    }
}

