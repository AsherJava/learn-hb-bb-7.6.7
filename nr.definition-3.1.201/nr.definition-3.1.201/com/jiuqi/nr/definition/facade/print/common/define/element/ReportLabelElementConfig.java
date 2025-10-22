/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.Guid
 *  com.jiuqi.xg.draw2d.Font
 *  com.jiuqi.xg.print.viewer.AbstractElementConfiger
 *  com.jiuqi.xg.process.IGraphicalElement
 */
package com.jiuqi.nr.definition.facade.print.common.define.element;

import com.jiuqi.nr.definition.facade.print.core.ReportLabelTemplateObject;
import com.jiuqi.util.Guid;
import com.jiuqi.xg.draw2d.Font;
import com.jiuqi.xg.print.viewer.AbstractElementConfiger;
import com.jiuqi.xg.process.IGraphicalElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportLabelElementConfig
extends AbstractElementConfiger {
    private static final Logger logger = LoggerFactory.getLogger(ReportLabelElementConfig.class);
    private int count = 0;

    public ReportLabelElementConfig(String nature, String name, String title) {
        super(nature, name, title);
    }

    public ReportLabelElementConfig(String nature) {
        this(nature, "element_reportLabel", "\u62a5\u8868\u6807\u7b7e");
    }

    public IGraphicalElement initElement(IGraphicalElement element) {
        ReportLabelTemplateObject word = (ReportLabelTemplateObject)element;
        word.setID("reportLabel_" + Guid.newGuid());
        if (word.getWidth() == 0.0 && word.getHeight() == 0.0) {
            word.setWidth(30.0);
            word.setHeight(10.0);
        }
        word.setContent("\u62a5\u8868\u6807\u7b7e" + this.count++);
        word.setFont(new Font());
        word.setAutoWrap(false);
        return word;
    }
}

