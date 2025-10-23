/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 */
package com.jiuqi.nrdt.parampacket.manage.service;

import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nrdt.parampacket.manage.bean.ParamPacket;
import com.jiuqi.nrdt.parampacket.manage.bean.ParamPacketGroup;
import com.jiuqi.nrdt.parampacket.manage.bean.ParamPacketQuery;
import com.jiuqi.nrdt.parampacket.manage.bean.ResponseObj;
import java.util.List;

public interface IParamPacketManageService {
    public List<ParamPacket> queryParamPacketByParent(String var1);

    public ParamPacket queryParanPacket(String var1);

    public ParamPacket addParamPacket(ParamPacket var1);

    public ParamPacket updateParamPacket(ParamPacket var1);

    public boolean deleteParamPacket(String var1);

    public AsyncTaskInfo saveResource(String var1, String var2, Boolean var3);

    public AsyncTaskInfo packedParamPacket(String var1);

    public ParamPacket queryParamPacketByCode(String var1);

    public List<ParamPacket> queryParamPacketByUser(Boolean var1);

    public Object downloadParamPacket(String var1);

    public List<ParamPacketGroup> queryParamPacketGroupByParent(String var1);

    public ParamPacketGroup addParamPacketGroup(ParamPacketGroup var1);

    public ParamPacketGroup updateParamPacketGroup(ParamPacketGroup var1);

    public boolean deleteParamPacketGroup(String var1);

    public ResponseObj getResourcetree(ParamPacketQuery var1) throws Exception;
}

