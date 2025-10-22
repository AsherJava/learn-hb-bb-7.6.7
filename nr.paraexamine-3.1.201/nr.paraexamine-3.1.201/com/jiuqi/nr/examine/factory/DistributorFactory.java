/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.factory;

import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.distributor.ExamineTaskDispatcher;
import com.jiuqi.nr.examine.distributor.FormDispatcher;
import com.jiuqi.nr.examine.distributor.FormGroupDispatcher;
import com.jiuqi.nr.examine.distributor.FormSchemeDispatcher;
import com.jiuqi.nr.examine.distributor.FormulaDispatcher;
import com.jiuqi.nr.examine.distributor.FormulaSchemeDispatcher;
import com.jiuqi.nr.examine.distributor.LinkDispatcher;
import com.jiuqi.nr.examine.distributor.PrintSchemeDispatcher;
import com.jiuqi.nr.examine.distributor.RegionDispatcher;
import com.jiuqi.nr.examine.distributor.TaskDispatcher;
import com.jiuqi.nr.examine.task.ExamineTask;

public class DistributorFactory {
    public static ExamineTaskDispatcher getDistributor(ExamineTask task) {
        ParaType paraType = task.getParaType();
        switch (paraType) {
            case TASK: {
                return new TaskDispatcher(task);
            }
            case FORM: {
                return new FormDispatcher(task);
            }
            case FORMGROUP: {
                return new FormGroupDispatcher(task);
            }
            case FORMSCHEME: {
                return new FormSchemeDispatcher(task);
            }
            case FORMULA: {
                return new FormulaDispatcher(task);
            }
            case FORMULASCHEME: {
                return new FormulaSchemeDispatcher(task);
            }
            case LINK: {
                return new LinkDispatcher(task);
            }
            case PRINTSCHEME: {
                return new PrintSchemeDispatcher(task);
            }
            case REGION: {
                return new RegionDispatcher(task);
            }
        }
        return new TaskDispatcher(task);
    }
}

