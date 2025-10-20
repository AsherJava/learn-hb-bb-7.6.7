/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.access.api.IStateFormLockService
 *  com.jiuqi.nr.data.access.api.param.LockParam
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.dataentry.bean.FormLockParam
 *  com.jiuqi.nr.dataentry.exception.DataEntryException
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.inputdata.dataentryext.syncformlock.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.dataentryext.syncformlock.params.GcBatchFormLockParam;
import com.jiuqi.gcreport.inputdata.dataentryext.syncformlock.params.GcFormLockParam;
import com.jiuqi.gcreport.inputdata.dataentryext.syncformlock.service.GcFormLockService;
import com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.access.api.IStateFormLockService;
import com.jiuqi.nr.data.access.api.param.LockParam;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.dataentry.bean.FormLockParam;
import com.jiuqi.nr.dataentry.exception.DataEntryException;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcFormLockServiceImpl
implements GcFormLockService {
    private static final Logger logger = LoggerFactory.getLogger(GcFormLockServiceImpl.class);
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired
    IStateFormLockService formLockService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @Override
    public void lockForm(FormLockParam param) {
        Map<String, GcFormLockParam> formLockParamGroupByScheme = this.getFormLockParamGroupByScheme(param);
        StringBuilder message = this.lockForm(formLockParamGroupByScheme);
        if (message.length() > 0) {
            throw new DataEntryException(message.toString());
        }
    }

    private Map<String, GcFormLockParam> getFormLockParamGroupByScheme(FormLockParam param) {
        HashMap<String, GcFormLockParam> formLockParamGroupByScheme = new HashMap<String, GcFormLockParam>();
        String formSchemeKey = param.getContext().getFormSchemeKey();
        String taskId = param.getContext().getTaskKey();
        String orgType = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)taskId);
        List subDimensionValueList = DimensionValueSetUtil.getDimensionSetList((Map)param.getContext().getDimensionSet());
        for (Map dimensionValueMap : subDimensionValueList) {
            List<String> schemeIds = this.listSchemeIdByConsolidatedSystem(taskId, formSchemeKey, dimensionValueMap, orgType);
            Map<String, Set<String>> manageWithInputFormKeyGroupBySchemeId = this.getManageWithInputFormKeyBySchemeId(schemeIds, param.getFormKeys());
            if (manageWithInputFormKeyGroupBySchemeId.isEmpty()) continue;
            for (String schemeId : manageWithInputFormKeyGroupBySchemeId.keySet()) {
                Set<String> formKeys;
                FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(schemeId);
                if (formScheme == null || CollectionUtils.isEmpty(formKeys = manageWithInputFormKeyGroupBySchemeId.get(schemeId))) continue;
                GcFormLockParam formLockParam = formLockParamGroupByScheme.computeIfAbsent(schemeId, key -> new GcFormLockParam());
                formLockParam.getFormKeys().addAll(formKeys);
                formLockParam.setLock(param.isLock());
                formLockParam.setFormScheme(formScheme);
                formLockParam.setDimensionValueMap(param.getContext().getDimensionSet());
                formLockParam.getDimensionValueMaps().add(dimensionValueMap);
                formLockParam.setForceUnLock(param.isForceUnLock());
            }
        }
        return formLockParamGroupByScheme;
    }

    private StringBuilder lockForm(Map<String, GcFormLockParam> formLockParamGroupByScheme) {
        StringBuilder message = new StringBuilder();
        for (String schemeId : formLockParamGroupByScheme.keySet()) {
            List<Map<String, DimensionValue>> dimensionValueMaps = formLockParamGroupByScheme.get(schemeId).getDimensionValueMaps();
            if (CollectionUtils.isEmpty(dimensionValueMaps)) continue;
            String messageStr = this.lockFormBySchemeId(formLockParamGroupByScheme.get(schemeId));
            message.append(messageStr);
        }
        return message;
    }

    private boolean isBatchFormLock(List<Map<String, DimensionValue>> dimensionValueSets, Set<String> formKeys) {
        return dimensionValueSets.size() > 1 || formKeys.size() > 1;
    }

    private String lockFormBySchemeId(GcFormLockParam gcFormLockParam) {
        boolean isBatch = this.isBatchFormLock(gcFormLockParam.getDimensionValueMaps(), gcFormLockParam.getFormKeys());
        StringBuilder message = new StringBuilder();
        if (!isBatch) {
            LockParam lockParam = this.getFormLockParam(gcFormLockParam.getFormKeys(), gcFormLockParam.getDimensionValueMaps().get(0), gcFormLockParam.getFormScheme(), gcFormLockParam.isLock());
            String messageStr = this.formLockService.lockForm(lockParam);
            if (!StringUtils.isEmpty((String)messageStr)) {
                String formKey = gcFormLockParam.getFormKeys().iterator().next();
                FormDefine formDefine = this.iRunTimeViewController.queryFormById(formKey);
                TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(lockParam.getTaskKey());
                message = message.append("\u4efb\u52a1\uff1a\u3010").append(taskDefine.getTitle()).append("\u3011\uff0c\u8868\u5355\uff1a\u3010").append(formDefine.getTitle()).append("\u3011").append(messageStr);
                logger.error("\u5408\u5e76\u5355\u8868\u9501\u5b9a\u89e3\u9501\u5931\u8d25\uff1a" + message + ", \u53c2\u6570\u4fe1\u606f\uff1a" + gcFormLockParam.getDimensionValueMaps().get(0).toString());
            }
        } else {
            GcBatchFormLockParam gcBatchFormLockParam = new GcBatchFormLockParam();
            gcBatchFormLockParam.setTaskKey(gcFormLockParam.getFormScheme().getTaskKey());
            gcBatchFormLockParam.setFormSchemeKey(gcFormLockParam.getFormScheme().getKey());
            gcBatchFormLockParam.setLock(gcFormLockParam.isLock());
            gcBatchFormLockParam.setFormKeys(gcFormLockParam.getFormKeys());
            gcBatchFormLockParam.setDimensionValueMap(gcFormLockParam.getDimensionValueMap());
            gcBatchFormLockParam.setDimensionValueMaps(gcFormLockParam.getDimensionValueMaps());
            gcBatchFormLockParam.setForceUnLock(gcFormLockParam.isForceUnLock());
            this.asyncTaskManager.publishTask((Object)JsonUtils.writeValueAsString((Object)gcBatchFormLockParam), GcAsyncTaskPoolType.ASYNCTASK_GC_BATCH_FORM_LOCK.getName());
        }
        return message.toString();
    }

    private LockParam getFormLockParam(Set<String> formKeys, Map<String, DimensionValue> dimensionValueMap, FormSchemeDefine formScheme, boolean isLock) {
        LockParam lockParam = new LockParam();
        lockParam.setFormKeys(formKeys.stream().collect(Collectors.toList()));
        lockParam.setTaskKey(formScheme.getTaskKey());
        lockParam.setFormSchemeKey(formScheme.getKey());
        lockParam.setLock(isLock);
        lockParam.setMasterKeys(this.dimCollectionBuildUtil.buildDimensionCollection(dimensionValueMap, formScheme.getKey()));
        lockParam.setIgnoreAuth(true);
        lockParam.setForceUnLock(true);
        return lockParam;
    }

    private List<String> listSchemeIdByConsolidatedSystem(String taskId, String formSchemeKey, Map<String, DimensionValue> dimensionValueMap, String contextEntityId) {
        GcOrgCacheVO cacheVO;
        String periodStr = dimensionValueMap.get("DATATIME").getValue();
        String orgId = dimensionValueMap.get("MD_ORG").getValue();
        YearPeriodDO yp = GcPeriodAssistUtil.getPeriodObject((String)formSchemeKey, (String)periodStr);
        String orgTableName = this.getOrgTableCodeByFormSchemeKey(formSchemeKey);
        if (!StringUtils.isEmpty((String)contextEntityId)) {
            TableModelDefine tableModelDefine = this.entityMetaService.getTableModel(contextEntityId);
            orgTableName = tableModelDefine.getName();
        }
        if ((cacheVO = this.getOrgByOrgTableName(orgTableName, yp, orgId)) == null || StringUtils.isEmpty((String)cacheVO.getOrgTypeId())) {
            return Collections.emptyList();
        }
        boolean isCorporate = this.consolidatedTaskService.isCorporate(taskId, periodStr, orgTableName);
        List<Object> schemeIds = new ArrayList();
        if (isCorporate) {
            schemeIds = this.listManageWithInputSchemeId(formSchemeKey, periodStr, cacheVO);
        } else {
            String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(formSchemeKey, periodStr);
            if (!StringUtils.isEmpty((String)systemId)) {
                schemeIds = this.listManageWithInputSchemeId(formSchemeKey, periodStr, cacheVO);
            }
        }
        return schemeIds;
    }

    private Map<String, Set<String>> getManageWithInputFormKeyBySchemeId(List<String> schemeIds, List<String> lockFormKeys) {
        HashMap<String, Set<String>> manageWithInputFormKeyGroupBySchemeId = new HashMap<String, Set<String>>();
        for (String schemeId : schemeIds) {
            List formDefines = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(schemeId);
            HashMap<String, List> formKeyByTitles = new HashMap<String, List>(16);
            for (FormDefine formDefine : formDefines) {
                List formKeys = formKeyByTitles.computeIfAbsent(formDefine.getTitle(), key -> new ArrayList());
                formKeys.add(formDefine.getKey());
            }
            HashSet manageWithInputFormKeys = new HashSet();
            for (String formKey : lockFormKeys) {
                FormDefine formDefine = this.iRunTimeViewController.queryFormById(formKey);
                if (formDefine == null || !formKeyByTitles.containsKey(formDefine.getTitle())) continue;
                manageWithInputFormKeys.addAll((Collection)formKeyByTitles.get(formDefine.getTitle()));
            }
            manageWithInputFormKeyGroupBySchemeId.put(schemeId, manageWithInputFormKeys);
        }
        return manageWithInputFormKeyGroupBySchemeId;
    }

    private List<String> listManageWithInputSchemeId(String schemeId, String periodStr, GcOrgCacheVO gcOrgCache) {
        ArrayList<String> manageWithInputSchemeIds = new ArrayList<String>();
        ConsolidatedTaskVO consolidatedTaskVO = this.consolidatedTaskService.getTaskBySchemeId(schemeId, periodStr);
        Set schemeIds = ConsolidatedSystemUtils.listSchemeIdSetByTaskIdListAndPeriod((List)consolidatedTaskVO.getManageTaskKeys(), (String)periodStr);
        if (consolidatedTaskVO == null || CollectionUtils.isEmpty((Collection)schemeIds)) {
            return manageWithInputSchemeIds;
        }
        schemeIds.add(ConsolidatedSystemUtils.getSchemeIdByTaskIdAndPeriod((String)consolidatedTaskVO.getTaskKey(), (String)periodStr));
        for (String schemeKey : schemeIds) {
            YearPeriodDO yp;
            if (schemeId.equals(schemeKey)) continue;
            FormSchemeDefine formSchemeDefine = this.commonUtil.getFormScheme(schemeKey);
            if (formSchemeDefine == null) {
                Object[] args = new String[]{schemeKey};
                throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryext.listener.fromschemedeniedmsg", (Object[])args));
            }
            String orgTableName = this.getOrgTableCodeByFormSchemeKey(schemeKey);
            GcOrgCacheVO gOrgCacheByScheme = this.getOrgByOrgTableName(orgTableName, yp = GcPeriodAssistUtil.getPeriodObject((String)schemeKey, (String)periodStr), gcOrgCache.getCode());
            if (gOrgCacheByScheme == null || !gcOrgCache.getOrgTypeId().equals(gOrgCacheByScheme.getOrgTypeId())) continue;
            manageWithInputSchemeIds.add(schemeKey);
        }
        return manageWithInputSchemeIds;
    }

    private GcOrgCacheVO getOrgByOrgTableName(String orgTableName, YearPeriodDO yp, String orgCode) {
        if (StringUtils.isEmpty((String)orgTableName)) {
            return null;
        }
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgTableName, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)yp);
        GcOrgCacheVO cacheVO = tool.getOrgByCode(orgCode);
        return cacheVO;
    }

    private String getOrgTableCodeByFormSchemeKey(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        TableModelDefine tableDefine = this.entityMetaService.getTableModel(formSchemeDefine.getDw());
        return tableDefine.getName();
    }
}

