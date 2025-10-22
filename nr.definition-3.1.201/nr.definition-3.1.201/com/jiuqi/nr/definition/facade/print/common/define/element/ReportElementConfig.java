/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.Guid
 *  com.jiuqi.xg.print.viewer.AbstractElementConfiger
 *  com.jiuqi.xg.process.IGraphicalElement
 */
package com.jiuqi.nr.definition.facade.print.common.define.element;

import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.util.Guid;
import com.jiuqi.xg.print.viewer.AbstractElementConfiger;
import com.jiuqi.xg.process.IGraphicalElement;

public class ReportElementConfig
extends AbstractElementConfiger {
    public ReportElementConfig(String nature) {
        super(nature, "element_report", "\u62a5\u8868");
    }

    public ReportElementConfig(String nature, String name, String title) {
        super(nature, name, title);
    }

    public IGraphicalElement initElement(IGraphicalElement element) {
        ReportTemplateObject report = (ReportTemplateObject)element;
        report.setID("report_" + Guid.newGuid());
        if (report.getWidth() == 0.0 && report.getHeight() == 0.0) {
            report.setWidth(100.0);
            report.setHeight(100.0);
        }
        return report;
    }
}

