/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.systemcheck.spi;

import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import java.util.List;

public interface ICheckResource {
    public String getKey();

    public String getTitle();

    public String getGroupKey();

    public Double getOrder();

    public String getIcon();

    public String getMessage();

    public List<String> getTagMessages();

    public boolean checkAuth(String var1);

    public ICheckAction getCheckOption();
}

