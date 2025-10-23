/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.dafafill.web.vo;

import com.jiuqi.nr.dafafill.entity.DataFillDefinition;
import com.jiuqi.nr.dafafill.entity.DataFillGroup;
import com.jiuqi.nr.dafafill.model.enums.ModelType;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.text.SimpleDateFormat;
import java.util.Map;

public class TableItemVO {
    private String id;
    private String code;
    private String title;
    private String type;
    private ModelType sourceType;
    private String modifyTime;
    private String taskKey;
    private String taskCode;
    private String taskTitle;
    private String description;

    public TableItemVO() {
    }

    public TableItemVO(DataFillGroup group) {
        this.id = group.getId();
        this.title = group.getTitle();
        this.type = "nr.dataFill.folder";
        this.description = group.getDescription();
        if (group.getModifyTime() != null) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.modifyTime = sf.format(group.getModifyTime());
        }
    }

    public TableItemVO(DataFillDefinition def, Map<String, TaskDefine> taskDefineMap) {
        this.id = def.getId();
        this.code = def.getCode();
        this.title = def.getTitle();
        this.type = "nr.dataFill.dataFillMsg.model";
        this.sourceType = def.getSourceType();
        this.taskKey = def.getTaskKey();
        if (taskDefineMap.containsKey(this.taskKey)) {
            this.taskCode = taskDefineMap.get(this.taskKey).getTaskCode();
            this.taskTitle = taskDefineMap.get(this.taskKey).getTitle();
        }
        this.description = def.getDescription();
        if (def.getModifyTime() != null) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.modifyTime = sf.format(def.getModifyTime());
        }
    }

    public String getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ModelType getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(ModelType sourceType) {
        this.sourceType = sourceType;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

