/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.parain.internal.cache;

import java.util.List;
import nr.single.para.parain.internal.cache.FieldInfoDefine;

public class FieldInfoCache {
    String fieldTitle;
    String fieldCode;
    String ownerTableCode;
    FieldInfoDefine fieldDefine;
    List<FieldInfoDefine> mapFields;
    boolean isFieldNew;
    boolean needPrefix;

    public FieldInfoCache() {
        this.isFieldNew = false;
    }

    public FieldInfoCache(String ownerTableCode, FieldInfoDefine fieldDefine) {
        this.ownerTableCode = ownerTableCode;
        this.fieldDefine = fieldDefine;
        this.fieldCode = fieldDefine.getCode();
        this.fieldTitle = fieldDefine.getTitle();
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public FieldInfoDefine getFieldDefine() {
        return this.fieldDefine;
    }

    public void setFieldDefine(FieldInfoDefine fieldDefine) {
        this.fieldDefine = fieldDefine;
    }

    public List<FieldInfoDefine> getMapFields() {
        return this.mapFields;
    }

    public void setMapFields(List<FieldInfoDefine> mapFields) {
        this.mapFields = mapFields;
    }

    public boolean isFieldNew() {
        return this.isFieldNew;
    }

    public void setFieldNew(boolean isFieldNew) {
        this.isFieldNew = isFieldNew;
    }

    public boolean isNeedPrefix() {
        return this.needPrefix;
    }

    public void setNeedPrefix(boolean needPrefix) {
        this.needPrefix = needPrefix;
    }

    public String getOwnerTableCode() {
        return this.ownerTableCode;
    }

    public void setOwnerTableCode(String ownerTableCode) {
        this.ownerTableCode = ownerTableCode;
    }
}

