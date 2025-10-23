/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO
 *  com.jiuqi.nr.datascheme.internal.service.DataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.nr.data.access.api.impl;

import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.data.access.api.IStateEndUploadService;
import com.jiuqi.nr.data.access.common.StateConst;
import com.jiuqi.nr.data.access.common.TableConsts;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.IDataRowInfo;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.access.util.NvwaDataEngineQueryUtil;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateEndUploadServiceImpl
implements IStateEndUploadService {
    private static final String TASK_STATE = "1";
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    IDataAccessProvider dataAccessProvider;
    @Resource
    IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    RoleService roleService;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    RuntimeViewController runtimeViewController;
    @Autowired
    RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private NvwaDataEngineQueryUtil nvwaDataEngineQueryUtil;
    @Autowired
    private DataAccesslUtil dataAccesUtil;
    @Autowired
    private SystemIdentityService systemIdentityService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean saveData(String formSchemeKey, DimensionValueSet masterKey, int state) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, (String)"masterKey is must not be null!");
        boolean succ = false;
        FormSchemeDefine formScheme = this.getFormSchemeDefine(formSchemeKey);
        if (formScheme == null) {
            this.logger.info("\u62a5\u8868\u65b9\u6848\u4e3a\u7a7a");
            return succ;
        }
        TableModelDefine tableDefine = this.getTableDefineFMSC(formScheme);
        if (tableDefine == null) {
            this.logger.info("\u672a\u627e\u5230\u76f8\u5bf9\u5e94\u7684\u7269\u7406\u8868");
            return succ;
        }
        List<DimensionValueSet> buildDimsionValueSet = this.buildDimsionValueSet(formScheme, masterKey);
        ArrayList<DimensionValueSet> addDims = new ArrayList<DimensionValueSet>();
        ArrayList<DimensionValueSet> delDims = new ArrayList<DimensionValueSet>();
        for (DimensionValueSet dimensionValueSet : buildDimsionValueSet) {
            boolean userEqual = this.isUserEqual(formScheme, dimensionValueSet, tableDefine);
            boolean stateEqual = this.isStateEqual(formScheme, dimensionValueSet, tableDefine, state);
            if (userEqual || this.systemIdentityService.isAdmin() && state != 1) {
                if (stateEqual) continue;
                delDims.add(dimensionValueSet);
                continue;
            }
            if (state != 1) continue;
            addDims.add(dimensionValueSet);
        }
        if (delDims.size() > 0) {
            DimensionValueSet delMergDim = DimensionValueSetUtil.mergeDimensionValueSet(delDims);
            DimensionValueSet queryKey = this.appendDimension(delMergDim, formScheme.getKey());
            this.deleteData(tableDefine, queryKey);
        }
        if (addDims.size() > 0) {
            succ = this.insertData(tableDefine, addDims, formScheme, state, masterKey);
        }
        return succ;
    }

    @Override
    public Map<DimensionValueSet, StateConst> getStatesInfo(String formSchemeKey, DimensionValueSet masterKey, String userId) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, (String)"masterKey is must not be null!");
        Assert.notNull((Object)userId, (String)"userId is must not be null!");
        Map<DimensionValueSet, StateConst> stateInfoBean = new HashMap<DimensionValueSet, StateConst>();
        FormSchemeDefine formSchemeDefine = this.runtimeViewController.getFormScheme(formSchemeKey);
        TableModelDefine tableExist = this.getTableDefineFMSC(formSchemeDefine);
        if (tableExist != null) {
            masterKey = this.dataAccesUtil.filterReportDims(formSchemeDefine, masterKey);
            stateInfoBean = this.queryRoleStateData(masterKey, tableExist, userId, formSchemeDefine);
        }
        return stateInfoBean;
    }

    @Override
    public void deleteData(String formSchemeKey, DimensionValueSet dimensionValue) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must bot be null!");
        Assert.notNull((Object)dimensionValue, (String)"dimensionValue is must bot be null!");
        FormSchemeDefine formScheme = this.getFormSchemeDefine(formSchemeKey);
        TableModelDefine tableDefine = this.getTableDefineFMSC(formScheme);
        List fields = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
        dimensionValue = this.dataAccesUtil.filterReportDims(formScheme, dimensionValue);
        DimensionValueSet delKey = this.appendDimension(dimensionValue, formScheme.getKey());
        this.delete(delKey, tableDefine, fields);
    }

    private boolean insertData(TableModelDefine tableDefine, List<DimensionValueSet> buildDimsionValueSets, FormSchemeDefine formScheme, int state, DimensionValueSet dims) {
        boolean isSucc = false;
        List fields = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
        List clumnsList = fields.stream().map(e -> e.getName()).collect(Collectors.toList());
        INvwaDataSet dataTable = this.getDataTable(formScheme, dims, tableDefine, fields, false);
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataRow dataRow = null;
            INvwaUpdatableDataSet updateDataSet = (INvwaUpdatableDataSet)dataTable;
            for (DimensionValueSet dimensionValueSet : buildDimsionValueSets) {
                DimensionValueSet queryKey = this.appendDimension(dimensionValueSet, formScheme.getKey());
                dataRow = updateDataSet.findRow(this.nvwaDataEngineQueryUtil.convertMasterToArrayKey(tableDefine.getName(), dataTable, queryKey));
                if (dataRow != null) continue;
                dataRow = updateDataSet.appendRow();
                this.nvwaDataEngineQueryUtil.setRowKey(tableDefine.getName(), dataRow, dimensionValueSet);
                dataRow.setValue(clumnsList.indexOf("US_ID"), (Object)UUIDUtils.getKey());
                dataRow.setValue(clumnsList.indexOf("US_USER"), (Object)this.getRoleOfUser(this.getCurrentUserId()));
                dataRow.setValue(clumnsList.indexOf("US_UPDATETIME"), (Object)new Date());
                dataRow.setValue(clumnsList.indexOf("US_STATE"), (Object)state);
                dataRow.setValue(clumnsList.indexOf("US_FORMSCHEMEKEY"), (Object)formScheme.getKey());
                dataRow.setValue(clumnsList.indexOf("US_FORMKEY"), (Object)"00000000-0000-0000-0000-000000000000");
            }
            updateDataSet.commitChanges(dataAccessContext);
        }
        catch (Exception e2) {
            this.logger.error("\u72b6\u6001\u5f15\u64ce-\u7ec8\u6b62\u586b\u62a5\u8868\u4fdd\u5b58\u6570\u636e\u5931\u8d25", e2);
            throw new AccessException(e2);
        }
        return isSucc;
    }

    private String getRoleOfUser(String userId) {
        String substring = null;
        if (this.systemIdentityService.isSystemByUserId(userId)) {
            return "ffffffff-ffff-ffff-aaaa-ffffffffffff";
        }
        StringBuilder roleStr = new StringBuilder();
        Set idByIdentity = this.roleService.getIdByIdentity(userId);
        for (String roleId : idByIdentity) {
            roleStr.append(roleId);
            roleStr.append(";");
        }
        if (roleStr.length() > 1) {
            substring = roleStr.substring(0, roleStr.length() - 1);
        }
        return substring;
    }

    public void delete(DimensionValueSet dimensionValue, TableModelDefine tableDefine, List<ColumnModelDefine> fields) {
        try {
            this.nvwaDataEngineQueryUtil.delete(dimensionValue, tableDefine.getName(), fields);
        }
        catch (Exception e) {
            throw new AccessException(e);
        }
    }

    private Map<DimensionValueSet, StateConst> queryRoleStateData(DimensionValueSet dimensionValueSet, TableModelDefine tableDefine, String userId, FormSchemeDefine formScheme) {
        Map<DimensionValueSet, StateConst> stateMap = new HashMap<DimensionValueSet, StateConst>();
        List stateFields = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
        List clumnsList = stateFields.stream().map(e -> e.getName()).collect(Collectors.toList());
        int userIndex = clumnsList.indexOf("US_USER");
        int stateIndex = clumnsList.indexOf("US_STATE");
        try {
            DimensionValueSet queryKey = this.appendDimension(dimensionValueSet, formScheme.getKey());
            INvwaDataSet dataTable = this.getDataTable(formScheme, queryKey, tableDefine, stateFields, true);
            if (dataTable != null) {
                for (int i = 0; i < dataTable.size(); ++i) {
                    INvwaDataRow dataRow = dataTable.getRow(i);
                    DimensionValueSet rowKey = this.nvwaDataEngineQueryUtil.convertRowKeyToDimensionValueSet(tableDefine.getName(), dataRow.getRowKey(), dataTable.getRowKeyColumns());
                    DimensionValueSet oriMasterKeys = DimensionValueSetUtil.filterDimensionValueSet(rowKey, "US_FORMKEY");
                    stateMap.put(oriMasterKeys, this.getState(stateIndex, userIndex, dataRow, userId, formScheme));
                }
            }
            stateMap = this.returnStateMap(formScheme, dimensionValueSet, userId, stateMap);
        }
        catch (Exception e2) {
            throw new AccessException(e2);
        }
        return stateMap;
    }

    private StateConst getState(int stateIndex, int userIndex, INvwaDataRow dataRow, String user, FormSchemeDefine formScheme) {
        int state = 1;
        String userId = null;
        try {
            state = Integer.parseInt(String.valueOf(dataRow.getValue(stateIndex)));
            userId = String.valueOf(dataRow.getValue(userIndex));
        }
        catch (DataTypeException e) {
            e.getMessage();
        }
        String roleOfUser = this.getRoleOfUser(user);
        String stateRole = this.getStateRole(formScheme);
        boolean equaled = this.equaled(stateRole, roleOfUser);
        String allowStoped = this.taskOptionController.getValue(formScheme.getTaskKey(), "ALLOW_STOP_FILING");
        if (TASK_STATE.equals(allowStoped)) {
            if (StringUtils.isEmpty((String)stateRole) && this.systemIdentityService.isSystemByUserId(userId)) {
                switch (state) {
                    case 0: {
                        return StateConst.STARTFILL;
                    }
                    case 1: {
                        return StateConst.ENDFILL;
                    }
                }
            } else if (equaled) {
                switch (state) {
                    case 0: {
                        return StateConst.STARTFILL;
                    }
                    case 1: {
                        return StateConst.ENDFILL;
                    }
                }
            } else {
                switch (state) {
                    case 0: {
                        return StateConst.STARTFILLICON;
                    }
                    case 1: {
                        return StateConst.ENDFILLICON;
                    }
                }
            }
        }
        return StateConst.ENDFILL;
    }

    private String getStateRole(FormSchemeDefine formScheme) {
        try {
            String allowStoped = this.taskOptionController.getValue(formScheme.getTaskKey(), "ALLOW_STOP_FILING");
            String role = this.taskOptionController.getValue(formScheme.getTaskKey(), "ALLOW_STOP_FILING_ROLE");
            if (TASK_STATE.equals(allowStoped) && null != role) {
                return role;
            }
        }
        catch (Exception e) {
            throw new AccessException(e);
        }
        return null;
    }

    private boolean equaled(String preRoleId, String currRoleId) {
        if (currRoleId != null && currRoleId.contains("ffffffff-ffff-ffff-aaaa-ffffffffffff")) {
            return true;
        }
        boolean equaled = false;
        if (StringUtils.isNotEmpty((String)preRoleId) && currRoleId != null) {
            String[] currRoleIdSplit;
            for (String currId : currRoleIdSplit = currRoleId.split(";")) {
                if (!currId.equals(preRoleId)) continue;
                equaled = true;
                break;
            }
        }
        return equaled;
    }

    private FormSchemeDefine getFormSchemeDefine(String formSchemeKey) {
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runTimeAuthViewController.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            throw new AccessException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY);
        }
        return formScheme;
    }

    private TableModelDefine getTableDefineFMSC(FormSchemeDefine formScheme) {
        TableModelDefine formDataStatusTable = null;
        try {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            DataSchemeDTO dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
            String tableName = TableConsts.getSysTableName("NR_UNITSTATE_%s", dataScheme.getBizCode());
            formDataStatusTable = this.dataModelService.getTableModelDefineByName(tableName);
        }
        catch (Exception e) {
            throw new AccessException(ExceptionCodeCost.NOTFOUND_TABLEDEFINE + "\u7ec8\u6b62\u586b\u62a5\u8868");
        }
        return formDataStatusTable;
    }

    private List<FieldDefine> getAllFieldsInTable(String tableKey) {
        try {
            return this.dataDefinitionRuntimeController.getAllFieldsInTable(tableKey);
        }
        catch (Exception e) {
            throw new AccessException(e);
        }
    }

    private List<DimensionValueSet> buildDimsionValueSet(FormSchemeDefine formScheme, DimensionValueSet dims) {
        ArrayList dimSets = new ArrayList();
        dims = this.dataAccesUtil.filterReportDims(formScheme, dims);
        return DimensionValueSetUtil.getDimensionSetList(dims);
    }

    public String getMainDimName(String formSchemeKey) {
        EntityViewDefine viewDefine = this.getEntityViewDefine(formSchemeKey);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(context);
        return dataAssist.getDimensionName(viewDefine);
    }

    private Map<DimensionValueSet, StateConst> returnStateMap(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, String currentUserId, Map<DimensionValueSet, StateConst> stateMap) {
        HashMap<DimensionValueSet, StateConst> returnStateMap = new HashMap<DimensionValueSet, StateConst>();
        List<DimensionValueSet> buildDimsionValueSet = this.buildDimsionValueSet(formScheme, dimensionValueSet);
        for (DimensionValueSet dimension : buildDimsionValueSet) {
            StateConst stateConst = stateMap.get(dimension);
            if (stateConst != null) {
                returnStateMap.put(dimension, stateMap.get(dimension));
                continue;
            }
            String roleOfUser = this.getRoleOfUser(currentUserId);
            String stateRole = this.getStateRole(formScheme);
            String allowStoped = this.taskOptionController.getValue(formScheme.getTaskKey(), "ALLOW_STOP_FILING_ROLE");
            if (!TASK_STATE.equals(allowStoped)) continue;
            if (StringUtils.isEmpty((String)stateRole)) {
                if (this.systemIdentityService.isSystemByUserId(currentUserId)) {
                    returnStateMap.put(dimension, StateConst.STARTFILL);
                    continue;
                }
                returnStateMap.put(dimension, StateConst.STARTFILLICON);
                continue;
            }
            boolean equaled = this.equaled(stateRole, roleOfUser);
            if (equaled) {
                returnStateMap.put(dimension, StateConst.STARTFILL);
                continue;
            }
            returnStateMap.put(dimension, StateConst.STARTFILLICON);
        }
        return returnStateMap;
    }

    private boolean isUserEqual(FormSchemeDefine formSchemeDefine, DimensionValueSet dims, TableModelDefine tableDefine) {
        boolean equal = false;
        IDataRowInfo queryData = this.queryData(formSchemeDefine, dims, tableDefine);
        if (queryData.getDataRow() != null) {
            INvwaDataRow dataRow = queryData.getDataRow();
            int userIndex = queryData.getUserIndex();
            try {
                Object asString = dataRow.getValue(userIndex);
                Set idByIdentity = this.roleService.getIdByIdentity(StateEndUploadServiceImpl.getCurrentUser().getId());
                String stateRole = this.getStateRole(formSchemeDefine);
                if (stateRole != null) {
                    for (String string : idByIdentity) {
                        if (!stateRole.contains(string)) continue;
                        equal = true;
                    }
                }
            }
            catch (DataTypeException e) {
                throw new AccessException(e);
            }
        }
        return equal;
    }

    private boolean isStateEqual(FormSchemeDefine formScheme, DimensionValueSet dims, TableModelDefine tableDefine, int state) {
        boolean equal = false;
        IDataRowInfo queryData = this.queryData(formScheme, dims, tableDefine);
        if (queryData.getDataRow() != null) {
            INvwaDataRow dataRow = queryData.getDataRow();
            int stateIndex = queryData.getStateIndex();
            try {
                Object asString = dataRow.getValue(stateIndex);
                if (String.valueOf(asString).equals(String.valueOf(state))) {
                    equal = true;
                }
            }
            catch (DataTypeException e) {
                throw new AccessException(e);
            }
        }
        return equal;
    }

    private IDataRowInfo queryData(FormSchemeDefine formSchemeDefine, DimensionValueSet dims, TableModelDefine tableDefine) {
        IDataRowInfo dataRowInfo = new IDataRowInfo();
        List stateFields = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
        List columnsList = stateFields.stream().map(e -> e.getName().toUpperCase()).collect(Collectors.toList());
        int stateIndex = columnsList.indexOf("US_STATE");
        int userIndex = columnsList.indexOf("US_USER");
        INvwaDataSet dataTable = this.getDataTable(formSchemeDefine, dims, tableDefine, stateFields, true);
        if (dataTable != null) {
            for (int i = 0; i < dataTable.size(); ++i) {
                INvwaDataRow dataRow = dataTable.getRow(i);
                dataRowInfo.setDataRow(dataRow);
                dataRowInfo.setStateIndex(stateIndex);
                dataRowInfo.setUserIndex(userIndex);
            }
        }
        return dataRowInfo;
    }

    public static ContextUser getCurrentUser() {
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        return user;
    }

    public String getCurrentUserId() {
        ContextUser user = StateEndUploadServiceImpl.getCurrentUser();
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    private void deleteData(TableModelDefine tableDefine, DimensionValueSet dimensionValue) {
        List fields = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
        try {
            this.delete(dimensionValue, tableDefine, fields);
        }
        catch (Exception e) {
            throw new AccessException(e);
        }
    }

    private INvwaDataSet getDataTable(FormSchemeDefine formScheme, DimensionValueSet masterKey, TableModelDefine dataPublishTable, List<ColumnModelDefine> columns, boolean readOnly) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet(masterKey);
        INvwaDataSet table = this.nvwaDataEngineQueryUtil.queryDataSetWithRowKey(dataPublishTable.getName(), dimensionValueSet, columns, new ArrayList<String>(), new HashMap<String, Boolean>(), readOnly);
        return table;
    }

    public EntityViewDefine getEntityViewDefine(String formSchemeId) {
        try {
            FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(formSchemeId);
            String entityId = this.dataAccesUtil.contextEntityId(formScheme.getDw());
            EntityViewDefine viewByFormSchemeKey = this.runtimeViewController.getViewByFormSchemeKey(formScheme.getKey());
            return this.entityViewRunTimeController.buildEntityView(entityId, viewByFormSchemeKey.getRowFilterExpression());
        }
        catch (Exception e) {
            throw new AccessException(e);
        }
    }

    private DimensionValueSet appendDimension(DimensionValueSet dimensionValueSet, String formSchemeKey) {
        DimensionValueSet queryKey = new DimensionValueSet(dimensionValueSet);
        queryKey.setValue("US_FORMSCHEMEKEY", (Object)formSchemeKey);
        return queryKey;
    }
}

