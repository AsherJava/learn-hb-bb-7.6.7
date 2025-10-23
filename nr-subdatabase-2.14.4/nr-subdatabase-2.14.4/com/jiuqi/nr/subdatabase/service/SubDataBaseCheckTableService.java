/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.LogicPrimaryKey
 *  com.jiuqi.bi.database.metadata.LogicTable
 *  com.jiuqi.bi.database.statement.CreateTableStatement
 *  com.jiuqi.bi.database.statement.SqlStatement
 *  com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.subdatabase.service;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicPrimaryKey;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.database.statement.SqlStatement;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.subdatabase.controller.SubDataBaseController;
import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import com.jiuqi.nr.subdatabase.service.SubDataBaseService;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class SubDataBaseCheckTableService {
    private static final Logger logger = LoggerFactory.getLogger(SubDataBaseCheckTableService.class);
    @Autowired
    private SubDataBaseService subDataBaseService;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private RuntimeViewController runtimeViewController;
    @Autowired
    private SubDataBaseController subDataBaseController;

    public void createCheckTableWithModel(String taskKey, SubDataBase subDataBase) throws Exception {
        Assert.notNull((Object)taskKey, "taskKey must not be null.");
        Assert.notNull((Object)subDataBase, "subDataBase must not be null.");
        Set<String> checkTables = this.getCheckTableNames(taskKey);
        Set<String> checkTableIDs = this.getCheckTableIds(checkTables);
        if (!CollectionUtils.isEmpty(checkTableIDs)) {
            this.subDataBaseService.createAndDeploy(checkTableIDs, subDataBase.getCode(), subDataBase.getDataScheme());
        }
    }

    public void createCheckTableWithoutModel(SubDataBase subDataBase) throws Exception {
        String subDataBaseCode = subDataBase.getCode();
        String tableName = CheckTableNameUtil.getRWIFTableName();
        LogicTable table = this.subDataBaseService.getLogicTable(subDataBaseCode + tableName);
        if (table == null) {
            LogicTable logicTable = this.subDataBaseService.getLogicTable(tableName);
            List<LogicField> logicFields = this.subDataBaseService.getLogicFields(tableName);
            LogicPrimaryKey logicPrimaryKey = this.subDataBaseService.getLogicPrimaryKey(tableName);
            if (logicTable == null || logicFields == null || logicPrimaryKey == null) {
                logger.error(String.format("\u83b7\u53d6\u8868[%s]\u4fe1\u606f\u6709\u8bef\uff0c\u8bf7\u68c0\u67e5\u8be5\u8868\u662f\u5426\u5b8c\u6574", tableName));
                throw new RuntimeException(String.format("\u83b7\u53d6\u8868[%s]\u4fe1\u606f\u6709\u8bef\uff0c\u8bf7\u68c0\u67e5\u8be5\u8868\u662f\u5426\u5b8c\u6574", tableName));
            }
            CreateTableStatement createTableStatement = new CreateTableStatement(null, subDataBaseCode + tableName);
            logicFields.forEach(logicField -> createTableStatement.addColumn(logicField));
            createTableStatement.setPkName(logicPrimaryKey.getPkName() + subDataBaseCode);
            createTableStatement.getPrimaryKeys().addAll(logicPrimaryKey.getFieldNames());
            this.subDataBaseService.doCreateTable((SqlStatement)createTableStatement);
        }
    }

    public void deleteCheckTableWithModel(String taskKey, SubDataBase subDataBase) throws Exception {
        Set<String> checkTables = this.getCheckTableNames(taskKey);
        Set<String> checkTableIDs = this.getCheckTableIds(checkTables);
        if (!CollectionUtils.isEmpty(checkTableIDs)) {
            this.subDataBaseService.deleteAndDeploy(checkTableIDs, subDataBase.getCode(), true);
        }
    }

    public void deleteCheckTableWithoutModel(SubDataBase subDataBase) throws Exception {
        String subDataBaseCode = subDataBase.getCode();
        String tableName = CheckTableNameUtil.getRWIFTableName();
        LogicTable logicTable = this.subDataBaseService.getLogicTable(subDataBaseCode + tableName);
        if (logicTable != null && this.ifNeedDelete(subDataBaseCode)) {
            this.subDataBaseService.doDropTable(subDataBaseCode + tableName);
        } else {
            logger.error(String.format("\u8be5\u7269\u7406\u8868[%s]\u4e0d\u5b58\u5728", subDataBaseCode + tableName));
        }
    }

    private boolean ifNeedDelete(String subDataBaseCode) {
        List<SubDataBase> allDataBases = this.subDataBaseController.getAllSubDataBase();
        if (allDataBases.isEmpty()) {
            return true;
        }
        List allSubDataBaseCodes = allDataBases.stream().map(SubDataBase::getCode).collect(Collectors.toList());
        return !allSubDataBaseCodes.contains(subDataBaseCode);
    }

    private Set<String> getCheckTableIds(Set<String> checkTableNames) {
        HashSet<DesignTableModelDefine> tableModelDefines = new HashSet<DesignTableModelDefine>();
        for (String checkTableName : checkTableNames) {
            DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefineByCode(checkTableName);
            if (tableModelDefine == null) continue;
            tableModelDefines.add(tableModelDefine);
        }
        return tableModelDefines.stream().map(IModelDefineItem::getID).collect(Collectors.toSet());
    }

    public Set<String> getCheckTableNames(String taskKey) {
        List formSchemeDefines;
        HashSet<String> result = new HashSet<String>();
        try {
            formSchemeDefines = this.runtimeViewController.queryFormSchemeByTask(taskKey);
        }
        catch (Exception e) {
            throw new RuntimeException("\u83b7\u53d6\u4efb\u52a1\u4e0b\u62a5\u8868\u65b9\u6848\u5931\u8d25\uff01");
        }
        for (FormSchemeDefine define : formSchemeDefines) {
            Set<String> checkTablesByFS = this.getCheckTablesByFS(define.getFormSchemeCode());
            result.addAll(checkTablesByFS);
        }
        this.getCheckSystemTable(result);
        return result;
    }

    private Set<String> getCheckTablesByFS(String formSchemeCode) {
        HashSet<String> result = new HashSet<String>();
        result.add(CheckTableNameUtil.getCKDTableName((String)formSchemeCode));
        result.add(CheckTableNameUtil.getCKRTableName((String)formSchemeCode));
        result.add(CheckTableNameUtil.getAllCKRTableName((String)formSchemeCode));
        result.add(CheckTableNameUtil.getCKSTableName((String)formSchemeCode));
        result.add(CheckTableNameUtil.getCKSSubTableName((String)formSchemeCode));
        return result;
    }

    private void getCheckSystemTable(Set<String> checkTables) {
        checkTables.add(CheckTableNameUtil.getRWIFTableName());
    }
}

