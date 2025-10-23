/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller2.RunTimeViewController
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.multcheck2.dynamic.impl;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller2.RunTimeViewController;
import com.jiuqi.nr.multcheck2.bean.MultcheckResOrg;
import com.jiuqi.nr.multcheck2.dynamic.IMCItemOrgService;
import com.jiuqi.nr.multcheck2.service.IMCDimService;
import com.jiuqi.nr.multcheck2.service.IMCResultService;
import com.jiuqi.nr.multcheck2.service.res.FileOrgService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MCItemOrgServiceImpl
implements IMCItemOrgService {
    private static final int LIMIT = 1000;
    @Autowired
    private RunTimeViewController runTimeViewController;
    @Autowired
    IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    FileOrgService fOrg;
    @Autowired
    IMCResultService resultService;
    @Autowired
    IMCDimService mcDimService;

    @Override
    @Transactional
    public void batchSave(List<MultcheckResOrg> resOrgs, List<String> dims, String task) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.resultService.getTableName("NR_MCR_ITEMORG_", task, dataScheme);
        this.fOrg.batchAdd(resOrgs, dims, tableName);
    }

    @Override
    public List<MultcheckResOrg> getByOrg(String recordKey, String itemKey, String task, String itemType, String org, Map<String, String> dimSet) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.resultService.getTableName("NR_MCR_ITEMORG_", task, dataScheme);
        List<String> dims = this.mcDimService.getDynamicFields(dataScheme.getKey());
        MultcheckResOrg param = new MultcheckResOrg();
        param.setRecordKey(recordKey);
        param.setItemKey(itemKey);
        param.setItemType(itemType);
        param.setOrg(org);
        param.setDims(dimSet);
        return this.fOrg.getMultcheckResOrg(tableName, param, dims);
    }

    @Override
    public List<MultcheckResOrg> getByItem(String recordKey, String itemKey, String task, String itemType, Map<String, String> dimSet) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.resultService.getTableName("NR_MCR_ITEMORG_", task, dataScheme);
        List<String> dims = this.mcDimService.getDynamicFields(dataScheme.getKey());
        MultcheckResOrg param = new MultcheckResOrg();
        param.setRecordKey(recordKey);
        param.setItemKey(itemKey);
        param.setItemType(itemType);
        param.setDims(dimSet);
        return this.fOrg.getMultcheckResOrg(tableName, param, dims);
    }

    @Override
    public List<MultcheckResOrg> getByOrg(String recordKey, String itemKey, String task, String org) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.resultService.getTableName("NR_MCR_ITEMORG_", task, dataScheme);
        List<String> dims = this.mcDimService.getDynamicFields(dataScheme.getKey());
        MultcheckResOrg param = new MultcheckResOrg();
        param.setRecordKey(recordKey);
        param.setItemKey(itemKey);
        param.setOrg(org);
        return this.fOrg.getMultcheckResOrg(tableName, param, dims);
    }

    @Override
    public List<MultcheckResOrg> getByItem(String recordKey, String itemKey, String task) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.resultService.getTableName("NR_MCR_ITEMORG_", task, dataScheme);
        List<String> dims = this.mcDimService.getDynamicFields(dataScheme.getKey());
        MultcheckResOrg param = new MultcheckResOrg();
        param.setRecordKey(recordKey);
        param.setItemKey(itemKey);
        return this.fOrg.getMultcheckResOrg(tableName, param, dims);
    }
}

