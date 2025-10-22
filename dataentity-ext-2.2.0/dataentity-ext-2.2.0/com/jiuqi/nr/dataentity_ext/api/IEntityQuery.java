/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentity_ext.api;

import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO;
import java.util.List;
import java.util.Map;

public interface IEntityQuery {
    public List<IEntityDataDTO> listAllRows();

    public int getTotalCount();

    public IEntityDataDTO queryByKey(String var1);

    public List<IEntityDataDTO> listAllLeafRows();

    public List<IEntityDataDTO> listAllNonLeafRows();

    public List<IEntityDataDTO> listRootRows();

    public List<IEntityDataDTO> getChildRows(List<String> var1);

    public int countChildRows(String var1);

    public List<IEntityDataDTO> getChildRowsAndSelf(List<String> var1);

    public List<IEntityDataDTO> getAllChildRows(List<String> var1);

    public int countAllChildRows(String var1);

    public List<IEntityDataDTO> getAllChildRowsAndSelf(List<String> var1);

    public List<IEntityDataDTO> getAllChildLeaveRows(List<String> var1);

    public List<IEntityDataDTO> getAllChildNonLeaveRows(List<String> var1);

    public List<IEntityDataDTO> getAllParentRows(List<String> var1);

    public List<IEntityDataDTO> getCheckRows(List<String> var1);

    public Map<EntityDataType, Integer> countTypes();
}

