/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 */
package com.jiuqi.nr.data.estimation.sub.database.intf.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.data.estimation.sub.database.common.SubDatabaseLogger;
import com.jiuqi.nr.data.estimation.sub.database.common.SubDatabaseUtils;
import com.jiuqi.nr.data.estimation.sub.database.entity.DataSchemeSubDatabaseDefine;
import com.jiuqi.nr.data.estimation.sub.database.entity.IDeployTableInfo;
import com.jiuqi.nr.data.estimation.sub.database.intf.DSSubDatabaseExecutor;
import com.jiuqi.nr.data.estimation.sub.database.intf.ICopiedTableGenerator;
import com.jiuqi.nr.data.estimation.sub.database.intf.IMakeSubDatabaseParam;
import com.jiuqi.nr.data.estimation.sub.database.intf.IOriginalDesTableModelDefine;
import com.jiuqi.nr.data.estimation.sub.database.intf.impl.DataSchemeSubDatabaseHelper;
import com.jiuqi.nr.data.estimation.sub.database.intf.impl.RemoveSubDatabaseExecutor;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CreateSubDatabaseExecutor
implements DSSubDatabaseExecutor {
    protected String dataSchemeKey;
    protected DataSchemeSubDatabaseHelper helper;

    public CreateSubDatabaseExecutor(DataSchemeSubDatabaseHelper helper, String dataSchemeKey) {
        this.helper = helper;
        this.dataSchemeKey = dataSchemeKey;
    }

    @Override
    public void execute(IMakeSubDatabaseParam makePara, SubDatabaseLogger logger) {
        double process = 1.0;
        logger.logInfo(">>>>>>>\u5f00\u59cb");
        DataScheme dataScheme = this.helper.runtimeDataSchemeService.getDataScheme(this.dataSchemeKey);
        if (dataScheme == null) {
            logger.logError("\u65e0\u6548\u7684\u6570\u636e\u65b9\u6848\uff1a[" + this.dataSchemeKey + "]");
            logger.logError("\u5206\u5e93\u521b\u5efa\u5931\u8d25\uff01");
            logger.logInfo(">>>>>>>\u7ed3\u675f", process);
            return;
        }
        String databaseCode = makePara.getSubDatabaseCode();
        if (!SubDatabaseUtils.validateDatabaseCode(databaseCode) || StringUtils.isEmpty((String)databaseCode)) {
            logger.logError("\u65e0\u6548\u7684\u5206\u5e93\u6807\u8bc6\uff1a[" + databaseCode + "]");
            logger.logError("\u5206\u5e93\u6807\u8bc6\uff1a\u53ea\u80fd\u7531\u5927\u5199\u5b57\u6bcd\u548c\u4e0b\u5212\u7ebf\u7ec4\u6210");
            logger.logError("\u5206\u5e93\u6807\u8bc6\uff1a\u53ea\u80fd\u4ee5\u4e0b\u5212\u7ebf\u5f00\u59cb\uff0c\u4ee5\u4e0b\u5212\u7ebf\u7ed3\u5c3e");
            logger.logError("\u5206\u5e93\u6807\u8bc6\uff1a\u4e0d\u80fd\u8d85\u8fc75\u4f4d");
            logger.logError("\u5206\u5e93\u521b\u5efa\u5931\u8d25\uff01");
            logger.logInfo(">>>>>>>\u7ed3\u675f", process);
            return;
        }
        DataSchemeSubDatabaseDefine record = this.helper.subDatabaseDao.findRecord(dataScheme.getKey(), makePara.getSubDatabaseCode());
        if (record != null) {
            logger.logInfo("\u5206\u5e93\u5df2\u5b58\u5728\uff0c\u65e0\u9700\u518d\u6b21\u521b\u5efa\uff01");
            logger.logInfo(">>>>>>>\u7ed3\u675f", process);
        }
        List<IDeployTableInfo> deployTables = this.getDeployTables(makePara, logger, process / 4.0);
        try {
            this.createSubDatabase(dataScheme, makePara, deployTables, logger, process * 3.0 / 4.0);
            DataSchemeSubDatabaseDefine databaseDefine = new DataSchemeSubDatabaseDefine();
            databaseDefine.setDataSchemeKey(dataScheme.getKey());
            databaseDefine.setDatabaseCode(databaseCode);
            databaseDefine.setDatabaseTitle(makePara.getSubDatabaseTitle(this.dataSchemeKey));
            this.helper.subDatabaseDao.insertRecord(databaseDefine);
        }
        catch (Exception e) {
            logger.logError("\u521b\u5efa\u5206\u5e93\u5931\u8d25!!!!");
            logger.logError("\u5f02\u5e38\u4fe1\u606f\uff1a" + e.getMessage());
            logger.logInfo(">>>>>>>\u5f00\u59cb\u56de\u6eda");
            RemoveSubDatabaseExecutor executor = new RemoveSubDatabaseExecutor(this.helper, this.dataSchemeKey, true);
            executor.execute(makePara, logger);
        }
        logger.logInfo(">>>>>>>\u7ed3\u675f");
    }

    private void createSubDatabase(DataScheme dataScheme, IMakeSubDatabaseParam makePara, List<IDeployTableInfo> deployTables, SubDatabaseLogger logger, double process) throws Exception {
        logger.logInfo("\u6b63\u5728\u521b\u5efa\u5206\u5e93...");
        String databaseCode = makePara.getSubDatabaseCode();
        logger.logInfo("\u6570\u636e\u65b9\u6848\uff1a" + dataScheme.getTitle() + "[" + dataScheme.getCode() + "]");
        logger.logInfo("\u5206\u5e93\u6807\u8bc6\uff1a" + databaseCode);
        for (IDeployTableInfo tableInfo : deployTables) {
            logger.logInfo("----------------------------------------");
            logger.logInfo("\u6b63\u5728\u590d\u5236\uff1a" + tableInfo.getOriTableModelTitle() + "[" + tableInfo.getOriTableModelCode() + "] ");
            logger.logInfo("\u5206\u5e93\u7684\u8868\uff1a" + tableInfo.getNewTableModel().getTitle() + "[" + tableInfo.getNewTableModel().getCode() + "]");
            this.insertTableIndex(tableInfo.getNewTableIndexes());
            this.helper.designDataModelService.insertColumnModelDefines(tableInfo.getNewTableColumns().toArray(new DesignColumnModelDefine[0]));
            this.helper.designDataModelService.insertTableModelDefine(tableInfo.getNewTableModel());
            this.helper.dataModelDeployService.deployTable(tableInfo.getNewTableModel().getID());
            logger.addProcess(process / (double)deployTables.size());
        }
    }

    private void insertTableIndex(List<DesignIndexModelDefine> tableIndexes) {
        for (DesignIndexModelDefine tableIndex : tableIndexes) {
            tableIndex.setID(UUIDUtils.getKey());
            this.helper.designIndexModelDao.insert((Object)tableIndex);
        }
    }

    private List<IDeployTableInfo> getDeployTables(IMakeSubDatabaseParam makePara, SubDatabaseLogger logger, double process) {
        ArrayList<IDeployTableInfo> copyTables = new ArrayList<IDeployTableInfo>();
        List<IOriginalDesTableModelDefine> tableInfos = this.helper.getDesignTableModelsByDataScheme(this.dataSchemeKey);
        for (IOriginalDesTableModelDefine tableInfo : tableInfos) {
            DataTableType dataTableType = tableInfo.getDataTableType();
            DesignTableModelDefine oriTableModel = tableInfo.getDesTableModelDefine();
            IDeployTableInfo impl = new IDeployTableInfo(oriTableModel.getID(), oriTableModel.getCode(), oriTableModel.getTitle());
            impl.setNewTableModel(this.madeNewTableModel(oriTableModel, makePara));
            impl.setNewTableColumns(this.madeNewTableColumns(oriTableModel, makePara, impl, dataTableType));
            copyTables.add(impl);
            logger.addProcess(process / (double)tableInfos.size());
        }
        return copyTables;
    }

    private DesignTableModelDefine madeNewTableModel(DesignTableModelDefine tableModelDefine, IMakeSubDatabaseParam makePara) {
        ICopiedTableGenerator tableGenerator = makePara.getCopiedTableGenerator(this.dataSchemeKey);
        tableModelDefine.setID(tableGenerator.madeCopiedId(tableModelDefine.getID()));
        tableModelDefine.setCode(tableGenerator.madeCopiedTableCode(tableModelDefine.getCode()));
        tableModelDefine.setTitle(tableGenerator.madeCopiedTitle(tableModelDefine.getTitle()));
        tableModelDefine.setName(tableModelDefine.getCode());
        return tableModelDefine;
    }

    private List<DesignColumnModelDefine> madeNewTableColumns(DesignTableModelDefine newTableModelDefine, IMakeSubDatabaseParam makePara, IDeployTableInfo deployTable, DataTableType dataTableType) {
        ICopiedTableGenerator tableGenerator = makePara.getCopiedTableGenerator(this.dataSchemeKey);
        HashMap<String, String> oriFieldKey2NewFieldKey = new HashMap<String, String>();
        List columnModelDefines = this.helper.designDataModelService.getColumnModelDefinesByTable(deployTable.getOriTableModelId());
        columnModelDefines.forEach(col -> {
            oriFieldKey2NewFieldKey.put(col.getID(), tableGenerator.madeCopiedId(col.getID()));
            col.setID((String)oriFieldKey2NewFieldKey.get(col.getID()));
            col.setTableID(newTableModelDefine.getID());
            col.setCode(tableGenerator.madeCopiedColumnCode(col.getCode()));
        });
        ArrayList<DesignColumnModelDefine> otherPrimaryColumns = new ArrayList<DesignColumnModelDefine>();
        for (DesignColumnModelDefine otherCol : makePara.getOtherPrimaryColumns(this.dataSchemeKey)) {
            DesignColumnModelDefine newCol = this.newDesignColumnModel(otherCol);
            newCol.setID(tableGenerator.madeCopiedId(UUIDUtils.getKey()));
            newCol.setTableID(newTableModelDefine.getID());
            columnModelDefines.add(newCol);
            otherPrimaryColumns.add(newCol);
        }
        String[] otherPrimaryColKeys = (String[])otherPrimaryColumns.stream().map(IModelDefineItem::getID).distinct().toArray(String[]::new);
        if (dataTableType == DataTableType.TABLE) {
            newTableModelDefine.setKeys(this.mergeColumnKeys(otherPrimaryColKeys, this.madeGroupFieldIds(newTableModelDefine.getKeys(), oriFieldKey2NewFieldKey)));
        } else {
            newTableModelDefine.setKeys(this.madeGroupFieldIds(newTableModelDefine.getKeys(), oriFieldKey2NewFieldKey));
        }
        newTableModelDefine.setBizKeys(this.mergeColumnKeys(otherPrimaryColKeys, this.madeGroupFieldIds(newTableModelDefine.getBizKeys(), oriFieldKey2NewFieldKey)));
        deployTable.setNewTableIndexes(this.madeNewTableIndexes(deployTable, newTableModelDefine, makePara, oriFieldKey2NewFieldKey, otherPrimaryColKeys));
        return columnModelDefines;
    }

    private String mergeColumnKeys(String[] otherPrimaryFieldKeys, String fieldKeys) {
        return String.join((CharSequence)";", SubDatabaseUtils.concatArrays(otherPrimaryFieldKeys, new String[][]{fieldKeys.split(";")}));
    }

    private List<DesignIndexModelDefine> madeNewTableIndexes(IDeployTableInfo deployTable, DesignTableModelDefine newTableModelDefine, IMakeSubDatabaseParam makePara, Map<String, String> oriFieldKey2NewFieldKey, String[] otherPrimaryColKeys) {
        ICopiedTableGenerator tableGenerator = makePara.getCopiedTableGenerator(this.dataSchemeKey);
        HashMap ori2NewIndexMap = new HashMap();
        List oriTableIndexes = this.helper.designDataModelService.getIndexsByTable(deployTable.getOriTableModelId());
        oriTableIndexes.forEach(tableIndex -> {
            String newIndexName = (String)ori2NewIndexMap.get(tableIndex.getName());
            if (StringUtils.isEmpty((String)newIndexName)) {
                newIndexName = tableGenerator.madeCopiedTableIndexName(tableIndex.getName());
                ori2NewIndexMap.put(tableIndex.getName(), newIndexName);
            }
            tableIndex.setTableID(newTableModelDefine.getID());
            tableIndex.setFieldIDs(this.mergeColumnKeys(otherPrimaryColKeys, this.madeGroupFieldIds(tableIndex.getFieldIDs(), oriFieldKey2NewFieldKey)));
            tableIndex.setName(newIndexName);
        });
        return oriTableIndexes;
    }

    private String madeGroupFieldIds(String oriFieldIds, Map<String, String> oriFieldKey2NewFieldKey) {
        return Arrays.stream(oriFieldIds.split(";")).map(oriFieldKey2NewFieldKey::get).collect(Collectors.joining(";"));
    }

    private DesignColumnModelDefine newDesignColumnModel(DesignColumnModelDefine oriCol) {
        DesignColumnModelDefine newCol = this.helper.designDataModelService.createColumnModelDefine();
        newCol.setID(oriCol.getID());
        newCol.setTableID(oriCol.getTableID());
        newCol.setCode(oriCol.getCode());
        newCol.setName(oriCol.getName());
        newCol.setTitle(oriCol.getTitle());
        newCol.setDesc(oriCol.getDesc());
        newCol.setCatagory(oriCol.getCatagory());
        newCol.setColumnType(oriCol.getColumnType());
        newCol.setPrecision(oriCol.getPrecision());
        newCol.setDecimal(oriCol.getDecimal());
        newCol.setNullAble(oriCol.isNullAble());
        newCol.setDefaultValue(oriCol.getDefaultValue());
        newCol.setReferTableID(oriCol.getReferTableID());
        newCol.setReferColumnID(oriCol.getReferColumnID());
        newCol.setFilter(oriCol.getFilter());
        newCol.setMultival(oriCol.isMultival());
        newCol.setAggrType(oriCol.getAggrType());
        newCol.setApplyType(oriCol.getApplyType());
        newCol.setShowFormat(oriCol.getShowFormat());
        newCol.setMeasureUnit(oriCol.getMeasureUnit());
        newCol.setKind(oriCol.getKind());
        newCol.setOrder(oriCol.getOrder());
        return newCol;
    }
}

