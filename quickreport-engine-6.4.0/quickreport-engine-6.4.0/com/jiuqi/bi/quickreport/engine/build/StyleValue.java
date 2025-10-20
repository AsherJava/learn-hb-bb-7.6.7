/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.build;

import com.jiuqi.bi.quickreport.model.DataBarMode;
import com.jiuqi.bi.quickreport.model.DataBarStyle;
import com.jiuqi.bi.quickreport.model.IconStyle;
import java.util.StringJoiner;

public final class StyleValue {
    private DataBarStyle dataBarStyle;
    private IconStyle iconStyle;
    private int options;
    private static final int OPT_BACKCOLOR = 1;

    public DataBarStyle getDataBarStyle() {
        return this.dataBarStyle;
    }

    public void setDataBarStyle(DataBarStyle dataBarStyle) {
        this.dataBarStyle = dataBarStyle;
    }

    @Deprecated
    public DataBarMode getBarMode() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void setBarMode(DataBarMode barMode) {
        throw new UnsupportedOperationException();
    }

    public IconStyle getIconStyle() {
        return this.iconStyle;
    }

    public void setIconStyle(IconStyle iconStyle) {
        this.iconStyle = iconStyle;
    }

    @Deprecated
    public String getIconName() {
        return this.iconStyle == null ? null : this.iconStyle.getIconName();
    }

    @Deprecated
    public void setIconName(String iconName) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public int getIconPos() {
        return this.iconStyle == null ? 0 : this.iconStyle.getPosition();
    }

    @Deprecated
    public void setIconPos(int iconPos) {
        throw new UnsupportedOperationException();
    }

    public boolean isBackColorChanged() {
        return (this.options & 1) != 0;
    }

    public void setBackColorChanged(boolean changed) {
        this.options = changed ? (this.options |= 1) : (this.options &= 0xFFFFFFFE);
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        if (this.dataBarStyle != null) {
            joiner.add(this.dataBarStyle.toString());
        }
        if (this.iconStyle != null) {
            joiner.add(this.iconStyle.toString());
        }
        if (this.isBackColorChanged()) {
            joiner.add("backcolored");
        }
        return joiner.toString();
    }
}

