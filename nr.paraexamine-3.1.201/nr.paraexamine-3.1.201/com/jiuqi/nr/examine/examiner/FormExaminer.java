/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.i18n.language.LanguageType
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.examine.examiner;

import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.examine.common.ErrorType;
import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.examiner.AbstractExaminer;
import com.jiuqi.nr.examine.task.ExamineTask;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class FormExaminer
extends AbstractExaminer {
    private List<DesignFormDefine> formDefines;

    public FormExaminer(ExamineTask task) {
        super(task);
    }

    @Override
    protected ParaType getParaType() {
        return ParaType.FORM;
    }

    @Override
    protected void checkRefuse() {
        this.formDefines.forEach(form -> {
            switch (form.getFormType()) {
                case FORM_TYPE_FIX: 
                case FORM_TYPE_FLOAT: 
                case FORM_TYPE_FMDM: {
                    List groups;
                    byte[] reportData = this.task.getEnv().getNrDesignController().getReportDataFromForm(form.getKey(), LanguageType.CHINESE.getValue());
                    if (reportData == null || reportData.length == 0) {
                        this.writeRefuse(form.getKey(), form.getTitle(), ErrorType.FORM_REFUSE_FORMDATA);
                    }
                    if ((groups = this.task.getEnv().getNrDesignController().getFormGroupsByFormId(form.getKey())) != null && groups.size() != 0) break;
                    this.writeRefuse(form.getKey(), form.getTitle(), ErrorType.FORM_REFUSE_GROUP);
                    break;
                }
            }
            List regions = this.task.getEnv().getNrDesignController().getAllRegionsInForm(form.getKey());
            if (regions == null || regions.size() == 0) {
                this.writeQuotError(form.getKey(), form.getTitle(), ErrorType.FORM_QUOTE_REGION);
            }
        });
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
            this.formDefines = this.task.getEnv().getNrDesignController().getAllFormDefinesWithoutBinaryData();
        } else {
            DesignFormDefine formDefine = this.task.getEnv().getNrDesignController().querySoftFormDefine(paraKey);
            this.formDefines = new ArrayList<DesignFormDefine>();
            if (formDefine != null) {
                this.formDefines.add(formDefine);
            }
        }
    }
}

