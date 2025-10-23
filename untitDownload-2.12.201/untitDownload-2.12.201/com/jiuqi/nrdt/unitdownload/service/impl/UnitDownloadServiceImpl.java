/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.internal.service.DataFieldService
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.feign.client.OrgVersionClient
 *  javax.annotation.Resource
 */
package com.jiuqi.nrdt.unitdownload.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.internal.service.DataFieldService;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nrdt.unitdownload.common.FMDMTransferDTO;
import com.jiuqi.nrdt.unitdownload.service.UnitDownloadService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.feign.client.OrgVersionClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitDownloadServiceImpl
implements UnitDownloadService {
    private static final Logger logger = LoggerFactory.getLogger(UnitDownloadServiceImpl.class);
    private static final String ID = "ID";
    private static final String VER = "VER";
    @Resource
    private IRuntimeTaskService runtimeTaskService;
    @Autowired
    IEntityMetaService entityMetaService;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    IEntityDataService entityDataService;
    @Autowired
    IFMDMDataService fmdmDataService;
    @Autowired
    OrgDataClient orgDataClient;
    @Autowired
    DataModelClient dataModelClient;
    @Autowired
    OrgVersionClient orgVersionClient;
    @Autowired
    RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    DefinitionAuthorityProvider authorityProvider;
    @Autowired
    IFMDMAttributeService fmdmAttributeService;
    @Autowired
    DataFieldService dataFieldService;
    @Autowired
    IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    IDataDefinitionRuntimeController definitionRuntimeController;

    @Override
    public Map<String, String> getTaskInfo(String taskCode, String formSchemeKey) {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        TaskDefine task = this.runtimeTaskService.queryTaskDefineByCode(taskCode);
        if (task == null) {
            return resultMap;
        }
        boolean canReadTask = this.authorityProvider.canReadTask(task.getKey());
        if (!canReadTask) {
            return resultMap;
        }
        String title = task.getTitle();
        resultMap.put("taskTitle", title);
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(task.getDw());
        String entityId = entityDefine.getId();
        resultMap.put("entityId", entityId);
        String orgName = entityDefine.getTitle();
        resultMap.put("orgName", orgName);
        Integer versionFlag = entityDefine.getVersion();
        resultMap.put("version", versionFlag.toString());
        List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        resultMap.put("hasFmdm", this.hasFmdm(formDefines));
        return resultMap;
    }

    private String hasFmdm(List<FormDefine> forms) {
        for (FormDefine form : forms) {
            if (form.getFormType().getValue() != FormType.FORM_TYPE_NEWFMDM.getValue() && form.getFormType().getValue() != FormType.FORM_TYPE_FMDM.getValue()) continue;
            return "hasFmdm";
        }
        return "noFMDM";
    }

    @Override
    public List<FMDMTransferDTO> getUnitData(String taskCode, String period, List<String> unitKeys) {
        TaskDefine task = this.runtimeTaskService.queryTaskDefineByCode(taskCode);
        String formSchemeKey = "";
        try {
            SchemePeriodLinkDefine schemePeriodDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, task.getKey());
            formSchemeKey = schemePeriodDefine.getSchemeKey();
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u62a5\u8868\u65b9\u6848key\u5f02\u5e38:" + e.getMessage(), e);
        }
        FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
        fmdmDataDTO.setFormSchemeKey(formSchemeKey);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        if (!unitKeys.isEmpty()) {
            dimensionValueSet.setValue("MD_ORG", unitKeys);
        }
        fmdmDataDTO.setDimensionValueSet(dimensionValueSet);
        fmdmDataDTO.setAuthorityType(AuthorityType.Read);
        List fmdmDataList = this.fmdmDataService.list(fmdmDataDTO);
        FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
        fmdmAttributeDTO.setFormSchemeKey(formSchemeKey);
        List fmdmAttributeList = this.fmdmAttributeService.list(fmdmAttributeDTO);
        List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        FormDefine fmdmForm = this.getFmdmForm(formDefines);
        List allLinksInForm = this.runTimeViewController.getAllLinksInForm(fmdmForm.getKey());
        ArrayList<IFMDMAttribute> filterAttributeList = new ArrayList<IFMDMAttribute>();
        for (IFMDMAttribute attribute : fmdmAttributeList) {
            for (Object dataLinkDefine : allLinksInForm) {
                DataLinkType dataLinkType = dataLinkDefine.getType();
                int type = dataLinkType.getValue();
                if (DataLinkType.DATA_LINK_TYPE_FMDM.getValue() != type) continue;
                String linkExpression = dataLinkDefine.getLinkExpression();
                if (attribute.isNullAble() && (linkExpression == null || !linkExpression.equals(attribute.getCode())) && !"CODE".equals(attribute.getCode()) || filterAttributeList.contains(attribute)) continue;
                filterAttributeList.add(attribute);
            }
        }
        ArrayList<FMDMTransferDTO> fmdmTranList = new ArrayList<FMDMTransferDTO>();
        for (IFMDMData fmdmData : fmdmDataList) {
            HashMap<String, Object> modifyData = new HashMap<String, Object>();
            FMDMTransferDTO fmdmTransferDO = new FMDMTransferDTO();
            for (IFMDMAttribute attribute : filterAttributeList) {
                String code = attribute.getCode();
                if (code.equals(ID) || code.equals(VER)) continue;
                if (attribute.getColumnType() == ColumnModelType.BOOLEAN) {
                    fmdmData.getValue(code);
                    if (AbstractData.isNull((AbstractData)fmdmData.getValue(code))) continue;
                    modifyData.put(code, fmdmData.getValue(code).getAsInt());
                } else {
                    modifyData.put(code, fmdmData.getAsObject(code));
                }
                fmdmTransferDO.setModifyData(modifyData);
            }
            Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)fmdmData.getMasterKey());
            fmdmTransferDO.setDimensionSet(dimensionSet);
            fmdmTransferDO.setFmdmKey(fmdmData.getFMDMKey());
            fmdmTranList.add(fmdmTransferDO);
        }
        return fmdmTranList;
    }

    private FormDefine getFmdmForm(List<FormDefine> forms) {
        for (FormDefine form : forms) {
            if (form.getFormType().getValue() != FormType.FORM_TYPE_NEWFMDM.getValue()) continue;
            return form;
        }
        return null;
    }

    @Override
    public List<String> getOrgNotNullAttribute(String category) {
        DataModelDTO dataModel = new DataModelDTO();
        dataModel.setName(category);
        DataModelDO orgModel = this.dataModelClient.get(dataModel);
        List columns = orgModel.getColumns();
        ArrayList<String> orgNotNullAttribute = new ArrayList<String>();
        columns.stream().filter(p -> p.isNullable() == false).forEach(p -> orgNotNullAttribute.add(p.getColumnName()));
        return orgNotNullAttribute;
    }

    @Override
    public Map<Integer, List<Map<String, Object>>> getOrgData(String taskCode, String period, List<String> unitKeys) {
        TaskDefine task = this.runtimeTaskService.queryTaskDefineByCode(taskCode);
        SchemePeriodLinkDefine schemePeriodLinkDefine = null;
        try {
            schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, task.getKey());
        }
        catch (Exception e) {
            logger.error("\u901a\u8fc7\u65f6\u671f\u548c\u4efb\u52a1\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u5931\u8d25", e);
            throw new RuntimeException(e);
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
        String periodView = formScheme.getDateTime();
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(task.getDw());
        String entityId = entityDefine.getId();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        if (!unitKeys.isEmpty() && !unitKeys.get(0).equals("AllUnit")) {
            dimensionValueSet.setValue("MD_ORG", unitKeys);
        }
        return this.queryOrgData(entityId, dimensionValueSet, periodView);
    }

    @Override
    public List<OrgVersionDO> getOrgVersion(String taskCode) {
        TaskDefine task = this.runtimeTaskService.queryTaskDefineByCode(taskCode);
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(task.getDw());
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityDefine.getId());
        String entityCode = this.entityMetaService.getEntityCode(entityModel.getEntityId());
        OrgVersionDTO orgVersionDTO = new OrgVersionDTO();
        orgVersionDTO.setCategoryname(entityCode);
        PageVO pageVO = this.orgVersionClient.list(orgVersionDTO);
        return pageVO.getRows();
    }

    private IEntityTable getEntityTable(String entityId, DimensionValueSet dimensionValueSet, String periodView) {
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setAuthorityOperations(AuthorityType.Read);
        iEntityQuery.setEntityView(entityViewDefine);
        iEntityQuery.setMasterKeys(dimensionValueSet);
        iEntityQuery.sorted(true);
        ExecutorContext executorContext = new ExecutorContext(this.definitionRuntimeController);
        executorContext.setPeriodView(periodView);
        try {
            return iEntityQuery.executeFullBuild((IContext)executorContext);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u53ea\u8bfb\u5b9e\u4f53\u7ed3\u679c\u96c6\u5f02\u5e38", e);
            return null;
        }
    }

    public Map<Integer, List<Map<String, Object>>> queryOrgData(String entityId, DimensionValueSet dimensionValueSet, String periodView) {
        IEntityTable entityTable = this.getEntityTable(entityId, dimensionValueSet, periodView);
        assert (entityTable != null);
        IEntityModel entityModel = entityTable.getEntityModel();
        Iterator attributes = entityModel.getAttributes();
        ArrayList<String> fieldKey = new ArrayList<String>();
        while (attributes.hasNext()) {
            String code = ((IEntityAttribute)attributes.next()).getCode();
            if (code.equals(ID) || code.equals(VER)) continue;
            fieldKey.add(code);
        }
        List rootRows = entityTable.getRootRows();
        LinkedHashMap<Integer, List<Map<String, Object>>> integerListMap = new LinkedHashMap<Integer, List<Map<String, Object>>>();
        if (rootRows == null || rootRows.size() == 0) {
            return integerListMap;
        }
        this.getRows(rootRows, integerListMap, entityTable, 0, fieldKey);
        return integerListMap;
    }

    private void getRows(List<IEntityRow> rootRowsList, Map<Integer, List<Map<String, Object>>> levelListMap, IEntityTable entityTable, int level, List<String> fieldKey) {
        if (levelListMap.containsKey(level)) {
            List<Map<String, Object>> mapList = levelListMap.get(level);
            mapList.addAll(this.buildTransferObject(rootRowsList, fieldKey));
        } else {
            levelListMap.put(level, this.buildTransferObject(rootRowsList, fieldKey));
        }
        for (IEntityRow entityRow : rootRowsList) {
            List childRows = entityTable.getChildRows(entityRow.getEntityKeyData());
            if (childRows.size() <= 0) continue;
            this.getRows(childRows, levelListMap, entityTable, ++level, fieldKey);
        }
    }

    private List<Map<String, Object>> buildTransferObject(List<IEntityRow> entityRows, List<String> fieldKey) {
        LinkedList<Map<String, Object>> mapList = new LinkedList<Map<String, Object>>();
        for (IEntityRow entityRow : entityRows) {
            HashMap<String, Object> valueMap = new HashMap<String, Object>();
            block9: for (String key : fieldKey) {
                AbstractData value = entityRow.getValue(key);
                if (value.isNull) continue;
                switch (value.dataType) {
                    case 4: {
                        valueMap.put(key, value.getAsInt());
                        continue block9;
                    }
                    case 1: {
                        valueMap.put(key, value.getAsInt());
                        continue block9;
                    }
                    case 2: {
                        valueMap.put(key, value.getAsDateTime());
                        continue block9;
                    }
                    case 3: {
                        valueMap.put(key, value.getAsFloat());
                        continue block9;
                    }
                    case 6: {
                        valueMap.put(key, value.getAsString());
                        continue block9;
                    }
                    case 10: {
                        valueMap.put(key, value.getAsCurrency());
                        continue block9;
                    }
                }
                valueMap.put(key, value);
            }
            mapList.add(valueMap);
        }
        return mapList;
    }
}

