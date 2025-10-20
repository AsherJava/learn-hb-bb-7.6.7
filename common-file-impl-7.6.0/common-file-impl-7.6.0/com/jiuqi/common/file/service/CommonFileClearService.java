/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.file.service;

import com.jiuqi.common.file.entity.CommonFileClearEO;
import java.util.List;

public interface CommonFileClearService {
    public boolean clearExpiredFiles();

    public boolean addFileClearData(List<CommonFileClearEO> var1);
}

