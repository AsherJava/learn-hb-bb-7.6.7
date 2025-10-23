/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.Authority
 *  com.jiuqi.np.authz2.privilege.AuthzType
 *  com.jiuqi.np.authz2.privilege.PrivilegeType
 *  com.jiuqi.np.authz2.privilege.service.AuthorizationService
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nvwa.jobmanager.dao.PlanTaskGroupDAO
 *  com.jiuqi.nvwa.jobmanager.entity.PlanTaskGroupEO
 */
package com.jiuqi.nr.workflow2.schedule.init;

import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.np.authz2.privilege.AuthzType;
import com.jiuqi.np.authz2.privilege.PrivilegeType;
import com.jiuqi.np.authz2.privilege.service.AuthorizationService;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.workflow2.schedule.init.StartupPlanTaskGroupDefine;
import com.jiuqi.nvwa.jobmanager.dao.PlanTaskGroupDAO;
import com.jiuqi.nvwa.jobmanager.entity.PlanTaskGroupEO;
import java.util.List;
import javax.sql.DataSource;

public class StartupPlanTaskInit
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
    }

    public void initTaskGroup() {
        PlanTaskGroupDAO planTaskGroupDAO = (PlanTaskGroupDAO)SpringBeanUtils.getBean(PlanTaskGroupDAO.class);
        AuthorizationService authorizationService = (AuthorizationService)SpringBeanUtils.getBean(AuthorizationService.class);
        StartupPlanTaskGroupDefine group = new StartupPlanTaskGroupDefine();
        if (planTaskGroupDAO.findById(group.getId()) == null) {
            planTaskGroupDAO.insert((PlanTaskGroupEO)group);
            User oneSystemUser = this.getOneSystemUser();
            String userId = oneSystemUser.getId();
            authorizationService.grant("job_privilege_read", userId, userId, group.getId(), PrivilegeType.OBJECT.getValue(), Authority.ALLOW, AuthzType.ACCESS);
            authorizationService.grant("job_privilege_edit", userId, userId, group.getId(), PrivilegeType.OBJECT.getValue(), Authority.ALLOW, AuthzType.ACCESS);
        } else {
            planTaskGroupDAO.update((PlanTaskGroupEO)group);
        }
    }

    private User getOneSystemUser() {
        SystemUserService systemUserService = (SystemUserService)SpringBeanUtils.getBean(SystemUserService.class);
        List allSystemUsers = systemUserService.getAllUsers();
        if (allSystemUsers != null && !allSystemUsers.isEmpty()) {
            return (User)allSystemUsers.get(0);
        }
        return ((NvwaUserClient)SpringBeanUtils.getBean(NvwaUserClient.class)).find("admin");
    }
}

