/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.multcheck2.dynamic.impl;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.multcheck2.bean.MCErrorDescription;
import com.jiuqi.nr.multcheck2.dynamic.IMCErrorInfoService;
import com.jiuqi.nr.multcheck2.service.IMCDimService;
import com.jiuqi.nr.multcheck2.service.IMCResultService;
import com.jiuqi.nr.multcheck2.service.res.FileErrorService;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MCErrorInfoServiceImpl
implements IMCErrorInfoService {
    private static final int LIMIT = 1000;
    @Autowired
    private RunTimeAuthViewController runTimeAuthView;
    @Autowired
    IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    FileErrorService fError;
    @Autowired
    IMCResultService resultService;
    @Autowired
    IMCDimService mcDimService;

    @Override
    public List<MCErrorDescription> getByResource(String task, String period, String itemType, String resource) throws Exception {
        TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.resultService.getTableName("NR_MCR_ERRORINFO_", taskDefine.getKey(), dataScheme);
        List<String> dims = this.mcDimService.getDynamicFields(dataScheme.getKey());
        MCErrorDescription param = new MCErrorDescription();
        param.setTask(task);
        param.setPeriod(period);
        param.setItemType(itemType);
        param.setResource(resource);
        return this.fError.getByResource(tableName, param, dims);
    }

    @Override
    public List<MCErrorDescription> getByOrg(String task, String period, String itemType, String org) throws Exception {
        TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.resultService.getTableName("NR_MCR_ERRORINFO_", taskDefine.getKey(), dataScheme);
        List<String> dims = this.mcDimService.getDynamicFields(dataScheme.getKey());
        MCErrorDescription param = new MCErrorDescription();
        param.setTask(task);
        param.setPeriod(period);
        param.setItemType(itemType);
        param.setOrg(org);
        return this.fError.getByOrg(tableName, param, dims);
    }

    @Override
    public List<MCErrorDescription> getByResourcesAndOrgs(String task, String period, String itemType, List<String> resourceList, List<String> orgList) throws Exception {
        if (CollectionUtils.isEmpty(resourceList) || CollectionUtils.isEmpty(orgList)) {
            return Collections.emptyList();
        }
        int sizeRes = resourceList.size();
        int sizeOrg = orgList.size();
        if (sizeRes == 1 && sizeOrg == 1) {
            return this.getByResourceAndOrg(task, period, itemType, resourceList.get(0), orgList.get(0));
        }
        if (sizeRes == 1) {
            return this.getByResourceAndOrgs(task, period, itemType, resourceList.get(0), orgList);
        }
        if (sizeOrg == 1) {
            return this.getByResourcesAndOrg(task, period, itemType, resourceList, orgList.get(0));
        }
        TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.resultService.getTableName("NR_MCR_ERRORINFO_", taskDefine.getKey(), dataScheme);
        List<String> dims = this.mcDimService.getDynamicFields(dataScheme.getKey());
        MCErrorDescription param = new MCErrorDescription();
        param.setTask(task);
        param.setPeriod(period);
        param.setItemType(itemType);
        return this.fError.getByResourcesAndOrgs(tableName, param, dims, resourceList, orgList);
    }

    private List<MCErrorDescription> getByResourceAndOrgs(String task, String period, String itemType, String resource, List<String> orgList) throws Exception {
        if (CollectionUtils.isEmpty(orgList)) {
            return Collections.emptyList();
        }
        int sizeOrg = orgList.size();
        if (sizeOrg == 1) {
            return this.getByResourceAndOrg(task, period, itemType, resource, orgList.get(0));
        }
        TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.resultService.getTableName("NR_MCR_ERRORINFO_", taskDefine.getKey(), dataScheme);
        List<String> dims = this.mcDimService.getDynamicFields(dataScheme.getKey());
        MCErrorDescription param = new MCErrorDescription();
        param.setTask(task);
        param.setPeriod(period);
        param.setItemType(itemType);
        param.setResource(resource);
        return this.fError.getByResourceAndOrgs(tableName, param, dims, orgList);
    }

    @Override
    public List<MCErrorDescription> getByResourcesAndOrg(String task, String period, String itemType, List<String> resourceList, String org) throws Exception {
        if (CollectionUtils.isEmpty(resourceList)) {
            return Collections.emptyList();
        }
        int sizeRes = resourceList.size();
        if (sizeRes == 1) {
            return this.getByResourceAndOrg(task, period, itemType, resourceList.get(0), org);
        }
        TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.resultService.getTableName("NR_MCR_ERRORINFO_", taskDefine.getKey(), dataScheme);
        List<String> dims = this.mcDimService.getDynamicFields(dataScheme.getKey());
        MCErrorDescription param = new MCErrorDescription();
        param.setTask(task);
        param.setPeriod(period);
        param.setItemType(itemType);
        param.setOrg(org);
        return this.fError.getByResourcesAndOrg(tableName, param, dims, resourceList);
    }

    @Override
    public List<MCErrorDescription> getByResourceAndOrg(String task, String period, String itemType, String resource, String org) throws Exception {
        TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.resultService.getTableName("NR_MCR_ERRORINFO_", taskDefine.getKey(), dataScheme);
        List<String> dims = this.mcDimService.getDynamicFields(dataScheme.getKey());
        MCErrorDescription param = new MCErrorDescription();
        param.setTask(task);
        param.setPeriod(period);
        param.setItemType(itemType);
        param.setOrg(org);
        param.setResource(resource);
        return this.fError.getByResourceAndOrg(tableName, param, dims);
    }

    @Override
    public String add(MCErrorDescription error) throws Exception {
        String task = error.getTask();
        TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.resultService.getTableName("NR_MCR_ERRORINFO_", taskDefine.getKey(), dataScheme);
        List<String> dims = this.mcDimService.getDynamicFields(dataScheme.getKey());
        error.setKey(UUID.randomUUID().toString());
        error.setTime(new Date());
        error.setUser(NpContextHolder.getContext().getUserName());
        this.fError.add(tableName, error, dims);
        return error.getKey();
    }

    @Override
    @Transactional
    public void batchAdd(List<MCErrorDescription> errorDescriptions, String task) throws Exception {
        if (CollectionUtils.isEmpty(errorDescriptions)) {
            return;
        }
        TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.resultService.getTableName("NR_MCR_ERRORINFO_", taskDefine.getKey(), dataScheme);
        List<String> dims = this.mcDimService.getDynamicFields(dataScheme.getKey());
        Date date = new Date();
        for (MCErrorDescription error : errorDescriptions) {
            error.setKey(UUID.randomUUID().toString());
            error.setTime(date);
            error.setUser(NpContextHolder.getContext().getUserName());
        }
        this.fError.batchAdd(tableName, errorDescriptions, dims);
    }

    @Override
    public void modify(MCErrorDescription error) throws Exception {
        if (StringUtils.hasText(error.getKey()) && StringUtils.hasText(error.getDescription())) {
            String task = error.getTask();
            TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(task);
            DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
            String tableName = this.resultService.getTableName("NR_MCR_ERRORINFO_", taskDefine.getKey(), dataScheme);
            this.fError.modify(tableName, error);
        }
    }

    @Override
    @Transactional
    public void batchDeleteByKeys(List<String> keys, String task) throws Exception {
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.resultService.getTableName("NR_MCR_ERRORINFO_", taskDefine.getKey(), dataScheme);
        this.fError.deleteByKeys(tableName, keys);
    }

    @Override
    @Transactional
    public void batchDeleteByOrgAndModel(List<String> modelKeys, List<String> orgCodes, String task, String period, String type) throws Exception {
        TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.resultService.getTableName("NR_MCR_ERRORINFO_", taskDefine.getKey(), dataScheme);
        this.fError.deleteByOrgAndModel(tableName, task, period, modelKeys, orgCodes, type);
    }

    @Override
    public void deleteByKey(String key, String task) throws Exception {
        TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName = this.resultService.getTableName("NR_MCR_ERRORINFO_", taskDefine.getKey(), dataScheme);
        this.fError.deleteByKey(tableName, key);
    }
}

