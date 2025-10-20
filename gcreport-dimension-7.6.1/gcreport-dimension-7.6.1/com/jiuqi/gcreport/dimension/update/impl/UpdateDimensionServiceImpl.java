/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.utils.DateTimeCenter
 *  com.jiuqi.budget.domain.DataType
 *  com.jiuqi.budget.domain.GroupDefine
 *  com.jiuqi.budget.domain.GroupType
 *  com.jiuqi.budget.param.dimension.domain.DimensionDTO
 *  com.jiuqi.budget.param.dimension.service.DimensionService
 *  com.jiuqi.budget.param.dimension.service.DimensionServiceImpl
 *  com.jiuqi.budget.param.group.domain.BaseGroupDO
 *  com.jiuqi.budget.param.group.domain.BaseGroupDTO
 *  com.jiuqi.budget.param.group.service.BudGroupService
 *  com.jiuqi.budget.param.hypermodel.domain.HyperDataSchemeDO
 *  com.jiuqi.budget.param.hypermodel.domain.ModelShowDimensionDTO
 *  com.jiuqi.budget.param.hypermodel.domain.ModelShowMeasurementDTO
 *  com.jiuqi.budget.param.hypermodel.domain.ModelStateType
 *  com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO
 *  com.jiuqi.budget.param.hypermodel.domain.enums.BudGatherType
 *  com.jiuqi.budget.param.hypermodel.domain.enums.DataSchemeType
 *  com.jiuqi.budget.param.hypermodel.domain.enums.DimensionType
 *  com.jiuqi.budget.param.hypermodel.domain.enums.ModelType
 *  com.jiuqi.budget.param.hypermodel.service.HyperDataSchemeService
 *  com.jiuqi.budget.param.hypermodel.service.HyperModelDefineService
 *  com.jiuqi.budget.param.hypermodel.service.HyperModelService
 *  com.jiuqi.budget.param.hypermodel.service.impl.HyperDataSchemeBizService
 *  com.jiuqi.budget.param.measurement.domain.CalcType
 *  com.jiuqi.budget.param.measurement.domain.MeasurementDO
 *  com.jiuqi.budget.param.measurement.service.MeasInnerBizService
 *  com.jiuqi.budget.param.measurement.service.MeasurementService
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.TableParseUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionGroupV
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  org.apache.commons.collections4.MapUtils
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.gcreport.dimension.update.impl;

import com.jiuqi.budget.common.utils.DateTimeCenter;
import com.jiuqi.budget.domain.DataType;
import com.jiuqi.budget.domain.GroupDefine;
import com.jiuqi.budget.domain.GroupType;
import com.jiuqi.budget.param.dimension.domain.DimensionDTO;
import com.jiuqi.budget.param.dimension.service.DimensionService;
import com.jiuqi.budget.param.dimension.service.DimensionServiceImpl;
import com.jiuqi.budget.param.group.domain.BaseGroupDO;
import com.jiuqi.budget.param.group.domain.BaseGroupDTO;
import com.jiuqi.budget.param.group.service.BudGroupService;
import com.jiuqi.budget.param.hypermodel.domain.HyperDataSchemeDO;
import com.jiuqi.budget.param.hypermodel.domain.ModelShowDimensionDTO;
import com.jiuqi.budget.param.hypermodel.domain.ModelShowMeasurementDTO;
import com.jiuqi.budget.param.hypermodel.domain.ModelStateType;
import com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO;
import com.jiuqi.budget.param.hypermodel.domain.enums.BudGatherType;
import com.jiuqi.budget.param.hypermodel.domain.enums.DataSchemeType;
import com.jiuqi.budget.param.hypermodel.domain.enums.DimensionType;
import com.jiuqi.budget.param.hypermodel.domain.enums.ModelType;
import com.jiuqi.budget.param.hypermodel.service.HyperDataSchemeService;
import com.jiuqi.budget.param.hypermodel.service.HyperModelDefineService;
import com.jiuqi.budget.param.hypermodel.service.HyperModelService;
import com.jiuqi.budget.param.hypermodel.service.impl.HyperDataSchemeBizService;
import com.jiuqi.budget.param.measurement.domain.CalcType;
import com.jiuqi.budget.param.measurement.domain.MeasurementDO;
import com.jiuqi.budget.param.measurement.service.MeasInnerBizService;
import com.jiuqi.budget.param.measurement.service.MeasurementService;
import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.TableParseUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionGroupV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper;
import com.jiuqi.gcreport.dimension.internal.utils.FieldTypeUtils;
import com.jiuqi.gcreport.dimension.update.UpdateDimensionService;
import com.jiuqi.gcreport.dimension.vo.DimTableRelVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class UpdateDimensionServiceImpl
implements UpdateDimensionService {
    private static final Logger logger = LoggerFactory.getLogger("com.jiuqi.dc.mappingscheme.impl.service.tableCheckAndSchemeUpdate");
    private static final String LOG_PREFIX = "\u7ef4\u5ea6\u7ba1\u7406\u5230\u9884\u7b97\u7ef4\u5ea6\u6570\u636e\u5347\u7ea7-";
    private static final String GC_GROUP_CODE = "GCREPORT";
    private static final String GC_GROUP_NAME = "\u5408\u5e76\u62a5\u8868\u5206\u7ec4";
    private static final String DC_GROUP_CODE = "DATACENTER";
    private static final String DC_GROUP_NAME = "\u4e00\u672c\u8d26\u5206\u7ec4";
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;

    @Override
    public void updateDimension(String dataSourceCode) {
        String systemName;
        String string = systemName = StringUtils.isEmpty((String)dataSourceCode) ? "\u5408\u5e76\u62a5\u8868" : "\u4e00\u672c\u8d26-BDE";
        if (!TableParseUtils.tableExist((String)dataSourceCode, Arrays.asList("GC_DIMENSION", "GC_DIMTABLEREL")).booleanValue()) {
            logger.info("\u7ef4\u5ea6\u7ba1\u7406\u5230\u9884\u7b97\u7ef4\u5ea6\u6570\u636e\u5347\u7ea7-\u5f53\u524d\u6570\u636e\u6e90\u4e2d\u7f3a\u5c11\u9700\u8981\u7684\u6570\u636e\u8868\uff0c\u4e0d\u8fdb\u884c\u5347\u7ea7\u3002");
            return;
        }
        GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate((String)dataSourceCode);
        String sql = "SELECT * FROM GC_DIMENSION WHERE PUBLISHEDFLAG IN (1,2)";
        List dimensionVOList = jdbcTemplate.query(sql, (RowMapper)new BeanPropertyRowMapper(DimensionVO.class));
        String dimTableSql = "SELECT * FROM GC_DIMTABLEREL";
        List dimTableRelVOS = jdbcTemplate.query(dimTableSql, (RowMapper)new BeanPropertyRowMapper(DimTableRelVO.class));
        if (CollectionUtils.isEmpty((Collection)dimensionVOList) || CollectionUtils.isEmpty((Collection)dimTableRelVOS)) {
            logger.info(LOG_PREFIX + systemName + "\u5f53\u524d\u9700\u8981\u5347\u7ea7\u7684\u6570\u636e\u4e3a\u7a7a\uff0c\u4e0d\u8fdb\u884c\u5347\u7ea7\u3002");
            return;
        }
        HashMap dimTableRelMap = new HashMap();
        Map<String, String> exisitBaseDataDefineMap = this.getExisitBaseDataDefineMap();
        for (DimTableRelVO relVO : dimTableRelVOS) {
            if (ObjectUtils.isEmpty(dimTableRelMap.get(relVO.getDimensionId()))) {
                dimTableRelMap.put(relVO.getDimensionId(), new ArrayList());
            }
            ((List)dimTableRelMap.get(relVO.getDimensionId())).add(relVO);
        }
        logger.info("\u7ef4\u5ea6\u7ba1\u7406\u5347\u7ea7\u9884\u7b97\u7ef4\u5ea6\u7ba1\u7406\u5f00\u59cb\u5904\u7406...");
        logger.info("\u5f00\u59cb\u521b\u5efa\u7ef4\u5ea6\u4e0e\u6307\u6807\u5206\u7ec4...");
        String groupCode = StringUtils.isEmpty((String)dataSourceCode) ? GC_GROUP_CODE : DC_GROUP_CODE;
        String groupName = StringUtils.isEmpty((String)dataSourceCode) ? GC_GROUP_NAME : DC_GROUP_NAME;
        BudGroupService groupService = (BudGroupService)SpringContextUtils.getBean(BudGroupService.class);
        this.createGroup(groupService, GroupType.DIMENSION, groupCode, groupName);
        logger.info("\u7ef4\u5ea6\u7ba1\u7406\u3010{}\u3011\u521b\u5efa\u6210\u529f", (Object)groupName);
        this.createGroup(groupService, GroupType.MEASUREMENT, groupCode, groupName);
        logger.info("\u6307\u6807\u7ba1\u7406\u3010{}\u3011\u521b\u5efa\u6210\u529f", (Object)groupName);
        try {
            GroupDefine groupDimensionDefine = groupService.getByCodeAndType(groupCode, GroupType.DIMENSION);
            GroupDefine groupMeasurementDefine = groupService.getByCodeAndType(groupCode, GroupType.MEASUREMENT);
            HashMap<String, List<ModelShowDimensionDTO>> dimensionTableMap = new HashMap<String, List<ModelShowDimensionDTO>>();
            HashMap<String, List<ModelShowMeasurementDTO>> measurementTableMap = new HashMap<String, List<ModelShowMeasurementDTO>>();
            logger.info("\u5f00\u59cb\u521b\u5efa\u7ef4\u5ea6\u4e0e\u6307\u6807...");
            for (DimensionVO dimensionVO : dimensionVOList) {
                List dimTableRelVOList = (List)dimTableRelMap.get(dimensionVO.getId());
                if (!StringUtils.isEmpty((String)dimensionVO.getReferField())) {
                    this.createDimension(dimensionVO, groupDimensionDefine, exisitBaseDataDefineMap);
                } else {
                    this.createMeasurement(dimensionVO, groupMeasurementDefine);
                }
                this.deployTableField(dimensionVO, dimTableRelVOList, dimensionTableMap, measurementTableMap);
            }
            this.createBudModule(dimensionTableMap, measurementTableMap);
            logger.info("{}\u3010{}\u3011\u7ef4\u5ea6\u7ba1\u7406\u5347\u7ea7\u9884\u7b97\u7ef4\u5ea6\u7ba1\u7406\u5904\u7406\u5b8c\u6210", (Object)LOG_PREFIX, (Object)systemName);
        }
        catch (Exception e) {
            logger.error("{}\u3010{}\u3011\u7ef4\u5ea6\u6570\u636e\u5347\u7ea7\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f\uff1a", LOG_PREFIX, systemName, e);
        }
    }

    private void createGroup(BudGroupService groupService, GroupType groupType, String groupCode, String groupName) {
        BaseGroupDO groupDO = new BaseGroupDO();
        groupDO.setCode(groupCode);
        groupDO.setGroupType(groupType);
        if (!groupService.exist(groupDO)) {
            BaseGroupDTO groupDTO = new BaseGroupDTO();
            groupDTO.setCode(groupCode);
            groupDTO.setName(groupName);
            groupDTO.setParentId("00000000-0000-0000-0000-000000000000");
            groupDTO.setGroupType(groupType);
            groupService.add((Object)groupDTO);
        }
    }

    private void createDimension(DimensionVO dimensionVO, GroupDefine groupDefine, Map<String, String> exisitBaseDataDefineMap) {
        DimensionService dimensionService = (DimensionService)SpringContextUtils.getBean(DimensionService.class);
        if (dimensionService.exist(dimensionVO.getCode())) {
            logger.info("\u7ef4\u5ea6\u3010{}\u3011\u5df2\u5b58\u5728\uff0c\u8df3\u8fc7", (Object)dimensionVO.getTitle());
            return;
        }
        DimensionServiceImpl dimensionServiceImpl = (DimensionServiceImpl)SpringContextUtils.getBean(DimensionServiceImpl.class);
        DimensionDTO dimensionDTO = new DimensionDTO();
        dimensionDTO.setBaseDataCode(dimensionVO.getReferField());
        dimensionDTO.setBaseDataName(MapUtils.getString(exisitBaseDataDefineMap, (Object)dimensionVO.getReferField(), (String)dimensionVO.getReferField()));
        dimensionDTO.setCode(dimensionVO.getCode());
        dimensionDTO.setMatchRule(dimensionVO.getMatchRule());
        dimensionDTO.setName(dimensionVO.getTitle());
        dimensionDTO.setParentId(groupDefine.getId());
        DimensionDTO dimensionBaseDTO = new DimensionDTO();
        if (!dimensionVO.getCode().equals(dimensionVO.getReferField()) && !dimensionService.exist(dimensionVO.getReferField())) {
            dimensionBaseDTO.setBaseDataCode(dimensionVO.getReferField());
            dimensionBaseDTO.setBaseDataName(dimensionVO.getTitle());
            dimensionBaseDTO.setCode(dimensionVO.getReferField());
            dimensionBaseDTO.setMatchRule(dimensionVO.getMatchRule());
            dimensionBaseDTO.setName(dimensionVO.getTitle() + "\uff08\u57fa\u7840\u6570\u636e\uff09");
            dimensionBaseDTO.setParentId(groupDefine.getId());
            dimensionServiceImpl.relCreate(dimensionBaseDTO);
            dimensionServiceImpl.relCreate(dimensionDTO);
            dimensionServiceImpl.delete(dimensionBaseDTO);
        } else {
            dimensionServiceImpl.relCreate(dimensionDTO);
        }
        logger.info("\u7ef4\u5ea6\u3010{}\u3011\u521b\u5efa\u6210\u529f", (Object)dimensionDTO.getName());
    }

    private void createMeasurement(DimensionVO dimensionVO, GroupDefine groupDefine) {
        MeasurementService measurementService = (MeasurementService)SpringContextUtils.getBean(MeasurementService.class);
        if (measurementService.exist(dimensionVO.getCode())) {
            logger.info("\u6307\u6807\u3010{}\u3011\u5df2\u5b58\u5728\uff0c\u8df3\u8fc7", (Object)dimensionVO.getTitle());
            return;
        }
        MeasInnerBizService measInnerBizService = (MeasInnerBizService)SpringContextUtils.getBean(MeasInnerBizService.class);
        MeasurementDO measurementDO = new MeasurementDO();
        measurementDO.setCalcType(CalcType.NOT_SUM);
        measurementDO.setCode(dimensionVO.getCode());
        if (FieldTypeUtils.FieldType.FIELD_TYPE_DECIMAL.getNrValue() == dimensionVO.getFieldType().intValue()) {
            measurementDO.setDataType(DataType.NUM);
            measurementDO.setDataLength(dimensionVO.getFieldSize());
            measurementDO.setDataPrecision(dimensionVO.getFieldDecimal());
        } else if (FieldTypeUtils.FieldType.FIELD_TYPE_DATE.getNrValue() == dimensionVO.getFieldType().intValue()) {
            measurementDO.setDataType(DataType.DATE);
        } else {
            measurementDO.setDataType(DataType.STR);
            measurementDO.setDataLength(Integer.valueOf(Objects.nonNull(dimensionVO.getFieldSize()) ? dimensionVO.getFieldSize() : 60));
        }
        measurementDO.setName(dimensionVO.getTitle());
        measurementDO.setParentId(groupDefine.getId());
        measInnerBizService.insert(measurementDO);
        logger.info("\u6307\u6807\u3010{}\u3011\u521b\u5efa\u6210\u529f", (Object)measurementDO.getName());
    }

    private void deployTableField(DimensionVO dimensionVO, List<DimTableRelVO> dimTableRelVOS, Map<String, List<ModelShowDimensionDTO>> dimensionTableMap, Map<String, List<ModelShowMeasurementDTO>> measurementTableMap) {
        for (DimTableRelVO relVO : dimTableRelVOS) {
            if (!StringUtils.isEmpty((String)dimensionVO.getReferField())) {
                if (ObjectUtils.isEmpty(dimensionTableMap.get(relVO.getEffectTableName()))) {
                    dimensionTableMap.put(relVO.getEffectTableName(), new ArrayList());
                }
                ModelShowDimensionDTO dimensionDTO = this.getModelShowDimensionDTO(dimensionVO);
                dimensionTableMap.get(relVO.getEffectTableName()).add(dimensionDTO);
                continue;
            }
            if (ObjectUtils.isEmpty(measurementTableMap.get(relVO.getEffectTableName()))) {
                measurementTableMap.put(relVO.getEffectTableName(), new ArrayList());
            }
            ModelShowMeasurementDTO measurementDTO = this.getModelShowMeasurementDTO(dimensionVO);
            measurementTableMap.get(relVO.getEffectTableName()).add(measurementDTO);
        }
    }

    private ModelShowDimensionDTO getModelShowDimensionDTO(DimensionVO dimensionVO) {
        ModelShowDimensionDTO dimensionDTO = new ModelShowDimensionDTO();
        dimensionDTO.setCode(dimensionVO.getCode());
        dimensionDTO.setName(dimensionVO.getTitle());
        dimensionDTO.setBaseDataCode(dimensionVO.getReferField());
        dimensionDTO.setDbCode(dimensionVO.getCode());
        dimensionDTO.setPublishState("PUBLISHED");
        dimensionDTO.setDimensionType(DimensionType.CUSTOM);
        dimensionDTO.setHfFlag(false);
        return dimensionDTO;
    }

    private ModelShowMeasurementDTO getModelShowMeasurementDTO(DimensionVO dimensionVO) {
        ModelShowMeasurementDTO measurementDTO = new ModelShowMeasurementDTO();
        measurementDTO.setCode(dimensionVO.getCode());
        measurementDTO.setDbCode(dimensionVO.getCode());
        measurementDTO.setName(dimensionVO.getTitle());
        if (FieldTypeUtils.FieldType.FIELD_TYPE_DECIMAL.getNrValue() == dimensionVO.getFieldType().intValue()) {
            measurementDTO.setDataType(DataType.NUM);
        } else if (FieldTypeUtils.FieldType.FIELD_TYPE_DATE.getNrValue() == dimensionVO.getFieldType().intValue()) {
            measurementDTO.setDataType(DataType.DATE);
        } else {
            measurementDTO.setDataType(DataType.STR);
        }
        measurementDTO.setCalcType(CalcType.NOT_SUM);
        measurementDTO.setPublishState("PUBLISHED");
        measurementDTO.setPrimaryKeyFlag(false);
        measurementDTO.setNullable(true);
        measurementDTO.setAllowMultipleSelect(false);
        return measurementDTO;
    }

    private void createBudModule(Map<String, List<ModelShowDimensionDTO>> dimensionTableMap, Map<String, List<ModelShowMeasurementDTO>> measurementTableMap) throws Exception {
        HyperModelDefineService hyperModelDefineService = (HyperModelDefineService)SpringContextUtils.getBean(HyperModelDefineService.class);
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        List baseEntities = entityTableCollector.getEntitys();
        for (BaseEntity baseEntity : baseEntities) {
            DBTable dbTable = entityTableCollector.getDbTableByType(baseEntity.getClass());
            if (!dbTable.convertToBudModel() || !StringUtils.isEmpty((String)dbTable.sourceTable())) continue;
            TableDefineConvertHelper tableDefineConvertHelper = TableDefineConvertHelper.newInstance((BaseEntity)baseEntity, (DBTable)dbTable);
            DefinitionTableV tableDefine = tableDefineConvertHelper.convert();
            this.checkModuleGroup(tableDefine);
            ShowModelDTO showModelDTO = this.getShowModelDTO(tableDefine);
            showModelDTO.setDimensionDOs(this.handleDimensions(tableDefine.getTableName(), showModelDTO, dimensionTableMap));
            showModelDTO.setDimCodeList((List)CollectionUtils.newArrayList());
            showModelDTO.setMeasurementDOs(this.handleMeasurements(tableDefine.getTableName(), showModelDTO, measurementTableMap));
            hyperModelDefineService.insertOrUpdate(showModelDTO);
            logger.info("\u6a21\u578b\u3010{}\u3011\u66f4\u65b0\u6210\u529f", (Object)tableDefine.getTableName());
        }
    }

    @NotNull
    private ShowModelDTO getShowModelDTO(DefinitionTableV definitionTable) {
        HyperModelService hyperModelService = (HyperModelService)SpringContextUtils.getBean(HyperModelService.class);
        ShowModelDTO showModelDTO = new ShowModelDTO();
        if (!hyperModelService.isExist(definitionTable.getTableName())) {
            showModelDTO.setId(UUIDUtils.newUUIDStr());
            showModelDTO.setCode(definitionTable.getModelTableName());
            showModelDTO.setName(definitionTable.getTitle());
            showModelDTO.setPeriodType(ModelType.NORMAL.equals((Object)definitionTable.getBudModelType()) ? null : "Y");
            showModelDTO.setModelType(definitionTable.getBudModelType());
            showModelDTO.setPublishState(ModelStateType.PUBLISHED);
            showModelDTO.setParentId(definitionTable.getOwnerGroup().getId());
            showModelDTO.setOrderNum(BigDecimal.ZERO);
            showModelDTO.setDataschemeID("b9add117-a540-4904-bdf7-c0e0d992a68b");
            showModelDTO.setCanModifyModelType(true);
            showModelDTO.setBudGatherType(BudGatherType.LIST);
            showModelDTO.setDsCode(definitionTable.getDataSource());
            return showModelDTO;
        }
        showModelDTO = hyperModelService.selectOneByCode(definitionTable.getTableName());
        return showModelDTO;
    }

    private List<ModelShowDimensionDTO> handleDimensions(String tableName, ShowModelDTO showModelDTO, Map<String, List<ModelShowDimensionDTO>> dimensionTableMap) {
        if (ObjectUtils.isEmpty(dimensionTableMap.get(tableName))) {
            return new ArrayList<ModelShowDimensionDTO>();
        }
        if (CollectionUtils.isEmpty((Collection)showModelDTO.getDimensionDOs())) {
            return dimensionTableMap.get(tableName);
        }
        List oldDimensionList = showModelDTO.getDimensionDOs();
        Set oldDimCodeSet = oldDimensionList.stream().map(ModelShowDimensionDTO::getCode).collect(Collectors.toSet());
        List<ModelShowDimensionDTO> newDimensionList = dimensionTableMap.get(tableName);
        for (ModelShowDimensionDTO dimensionDTO : newDimensionList) {
            if (oldDimCodeSet.contains(dimensionDTO.getCode())) continue;
            oldDimensionList.add(dimensionDTO);
        }
        return oldDimensionList;
    }

    private List<ModelShowMeasurementDTO> handleMeasurements(String tableName, ShowModelDTO showModelDTO, Map<String, List<ModelShowMeasurementDTO>> measurementTableMap) {
        if (ObjectUtils.isEmpty(measurementTableMap.get(tableName))) {
            return new ArrayList<ModelShowMeasurementDTO>();
        }
        if (CollectionUtils.isEmpty((Collection)showModelDTO.getMeasurementDOs())) {
            return measurementTableMap.get(tableName);
        }
        List oldMeasureMentList = showModelDTO.getMeasurementDOs();
        Set oldMeasurementCodeSet = oldMeasureMentList.stream().map(ModelShowMeasurementDTO::getCode).collect(Collectors.toSet());
        List<ModelShowMeasurementDTO> newMeasureMentList = measurementTableMap.get(tableName);
        for (ModelShowMeasurementDTO measurementDTO : newMeasureMentList) {
            if (oldMeasurementCodeSet.contains(measurementDTO.getCode())) continue;
            oldMeasureMentList.add(measurementDTO);
        }
        return oldMeasureMentList;
    }

    private void checkModuleGroup(DefinitionTableV definitionTable) {
        HyperDataSchemeService hyperDataSchemeService = (HyperDataSchemeService)SpringContextUtils.getBean(HyperDataSchemeService.class);
        HyperDataSchemeBizService hyperDataSchemeBizService = (HyperDataSchemeBizService)SpringContextUtils.getBean(HyperDataSchemeBizService.class);
        BudGroupService groupService = (BudGroupService)SpringContextUtils.getBean(BudGroupService.class);
        if (ObjectUtils.isEmpty(hyperDataSchemeService.getByCode("GCCUSTOM"))) {
            HyperDataSchemeDO schemeDO = new HyperDataSchemeDO();
            schemeDO.setCode("GCCUSTOM");
            schemeDO.setName("\u5408\u5e76\u62a5\u8868\u65b9\u6848");
            schemeDO.setParentId("ALL");
            schemeDO.setId("b9add117-a540-4904-bdf7-c0e0d992a68b");
            schemeDO.setDataSchemeType(DataSchemeType.OTHER);
            hyperDataSchemeBizService.designInsertNrDataScheme(schemeDO);
            schemeDO.setCreateTime(DateTimeCenter.convertDateToLDT((Date)new Date()));
            schemeDO.setParentId("00000000-0000-0000-0000-000000000000");
            schemeDO.setTenantName("__default_tenant__");
            hyperDataSchemeBizService.delopyInsert(schemeDO);
        }
        DefinitionGroupV groupV = definitionTable.getOwnerGroup();
        BaseGroupDO groupDO = new BaseGroupDO();
        groupDO.setCode(groupV.getCode().toUpperCase());
        groupDO.setGroupType(GroupType.HYPERMODEL);
        if (groupService.exist(groupDO)) {
            return;
        }
        BaseGroupDTO groupDTO = new BaseGroupDTO();
        groupDTO.setId(groupV.getId());
        groupDTO.setCode(groupV.getCode().toUpperCase());
        groupDTO.setGroupType(GroupType.HYPERMODEL);
        groupDTO.setName(groupV.getTitle());
        groupDTO.setParentId("b9add117-a540-4904-bdf7-c0e0d992a68b");
        groupService.add((Object)groupDTO);
    }

    private Map<String, String> getExisitBaseDataDefineMap() {
        BaseDataDefineDTO condi = new BaseDataDefineDTO();
        condi.setPagination(false);
        condi.setDeepClone(Boolean.valueOf(true));
        List baseDatas = this.baseDataDefineClient.list(condi).getRows();
        if (CollectionUtils.isEmpty((Collection)baseDatas)) {
            return CollectionUtils.newHashMap();
        }
        return baseDatas.stream().collect(Collectors.toMap(BaseDataDefineDO::getName, BaseDataDefineDO::getTitle, (k1, k2) -> k2));
    }
}

