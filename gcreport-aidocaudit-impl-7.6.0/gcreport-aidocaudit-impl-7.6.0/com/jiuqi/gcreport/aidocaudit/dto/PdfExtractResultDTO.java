/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.dto;

import com.jiuqi.gcreport.aidocaudit.service.impl.GetCharLocationAndSize;
import java.util.List;

public class PdfExtractResultDTO {
    private List<GetCharLocationAndSize> result;
    private boolean hasCatalog;

    public PdfExtractResultDTO() {
    }

    public PdfExtractResultDTO(List<GetCharLocationAndSize> result, boolean hasCatalog) {
        this.result = result;
        this.hasCatalog = hasCatalog;
    }

    public List<GetCharLocationAndSize> getResult() {
        return this.result;
    }

    public void setResult(List<GetCharLocationAndSize> result) {
        this.result = result;
    }

    public boolean isHasCatalog() {
        return this.hasCatalog;
    }

    public void setHasCatalog(boolean hasCatalog) {
        this.hasCatalog = hasCatalog;
    }
}

