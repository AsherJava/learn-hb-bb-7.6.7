/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.subdatabase.controller.Impl;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.subdatabase.controller.SubDataBaseController;
import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import com.jiuqi.nr.subdatabase.facade.impl.SubDataBaseImpl;
import com.jiuqi.nr.subdatabase.service.SubDataBaseService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubDataBaseControllerImpl
implements SubDataBaseController {
    private static final Logger logger = LoggerFactory.getLogger(SubDataBaseControllerImpl.class);
    @Autowired
    private SubDataBaseService subDataBaseService;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public void insertSubDataBaseObj(SubDataBase obj, String taskKey) throws Exception {
        this.subDataBaseService.insertSubDataBase(obj, true, taskKey);
    }

    @Override
    public void insertSubDataBaseObj(SubDataBase obj, boolean createOrgCateGory, String taskKey) throws Exception {
        this.subDataBaseService.insertSubDataBase(obj, createOrgCateGory, taskKey);
    }

    @Override
    public void deleteSubDataBaseObj(SubDataBase obj, String taskKey) {
        this.subDataBaseService.deleteSubDataBase(obj, true, taskKey);
    }

    @Override
    public void deleteSubDataBaseObj(SubDataBase obj, boolean deleteOrgCateGory, String taskKey) {
        this.subDataBaseService.deleteSubDataBase(obj, deleteOrgCateGory, taskKey);
    }

    @Override
    public void updateSubDataBaseObj(SubDataBase obj) throws Exception {
        this.subDataBaseService.updateSubDataBase(obj);
    }

    @Override
    public SubDataBase getSubDataBaseObjByCode(String dataSchemeKey, String code) {
        SubDataBase result = this.subDataBaseService.getSubDataBaseObjByCode(dataSchemeKey, code);
        return result;
    }

    @Override
    public List<SubDataBase> getSubDataBaseObjByDataScheme(String dataSchemeKey) {
        List<SubDataBase> result = this.subDataBaseService.getSubDataBaseObjByDataScheme(dataSchemeKey);
        return result;
    }

    @Override
    public Boolean paramTablesExist(String dataSchemeKey, String orgCategoryName, String code) throws Exception {
        return this.subDataBaseService.orgCateGoryExist(orgCategoryName, code) || this.subDataBaseService.paramTableExists(dataSchemeKey, code) != false;
    }

    @Override
    public List<SubDataBase> getAllSubDataBase() {
        return this.subDataBaseService.getAllSubDataBase();
    }

    @Override
    public List<SubDataBaseImpl> getAllSubDataBase(String fieldName, boolean isDesc) {
        return this.subDataBaseService.getAllSubDataBase(fieldName, isDesc);
    }

    @Override
    public List<SubDataBase> getSameTitleSubDataBase(String title) {
        return this.subDataBaseService.getSameTitleSDB(title);
    }

    @Override
    public SubDataBase getSameTitleSubDataBase(String taskKey, String title) {
        TaskDefine task = this.runTimeViewController.getTask(taskKey);
        if (task != null) {
            return this.subDataBaseService.getSameTitleSubDataBase(task.getDataScheme(), title);
        }
        return null;
    }
}

