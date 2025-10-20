/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.bizmeta.service;

import com.jiuqi.va.bizmeta.domain.bizres.BizResGroupDO;
import com.jiuqi.va.domain.common.R;

public interface IBizResGroupService {
    public R add(BizResGroupDO var1);

    public R list();

    public R delete(BizResGroupDO var1);

    public R update(BizResGroupDO var1);

    public R checkName(String var1);
}

