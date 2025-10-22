/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.treestore.uselector.bean;

import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterSchemeImpl;
import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterTemplate;
import java.util.Date;

public interface USFilterScheme {
    public String getKey();

    public String getTitle();

    public String getOwner();

    public String getEntityId();

    public boolean isShared();

    public USFilterTemplate getTemplate();

    public Date getCreateTime();

    public static USFilterSchemeImpl assign(USFilterScheme scheme) {
        USFilterSchemeImpl impl = new USFilterSchemeImpl();
        impl.setKey(scheme.getKey());
        impl.setTitle(scheme.getTitle());
        impl.setEntityId(scheme.getEntityId());
        impl.setOwner(scheme.getOwner());
        impl.setShared(scheme.isShared());
        impl.setTemplate(scheme.getTemplate());
        impl.setCreateTime(scheme.getCreateTime());
        return impl;
    }
}

