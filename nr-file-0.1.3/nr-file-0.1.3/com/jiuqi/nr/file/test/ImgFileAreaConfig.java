/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.file.test;

import com.jiuqi.nr.file.FileAreaConfig;

public class ImgFileAreaConfig
implements FileAreaConfig {
    @Override
    public String getName() {
        return "img";
    }

    @Override
    public boolean isEnableFastDownload() {
        return true;
    }
}

