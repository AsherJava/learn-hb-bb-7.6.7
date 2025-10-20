/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.constant.SumTypeEnum
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.constant.SumTypeEnum;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl.AbstractBizModelHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AssCflBalanceHandler
extends AbstractBizModelHandler {
    @Override
    public String getBizModelCode() {
        return ComputationModelEnum.ASSCFLBALANCE.getCode();
    }

    @Override
    public boolean basicCheck(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        if (!super.basicCheck(excelRowFetchSettingVO, bizModelDTO)) {
            return false;
        }
        if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getDimTypeName())) {
            excelRowFetchSettingVO.setErrorLog("\u91cd\u5206\u7c7b\u7ef4\u5ea6\u4e0d\u80fd\u4e3a\u7a7a");
            return false;
        }
        if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getSumTypeName())) {
            excelRowFetchSettingVO.setErrorLog("\u6c47\u603b\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
            return false;
        }
        return this.checkAssItemExist(excelRowFetchSettingVO.getDimTypeName(), bizModelDTO) && this.checkAssItemRequire(excelRowFetchSettingVO, bizModelDTO);
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
        if (!StringUtils.isEmpty((String)excelRowFetchSettingVO.getSumTypeName())) {
            rowImpSettingVO.setSumType(SumTypeEnum.getSumTypeEnumByName((String)excelRowFetchSettingVO.getSumTypeName()).getCode());
        }
        return rowImpSettingVO;
    }
}

