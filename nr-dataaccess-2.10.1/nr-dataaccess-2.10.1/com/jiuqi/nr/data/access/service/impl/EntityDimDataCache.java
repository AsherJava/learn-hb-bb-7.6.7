/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.nr.data.access.param.EntityDimData;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityDimDataCache {
    private final DataAccesslUtil dataAccesslUtil;
    private final Map<String, EntityDimData> dwMap = new HashMap<String, EntityDimData>();
    private final Map<String, EntityDimData> periodMap = new HashMap<String, EntityDimData>();
    private final Map<String, List<EntityDimData>> dimsMap = new HashMap<String, List<EntityDimData>>();

    public EntityDimDataCache(DataAccesslUtil dataAccesslUtil) {
        this.dataAccesslUtil = dataAccesslUtil;
    }

    public EntityDimData getDwEntityDimDataByTaskKey(String taskKey) {
        EntityDimData entityDimData = this.dwMap.get(taskKey);
        if (entityDimData == null) {
            entityDimData = this.dataAccesslUtil.getDwEntityDimDataByTaskKey(taskKey);
            this.dwMap.put(taskKey, entityDimData);
        }
        return entityDimData;
    }

    public EntityDimData getPeriodEntityDimDataByTaskKey(String taskKey) {
        EntityDimData entityDimData = this.periodMap.get(taskKey);
        if (entityDimData == null) {
            entityDimData = this.dataAccesslUtil.getPeriodEntityDimDataByTaskKey(taskKey);
            this.periodMap.put(taskKey, entityDimData);
        }
        return entityDimData;
    }

    public List<EntityDimData> getDimsEntityDimDataByTaskKey(String taskKey) {
        List<EntityDimData> entityDimData = this.dimsMap.get(taskKey);
        if (entityDimData == null) {
            entityDimData = this.dataAccesslUtil.getDimEntityDimDataByTaskKey(taskKey);
            this.dimsMap.put(taskKey, entityDimData);
        }
        return entityDimData;
    }

    public EntityDimData getSrcDwEntityDimDataByTaskKey(String taskKey) {
        EntityDimData entityDimData = this.dwMap.get(taskKey);
        if (entityDimData == null) {
            entityDimData = this.dataAccesslUtil.getSrcDwEntityDimDataByTaskKey(taskKey);
            this.dwMap.put(taskKey, entityDimData);
        }
        return entityDimData;
    }
}

