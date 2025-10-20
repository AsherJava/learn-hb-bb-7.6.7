/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.PenetrateInitDTO
 */
package com.jiuqi.bde.penetrate.impl.service;

import com.jiuqi.bde.common.dto.PenetrateInitDTO;
import java.util.Map;

public interface BdePenetrateMainService {
    public PenetrateInitDTO init(String var1, String var2, Map<String, Object> var3);

    public Object doQuery(String var1, String var2, Map<String, Object> var3);

    public Object customFetchDetailLedger(String var1, Map<String, Object> var2);
}

