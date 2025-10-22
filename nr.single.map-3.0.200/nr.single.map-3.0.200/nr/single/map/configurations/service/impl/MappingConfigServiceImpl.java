/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.DesignTimeViewController
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  javax.validation.constraints.NotNull
 */
package nr.single.map.configurations.service.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.DesignTimeViewController;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import nr.single.map.configurations.bean.EntityVO;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.MappingConfig;
import nr.single.map.configurations.bean.RuleKind;
import nr.single.map.configurations.bean.RuleMap;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.configurations.bean.SingleFileInfo;
import nr.single.map.configurations.bean.UnitCustomMapping;
import nr.single.map.configurations.bean.UnitMapping;
import nr.single.map.configurations.common.Utils;
import nr.single.map.configurations.dao.ConfigDao;
import nr.single.map.configurations.dao.FileInfoDao;
import nr.single.map.configurations.internal.bean.IllegalData;
import nr.single.map.configurations.service.HandleUpdateConfigService;
import nr.single.map.configurations.service.MappingConfigService;
import nr.single.map.configurations.vo.CommonEnumMapping;
import nr.single.map.configurations.vo.CommonTreeNode;
import nr.single.map.configurations.vo.EntitySaveResult;
import nr.single.map.configurations.vo.EnumItemMappingVO;
import nr.single.map.configurations.vo.EnumMappingVO;
import nr.single.map.data.facade.SingleFileEnumInfo;
import nr.single.map.data.facade.SingleFileEnumItem;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFormulaItem;
import nr.single.map.data.internal.SingleFileEnumItemImpl;
import nr.single.map.data.internal.SingleFileFieldInfoImpl;
import nr.single.map.data.internal.SingleFileFormulaItemImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class MappingConfigServiceImpl
implements MappingConfigService {
    private static final Logger logger = LoggerFactory.getLogger(MappingConfigServiceImpl.class);
    private static final String MAPPING_INFO = "mappingInfo";
    private static final String TASK_INFO = "taskInfo";
    private static final String ENUM_MATCH_CODE = "1";
    @Autowired
    private ConfigDao configDao;
    @Autowired
    private FileInfoDao fileInfoDao;
    @Autowired
    private DesignTimeViewController desCtrl;
    @Autowired
    private IRunTimeViewController runtimeCtrl;
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeCtrl;
    @Autowired
    private HandleUpdateConfigService handleUpdateConfigService;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IFormulaRunTimeController runTimeFormula;
    @Autowired
    private IFMDMAttributeService ifmdmAttributeService;
    @Autowired
    private USelectorResultSet uSelectorResultSet;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private BaseDataClient baseDataClient;

    @Override
    public List<SingleConfigInfo> getAllMappingInTask(String taskKey) {
        return this.configDao.queryConfigByTask(taskKey);
    }

    @Override
    public ISingleMappingConfig getConfigByKey(String mappingKey) {
        return this.configDao.query(mappingKey);
    }

    @Override
    public List<SingleConfigInfo> getAllMappingInReport(String schemeKey) {
        Assert.notNull((Object)schemeKey, "schemeKey not allowed to be empty!");
        List<SingleConfigInfo> collect = this.configDao.queryConfigByScheme(schemeKey);
        List canSortedInfos = collect.stream().filter(e -> e.getOrder() != null).collect(Collectors.toList());
        List emptyOrderInfos = collect.stream().filter(e -> e.getOrder() == null).collect(Collectors.toList());
        List<SingleConfigInfo> sortedInfos = canSortedInfos.stream().sorted(Comparator.comparing(SingleConfigInfo::getOrder)).collect(Collectors.toList());
        sortedInfos.addAll(emptyOrderInfos);
        return sortedInfos;
    }

    @Override
    public List<SingleConfigInfo> getAllMapping() {
        return this.configDao.queryAllConfigInfo();
    }

    @Override
    public List<SingleConfigInfo> getConfigInSingleTask(String singleTask) {
        Assert.notNull((Object)singleTask, "singleTaskFlag not allowed to be empty!");
        return this.configDao.queryConfigBySingleTask(singleTask);
    }

    @Override
    public SingleConfigInfo getMappingByKey(String mappingKey) {
        Assert.notNull((Object)mappingKey, "mappingKey not allowed to be empty!");
        return this.configDao.queryConfigByKey(mappingKey);
    }

    @Override
    public List<RuleMap> getRuleMapByConfig(String configKey) {
        Assert.notNull((Object)configKey, "configKey not allowed to be empty!");
        return this.configDao.query(configKey).getMapRule();
    }

    @Override
    public Map<String, Object> getMappingConfig(String configKey) {
        Assert.notNull((Object)configKey, "configKey not allowed to be empty!");
        HashMap<String, Object> config = new HashMap<String, Object>(2);
        ISingleMappingConfig query = this.configDao.query(configKey);
        config.put(MAPPING_INFO, query.getConfig());
        config.put(TASK_INFO, query.getTaskInfo());
        return config;
    }

    @Override
    public UnitMapping getEntityConfig(String configKey) {
        Assert.notNull((Object)configKey, "configKey not allowed to be empty!");
        return this.configDao.query(configKey).getMapping();
    }

    @Override
    public List<SingleFileFieldInfo> getZbConfig(String configKey) {
        Assert.notNull((Object)configKey, "configKey not allowed to be empty!");
        return this.configDao.query(configKey).getZbFields();
    }

    @Override
    public List<SingleFileFieldInfo> getZbConfigByForm(@NotNull String configKey, String formKey) {
        Assert.notNull((Object)formKey, "formKey must not be empty!");
        ArrayList<SingleFileFieldInfo> allFileds = new ArrayList<SingleFileFieldInfo>();
        List keys = this.runtimeCtrl.getFieldKeysInForm(formKey);
        FormDefine form = this.runtimeCtrl.queryFormById(formKey);
        boolean isFMDM = FormType.FORM_TYPE_NEWFMDM.equals((Object)form.getFormType());
        List allRegionsInForm = this.runtimeCtrl.getAllRegionsInForm(form.getKey());
        List<DataRegionDefine> floatRegion = allRegionsInForm.stream().filter(e -> !e.getRegionKind().equals((Object)DataRegionKind.DATA_REGION_SIMPLE)).collect(Collectors.toList());
        HashMap regionToTable = new HashMap();
        floatRegion.forEach(e -> {
            RegionSettingDefine regionSetting = this.runtimeCtrl.getRegionSetting(e.getKey());
            String dictionaryFillLinks = regionSetting.getDictionaryFillLinks();
            if (StringUtils.hasText(dictionaryFillLinks)) {
                String[] fieldKeys = dictionaryFillLinks.split(";");
                try {
                    FieldDefine fieldDefine = this.dataRuntimeCtrl.queryFieldDefine(fieldKeys[0]);
                    TableDefine tableDefine = this.dataRuntimeCtrl.queryTableDefine(fieldDefine.getOwnerTableKey());
                    regionToTable.put(e.getKey(), tableDefine.getCode());
                }
                catch (Exception exception) {
                    logger.error(exception.getMessage(), exception);
                }
            }
        });
        List attributes = new ArrayList();
        if (isFMDM) {
            FormSchemeDefine formSchemeDefine = this.runtimeCtrl.getFormScheme(form.getFormScheme());
            TaskDefine taskDefine = this.runtimeCtrl.queryTaskDefine(formSchemeDefine.getTaskKey());
            try {
                FMDMAttributeDTO fMDMAttributeDTO = new FMDMAttributeDTO();
                fMDMAttributeDTO.setEntityId(taskDefine.getDw());
                fMDMAttributeDTO.setFormSchemeKey(formSchemeDefine.getKey());
                attributes = this.ifmdmAttributeService.list(fMDMAttributeDTO);
            }
            catch (Exception e2) {
                logger.error(e2.getMessage(), e2);
            }
        }
        List runtimeFields = this.dataRuntimeCtrl.queryFieldDefinesInRange((Collection)keys);
        List<SingleFileFieldInfo> zbConfig = this.getZbConfig(configKey);
        Map<String, List<SingleFileFieldInfo>> formMap = zbConfig.stream().filter(zb -> zb.getNetFormCode() != null).collect(Collectors.groupingBy(SingleFileFieldInfo::getNetFormCode));
        List<SingleFileFieldInfo> formFields = formMap.get(form.getFormCode());
        List allLinks = this.runTimeAuthViewController.getAllLinksInForm(formKey);
        List finalAttributes = attributes;
        allLinks.forEach(link -> {
            boolean needExpandLink = !StringUtils.isEmpty(regionToTable.get(link.getRegionKey()));
            List<Object> findZb = new ArrayList();
            if (formFields != null && !formFields.isEmpty()) {
                findZb = formFields.stream().filter(zb -> zb.getNetDataLinkKey() != null && link.getKey().equals(zb.getNetDataLinkKey())).filter(zb -> {
                    if (this.fmdmField(isFMDM, (DataLinkDefine)link)) {
                        return zb.getNetFieldCode() != null && link.getLinkExpression().equals(zb.getNetFieldCode());
                    }
                    return zb.getNetFieldKey() != null && link.getLinkExpression().equals(zb.getNetFieldKey());
                }).collect(Collectors.toList());
            }
            if (!CollectionUtils.isEmpty(findZb)) {
                allFileds.addAll(findZb);
            } else {
                Optional<Object> findField = Optional.empty();
                Optional<Object> findAttribute = Optional.empty();
                if (this.fmdmField(isFMDM, (DataLinkDefine)link)) {
                    findAttribute = finalAttributes.stream().filter(e -> e.getCode().equals(link.getLinkExpression())).findFirst();
                } else {
                    findField = runtimeFields.stream().filter(e -> e.getKey().equals(link.getLinkExpression())).findFirst();
                }
                if (findField.isPresent() || findAttribute.isPresent()) {
                    SingleFileFieldInfo info;
                    FieldDefine fieldDefine = null;
                    IFMDMAttribute attribute = null;
                    if (findField.isPresent()) {
                        fieldDefine = (FieldDefine)findField.get();
                    }
                    if (findAttribute.isPresent()) {
                        attribute = (IFMDMAttribute)findAttribute.get();
                    }
                    Optional<Object> finalFind = Optional.empty();
                    if (formFields != null && !formFields.isEmpty()) {
                        IFMDMAttribute finalAttribute = attribute;
                        FieldDefine finalFieldDefine = fieldDefine;
                        finalFind = formFields.stream().filter(e -> StringUtils.hasText(e.getNetFieldKey()) && e.getNetFieldKey().equals(this.fmdmField(isFMDM, (DataLinkDefine)link) ? finalAttribute.getID() : finalFieldDefine.getKey()) && !StringUtils.hasText(e.getNetDataLinkKey())).findFirst();
                    }
                    if (finalFind.isPresent()) {
                        info = (SingleFileFieldInfo)finalFind.get();
                    } else {
                        String netCode = null;
                        try {
                            TableDefine tableDefine;
                            TableModelDefine tableModelDefine;
                            netCode = this.fmdmField(isFMDM, (DataLinkDefine)link) ? ((tableModelDefine = this.dataModelService.getTableModelDefineById(attribute.getTableID())) != null ? tableModelDefine.getCode() : attribute.getTableID()) : ((tableDefine = this.dataRuntimeCtrl.queryTableDefine(fieldDefine.getOwnerTableKey())) != null ? tableDefine.getCode() : fieldDefine.getOwnerTableKey());
                        }
                        catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        info = new SingleFileFieldInfoImpl();
                        info.setNetFieldCode(this.fmdmField(isFMDM, (DataLinkDefine)link) ? attribute.getCode() : fieldDefine.getCode());
                        info.setNetFieldKey(this.fmdmField(isFMDM, (DataLinkDefine)link) ? attribute.getID() : fieldDefine.getKey());
                        info.setNetTableCode(netCode);
                        info.setNetFormCode(form.getFormCode());
                        info.setNetFieldValue(this.fmdmField(isFMDM, (DataLinkDefine)link) ? attribute.getCode() : fieldDefine.getCode());
                        info.setFieldType(this.fmdmField(isFMDM, (DataLinkDefine)link) ? this.getType(attribute.getColumnType()) : fieldDefine.getType());
                        info.setFieldSize(this.fmdmField(isFMDM, (DataLinkDefine)link) ? attribute.getPrecision() : fieldDefine.getSize().intValue());
                        info.setFieldDecimal(this.fmdmField(isFMDM, (DataLinkDefine)link) ? attribute.getDecimal() : fieldDefine.getFractionDigits().intValue());
                    }
                    info.setNetDataLinkKey(link.getKey());
                    allFileds.add(info);
                }
            }
        });
        return allFileds;
    }

    private boolean fmdmField(boolean isFMDM, DataLinkDefine link) {
        return isFMDM && link.getType() == DataLinkType.DATA_LINK_TYPE_FMDM;
    }

    private FieldType getType(ColumnModelType columnModelType) {
        switch (columnModelType) {
            case BOOLEAN: {
                return FieldType.FIELD_TYPE_LOGIC;
            }
            case DOUBLE: {
                return FieldType.FIELD_TYPE_FLOAT;
            }
            case STRING: {
                return FieldType.FIELD_TYPE_STRING;
            }
            case INTEGER: {
                return FieldType.FIELD_TYPE_INTEGER;
            }
            case DATETIME: {
                return FieldType.FIELD_TYPE_DATE_TIME;
            }
        }
        return FieldType.FIELD_TYPE_STRING;
    }

    @Override
    public List<SingleFileFormulaItem> getFormulaConfig(String configKey) {
        Assert.notNull((Object)configKey, "configKey must not be empty!");
        return this.configDao.query(configKey).getFormulaInfos();
    }

    @Override
    public List<SingleFileFormulaItem> getFormulaConfig(String configKey, String schemeKey) {
        Assert.notNull((Object)configKey, "ConfigKey must not be empty!");
        Assert.notNull((Object)schemeKey, "SchemeKey must not be empty!");
        FormulaSchemeDefine schemeDefine = this.runTimeFormula.queryFormulaSchemeDefine(schemeKey);
        List queryBaseFormulas = this.runTimeFormula.getAllFormulasInScheme(schemeDefine.getKey());
        List formulaDefines = null;
        try {
            formulaDefines = this.runTimeFormula.queryPublicFormulaDefineByScheme(schemeDefine.getKey());
        }
        catch (Exception e2) {
            logger.warn("\u67e5\u8be2\u516c\u5f0f\u6620\u5c04\u65f6\uff0c\u67e5\u627e\u62a5\u8868\u5f15\u7528\u516c\u5f0f\u9519\u8bef.");
        }
        queryBaseFormulas.addAll(formulaDefines);
        Stream<String> queryKeys = queryBaseFormulas.stream().distinct().map(e -> e.getKey());
        Map<String, List<SingleFileFormulaItem>> formulaMap = this.getFormulaConfig(configKey).stream().filter(e -> !StringUtils.isEmpty(e.getNetFormulaKey())).collect(Collectors.groupingBy(SingleFileFormulaItem::getNetFormulaKey));
        ArrayList<SingleFileFormulaItem> allItems = new ArrayList<SingleFileFormulaItem>();
        queryKeys.forEach(key -> {
            List formulaItems = (List)formulaMap.get(key);
            if (!CollectionUtils.isEmpty(formulaItems)) {
                allItems.addAll(formulaItems);
            }
        });
        queryKeys = null;
        queryBaseFormulas = null;
        formulaDefines = null;
        return allItems;
    }

    @Override
    public List<SingleFileFormulaItem> getFormulaConfig(String configKey, String schemeKey, String reportKey) {
        Assert.notNull((Object)configKey, "ConfigKey must not be empty!");
        Assert.notNull((Object)schemeKey, "SchemeKey must not be empty!");
        Assert.notNull((Object)reportKey, "ReportKey must not be empty!");
        List<Object> result = new ArrayList();
        if ("00000000-0000-0000-0000-000000000000".equals(reportKey)) {
            reportKey = null;
        }
        List<SingleFileFormulaItem> formulaConfig = this.getFormulaConfig(configKey, schemeKey);
        if (StringUtils.isEmpty(reportKey)) {
            result = formulaConfig.stream().filter(e -> StringUtils.isEmpty(e.getNetFormKey())).collect(Collectors.toList());
        } else {
            String finalReportKey = reportKey;
            result = formulaConfig.stream().filter(e -> e.getNetFormKey() != null && finalReportKey.equals(e.getNetFormKey())).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public String getEntityIdByTask(String taskKey, String schemeKey) {
        Assert.notNull((Object)taskKey, "TaskKey must not be empty!");
        DesignFormSchemeDefine schemeDefine = this.desCtrl.queryFormSchemeDefine(schemeKey);
        String entityKey = schemeDefine.getDw();
        if (StringUtils.isEmpty(entityKey)) {
            DesignTaskDefine taskDefine = this.desCtrl.queryTaskDefine(taskKey);
            entityKey = taskDefine.getDw();
        }
        if (entityKey == null || "".equals(entityKey)) {
            throw new NullPointerException("\u4efb\u52a1\u4e0b\u6ca1\u6709\u627e\u5230\u4e3b\u4f53");
        }
        return entityKey;
    }

    @Override
    public SingleConfigInfo copyMappingConfig(String configKey) {
        Assert.notNull((Object)configKey, "ConfigKey must not be empty!");
        ISingleMappingConfig config = this.configDao.query(configKey);
        SingleFileInfo fileInfo = this.fileInfoDao.query(configKey);
        List<SingleConfigInfo> allMappingInReport = this.getAllMappingInReport(config.getSchemeKey().toString());
        String configName = this.buildConfigName(allMappingInReport, config.getTaskInfo().getSingleTaskTitle(), true);
        config.getTaskInfo().setUploadFileName(configName + ".jio");
        config.setConfigName(configName);
        config.setMappingConfigKey(UUID.randomUUID().toString());
        if (config != null) {
            this.configDao.insert(config);
        }
        if (fileInfo != null) {
            fileInfo.setKey(config.getMappingConfigKey());
            this.fileInfoDao.insert(fileInfo);
        }
        SingleConfigInfo info = new SingleConfigInfo();
        info.setConfigKey(config.getMappingConfigKey());
        info.setConfigName(config.getConfigName());
        return info;
    }

    @Override
    public void deleteMappingConfig(String configKey) {
        Assert.notNull((Object)configKey, "ConfigKey must not be empty!");
        this.configDao.delete(configKey);
        this.fileInfoDao.deleteBykey(configKey);
    }

    @Override
    public EntitySaveResult saveEntityConfig(String mappingKey, UnitMapping entityConfig, List<RuleMap> mapRule) {
        EntitySaveResult saveResult = new EntitySaveResult();
        ISingleMappingConfig config = this.configDao.query(mappingKey);
        UnitMapping mapping = config.getMapping();
        String errorMessage = null;
        if (entityConfig.getPeriodMapping() != null || entityConfig.getUnitInfos() != null) {
            if (entityConfig.getUnitInfos() != null) {
                IEntityAttribute bblx = null;
                List<IEntityRow> rows = null;
                FormSchemeDefine formSchemeDefine = this.runtimeCtrl.getFormScheme(config.getSchemeKey());
                String dw = formSchemeDefine.getDw();
                try {
                    if (!StringUtils.hasText(dw)) {
                        TaskDefine taskDefine = this.runtimeCtrl.queryTaskDefine(formSchemeDefine.getTaskKey());
                        dw = taskDefine.getDw();
                    }
                    IEntityModel entityModel = this.entityMetaService.getEntityModel(dw);
                    bblx = entityModel.getBblxField();
                    rows = this.listEntityRows(config.getSchemeKey());
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    errorMessage = String.format("\u67e5\u8be2\u673a\u6784%s\u7684\u5355\u4f4d\u6570\u636e\u65f6\u53d1\u751f\u5f02\u5e38:%s", dw, e.getMessage());
                }
                List<Object> filterInfos = new ArrayList();
                if (rows != null && bblx != null) {
                    for (UnitCustomMapping unit : entityConfig.getUnitInfos()) {
                        Optional<IEntityRow> findRow = rows.stream().filter(r -> r.getEntityKeyData().equals(unit.getNetUnitKey())).findFirst();
                        if (findRow.isPresent()) {
                            AbstractData bblxValue = findRow.get().getValue(bblx.getCode());
                            if (!bblxValue.isNull) {
                                unit.setBblx(bblxValue.getAsString());
                            }
                        } else {
                            saveResult.addError(unit.getNetUnitKey(), String.format("\u5f53\u524d\u65f6\u671f\u7248\u672c\u4e0b\uff0c\u672a\u627e\u5230\u7f51\u62a5\u5355\u4f4d\u4ee3\u7801\u4e3a'%s'\u7684\u6570\u636e", unit.getNetUnitKey()));
                        }
                        filterInfos.add(unit);
                        saveResult.addSuccess();
                    }
                } else {
                    filterInfos = entityConfig.getUnitInfos();
                    saveResult.setSuccessTotal(filterInfos.size());
                }
                mapping.setUnitInfos(filterInfos);
            }
            if (entityConfig.getPeriodMapping() != null) {
                mapping.setPeriodMapping(entityConfig.getPeriodMapping());
            }
            config.setMapping(mapping);
        }
        if (mapRule != null && mapRule.size() > 0) {
            config.setMapRule(mapRule);
        }
        this.configDao.update(config);
        if (StringUtils.hasText(errorMessage)) {
            throw new RuntimeException(errorMessage);
        }
        return saveResult;
    }

    @Override
    public void saveEntityUnitCustomMapping(String mappingKey, List<UnitCustomMapping> unitInfos) {
        ISingleMappingConfig config = this.configDao.query(mappingKey);
        UnitMapping mapping = config.getMapping();
        mapping.setUnitInfos(unitInfos);
        this.configDao.update(config);
    }

    @Override
    public void saveZbConfig(String mappingKey, String formCode, List<SingleFileFieldInfoImpl> zbField) {
        ISingleMappingConfig config = this.configDao.query(mappingKey);
        List<SingleFileFieldInfo> fields = config.getZbFields();
        ArrayList<SingleFileFieldInfo> newFields = new ArrayList<SingleFileFieldInfo>();
        zbField.forEach(e -> newFields.add((SingleFileFieldInfo)e));
        config.setZbFields(this.handleUpdateConfigService.updateConfigZb(formCode, newFields, fields));
        this.configDao.update(config);
    }

    @Override
    public void saveFormulaConfig(String mappingKey, String schemeKey, List<SingleFileFormulaItemImpl> formula) {
        ArrayList<SingleFileFormulaItem> formulas = new ArrayList<SingleFileFormulaItem>();
        for (SingleFileFormulaItemImpl item : formula) {
            formulas.add(item);
        }
        if (!CollectionUtils.isEmpty(formulas)) {
            try {
                ISingleMappingConfig config = this.handleUpdateConfigService.saveFormulas(mappingKey, schemeKey, formulas, new IllegalData());
                this.configDao.update(config);
            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @Override
    public void saveMappingConfig(String mappingKey, MappingConfig config) {
        ISingleMappingConfig query = this.configDao.query(mappingKey);
        query.setConfig(config);
        this.configDao.update(query);
    }

    @Override
    public void updateMappingName(String name, String mappingKey) {
        ISingleMappingConfig query = this.configDao.query(mappingKey);
        query.setConfigName(name);
        this.configDao.update(query);
    }

    @Override
    public String buildConfigName(List<SingleConfigInfo> infos, String fileName, boolean isCopy) {
        if (isCopy) {
            fileName = fileName + "-\u590d\u5236";
        }
        if (infos.size() > 0) {
            String finalConfigName = fileName;
            List filterInfos = infos.stream().filter(e -> e.getConfigName().indexOf(finalConfigName) != -1).collect(Collectors.toList());
            ArrayList<Integer> idxSort = new ArrayList<Integer>();
            boolean hasDefault = false;
            if (filterInfos != null && !filterInfos.isEmpty()) {
                for (int i = 0; i < filterInfos.size(); ++i) {
                    String idxNum;
                    String filterName = ((SingleConfigInfo)filterInfos.get(i)).getConfigName();
                    String fileIndexSuffix = filterName.substring(fileName.length(), filterName.length());
                    if (fileIndexSuffix.isEmpty()) {
                        hasDefault = true;
                        continue;
                    }
                    if (fileIndexSuffix.lastIndexOf(")") != fileIndexSuffix.length() - 1 || !Utils.isInteger(idxNum = fileIndexSuffix.substring(fileIndexSuffix.lastIndexOf("(") + 1, fileIndexSuffix.lastIndexOf(")")))) continue;
                    idxSort.add(Integer.valueOf(idxNum));
                }
            }
            int lastIdx = 0;
            if (idxSort.size() > 0) {
                Collections.sort(idxSort);
                if ((Integer)idxSort.get(0) != 1 && (Integer)idxSort.get(0) != 2) {
                    lastIdx = 2;
                } else {
                    lastIdx = (Integer)idxSort.get(idxSort.size() - 1) + 1;
                    for (int i = idxSort.size() - 1; i > 0; --i) {
                        if ((Integer)idxSort.get(i) - 1 == (Integer)idxSort.get(i - 1)) continue;
                        lastIdx = (Integer)idxSort.get(i) - 1;
                        break;
                    }
                }
            } else if (filterInfos != null && filterInfos.size() > 0) {
                lastIdx = 2;
            }
            if (!hasDefault) {
                lastIdx = 0;
            }
            if (lastIdx != 0) {
                fileName = fileName + "(" + lastIdx + ")";
            }
        }
        return fileName;
    }

    @Override
    public void cleanEntityConfig(String mappingKey) {
        ISingleMappingConfig singleMappingConfig = this.configDao.query(mappingKey);
        UnitMapping unitMapping = singleMappingConfig.getMapping();
        unitMapping.setUnitInfos(new ArrayList<UnitCustomMapping>());
        this.configDao.update(singleMappingConfig);
    }

    @Override
    public void cleanRuleConfig(String mappingKey) {
        ISingleMappingConfig singleMappingConfig = this.configDao.query(mappingKey);
        List<RuleMap> mapRule = singleMappingConfig.getMapRule();
        for (int i = 0; i < mapRule.size(); ++i) {
            RuleMap rule = mapRule.get(i);
            if (rule.getRule().equals((Object)RuleKind.UNIT_MAP_IMPORT)) {
                rule.setSingleCode(null);
                continue;
            }
            if (!rule.getRule().equals((Object)RuleKind.UNIT_MAP_EXPORT)) continue;
            rule.setNetCode(null);
        }
        this.configDao.update(singleMappingConfig);
    }

    @Override
    public void cleanZbConfig(String mappingKey, String reportKey) {
        ISingleMappingConfig singleMappingConfig = this.configDao.query(mappingKey);
        List<SingleFileFieldInfo> zbFields = singleMappingConfig.getZbFields();
        FormDefine form = this.runtimeCtrl.queryFormById(reportKey);
        if (form == null) {
            throw new RuntimeException("\u67e5\u8be2\u4e0d\u5230\u8981\u6e05\u9664\u7684\u62a5\u8868,\u8bf7\u68c0\u67e5\u662f\u5426\u5df2\u53d1\u5e03\u5f53\u524d\u4efb\u52a1!");
        }
        List<SingleFileFieldInfo> filterList = zbFields.stream().filter(e -> e.getNetFormCode() != null && e.getNetFormCode().equals(form.getFormCode())).collect(Collectors.toList());
        ArrayList<SingleFileFieldInfo> updateSingle = new ArrayList<SingleFileFieldInfo>();
        filterList.forEach(e -> {
            e.setFieldCode(null);
            e.setTableCode(null);
            e.setFormCode(null);
            updateSingle.add((SingleFileFieldInfo)e);
        });
        singleMappingConfig.setZbFields(this.handleUpdateConfigService.updateConfigZb(form.getFormCode(), updateSingle, zbFields));
        this.configDao.update(singleMappingConfig);
    }

    @Override
    public void cleanPeriodConfig(String mappingkey) {
        ISingleMappingConfig singleMappingConfig = this.configDao.query(mappingkey);
        UnitMapping mapping = singleMappingConfig.getMapping();
        Map<String, String> periodMapping = mapping.getPeriodMapping();
        for (String key : periodMapping.keySet()) {
            periodMapping.put(key, "");
        }
        this.configDao.update(singleMappingConfig);
    }

    @Override
    public void cleanFormula(String mappingKey, String schemeKey, String reportKey) {
        ISingleMappingConfig singleMappingConfig = this.configDao.query(mappingKey);
        FormDefine form = this.runtimeCtrl.queryFormById(reportKey);
        if (form == null) {
            throw new RuntimeException("\u67e5\u8be2\u4e0d\u5230\u8981\u6e05\u9664\u7684\u62a5\u8868,\u8bf7\u68c0\u67e5\u662f\u5426\u5df2\u53d1\u5e03\u5f53\u524d\u4efb\u52a1!");
        }
        List<SingleFileFormulaItem> infos = singleMappingConfig.getFormulaInfos();
        ArrayList<SingleFileFormulaItem> formulas = new ArrayList<SingleFileFormulaItem>();
        List<SingleFileFormulaItem> filterFormCode = infos.stream().filter(e -> e.getNetSchemeKey() != null && schemeKey.equals(e.getNetSchemeKey())).filter(e -> e.getNetFormCode() != null && form.getFormCode().equals(e.getNetFormCode())).collect(Collectors.toList());
        filterFormCode.forEach(e -> {
            e.setSingleTableCode(null);
            e.setSingleFormulaExp(null);
            e.setSingleFormulaCode(null);
            e.setSingleSchemeName(null);
            formulas.add((SingleFileFormulaItem)e);
        });
        try {
            ISingleMappingConfig config = this.handleUpdateConfigService.saveFormulas(mappingKey, schemeKey, formulas, new IllegalData());
            this.configDao.update(config);
        }
        catch (Exception e2) {
            throw new RuntimeException(e2.getMessage());
        }
    }

    @Override
    public void changeOrder(String mappingKey, boolean moveUp) {
        int otherIndex;
        SingleConfigInfo selectedConfig = this.configDao.queryConfigByKey(mappingKey);
        List<SingleConfigInfo> allMappingInReport = this.getAllMappingInReport(selectedConfig.getSchemeKey());
        int index = -1;
        for (int i = 0; i < allMappingInReport.size(); ++i) {
            if (!allMappingInReport.get(i).getConfigKey().equals(selectedConfig.getConfigKey())) continue;
            index = i;
            break;
        }
        if (index == -1) {
            throw new RuntimeException("\u672a\u627e\u5230\u9700\u8981\u79fb\u52a8\u7684\u914d\u7f6e\u4fe1\u606f");
        }
        if (moveUp) {
            if (index == 0) {
                throw new RuntimeException("\u5df2\u5904\u4e8e\u7b2c\u4e00\u4f4d\uff0c\u65e0\u6cd5\u79fb\u52a8");
            }
            otherIndex = index - 1;
        } else {
            if (index == allMappingInReport.size() - 1) {
                throw new RuntimeException("\u5df2\u5904\u4e8e\u6700\u540e\u4e00\u4f4d\uff0c\u65e0\u6cd5\u79fb\u52a8");
            }
            otherIndex = index + 1;
        }
        SingleConfigInfo otherConfig = allMappingInReport.get(otherIndex);
        String order = selectedConfig.getOrder();
        selectedConfig.setOrder(otherConfig.getOrder());
        otherConfig.setOrder(order);
        this.configDao.updateInfo(otherConfig);
        this.configDao.updateInfo(selectedConfig);
    }

    @Override
    public List<EntityVO> getSeletedEntities(String unitSelectKey) throws Exception {
        ArrayList<EntityVO> entityVOS = new ArrayList<EntityVO>();
        List filterSet = this.uSelectorResultSet.getFilterSet(unitSelectKey);
        if (CollectionUtils.isEmpty(filterSet)) {
            return entityVOS;
        }
        IUnitTreeContext runContext = this.uSelectorResultSet.getRunContext(unitSelectKey);
        String entityId = runContext.getEntityDefine().getId();
        String period = runContext.getPeriod();
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue(entityDefine.getDimensionName(), (Object)filterSet);
        if (StringUtils.hasText(period)) {
            dimensionValueSet.setValue("DATATIME", (Object)period);
        }
        String periodView = this.getPeriodViewByFormSchemeKey(runContext.getFormScheme().getKey());
        List<IEntityRow> allRows = this.listEntityRows(entityId, dimensionValueSet, periodView);
        allRows.forEach(r -> {
            EntityVO entityVO = new EntityVO();
            entityVO.setKey(r.getEntityKeyData());
            entityVO.setCode(r.getCode());
            entityVO.setTitle(r.getTitle());
            entityVOS.add(entityVO);
        });
        return entityVOS;
    }

    @Override
    public EnumMappingVO getEnumMapping(String configKey) {
        EnumMappingVO enumMapping = new EnumMappingVO();
        ArrayList<CommonTreeNode> enumTree = new ArrayList<CommonTreeNode>();
        ArrayList<CommonEnumMapping> enumInfos = new ArrayList<CommonEnumMapping>();
        CommonTreeNode rootNode = new CommonTreeNode();
        rootNode.setCode("-");
        rootNode.setTitle("\u5355\u673a\u7248\u679a\u4e3e\u5b57\u5178");
        rootNode.setExpand(true);
        rootNode.setSelected(true);
        enumTree.add(rootNode);
        ISingleMappingConfig config = this.configDao.query(configKey);
        Map<String, SingleFileEnumInfo> queryEnumInfos = config.getEnumInfos();
        Set<String> enumCodes = queryEnumInfos.keySet();
        for (String enumCode : enumCodes) {
            SingleFileEnumInfo enumInfo = queryEnumInfos.get(enumCode);
            BaseDataDefineDO baseDataDefineDO = null;
            if (StringUtils.hasText(enumInfo.getNetTableCode())) {
                BaseDataDefineDTO basedataDefineDTO = new BaseDataDefineDTO();
                basedataDefineDTO.setName(enumInfo.getNetTableCode());
                baseDataDefineDO = this.baseDataDefineClient.get(basedataDefineDTO);
            }
            if ("sys_taskperiod".equalsIgnoreCase(enumInfo.getEnumCode())) continue;
            CommonEnumMapping mapping = new CommonEnumMapping();
            mapping.setCode(enumInfo.getEnumCode());
            mapping.setTitle(enumInfo.getEnumTitle());
            mapping.setNetCode(enumInfo.getNetTableCode());
            if (baseDataDefineDO != null) {
                mapping.setNetTitle(baseDataDefineDO.getTitle());
            }
            enumInfos.add(mapping);
            rootNode.addChildren(new CommonTreeNode(enumCode, enumInfo.getEnumTitle()));
        }
        enumMapping.setEnumTree(enumTree);
        enumMapping.setEnumInfos(enumInfos);
        return enumMapping;
    }

    @Override
    public EnumItemMappingVO getEnumItemMapping(String configKey, String enumCode) {
        EnumItemMappingVO enumItemMapping = new EnumItemMappingVO();
        ISingleMappingConfig config = this.configDao.query(configKey);
        Map<String, SingleFileEnumInfo> enumInfos = config.getEnumInfos();
        SingleFileEnumInfo singleFileEnumInfo = enumInfos.get(enumCode);
        List<SingleFileEnumItem> enumItems = singleFileEnumInfo.getEnumItems();
        ArrayList<CommonEnumMapping> mappings = new ArrayList<CommonEnumMapping>(enumItems.size());
        for (SingleFileEnumItem enumItem : enumItems) {
            CommonEnumMapping mapping = new CommonEnumMapping();
            mapping.setCode(enumItem.getItemCode());
            mapping.setTitle(enumItem.getItemTitle());
            mapping.setNetCode(enumItem.getNetItemCode());
            mapping.setNetTitle(enumItem.getNetItemTitle());
            mappings.add(mapping);
        }
        enumItemMapping.setEnumItems(mappings);
        return enumItemMapping;
    }

    @Override
    public void saveEnumMapping(EnumMappingVO enumMappingVO) {
        ISingleMappingConfig config = this.configDao.query(enumMappingVO.getConfigKey());
        Map<String, SingleFileEnumInfo> queryEnumInfo = config.getEnumInfos();
        List<CommonEnumMapping> enumInfos = enumMappingVO.getEnumInfos();
        for (CommonEnumMapping enumInfo : enumInfos) {
            TableModelDefine tableModelDefineByCode;
            SingleFileEnumInfo singleFileEnumInfo = queryEnumInfo.get(enumInfo.getCode());
            if (singleFileEnumInfo == null || (tableModelDefineByCode = this.dataModelService.getTableModelDefineByCode(enumInfo.getNetCode())) == null) continue;
            String oldCode = singleFileEnumInfo.getNetTableCode();
            singleFileEnumInfo.setNetTableCode(enumInfo.getNetCode());
            singleFileEnumInfo.setNetTableKey(tableModelDefineByCode.getID());
            if (StringUtils.hasText(enumInfo.getNetCode()) && (!StringUtils.hasText(enumInfo.getNetCode()) || enumInfo.getNetCode().equals(oldCode))) continue;
            List<SingleFileEnumItem> enumItems = singleFileEnumInfo.getEnumItems();
            for (SingleFileEnumItem enumItem : enumItems) {
                enumItem.setNetItemCode(null);
                enumItem.setNetItemTitle(null);
            }
        }
        this.configDao.update(config);
    }

    @Override
    public void saveEnumItemMapping(EnumItemMappingVO enumItemMappingVO) {
        ISingleMappingConfig config = this.configDao.query(enumItemMappingVO.getConfigKey());
        Map<String, SingleFileEnumInfo> queryEnumInfo = config.getEnumInfos();
        String enumCode = enumItemMappingVO.getEnumCode();
        SingleFileEnumInfo queryEnumMapping = queryEnumInfo.get(enumCode);
        List<CommonEnumMapping> enumItems = enumItemMappingVO.getEnumItems();
        Map<String, CommonEnumMapping> saveMap = enumItems.stream().collect(Collectors.toMap(CommonEnumMapping::getCode, e -> e, (e1, e2) -> e2));
        if (queryEnumMapping != null) {
            ArrayList<String> updateCode = new ArrayList<String>();
            List<SingleFileEnumItem> queryEnumItems = queryEnumMapping.getEnumItems();
            for (SingleFileEnumItem queryEnumItem : queryEnumItems) {
                String itemCode = queryEnumItem.getItemCode();
                CommonEnumMapping updateMapping = saveMap.get(itemCode);
                if (updateMapping == null) continue;
                queryEnumItem.setNetItemCode(updateMapping.getNetCode());
                queryEnumItem.setNetItemTitle(updateMapping.getNetTitle());
                updateCode.add(itemCode);
            }
            if (updateCode.size() != enumItems.size()) {
                List addMapping = enumItems.stream().filter(e -> !updateCode.contains(e.getCode())).collect(Collectors.toList());
                for (CommonEnumMapping mapping : addMapping) {
                    SingleFileEnumItemImpl item = new SingleFileEnumItemImpl();
                    item.setItemCode(mapping.getCode());
                    item.setItemTitle(mapping.getTitle());
                    item.setNetItemCode(mapping.getNetCode());
                    item.setNetItemTitle(mapping.getNetTitle());
                    queryEnumItems.add(item);
                }
            }
            queryEnumMapping.setEnumItems(queryEnumItems);
            this.configDao.update(config);
        }
    }

    @Override
    public EnumItemMappingVO quickMatch(String configKey, String enumCode, String type) {
        EnumItemMappingVO enumItemMapping = new EnumItemMappingVO();
        ISingleMappingConfig config = this.configDao.query(configKey);
        Map<String, SingleFileEnumInfo> enumInfos = config.getEnumInfos();
        SingleFileEnumInfo singleFileEnumInfo = enumInfos.get(enumCode);
        BaseDataDefineDTO basedataDefineDTO = new BaseDataDefineDTO();
        String netTableCode = singleFileEnumInfo.getNetTableCode();
        basedataDefineDTO.setName(netTableCode);
        BaseDataDefineDO baseDataDefineDO = this.baseDataDefineClient.get(basedataDefineDTO);
        if (baseDataDefineDO == null) {
            throw new RuntimeException("\u672a\u627e\u5230\u6807\u8bc6\u4e3a" + netTableCode + "\u7684\u57fa\u7840\u6570\u636e");
        }
        List<SingleFileEnumItem> enumItems = singleFileEnumInfo.getEnumItems();
        ArrayList<CommonEnumMapping> mappings = new ArrayList<CommonEnumMapping>(enumItems.size());
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName(netTableCode);
        PageVO pageVO = this.baseDataClient.list(baseDataDTO);
        List rows = pageVO.getRows();
        Map<String, String> codeToName = rows.stream().collect(Collectors.toMap(BaseDataDO::getCode, BaseDataDO::getName, (e1, e2) -> e2));
        Map<String, String> nameToCode = rows.stream().collect(Collectors.toMap(BaseDataDO::getName, BaseDataDO::getCode, (e1, e2) -> e2));
        for (SingleFileEnumItem enumItem : enumItems) {
            CommonEnumMapping mapping = new CommonEnumMapping();
            mapping.setCode(enumItem.getItemCode());
            mapping.setTitle(enumItem.getItemTitle());
            if (ENUM_MATCH_CODE.equals(type)) {
                String itemCode = enumItem.getItemCode();
                String name = codeToName.get(itemCode);
                if (name != null) {
                    mapping.setNetCode(itemCode);
                    mapping.setNetTitle(name);
                } else {
                    mapping.setNetCode(enumItem.getNetItemCode());
                    mapping.setNetTitle(enumItem.getNetItemTitle());
                }
            } else {
                String itemTitle = enumItem.getItemTitle();
                String code = nameToCode.get(itemTitle);
                if (code != null) {
                    mapping.setNetTitle(itemTitle);
                    mapping.setNetCode(code);
                } else {
                    mapping.setNetTitle(enumItem.getNetItemTitle());
                    mapping.setNetCode(enumItem.getNetItemCode());
                }
            }
            mappings.add(mapping);
        }
        enumItemMapping.setEnumItems(mappings);
        return enumItemMapping;
    }

    @Override
    public List<IEntityRow> listEntityRows(String entityId, DimensionValueSet dimensionValueSet, String periodView) throws Exception {
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.setMasterKeys(dimensionValueSet);
        ExecutorContext context = new ExecutorContext(this.dataRuntimeCtrl);
        context.setPeriodView(periodView);
        IEntityTable iEntityTable = entityQuery.executeReader((IContext)context);
        return iEntityTable.getAllRows();
    }

    @Override
    public List<IEntityRow> listEntityRows(String formSchemeKey) throws Exception {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)this.queryPeriod(formSchemeKey));
        String dw = this.getEntityIdByFormSchemeKey(formSchemeKey);
        String periodView = this.getPeriodViewByFormSchemeKey(formSchemeKey);
        return this.listEntityRows(dw, dimensionValueSet, periodView);
    }

    private String getEntityIdByFormSchemeKey(String formSchemeKey) {
        DesignFormSchemeDefine formSchemeDefine = this.desCtrl.queryFormSchemeDefine(formSchemeKey);
        String dw = formSchemeDefine.getDw();
        if (!StringUtils.hasText(dw)) {
            DesignTaskDefine designTaskDefine = this.desCtrl.queryTaskDefine(formSchemeDefine.getTaskKey());
            dw = designTaskDefine.getDw();
        }
        return dw;
    }

    private String getPeriodViewByFormSchemeKey(String formSchemeKey) {
        DesignFormSchemeDefine formSchemeDefine = this.desCtrl.queryFormSchemeDefine(formSchemeKey);
        String periodView = formSchemeDefine.getDateTime();
        if (!StringUtils.hasText(periodView)) {
            DesignTaskDefine designTaskDefine = this.desCtrl.queryTaskDefine(formSchemeDefine.getTaskKey());
            periodView = designTaskDefine.getDateTime();
        }
        return periodView;
    }

    @Override
    public String queryPeriod(String formSchemeKey) {
        List linkDefines = null;
        try {
            linkDefines = this.desCtrl.querySchemePeriodLinkByScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (CollectionUtils.isEmpty(linkDefines)) {
            return null;
        }
        if (linkDefines != null) {
            for (int i = 0; i < linkDefines.size(); ++i) {
                if (!((DesignSchemePeriodLinkDefine)linkDefines.get(i)).getIsdefault() && i != linkDefines.size() - 1) continue;
                return ((DesignSchemePeriodLinkDefine)linkDefines.get(i)).getPeriodKey();
            }
        }
        return null;
    }

    @Override
    public List<EntityVO> queryEntityAttribute(String formSchemeKey) {
        String entityId = this.getEntityIdByFormSchemeKey(formSchemeKey);
        if (entityId == null) {
            return Collections.emptyList();
        }
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        Iterator attributes = entityModel.getAttributes();
        ArrayList<EntityVO> entityAttributes = new ArrayList<EntityVO>();
        while (attributes.hasNext()) {
            IEntityAttribute attribute = (IEntityAttribute)attributes.next();
            EntityVO vo = new EntityVO();
            vo.setKey(attribute.getID());
            vo.setCode(attribute.getCode());
            vo.setTitle(attribute.getTitle());
            entityAttributes.add(vo);
        }
        return entityAttributes;
    }
}

