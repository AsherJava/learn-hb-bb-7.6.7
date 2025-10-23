/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.dto;

import com.jiuqi.nr.task.form.formio.common.ImportDropDownType;
import com.jiuqi.nr.task.form.formio.dto.ExcelDropDownOption;
import com.jiuqi.nr.task.form.formio.dto.ImportBaseDTO;
import java.util.ArrayList;
import java.util.List;

public class ImportDropDownDTO
extends ImportBaseDTO {
    private ImportDropDownType dropDownType;
    private String formula;
    private List<ExcelDropDownOption> options;
    private String suffixCode;

    public ImportDropDownType getDropDownType() {
        return this.dropDownType;
    }

    public void setDropDownType(ImportDropDownType dropDownType) {
        this.dropDownType = dropDownType;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public List<ExcelDropDownOption> getOptions() {
        return this.options;
    }

    public void setOptions(List<ExcelDropDownOption> options) {
        this.options = options;
    }

    public void generateDropDownOptions(List<String> cellValues) {
        this.options = new ArrayList<ExcelDropDownOption>();
        int size = cellValues.size();
        ArrayList<String> codes = new ArrayList<String>(size);
        for (int i = 1; i <= size; ++i) {
            codes.add(String.format("%s%d", this.getSuffixCode(), i));
        }
        for (int i = 0; i < size; ++i) {
            ExcelDropDownOption option = new ExcelDropDownOption();
            option.setTitle(cellValues.get(i));
            option.setCode((String)codes.get(i));
            this.options.add(option);
        }
    }

    public String getSuffixCode() {
        return this.suffixCode;
    }

    public void setSuffixCode(String suffixCode) {
        this.suffixCode = suffixCode;
    }
}

