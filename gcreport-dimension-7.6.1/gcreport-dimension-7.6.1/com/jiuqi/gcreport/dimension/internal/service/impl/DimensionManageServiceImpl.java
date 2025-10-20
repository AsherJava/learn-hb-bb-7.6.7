/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.internal.service.impl;

import com.jiuqi.gcreport.dimension.internal.service.DimensionManageService;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionQueryVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DimensionManageServiceImpl
implements DimensionManageService {
    @Autowired
    private DimensionService dimensionService;

    @Override
    public List<DimensionQueryVO> listDimensions() {
        List<DimensionVO> dimensionVOS = this.dimensionService.loadAllDimensions();
        ArrayList<DimensionQueryVO> res = new ArrayList<DimensionQueryVO>();
        for (DimensionVO dimVo : dimensionVOS) {
            DimensionQueryVO dimensionQueryVO = new DimensionQueryVO();
            dimensionQueryVO.setId(dimVo.getId());
            dimensionQueryVO.setCode(dimVo.getCode());
            dimensionQueryVO.setTitle(dimVo.getTitle());
            dimensionQueryVO.setFieldType(dimVo.getFieldType());
            dimensionQueryVO.setFieldSize(dimVo.getFieldSize());
            dimensionQueryVO.setFieldDecimal(dimVo.getFieldDecimal());
            dimensionQueryVO.setReferField(dimVo.getReferField());
            dimensionQueryVO.setReferTable(dimVo.getReferTable());
            dimensionQueryVO.setDescription(dimVo.getDescription());
            dimensionQueryVO.setPublishedFlag(dimVo.getPublishedFlag());
            dimensionQueryVO.setCreator(dimVo.getCreator());
            dimensionQueryVO.setCreateTime(dimVo.getCreateTime());
            dimensionQueryVO.setUpdateTime(dimVo.getUpdateTime());
            dimensionQueryVO.setConvertByOpposite(dimVo.getConvertByOpposite());
            dimensionQueryVO.setPeriodMappingFlag(dimVo.getPeriodMappingFlag());
            dimensionQueryVO.setMatchRule(dimVo.getMatchRule());
            dimensionQueryVO.setGroupDimFlag(dimVo.getGroupDimFlag());
            dimensionQueryVO.setDictTableName(dimVo.getDictTableName());
            dimensionQueryVO.setEffectScopeCodes(dimVo.getEffectScopeCodes());
            dimensionQueryVO.setFieldPrecision(dimVo.getFieldPrecision());
            res.add(dimensionQueryVO);
        }
        return res;
    }
}

