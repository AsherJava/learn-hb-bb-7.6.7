/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.context.DataModelSyncCacheChannel
 *  com.jiuqi.va.context.DataModelSyncCacheDTO
 *  com.jiuqi.va.context.VaDataModelContextConfig
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelIndex
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 */
package com.jiuqi.va.datamodel.service.impl.help;

import com.jiuqi.va.context.DataModelSyncCacheChannel;
import com.jiuqi.va.context.DataModelSyncCacheDTO;
import com.jiuqi.va.context.VaDataModelContextConfig;
import com.jiuqi.va.datamodel.dao.VaDataModelPublishedDao;
import com.jiuqi.va.datamodel.domain.DataModelPublishedDO;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelIndex;
import com.jiuqi.va.domain.datamodel.DataModelType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VaDataModelCacheService {
    private static Logger logger = LoggerFactory.getLogger(VaDataModelCacheService.class);
    @Autowired
    private VaDataModelPublishedDao dataModelDao;
    @Autowired
    private List<DataModelSyncCacheChannel> dataModelCacheChannelList;
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, BigDecimal>> dataVerMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, DataModelDO>> dataCodeMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Boolean> lockMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Boolean> checkSyncMap = new ConcurrentHashMap();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void handleSyncCacheMsg(DataModelSyncCacheDTO dmsc) {
        String tenantName = dmsc.getTenantName();
        try {
            if (dmsc.isForceUpdate()) {
                dataCodeMap.remove(tenantName);
                return;
            }
            if (dmsc.getDataModel() == null) {
                return;
            }
            String modelName = dmsc.getDataModel().getName();
            if (modelName == null) {
                return;
            }
            if (dmsc.isRemove()) {
                Map currCodeCacheMap = dataCodeMap.get(tenantName);
                if (currCodeCacheMap != null) {
                    currCodeCacheMap.remove(modelName);
                }
                return;
            }
            ConcurrentHashMap<String, BigDecimal> tenantVer = dataVerMap.get(tenantName);
            if (tenantVer == null) {
                dataVerMap.putIfAbsent(tenantName, new ConcurrentHashMap());
                tenantVer = dataVerMap.get(tenantName);
            }
            tenantVer.put(modelName, dmsc.getVer());
        }
        catch (Throwable e) {
            logger.error("\u5efa\u6a21\u540c\u6b65\u6d88\u606f\u5f02\u5e38", e);
        }
        finally {
            if (EnvConfig.getCurrNodeId().equals(dmsc.getCurrNodeId())) {
                checkSyncMap.put(dmsc.getCheckKey(), true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void syncCache(String tenantName, boolean forceUpdate) {
        ConcurrentHashMap<String, Object> codeCacheMap = dataCodeMap.get(tenantName);
        if (codeCacheMap == null) {
            forceUpdate = true;
        }
        ConcurrentHashMap<String, BigDecimal> tenantVer = dataVerMap.get(tenantName);
        if (!forceUpdate && tenantVer != null && tenantVer.isEmpty()) {
            return;
        }
        HashMap<String, BigDecimal> currVerMap = null;
        BigDecimal startVer = null;
        if (tenantVer != null && !tenantVer.isEmpty() && !(currVerMap = new HashMap<String, BigDecimal>(tenantVer)).isEmpty()) {
            startVer = (BigDecimal)Collections.min(currVerMap.values());
        }
        if (!forceUpdate && startVer == null) {
            return;
        }
        boolean first = codeCacheMap == null;
        String lockKey = startVer == null ? tenantName : tenantName + startVer.toString();
        boolean locked = lockMap.putIfAbsent(lockKey, true) != null;
        try {
            if (locked) {
                while (lockMap.get(lockKey) != null) {
                    try {
                        Thread.sleep(50L);
                    }
                    catch (InterruptedException e) {
                        logger.error("dataModelCacheWaittingErro", e);
                        Thread.currentThread().interrupt();
                    }
                }
                return;
            }
            DataModelDTO param = new DataModelDTO();
            param.setTenantName(tenantName);
            boolean isAll = false;
            List<DataModelPublishedDO> listAll = null;
            if (forceUpdate || first || startVer == null) {
                listAll = this.dataModelDao.list(param);
                isAll = true;
            } else {
                param.setVer(startVer);
                listAll = this.dataModelDao.selectByStartVer(param);
            }
            if (listAll == null || listAll.isEmpty()) {
                if (isAll && !first && codeCacheMap != null) {
                    codeCacheMap.clear();
                }
                if (currVerMap != null) {
                    for (Map.Entry e : currVerMap.entrySet()) {
                        tenantVer.remove(e.getKey(), e.getValue());
                    }
                }
                return;
            }
            if (first) {
                codeCacheMap = new ConcurrentHashMap();
            }
            HashSet<String> codeSet = new HashSet<String>();
            DataModelDO dataModelDO = null;
            for (DataModelPublishedDO dataModelPublishedDO : listAll) {
                dataModelDO = this.convertData(dataModelPublishedDO);
                if (dataModelDO == null) continue;
                dataModelDO.setTenantName(tenantName);
                dataModelDO.setLocked(true);
                codeCacheMap.put(dataModelDO.getName(), dataModelDO);
                codeSet.add(dataModelDO.getName());
            }
            if (!first && isAll) {
                codeCacheMap.entrySet().removeIf(entry -> !codeSet.contains(entry.getKey()));
            }
            if (currVerMap != null) {
                for (Map.Entry entry2 : currVerMap.entrySet()) {
                    tenantVer.remove(entry2.getKey(), entry2.getValue());
                }
            }
            if (first) {
                dataCodeMap.put(tenantName, codeCacheMap);
            }
        }
        catch (Throwable e) {
            logger.error("\u5efa\u6a21\u7f13\u5b58\u540c\u6b65\u5f02\u5e38", e);
        }
        finally {
            if (!locked) {
                lockMap.remove(lockKey);
            }
        }
    }

    public DataModelDO convertData(DataModelPublishedDO data) {
        try {
            DataModelDO dmdo = (DataModelDO)JSONUtil.parseObject((String)data.getDefinedata(), DataModelDO.class);
            dmdo.setId(data.getId());
            dmdo.setBiztype(data.getBiztype());
            dmdo.setGroupcode(data.getGroupcode());
            if (dmdo.getBiztype() != null && dmdo.getBiztype() != DataModelType.BizType.BASEDATA && dmdo.getSubBiztype() == null) {
                if (dmdo.getBiztype() != DataModelType.BizType.BILL) {
                    dmdo.setSubBiztype(Integer.valueOf(dmdo.getBiztype().toString().hashCode()));
                } else if (data.getDefinedata().contains("MASTERID")) {
                    dmdo.setSubBiztype(Integer.valueOf(2));
                } else {
                    dmdo.setSubBiztype(Integer.valueOf(1));
                }
            }
            return dmdo;
        }
        catch (Exception e) {
            logger.error("\u7f13\u5b58\u5bf9\u8c61\u8f6c\u6362\u5f02\u5e38" + data.getName(), e);
            return null;
        }
    }

    public DataModelDO getByName(DataModelDTO param) {
        this.syncCache(param.getTenantName(), false);
        DataModelDO data = null;
        Map currCodeCacheMap = dataCodeMap.get(param.getTenantName());
        if (currCodeCacheMap != null) {
            data = (DataModelDO)currCodeCacheMap.get(param.getName());
            if (param.isDeepClone().booleanValue()) {
                data = this.cloneObj(data);
            }
        }
        return data;
    }

    public List<DataModelDO> list(DataModelDTO param) {
        this.syncCache(param.getTenantName(), false);
        ArrayList<DataModelDO> list = new ArrayList<DataModelDO>();
        ConcurrentHashMap<String, DataModelDO> currCodeCacheMap = dataCodeMap.get(param.getTenantName());
        if (currCodeCacheMap != null) {
            if (param.isDeepClone().booleanValue()) {
                DataModelDO data2 = null;
                for (DataModelDO data2 : currCodeCacheMap.values()) {
                    list.add(this.cloneObj(data2));
                }
            } else {
                list.addAll(currCodeCacheMap.values());
            }
        }
        return list;
    }

    private DataModelDO cloneObj(DataModelDO dataModelDO) {
        if (dataModelDO == null) {
            return null;
        }
        DataModelDO newData = new DataModelDO();
        newData.setId(dataModelDO.getId());
        newData.setName(dataModelDO.getName());
        newData.setTitle(dataModelDO.getTitle());
        newData.setGroupcode(dataModelDO.getGroupcode());
        newData.setBiztype(dataModelDO.getBiztype());
        newData.setSubBiztype(dataModelDO.getSubBiztype());
        newData.setRemark(dataModelDO.getRemark());
        if (dataModelDO.getExtInfo() != null) {
            HashMap extInfo = new HashMap();
            extInfo.putAll(dataModelDO.getExtInfo());
            newData.setExtInfo(extInfo);
        }
        ArrayList<DataModelColumn> newColumns = new ArrayList<DataModelColumn>();
        for (DataModelColumn dataModelColumn : dataModelDO.getColumns()) {
            DataModelColumn newColumn = new DataModelColumn();
            newColumn.setColumnName(dataModelColumn.getColumnName());
            newColumn.setColumnTitle(dataModelColumn.getColumnTitle());
            newColumn.setColumnType(dataModelColumn.getColumnType());
            newColumn.setLengths(dataModelColumn.getLengths());
            newColumn.setNullable(dataModelColumn.getNullable());
            newColumn.setPkey(dataModelColumn.getPkey());
            newColumn.setDefaultVal(dataModelColumn.getDefaultVal());
            newColumn.setMappingType(dataModelColumn.getMappingType());
            newColumn.setMapping(dataModelColumn.getMapping());
            newColumn.setColumnAttr(dataModelColumn.getColumnAttr());
            newColumns.add(newColumn);
        }
        newData.setColumns(newColumns);
        ArrayList<DataModelIndex> newIndedx = new ArrayList<DataModelIndex>();
        if (dataModelDO.getIndexConsts() != null) {
            for (DataModelIndex dataModelIndex : dataModelDO.getIndexConsts()) {
                DataModelIndex newIndex = new DataModelIndex();
                newIndex.setIndexName(dataModelIndex.getIndexName());
                newIndex.setUnique(dataModelIndex.getUnique());
                newIndex.setColumnList(dataModelIndex.getColumnList());
                newIndedx.add(newIndex);
            }
        }
        newData.setIndexConsts(newIndedx);
        return newData;
    }

    private void handleLocalChannel(DataModelSyncCacheDTO dmscd) {
        for (DataModelSyncCacheChannel dataModelSyncCacheChannel : this.dataModelCacheChannelList) {
            try {
                dataModelSyncCacheChannel.execute(dmscd);
            }
            catch (Throwable e) {
                logger.error("\u5efa\u6a21\u7f13\u5b58\u6269\u5c55\u6267\u884c\u5931\u8d25", e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void pushSyncMsg(DataModelSyncCacheDTO dmscd) {
        BigDecimal ver = dmscd.getVer();
        if (ver == null) {
            ver = OrderNumUtil.getOrderNumByCurrentTimeMillis();
            dmscd.setVer(ver);
        }
        String modelName = "";
        DataModelDO dataModel = dmscd.getDataModel();
        if (dataModel != null && dataModel.getName() != null) {
            modelName = dataModel.getName();
            DataModelDO msgData = new DataModelDO();
            msgData.setTenantName(dmscd.getTenantName());
            msgData.setName(modelName);
            msgData.setBiztype(dataModel.getBiztype());
            msgData.setSubBiztype(dataModel.getSubBiztype());
            dmscd.setDataModel(msgData);
        }
        dmscd.setCurrNodeId(EnvConfig.getCurrNodeId());
        dmscd.setRetry(0);
        try {
            if (!EnvConfig.getRedisEnable()) {
                this.handleLocalChannel(dmscd);
                return;
            }
            if (!this.tryPushBroadcast(dmscd)) {
                logger.error(modelName + "\u5efa\u6a21\u7f13\u5b58\u5e7f\u64ad\u9a8c\u8bc1\u6b21\u6570\u8fbe\u5230\u4e0a\u9650\uff0c \u4ec5\u5904\u7406\u672c\u5730\u7f13\u5b58\u3002");
                this.handleSyncCacheMsg(dmscd);
            }
        }
        catch (Throwable e) {
            logger.error(modelName + "\u5efa\u6a21\u7f13\u5b58\u5e7f\u64ad\u5931\u8d25", e);
        }
        finally {
            checkSyncMap.remove(dmscd.getCheckKey());
        }
    }

    private boolean tryPushBroadcast(DataModelSyncCacheDTO dmscd) {
        int cnt;
        if (dmscd.getRetry() > 3) {
            return false;
        }
        EnvConfig.sendRedisMsg((String)VaDataModelContextConfig.getDataModelSyncCachePub(), (String)JSONUtil.toJSONString((Object)dmscd));
        String checkKey = dmscd.getCheckKey();
        for (cnt = 0; !checkSyncMap.containsKey(checkKey) && cnt < 300; ++cnt) {
            try {
                Thread.sleep(cnt < 10 ? 5L : (long)(cnt < 100 ? 50 : 500));
                continue;
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (cnt == 300) {
            dmscd.setRetry(dmscd.getRetry() + 1);
            return this.tryPushBroadcast(dmscd);
        }
        return true;
    }
}

