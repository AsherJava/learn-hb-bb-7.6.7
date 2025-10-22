/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.configuration.event.SystemOptionEvent
 *  com.jiuqi.nr.configuration.event.bean.EventBO
 *  com.jiuqi.nr.data.access.api.IStateEndUploadService
 *  com.jiuqi.nr.data.access.common.StateConst
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.state.serviceImpl;

import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.configuration.event.SystemOptionEvent;
import com.jiuqi.nr.configuration.event.bean.EventBO;
import com.jiuqi.nr.data.access.api.IStateEndUploadService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.state.beans.StateInfoBean;
import com.jiuqi.nr.state.common.StateConst;
import com.jiuqi.nr.state.exception.NotFoundException;
import com.jiuqi.nr.state.pojo.StateEntites;
import com.jiuqi.nr.state.pojo.StatePojo;
import com.jiuqi.nr.state.service.IStateSevice;
import com.jiuqi.nr.state.untils.StateEntityUpgraderImpl;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.util.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class StateServiceImpl
implements IStateSevice {
    private static final Logger logger = LogFactory.getLogger(StateServiceImpl.class);
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
    ITaskOptionController taskOptionController;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    StateEntityUpgraderImpl stateEntityUtils;
    @Autowired
    DataModelService dataModelService;
    @Autowired
    DataEngineAdapter dataEngineAdapter;
    @Autowired
    INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    IStateEndUploadService stateEndUploadService;
    @Autowired
    SystemUserService systemUserService;

    @Override
    public StateInfoBean getStatesInfo(StateEntites stateEntites) {
        StateInfoBean stateInfoBean = new StateInfoBean();
        if (stateEntites == null) {
            return null;
        }
        FormSchemeDefine formSchemeDefine = this.getFormSchemeDefine(stateEntites.getFormSchemeKey());
        Map stateInfo = this.stateEndUploadService.getStatesInfo(stateEntites.getFormSchemeKey(), stateEntites.getDims(), stateEntites.getUserId());
        String roleOfUser = this.getRoleOfUser(stateEntites.getUserId());
        String stateRole = this.getStateRole(formSchemeDefine);
        boolean equaled = this.equaled(stateRole, roleOfUser);
        stateInfoBean.setDefaultStateRole(equaled);
        stateInfoBean.setStateInfo(this.castStateInfo(stateInfo));
        return stateInfoBean;
    }

    @Override
    public boolean saveOrUpdateData(StatePojo sp) {
        return this.stateEndUploadService.saveData(sp.getFormSchemeKey(), sp.getDims(), sp.getState());
    }

    @Override
    public Map<DimensionValueSet, StateConst> getStateInfo(StateEntites stateEntites) {
        if (stateEntites == null) {
            return null;
        }
        Map stateInfo = this.stateEndUploadService.getStatesInfo(stateEntites.getFormSchemeKey(), stateEntites.getDims(), stateEntites.getUserId());
        return this.castStateInfo(stateInfo);
    }

    @EventListener
    public void deleteState(SystemOptionEvent systemOptionEvent) {
        try {
            DimensionValueSet dim = new DimensionValueSet();
            EventBO eventBO = systemOptionEvent.getEventBO();
            Object value = eventBO.getValue();
            if (!TASK_STATE.equals(value)) {
                List formSchemeByTask = this.runTimeViewController.queryFormSchemeByTask(eventBO.getTaskKey());
                for (FormSchemeDefine formSchemeDefine : formSchemeByTask) {
                    this.deleteData(formSchemeDefine.getKey(), dim);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public void deleteData(String formSchemeKey, DimensionValueSet dimensionValue) {
        this.stateEndUploadService.deleteData(formSchemeKey, dimensionValue);
    }

    private Map<DimensionValueSet, StateConst> castStateInfo(Map<DimensionValueSet, com.jiuqi.nr.data.access.common.StateConst> stateInfo) {
        HashMap<DimensionValueSet, StateConst> stateInfoBean = new HashMap<DimensionValueSet, StateConst>();
        stateInfo.forEach((key, value) -> stateInfoBean.put((DimensionValueSet)key, StateConst.valueOf(value.getValue())));
        return stateInfoBean;
    }

    private FormSchemeDefine getFormSchemeDefine(String formSchemeKey) {
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            throw new NotFoundException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{formSchemeKey});
        }
        return formScheme;
    }

    private Object allowStoped(String taskKey) {
        return this.taskOptionController.getValue(taskKey, "ALLOW_STOP_FILING");
    }

    private Object getRole(String taskKey) {
        return this.taskOptionController.getValue(taskKey, "ALLOW_STOP_FILING_ROLE");
    }

    private String getStateRole(FormSchemeDefine formScheme) {
        try {
            Object allowStoped = this.allowStoped(formScheme.getTaskKey());
            Object role = this.getRole(formScheme.getTaskKey());
            if (TASK_STATE.equals(allowStoped) && null != role) {
                return (String)role;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return null;
    }

    private boolean equaled(String preRoleId, String currRoleId) {
        boolean equaled = false;
        if (StringUtils.isNotEmpty((String)preRoleId) && currRoleId != null) {
            String[] currRoleIdSplit;
            String[] currList = currRoleIdSplit = currRoleId.split(";");
            User systemUser = this.getSystemUser();
            for (String currId : currList) {
                if (systemUser != null && currRoleId.contains(systemUser.getId())) {
                    return true;
                }
                if (!currId.equals(preRoleId)) continue;
                equaled = true;
                break;
            }
        }
        return equaled;
    }

    private String getRoleOfUser(String userId) {
        String substring = null;
        StringBuffer roleStr = new StringBuffer();
        Set idByIdentity = this.roleService.getIdByIdentity(userId);
        User systemUser = this.getSystemUser();
        for (String roleId : idByIdentity) {
            if (systemUser != null && (systemUser.getId().equals(userId) || systemUser.getId().equals(roleId))) {
                roleStr.append(roleId);
                roleStr.append(";");
                continue;
            }
            roleStr.append(roleId);
            roleStr.append(";");
        }
        if (roleStr.length() > 1) {
            substring = roleStr.substring(0, roleStr.length() - 1);
        }
        return substring;
    }

    private User getSystemUser() {
        List users = this.systemUserService.getUsers();
        if (users != null && users.size() > 0) {
            return (User)users.get(0);
        }
        return null;
    }
}

