/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.logic.facade.event.CheckTableDeployEvent
 *  com.jiuqi.nr.data.logic.facade.event.CheckTableDeploySource
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.internal.service.impl.RuntimeDataSchemeServiceImpl
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.batch.summary.database.listener;

import com.jiuqi.nr.batch.summary.database.service.impl.GatherDataBaseServiceImpl;
import com.jiuqi.nr.data.logic.facade.event.CheckTableDeployEvent;
import com.jiuqi.nr.data.logic.facade.event.CheckTableDeploySource;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.internal.service.impl.RuntimeDataSchemeServiceImpl;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CheckTableDeployListener
implements ApplicationListener<CheckTableDeployEvent> {
    private static final Logger logger = LoggerFactory.getLogger(CheckTableDeployListener.class);
    @Autowired
    private GatherDataBaseServiceImpl gatherDataBaseService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private RuntimeDataSchemeServiceImpl runtimeDataSchemeService;

    @Override
    public void onApplicationEvent(CheckTableDeployEvent event) {
        CheckTableDeploySource source = event.getSource();
        TaskDefine task = source.getTask();
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(task.getDataScheme());
        if (!dataScheme.getGatherDB().booleanValue()) {
            return;
        }
        FormSchemeDefine formScheme = source.getFormScheme();
        Set<String> checkTablesName = this.gatherDataBaseService.getCheckTablesByFS(formScheme.getFormSchemeCode());
        HashSet<DesignTableModelDefine> tableModelDefines = new HashSet<DesignTableModelDefine>();
        HashSet<DesignTableModelDefine> gatherTableModelDefines = new HashSet<DesignTableModelDefine>();
        for (String checkTableName : checkTablesName) {
            DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefineByCode(checkTableName);
            DesignTableModelDefine gatherTableModelDefine = this.designDataModelService.getTableModelDefineByCode(checkTableName + "_G_");
            if (tableModelDefine != null) {
                tableModelDefines.add(tableModelDefine);
            }
            if (gatherTableModelDefine == null) continue;
            gatherTableModelDefines.add(gatherTableModelDefine);
        }
        try {
            if (CollectionUtils.isEmpty(gatherTableModelDefines)) {
                Set checkTableIDs = tableModelDefines.stream().map(IModelDefineItem::getID).collect(Collectors.toSet());
                this.gatherDataBaseService.createAndDeploy(checkTableIDs);
            } else {
                for (DesignTableModelDefine tableModelDefine : tableModelDefines) {
                    List columnModelDefines = this.designDataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
                    List gatherColumnModelDefines = this.designDataModelService.getColumnModelDefinesByTable(tableModelDefine.getID() + "_g_");
                    List<DesignColumnModelDefine> deleteCol = this.getDelList(columnModelDefines, gatherColumnModelDefines);
                    List<DesignColumnModelDefine> addList = this.getAddList(columnModelDefines, gatherColumnModelDefines);
                    this.designDataModelService.deleteColumnModelDefines((String[])deleteCol.stream().map(IModelDefineItem::getID).toArray(String[]::new));
                    this.setGatherColumn(columnModelDefines);
                    this.designDataModelService.updateColumnModelDefines(columnModelDefines.toArray(new DesignColumnModelDefine[0]));
                    this.designDataModelService.insertColumnModelDefines(addList.toArray(new DesignColumnModelDefine[0]));
                    this.designDataModelService.deleteIndexsByTable(tableModelDefine.getID() + "_g_");
                    List indexsByTable = this.designDataModelService.getIndexsByTable(tableModelDefine.getID());
                    this.addGatherIndexDefines(indexsByTable);
                    this.setGatherTable(tableModelDefine);
                    this.designDataModelService.updateTableModelDefine(tableModelDefine);
                    this.dataModelDeployService.deployTable(tableModelDefine.getID());
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void setGatherTable(DesignTableModelDefine tableModelDefine) {
        String newTableID = tableModelDefine.getID() + "_g_";
        String newTableTitle = tableModelDefine.getTitle() + "_\u6c47\u603b\u5e93";
        String newTableCode = tableModelDefine.getCode() + "_G_";
        String newTableName = tableModelDefine.getName() + "_G_";
        String newKeys = this.gatherDataBaseService.getNewKeys(tableModelDefine.getKeys());
        String newBizKeys = this.gatherDataBaseService.getNewKeys(tableModelDefine.getBizKeys());
        tableModelDefine.setKeys(newKeys);
        tableModelDefine.setBizKeys(newBizKeys);
        tableModelDefine.setID(newTableID);
        tableModelDefine.setTitle(newTableTitle);
        tableModelDefine.setCode(newTableCode);
        tableModelDefine.setName(newTableName);
    }

    private void setGatherColumn(List<DesignColumnModelDefine> columnModelDefines) {
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

    private List<DesignColumnModelDefine> getDelList(List<DesignColumnModelDefine> columnModelDefines, List<DesignColumnModelDefine> gatherColumnModelDefines) {
        ArrayList<DesignColumnModelDefine> deleteCol = new ArrayList<DesignColumnModelDefine>();
        List collect = gatherColumnModelDefines.stream().filter(a -> !a.getCode().equals("GATHER_SCHEME_CODE")).collect(Collectors.toList());
        Map<String, DesignColumnModelDefine> collect1 = columnModelDefines.stream().collect(Collectors.toMap(IModelDefineItem::getCode, a -> a));
        for (DesignColumnModelDefine columnModelDefine : collect) {
            if (collect1.get(columnModelDefine.getCode()) != null) continue;
            deleteCol.add(columnModelDefine);
        }
        return deleteCol;
    }

    private List<DesignColumnModelDefine> getAddList(List<DesignColumnModelDefine> columnModelDefines, List<DesignColumnModelDefine> gatherColumnModelDefines) {
        ArrayList<DesignColumnModelDefine> addCol = new ArrayList<DesignColumnModelDefine>();
        Map<String, DesignColumnModelDefine> collect = gatherColumnModelDefines.stream().collect(Collectors.toMap(IModelDefineItem::getCode, a -> a));
        for (DesignColumnModelDefine columnModelDefine : columnModelDefines) {
            if (collect.get(columnModelDefine.getCode()) != null) continue;
            addCol.add(columnModelDefine);
        }
        return addCol;
    }

    private void addGatherIndexDefines(List<DesignIndexModelDefine> indexDefines) {
        if (CollectionUtils.isEmpty(indexDefines)) {
            return;
        }
        for (DesignIndexModelDefine indexModelDefine : indexDefines) {
            String newIndexName = this.gatherDataBaseService.getNewIndexName(indexModelDefine.getName());
            String newFieldIDs = this.gatherDataBaseService.getNewKeys(indexModelDefine.getFieldIDs());
            this.designDataModelService.addIndexToTable(indexModelDefine.getTableID() + "_g_", newFieldIDs.split(";"), newIndexName, indexModelDefine.getType());
        }
    }
}

