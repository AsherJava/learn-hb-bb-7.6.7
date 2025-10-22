/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFieldInfo;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.FileKeyAndInfo;
import java.util.HashMap;
import java.util.Map;

public class FieldAndFileInfo {
    private String unitKey;
    private FieldDefine fieldDefine;
    private BlobFieldInfo blobFieldInfo;
    private Map<String, FileKeyAndInfo> fileKeyAndInfoMap = new HashMap<String, FileKeyAndInfo>();

    public String getUnitKey() {
        return this.unitKey;
    }

    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
    }

    public FieldDefine getFieldDefine() {
        return this.fieldDefine;
    }

    public void setFieldDefine(FieldDefine fieldDefine) {
        this.fieldDefine = fieldDefine;
    }

    public BlobFieldInfo getBlobFieldInfo() {
        return this.blobFieldInfo;
    }

    public void setBlobFieldInfo(BlobFieldInfo blobFieldInfo) {
        this.blobFieldInfo = blobFieldInfo;
    }

    public Map<String, FileKeyAndInfo> getFileKeyAndInfoMap() {
        return this.fileKeyAndInfoMap;
    }

    public void setFileKeyAndInfoMap(Map<String, FileKeyAndInfo> fileKeyAndInfoMap) {
        this.fileKeyAndInfoMap = fileKeyAndInfoMap;
    }
}

