/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.Pair
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.IsolationParamContext
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.vo.OrgMappingVO
 *  com.jiuqi.dc.mappingscheme.impl.define.IPluginType
 *  com.jiuqi.dc.mappingscheme.impl.define.gather.impl.PluginTypeGather
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 *  com.jiuqi.dc.mappingscheme.impl.enums.StorageType
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.dc.datamapping.impl.utils;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.Pair;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.IsolationParamContext;
import com.jiuqi.dc.datamapping.impl.enums.RefDynamicField;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.vo.OrgMappingVO;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.impl.PluginTypeGather;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import com.jiuqi.dc.mappingscheme.impl.enums.StorageType;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class IsolationUtil {
    public static IsolationParamContext buildIsolationParam(DataRefDTO dto, String isolationStrategy) {
        IsolationParamContext isolationParamContext = new IsolationParamContext(dto.getDataSchemeCode());
        List fieldList = IsolationStrategy.getIsolationFieldByCode((String)isolationStrategy);
        if (CollectionUtils.isEmpty((Collection)fieldList)) {
            return isolationParamContext;
        }
        String unitCode = fieldList.contains("DC_UNITCODE") ? dto.getValueStr("DC_UNITCODE") : null;
        String bookCode = fieldList.contains("ODS_BOOKCODE") ? dto.getValueStr("ODS_BOOKCODE") : null;
        Integer acctYear = null;
        if (fieldList.contains("ODS_ACCTYEAR") && Objects.nonNull(dto.get((Object)"ODS_ACCTYEAR"))) {
            acctYear = Integer.valueOf(dto.get((Object)"ODS_ACCTYEAR").toString());
        }
        isolationParamContext.setUnitCode(unitCode);
        isolationParamContext.setBookCode(bookCode);
        isolationParamContext.setAcctYear(acctYear);
        return isolationParamContext;
    }

    public static IsolationParamContext buildIsolationParam(DataRefDTO dto, BaseDataMappingDefineDTO define) {
        IsolationParamContext isolationParamContext = IsolationUtil.buildIsolationParam(dto, define.getIsolationStrategy());
        if (IsolationUtil.listDynamicField(define).contains("ODS_ASSISTCODE")) {
            isolationParamContext.setAssistCode(dto.getValueStr("ODS_ASSISTCODE"));
        }
        return isolationParamContext;
    }

    public static List<FieldMappingDefineDTO> buildIsolationFieldDefine(String isolationStrategy) {
        ArrayList result = CollectionUtils.newArrayList();
        List fieldList = IsolationStrategy.getIsolationFieldByCode((String)isolationStrategy);
        if (CollectionUtils.isEmpty((Collection)fieldList)) {
            return result;
        }
        for (String field : fieldList) {
            FieldMappingDefineDTO fieldMappingDefineDTO = new FieldMappingDefineDTO(field, RefDynamicField.getFieldTitle(field), null);
            fieldMappingDefineDTO.setFixedFlag(Integer.valueOf(1));
            result.add(fieldMappingDefineDTO);
        }
        return result;
    }

    public static Set<String> listDynamicField(BaseDataMappingDefineDTO dto) {
        return IsolationUtil.listDynamicField(dto, null);
    }

    public static Set<String> listDynamicField(BaseDataMappingDefineDTO dto, Set<String> isolationList) {
        HashSet result = CollectionUtils.newHashSet();
        String isolationStrategy = dto.getIsolationStrategy();
        if (IsolationStrategy.UNITCODE.getCode().equals(isolationStrategy)) {
            result.add("ODS_UNITCODE");
        }
        result.addAll(IsolationStrategy.getIsolationFieldByCode((String)isolationStrategy));
        if (!CollectionUtils.isEmpty(isolationList)) {
            for (String isolationCode : isolationList) {
                result.addAll(IsolationStrategy.getIsolationFieldByCode((String)isolationCode));
            }
        }
        for (FieldMappingDefineDTO fieldMappingDefine : dto.getItems()) {
            if (!RefDynamicField.containsRefFieldName(fieldMappingDefine.getFieldName(), "MD_ORG".equals(dto.getCode()) ? null : Integer.valueOf(1)) || StringUtils.isEmpty((String)fieldMappingDefine.getOdsFieldName())) continue;
            result.add(RefDynamicField.getFieldName(fieldMappingDefine.getFieldName()));
        }
        return result;
    }

    public static List<Pair<String, String>> listDynamicRefField(BaseDataMappingDefineDTO dto) {
        ArrayList result = CollectionUtils.newArrayList();
        String isolationStrategy = dto.getIsolationStrategy();
        for (String field : IsolationStrategy.getIsolationFieldByCode((String)isolationStrategy)) {
            result.add(new Pair((Object)field, (Object)RefDynamicField.getRefFieldName(field)));
        }
        for (FieldMappingDefineDTO fieldMappingDefine : dto.getItems()) {
            if (!RefDynamicField.containsRefFieldName(fieldMappingDefine.getFieldName(), "MD_ORG".equals(dto.getCode()) ? null : Integer.valueOf(1)) || StringUtils.isEmpty((String)fieldMappingDefine.getOdsFieldName())) continue;
            result.add(new Pair((Object)RefDynamicField.getFieldName(fieldMappingDefine.getFieldName()), (Object)fieldMappingDefine.getOdsFieldName()));
        }
        return result;
    }

    public static List<RefDynamicField> listDynamicRefFieldAndTitle(BaseDataMappingDefineDTO dto) {
        ArrayList result = CollectionUtils.newArrayList();
        String isolationStrategy = dto.getIsolationStrategy();
        for (String field : IsolationStrategy.getIsolationFieldByCode((String)isolationStrategy)) {
            result.add(RefDynamicField.valueOf(field));
        }
        for (FieldMappingDefineDTO fieldMappingDefine : dto.getItems()) {
            if (!RefDynamicField.containsRefFieldName(fieldMappingDefine.getFieldName(), "MD_ORG".equals(dto.getCode()) ? null : Integer.valueOf(1)) || StringUtils.isEmpty((String)fieldMappingDefine.getOdsFieldName())) continue;
            String fieldName = RefDynamicField.getFieldName(fieldMappingDefine.getFieldName());
            result.add(RefDynamicField.valueOf(fieldName));
        }
        return result;
    }

    public static Pair<Boolean, String> buildAdvancedSqlWithUnit(BaseDataMappingDefineDTO dto) {
        String advancedSql = dto.getAdvancedSql();
        if (!IsolationStrategy.getIsolationFieldByCode((String)dto.getIsolationStrategy()).contains("DC_UNITCODE")) {
            return new Pair((Object)false, (Object)advancedSql);
        }
        DataSchemeService schemeService = (DataSchemeService)ApplicationContextRegister.getBean(DataSchemeService.class);
        DataSchemeDTO dataSchemeDTO = schemeService.getByCode(dto.getDataSchemeCode());
        PluginTypeGather pluginTypeGather = (PluginTypeGather)ApplicationContextRegister.getBean(PluginTypeGather.class);
        IPluginType pluginType = pluginTypeGather.getPluginType(dataSchemeDTO.getPluginType());
        if (!StorageType.ID.getCode().equals(pluginType.storageType())) {
            return new Pair((Object)false, (Object)advancedSql);
        }
        OrgMappingVO orgMapping = dataSchemeDTO.getDataMapping().getOrgMapping();
        String orgMappingAdvancedSql = orgMapping.getAdvancedSql();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT TEMP1.*, TEMP2.CODE AS REAL_UNITCODE \n");
        sb.append("  FROM ( \n");
        sb.append(advancedSql);
        sb.append(") TEMP1 \n");
        sb.append("  LEFT JOIN (");
        sb.append(orgMappingAdvancedSql);
        sb.append(") TEMP2 \n");
        sb.append("  ON TEMP1.UNITCODE = TEMP2.ID");
        return new Pair((Object)true, (Object)sb.toString());
    }
}

