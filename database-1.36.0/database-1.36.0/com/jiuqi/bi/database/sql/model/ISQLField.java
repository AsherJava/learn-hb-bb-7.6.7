/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model;

import com.jiuqi.bi.database.sql.model.GroupMode;
import com.jiuqi.bi.database.sql.model.IGroupConvertor;
import com.jiuqi.bi.database.sql.model.ISQLObject;
import com.jiuqi.bi.database.sql.model.ISQLTable;

public interface ISQLField
extends ISQLObject {
    public ISQLTable owner();

    public GroupMode groupMode();

    public void setGroupMode(GroupMode var1);

    public String fieldName();

    public boolean isVisible();

    public void setVisible(boolean var1);

    public IGroupConvertor getGroupConvertor();

    public void setGroupConvertor(IGroupConvertor var1);
}

