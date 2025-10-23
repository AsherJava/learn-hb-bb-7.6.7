/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 */
package com.jiuqi.nr.task.web.vo;

import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.task.web.vo.ResourceSearchVO;

public class TaskResourceSearchVO
extends ResourceSearchVO {
    private String path;
    private Category type;
    private String group;

    public TaskResourceSearchVO(DesignFormSchemeDefine designFormSchemeDefine) {
        this.setKey(designFormSchemeDefine.getKey());
        this.setTitle(designFormSchemeDefine.getTitle());
        this.setCode(designFormSchemeDefine.getFormSchemeCode());
        this.setParentKey(designFormSchemeDefine.getTaskKey());
        this.type = new Category("formScheme", "\u62a5\u8868\u65b9\u6848");
    }

    public TaskResourceSearchVO(DesignTaskDefine taskDefine) {
        this.setKey(taskDefine.getKey());
        this.setTitle(taskDefine.getTitle());
        this.setCode(taskDefine.getTaskCode());
        this.type = new Category("task", "\u4efb\u52a1");
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Category getType() {
        return this.type;
    }

    public void setType(Category type) {
        this.type = type;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public static class Category {
        private String key;
        private String title;

        public Category(String key, String title) {
            this.title = title;
            this.key = key;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

