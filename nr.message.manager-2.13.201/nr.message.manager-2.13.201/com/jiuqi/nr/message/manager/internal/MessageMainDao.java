/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.message.manager.internal;

import com.jiuqi.nr.message.manager.pojo.MessageMainPO;
import java.util.List;
import java.util.Optional;

public interface MessageMainDao {
    public List<MessageMainPO> findByIds(List<String> var1, int var2, int var3);

    public Optional<MessageMainPO> findById(String var1);

    public void deleteById(String var1);

    public boolean save(MessageMainPO var1);

    public List<MessageMainPO> findByIdsAndType(List<String> var1, int var2);
}

