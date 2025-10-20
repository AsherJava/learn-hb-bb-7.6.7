/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.controller;

import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import java.util.List;

public interface IDesignParaCheckController {
    public List<DesignDataLinkDefine> listLinkNotInRegion();

    public List<String> listLinkKeyPhysicalCoordinatesDuplicate(String var1);

    public List<String> listLinkKeyDataCoordinatesDuplicate(String var1);

    public List<String> listLinkKeyRefuseView(String var1);

    public List<String> listLinkKeyViewQuoteError(String var1);
}

