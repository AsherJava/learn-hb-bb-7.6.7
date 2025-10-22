/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formtype.facade;

import com.jiuqi.nr.formtype.common.UnitNature;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public interface FormTypeDataDefine
extends Serializable {
    public UUID getId();

    public void setId(UUID var1);

    public String getCode();

    public void setCode(String var1);

    public String getName();

    public void setName(String var1);

    public String getNameEnUS();

    public void setNameEnUS(String var1);

    public String getShortname();

    public void setShortname(String var1);

    public UnitNature getUnitNatrue();

    public void setUnitNatrue(UnitNature var1);

    public String getIcon();

    public void setIcon(String var1);

    public String getFormTypeCode();

    public void setFormTypeCode(String var1);

    public BigDecimal getOrdinal();

    public void setOrdinal(BigDecimal var1);

    public Date getUpdateTime();

    public void setUpdateTime(Date var1);
}

