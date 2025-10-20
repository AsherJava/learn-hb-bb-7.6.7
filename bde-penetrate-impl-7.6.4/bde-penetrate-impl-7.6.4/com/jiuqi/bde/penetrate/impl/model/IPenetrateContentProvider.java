/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.bde.penetrate.impl.model;

import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.va.domain.common.PageVO;

public interface IPenetrateContentProvider<C extends PenetrateBaseDTO, E> {
    public static final String DEFAULT_KEY = "DEFAULT_CONTENTPROVIDER";

    public String getPluginType();

    public String getBizModel();

    public PenetrateTypeEnum getPenetrateType();

    public PageVO<E> doQuery(C var1);
}

