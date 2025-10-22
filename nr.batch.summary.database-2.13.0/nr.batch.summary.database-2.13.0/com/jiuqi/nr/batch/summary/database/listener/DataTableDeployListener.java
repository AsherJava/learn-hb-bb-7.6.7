/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.event.DataTableDeployEvent
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.IndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModel
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.batch.summary.database.listener;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.batch.summary.database.dao.GatherDataBaseDao;
import com.jiuqi.nr.batch.summary.database.service.impl.GatherDataBaseServiceImpl;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.event.DataTableDeployEvent;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.IndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModel;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import com.jiuqi.util.StringUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DataTableDeployListener
implements ApplicationListener<DataTableDeployEvent> {
    private static final Logger logger = LoggerFactory.getLogger(DataTableDeployListener.class);
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private GatherDataBaseServiceImpl gatherDataBaseService;
    @Autowired
    private GatherDataBaseDao gatherDataBaseDao;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;

    @Override
    public void onApplicationEvent(DataTableDeployEvent event) {
        String dataSchemeKey = event.getSource().getDataSchemeKey();
        DesignDataScheme designDataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        if (designDataScheme == null) {
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
            if (dataScheme == null) {
                return;
            }
            if (!dataScheme.getGatherDB().booleanValue()) {
                return;
            }
        } else if (!designDataScheme.getGatherDB().booleanValue()) {
            return;
        }
        if (!this.gatherDataBaseService.isExistGatherDataBase(dataSchemeKey)) {
            return;
        }
        DesignTableModel tableModel = event.getSource().getTableModel();
        String tableModelKey = event.getSource().getTableModelKey();
        logger.info("\u6c47\u603b\u5206\u5e93\u5b58\u50a8\u8868\u7ef4\u62a4\u5f00\u59cb\u3002");
        if (tableModel == null) {
            HashSet<String> deleteSet = new HashSet<String>();
            deleteSet.add(tableModelKey);
            try {
                this.gatherDataBaseService.deleteAndDeploy(deleteSet, true);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            DesignTableModelDefine tableModelDefine = tableModel.getTableModelDefine();
            String gatherTableModelKey = tableModelKey + "_g_";
            DesignTableModelDefine gatherTableModel = this.designDataModelService.getTableModelDefine(gatherTableModelKey);
            boolean doUpdate = gatherTableModel != null;
            try {
                if (doUpdate) {
                    String bizKeys = tableModelDefine.getBizKeys();
                    String newKeys = this.gatherDataBaseService.getNewKeys(bizKeys);
                    String oldKeys = gatherTableModel.getBizKeys();
                    if (!oldKeys.equals(newKeys)) {
                        this.gatherDataBaseDao.deleteTableData(gatherTableModel.getName());
                    }
                    this.update(tableModel, gatherTableModelKey);
                    List columnModelDefines = this.designDataModelService.getColumnModelDefinesByTable(tableModelDefine.getID() + "_g_");
                    List collect = columnModelDefines.stream().filter(a -> a.getCode().equals("GATHER_SCHEME_CODE")).collect(Collectors.toList());
                    this.setGatherTableDefines(tableModelDefine, ((DesignColumnModelDefine)collect.get(0)).getID());
                    this.designDataModelService.updateTableModelDefine(tableModelDefine);
                } else {
                    DesignColumnModelDefine gatherColumnModel = this.designDataModelService.createColumnModelDefine();
                    gatherColumnModel.setCode("GATHER_SCHEME_CODE");
                    gatherColumnModel.setTitle("\u6c47\u603b\u65b9\u6848\u6807\u8bc6");
                    gatherColumnModel.setColumnType(ColumnModelType.STRING);
                    gatherColumnModel.setPrecision(50);
                    gatherColumnModel.setID(UUIDUtils.getKey() + "_g_");
                    gatherColumnModel.setTableID(gatherTableModelKey);
                    this.designDataModelService.insertColumnModelDefine(gatherColumnModel);
                    this.setGatherTableDefines(tableModelDefine, gatherColumnModel.getID());
                    this.designDataModelService.insertTableModelDefine(tableModelDefine);
                }
                List newColumns = tableModel.getNewColumns();
                this.setGatherColumnDefines(newColumns);
                this.designDataModelService.insertColumnModelDefines(newColumns.toArray(new DesignColumnModelDefine[0]));
                this.addGatherIndexDefines(tableModel.getNewIndexes());
                this.dataModelDeployService.deployTable(gatherTableModelKey);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        logger.info("\u6c47\u603b\u5206\u5e93\u5b58\u50a8\u8868\u7ef4\u62a4\u7ed3\u675f\u3002");
    }

    private void update(DesignTableModel tableModel, String gatherTableModelKey) {
        DesignIndexModelDefine gatherIndexModel;
        List deleteColumns = tableModel.getDeleteColumns();
        List updateColumns = tableModel.getUpdateColumns();
        List updateIndexes = tableModel.getUpdateIndexes();
        List deleteIndexes = tableModel.getDeleteIndexes();
        this.setGatherColumnDefines(deleteColumns);
        this.designDataModelService.deleteColumnModelDefines((String[])deleteColumns.stream().map(IModelDefineItem::getID).toArray(String[]::new));
        this.setGatherColumnDefines(updateColumns);
        this.designDataModelService.updateColumnModelDefines(updateColumns.toArray(new DesignColumnModelDefine[0]));
        List gatherIndexs = this.designDataModelService.getIndexsByTable(gatherTableModelKey);
        Map<String, DesignIndexModelDefine> collect = gatherIndexs.stream().collect(Collectors.toMap(IndexModelDefine::getName, a -> a));
        for (DesignIndexModelDefine indexModelDefine : updateIndexes) {
            gatherIndexModel = collect.get(indexModelDefine.getName() + "_G_");
            if (gatherIndexModel == null) continue;
            indexModelDefine.setID(gatherIndexModel.getID());
            indexModelDefine.setName(gatherIndexModel.getName());
            indexModelDefine.setTableID(gatherIndexModel.getTableID());
            this.designDataModelService.updateIndexModelDefine(indexModelDefine);
        }
        for (DesignIndexModelDefine indexModelDefine : deleteIndexes) {
            gatherIndexModel = collect.get(indexModelDefine.getName() + "_G_");
            if (gatherIndexModel == null) continue;
            this.designDataModelService.deleteIndexModelDefine(gatherIndexModel.getID());
        }
    }

    private void setGatherTableDefines(DesignTableModelDefine tableModelDefine, String gatherColModelID) {
        if (tableModelDefine == null) {
            return;
        }
        String newTableID = tableModelDefine.getID() + "_g_";
        String newTableTitle = tableModelDefine.getTitle() + "_\u6c47\u603b\u5e93";
        String newTableCode = tableModelDefine.getCode() + "_G_";
        String newTableName = tableModelDefine.getName() + "_G_";
        String newKeys = this.gatherDataBaseService.getNewKeys(tableModelDefine.getKeys());
        if (StringUtils.isNotEmpty((String)gatherColModelID)) {
            newKeys = newKeys + ";" + gatherColModelID;
        }
        String newBizKeys = this.gatherDataBaseService.getNewKeys(tableModelDefine.getBizKeys());
        tableModelDefine.setKeys(newKeys);
        tableModelDefine.setBizKeys(newBizKeys);
        tableModelDefine.setID(newTableID);
        tableModelDefine.setTitle(newTableTitle);
        tableModelDefine.setCode(newTableCode);
        tableModelDefine.setName(newTableName);
    }

    private void setGatherColumnDefines(List<DesignColumnModelDefine> columnModelDefines) {
        if (CollectionUtils.isEmpty(columnModelDefines)) {
            return;
        }
        for (DesignColumnModelDefine columnModelDefine : columnModelDefines) {
            String newTableID = columnModelDefine.getTableID() + "_g_";
            String newColModelID = columnModelDefine.getID() + "_g_";
            columnModelDefine.setTableID(newTableID);
            columnModelDefine.setID(newColModelID);
        }
    }

    private void addGatherIndexDefines(List<DesignIndexModelDefine> indexDefines) {
        if (CollectionUtils.isEmpty(indexDefines)) {
            return;
        }
        for (DesignIndexModelDefine indexModelDefine : indexDefines) {
            String newIndexName = indexModelDefine.getName() + "_G_";
            String newFieldIDs = this.gatherDataBaseService.getNewKeys(indexModelDefine.getFieldIDs());
            this.designDataModelService.addIndexToTable(indexModelDefine.getTableID() + "_g_", newFieldIDs.split(";"), newIndexName, indexModelDefine.getType());
        }
    }
}

