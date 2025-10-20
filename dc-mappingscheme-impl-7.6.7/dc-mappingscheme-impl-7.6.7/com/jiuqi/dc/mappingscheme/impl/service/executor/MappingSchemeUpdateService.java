/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.TableParseUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil
 *  com.jiuqi.dc.mappingscheme.client.common.Columns
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.vo.AdvancedMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.AssistMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.BizMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.DataMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.OrgMappingVO
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.base.DeployTableProcessor
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.mappingscheme.impl.service.executor;

import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.TableParseUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil;
import com.jiuqi.dc.mappingscheme.client.common.Columns;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.vo.AdvancedMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.AssistMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.BizMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.DataMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.OrgMappingVO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.dao.DataSchemeOptionDao;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.impl.PluginTypeGather;
import com.jiuqi.dc.mappingscheme.impl.domain.DataSchemeOptionDO;
import com.jiuqi.dc.mappingscheme.impl.enums.FieldMappingType;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import com.jiuqi.dc.mappingscheme.impl.enums.StorageType;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.dc.mappingscheme.impl.service.impl.DataSchemeServiceImpl;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.base.DeployTableProcessor;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class MappingSchemeUpdateService {
    private static final Logger logger = LoggerFactory.getLogger("com.jiuqi.dc.mappingscheme.impl.service.tableCheckAndSchemeUpdate");

    public void execute(String dataSourceCode) throws Exception {
        logger.info("\u6570\u636e\u6620\u5c04\u65b9\u6848\u5f00\u59cb\u5347\u7ea7");
        boolean tableExists = TableParseUtils.tableExist((String)dataSourceCode, (List)CollectionUtils.newArrayList((Object[])new String[]{"DC_REF_DATASCHEME", "DC_REF_DATAMAPINGDFINE", "DC_REF_FIELDMAPINGDFINE"}));
        if (!tableExists) {
            logger.info("\u6570\u636e\u6620\u5c04\u65b9\u6848\u65e7\u8868\u4e0d\u5b58\u5728\uff0c\u8df3\u8fc7\u6267\u884c");
            return;
        }
        logger.info("\u5f00\u59cb\u521d\u59cb\u5316\u6570\u636e\u6620\u5c04\u65b9\u6848\u8868\u7ed3\u6784");
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        BaseEntity entity = entityTableCollector.getEntityByName("DC_SCHEME_DATAMAPPING");
        TableDefineConvertHelper tableDefineConvertHelper = TableDefineConvertHelper.newInstance((BaseEntity)entity, (DBTable)entityTableCollector.getDbTableByType(entity.getClass()));
        DefinitionTableV define = tableDefineConvertHelper.convert();
        DeployTableProcessor.newInstance((DefinitionTableV)define).deploy();
        logger.info("\u521d\u59cb\u5316\u6570\u636e\u6620\u5c04\u65b9\u6848\u8868\u7ed3\u6784\u5b8c\u6bd5");
        logger.info("\u5f00\u59cb\u521d\u59cb\u5316\u6570\u636e\u6620\u5c04\u65b9\u6848\u7ba1\u63a7\u9009\u9879\u8868\u7ed3\u6784");
        BaseEntity optionEntity = entityTableCollector.getEntityByName("DC_SCHEME_DATAMAPPINGOPTION");
        TableDefineConvertHelper optionHelper = TableDefineConvertHelper.newInstance((BaseEntity)optionEntity, (DBTable)entityTableCollector.getDbTableByType(optionEntity.getClass()));
        DefinitionTableV optionDefine = optionHelper.convert();
        DeployTableProcessor.newInstance((DefinitionTableV)optionDefine).deploy();
        logger.info("\u521d\u59cb\u5316\u6570\u636e\u6620\u5c04\u65b9\u6848\u7ba1\u63a7\u9009\u9879\u8868\u7ed3\u6784\u5b8c\u6bd5");
        logger.info("\u5f00\u59cb\u8fc1\u79fb\u6570\u636e\u6620\u5c04\u65b9\u6848");
        JdbcTemplate sysJdbcTemplate = (JdbcTemplate)ApplicationContextRegister.getBean(JdbcTemplate.class);
        GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate((String)dataSourceCode);
        String sql = "SELECT ID, VER, CODE, NAME, PLUGINTYPE, DATASOURCECODE, SOURCEDATATYPE, CUSTOMCONFIG, STOPFLAG, ETLJOBID, ORGMAPPINGTYPE FROM DC_REF_DATASCHEME";
        List oldSchemeList = jdbcTemplate.query(sql, (RowMapper)new BeanPropertyRowMapper(DataSchemeDTO.class));
        String dataMapSql = "SELECT DATASCHEMECODE, MODELTYPE, CODE, NAME, ADVANCEDSQL, ORGMAPPINGTYPE, RULETYPE, AUTOMATCHDIM FROM DC_REF_DATAMAPINGDFINE";
        List oldDataMappingDefineList = jdbcTemplate.query(dataMapSql, (RowMapper)new BeanPropertyRowMapper(BaseDataMappingDefineDTO.class));
        String fieldMapSQl = "SELECT TABLENAME, DATASCHEMECODE, FIELDNAME, FIELDTITLE, FIELDMAPPINGTYPE, ODS_FIELDNAME AS odsFieldName, RULETYPE, FIXEDFLAG, ORDINAL FROM DC_REF_FIELDMAPINGDFINE";
        List oldFieldMappingDefineList = jdbcTemplate.query(fieldMapSQl, (RowMapper)new BeanPropertyRowMapper(FieldMappingDefineDTO.class));
        logger.info("\u83b7\u53d6\u5230\u65e7\u7684\u6570\u636e\u6620\u5c04\u65b9\u6848\u5171{}\u6761", (Object)oldSchemeList.size());
        if (CollectionUtils.isEmpty((Collection)oldSchemeList)) {
            logger.info("\u6ca1\u6709\u9700\u8981\u8fc1\u79fb\u7684\u6570\u636e");
            return;
        }
        DimensionService dimensionService = (DimensionService)ApplicationContextRegister.getBean(DimensionService.class);
        PluginTypeGather pluginTypeGather = (PluginTypeGather)ApplicationContextRegister.getBean(PluginTypeGather.class);
        DataSchemeServiceImpl dataSchemeService = (DataSchemeServiceImpl)ApplicationContextRegister.getBean(DataSchemeServiceImpl.class);
        DataSchemeOptionDao dataSchemeOptionDao = (DataSchemeOptionDao)ApplicationContextRegister.getBean(DataSchemeOptionDao.class);
        DataSourceService dataSourceService = (DataSourceService)ApplicationContextRegister.getBean(DataSourceService.class);
        Map<String, DimensionVO> dimensionMap = dimensionService.loadAllDimensions().stream().collect(Collectors.toMap(DimensionVO::getCode, item -> item, (o1, o2) -> o2));
        Map<String, List<BaseDataMappingDefineDTO>> schemeDataMappingMap = oldDataMappingDefineList.stream().collect(Collectors.groupingBy(DataMappingDefineDTO::getDataSchemeCode));
        Map<String, List<FieldMappingDefineDTO>> schemeFieldMappingMap = oldFieldMappingDefineList.stream().collect(Collectors.groupingBy(FieldMappingDefineDTO::getDataSchemeCode));
        for (DataSchemeDTO dataSchemeDTO : oldSchemeList) {
            try {
                logger.info("\u5f00\u59cb\u5904\u7406\u65b9\u6848{}", (Object)dataSchemeDTO.getCode());
                DataSchemeDTO newDataScheme = new DataSchemeDTO();
                BeanUtils.copyProperties(dataSchemeDTO, newDataScheme);
                DataMappingVO dataMappingVO = new DataMappingVO();
                ArrayList assistMappingList = CollectionUtils.newArrayList();
                ArrayList advancedMappingList = CollectionUtils.newArrayList();
                IPluginType pluginType = pluginTypeGather.getPluginType(dataSchemeDTO.getPluginType());
                ArrayList dataMappingDefineDTOS = schemeDataMappingMap.get(dataSchemeDTO.getCode());
                if (CollectionUtils.isEmpty(dataMappingDefineDTOS)) {
                    dataMappingDefineDTOS = CollectionUtils.newArrayList();
                }
                Set bizModels = dataMappingDefineDTOS.stream().filter(item -> "BIZDATA".equals(item.getModelType())).map(DataMappingDefineDTO::getCode).collect(Collectors.toSet());
                ArrayList fieldMappingDefineDTOS = schemeFieldMappingMap.get(dataSchemeDTO.getCode());
                if (CollectionUtils.isEmpty(fieldMappingDefineDTOS)) {
                    fieldMappingDefineDTOS = CollectionUtils.newArrayList();
                }
                Set<String> bizFields = "DC_STANDARD_MODEL".equals(dataSchemeDTO.getPluginType()) ? this.getStandModelFields(dataMappingDefineDTOS) : fieldMappingDefineDTOS.stream().filter(item -> bizModels.contains(item.getTableName())).map(FieldMappingDefineDTO::getFieldName).collect(Collectors.toSet());
                OrgMappingVO orgMappingVO = new OrgMappingVO();
                orgMappingVO.setCode("MD_ORG");
                orgMappingVO.setName("\u7ec4\u7ec7\u673a\u6784");
                orgMappingVO.setRuleType(RuleType.ALL.getCode());
                orgMappingVO.setOdsFieldName(dataSchemeDTO.getOrgMappingType());
                orgMappingVO.setOrgMappingType(dataSchemeDTO.getOrgMappingType());
                orgMappingVO.setIsolationStrategy(IsolationStrategy.SHARE.getCode());
                orgMappingVO.setOdsFieldFixedFlag(Boolean.valueOf(true));
                orgMappingVO.setIsolationStrategyFixedFlag(Boolean.valueOf(true));
                orgMappingVO.setFieldMappingType(FieldMappingType.SOURCE_FIELD.getCode());
                Optional<BaseDataMappingDefineDTO> unitBaseDataOptional = dataMappingDefineDTOS.stream().filter(item -> "MD_ORG".equals(item.getCode())).findFirst();
                if (unitBaseDataOptional.isPresent()) {
                    BaseDataMappingDefineDTO baseDataMappingDefine = unitBaseDataOptional.get();
                    orgMappingVO.setAutoMatchDim(baseDataMappingDefine.getAutoMatchDim());
                    orgMappingVO.setAdvancedSql(baseDataMappingDefine.getAdvancedSql());
                }
                List orgFieldList = fieldMappingDefineDTOS.stream().filter(item -> "MD_ORG".equals(item.getTableName())).collect(Collectors.toList());
                ArrayList<Columns> baseMapping = new ArrayList<Columns>();
                orgFieldList.sort(Comparator.comparing(FieldMappingDefineDTO::getOrdinal));
                for (FieldMappingDefineDTO field : orgFieldList) {
                    Columns columns = new Columns(field.getFieldName(), field.getFieldTitle(), field.getOdsFieldName());
                    baseMapping.add(columns);
                }
                orgMappingVO.setBaseMapping(baseMapping);
                dataMappingVO.setOrgMapping(orgMappingVO);
                bizFields.addAll(CollectionUtils.newArrayList((Object[])new String[]{"SUBJECTCODE", "CURRENCYCODE", "CFITEMCODE"}));
                block16: for (String bizField : bizFields) {
                    String dimCode = this.convertDimCode(bizField);
                    try {
                        BaseDataMappingDefineDTO baseDataMappingDefine = null;
                        Optional<BaseDataMappingDefineDTO> baseDataOptional = dataMappingDefineDTOS.stream().filter(item -> dimCode.equals(item.getCode())).findFirst();
                        if (baseDataOptional.isPresent()) {
                            baseDataMappingDefine = baseDataOptional.get();
                        }
                        List<FieldMappingDefineDTO> fieldMappingDefineList = fieldMappingDefineDTOS.stream().filter(item -> bizField.equals(item.getFieldName())).collect(Collectors.toList());
                        List<FieldMappingDefineDTO> fieldList = fieldMappingDefineDTOS.stream().filter(item -> dimCode.equals(item.getTableName())).collect(Collectors.toList());
                        switch (dimCode) {
                            case "MD_ACCTSUBJECT": {
                                FieldMappingDefineDTO fieldMappingDefine;
                                FieldDTO subjectField = pluginType.subjectField(dataSchemeDTO);
                                if (subjectField == null) continue block16;
                                if (CollectionUtils.isEmpty(fieldMappingDefineList)) {
                                    fieldMappingDefine = new FieldMappingDefineDTO();
                                    fieldMappingDefine.setFieldName(dimCode);
                                    fieldMappingDefine.setFieldMappingType(FieldMappingType.SOURCE_FIELD.getCode());
                                    if (baseDataMappingDefine != null) {
                                        fieldMappingDefine.setRuleType(baseDataMappingDefine.getRuleType());
                                    } else {
                                        fieldMappingDefine.setRuleType(subjectField.getRuleType());
                                    }
                                } else {
                                    fieldMappingDefine = (FieldMappingDefineDTO)fieldMappingDefineList.get(0);
                                    fieldMappingDefine.setFieldName(dimCode);
                                }
                                fieldMappingDefine.setFieldTitle("\u79d1\u76ee");
                                fieldMappingDefine.setOdsFieldName(subjectField.getName());
                                DimMappingVO mappingVO = this.convert(fieldMappingDefine, baseDataMappingDefine, subjectField, fieldList);
                                dataMappingVO.setSubjectMapping(mappingVO);
                                break;
                            }
                            case "MD_CURRENCY": {
                                FieldMappingDefineDTO fieldMappingDefine;
                                FieldDTO currencyField = pluginType.currencyField(dataSchemeDTO);
                                if (currencyField == null) continue block16;
                                if (CollectionUtils.isEmpty(fieldMappingDefineList)) {
                                    fieldMappingDefine = new FieldMappingDefineDTO();
                                    fieldMappingDefine.setFieldName(dimCode);
                                    fieldMappingDefine.setFieldMappingType(FieldMappingType.SOURCE_FIELD.getCode());
                                    if (baseDataMappingDefine != null) {
                                        fieldMappingDefine.setRuleType(baseDataMappingDefine.getRuleType());
                                    } else {
                                        fieldMappingDefine.setRuleType(currencyField.getRuleType());
                                    }
                                } else {
                                    fieldMappingDefine = (FieldMappingDefineDTO)fieldMappingDefineList.get(0);
                                    fieldMappingDefine.setFieldName(dimCode);
                                }
                                fieldMappingDefine.setFieldTitle("\u5e01\u522b");
                                fieldMappingDefine.setOdsFieldName(currencyField.getName());
                                DimMappingVO mappingVO = this.convert(fieldMappingDefine, baseDataMappingDefine, currencyField, fieldList);
                                dataMappingVO.setCurrencyMapping(mappingVO);
                                break;
                            }
                            case "MD_CFITEM": {
                                FieldMappingDefineDTO fieldMappingDefine;
                                FieldDTO cfItemField = pluginType.cfItemField(dataSchemeDTO);
                                if (cfItemField == null) continue block16;
                                if (CollectionUtils.isEmpty(fieldMappingDefineList)) {
                                    fieldMappingDefine = new FieldMappingDefineDTO();
                                    fieldMappingDefine.setFieldName(dimCode);
                                    fieldMappingDefine.setFieldMappingType(FieldMappingType.SOURCE_FIELD.getCode());
                                    if (baseDataMappingDefine != null) {
                                        fieldMappingDefine.setRuleType(baseDataMappingDefine.getRuleType());
                                    } else {
                                        fieldMappingDefine.setRuleType(cfItemField.getRuleType());
                                    }
                                } else {
                                    fieldMappingDefine = (FieldMappingDefineDTO)fieldMappingDefineList.get(0);
                                    fieldMappingDefine.setFieldName(dimCode);
                                }
                                fieldMappingDefine.setFieldTitle("\u73b0\u6d41\u9879\u76ee");
                                DimMappingVO mappingVO = this.convert(fieldMappingDefine, baseDataMappingDefine, cfItemField, fieldList);
                                mappingVO.setOdsFieldName(fieldMappingDefine.getOdsFieldName());
                                dataMappingVO.setCfitemMapping(mappingVO);
                                break;
                            }
                            default: {
                                DimensionVO dimensionVO;
                                FieldMappingDefineDTO fieldMappingDefine;
                                if ("DC_STANDARD_MODEL".equals(dataSchemeDTO.getPluginType())) {
                                    fieldMappingDefine = new FieldMappingDefineDTO();
                                    fieldMappingDefine.setFieldName(dimCode);
                                    fieldMappingDefine.setOdsFieldName("CUSTOMFIELD");
                                    if (baseDataMappingDefine != null) {
                                        fieldMappingDefine.setRuleType(baseDataMappingDefine.getRuleType());
                                        fieldMappingDefine.setFieldTitle(baseDataMappingDefine.getName());
                                    }
                                } else if (pluginType.assistFieldFlag()) {
                                    fieldMappingDefine = (FieldMappingDefineDTO)fieldMappingDefineList.get(0);
                                } else {
                                    fieldMappingDefine = new FieldMappingDefineDTO();
                                    fieldMappingDefine.setFieldName(dimCode);
                                    if (StringUtils.isEmpty((String)fieldMappingDefine.getOdsFieldName())) {
                                        fieldMappingDefine.setOdsFieldName("CUSTOMFIELD");
                                    }
                                    if (baseDataMappingDefine != null) {
                                        fieldMappingDefine.setRuleType(baseDataMappingDefine.getRuleType());
                                        fieldMappingDefine.setFieldTitle(baseDataMappingDefine.getName());
                                    } else {
                                        fieldMappingDefine.setRuleType(((FieldMappingDefineDTO)fieldMappingDefineList.get(0)).getRuleType());
                                    }
                                    fieldMappingDefine.setFieldMappingType(((FieldMappingDefineDTO)fieldMappingDefineList.get(0)).getFieldMappingType());
                                }
                                if (baseDataMappingDefine == null) {
                                    IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)dataSourceService.getDbType(dataSchemeDTO.getDataSourceCode()));
                                    String mappingSql = String.format(" SELECT 'ID' AS ID ,'CODE' AS CODE,'NAME' AS NAME %1$s ", StringUtils.isEmpty((String)sqlHandler.getVirtualTable()) ? "" : "FROM " + sqlHandler.getVirtualTable());
                                    baseDataMappingDefine = new BaseDataMappingDefineDTO();
                                    baseDataMappingDefine.setAdvancedSql(mappingSql);
                                }
                                if ((dimensionVO = dimensionMap.get(dimCode)) == null) continue block16;
                                fieldMappingDefine.setFieldTitle(dimensionVO.getTitle());
                                if ("dims".equals(dimensionVO.getDimensionType())) {
                                    DimMappingVO mappingVO = this.convert(fieldMappingDefine, baseDataMappingDefine, null, fieldList);
                                    AssistMappingVO assistMappingVO = new AssistMappingVO();
                                    BeanUtils.copyProperties(mappingVO, assistMappingVO);
                                    assistMappingList.add(assistMappingVO);
                                    if ("CUSTOMFIELD".equals(mappingVO.getOdsFieldName()) || !pluginType.assistFieldFlag()) {
                                        AdvancedMappingVO advancedMappingVO = this.convert(mappingVO, fieldMappingDefine, baseDataMappingDefine, fieldMappingDefineList);
                                        advancedMappingList.add(advancedMappingVO);
                                    }
                                } else {
                                    AdvancedMappingVO advancedMappingVO = this.convert(null, fieldMappingDefine, baseDataMappingDefine, fieldMappingDefineList);
                                    advancedMappingList.add(advancedMappingVO);
                                }
                                if (!pluginType.assistFieldFlag() || !StorageType.ID.getCode().equals(pluginType.storageType()) || !Objects.isNull(baseDataMappingDefine) && !StringUtils.isEmpty((String)baseDataMappingDefine.getAdvancedSql())) continue block16;
                                logger.warn("\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e2d{}({})\u8f85\u52a9\u7ef4\u5ea6\u672a\u5173\u8054\u57fa\u7840\u6570\u636e\uff0c\u6620\u5c04\u65e0\u6cd5\u81ea\u52a8\u5347\u7ea7\uff0c\u5347\u7ea7\u540e\u8bf7\u624b\u52a8\u91cd\u65b0\u7ef4\u62a4\u6620\u5c04\u3002", (Object)fieldMappingDefine.getFieldTitle(), (Object)fieldMappingDefine.getFieldName());
                                break;
                            }
                        }
                    }
                    catch (Exception e) {
                        logger.error("\u5904\u7406\u65e7\u65b9\u6848{}{}\u5b57\u6bb5\u51fa\u9519\uff1a{}", dataSchemeDTO.getCode(), dimCode, e.getMessage());
                    }
                }
                dataMappingVO.setAssistMapping((List)assistMappingList);
                dataMappingVO.setAdvancedMapping((List)advancedMappingList);
                newDataScheme.setDataMapping(dataMappingVO);
                List optionDOList = jdbcTemplate.query("SELECT * FROM DC_REF_DATASCHEMEOPTION WHERE DATASCHEMECODE = ?", (RowMapper)new BeanPropertyRowMapper(DataSchemeOptionDO.class), new Object[]{dataSchemeDTO.getCode()});
                logger.info("\u65b9\u6848{}\u83b7\u53d6\u5230{}\u6761\u7ba1\u63a7\u9009\u9879", (Object)dataSchemeDTO.getCode(), (Object)optionDOList.size());
                Integer exist = (Integer)sysJdbcTemplate.queryForObject("SELECT COUNT(*) FROM DC_SCHEME_DATAMAPPING WHERE CODE = ?", Integer.class, new Object[]{dataSchemeDTO.getCode()});
                if (exist == 0) {
                    dataSchemeService.create(newDataScheme);
                    for (DataSchemeOptionDO optionDO : optionDOList) {
                        dataSchemeOptionDao.insert(optionDO);
                    }
                }
                logger.info("\u65b9\u6848{}\u5904\u7406\u5b8c\u6210", (Object)dataSchemeDTO.getCode());
            }
            catch (Exception e) {
                logger.error("\u5904\u7406\u65e7\u65b9\u6848{}\u51fa\u9519\uff1a{}", dataSchemeDTO.getCode(), e.getMessage(), e);
            }
        }
        logger.info("\u6570\u636e\u6620\u5c04\u65b9\u6848\u8fc1\u79fb\u5b8c\u6bd5");
    }

    private DimMappingVO convert(FieldMappingDefineDTO bizField, BaseDataMappingDefineDTO baseDto, FieldDTO fieldDTO, List<FieldMappingDefineDTO> fieldList) {
        OrgMappingVO dimMappingVO = new OrgMappingVO();
        dimMappingVO.setCode(bizField.getFieldName());
        dimMappingVO.setName(bizField.getFieldTitle());
        if (!FieldMappingType.SOURCE_FIELD.getCode().equals(bizField.getFieldMappingType())) {
            dimMappingVO.setOdsFieldName("CUSTOMFIELD");
        } else {
            dimMappingVO.setOdsFieldName(!StringUtils.isEmpty((String)bizField.getOdsFieldName()) ? bizField.getOdsFieldName() : fieldDTO.getName());
        }
        dimMappingVO.setRuleType(this.convertRuleType(bizField.getRuleType()));
        dimMappingVO.setIsolationStrategy(this.convertIsolationStrategy(bizField.getRuleType()));
        if (fieldDTO != null) {
            dimMappingVO.setOdsFieldFixedFlag(fieldDTO.getOdsFieldFixedFlag());
            dimMappingVO.setIsolationStrategyFixedFlag(fieldDTO.getIsolationStrategyFixedFlag());
            dimMappingVO.setAdvancedSql(fieldDTO.getAdvancedSql());
        } else {
            dimMappingVO.setOdsFieldFixedFlag(Boolean.valueOf(true));
            dimMappingVO.setIsolationStrategyFixedFlag(Boolean.valueOf(true));
        }
        dimMappingVO.setFieldMappingType(bizField.getFieldMappingType());
        if (Objects.nonNull(baseDto)) {
            dimMappingVO.setAutoMatchDim(baseDto.getAutoMatchDim());
            dimMappingVO.setAdvancedSql(baseDto.getAdvancedSql());
        }
        ArrayList<Columns> baseMapping = new ArrayList<Columns>();
        fieldList.sort(Comparator.comparing(FieldMappingDefineDTO::getOrdinal));
        for (FieldMappingDefineDTO field : fieldList) {
            Columns columns = new Columns(field.getFieldName(), field.getFieldTitle(), field.getOdsFieldName());
            baseMapping.add(columns);
        }
        dimMappingVO.setBaseMapping(baseMapping);
        return dimMappingVO;
    }

    private AdvancedMappingVO convert(DimMappingVO dimMappingVO, FieldMappingDefineDTO bizField, BaseDataMappingDefineDTO baseDto, List<FieldMappingDefineDTO> fieldMappingDefineList) {
        AdvancedMappingVO advancedMappingVO = new AdvancedMappingVO();
        advancedMappingVO.setCode(bizField.getFieldName());
        advancedMappingVO.setName(bizField.getFieldTitle());
        advancedMappingVO.setRuleType(this.convertRuleType(bizField.getRuleType()));
        advancedMappingVO.setIsolationStrategy(this.convertIsolationStrategy(bizField.getRuleType()));
        advancedMappingVO.setFieldMappingType(bizField.getFieldMappingType());
        if (Objects.nonNull(baseDto)) {
            advancedMappingVO.setAdvancedSql(baseDto.getAdvancedSql());
        }
        advancedMappingVO.setStorageType(this.convertStorageType(bizField.getRuleType()));
        BizMappingVO bizMappingVO = new BizMappingVO();
        for (FieldMappingDefineDTO field : fieldMappingDefineList) {
            bizMappingVO.put((Object)field.getTableName(), (Object)field.getOdsFieldName());
        }
        advancedMappingVO.setBizMapping(bizMappingVO);
        return advancedMappingVO;
    }

    private String convertRuleType(String oldRuleType) {
        if (StringUtils.isEmpty((String)oldRuleType)) {
            return RuleType.PART.getCode();
        }
        if (RuleType.isItemByItem(oldRuleType).booleanValue()) {
            return RuleType.ALL.getCode();
        }
        return RuleType.PART.getCode();
    }

    private String convertIsolationStrategy(String oldIsolationStrategy) {
        if (StringUtils.isEmpty((String)oldIsolationStrategy)) {
            return IsolationStrategy.SHARE.getCode();
        }
        switch (oldIsolationStrategy) {
            case "ISOLATION_BY_UNIT": 
            case "ISOLATION_BY_UNIT_ITEM_BY_ITEM": {
                return IsolationStrategy.UNITCODE.getCode();
            }
            case "SHARE_TO_SUB_BY_UNIT": {
                return IsolationStrategy.ISOLATION_SHARE.getCode();
            }
            case "SHARE_ISOLATION_BY_UNIT": 
            case "SHARE_ISOLATION_BY_UNIT_ITEM_BY_ITEM": {
                return IsolationStrategy.SHARE_ISOLATION.getCode();
            }
            case "ISOLATION_BY_BOOK": 
            case "ISOLATION_BY_BOOK_ITEM_BY_ITEM": 
            case "SHARE_TO_SUB_BY_BOOKUNIT": 
            case "ISOLATION_BY_BOOKUNIT_ITEM_BY_ITEM": {
                return IsolationStrategy.BOOKCODE.getCode();
            }
            case "ISOLATION_BY_YEAR_ITEM_BY_ITEM": 
            case "ISOLATION_BY_YEARUNIT_ITEM_BY_ITEM": {
                return IsolationStrategy.YEAR.getCode();
            }
        }
        return IsolationStrategy.SHARE.getCode();
    }

    private String convertStorageType(String oldStorageType) {
        if (StringUtils.isEmpty((String)oldStorageType)) {
            return "ID";
        }
        if ("ID_TO_CODE".equals(oldStorageType)) {
            return "ID";
        }
        return "CODE";
    }

    private String convertDimCode(String dimCode) {
        if ("SUBJECTCODE".equals(dimCode)) {
            return "MD_ACCTSUBJECT";
        }
        if ("CURRENCYCODE".equals(dimCode)) {
            return "MD_CURRENCY";
        }
        if ("CFITEMCODE".equals(dimCode)) {
            return "MD_CFITEM";
        }
        return dimCode;
    }

    private Set<String> getStandModelFields(List<BaseDataMappingDefineDTO> dataMappingDefineDTOS) {
        if (!CollectionUtils.isEmpty(dataMappingDefineDTOS)) {
            return dataMappingDefineDTOS.stream().filter(item -> {
                if (!"BASEDATA".equals(item.getModelType())) {
                    return false;
                }
                return !"MD_ORG".equals(item.getCode()) && !"MD_ACCTSUBJECT".equals(item.getCode()) && !"MD_CURRENCY".equals(item.getCode()) && !"MD_CFITEM".equals(item.getCode());
            }).map(DataMappingDefineDTO::getCode).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }
}

