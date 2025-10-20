/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.process.IGraphicalElement
 */
package com.jiuqi.nr.designer.util;

import com.jiuqi.xg.process.IGraphicalElement;

public class PrintTemSyncCheckRes {
    private IGraphicalElement[] addElement;
    private IGraphicalElement[] deleteElement;
    private IGraphicalElement[] updateElement;
    private IGraphicalElement[] updateCommonElements;
    private IGraphicalElement[] updatePrintElements;

    public IGraphicalElement[] getAddElement() {
        return this.addElement;
    }

    public void setAddElement(IGraphicalElement[] addElement) {
        this.addElement = addElement;
    }

    public IGraphicalElement[] getDeleteElement() {
        return this.deleteElement;
    }

    public void setDeleteElement(IGraphicalElement[] deleteElement) {
        this.deleteElement = deleteElement;
    }

    public IGraphicalElement[] getUpdateElement() {
        return this.updateElement;
    }

    public void setUpdateElement(IGraphicalElement[] updateElement) {
        this.updateElement = updateElement;
    }

    public IGraphicalElement[] getUpdateCommonElements() {
        return this.updateCommonElements;
    }

    public void setUpdateCommonElements(IGraphicalElement[] updateCommonElements) {
        this.updateCommonElements = updateCommonElements;
    }

    public IGraphicalElement[] getUpdatePrintElements() {
        return this.updatePrintElements;
    }

    public void setUpdatePrintElements(IGraphicalElement[] updatePrintElements) {
        this.updatePrintElements = updatePrintElements;
    }
}

