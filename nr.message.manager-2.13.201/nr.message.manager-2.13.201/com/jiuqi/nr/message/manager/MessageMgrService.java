/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.message.pojo.MessageDTO
 */
package com.jiuqi.nr.message.manager;

import com.jiuqi.np.message.pojo.MessageDTO;
import com.jiuqi.nr.message.manager.pojo.MessageIdsDTO;
import com.jiuqi.nr.message.manager.pojo.PagingQueryVO;
import java.util.List;

public interface MessageMgrService {
    public boolean push(MessageDTO var1);

    public PagingQueryVO getUnread(String var1, int var2, int var3);

    public PagingQueryVO getHistory(String var1, int var2, int var3, int var4);

    public MessageDTO readUnread(String var1, String var2);

    public MessageDTO readRead(String var1);

    public boolean unReadToHistory(String var1, List<String> var2, int var3);

    public boolean updateHistoryStatus(MessageIdsDTO var1, int var2);

    public List<String> getReadInfo(String var1);

    public boolean revoke(String var1);

    public PagingQueryVO getUnread(String var1, int var2, int var3, int var4);

    public PagingQueryVO getHistory(String var1, int var2, int var3, int var4, int var5);
}

