/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.examine.examiner;

import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.examine.common.ErrorType;
import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.examiner.AbstractExaminer;
import com.jiuqi.nr.examine.task.ExamineTask;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintSchemeExaminer
extends AbstractExaminer {
    private static final Logger logger = LoggerFactory.getLogger(PrintSchemeExaminer.class);
    private List<DesignPrintTemplateSchemeDefine> schemeDefines;

    public PrintSchemeExaminer(ExamineTask task) {
        super(task);
    }

    @Override
    protected ParaType getParaType() {
        return ParaType.PRINTSCHEME;
    }

    @Override
    protected void checkRefuse() {
        this.checkFormSchemeRefuse();
    }

    private void checkFormSchemeRefuse() {
        this.schemeDefines.forEach(scheme -> {
            String formSchemeKey = scheme.getFormSchemeKey();
            if (StringUtils.isEmpty((String)formSchemeKey) || this.task.getEnv().getNrDesignController().queryFormSchemeDefine(formSchemeKey) == null) {
                this.writeRefuse(scheme.getKey(), scheme.getTitle(), ErrorType.PRINTSCHEME_REFUSE_FORMSCHEME);
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
            try {
                this.schemeDefines = this.task.getEnv().getDesignPrintController().getAllPrintScheme();
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            this.schemeDefines = new ArrayList<DesignPrintTemplateSchemeDefine>();
        }
    }
}

