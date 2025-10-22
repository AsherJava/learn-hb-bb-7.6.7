/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.examine.examiner;

import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.examine.common.ErrorType;
import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.examiner.AbstractExaminer;
import com.jiuqi.nr.examine.task.ExamineTask;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class FormGroupExaminer
extends AbstractExaminer {
    private List<DesignFormGroupDefine> groupDefines;

    public FormGroupExaminer(ExamineTask task) {
        super(task);
    }

    @Override
    protected ParaType getParaType() {
        return ParaType.FORMGROUP;
    }

    @Override
    protected void checkRefuse() {
        this.checkFormSchemeRefuse();
        this.checkFormRefuse();
    }

    private void checkFormRefuse() {
        this.groupDefines.forEach(group -> {
            List forms = this.task.getEnv().getNrDesignController().getAllFormsInGroupWithoutBinaryData(group.getKey());
            if (forms == null || forms.size() == 0) {
                this.writeRefuse(group.getKey(), group.getTitle(), ErrorType.FORMGROUP_REFUSE_FORM);
            }
        });
    }

    private void checkFormSchemeRefuse() {
        if (StringUtils.isEmpty((String)this.task.getKey())) {
            this.groupDefines.forEach(group -> {
                String schemeKey = group.getFormSchemeKey();
                DesignFormSchemeDefine shcemeDefine = this.task.getEnv().getNrDesignController().queryFormSchemeDefine(schemeKey);
                if (shcemeDefine == null) {
                    this.writeRefuse(group.getKey(), group.getTitle(), ErrorType.FORMGROUP_REFUSE_FORMSCHEME);
                }
            });
        }
    }

    @Override
    protected void checkQuote() {
    }

    @Override
    protected void checkError() {
    }

    @Override
    protected void init() {
        String paraKey = this.task.getKey();
        if (StringUtils.isEmpty((String)paraKey)) {
            this.groupDefines = this.task.getEnv().getNrDesignController().getAllFormGroups();
        } else {
            DesignFormGroupDefine groupDefine = this.task.getEnv().getNrDesignController().queryFormGroup(paraKey);
            this.groupDefines = new ArrayList<DesignFormGroupDefine>();
            if (groupDefine != null) {
                this.groupDefines.add(groupDefine);
            }
        }
    }
}

