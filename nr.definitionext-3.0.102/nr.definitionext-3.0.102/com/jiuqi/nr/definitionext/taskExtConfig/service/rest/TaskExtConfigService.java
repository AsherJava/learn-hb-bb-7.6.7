/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.definitionext.taskExtConfig.service.rest;

import com.jiuqi.nr.definitionext.taskExtConfig.controller.ITaskExtConfigController;
import com.jiuqi.nr.definitionext.taskExtConfig.model.ExtConfigParam;
import com.jiuqi.nr.definitionext.taskExtConfig.model.ExtensionBasicModel;
import com.jiuqi.nr.definitionext.taskExtConfig.model.TaskExtConfigDefine;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/formschemaConfig"})
public class TaskExtConfigService {
    private static final Logger logger = LoggerFactory.getLogger(TaskExtConfigService.class);
    @Autowired
    private ITaskExtConfigController TaskExtConfigController;

    @RequestMapping(value={"/add"}, method={RequestMethod.POST})
    public Boolean add(@Valid @RequestBody TaskExtConfigDefine taskExtConfigDefine) {
        Boolean flag = false;
        try {
            flag = this.TaskExtConfigController.insertTaskExtConfigDefine(taskExtConfigDefine);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return flag;
    }

    @RequestMapping(value={"/update"}, method={RequestMethod.POST})
    public Boolean update(@Valid @RequestBody TaskExtConfigDefine taskExtConfigDefine) {
        Boolean flag = false;
        try {
            TaskExtConfigDefine define;
            if (!taskExtConfigDefine.getExtKey().isEmpty() && (define = this.TaskExtConfigController.getTaskExtConfigDefineByKey(taskExtConfigDefine.getExtKey())) != null) {
                flag = this.TaskExtConfigController.updateTaskExtConfigDefine(taskExtConfigDefine);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return flag;
    }

    @RequestMapping(value={"/remove"}, method={RequestMethod.GET})
    public Boolean remove(@Valid @RequestBody String key) {
        Boolean flag = false;
        try {
            if (!key.isEmpty()) {
                flag = this.TaskExtConfigController.deleteTaskExtConfigDefineByKey(key);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return flag;
    }

    @RequestMapping(value={"/queryitemsByKey"}, method={RequestMethod.POST})
    public TaskExtConfigDefine queryItemsByKey(@Valid @RequestBody String key) {
        try {
            if (!key.isEmpty()) {
                return this.TaskExtConfigController.getTaskExtConfigDefineByKey(key);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return null;
    }

    @RequestMapping(value={"/queryItemsByTask"}, method={RequestMethod.POST})
    public ExtensionBasicModel<?> queryItemsByTask(@Valid @RequestBody ExtConfigParam Param) {
        try {
            if (Param != null && !Param.getSchemaKey().isEmpty()) {
                return this.TaskExtConfigController.getTaskExtConfigDefineBySchemakey(Param.getTaskKey(), Param.getSchemaKey(), Param.getExtType());
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return null;
    }
}

