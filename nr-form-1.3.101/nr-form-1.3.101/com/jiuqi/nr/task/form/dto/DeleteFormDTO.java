/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import java.io.Serializable;
import java.util.List;

public class DeleteFormDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String key;
    private String deleteId;
    private List<String> fieldKeys;
    private List<String> tableKeys;
    private List<String> baseDataNames;
    private List<DeleteBaseDataDefine> baseDatas;

    public DeleteFormDTO(String formKey, String deleteId) {
        this.key = formKey;
        this.deleteId = deleteId;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getFieldKeys() {
        return this.fieldKeys;
    }

    public void setFieldKeys(List<String> fieldKeys) {
        this.fieldKeys = fieldKeys;
    }

    public List<DeleteBaseDataDefine> getBaseDatas() {
        return this.baseDatas;
    }

    public void setBaseDatas(List<DeleteBaseDataDefine> baseDatas) {
        this.baseDatas = baseDatas;
    }

    public List<String> getBaseDataNames() {
        return this.baseDataNames;
    }

    public void setBaseDataNames(List<String> baseDataNames) {
        this.baseDataNames = baseDataNames;
    }

    public List<String> getTableKeys() {
        return this.tableKeys;
    }

    public void setTableKeys(List<String> tableKeys) {
        this.tableKeys = tableKeys;
    }

    public String getDeleteId() {
        return this.deleteId;
    }

    public void setDeleteId(String deleteId) {
        this.deleteId = deleteId;
    }

    public static class DeleteBaseDataDefine
    implements Serializable {
        private Integer index;
        private String name;
        private String title;

        public DeleteBaseDataDefine(Integer index, String name, String title) {
            this.index = index;
            this.name = name;
            this.title = title;
        }

        public Integer getIndex() {
            return this.index;
        }

        public String getName() {
            return this.name;
        }

        public String getTitle() {
            return this.title;
        }
    }
}

