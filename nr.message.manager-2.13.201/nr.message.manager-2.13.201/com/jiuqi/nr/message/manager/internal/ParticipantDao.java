/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.message.manager.internal;

import com.jiuqi.nr.message.manager.pojo.ParticipantPO;
import java.sql.Timestamp;
import java.util.List;

public interface ParticipantDao {
    public List<String> findMsgIdByParticipantIdAndValidTimeAndInvalidTime(List<String> var1, Timestamp var2, Timestamp var3);

    public void deleteById(String var1);

    public boolean saveAll(List<ParticipantPO> var1);

    public List<String> findMsgIdByParticipantIdAndType(List<String> var1, Timestamp var2, Timestamp var3, int var4);

    public List<String> findMsgIdByParticipantId(List<String> var1, Timestamp var2, Timestamp var3);
}

