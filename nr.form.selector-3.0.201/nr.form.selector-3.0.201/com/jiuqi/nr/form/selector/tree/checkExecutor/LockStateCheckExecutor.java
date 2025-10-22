/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.FormLockParam
 *  com.jiuqi.nr.dataentry.service.IFormLockService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.form.selector.tree.checkExecutor;

import com.jiuqi.nr.dataentry.bean.FormLockParam;
import com.jiuqi.nr.dataentry.service.IFormLockService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.form.selector.context.FormQueryHelperImpl;
import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LockStateCheckExecutor
implements IFormCheckExecutor {
    protected IFormQueryContext context;
    protected IFormLockService formLockService;
    protected IFormQueryHelper formQueryHelper;
    protected String type;

    public LockStateCheckExecutor(IFormQueryContext context, IRunTimeViewController runTimeView, IFormLockService formLockService, String type) {
        this.context = context;
        this.formQueryHelper = new FormQueryHelperImpl(context, runTimeView);
        this.formLockService = formLockService;
        this.type = type;
    }

    @Override
    public List<FormDefine> checkFormList(List<FormDefine> forms) {
        ArrayList<FormDefine> formDefines = new ArrayList<FormDefine>();
        if (forms != null && forms.size() > 0) {
            String userId;
            JtableContext jtableContext = this.formQueryHelper.translate2JTableContext(this.context);
            FormLockParam param = new FormLockParam();
            param.setContext(jtableContext);
            Map lockedFormKeysMap = this.formLockService.getLockedFormKeysMap(param, false);
            if ("locked-checker".equals(this.type) && lockedFormKeysMap != null && lockedFormKeysMap.size() > 0) {
                for (FormDefine from : forms) {
                    userId = (String)lockedFormKeysMap.get(from.getKey());
                    if (userId == null) continue;
                    formDefines.add(from);
                }
            }
            if ("unlock-checker".equals(this.type)) {
                if (lockedFormKeysMap == null || lockedFormKeysMap.size() == 0) {
                    return forms;
                }
                for (FormDefine from : forms) {
                    userId = (String)lockedFormKeysMap.get(from.getKey());
                    if (userId != null) continue;
                    formDefines.add(from);
                }
            }
        }
        return formDefines;
    }
}

