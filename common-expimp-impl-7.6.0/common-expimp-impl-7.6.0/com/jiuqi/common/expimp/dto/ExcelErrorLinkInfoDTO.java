/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.expimp.dto;

import java.util.HashSet;
import java.util.Set;

public class ExcelErrorLinkInfoDTO {
    private int errorCellRowNum;
    private Set<Integer> errorCellColNums;
    private String errorCellMsg;
    private String sheetName;

    public ExcelErrorLinkInfoDTO() {
    }

    public ExcelErrorLinkInfoDTO(int errorCellRowNum, String errorCellMsg, String sheetName) {
        this.errorCellRowNum = errorCellRowNum;
        this.errorCellMsg = errorCellMsg;
        this.sheetName = sheetName;
        this.errorCellColNums = new HashSet<Integer>();
    }

    public int getErrorCellRowNum() {
        return this.errorCellRowNum;
    }

    public void setErrorCellRowNum(int errorCellRowNum) {
        this.errorCellRowNum = errorCellRowNum;
    }

    public String getErrorCellMsg() {
        return this.errorCellMsg;
    }

    public void setErrorCellMsg(String errorCellMsg) {
        this.errorCellMsg = errorCellMsg;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Set<Integer> getErrorCellColNums() {
        return this.errorCellColNums;
    }
}

