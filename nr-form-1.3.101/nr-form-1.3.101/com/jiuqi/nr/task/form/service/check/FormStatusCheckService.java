/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IParamDeployController
 *  com.jiuqi.nr.definition.common.ParamDeployEnum$DeployStatus
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.deploy.ParamDeployStatus
 */
package com.jiuqi.nr.task.form.service.check;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IParamDeployController;
import com.jiuqi.nr.definition.common.ParamDeployEnum;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.deploy.ParamDeployStatus;
import com.jiuqi.nr.task.form.dto.CheckResult;
import com.jiuqi.nr.task.form.dto.ErrorData;
import com.jiuqi.nr.task.form.dto.FormDesignerDTO;
import com.jiuqi.nr.task.form.dto.FormParamType;
import com.jiuqi.nr.task.form.face.ParamType;
import com.jiuqi.nr.task.form.form.dto.FormDTO;
import com.jiuqi.nr.task.form.service.IFormCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormStatusCheckService
implements IFormCheckService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IParamDeployController paramDeployController;

    @Override
    public CheckResult doCheck(FormDesignerDTO formDesigner) {
        FormDTO form = formDesigner.getForm();
        if (form != null) {
            return this.checkDeployStatus(form.getFormScheme(), form.getKey());
        }
        return CheckResult.successResult();
    }

    @Override
    public CheckResult doCheck(String formKey) {
        DesignFormDefine form = this.designTimeViewController.getForm(formKey);
        if (null != form) {
            return this.checkDeployStatus(form.getFormScheme(), form.getKey());
        }
        return CheckResult.successResult();
    }

    public CheckResult checkDeployStatus(String formSchemeKey, String formKey) {
        ParamDeployStatus status = this.paramDeployController.getDeployStatus(formSchemeKey);
        if (null != status && ParamDeployEnum.DeployStatus.DEPLOY == status.getDeployStatus()) {
            return this.error("\u62a5\u8868\u65b9\u6848\u53d1\u5e03\u4e2d", formKey);
        }
        return CheckResult.successResult();
    }

    private CheckResult error(String message, String formKey) {
        return CheckResult.errorResult(new ErrorData((ParamType)FormParamType.FORM_STATUS, message, formKey));
    }
}

