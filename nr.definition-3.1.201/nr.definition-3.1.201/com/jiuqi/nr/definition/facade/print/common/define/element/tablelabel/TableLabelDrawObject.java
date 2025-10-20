/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.xg.process.IDrawElement
 *  com.jiuqi.xg.process.IDrawElementContainer
 *  com.jiuqi.xg.process.obj.AbstractDrawObject
 */
package com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel;

import com.jiuqi.grid.GridData;
import com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel.IGraphicalTableLabel;
import com.jiuqi.xg.process.IDrawElement;
import com.jiuqi.xg.process.IDrawElementContainer;
import com.jiuqi.xg.process.obj.AbstractDrawObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TableLabelDrawObject
extends AbstractDrawObject
implements IDrawElement,
IGraphicalTableLabel {
    public transient IDrawElementContainer parent;
    private GridData gridData;

    public String getKind() {
        return "element_tableLabel";
    }

    public IDrawElementContainer getParent() {
        return this.parent;
    }

    public void setParent(IDrawElementContainer container) {
        this.parent = container;
    }

    @Override
    public GridData getGridData() {
        return this.gridData;
    }

    public void setGridData(GridData gridData) {
        GridData old = this.gridData;
        if (old != null && old.equals(gridData)) {
            return;
        }
        this.gridData = gridData;
        this.firePropertyChange("gridData", old, gridData);
        this.firePropertyChange("validate", false, true);
    }

    public void deserialize(Element element) {
        super.deserialize(element);
        if (element.getAttribute("gridData") != null) {
            String gridDataStr = element.getAttribute("gridData").replace(" ", "");
            this.gridData = GridData.base64ToGrid((String)gridDataStr);
        }
    }

    public Element serialize(Document ownerDocument) {
        Element element = super.serialize(ownerDocument);
        element.setAttribute("gridData", GridData.gridToBase64((GridData)this.gridData));
        return element;
    }
}

