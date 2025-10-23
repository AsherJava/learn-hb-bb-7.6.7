/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.service.impl;

import com.jiuqi.nr.task.form.dto.CheckResult;
import com.jiuqi.nr.task.form.ext.FormDefineResourceExtSupport;
import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import com.jiuqi.nr.task.form.ext.face.IFormDefineResourceExt;
import com.jiuqi.nr.task.form.service.IConfigExtCheckService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ConfigExtCheckImpl
extends FormDefineResourceExtSupport
implements IConfigExtCheckService {
    protected ConfigExtCheckImpl(List<IFormDefineResourceExt> formDefineResourceExts) {
        super(formDefineResourceExts);
    }

    @Override
    public CheckResult checkLinkConfigs(String formKey, List<ConfigDTO> datas) {
        CheckResult checkResult = CheckResult.successResult();
        for (IFormDefineResourceExt ext : this.getFormDefineResourceExts()) {
            CheckResult resultTemp;
            if (ext.getLinkConfigExt() == null || !(resultTemp = ext.getLinkConfigExt().doCheck(formKey, datas)).isError()) continue;
            checkResult.addErrorData(resultTemp.getErrorData());
        }
        return checkResult;
    }

    @Override
    public CheckResult checkRegionConfigs(String formKey, List<ConfigDTO> datas) {
        CheckResult checkResult = CheckResult.successResult();
        for (IFormDefineResourceExt ext : this.getFormDefineResourceExts()) {
            CheckResult resultTemp;
            if (ext.getLinkConfigExt() == null || !(resultTemp = ext.getRegionConfigExt().doCheck(formKey, datas)).isError()) continue;
            checkResult.addErrorData(resultTemp.getErrorData());
        }
        return checkResult;
    }

    @Override
    public CheckResult checkFieldConfigs(String formKey, List<ConfigDTO> datas) {
        return CheckResult.successResult();
    }

    @Override
    public CheckResult checkComponentConfigs(String formKey, List<ConfigDTO> datas) {
        CheckResult checkResult = CheckResult.successResult();
        for (IFormDefineResourceExt ext : this.getFormDefineResourceExts()) {
            CheckResult resultTemp;
            if (ext.getLinkConfigExt() == null || !(resultTemp = ext.getComponentConfigExt().doCheck(formKey, datas)).isError()) continue;
            checkResult.addErrorData(resultTemp.getErrorData());
        }
        return checkResult;
    }
}

