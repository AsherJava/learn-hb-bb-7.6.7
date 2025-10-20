/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.utils.DateTimeCenter
 *  com.jiuqi.budget.domain.GroupType
 *  com.jiuqi.budget.param.group.domain.BaseGroupDO
 *  com.jiuqi.budget.param.group.domain.BaseGroupDTO
 *  com.jiuqi.budget.param.group.service.BudGroupService
 *  com.jiuqi.budget.param.hypermodel.domain.HyperDataSchemeDO
 *  com.jiuqi.budget.param.hypermodel.domain.ModelStateType
 *  com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO
 *  com.jiuqi.budget.param.hypermodel.domain.enums.BudGatherType
 *  com.jiuqi.budget.param.hypermodel.domain.enums.DataSchemeType
 *  com.jiuqi.budget.param.hypermodel.domain.enums.ModelType
 *  com.jiuqi.budget.param.hypermodel.service.HyperDataSchemeService
 *  com.jiuqi.budget.param.hypermodel.service.HyperModelDefineService
 *  com.jiuqi.budget.param.hypermodel.service.HyperModelService
 *  com.jiuqi.budget.param.hypermodel.service.impl.HyperDataSchemeBizService
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.nvwa.definition.common.ColumnModelKind
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.bean.CatalogModelDefineImpl
 *  com.jiuqi.nvwa.definition.interval.bean.design.DesignColumnModelDefineImpl
 *  com.jiuqi.nvwa.definition.interval.bean.design.DesignTableModelDefineImpl
 *  com.jiuqi.nvwa.definition.interval.dao.ColumnModelDao
 *  com.jiuqi.nvwa.definition.interval.dao.DataModelDao
 *  com.jiuqi.nvwa.definition.interval.dao.DesignDataModelDao
 *  com.jiuqi.nvwa.definition.interval.dao.IndexModelDao
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.base;

import com.jiuqi.budget.common.utils.DateTimeCenter;
import com.jiuqi.budget.domain.GroupType;
import com.jiuqi.budget.param.group.domain.BaseGroupDO;
import com.jiuqi.budget.param.group.domain.BaseGroupDTO;
import com.jiuqi.budget.param.group.service.BudGroupService;
import com.jiuqi.budget.param.hypermodel.domain.HyperDataSchemeDO;
import com.jiuqi.budget.param.hypermodel.domain.ModelStateType;
import com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO;
import com.jiuqi.budget.param.hypermodel.domain.enums.BudGatherType;
import com.jiuqi.budget.param.hypermodel.domain.enums.DataSchemeType;
import com.jiuqi.budget.param.hypermodel.domain.enums.ModelType;
import com.jiuqi.budget.param.hypermodel.service.HyperDataSchemeService;
import com.jiuqi.budget.param.hypermodel.service.HyperModelDefineService;
import com.jiuqi.budget.param.hypermodel.service.HyperModelService;
import com.jiuqi.budget.param.hypermodel.service.impl.HyperDataSchemeBizService;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.anno.intf.ITableGroupDefine;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionGroupV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableIndexV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.base.util.DataBaseKeyFieldUtil;
import com.jiuqi.gcreport.definition.impl.basic.base.util.UUIDTool;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.nvwa.definition.common.ColumnModelKind;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.bean.CatalogModelDefineImpl;
import com.jiuqi.nvwa.definition.interval.bean.design.DesignColumnModelDefineImpl;
import com.jiuqi.nvwa.definition.interval.bean.design.DesignTableModelDefineImpl;
import com.jiuqi.nvwa.definition.interval.dao.ColumnModelDao;
import com.jiuqi.nvwa.definition.interval.dao.DataModelDao;
import com.jiuqi.nvwa.definition.interval.dao.DesignDataModelDao;
import com.jiuqi.nvwa.definition.interval.dao.IndexModelDao;
import com.jiuqi.nvwa.definition.service.CatalogModelService;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

public class DeployTableProcessor {
    private static Logger logger = LoggerFactory.getLogger(DeployTableProcessor.class);
    private final DefinitionTableV definitionTable;
    private final DesignDataModelService designDataModelService;
    private final DataModelDeployService dataModelDeployService;
    private final HyperModelDefineService hyperModelDefineService;
    private final HyperModelService hyperModelService;
    private final BudGroupService groupService;
    private final HyperDataSchemeService hyperDataSchemeService;
    private final HyperDataSchemeBizService hyperDataSchemeBizService;
    private final DesignDataModelDao designDataModelDao;
    private final DataModelDao dataModelDao;
    private final ColumnModelDao columnModelDao;
    private final IndexModelDao indexModelDao;
    protected StringBuilder publishLog;
    protected List<String> invalidFields;
    protected String[] bizFieldIds;
    protected boolean needDeployTableDefine;

    public static DeployTableProcessor newInstance(DefinitionTableV definitionTable) {
        return new DeployTableProcessor(definitionTable);
    }

    protected DeployTableProcessor(DefinitionTableV definitionTable) {
        this.definitionTable = definitionTable;
        this.designDataModelService = (DesignDataModelService)SpringContextUtils.getBean(DesignDataModelService.class);
        this.dataModelDeployService = (DataModelDeployService)SpringContextUtils.getBean(DataModelDeployService.class);
        this.hyperModelDefineService = (HyperModelDefineService)SpringContextUtils.getBean(HyperModelDefineService.class);
        this.hyperModelService = (HyperModelService)SpringContextUtils.getBean(HyperModelService.class);
        this.groupService = (BudGroupService)SpringContextUtils.getBean(BudGroupService.class);
        this.hyperDataSchemeService = (HyperDataSchemeService)SpringContextUtils.getBean(HyperDataSchemeService.class);
        this.hyperDataSchemeBizService = (HyperDataSchemeBizService)SpringContextUtils.getBean(HyperDataSchemeBizService.class);
        this.designDataModelDao = (DesignDataModelDao)SpringContextUtils.getBean(DesignDataModelDao.class);
        this.dataModelDao = (DataModelDao)SpringContextUtils.getBean(DataModelDao.class);
        this.columnModelDao = (ColumnModelDao)SpringContextUtils.getBean(ColumnModelDao.class);
        this.indexModelDao = (IndexModelDao)SpringContextUtils.getBean(IndexModelDao.class);
    }

    public void deploy() throws Exception {
        this.publishLog = new StringBuilder("\u521d\u59cb\u5316\u8868" + this.definitionTable.getTableName() + ": \n ");
        this.invalidFields = new ArrayList<String>();
        this.bizFieldIds = new String[this.definitionTable.getBizKeyFields().length];
        this.deployCatalog(this.definitionTable.getOwnerGroup());
        DesignTableModelDefine designTableDefine = this.deployTableDefine();
        this.definitionTable.setKey(designTableDefine.getID());
        this.deployColumns();
        this.processInvalidFields();
        this.deployIndexs();
        this.resetTableKeys();
        if (this.needDeployTableDefine) {
            this.dataModelDeployService.deployTable(designTableDefine.getID());
            logger.info(this.publishLog.toString());
        }
    }

    protected void deployBudModel() throws Exception {
        if (!StringUtils.isEmpty((String)this.definitionTable.getModelTableName())) {
            EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
            entityTableCollector.addToModelTableMap(this.definitionTable.getModelTableName(), this.definitionTable);
        }
        if (Objects.nonNull(this.definitionTable.getBudModelType()) && Boolean.FALSE.equals(this.hyperModelService.isExist(this.definitionTable.getModelTableName()))) {
            this.checkModuleGroup();
            ShowModelDTO showModelDTO = this.getShowModelDTO();
            showModelDTO.setMeasurementDOs((List)CollectionUtils.newArrayList());
            showModelDTO.setDimCodeList((List)CollectionUtils.newArrayList());
            showModelDTO.setDimensionDOs((List)CollectionUtils.newArrayList());
            this.hyperModelDefineService.insertOrUpdate(showModelDTO);
        }
    }

    @NotNull
    private ShowModelDTO getShowModelDTO() {
        ShowModelDTO showModelDTO = new ShowModelDTO();
        showModelDTO.setId(UUIDUtils.newUUIDStr());
        showModelDTO.setCode(this.definitionTable.getModelTableName());
        showModelDTO.setName(this.definitionTable.getTitle());
        showModelDTO.setPeriodType(ModelType.NORMAL.equals((Object)this.definitionTable.getBudModelType()) ? null : "Y");
        showModelDTO.setModelType(this.definitionTable.getBudModelType());
        showModelDTO.setPublishState(ModelStateType.PUBLISHED);
        showModelDTO.setParentId(this.definitionTable.getOwnerGroup().getId());
        showModelDTO.setOrderNum(BigDecimal.ZERO);
        showModelDTO.setDataschemeID("b9add117-a540-4904-bdf7-c0e0d992a68b");
        showModelDTO.setCanModifyModelType(true);
        showModelDTO.setBudGatherType(BudGatherType.LIST);
        showModelDTO.setDsCode(this.definitionTable.getDataSource());
        return showModelDTO;
    }

    protected void deployCatalog(DefinitionGroupV group) {
        CatalogModelService catalogModelService = (CatalogModelService)SpringContextUtils.getBean(CatalogModelService.class);
        assert (catalogModelService != null);
        DesignCatalogModelDefine catalogModelDefine = catalogModelService.getCatalogModelDefine(group.getId());
        if (catalogModelDefine != null) {
            return;
        }
        DesignCatalogModelDefine newCatalog = this.toDesignCatalogModelDefine(group);
        if (!"11000000-0000-0000-0000-000000000001".equals(group.getId())) {
            this.deployCatalog(new ITableGroupDefine(){}.initGroup(group.getTable()));
        }
        catalogModelService.insertCatalogModelDefine(newCatalog);
    }

    private DesignCatalogModelDefine toDesignCatalogModelDefine(DefinitionGroupV definitionGroup) {
        if (ObjectUtils.isEmpty(definitionGroup.getId())) {
            definitionGroup.setId(UUIDTool.newUUIDString36());
        }
        CatalogModelDefineImpl catalogDefine = new CatalogModelDefineImpl();
        catalogDefine.setID(definitionGroup.getId());
        catalogDefine.setTitle(definitionGroup.getTitle());
        catalogDefine.setParentID(definitionGroup.getParentId());
        return catalogDefine;
    }

    protected DesignTableModelDefine deployTableDefine() throws Exception {
        try {
            this.deployBudModel();
        }
        catch (Exception e) {
            logger.error("\u3010{}\u3011\u9884\u7b97\u6a21\u578b\u53d1\u5e03\u5931\u8d25", (Object)this.definitionTable.getModelTableName(), (Object)e);
        }
        DesignTableModelDefine oldTab = this.getTableModelDefineByCode(this.definitionTable.getCode(), this.definitionTable.getDataSource());
        if (oldTab != null) {
            return oldTab;
        }
        DesignTableModelDefine oldTable = null;
        List defines = this.designDataModelDao.list(new String[]{"code"}, new Object[]{this.definitionTable.getCode()}, DesignTableModelDefineImpl.class);
        if (null != defines && defines.size() > 0) {
            oldTable = (DesignTableModelDefine)defines.get(0);
        }
        if (Objects.nonNull(oldTable)) {
            oldTable.setDataSource(this.definitionTable.getDataSource());
            this.designDataModelService.updateTableModelDefine(oldTable);
            this.indexModelDao.removeIndexsByTable(oldTable.getID());
            this.columnModelDao.deleteColumnModelDefineByTable(oldTable.getID());
            this.dataModelDao.deleteRunTableDefines(new String[]{oldTable.getID()});
            this.needDeployTableDefine = true;
            return oldTable;
        }
        DesignTableModelDefine designTableDefine = this.toDesignTableDefineImpl(this.definitionTable);
        this.checkTableKind(designTableDefine);
        this.needDeployTableDefine = true;
        this.designDataModelService.insertTableModelDefine(designTableDefine);
        this.publishLog.append("\u65b0\u589e\u8868\u3002");
        return this.getTableModelDefineByCode(designTableDefine.getCode(), designTableDefine.getDataSource());
    }

    private DesignTableModelDefine getTableModelDefineByCode(String tableCode, String dataSourceCode) {
        if (StringUtils.isEmpty((String)dataSourceCode)) {
            return this.designDataModelService.getTableModelDefineByCode(tableCode);
        }
        return this.designDataModelService.getTableModelDefineByCode(tableCode, dataSourceCode);
    }

    private void checkTableKind(DesignTableModelDefine table) {
        String baseDataTablePrefix = "MD_";
        if (table.getName().startsWith(baseDataTablePrefix)) {
            throw new RuntimeException(table.getName() + "\u4ee5MD_\u5f00\u5934\u7684\u4e3b\u4f53\u6216\u8005\u679a\u4e3e\u9700\u8981\u6ce8\u518c\u5728\u57fa\u7840\u6570\u636e\u6a21\u5757\uff0c\u672c\u65b9\u6cd5\u4e0d\u652f\u6301\u521d\u59cb\u5316\u3002");
        }
    }

    protected void deployColumns() throws Exception {
        for (DefinitionFieldV definitionFieldV : this.definitionTable.getFields()) {
            this.deployColumn(definitionFieldV);
        }
        for (int i = 0; i < this.definitionTable.getBizKeyFields().length; ++i) {
            if (this.bizFieldIds[i] != null || !this.definitionTable.isPrimaryRequired()) continue;
            throw new BusinessRuntimeException("\u8868\u3010" + this.definitionTable.getTableName() + "\u3011\u7684\u4e1a\u52a1\u4e3b\u952e\u5b57\u6bb5\u3010" + this.definitionTable.getBizKeyFields()[i] + "\u3011\u4e0d\u5b58\u5728\u3002");
        }
    }

    private void deployColumn(DefinitionFieldV definitionField) throws Exception {
        Environment environment = (Environment)SpringContextUtils.getBean(Environment.class);
        boolean allowKeyword = Boolean.parseBoolean(environment.getProperty("jiuqi.nvwa.database.allowKeyword", "false"));
        if (!allowKeyword && DataBaseKeyFieldUtil.isKeyField(definitionField.getCode())) {
            this.invalidFields.add(definitionField.getCode());
            return;
        }
        this.initRefField(definitionField);
        DesignColumnModelDefineImpl designFieldDefine = this.toDesignFieldDefineImpl(definitionField);
        DesignColumnModelDefine oldField = this.designDataModelService.getColumnModelDefineByCode(this.definitionTable.getKey(), designFieldDefine.getCode());
        if (oldField == null) {
            this.needDeployTableDefine = true;
            this.designDataModelService.insertColumnModelDefine((DesignColumnModelDefine)designFieldDefine);
            this.publishLog.append("\u65b0\u589e\u5b57\u6bb5").append(designFieldDefine.getCode()).append("\u3002");
        } else {
            designFieldDefine.setID(oldField.getID());
        }
        if (!(!Objects.nonNull(oldField) || Objects.equals(designFieldDefine.getDefaultValue(), oldField.getDefaultValue()) && Objects.equals(designFieldDefine.isNullAble(), oldField.isNullAble()))) {
            this.needDeployTableDefine = true;
            designFieldDefine.setColumnType(oldField.getColumnType());
            designFieldDefine.setDecimal(oldField.getDecimal());
            designFieldDefine.setPrecision(oldField.getPrecision());
            this.designDataModelService.updateColumnModelDefine((DesignColumnModelDefine)designFieldDefine);
            this.publishLog.append("\u66f4\u65b0\u5b57\u6bb5").append(designFieldDefine.getCode()).append("\u3002");
        }
        for (int i = 0; i < this.definitionTable.getBizKeyFields().length; ++i) {
            if (!this.definitionTable.getBizKeyFields()[i].equals(designFieldDefine.getName())) continue;
            this.bizFieldIds[i] = designFieldDefine.getID();
        }
    }

    private void initRefField(DefinitionFieldV field) {
        DesignColumnModelDefine refFieldDefine;
        if (ObjectUtils.isEmpty(field.getRefTabField())) {
            return;
        }
        String[] tableAndField = field.getRefTabField().split("\\.");
        DesignTableModelDefine refTableDefine = this.getTableModelDefineByCode(tableAndField[0], this.definitionTable.getDataSource());
        if (refTableDefine == null) {
            return;
        }
        if (tableAndField.length > 1) {
            DesignColumnModelDefine refFieldDefine2 = this.designDataModelService.getColumnModelDefineByCode(refTableDefine.getID(), tableAndField[1]);
            if (refFieldDefine2 != null) {
                field.setReferTable(refTableDefine.getID());
                field.setReferField(refFieldDefine2.getID());
            }
        } else if (refTableDefine.getBizKeys() != null && refTableDefine.getBizKeys().split(";").length == 1 && (refFieldDefine = this.designDataModelService.getColumnModelDefine(refTableDefine.getBizKeys())) != null) {
            field.setReferTable(refTableDefine.getID());
            field.setReferField(refTableDefine.getBizKeys());
        }
    }

    protected void processInvalidFields() {
        if (!this.invalidFields.isEmpty()) {
            StringBuilder errLog = new StringBuilder("\u8868(" + this.definitionTable.getTableName() + ") \u5b57\u6bb5(");
            this.invalidFields.forEach(v -> errLog.append("\u3010").append((String)v).append("\u3011"));
            errLog.append(") \u662f\u5173\u952e\u5b57\uff0c\u8bf7\u8c03\u6574\u5b57\u6bb5\u540d\u79f0");
            logger.info(errLog.toString());
            if (this.needDeployTableDefine) {
                logger.error("\u670d\u52a1\u5c06\u81ea\u52a8\u5173\u95ed\uff0c\u8bf7\u4fee\u6539\u5b57\u6bb5\u540d\u4e3a\u5173\u952e\u5b57\u7684\u4ee3\u7801\u540e\u91cd\u542f\u670d\u52a1\u3002");
                System.exit(0);
            }
        }
    }

    protected void deployIndexs() {
        if (CollectionUtils.isEmpty(this.definitionTable.getIndexs())) {
            return;
        }
        List fields = this.designDataModelService.getColumnModelDefinesByTable(this.definitionTable.getKey());
        Map<String, DesignColumnModelDefine> fieldGroupByCode = fields.stream().collect(Collectors.toMap(IModelDefineItem::getCode, e -> e));
        List indexs = this.designDataModelService.getIndexsByTable(this.definitionTable.getKey());
        for (DefinitionTableIndexV index : this.definitionTable.getIndexs()) {
            this.initTableIndex(index, fieldGroupByCode);
            if (!this.canAddIndex(indexs, index)) continue;
            this.designDataModelService.addIndexToTable(this.definitionTable.getKey(), index.getColumnIds(), index.getTitle(), index.getIndexType());
            this.needDeployTableDefine = true;
            this.publishLog.append("\u8868").append(this.definitionTable.getTableName()).append("\u7d22\u5f15").append(index.getTitle()).append("\u65b0\u589e\u6216\u66f4\u65b0\u3002");
        }
    }

    private boolean canAddIndex(List<DesignIndexModelDefine> existsIndexs, DefinitionTableIndexV newIndex) {
        if (CollectionUtils.isEmpty(existsIndexs)) {
            return true;
        }
        for (DesignIndexModelDefine index : existsIndexs) {
            if (!DeployTableProcessor.equalArray(index.getFieldIDs().split(";"), newIndex.getColumnIds())) continue;
            return false;
        }
        return true;
    }

    private static boolean equalArray(String[] a, String[] b) {
        if (a == null || b == null) {
            return false;
        }
        Object[] newA = Arrays.copyOf(a, a.length);
        Object[] newB = Arrays.copyOf(b, b.length);
        Arrays.sort(newA);
        Arrays.sort(newB);
        return Arrays.equals(newA, newB);
    }

    protected void resetTableKeys() throws Exception {
        DesignTableModelDefine modelDefine = this.getTableModelDefineByCode(this.definitionTable.getCode(), this.definitionTable.getDataSource());
        List fields = this.designDataModelService.getColumnModelDefinesByTable(this.definitionTable.getKey());
        Map<String, DesignColumnModelDefine> fieldGroupByCode = fields.stream().collect(Collectors.toMap(IModelDefineItem::getCode, e -> e));
        boolean updateTable = false;
        if (ObjectUtils.isEmpty(modelDefine.getKeys()) && !CollectionUtils.isEmpty(this.definitionTable.getPkeys())) {
            StringBuilder keyIdsBuilder = new StringBuilder();
            this.definitionTable.getPkeys().forEach(fieldCode -> {
                if (!fieldGroupByCode.containsKey(fieldCode)) {
                    throw new BusinessRuntimeException("\u8868\u3010" + this.definitionTable.getTableName() + "\u3011\u4e3b\u952e\u5b57\u6bb5\u3010" + fieldCode + "\u3011\u4e0d\u5b58\u5728\u3002");
                }
                keyIdsBuilder.append(((DesignColumnModelDefine)fieldGroupByCode.get(fieldCode)).getID()).append(";");
            });
            modelDefine.setKeys(keyIdsBuilder.deleteCharAt(keyIdsBuilder.length() - 1).toString());
            updateTable = true;
        }
        if (ObjectUtils.isEmpty(modelDefine.getBizKeys()) && !CollectionUtils.isEmpty((Object[])this.bizFieldIds)) {
            StringBuilder bizKeyIdsBuilder = new StringBuilder();
            for (String key : this.bizFieldIds) {
                bizKeyIdsBuilder.append(key);
                bizKeyIdsBuilder.append(";");
            }
            if (bizKeyIdsBuilder.length() > 0) {
                modelDefine.setBizKeys(bizKeyIdsBuilder.deleteCharAt(bizKeyIdsBuilder.length() - 1).toString());
                updateTable = true;
            }
        }
        if (updateTable) {
            modelDefine.setUpdateTime(new Date());
            this.designDataModelService.updateTableModelDefine(modelDefine);
            this.needDeployTableDefine = true;
        }
    }

    private DesignTableModelDefine toDesignTableDefineImpl(DefinitionTableV definitionTable) {
        definitionTable.initPrimaryIndex();
        DesignTableModelDefineImpl designTableDefine = new DesignTableModelDefineImpl();
        if (ObjectUtils.isEmpty(definitionTable.getKey())) {
            definitionTable.setKey(UUIDUtils.newUUIDStr());
        }
        designTableDefine.setID(definitionTable.getKey());
        designTableDefine.setCatalogID(definitionTable.getOwnerGroup().getId());
        designTableDefine.setCode(definitionTable.getCode());
        designTableDefine.setName(definitionTable.getTableName());
        designTableDefine.setTitle(definitionTable.getTitle());
        designTableDefine.setDesc(definitionTable.getDescription());
        designTableDefine.setType(definitionTable.getTableType());
        designTableDefine.setOwner(definitionTable.getOrder());
        designTableDefine.setKind(definitionTable.getKind());
        designTableDefine.setCreateTime(new Date());
        designTableDefine.setUpdateTime(new Date());
        designTableDefine.setSupportI18n(true);
        designTableDefine.setDataSource(definitionTable.getDataSource());
        return designTableDefine;
    }

    private DesignColumnModelDefineImpl toDesignFieldDefineImpl(DefinitionFieldV definitionField) {
        DesignColumnModelDefineImpl impl = new DesignColumnModelDefineImpl();
        if (ObjectUtils.isEmpty(definitionField.getKey())) {
            definitionField.setKey(UUIDTool.newUUIDString36());
        }
        impl.setID(definitionField.getKey());
        impl.setTableID(this.definitionTable.getKey());
        impl.setCode(definitionField.getCode());
        if (ObjectUtils.isEmpty(definitionField.getFieldName())) {
            impl.setName(definitionField.getCode());
        } else {
            impl.setName(definitionField.getFieldName());
        }
        impl.setTitle(definitionField.getTitle());
        impl.setDesc(definitionField.getDescription());
        impl.setCatagory(this.definitionTable.getTableName());
        impl.setColumnType(ColumnModelType.forValue((int)definitionField.getDbType().getBiType()));
        impl.setPrecision(definitionField.getSize());
        impl.setDecimal(definitionField.getFractionDigits());
        impl.setNullAble(definitionField.isNullable());
        impl.setDefaultValue(definitionField.getDefaultValue());
        impl.setReferTableID(definitionField.getReferTable());
        impl.setReferColumnID(definitionField.getReferField());
        impl.setShowFormat(definitionField.getShowFormat());
        impl.setOrder(definitionField.getOrder());
        impl.setMeasureUnit(definitionField.getMeasureUnitKey());
        impl.setKind(ColumnModelKind.SYSTEM);
        return impl;
    }

    private void initTableIndex(DefinitionTableIndexV index, Map<String, DesignColumnModelDefine> fieldGroupByCode) {
        if (index.getColumnsFields() == null || index.getColumnsFields().length == 0) {
            throw new RuntimeException("\u8868:" + this.definitionTable.getTableName() + "\u7d22\u5f15:" + index.getTitle() + "\u5b57\u6bb5\u5217\u5fc5\u586b");
        }
        String[] columnIds = new String[index.getColumnsFields().length];
        for (int i = 0; i < index.getColumnsFields().length; ++i) {
            String[] fieldCode = index.getColumnsFields()[i].toUpperCase();
            if (!fieldGroupByCode.containsKey(fieldCode)) {
                throw new RuntimeException("\u8868:" + this.definitionTable.getTableName() + "\u7d22\u5f15:" + index.getTitle() + "\u4e2d\u5b57\u6bb5\u201c" + (String)fieldCode + "\u201d\u4e0d\u5b58\u5728");
            }
            columnIds[i] = fieldGroupByCode.get(fieldCode).getID();
        }
        index.setColumnsIds(columnIds);
        if (index.getIndexType() == null) {
            index.setIndexType(IndexModelType.NORMAL);
        }
        if (ObjectUtils.isEmpty(index.getTitle())) {
            StringBuilder sb = new StringBuilder("index");
            sb.append(index.getIndexType().getValue()).append("_");
            sb.append(this.definitionTable.getTableName());
            for (String column : index.getColumnsFields()) {
                sb.append("_").append(column);
            }
            String name = index.getIndexType().name() + "_" + DigestUtils.md5DigestAsHex(sb.toString().getBytes());
            index.setTitle(name);
        }
        if (index.getTitle().length() > 30) {
            index.setTitle("idx_" + System.nanoTime());
        }
    }

    private void checkModuleGroup() {
        if (ObjectUtils.isEmpty(this.hyperDataSchemeService.getByCode("GCCUSTOM"))) {
            HyperDataSchemeDO schemeDO = new HyperDataSchemeDO();
            schemeDO.setCode("GCCUSTOM");
            schemeDO.setName("\u5408\u5e76\u62a5\u8868\u65b9\u6848");
            schemeDO.setParentId("ALL");
            schemeDO.setId("b9add117-a540-4904-bdf7-c0e0d992a68b");
            schemeDO.setDataSchemeType(DataSchemeType.OTHER);
            this.hyperDataSchemeBizService.designInsertNrDataScheme(schemeDO);
            schemeDO.setCreateTime(DateTimeCenter.convertDateToLDT((Date)new Date()));
            schemeDO.setParentId("00000000-0000-0000-0000-000000000000");
            schemeDO.setTenantName("__default_tenant__");
            this.hyperDataSchemeBizService.delopyInsert(schemeDO);
        }
        DefinitionGroupV groupV = this.definitionTable.getOwnerGroup();
        BaseGroupDO groupDO = new BaseGroupDO();
        groupDO.setCode(groupV.getCode().toUpperCase());
        groupDO.setGroupType(GroupType.HYPERMODEL);
        if (this.groupService.exist(groupDO)) {
            return;
        }
        BaseGroupDTO groupDTO = new BaseGroupDTO();
        groupDTO.setId(groupV.getId());
        groupDTO.setCode(groupV.getCode().toUpperCase());
        groupDTO.setGroupType(GroupType.HYPERMODEL);
        groupDTO.setName(groupV.getTitle());
        groupDTO.setParentId("b9add117-a540-4904-bdf7-c0e0d992a68b");
        this.groupService.add((Object)groupDTO);
    }
}

