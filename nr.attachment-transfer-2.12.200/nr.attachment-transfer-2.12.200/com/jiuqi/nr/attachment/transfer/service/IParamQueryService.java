/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.attachment.transfer.service;

import com.jiuqi.nr.attachment.transfer.dto.TaskParamDTO;
import com.jiuqi.nr.attachment.transfer.dto.TransferTreeDTO;
import com.jiuqi.nr.common.itree.ITree;
import java.util.List;

public interface IParamQueryService {
    public List<ITree<TransferTreeDTO>> getTask(String var1);

    public List<ITree<TransferTreeDTO>> getMapping(String var1, String var2);

    public TaskParamDTO getTaskParam(String var1, String var2);
}

