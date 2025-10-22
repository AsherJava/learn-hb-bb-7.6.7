/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.data.checkdes.obj;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.data.checkdes.api.DefCKDValidator;
import com.jiuqi.nr.data.checkdes.obj.BasePar;
import com.jiuqi.nr.data.checkdes.service.ICKDImpValidator;
import com.jiuqi.nr.data.checkdes.util.CKDExtendBeanCollector;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.io.Serializable;
import java.util.List;

public class CKDImpPar
extends BasePar
implements Serializable {
    private static final long serialVersionUID = 7984807812382875578L;
    private String filePath;
    private List<String> formulaKeys;
    @Deprecated
    private String ckdImpValidatorName;
    private com.jiuqi.nr.data.checkdes.api.ICKDImpValidator ckdImpValidator;

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getFormulaKeys() {
        return this.formulaKeys;
    }

    public void setFormulaKeys(List<String> formulaKeys) {
        this.formulaKeys = formulaKeys;
    }

    public String getCkdImpValidatorName() {
        return this.ckdImpValidatorName;
    }

    public void setCkdImpValidatorName(String ckdImpValidatorName) {
        this.ckdImpValidatorName = ckdImpValidatorName;
    }

    public ICKDImpValidator getValidator() {
        String validatorName = StringUtils.isEmpty((String)this.ckdImpValidatorName) ? "defCKDValidator" : this.ckdImpValidatorName;
        return ((CKDExtendBeanCollector)BeanUtil.getBean(CKDExtendBeanCollector.class)).getCKDImpValidatorByName(validatorName);
    }

    public com.jiuqi.nr.data.checkdes.api.ICKDImpValidator getCkdImpValidator() {
        if (this.ckdImpValidator == null) {
            this.ckdImpValidator = (com.jiuqi.nr.data.checkdes.api.ICKDImpValidator)BeanUtil.getBean(DefCKDValidator.class);
        }
        return this.ckdImpValidator;
    }

    public void setCkdImpValidator(com.jiuqi.nr.data.checkdes.api.ICKDImpValidator ckdImpValidator) {
        this.ckdImpValidator = ckdImpValidator;
    }
}

