/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.user.dto;

import java.io.Serializable;
import java.time.Instant;
import org.springframework.util.ObjectUtils;

public class AttributeDefineDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String title;
    private String titleEn;
    private String dataType;
    private String tableName;
    private String desc;
    private boolean isEmpty;
    private String defaultValue;
    private String location;
    private String ordinal;
    private Instant createTime;
    private String attr1;
    private int encryptType;
    private boolean isSensitive;
    private boolean showInDetail;
    private boolean showInList;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return ObjectUtils.isEmpty(this.name) ? "" : this.name.toUpperCase();
    }

    public void setName(String name) {
        this.name = ObjectUtils.isEmpty(name) ? "" : name.toUpperCase();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEn() {
        return this.titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }

    public void setEmpty(boolean empty) {
        this.isEmpty = empty;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getAttr1() {
        return this.attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public int getEncryptType() {
        return this.encryptType;
    }

    public void setEncryptType(int encryptType) {
        this.encryptType = encryptType;
    }

    public boolean isSensitive() {
        return this.isSensitive;
    }

    public void setSensitive(boolean sensitive) {
        this.isSensitive = sensitive;
    }

    public boolean isShowInDetail() {
        return this.showInDetail;
    }

    public void setShowInDetail(boolean showInDetail) {
        this.showInDetail = showInDetail;
    }

    public boolean isShowInList() {
        return this.showInList;
    }

    public void setShowInList(boolean showInList) {
        this.showInList = showInList;
    }
}

