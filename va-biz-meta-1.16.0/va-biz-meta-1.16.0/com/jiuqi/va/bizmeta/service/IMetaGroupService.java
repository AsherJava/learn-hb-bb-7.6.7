/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.meta.OperateType
 *  com.jiuqi.va.domain.metadeploy.MetaDataDeployDim
 */
package com.jiuqi.va.bizmeta.service;

import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDTO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupEditionDO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupHistoryDO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoFilterDTO;
import com.jiuqi.va.domain.meta.OperateType;
import com.jiuqi.va.domain.metadeploy.MetaDataDeployDim;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IMetaGroupService {
    public MetaGroupEditionDO createGroup(String var1, MetaGroupDTO var2);

    public MetaGroupEditionDO deleteGroupById(String var1, UUID var2);

    public MetaGroupEditionDO updateGroup(String var1, MetaGroupDTO var2);

    public MetaGroupDO findGroup(String var1, String var2, String var3);

    public MetaGroupDO getGroup(String var1, String var2, String var3);

    public MetaGroupDTO getGroupById(UUID var1);

    public List<MetaGroupDO> getGroupListByModule(String var1);

    public List<MetaGroupDO> getGroupListByMetaType(String var1);

    public List<MetaGroupDO> getGroupListByFilter(MetaInfoFilterDTO var1);

    public List<MetaGroupDTO> getGroupList(String var1, String var2, OperateType var3);

    public List<MetaGroupDTO> getChildGroupList(String var1, String var2, UUID var3, OperateType var4);

    public MetaGroupDTO getGroupEditionById(UUID var1);

    public List<MetaGroupEditionDO> getGroupEditionListByModule(String var1);

    public List<MetaGroupEditionDO> getGroupEditionListByMetaType(String var1);

    public List<MetaGroupHistoryDO> getGroupHistoryList(String var1, String var2, String var3);

    public void deployGroupById(String var1, MetaDataDeployDim var2, long var3);

    public void addChildrenToSet(String var1, HashMap<String, String> var2, Set<String> var3);

    public void addParentToSet(String var1, HashMap<String, String> var2, Set<String> var3);

    public void addGroupToList(HashMap<String, String> var1, HashMap<String, String> var2, String var3);
}

