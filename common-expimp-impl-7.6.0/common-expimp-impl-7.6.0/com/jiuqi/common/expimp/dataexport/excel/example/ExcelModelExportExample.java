/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 */
package com.jiuqi.common.expimp.dataexport.excel.example;

import com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class ExcelModelExportExample {
    @ExcelColumn(index=0, title={"\u540d\u79f0", "\u540d\u79f0"})
    private String name;
    @ExcelColumn(index=1, title={"\u4ee3\u7801"}, horizontalAlignment=HorizontalAlignment.RIGHT)
    private String code;
    @ExcelColumn(index=2, title={"\u5e74\u9f84", "0-100"})
    private Integer age;

    public ExcelModelExportExample(String name, String code, Integer age) {
        this.name = name;
        this.code = code;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

