/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bizmeta.common.utils;

import com.jiuqi.va.bizmeta.domain.metaversion.MetaDataVersionDTO;
import com.jiuqi.va.bizmeta.service.IMetaVersionService;
import java.util.Calendar;

public class VersionManage {
    private static VersionManage versionManage;

    private VersionManage() {
    }

    public static final VersionManage getInstance() {
        if (versionManage == null) {
            versionManage = new VersionManage();
        }
        return versionManage;
    }

    public synchronized long newVersion(IMetaVersionService versionService) {
        long maxVersion = versionService.getMaxVersion(new MetaDataVersionDTO());
        long newVersion = Calendar.getInstance().getTimeInMillis();
        if (newVersion > maxVersion) {
            return newVersion;
        }
        return maxVersion + 1L;
    }
}

