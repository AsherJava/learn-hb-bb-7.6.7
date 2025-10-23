/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.facade.DesignPrintComTemDefine
 */
package com.jiuqi.nr.print.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.print.web.param.CommonTemplatePM;

public interface IPrintCommonTemService {
    public void coverTemplate(CommonTemplatePM var1);

    public void syncTemplate(CommonTemplatePM var1);

    public String insertPrintComTem(DesignPrintComTemDefine var1) throws JQException;

    public void updatePrintComTem(DesignPrintComTemDefine var1) throws JQException;

    public void deletePrintComTem(DesignPrintComTemDefine var1) throws JQException;
}

