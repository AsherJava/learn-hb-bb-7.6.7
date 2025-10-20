/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.basedata.service;

import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.R;
import java.util.Set;

public interface BaseDataCommonlyUsedService {
    public Set<String> listObjectcode(BaseDataDTO var1);

    public R changeData(BaseDataDTO var1);

    public R removeData(BaseDataDTO var1);

    public R countFlag(BaseDataDTO var1);

    public R changeFlag(BaseDataDTO var1);
}

