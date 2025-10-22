/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemePeriodService
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessCaches;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.AccessItem;
import com.jiuqi.nr.data.access.param.AccessParam;
import com.jiuqi.nr.data.access.param.BatchAccessFormMerge;
import com.jiuqi.nr.data.access.param.CanAccessBatchMergeAccess;
import com.jiuqi.nr.data.access.param.FormBatchAccessCache;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.param.IBatchMergeAccess;
import com.jiuqi.nr.data.access.param.UnitBatchAccessCache;
import com.jiuqi.nr.data.access.param.ZbAccessCaches;
import com.jiuqi.nr.data.access.service.IDataAccessExtraResultService;
import com.jiuqi.nr.data.access.service.IDataAccessItemBaseService;
import com.jiuqi.nr.data.access.service.IDataAccessItemService;
import com.jiuqi.nr.data.access.service.IDataExtendAccessItemService;
import com.jiuqi.nr.data.access.service.TaskDimensionFilterFactory;
import com.jiuqi.nr.data.access.service.impl.AbstractCacheManager;
import com.jiuqi.nr.data.access.service.impl.AccessItemServiceCollector;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemePeriodService;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessCacheManager
extends AbstractCacheManager {
    private static final Logger logger = LoggerFactory.getLogger(AccessCacheManager.class);

    public AccessCacheManager(List<IDataAccessExtraResultService> accessExtraResultController, AccessItemServiceCollector accessItemCollector, IRunTimeViewController runtimeView, TaskDimensionFilterFactory filterFactory, IRuntimeFormSchemePeriodService periodService) {
        super(accessItemCollector, accessExtraResultController, runtimeView, filterFactory, periodService);
    }

    public AccessCacheManager(List<IDataAccessExtraResultService> accessExtraResultController, AccessItemServiceCollector accessItemCollector, IRunTimeViewController runtimeView, TaskDimensionFilterFactory filterFactory, Set<String> ignoreAccessItems, IRuntimeFormSchemePeriodService periodService) {
        super(accessItemCollector, accessExtraResultController, runtimeView, filterFactory, ignoreAccessItems, periodService);
    }

    public AccessCacheManager(List<IDataAccessExtraResultService> accessExtraResultController, AccessItemServiceCollector accessItemCollector, IRunTimeViewController runtimeView, TaskDimensionFilterFactory filterFactory, List<String> taskKeys, Set<String> ignoreAccessItems, IRuntimeFormSchemePeriodService periodService) {
        super(accessItemCollector, accessExtraResultController, runtimeView, filterFactory, taskKeys, ignoreAccessItems, periodService);
    }

    public AccessCaches visible(String taskKey, String formSchemeKey, DimensionCollection masterKeys, DimensionCombination masterKey, List<String> formKeys, String formKey) {
        IBatchAccess batchReadable;
        AccessCode accessCode;
        boolean enable;
        HashMap<String, IBatchAccess> batchAccessMap = new HashMap<String, IBatchAccess>();
        List<IDataAccessItemService> list = this.getDataAccessItemList();
        AccessCode item = new AccessCode(null, "1");
        for (IDataAccessItemService service : list) {
            if (this.needIgnoreItem(service.group())) continue;
            enable = service.isEnable(taskKey, formSchemeKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-visible-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:taskKey:[%s];formSchemeKey:[%s]; \u662f\u5426\u542f\u7528\u6743\u9650:[%s]", service.name(), taskKey, formSchemeKey, enable));
            }
            if (!enable) continue;
            if (batchAccessMap.containsKey(service.name())) {
                accessCode = ((IBatchAccess)batchAccessMap.get(service.name())).getAccessCode(masterKey, formKey);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-visible-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u4ecemap\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];formKey:[%s];\u7ed3\u679c:[%s]", service.name(), masterKey, formKey, accessCode.getCode()));
                }
            } else {
                batchReadable = service.getBatchVisible(formSchemeKey, masterKeys, formKeys);
                if (batchReadable == null) {
                    batchReadable = this.emptyAccessCache(service, formSchemeKey);
                }
                batchAccessMap.put(service.name(), batchReadable);
                accessCode = batchReadable.getAccessCode(masterKey, formKey);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-visible-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u76f4\u63a5\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];formKey:[%s];\u7ed3\u679c:[%s]", service.name(), masterKey, formKey, accessCode.getCode()));
                }
            }
            if (accessCode.getCode() == "1") continue;
            item = new AccessCode(service.name(), accessCode.getCode());
        }
        for (IDataExtendAccessItemService extendController : this.getDataExtendAccessItemList()) {
            if (this.needIgnoreItem(extendController.group())) continue;
            enable = extendController.isEnable(taskKey, formSchemeKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-visible-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:taskKey:[%s];formSchemeKey:[%s]; \u662f\u5426\u542f\u7528\u6743\u9650:[%s]", extendController.name(), taskKey, formSchemeKey, enable));
            }
            if (!enable) continue;
            if (batchAccessMap.containsKey(extendController.name())) {
                accessCode = ((IBatchAccess)batchAccessMap.get(extendController.name())).getAccessCode(masterKey, formKey);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-visible-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u4ecemap\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];formKey:[%s];\u7ed3\u679c:[%s]", extendController.name(), masterKey, formKey, accessCode.getCode()));
                }
            } else {
                batchReadable = extendController.getBatchVisible(formSchemeKey, masterKeys, formKeys);
                if (batchReadable == null) {
                    batchReadable = this.emptyAccessCache(extendController, formSchemeKey);
                }
                batchAccessMap.put(extendController.name(), batchReadable);
                accessCode = batchReadable.getAccessCode(masterKey, formKey);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-visible-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u76f4\u63a5\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];formKey:[%s];\u7ed3\u679c:[%s]", extendController.name(), masterKey, formKey, accessCode.getCode()));
                }
            }
            if (accessCode.getCode() == "1") continue;
            item = new AccessCode(extendController.name(), accessCode.getCode());
        }
        return new AccessCaches(batchAccessMap, item);
    }

    public AccessCaches readable(String taskKey, String formSchemeKey, DimensionCollection masterKeys, DimensionCombination masterKey, List<String> formKeys, String formKey) {
        IBatchAccess batchReadable;
        AccessCode accessCode;
        boolean enable;
        HashMap<String, IBatchAccess> batchAccessMap = new HashMap<String, IBatchAccess>();
        List<IDataAccessItemService> list = this.getDataAccessItemList();
        AccessCode item = new AccessCode(null, "1");
        for (IDataAccessItemService service : list) {
            if (this.needIgnoreItem(service.group())) continue;
            enable = service.isEnable(taskKey, formSchemeKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-readable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:taskKey:[%s];formSchemeKey:[%s]; \u662f\u5426\u542f\u7528\u6743\u9650:[%s]", service.name(), taskKey, formSchemeKey, enable));
            }
            if (!enable) continue;
            if (batchAccessMap.containsKey(service.name())) {
                accessCode = ((IBatchAccess)batchAccessMap.get(service.name())).getAccessCode(masterKey, formKey);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-readable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u4ecemap\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];formKey:[%s];\u7ed3\u679c:[%s]", service.name(), masterKey, formKey, accessCode.getCode()));
                }
            } else {
                batchReadable = service.getBatchReadable(formSchemeKey, masterKeys, formKeys);
                if (batchReadable == null) {
                    batchReadable = this.emptyAccessCache(service, formSchemeKey);
                }
                batchAccessMap.put(service.name(), batchReadable);
                accessCode = batchReadable.getAccessCode(masterKey, formKey);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-readable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u76f4\u63a5\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];formKey:[%s];\u7ed3\u679c:[%s]", service.name(), masterKey, formKey, accessCode.getCode()));
                }
            }
            if (accessCode.getCode() == "1") continue;
            item = new AccessCode(service.name(), accessCode.getCode());
        }
        for (IDataExtendAccessItemService extendController : this.getDataExtendAccessItemList()) {
            if (this.needIgnoreItem(extendController.group())) continue;
            enable = extendController.isEnable(taskKey, formSchemeKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-readable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:taskKey:[%s];formSchemeKey:[%s]; \u662f\u5426\u542f\u7528\u6743\u9650:[%s]", extendController.name(), taskKey, formSchemeKey, enable));
            }
            if (!enable) continue;
            if (batchAccessMap.containsKey(extendController.name())) {
                accessCode = ((IBatchAccess)batchAccessMap.get(extendController.name())).getAccessCode(masterKey, formKey);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-readable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u4ecemap\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];formKey:[%s];\u7ed3\u679c:[%s]", extendController.name(), masterKey, formKey, accessCode.getCode()));
                }
            } else {
                batchReadable = extendController.getBatchReadable(formSchemeKey, masterKeys, formKeys);
                if (batchReadable == null) {
                    batchReadable = this.emptyAccessCache(extendController, formSchemeKey);
                }
                batchAccessMap.put(extendController.name(), batchReadable);
                accessCode = batchReadable.getAccessCode(masterKey, formKey);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-readable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u76f4\u63a5\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];formKey:[%s];\u7ed3\u679c:[%s]", extendController.name(), masterKey, formKey, accessCode.getCode()));
                }
            }
            if (accessCode.getCode() == "1") continue;
            item = new AccessCode(extendController.name(), accessCode.getCode());
        }
        return new AccessCaches(batchAccessMap, item);
    }

    public AccessCaches writeable(String taskKey, String formSchemeKey, DimensionCollection masterKeys, DimensionCombination masterKey, List<String> formKeys, String formKey) {
        IBatchAccess batchReadable;
        AccessCode accessCode;
        boolean enable;
        HashMap<String, IBatchAccess> batchAccessMap = new HashMap<String, IBatchAccess>();
        List<IDataAccessItemService> list = this.getDataAccessItemList();
        AccessCode item = new AccessCode(null, "1");
        for (IDataAccessItemService service : list) {
            if (this.needIgnoreItem(service.group())) continue;
            enable = service.isEnable(taskKey, formSchemeKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-writeable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:taskKey:[%s];formSchemeKey:[%s]; \u662f\u5426\u542f\u7528\u6743\u9650:[%s]", service.name(), taskKey, formSchemeKey, enable));
            }
            if (!enable) continue;
            if (batchAccessMap.containsKey(service.name())) {
                accessCode = ((IBatchAccess)batchAccessMap.get(service.name())).getAccessCode(masterKey, formKey);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-writeable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u4ecemap\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];formKey:[%s];\u7ed3\u679c:[%s]", service.name(), masterKey, formKey, accessCode.getCode()));
                }
            } else {
                batchReadable = service.getBatchWriteable(formSchemeKey, masterKeys, formKeys);
                if (batchReadable == null) {
                    batchReadable = this.emptyAccessCache(service, formSchemeKey);
                }
                batchAccessMap.put(service.name(), batchReadable);
                accessCode = batchReadable.getAccessCode(masterKey, formKey);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-writeable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u76f4\u63a5\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];formKey:[%s];\u7ed3\u679c:[%s]", service.name(), masterKey, formKey, accessCode.getCode()));
                }
            }
            if (accessCode.getCode() == "1") continue;
            item = new AccessCode(service.name(), accessCode.getCode());
        }
        for (IDataExtendAccessItemService extendController : this.getDataExtendAccessItemList()) {
            if (this.needIgnoreItem(extendController.group())) continue;
            enable = extendController.isEnable(taskKey, formSchemeKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-writeable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:taskKey:[%s];formSchemeKey:[%s]; \u662f\u5426\u542f\u7528\u6743\u9650:[%s]", extendController.name(), taskKey, formSchemeKey, enable));
            }
            if (!enable) continue;
            if (batchAccessMap.containsKey(extendController.name())) {
                accessCode = ((IBatchAccess)batchAccessMap.get(extendController.name())).getAccessCode(masterKey, formKey);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-writeable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u4ecemap\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];formKey:[%s];\u7ed3\u679c:[%s]", extendController.name(), masterKey, formKey, accessCode.getCode()));
                }
            } else {
                batchReadable = extendController.getBatchWriteable(formSchemeKey, masterKeys, formKeys);
                if (batchReadable == null) {
                    batchReadable = this.emptyAccessCache(extendController, formSchemeKey);
                }
                batchAccessMap.put(extendController.name(), batchReadable);
                accessCode = batchReadable.getAccessCode(masterKey, formKey);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-writeable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u76f4\u63a5\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];formKey:[%s];\u7ed3\u679c:[%s]", extendController.name(), masterKey, formKey, accessCode.getCode()));
                }
            }
            if (accessCode.getCode() == "1") continue;
            item = new AccessCode(extendController.name(), accessCode.getCode());
        }
        return new AccessCaches(batchAccessMap, item);
    }

    public AccessCaches sysWriteable(String taskKey, String formSchemeKey, DimensionCollection masterKeys, DimensionCombination masterKey, List<String> formKeys, String formKey) {
        IBatchAccess batchReadable;
        AccessCode accessCode;
        boolean enable;
        HashMap<String, IBatchAccess> batchAccessMap = new HashMap<String, IBatchAccess>();
        List<IDataAccessItemService> list = this.getDataAccessItemList();
        AccessCode item = new AccessCode(null, "1");
        for (IDataAccessItemService service : list) {
            if (this.needIgnoreItem(service.group())) continue;
            enable = service.isEnable(taskKey, formSchemeKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-sysWriteable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:taskKey:[%s];formSchemeKey:[%s]; \u662f\u5426\u542f\u7528\u6743\u9650:[%s]", service.name(), taskKey, formSchemeKey, enable));
            }
            if (!enable) continue;
            if (batchAccessMap.containsKey(service.name())) {
                accessCode = ((IBatchAccess)batchAccessMap.get(service.name())).getAccessCode(masterKey, formKey);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-sysWriteable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u4ecemap\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];formKey:[%s];\u7ed3\u679c:[%s]", service.name(), masterKey, formKey, accessCode.getCode()));
                }
            } else {
                batchReadable = service.getSysBatchWriteable(formSchemeKey, masterKeys, formKeys);
                if (batchReadable == null) {
                    batchReadable = this.emptyAccessCache(service, formSchemeKey);
                }
                batchAccessMap.put(service.name(), batchReadable);
                accessCode = batchReadable.getAccessCode(masterKey, formKey);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-sysWriteable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u76f4\u63a5\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];formKey:[%s];\u7ed3\u679c:[%s]", service.name(), masterKey, formKey, accessCode.getCode()));
                }
            }
            if (accessCode.getCode() == "1") continue;
            item = new AccessCode(service.name(), accessCode.getCode());
        }
        for (IDataExtendAccessItemService extendController : this.getDataExtendAccessItemList()) {
            if (this.needIgnoreItem(extendController.group())) continue;
            enable = extendController.isEnable(taskKey, formSchemeKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-sysWriteable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:taskKey:[%s];formSchemeKey:[%s]; \u662f\u5426\u542f\u7528\u6743\u9650:[%s]", extendController.name(), taskKey, formSchemeKey, enable));
            }
            if (!enable) continue;
            if (batchAccessMap.containsKey(extendController.name())) {
                accessCode = ((IBatchAccess)batchAccessMap.get(extendController.name())).getAccessCode(masterKey, formKey);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-sysWriteable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u4ecemap\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];formKey:[%s];\u7ed3\u679c:[%s]", extendController.name(), masterKey, formKey, accessCode.getCode()));
                }
            } else {
                batchReadable = extendController.getSysBatchWriteable(formSchemeKey, masterKeys, formKeys);
                if (batchReadable == null) {
                    batchReadable = this.emptyAccessCache(extendController, formSchemeKey);
                }
                batchAccessMap.put(extendController.name(), batchReadable);
                accessCode = batchReadable.getAccessCode(masterKey, formKey);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-sysWriteable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u76f4\u63a5\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];formKey:[%s];\u7ed3\u679c:[%s]", extendController.name(), masterKey, formKey, accessCode.getCode()));
                }
            }
            if (accessCode.getCode() == "1") continue;
            item = new AccessCode(extendController.name(), accessCode.getCode());
        }
        return new AccessCaches(batchAccessMap, item);
    }

    public Map<String, Object> getExtendAccessResult(AccessParam param, String formShcemeKey, DimensionCombination masterKey, String formKey) {
        HashMap<String, Object> res = new HashMap<String, Object>();
        List<AccessItem> items = param.getItems();
        for (AccessItem item : items) {
            IDataAccessExtraResultService controller;
            Optional result;
            Optional<IDataAccessExtraResultService> extendController = this.getDataAccessExtraResultController(item);
            if (!extendController.isPresent() || !(result = (controller = extendController.get()).getExtraResult(item, formShcemeKey, masterKey, formKey)).isPresent()) continue;
            res.put(controller.name(), result.get());
        }
        return res;
    }

    private IBatchAccess emptyAccessCache(IDataAccessItemBaseService controller, String formShcemeKey) {
        AccessLevel accessLevel = controller.getLevel();
        return accessLevel == AccessLevel.FORM ? new FormBatchAccessCache(controller.name(), formShcemeKey) : new UnitBatchAccessCache(controller.name(), formShcemeKey);
    }

    private IBatchMergeAccess emptyAccessCache(IDataAccessItemBaseService controller) {
        return new CanAccessBatchMergeAccess(controller.name());
    }

    public ZbAccessCaches zbVisible(DimensionCollection masterKeys, List<String> zbKeys, DimensionCombination masterKey, String zbKey) {
        LinkedHashMap<String, IBatchMergeAccess> batchAccessMap = new LinkedHashMap<String, IBatchMergeAccess>();
        List<IDataAccessItemService> list = this.getDataAccessItemList();
        AccessCode item = null;
        BatchAccessFormMerge accessForm = super.getAccessForm(masterKeys, zbKeys);
        Set<String> forms = accessForm.getFormKeysByZbKey(zbKey);
        if (forms == null) {
            throw new IllegalArgumentException("\u8bf7\u68c0\u67e5\u53c2\u6570,\u4f20\u5165\u7684\u6307\u6807key\u4e0d\u518d\u6279\u91cf\u5224\u65ad\u6743\u9650\u7684\u5217\u8868\u4e2d");
        }
        IAccessFormMerge merge = accessForm.getAccessFormMerge(masterKey, zbKey);
        if (forms.isEmpty()) {
            item = new AccessCode("\u8be5\u6307\u6807\u6ca1\u6709\u62a5\u8868\u4f7f\u7528", "1");
            if (logger.isDebugEnabled()) {
                logger.debug("\u4f20\u5165\u6307\u6807\u8be5\u6ca1\u6709\u62a5\u8868\u4f7f\u7528,\u9ed8\u8ba4\u6709\u6743\u9650");
            }
        } else if (merge == null) {
            item = new AccessCode(null, "1");
            if (logger.isDebugEnabled()) {
                logger.debug("\u4f20\u5165\u60c5\u666f\u4e0e\u6307\u6807\u6240\u5728\u4efb\u52a1\u5173\u8054\u60c5\u666f\u5339\u914d\u4e0d\u4e0a,\u7ef4\u5ea6:{},\u6307\u6807:{}", (Object)masterKey, (Object)zbKey);
            }
        } else {
            for (IDataAccessItemService service : list) {
                AccessCode accessCode;
                if (this.needIgnoreItem(service.group())) continue;
                IBatchMergeAccess batchVisible = service.getBatchVisible(accessForm);
                if (batchVisible == null) {
                    batchVisible = this.emptyAccessCache(service);
                }
                batchAccessMap.put(service.name(), batchVisible);
                if (item != null || (accessCode = batchVisible.getAccessCode(merge)) == null || Objects.equals(accessCode.getCode(), "1")) continue;
                item = new AccessCode(service.name(), accessCode.getCode());
            }
        }
        if (item == null) {
            item = new AccessCode("all", "1");
        }
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-zbVisible-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u76f4\u63a5\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];zbKey:[%s];\u7ed3\u679c:[%s]", item.getName(), masterKey, zbKey, item.getCode()));
        }
        return new ZbAccessCaches(accessForm, batchAccessMap, item);
    }

    public ZbAccessCaches zbReadable(DimensionCollection masterKeys, List<String> zbKeys, DimensionCombination masterKey, String zbKey) {
        LinkedHashMap<String, IBatchMergeAccess> batchAccessMap = new LinkedHashMap<String, IBatchMergeAccess>();
        List<IDataAccessItemService> list = this.getDataAccessItemList();
        AccessCode item = null;
        BatchAccessFormMerge accessForm = super.getAccessForm(masterKeys, zbKeys);
        Set<String> forms = accessForm.getFormKeysByZbKey(zbKey);
        if (forms == null) {
            throw new IllegalArgumentException("\u8bf7\u68c0\u67e5\u53c2\u6570,\u4f20\u5165\u7684\u6307\u6807key\u4e0d\u518d\u6279\u91cf\u5224\u65ad\u6743\u9650\u7684\u5217\u8868\u4e2d");
        }
        IAccessFormMerge merge = accessForm.getAccessFormMerge(masterKey, zbKey);
        if (forms.isEmpty()) {
            item = new AccessCode("\u8be5\u6307\u6807\u6ca1\u6709\u62a5\u8868\u4f7f\u7528", "1");
            if (logger.isDebugEnabled()) {
                logger.debug("\u4f20\u5165\u6307\u6807\u8be5\u6ca1\u6709\u62a5\u8868\u4f7f\u7528,\u9ed8\u8ba4\u6709\u6743\u9650");
            }
        } else if (merge == null) {
            item = new AccessCode(null, "1");
            if (logger.isDebugEnabled()) {
                logger.debug("\u4f20\u5165\u60c5\u666f\u4e0e\u6307\u6807\u6240\u5728\u4efb\u52a1\u5173\u8054\u60c5\u666f\u5339\u914d\u4e0d\u4e0a,\u7ef4\u5ea6:{},\u6307\u6807:{}", (Object)masterKey, (Object)zbKey);
            }
        } else {
            for (IDataAccessItemService service : list) {
                AccessCode accessCode;
                if (this.needIgnoreItem(service.group())) continue;
                IBatchMergeAccess batchReadable = service.getBatchReadable(accessForm);
                if (batchReadable == null) {
                    batchReadable = this.emptyAccessCache(service);
                }
                batchAccessMap.put(service.name(), batchReadable);
                if (item != null || (accessCode = batchReadable.getAccessCode(merge)) == null || Objects.equals(accessCode.getCode(), "1")) continue;
                item = new AccessCode(service.name(), accessCode.getCode());
            }
        }
        if (item == null) {
            item = new AccessCode("all", "1");
        }
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-zbReadable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u76f4\u63a5\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];zbKey:[%s];\u7ed3\u679c:[%s]", item.getName(), masterKey, zbKey, item.getCode()));
        }
        return new ZbAccessCaches(accessForm, batchAccessMap, item);
    }

    public ZbAccessCaches zbWriteable(DimensionCollection masterKeys, List<String> zbKeys, DimensionCombination masterKey, String zbKey) {
        LinkedHashMap<String, IBatchMergeAccess> batchAccessMap = new LinkedHashMap<String, IBatchMergeAccess>();
        List<IDataAccessItemService> list = this.getDataAccessItemList();
        AccessCode item = null;
        BatchAccessFormMerge accessForm = super.getAccessForm(masterKeys, zbKeys);
        Set<String> forms = accessForm.getFormKeysByZbKey(zbKey);
        if (forms == null) {
            throw new IllegalArgumentException("\u8bf7\u68c0\u67e5\u53c2\u6570,\u4f20\u5165\u7684\u6307\u6807key\u4e0d\u518d\u6279\u91cf\u5224\u65ad\u6743\u9650\u7684\u5217\u8868\u4e2d");
        }
        IAccessFormMerge merge = accessForm.getAccessFormMerge(masterKey, zbKey);
        if (forms.isEmpty()) {
            item = new AccessCode("\u8be5\u6307\u6807\u6ca1\u6709\u62a5\u8868\u4f7f\u7528", "1");
            if (logger.isDebugEnabled()) {
                logger.debug("\u4f20\u5165\u6307\u6807\u8be5\u6ca1\u6709\u62a5\u8868\u4f7f\u7528,\u9ed8\u8ba4\u6709\u6743\u9650");
            }
        } else if (merge == null) {
            item = new AccessCode(null, "1");
            if (logger.isDebugEnabled()) {
                logger.debug("\u4f20\u5165\u60c5\u666f\u4e0e\u6307\u6807\u6240\u5728\u4efb\u52a1\u5173\u8054\u60c5\u666f\u5339\u914d\u4e0d\u4e0a,\u7ef4\u5ea6:{},\u6307\u6807:{}", (Object)masterKey, (Object)zbKey);
            }
        } else {
            for (IDataAccessItemService service : list) {
                AccessCode accessCode;
                if (this.needIgnoreItem(service.group())) continue;
                IBatchMergeAccess batchWriteable = service.getBatchWriteable(accessForm);
                if (batchWriteable == null) {
                    batchWriteable = this.emptyAccessCache(service);
                }
                batchAccessMap.put(service.name(), batchWriteable);
                if (item != null || (accessCode = batchWriteable.getAccessCode(merge)) == null || Objects.equals(accessCode.getCode(), "1")) continue;
                item = new AccessCode(service.name(), accessCode.getCode());
            }
        }
        if (item == null) {
            item = new AccessCode("all", "1");
        }
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-zbWriteable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u76f4\u63a5\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];zbKey:[%s];\u7ed3\u679c:[%s]", item.getName(), masterKey, zbKey, item.getCode()));
        }
        return new ZbAccessCaches(accessForm, batchAccessMap, item);
    }

    public ZbAccessCaches zbSysWriteable(DimensionCollection masterKeys, List<String> zbKeys, DimensionCombination masterKey, String zbKey) {
        LinkedHashMap<String, IBatchMergeAccess> batchAccessMap = new LinkedHashMap<String, IBatchMergeAccess>();
        List<IDataAccessItemService> list = this.getDataAccessItemList();
        AccessCode item = null;
        BatchAccessFormMerge accessForm = super.getAccessForm(masterKeys, zbKeys);
        Set<String> forms = accessForm.getFormKeysByZbKey(zbKey);
        if (forms == null) {
            throw new IllegalArgumentException("\u8bf7\u68c0\u67e5\u53c2\u6570,\u4f20\u5165\u7684\u6307\u6807key\u4e0d\u518d\u6279\u91cf\u5224\u65ad\u6743\u9650\u7684\u5217\u8868\u4e2d");
        }
        IAccessFormMerge merge = accessForm.getAccessFormMerge(masterKey, zbKey);
        if (forms.isEmpty()) {
            item = new AccessCode("\u8be5\u6307\u6807\u6ca1\u6709\u62a5\u8868\u4f7f\u7528", "1");
            if (logger.isDebugEnabled()) {
                logger.debug("\u4f20\u5165\u6307\u6807\u8be5\u6ca1\u6709\u62a5\u8868\u4f7f\u7528,\u9ed8\u8ba4\u6709\u6743\u9650");
            }
        } else if (merge == null) {
            item = new AccessCode(null, "1");
            if (logger.isDebugEnabled()) {
                logger.debug("\u4f20\u5165\u60c5\u666f\u4e0e\u6307\u6807\u6240\u5728\u4efb\u52a1\u5173\u8054\u60c5\u666f\u5339\u914d\u4e0d\u4e0a,\u7ef4\u5ea6:{},\u6307\u6807:{}", (Object)masterKey, (Object)zbKey);
            }
        } else {
            for (IDataAccessItemService service : list) {
                AccessCode accessCode;
                if (this.needIgnoreItem(service.group())) continue;
                IBatchMergeAccess batchSysWr = service.getSysBatchWriteable(accessForm);
                if (batchSysWr == null) {
                    batchSysWr = this.emptyAccessCache(service);
                }
                batchAccessMap.put(service.name(), batchSysWr);
                if (item != null || (accessCode = batchSysWr.getAccessCode(merge)) == null || Objects.equals(accessCode.getCode(), "1")) continue;
                item = new AccessCode(service.name(), accessCode.getCode());
            }
        }
        if (item == null) {
            item = new AccessCode("all", "1");
        }
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-zbSysWriteable-\u62a5\u8868\u6279\u91cf\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s]-\u76f4\u63a5\u83b7\u53d6\u63a5\u53e3 \u53c2\u6570:masterKey:[%s];zbKey:[%s];\u7ed3\u679c:[%s]", item.getName(), masterKey, zbKey, item.getCode()));
        }
        return new ZbAccessCaches(accessForm, batchAccessMap, item);
    }
}

