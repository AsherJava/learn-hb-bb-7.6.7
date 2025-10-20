/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.StringJoiner;

public class VarJD
extends Variable {
    private static final long serialVersionUID = -6355792877256949800L;

    public VarJD() {
        super("JD", "\u5b63\u5ea6", 6);
    }

    public Object getVarValue(IContext context) {
        INvwaSystemOptionService nvwaSystemOptionService = (INvwaSystemOptionService)SpringBeanUtils.getBean(INvwaSystemOptionService.class);
        int valueLength = "1".equals(nvwaSystemOptionService.findValueById("@nr/logic/compatibility-mode")) && "1".equals(nvwaSystemOptionService.findValueById("@nr/var/yf-format")) ? 2 : 4;
        QueryContext qContext = (QueryContext)context;
        PeriodWrapper period = qContext.getPeriodWrapper();
        Object jd = null;
        if (period == null || period.getType() != 4 && period.getType() != 3) {
            return jd;
        }
        String periodStr = period.toString();
        switch (period.getType()) {
            case 4: {
                String yf = periodStr.substring(periodStr.length() - 4);
                int i = Integer.parseInt(yf) / 4 + 1;
                StringJoiner joiner = new StringJoiner("", "", i > 4 ? "4" : String.valueOf(i));
                int bound = valueLength - 1;
                for (int s = 0; s < bound; ++s) {
                    String number = "0";
                    joiner.add(number);
                }
                return joiner.toString();
            }
            case 3: {
                return periodStr.substring(periodStr.length() - valueLength);
            }
        }
        return jd;
    }

    public void setVarValue(Object value) {
    }
}

