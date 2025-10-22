/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IEntityQuery
 *  com.jiuqi.np.dataengine.intf.IEntityRow
 *  com.jiuqi.np.dataengine.setting.AuthorityType
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.definition.facade.analysis.DimensionInfo
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.designer.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IEntityQuery;
import com.jiuqi.np.dataengine.intf.IEntityRow;
import com.jiuqi.np.dataengine.setting.AuthorityType;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.definition.facade.analysis.DimensionInfo;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.common.IDesignerEntityUpgrader;
import com.jiuqi.nr.designer.web.facade.EntityObj;
import com.jiuqi.nr.designer.web.facade.SaveEntityVO;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DesignerEntityUpgrader
implements IDesignerEntityUpgrader {
    private static final Logger log = LoggerFactory.getLogger(DesignerEntityUpgrader.class);
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private IDataDefinitionRuntimeController runtimeCtrl;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;

    @Override
    public List<IEntityRow> queryEnumData(String tableKey) throws JQException, Exception {
        TableDefine tableDefine = this.runtimeCtrl.queryTableDefine(tableKey);
        if (tableDefine != null && tableDefine.getKind() == TableKind.TABLE_KIND_DICTIONARY) {
            ExecutorContext context = new ExecutorContext(this.runtimeCtrl);
            IEntityQuery newEntityQuery = this.dataAccessProvider.newEntityQuery();
            newEntityQuery.setMasterKeys(new DimensionValueSet());
            newEntityQuery.setIgnoreViewFilter(true);
            newEntityQuery.setAuthorityOperations(AuthorityType.NONE);
            List fieldsInTable = this.nrDesignTimeController.getAllFieldsInTable(tableKey);
            fieldsInTable.forEach(e -> newEntityQuery.addColumn((FieldDefine)e));
            return newEntityQuery.executeQuery(context).getAllRows();
        }
        return null;
    }

    @Override
    public IEntityTable getTableData(String tableKey) {
        IEntityTable iEntityTable = null;
        try {
            TableDefine tableDefine = this.runtimeCtrl.queryTableDefine(tableKey);
            if (tableDefine != null) {
                com.jiuqi.nr.entity.engine.intf.IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
                iEntityQuery.executeReader((IContext)new ExecutorContext(this.runtimeCtrl));
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return iEntityTable;
    }

    @Override
    public IEntityTable getTableDataDefaultAllFields(String tableKey) {
        return this.getTableData(tableKey);
    }

    @Override
    public String getCaptionFieldsKeys(DesignTableDefine designTableDefine) throws JQException {
        IEntityModel entityModel = this.entityMetaService.getEntityModel(designTableDefine.getKey());
        IEntityAttribute nameField = entityModel.getNameField();
        return nameField.getID();
    }

    @Override
    public List<String> getEntityList(String[] tableKeys) {
        ArrayList<String> entityList = new ArrayList<String>();
        for (String tableKey : tableKeys) {
            if (this.periodEngineService.getPeriodAdapter().isPeriodEntity(tableKey)) continue;
            entityList.add(tableKey);
        }
        return entityList;
    }

    @Override
    public List<IEntityDefine> getAllEntityList() throws JQException {
        return this.entityMetaService.getEntities(1);
    }

    @Override
    public List<IPeriodEntity> getAllPeriodList() throws JQException {
        return this.periodEntityAdapter.getPeriodEntity();
    }

    @Override
    public void queryData_Business_Period_Copy_Properties(Map<String, String> itemsInEntity, Map<String, String> itemsInCustomPeriod, DesignTableDefine tableDefine) throws Exception {
        block7: {
            if ("d5d69e13-7cf1-4a62-ac64-4e37c513e78c".equals(tableDefine.getKey())) break block7;
            List allFieldsInTable = this.runtimeCtrl.getAllFieldsInTable(tableDefine.getKey());
            IEntityTable iEntityTable = this.getTableData(tableDefine.getKey());
            if (iEntityTable != null) {
                List allRows = iEntityTable.getAllRows();
                IEntityModel entityModel = iEntityTable.getEntityModel();
                if (allRows != null) {
                    if (!this.judgementPeriod(tableDefine.getKey())) {
                        for (com.jiuqi.nr.entity.engine.intf.IEntityRow iEntityRow : allRows) {
                            String rowId = iEntityRow.getAsString(entityModel.getNameField().getCode());
                            String rowTitle = iEntityRow.getAsString(entityModel.getBizKeyField().getCode());
                            if (!StringUtils.isNotEmpty((CharSequence)rowId) || !StringUtils.isNotEmpty((CharSequence)rowTitle)) continue;
                            itemsInEntity.put(rowId, rowTitle);
                        }
                    } else if (tableDefine.getKind() == TableKind.TABLE_KIND_ENTITY_PERIOD) {
                        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(tableDefine.getKey());
                        List periodItems = periodProvider.getPeriodItems();
                        for (IPeriodRow periodRow : periodItems) {
                            String rowCode = periodRow.getCode();
                            String rowTitle = periodRow.getTitle();
                            if (!StringUtils.isNotEmpty((CharSequence)rowCode) || !StringUtils.isNotEmpty((CharSequence)rowTitle)) continue;
                            itemsInCustomPeriod.put(rowCode, rowTitle);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean judgementEntity(TableKind kind) {
        return kind == TableKind.TABLE_KIND_ENTITY;
    }

    @Override
    public boolean judgementPeriod(String entityId) {
        return this.periodEntityAdapter.isPeriodEntity(entityId);
    }

    @Override
    public boolean judgementDictionary(TableKind kind) {
        return kind == TableKind.TABLE_KIND_DICTIONARY;
    }

    @Override
    public List<IPeriodRow> queryNoTimePeriodData(String entityId) throws JQException {
        return this.periodEntityAdapter.getPeriodProvider(entityId).getPeriodItems();
    }

    @Override
    public String getFieldByTableID(String tableid) {
        ObjectMapper mapper = new ObjectMapper();
        if (StringUtils.isEmpty((CharSequence)tableid)) {
            return "";
        }
        HashMap fieldMap = new HashMap();
        String returnStr = "";
        Boolean isSuccess = false;
        String dictionaryTitleId = "";
        try {
            IEntityModel entityModel = this.entityMetaService.getEntityModel(tableid);
            dictionaryTitleId = entityModel.getNameField().getID();
            isSuccess = true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (fieldMap != null && !fieldMap.isEmpty()) {
            try {
                HashMap<String, Object> returnJson = new HashMap<String, Object>();
                returnJson.put("success", isSuccess);
                returnJson.put("fieldMap", fieldMap);
                returnJson.put("dictionaryTitleId", dictionaryTitleId);
                returnStr = mapper.writeValueAsString(returnJson);
            }
            catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
            }
        }
        return returnStr;
    }

    @Override
    public void saveEnum(SaveEntityVO entityVO) throws Exception {
    }

    @Override
    public EntityObj initEnum() throws JQException {
        return null;
    }

    @Override
    public EntityObj getEnum(String viewKey) throws Exception {
        return null;
    }

    @Override
    public void deleteEnum(String viewKey) throws Exception {
    }

    @Override
    public DesignTableDefine getDesignEnumDefineByField(DesignFieldDefine field) throws JQException {
        return null;
    }

    @Override
    public TableDefine getRunTimeEnumDefineByField(FieldDefine fieldDefine) throws Exception {
        return null;
    }

    @Override
    public DimensionInfo createDimension_Copy_Properties(TableDefine table, String viewKey) {
        return null;
    }
}

