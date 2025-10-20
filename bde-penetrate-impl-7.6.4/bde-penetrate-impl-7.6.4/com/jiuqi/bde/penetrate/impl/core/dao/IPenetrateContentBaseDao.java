/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.bde.penetrate.impl.core.dao;

import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.va.domain.common.PageVO;

public interface IPenetrateContentBaseDao {
    public static final String COUNT_SQL_TEMPLATE = "SELECT COUNT(1) FROM (%s) T ";

    public <C extends PenetrateBaseDTO, E> PageVO<E> query(C var1, QueryParam<E> var2);
}

