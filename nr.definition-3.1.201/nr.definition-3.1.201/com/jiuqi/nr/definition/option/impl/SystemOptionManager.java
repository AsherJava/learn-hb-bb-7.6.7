/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.configuration.controller.ISystemOptionManager
 *  com.jiuqi.nr.configuration.facade.SystemOptionBase
 *  com.jiuqi.nr.configuration.facade.SystemOptionDefine
 */
package com.jiuqi.nr.definition.option.impl;

import com.jiuqi.nr.configuration.controller.ISystemOptionManager;
import com.jiuqi.nr.configuration.facade.SystemOptionBase;
import com.jiuqi.nr.configuration.facade.SystemOptionDefine;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.option.core.TaskOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemOptionManager
implements ISystemOptionManager {
    @Autowired
    private ITaskOptionController taskOptionController;

    public void registerSystemOption(SystemOptionBase optionDefine) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u5973\u5a32\u7cfb\u7edf\u9009\u9879");
    }

    public Object getObject(String key) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u5973\u5a32\u7cfb\u7edf\u9009\u9879");
    }

    public Object getObject(String key, String taskKey) {
        return this.taskOptionController.getValue(taskKey, key);
    }

    public Object getObjectFromDB(String key, String taskKey, String formSchemeKey) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u65b0\u7684\u4efb\u52a1\u9009\u9879\u63a5\u53e3 com.jiuqi.nr.definition.controller.ITaskOptionController");
    }

    public Object getObject(String key, String taskKey, String formSchemeKey) {
        return this.taskOptionController.getValue(taskKey, key);
    }

    public HashMap<String, Object> batchGetObjects(List<String> keys) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u5973\u5a32\u7cfb\u7edf\u9009\u9879");
    }

    public HashMap<String, Object> batchGetObjects(List<String> keys, String taskKey) {
        HashMap<String, Object> keyValue = new HashMap<String, Object>();
        for (String key : keys) {
            keyValue.put(key, null);
        }
        List<TaskOption> options = this.taskOptionController.getOptions(taskKey);
        for (TaskOption option : options) {
            if (!keyValue.containsKey(option.getKey())) continue;
            keyValue.put(option.getKey(), option.getValue());
        }
        return keyValue;
    }

    public HashMap<String, Object> batchGetObjects(List<String> keys, String taskKey, String formSchemeKey) {
        return this.batchGetObjects(keys, taskKey);
    }

    public void setObject(String key, Object value) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u5973\u5a32\u7cfb\u7edf\u9009\u9879");
    }

    public void setObject(String key, String taskKey, Object value) {
        String valueStr = value instanceof String ? (String)value : null;
        this.taskOptionController.setValue(taskKey, key, valueStr);
    }

    public void setObject(String key, String taskKey, String formSchemeKey, Object value) {
        this.setObject(key, taskKey, value);
    }

    public void batchSetObjects(HashMap<String, Object> optionValues) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u5973\u5a32\u7cfb\u7edf\u9009\u9879");
    }

    public void setObject(HashMap<String, Object> optionValues, String taskKey) {
        for (Map.Entry<String, Object> entry : optionValues.entrySet()) {
            this.setObject(entry.getKey(), taskKey, entry.getValue());
        }
    }

    public void setObject(HashMap<String, Object> optionValues, String taskKey, String formSchemeKey) {
        this.setObject(optionValues, taskKey);
    }

    public SystemOptionDefine getGlobalOptionByKey(String key) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u5973\u5a32\u7cfb\u7edf\u9009\u9879");
    }

    public List<SystemOptionDefine> getGlobalOptions() {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u5973\u5a32\u7cfb\u7edf\u9009\u9879");
    }

    public List<SystemOptionDefine> getOptionsByTask(String taskKey) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u65b0\u7684\u4efb\u52a1\u9009\u9879\u63a5\u53e3 com.jiuqi.nr.definition.controller.ITaskOptionController");
    }

    public List<SystemOptionDefine> getOptionsByFormScheme(String taskKey, String formSchemeKey) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u65b0\u7684\u4efb\u52a1\u9009\u9879\u63a5\u53e3 com.jiuqi.nr.definition.controller.ITaskOptionController");
    }

    public List<SystemOptionDefine> getAllOptions() {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u5973\u5a32\u7cfb\u7edf\u9009\u9879");
    }

    public List<SystemOptionDefine> getOptionsByGroup(String groupKey) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u5973\u5a32\u7cfb\u7edf\u9009\u9879");
    }

    public void deleteSystemOption(String key) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u5973\u5a32\u7cfb\u7edf\u9009\u9879");
    }

    public void deleteSystemOption(String key, String taskKey) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u65b0\u7684\u4efb\u52a1\u9009\u9879\u63a5\u53e3 com.jiuqi.nr.definition.controller.ITaskOptionController");
    }

    public void deleteSystemOption(String key, String taskKey, String formSchemeKey) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u65b0\u7684\u4efb\u52a1\u9009\u9879\u63a5\u53e3 com.jiuqi.nr.definition.controller.ITaskOptionController");
    }

    public void batchDeleteOptions(List<String> keys) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u5973\u5a32\u7cfb\u7edf\u9009\u9879");
    }

    public void batchDeleteOptions(List<String> keys, String string) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u65b0\u7684\u4efb\u52a1\u9009\u9879\u63a5\u53e3 com.jiuqi.nr.definition.controller.ITaskOptionController");
    }

    public void batchDeleteOptions(List<String> keys, String taskKey, String formSchemeKey) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u65b0\u7684\u4efb\u52a1\u9009\u9879\u63a5\u53e3 com.jiuqi.nr.definition.controller.ITaskOptionController");
    }

    public void batchResetOptions(List<String> keys) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u5973\u5a32\u7cfb\u7edf\u9009\u9879");
    }

    public List<SystemOptionDefine> getSystemOption(List<String> optionKeys) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u5973\u5a32\u7cfb\u7edf\u9009\u9879");
    }

    public boolean adjustOptionsInheritProperty(String key, String taskKey) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u65b0\u7684\u4efb\u52a1\u9009\u9879\u63a5\u53e3 com.jiuqi.nr.definition.controller.ITaskOptionController");
    }

    public boolean adjustOptionsInheritProperty(String key, String taskKey, String formSchemeKey) {
        throw new UnsupportedOperationException("\u65b9\u6cd5\u5df2\u5e9f\u5f03,\u8bf7\u4f7f\u7528\u65b0\u7684\u4efb\u52a1\u9009\u9879\u63a5\u53e3 com.jiuqi.nr.definition.controller.ITaskOptionController");
    }
}

