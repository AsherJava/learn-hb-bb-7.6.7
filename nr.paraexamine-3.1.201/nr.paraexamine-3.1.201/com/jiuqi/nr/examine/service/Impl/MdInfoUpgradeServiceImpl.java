/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.deploy.RefreshDefinitionCacheEvent
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nr.datascheme.api.event.RefreshScheme
 *  com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl
 *  com.jiuqi.nr.datascheme.internal.deploy.RuntimeDataSchemeManagerService
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.deploy.DeployDefinitionService
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.service.DesignDataLinkDefineService
 *  com.jiuqi.nr.definition.internal.service.DesignFormulaDefineService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.service.DataModelRegisterService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionStatus
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.transaction.support.DefaultTransactionDefinition
 */
package com.jiuqi.nr.examine.service.Impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.deploy.RefreshDefinitionCacheEvent;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.api.event.RefreshScheme;
import com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl;
import com.jiuqi.nr.datascheme.internal.deploy.RuntimeDataSchemeManagerService;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.deploy.DeployDefinitionService;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.service.DesignDataLinkDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormulaDefineService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.examine.bean.MdInfoUpgradeRecordDO;
import com.jiuqi.nr.examine.common.ExamineErrorEnum;
import com.jiuqi.nr.examine.dao.MdInfoUpgradeRecordDao;
import com.jiuqi.nr.examine.facade.MdInfoDataUpgradeRecordDTO;
import com.jiuqi.nr.examine.facade.MdInfoUpgradeParamsDTO;
import com.jiuqi.nr.examine.facade.MdInfoUpgradeRecordDTO;
import com.jiuqi.nr.examine.service.IMdInfoUpgradeService;
import com.jiuqi.nvwa.definition.service.DataModelRegisterService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MdInfoUpgradeServiceImpl
implements IMdInfoUpgradeService {
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelRegisterService dataModelRegisterService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IDataSchemeDeployService dataSchemeDeployService;
    @Autowired
    private DataFieldDeployInfoDaoImpl dataFieldDeployInfoService;
    @Autowired
    private RuntimeDataSchemeManagerService runtimeDataSchemeManagerService;
    @Autowired
    private DesignFormulaDefineService designFormulaDefineService;
    @Autowired
    private IDesignTimeFormulaController designTimeFormulaController;
    @Autowired
    private DesignDataLinkDefineService designDataLinkDefineService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private DeployDefinitionService deployDefinitionService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private MdInfoUpgradeRecordDao mdInfoUpgradeRecordDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(MdInfoUpgradeServiceImpl.class);

    @Override
    public MdInfoUpgradeRecordDTO queryFailedUpgradeRecord(String dataSchemeKey) {
        MdInfoUpgradeRecordDO record = this.mdInfoUpgradeRecordDao.getFailedMdInfoUpgrade(dataSchemeKey);
        if (null != record) {
            return MdInfoUpgradeRecordDTO.fromDO(record);
        }
        return null;
    }

    @Override
    public void insertUpgradeRecord(MdInfoUpgradeRecordDTO record) {
        MdInfoUpgradeRecordDO obj = record.toDO();
        this.mdInfoUpgradeRecordDao.insertMdInfoUpgrade(obj);
    }

    @Override
    public void updateUpgradeRecord(MdInfoUpgradeRecordDTO record) {
        MdInfoUpgradeRecordDO obj = record.toDO();
        this.mdInfoUpgradeRecordDao.upgradeMdInfoUpgrade(obj);
    }

    @Override
    @Transactional
    public void upgradeParam(MdInfoUpgradeRecordDTO record) throws JQException {
        String dataSchemeKey = record.getDataSchemeKey();
        List<String> dataFieldKeys = record.getUpgradeParams().getDataFieldKeys();
        List<String> formKeys = record.getUpgradeParams().getFormKeys();
        List dataFields = CollectionUtils.isEmpty(dataFieldKeys) ? Collections.emptyList() : this.designDataSchemeService.getDataFields(dataFieldKeys);
        List formDefines = CollectionUtils.isEmpty(formKeys) ? Collections.emptyList() : this.designTimeViewController.listForm(formKeys);
        List<String> allFormSchemeKeys = this.getAllFormSchemeKeys(dataSchemeKey);
        this.checkParams(dataSchemeKey, dataFieldKeys, dataFields, allFormSchemeKeys, formKeys, formDefines);
        String mdInfoDataTableKey = this.designDataSchemeService.insertDataTableForMdInfo(dataSchemeKey);
        this.upgradeFormulas(allFormSchemeKeys, dataFields);
        this.upgradeForm(mdInfoDataTableKey, dataFields, formDefines);
        List<MdInfoDataUpgradeRecordDTO> upgradeResults = this.upgradeDataFields(mdInfoDataTableKey, dataFieldKeys, dataFields);
        this.refreshDataSchemeCache(dataSchemeKey);
        this.insertRecord(record, upgradeResults);
    }

    private List<String> getAllFormSchemeKeys(String dataSchemeKey) {
        ArrayList<String> allFormSchemeKeys = new ArrayList<String>();
        List allTaskDefines = this.designTimeViewController.listTaskByDataScheme(dataSchemeKey);
        for (DesignTaskDefine taskDefine : allTaskDefines) {
            List formSchemeDefines = this.designTimeViewController.listFormSchemeByTask(taskDefine.getKey());
            for (DesignFormSchemeDefine formSchemeDefine : formSchemeDefines) {
                allFormSchemeKeys.add(formSchemeDefine.getKey());
            }
        }
        return allFormSchemeKeys;
    }

    private void upgradeFormulas(List<String> allFormSchemeKeys, List<DesignDataField> dataFields) throws JQException {
        if (CollectionUtils.isEmpty(dataFields)) {
            return;
        }
        LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u5347\u7ea7\u62a5\u8868\u516c\u5f0f\u5f00\u59cb");
        HashMap<String, DesignFormulaSchemeDefine> formulaSchemes = new HashMap<String, DesignFormulaSchemeDefine>();
        for (String formSchemeKey : allFormSchemeKeys) {
            List formulaSchemeDefines = this.designTimeFormulaController.listFormulaSchemeByFormScheme(formSchemeKey);
            for (DesignFormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
                formulaSchemes.put(formulaSchemeDefine.getKey(), formulaSchemeDefine);
            }
        }
        Set dataTableKeys = dataFields.stream().map(DataField::getDataTableKey).collect(Collectors.toSet());
        List dataTables = this.designDataSchemeService.getDataTables(new ArrayList(dataTableKeys));
        HashMap<String, String> dataTableCodes = new HashMap<String, String>();
        for (DesignDataTable dataTable : dataTables) {
            dataTableCodes.put(dataTable.getKey(), dataTable.getCode());
        }
        for (DesignDataField dataField : dataFields) {
            this.upgradeFormulas(formulaSchemes.keySet(), (String)dataTableCodes.get(dataField.getDataTableKey()), dataField.getCode());
        }
        LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u5347\u7ea7\u62a5\u8868\u516c\u5f0f\u7ed3\u675f");
    }

    private void upgradeFormulas(Collection<String> formulaSchemeKeys, String tableCode, String fieldCode) throws JQException {
        List formulaDefines = this.designFormulaDefineService.search(formulaSchemeKeys, fieldCode);
        if (CollectionUtils.isEmpty(formulaDefines)) {
            return;
        }
        String regx = String.format("%s\\s*(\\[\\s*%s(\\s|\\]|[^_0-9a-zA-Z]))", tableCode, fieldCode);
        for (DesignFormulaDefine formulaDefine : formulaDefines) {
            String expression = formulaDefine.getExpression();
            String newExpression = expression.replaceAll(regx, "$1");
            formulaDefine.setExpression(newExpression);
            LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u5347\u7ea7\u62a5\u8868\u516c\u5f0f[{}]\uff0c\u6e90\u516c\u5f0f[{}]\uff0c\u65b0\u516c\u5f0f[{}]", formulaDefine.getCode(), expression, newExpression);
        }
        try {
            this.designFormulaDefineService.updateFormulaDefines(formulaDefines.toArray(new DesignFormulaDefine[0]));
        }
        catch (Exception e) {
            LOGGER.error("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u5347\u7ea7\u62a5\u8868\u516c\u5f0f\u5931\u8d25", e);
            throw new JQException((ErrorEnum)ExamineErrorEnum.MD_INFO_001, "\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u5347\u7ea7\u62a5\u8868\u516c\u5f0f\u5931\u8d25");
        }
        Set keys = formulaDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        this.deployDefinitionService.deleteFormulas(keys, true);
        this.deployDefinitionService.insertFormulas(keys, true);
    }

    private void insertRecord(MdInfoUpgradeRecordDTO record, List<MdInfoDataUpgradeRecordDTO> upgradeResults) {
        LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u4fdd\u5b58\u5347\u7ea7\u8bb0\u5f55");
        record.setDataUpgradeRecords(upgradeResults);
        this.insertUpgradeRecord(record);
        LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u4fdd\u5b58\u5347\u7ea7\u8bb0\u5f55\u6210\u529f");
    }

    private void upgradeForm(String mdInfoDataTableKey, List<DesignDataField> dataFields, List<DesignFormDefine> formDefines) {
        if (CollectionUtils.isEmpty(formDefines)) {
            return;
        }
        LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u5347\u7ea7\u62a5\u8868\u53c2\u6570\u5f00\u59cb");
        HashMap<String, DesignDataField> dataFieldKeyMap = new HashMap<String, DesignDataField>();
        HashMap<String, DesignDataField> dataFieldCodeMap = new HashMap<String, DesignDataField>();
        for (DesignDataField designDataField : dataFields) {
            dataFieldKeyMap.put(designDataField.getKey(), designDataField);
            dataFieldCodeMap.put(this.toFormulaLinkExpression(designDataField), designDataField);
        }
        List mdInfoDataFields = this.designDataSchemeService.getDataFieldByTableKeyAndKind(mdInfoDataTableKey, new DataFieldKind[]{DataFieldKind.FIELD_ZB});
        for (DesignDataField mdInfoDataField : mdInfoDataFields) {
            dataFieldKeyMap.put(mdInfoDataField.getKey(), mdInfoDataField);
            dataFieldCodeMap.put(this.toFormulaLinkExpression(mdInfoDataField), mdInfoDataField);
        }
        HashSet<String> hashSet = new HashSet<String>();
        HashMap<String, DesignDataLinkDefine> updateDataLinkDefines = new HashMap<String, DesignDataLinkDefine>();
        for (DesignFormDefine designFormDefine : formDefines) {
            LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u5347\u7ea7\u62a5\u8868{}[{}]", (Object)designFormDefine.getFormCode(), (Object)designFormDefine.getTitle());
            if (FormType.FORM_TYPE_NEWFMDM != designFormDefine.getFormType()) {
                designFormDefine.setFormType(FormType.FORM_TYPE_NEWFMDM);
                this.designTimeViewController.updateForm(designFormDefine);
                hashSet.add(designFormDefine.getKey());
            }
            List dataLinkDefines = this.designTimeViewController.listDataLinkByForm(designFormDefine.getKey());
            for (DesignDataLinkDefine dataLinkDefine : dataLinkDefines) {
                if (DataLinkType.DATA_LINK_TYPE_FIELD == dataLinkDefine.getType() && dataFieldKeyMap.containsKey(dataLinkDefine.getLinkExpression())) {
                    dataLinkDefine.setType(DataLinkType.DATA_LINK_TYPE_INFO);
                    updateDataLinkDefines.put(dataLinkDefine.getKey(), dataLinkDefine);
                    continue;
                }
                if (DataLinkType.DATA_LINK_TYPE_FORMULA != dataLinkDefine.getType() || !dataFieldCodeMap.containsKey(dataLinkDefine.getLinkExpression())) continue;
                dataLinkDefine.setType(DataLinkType.DATA_LINK_TYPE_INFO);
                dataLinkDefine.setLinkExpression(((DesignDataField)dataFieldCodeMap.get(dataLinkDefine.getLinkExpression())).getKey());
                updateDataLinkDefines.put(dataLinkDefine.getKey(), dataLinkDefine);
            }
        }
        List dataLinkDefines = this.designDataLinkDefineService.getDefinesByFieldKeys(new ArrayList(dataFieldKeyMap.keySet()));
        for (DesignDataLinkDefine dataLinkDefine : dataLinkDefines) {
            if (updateDataLinkDefines.containsKey(dataLinkDefine.getKey()) || DataLinkType.DATA_LINK_TYPE_FIELD != dataLinkDefine.getType() || !dataFieldKeyMap.containsKey(dataLinkDefine.getLinkExpression())) continue;
            dataLinkDefine.setType(DataLinkType.DATA_LINK_TYPE_FORMULA);
            dataLinkDefine.setLinkExpression(this.toFormulaLinkExpression((DesignDataField)dataFieldKeyMap.get(dataLinkDefine.getLinkExpression())));
            updateDataLinkDefines.put(dataLinkDefine.getKey(), dataLinkDefine);
        }
        if (!updateDataLinkDefines.isEmpty()) {
            this.designTimeViewController.updateDataLink(updateDataLinkDefines.values().toArray(new DesignDataLinkDefine[0]));
        }
        if (!hashSet.isEmpty()) {
            this.deployDefinitionService.deleteFormDefines(hashSet, true);
            this.deployDefinitionService.insertFormDefines(hashSet, true);
        }
        if (!updateDataLinkDefines.isEmpty()) {
            Set set = updateDataLinkDefines.keySet();
            this.deployDefinitionService.deleteDataLinks(set, true);
            this.deployDefinitionService.insertDataLinks(set, true);
        }
        LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u5347\u7ea7\u62a5\u8868\u53c2\u6570\u6210\u529f");
    }

    private String toFormulaLinkExpression(DesignDataField dataField) {
        if (null == dataField) {
            return null;
        }
        return "[" + dataField.getCode() + "]";
    }

    private List<MdInfoDataUpgradeRecordDTO> upgradeDataFields(String mdInfoDataTableKey, List<String> dataFieldKeys, List<DesignDataField> dataFields) {
        if (CollectionUtils.isEmpty(dataFieldKeys)) {
            return Collections.emptyList();
        }
        LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u5347\u7ea7\u6307\u6807\u53c2\u6570\u5f00\u59cb");
        HashSet<String> oldDataTableKeys = new HashSet<String>();
        HashSet<String> mdInfoDataFieldKeys = new HashSet<String>();
        for (DesignDataField dataField : dataFields) {
            if (mdInfoDataTableKey.equals(dataField.getDataTableKey())) continue;
            mdInfoDataFieldKeys.add(dataField.getKey());
            oldDataTableKeys.add(dataField.getDataTableKey());
            dataField.setDataTableKey(mdInfoDataTableKey);
            LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u5c06\u6307\u6807\u79fb\u52a8{}[{}]\u5230\u5355\u4f4d\u4fe1\u606f\u8868\u4e2d", (Object)dataField.getCode(), (Object)dataField.getTitle());
        }
        List oldDeployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(mdInfoDataFieldKeys.toArray(new String[0]));
        DataFieldDeployInfoDO[] infos = new DataFieldDeployInfoDO[oldDeployInfos.size()];
        String[] oldColumnKeys = new String[oldDeployInfos.size()];
        HashMap oldDataTable2Table = new HashMap();
        HashMap<String, MdInfoDataUpgradeRecordDTO> upgradeTableInfo = new HashMap<String, MdInfoDataUpgradeRecordDTO>();
        for (int i = 0; i < oldDeployInfos.size(); ++i) {
            DataFieldDeployInfo info = (DataFieldDeployInfo)oldDeployInfos.get(i);
            infos[i] = DataFieldDeployInfoDO.valueOf((DataFieldDeployInfo)info);
            oldColumnKeys[i] = info.getColumnModelKey();
            oldDataTable2Table.computeIfAbsent(info.getDataTableKey(), key -> new HashSet()).add(info.getTableModelKey());
            upgradeTableInfo.computeIfAbsent(info.getTableModelKey(), key -> new MdInfoDataUpgradeRecordDTO(info.getTableModelKey(), info.getTableName(), null)).getSourceFiledNameMap().put(info.getDataFieldKey(), info.getFieldName());
        }
        this.designDataSchemeService.updateDataFields(dataFields);
        for (String sourceDataTableKey : oldDataTableKeys) {
            long count = this.designDataSchemeService.getDataFieldByTable(sourceDataTableKey).stream().filter(f -> DataFieldKind.FIELD_ZB == f.getDataFieldKind()).count();
            if (0L != count) continue;
            DesignDataTable sourceDataTable = this.designDataSchemeService.getDataTable(sourceDataTableKey);
            LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u6570\u636e\u8868{}[{}]\u4e2d\u6307\u6807\u4e3a\u7a7a\uff0c\u5220\u9664\u6570\u636e\u8868", (Object)sourceDataTable.getCode(), (Object)sourceDataTable.getTitle());
            this.designDataSchemeService.deleteDataTable(sourceDataTableKey);
            this.runtimeDataSchemeManagerService.deleteRuntimeDataTable(sourceDataTableKey);
            this.runtimeDataSchemeManagerService.deleteRuntimeDataFieldByTable(sourceDataTableKey);
            this.runtimeDataSchemeManagerService.deleteDeployInfoByTable(sourceDataTableKey);
            for (String tableKey : oldDataTable2Table.getOrDefault(sourceDataTableKey, Collections.emptySet())) {
                this.designDataModelService.deleteTableModelDefine(tableKey);
            }
        }
        this.runtimeDataSchemeManagerService.deleteRuntimeDataFields(new HashSet<String>(dataFieldKeys));
        this.dataFieldDeployInfoService.delete(infos);
        this.designDataModelService.deleteColumnModelDefines(oldColumnKeys);
        LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u5347\u7ea7\u6307\u6807\u53c2\u6570\u6210\u529f");
        return new ArrayList<MdInfoDataUpgradeRecordDTO>(upgradeTableInfo.values());
    }

    private void refreshDataSchemeCache(String dataSchemeKey) {
        RefreshCache refreshCache = new RefreshCache();
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        if (null == dataScheme) {
            dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        }
        if (null != dataScheme) {
            HashMap refreshTable = new HashMap();
            RefreshScheme refreshScheme = new RefreshScheme(dataScheme.getKey(), dataScheme.getCode());
            refreshTable.put(refreshScheme, Collections.emptySet());
            refreshCache.setRefreshTable(refreshTable);
        } else {
            refreshCache.setRefreshAll(true);
        }
        this.applicationContext.publishEvent((ApplicationEvent)new RefreshSchemeCacheEvent(refreshCache));
        this.applicationContext.publishEvent((ApplicationEvent)new RefreshDefinitionCacheEvent());
    }

    private void checkParams(String dataSchemeKey, List<String> dataFieldKeys, List<DesignDataField> dataFields, List<String> formSchemeKeys, List<String> formKeys, List<DesignFormDefine> formDefines) throws JQException {
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        if (null == dataScheme) {
            throw new JQException((ErrorEnum)ExamineErrorEnum.MD_INFO_001, "\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u6570\u636e\u65b9\u6848\u672a\u53d1\u5e03\uff0c\u65e0\u9700\u5347\u7ea7");
        }
        long count = dataFields.stream().filter(f -> !dataSchemeKey.equals(f.getDataSchemeKey()) || DataFieldKind.FIELD_ZB != f.getDataFieldKind()).count();
        if (0L != count || dataFieldKeys.size() != dataFields.size()) {
            throw new JQException((ErrorEnum)ExamineErrorEnum.MD_INFO_001, "\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u6307\u6807\u5fc5\u987b\u5c5e\u4e8e\u540c\u4e00\u4e2a\u6570\u636e\u65b9\u6848");
        }
        count = formDefines.stream().filter(f -> !formSchemeKeys.contains(f.getFormScheme())).count();
        if (0L != count || formKeys.size() != formDefines.size()) {
            throw new JQException((ErrorEnum)ExamineErrorEnum.MD_INFO_001, "\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u62a5\u8868\u6240\u5c5e\u4efb\u52a1\u5fc5\u987b\u5c5e\u4e8e\u540c\u4e00\u4e2a\u6570\u636e\u65b9\u6848");
        }
    }

    @Override
    public void upgradeMdInfoTable(MdInfoUpgradeRecordDTO record) {
        DesignDataTable mdInfoDataTable = this.designDataSchemeService.getAllDataTableBySchemeAndTypes(record.getDataSchemeKey(), new DataTableType[]{DataTableType.MD_INFO}).stream().findAny().orElse(null);
        if (null == mdInfoDataTable || CollectionUtils.isEmpty(record.getDataUpgradeRecords())) {
            LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u65e0\u987b\u53d1\u5e03\u5355\u4f4d\u4fe1\u606f\u8868");
            this.updateUpgradeRecordSuccess(record, false);
            return;
        }
        try {
            LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u53d1\u5e03\u5355\u4f4d\u4fe1\u606f\u8868{}[{}]", (Object)mdInfoDataTable.getCode(), (Object)mdInfoDataTable.getTitle());
            this.dataSchemeDeployService.deployDataTable(mdInfoDataTable.getKey(), false);
            Map<String, DataFieldDeployInfoDO> mdDeployInfo = this.dataFieldDeployInfoService.getByDataTableKey(mdInfoDataTable.getKey()).stream().collect(Collectors.toMap(DataFieldDeployInfo::getDataFieldKey, v -> v));
            for (MdInfoDataUpgradeRecordDTO dataUpgradeRecord : record.getDataUpgradeRecords()) {
                for (Map.Entry<String, String> entry : dataUpgradeRecord.getSourceFiledNameMap().entrySet()) {
                    DataFieldDeployInfoDO info = mdDeployInfo.get(entry.getKey());
                    dataUpgradeRecord.setMdInfoTableName(info.getTableName());
                    dataUpgradeRecord.getMdInfoFieldNameMap().put(info.getDataFieldKey(), info.getFieldName());
                }
            }
            this.updateUpgradeRecordSuccess(record, false);
        }
        catch (JQException e) {
            this.updateUpgradeRecord(record, false, e.getErrorMessage(), false);
            LOGGER.error("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u53d1\u5e03\u5355\u4f4d\u4fe1\u606f\u8868{}[{}]\u5931\u8d25", new Object[]{mdInfoDataTable.getCode(), mdInfoDataTable.getTitle(), e});
        }
        LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u53d1\u5e03\u5355\u4f4d\u4fe1\u606f\u8868{}[{}]\u6210\u529f", (Object)mdInfoDataTable.getCode(), (Object)mdInfoDataTable.getTitle());
    }

    private void updateUpgradeRecord(MdInfoUpgradeRecordDTO record, boolean succeed, String errorMessage, boolean log) {
        record.setUpgradeSucceed(succeed);
        record.setUpgradeMessage("\u53d1\u5e03\u5355\u4f4d\u4fe1\u606f\u8868\u5931\u8d25\uff1a" + errorMessage);
        MdInfoUpgradeRecordDO obj = record.toDO();
        this.mdInfoUpgradeRecordDao.upgradeMdInfoUpgrade(obj);
        if (log) {
            DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(record.getDataSchemeKey());
            String message = "\u6570\u636e\u65b9\u6848\uff1a" + dataScheme.getTitle() + "[" + dataScheme.getCode() + "]\uff1b\r\n\u5347\u7ea7\u72b6\u6001\uff1a" + (record.isUpgradeSucceed() ? "\u5347\u7ea7\u6210\u529f" : "\u5347\u7ea7\u5931\u8d25") + "\uff1b\r\n\u5347\u7ea7\u53c2\u6570\uff1a" + this.toString(record.getUpgradeParams()) + "\r\n\u8be6\u7ec6\u4fe1\u606f\uff1a" + this.toString(record.getDataUpgradeRecords());
            LogHelper.info((String)"\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7", (String)(record.isUpgradeSucceed() ? "\u5347\u7ea7\u6210\u529f" : "\u5347\u7ea7\u5931\u8d25"), (String)message);
        }
    }

    private String toString(MdInfoUpgradeParamsDTO dto) {
        StringBuffer sbr = new StringBuffer();
        if (!CollectionUtils.isEmpty(dto.getFormKeys())) {
            List formDefines = this.designTimeViewController.listForm(dto.getFormKeys());
            sbr.append("\r\n\t\u5347\u7ea7\u62a5\u8868\uff1a");
            sbr.append(formDefines.stream().map(f -> f.getTitle() + "[" + f.getFormCode() + "]").collect(Collectors.joining("\uff0c")));
            sbr.append("\uff1b");
        }
        if (!CollectionUtils.isEmpty(dto.getDataFieldKeys())) {
            List dataFields = this.designDataSchemeService.getDataFields(dto.getDataFieldKeys());
            sbr.append("\r\n\t\u5347\u7ea7\u6307\u6807\uff1a");
            sbr.append(dataFields.stream().map(f -> f.getTitle() + "[" + f.getCode() + "]").collect(Collectors.joining("\uff0c")));
            sbr.append("\uff1b");
        }
        if (!CollectionUtils.isEmpty(dto.getDataDimValues())) {
            sbr.append("\r\n\t\u5347\u7ea7\u7ef4\u5ea6\uff1a");
            sbr.append(dto.getDataDimValues().entrySet().stream().map(d -> (String)d.getKey() + "=" + (String)d.getValue()).collect(Collectors.joining("\uff0c")));
            sbr.append("\uff1b");
        }
        return sbr.toString();
    }

    private String toString(List<MdInfoDataUpgradeRecordDTO> dataUpgradeRecords) {
        if (CollectionUtils.isEmpty(dataUpgradeRecords)) {
            return "\u65e0";
        }
        StringBuffer sbr = new StringBuffer();
        for (MdInfoDataUpgradeRecordDTO dataUpgradeRecord : dataUpgradeRecords) {
            sbr.append("\r\n\t\u6765\u6e90\u7269\u7406\u8868\u540d\uff1a[").append(dataUpgradeRecord.getSourceTableName()).append("]").append("\uff0c\u6765\u6e90\u5b57\u6bb5\u540d\uff1a").append(dataUpgradeRecord.getSourceFiledNameMap().values());
            if (dataUpgradeRecord.isUpgradeSucceed()) {
                sbr.append("\uff0c\u5347\u7ea7\u72b6\u6001\uff1a\u5347\u7ea7\u6210\u529f\uff1b");
                continue;
            }
            sbr.append("\uff0c\u5347\u7ea7\u72b6\u6001\uff1a\u5347\u7ea7\u5931\u8d25\uff0c").append(dataUpgradeRecord.getUpgradeMessage());
        }
        return sbr.toString();
    }

    private void updateUpgradeRecordSuccess(MdInfoUpgradeRecordDTO record, boolean log) {
        this.updateUpgradeRecord(record, true, "\u5347\u7ea7\u6210\u529f", log);
    }

    @Override
    public void upgradeData(MdInfoUpgradeRecordDTO record) {
        List<MdInfoDataUpgradeRecordDTO> dataUpgradeRecords = record.getDataUpgradeRecords();
        if (CollectionUtils.isEmpty(dataUpgradeRecords)) {
            this.updateUpgradeRecordSuccess(record, true);
            LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u65e0\u987b\u8fc1\u79fb\u5b58\u50a8\u8868\u6570\u636e");
            return;
        }
        boolean sucessed = true;
        String subTableSqlFormat = this.buildSubSqlFormat(record);
        for (MdInfoDataUpgradeRecordDTO dataUpgradeRecord : dataUpgradeRecords) {
            if (null == dataUpgradeRecord || dataUpgradeRecord.isUpgradeSucceed()) continue;
            this.upgradeData(dataUpgradeRecord, subTableSqlFormat);
            sucessed = sucessed && dataUpgradeRecord.isUpgradeSucceed();
        }
        this.updateUpgradeRecord(record, sucessed, sucessed ? "\u5347\u7ea7\u6210\u529f" : "\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7: \u6570\u636e\u8fc1\u79fb\u5931\u8d25", true);
    }

    private String buildSubSqlFormat(MdInfoUpgradeRecordDTO record) {
        String subSqlFormat = "SELECT %s FROM %s";
        Map<String, String> dataDimValues = record.getUpgradeParams().getDataDimValues();
        List dimensions = this.designDataSchemeService.getDataSchemeDimension(record.getDataSchemeKey(), DimensionType.DIMENSION);
        if (!CollectionUtils.isEmpty(dimensions)) {
            subSqlFormat = subSqlFormat + " WHERE " + dimensions.stream().map(d -> {
                if ("ADJUST".equals(d.getDimKey())) {
                    return "ADJUST='0'";
                }
                IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(d.getDimKey());
                return iEntityDefine.getCode() + (null == dataDimValues || !dataDimValues.containsKey(d.getDimKey()) ? " IS NULL" : "='" + (String)dataDimValues.get(d.getDimKey())) + "'";
            }).collect(Collectors.joining(" AND "));
        }
        return subSqlFormat;
    }

    private void upgradeData(MdInfoDataUpgradeRecordDTO dataUpgradeRecord, String subTableSqlFormat) {
        block4: {
            if (CollectionUtils.isEmpty(dataUpgradeRecord.getSourceFiledNameMap())) {
                dataUpgradeRecord.setUpgradeSucceed(true);
                dataUpgradeRecord.setUpgradeMessage("\u5347\u7ea7\u6210\u529f");
                return;
            }
            TransactionStatus transactionStatus = null;
            String sourceTableName = dataUpgradeRecord.getSourceTableName();
            String mdInfoTableName = dataUpgradeRecord.getMdInfoTableName();
            Map<String, String> sourceFiledNameMap = dataUpgradeRecord.getSourceFiledNameMap();
            Map<String, String> mdInfoFieldNameMap = dataUpgradeRecord.getMdInfoFieldNameMap();
            ArrayList<String> subTableFields = new ArrayList<String>();
            ArrayList<String> fields = new ArrayList<String>();
            for (Map.Entry<String, String> entry : sourceFiledNameMap.entrySet()) {
                String sourceFieldName = entry.getValue();
                String mdInfoFieldName = mdInfoFieldNameMap.get(entry.getKey());
                subTableFields.add(String.format(" %s AS %s ", sourceFieldName, mdInfoFieldName));
                fields.add(mdInfoFieldName);
            }
            try {
                transactionStatus = this.getTransactionStatus();
                LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u8fc1\u79fb\u5b58\u50a8\u8868{}\u7684\u6570\u636e\u5f00\u59cb", (Object)sourceTableName);
                String subTableSql = String.format(subTableSqlFormat, "MDCODE, DATATIME," + StringUtils.collectionToDelimitedString(subTableFields, ","), sourceTableName);
                String queryNewDataSql = String.format("SELECT ST.MDCODE,ST.DATATIME,%s FROM (%s) ST LEFT JOIN %s NT ON ST.MDCODE=NT.MDCODE AND ST.DATATIME=NT.DATATIME WHERE NT.MDCODE IS NULL ", fields.stream().map(s -> "ST." + s).collect(Collectors.joining(",")), subTableSql, mdInfoTableName);
                String insertSql = String.format("INSERT INTO %s (MDCODE,DATATIME,%s) %s", mdInfoTableName, StringUtils.collectionToDelimitedString(fields, ","), queryNewDataSql);
                LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u65b0\u589e\u6570\u636e{}", (Object)insertSql);
                this.jdbcTemplate.execute(insertSql);
                String updateFieldFormat = "NT.%s = (SELECT ST.%s FROM ( " + subTableSql + " ) ST WHERE ST.MDCODE=NT.MDCODE AND ST.DATATIME=NT.DATATIME)";
                String updateFieldSql = mdInfoFieldNameMap.values().stream().map(s -> String.format(updateFieldFormat, s, s)).collect(Collectors.joining(","));
                String updateWhereSql = "EXISTS (SELECT 1 FROM ( " + subTableSql + " ) ST WHERE ST.MDCODE=NT.MDCODE AND ST.DATATIME=NT.DATATIME)";
                String updateFormat = "UPDATE %s NT SET %s WHERE %s";
                String updateSql = String.format(updateFormat, mdInfoTableName, updateFieldSql, updateWhereSql);
                LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u66f4\u65b0\u6570\u636e{}", (Object)updateSql);
                this.jdbcTemplate.execute(updateSql);
                LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u5b58\u50a8\u8868{}\u53d1\u5e03\u5f00\u59cb", (Object)dataUpgradeRecord.getSourceTableName());
                this.dataModelRegisterService.registerTable(dataUpgradeRecord.getSourceTableKey());
                LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u5b58\u50a8\u8868{}\u53d1\u5e03\u6210\u529f", (Object)dataUpgradeRecord.getSourceTableName());
                dataUpgradeRecord.setUpgradeSucceed(true);
                dataUpgradeRecord.setUpgradeMessage("\u5347\u7ea7\u6210\u529f");
                LOGGER.info("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u8fc1\u79fb\u5b58\u50a8\u8868{}\u7684\u6570\u636e\u7ed3\u675f", (Object)sourceTableName);
                this.platformTransactionManager.commit(transactionStatus);
            }
            catch (Exception e) {
                dataUpgradeRecord.setUpgradeSucceed(false);
                dataUpgradeRecord.setUpgradeMessage("\u8fc1\u79fb\u6570\u636e\u5931\u8d25\uff1a" + e.getMessage());
                LOGGER.error("\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff1a\u8fc1\u79fb\u5b58\u50a8\u8868{}\u7684\u6570\u636e\u5931\u8d25", (Object)sourceTableName, (Object)e);
                if (null == transactionStatus) break block4;
                this.platformTransactionManager.rollback(transactionStatus);
            }
        }
    }

    private TransactionStatus getTransactionStatus() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(3);
        return this.platformTransactionManager.getTransaction((TransactionDefinition)def);
    }
}

