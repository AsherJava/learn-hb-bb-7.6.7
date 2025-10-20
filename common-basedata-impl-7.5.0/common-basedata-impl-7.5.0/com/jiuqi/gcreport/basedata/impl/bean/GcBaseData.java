/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.basedata.impl.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface GcBaseData
extends Comparable<GcBaseData> {
    public String getKey();

    public String getId();

    public String getCode();

    public String getTitle();

    public String getParentid();

    public BigDecimal getOrdinal();

    public String getParents();

    public String getObjectCode();

    public String getTableName();

    public String getShortName();

    public String getUnitCode();

    public Boolean isStop();

    public Boolean isRecovery();

    public LocalDateTime getValidTime();

    public LocalDateTime getInValidTime();

    public BigDecimal getVer();

    public String getCreatorId();

    public LocalDateTime getCreateTime();

    public Boolean isLeaf();

    public Object getFieldVal(String var1);
}

