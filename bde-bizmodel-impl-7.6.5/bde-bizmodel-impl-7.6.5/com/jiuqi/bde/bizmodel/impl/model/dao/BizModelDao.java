/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.enums.StopFlagEnum
 */
package com.jiuqi.bde.bizmodel.impl.model.dao;

import com.jiuqi.dc.base.common.enums.StopFlagEnum;
import java.math.BigDecimal;

public interface BizModelDao {
    public int updateStopFlagById(String var1, StopFlagEnum var2, String var3);

    public int updateOrdinalById(String var1, BigDecimal var2, String var3);
}

