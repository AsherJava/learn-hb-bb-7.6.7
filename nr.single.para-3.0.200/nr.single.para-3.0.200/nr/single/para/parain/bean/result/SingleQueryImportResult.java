/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.parain.bean.result;

import java.util.List;
import nr.single.para.parain.bean.result.SingleQueryImportItemResult;

public class SingleQueryImportResult {
    private boolean success;
    private String message;
    private List<SingleQueryImportItemResult> itemList;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SingleQueryImportItemResult> getItemList() {
        return this.itemList;
    }

    public void setItemList(List<SingleQueryImportItemResult> itemList) {
        this.itemList = itemList;
    }
}

