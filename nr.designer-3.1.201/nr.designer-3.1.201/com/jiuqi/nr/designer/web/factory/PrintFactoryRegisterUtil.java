/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.print.common.runtime.factory.ReportDrawObjectFactory
 *  com.jiuqi.nr.definition.facade.print.common.runtime.factory.ReportPaginateFactory
 *  com.jiuqi.nr.definition.facade.print.common.runtime.factory.ReportPaintFactory
 *  com.jiuqi.nr.definition.facade.print.common.runtime.factory.ReportTemplateObjectFactory
 *  com.jiuqi.xg.process.GraphicalFactoryManager
 *  com.jiuqi.xg.process.IDrawObjectFactory
 *  com.jiuqi.xg.process.IPaginateFactory
 *  com.jiuqi.xg.process.IPaintFactory
 *  com.jiuqi.xg.process.ITemplateObjectFactory
 *  com.jiuqi.xg.process.table.ITablePaginatePolicyFactory
 *  com.jiuqi.xg.process.table.TablePaginateFactoryManager
 *  com.jiuqi.xg.process.table.impl.BasicTablePaginatePolicyFactory
 *  com.jiuqi.xlib.channel.ClientChannels
 *  com.jiuqi.xlib.channel.IClientChannelFactory
 *  com.jiuqi.xlib.resource.IResourceManagerFactory
 *  com.jiuqi.xlib.resource.ResourceManagers
 *  com.jiuqi.xlib.spring.SpringClientFactory
 *  com.jiuqi.xlib.spring.SpringResourceManageFactory
 */
package com.jiuqi.nr.designer.web.factory;

import com.jiuqi.nr.definition.facade.print.common.runtime.factory.ReportDrawObjectFactory;
import com.jiuqi.nr.definition.facade.print.common.runtime.factory.ReportPaginateFactory;
import com.jiuqi.nr.definition.facade.print.common.runtime.factory.ReportPaintFactory;
import com.jiuqi.nr.definition.facade.print.common.runtime.factory.ReportTemplateObjectFactory;
import com.jiuqi.xg.process.GraphicalFactoryManager;
import com.jiuqi.xg.process.IDrawObjectFactory;
import com.jiuqi.xg.process.IPaginateFactory;
import com.jiuqi.xg.process.IPaintFactory;
import com.jiuqi.xg.process.ITemplateObjectFactory;
import com.jiuqi.xg.process.table.ITablePaginatePolicyFactory;
import com.jiuqi.xg.process.table.TablePaginateFactoryManager;
import com.jiuqi.xg.process.table.impl.BasicTablePaginatePolicyFactory;
import com.jiuqi.xlib.channel.ClientChannels;
import com.jiuqi.xlib.channel.IClientChannelFactory;
import com.jiuqi.xlib.resource.IResourceManagerFactory;
import com.jiuqi.xlib.resource.ResourceManagers;
import com.jiuqi.xlib.spring.SpringClientFactory;
import com.jiuqi.xlib.spring.SpringResourceManageFactory;

public class PrintFactoryRegisterUtil {
    public static void init() {
        SpringResourceManageFactory facroty = new SpringResourceManageFactory();
        ResourceManagers.register((String)"com.jiuqi.xg.print.resource", (IResourceManagerFactory)facroty);
        ResourceManagers.register((String)"com.jiuqi.xg.gef.resource", (IResourceManagerFactory)facroty);
        SpringClientFactory clientFactory = new SpringClientFactory();
        ClientChannels.regisiter((String)"com.jiuqi.xg.print.channel", (IClientChannelFactory)clientFactory);
        ClientChannels.regisiter((String)"com.jiuqi.xg.gef.channel", (IClientChannelFactory)clientFactory);
        ReportTemplateObjectFactory templateObjectFactory = new ReportTemplateObjectFactory("REPORT_PRINT_NATURE");
        GraphicalFactoryManager.registerTemplateObjectFactory((String)"REPORT_PRINT_NATURE", (ITemplateObjectFactory)templateObjectFactory);
        GraphicalFactoryManager.registerTemplateObjectFactory((String)"REPORT_PRINT_NATURE", (ITemplateObjectFactory)new ReportTemplateObjectFactory("REPORT_PRINT_NATURE"));
        GraphicalFactoryManager.registerDrawObjectFactory((String)"REPORT_PRINT_NATURE", (IDrawObjectFactory)new ReportDrawObjectFactory("REPORT_PRINT_NATURE"));
        GraphicalFactoryManager.registerPaginateFactory((String)"REPORT_PRINT_NATURE", (IPaginateFactory)new ReportPaginateFactory("REPORT_PRINT_NATURE"));
        GraphicalFactoryManager.registerPaintFactory((String)"REPORT_PRINT_NATURE", (IPaintFactory)new ReportPaintFactory("REPORT_PRINT_NATURE"));
        TablePaginateFactoryManager.registerTablePaginatePolicyFactory((String)"REPORT_PRINT_NATURE", (ITablePaginatePolicyFactory)new BasicTablePaginatePolicyFactory("REPORT_PRINT_NATURE"));
    }
}

