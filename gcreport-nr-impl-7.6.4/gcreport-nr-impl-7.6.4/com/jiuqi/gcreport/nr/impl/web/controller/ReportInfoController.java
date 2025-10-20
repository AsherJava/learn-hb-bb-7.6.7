/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.nr.api.ReportInfoClient
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.nr.impl.web.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.nr.api.ReportInfoClient;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportInfoController
implements ReportInfoClient {
    @Autowired
    private IFormulaRunTimeController formCtrl;
    private RuntimeViewController runtimeViewController;

    public ReportInfoController(RuntimeViewController runtimeViewController) {
        this.runtimeViewController = runtimeViewController;
    }

    public BusinessResponseEntity<Integer> querySchemePeriodType(String schemeId) {
        FormSchemeDefine formSchemeDefine = this.runtimeViewController.getFormScheme(schemeId);
        if (formSchemeDefine == null) {
            return BusinessResponseEntity.ok((Object)-1);
        }
        return BusinessResponseEntity.ok((Object)formSchemeDefine.getPeriodType().type());
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<FormulaSchemeDefine>> cwFormulaScheme(@PathVariable(value="schemeId") String schemeId) {
        return BusinessResponseEntity.ok((Object)this.formCtrl.getAllCWFormulaSchemeDefinesByFormScheme(schemeId));
    }
}

