/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.dc.mappingscheme.impl.util;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeOptionService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

public class ColumnExtendUtil {
    private static final Logger logger = LoggerFactory.getLogger(ColumnExtendUtil.class);

    public static List<DefinitionFieldV> getExtendFieldList(List<DefinitionFieldV> dimColumnList, boolean isCf) {
        ArrayList extendColumnList = CollectionUtils.newArrayList();
        HashSet oppositeDimSet = CollectionUtils.newHashSet();
        if (Boolean.FALSE.equals(isCf)) {
            try {
                List<DataSchemeDTO> dataSchemeDTOS = ((DataSchemeService)ApplicationContextRegister.getBean(DataSchemeService.class)).listAll();
                DataSchemeOptionService dataSchemeOptionService = (DataSchemeOptionService)ApplicationContextRegister.getBean(DataSchemeOptionService.class);
                if (!CollectionUtils.isEmpty(dataSchemeDTOS)) {
                    for (DataSchemeDTO dataSchemeDTO : dataSchemeDTOS) {
                        List<DimensionVO> dimensionVOS = dataSchemeOptionService.listOppositeDimensionByScheme(dataSchemeDTO.getCode());
                        if (CollectionUtils.isEmpty(dimensionVOS)) continue;
                        oppositeDimSet.addAll(dimensionVOS.stream().map(DimensionVO::getCode).collect(Collectors.toList()));
                    }
                }
            }
            catch (Exception e) {
                logger.error("\u83b7\u53d6\u5bf9\u65b9\u5206\u5f55\u7ef4\u5ea6\u5931\u8d25", e);
            }
        }
        List dimensions = ((DimensionService)ApplicationContextRegister.getBean(DimensionService.class)).loadAllDimensions();
        Map<String, DimensionVO> dimensionMap = dimensions.stream().collect(Collectors.toMap(DimensionVO::getCode, dimension -> dimension, (existing, replacement) -> existing));
        for (DefinitionFieldV definitionFieldV : dimColumnList) {
            DimensionVO dimensionVO = dimensionMap.get(definitionFieldV.getCode());
            if (!ObjectUtils.isEmpty(dimensionVO) && "dims".equals(dimensionVO.getDimensionType())) {
                extendColumnList.add(ColumnExtendUtil.getDefinitionFieldV(definitionFieldV));
            }
            if (!oppositeDimSet.contains(definitionFieldV.getCode())) continue;
            DefinitionFieldV fieldV = ColumnExtendUtil.getDefinitionFieldV(definitionFieldV);
            fieldV.setCode("SRC" + definitionFieldV.getFieldName());
            fieldV.setFieldName("SRC" + definitionFieldV.getFieldName());
            fieldV.setEntityFieldName("SRC" + definitionFieldV.getFieldName());
            fieldV.setTitle("\u5bf9\u65b9\u5206\u5f55" + definitionFieldV.getTitle());
            fieldV.setDefaultValue("#");
            extendColumnList.add(fieldV);
        }
        return extendColumnList;
    }

    private static DefinitionFieldV getDefinitionFieldV(DefinitionFieldV definitionFieldV) {
        DefinitionFieldV fieldV = new DefinitionFieldV();
        fieldV.setKey(UUIDUtils.newHalfGUIDStr());
        fieldV.setNullable(true);
        fieldV.setCode(String.format("%1$s_SRCCODE", definitionFieldV.getFieldName()));
        fieldV.setFieldName(String.format("%1$s_SRCCODE", definitionFieldV.getFieldName()));
        fieldV.setEntityFieldName(String.format("%1$s_SRCCODE", definitionFieldV.getFieldName()));
        fieldV.setTitle("\u6e90\u6838\u7b97" + definitionFieldV.getTitle());
        fieldV.setFieldValueType(DBColumn.DBType.NVarchar.getType());
        fieldV.setDbType(DBColumn.DBType.NVarchar);
        fieldV.setSize(definitionFieldV.getSize());
        fieldV.setDescription(definitionFieldV.getDescription());
        fieldV.setOrder(definitionFieldV.getOrder());
        fieldV.setDefaultValue(null);
        return fieldV;
    }
}

