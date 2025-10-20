/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.consolidatedsystem.api.ConsolidatedSystemClient
 *  com.jiuqi.gcreport.consolidatedsystem.api.option.ConsolidatedOptionClient
 *  com.jiuqi.gcreport.consolidatedsystem.vo.ConsolidatedSystemVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 */
package com.jiuqi.gcreport.offsetitem.cache;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.consolidatedsystem.api.ConsolidatedSystemClient;
import com.jiuqi.gcreport.consolidatedsystem.api.option.ConsolidatedOptionClient;
import com.jiuqi.gcreport.consolidatedsystem.vo.ConsolidatedSystemVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OffSetUnSysDimensionCache {
    private static final Map<String, List<DimensionVO>> systemId2allDimValues = new ConcurrentHashMap<String, List<DimensionVO>>();
    private static final Map<String, List<DimensionVO>> systemId2notNullDimValues = new ConcurrentHashMap<String, List<DimensionVO>>();
    private static long lastUpdateTime = 0L;
    private static long intervalTime = 10000L;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void load() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime > intervalTime) {
            Map<String, List<DimensionVO>> map = systemId2allDimValues;
            synchronized (map) {
                if (currentTime - lastUpdateTime > intervalTime) {
                    List<DimensionVO> dimensionVOs;
                    systemId2allDimValues.clear();
                    BusinessResponseEntity consolidatedSystems = ((ConsolidatedSystemClient)SpringContextUtils.getBean(ConsolidatedSystemClient.class)).getConsolidatedSystems(null);
                    if (consolidatedSystems == null || CollectionUtils.isEmpty((Collection)((Collection)consolidatedSystems.getData()))) {
                        systemId2notNullDimValues.clear();
                        return;
                    }
                    for (ConsolidatedSystemVO systemVO : (List)consolidatedSystems.getData()) {
                        if (null == systemVO) continue;
                        BusinessResponseEntity dimensionsByTableName = ((ConsolidatedOptionClient)SpringContextUtils.getBean(ConsolidatedOptionClient.class)).getAllDimensionsByTableName("GC_OFFSETVCHRITEM", systemVO.getId());
                        dimensionVOs = (List<DimensionVO>)dimensionsByTableName.getData();
                        systemId2allDimValues.put(systemVO.getId(), dimensionVOs);
                        if (null != dimensionVOs) continue;
                    }
                    Map<String, List<DimensionVO>> map2 = systemId2notNullDimValues;
                    synchronized (map2) {
                        systemId2notNullDimValues.clear();
                        for (Map.Entry<String, List<DimensionVO>> entry : systemId2allDimValues.entrySet()) {
                            dimensionVOs = entry.getValue();
                            String systemId = entry.getKey();
                            ArrayList notNullDimValues = new ArrayList();
                            dimensionVOs.forEach(dimensionVO -> {
                                if (null != dimensionVO.getNullAble() && !dimensionVO.getNullAble().booleanValue()) {
                                    notNullDimValues.add(dimensionVO);
                                }
                            });
                            systemId2notNullDimValues.put(systemId, notNullDimValues);
                        }
                    }
                    lastUpdateTime = System.currentTimeMillis();
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static List<DimensionVO> allDimValue(String systemId) {
        Map<String, List<DimensionVO>> map = systemId2allDimValues;
        synchronized (map) {
            return systemId2allDimValues.get(systemId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static List<DimensionVO> notNullDimValues(String systemId) {
        if (null == systemId) {
            return Collections.emptyList();
        }
        Map<String, List<DimensionVO>> map = systemId2notNullDimValues;
        synchronized (map) {
            return systemId2notNullDimValues.get(systemId);
        }
    }
}

