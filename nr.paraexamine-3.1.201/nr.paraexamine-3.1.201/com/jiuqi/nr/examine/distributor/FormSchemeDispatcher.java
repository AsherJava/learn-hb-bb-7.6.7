/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.examine.distributor;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.distributor.ExamineTaskDispatcher;
import com.jiuqi.nr.examine.task.ExamineTask;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class FormSchemeDispatcher
extends ExamineTaskDispatcher {
    private Logger logger = LogFactory.getLogger(this.getClass());

    public FormSchemeDispatcher(ExamineTask task) {
        super(task);
    }

    @Override
    protected List<ExamineTask> childrenTask() {
        ArrayList<ExamineTask> childrenTask = new ArrayList<ExamineTask>();
        String key = this.task.getKey();
        if (StringUtils.isEmpty((String)key)) {
            ExamineTask childTask = new ExamineTask(this.task);
            childTask.setParaType(ParaType.FORMULASCHEME);
            childrenTask.add(childTask);
            childTask = new ExamineTask(this.task);
            childTask.setParaType(ParaType.PRINTSCHEME);
            childrenTask.add(childTask);
            childTask = new ExamineTask(this.task);
            childTask.setParaType(ParaType.FORMGROUP);
            childrenTask.add(childTask);
        } else {
            List fSchemes = this.task.getEnv().getDesignFormulaController().getAllFormulaSchemeDefinesByFormScheme(key);
            fSchemes.stream().forEach(scheme -> {
                ExamineTask childTask = new ExamineTask(this.task);
                childTask.setParaType(ParaType.FORMULASCHEME);
                childTask.setKey(scheme.getKey());
                childrenTask.add(childTask);
            });
            List pSchemes = new ArrayList();
            try {
                pSchemes = this.task.getEnv().getDesignPrintController().getAllPrintSchemeByFormScheme(key);
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), (Throwable)e);
            }
            pSchemes.stream().forEach(scheme -> {
                ExamineTask childTask = new ExamineTask(this.task);
                childTask.setParaType(ParaType.PRINTSCHEME);
                childTask.setKey(scheme.getKey());
                childrenTask.add(childTask);
            });
            try {
                List groups = this.task.getEnv().getNrDesignController().queryAllGroupsByFormScheme(key);
                groups.stream().forEach(group -> {
                    ExamineTask childTask = new ExamineTask(this.task);
                    childTask.setParaType(ParaType.FORMGROUP);
                    childTask.setKey(group.getKey());
                    childrenTask.add(childTask);
                });
            }
            catch (JQException e) {
                this.logger.error("\u53c2\u6570\u68c0\u67e5\u53d1\u751f\u9519\u8bef\uff01", (Throwable)e);
            }
        }
        return childrenTask;
    }
}

