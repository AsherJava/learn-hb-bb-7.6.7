/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IParamNoddlDeployController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.common.ParamDeployEnum$DeployStatus
 *  com.jiuqi.nr.definition.common.ParamDeployEnum$ParamStatus
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.deploy.ParamDeployStatus
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.definition.deploy;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IParamNoddlDeployController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.common.ParamDeployEnum;
import com.jiuqi.nr.definition.deploy.common.DDLStatus;
import com.jiuqi.nr.definition.deploy.common.ParamDeployException;
import com.jiuqi.nr.definition.deploy.dto.ParamDeployStatusDTO;
import com.jiuqi.nr.definition.deploy.extend.IParamNoddlDeployService;
import com.jiuqi.nr.definition.deploy.service.IParamDeployStatusService;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.deploy.ParamDeployStatus;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class ParamNoddlDeployController
implements IParamNoddlDeployController {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IParamDeployStatusService paramDeployStatusService;
    @Autowired
    private List<IParamNoddlDeployService> paramNoddlDeployServices;
    @Value(value="${jiuqi.nvwa.databaseLimitMode:false}")
    private boolean noDDL;
    private static final Logger logger = LoggerFactory.getLogger(ParamNoddlDeployController.class);

    private IParamNoddlDeployService.NoddlDeployArgs getNoddlDeployArgs(String formSchemeKey) {
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(formSchemeKey);
        if (null == formScheme) {
            formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        }
        if (null == formScheme) {
            throw new ParamDeployException("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\u5f02\u5e38\uff1a\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728");
        }
        DesignTaskDefine task = this.designTimeViewController.getTask(formScheme.getTaskKey());
        if (null == task) {
            throw new ParamDeployException("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\u5f02\u5e38\uff1a\u62a5\u8868\u65b9\u6848\u6240\u5c5e\u4efb\u52a1\u4e0d\u5b58\u5728");
        }
        return new IParamNoddlDeployService.NoddlDeployArgs(task.getKey(), formScheme.getKey(), task.getDataScheme());
    }

    private ParamDeployStatus getParamDeployStatus(String formSchemeKey) {
        ParamDeployStatus status = this.paramDeployStatusService.getDeployStatus(formSchemeKey);
        if (null == status) {
            status = new ParamDeployStatusDTO();
            status.setSchemeKey(formSchemeKey);
            status.setParamStatus(ParamDeployEnum.ParamStatus.DEFAULT);
            status.setDeployStatus(ParamDeployEnum.DeployStatus.NOT_DEPLOYED);
            this.paramDeployStatusService.insertDeployStatus(status);
        }
        return status;
    }

    public boolean enableNoDDL() {
        return this.noDDL;
    }

    public List<String> preDeploy(String formSchemeKey) {
        this.checkDDL();
        ParamDeployStatus status = this.getParamDeployStatus(formSchemeKey);
        if (ParamDeployEnum.DeployStatus.DEPLOY.equals((Object)status.getDeployStatus())) {
            throw new ParamDeployException("\u6b64\u65b9\u6848\u6b63\u5728\u53d1\u5e03\u4e2d");
        }
        if (DDLStatus.EXECUTE_SQL.equals((Object)DDLStatus.valueOf(status.getDdlStatus()))) {
            throw new ParamDeployException("\u62a5\u8868\u65b9\u6848\u5df2\u7ecf\u6267\u884cSQL\uff0c\u8bf7\u53d1\u5e03");
        }
        if (DDLStatus.GENERATE_SQL.equals((Object)DDLStatus.valueOf(status.getDdlStatus()))) {
            throw new RuntimeException("\u6570\u636e\u65b9\u6848\u9501\u5b9a\u4e2d\uff0c\u6682\u4e0d\u80fd\u751f\u6210SQL");
        }
        if (ParamDeployEnum.ParamStatus.MAINTENANCE == status.getParamStatus()) {
            status.setParamStatus(ParamDeployEnum.ParamStatus.LOCKED);
        } else {
            status.setParamStatus(ParamDeployEnum.ParamStatus.READONLY);
        }
        ArrayList<String> result = new ArrayList<String>();
        IParamNoddlDeployService.NoddlDeployArgs args = this.getNoddlDeployArgs(formSchemeKey);
        for (IParamNoddlDeployService paramNoddlDeployService : this.paramNoddlDeployServices) {
            try {
                List<String> sql = paramNoddlDeployService.preDeploy(args);
                if (CollectionUtils.isEmpty(sql)) continue;
                result.addAll(sql);
            }
            catch (Exception e) {
                logger.error("\n\t\u83b7\u53d6DDL\u53d1\u5e03SQL\u5931\u8d25: {}\n\t\t{}", (Object)paramNoddlDeployService.getClass(), (Object)e);
            }
        }
        result.add(this.paramDeployStatusService.getUpdateStatusSqlByDDLBit(formSchemeKey, DDLStatus.EXECUTE_SQL.getValue()));
        status.setDdlStatus(DDLStatus.GENERATE_SQL.getValue());
        this.paramDeployStatusService.updateDeployStatus(status);
        return result;
    }

    private void checkDDL() {
        if (Boolean.FALSE.equals(this.enableNoDDL())) {
            throw new ParamDeployException("\u670d\u52a1\u62e5\u6709DDL\u6743\u9650\uff0c\u751f\u6210SQL");
        }
    }

    public void unPreDeploy(String formSchemeKey) {
        ParamDeployStatus status = this.getParamDeployStatus(formSchemeKey);
        DDLStatus ddlStatus = DDLStatus.valueOf(status.getDdlStatus());
        switch (ddlStatus) {
            case DEFAULT: {
                throw new ParamDeployException("\u6b64\u65b9\u6848\u672a\u751f\u6210SQL\uff0c\u65e0\u9700\u64a4\u9500");
            }
            case EXECUTE_SQL: {
                throw new ParamDeployException("\u6b64\u65b9\u6848\u5df2\u7ecf\u6267\u884cSQL\uff0c\u7981\u6b62\u64a4\u9500");
            }
            case GENERATE_SQL: {
                if (ParamDeployEnum.ParamStatus.LOCKED == status.getParamStatus()) {
                    status.setParamStatus(ParamDeployEnum.ParamStatus.MAINTENANCE);
                } else {
                    status.setParamStatus(ParamDeployEnum.ParamStatus.DEFAULT);
                }
                status.setDdlStatus(DDLStatus.DEFAULT.getValue());
                this.paramDeployStatusService.updateDeployStatus(status);
            }
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void deploy(String formSchemeKey) {
        ParamDeployStatus status = this.getParamDeployStatus(formSchemeKey);
        DDLStatus ddlStatus = DDLStatus.valueOf(status.getDdlStatus());
        switch (ddlStatus) {
            case DEFAULT: 
            case GENERATE_SQL: {
                throw new ParamDeployException("\u6b64\u65b9\u6848\u672a\u6267\u884cSQL\uff0c\u8bf7\u5148\u6267\u884cSQL");
            }
            case EXECUTE_SQL: {
                if (ParamDeployEnum.ParamStatus.LOCKED == status.getParamStatus()) {
                    status.setParamStatus(ParamDeployEnum.ParamStatus.MAINTENANCE);
                } else {
                    status.setParamStatus(ParamDeployEnum.ParamStatus.DEFAULT);
                }
                status.setDdlStatus(DDLStatus.DEFAULT.getValue());
                this.paramDeployStatusService.updateDeployStatus(status);
                IParamNoddlDeployService.NoddlDeployArgs args = this.getNoddlDeployArgs(formSchemeKey);
                for (IParamNoddlDeployService paramNoddlDeployService : this.paramNoddlDeployServices) {
                    paramNoddlDeployService.deploy(args);
                }
                break;
            }
        }
    }

    public int getDDLStatus(String formScheme) {
        ParamDeployStatus status = this.getParamDeployStatus(formScheme);
        return status.getDdlStatus();
    }
}

