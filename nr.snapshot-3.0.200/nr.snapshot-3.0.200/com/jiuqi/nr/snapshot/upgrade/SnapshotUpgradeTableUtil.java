/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.snapshot.upgrade;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.snapshot.deploy.ColumnInfo;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.CatalogModelService;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnapshotUpgradeTableUtil {
    private static final Logger logger = LoggerFactory.getLogger(SnapshotUpgradeTableUtil.class);
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private DesignDataModelService designDataModelService;
    private CatalogModelService catalogModelService;
    private DataModelDeployService dataModelDeployService;
    private IEntityMetaService iEntityMetaService;
    private PeriodEngineService periodEngineService;
    private final List<ColumnInfo> snapshotUpgradeTableColumnList = new ArrayList<ColumnInfo>();

    public SnapshotUpgradeTableUtil() {
        this.runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
        this.designDataModelService = (DesignDataModelService)SpringBeanUtils.getBean(DesignDataModelService.class);
        this.catalogModelService = (CatalogModelService)SpringBeanUtils.getBean(CatalogModelService.class);
        this.dataModelDeployService = (DataModelDeployService)SpringBeanUtils.getBean(DataModelDeployService.class);
        this.iEntityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        this.periodEngineService = (PeriodEngineService)SpringBeanUtils.getBean(PeriodEngineService.class);
        this.snapshotUpgradeTableColumnList.add(new ColumnInfo("ID", "ID", ColumnModelType.STRING, 50, "", true));
        this.snapshotUpgradeTableColumnList.add(new ColumnInfo("TASKKEY", "TASKKEY", ColumnModelType.STRING, 50, ""));
        this.snapshotUpgradeTableColumnList.add(new ColumnInfo("FORMSCHEMEKEY", "FORMSCHEMEKEY", ColumnModelType.STRING, 50, ""));
        this.snapshotUpgradeTableColumnList.add(new ColumnInfo("DATATIME", "DATATIME", ColumnModelType.STRING, 9, ""));
    }

    public boolean haveTable() {
        String snapshotUpgradeTableName = "NR_SNAPSHOT_UP";
        DesignTableModelDefine snapshotUpgradeTable = this.designDataModelService.getTableModelDefineByCode(snapshotUpgradeTableName);
        return null != snapshotUpgradeTable;
    }

    public void doObserver() {
        try {
            this.deploySnapshotUpgradeTable();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void deploySnapshotUpgradeTable() throws Exception {
        String snapshotUpgradeTableName = "NR_SNAPSHOT_UP";
        DesignTableModelDefine snapshotUpgradeTable = this.designDataModelService.getTableModelDefineByCode(snapshotUpgradeTableName);
        if (null != snapshotUpgradeTable) {
            return;
        }
        snapshotUpgradeTable = this.initNewTableDefine(snapshotUpgradeTableName);
        snapshotUpgradeTable.setDesc("\u5feb\u7167\u5347\u7ea7\u6807\u8bc6\u8868");
        snapshotUpgradeTable.setTitle("\u5feb\u7167\u5347\u7ea7\u6807\u8bc6\u8868");
        ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
        for (ColumnInfo column : this.snapshotUpgradeTableColumnList) {
            this.addColumn(snapshotUpgradeTable, column, createFieldList, modifyFieldList);
        }
        this.doSaveAndDeploy(snapshotUpgradeTable, createFieldList, modifyFieldList);
    }

    private DesignTableModelDefine initNewTableDefine(String tableCode) {
        DesignTableModelDefine tableDefine = this.designDataModelService.createTableModelDefine();
        tableDefine.setCode(tableCode);
        tableDefine.setName(tableCode);
        tableDefine.setOwner("NR");
        DesignCatalogModelDefine sysTableGroupByParent = this.catalogModelService.createChildCatalogModelDefine("10000000-1100-1110-1111-000000000000");
        tableDefine.setCatalogID(sysTableGroupByParent.getID());
        return tableDefine;
    }

    private void addColumn(DesignTableModelDefine tableDefine, ColumnInfo columnInfo, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) {
        DesignColumnModelDefine userColumn = this.initField(tableDefine.getID(), columnInfo.getReferFieldID(), columnInfo);
        createFieldList.add(userColumn);
        if (columnInfo.isKeyField()) {
            String keys = StringUtils.isEmpty((String)tableDefine.getBizKeys()) ? userColumn.getID() : tableDefine.getBizKeys() + ";" + userColumn.getID();
            tableDefine.setBizKeys(keys);
            tableDefine.setKeys(keys);
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

    private void doSaveAndDeploy(DesignTableModelDefine tableDefine, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) throws Exception {
        this.designDataModelService.insertTableModelDefine(tableDefine);
        if (createFieldList.size() > 0) {
            this.designDataModelService.insertColumnModelDefines(createFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        if (modifyFieldList.size() > 0) {
            this.designDataModelService.updateColumnModelDefines(modifyFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        if (createFieldList.size() > 0 || modifyFieldList.size() > 0) {
            this.dataModelDeployService.deployTableUnCheck(tableDefine.getID());
        }
    }
}

