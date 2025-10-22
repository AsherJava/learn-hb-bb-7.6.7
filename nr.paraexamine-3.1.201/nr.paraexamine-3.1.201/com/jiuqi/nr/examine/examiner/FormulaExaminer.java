/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.examine.examiner;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.examine.common.ErrorType;
import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.examiner.AbstractExaminer;
import com.jiuqi.nr.examine.task.ExamineTask;
import com.jiuqi.util.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormulaExaminer
extends AbstractExaminer {
    private List<DesignFormulaDefine> formulaDefines;
    private final Logger logger = LogFactory.getLogger(this.getClass());

    public FormulaExaminer(ExamineTask task) {
        super(task);
    }

    @Override
    protected ParaType getParaType() {
        return ParaType.FORMULA;
    }

    @Override
    protected void checkRefuse() {
        this.checkFormulaRefuse();
    }

    private void checkFormulaRefuse() {
        HashMap formMap = new HashMap();
        HashMap schemeMap = new HashMap();
        this.formulaDefines.forEach(formula -> {
            this.checkFormRefuse(formMap, (DesignFormulaDefine)formula);
            this.checkSchemeRefuse(schemeMap, (DesignFormulaDefine)formula);
        });
    }

    private void checkSchemeRefuse(Map<String, Boolean> schemeMap, DesignFormulaDefine formula) {
        if (StringUtils.isEmpty((String)this.task.getKey())) {
            String schemeKey = formula.getFormulaSchemeKey();
            if (StringUtils.isEmpty((String)schemeKey)) {
                this.writeRefuse(formula.getKey(), null, ErrorType.FORMULA_REFUSE_FORMUASCHEME);
            } else {
                Boolean b = schemeMap.get(schemeKey);
                if (b == null) {
                    b = this.task.getEnv().getDesignFormulaController().queryFormulaSchemeDefine(schemeKey) == null;
                    schemeMap.put(schemeKey, b);
                }
                if (b.booleanValue()) {
                    this.writeRefuse(formula.getKey(), null, ErrorType.FORMULA_REFUSE_FORMUASCHEME);
                }
            }
        }
    }

    private void checkFormRefuse(Map<String, Boolean> formMap, DesignFormulaDefine formula) {
        String formKey = formula.getFormKey();
        if (StringUtils.isNotEmpty((String)formKey)) {
            Boolean b = formMap.get(formKey);
            if (b == null) {
                b = this.task.getEnv().getNrDesignController().queryFormById(formKey) == null;
                formMap.put(formKey, b);
            }
            if (b.booleanValue()) {
                this.writeRefuse(formula.getKey(), null, ErrorType.FORMULA_REFUSE_FORM);
            }
        }
    }

    @Override
    protected void checkQuote() {
        if (StringUtils.isEmpty((String)this.task.getKey())) {
            // empty if block
        }
    }

    @Override
    protected void checkError() {
    }

    @Override
    protected void init() {
        String paraKey = this.task.getKey();
        try {
            this.formulaDefines = StringUtils.isEmpty((String)paraKey) ? this.task.getEnv().getDesignFormulaController().getAllFormulas() : this.task.getEnv().getDesignFormulaController().getAllFormulasInScheme(paraKey);
        }
        catch (JQException e) {
            this.logger.error("\u53c2\u6570\u68c0\u67e5\u53d1\u751f\u9519\u8bef\uff01", (Throwable)e);
        }
    }
}

