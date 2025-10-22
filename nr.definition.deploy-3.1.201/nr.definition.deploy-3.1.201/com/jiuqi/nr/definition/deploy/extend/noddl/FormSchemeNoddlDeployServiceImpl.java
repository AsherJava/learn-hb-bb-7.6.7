/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.common.ParamDeployEnum$DeployStatus
 *  com.jiuqi.nr.definition.common.ParamDeployEnum$ParamStatus
 *  com.jiuqi.nr.definition.common.ParamResourceType
 *  com.jiuqi.nr.definition.exception.NrParamSyncException
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.deploy.ParamDeployStatus
 *  com.jiuqi.nr.definition.util.DefinitionUtils
 *  com.jiuqi.nr.definition.util.NrDefinitionHelper
 *  com.jiuqi.nr.graph.IRWLockExecuterManager
 */
package com.jiuqi.nr.definition.deploy.extend.noddl;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.common.ParamDeployEnum;
import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.deploy.common.ParamDeployContext;
import com.jiuqi.nr.definition.deploy.common.ParamDeployException;
import com.jiuqi.nr.definition.deploy.dto.ParamDeployStatusDTO;
import com.jiuqi.nr.definition.deploy.extend.IParamNoddlDeployService;
import com.jiuqi.nr.definition.deploy.service.IParamDeployService;
import com.jiuqi.nr.definition.deploy.service.IParamDeployStatusService;
import com.jiuqi.nr.definition.deploy.service.IRuntimeParamChangeService;
import com.jiuqi.nr.definition.exception.NrParamSyncException;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.deploy.ParamDeployStatus;
import com.jiuqi.nr.definition.util.DefinitionUtils;
import com.jiuqi.nr.definition.util.NrDefinitionHelper;
import com.jiuqi.nr.graph.IRWLockExecuterManager;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(value=1000)
public class FormSchemeNoddlDeployServiceImpl
implements IParamNoddlDeployService {
    @Autowired
    private IDataSchemeDeployService dataSchemeDeployService;
    @Autowired
    private IParamDeployStatusService paramDeployStatusService;
    @Autowired
    private NrDefinitionHelper nrDefinitionHelper;
    @Autowired
    private IRWLockExecuterManager rwLockExecuterManager;
    @Autowired
    private IParamDeployService paramDeployService;
    @Autowired
    private IRuntimeParamChangeService paramChangeService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public List<String> preDeploy(IParamNoddlDeployService.NoddlDeployArgs args) {
        try {
            return this.dataSchemeDeployService.preDeployDataScheme(args.dataSchemeKey, true, p -> {});
        }
        catch (Exception e) {
            throw new ParamDeployException("\u65e0DDL\u53d1\u5e03\uff1a\u83b7\u53d6DDL\u5f02\u5e38", e);
        }
    }

    @Override
    public void deploy(IParamNoddlDeployService.NoddlDeployArgs args) {
        try {
            this.dataSchemeDeployService.deployTableModel(args.dataSchemeKey);
        }
        catch (Exception e) {
            throw new ParamDeployException("\u65e0DDL\u53d1\u5e03\uff1a\u540c\u6b65\u53c2\u6570\u5f02\u5e38", e);
        }
        this.deploy(args.formSchemeKey);
    }

    private void deploy(String formSchemeKey) {
        ParamDeployStatus deployStatus = this.paramDeployStatusService.getDeployStatus(formSchemeKey);
        if (null != deployStatus && ParamDeployEnum.DeployStatus.DEPLOY == deployStatus.getDeployStatus()) {
            throw new ParamDeployException("\u65e0DDL\u53d1\u5e03\uff1a\u53c2\u6570\u540c\u6b65\u4e2d");
        }
        FormSchemeDefine formScheme = this.getFormSchemeDefine(formSchemeKey);
        this.updateDeployStatusStart(formScheme, deployStatus);
        ParamDeployContext paramDeployContext = new ParamDeployContext((type, message) -> {});
        try {
            this.rwLockExecuterManager.getRWLockExecuter(this.nrDefinitionHelper.getLockName(ParamResourceType.FORM, formSchemeKey)).tryWrite(() -> {
                this.paramDeployService.deploy(paramDeployContext, formSchemeKey);
                return null;
            });
        }
        catch (Exception e) {
            this.updateDeployStatusFail(formScheme, e.getMessage());
            throw new ParamDeployException("\u65e0DDL\u53d1\u5e03\uff1a\u540c\u6b65\u53c2\u6570\u5f02\u5e38", e);
        }
        NrParamSyncException exception = null;
        try {
            this.paramChangeService.reload(paramDeployContext.getDeployItems());
        }
        catch (NrParamSyncException e) {
            exception = e;
        }
        this.updateDeployStatusFinish(formScheme);
        if (null != exception) {
            throw new ParamDeployException("\u65e0DDL\u53d1\u5e03\uff1a\u96c6\u7fa4\u95f4\u7f13\u5b58\u540c\u6b65\u5f02\u5e38", exception);
        }
    }

    private FormSchemeDefine getFormSchemeDefine(String formSchemeKey) {
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(formSchemeKey);
        if (null == formScheme) {
            formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        }
        if (null == formScheme) {
            throw new ParamDeployException("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\u5f02\u5e38\uff1a\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728");
        }
        return formScheme;
    }

    private void updateDeployStatusStart(FormSchemeDefine formScheme, ParamDeployStatus deployStatus) {
        DefinitionUtils.info((String)"\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u5f00\u59cb", (String)"\u62a5\u8868\u65b9\u6848\uff1a{}[{}]", (Object[])new Object[]{formScheme.getTitle(), formScheme.getKey()});
        ParamDeployStatusDTO deployStatusDTO = null == deployStatus ? new ParamDeployStatusDTO() : new ParamDeployStatusDTO(deployStatus);
        deployStatusDTO.setSchemeKey(formScheme.getKey());
        deployStatusDTO.setDeployStatus(ParamDeployEnum.DeployStatus.DEPLOY);
        NpContext context = NpContextHolder.getContext();
        deployStatusDTO.setUserName(context.getUserName());
        deployStatusDTO.setUserKey(context.getUserId());
        deployStatusDTO.setDeployTime(new Date());
        if (null != deployStatus) {
            deployStatusDTO.setLastDeployTime(deployStatus.getDeployTime());
            this.paramDeployStatusService.updateDeployStatus(deployStatusDTO);
        } else {
            this.paramDeployStatusService.insertDeployStatus(deployStatusDTO);
        }
    }

    private void updateDeployStatusFail(FormSchemeDefine formScheme, String deployDetail) {
        DefinitionUtils.info((String)"\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u5931\u8d25", (String)"\u62a5\u8868\u65b9\u6848\uff1a{}[{}]\uff0c{}", (Object[])new Object[]{formScheme.getTitle(), formScheme.getKey(), deployDetail});
        ParamDeployStatus status = this.paramDeployStatusService.getDeployStatus(formScheme.getKey());
        ParamDeployStatusDTO deployStatusDTO = new ParamDeployStatusDTO(status);
        deployStatusDTO.setDeployStatus(ParamDeployEnum.DeployStatus.FAIL);
        this.paramDeployStatusService.updateDeployStatus(deployStatusDTO);
    }

    private void updateDeployStatusFinish(FormSchemeDefine formScheme) {
        DefinitionUtils.info((String)"\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u6210\u529f", (String)"\u62a5\u8868\u65b9\u6848\uff1a{}[{}]", (Object[])new Object[]{formScheme.getTitle(), formScheme.getKey()});
        ParamDeployStatus status = this.paramDeployStatusService.getDeployStatus(formScheme.getKey());
        ParamDeployStatusDTO deployStatusDTO = new ParamDeployStatusDTO(status);
        deployStatusDTO.setParamStatus(ParamDeployEnum.ParamStatus.DEFAULT);
        deployStatusDTO.setDeployStatus(ParamDeployEnum.DeployStatus.SUCCESS);
        deployStatusDTO.setDeployDetail(null);
        this.paramDeployStatusService.updateDeployStatus(deployStatusDTO);
    }
}

