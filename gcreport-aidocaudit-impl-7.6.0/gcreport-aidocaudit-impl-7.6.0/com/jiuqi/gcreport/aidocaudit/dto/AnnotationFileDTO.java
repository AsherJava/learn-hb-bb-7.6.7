/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 */
package com.jiuqi.gcreport.aidocaudit.dto;

import com.jiuqi.bi.oss.ObjectInfo;
import java.io.ByteArrayOutputStream;

public class AnnotationFileDTO {
    private ObjectInfo objectInfo;
    private ByteArrayOutputStream outputStream;

    public AnnotationFileDTO() {
    }

    public AnnotationFileDTO(ObjectInfo objectInfo, ByteArrayOutputStream outputStream) {
        this.objectInfo = objectInfo;
        this.outputStream = outputStream;
    }

    public ObjectInfo getObjectInfo() {
        return this.objectInfo;
    }

    public void setObjectInfo(ObjectInfo objectInfo) {
        this.objectInfo = objectInfo;
    }

    public ByteArrayOutputStream getOutputStream() {
        return this.outputStream;
    }

    public void setOutputStream(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }
}

