/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.vo.Dimension
 *  com.jiuqi.bde.common.constant.AgingPeriodTypeEnum
 *  com.jiuqi.bde.common.constant.FetchFixedFieldEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.constant.SumTypeEnum
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO;
import com.jiuqi.bde.bizmodel.client.vo.Dimension;
import com.jiuqi.bde.common.constant.AgingPeriodTypeEnum;
import com.jiuqi.bde.common.constant.FetchFixedFieldEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.constant.SumTypeEnum;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.BizModelHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import org.springframework.beans.BeanUtils;

public abstract class AbstractBizModelHandler
implements BizModelHandler {
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
        if (StringUtils.isEmpty((String)excelRowFetchSettingVO.getSubjectCode())) {
            excelRowFetchSettingVO.setErrorLog("\u79d1\u76ee\u4e0d\u80fd\u4e3a\u7a7a");
            return false;
        }
        if (!StringUtils.isEmpty((String)excelRowFetchSettingVO.getExcludeSubjectCode()) && excelRowFetchSettingVO.getExcludeSubjectCode().contains(":")) {
            excelRowFetchSettingVO.setErrorLog("\u6392\u9664\u79d1\u76ee\u4e0d\u80fd\u4e3a\u8303\u56f4");
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
        excelRowFetchSettingVO.setFetchTypeName(FetchTypeEnum.getEnumByCode((String)fixedFetchSourceRowSettingVO.getFetchType()).getName());
        if (!StringUtils.isEmpty((String)fixedFetchSourceRowSettingVO.getSumType())) {
            excelRowFetchSettingVO.setSumTypeName(SumTypeEnum.getSumTypeEnumByCode((String)fixedFetchSourceRowSettingVO.getSumType()).getName());
        }
        if (!StringUtils.isEmpty((String)fixedFetchSourceRowSettingVO.getAgingRangeType())) {
            excelRowFetchSettingVO.setAgingRangeTypeName(AgingPeriodTypeEnum.fromCode((String)fixedFetchSourceRowSettingVO.getAgingRangeType()).getName());
        }
        return excelRowFetchSettingVO;
    }

    @Override
    public ExcelRowFetchSettingVO importDataHandle(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        ExcelRowFetchSettingVO rowImpSettingVO = new ExcelRowFetchSettingVO();
        BeanUtils.copyProperties((Object)excelRowFetchSettingVO, (Object)rowImpSettingVO);
        rowImpSettingVO.setFetchType(FetchTypeEnum.getEnumByName((String)excelRowFetchSettingVO.getFetchTypeName()).getCode());
        return rowImpSettingVO;
    }

    protected boolean checkAssItemRequire(ExcelRowFetchSettingVO excelRowFetchSettingVO, BizModelDTO bizModelDTO) {
        FinBizModelDTO finBizModelDTO = (FinBizModelDTO)bizModelDTO;
        for (Dimension dimensionCode : finBizModelDTO.getDimensions()) {
            if (!dimensionCode.getRequired().booleanValue() || excelRowFetchSettingVO.getDimSettingValueMap().get(dimensionCode.getDimensionCode()) != null) continue;
            excelRowFetchSettingVO.setErrorLog(String.format("\u3010%1$s\u3011\u7ef4\u5ea6\u4e0d\u80fd\u4e3a\u7a7a", finBizModelDTO.getDimensionMap().get(dimensionCode.getDimensionCode())));
            return false;
        }
        return true;
    }

    protected boolean checkAssItemExist(String dimensionNameStr, BizModelDTO bizModelDTO) {
        FinBizModelDTO finBizModelDTO = (FinBizModelDTO)bizModelDTO;
        for (String dimensionName : dimensionNameStr.split(",")) {
            if (finBizModelDTO.getDimensionMap() == null || finBizModelDTO.getDimensionMap().containsValue(dimensionName) || FetchFixedFieldEnum.SUBJECTCODE.getName().equals(dimensionName)) continue;
            return false;
        }
        return true;
    }
}

