/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.enumcheck.common;

import com.jiuqi.nr.enumcheck.common.EnumCheckResultSaveInfo;
import java.io.Serializable;
import java.util.List;

public class EnumDataCheckResultInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> enumDataCheckResult;
    private boolean hasTooManyError;
    private int selEntityCount;
    private EnumCheckResultSaveInfo saveInfo;
    private int selEnumDicCount;
    private String checkResultMsg;
    private int formCount;

    public EnumCheckResultSaveInfo getSaveInfo() {
        return this.saveInfo;
    }

    public void setSaveInfo(EnumCheckResultSaveInfo saveInfo) {
        this.saveInfo = saveInfo;
    }

    public int getSelEntityCount() {
        return this.selEntityCount;
    }

    public void setSelEntityCount(int selEntityCount) {
        this.selEntityCount = selEntityCount;
    }

    public int getSelEnumDicCount() {
        return this.selEnumDicCount;
    }

    public void setSelEnumDicCount(int selEnumDicCount) {
        this.selEnumDicCount = selEnumDicCount;
    }

    public String getCheckResultMsg() {
        return this.checkResultMsg;
    }

    public void setCheckResultMsg(String checkResultMsg) {
        this.checkResultMsg = checkResultMsg;
    }

    public static long getSerialversionuid() {
        return 1L;
    }

    public List<String> getEnumDataCheckResult() {
        return this.enumDataCheckResult;
    }

    public void setEnumDataCheckResult(List<String> enumDataCheckResult) {
        this.enumDataCheckResult = enumDataCheckResult;
    }

    public boolean getHasTooManyError() {
        return this.hasTooManyError;
    }

    public void setHasTooManyError(boolean hasTooManyError) {
        this.hasTooManyError = hasTooManyError;
    }

    public int getFormCount() {
        return this.formCount;
    }

    public void setFormCount(int formCount) {
        this.formCount = formCount;
    }
}

