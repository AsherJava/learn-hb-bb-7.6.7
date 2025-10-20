/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl.AbstractBizModelHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TfvHandler
extends AbstractBizModelHandler {
    @Override
    public String getBizModelCode() {
        return ComputationModelEnum.TFV.getCode();
    }

    @Override
    public boolean basicCheck(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getSign())) {
            excelRowFetchSettingVO.setErrorLog("\u8fd0\u7b97\u7b26\u4e0d\u80fd\u4e3a\u7a7a");
            return false;
        }
        if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getFormula())) {
            excelRowFetchSettingVO.setErrorLog("\u81ea\u5b9a\u4e49SQL\u4e0d\u80fd\u4e3a\u7a7a");
            return false;
        }
        return true;
    }

    @Override
    public boolean doCheck(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        return true;
    }

    @Override
    public ExcelRowFetchSettingVO exportDataHandle(FixedFetchSourceRowSettingVO fixedFetchSourceRowSettingVO, BizModelDTO bizModelDTO) {
        ExcelRowFetchSettingVO excelRowFetchSettingVO = new ExcelRowFetchSettingVO(fixedFetchSourceRowSettingVO);
        return excelRowFetchSettingVO;
    }

    @Override
    public ExcelRowFetchSettingVO importDataHandle(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        ExcelRowFetchSettingVO rowImpSettingVO = new ExcelRowFetchSettingVO();
        BeanUtils.copyProperties((Object)excelRowFetchSettingVO, (Object)rowImpSettingVO);
        return rowImpSettingVO;
    }
}

