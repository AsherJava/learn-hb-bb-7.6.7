/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  tk.mybatis.mapper.version.NextVersion
 *  tk.mybatis.mapper.version.VersionException
 */
package com.jiuqi.dc.base.common.utils;

import tk.mybatis.mapper.version.NextVersion;
import tk.mybatis.mapper.version.VersionException;

public class SequenceNextVersion
implements NextVersion<Long> {
    public Long nextVersion(Long current) throws VersionException {
        if (current == null) {
            throw new VersionException("\u5f53\u524d\u7248\u672c\u53f7\u4e3a\u7a7a!");
        }
        return current + 1L;
    }
}

