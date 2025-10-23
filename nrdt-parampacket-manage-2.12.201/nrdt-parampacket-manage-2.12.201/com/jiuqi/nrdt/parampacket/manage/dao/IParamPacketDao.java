/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nrdt.parampacket.manage.dao;

import com.jiuqi.nrdt.parampacket.manage.bean.ParamPacket;
import java.util.List;

public interface IParamPacketDao {
    public List<ParamPacket> queryParamPacketByParent(String var1);

    public void addParamPacket(ParamPacket var1);

    public void updateParamPacket(ParamPacket var1);

    public void deleteParamPacket(String var1);

    public ParamPacket queryParamPacket(String var1);

    public ParamPacket queryParamPacketByCode(String var1);

    public List<ParamPacket> queryAllParamPacket();
}

