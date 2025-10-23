/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.exception.NotFoundTableDefineException
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.common.util.JsonUtil
 *  com.jiuqi.nr.dataentity.entity.IDataEntity
 *  com.jiuqi.nr.dataentity.entity.IDataEntityRow
 *  com.jiuqi.nr.dataentity.service.DataEntityService
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.access.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NotFoundTableDefineException;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.common.util.JsonUtil;
import com.jiuqi.nr.data.access.api.IStateFormLockService;
import com.jiuqi.nr.data.access.api.impl.dto.LockDTO;
import com.jiuqi.nr.data.access.api.impl.dto.SystemOptionsDTO;
import com.jiuqi.nr.data.access.api.impl.dto.TableAndDataParamDTO;
import com.jiuqi.nr.data.access.api.param.CancelFormInfo;
import com.jiuqi.nr.data.access.api.param.CancelInfoVO;
import com.jiuqi.nr.data.access.api.param.FormLockHistory;
import com.jiuqi.nr.data.access.api.param.LockParam;
import com.jiuqi.nr.data.access.api.response.BatchLockReturnInfo;
import com.jiuqi.nr.data.access.api.response.BatchLockUnitFormsInfo;
import com.jiuqi.nr.data.access.common.FormLockAuthType;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.FormLockBatchReadWriteResult;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.service.impl.FormLockAccessServiceImpl;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.access.util.I18nUtil;
import com.jiuqi.nr.data.access.util.NvwaDataEngineQueryUtil;
import com.jiuqi.nr.dataentity.entity.IDataEntity;
import com.jiuqi.nr.dataentity.entity.IDataEntityRow;
import com.jiuqi.nr.dataentity.service.DataEntityService;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class StateFormLockServiceImpl
implements IStateFormLockService {
    private static final Logger logger = LoggerFactory.getLogger(StateFormLockServiceImpl.class);
    @Autowired
    IRunTimeViewController runtimeView;
    @Autowired
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    IDataAccessProvider dataAccessProvider;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    private ITaskOptionController iTaskOptionController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private NvwaDataEngineQueryUtil nvwaDataEngineQueryUtil;
    @Autowired
    private FormLockAccessServiceImpl formLockAccessService;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;
    @Autowired
    private DataEntityService dataEntityService;
    @Autowired
    private IDataAccessServiceProvider provider;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private RoleService roleService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String ERRORINFO = "\u672a\u627e\u5230\u9501\u5b9a\u72b6\u6001\u8868\uff01";

    @Override
    public boolean isEnableFormLock(String taskKey) {
        return this.isEnableFormLock(taskKey, null);
    }

    @Override
    public boolean isFormLocked(LockParam lockParam) {
        TableModelDefine tableModel;
        Assert.notNull((Object)lockParam.getFormSchemeKey(), "formSchemeKey is must not be null!");
        Assert.notNull((Object)lockParam.getMasterKeys(), "masterKeys is must not be null!");
        Assert.isTrue(!lockParam.getFormKeys().isEmpty(), "formKey is must not be null!");
        String formSchemeKey = lockParam.getFormSchemeKey();
        DimensionCollection dimCollection = lockParam.getMasterKeys();
        List<String> formKeys = lockParam.getFormKeys();
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        if (!this.isEnableFormLock(formScheme.getTaskKey(), formSchemeKey)) {
            return false;
        }
        if (this.isEmptyDim(dimCollection)) {
            return false;
        }
        String formLockTable = this.dataAccesslUtil.getTableName(formScheme, "NR_STATE_%s_FORMLOCK");
        try {
            tableModel = this.dataModelService.getTableModelDefineByCode(formLockTable);
        }
        catch (Exception e) {
            throw new NotFoundTableDefineException(new String[]{formLockTable});
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(dimCollection);
        DimensionValueSet queryKey = this.rebuildDimKey(dimensionValueSet, formSchemeKey, formKeys.stream().findFirst().get());
        INvwaDataSet dataSet = this.getFormLockDataTable(queryKey, tableModel, formSchemeKey, true);
        int totalCount = dataSet.size();
        for (int i = 0; i < totalCount; ++i) {
            Object value;
            INvwaDataRow item = dataSet.getRow(i);
            if (item == null || !"1".equals(value = item.getValue(this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), "FL_ISLOCK")))) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isFormLockedByUser(LockParam lockParam) {
        TableModelDefine tableModel;
        Assert.notNull((Object)lockParam.getFormSchemeKey(), "formSchemeKey is must not be null!");
        Assert.notNull((Object)lockParam.getMasterKeys(), "masterKeys is must not be null!");
        Assert.isTrue(!lockParam.getFormKeys().isEmpty(), "formKey is must not be null!");
        String formSchemeKey = lockParam.getFormSchemeKey();
        DimensionCollection dimCollection = lockParam.getMasterKeys();
        List<String> formKeys = lockParam.getFormKeys();
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        if (!this.isEnableFormLock(formScheme.getTaskKey(), formSchemeKey)) {
            return false;
        }
        if (this.isEmptyDim(dimCollection)) {
            return false;
        }
        String formLockTable = this.dataAccesslUtil.getTableName(formScheme, "NR_STATE_%s_FORMLOCK");
        boolean isFormLocked = false;
        try {
            tableModel = this.dataModelService.getTableModelDefineByCode(formLockTable);
        }
        catch (Exception e) {
            throw new NotFoundTableDefineException(new String[]{formLockTable});
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(dimCollection);
        DimensionValueSet queryKey = this.rebuildDimKeyAndUser(dimensionValueSet, formSchemeKey, formKeys.stream().findFirst().get());
        INvwaDataSet dataTable = this.getFormLockDataTable(queryKey, tableModel, formSchemeKey, true);
        ArrayKey arrayKey = this.nvwaDataEngineQueryUtil.convertMasterToArrayKey(formLockTable, dataTable, queryKey);
        INvwaDataRow findRow = dataTable.findRow(arrayKey);
        if (findRow != null) {
            Object value = findRow.getValue(this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), "FL_ISLOCK"));
            isFormLocked = "1".equals(value);
        }
        return isFormLocked;
    }

    @Override
    public Map<String, String> getLockedFormKeysMap(LockParam lockParam, boolean changToNickName) {
        Assert.notNull((Object)lockParam.getFormSchemeKey(), "formSchemeKey is must not be null!");
        Assert.notNull((Object)lockParam.getMasterKeys(), "masterKeys is must not be null!");
        HashMap<String, String> formKeysMap = new HashMap<String, String>();
        String formSchemeKey = lockParam.getFormSchemeKey();
        DimensionCollection dimCollection = lockParam.getMasterKeys();
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        if (!this.isEnableFormLock(formScheme.getTaskKey(), formSchemeKey)) {
            return formKeysMap;
        }
        if (this.isEmptyDim(dimCollection)) {
            return formKeysMap;
        }
        TableModelDefine formLockTable = this.getFormLockTable(formScheme);
        if (Objects.isNull(formLockTable)) {
            return formKeysMap;
        }
        List collections = dimCollection.getDimensionCombinations();
        List<DimensionValueSet> masterKeyList = collections.stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList());
        DimensionValueSet masterKeys = DimensionValueSetUtil.mergeDimensionValueSet(masterKeyList);
        DimensionValueSet queryKey = this.rebuildDimKey(masterKeys, formSchemeKey, null);
        HashMap<String, Boolean> orderMap = new HashMap<String, Boolean>();
        orderMap.put("FL_UPDATETIME", false);
        INvwaDataSet dataTable = this.getFormLockDataTableAndOrder(queryKey, formLockTable, formSchemeKey, true, orderMap);
        int totalCount = dataTable.size();
        ArrayList<String> userIdList = new ArrayList<String>();
        for (int i = 0; i < totalCount; ++i) {
            try {
                INvwaDataRow item = dataTable.getRow(i);
                Object value = item.getValue(this.dataModelService.getColumnModelDefineByCode(formLockTable.getID(), "FL_ISLOCK"));
                if (!"1".equals(value)) continue;
                Object form = item.getValue(this.dataModelService.getColumnModelDefineByCode(formLockTable.getID(), "FL_FORMKEY"));
                Object fmlkUser = item.getValue(this.dataModelService.getColumnModelDefineByCode(formLockTable.getID(), "FL_USER"));
                String users = (String)formKeysMap.get(form.toString());
                if (StringUtils.isNotEmpty((String)users)) {
                    formKeysMap.put(form.toString(), users + ";" + fmlkUser.toString());
                } else {
                    formKeysMap.put(form.toString(), fmlkUser.toString());
                }
                userIdList.add(fmlkUser.toString());
                continue;
            }
            catch (Exception e) {
                Log.error((Exception)e);
            }
        }
        if (changToNickName) {
            ArrayList<User> usersList = new ArrayList<User>();
            usersList.addAll(this.systemUserService.get(userIdList.toArray(new String[0])));
            usersList.addAll(this.userService.get(userIdList.toArray(new String[0])));
            this.formatFormKeysMap(formKeysMap, usersList);
        }
        return formKeysMap;
    }

    @Override
    public String lockForm(LockParam lockParam) {
        Assert.notNull((Object)lockParam.getFormSchemeKey(), "formSchemeKey is must not be null!");
        Assert.notNull((Object)lockParam.getMasterKeys(), "masterKeys is must not be null!");
        Assert.notNull((Object)lockParam.getTaskKey(), "taskKey is must not be null!");
        Assert.isTrue(!lockParam.getFormKeys().isEmpty(), "formKey is must not be null!");
        String formSchemeKey = lockParam.getFormSchemeKey();
        String taskKey = lockParam.getTaskKey();
        DimensionCollection dimensionCollection = lockParam.getMasterKeys();
        List<String> formKeys = lockParam.getFormKeys();
        boolean lock = lockParam.isLock();
        boolean ignoreAuth = lockParam.isIgnoreAuth();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        List dimensionCollectionList = dimensionCollection.getDimensionCombinations();
        if (!dimensionCollectionList.isEmpty()) {
            dimensionValueSet = ((DimensionCombination)dimensionCollectionList.stream().findFirst().get()).toDimensionValueSet();
        }
        if (dimensionValueSet.hasValue("VERSIONID")) {
            dimensionValueSet.clearValue("VERSIONID");
        }
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(this.dataAccesslUtil.getTableName(formScheme, "NR_STATE_%s_FORMLOCK"));
        String dwDimensionName = this.dataAccesslUtil.getDwDimensionName(formScheme.getDw());
        String entityId = this.dataAccesslUtil.contextEntityId(formScheme.getDw());
        String periodDimensionName = this.dataAccesslUtil.getPeriodDimensionName(entityId);
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("\u6570\u636e\u6743\u9650\u670d\u52a1", OperLevel.USER_OPER);
        DimensionValueSet dimensionSet = DimensionValueSetUtil.mergeDimensionValueSet(dimensionCollection);
        String dwCode = String.valueOf(dimensionSet.getValue(dwDimensionName));
        String periodCode = String.valueOf(dimensionSet.getValue(periodDimensionName));
        String dateTime = formScheme.getDateTime();
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setPeriod(dateTime, periodCode);
        logDimensionCollection.setDw(entityId, (String[])dimensionCollectionList.stream().map(e -> (String)e.getValue(dwDimensionName)).toArray(String[]::new));
        String content = lock ? "\u9501\u5b9a" : "\u89e3\u9501";
        logHelper.info(taskKey, logDimensionCollection, "\u62a5\u8868" + content + "\u5f00\u59cb", "\u62a5\u8868" + content + "\u5f00\u59cb");
        String formSchemeKeyName = dimensionChanger.getDimensionName("FL_FORMSCHEMEKEY");
        String formKeyName = dimensionChanger.getDimensionName("FL_FORMKEY");
        dimensionValueSet.setValue(formSchemeKeyName, (Object)formSchemeKey);
        String message = "";
        String formLockObj = this.iTaskOptionController.getValue(taskKey, "FORM_LOCK");
        FormLockAuthType formLockType = FormLockAuthType.getTypeByCode(formLockObj);
        String multiUserLock = this.iTaskOptionController.getValue(taskKey, "MULTIUSER_FORM_LOCK");
        boolean multiUserLockOption = false;
        if (StringUtils.isNotEmpty((String)multiUserLock)) {
            multiUserLockOption = multiUserLock.equals("1");
        }
        boolean forceUnLockOption = false;
        if (lockParam.isForceUnLock()) {
            boolean systemIdentity = this.systemIdentityService.isAdmin();
            boolean userCanForceUnLock = false;
            if (systemIdentity) {
                userCanForceUnLock = true;
            } else {
                String userId;
                List userIdByRole;
                HashSet users;
                String forceLock = this.iTaskOptionController.getValue(taskKey, "FORCE_FORM_UNLOCK_ROLE");
                if (StringUtils.isNotEmpty((String)forceLock) && (users = new HashSet(userIdByRole = this.roleService.getUserIdByRole(forceLock))).contains(userId = NpContextHolder.getContext().getUserId())) {
                    userCanForceUnLock = true;
                }
            }
            if (userCanForceUnLock) {
                forceUnLockOption = true;
                if (!lock) {
                    formLockType = FormLockAuthType.FORCEUNLOCK;
                }
            }
        }
        if (formLockType == null) {
            throw new AccessException("\u62a5\u8868\u9501\u5b9a-\u6743\u9650\u5224\u65ad\u5f02\u5e38");
        }
        boolean chinese = I18nUtil.isChinese();
        LockParam param = new LockParam();
        param.setFormSchemeKey(lockParam.getFormSchemeKey());
        param.setMasterKeys(lockParam.getMasterKeys());
        for (String formKey : formKeys) {
            boolean hasEntityAuthor;
            try {
                hasEntityAuthor = ignoreAuth || this.hasFormAuthor(formLockType, formKey, dwCode, entityId) && this.hasLockUnitAuthor(dimensionValueSet, formScheme, formLockType);
            }
            catch (UnauthorizedEntityException e2) {
                throw new AccessException("\u62a5\u8868\u9501\u5b9a-\u6743\u9650\u5224\u65ad\u5f02\u5e38");
            }
            if (!hasEntityAuthor) {
                switch (formLockType) {
                    case WRITE: {
                        if (chinese) {
                            message = "\u5f53\u524d\u7528\u6237\u6ca1\u6709\u5199\u6743\u9650\uff0c\u4e0d\u53ef\u9501\u5b9a\u548c\u89e3\u9501";
                            break;
                        }
                        message = "The current user has no write permission and cannot be locked or unlocked";
                        break;
                    }
                    case UPLOAD: {
                        if (chinese) {
                            message = "\u5f53\u524d\u7528\u6237\u6ca1\u6709\u4e0a\u62a5\u6743\u9650\uff0c\u4e0d\u53ef\u9501\u5b9a\u548c\u89e3\u9501";
                            break;
                        }
                        message = "The current user has no upload permission and cannot be locked or unlocked";
                        break;
                    }
                    case AUDIT: {
                        if (chinese) {
                            message = "\u5f53\u524d\u7528\u6237\u6ca1\u6709\u5ba1\u6279\u6743\u9650\uff0c\u4e0d\u53ef\u9501\u5b9a\u548c\u89e3\u9501";
                            break;
                        }
                        message = "The current user has no audit permission and cannot be locked or unlocked";
                        break;
                    }
                }
                logHelper.error(taskKey, logDimensionCollection, "\u62a5\u8868" + content + "\u5931\u8d25", "\u62a5\u8868" + content + "\u5931\u8d25" + message);
                return message;
            }
            dimensionValueSet.setValue(formKeyName, (Object)formKey);
            try {
                ArrayList<String> fk = new ArrayList<String>();
                fk.add(formKey);
                param.setFormKeys(fk);
                if (lock) {
                    if (multiUserLockOption) {
                        if (this.isFormLockedByUser(param)) continue;
                        message = this.lock(lockParam.getMasterKeys(), dimensionValueSet, formScheme, formKey);
                        continue;
                    }
                    if (this.isFormLocked(param)) continue;
                    message = this.lock(lockParam.getMasterKeys(), dimensionValueSet, formScheme, formKey);
                    continue;
                }
                if (multiUserLockOption) {
                    message = this.unLock(lockParam.getMasterKeys(), dimensionValueSet, formScheme, formKey, forceUnLockOption, !forceUnLockOption);
                    continue;
                }
                if (!forceUnLockOption && !this.isFormLockedByUser(param)) continue;
                message = this.unLock(lockParam.getMasterKeys(), dimensionValueSet, formScheme, formKey, forceUnLockOption, false);
            }
            catch (Exception e3) {
                logHelper.error(taskKey, logDimensionCollection, "\u62a5\u8868" + content + "\u5931\u8d25", "\u62a5\u8868" + content + "\u5931\u8d25" + e3.getMessage());
            }
        }
        if (message.equals("")) {
            logHelper.info(taskKey, logDimensionCollection, "\u62a5\u8868" + content + "\u7ed3\u675f", "\u62a5\u8868" + content + "\u7ed3\u675f");
        } else {
            logHelper.error(taskKey, logDimensionCollection, "\u62a5\u8868" + content + "\u5931\u8d25", "\u62a5\u8868" + content + "\u5931\u8d25" + message);
        }
        return message;
    }

    private String lock(DimensionCollection dimensionCollection, DimensionValueSet masterKeys, FormSchemeDefine formScheme, String formKey) throws Exception {
        TableModelDefine formLockTable = this.getFormLockTable(formScheme);
        if (Objects.isNull(formLockTable)) {
            throw new AccessException(ERRORINFO);
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        List columns = this.dataModelService.getColumnModelDefinesByTable(formLockTable.getID());
        List columnCodes = columns.stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
        DimensionValueSet queryKey = this.rebuildDimKeyAndUser(masterKeys, formScheme.getKey(), formKey);
        INvwaDataSet dataTable = this.getFormLockDataTable(queryKey, formLockTable, formScheme.getKey(), false);
        INvwaUpdatableDataSet updatableDataSet = (INvwaUpdatableDataSet)dataTable;
        String userId = "";
        try {
            if (NpContextHolder.getContext().getUserName() != null) {
                userId = NpContextHolder.getContext().getUser().getId();
            }
            ArrayKey arrayKey = this.nvwaDataEngineQueryUtil.convertMasterToArrayKey(formLockTable.getName(), dataTable, queryKey);
            INvwaDataRow findRow = updatableDataSet.findRow(arrayKey);
            int lockIndex = columnCodes.indexOf("FL_ISLOCK");
            if (findRow == null) {
                INvwaDataRow appendRow = updatableDataSet.appendRow();
                this.nvwaDataEngineQueryUtil.setRowKey(formLockTable.getName(), appendRow, queryKey);
                appendRow.setValue(columnCodes.indexOf("FL_UPDATETIME"), (Object)new Timestamp(System.currentTimeMillis()));
                appendRow.setValue(columnCodes.indexOf("FL_FORMSCHEMEKEY"), (Object)formScheme.getKey());
                appendRow.setValue(columnCodes.indexOf("FL_ID"), (Object)UUID.randomUUID().toString());
                appendRow.setValue(columnCodes.indexOf("FL_FORMKEY"), (Object)formKey);
                appendRow.setValue(columnCodes.indexOf("FL_USER"), (Object)userId);
                appendRow.setValue(lockIndex, (Object)"1");
            } else {
                findRow.setValue(columnCodes.indexOf("FL_UPDATETIME"), (Object)new Timestamp(System.currentTimeMillis()));
                findRow.setValue(lockIndex, (Object)"1");
                findRow.setValue(columnCodes.indexOf("FL_USER"), (Object)userId);
            }
        }
        catch (Exception e) {
            throw new NotFoundTableDefineException(ExceptionCodeCost.NOTFOUND_TABLEFIELD, new String[]{this.dataAccesslUtil.getTableName(formScheme, "NR_STATE_%s_FORMLOCK")});
        }
        try {
            updatableDataSet.commitChanges(context);
            this.doHistoryData(dimensionCollection, formScheme, formKey, 1);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw e;
        }
        return "";
    }

    private void lock(INvwaDataSet dataSet, INvwaUpdatableDataSet updatableDataSet, DimensionValueSet masterKeys, FormSchemeDefine formScheme, String formKey) {
        TableModelDefine formLockTable = this.getFormLockTable(formScheme);
        if (Objects.isNull(formLockTable)) {
            throw new AccessException(ERRORINFO);
        }
        List columns = this.dataModelService.getColumnModelDefinesByTable(formLockTable.getID());
        List columnCodes = columns.stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
        DimensionValueSet queryKey = this.rebuildDimKeyAndUser(masterKeys, formScheme.getKey(), formKey);
        String userId = "";
        try {
            if (NpContextHolder.getContext().getUserName() != null) {
                userId = NpContextHolder.getContext().getUser().getId();
            }
            ArrayKey arrayKey = this.nvwaDataEngineQueryUtil.convertMasterToArrayKey(formLockTable.getName(), dataSet, queryKey);
            INvwaDataRow findRow = updatableDataSet.findRow(arrayKey);
            int lockIndex = columnCodes.indexOf("FL_ISLOCK");
            if (findRow == null) {
                INvwaDataRow appendRow = updatableDataSet.appendRow();
                this.nvwaDataEngineQueryUtil.setRowKey(formLockTable.getName(), appendRow, queryKey);
                appendRow.setValue(columnCodes.indexOf("FL_UPDATETIME"), (Object)new Timestamp(System.currentTimeMillis()));
                appendRow.setValue(columnCodes.indexOf("FL_FORMSCHEMEKEY"), (Object)formScheme.getKey());
                appendRow.setValue(columnCodes.indexOf("FL_ID"), (Object)UUID.randomUUID().toString());
                appendRow.setValue(columnCodes.indexOf("FL_FORMKEY"), (Object)formKey);
                appendRow.setValue(columnCodes.indexOf("FL_USER"), (Object)userId);
                appendRow.setValue(lockIndex, (Object)"1");
            } else {
                findRow.setValue(columnCodes.indexOf("FL_UPDATETIME"), (Object)new Timestamp(System.currentTimeMillis()));
                findRow.setValue(lockIndex, (Object)"1");
                findRow.setValue(columnCodes.indexOf("FL_USER"), (Object)userId);
            }
        }
        catch (Exception e) {
            throw new NotFoundTableDefineException(ExceptionCodeCost.NOTFOUND_TABLEFIELD, new String[]{this.dataAccesslUtil.getTableName(formScheme, "NR_STATE_%s_FORMLOCK")});
        }
    }

    private String unLock(DimensionCollection dimensionCollection, DimensionValueSet masterKeys, FormSchemeDefine formScheme, String formKey, boolean forceUnLock, boolean unlockByUser) throws Exception {
        TableModelDefine formLockTable = this.getFormLockTable(formScheme);
        if (Objects.isNull(formLockTable)) {
            throw new AccessException(ERRORINFO);
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        DimensionValueSet queryKey = unlockByUser ? this.rebuildDimKeyAndUser(masterKeys, formScheme.getKey(), formKey) : this.rebuildDimKey(masterKeys, formScheme.getKey(), formKey);
        INvwaDataSet dataTable = this.getFormLockDataTable(queryKey, formLockTable, formScheme.getKey(), false);
        INvwaUpdatableDataSet updatableDataSet = (INvwaUpdatableDataSet)dataTable;
        if (updatableDataSet.size() > 0) {
            if (unlockByUser) {
                this.doHistoryData(dimensionCollection, formScheme, formKey, 0);
            } else if (forceUnLock) {
                this.doHistoryData(dimensionCollection, formScheme, formKey, 2);
            } else {
                this.doHistoryData(dimensionCollection, formScheme, formKey, 0);
            }
        }
        updatableDataSet.deleteAll();
        try {
            updatableDataSet.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw e;
        }
        return "";
    }

    private void unLock(LockDTO lockDTO, INvwaDataSet dataSet, DataSetDefine hisDataSet, INvwaUpdatableDataSet updatableDataSet, DimensionValueSet masterKeys, FormSchemeDefine formScheme, String formKey, boolean forceUnLock, boolean unlockByUser, TableAndDataParamDTO tableAndDataParam) throws Exception {
        String userId = NpContextHolder.getContext().getUser().getId();
        List<ColumnModelDefine> keys = tableAndDataParam.getKeys();
        List<String> dimNames = tableAndDataParam.getDimNames();
        TableModelDefine formLockTable = this.getFormLockTable(formScheme);
        if (Objects.isNull(formLockTable)) {
            throw new AccessException(ERRORINFO);
        }
        List columns = this.dataModelService.getColumnModelDefinesByTable(formLockTable.getID());
        List columnCodes = columns.stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
        if (unlockByUser) {
            DimensionValueSet dimensionValueSet = this.rebuildDimKeyAndUser(masterKeys, formScheme.getKey(), formKey);
            ArrayKey arrayKey = this.nvwaDataEngineQueryUtil.convertMasterToArrayKey(formLockTable.getName(), dataSet, dimensionValueSet);
            INvwaDataRow row = updatableDataSet.findRow(arrayKey);
            if (row != null && "1".equals(row.getValue(columnCodes.indexOf("FL_ISLOCK")))) {
                updatableDataSet.deleteRow(arrayKey);
                this.appendHistory(hisDataSet, masterKeys, tableAndDataParam.getDwDimensionName(), formKey, formScheme, 0, userId);
            }
        } else {
            if (!lockDTO.rowMapInit()) {
                lockDTO.initRowMap(dataSet, keys);
            }
            int count = 0;
            ArrayKey arrayKey = this.getArrayKey(masterKeys, dimNames, formKey);
            List<INvwaDataRow> rows = lockDTO.getRows(arrayKey);
            if (!CollectionUtils.isEmpty(rows)) {
                for (INvwaDataRow item : rows) {
                    updatableDataSet.deleteRow(item.getRowKey());
                    ++count;
                }
            }
            if (count > 0) {
                if (forceUnLock) {
                    this.appendHistory(hisDataSet, masterKeys, tableAndDataParam.getDwDimensionName(), formKey, formScheme, 2, userId);
                } else {
                    this.appendHistory(hisDataSet, masterKeys, tableAndDataParam.getDwDimensionName(), formKey, formScheme, 0, userId);
                }
            }
        }
    }

    private void doHistoryData(DimensionCollection dimensionCollection, FormSchemeDefine formScheme, String formKey, int lock) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        List dimensionCollectionList = dimensionCollection.getDimensionCombinations();
        if (!dimensionCollectionList.isEmpty()) {
            dimensionValueSet = ((DimensionCombination)dimensionCollectionList.stream().findFirst().get()).toDimensionValueSet();
        }
        if (dimensionValueSet.hasValue("VERSIONID")) {
            dimensionValueSet.clearValue("VERSIONID");
        }
        String dwDimensionName = this.dataAccesslUtil.getDwDimensionName(formScheme.getDw());
        DataSetDefine lockHisDataSet = this.getLockHisDataSet(formScheme);
        this.appendHistory(lockHisDataSet, dimensionValueSet, dwDimensionName, formKey, formScheme, lock, NpContextHolder.getContext().getUserId());
        this.commitHistory(lockHisDataSet);
    }

    private DataSetDefine getLockHisDataSet(FormSchemeDefine formScheme) {
        TableModelDefine formLockTable = this.getFormLockHisTable(formScheme);
        if (Objects.isNull(formLockTable)) {
            throw new AccessException("\u672a\u627e\u5230\u9501\u5b9a\u5386\u53f2\u8868\uff01");
        }
        List colums = this.dataModelService.getColumnModelDefinesByTable(formLockTable.getID());
        List<String> columnCodes = colums.stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
        HashMap<String, Object> filter = new HashMap<String, Object>();
        filter.put("FLH_FORMSCHEME", formScheme.getKey());
        filter.put("FLH_ID", UUIDUtils.getKey());
        ColumnModelDefine flhIdCol = (ColumnModelDefine)colums.get(columnCodes.indexOf("FLH_ID"));
        ColumnModelDefine flhFormSchemeCol = (ColumnModelDefine)colums.get(columnCodes.indexOf("FLH_FORMSCHEME"));
        INvwaUpdatableDataSet updatableDataSet = this.nvwaDataEngineQueryUtil.queryUpdateDataSet(formLockTable.getCode(), null, colums, filter);
        return new DataSetDefine(formLockTable.getCode(), updatableDataSet, columnCodes, flhIdCol, flhFormSchemeCol);
    }

    private void appendHistory(DataSetDefine dataSetDefine, DimensionValueSet masterKeys, String dwDimName, String formKey, FormSchemeDefine formScheme, int lock, String userId) {
        INvwaUpdatableDataSet updatableDataSet = dataSetDefine.getDataset();
        try {
            INvwaDataRow appendRow = updatableDataSet.appendRow();
            DimensionSet dimensions = masterKeys.getDimensionSet();
            for (int index = 0; index < dimensions.size(); ++index) {
                String dimName;
                String code = dimName = dimensions.get(index);
                if ("DATATIME".equals(dimName)) {
                    code = "PERIOD";
                } else if (dwDimName.equals(dimName)) {
                    code = "MDCODE";
                }
                appendRow.setValue(dataSetDefine.getIndex(code), masterKeys.getValue(dimName));
            }
            appendRow.setKeyValue(dataSetDefine.flhId, (Object)UUIDUtils.getKey());
            appendRow.setValue(dataSetDefine.getIndex(dataSetDefine.flhFormScheme.getCode()), (Object)formScheme.getKey());
            appendRow.setValue(dataSetDefine.getIndex("FLH_OPERTIME"), (Object)new Timestamp(System.currentTimeMillis()));
            appendRow.setValue(dataSetDefine.getIndex("FLH_USER"), (Object)userId);
            appendRow.setValue(dataSetDefine.getIndex("FLH_OPER"), (Object)lock);
            appendRow.setValue(dataSetDefine.getIndex("FLH_FORM"), (Object)formKey);
        }
        catch (Exception e) {
            throw new NotFoundTableDefineException(ExceptionCodeCost.NOTFOUND_TABLEFIELD, new String[]{this.dataAccesslUtil.getTableName(formScheme, "NR_STATE_%s_FORMLOCK_HIS")});
        }
    }

    private void commitHistory(DataSetDefine dataSetDefine) {
        if (dataSetDefine == null || dataSetDefine.getDataset() == null) {
            return;
        }
        INvwaUpdatableDataSet updatableDataSet = dataSetDefine.getDataset();
        try {
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            updatableDataSet.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    @Override
    public void batchLockForm(LockParam lockParam, AsyncTaskMonitor asyncTaskMonitor) {
        String islockmessage;
        LockDTO lockDTO;
        double step;
        double progress;
        Assert.notNull((Object)lockParam.getFormSchemeKey(), "formSchemeKey is must not be null!");
        Assert.notNull((Object)lockParam.getMasterKeys(), "masterKeys is must not be null!");
        Assert.notNull((Object)lockParam.getTaskKey(), "taskKey is must not be null!");
        Assert.isTrue(!lockParam.getFormKeys().isEmpty(), "formKey is must not be null!");
        List<String> formKeys = lockParam.getFormKeys();
        String formSchemeKey = lockParam.getFormSchemeKey();
        String taskKey = lockParam.getTaskKey();
        DimensionCollection dimCollection = lockParam.getMasterKeys();
        boolean isLock = lockParam.isLock();
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
        boolean ignoreAuth = lockParam.isIgnoreAuth();
        boolean chinese = I18nUtil.isChinese();
        if (!this.isEnableFormLock(taskKey, formSchemeKey)) {
            asyncTaskMonitor.error("no_enable_lock_function", null, null);
        }
        ArrayList<BatchLockUnitFormsInfo> noAuthInfos = new ArrayList<BatchLockUnitFormsInfo>();
        HashMap<String, Set<String>> noAuthUnitFormMap = new HashMap<String, Set<String>>();
        SystemOptionsDTO systemOptions = this.getSystemOptions(taskKey, isLock, lockParam.isForceUnLock());
        TableAndDataParamDTO tableAndDataParam = this.getTableAndDataParam(taskDefine, formScheme);
        List collections = dimCollection.getDimensionCombinations();
        tableAndDataParam.setCollections(collections);
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("\u6570\u636e\u6743\u9650\u670d\u52a1", OperLevel.USER_OPER);
        DimensionValueSet dimensionSet = DimensionValueSetUtil.mergeDimensionValueSet(dimCollection);
        String periodCode = String.valueOf(dimensionSet.getValue(tableAndDataParam.getPeriodDimensionName()));
        String dateTime = formScheme.getDateTime();
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setPeriod(dateTime, periodCode);
        logDimensionCollection.setDw(tableAndDataParam.getEntityId(), (String[])collections.stream().map(e -> (String)e.getValue(tableAndDataParam.getDwDimensionName())).toArray(String[]::new));
        asyncTaskMonitor.progressAndMessage(0.05, "");
        IDataAccessService dataAccessService = this.provider.getDataAccessService(taskKey, formSchemeKey);
        IBatchAccessResult visitAccess = dataAccessService.getVisitAccess(dimCollection, formKeys);
        List<DimensionValueSet> masterKeyList = collections.stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList());
        DimensionValueSet masterKey = DimensionValueSetUtil.mergeDimensionValueSet(masterKeyList);
        tableAndDataParam.setMasterKey(masterKey);
        List<String> allDwCodes = this.getAllDwCodes(masterKey, tableAndDataParam.getDwDimensionName());
        Set<String> lockUnitAuthor = this.getLockUnitAuthor(masterKey, formScheme, systemOptions.getFormLockType(), allDwCodes);
        Map<String, Map<String, Boolean>> formAuth = this.hasFormAuthor(systemOptions.getFormLockType(), formKeys, allDwCodes, tableAndDataParam.getEntityId());
        int fuzzyRowCount = collections.size() * formKeys.size();
        ArrayList<List<String>> formList = new ArrayList<List<String>>();
        if (fuzzyRowCount > 200000) {
            for (int i = 0; i < formKeys.size(); ++i) {
                formList.add(formKeys.subList(i, i + 1));
            }
        } else {
            formList.add(formKeys);
        }
        CancelFormInfo cancelFormInfo = new CancelFormInfo();
        cancelFormInfo.setAllFormKeys(lockParam.getFormKeys());
        cancelFormInfo.setDoneFormKeys(new ArrayList<String>());
        boolean b = false;
        if (lockParam.isLock()) {
            logHelper.info(formScheme.getTaskKey(), logDimensionCollection, "\u62a5\u8868\u6279\u91cf\u9501\u5b9a\u5f00\u59cb", "\u62a5\u8868\u6279\u91cf\u9501\u5b9a\u5f00\u59cb");
            progress = 0.05;
            step = 0.9 / (double)formList.size();
            for (List list : formList) {
                lockDTO = new LockDTO();
                lockDTO.setLockIndex(tableAndDataParam.getIsLockColIndex());
                lockDTO.setFormAuth(formAuth);
                lockDTO.setAccessUnit(lockUnitAuthor);
                progress += step;
                try {
                    b = this.batchLockForm(systemOptions, tableAndDataParam, list, visitAccess, noAuthUnitFormMap, ignoreAuth, formScheme, lockDTO, asyncTaskMonitor);
                }
                catch (Exception e2) {
                    logHelper.error(formScheme.getTaskKey(), logDimensionCollection, "\u62a5\u8868\u6279\u91cf\u9501\u5b9a\u5f02\u5e38", "\u62a5\u8868\u6279\u91cf\u9501\u5b9a\u5f02\u5e38" + e2.getMessage());
                    throw new NotFoundTableDefineException(ExceptionCodeCost.NOTFOUND_TABLEFIELD, new String[]{tableAndDataParam.getTable().getName()});
                }
                if (b) {
                    cancelFormInfo.getDoneFormKeys().addAll(list);
                }
                if (StateFormLockServiceImpl.cancelDeal(asyncTaskMonitor, cancelFormInfo)) {
                    logHelper.info(formScheme.getTaskKey(), logDimensionCollection, "\u62a5\u8868\u6279\u91cf\u9501\u5b9a\u53d6\u6d88", "\u62a5\u8868\u6279\u91cf\u9501\u5b9a\u53d6\u6d88\u5b8c\u6210");
                    return;
                }
                asyncTaskMonitor.progressAndMessage(progress, "");
            }
        } else {
            logHelper.info(formScheme.getTaskKey(), logDimensionCollection, "\u62a5\u8868\u6279\u91cf\u89e3\u9501\u5f00\u59cb", "\u62a5\u8868\u6279\u91cf\u89e3\u9501\u5f00\u59cb");
            progress = 0.05;
            step = 0.9 / (double)formList.size();
            for (List list : formList) {
                lockDTO = new LockDTO();
                lockDTO.setLockIndex(tableAndDataParam.getIsLockColIndex());
                lockDTO.setFormAuth(formAuth);
                lockDTO.setAccessUnit(lockUnitAuthor);
                progress += step;
                try {
                    String dwDimensionName = tableAndDataParam.getDwDimensionName();
                    if (systemOptions.isSystem()) {
                        if (StringUtils.isNotEmpty((String)String.valueOf(masterKey.getValue(dwDimensionName)))) {
                            b = this.systemBatchUnLock(lockDTO, tableAndDataParam, list, formScheme, systemOptions.isForceUnLockOption(), asyncTaskMonitor);
                        }
                    } else if (StringUtils.isNotEmpty((String)String.valueOf(masterKey.getValue(dwDimensionName)))) {
                        b = this.batchUnLockForm(lockDTO, systemOptions, tableAndDataParam, ignoreAuth, formScheme, noAuthUnitFormMap, visitAccess, list, asyncTaskMonitor);
                    }
                }
                catch (Exception e3) {
                    logHelper.error(formScheme.getTaskKey(), logDimensionCollection, "\u62a5\u8868\u6279\u91cf\u89e3\u9501\u5f02\u5e38", "\u62a5\u8868\u6279\u91cf\u89e3\u9501\u5f02\u5e38" + e3.getMessage());
                    throw new NotFoundTableDefineException(ExceptionCodeCost.NOTFOUND_TABLEFIELD, new String[]{tableAndDataParam.getTable().getName()});
                }
                if (b) {
                    cancelFormInfo.getDoneFormKeys().addAll(list);
                }
                if (StateFormLockServiceImpl.cancelDeal(asyncTaskMonitor, cancelFormInfo)) {
                    logHelper.info(formScheme.getTaskKey(), logDimensionCollection, "\u62a5\u8868\u6279\u91cf\u89e3\u9501\u53d6\u6d88", "\u62a5\u8868\u6279\u91cf\u89e3\u9501\u53d6\u6d88\u5b8c\u6210");
                    return;
                }
                asyncTaskMonitor.progressAndMessage(progress, "");
            }
        }
        if (!noAuthUnitFormMap.isEmpty()) {
            HashMap<String, String> findByEntityKeys = new HashMap<String, String>();
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            executorContext.setPeriodView(taskDefine.getDateTime());
            DimensionValueSet queryKey = new DimensionValueSet();
            queryKey.setValue("DATATIME", masterKey.getValue("DATATIME"));
            IDataEntity entityTable = null;
            HashMap<String, String> formTitleMap = new HashMap<String, String>();
            HashSet hashSet = new HashSet();
            HashSet queryFormKeys = new HashSet();
            for (Map.Entry entry : noAuthUnitFormMap.entrySet()) {
                Set set = (Set)entry.getValue();
                if (CollectionUtils.isEmpty(set)) continue;
                hashSet.add(entry.getKey());
                queryFormKeys.addAll(set);
            }
            List formDefines = this.runtimeView.queryFormsById(new ArrayList(queryFormKeys));
            for (FormDefine formDefine : formDefines) {
                formTitleMap.put(formDefine.getKey(), formDefine.getTitle());
            }
            try {
                EntityViewDefine dwEntity = this.dataAccesslUtil.queryDWEntityViewDefine(formScheme, taskDefine);
                entityTable = this.dataEntityService.getIEntityTable(dwEntity, executorContext, queryKey, formSchemeKey);
            }
            catch (Exception e4) {
                logger.error("\u67e5\u8be2\u5b9e\u4f53\u6570\u636e\u5f02\u5e38\uff01", e4);
            }
            for (String string : hashSet) {
                if (entityTable == null) {
                    logger.error("\u672a\u67e5\u8be2\u5230\u5b9e\u4f53\u6570\u636e\uff01");
                    break;
                }
                IDataEntityRow entityDataByKey = entityTable.findByEntityKey(string);
                List entityRow = entityDataByKey.getRowList();
                if (CollectionUtils.isEmpty(entityRow)) {
                    logger.warn("\u672a\u67e5\u8be2\u5230\u5b9e\u4f53\u6570\u636e\u884c\uff01unitKey\uff1a" + string);
                    continue;
                }
                IEntityRow iEntityRow = (IEntityRow)entityRow.get(0);
                if (iEntityRow == null) continue;
                findByEntityKeys.put(string, iEntityRow.getTitle());
            }
            for (Map.Entry entry : noAuthUnitFormMap.entrySet()) {
                if (CollectionUtils.isEmpty((Collection)entry.getValue())) continue;
                BatchLockUnitFormsInfo returnInfo = new BatchLockUnitFormsInfo();
                returnInfo.setUnitTitle((String)findByEntityKeys.get(entry.getKey()));
                ArrayList<String> formTitles = new ArrayList<String>();
                returnInfo.setFormTitle(formTitles);
                for (String formKey : (Set)entry.getValue()) {
                    formTitles.add((String)formTitleMap.get(formKey));
                }
                noAuthInfos.add(returnInfo);
            }
        }
        String result = "";
        BatchLockReturnInfo batchLockReturnInfo = new BatchLockReturnInfo();
        batchLockReturnInfo.setNoAuthUnitForms(noAuthInfos);
        batchLockReturnInfo.setLock(isLock);
        FormLockAuthType formLockType = systemOptions.getFormLockType();
        if (chinese) {
            batchLockReturnInfo.setNoAuthReason(FormLockAuthType.WRITE == formLockType ? "\u5199\u6743\u9650" : (FormLockAuthType.UPLOAD == formLockType ? "\u4e0a\u62a5\u6743\u9650" : "\u5ba1\u6279\u6743\u9650"));
        } else {
            batchLockReturnInfo.setNoAuthReason(FormLockAuthType.WRITE == formLockType ? " write permission" : (FormLockAuthType.UPLOAD == formLockType ? "upload permission" : "audit permission"));
        }
        try {
            result = this.objectMapper.writeValueAsString((Object)batchLockReturnInfo);
        }
        catch (Exception e5) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e5.getMessage(), e5);
        }
        String string = islockmessage = isLock ? "\u9501\u5b9a" : "\u89e3\u9501";
        if (noAuthInfos.isEmpty()) {
            logHelper.info(formScheme.getTaskKey(), logDimensionCollection, "\u62a5\u8868\u6279\u91cf" + islockmessage + "\u5b8c\u6210", "\u62a5\u8868\u6279\u91cf" + islockmessage + "\u5b8c\u6210");
            if (chinese) {
                asyncTaskMonitor.finish(isLock ? "batch_lock_success_info" : "batch_unlock_success_info", (Object)result);
            } else {
                asyncTaskMonitor.finish(isLock ? "Batch locking completed" : "Batch unlocking completed", (Object)result);
            }
        } else {
            String message = "\u5b58\u5728\u62a5\u8868%s\u5931\u8d25";
            String string2 = String.format(message, islockmessage);
            logHelper.info(formScheme.getTaskKey(), logDimensionCollection, "\u62a5\u8868\u6279\u91cf" + islockmessage + "\u5b8c\u6210" + string2, result);
            if (chinese) {
                asyncTaskMonitor.error(isLock ? "batch_lock_fail_info" : "batch_unlock_fail_info", null, result);
            } else {
                asyncTaskMonitor.error(isLock ? "Batch locking completed\uff0creports failed to lock,click to view details" : "Batch unlocking completed,some reports failed to unlock,click to view details", null, result);
            }
        }
    }

    private static boolean cancelDeal(AsyncTaskMonitor asyncTaskMonitor, CancelFormInfo cancelFormInfo) {
        if (asyncTaskMonitor.isCancel()) {
            if (CollectionUtils.isEmpty(cancelFormInfo.getDoneFormKeys())) {
                asyncTaskMonitor.canceled(null, (Object)cancelFormInfo);
            } else {
                CancelInfoVO cancelInfoVO = new CancelInfoVO();
                cancelInfoVO.setCode("lock_cancel");
                cancelInfoVO.setParam(new CancelInfoVO.Param(cancelFormInfo.getDoneFormKeys().size()));
                asyncTaskMonitor.canceled(JsonUtil.objectToJson((Object)cancelInfoVO), (Object)cancelFormInfo);
            }
            return true;
        }
        return false;
    }

    private SystemOptionsDTO getSystemOptions(String taskKey, boolean isLock, boolean isForceUnLock) {
        SystemOptionsDTO systemOptionsDTO = new SystemOptionsDTO();
        String userId = NpContextHolder.getContext().getUser().getId();
        systemOptionsDTO.setUserId(userId);
        String formLockCode = this.iTaskOptionController.getValue(taskKey, "FORM_LOCK");
        FormLockAuthType formLockType = FormLockAuthType.getTypeByCode(formLockCode);
        boolean isSystem = this.systemIdentityService.isAdmin();
        String multiUserLock = this.iTaskOptionController.getValue(taskKey, "MULTIUSER_FORM_LOCK");
        boolean multiUserLockOption = false;
        if (StringUtils.isNotEmpty((String)multiUserLock)) {
            multiUserLockOption = multiUserLock.equals("1");
        }
        systemOptionsDTO.setMultiUserLockOption(multiUserLockOption);
        boolean forceUnLockOption = false;
        if (isForceUnLock) {
            if (isSystem) {
                forceUnLockOption = true;
                if (!isLock) {
                    formLockType = FormLockAuthType.FORCEUNLOCK;
                }
            } else {
                List userIdByRole;
                HashSet users;
                String forceLock = this.iTaskOptionController.getValue(taskKey, "FORCE_FORM_UNLOCK_ROLE");
                if (StringUtils.isNotEmpty((String)forceLock) && (users = new HashSet(userIdByRole = this.roleService.getUserIdByRole(forceLock))).contains(userId)) {
                    forceUnLockOption = true;
                    if (!isLock) {
                        formLockType = FormLockAuthType.FORCEUNLOCK;
                    }
                }
            }
        }
        systemOptionsDTO.setForceUnLockOption(forceUnLockOption);
        systemOptionsDTO.setFormLockType(formLockType);
        systemOptionsDTO.setSystem(isSystem);
        return systemOptionsDTO;
    }

    private TableAndDataParamDTO getTableAndDataParam(TaskDefine taskDefine, FormSchemeDefine formScheme) {
        TableAndDataParamDTO tableAndDataParamDTO = new TableAndDataParamDTO();
        TableModelDefine table = this.getFormLockTable(formScheme);
        tableAndDataParamDTO.setTable(table);
        DataAccessContext dataContext = new DataAccessContext(this.dataModelService);
        tableAndDataParamDTO.setDataContext(dataContext);
        String dwDimensionName = this.dataAccesslUtil.getDwDimensionName(formScheme.getDw());
        tableAndDataParamDTO.setDwDimensionName(dwDimensionName);
        String entityId = this.dataAccesslUtil.contextEntityId(formScheme.getDw());
        tableAndDataParamDTO.setEntityId(entityId);
        String periodDimensionName = this.dataAccesslUtil.getPeriodDimensionName(entityId);
        tableAndDataParamDTO.setPeriodDimensionName(periodDimensionName);
        List allColumns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        HashMap<String, ColumnModelDefine> fieldNameAndDefine = new HashMap<String, ColumnModelDefine>();
        int isLockColIndex = -1;
        for (int i = 0; i < allColumns.size(); ++i) {
            ColumnModelDefine columnModelDefine = (ColumnModelDefine)allColumns.get(i);
            if (columnModelDefine.getCode().equals("FL_ISLOCK")) {
                isLockColIndex = i;
            }
            fieldNameAndDefine.put(columnModelDefine.getCode(), columnModelDefine);
        }
        tableAndDataParamDTO.setIsLockColIndex(isLockColIndex);
        String dims = taskDefine.getDims();
        ArrayList<String> codeName = new ArrayList<String>();
        ArrayList<String> dimNames = new ArrayList<String>();
        dimNames.add(dwDimensionName);
        codeName.add("MDCODE");
        if (Objects.nonNull(dims)) {
            String[] dimAry = dims.split(";");
            for (String key : dimAry) {
                if ("ADJUST".equals(key)) {
                    codeName.add(key);
                    dimNames.add(key);
                    continue;
                }
                IEntityDefine otherDim = this.iEntityMetaService.queryEntity(key);
                if (otherDim == null) continue;
                codeName.add(otherDim.getDimensionName());
                dimNames.add(otherDim.getDimensionName());
            }
        }
        codeName.add("PERIOD");
        dimNames.add("DATATIME");
        tableAndDataParamDTO.setDimNames(dimNames);
        ArrayList<ColumnModelDefine> keys = new ArrayList<ColumnModelDefine>();
        for (String dimName : codeName) {
            ColumnModelDefine columnModelDefine = (ColumnModelDefine)fieldNameAndDefine.get(dimName);
            keys.add(columnModelDefine);
        }
        keys.add((ColumnModelDefine)fieldNameAndDefine.get("FL_FORMKEY"));
        tableAndDataParamDTO.setKeys(keys);
        return tableAndDataParamDTO;
    }

    private boolean batchUnLockForm(LockDTO lockDTO, SystemOptionsDTO systemOptionsDTO, TableAndDataParamDTO tableAndDataParam, boolean ignoreAuth, FormSchemeDefine formScheme, Map<String, Set<String>> noAuthUnitFormMap, IBatchAccessResult visitAccess, List<String> formKeys, AsyncTaskMonitor monitor) throws Exception {
        DimensionValueSet dataKey = this.rebuildDimKey(tableAndDataParam.getMasterKey(), formScheme.getKey(), formKeys);
        TableModelDefine table = tableAndDataParam.getTable();
        INvwaDataSet lockDataTable = this.getFormLockDataTable(dataKey, table, formScheme.getKey(), false);
        INvwaUpdatableDataSet formLockDataTable = (INvwaUpdatableDataSet)lockDataTable;
        DataSetDefine lockHisDataSet = this.getLockHisDataSet(formScheme);
        FormLockAuthType formLockType = systemOptionsDTO.getFormLockType();
        boolean forceUnLockOption = systemOptionsDTO.isForceUnLockOption();
        for (DimensionCombination dimensionCombination : tableAndDataParam.getCollections()) {
            DimensionValueSet dimension = dimensionCombination.toDimensionValueSet();
            String unit = String.valueOf(dimension.getValue(tableAndDataParam.getDwDimensionName()));
            if (!ignoreAuth && !lockDTO.getAccessUnit().contains(unit)) {
                Set<String> forms = noAuthUnitFormMap.get(unit);
                for (String formKey : formKeys) {
                    IAccessResult access = visitAccess.getAccess(dimensionCombination, formKey);
                    if (!access.haveAccess()) continue;
                    if (forms == null) {
                        forms = new HashSet<String>();
                    }
                    forms.add(formKey);
                }
                if (CollectionUtils.isEmpty(forms)) continue;
                noAuthUnitFormMap.put(unit, forms);
                continue;
            }
            for (String formKey : formKeys) {
                if (monitor.isCancel()) {
                    return false;
                }
                IAccessResult access = visitAccess.getAccess(dimensionCombination, formKey);
                if (!access.haveAccess()) continue;
                Map<String, Map<String, Boolean>> formAuth = lockDTO.getFormAuth();
                if (!ignoreAuth && !formAuth.get(unit).get(formKey).booleanValue()) {
                    if (noAuthUnitFormMap.containsKey(unit)) {
                        noAuthUnitFormMap.get(unit).add(formKey);
                        continue;
                    }
                    HashSet<String> noAuthFormKeys = new HashSet<String>();
                    noAuthFormKeys.add(formKey);
                    noAuthUnitFormMap.put(unit, noAuthFormKeys);
                    continue;
                }
                if (systemOptionsDTO.isMultiUserLockOption()) {
                    this.unLock(lockDTO, lockDataTable, lockHisDataSet, formLockDataTable, dimension, formScheme, formKey, forceUnLockOption, !forceUnLockOption, tableAndDataParam);
                    continue;
                }
                if (!forceUnLockOption && !this.isFormLockedByUser((INvwaDataSet)formLockDataTable, dimension, table, formScheme, formKey)) continue;
                this.unLock(lockDTO, lockDataTable, lockHisDataSet, formLockDataTable, dimension, formScheme, formKey, forceUnLockOption, false, tableAndDataParam);
            }
        }
        formLockDataTable.commitChanges(tableAndDataParam.getDataContext());
        this.commitHistory(lockHisDataSet);
        return true;
    }

    private boolean systemBatchUnLock(LockDTO lockDTO, TableAndDataParamDTO tableAndDataParam, List<String> formKeys, FormSchemeDefine formScheme, boolean forceUnLockOption, AsyncTaskMonitor monitor) throws Exception {
        DimensionValueSet dataKey = this.rebuildDimKey(tableAndDataParam.getMasterKey(), formScheme.getKey(), formKeys);
        INvwaDataSet lockDataTable = this.getFormLockDataTable(dataKey, tableAndDataParam.getTable(), formScheme.getKey(), false);
        INvwaUpdatableDataSet formLockDataTable = (INvwaUpdatableDataSet)lockDataTable;
        DataSetDefine lockHisDataSet = this.getLockHisDataSet(formScheme);
        for (DimensionCombination dimensionCombination : tableAndDataParam.getCollections()) {
            for (String formKey : formKeys) {
                if (monitor.isCancel()) {
                    return false;
                }
                DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
                this.unLock(lockDTO, lockDataTable, lockHisDataSet, formLockDataTable, dimensionValueSet, formScheme, formKey, forceUnLockOption, !forceUnLockOption, tableAndDataParam);
            }
        }
        formLockDataTable.commitChanges(tableAndDataParam.getDataContext());
        this.commitHistory(lockHisDataSet);
        return true;
    }

    private boolean isFormLockedByUser(INvwaDataSet formLockDataTable, DimensionValueSet dimensionValueSet, TableModelDefine tableModel, FormSchemeDefine formScheme, String formKey) {
        DimensionValueSet queryKey = this.rebuildDimKeyAndUser(dimensionValueSet, formScheme.getKey(), formKey);
        String formLockTable = this.dataAccesslUtil.getTableName(formScheme, "NR_STATE_%s_FORMLOCK");
        ArrayKey arrayKey = this.nvwaDataEngineQueryUtil.convertMasterToArrayKey(formLockTable, formLockDataTable, queryKey);
        INvwaDataRow findRow = formLockDataTable.findRow(arrayKey);
        if (findRow != null) {
            Object isLock = findRow.getValue(this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), "FL_ISLOCK"));
            return "1".equals(isLock);
        }
        return false;
    }

    private boolean isLocked(LockDTO lockDTO, ArrayKey arrayKey) {
        List<INvwaDataRow> rows = lockDTO.getRows(arrayKey);
        if (CollectionUtils.isEmpty(rows)) {
            return false;
        }
        for (INvwaDataRow row : rows) {
            Object value = row.getValue(lockDTO.getLockIndex());
            boolean lock = "1".equals(value);
            if (!lock) continue;
            return true;
        }
        return false;
    }

    private boolean isFormLocked(INvwaDataSet formLockDataTable, TableModelDefine tableModel, DimensionValueSet dimensionValueSet, Map<String, ColumnModelDefine> dims, String formKey) {
        int totalCount = formLockDataTable.size();
        for (int i = 0; i < totalCount; ++i) {
            INvwaDataRow item = formLockDataTable.getRow(i);
            if (item == null) continue;
            Object value = item.getValue(this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), "FL_FORMKEY"));
            Object isLock = item.getValue(this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), "FL_ISLOCK"));
            boolean condition = false;
            for (Map.Entry<String, ColumnModelDefine> map : dims.entrySet()) {
                String value2;
                String dimName = map.getKey();
                ColumnModelDefine define = map.getValue();
                String value1 = (String)item.getValue(define);
                if (value1.equals(value2 = (String)dimensionValueSet.getValue(dimName))) continue;
                condition = true;
                break;
            }
            if (condition || !formKey.equals(value) || !"1".equals(isLock)) continue;
            return true;
        }
        return false;
    }

    private boolean batchLockForm(SystemOptionsDTO systemOptionsDTO, TableAndDataParamDTO tableAndDataParam, List<String> formKeys, IBatchAccessResult visitAccess, Map<String, Set<String>> noAuthUnitFormMap, boolean ignoreAuth, FormSchemeDefine formScheme, LockDTO lockDTO, AsyncTaskMonitor monitor) throws Exception {
        DimensionValueSet dataKey = this.rebuildDimKey(tableAndDataParam.getMasterKey(), formScheme.getKey(), formKeys);
        INvwaDataSet lockDataTable = this.getFormLockDataTable(dataKey, tableAndDataParam.getTable(), formScheme.getKey(), false);
        INvwaUpdatableDataSet formLockDataTable = (INvwaUpdatableDataSet)lockDataTable;
        DataSetDefine lockHisDataSet = this.getLockHisDataSet(formScheme);
        String userId = systemOptionsDTO.getUserId();
        for (DimensionCombination dimensionCombination : tableAndDataParam.getCollections()) {
            String unit = String.valueOf(dimensionCombination.getValue(tableAndDataParam.getDwDimensionName()));
            if (!ignoreAuth && !lockDTO.getAccessUnit().contains(unit)) {
                Set forms = noAuthUnitFormMap.computeIfAbsent(unit, k -> new HashSet());
                for (String formKey : formKeys) {
                    IAccessResult access = visitAccess.getAccess(dimensionCombination, formKey);
                    if (!access.haveAccess()) continue;
                    forms.add(formKey);
                }
                continue;
            }
            for (String formKey : formKeys) {
                ArrayKey arrayKey;
                if (monitor.isCancel()) {
                    return false;
                }
                IAccessResult access = visitAccess.getAccess(dimensionCombination, formKey);
                if (!access.haveAccess()) continue;
                Map<String, Map<String, Boolean>> formAuth = lockDTO.getFormAuth();
                if (!ignoreAuth && !formAuth.get(unit).get(formKey).booleanValue()) {
                    if (noAuthUnitFormMap.containsKey(unit)) {
                        noAuthUnitFormMap.get(unit).add(formKey);
                        continue;
                    }
                    HashSet<String> noAuthformKeys = new HashSet<String>();
                    noAuthformKeys.add(formKey);
                    noAuthUnitFormMap.put(unit, noAuthformKeys);
                    continue;
                }
                DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
                if (systemOptionsDTO.isMultiUserLockOption()) {
                    if (this.isFormLockedByUser(lockDataTable, dimensionValueSet, tableAndDataParam.getTable(), formScheme, formKey)) continue;
                    this.lock(lockDataTable, formLockDataTable, dimensionValueSet, formScheme, formKey);
                    this.appendHistory(lockHisDataSet, dimensionCombination.toDimensionValueSet(), tableAndDataParam.getDwDimensionName(), formKey, formScheme, 1, userId);
                    continue;
                }
                if (!lockDTO.rowMapInit()) {
                    lockDTO.initRowMap(lockDataTable, tableAndDataParam.getKeys());
                }
                if (this.isLocked(lockDTO, arrayKey = this.getArrayKey(dimensionValueSet, tableAndDataParam.getDimNames(), formKey))) continue;
                this.lock(lockDataTable, formLockDataTable, dimensionValueSet, formScheme, formKey);
                this.appendHistory(lockHisDataSet, dimensionCombination.toDimensionValueSet(), tableAndDataParam.getDwDimensionName(), formKey, formScheme, 1, userId);
            }
        }
        formLockDataTable.commitChanges(tableAndDataParam.getDataContext());
        this.commitHistory(lockHisDataSet);
        return true;
    }

    @Override
    public List<FormLockBatchReadWriteResult> batchDimension(LockParam param) {
        ColumnModelDefine userIdField;
        ColumnModelDefine lockFormKeyField;
        ColumnModelDefine lockField;
        Assert.notNull((Object)param.getFormSchemeKey(), "formSchemeKey is must not be null!");
        Assert.notNull((Object)param.getMasterKeys(), "masterKeys is must not be null!");
        String formSchemeKey = param.getFormSchemeKey();
        DimensionCollection dimensionCollection = param.getMasterKeys();
        List<String> formKeys = param.getFormKeys();
        formKeys = CollectionUtils.isEmpty(formKeys) ? null : formKeys;
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        String entityDimensionName = this.dataAccesslUtil.getDwDimensionName(formScheme.getDw());
        List collections = dimensionCollection.getDimensionCombinations();
        List<DimensionValueSet> masterKeyList = collections.stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList());
        DimensionValueSet masterKey = DimensionValueSetUtil.mergeDimensionValueSet(masterKeyList);
        if (StringUtils.isEmpty((String)String.valueOf(masterKey.getValue(entityDimensionName)))) {
            masterKey.clearValue(entityDimensionName);
        }
        masterKey = this.rebuildDimKey(masterKey, formSchemeKey, formKeys);
        ArrayList<FormLockBatchReadWriteResult> list = new ArrayList<FormLockBatchReadWriteResult>();
        if (!this.isEnableFormLock(formScheme.getTaskKey(), formSchemeKey)) {
            return list;
        }
        TableModelDefine formLockTable = this.getFormLockTable(formScheme);
        if (Objects.isNull(formLockTable)) {
            throw new AccessException(ERRORINFO);
        }
        List columns = this.dataModelService.getColumnModelDefinesByTable(formLockTable.getID());
        Map columnMap = columns.stream().collect(Collectors.toMap(ColumnModelDefine::getName, Function.identity(), (e, o) -> o));
        try {
            lockField = (ColumnModelDefine)columnMap.get("FL_ISLOCK");
            lockFormKeyField = (ColumnModelDefine)columnMap.get("FL_FORMKEY");
            userIdField = (ColumnModelDefine)columnMap.get("FL_USER");
        }
        catch (Exception e2) {
            throw new NotFoundTableDefineException(ExceptionCodeCost.NOTFOUND_TABLEFIELD, new String[]{this.dataAccesslUtil.getTableName(formScheme, "NR_STATE_%s_FORMLOCK")});
        }
        INvwaDataSet dataSet = this.nvwaDataEngineQueryUtil.queryDataSetWithRowKey(formLockTable.getName(), masterKey, columns, new ArrayList<String>(), new HashMap<String, Boolean>(), true);
        int totalCount = dataSet.size();
        for (int i = 0; i < totalCount; ++i) {
            try {
                FormLockBatchReadWriteResult formLockBatchReadWriteResult = new FormLockBatchReadWriteResult();
                INvwaDataRow item = dataSet.getRow(i);
                ArrayKey rowKey = item.getRowKey();
                DimensionValueSet oriMasterKeys = this.nvwaDataEngineQueryUtil.convertRowKeyToDimensionValueSet(formLockTable.getName(), rowKey, dataSet.getRowKeyColumns());
                oriMasterKeys = DimensionValueSetUtil.filterDimensionValueSet(oriMasterKeys, "FL_FORMKEY");
                oriMasterKeys = DimensionValueSetUtil.filterDimensionValueSet(oriMasterKeys, "FL_USER");
                formLockBatchReadWriteResult.setDimensionValueSet(oriMasterKeys);
                Object value = item.getValue(lockField);
                formLockBatchReadWriteResult.setLock("1".equals(value));
                Object formKey = item.getValue(lockFormKeyField);
                formLockBatchReadWriteResult.setFormKey(String.valueOf(formKey));
                Object userId = item.getValue(userIdField);
                formLockBatchReadWriteResult.setUserId(String.valueOf(userId));
                list.add(formLockBatchReadWriteResult);
                continue;
            }
            catch (Exception e3) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e3.getMessage(), e3);
            }
        }
        return list;
    }

    @Override
    public List<FormLockHistory> getLockHistory(DimensionCombination dimension, String formSchemeKey, String formKey) {
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        Map<String, Object> filterMap = this.buildFormLockHisFilter(dimension, formScheme, formKey);
        TableModelDefine tableModel = this.getFormLockHisTable(formScheme);
        String tableCode = tableModel.getName();
        List fields = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        HashMap<String, Boolean> orderMap = new HashMap<String, Boolean>();
        orderMap.put("FLH_OPERTIME", true);
        INvwaDataSet dataSet = this.nvwaDataEngineQueryUtil.queryDataSet(tableCode, null, fields, new ArrayList<String>(), filterMap, orderMap, true);
        Map columnMap = fields.stream().collect(Collectors.toMap(IModelDefineItem::getCode, Function.identity(), (n, o) -> o));
        ArrayList<FormLockHistory> list = new ArrayList<FormLockHistory>();
        int totalCount = dataSet.size();
        for (int i = 0; i < totalCount; ++i) {
            FormLockHistory history = new FormLockHistory();
            INvwaDataRow item = dataSet.getRow(i);
            history.setFormKey(formKey);
            history.setId((String)item.getValue((ColumnModelDefine)columnMap.get("FLH_ID")));
            history.setOper((Integer)item.getValue((ColumnModelDefine)columnMap.get("FLH_OPER")));
            history.setUserID((String)item.getValue((ColumnModelDefine)columnMap.get("FLH_USER")));
            GregorianCalendar time = (GregorianCalendar)item.getValue((ColumnModelDefine)columnMap.get("FLH_OPERTIME"));
            history.setOperTime(time.getTime());
            list.add(history);
        }
        return list;
    }

    private Map<String, Object> buildFormLockHisFilter(DimensionCombination dimension, FormSchemeDefine formScheme, String formKey) {
        String dwDimensionName = this.dataAccesslUtil.getDwDimensionName(formScheme.getDw());
        HashMap<String, Object> filterMap = new HashMap<String, Object>();
        for (FixedDimensionValue fixedDimensionValue : dimension) {
            String dimName = fixedDimensionValue.getName();
            Object value = fixedDimensionValue.getValue();
            String key = dimName;
            if ("DATATIME".equals(dimName)) {
                key = "PERIOD";
            } else if (dwDimensionName.equals(dimName)) {
                key = "MDCODE";
            }
            filterMap.put(key, value);
        }
        filterMap.put("FLH_FORM", formKey);
        filterMap.put("FLH_FORMSCHEME", formScheme.getKey());
        return filterMap;
    }

    private INvwaDataSet getFormLockDataTable(DimensionValueSet masterKeys, TableModelDefine tableModel, String formSchemeKey, boolean readOnly) {
        List fields;
        DimensionValueSet dimensionValueSet = new DimensionValueSet(masterKeys);
        String tableCode = tableModel.getName();
        try {
            fields = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        }
        catch (Exception e) {
            throw new NotFoundTableDefineException(ExceptionCodeCost.NOTFOUND_TABLEFIELD, new String[]{tableCode});
        }
        HashMap<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("FL_FORMSCHEMEKEY", formSchemeKey);
        return this.nvwaDataEngineQueryUtil.queryDataSet(tableCode, dimensionValueSet, fields, new ArrayList<String>(), filterMap, new HashMap<String, Boolean>(), readOnly);
    }

    private INvwaDataSet getFormLockDataTableAndOrder(DimensionValueSet masterKeys, TableModelDefine tableModel, String formSchemeKey, boolean readOnly, Map<String, Boolean> orderMap) {
        List fields;
        DimensionValueSet dimensionValueSet = new DimensionValueSet(masterKeys);
        String tableCode = tableModel.getName();
        try {
            fields = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        }
        catch (Exception e) {
            throw new NotFoundTableDefineException(ExceptionCodeCost.NOTFOUND_TABLEFIELD, new String[]{tableCode});
        }
        HashMap<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("FL_FORMSCHEMEKEY", formSchemeKey);
        return this.nvwaDataEngineQueryUtil.queryDataSet(tableCode, dimensionValueSet, fields, new ArrayList<String>(), filterMap, orderMap, readOnly);
    }

    private void formatFormKeysMap(Map<String, String> formKeysMap, List<User> userList) {
        Set<String> formKeySet = formKeysMap.keySet();
        Map<String, String> map = userList.stream().collect(Collectors.toMap(User::getId, User::getFullname));
        for (String formKey : formKeySet) {
            String userIds = formKeysMap.get(formKey);
            String[] ids = userIds.split(";");
            StringBuilder names = new StringBuilder();
            for (int i = 0; i < ids.length; ++i) {
                String name = map.get(ids[i]);
                if (!StringUtils.isNotEmpty((String)name)) continue;
                names.append(name).append(";");
            }
            names.setLength(names.length() - 1);
            formKeysMap.put(formKey, names.toString());
        }
    }

    private boolean hasFormAuthor(FormLockAuthType formLockType, String formKey, String dwCode, String orgType) throws UnauthorizedEntityException {
        boolean entityAuthority = false;
        switch (formLockType) {
            case WRITE: {
                entityAuthority = this.authorityProvider.canWriteForm(formKey, dwCode, orgType);
                break;
            }
            case UPLOAD: {
                entityAuthority = this.authorityProvider.canUploadForm(formKey, dwCode, orgType);
                break;
            }
            case AUDIT: {
                entityAuthority = this.authorityProvider.canAuditForm(formKey, dwCode, orgType);
                break;
            }
            case FORCEUNLOCK: {
                entityAuthority = this.authorityProvider.canReadForm(formKey, dwCode, orgType);
            }
        }
        return entityAuthority;
    }

    private Map<String, Map<String, Boolean>> hasFormAuthor(FormLockAuthType formLockType, List<String> formKeys, List<String> dwCodes, String orgType) {
        Map entityAuthority = Collections.emptyMap();
        switch (formLockType) {
            case WRITE: {
                entityAuthority = this.authorityProvider.batchQueryCanWriteFormWithDuty(formKeys, dwCodes, orgType);
                break;
            }
            case UPLOAD: {
                entityAuthority = this.authorityProvider.batchQueryCanUploadFormWithDuty(formKeys, dwCodes, orgType);
                break;
            }
            case AUDIT: {
                entityAuthority = this.authorityProvider.batchQueryCanAuditFormWithDuty(formKeys, dwCodes, orgType);
                break;
            }
            case FORCEUNLOCK: {
                entityAuthority = this.authorityProvider.batchQueryCanReadFormWithDuty(formKeys, dwCodes, orgType);
            }
        }
        return entityAuthority;
    }

    private Date[] getPeriodStartAndEnd(String taskKey, String periodValue) throws ParseException {
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
        List dimensions = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        Optional<DataDimension> first = dimensions.stream().filter(e -> e.getDimensionType().equals((Object)DimensionType.PERIOD)).findFirst();
        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(first.get().getDimKey());
        return periodProvider.getPeriodDateRegion(periodValue);
    }

    private boolean hasLockUnitAuthor(DimensionValueSet dimensionValueSet, FormSchemeDefine formScheme, FormLockAuthType formlockType) throws UnauthorizedEntityException {
        boolean entityAuthority = false;
        String dimensionName = this.dataAccesslUtil.getDwDimensionName(formScheme.getDw());
        String unitValue = String.valueOf(dimensionValueSet.getValue(dimensionName));
        String periodValue = String.valueOf(dimensionValueSet.getValue("DATATIME"));
        Date queryVersionStartDate = Consts.DATE_VERSION_MIN_VALUE;
        Date queryVersionEndDate = Consts.DATE_VERSION_MAX_VALUE;
        if (StringUtils.isNotEmpty((String)periodValue)) {
            try {
                Date[] periodList = this.getPeriodStartAndEnd(formScheme.getTaskKey(), periodValue);
                if (periodList != null && periodList.length == 2) {
                    queryVersionStartDate = periodList[0];
                    queryVersionEndDate = periodList[1];
                }
            }
            catch (ParseException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
            }
        }
        if (StringUtils.isNotEmpty((String)unitValue)) {
            boolean systemIdentity;
            entityAuthority = systemIdentity = this.systemIdentityService.isAdmin();
            String entityId = this.dataAccesslUtil.contextEntityId(formScheme.getDw());
            boolean enableAuthority = this.entityAuthorityService.isEnableAuthority(entityId);
            if (!enableAuthority) {
                entityAuthority = true;
            }
            if (!systemIdentity && enableAuthority) {
                switch (formlockType) {
                    case WRITE: {
                        entityAuthority = this.entityAuthorityService.canWriteEntity(entityId, unitValue, queryVersionStartDate, queryVersionEndDate);
                        break;
                    }
                    case UPLOAD: {
                        entityAuthority = this.entityAuthorityService.canUploadEntity(entityId, unitValue, queryVersionStartDate, queryVersionEndDate);
                        break;
                    }
                    case AUDIT: {
                        entityAuthority = this.entityAuthorityService.canAuditEntity(entityId, unitValue, queryVersionStartDate, queryVersionEndDate);
                        break;
                    }
                    case FORCEUNLOCK: {
                        entityAuthority = this.entityAuthorityService.canReadEntity(entityId, unitValue, queryVersionStartDate, queryVersionEndDate);
                    }
                }
            }
        }
        return entityAuthority;
    }

    private Set<String> getLockUnitAuthor(DimensionValueSet masterKey, FormSchemeDefine formScheme, FormLockAuthType formlockType, List<String> dwValues) {
        String entityId;
        boolean enableAuthority;
        boolean systemIdentity = this.systemIdentityService.isAdmin();
        if (systemIdentity) {
            return new HashSet<String>(dwValues);
        }
        String periodValue = String.valueOf(masterKey.getValue("DATATIME"));
        Date queryVersionEndDate = Consts.DATE_VERSION_MAX_VALUE;
        if (StringUtils.isNotEmpty((String)periodValue)) {
            try {
                Date[] periodList = this.getPeriodStartAndEnd(formScheme.getTaskKey(), periodValue);
                if (periodList != null && periodList.length == 2) {
                    queryVersionEndDate = periodList[1];
                }
            }
            catch (ParseException e) {
                throw new RuntimeException("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        if (!(enableAuthority = this.entityAuthorityService.isEnableAuthority(entityId = this.dataAccesslUtil.contextEntityId(formScheme.getDw())))) {
            return new HashSet<String>(dwValues);
        }
        try {
            Map entityKeyDatas;
            switch (formlockType) {
                case WRITE: {
                    entityKeyDatas = this.entityAuthorityService.canWriteEntity(entityId, new HashSet<String>(dwValues), queryVersionEndDate);
                    break;
                }
                case UPLOAD: {
                    entityKeyDatas = this.entityAuthorityService.canUploadEntity(entityId, new HashSet<String>(dwValues), queryVersionEndDate);
                    break;
                }
                case AUDIT: {
                    entityKeyDatas = this.entityAuthorityService.canAuditEntity(entityId, new HashSet<String>(dwValues), queryVersionEndDate);
                    break;
                }
                case FORCEUNLOCK: {
                    entityKeyDatas = this.entityAuthorityService.canReadEntity(entityId, new HashSet<String>(dwValues), queryVersionEndDate);
                    break;
                }
                default: {
                    throw new RuntimeException("\u4e0d\u652f\u6301\u7684\u6743\u9650\u5224\u65ad\u7c7b\u578b" + (Object)((Object)formlockType));
                }
            }
            HashSet<String> res = new HashSet<String>();
            for (Map.Entry entry : entityKeyDatas.entrySet()) {
                String key = (String)entry.getKey();
                Boolean value = (Boolean)entry.getValue();
                if (!Boolean.TRUE.equals(value)) continue;
                res.add(key);
            }
            return res;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getAllDwCodes(DimensionValueSet masterKey, String dwDimensionName) {
        Object dwValueObjs = masterKey.getValue(dwDimensionName);
        ArrayList<String> dwValues = new ArrayList<String>();
        if (dwValueObjs instanceof String) {
            String dwValue = (String)dwValueObjs;
            dwValues.add(dwValue);
        } else if (dwValueObjs instanceof List) {
            dwValues.addAll((List)dwValueObjs);
        } else {
            throw new RuntimeException("dw\u7ef4\u5ea6\u503c\u7c7b\u578b\u9519\u8bef");
        }
        return dwValues;
    }

    private boolean isEnableFormLock(String taskKey, String formSchemeKey) {
        return this.formLockAccessService.isEnable(taskKey, formSchemeKey);
    }

    private TableModelDefine getFormLockTable(FormSchemeDefine formSchemeDefine) {
        TableModelDefine tableModel;
        String formLockTable = this.dataAccesslUtil.getTableName(formSchemeDefine, "NR_STATE_%s_FORMLOCK");
        try {
            tableModel = this.dataModelService.getTableModelDefineByCode(formLockTable);
        }
        catch (Exception e) {
            throw new NotFoundTableDefineException(new String[]{formLockTable});
        }
        return tableModel;
    }

    private TableModelDefine getFormLockHisTable(FormSchemeDefine formSchemeDefine) {
        TableModelDefine tableModel;
        String formLockTable = this.dataAccesslUtil.getTableName(formSchemeDefine, "NR_STATE_%s_FORMLOCK_HIS");
        try {
            tableModel = this.dataModelService.getTableModelDefineByCode(formLockTable);
        }
        catch (Exception e) {
            throw new NotFoundTableDefineException(new String[]{formLockTable});
        }
        return tableModel;
    }

    private DimensionValueSet rebuildDimKey(DimensionValueSet masterKeys, String formSchemeKey, Object formKey) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet(masterKeys);
        dimensionValueSet.setValue("FL_FORMSCHEMEKEY", (Object)formSchemeKey);
        if (formKey != null) {
            dimensionValueSet.setValue("FL_FORMKEY", formKey);
        }
        return dimensionValueSet;
    }

    private DimensionValueSet rebuildDimKeyAndUser(DimensionValueSet masterKeys, String formSchemeKey, Object formKey) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet(masterKeys);
        dimensionValueSet.setValue("FL_FORMSCHEMEKEY", (Object)formSchemeKey);
        if (formKey != null) {
            dimensionValueSet.setValue("FL_FORMKEY", formKey);
        }
        String userId = "";
        if (NpContextHolder.getContext().getUserName() != null) {
            userId = NpContextHolder.getContext().getUser().getId();
        }
        dimensionValueSet.setValue("FL_USER", (Object)userId);
        return dimensionValueSet;
    }

    private boolean isEmptyDim(DimensionCollection collection) {
        DimensionValueSet collectionSet = DimensionValueSetUtil.mergeDimensionValueSet(collection);
        Map<String, DimensionValue> dimensionSet = DimensionValueSetUtil.getDimensionSet(collectionSet);
        for (Map.Entry<String, DimensionValue> item : dimensionSet.entrySet()) {
            if (!StringUtils.isEmpty((String)item.getValue().getValue())) continue;
            return true;
        }
        return false;
    }

    public ArrayKey getArrayKey(DimensionValueSet dimensionValueSet, List<String> dimNames, String formKey) {
        int size = dimNames.size();
        Object[] keys = new Object[size + 1];
        for (int i = 0; i < size; ++i) {
            Object dimensionValue;
            String dimensionName = dimNames.get(i);
            keys[i] = dimensionValue = dimensionValueSet.getValue(dimensionName);
        }
        keys[i] = formKey;
        return new ArrayKey(keys);
    }

    static class DataSetDefine {
        private String tableName;
        private INvwaUpdatableDataSet dataSet;
        private Map<String, Integer> columnCode2Index;
        private ColumnModelDefine flhId;
        private ColumnModelDefine flhFormScheme;

        protected DataSetDefine(String tableName, INvwaUpdatableDataSet dataset, List<String> columnCodes, ColumnModelDefine flhId, ColumnModelDefine flhFormScheme) {
            this.tableName = tableName;
            this.dataSet = dataset;
            this.flhId = flhId;
            this.flhFormScheme = flhFormScheme;
            this.columnCode2Index = new HashMap<String, Integer>();
            for (int i = 0; i < columnCodes.size(); ++i) {
                this.columnCode2Index.put(columnCodes.get(i), i);
            }
        }

        public int getIndex(String columnCode) {
            Integer index = this.columnCode2Index.get(columnCode);
            if (index == null) {
                throw new IllegalArgumentException(columnCode + "\uff1a\u7d22\u5f15\u672a\u627e\u5230");
            }
            return index;
        }

        INvwaUpdatableDataSet getDataset() {
            return this.dataSet;
        }

        String getTableName() {
            return this.tableName;
        }

        ColumnModelDefine getFlhId() {
            return this.flhId;
        }

        ColumnModelDefine getFlhFormScheme() {
            return this.flhFormScheme;
        }
    }
}

