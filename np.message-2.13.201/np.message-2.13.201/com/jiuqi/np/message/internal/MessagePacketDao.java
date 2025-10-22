/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.message.internal;

import com.jiuqi.np.message.constants.MessageHandleStateEnum;
import com.jiuqi.np.message.pojo.MessagePacketPO;
import java.sql.Timestamp;
import java.util.List;

public interface MessagePacketDao {
    public List<MessagePacketPO> findByState(Integer var1);

    public void save(MessagePacketPO var1);

    public void batchUpdateState(List<String> var1, MessageHandleStateEnum var2, Timestamp var3);

    public MessagePacketPO get(String var1);
}

