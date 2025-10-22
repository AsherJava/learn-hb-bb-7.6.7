/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.domain;

import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import java.util.List;

public class BatchCalibreDataOptionsDTO {
    private String defineKey;
    private String defineCode;
    List<CalibreDataDTO> calibreDataDTOS;

    public String getDefineKey() {
        return this.defineKey;
    }

    public void setDefineKey(String defineKey) {
        this.defineKey = defineKey;
    }

    public String getDefineCode() {
        return this.defineCode;
    }

    public void setDefineCode(String defineCode) {
        this.defineCode = defineCode;
    }

    public List<CalibreDataDTO> getCalibreDataDTOS() {
        return this.calibreDataDTOS;
    }

    public void setCalibreDataDTOS(List<CalibreDataDTO> calibreDataDTOS) {
        this.calibreDataDTOS = calibreDataDTOS;
    }
}

