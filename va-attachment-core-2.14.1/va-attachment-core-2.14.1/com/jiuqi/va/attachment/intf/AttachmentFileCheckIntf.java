/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.attachment.intf;

import com.jiuqi.va.domain.common.R;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentFileCheckIntf {
    public String getName();

    public R doCheck(MultipartFile var1);
}

