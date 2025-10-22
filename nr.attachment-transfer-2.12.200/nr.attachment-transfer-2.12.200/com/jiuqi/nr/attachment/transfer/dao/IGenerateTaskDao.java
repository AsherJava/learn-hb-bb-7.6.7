/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.dao;

import com.jiuqi.nr.attachment.transfer.domain.GenerateTaskDO;

public interface IGenerateTaskDao {
    public GenerateTaskDO query(String var1);

    public int insert(GenerateTaskDO var1);

    public int update(GenerateTaskDO var1);

    public int delete(String var1);

    public int updateStatus(String var1, int var2);
}

