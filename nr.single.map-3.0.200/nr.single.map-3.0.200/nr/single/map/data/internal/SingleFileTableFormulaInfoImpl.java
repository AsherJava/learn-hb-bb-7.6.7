/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.internal;

import java.util.LinkedHashMap;
import java.util.Map;
import nr.single.map.data.facade.SingleFileFormulaItem;
import nr.single.map.data.facade.SingleFileTableFormulaInfo;
import nr.single.map.data.internal.SingleFileFormulaItemImpl;

public class SingleFileTableFormulaInfoImpl
implements SingleFileTableFormulaInfo {
    private static final long serialVersionUID = -308443831212818638L;
    private String singleTableCode;
    private String netFormCode;
    private String netFormKey;
    private Map<String, SingleFileFormulaItem> formulaItems = new LinkedHashMap<String, SingleFileFormulaItem>();

    @Override
    public String getSingleTableCode() {
        return this.singleTableCode;
    }

    @Override
    public void setSingleTableCode(String singleTableCode) {
        this.singleTableCode = singleTableCode;
    }

    @Override
    public String getNetFormCode() {
        return this.netFormCode;
    }

    @Override
    public void setNetFormCode(String formCode) {
        this.netFormCode = formCode;
    }

    @Override
    public String getNetFormKey() {
        return this.netFormKey;
    }

    @Override
    public void setNetFormKey(String formKey) {
        this.netFormKey = formKey;
    }

    @Override
    public Map<String, SingleFileFormulaItem> getFormulaItems() {
        return this.formulaItems;
    }

    @Override
    public void copyFrom(SingleFileTableFormulaInfo info) {
        this.singleTableCode = info.getSingleTableCode();
        this.netFormCode = info.getNetFormCode();
        this.netFormKey = info.getNetFormKey();
        this.formulaItems.clear();
        for (SingleFileFormulaItem item : info.getFormulaItems().values()) {
            SingleFileFormulaItemImpl newItem = new SingleFileFormulaItemImpl();
            newItem.copyFrom(item);
            this.formulaItems.put(item.getSingleFormulaCode(), newItem);
        }
    }
}

