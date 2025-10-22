/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 */
package com.jiuqi.nr.conditionalstyle.controller;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import java.util.List;

public interface IDesignConditionalStyleController {
    public void insertCS(List<DesignConditionalStyle> var1) throws JQException;

    public void updateCS(List<DesignConditionalStyle> var1) throws JQException;

    public void deleteCS(List<DesignConditionalStyle> var1) throws JQException;

    public void deleteCSInForm(String var1) throws JQException;

    public List<DesignConditionalStyle> getCSByTask(String var1);

    public List<DesignConditionalStyle> getCSByPos(String var1, int var2, int var3);

    public List<DesignConditionalStyle> getCSByDataLink(DesignDataLinkDefine var1);

    public List<DesignConditionalStyle> getAllCSInForm(String var1);

    public List<DesignConditionalStyle> getCSByRegion(String var1, String var2, String var3);
}

