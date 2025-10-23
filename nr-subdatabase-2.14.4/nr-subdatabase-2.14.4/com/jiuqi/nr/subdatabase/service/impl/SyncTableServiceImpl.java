/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.subdatabase.service.impl;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.subdatabase.facade.DataTableDDLInfo;
import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import com.jiuqi.nr.subdatabase.service.DataTableDDLInfoService;
import com.jiuqi.nr.subdatabase.service.SubDataBaseService;
import com.jiuqi.nr.subdatabase.service.SyncTableService;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import com.jiuqi.util.OrderGenerator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class SyncTableServiceImpl
implements SyncTableService {
    @Autowired
    private DataTableDDLInfoService dataTableDDLInfoService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private SubDataBaseService subDataBaseService;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;

    @Override
    public boolean isNeedSync(SubDataBase subDataBase) {
        DataTableDDLInfo newestDataTableDDLInfo = this.getNewestDataTableDDLInfo(subDataBase);
        if (newestDataTableDDLInfo == null) {
            return false;
        }
        return newestDataTableDDLInfo.getSyncOrder() > subDataBase.getSyncOrder();
    }

    @Override
    public void syncTable(SubDataBase subDataBase, boolean isForce) throws Exception {
        this.doSync(this.getNeedSyncTableModels(subDataBase, isForce), subDataBase);
    }

    public List<String> getNeedSyncTableModels(SubDataBase subDataBase, boolean isForce) {
        List<String> needSyncTablemodels = new ArrayList<String>();
        if (!isForce) {
            if (this.isNeedSync(subDataBase)) {
                List<DataTableDDLInfo> dataTableDDLInfos = this.dataTableDDLInfoService.queryInfoBySchemeKey(subDataBase.getDataScheme());
                needSyncTablemodels = dataTableDDLInfos.stream().filter(obj -> obj.getSyncOrder() > subDataBase.getSyncOrder()).map(DataTableDDLInfo::getTableModelKey).collect(Collectors.toList());
            }
        } else {
            needSyncTablemodels = new ArrayList<String>(this.subDataBaseService.getTableModelKey(subDataBase.getDataScheme()));
        }
        return needSyncTablemodels;
    }

    private void doSync(List<String> needSyncTableModel, SubDataBase subDataBase) throws Exception {
        if (CollectionUtils.isEmpty(needSyncTableModel)) {
            return;
        }
        for (String tableModelKey : needSyncTableModel) {
            HashSet<String> id;
            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(tableModelKey);
            TableModelDefine subTableModel = this.dataModelService.getTableModelDefineById(tableModelKey + subDataBase.getCode());
            if (tableModelDefine == null && subTableModel != null) {
                id = new HashSet<String>();
                id.add(tableModelKey);
                this.subDataBaseService.deleteAndDeploy(id, subDataBase.getCode(), true);
                continue;
            }
            if (tableModelDefine != null && subTableModel == null) {
                id = new HashSet();
                id.add(tableModelKey);
                this.subDataBaseService.createAndDeploy(id, subDataBase.getCode(), subDataBase.getDataScheme());
                continue;
            }
            if (tableModelDefine == null) continue;
            this.deleteDesignTableModel(tableModelKey, subDataBase.getCode());
            String subTableModelID = this.copyRunTableModel(tableModelKey, subDataBase);
            this.dataModelDeployService.deployTable(subTableModelID);
        }
        this.updateSDSyncTime(subDataBase);
    }

    private void updateSDSyncTime(SubDataBase subDataBase) throws DBParaException {
        DataTableDDLInfo newestDataTableDDLInfo = this.getNewestDataTableDDLInfo(subDataBase);
        if (newestDataTableDDLInfo != null) {
            Integer syncOrder = newestDataTableDDLInfo.getSyncOrder();
            subDataBase.setSyncOrder(syncOrder);
        }
        subDataBase.setSDSyncTime(new Date());
        this.subDataBaseService.updateSyncTime(subDataBase);
    }

    private DataTableDDLInfo getNewestDataTableDDLInfo(SubDataBase subDataBase) {
        List<DataTableDDLInfo> dataTableDDLInfos = this.dataTableDDLInfoService.queryInfoBySchemeKey(subDataBase.getDataScheme());
        if (CollectionUtils.isEmpty(dataTableDDLInfos)) {
            return null;
        }
        dataTableDDLInfos.sort(Comparator.comparing(DataTableDDLInfo::getSyncOrder).reversed());
        return dataTableDDLInfos.get(0);
    }

    private void deleteDesignTableModel(String tableModelID, String subDataBaseCode) {
        String subTableModelID = tableModelID + subDataBaseCode;
        this.designDataModelService.deleteColumnModelDefineByTable(subTableModelID);
        this.designDataModelService.deleteIndexsByTable(subTableModelID);
        this.designDataModelService.deleteTableModelDefine(subTableModelID);
    }

    private String copyRunTableModel(String tableModelID, SubDataBase subDataBase) throws ModelValidateException {
        DataScheme dataScheme = this.iRuntimeDataSchemeService.getDataScheme(subDataBase.getDataScheme());
        List columnModelDefines = this.designDataModelService.getColumnModelDefinesByTable(tableModelID);
        String newTableID = tableModelID + subDataBase.getCode();
        columnModelDefines.forEach(a -> {
            a.setTableID(newTableID);
            a.setID(a.getID() + subDataBase.getCode());
        });
        this.designDataModelService.insertColumnModelDefines(columnModelDefines.toArray(new DesignColumnModelDefine[0]));
        DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefine(tableModelID);
        String newTableTitle = subDataBase.getCode() + tableModelDefine.getTitle();
        String newTableCode = subDataBase.getCode() + tableModelDefine.getCode();
        String newTableName = subDataBase.getCode() + tableModelDefine.getName();
        String newKeys = this.subDataBaseService.getNewKeys(tableModelDefine.getKeys(), subDataBase.getCode());
        String newBizKeys = this.subDataBaseService.getNewKeys(tableModelDefine.getBizKeys(), subDataBase.getCode());
        tableModelDefine.setID(newTableID);
        tableModelDefine.setKeys(newKeys);
        tableModelDefine.setBizKeys(newBizKeys);
        tableModelDefine.setTitle(newTableTitle);
        tableModelDefine.setCode(newTableCode);
        tableModelDefine.setName(newTableName);
        tableModelDefine.setSupportNrdb(true);
        tableModelDefine.setStorageName(subDataBase.getCode() + dataScheme.getBizCode());
        this.designDataModelService.insertTableModelDefine(tableModelDefine);
        List indexsByTable = this.designDataModelService.getIndexsByTable(tableModelID);
        indexsByTable.forEach(a -> {
            String newIndexName = OrderGenerator.newOrder() + "_G_";
            this.designDataModelService.addIndexToTable(newTableID, this.subDataBaseService.getNewKeys(a.getFieldIDs(), subDataBase.getCode()).split(";"), newIndexName, a.getType());
        });
        return newTableID;
    }
}

