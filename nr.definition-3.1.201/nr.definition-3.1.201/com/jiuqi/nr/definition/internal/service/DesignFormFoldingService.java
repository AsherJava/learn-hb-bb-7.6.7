/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormFoldingDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormFoldingDefine;
import com.jiuqi.nr.definition.internal.dao.DesignFormFoldingDao;
import com.jiuqi.nr.definition.internal.dao.FormFoldingDao;
import com.jiuqi.nr.definition.internal.impl.DesignFormFoldingDefineImpl;
import com.jiuqi.nr.definition.internal.service.FormFoldingService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class DesignFormFoldingService {
    @Autowired
    private DesignFormFoldingDao designFormFoldingDao;
    @Autowired
    private FormFoldingDao formFoldingDao;
    @Autowired
    private FormFoldingService formFoldingService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Transactional(rollbackFor={Exception.class})
    public void deploy(String taskKey) throws DBParaException {
        Set<String> desTimeFormFoldingKeys;
        Set<String> runTimeFormFoldingKeys = this.getRunTimeFormFoldingKeys(taskKey);
        if (!CollectionUtils.isEmpty(runTimeFormFoldingKeys)) {
            this.formFoldingDao.deleteFormFoldingDefine(runTimeFormFoldingKeys.toArray(new String[runTimeFormFoldingKeys.size()]));
        }
        if (!CollectionUtils.isEmpty(desTimeFormFoldingKeys = this.getDesTimeFormFoldingKeys(taskKey))) {
            this.designFormFoldingDao.insertObjectsFromDesignTime(DesignFormFoldingDefineImpl.class, "FF_KEY", desTimeFormFoldingKeys, true);
        }
        this.formFoldingService.onClearCache();
    }

    private Set<String> getDesTimeFormFoldingKeys(String taskKey) {
        HashSet formFoldings = new HashSet();
        List<DesignFormDefine> formDefines = this.designTimeViewController.queryAllSoftFormDefinesByTask(taskKey);
        formDefines.forEach(formDefine -> {
            List<DesignFormFoldingDefine> formFoldingInDb = this.designFormFoldingDao.getFormFoldingDefineByFormKey(formDefine.getKey());
            formFoldings.addAll(formFoldingInDb);
        });
        if (!CollectionUtils.isEmpty(formFoldings)) {
            return formFoldings.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    private Set<String> getRunTimeFormFoldingKeys(String taskKey) {
        ArrayList formFoldings = new ArrayList();
        List<FormDefine> formDefines = this.runTimeViewController.queryAllFormDefinesByTask(taskKey);
        formDefines.forEach(formDefine -> {
            List<FormFoldingDefine> formFoldingInDb = this.formFoldingDao.getFormFoldingDefineByFormKey(formDefine.getKey());
            formFoldings.addAll(formFoldingInDb);
        });
        if (!CollectionUtils.isEmpty(formFoldings)) {
            return formFoldings.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    public void insert(DesignFormFoldingDefine[] defines) throws DBParaException {
        this.designFormFoldingDao.insertFormFoldingDefine(defines);
    }

    public void delete(String[] keys) throws DBParaException {
        this.designFormFoldingDao.deleteFormFoldingDefine(keys);
    }

    public void deleteByFormKey(String formKey) throws DBParaException {
        this.designFormFoldingDao.deleteFormFoldingByForm(formKey);
    }

    public void update(DesignFormFoldingDefine[] defines) throws DBParaException {
        this.designFormFoldingDao.updateFormFoldingDefine(defines);
    }

    public DesignFormFoldingDefine getByKey(String key) {
        return this.designFormFoldingDao.getFormFoldingDefineByKey(key);
    }

    public List<DesignFormFoldingDefine> getByFormKey(String formKey) {
        return this.designFormFoldingDao.getFormFoldingDefineByFormKey(formKey);
    }
}

