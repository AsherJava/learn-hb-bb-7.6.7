/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.biz.intf.autotask;

import com.jiuqi.va.biz.intf.value.NamedElement;
import com.jiuqi.va.domain.common.R;

public interface AutoTask
extends NamedElement {
    @Override
    public String getName();

    public String getTitle();

    default public String getOrder() {
        return "";
    }

    public String getAutoTaskModule();

    public Boolean canRetract();

    public R execute(Object var1);

    public R retrack(Object var1);
}

