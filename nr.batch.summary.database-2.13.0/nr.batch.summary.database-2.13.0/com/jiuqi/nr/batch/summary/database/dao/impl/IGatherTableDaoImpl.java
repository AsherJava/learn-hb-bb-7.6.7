/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.batch.summary.database.dao.impl;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.batch.summary.database.dao.IGatherTableDao;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IGatherTableDaoImpl
implements IGatherTableDao {
    private static final Logger logger = LoggerFactory.getLogger(IGatherTableDaoImpl.class);
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;

    @Override
    public void doCopyTableAndAddCol(Set<String> tableModelIDs, String subDataBaseCode) throws Exception {
        DesignColumnModelDefine gatherColumnModel = this.designDataModelService.createColumnModelDefine();
        gatherColumnModel.setCode("GATHER_SCHEME_CODE");
        gatherColumnModel.setTitle("\u6c47\u603b\u65b9\u6848\u6807\u8bc6");
        gatherColumnModel.setColumnType(ColumnModelType.STRING);
        gatherColumnModel.setPrecision(50);
        for (String tableModelID : tableModelIDs) {
            ArrayList<DesignColumnModelDefine> designColumnModelDefines = new ArrayList<DesignColumnModelDefine>();
            List columnModelDefines = this.dataModelService.getColumnModelDefinesByTable(tableModelID);
            String newTableID = tableModelID + "_" + subDataBaseCode;
            columnModelDefines.forEach(a -> {
                DesignColumnModelDefine columnModelDefine = this.designDataModelService.createColumnModelDefine();
                BeanUtils.copyProperties(a, columnModelDefine);
                columnModelDefine.setTableID(newTableID);
                String newColModelID = a.getID() + "_" + subDataBaseCode;
                columnModelDefine.setID(newColModelID);
                designColumnModelDefines.add(columnModelDefine);
            });
            gatherColumnModel.setID(UUIDUtils.getKey() + "_" + subDataBaseCode);
            gatherColumnModel.setTableID(newTableID);
            designColumnModelDefines.add(gatherColumnModel);
            this.designDataModelService.insertColumnModelDefines(designColumnModelDefines.toArray(new DesignColumnModelDefine[0]));
            DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefine(tableModelID);
            String newTableTitle = tableModelDefine.getTitle() + "_\u6c47\u603b\u5e93";
            String newTableCode = this.getRealName(tableModelDefine.getCode(), subDataBaseCode);
            String newTableName = this.getRealName(tableModelDefine.getName(), subDataBaseCode);
            String newKeys = this.getNewKeys(tableModelDefine.getKeys(), subDataBaseCode) + ";" + gatherColumnModel.getID();
            String newBizKeys = this.getNewKeys(tableModelDefine.getBizKeys(), subDataBaseCode);
            tableModelDefine.setKeys(newKeys);
            tableModelDefine.setBizKeys(newBizKeys);
            tableModelDefine.setID(newTableID);
            tableModelDefine.setTitle(newTableTitle);
            tableModelDefine.setCode(newTableCode);
            tableModelDefine.setName(newTableName);
            this.designDataModelService.insertTableModelDefine(tableModelDefine);
            List indexsByTable = this.designDataModelService.getIndexsByTable(tableModelID);
            indexsByTable.forEach(a -> {
                String newIndexName = this.getNewIndexName(a.getName());
                this.designDataModelService.addIndexToTable(newTableID, this.getNewKeys(a.getFieldIDs(), subDataBaseCode).split(";"), newIndexName, a.getType());
            });
            this.dataModelDeployService.deployTable(newTableID);
        }
    }

    @Override
    public void doCopyCheckTableAndAddCol(Set<String> tableNames, String subDataBaseCode) throws Exception {
        Set<String> checkTableIDs = this.getCheckTableIDs(tableNames);
        this.doCopyTableAndAddCol(checkTableIDs, subDataBaseCode);
    }

    @Override
    public void deleteCopyTable(Set<String> tableModelIDs, String subDataBaseCode, boolean forceDelete) throws Exception {
        String[] gatherTableIDs;
        for (String gatherTableID : gatherTableIDs = (String[])tableModelIDs.stream().map(a -> {
            a = a + "_" + subDataBaseCode;
            return a;
        }).toArray(String[]::new)) {
            this.designDataModelService.deleteColumnModelDefineByTable(gatherTableID);
        }
        this.designDataModelService.deleteTableModelDefines(gatherTableIDs);
        if (forceDelete) {
            for (String tableId : gatherTableIDs) {
                LogHelper.info((String)"\u6279\u91cf\u6c47\u603b\u5206\u5e93", (String)"\u5355\u673a\u7248\u6c47\u603b\u5206\u5e93\u64cd\u4f5c", (String)("\u5220\u9664 " + tableId + " \u5b58\u50a8\u8868"));
                this.dataModelDeployService.deployTableUnCheck(tableId);
            }
        } else {
            this.dataModelDeployService.deployTables(gatherTableIDs);
        }
    }

    @Override
    public void deleteCopyCheckTable(Set<String> tableNames, String subDataBaseCode, boolean forceDelete) throws Exception {
        Set<String> checkTableIDs = this.getCheckTableIDs(tableNames);
        this.deleteCopyTable(checkTableIDs, subDataBaseCode, forceDelete);
    }

    @Override
    public String getRealName(String name, String code) {
        return code + name + "_G_";
    }

    private Set<String> getCheckTableIDs(Set<String> tableNames) {
        HashSet<DesignTableModelDefine> tableModelDefines = new HashSet<DesignTableModelDefine>();
        for (String tableName : tableNames) {
            DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefineByCode(tableName);
            if (tableModelDefine == null) continue;
            tableModelDefines.add(tableModelDefine);
        }
        return tableModelDefines.stream().map(IModelDefineItem::getID).collect(Collectors.toSet());
    }

    private String getCheckNewName(String name, String code) {
        return name + "_G_";
    }

    public String getNewKeys(String keys, String subDataBaseCode) {
        String[] split = keys.split(";");
        return Arrays.stream(split).map(a -> {
            a = a + "_" + subDataBaseCode;
            return a;
        }).collect(Collectors.joining(";"));
    }

    public String getNewIndexName(String indexName) {
        if (indexName.indexOf("SYS_ALLCKR_") > 0) {
            return indexName.replace("SYS", "G");
        }
        return indexName + "_G_";
    }
}

