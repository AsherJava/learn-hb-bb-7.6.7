/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.deploy.DeployItem
 *  com.jiuqi.np.definition.observer.MessageType
 *  com.jiuqi.np.definition.observer.NpDefinitionObserver
 *  com.jiuqi.np.definition.observer.Observer
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.deploy.DeployParams
 *  com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.service.DeployPrepareEventListener
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.annotation.deploy;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.deploy.DeployItem;
import com.jiuqi.np.definition.observer.MessageType;
import com.jiuqi.np.definition.observer.NpDefinitionObserver;
import com.jiuqi.np.definition.observer.Observer;
import com.jiuqi.nr.annotation.deploy.ColumnInfo;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.service.DeployPrepareEventListener;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.CatalogModelService;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@NpDefinitionObserver(type={MessageType.NRPUBLISHTASK}, name={"formCellAnnotationObserver"})
public class FormCellAnnotationObserver
implements Observer,
DeployPrepareEventListener,
IParamDeployFinishListener {
    private static final Logger logger = LoggerFactory.getLogger(FormCellAnnotationObserver.class);
    private final List<ColumnInfo> typeTableColumnList = new ArrayList<ColumnInfo>();
    private final List<ColumnInfo> coTableColumnList = new ArrayList<ColumnInfo>();
    private final List<ColumnInfo> dfTableColumnList = new ArrayList<ColumnInfo>();
    private final List<ColumnInfo> dataTableColumnList = new ArrayList<ColumnInfo>();
    @Autowired
    private IDesignTimeViewController nrDesignController;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private CatalogModelService catalogModelService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    private static final String DW_FIELD = "MDCODE";
    private static final String PERIOD_FIELD = "PERIOD";

    public FormCellAnnotationObserver() {
        this.dataTableColumnList.add(new ColumnInfo("FMCEAN_ID", "FMCEAN_ID", ColumnModelType.STRING, 40, "", true));
        this.dataTableColumnList.add(new ColumnInfo("FMCEAN_CONTENT", "FMCEAN_CONTENT", ColumnModelType.STRING, 500, ""));
        this.dataTableColumnList.add(new ColumnInfo("FMCEAN_USER_ID", "FMCEAN_USER_ID", ColumnModelType.STRING, 50, ""));
        this.dataTableColumnList.add(new ColumnInfo("FMCEAN_UPDATE_DATE", "FMCEAN_UPDATE_DATE", ColumnModelType.DATETIME, 0, ""));
        this.dfTableColumnList.add(new ColumnInfo("FMCEANDF_ID", "FMCEANDF_ID", ColumnModelType.STRING, 40, "", true));
        this.dfTableColumnList.add(new ColumnInfo("FMCEANDF_FMCEAN_ID", "FMCEANDF_FMCEAN_ID", ColumnModelType.STRING, 40, ""));
        this.dfTableColumnList.add(new ColumnInfo("FMCEANDF_SHOW", "FMCEANDF_SHOW", ColumnModelType.STRING, 500, ""));
        this.dfTableColumnList.add(new ColumnInfo("FORM_KEY", "FORM_KEY", ColumnModelType.STRING, 40, ""));
        this.dfTableColumnList.add(new ColumnInfo("REGION_KEY", "REGION_KEY", ColumnModelType.STRING, 40, ""));
        this.dfTableColumnList.add(new ColumnInfo("DATALINK_KEY", "DATALINK_KEY", ColumnModelType.STRING, 40, ""));
        this.dfTableColumnList.add(new ColumnInfo("ROW_ID", "ROW_ID", ColumnModelType.STRING, 100, ""));
        this.dfTableColumnList.add(new ColumnInfo("FIELD_KEY", "FIELD_KEY", ColumnModelType.STRING, 40, ""));
        this.coTableColumnList.add(new ColumnInfo("FMCEANCO_ID", "FMCEANCO_ID", ColumnModelType.STRING, 40, "", true));
        this.coTableColumnList.add(new ColumnInfo("FMCEANCO_FMCEAN_ID", "FMCEANCO_FMCEAN_ID", ColumnModelType.STRING, 40, ""));
        this.coTableColumnList.add(new ColumnInfo("FMCEANCO_CONTENT", "FMCEANCO_CONTENT", ColumnModelType.STRING, 500, ""));
        this.coTableColumnList.add(new ColumnInfo("FMCEANCO_USER_ID", "FMCEANCO_USER_ID", ColumnModelType.STRING, 50, ""));
        this.coTableColumnList.add(new ColumnInfo("FMCEANCO_REPY_USER_ID", "FMCEANCO_REPY_USER_ID", ColumnModelType.STRING, 50, ""));
        this.coTableColumnList.add(new ColumnInfo("FMCEANCO_UPDATE_DATE", "FMCEANCO_UPDATE_DATE", ColumnModelType.DATETIME, 0, ""));
        this.typeTableColumnList.add(new ColumnInfo("TYPE_FMCEAN_ID", "TYPE_FMCEAN_ID", ColumnModelType.STRING, 40, "", true));
        this.typeTableColumnList.add(new ColumnInfo("TYPE_CODE", "TYPE_CODE", ColumnModelType.STRING, 40, "", true));
    }

    public boolean isAsyn() {
        return false;
    }

    public String getName() {
        return "\u8868\u683c\u5355\u5143\u683c\u6279\u6ce8\u8868" + this.getClass().getName();
    }

    public void excute(Object[] objs) throws Exception {
        if (objs == null || objs.length == 0) {
            return;
        }
        for (Object obj : objs) {
            String taskID;
            DesignTaskDefine taskDefine;
            if (!(obj instanceof String) || "2.0".equals((taskDefine = this.nrDesignController.queryTaskDefine(taskID = (String)obj)).getVersion())) continue;
            List schemes = this.nrDesignController.queryFormSchemeByTask(taskID);
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
            if (schemes == null) {
                return;
            }
            for (DesignFormSchemeDefine scheme : schemes) {
                this.deployAnnotation(taskDefine, scheme, dataScheme);
            }
        }
    }

    private void deployAnnotation(DesignTaskDefine taskDefine, DesignFormSchemeDefine scheme, DataScheme dataScheme) throws Exception {
        this.deployDataTable(taskDefine, scheme, dataScheme);
        this.deployDFTable(taskDefine, scheme, dataScheme);
        this.deployCOTable(taskDefine, scheme, dataScheme);
        this.deployTYPETable(taskDefine, scheme, dataScheme);
    }

    private void deployTYPETable(DesignTaskDefine taskDefine, DesignFormSchemeDefine scheme, DataScheme dataScheme) throws Exception {
        boolean doInsert;
        String typeTableCode = "SYS_FMCEANTYPE_" + scheme.getFormSchemeCode();
        DesignTableModelDefine typeTableDefine = this.designDataModelService.getTableModelDefineByCode(typeTableCode);
        Map<Object, Object> oldColumnMap = new HashMap();
        boolean bl = doInsert = typeTableDefine == null;
        if (doInsert) {
            typeTableDefine = this.initNewTableDefine(typeTableCode, dataScheme);
        } else {
            typeTableDefine.setBizKeys("");
            typeTableDefine.setKeys("");
            List oldColumns = this.designDataModelService.getColumnModelDefinesByTable(typeTableDefine.getID());
            oldColumnMap = oldColumns.stream().collect(Collectors.toMap(IModelDefineItem::getCode, t -> t));
        }
        typeTableDefine.setDesc("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u6279\u6ce8\u7c7b\u578b\u5173\u8054\u8868");
        typeTableDefine.setTitle("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u6279\u6ce8\u7c7b\u578b\u5173\u8054\u8868");
        ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> deleteFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<String> newColumCodes = new ArrayList<String>();
        for (ColumnInfo column : this.typeTableColumnList) {
            this.addColumn(typeTableDefine, oldColumnMap, column, createFieldList, modifyFieldList);
            newColumCodes.add(column.getCode());
        }
        if (!oldColumnMap.isEmpty()) {
            Set<Object> codes = oldColumnMap.keySet();
            for (String string : codes) {
                DesignColumnModelDefine designColumnModelDefine = (DesignColumnModelDefine)oldColumnMap.get(string);
                if (newColumCodes.contains(designColumnModelDefine.getCode())) continue;
                deleteFieldList.add(designColumnModelDefine);
            }
        }
        this.doSaveAndDeploy(typeTableDefine, doInsert, createFieldList, modifyFieldList, deleteFieldList);
    }

    private void deployCOTable(DesignTaskDefine taskDefine, DesignFormSchemeDefine scheme, DataScheme dataScheme) throws Exception {
        boolean doInsert;
        String commentTableCode = "SYS_FMCEANCO_" + scheme.getFormSchemeCode();
        DesignTableModelDefine commentTableDefine = this.designDataModelService.getTableModelDefineByCode(commentTableCode);
        Map<Object, Object> oldColumnMap = new HashMap();
        boolean bl = doInsert = commentTableDefine == null;
        if (doInsert) {
            commentTableDefine = this.initNewTableDefine(commentTableCode, dataScheme);
        } else {
            commentTableDefine.setBizKeys("");
            commentTableDefine.setKeys("");
            List oldColumns = this.designDataModelService.getColumnModelDefinesByTable(commentTableDefine.getID());
            oldColumnMap = oldColumns.stream().collect(Collectors.toMap(IModelDefineItem::getCode, t -> t));
        }
        commentTableDefine.setDesc("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u62a5\u8868\u5355\u5143\u683c\u6279\u6ce8\u8868\u7684\u8bc4\u8bba\u8868");
        commentTableDefine.setTitle("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u62a5\u8868\u5355\u5143\u683c\u6279\u6ce8\u8868\u7684\u8bc4\u8bba\u8868");
        ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> deleteFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<String> newColumCodes = new ArrayList<String>();
        for (ColumnInfo column : this.coTableColumnList) {
            this.addColumn(commentTableDefine, oldColumnMap, column, createFieldList, modifyFieldList);
            newColumCodes.add(column.getCode());
        }
        if (!oldColumnMap.isEmpty()) {
            Set<Object> codes = oldColumnMap.keySet();
            for (String string : codes) {
                DesignColumnModelDefine designColumnModelDefine = (DesignColumnModelDefine)oldColumnMap.get(string);
                if (newColumCodes.contains(designColumnModelDefine.getCode())) continue;
                deleteFieldList.add(designColumnModelDefine);
            }
        }
        this.doSaveAndDeploy(commentTableDefine, doInsert, createFieldList, modifyFieldList, deleteFieldList);
    }

    private void deployDFTable(DesignTaskDefine taskDefine, DesignFormSchemeDefine scheme, DataScheme dataScheme) throws Exception {
        boolean doInsert;
        String dataLinkFileTableName = "SYS_FMCEANDF_" + scheme.getFormSchemeCode();
        DesignTableModelDefine dataLinkFileTable = this.designDataModelService.getTableModelDefineByCode(dataLinkFileTableName);
        Map<Object, Object> oldColumnMap = new HashMap();
        boolean bl = doInsert = dataLinkFileTable == null;
        if (doInsert) {
            dataLinkFileTable = this.initNewTableDefine(dataLinkFileTableName, dataScheme);
        } else {
            dataLinkFileTable.setBizKeys("");
            dataLinkFileTable.setKeys("");
            List oldColumns = this.designDataModelService.getColumnModelDefinesByTable(dataLinkFileTable.getID());
            oldColumnMap = oldColumns.stream().collect(Collectors.toMap(IModelDefineItem::getCode, t -> t));
        }
        dataLinkFileTable.setDesc("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u62a5\u8868\u6279\u6ce8\u5173\u8054\u7684\u94fe\u63a5\u6307\u6807\u8868");
        dataLinkFileTable.setTitle("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u62a5\u8868\u6279\u6ce8\u5173\u8054\u7684\u94fe\u63a5\u6307\u6807\u8868");
        ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> deleteFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<String> newColumCodes = new ArrayList<String>();
        for (ColumnInfo column : this.dfTableColumnList) {
            this.addColumn(dataLinkFileTable, oldColumnMap, column, createFieldList, modifyFieldList);
            newColumCodes.add(column.getCode());
        }
        if (!oldColumnMap.isEmpty()) {
            Set<Object> codes = oldColumnMap.keySet();
            for (String string : codes) {
                DesignColumnModelDefine designColumnModelDefine = (DesignColumnModelDefine)oldColumnMap.get(string);
                if (newColumCodes.contains(designColumnModelDefine.getCode())) continue;
                deleteFieldList.add(designColumnModelDefine);
            }
        }
        this.doSaveAndDeploy(dataLinkFileTable, doInsert, createFieldList, modifyFieldList, deleteFieldList);
    }

    private void deployDataTable(DesignTaskDefine taskDefine, DesignFormSchemeDefine scheme, DataScheme dataScheme) throws Exception {
        boolean doInsert;
        String annotitionTableName = "SYS_FMCEAN_" + scheme.getFormSchemeCode();
        DesignTableModelDefine annotitionTable = this.designDataModelService.getTableModelDefineByCode(annotitionTableName);
        HashMap<String, DesignColumnModelDefine> oldColumnMap = new HashMap();
        boolean bl = doInsert = annotitionTable == null;
        if (doInsert) {
            annotitionTable = this.initNewTableDefine(annotitionTableName, dataScheme);
        } else {
            annotitionTable.setBizKeys("");
            annotitionTable.setKeys("");
            List oldColumns = this.designDataModelService.getColumnModelDefinesByTable(annotitionTable.getID());
            oldColumnMap = oldColumns.stream().collect(Collectors.toMap(IModelDefineItem::getCode, t -> t));
        }
        annotitionTable.setDesc("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u62a5\u8868\u5355\u5143\u683c\u6279\u6ce8\u8868");
        annotitionTable.setTitle("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u62a5\u8868\u5355\u5143\u683c\u6279\u6ce8\u8868");
        ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> deleteFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<String> newColumCodes = new ArrayList<String>();
        String entitiesKey = taskDefine.getMasterEntitiesKey();
        if (StringUtils.isEmpty((String)entitiesKey)) {
            throw new Exception("\u62a5\u8868\u65b9\u6848\u3001\u4efb\u52a1\u6ca1\u6709\u8bbe\u7f6e\u4e3b\u952e\u65e0\u6cd5\u521b\u5efa\u6279\u6ce8\u8868");
        }
        this.addColumn(annotitionTable, oldColumnMap, new ColumnInfo(DW_FIELD, DW_FIELD, ColumnModelType.STRING, 40, this.queryEntityTableBizKeyByEntityID(taskDefine.getDw()), true), createFieldList, modifyFieldList);
        newColumCodes.add(DW_FIELD);
        String dimsStr = taskDefine.getDims();
        if (StringUtils.isNotEmpty((String)dimsStr)) {
            Object dims;
            for (String dim : dims = dimsStr.split(";")) {
                IEntityDefine entity = this.iEntityMetaService.queryEntity(dim);
                if (null == entity) continue;
                this.addColumn(annotitionTable, oldColumnMap, new ColumnInfo(entity.getDimensionName(), entity.getDimensionName(), ColumnModelType.STRING, 50, this.queryEntityBizKeyByEntityId(entity.getId()), true), createFieldList, modifyFieldList);
                newColumCodes.add(entity.getDimensionName());
            }
        }
        this.addColumn(annotitionTable, oldColumnMap, new ColumnInfo(PERIOD_FIELD, PERIOD_FIELD, ColumnModelType.STRING, 9, this.queryPeriodBizKey(taskDefine.getDateTime()), true), createFieldList, modifyFieldList);
        newColumCodes.add(PERIOD_FIELD);
        for (ColumnInfo column : this.dataTableColumnList) {
            this.addColumn(annotitionTable, oldColumnMap, column, createFieldList, modifyFieldList);
            newColumCodes.add(column.getCode());
        }
        if (!oldColumnMap.isEmpty()) {
            Set codes = oldColumnMap.keySet();
            for (String code : codes) {
                DesignColumnModelDefine designColumnModelDefine = (DesignColumnModelDefine)oldColumnMap.get(code);
                if (newColumCodes.contains(designColumnModelDefine.getCode())) continue;
                deleteFieldList.add(designColumnModelDefine);
            }
        }
        this.doSaveAndDeploy(annotitionTable, doInsert, createFieldList, modifyFieldList, deleteFieldList);
    }

    private DesignTableModelDefine initNewTableDefine(String commentTableCode, DataScheme dataScheme) {
        DesignTableModelDefine commentTableDefine = this.designDataModelService.createTableModelDefine();
        commentTableDefine.setCode(commentTableCode);
        commentTableDefine.setName(commentTableCode);
        commentTableDefine.setOwner("NR");
        DesignCatalogModelDefine sysTableGroupByParent = this.catalogModelService.createChildCatalogModelDefine("10000000-1100-1110-1111-000000000000");
        commentTableDefine.setCatalogID(sysTableGroupByParent.getID());
        commentTableDefine.setSupportNrdb(true);
        commentTableDefine.setStorageName(dataScheme.getBizCode());
        return commentTableDefine;
    }

    private void doSaveAndDeploy(DesignTableModelDefine commentTableDefine, boolean doInsert, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, List<DesignColumnModelDefine> deleteFieldList) throws Exception {
        if (doInsert) {
            this.designDataModelService.insertTableModelDefine(commentTableDefine);
        } else {
            this.designDataModelService.updateTableModelDefine(commentTableDefine);
        }
        if (createFieldList.size() > 0) {
            this.designDataModelService.insertColumnModelDefines(createFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        if (modifyFieldList.size() > 0) {
            this.designDataModelService.updateColumnModelDefines(modifyFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        if (deleteFieldList.size() > 0) {
            String[] deleteIds = (String[])deleteFieldList.stream().map(IModelDefineItem::getID).toArray(String[]::new);
            this.designDataModelService.deleteColumnModelDefines(deleteIds);
        }
        if (!(createFieldList.isEmpty() && modifyFieldList.isEmpty() && deleteFieldList.isEmpty())) {
            this.dataModelDeployService.deployTableUnCheck(commentTableDefine.getID());
        }
    }

    private void addColumn(DesignTableModelDefine annotitionTable, Map<String, DesignColumnModelDefine> oldColumnMap, ColumnInfo columnInfo, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) {
        DesignColumnModelDefine columnModelDefine = this.initField(annotitionTable.getID(), columnInfo.getReferFieldID(), columnInfo);
        DesignColumnModelDefine oldUserColumn = oldColumnMap.get(columnInfo.getCode());
        if (oldUserColumn == null) {
            createFieldList.add(columnModelDefine);
        } else {
            columnModelDefine.setID(oldUserColumn.getID());
            if (!this.checkColumnEquals(columnModelDefine, oldUserColumn)) {
                modifyFieldList.add(columnModelDefine);
            }
        }
        if (columnInfo.isKeyField()) {
            String keys = StringUtils.isEmpty((String)annotitionTable.getBizKeys()) ? columnModelDefine.getID() : annotitionTable.getBizKeys() + ";" + columnModelDefine.getID();
            annotitionTable.setBizKeys(keys);
            annotitionTable.setKeys(keys);
        }
    }

    private DesignColumnModelDefine initField(String tableKey, String referField, ColumnInfo columnInfo) {
        DesignColumnModelDefine fieldDefine = this.designDataModelService.createColumnModelDefine();
        fieldDefine.setCode(columnInfo.getCode());
        fieldDefine.setName(columnInfo.getCode());
        fieldDefine.setColumnType(columnInfo.getType());
        fieldDefine.setTableID(tableKey);
        fieldDefine.setPrecision(columnInfo.getSize());
        fieldDefine.setReferColumnID(referField);
        return fieldDefine;
    }

    private void deployDeleteTableByCode(String tableCode) {
        try {
            DesignTableModelDefine tableDefine = this.designDataModelService.getTableModelDefineByCode(tableCode);
            if (tableDefine == null) {
                return;
            }
            this.designDataModelService.deleteTableModelDefine(tableDefine.getID());
            this.dataModelDeployService.deployTableUnCheck(tableDefine.getID());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private String queryEntityTableBizKeyByEntityID(String entityID) {
        IEntityDefine entity;
        if (StringUtils.isNotEmpty((String)entityID) && (entity = this.iEntityMetaService.queryEntity(entityID)) != null) {
            return this.queryEntityBizKeyByEntityId(entity.getId());
        }
        return null;
    }

    private String queryEntityBizKeyByEntityId(String entityId) {
        IEntityModel entityModel = this.iEntityMetaService.getEntityModel(entityId);
        IEntityAttribute referFieldId = entityModel.getBizKeyField();
        if (referFieldId == null) {
            referFieldId = entityModel.getRecordKeyField();
        }
        return referFieldId.getID();
    }

    private String queryPeriodBizKey(String viewKey) {
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(viewKey);
        return this.periodEngineService.getPeriodAdapter().getPeriodEntityTableModel(periodEntity.getKey()).getBizKeys();
    }

    private boolean checkColumnEquals(DesignColumnModelDefine o1, DesignColumnModelDefine o2) {
        return o1.getCode().equalsIgnoreCase(o2.getCode()) && o1.getColumnType() == o2.getColumnType() && (StringUtils.isEmpty((String)o1.getReferColumnID()) && StringUtils.isEmpty((String)o2.getReferColumnID()) || o1.getReferColumnID().equals(o2.getReferColumnID())) && o1.getPrecision() == o2.getPrecision();
    }

    public void onDeploy(DeployParams deployParams) {
        DeployItem formScheme = deployParams.getFormScheme();
        if (null == formScheme) {
            return;
        }
        Set runTimeKeys = formScheme.getRunTimeKeys();
        Set designTimeKeys = formScheme.getDesignTimeKeys();
        HashSet runKeysCopy = new HashSet(runTimeKeys);
        runKeysCopy.removeAll(designTimeKeys);
        if (runKeysCopy.isEmpty()) {
            return;
        }
        for (String runTimeKey : runKeysCopy) {
            FormSchemeDefine scheme = this.runTimeViewController.getFormScheme(runTimeKey);
            if (null == scheme) continue;
            String schemeCode = scheme.getFormSchemeCode();
            this.deployDeleteTableByCode("SYS_FMCEAN_" + schemeCode);
            this.deployDeleteTableByCode("SYS_FMCEANDF_" + schemeCode);
            this.deployDeleteTableByCode("SYS_FMCEANCO_" + schemeCode);
            this.deployDeleteTableByCode("SYS_FMCEANTYPE_" + schemeCode);
        }
    }

    public void manuaDdeployAnnotationTypeTable(List<TaskDefine> taskDefines) throws Exception {
        for (TaskDefine taskDefine : taskDefines) {
            DataScheme dataScheme;
            List schemes;
            DesignTaskDefine designTaskDefine = this.nrDesignController.queryTaskDefine(taskDefine.getKey());
            if (null == designTaskDefine || null == (schemes = this.nrDesignController.queryFormSchemeByTask(designTaskDefine.getKey())) || null == (dataScheme = this.runtimeDataSchemeService.getDataScheme(designTaskDefine.getDataScheme()))) continue;
            for (DesignFormSchemeDefine scheme : schemes) {
                this.deployTYPETable(designTaskDefine, scheme, dataScheme);
            }
        }
    }

    public void onAdd(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        progressConsumer.accept("\u6b63\u5728\u53d1\u5e03\u6279\u6ce8\u8868");
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(define.getTaskKey());
        DesignFormSchemeDefine designFormSchemeDefine = this.nrDesignController.queryFormSchemeDefine(define.getKey());
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        try {
            this.deployAnnotation(taskDefine, designFormSchemeDefine, dataScheme);
            progressConsumer.accept("\u53d1\u5e03\u6279\u6ce8\u8868\u6210\u529f");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            warningConsumer.accept("\u53d1\u5e03\u6279\u6ce8\u8868\u5931\u8d25");
        }
    }

    public void onDelete(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        DesignFormSchemeDefine designFormScheme = this.nrDesignController.queryFormSchemeDefine(define.getKey());
        FormSchemeDefine runTimeformScheme = this.runTimeViewController.getFormScheme(define.getKey());
        if (null == designFormScheme && null != runTimeformScheme) {
            progressConsumer.accept("\u6b63\u5728\u5220\u9664\u679a\u4e3e\u5b57\u5178\u68c0\u67e52.0\u8868");
            String schemeCode = runTimeformScheme.getFormSchemeCode();
            this.deployDeleteTableByCode("SYS_FMCEAN_" + schemeCode);
            this.deployDeleteTableByCode("SYS_FMCEANDF_" + schemeCode);
            this.deployDeleteTableByCode("SYS_FMCEANCO_" + schemeCode);
            this.deployDeleteTableByCode("SYS_FMCEANTYPE_" + schemeCode);
            progressConsumer.accept("\u5220\u9664\u679a\u4e3e\u5b57\u5178\u68c0\u67e52.0\u8868\u6210\u529f");
        }
    }

    public void onUpdate(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        progressConsumer.accept("\u6b63\u5728\u66f4\u65b0\u6279\u6ce8\u8868");
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(define.getTaskKey());
        DesignFormSchemeDefine designFormSchemeDefine = this.nrDesignController.queryFormSchemeDefine(define.getKey());
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        try {
            this.deployAnnotation(taskDefine, designFormSchemeDefine, dataScheme);
            progressConsumer.accept("\u66f4\u65b0\u6279\u6ce8\u8868\u6210\u529f");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            warningConsumer.accept("\u66f4\u65b0\u6279\u6ce8\u8868\u5931\u8d25");
        }
    }
}

