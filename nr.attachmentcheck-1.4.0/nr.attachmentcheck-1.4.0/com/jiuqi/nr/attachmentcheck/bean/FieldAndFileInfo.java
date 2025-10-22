/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.attachmentcheck.bean;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentFieldInfo;
import com.jiuqi.nr.attachmentcheck.bean.FileKeyAndInfo;
import com.jiuqi.nr.attachmentcheck.bean.MapWrapper;
import java.util.HashMap;
import java.util.Map;

public class FieldAndFileInfo {
    private MapWrapper dimNameValue;
    private FieldDefine fieldDefine;
    private AttachmentFieldInfo blobFieldInfo;
    private Map<String, FileKeyAndInfo> fileKeyAndInfoMap = new HashMap<String, FileKeyAndInfo>();

    public MapWrapper getDimNameValue() {
        return this.dimNameValue;
    }

    public void setDimNameValue(MapWrapper dimNameValue) {
        this.dimNameValue = dimNameValue;
    }

    public FieldDefine getFieldDefine() {
        return this.fieldDefine;
    }

    public void setFieldDefine(FieldDefine fieldDefine) {
        this.fieldDefine = fieldDefine;
    }

    public AttachmentFieldInfo getBlobFieldInfo() {
        return this.blobFieldInfo;
    }

    public void setBlobFieldInfo(AttachmentFieldInfo blobFieldInfo) {
        this.blobFieldInfo = blobFieldInfo;
    }

    public Map<String, FileKeyAndInfo> getFileKeyAndInfoMap() {
        return this.fileKeyAndInfoMap;
    }

    public void setFileKeyAndInfoMap(Map<String, FileKeyAndInfo> fileKeyAndInfoMap) {
        this.fileKeyAndInfoMap = fileKeyAndInfoMap;
    }
}

