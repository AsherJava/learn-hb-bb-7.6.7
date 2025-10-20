/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.bizmeta.service;

import com.jiuqi.va.bizmeta.domain.bizres.BizResDataDO;
import com.jiuqi.va.bizmeta.domain.bizres.BizResInfoDO;
import com.jiuqi.va.bizmeta.domain.bizres.BizResInfoDTO;
import com.jiuqi.va.domain.common.R;
import java.util.List;
import java.util.UUID;

public interface IBizResInfoService {
    public R list(BizResInfoDTO var1);

    public R add(BizResDataDO var1, BizResInfoDO var2);

    public R deletes(List<UUID> var1);

    public void downloads(List<UUID> var1);
}

