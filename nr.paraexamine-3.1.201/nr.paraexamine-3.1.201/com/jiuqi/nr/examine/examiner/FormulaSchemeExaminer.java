/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.examine.examiner;

import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.examine.common.ErrorType;
import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.examiner.AbstractExaminer;
import com.jiuqi.nr.examine.task.ExamineTask;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class FormulaSchemeExaminer
extends AbstractExaminer {
    private List<DesignFormulaSchemeDefine> schemeDefines = new ArrayList<DesignFormulaSchemeDefine>();

    public FormulaSchemeExaminer(ExamineTask task) {
        super(task);
    }

    @Override
    protected ParaType getParaType() {
        return ParaType.FORMULASCHEME;
    }

    @Override
    protected void checkRefuse() {
        this.checkFormSchemeRefuse();
    }

    private void checkFormSchemeRefuse() {
        this.schemeDefines.forEach(scheme -> {
            String formSchemeKey = scheme.getFormSchemeKey();
            if (StringUtils.isEmpty((String)formSchemeKey) || this.task.getEnv().getNrDesignController().queryFormSchemeDefine(formSchemeKey) == null) {
                this.writeRefuse(scheme.getKey(), scheme.getTitle(), ErrorType.FORMULASCHEME_REFUSE_FORMSCHEME);
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
            this.schemeDefines = this.task.getEnv().getDesignFormulaController().getAllFormulaSchemeDefines();
        } else {
            DesignFormulaSchemeDefine schemeDefine = this.task.getEnv().getDesignFormulaController().queryFormulaSchemeDefine(paraKey);
            this.schemeDefines = new ArrayList<DesignFormulaSchemeDefine>();
            if (schemeDefine != null) {
                this.schemeDefines.add(schemeDefine);
            }
        }
    }
}

