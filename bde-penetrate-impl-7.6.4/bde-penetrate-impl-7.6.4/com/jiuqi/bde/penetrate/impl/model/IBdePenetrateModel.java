/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.PenetrateInitDTO
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 */
package com.jiuqi.bde.penetrate.impl.model;

import com.jiuqi.bde.common.dto.PenetrateInitDTO;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import java.util.Map;

public interface IBdePenetrateModel<C extends PenetrateBaseDTO, E> {
    public PenetrateTypeEnum getPenetrateType();

    public C getCondi(Map<String, Object> var1);

    public C initCondi(C var1);

    public PenetrateInitDTO init(String var1, C var2);

    public E doQuery(String var1, C var2);
}

