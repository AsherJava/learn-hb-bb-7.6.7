/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.examine.examiner;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.examine.common.ErrorType;
import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.examiner.AbstractExaminer;
import com.jiuqi.nr.examine.task.ExamineTask;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class FormSchemeExaminer
extends AbstractExaminer {
    private final Logger logger = LogFactory.getLogger(this.getClass());
    private List<DesignFormSchemeDefine> schemeDefines;

    public FormSchemeExaminer(ExamineTask task) {
        super(task);
    }

    @Override
    protected ParaType getParaType() {
        return ParaType.FORMSCHEME;
    }

    @Override
    protected void checkRefuse() {
        this.checkTaskRefuse();
        this.checkFormGroupRefuse();
    }

    private void checkFormGroupRefuse() {
        this.schemeDefines.forEach(scheme -> {
            try {
                List groups = this.task.getEnv().getNrDesignController().queryAllGroupsByFormScheme(scheme.getKey());
                if (groups == null || groups.size() == 0) {
                    this.writeRefuse(scheme.getKey(), scheme.getTitle(), ErrorType.FORMSCHEME_REFUSE_FORMGROUP);
                }
            }
            catch (JQException e) {
                this.logger.error("\u53c2\u6570\u68c0\u67e5\u53d1\u751f\u9519\u8bef\uff01", (Throwable)e);
            }
        });
    }

    private void checkTaskRefuse() {
        if (StringUtils.isEmpty((String)this.task.getKey())) {
            this.schemeDefines.forEach(scheme -> {
                DesignTaskDefine taskDefine = this.task.getEnv().getNrDesignController().queryTaskDefine(scheme.getTaskKey());
                if (taskDefine == null) {
                    this.writeRefuse(scheme.getKey(), scheme.getTitle(), ErrorType.FORMSCHEME_REFUSE_TASK);
                }
            });
        }
    }

    @Override
    protected void checkQuote() {
        this.schemeDefines.forEach(scheme -> {
            List links = this.task.getEnv().getNrDesignController().queryLinksByCurrentFormScheme(scheme.getKey());
            links.forEach(link -> {
                String relatedTaskKey;
                String relatedFormSchemeKey = link.getRelatedFormSchemeKey();
                if (StringUtils.isNotEmpty((String)link.getRelatedFormSchemeKey()) && this.task.getEnv().getNrDesignController().queryFormSchemeDefine(relatedFormSchemeKey) == null) {
                    this.writeQuotError(scheme.getKey(), scheme.getTitle(), ErrorType.FORMSCHEME_QUOTE_LINKTASK_ERROR);
                }
                if (StringUtils.isNotEmpty((String)(relatedTaskKey = link.getRelatedTaskKey())) && this.task.getEnv().getNrDesignController().queryTaskDefine(relatedTaskKey) == null) {
                    this.writeQuotError(scheme.getKey(), scheme.getTitle(), ErrorType.FORMSCHEME_QUOTE_LINKTASK_ERROR);
                }
            });
        });
    }

    @Override
    protected void checkError() {
    }

    @Override
    protected void init() {
        String paraKey = this.task.getKey();
        if (StringUtils.isEmpty((String)paraKey)) {
            this.schemeDefines = this.task.getEnv().getNrDesignController().queryAllFormSchemeDefine();
        } else {
            DesignFormSchemeDefine schemeDefine = this.task.getEnv().getNrDesignController().queryFormSchemeDefine(paraKey);
            this.schemeDefines = new ArrayList<DesignFormSchemeDefine>();
            if (schemeDefine != null) {
                this.schemeDefines.add(schemeDefine);
            }
        }
    }
}

