/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl.AbstractBizModelHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.ImportInnerColumnUtil;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class BaseDataHandler
extends AbstractBizModelHandler {
    @Override
    public String getBizModelCode() {
        return ComputationModelEnum.BASEDATA.getCode();
    }

    @Override
    public boolean basicCheck(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getBaseDataCode())) {
            excelRowFetchSettingVO.setErrorLog("\u57fa\u7840\u6570\u636e\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a");
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
        excelRowFetchSettingVO.setBaseDataCode(fixedFetchSourceRowSettingVO.getSubjectCode());
        excelRowFetchSettingVO.setSubjectCode("");
        List<String> fetchTypeNames = Arrays.asList(ImportInnerColumnUtil.getBizModelFieldName(bizModelDTO).split(","));
        for (int i = 0; i < FetchSettingNrUtil.getBizModelFields(bizModelDTO).size(); ++i) {
            if (!excelRowFetchSettingVO.getFetchType().equals(FetchSettingNrUtil.getBizModelFields(bizModelDTO).get(i))) continue;
            excelRowFetchSettingVO.setFetchTypeName(fetchTypeNames.get(i));
        }
        return excelRowFetchSettingVO;
    }

    @Override
    public ExcelRowFetchSettingVO importDataHandle(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        ExcelRowFetchSettingVO rowImpSettingVO = new ExcelRowFetchSettingVO();
        BeanUtils.copyProperties((Object)excelRowFetchSettingVO, (Object)rowImpSettingVO);
        List<String> fetchTypeNames = Arrays.asList(ImportInnerColumnUtil.getBizModelFieldName(bizModelDTO).split(","));
        for (int i = 0; i < fetchTypeNames.size(); ++i) {
            if (!excelRowFetchSettingVO.getFetchTypeName().equals(fetchTypeNames.get(i))) continue;
            rowImpSettingVO.setFetchType(FetchSettingNrUtil.getBizModelFields(bizModelDTO).get(i));
            break;
        }
        if (StringUtils.isEmpty((String)rowImpSettingVO.getFetchType())) {
            throw new BusinessRuntimeException("\u53d6\u6570\u7c7b\u578b\u914d\u7f6e\u9519\u8bef");
        }
        rowImpSettingVO.setSubjectCode(excelRowFetchSettingVO.getBaseDataCode());
        return rowImpSettingVO;
    }
}

