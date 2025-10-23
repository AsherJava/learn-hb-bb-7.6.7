/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nrdt.parampacket.manage.dao;

import com.jiuqi.nrdt.parampacket.manage.bean.ParamPacketGroup;
import java.util.List;

public interface IParamPacketGroupDao {
    public List<ParamPacketGroup> queryParamPacketGroupByParent(String var1);

    public void addParamPacketGroup(ParamPacketGroup var1);

    public void updateParamPacketGroup(ParamPacketGroup var1);

    public void deleteParamPacketGroup(String var1);

    public ParamPacketGroup queryParamPacketGroup(String var1);
}

