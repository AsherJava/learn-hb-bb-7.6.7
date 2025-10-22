/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.factory;

import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.examiner.AbstractExaminer;
import com.jiuqi.nr.examine.examiner.FormExaminer;
import com.jiuqi.nr.examine.examiner.FormGroupExaminer;
import com.jiuqi.nr.examine.examiner.FormSchemeExaminer;
import com.jiuqi.nr.examine.examiner.FormulaExaminer;
import com.jiuqi.nr.examine.examiner.FormulaSchemeExaminer;
import com.jiuqi.nr.examine.examiner.LinkExaminer;
import com.jiuqi.nr.examine.examiner.PrintSchemeExaminer;
import com.jiuqi.nr.examine.examiner.RegionExaminer;
import com.jiuqi.nr.examine.examiner.TaskExaminer;
import com.jiuqi.nr.examine.task.ExamineTask;

public class ExaminerFactory {
    public static AbstractExaminer getExaminer(ExamineTask task) {
        ParaType type = task.getParaType();
        switch (type) {
            case TASK: {
                return new TaskExaminer(task);
            }
            case FORMSCHEME: {
                return new FormSchemeExaminer(task);
            }
            case FORMULASCHEME: {
                return new FormulaSchemeExaminer(task);
            }
            case FORMULA: {
                return new FormulaExaminer(task);
            }
            case PRINTSCHEME: {
                return new PrintSchemeExaminer(task);
            }
            case FORMGROUP: {
                return new FormGroupExaminer(task);
            }
            case FORM: {
                return new FormExaminer(task);
            }
            case REGION: {
                return new RegionExaminer(task);
            }
            case LINK: {
                return new LinkExaminer(task);
            }
        }
        return new TaskExaminer(task);
    }
}

