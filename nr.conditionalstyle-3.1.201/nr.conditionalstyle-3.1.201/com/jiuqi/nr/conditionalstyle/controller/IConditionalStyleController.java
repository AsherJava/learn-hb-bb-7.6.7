/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 */
package com.jiuqi.nr.conditionalstyle.controller;

import com.jiuqi.nr.conditionalstyle.facade.ConditionalStyle;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import java.util.List;

public interface IConditionalStyleController {
    public List<ConditionalStyle> getCSByTask(String var1);

    public List<ConditionalStyle> getCSByPos(String var1, int var2, int var3);

    public List<ConditionalStyle> getCSByDataLink(DataLinkDefine var1);

    public List<ConditionalStyle> getAllCSInForm(String var1);

    public List<ConditionalStyle> getCSByRegion(String var1, String var2, String var3);
}

