/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.Guid
 *  com.jiuqi.xg.print.viewer.AbstractElementConfiger
 *  com.jiuqi.xg.process.IGraphicalElement
 */
package com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel;

import com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel.TableLabelTemplateObject;
import com.jiuqi.util.Guid;
import com.jiuqi.xg.print.viewer.AbstractElementConfiger;
import com.jiuqi.xg.process.IGraphicalElement;

public class TableLabelElementConfig
extends AbstractElementConfiger {
    public TableLabelElementConfig(String nature) {
        super(nature, "element_tableLabel", "\u8868\u683c\u6807\u7b7e");
    }

    public IGraphicalElement initElement(IGraphicalElement element) {
        TableLabelTemplateObject tableLabel = (TableLabelTemplateObject)element;
        tableLabel.setID("tablelabel_" + Guid.newGuid());
        if (tableLabel.getWidth() == 0.0 && tableLabel.getHeight() == 0.0) {
            tableLabel.setWidth(80.0);
            tableLabel.setHeight(30.0);
        }
        return null;
    }
}

