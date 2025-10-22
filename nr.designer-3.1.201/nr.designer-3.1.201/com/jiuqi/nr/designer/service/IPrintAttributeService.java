/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.print.common.PrintElementUtils
 *  com.jiuqi.xg.process.Paper
 */
package com.jiuqi.nr.designer.service;

import com.jiuqi.nr.definition.print.common.PrintElementUtils;
import com.jiuqi.nr.designer.web.rest.vo.PrintAttributeVo;
import com.jiuqi.xg.process.Paper;
import java.awt.GraphicsEnvironment;
import java.util.List;

public interface IPrintAttributeService {
    public PrintAttributeVo queryPrintAttribute(String var1) throws Exception;

    public void updatePrintAttribute(PrintAttributeVo var1) throws Exception;

    default public List<Paper> loadPapers() throws IllegalArgumentException, IllegalAccessException {
        return PrintElementUtils.getAllPapers();
    }

    default public String[] loadFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        return ge.getAvailableFontFamilyNames();
    }

    public boolean printAttributeISChange(PrintAttributeVo var1) throws Exception;
}

