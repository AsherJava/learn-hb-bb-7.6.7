/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 */
package com.jiuqi.bde.penetrate.impl.service;

import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import java.util.List;

public interface AdjustVchrPenetrateContentProvider {
    public List<PenetrateBalance> query(PenetrateBaseDTO var1);

    public List<PenetrateBalance> xjllQuery(PenetrateBaseDTO var1);
}

