/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil
 *  com.jiuqi.dc.mappingscheme.client.common.SchemeDefaultDataVO
 *  com.jiuqi.dc.mappingscheme.client.common.SchemeInitVO
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 */
package com.jiuqi.dc.mappingscheme.impl.init;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil;
import com.jiuqi.dc.mappingscheme.client.common.SchemeDefaultDataVO;
import com.jiuqi.dc.mappingscheme.client.common.SchemeInitVO;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.DataSchemeInit;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeInitializer;
import com.jiuqi.dc.mappingscheme.impl.define.IRuleType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IRuleTypeGather;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.dc.mappingscheme.impl.util.SchemeInitUtil;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDataSchemeInit
implements IDataSchemeInitializer {
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private IRuleTypeGather ruleTypeGather;

    @Override
    public void doInit(DataSchemeDTO schemeDto) {
    }

    @Override
    public SchemeDefaultDataVO getDefaultSchemeData(DataSchemeDTO schemeDto) {
        return null;
    }

    @Override
    public DataSchemeInit doAnalysis(DataSchemeDTO dto, String initSchemeData) {
        return null;
    }

    @Override
    public Map<String, Object> getRuleAndBaseData(Map<String, Object> tableData, Set<String> fieldNameSet, String odsFieldTitle) {
        Set odsFieldNameSet = fieldNameSet.stream().filter(s -> !StringUtils.isEmpty((String)s)).collect(Collectors.toSet());
        SchemeInitVO schemeInitVO = new SchemeInitVO();
        if (odsFieldNameSet.size() > 1) {
            schemeInitVO.setOdsFieldName("");
            schemeInitVO.setOdsFieldTitle("");
            tableData.put("ruleType", "");
            tableData.put("ruleTypeTitle", "");
        } else {
            ArrayList odsFieldNameList = new ArrayList(odsFieldNameSet);
            String odsFieldName = "";
            odsFieldName = odsFieldNameList.size() == 0 ? "" : (String)odsFieldNameList.get(0);
            schemeInitVO.setOdsFieldName(odsFieldName);
            schemeInitVO.setOdsFieldTitle(odsFieldTitle);
        }
        schemeInitVO.setEnable(Boolean.valueOf(true));
        tableData.put("BaseDataConvert", schemeInitVO);
        return tableData;
    }

    protected BaseDataMappingDefineDTO buildDataMappingDefineDTO(String dataSchemeCode, String code, String name, String advancedSql, String odsTableName, String ruleType, String autoMatchDim) {
        BaseDataMappingDefineDTO dataMappingDefineDTO = new BaseDataMappingDefineDTO();
        dataMappingDefineDTO.setCode(code);
        dataMappingDefineDTO.setName(name);
        dataMappingDefineDTO.setAdvancedSql(advancedSql);
        dataMappingDefineDTO.setRuleType(ruleType);
        dataMappingDefineDTO.setAutoMatchDim(autoMatchDim);
        dataMappingDefineDTO.setDataSchemeCode(dataSchemeCode);
        dataMappingDefineDTO.setItems(SchemeInitUtil.commonFieldInfo());
        return dataMappingDefineDTO;
    }

    protected BaseDataMappingDefineDTO buildOrgDefine(DataSchemeDTO dataScheme, String orgSql) {
        return this.buildDataMappingDefineDTO(dataScheme.getCode(), "MD_ORG", "\u7ec4\u7ec7\u673a\u6784", orgSql, "", RuleType.ITEM_BY_ITEM.getCode(), "NAME");
    }

    protected BaseDataMappingDefineDTO buildSubjectDefine(DataSchemeDTO dataScheme, String subjectSql, String ruleType) {
        String autoMatchDim = Optional.ofNullable(this.ruleTypeGather.getRuleTypeByCode(ruleType)).map(IRuleType::getItem2Item).orElse(Boolean.FALSE) != false ? "NAME" : "#";
        return this.buildDataMappingDefineDTO(dataScheme.getCode(), "MD_ACCTSUBJECT", "\u79d1\u76ee", subjectSql, "", ruleType, autoMatchDim);
    }

    protected BaseDataMappingDefineDTO buildCurrencyDefine(DataSchemeDTO dataScheme, String currencySql, String ruleType) {
        String autoMatchDim = Optional.ofNullable(this.ruleTypeGather.getRuleTypeByCode(ruleType)).map(IRuleType::getItem2Item).orElse(Boolean.FALSE) != false ? "NAME" : "#";
        return this.buildDataMappingDefineDTO(dataScheme.getCode(), "MD_CURRENCY", "\u5e01\u522b", currencySql, "", ruleType, autoMatchDim);
    }

    protected BaseDataMappingDefineDTO buildCfItemDefine(DataSchemeDTO dataScheme, String cfItemSql, String ruleType) {
        String autoMatchDim = Optional.ofNullable(this.ruleTypeGather.getRuleTypeByCode(ruleType)).map(IRuleType::getItem2Item).orElse(Boolean.FALSE) != false ? "NAME" : "#";
        return this.buildDataMappingDefineDTO(dataScheme.getCode(), "MD_CFITEM", "\u73b0\u6d41\u9879\u76ee", cfItemSql, "", ruleType, autoMatchDim);
    }

    protected String getDefaultSql(String dataSourceCode) {
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(dataSourceCode));
        return String.format(" SELECT 'ID' AS ID ,'CODE' AS CODE,'NAME' AS NAME %1$s ", StringUtils.isEmpty((String)sqlHandler.getVirtualTable()) ? "" : "FROM " + sqlHandler.getVirtualTable());
    }
}

