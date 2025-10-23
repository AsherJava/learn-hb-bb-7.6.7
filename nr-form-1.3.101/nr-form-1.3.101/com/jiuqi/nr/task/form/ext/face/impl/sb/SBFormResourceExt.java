/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.task.api.face.IFormTypeExt
 */
package com.jiuqi.nr.task.form.ext.face.impl.sb;

import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.task.api.face.IFormTypeExt;
import com.jiuqi.nr.task.form.dto.CheckResult;
import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import com.jiuqi.nr.task.form.ext.face.IComponentConfigExt;
import com.jiuqi.nr.task.form.ext.face.IExtendInfo;
import com.jiuqi.nr.task.form.ext.face.IFormDefineResourceExt;
import com.jiuqi.nr.task.form.ext.face.ILinkConfigExt;
import com.jiuqi.nr.task.form.ext.face.IRegionConfigExt;
import com.jiuqi.nr.task.form.ext.face.impl.SBReverseModelingExecutor;
import com.jiuqi.nr.task.form.ext.face.impl.sb.SBExtendInfo;
import com.jiuqi.nr.task.form.service.IReverseModelingExecutor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SBFormResourceExt
implements IFormDefineResourceExt {
    @Value(value="${jiuqi.nr.datascheme.account.enable:false}")
    private boolean enableAccount;

    @Override
    public String getCode() {
        return "accountForm-plugin";
    }

    @Override
    public String prodLine() {
        return "@nr";
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public double getOrder() {
        return 0.0;
    }

    @Override
    public IFormTypeExt getFormType() {
        return new IFormTypeExt(){

            public String getCode() {
                return FormType.FORM_TYPE_ACCOUNT.name();
            }

            public String getTitle() {
                return "\u53f0\u5e10\u8868";
            }
        };
    }

    @Override
    public IRegionConfigExt getRegionConfigExt() {
        return new IRegionConfigExt(){

            @Override
            public Map<String, List<ConfigDTO>> listConfigs(String formKey, List<String> keys) {
                return null;
            }

            @Override
            public void saveConfigs(String formKey, List<ConfigDTO> datas) {
            }

            @Override
            public ConfigDTO getConfig(String regionKey) {
                return null;
            }

            @Override
            public CheckResult doCheck(String formKey, List<ConfigDTO> datas) {
                return null;
            }

            @Override
            public List<IReverseModelingExecutor> getReverseModelingExecutor() {
                return Arrays.asList(new SBReverseModelingExecutor());
            }
        };
    }

    @Override
    public ILinkConfigExt getLinkConfigExt() {
        return null;
    }

    @Override
    public IComponentConfigExt getComponentConfigExt() {
        return null;
    }

    @Override
    public IExtendInfo extendConfig(String formSchemeKey) {
        SBExtendInfo sbExtendInfo = new SBExtendInfo();
        sbExtendInfo.setEnableAccount(this.enableAccount);
        return sbExtendInfo;
    }
}

