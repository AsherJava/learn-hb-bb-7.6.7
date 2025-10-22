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
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.datastatus.internal.model;

import com.jiuqi.nr.datastatus.internal.model.DeployColumnCollection;
import com.jiuqi.nr.datastatus.internal.util.NvwaDataModelUtil;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
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
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class TableDeployService {
    @Autowired
    private NvwaDataModelUtil nvwaDataModelUtil;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private DesignDataModelService designDataModelService;
    private static final Logger logger = LoggerFactory.getLogger(TableDeployService.class);

    public void deployTables(TaskDefine task, FormSchemeDefine formScheme) throws Exception {
        this.nvwaDataModelUtil.deployTableUnCheck(this.modelingDAST(task, formScheme));
    }

    public void deleteTables(String formSchemeCode) {
        this.nvwaDataModelUtil.deployDeleteTableByCode("DE_DAST_" + formSchemeCode);
    }

    public List<String> getPreDeployDdl(TaskDefine task, FormSchemeDefine formScheme) {
        ArrayList<String> result = new ArrayList<String>();
        this.appendDASTDdl(task, formScheme, result);
        return result;
    }

    public List<String> getRegisterTableId(String formSchemeCode) {
        ArrayList<String> result = new ArrayList<String>();
        result.add(this.designDataModelService.getTableModelDefineByCode("DE_DAST_" + formSchemeCode).getID());
        return result;
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

    public String modelingDAST(TaskDefine taskDefine, FormSchemeDefine scheme) throws Exception {
        String tableCode = "DE_DAST_" + scheme.getFormSchemeCode();
        DesignTableModelDefine tableModelDefine = this.nvwaDataModelUtil.getDesTableByCode(tableCode);
        boolean doInsert = true;
        if (tableModelDefine == null) {
            tableModelDefine = this.nvwaDataModelUtil.createTable();
        } else {
            doInsert = false;
        }
        this.nvwaDataModelUtil.initTableModel(taskDefine, scheme, tableCode, tableModelDefine, "\u62a5\u8868\u6570\u636e\u72b6\u6001\u8868");
        String tableID = tableModelDefine.getID();
        DeployColumnCollection deployColumnCollection = new DeployColumnCollection(this.nvwaDataModelUtil.getAllDesColumnsMap(tableID));
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.INTEGER, 1, "0", tableID, "DAST_ISENTRY", null, deployColumnCollection);
        ColumnModelDefine formCol = this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 50, "-", tableID, "DAST_FORMKEY", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 50, "", tableID, "DAST_FORMSCHEMEKEY", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 50, "", tableID, "DAST_RECID", null, deployColumnCollection);
        String masterEntityKeys = this.nvwaDataModelUtil.initEntityColumns(taskDefine, scheme, tableID, deployColumnCollection);
        String keys = masterEntityKeys + formCol.getID() + ";";
        tableModelDefine.setKeys(keys);
        tableModelDefine.setBizKeys(keys);
        if (doInsert) {
            DesignCatalogModelDefine catalog = this.nvwaDataModelUtil.createCatalog();
            tableModelDefine.setCatalogID(catalog.getID());
            this.nvwaDataModelUtil.insertTableModelDefine(tableModelDefine);
        } else {
            this.nvwaDataModelUtil.updateTableModelDefine(tableModelDefine);
        }
        this.updateColumInfo(deployColumnCollection);
        return tableID;
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

    private void appendDASTDdl(TaskDefine taskDefine, FormSchemeDefine scheme, List<String> result) {
        try {
            String tableId = this.modelingDAST(taskDefine, scheme);
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

