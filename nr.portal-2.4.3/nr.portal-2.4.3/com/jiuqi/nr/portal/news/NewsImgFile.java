/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileAreaConfig
 */
package com.jiuqi.nr.portal.news;

import com.jiuqi.nr.file.FileAreaConfig;
import org.springframework.stereotype.Component;

@Component
public class NewsImgFile
implements FileAreaConfig {
    public static final String IMGZONE = "IMG_NEWS";
    private static final boolean ENABLEFASTDOWNLOAD = false;
    private static final long MAXFILESIZE = 0x6400000L;
    public static final long EXPIRATION_TIME = 3122064000L;
    private String name = "IMG_NEWS";
    private boolean enableFastDownload = false;
    private long maxFileSize = 0x6400000L;

    public String getName() {
        return this.name;
    }

    public long getMaxFileSize() {
        return this.maxFileSize;
    }

    public boolean isEnableFastDownload() {
        return this.enableFastDownload;
    }

    public long getExpirationTime() {
        return 3122064000L;
    }
}

