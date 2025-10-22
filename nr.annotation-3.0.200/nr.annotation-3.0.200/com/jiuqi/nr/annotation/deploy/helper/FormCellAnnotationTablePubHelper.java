/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.annotation.deploy.helper;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.CatalogModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.List;

public class FormCellAnnotationTablePubHelper {
    private final DesignDataModelService designDataModelService;
    private final NRDesignTimeController nrDesignTimeController;
    private final CatalogModelService catalogModelService;
    private final IEntityMetaService iEntityMetaService;
    private final PeriodEngineService periodEngineService;
    private static final String DW_FIELD = "DW";
    private static final String PERIOD_FIELD = "PERIOD";

    public FormCellAnnotationTablePubHelper(DesignDataModelService designDataModelService, NRDesignTimeController nrDesignTimeController, CatalogModelService catalogModelService, IEntityMetaService iEntityMetaService, PeriodEngineService periodEngineService) {
        this.designDataModelService = designDataModelService;
        this.nrDesignTimeController = nrDesignTimeController;
        this.catalogModelService = catalogModelService;
        this.iEntityMetaService = iEntityMetaService;
        this.periodEngineService = periodEngineService;
    }

    public String modelingAnnotition(DesignTaskDefine taskDefine, FormSchemeDefine scheme) throws Exception {
        String annotitionTableName = "SYS_FMCEAN_" + scheme.getFormSchemeCode();
        DesignTableModelDefine annotitionTable = this.designDataModelService.getTableModelDefineByCode(annotitionTableName);
        boolean doInsertAnnotitionTable = true;
        if (annotitionTable == null) {
            annotitionTable = this.designDataModelService.createTableModelDefine();
        } else {
            doInsertAnnotitionTable = false;
        }
        annotitionTable.setCode(annotitionTableName);
        annotitionTable.setDesc("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u62a5\u8868\u5355\u5143\u683c\u6279\u6ce8\u8868");
        annotitionTable.setTitle("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u62a5\u8868\u5355\u5143\u683c\u6279\u6ce8\u8868");
        annotitionTable.setName(annotitionTableName);
        annotitionTable.setType(TableModelType.DEFAULT);
        annotitionTable.setKind(TableModelKind.DEFAULT);
        annotitionTable.setOwner("NR");
        String annotitionTableKey = annotitionTable.getID();
        StringBuffer tableEnityMasterKeys = new StringBuffer();
        ArrayList<DesignColumnModelDefine> createAnnotitionFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyAnnotitionFieldList = new ArrayList<DesignColumnModelDefine>();
        String entitiesKey = this.nrDesignTimeController.getFormSchemeEntity(scheme.getKey());
        if (StringUtils.isEmpty((String)entitiesKey)) {
            entitiesKey = taskDefine.getMasterEntitiesKey();
        }
        if (StringUtils.isEmpty((String)entitiesKey)) {
            throw new Exception("\u62a5\u8868\u65b9\u6848\u3001\u4efb\u52a1\u6ca1\u6709\u8bbe\u7f6e\u4e3b\u952e\u65e0\u6cd5\u521b\u5efa\u6279\u6ce8\u8868");
        }
        String[] entitiesKeyArr = entitiesKey.split(";");
        boolean flag = true;
        String referFieldId = null;
        for (String entityKey : entitiesKeyArr) {
            if (this.isPeriodView(entityKey)) {
                referFieldId = this.queryPeriodBizKey(entityKey);
                tableEnityMasterKeys.append(this.initField(annotitionTableKey, PERIOD_FIELD, referFieldId, createAnnotitionFieldList, modifyAnnotitionFieldList, ColumnModelType.STRING, 9)).append(";");
                continue;
            }
            if (flag) {
                referFieldId = this.queryEntityTableBizKeyByView(entityKey);
                tableEnityMasterKeys.append(this.initField(annotitionTableKey, DW_FIELD, referFieldId, createAnnotitionFieldList, modifyAnnotitionFieldList, ColumnModelType.STRING, 40)).append(";");
                flag = false;
                continue;
            }
            IEntityDefine entity = this.queryDimisionByView(entityKey);
            if (null == entity) continue;
            referFieldId = this.queryEntityBizeKeyByEntityId(entity.getId());
            tableEnityMasterKeys.append(this.initField(annotitionTableKey, entity.getDimensionName(), referFieldId, createAnnotitionFieldList, modifyAnnotitionFieldList, ColumnModelType.STRING, 50)).append(";");
        }
        tableEnityMasterKeys.append(this.initField(annotitionTableKey, "FMCEAN_ID", null, createAnnotitionFieldList, modifyAnnotitionFieldList, ColumnModelType.STRING, 40)).append(";");
        annotitionTable.setBizKeys(tableEnityMasterKeys.toString());
        annotitionTable.setKeys(tableEnityMasterKeys.toString());
        this.initField(annotitionTableKey, "FMCEAN_CONTENT", null, createAnnotitionFieldList, modifyAnnotitionFieldList, ColumnModelType.STRING, 500);
        this.initField(annotitionTableKey, "FMCEAN_USER_ID", null, createAnnotitionFieldList, modifyAnnotitionFieldList, ColumnModelType.STRING, 50);
        this.initField(annotitionTableKey, "FMCEAN_UPDATE_DATE", null, createAnnotitionFieldList, modifyAnnotitionFieldList, ColumnModelType.DATETIME, null);
        if (doInsertAnnotitionTable) {
            DesignCatalogModelDefine sysTableGroupByParent = this.catalogModelService.createChildCatalogModelDefine("10000000-1100-1110-1111-000000000000");
            annotitionTable.setCatalogID(sysTableGroupByParent.getID());
            this.designDataModelService.insertTableModelDefine(annotitionTable);
        } else {
            this.designDataModelService.updateTableModelDefine(annotitionTable);
        }
        if (createAnnotitionFieldList.size() > 0) {
            this.designDataModelService.insertColumnModelDefines(createAnnotitionFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        if (modifyAnnotitionFieldList.size() > 0) {
            this.designDataModelService.updateColumnModelDefines(modifyAnnotitionFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        return annotitionTable.getID();
    }

    public String modelingDataLinkFile(DesignTaskDefine taskDefine, FormSchemeDefine scheme) throws Exception {
        String dataLinkFileTableName = "SYS_FMCEANDF_" + scheme.getFormSchemeCode();
        DesignTableModelDefine dataLinkFileTable = this.designDataModelService.getTableModelDefineByCode(dataLinkFileTableName);
        boolean doInsertDataLinkFile = true;
        if (dataLinkFileTable == null) {
            dataLinkFileTable = this.designDataModelService.createTableModelDefine();
        } else {
            doInsertDataLinkFile = false;
        }
        dataLinkFileTable.setCode(dataLinkFileTableName);
        dataLinkFileTable.setDesc("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u62a5\u8868\u6279\u6ce8\u5173\u8054\u7684\u94fe\u63a5\u6307\u6807\u8868");
        dataLinkFileTable.setTitle("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u62a5\u8868\u6279\u6ce8\u5173\u8054\u7684\u94fe\u63a5\u6307\u6807\u8868");
        dataLinkFileTable.setName(dataLinkFileTableName);
        dataLinkFileTable.setType(TableModelType.DEFAULT);
        dataLinkFileTable.setKind(TableModelKind.DEFAULT);
        dataLinkFileTable.setOwner("NR");
        String dataLinkFileTableKey = dataLinkFileTable.getID();
        ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
        String dataLinkFileTableId = this.initField(dataLinkFileTableKey, "FMCEANDF_ID", null, createFieldList, modifyFieldList, ColumnModelType.STRING, 40);
        this.initField(dataLinkFileTableKey, "FMCEANDF_FMCEAN_ID", null, createFieldList, modifyFieldList, ColumnModelType.STRING, 40);
        this.initField(dataLinkFileTableKey, "FMCEANDF_SHOW", null, createFieldList, modifyFieldList, ColumnModelType.STRING, 500);
        this.initField(dataLinkFileTableKey, "FORM_KEY", null, createFieldList, modifyFieldList, ColumnModelType.STRING, 40);
        this.initField(dataLinkFileTableKey, "REGION_KEY", null, createFieldList, modifyFieldList, ColumnModelType.STRING, 40);
        this.initField(dataLinkFileTableKey, "DATALINK_KEY", null, createFieldList, modifyFieldList, ColumnModelType.STRING, 40);
        this.initField(dataLinkFileTableKey, "ROW_ID", null, createFieldList, modifyFieldList, ColumnModelType.STRING, 100);
        this.initField(dataLinkFileTableKey, "FIELD_KEY", null, createFieldList, modifyFieldList, ColumnModelType.STRING, 40);
        dataLinkFileTable.setBizKeys(dataLinkFileTableId);
        dataLinkFileTable.setKeys(dataLinkFileTableId);
        if (doInsertDataLinkFile) {
            DesignCatalogModelDefine sysTableGroupByParent = this.catalogModelService.createChildCatalogModelDefine("10000000-1100-1110-1111-000000000000");
            dataLinkFileTable.setCatalogID(sysTableGroupByParent.getID());
            this.designDataModelService.insertTableModelDefine(dataLinkFileTable);
        } else {
            this.designDataModelService.updateTableModelDefine(dataLinkFileTable);
        }
        if (createFieldList.size() > 0) {
            this.designDataModelService.insertColumnModelDefines(createFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        if (modifyFieldList.size() > 0) {
            this.designDataModelService.updateColumnModelDefines(modifyFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        return dataLinkFileTable.getID();
    }

    public String modelingComment(DesignTaskDefine taskDefine, FormSchemeDefine scheme) throws Exception {
        String commentTableCode = "SYS_FMCEANCO_" + scheme.getFormSchemeCode();
        DesignTableModelDefine commentTableDefine = this.designDataModelService.getTableModelDefineByCode(commentTableCode);
        boolean doInsertComment = true;
        if (commentTableDefine == null) {
            commentTableDefine = this.designDataModelService.createTableModelDefine();
        } else {
            doInsertComment = false;
        }
        commentTableDefine.setCode(commentTableCode);
        commentTableDefine.setDesc("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u62a5\u8868\u5355\u5143\u683c\u6279\u6ce8\u8868\u7684\u8bc4\u8bba\u8868");
        commentTableDefine.setTitle("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u62a5\u8868\u5355\u5143\u683c\u6279\u6ce8\u8868\u7684\u8bc4\u8bba\u8868");
        commentTableDefine.setName(commentTableCode);
        commentTableDefine.setType(TableModelType.DEFAULT);
        commentTableDefine.setKind(TableModelKind.DEFAULT);
        commentTableDefine.setOwner("NR");
        String commentTableKey = commentTableDefine.getID();
        ArrayList<DesignColumnModelDefine> commentCreateFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> commentModifyFieldList = new ArrayList<DesignColumnModelDefine>();
        String idKey = this.initField(commentTableKey, "FMCEANCO_ID", null, commentCreateFieldList, commentModifyFieldList, ColumnModelType.STRING, 40);
        this.initField(commentTableKey, "FMCEANCO_FMCEAN_ID", null, commentCreateFieldList, commentModifyFieldList, ColumnModelType.STRING, 40);
        this.initField(commentTableKey, "FMCEANCO_CONTENT", null, commentCreateFieldList, commentModifyFieldList, ColumnModelType.STRING, 500);
        this.initField(commentTableKey, "FMCEANCO_USER_ID", null, commentCreateFieldList, commentModifyFieldList, ColumnModelType.STRING, 50);
        this.initField(commentTableKey, "FMCEANCO_REPY_USER_ID", null, commentCreateFieldList, commentModifyFieldList, ColumnModelType.STRING, 50);
        this.initField(commentTableKey, "FMCEANCO_UPDATE_DATE", null, commentCreateFieldList, commentModifyFieldList, ColumnModelType.DATETIME, null);
        commentTableDefine.setBizKeys(idKey);
        commentTableDefine.setKeys(idKey);
        if (doInsertComment) {
            DesignCatalogModelDefine sysTableGroupByParent = this.catalogModelService.createChildCatalogModelDefine("10000000-1100-1110-1111-000000000000");
            commentTableDefine.setCatalogID(sysTableGroupByParent.getID());
            this.designDataModelService.insertTableModelDefine(commentTableDefine);
        } else {
            this.designDataModelService.updateTableModelDefine(commentTableDefine);
        }
        if (commentCreateFieldList.size() > 0) {
            this.designDataModelService.insertColumnModelDefines(commentCreateFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        if (commentModifyFieldList.size() > 0) {
            this.designDataModelService.updateColumnModelDefines(commentModifyFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        return commentTableDefine.getID();
    }

    public String modelingType(DesignTaskDefine taskDefine, FormSchemeDefine scheme) throws Exception {
        String typeTableCode = "SYS_FMCEANTYPE_" + scheme.getFormSchemeCode();
        DesignTableModelDefine typeTableDefine = this.designDataModelService.getTableModelDefineByCode(typeTableCode);
        boolean doInsertComment = true;
        if (typeTableDefine == null) {
            typeTableDefine = this.designDataModelService.createTableModelDefine();
        } else {
            doInsertComment = false;
        }
        typeTableDefine.setCode(typeTableCode);
        typeTableDefine.setDesc("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u6279\u6ce8\u7c7b\u578b\u5173\u8054\u8868");
        typeTableDefine.setTitle("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u6279\u6ce8\u7c7b\u578b\u5173\u8054\u8868");
        typeTableDefine.setName(typeTableCode);
        typeTableDefine.setType(TableModelType.DEFAULT);
        typeTableDefine.setKind(TableModelKind.DEFAULT);
        typeTableDefine.setOwner("NR");
        String typeTableKey = typeTableDefine.getID();
        ArrayList<DesignColumnModelDefine> typeCreateFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> typeModifyFieldList = new ArrayList<DesignColumnModelDefine>();
        String fmceanIdFieldKey = this.initField(typeTableKey, "TYPE_FMCEAN_ID", null, typeCreateFieldList, typeModifyFieldList, ColumnModelType.STRING, 40);
        String typeCodeFieldKey = this.initField(typeTableKey, "TYPE_CODE", null, typeCreateFieldList, typeModifyFieldList, ColumnModelType.STRING, 40);
        String keys = fmceanIdFieldKey + ";" + typeCodeFieldKey;
        typeTableDefine.setBizKeys(keys);
        typeTableDefine.setKeys(keys);
        if (doInsertComment) {
            DesignCatalogModelDefine sysTableGroupByParent = this.catalogModelService.createChildCatalogModelDefine("10000000-1100-1110-1111-000000000000");
            typeTableDefine.setCatalogID(sysTableGroupByParent.getID());
            this.designDataModelService.insertTableModelDefine(typeTableDefine);
        } else {
            this.designDataModelService.updateTableModelDefine(typeTableDefine);
        }
        if (typeCreateFieldList.size() > 0) {
            this.designDataModelService.insertColumnModelDefines(typeCreateFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        if (typeModifyFieldList.size() > 0) {
            this.designDataModelService.updateColumnModelDefines(typeModifyFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        return typeTableDefine.getID();
    }

    private String initField(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, ColumnModelType fieldType, Integer size) throws Exception {
        DesignColumnModelDefine fieldDefine = this.designDataModelService.getColumnModelDefineByCode(tableKey, fieldCode);
        if (fieldDefine == null) {
            fieldDefine = this.designDataModelService.createColumnModelDefine();
            createFieldList.add(fieldDefine);
        } else {
            modifyFieldList.add(fieldDefine);
        }
        fieldDefine.setCode(fieldCode);
        fieldDefine.setColumnType(fieldType);
        fieldDefine.setTableID(tableKey);
        if (null != size) {
            fieldDefine.setPrecision(size.intValue());
        }
        if (referField != null) {
            fieldDefine.setReferColumnID(referField);
        }
        return fieldDefine.getID();
    }

    private IEntityDefine queryDimisionByView(String entityViewKey) {
        if (StringUtils.isNotEmpty((String)entityViewKey)) {
            return this.iEntityMetaService.queryEntity(entityViewKey);
        }
        return null;
    }

    public String queryEntityTableBizKeyByView(String entityViewKey) throws Exception {
        IEntityDefine entity = null;
        if (StringUtils.isNotEmpty((String)entityViewKey)) {
            entity = this.iEntityMetaService.queryEntity(entityViewKey);
        }
        if (entity != null) {
            return this.queryEntityBizeKeyByEntityId(entity.getId());
        }
        return null;
    }

    public String queryEntityBizeKeyByEntityId(String entityId) throws Exception {
        IEntityModel entityModel = this.iEntityMetaService.getEntityModel(entityId);
        IEntityAttribute referFieldId = entityModel.getBizKeyField();
        if (referFieldId == null) {
            referFieldId = entityModel.getRecordKeyField();
        }
        return referFieldId.getID();
    }

    public IPeriodEntity queryPeriodEntity(String viewKey) {
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        return periodAdapter.getPeriodEntity(viewKey);
    }

    public String queryPeriodBizKey(String viewKey) {
        IPeriodEntity periodEntity = this.queryPeriodEntity(viewKey);
        return this.periodEngineService.getPeriodAdapter().getPeriodEntityTableModel(periodEntity.getKey()).getBizKeys();
    }

    private boolean isPeriodView(String viewKey) {
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        return periodAdapter.isPeriodEntity(viewKey);
    }
}

