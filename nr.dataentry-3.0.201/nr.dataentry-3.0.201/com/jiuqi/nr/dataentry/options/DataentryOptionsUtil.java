/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.dataentry.options;

import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DataentryOptionsUtil {
    private final INvwaSystemOptionService iNvwaSystemOptionService;

    @Autowired
    public DataentryOptionsUtil(INvwaSystemOptionService iNvwaSystemOptionService) {
        this.iNvwaSystemOptionService = iNvwaSystemOptionService;
    }

    public boolean autoCaclUpload() {
        String value = this.iNvwaSystemOptionService.get("nr-data-entry-group", "AUTOCALCULAT_AFTER_IMPORT");
        if (StringUtils.hasLength(value)) {
            return value.equals("1");
        }
        return false;
    }

    public boolean allCheckCaclAutoExe() {
        String value = this.iNvwaSystemOptionService.get("nr-data-entry-group", "CALC_CHECK_EXE");
        if (StringUtils.hasLength(value)) {
            return value.equals("1");
        }
        return false;
    }

    public boolean exportAutoExe() {
        String value = this.iNvwaSystemOptionService.get("nr-data-entry-group", "EXPORT_EXE");
        if (StringUtils.hasLength(value)) {
            return value.equals("1");
        }
        return false;
    }
}

