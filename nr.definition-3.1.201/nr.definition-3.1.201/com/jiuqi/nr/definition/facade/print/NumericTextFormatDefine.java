/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.print;

import java.io.Serializable;

public interface NumericTextFormatDefine
extends Serializable {
    public String getThousandsSeparator();

    public String getFormatNullResult();

    public String getDecimal();

    public String getFormatZeroResult();

    public String getFormatRoundingZeroResult();

    public void setThousandsSeparator(String var1);

    public void setFormatNullResult(String var1);

    public void setFormatRoundingZeroResult(String var1);

    public void setFormatZeroResult(String var1);

    public void setDecimal(String var1);
}

