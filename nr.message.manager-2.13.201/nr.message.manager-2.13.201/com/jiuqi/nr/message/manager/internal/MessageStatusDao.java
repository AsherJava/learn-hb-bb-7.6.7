/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.message.manager.internal;

import com.jiuqi.nr.message.manager.pojo.MessageStatusPO;
import com.jiuqi.nr.message.manager.pojo.PageDTO;
import java.util.List;

public interface MessageStatusDao {
    public void saveOrUpdate(String var1, List<String> var2, int var3);

    public void updateStatus(String var1, List<String> var2, int var3);

    public List<String> findMessageIdByUserIdAndStatus(String var1, int var2, PageDTO var3);

    public List<String> findMessageIdByUserId(String var1);

    public List<String> findUserIdByMessageId(String var1);

    public void save(MessageStatusPO var1);

    public void deleteById(String var1);
}

