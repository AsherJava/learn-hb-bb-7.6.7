/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.process.GraphicalFactoryManager
 *  com.jiuqi.xg.process.IDrawObjectFactory
 *  com.jiuqi.xg.process.ITemplateObjectFactory
 *  com.jiuqi.xg.process.obj.PageTemplateObject
 */
package com.jiuqi.nr.definition.facade.print.core;

import com.jiuqi.xg.process.GraphicalFactoryManager;
import com.jiuqi.xg.process.IDrawObjectFactory;
import com.jiuqi.xg.process.ITemplateObjectFactory;
import com.jiuqi.xg.process.obj.PageTemplateObject;

public class DocumentBuildUtil {
    public static ITemplateObjectFactory getTemplateObjectFactory() {
        ITemplateObjectFactory factory = GraphicalFactoryManager.getTemplateObjectFactory((String)"REPORT_PRINT_NATURE");
        return factory;
    }

    public static IDrawObjectFactory getDrawObjectFactory() {
        return GraphicalFactoryManager.getDrawObjectFactory((String)"REPORT_PRINT_NATURE");
    }

    public static PageTemplateObject buildTemplatePage(String pageID) {
        PageTemplateObject page = (PageTemplateObject)DocumentBuildUtil.getTemplateObjectFactory().create("page");
        page.setID(pageID + "_" + String.valueOf(System.currentTimeMillis()));
        return page;
    }
}

