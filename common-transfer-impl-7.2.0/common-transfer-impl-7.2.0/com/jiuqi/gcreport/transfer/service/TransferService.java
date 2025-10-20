/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.transfer.vo.TransferColumnVo
 *  com.jiuqi.gcreport.transfer.vo.TransferVo
 */
package com.jiuqi.gcreport.transfer.service;

import com.jiuqi.gcreport.transfer.vo.TransferColumnVo;
import com.jiuqi.gcreport.transfer.vo.TransferVo;
import java.util.List;

public interface TransferService {
    public void save(TransferVo var1);

    public List<String> getSelectColumnsByPath(String var1, boolean var2);

    public List<String> getSelectColumnsByPathNoUser(String var1);

    public List<TransferColumnVo> getAllField(String var1);
}

