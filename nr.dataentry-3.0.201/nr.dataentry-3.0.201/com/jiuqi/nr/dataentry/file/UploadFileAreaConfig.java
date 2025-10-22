/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileAreaConfig
 */
package com.jiuqi.nr.dataentry.file;

import com.jiuqi.nr.file.FileAreaConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UploadFileAreaConfig
implements FileAreaConfig {
    public static final String DATAENTRY_UPLOAD_AREA = "UPLOADTEMP";
    @Value(value="${jiuqi.nr.dataentry.upload.fileSize:1073741824}")
    private long MAXFILESIZE;

    public String getName() {
        return DATAENTRY_UPLOAD_AREA;
    }

    public long getMaxFileSize() {
        return this.MAXFILESIZE;
    }
}

