/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.gather.util;

import com.jiuqi.nr.data.engine.gather.param.FileSumContext;
import com.jiuqi.nr.data.engine.gather.param.FileSumInfo;
import java.util.List;

public interface FileCalculateService {
    public List<FileSumInfo> sumFileGroup(FileSumContext var1);
}

