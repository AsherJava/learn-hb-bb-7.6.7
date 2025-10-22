/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.treestore.fmdmdisplay.bean;

import java.util.Date;
import java.util.List;

public interface FMDMDisplayScheme {
    public static final String GLOBAL_USER_KEY = "out_of_user_settings";

    public String getKey();

    public String getFormScheme();

    public String getEntityId();

    public String getOwner();

    public List<String> getFields();

    public Date getCreateTime();
}

