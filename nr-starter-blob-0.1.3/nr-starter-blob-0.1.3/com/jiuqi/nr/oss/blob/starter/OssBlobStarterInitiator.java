/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.oss.InitBlobOss
 */
package com.jiuqi.nr.oss.blob.starter;

import com.jiuqi.nr.file.oss.InitBlobOss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OssBlobStarterInitiator {
    @Autowired
    private InitBlobOss initBlobOss;

    public void init() throws Exception {
        this.initBlobOss.init();
    }

    public void initWhenStarted() throws Exception {
        this.initBlobOss.initWhenStarted();
    }
}

