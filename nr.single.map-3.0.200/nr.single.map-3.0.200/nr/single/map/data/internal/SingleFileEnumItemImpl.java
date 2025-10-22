/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.internal;

import nr.single.map.data.facade.SingleFileEnumItem;

public class SingleFileEnumItemImpl
implements SingleFileEnumItem {
    private static final long serialVersionUID = 4664719321170401742L;
    private String itemCode;
    private String itemTitle;
    private String netItemCode;
    private String netItemTitle;

    @Override
    public String getItemCode() {
        return this.itemCode;
    }

    @Override
    public String getItemTitle() {
        return this.itemTitle;
    }

    @Override
    public String getNetItemCode() {
        return this.netItemCode;
    }

    @Override
    public String getNetItemTitle() {
        return this.netItemTitle;
    }

    @Override
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Override
    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    @Override
    public void setNetItemCode(String itemCode) {
        this.netItemCode = itemCode;
    }

    @Override
    public void setNetItemTitle(String itemTitle) {
        this.netItemTitle = itemTitle;
    }

    @Override
    public void copyFrom(SingleFileEnumItem item) {
        this.itemCode = item.getItemCode();
        this.itemTitle = item.getItemTitle();
        this.netItemCode = item.getNetItemCode();
        this.netItemTitle = item.getNetItemTitle();
    }
}

