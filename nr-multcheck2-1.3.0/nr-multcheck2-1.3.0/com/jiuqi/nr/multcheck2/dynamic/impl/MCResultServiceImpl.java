/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 */
package com.jiuqi.nr.multcheck2.dynamic.impl;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResScheme;
import com.jiuqi.nr.multcheck2.common.SchemeExecuteResult;
import com.jiuqi.nr.multcheck2.common.SerializeUtil;
import com.jiuqi.nr.multcheck2.dynamic.IMCResultService;
import com.jiuqi.nr.multcheck2.provider.FailedOrgInfo;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.nr.multcheck2.service.res.FileItemService;
import com.jiuqi.nr.multcheck2.service.res.FileRecordService;
import com.jiuqi.nr.multcheck2.service.res.FileSchemeService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MCResultServiceImpl
implements IMCResultService {
    private static final Logger logger = LoggerFactory.getLogger(MCResultServiceImpl.class);
    public static final int LIMIT = 1000;
    @Autowired
    private RunTimeAuthViewController runTimeAuthView;
    @Autowired
    IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    FileRecordService fileRecordService;
    @Autowired
    FileSchemeService fileSchemeService;
    @Autowired
    FileItemService fileItemService;
    @Autowired
    com.jiuqi.nr.multcheck2.service.IMCResultService resultService;
    @Autowired
    IMCSchemeService schemeService;

    @Override
    public List<MultcheckResItem> getRecordByOrg(String task, String period, String orgCode, String scheme, String itemType) throws Exception {
        long t = System.currentTimeMillis();
        if (!(StringUtils.hasText(task) && StringUtils.hasText(period) && StringUtils.hasText(orgCode) && StringUtils.hasText(scheme) && StringUtils.hasText(itemType))) {
            return null;
        }
        logger.debug("--------------task=" + task + "period=" + period + "orgCode=" + orgCode + "scheme=" + scheme + "itemType=" + itemType);
        TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName_record = this.resultService.getTableName("NR_MCR_RECORD_", taskDefine.getKey(), dataScheme);
        String tableName_scheme = this.resultService.getTableName("NR_MCR_SCHEME_", taskDefine.getKey(), dataScheme);
        String tableName_item = this.resultService.getTableName("NR_MCR_ITEM_", taskDefine.getKey(), dataScheme);
        List<MultcheckItem> itemInfoList = this.schemeService.getItemInfoList(scheme);
        if (CollectionUtils.isEmpty(itemInfoList)) {
            logger.debug("--------------\u5ba1\u6838\u65b9\u6848\u4e0b\u5ba1\u6838\u9879\u662f\u7a7a\u7684");
            return null;
        }
        HashSet<String> itemInfoSet = new HashSet<String>();
        for (MultcheckItem item : itemInfoList) {
            if (!item.getType().equals(itemType)) continue;
            itemInfoSet.add(item.getKey());
        }
        if (CollectionUtils.isEmpty(itemInfoSet)) {
            logger.debug("--------------\u5ba1\u6838\u7c7b\u578b\u4e0b\u4e0d\u5b58\u5728\u5ba1\u6838\u9879");
            return null;
        }
        logger.debug("--------------\u8fc7\u5f55\u67e5\u8be2\u5ba1\u6838\u9879=" + itemInfoSet.size());
        List<String> records = this.fileRecordService.getRecordByTaskPeriodScheme(tableName_record, task, period, null, null, null);
        if (CollectionUtils.isEmpty(records)) {
            logger.debug("--------------\u5f53\u524d\u4efb\u52a1\u65f6\u671f\u4e0b\u5ba1\u6838\u8bb0\u5f55\u4e3a\u7a7a\uff1a\uff1a\uff1atableName_record=" + tableName_record);
            return null;
        }
        String record = null;
        int size = records.size();
        logger.debug("--------------\u5f00\u59cb\u5bfb\u627e\u6700\u540e\u4e00\u6b21\u5ba1\u6838\u8bb0\u5f55\uff1a\uff1a\u5f53\u524d\u4efb\u52a1\u65f6\u671f\u4e0b\u5ba1\u6838\u8bb0\u5f55\u6761\u6570=" + size + ":::tableName_record=" + tableName_record + ":::orgCode=" + orgCode + ":::t=" + (System.currentTimeMillis() - t));
        t = System.currentTimeMillis();
        if (size <= 1000) {
            record = this.doFindRecordByOrg(records, scheme, orgCode, tableName_scheme, itemInfoSet, tableName_item);
        } else {
            for (int i = 0; i < size && !StringUtils.hasText(record = this.doFindRecordByOrg(records.subList(i, Math.min(i + 1000, size)), scheme, orgCode, tableName_scheme, itemInfoSet, tableName_item)); i += 1000) {
            }
        }
        ArrayList<MultcheckResItem> res = new ArrayList<MultcheckResItem>();
        if (!StringUtils.hasText(record)) {
            logger.debug("--------------\u6700\u540e\u4e00\u6b21\u5ba1\u6838\u8bb0\u5f55\u672a\u627e\u5230:::scheme=" + scheme + ":::orgCode=" + orgCode + ":::t=" + (System.currentTimeMillis() - t));
            return res;
        }
        List<MultcheckResItem> resItems = this.fileItemService.getItemsBbyRecordAndKeys(record, itemInfoSet, tableName_item);
        if (!CollectionUtils.isEmpty(resItems)) {
            res.addAll(resItems);
        }
        logger.debug("--------------\u547d\u4e2d\u5ba1\u6838\u9879\u8bb0\u5f55=" + res.size() + ":::orgCode=" + orgCode + ":::t=" + (System.currentTimeMillis() - t));
        return res;
    }

    private String doFindRecordByOrg(List<String> records, String scheme, String orgCode, String tableName_scheme, Set<String> itemInfoSet, String tableName_item) {
        if (CollectionUtils.isEmpty(records)) {
            return null;
        }
        long t = System.currentTimeMillis();
        Set<String> recordByItemSet = this.fileItemService.filterRecordByItem(records, itemInfoSet, tableName_item);
        if (CollectionUtils.isEmpty(recordByItemSet)) {
            return null;
        }
        logger.debug("--------------\u5ba1\u6838\u8bb0\u5f55=" + records.size() + "\u4e0b\uff0c\u6839\u636e\u5ba1\u6838\u9879\u8fc7\u6ee4\u540e\u7684record=" + recordByItemSet.size() + ":::orgCode=" + orgCode + ":::t=" + (System.currentTimeMillis() - t));
        t = System.currentTimeMillis();
        ArrayList<String> recordByItemList = new ArrayList<String>();
        for (String record : records) {
            if (!recordByItemSet.contains(record)) continue;
            recordByItemList.add(record);
        }
        int size = recordByItemList.size();
        for (int i = 0; i < size; i += 10) {
            List<String> list = recordByItemList.subList(i, Math.min(i + 10, size));
            Map<String, MultcheckResScheme> resSchemeMap = this.fileSchemeService.getByRecordsScheme(list, scheme, tableName_scheme);
            for (String record : list) {
                MultcheckResScheme resScheme = resSchemeMap.get(record);
                try {
                    if (!this.findByOrg(orgCode, resScheme)) continue;
                    logger.debug(i + "--------------\u6700\u540e\u4e00\u6b21\u5ba1\u6838\u8bb0\u5f55\u547d\u4e2d=" + record + ":::scheme=" + scheme + ":::orgCode=" + orgCode + ":::t=" + (System.currentTimeMillis() - t));
                    return record;
                }
                catch (Exception e) {
                    logger.error("--------------doFindRecordByOrg\u65b9\u6848\u5173\u8054\u5355\u4f4d\u7ed3\u679c\u8f6c\u6362\u5931\u8d25\uff1a\uff1a" + resScheme.getSchemeKey() + "orgs=" + resScheme.getOrgs(), e);
                }
            }
        }
        return null;
    }

    private boolean findByOrg(String orgCode, MultcheckResScheme resScheme) throws Exception {
        if (resScheme == null) {
            return false;
        }
        SchemeExecuteResult eResult = SerializeUtil.deserializeFromJson(resScheme.getOrgs(), SchemeExecuteResult.class);
        List<String> successList = eResult.getSuccessList();
        if (!CollectionUtils.isEmpty(successList) && successList.contains(orgCode)) {
            return true;
        }
        Map<String, Map<String, FailedOrgInfo>> failedMap = eResult.getFailedMap();
        return !CollectionUtils.isEmpty(failedMap) && failedMap.containsKey(orgCode);
    }

    @Override
    public Map<String, List<MultcheckResItem>> getRecordByOrgList(String task, String period, List<String> orgCodesList, String scheme, String itemType) throws Exception {
        Map<String, List<MultcheckResItem>> itemsBbyRecordsAndKeys;
        long t = System.currentTimeMillis();
        if (!(StringUtils.hasText(task) && StringUtils.hasText(period) && !CollectionUtils.isEmpty(orgCodesList) && StringUtils.hasText(scheme) && StringUtils.hasText(itemType))) {
            return null;
        }
        logger.debug("--------------task=" + task + "period=" + period + "orgCodes=" + orgCodesList.size() + "scheme=" + scheme + "itemType=" + itemType);
        TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(task);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableName_record = this.resultService.getTableName("NR_MCR_RECORD_", taskDefine.getKey(), dataScheme);
        String tableName_scheme = this.resultService.getTableName("NR_MCR_SCHEME_", taskDefine.getKey(), dataScheme);
        String tableName_item = this.resultService.getTableName("NR_MCR_ITEM_", taskDefine.getKey(), dataScheme);
        HashSet<String> orgCodes = new HashSet<String>(orgCodesList);
        List<MultcheckItem> itemInfoList = this.schemeService.getItemInfoList(scheme);
        if (CollectionUtils.isEmpty(itemInfoList)) {
            logger.debug("--------------\u5ba1\u6838\u65b9\u6848\u4e0b\u5ba1\u6838\u9879\u662f\u7a7a\u7684");
            return null;
        }
        HashSet<String> itemInfoSet = new HashSet<String>();
        for (MultcheckItem item : itemInfoList) {
            if (!item.getType().equals(itemType)) continue;
            itemInfoSet.add(item.getKey());
        }
        if (CollectionUtils.isEmpty(itemInfoSet)) {
            logger.debug("--------------\u5ba1\u6838\u7c7b\u578b\u4e0b\u4e0d\u5b58\u5728\u5ba1\u6838\u9879");
            return null;
        }
        List<String> records = this.fileRecordService.getRecordByTaskPeriodScheme(tableName_record, task, period, null, null, null);
        if (CollectionUtils.isEmpty(records)) {
            logger.debug("--------------\u5f53\u524d\u4efb\u52a1\u65f6\u671f\u4e0b\u5ba1\u6838\u8bb0\u5f55\u4e3a\u7a7a\uff1a\uff1a\uff1atableName_record=" + tableName_record);
            return null;
        }
        int size = records.size();
        logger.debug("--------------\u5f00\u59cb\u5bfb\u627e\u6700\u540e\u4e00\u6b21\u5ba1\u6838\u8bb0\u5f55\uff1a\uff1a\u5f53\u524d\u4efb\u52a1\u65f6\u671f\u4e0b\u5ba1\u6838\u8bb0\u5f55\u6761\u6570=" + size + ":::tableName_record=" + tableName_record + ":::orgCodes=" + orgCodes.size());
        HashMap<String, Set<String>> recordOrgListMap = new HashMap<String, Set<String>>();
        if (size <= 1000) {
            this.doFindRecordByOrgList(records, scheme, orgCodes, tableName_scheme, itemInfoSet, tableName_item, recordOrgListMap);
        } else {
            for (int i = 0; i < size; i += 1000) {
                this.doFindRecordByOrgList(records.subList(i, Math.min(i + 1000, size)), scheme, orgCodes, tableName_scheme, itemInfoSet, tableName_item, recordOrgListMap);
                if (CollectionUtils.isEmpty(orgCodes)) break;
            }
        }
        logger.debug("--------------\u547d\u4e2d\u5ba1\u6838\u8bb0\u5f55=" + recordOrgListMap.size());
        HashMap<String, List<MultcheckResItem>> res = new HashMap<String, List<MultcheckResItem>>();
        if (!CollectionUtils.isEmpty(recordOrgListMap) && !CollectionUtils.isEmpty(itemsBbyRecordsAndKeys = this.fileItemService.getItemsBbyRecordsAndKeys(recordOrgListMap.keySet(), itemInfoSet, tableName_item))) {
            for (String record : itemsBbyRecordsAndKeys.keySet()) {
                List<MultcheckResItem> resItems = itemsBbyRecordsAndKeys.get(record);
                Set orgs = (Set)recordOrgListMap.get(record);
                for (String org : orgs) {
                    res.put(org, resItems);
                }
            }
        }
        logger.debug("--------------\u547d\u4e2d\u5355\u4f4d\u8bb0\u5f55=" + res.size() + ":::t=" + (System.currentTimeMillis() - t));
        return res;
    }

    private void doFindRecordByOrgList(List<String> records, String scheme, Set<String> orgCodes, String tableName_scheme, Set<String> itemInfoSet, String tableName_item, Map<String, Set<String>> recordOrgListMap) {
        if (CollectionUtils.isEmpty(records)) {
            return;
        }
        long t = System.currentTimeMillis();
        Set<String> recordByItemSet = this.fileItemService.filterRecordByItem(records, itemInfoSet, tableName_item);
        if (CollectionUtils.isEmpty(recordByItemSet)) {
            return;
        }
        logger.debug("--------------\u5ba1\u6838\u8bb0\u5f55=" + records.size() + "\u4e0b\uff0c\u6839\u636e\u5ba1\u6838\u9879\u8fc7\u6ee4\u540e\u7684record=" + recordByItemSet.size() + ":::orgCodes=" + orgCodes.size() + ":::t=" + (System.currentTimeMillis() - t));
        t = System.currentTimeMillis();
        ArrayList<String> recordByItemList = new ArrayList<String>();
        for (String record : records) {
            if (!recordByItemSet.contains(record)) continue;
            recordByItemList.add(record);
        }
        int size = recordByItemList.size();
        for (int i = 0; i < size; i += 10) {
            List<String> list = recordByItemList.subList(i, Math.min(i + 10, size));
            Map<String, MultcheckResScheme> resSchemeMap = this.fileSchemeService.getByRecordsScheme(list, scheme, tableName_scheme);
            for (String record : list) {
                MultcheckResScheme resScheme = resSchemeMap.get(record);
                try {
                    if (resScheme == null) continue;
                    SchemeExecuteResult eResult = SerializeUtil.deserializeFromJson(resScheme.getOrgs(), SchemeExecuteResult.class);
                    this.checkResOrgList(orgCodes, recordOrgListMap, record, new HashSet<String>(eResult.getSuccessList()));
                    logger.debug("--------------\u5ba1\u6838\u8bb0\u5f55=" + record + "\u4e0b\uff0c\u6839\u636e\u5ba1\u6838\u9879\u8fc7\u6ee4\u540e\u7684record=" + recordByItemSet.size() + ":::orgCodes=" + orgCodes.size() + ":::t=" + (System.currentTimeMillis() - t));
                    t = System.currentTimeMillis();
                    if (CollectionUtils.isEmpty(orgCodes)) {
                        logger.debug("--------------1record=" + record + "\u7ed3\u675f");
                        return;
                    }
                    this.checkResOrgList(orgCodes, recordOrgListMap, record, eResult.getFailedMap().keySet());
                    logger.debug("--------------\u5ba1\u6838\u8bb0\u5f55=" + record + "\u4e0b\uff0c\u6839\u636e\u5ba1\u6838\u9879\u8fc7\u6ee4\u540e\u7684record=" + recordByItemSet.size() + ":::orgCodes=" + orgCodes.size() + ":::t=" + (System.currentTimeMillis() - t));
                    t = System.currentTimeMillis();
                    if (!CollectionUtils.isEmpty(orgCodes)) continue;
                    logger.debug("--------------2record=" + record + "\u7ed3\u675f");
                    return;
                }
                catch (Exception e) {
                    logger.error("--------------doFindRecordByOrgs\u65b9\u6848\u5173\u8054\u5355\u4f4d\u7ed3\u679c\u8f6c\u6362\u5931\u8d25\uff1a\uff1a" + resScheme.getKey(), e);
                }
            }
        }
        logger.debug("--------------\u5ba1\u6838\u8bb0\u5f55=" + records.size() + "\u4e0b\uff0c\u6839\u636e\u5ba1\u6838\u9879\u8fc7\u6ee4\u540e\u7684record=" + recordByItemSet.size() + ":::orgCodes=" + orgCodes.size() + ":::t=" + (System.currentTimeMillis() - t));
    }

    private void checkResOrgList(Set<String> orgCodes, Map<String, Set<String>> recordOrgListMap, String record, Set<String> resOrgSet) {
        if (!CollectionUtils.isEmpty(resOrgSet)) {
            resOrgSet.retainAll(orgCodes);
            if (!CollectionUtils.isEmpty(resOrgSet)) {
                Set<String> recordOrgList = recordOrgListMap.get(record);
                if (recordOrgList != null) {
                    recordOrgList.addAll(resOrgSet);
                } else {
                    recordOrgListMap.put(record, resOrgSet);
                }
                orgCodes.removeAll(resOrgSet);
            }
        }
    }
}

