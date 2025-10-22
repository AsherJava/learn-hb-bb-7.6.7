/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.api.IStateFormLockService
 *  com.jiuqi.nr.data.access.api.param.LockParam
 *  com.jiuqi.nr.data.access.param.FormLockBatchReadWriteResult
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.access.util.I18nUtil
 *  com.jiuqi.nr.data.logic.internal.util.DimensionUtil
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.dataservice.core.dimension.build.DWLeafNodeBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil
 *  com.jiuqi.nr.dataservice.core.dimension.build.SpecificDimBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.aop.JLoggerAspect
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.api.IStateFormLockService;
import com.jiuqi.nr.data.access.api.param.LockParam;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.access.util.I18nUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.dataentry.bean.FormLockParam;
import com.jiuqi.nr.dataentry.bean.ResultObject;
import com.jiuqi.nr.dataentry.exception.DataEntryException;
import com.jiuqi.nr.dataentry.readwrite.bean.FormLockBatchReadWriteResult;
import com.jiuqi.nr.dataentry.service.IFormLockService;
import com.jiuqi.nr.dataentry.service.IFormLockSyncService;
import com.jiuqi.nr.dataservice.core.dimension.build.DWLeafNodeBuilder;
import com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil;
import com.jiuqi.nr.dataservice.core.dimension.build.SpecificDimBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.aop.JLoggerAspect;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class FormLockServiceImpl
implements IFormLockService {
    private static final Logger logger = LoggerFactory.getLogger(FormLockServiceImpl.class);
    @Autowired
    private JLoggerAspect jLoggerAspect;
    @Autowired(required=false)
    private IFormLockSyncService formLockSyncService;
    @Autowired
    private IStateFormLockService formLockService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Resource
    IRunTimeViewController runtimeView;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private DataEntityFullService dataEntityFullService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired
    private ITaskOptionController iTaskOptionController;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DimensionBuildUtil dimensionBuildUtil;
    private Function<List<com.jiuqi.nr.data.access.param.FormLockBatchReadWriteResult>, List<FormLockBatchReadWriteResult>> batchResultConvert = resultList -> {
        List list = resultList.stream().map(result -> {
            FormLockBatchReadWriteResult formLockBatchReadWriteResult = new FormLockBatchReadWriteResult();
            formLockBatchReadWriteResult.setDimensionSet(com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionSet((DimensionValueSet)result.getDimensionValueSet()));
            formLockBatchReadWriteResult.setFormKey(result.getFormKey());
            formLockBatchReadWriteResult.setLock(result.isLock());
            formLockBatchReadWriteResult.setUserId(result.getUserId());
            return formLockBatchReadWriteResult;
        }).collect(Collectors.toList());
        return list;
    };

    @Override
    public boolean isEnableFormLock(String taskKey) {
        return this.formLockService.isEnableFormLock(taskKey);
    }

    @Override
    public boolean isFormLocked(FormLockParam param) {
        JtableContext jtableContext = param.getContext();
        LockParam lockParam = new LockParam();
        lockParam.setFormKeys(param.getFormKeys());
        lockParam.setTaskKey(jtableContext.getTaskKey());
        lockParam.setFormSchemeKey(jtableContext.getFormSchemeKey());
        lockParam.setLock(param.isLock());
        lockParam.setMasterKeys(this.dimCollectionBuildUtil.buildDimensionCollectionNoFilter(com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionValueSet((Map)jtableContext.getDimensionSet()), jtableContext.getFormSchemeKey()));
        return this.formLockService.isFormLocked(lockParam);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public ResultObject lockForm(FormLockParam param) throws Exception {
        if (!this.isEnableFormLock(param.getContext().getTaskKey())) {
            throw new DataEntryException("\u672a\u5f00\u542f\u9501\u5b9a\u529f\u80fd");
        }
        if (param.isLock()) {
            this.jLoggerAspect.log(param.getContext(), "\u62a5\u8868\u9501\u5b9a");
        } else {
            this.jLoggerAspect.log(param.getContext(), "\u62a5\u8868\u89e3\u9501");
        }
        JtableContext jtableContext = param.getContext();
        if (jtableContext.getFormSchemeKey() == null) {
            throw new DataEntryException("\u6ca1\u6709\u62a5\u8868\u65b9\u6848");
        }
        if (jtableContext.getDimensionSet() == null) {
            throw new DataEntryException("\u6ca1\u6709\u7ef4\u5ea6");
        }
        if (jtableContext.getFormKey() == null && param.getFormKeys() == null) {
            throw new DataEntryException("\u6ca1\u6709\u62a5\u8868");
        }
        LockParam lockParam = new LockParam();
        lockParam.setFormKeys(param.getFormKeys());
        lockParam.setTaskKey(jtableContext.getTaskKey());
        lockParam.setFormSchemeKey(jtableContext.getFormSchemeKey());
        lockParam.setLock(param.isLock());
        lockParam.setForceUnLock(param.isForceUnLock());
        lockParam.setMasterKeys(com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((Map)jtableContext.getDimensionSet(), (String)jtableContext.getFormSchemeKey()));
        String message = this.formLockService.lockForm(lockParam);
        if (StringUtils.isNotEmpty((String)message)) {
            return new ResultObject(false, message);
        }
        if (this.formLockSyncService != null) {
            this.formLockSyncService.syncLockForm(param);
        }
        boolean chinese = I18nUtil.isChinese();
        String msg = "";
        msg = chinese ? (param.isLock() ? "\u9501\u5b9a\u6210\u529f\uff01" : "\u89e3\u9501\u6210\u529f\uff01") : (param.isLock() ? "Lock success\uff01" : "Unlock success\uff01");
        return new ResultObject(true, msg);
    }

    @Override
    public Boolean queryForceUnLock(FormLockParam param) {
        String userId;
        List userIdByRole;
        HashSet users;
        SystemIdentityService systemIdentityService = (SystemIdentityService)SpringBeanUtils.getBean(SystemIdentityService.class);
        if (systemIdentityService.isAdmin()) {
            return true;
        }
        boolean forceUnLockOption = false;
        String forceLock = this.iTaskOptionController.getValue(param.getContext().getTaskKey(), "FORCE_FORM_UNLOCK_ROLE");
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)forceLock) && (users = new HashSet(userIdByRole = this.roleService.getUserIdByRole(forceLock))).contains(userId = NpContextHolder.getContext().getUserId())) {
            forceUnLockOption = true;
        }
        return forceUnLockOption;
    }

    @Override
    public Map<String, String> getLockedFormKeysMap(FormLockParam param, boolean changToNickName) {
        JtableContext jtableContext = param.getContext();
        LockParam lockParam = new LockParam();
        lockParam.setFormKeys(param.getFormKeys());
        lockParam.setTaskKey(jtableContext.getTaskKey());
        lockParam.setFormSchemeKey(jtableContext.getFormSchemeKey());
        lockParam.setLock(param.isLock());
        lockParam.setMasterKeys(this.dimCollectionBuildUtil.buildDimensionCollectionNoFilter(com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionValueSet((Map)jtableContext.getDimensionSet()), jtableContext.getFormSchemeKey()));
        Map formKeysMap = this.formLockService.getLockedFormKeysMap(lockParam, changToNickName);
        return formKeysMap;
    }

    @Override
    public Map<String, String> getLockedFormKeysMapByUser(FormLockParam param, boolean changToNickName) {
        JtableContext jtableContext = param.getContext();
        LockParam lockParam = new LockParam();
        lockParam.setFormKeys(param.getFormKeys());
        lockParam.setTaskKey(jtableContext.getTaskKey());
        lockParam.setFormSchemeKey(jtableContext.getFormSchemeKey());
        lockParam.setLock(param.isLock());
        lockParam.setMasterKeys(this.dimCollectionBuildUtil.buildDimensionCollectionNoFilter(com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionValueSet((Map)jtableContext.getDimensionSet()), jtableContext.getFormSchemeKey()));
        Map formKeysMap = this.formLockService.getLockedFormKeysMap(lockParam, changToNickName);
        String multiUserLock = this.iTaskOptionController.getValue(jtableContext.getTaskKey(), "MULTIUSER_FORM_LOCK");
        boolean multiUserLockOption = false;
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)multiUserLock)) {
            multiUserLockOption = multiUserLock.equals("1");
        }
        if (multiUserLockOption) {
            String userId = NpContextHolder.getContext().getUserId();
            ArrayList<String> removeFormList = new ArrayList<String>();
            for (String key : formKeysMap.keySet()) {
                HashSet<String> users = new HashSet<String>(Arrays.asList(((String)formKeysMap.get(key)).split(";")));
                if (users.contains(userId)) continue;
                removeFormList.add(key);
            }
            for (String key : removeFormList) {
                formKeysMap.remove(key);
            }
        }
        return formKeysMap;
    }

    @Override
    public List<FormLockBatchReadWriteResult> batchDimension(JtableContext jtableContext) {
        Map dimension = jtableContext.getDimensionSet();
        LockParam lockParam = new LockParam();
        lockParam.setTaskKey(jtableContext.getTaskKey());
        lockParam.setFormSchemeKey(jtableContext.getFormSchemeKey());
        lockParam.setMasterKeys(com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((Map)dimension, (String)jtableContext.getFormSchemeKey()));
        List res = this.formLockService.batchDimension(lockParam);
        if (!CollectionUtils.isEmpty(res)) {
            return this.batchResultConvert.apply(res);
        }
        return Collections.emptyList();
    }

    @Override
    public void batchLockForm(FormLockParam param, AsyncTaskMonitor asyncTaskMonitor) {
        List<IEntityRow> entityDataList;
        JtableContext jtableContext = param.getContext();
        LockParam lockParam = new LockParam();
        lockParam.setFormKeys(param.getFormKeys());
        lockParam.setTaskKey(jtableContext.getTaskKey());
        lockParam.setFormSchemeKey(jtableContext.getFormSchemeKey());
        lockParam.setLock(param.isLock());
        lockParam.setForceUnLock(param.isForceUnLock());
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
        IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(formScheme.getDw());
        String value = ((DimensionValue)param.getContext().getDimensionSet().get(queryEntity.getDimensionName())).getValue();
        if ((value == null || value.equals("")) && (entityDataList = this.getEntityDataList(param.getContext().getFormSchemeKey(), param.getContext().getDimensionSet())) != null) {
            StringBuilder filtValue = new StringBuilder();
            for (IEntityRow iEntityRow : entityDataList) {
                filtValue.append(iEntityRow.getEntityKeyData() + ";");
            }
            filtValue.delete(filtValue.length() - 1, filtValue.length());
            ((DimensionValue)param.getContext().getDimensionSet().get(queryEntity.getDimensionName())).setValue(filtValue.toString());
        }
        lockParam.setMasterKeys(this.dimensionBuildUtil.getDimensionCollection(DimensionUtil.getDimensionValueSet((Map)jtableContext.getDimensionSet()), jtableContext.getFormSchemeKey(), (SpecificDimBuilder)DWLeafNodeBuilder.getInstance()));
        this.formLockService.batchLockForm(lockParam, asyncTaskMonitor);
        if (this.formLockSyncService != null) {
            this.formLockSyncService.syncLockForm(param);
        }
    }

    private List<IEntityRow> getEntityDataList(String formSchemeKey, Map<String, DimensionValue> dimensionValueSet) {
        IEntityTable iEntityTable = null;
        try {
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(formSchemeKey);
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet(dimensionValueSet));
            EntityViewDefine entityViewDefine = dwEntity.getEntityViewDefine();
            iEntityQuery.setEntityView(entityViewDefine);
            iEntityQuery.setAuthorityOperations(AuthorityType.Read);
            iEntityTable = this.dataEntityFullService.executeEntityReader(iEntityQuery, new ExecutorContext(this.dataDefinitionRuntimeController), entityViewDefine, formSchemeKey).getEntityTable();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (iEntityTable != null) {
            return iEntityTable.getAllRows();
        }
        return new ArrayList<IEntityRow>();
    }
}

