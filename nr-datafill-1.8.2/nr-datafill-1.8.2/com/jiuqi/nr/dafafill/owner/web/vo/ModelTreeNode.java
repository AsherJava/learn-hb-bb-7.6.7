/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.dafafill.owner.web.vo;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.dafafill.owner.entity.DataFillDefinitionPrivate;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.Map;
import org.springframework.util.StringUtils;

public class ModelTreeNode
implements INode {
    private String key;
    private String code;
    private String title;
    private String taskKey;
    private String taskTitle;
    private String taskCode;
    private String dataKey;

    public ModelTreeNode() {
    }

    public ModelTreeNode(String key, String title, String nodeGroupCode) {
        this.key = key;
        this.title = title;
        this.code = nodeGroupCode;
    }

    public ModelTreeNode(String key, String code, String dataKey, Map<String, DataFillDefinitionPrivate> definitionMap, Map<String, TaskDefine> taskMap) {
        this.key = key;
        this.code = code;
        this.dataKey = dataKey;
        DataFillDefinitionPrivate definition = definitionMap.get(dataKey);
        if (definition != null) {
            this.title = definition.getTitle();
            String dtaskKey = definition.getTask();
            if (StringUtils.hasText(dtaskKey) && taskMap.containsKey(dtaskKey)) {
                TaskDefine taskDefine = taskMap.get(dtaskKey);
                this.taskKey = taskDefine.getKey();
                this.taskTitle = taskDefine.getTitle();
                this.taskCode = taskDefine.getTaskCode();
            }
        }
    }

    public ModelTreeNode(String key, String title, String nodeGroupCode, String taskKey, String taskTitle, String taskCode) {
        this.key = key;
        this.dataKey = key;
        this.title = title;
        this.code = nodeGroupCode;
        this.taskKey = taskKey;
        this.taskTitle = taskTitle;
        this.taskCode = taskCode;
    }

    public String getKey() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getDataKey() {
        return this.dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }
}

