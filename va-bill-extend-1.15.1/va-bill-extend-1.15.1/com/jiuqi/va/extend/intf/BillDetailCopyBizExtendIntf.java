/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.extend.intf;

import com.jiuqi.va.extend.domain.BillDetailCopyDTO;

public interface BillDetailCopyBizExtendIntf {
    public String getName();

    public String getTitle();

    public boolean execute(BillDetailCopyDTO var1);

    public boolean validate(BillDetailCopyDTO var1);
}

