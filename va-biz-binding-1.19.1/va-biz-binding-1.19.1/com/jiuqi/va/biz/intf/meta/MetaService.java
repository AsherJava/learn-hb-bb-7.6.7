/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.meta;

import com.jiuqi.va.biz.intf.meta.MetaData;
import com.jiuqi.va.biz.intf.meta.MetaEdition;
import com.jiuqi.va.biz.intf.meta.MetaGroup;
import com.jiuqi.va.biz.intf.meta.MetaGroupEdition;
import com.jiuqi.va.biz.intf.meta.MetaGroupHistory;
import com.jiuqi.va.biz.intf.meta.MetaInfo;
import com.jiuqi.va.biz.intf.meta.MetaInfoEdition;
import com.jiuqi.va.biz.intf.meta.MetaInfoHistory;
import java.util.List;
import java.util.UUID;

public interface MetaService {
    public MetaEdition createGroup(String var1, MetaGroup var2);

    public MetaEdition deleteGroupById(String var1, UUID var2);

    public MetaEdition updateGroup(String var1, MetaGroup var2);

    public MetaGroup findGroup(String var1, String var2, String var3);

    public MetaGroup getGroup(String var1, String var2, String var3);

    public List<MetaGroup> getGroupList();

    public List<MetaGroup> getGroupListByModule(String var1);

    public List<MetaGroup> getGroupListByMetaType(String var1);

    public List<MetaGroup> getGroupList(String var1, String var2);

    public List<MetaGroupEdition> getGroupEditionList();

    public List<MetaGroupEdition> getGroupEditionListByModule(String var1);

    public List<MetaGroupEdition> getGroupEditionListByMetaType(String var1);

    public List<MetaGroupEdition> getGroupEditionList(String var1, String var2);

    public List<MetaGroupHistory> getGroupHistoryList(String var1, String var2, String var3);

    public MetaEdition createMeta(String var1, MetaInfo var2);

    public MetaEdition createMeta(String var1, MetaInfo var2, MetaData var3);

    public MetaEdition deleteMetaById(String var1, UUID var2);

    public MetaEdition updateMeta(String var1, MetaInfo var2, MetaData var3);

    public MetaEdition updateMeta(String var1, MetaInfo var2);

    public MetaEdition updateMeta(String var1, MetaData var2);

    public MetaInfo findMeta(String var1, String var2, String var3);

    public List<MetaInfo> getMetaList();

    public List<MetaInfo> getMetaListByModule(String var1);

    public List<MetaInfo> getMetaListByMetaType(String var1);

    public List<MetaInfo> getMetaList(String var1, String var2);

    public List<MetaInfoEdition> getMetaEditionList();

    public List<MetaInfoEdition> getMetaEditionListByModule(String var1);

    public List<MetaInfoEdition> getMetaEditionListByMetaType(String var1);

    public List<MetaInfoEdition> getMetaEditionList(String var1, String var2);

    public List<MetaInfoHistory> getMetaHistoryList(String var1, String var2, String var3);

    public MetaData getMetaDataById(UUID var1);

    public MetaData getMetaDataHistoryById(UUID var1);

    public MetaData getMetaDataUserById(UUID var1);

    public void deployGroupById(String var1, UUID var2, String var3);

    public void deployMetaById(String var1, UUID var2, String var3);
}

