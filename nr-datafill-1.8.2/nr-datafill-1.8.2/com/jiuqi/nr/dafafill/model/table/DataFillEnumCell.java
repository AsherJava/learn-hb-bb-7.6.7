/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.nr.dafafill.model.table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.dafafill.model.table.DataFillBaseCell;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataFillEnumCell
extends DataFillBaseCell {
    private static final long serialVersionUID = 1L;
    private String code;
    private String title;
    private String showTitle;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShowTitle() {
        return this.showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    @Override
    public String toString() {
        return this.code + "|" + this.title;
    }
}

