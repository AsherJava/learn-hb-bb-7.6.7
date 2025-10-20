/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.common.constant.AgingPeriodTypeEnum
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.common.constant.AgingPeriodTypeEnum;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl.AbstractBizModelHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AgingBalanceHandler
extends AbstractBizModelHandler {
    @Override
    public String getBizModelCode() {
        return ComputationModelEnum.AGINGBALANCE.getCode();
    }

    @Override
    public boolean basicCheck(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        if (!super.basicCheck(excelRowFetchSettingVO, bizModelDTO)) {
            return false;
        }
        if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getAgingRangeTypeName())) {
            excelRowFetchSettingVO.setErrorLog("\u533a\u95f4\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
            return false;
        }
        if (excelRowFetchSettingVO.getAgingRangeStart() == null) {
            excelRowFetchSettingVO.setErrorLog("\u8d77\u59cb\u4e0d\u80fd\u4e3a\u7a7a");
            return false;
        }
        if (excelRowFetchSettingVO.getAgingRangeEnd() == null) {
            excelRowFetchSettingVO.setErrorLog("\u622a\u81f3\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
            return false;
        }
        return true;
    }

    @Override
    public boolean doCheck(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        return true;
    }

    @Override
    public ExcelRowFetchSettingVO importDataHandle(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        ExcelRowFetchSettingVO rowImpSettingVO = new ExcelRowFetchSettingVO();
        BeanUtils.copyProperties((Object)excelRowFetchSettingVO, (Object)rowImpSettingVO);
        rowImpSettingVO.setFetchType(FetchTypeEnum.getEnumByName((String)excelRowFetchSettingVO.getFetchTypeName()).getCode());
        if (!StringUtils.isEmpty((String)excelRowFetchSettingVO.getAgingRangeTypeName())) {
            rowImpSettingVO.setAgingRangeType(AgingPeriodTypeEnum.getAgingPeriodTypeEnumByName((String)excelRowFetchSettingVO.getAgingRangeTypeName()).getCode());
        }
        return rowImpSettingVO;
    }
}

