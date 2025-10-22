/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.xlib.utils.CollectionUtils
 *  org.activiti.engine.delegate.DelegateExecution
 */
package com.jiuqi.nr.bpm.impl.countersign.impl;

import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.countersign.CounterSignParam;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.xlib.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import org.activiti.engine.delegate.DelegateExecution;

public class DefaultMulitiInstanceCompleteTask {
    private static final String COMPLETE_INSTANCE = "nrOfCompletedInstances";
    private static final String INSTANCES = "nrOfInstances";
    private NrParameterUtils nrParameterUtils;
    private RoleService roleService;

    public boolean completeTask(DelegateExecution execution, CounterSignParam counterSignParam) {
        String[] specialSignRole;
        this.nrParameterUtils = (NrParameterUtils)BeanUtil.getBean(NrParameterUtils.class);
        this.roleService = (RoleService)BeanUtil.getBean(RoleService.class);
        String actionCode = counterSignParam.getActionCode();
        if ("act_reject".equals(actionCode) || "cus_reject".equals(actionCode) || "act_return".equals(actionCode) || "cus_return".equals(actionCode)) {
            return true;
        }
        String[] specialUser = counterSignParam.getSpecialSignUser();
        if (specialUser != null && specialUser.length > 0) {
            ArrayList<String> specialsList = new ArrayList<String>();
            if (specialUser != null && specialUser.length > 0) {
                for (String user : specialUser) {
                    specialsList.add(user);
                }
            }
            Object userId = execution.getVariable(this.nrParameterUtils.getUserMapKey());
            if (!CollectionUtils.isEmpty(specialsList) && specialsList.contains(userId)) {
                return true;
            }
        }
        if ((specialSignRole = counterSignParam.getSpecialSignRole()) != null && specialSignRole.length > 0) {
            Object userId;
            ArrayList<String> specialsList = new ArrayList<String>();
            if (specialSignRole != null && specialSignRole.length > 0) {
                for (String user : specialSignRole) {
                    specialsList.add(user);
                }
            }
            if ((userId = execution.getVariable(this.nrParameterUtils.getUserMapKey())) != null) {
                Set idByIdentity = this.roleService.getIdByIdentity((String)userId);
                if (!CollectionUtils.isEmpty(specialsList) && !CollectionUtils.isEmpty((Collection)idByIdentity)) {
                    specialsList.retainAll(idByIdentity);
                    if (!specialsList.isEmpty()) {
                        return true;
                    }
                }
            }
        }
        Integer completeInstance = (Integer)execution.getVariable(COMPLETE_INSTANCE);
        Boolean isSignAllUser = counterSignParam.isCountSignAllUser();
        if (isSignAllUser.booleanValue()) {
            Integer instance = (Integer)execution.getVariable(INSTANCES);
            return completeInstance >= instance;
        }
        Integer signCount = counterSignParam.getSignCount();
        return completeInstance >= signCount;
    }
}

