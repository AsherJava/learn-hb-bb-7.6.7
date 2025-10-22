/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.definitionext.taskExtConfig.internal.controller;

import com.jiuqi.nr.definitionext.taskExtConfig.controller.IConfigModel;
import com.jiuqi.nr.definitionext.taskExtConfig.controller.IConfigModelController;
import com.jiuqi.nr.definitionext.taskExtConfig.controller.ITaskExtConfigController;
import com.jiuqi.nr.definitionext.taskExtConfig.dao.define.TaskExtConfigDao;
import com.jiuqi.nr.definitionext.taskExtConfig.internal.ConfigModelControllerFactory;
import com.jiuqi.nr.definitionext.taskExtConfig.internal.service.TaskExtConfigSession;
import com.jiuqi.nr.definitionext.taskExtConfig.model.ExtensionBasicModel;
import com.jiuqi.nr.definitionext.taskExtConfig.model.TaskExtConfigDefine;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TaskExtConfigController
implements ITaskExtConfigController {
    private static final Logger logger = LoggerFactory.getLogger(TaskExtConfigController.class);
    @Autowired
    TaskExtConfigDao dao;
    @Autowired
    ConfigModelControllerFactory configModelControllerFactory;
    @Resource
    private TaskExtConfigSession taskExtConfigSession;

    @Override
    public TaskExtConfigDefine initTaskExtConfigDefine(String taskKey, String schemaKey, String extType) throws Exception {
        IConfigModelController modelHelper;
        String dataStr;
        TaskExtConfigDefine taskExtConfigDefine = new TaskExtConfigDefine();
        taskExtConfigDefine.setTaskKey(taskKey);
        taskExtConfigDefine.setSchemaKey(schemaKey);
        taskExtConfigDefine.setExtType(extType);
        if (taskExtConfigDefine.getExtKey() == null || taskExtConfigDefine.getExtKey().length() == 0) {
            taskExtConfigDefine.setExtKey(UUID.randomUUID().toString());
        }
        if ((dataStr = (modelHelper = this.configModelControllerFactory.getImpl(taskExtConfigDefine.getExtType())).initData(taskExtConfigDefine.getTaskKey(), taskExtConfigDefine.getSchemaKey())) != null && !dataStr.isEmpty()) {
            taskExtConfigDefine.setExtData(dataStr);
            this.dao.insertDefine(taskExtConfigDefine);
        }
        return taskExtConfigDefine;
    }

    @Override
    public Boolean insertTaskExtConfigDefine(TaskExtConfigDefine taskExtConfigDefine) {
        Assert.notNull((Object)taskExtConfigDefine, "'taskExtConfigDefine' must not be null");
        try {
            if (taskExtConfigDefine.getExtKey() == null || taskExtConfigDefine.getExtKey().length() == 0) {
                taskExtConfigDefine.setExtKey(UUID.randomUUID().toString());
            }
            this.dao.insertDefine(taskExtConfigDefine);
            return true;
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean updateTaskExtConfigDefine(TaskExtConfigDefine taskExtConfigDefine) {
        Assert.notNull((Object)taskExtConfigDefine, "'taskExtConfigDefine' must not be null");
        try {
            this.dao.updateDefine(taskExtConfigDefine);
            String cacheKey = taskExtConfigDefine.getSchemaKey() + "_" + taskExtConfigDefine.getExtType();
            ExtensionBasicModel<Object> basicModel = this.getTaskExtConfigModel(taskExtConfigDefine);
            IConfigModelController modelHelper = this.configModelControllerFactory.getImpl(taskExtConfigDefine.getExtType());
            modelHelper.updateOperation(basicModel.getTaskKey(), basicModel.getSchemaKey(), basicModel.getExtInfoModel());
            this.taskExtConfigSession.removeResult(cacheKey);
            return true;
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean deleteTaskExtConfigDefineByKey(String key) {
        try {
            this.dao.deleteDefineById(key);
            return true;
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public TaskExtConfigDefine getTaskExtConfigDefineByKey(String key) {
        try {
            return this.dao.getDefineById(key);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<TaskExtConfigDefine> getAllTaskExtConfigDefine() {
        try {
            return this.dao.getDefineList();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<TaskExtConfigDefine> getTaskExtConfigDefineByType(String type) {
        try {
            return this.dao.getDefinesByType(type);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ExtensionBasicModel<?> getTaskExtConfigDefineBySchemakey(String taskKey, String schemaKey, String extType) {
        ExtensionBasicModel<Object> basicModel = new ExtensionBasicModel();
        try {
            List<TaskExtConfigDefine> configs = this.dao.getDefinesBySchemakey(schemaKey, extType);
            if (configs.size() == 0) {
                configs.add(this.initTaskExtConfigDefine(taskKey, schemaKey, extType));
            }
            if (configs.size() > 0) {
                TaskExtConfigDefine config = configs.get(0);
                basicModel = this.getTaskExtConfigModel(config);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return basicModel;
    }

    private ExtensionBasicModel<Object> getTaskExtConfigModel(TaskExtConfigDefine taskExtConfigDefine) {
        ExtensionBasicModel<Object> basicModel = new ExtensionBasicModel<Object>();
        if (taskExtConfigDefine != null) {
            basicModel.setExtKey(taskExtConfigDefine.getExtKey());
            basicModel.setTaskKey(taskExtConfigDefine.getTaskKey());
            basicModel.setSchemaKey(taskExtConfigDefine.getSchemaKey());
            basicModel.setExtData(taskExtConfigDefine.getExtData());
            basicModel.setExtType(taskExtConfigDefine.getExtType());
            basicModel.setExtCode(taskExtConfigDefine.getExtCode());
            IConfigModelController modelHelper = this.configModelControllerFactory.getImpl(taskExtConfigDefine.getExtType());
            if (taskExtConfigDefine.getExtData() != null && !taskExtConfigDefine.getExtData().isEmpty()) {
                basicModel.setExtInfoModel(modelHelper.getModel(taskExtConfigDefine.getTaskKey(), taskExtConfigDefine.getSchemaKey(), taskExtConfigDefine.getExtData()));
            }
        }
        return basicModel;
    }

    @Override
    public Object getTaskExtConfigDefineBySchemakeyCache(String taskKey, String schemaKey, String extType) {
        IConfigModel infoModel = null;
        try {
            String cacheKey = schemaKey + "_" + extType;
            String extData = "";
            Object cacheData = this.taskExtConfigSession.getResult(cacheKey);
            if (cacheData == null) {
                ExtensionBasicModel<?> basicModel = this.getTaskExtConfigDefineBySchemakey(taskKey, schemaKey, extType);
                if (basicModel != null && basicModel.getExtData() != null && !basicModel.getExtData().isEmpty()) {
                    extData = basicModel.getExtData();
                }
                this.taskExtConfigSession.saveResult(cacheKey, extData);
            } else {
                extData = cacheData.toString();
            }
            if (!extData.isEmpty()) {
                IConfigModelController modelHelper = this.configModelControllerFactory.getImpl(extType);
                infoModel = modelHelper.getModel(taskKey, schemaKey, extData);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return infoModel;
    }
}

