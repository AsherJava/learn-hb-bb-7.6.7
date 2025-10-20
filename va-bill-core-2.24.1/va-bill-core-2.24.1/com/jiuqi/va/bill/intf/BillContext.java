/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.ModelContext
 */
package com.jiuqi.va.bill.intf;

import com.jiuqi.va.biz.intf.model.ModelContext;
import java.util.Map;

public interface BillContext
extends ModelContext {
    public String getVerifyCode();

    public String getSchemeCode();

    public boolean isDisableVerify();

    public Object getContextValue(String var1);

    public void setContextValue(String var1, Object var2);

    public void setContextValue(Map<String, Object> var1);

    public Map<String, Object> getCustomContext();

    public boolean isDisable18n();
}

