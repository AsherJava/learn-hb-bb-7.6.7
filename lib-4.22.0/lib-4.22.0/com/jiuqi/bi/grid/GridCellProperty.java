/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.GridCell;

public final class GridCellProperty
extends GridCell {
    private static final long serialVersionUID = -1368146520867141545L;
    private boolean modifed = false;
    private int refCount = 0;
    private Integer lEdgeStyle = null;
    private Integer lEdgeColor = null;
    private Integer tEdgeStyle = null;
    private Integer tEdgeColor = null;

    @Override
    protected void dataChanged() {
        this.modifed = true;
        if (this.refCount == 0) {
            this.applyChange();
        }
    }

    protected void applyChange() {
        if (this.modifed) {
            if (this.grid != null) {
                this.grid.setCell(this);
            }
            if (this.lEdgeColor != null) {
                super.setLEdgeColor(this.lEdgeColor);
                this.lEdgeColor = null;
            }
            if (this.lEdgeStyle != null) {
                super.setLEdgeStyle(this.lEdgeStyle);
                this.lEdgeStyle = null;
            }
            if (this.tEdgeColor != null) {
                super.setTEdgeColor(this.tEdgeColor);
                this.tEdgeColor = null;
            }
            if (this.tEdgeStyle != null) {
                super.setTEdgeStyle(this.tEdgeStyle);
                this.tEdgeStyle = null;
            }
            this.modifed = false;
        }
    }

    public void beginUpdate() {
        ++this.refCount;
    }

    public void endUpdate() {
        --this.refCount;
        if (this.refCount == 0) {
            this.applyChange();
        }
    }

    public void cancelUpdate() {
        --this.refCount;
        if (this.modifed) {
            this.init(this.grid, this.getColNum(), this.getRowNum());
            this.modifed = false;
        }
    }

    @Override
    public void setLEdgeColor(int value) {
        if (this.refCount == 0) {
            super.setLEdgeColor(value);
        } else {
            this.lEdgeColor = new Integer(value);
            this.modifed = true;
        }
    }

    @Override
    public void setLEdgeStyle(int value) {
        if (this.refCount == 0) {
            super.setLEdgeStyle(value);
        } else {
            this.lEdgeStyle = new Integer(value);
            this.modifed = true;
        }
    }

    @Override
    public void setTEdgeColor(int value) {
        if (this.refCount == 0) {
            super.setTEdgeColor(value);
        } else {
            this.tEdgeColor = new Integer(value);
            this.modifed = true;
        }
    }

    @Override
    public void setTEdgeStyle(int value) {
        if (this.refCount == 0) {
            super.setTEdgeStyle(value);
        } else {
            this.tEdgeStyle = new Integer(value);
            this.modifed = true;
        }
    }

    @Override
    public void setSilverHead(boolean value) {
        this.beginUpdate();
        try {
            super.setSilverHead(value);
        }
        finally {
            this.endUpdate();
        }
    }
}

