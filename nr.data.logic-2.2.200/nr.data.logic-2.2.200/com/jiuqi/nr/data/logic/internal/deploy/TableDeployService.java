/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.data.logic.internal.deploy;

import com.jiuqi.nr.data.logic.facade.event.CheckTableDeployEvent;
import com.jiuqi.nr.data.logic.facade.event.CheckTableDeploySource;
import com.jiuqi.nr.data.logic.internal.deploy.TableInfo;
import com.jiuqi.nr.data.logic.internal.obj.DeployColumnCollection;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.data.logic.internal.util.NvwaDataModelUtil;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class TableDeployService {
    @Autowired
    private NvwaDataModelUtil nvwaDataModelUtil;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private DesignDataModelService designDataModelService;
    private static final Logger logger = LoggerFactory.getLogger(TableDeployService.class);

    public void deployTables(TaskDefine task, FormSchemeDefine formScheme) throws Exception {
        this.nvwaDataModelUtil.deployTableUncheck(this.modelingALLCKR(task, formScheme));
        this.nvwaDataModelUtil.deployTableUncheck(this.modelingCKR(task, formScheme));
        this.nvwaDataModelUtil.deployTableUncheck(this.modelingCKD(task, formScheme));
        this.nvwaDataModelUtil.deployTableUncheck(this.modelingCKS(task, formScheme));
        this.nvwaDataModelUtil.deployTableUncheck(this.modelingCKSSub(task, formScheme));
    }

    public void deployALLCKRTable(TaskDefine task, FormSchemeDefine formScheme) throws Exception {
        this.nvwaDataModelUtil.deployTableUncheck(this.modelingALLCKR(task, formScheme));
    }

    public void deployCKRTable(TaskDefine task, FormSchemeDefine formScheme) throws Exception {
        this.nvwaDataModelUtil.deployTableUncheck(this.modelingCKR(task, formScheme));
    }

    public void deployCKDTable(TaskDefine task, FormSchemeDefine formScheme) throws Exception {
        this.nvwaDataModelUtil.deployTableUncheck(this.modelingCKD(task, formScheme));
    }

    public void deployCKDTableUnCheck(TaskDefine task, FormSchemeDefine formScheme) throws Exception {
        this.nvwaDataModelUtil.deployTableUncheck(this.modelingCKD(task, formScheme));
    }

    public void deployCKSTableUncheck(TaskDefine task, FormSchemeDefine formScheme) throws Exception {
        this.nvwaDataModelUtil.deployTableUncheck(this.modelingCKS(task, formScheme));
    }

    public void deployCKSSubTableUncheck(TaskDefine task, FormSchemeDefine formScheme) {
        this.nvwaDataModelUtil.deployTableUncheck(this.modelingCKSSub(task, formScheme));
    }

    public void publishSuccessEvent(TaskDefine task, FormSchemeDefine formScheme) {
        CheckTableDeploySource checkTableDeploySource = new CheckTableDeploySource(task, formScheme);
        this.applicationContext.publishEvent(new CheckTableDeployEvent(checkTableDeploySource));
    }

    public void deleteTables(String formSchemeCode) {
        this.nvwaDataModelUtil.deployDeleteTableByCode(CheckTableNameUtil.getAllCKRTableName(formSchemeCode));
        this.nvwaDataModelUtil.deployDeleteTableByCode(CheckTableNameUtil.getCKRTableName(formSchemeCode));
        this.nvwaDataModelUtil.deployDeleteTableByCode(CheckTableNameUtil.getCKDTableName(formSchemeCode));
        this.nvwaDataModelUtil.deployDeleteTableByCode(CheckTableNameUtil.getCKSTableName(formSchemeCode));
        this.nvwaDataModelUtil.deployDeleteTableByCode(CheckTableNameUtil.getCKSSubTableName(formSchemeCode));
    }

    public List<String> getPreDeployDdl(TaskDefine task, FormSchemeDefine formScheme) {
        ArrayList<String> result = new ArrayList<String>();
        this.appendALLCKRDdl(task, formScheme, result);
        this.appendCKRDdl(task, formScheme, result);
        this.appendCKDDdl(task, formScheme, result);
        this.appendCKSDdl(task, formScheme, result);
        this.appendCKSSubDdl(task, formScheme, result);
        return result;
    }

    public List<String> getRegisterTableId(String formSchemeCode) {
        ArrayList<String> result = new ArrayList<String>();
        result.add(this.designDataModelService.getTableModelDefineByCode(CheckTableNameUtil.getAllCKRTableName(formSchemeCode)).getID());
        result.add(this.designDataModelService.getTableModelDefineByCode(CheckTableNameUtil.getCKRTableName(formSchemeCode)).getID());
        result.add(this.designDataModelService.getTableModelDefineByCode(CheckTableNameUtil.getCKDTableName(formSchemeCode)).getID());
        result.add(this.designDataModelService.getTableModelDefineByCode(CheckTableNameUtil.getCKSTableName(formSchemeCode)).getID());
        result.add(this.designDataModelService.getTableModelDefineByCode(CheckTableNameUtil.getCKSSubTableName(formSchemeCode)).getID());
        return result;
    }

    private TableInfo modelingALLCKR(TaskDefine taskDefine, FormSchemeDefine scheme) throws Exception {
        String tableCode = CheckTableNameUtil.getAllCKRTableName(scheme.getFormSchemeCode());
        DesignTableModelDefine tableModelDefine = this.nvwaDataModelUtil.getDesTableByCode(tableCode);
        boolean doInsert = true;
        if (tableModelDefine == null) {
            tableModelDefine = this.nvwaDataModelUtil.createTable();
        } else {
            doInsert = false;
        }
        this.nvwaDataModelUtil.initTableModel(taskDefine, scheme, tableCode, tableModelDefine, "\u5168\u5ba1\u7ed3\u679c\u8868");
        String tableID = tableModelDefine.getID();
        DeployColumnCollection deployColumnCollection = new DeployColumnCollection(this.nvwaDataModelUtil.getAllDesColumnsMap(tableID));
        String colRECIDColumnID = this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 36, "-", tableID, "ALLCKR_RECID", null, deployColumnCollection).getID();
        String colASYNCColumnID = this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 50, "-", tableID, "ALLCKR_ASYNCTASKID", null, deployColumnCollection).getID();
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 100, "", tableID, "ALLCKR_FORMORDER", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 100, "", tableID, "ALLCKR_FORMULAORDER", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 36, "", tableID, "ALLCKR_FORMULASCHEMEKEY", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 36, "", tableID, "ALLCKR_FORMKEY", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 100, "", tableID, "ALLCKR_FORMULAID", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 100, "", tableID, "ALLCKR_FORMULACODE", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.INTEGER, 10, "0", tableID, "ALLCKR_FORMULACHECKTYPE", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.INTEGER, 10, "0", tableID, "ALLCKR_GLOBROW", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.INTEGER, 10, "0", tableID, "ALLCKR_GLOBCOL", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 100, "", tableID, "ALLCKR_LEFT", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 100, "", tableID, "ALLCKR_RIGHT", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 100, "", tableID, "ALLCKR_BALANCE", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 1500, "", tableID, "ALLCKR_DIMSTR", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.CLOB, 0, "", tableID, "ALLCKR_ERRORDESC", null, deployColumnCollection);
        tableModelDefine.setKeys(colASYNCColumnID + ";" + colRECIDColumnID + ";");
        String masterEntityKeys = this.nvwaDataModelUtil.initEntityColumns(taskDefine, scheme, tableID, deployColumnCollection);
        tableModelDefine.setBizKeys(colASYNCColumnID + ";" + colRECIDColumnID + ";" + masterEntityKeys);
        String indexName = "IDX_ENTITY_" + tableCode;
        if (doInsert) {
            DesignCatalogModelDefine catalog = this.nvwaDataModelUtil.createCatalog();
            tableModelDefine.setCatalogID(catalog.getID());
            this.nvwaDataModelUtil.insertTableModelDefine(tableModelDefine);
            this.nvwaDataModelUtil.addIndexToTable(tableID, masterEntityKeys.split(";"), indexName, IndexModelType.NORMAL);
        } else {
            this.nvwaDataModelUtil.updateTableModelDefine(tableModelDefine);
            this.updateIndex(tableID, indexName, masterEntityKeys);
        }
        this.updateColumInfo(deployColumnCollection);
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableId(tableID);
        tableInfo.setTableName(tableCode);
        return tableInfo;
    }

    private void updateIndex(String tableID, String indexName, String indexFields) {
        List indexesByTable = this.designDataModelService.getIndexsByTable(tableID);
        Optional<DesignIndexModelDefine> any = indexesByTable.stream().filter(o -> o.getName().equals(indexName)).findAny();
        if (any.isPresent()) {
            DesignIndexModelDefine designIndexModelDefine = any.get();
            String fieldIDs = designIndexModelDefine.getFieldIDs();
            String latestFieldIDs = String.join((CharSequence)";", indexFields.split(";"));
            if (!latestFieldIDs.equals(fieldIDs)) {
                designIndexModelDefine.setFieldIDs(latestFieldIDs);
                designIndexModelDefine.setType(IndexModelType.NORMAL);
                this.designDataModelService.updateIndexModelDefine(designIndexModelDefine);
            }
        } else {
            this.nvwaDataModelUtil.addIndexToTable(tableID, indexFields.split(";"), indexName, IndexModelType.NORMAL);
        }
    }

    private TableInfo modelingCKR(TaskDefine taskDefine, FormSchemeDefine scheme) throws Exception {
        String tableCode = CheckTableNameUtil.getCKRTableName(scheme.getFormSchemeCode());
        DesignTableModelDefine tableModelDefine = this.nvwaDataModelUtil.getDesTableByCode(tableCode);
        boolean doInsert = true;
        if (tableModelDefine == null) {
            tableModelDefine = this.nvwaDataModelUtil.createTable();
        } else {
            doInsert = false;
        }
        this.nvwaDataModelUtil.initTableModel(taskDefine, scheme, tableCode, tableModelDefine, "\u6279\u91cf\u5ba1\u6838\u7ed3\u679c\u8868");
        String tableID = tableModelDefine.getID();
        DeployColumnCollection deployColumnCollection = new DeployColumnCollection(this.nvwaDataModelUtil.getAllDesColumnsMap(tableID));
        String colRECIDColumnID = this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 36, "-", tableID, "CKR_RECID", null, deployColumnCollection).getID();
        String batchColumnID = this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 50, "-", tableID, "CKR_BATCH_ID", null, deployColumnCollection).getID();
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.BIGDECIMAL, 20, "", tableID, "CKR_UNITORDER", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 100, "", tableID, "CKR_FORMORDER", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 100, "", tableID, "CKR_FORMULAORDER", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 36, "", tableID, "CKR_FORMULASCHEMEKEY", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 36, "", tableID, "CKR_FORMKEY", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 100, "", tableID, "CKR_FORMULAID", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 100, "", tableID, "CKR_FORMULACODE", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.INTEGER, 10, "0", tableID, "CKR_FORMULACHECKTYPE", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.INTEGER, 10, "0", tableID, "CKR_GLOBROW", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.INTEGER, 10, "0", tableID, "CKR_GLOBCOL", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 100, "", tableID, "CKR_LEFT", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 100, "", tableID, "CKR_RIGHT", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 100, "", tableID, "CKR_BALANCE", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 1500, "", tableID, "CKR_DIMSTR", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.CLOB, 0, "", tableID, "CKR_ERRORDESC", null, deployColumnCollection);
        tableModelDefine.setKeys(batchColumnID + ";" + colRECIDColumnID + ";");
        String masterEntityKeys = this.nvwaDataModelUtil.initEntityColumns(taskDefine, scheme, tableID, deployColumnCollection);
        String indexFields = batchColumnID + ";" + masterEntityKeys;
        tableModelDefine.setBizKeys(batchColumnID + ";" + colRECIDColumnID + ";" + masterEntityKeys);
        String indexName = "IDX_ENTITY_" + tableCode;
        if (doInsert) {
            DesignCatalogModelDefine catalog = this.nvwaDataModelUtil.createCatalog();
            tableModelDefine.setCatalogID(catalog.getID());
            this.nvwaDataModelUtil.insertTableModelDefine(tableModelDefine);
            this.nvwaDataModelUtil.addIndexToTable(tableID, indexFields.split(";"), indexName, IndexModelType.NORMAL);
        } else {
            this.nvwaDataModelUtil.updateTableModelDefine(tableModelDefine);
            this.updateIndex(tableID, indexName, indexFields);
        }
        this.updateColumInfo(deployColumnCollection);
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableId(tableID);
        tableInfo.setTableName(tableCode);
        return tableInfo;
    }

    private TableInfo modelingCKD(TaskDefine taskDefine, FormSchemeDefine scheme) throws Exception {
        String tableCode = CheckTableNameUtil.getCKDTableName(scheme.getFormSchemeCode());
        DesignTableModelDefine tableModelDefine = this.nvwaDataModelUtil.getDesTableByCode(tableCode);
        boolean doInsert = true;
        if (tableModelDefine == null) {
            tableModelDefine = this.nvwaDataModelUtil.createTable();
        } else {
            doInsert = false;
        }
        this.nvwaDataModelUtil.initTableModel(taskDefine, scheme, tableCode, tableModelDefine, "\u5ba1\u6838\u51fa\u9519\u8bf4\u660e\u8868");
        String tableID = tableModelDefine.getID();
        DeployColumnCollection deployColumnCollection = new DeployColumnCollection(this.nvwaDataModelUtil.getAllDesColumnsMap(tableID));
        String colRECIDColumnID = this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 36, "-", tableID, "CKD_RECID", null, deployColumnCollection).getID();
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 36, "", tableID, "CKD_FORMULASCHEMEKEY", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 36, "", tableID, "CKD_FORMKEY", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 100, "", tableID, "CKD_FORMULACODE", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 20, "", tableID, "CKD_GLOBCOL", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 20, "", tableID, "CKD_GLOBROW", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 36, "", tableID, "CKD_USERKEY", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 200, "", tableID, "CKD_USERNAME", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.DATETIME, 0, "", tableID, "CKD_UPDATETIME", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 1500, "", tableID, "CKD_DIMSTR", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 2000, "", tableID, "CKD_DESCRIPTION", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.INTEGER, 1, "0", tableID, "CKD_STATE", null, deployColumnCollection);
        tableModelDefine.setKeys(colRECIDColumnID + ";");
        String masterEntityKeys = this.nvwaDataModelUtil.initEntityColumns(taskDefine, scheme, tableID, deployColumnCollection);
        tableModelDefine.setBizKeys(colRECIDColumnID + ";" + masterEntityKeys);
        if (doInsert) {
            DesignCatalogModelDefine catalog = this.nvwaDataModelUtil.createCatalog();
            tableModelDefine.setCatalogID(catalog.getID());
            this.nvwaDataModelUtil.insertTableModelDefine(tableModelDefine);
        } else {
            this.nvwaDataModelUtil.updateTableModelDefine(tableModelDefine);
        }
        this.updateColumInfo(deployColumnCollection);
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableId(tableID);
        tableInfo.setTableName(tableCode);
        return tableInfo;
    }

    private TableInfo modelingCKS(TaskDefine taskDefine, FormSchemeDefine scheme) throws Exception {
        String tableCode = CheckTableNameUtil.getCKSTableName(scheme.getFormSchemeCode());
        DesignTableModelDefine tableModelDefine = this.nvwaDataModelUtil.getDesTableByCode(tableCode);
        boolean doInsert = true;
        if (tableModelDefine == null) {
            tableModelDefine = this.nvwaDataModelUtil.createTable();
        } else {
            doInsert = false;
        }
        this.nvwaDataModelUtil.initTableModel(taskDefine, scheme, tableCode, tableModelDefine, "\u5ba1\u6838\u72b6\u6001\u8bb0\u5f55\u8868");
        String tableID = tableModelDefine.getID();
        DeployColumnCollection deployColumnCollection = new DeployColumnCollection(this.nvwaDataModelUtil.getAllDesColumnsMap(tableID));
        ColumnModelDefine recKeyCol = this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 40, "", tableID, "CKS_REC_KEY", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 50, "", tableID, "CKS_BATCH_ID", null, deployColumnCollection);
        ColumnModelDefine flsCol = this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 40, "", tableID, "CKS_FLS_KEY", null, deployColumnCollection);
        ColumnModelDefine frmCol = this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 40, "", tableID, "CKS_FRM_KEY", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.INTEGER, 1, "", tableID, "CKS_CK_STATE", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.INTEGER, 1, "", tableID, "CKS_REUSE", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.INTEGER, 1, "", tableID, "CKS_ACTION", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.DATETIME, 0, "", tableID, "CKS_CK_TIME", null, deployColumnCollection);
        tableModelDefine.setKeys(recKeyCol.getID() + ";");
        String masterEntityKeys = this.nvwaDataModelUtil.initEntityColumns(taskDefine, scheme, tableID, deployColumnCollection);
        tableModelDefine.setBizKeys(masterEntityKeys + flsCol.getID() + ";" + frmCol.getID() + ";");
        DesignColumnModelDefine periodCol = deployColumnCollection.getColLatest("PERIOD");
        DesignColumnModelDefine unitCol = deployColumnCollection.getColLatest("MDCODE");
        String indexName = "IDX_QUERY_" + tableCode;
        String indexFields = periodCol.getID() + ";" + unitCol.getID() + ";" + frmCol.getID();
        if (doInsert) {
            DesignCatalogModelDefine catalog = this.nvwaDataModelUtil.createCatalog();
            tableModelDefine.setCatalogID(catalog.getID());
            this.nvwaDataModelUtil.insertTableModelDefine(tableModelDefine);
            this.nvwaDataModelUtil.addIndexToTable(tableID, new String[]{periodCol.getID(), unitCol.getID(), frmCol.getID()}, indexName, IndexModelType.NORMAL);
        } else {
            this.nvwaDataModelUtil.updateTableModelDefine(tableModelDefine);
            this.updateIndex(tableID, indexName, indexFields);
        }
        this.updateColumInfo(deployColumnCollection);
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableId(tableID);
        tableInfo.setTableName(tableCode);
        return tableInfo;
    }

    private TableInfo modelingCKSSub(TaskDefine taskDefine, FormSchemeDefine scheme) {
        String tableCode = CheckTableNameUtil.getCKSSubTableName(scheme.getFormSchemeCode());
        DesignTableModelDefine tableModelDefine = this.nvwaDataModelUtil.getDesTableByCode(tableCode);
        boolean doInsert = true;
        if (tableModelDefine == null) {
            tableModelDefine = this.nvwaDataModelUtil.createTable();
        } else {
            doInsert = false;
        }
        this.nvwaDataModelUtil.initTableModel(taskDefine, scheme, tableCode, tableModelDefine, "\u5ba1\u6838\u72b6\u6001\u8bb0\u5f55\u6269\u5c55\u5b50\u8868");
        String tableID = tableModelDefine.getID();
        DeployColumnCollection deployColumnCollection = new DeployColumnCollection(this.nvwaDataModelUtil.getAllDesColumnsMap(tableID));
        ColumnModelDefine recKeyCol = this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 40, "", tableID, "CKS_S_REC_KEY", null, deployColumnCollection);
        ColumnModelDefine checkTypeCol = this.nvwaDataModelUtil.initColumnModel(ColumnModelType.INTEGER, 10, "", tableID, "CKS_S_CK_TYPE", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.INTEGER, 10, "", tableID, "CKS_S_ERR_COUNT", null, deployColumnCollection);
        tableModelDefine.setKeys(recKeyCol.getID() + ";" + checkTypeCol.getID() + ";");
        tableModelDefine.setBizKeys(recKeyCol.getID() + ";" + checkTypeCol.getID() + ";");
        if (doInsert) {
            DesignCatalogModelDefine catalog = this.nvwaDataModelUtil.createCatalog();
            tableModelDefine.setCatalogID(catalog.getID());
            this.nvwaDataModelUtil.insertTableModelDefine(tableModelDefine);
        } else {
            this.nvwaDataModelUtil.updateTableModelDefine(tableModelDefine);
        }
        this.updateColumInfo(deployColumnCollection);
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableId(tableID);
        tableInfo.setTableName(tableCode);
        return tableInfo;
    }

    private void updateColumInfo(DeployColumnCollection deployColumnCollection) {
        deployColumnCollection.finishSetting();
        if (deployColumnCollection.needCre()) {
            this.nvwaDataModelUtil.insertColumns(deployColumnCollection.getCreColumns());
        }
        if (deployColumnCollection.needMod()) {
            this.nvwaDataModelUtil.updateColumns(deployColumnCollection.getModColumns());
        }
        if (deployColumnCollection.needDel()) {
            this.nvwaDataModelUtil.deleteColumns(deployColumnCollection.getDelColumns());
        }
    }

    private void appendALLCKRDdl(TaskDefine taskDefine, FormSchemeDefine scheme, List<String> result) {
        try {
            String tableId = this.modelingALLCKR(taskDefine, scheme).getTableId();
            List ddl = this.dataModelDeployService.getDeployTableSqls(tableId);
            if (!CollectionUtils.isEmpty(ddl)) {
                result.addAll(ddl);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void appendCKRDdl(TaskDefine taskDefine, FormSchemeDefine scheme, List<String> result) {
        try {
            String tableId = this.modelingCKR(taskDefine, scheme).getTableId();
            List ddl = this.dataModelDeployService.getDeployTableSqls(tableId);
            if (!CollectionUtils.isEmpty(ddl)) {
                result.addAll(ddl);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void appendCKDDdl(TaskDefine taskDefine, FormSchemeDefine scheme, List<String> result) {
        try {
            String tableId = this.modelingCKD(taskDefine, scheme).getTableId();
            List ddl = this.dataModelDeployService.getDeployTableSqls(tableId);
            if (!CollectionUtils.isEmpty(ddl)) {
                result.addAll(ddl);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void appendCKSDdl(TaskDefine taskDefine, FormSchemeDefine scheme, List<String> result) {
        try {
            String tableId = this.modelingCKS(taskDefine, scheme).getTableId();
            List ddl = this.dataModelDeployService.getDeployTableSqls(tableId);
            if (!CollectionUtils.isEmpty(ddl)) {
                result.addAll(ddl);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void appendCKSSubDdl(TaskDefine taskDefine, FormSchemeDefine scheme, List<String> result) {
        try {
            String tableId = this.modelingCKSSub(taskDefine, scheme).getTableId();
            List ddl = this.dataModelDeployService.getDeployTableSqls(tableId);
            if (!CollectionUtils.isEmpty(ddl)) {
                result.addAll(ddl);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

