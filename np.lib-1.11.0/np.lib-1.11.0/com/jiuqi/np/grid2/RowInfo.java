/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid2;

import com.jiuqi.np.graphics.ImageDescriptor;
import com.jiuqi.np.grid2.AbstractColRowInfo;
import java.io.Serializable;

public class RowInfo
extends AbstractColRowInfo
implements Serializable {
    private static final long serialVersionUID = -5618200911339809043L;
    private int rowBackgroundColor = -1;
    private ImageDescriptor image;

    public RowInfo() {
        this.size = 20;
    }

    public RowInfo(RowInfo info) {
        this();
        this.setSize(info.getSize());
        this.setAutoSize(info.isAutoSize());
        this.setVisiable(info.isVisiable());
    }

    public int getRowHeight() {
        return this.getSize();
    }

    public void setRowHeight(int height) {
        this.setSize(height);
    }

    public void setRowAutoHeight(boolean value) {
        this.setAutoSize(value);
    }

    public boolean isRowAutoHeight() {
        return this.isAutoSize();
    }

    public boolean isRowHidden() {
        return !this.isVisiable();
    }

    public void setRowHidden(boolean value) {
        this.setVisiable(!value);
    }

    public int getRowBackgroundColor() {
        return this.rowBackgroundColor;
    }

    public void setRowBackgroundColor(int rowBackgroundColor) {
        this.rowBackgroundColor = rowBackgroundColor;
    }

    public void setRowBackImage(ImageDescriptor image) {
        this.image = image;
    }

    public ImageDescriptor getRowBackImage() {
        return this.image;
    }
}

