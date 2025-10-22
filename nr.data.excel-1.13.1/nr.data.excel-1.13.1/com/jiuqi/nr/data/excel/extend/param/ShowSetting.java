/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.extend.param;

import com.jiuqi.nr.data.excel.extend.param.SheetNameType;
import com.jiuqi.nr.data.excel.obj.DWShow;
import com.jiuqi.nr.data.excel.obj.FormShow;
import java.util.List;

public class ShowSetting {
    private final List<DWShow> dwShows;
    private final List<FormShow> formShows;
    private final String separator;
    private final SheetNameType sheetNameType;

    public ShowSetting(List<DWShow> dwShows, List<FormShow> formShows, SheetNameType sheetNameType, String separator) {
        this.dwShows = dwShows;
        this.formShows = formShows;
        this.sheetNameType = sheetNameType;
        this.separator = separator;
    }

    public List<DWShow> getDwShows() {
        return this.dwShows;
    }

    public List<FormShow> getFormShows() {
        return this.formShows;
    }

    public SheetNameType getSheetNameType() {
        return this.sheetNameType;
    }

    public String getSeparator() {
        return this.separator;
    }
}

