/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.dao;

import com.jiuqi.nr.attachment.transfer.domain.WorkSpaceDO;

public interface IWorkSpaceDao {
    public int insert(WorkSpaceDO var1);

    public WorkSpaceDO get(int var1);

    public int update(WorkSpaceDO var1);
}

