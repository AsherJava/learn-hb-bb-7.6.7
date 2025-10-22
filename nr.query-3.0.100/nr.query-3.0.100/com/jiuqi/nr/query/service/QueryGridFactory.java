/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.query.service;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.query.block.DimensionPageLoadInfo;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QuerySelectItem;
import com.jiuqi.nr.query.common.QueryLayoutType;
import com.jiuqi.nr.query.dao.impl.QueryBlockDefineDao;
import com.jiuqi.nr.query.service.IQueryCacheManager;
import com.jiuqi.nr.query.service.LocalCacheManager;
import com.jiuqi.nr.query.service.QueryGridDefination;
import com.jiuqi.nr.query.service.impl.QueryHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class QueryGridFactory {
    static final String CACHETYPE_QUERYDEFINA = "QUERYDEFINA";
    IQueryCacheManager cacheManager = (IQueryCacheManager)BeanUtil.getBean(LocalCacheManager.class);
    String userKey = QueryHelper.getCacheKey(NpContextHolder.getContext().getTenant(), NpContextHolder.getContext().getUserId());
    private static Map<String, DimensionPageLoadInfo> pageInfos = new HashMap<String, DimensionPageLoadInfo>();

    public boolean containsKey(String key) {
        Object cach = this.cacheManager.getCache(this.userKey, key, CACHETYPE_QUERYDEFINA);
        return cach != null;
    }

    public QueryGridDefination get(String key) {
        Object cach = this.cacheManager.getCache(this.userKey, key, CACHETYPE_QUERYDEFINA);
        if (cach != null) {
            return (QueryGridDefination)cach;
        }
        return null;
    }

    public void put(String key, QueryGridDefination value) {
        this.cacheManager.setItem(this.userKey, key, CACHETYPE_QUERYDEFINA, value);
    }

    public void remove(String key) {
        this.cacheManager.reSetCache(this.userKey, key, CACHETYPE_QUERYDEFINA);
    }

    public QueryGridDefination reloadGridCache(QueryBlockDefine block, boolean isExport, boolean isOnlyForm) {
        try {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u91cd\u7f6e\u67e5\u8be2\u5757\u7ef4\u5ea6\u7ed3\u6784", (String)("\u5757\u540d\u79f0\uff1a" + block.getTitle()));
            QueryGridDefination gridDefination = new QueryGridDefination(block, isExport, isOnlyForm);
            String gridKey = QueryGridFactory.buildKey(block.getId(), isOnlyForm);
            this.put(gridKey, gridDefination);
            return gridDefination;
        }
        catch (Exception ex) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u91cd\u7f6e\u67e5\u8be2\u5757\u7ef4\u5ea6\u7ed3\u6784\u5931\u8d25", (String)("\u5757\u540d\u79f0\uff1a" + block.getTitle() + "\u5f02\u5e38\u4fe1\u606f\uff1a" + ex.getMessage()));
            return null;
        }
    }

    public static String buildKey(String blockId, boolean isOnlyForm) {
        String gridKey = QueryHelper.getCacheKey(NpContextHolder.getContext().getTenant(), NpContextHolder.getContext().getUserId()) + "_" + blockId + isOnlyForm;
        return gridKey;
    }

    public static void setPageInfo(String key, DimensionPageLoadInfo info) {
        pageInfos.put(key, info);
    }

    public static DimensionPageLoadInfo getPageInfo(String key) {
        if (pageInfos.containsKey(key)) {
            return pageInfos.get(key);
        }
        return new DimensionPageLoadInfo();
    }

    public QueryGridDefination getGridCache(QueryBlockDefine block, boolean isExport, boolean isOnlyForm) {
        try {
            QueryGridDefination defina;
            String gridKey = QueryGridFactory.buildKey(block.getId(), false);
            boolean changed = false;
            if (this.containsKey(gridKey) && !isExport) {
                defina = this.get(gridKey);
                try {
                    List dim = defina.block.getQueryDimensions().stream().filter(item -> item.getLayoutType() == QueryLayoutType.LYT_CONDITION).collect(Collectors.toList());
                    for (QueryDimensionDefine queryDimensionDefine : dim) {
                        List collect;
                        List<QuerySelectItem> selectItems = queryDimensionDefine.getSelectItems();
                        Optional<QueryDimensionDefine> first = block.getQueryDimensions().stream().filter(i -> i.getDimensionName().equals(queryDimensionDefine.getDimensionName()) && i.getLayoutType() == QueryLayoutType.LYT_CONDITION).findFirst();
                        if (!first.isPresent()) continue;
                        List<QuerySelectItem> selectItems1 = first.get().getSelectItems();
                        List collect1 = selectItems1.stream().map(i -> i.getCode()).collect(Collectors.toList());
                        boolean listEqual = QueryGridFactory.isListEqual(collect1, collect = selectItems.stream().map(i -> i.getCode()).collect(Collectors.toList()));
                        changed = !listEqual;
                    }
                }
                catch (Exception e) {
                    LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u67e5\u8be2\u5757\u7f13\u5b58\u5f02\u5e38", (String)("\u5757\u540d\u79f0\uff1a" + block.getTitle()));
                }
                if (changed) {
                    defina = new QueryGridDefination(block, isExport, isOnlyForm);
                    if (!isOnlyForm) {
                        this.put(gridKey, defina);
                    }
                } else {
                    defina.rows = null;
                    defina.cols = null;
                    defina.maxItemCount = 50;
                    defina.rowDimensions = null;
                    defina.colDimensions = null;
                    defina.setDimensions();
                    String formdata = block.getFormdata();
                    BeanUtils.copyProperties(block, defina.block);
                    BeanUtils.copyProperties(block.getBlockInfo(), defina.block.getBlockInfo());
                    defina.setMasterDimension();
                    defina.reloadDimData();
                    defina.block.setFormdata(formdata);
                    BeanUtils.copyProperties(defina.block, block);
                    BeanUtils.copyProperties(defina.block.getBlockInfo(), block.getBlockInfo());
                }
            } else {
                defina = new QueryGridDefination(block, isExport, isOnlyForm);
                if (!isOnlyForm) {
                    this.put(gridKey, defina);
                }
            }
            return defina;
        }
        catch (Exception ex) {
            return null;
        }
    }

    public static <E> boolean isListEqual(List<E> list1, List<E> list2) {
        if (list1 == list2) {
            return true;
        }
        if (list1 == null && list2 != null && list2.size() == 0 || list2 == null && list1 != null && list1.size() == 0) {
            return true;
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        return list1.containsAll(list2);
    }

    public QueryGridDefination getGridCache(QueryBlockDefine block, boolean isExport) {
        try {
            QueryGridDefination defina;
            String gridKey = QueryGridFactory.buildKey(block.getId(), false);
            if (this.containsKey(gridKey) && !isExport) {
                defina = this.get(gridKey);
                defina.rows = null;
                defina.cols = null;
                defina.maxItemCount = 50;
                defina.rowDimensions = null;
                defina.colDimensions = null;
                defina.setDimensions();
                String formdata = block.getFormdata();
                BeanUtils.copyProperties(block, defina.block);
                BeanUtils.copyProperties(block.getBlockInfo(), defina.block.getBlockInfo());
                defina.setMasterDimension();
                defina.reloadDimData();
                defina.block.setFormdata(formdata);
                BeanUtils.copyProperties(defina.block, block);
                BeanUtils.copyProperties(defina.block.getBlockInfo(), block.getBlockInfo());
            } else {
                defina = new QueryGridDefination(block, isExport);
                this.put(gridKey, defina);
            }
            return defina;
        }
        catch (Exception ex) {
            return null;
        }
    }

    public void removeCache(String blockId) {
        String gridKey = QueryGridFactory.buildKey(blockId, false);
        if (this.containsKey(gridKey)) {
            this.remove(gridKey);
        }
    }

    public void removeAllBlockCache(String modelId) {
        QueryBlockDefineDao blockDao = new QueryBlockDefineDao();
        List<QueryBlockDefine> blocks = blockDao.GetQueryBlockDefinesByModelId(modelId);
        for (QueryBlockDefine block : blocks) {
            this.removeCache(block.getId());
        }
    }

    public void removeAllLocalCache() {
        if (this.cacheManager instanceof LocalCacheManager) {
            ((LocalCacheManager)this.cacheManager).clearCache();
        }
    }
}

