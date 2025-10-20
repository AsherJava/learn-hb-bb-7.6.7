/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.DataTableDefine
 *  com.jiuqi.va.biz.intf.data.DataTableNodeContainer
 *  com.jiuqi.va.biz.intf.data.DataTableType
 *  com.jiuqi.va.domain.biz.DeployDTO
 *  com.jiuqi.va.domain.biz.DeployType
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  org.springframework.dao.DuplicateKeyException
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.bill.service.impl;

import com.jiuqi.va.bill.catche.MetaInfoCache;
import com.jiuqi.va.bill.config.VaBillCoreConfig;
import com.jiuqi.va.bill.dao.MetaInfoExtendBillDao;
import com.jiuqi.va.bill.domain.MetaInfoExtendBillDO;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.service.MetaInfoService;
import com.jiuqi.va.bill.utils.ConcurrentHashMapUtils;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.data.DataTableNodeContainer;
import com.jiuqi.va.biz.intf.data.DataTableType;
import com.jiuqi.va.domain.biz.DeployDTO;
import com.jiuqi.va.domain.biz.DeployType;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class MetaInfoServiceImpl
implements MetaInfoService {
    private static final Logger log = LoggerFactory.getLogger(MetaInfoServiceImpl.class);
    private static final String NAME = "name";
    private static final String SUBS = "subs";
    @Autowired
    private MetaInfoExtendBillDao extendBillDao;
    @Autowired
    private BillDefineService billDefineService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void syncMetaInfo(DeployDTO deployDTO) {
        BillDefine billDefine;
        DeployType deployType = deployDTO.getDeployType();
        String tenantName = ShiroUtil.getTenantName();
        String uniqueCode = deployDTO.getUniqueCode();
        String redisKey = tenantName + ":" + uniqueCode + ":ExtendBill";
        try {
            billDefine = (BillDefine)JSONUtil.parseObject((String)deployDTO.getDatas(), BillDefine.class);
        }
        catch (Exception e) {
            log.error("\u89e3\u6790\u5355\u636e\u5b9a\u4e49\u5931\u8d25" + e.getMessage(), e);
            this.deleteDataCache(uniqueCode, tenantName, redisKey);
            return;
        }
        if (DeployType.DELETE.equals((Object)deployType)) {
            this.deleteDataCache(uniqueCode, tenantName, redisKey);
        } else {
            try {
                MetaInfoExtendBillDO metaInfoExtendBillDO = new MetaInfoExtendBillDO();
                metaInfoExtendBillDO.setUniquecode(uniqueCode);
                this.extendBillDao.delete((Object)metaInfoExtendBillDO);
                Map<String, Object> tablesName = this.insertMetaInfo(billDefine, metaInfoExtendBillDO);
                this.addMetaInfoCache(redisKey, tablesName, tenantName, uniqueCode);
            }
            catch (Exception e) {
                log.error("\u66f4\u65b0\u8868\u5b9a\u4e49\u7f13\u5b58\u5931\u8d25" + e.getMessage(), e);
                this.deleteDataCache(uniqueCode, tenantName, redisKey);
            }
        }
    }

    private Map<String, Object> insertMetaInfo(BillDefine billDefine, MetaInfoExtendBillDO metaInfoExtendBillDO) {
        DataTableNodeContainer tables = billDefine.getData().getTables();
        try {
            DataTableDefine masterTable = (DataTableDefine)tables.getMasterTable();
            String masterTableTableName = masterTable.getTableName();
            metaInfoExtendBillDO.setId(UUID.randomUUID());
            metaInfoExtendBillDO.setTablename(masterTableTableName);
            this.extendBillDao.insert((Object)metaInfoExtendBillDO);
            tables.forEach((i, o) -> {
                if (o.getTableName().equals(masterTableTableName) || !DataTableType.DATA.equals((Object)o.getTableType())) {
                    return;
                }
                metaInfoExtendBillDO.setId(UUID.randomUUID());
                metaInfoExtendBillDO.setTablename(o.getTableName());
                metaInfoExtendBillDO.setParentname(((DataTableDefine)tables.get(o.getParentId())).getTableName());
                this.extendBillDao.insert((Object)metaInfoExtendBillDO);
            });
        }
        catch (DuplicateKeyException masterTable) {
            // empty catch block
        }
        HashMap nodeMap = new HashMap();
        tables.forEach((i, o) -> {
            if (!DataTableType.DATA.equals((Object)o.getTableType())) {
                return;
            }
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put(NAME, o.getTableName());
            data.put(SUBS, new ArrayList());
            nodeMap.put(o.getId(), data);
        });
        tables.forEach((i, o) -> {
            if (o.getParentId() != null && DataTableType.DATA.equals((Object)o.getTableType())) {
                Map map = (Map)nodeMap.get(o.getParentId());
                List subs = (List)map.get(SUBS);
                subs.add(nodeMap.get(o.getId()));
            }
        });
        return (Map)nodeMap.get(((DataTableDefine)tables.getMasterTable()).getId());
    }

    private void addMetaInfoCache(String redisKey, Map<String, Object> tablesName, String tenantName, String uniqueCode) {
        if (VaBillCoreConfig.isRedisEnable()) {
            this.redisTemplate.opsForValue().set((Object)redisKey, tablesName);
        } else {
            Map tenantCache = ConcurrentHashMapUtils.computeIfAbsent(MetaInfoCache.tenantDesignTableCache, tenantName, k -> new ConcurrentHashMap());
            tenantCache.put(uniqueCode, tablesName);
            MetaInfoCache.tenantDesignTableCache.put(tenantName, tenantCache);
        }
    }

    private void deleteDataCache(String uniqueCode, String tenantName, String redisKey) {
        MetaInfoExtendBillDO metaInfoExtendBillDO = new MetaInfoExtendBillDO();
        metaInfoExtendBillDO.setUniquecode(uniqueCode);
        this.extendBillDao.delete((Object)metaInfoExtendBillDO);
        if (VaBillCoreConfig.isRedisEnable()) {
            this.redisTemplate.delete((Object)redisKey);
        } else {
            Map<String, Map<String, Object>> tenantCache = MetaInfoCache.tenantDesignTableCache.get(tenantName);
            if (tenantCache != null) {
                tenantCache.remove(uniqueCode);
            }
        }
    }

    @Override
    public Map<String, Object> getTablesName(String uniqueCode) {
        String tenantName = ShiroUtil.getTenantName();
        String redisKey = tenantName + ":" + uniqueCode + ":ExtendBill";
        Map<String, Object> cacheData = null;
        if (VaBillCoreConfig.isRedisEnable()) {
            cacheData = (Map<String, Object>)this.redisTemplate.opsForValue().get((Object)redisKey);
        } else {
            Map<String, Map<String, Object>> tenantCache = MetaInfoCache.tenantDesignTableCache.get(tenantName);
            if (tenantCache != null) {
                cacheData = tenantCache.get(uniqueCode);
            }
        }
        if (!CollectionUtils.isEmpty(cacheData)) {
            return cacheData;
        }
        MetaInfoExtendBillDO metaInfoExtendBillDO = new MetaInfoExtendBillDO();
        metaInfoExtendBillDO.setUniquecode(uniqueCode);
        List list = this.extendBillDao.select((Object)metaInfoExtendBillDO);
        if (CollectionUtils.isEmpty(list)) {
            BillDefine define;
            try {
                define = this.billDefineService.getDefine(uniqueCode);
            }
            catch (Exception e) {
                log.error("\u83b7\u53d6\u5b9a\u4e49\u5931\u8d25" + e.getMessage(), e);
                return null;
            }
            if (define != null) {
                Map<String, Object> tablesName = this.insertMetaInfo(define, metaInfoExtendBillDO);
                this.addMetaInfoCache(redisKey, tablesName, tenantName, uniqueCode);
                return tablesName;
            }
            log.error("\u83b7\u53d6\u4e3b\u8868\u540d\u79f0\u5931\u8d25\uff0c\u672a\u627e\u5230\u5b9a\u4e49");
            return null;
        }
        HashMap nodeMap = new HashMap();
        String masterName = "";
        for (MetaInfoExtendBillDO node : list) {
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put(NAME, node.getTablename());
            data.put(SUBS, new ArrayList());
            nodeMap.put(node.getTablename(), data);
        }
        for (MetaInfoExtendBillDO infoExtendBillDO : list) {
            if (infoExtendBillDO.getParentname() != null) {
                Map map = (Map)nodeMap.get(infoExtendBillDO.getParentname());
                List subs = (List)map.get(SUBS);
                subs.add(nodeMap.get(infoExtendBillDO.getTablename()));
                continue;
            }
            masterName = infoExtendBillDO.getTablename();
        }
        Map objectMap = (Map)nodeMap.get(masterName);
        this.addMetaInfoCache(redisKey, objectMap, tenantName, uniqueCode);
        return objectMap;
    }
}

