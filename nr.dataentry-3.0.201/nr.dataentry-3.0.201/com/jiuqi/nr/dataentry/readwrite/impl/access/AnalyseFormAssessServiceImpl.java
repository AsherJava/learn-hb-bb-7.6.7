/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.AccessItem
 *  com.jiuqi.nr.data.access.param.IAccessMessage
 *  com.jiuqi.nr.data.access.service.IDataExtendAccessItemService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.dataentry.readwrite.impl.access;

import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.AccessItem;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.service.IDataExtendAccessItemService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AnalyseFormAssessServiceImpl
implements IDataExtendAccessItemService {
    @Autowired
    private IRunTimeViewController runTimeController;
    public static final String ANYLISE_READ_MSG = "\u5206\u6790\u62a5\u544a\u8868\u5355\u4e0d\u53ef\u7f16\u8f91";
    private Function<String, String> noAccessReason = code -> Optional.ofNullable(code).filter(e -> e.equals("1")).orElse(ANYLISE_READ_MSG);

    public String name() {
        return "analyseForm";
    }

    public int getOrder() {
        return 11;
    }

    public boolean isEnable(String taskKey, String formSchemeKey) {
        return true;
    }

    public IAccessMessage getAccessMessage() {
        return code -> this.noAccessReason.apply(code);
    }

    public AccessCode visible(AccessItem pram, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode readable(AccessItem pram, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode writeable(AccessItem pram, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)formKey, "formKey is must not be null!");
        FormDefine formDefine = this.runTimeController.queryFormById(formKey);
        if (FormType.FORM_TYPE_ANALYSISREPORT == formDefine.getFormType() || FormType.FORM_TYPE_INSERTANALYSIS == formDefine.getFormType()) {
            return new AccessCode(this.name(), "2");
        }
        return new AccessCode(this.name());
    }

    public boolean isServerAccess() {
        return true;
    }
}

