/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import com.jiuqi.nvwa.cellbook.constant.EnterNext;
import com.jiuqi.nvwa.cellbook.constant.SelectionModel;
import com.jiuqi.nvwa.cellbook.model.Footer;
import com.jiuqi.nvwa.cellbook.model.Header;
import com.jiuqi.nvwa.cellbook.model.Resize;
import java.io.Serializable;

public class Options
implements Serializable {
    private static final long serialVersionUID = 1L;
    private SelectionModel selectMode = SelectionModel.SINGLE;
    private EnterNext enterNext = EnterNext.AUTO;
    private Header header = new Header();
    private Footer footer = new Footer();
    private Resize resize = new Resize();
    private boolean hiddenSerialNumberHeader;

    public SelectionModel getSelectMode() {
        return this.selectMode;
    }

    public void setSelectMode(SelectionModel selectMode) {
        this.selectMode = selectMode;
    }

    public EnterNext getEnterNext() {
        return this.enterNext;
    }

    public void setEnterNext(EnterNext enterNext) {
        this.enterNext = enterNext;
    }

    public Header getHeader() {
        return this.header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Resize getResize() {
        return this.resize;
    }

    public void setResize(Resize resize) {
        this.resize = resize;
    }

    public boolean isHiddenSerialNumberHeader() {
        return this.hiddenSerialNumberHeader;
    }

    public void setHiddenSerialNumberHeader(boolean hiddenSerialNumberHeader) {
        this.hiddenSerialNumberHeader = hiddenSerialNumberHeader;
    }

    public Footer getFooter() {
        return this.footer;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
    }
}

