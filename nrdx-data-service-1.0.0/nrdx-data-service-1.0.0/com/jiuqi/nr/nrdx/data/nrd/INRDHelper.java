/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.nrdx.data.nrd;

import com.jiuqi.nr.nrdx.data.nrd.CheckRes;
import org.springframework.web.multipart.MultipartFile;

public interface INRDHelper {
    public CheckRes isNRD(MultipartFile var1);
}

