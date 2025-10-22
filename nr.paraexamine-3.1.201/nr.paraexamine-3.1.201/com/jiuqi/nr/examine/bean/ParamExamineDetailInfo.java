/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.examine.bean;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.examine.common.ErrorType;
import com.jiuqi.nr.examine.common.ExamineType;
import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.common.RestoreStatus;

@DBAnno.DBTable(dbTable="NR_PARAMEXAMINE_DETAILINFO")
public class ParamExamineDetailInfo {
    @DBAnno.DBField(dbField="PED_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="PED_PEI_KEY")
    private String infoKey;
    @DBAnno.DBField(dbField="PED_EXAMINE_TYPE", tranWith="transExamineType", dbType=Integer.class, appType=ExamineType.class)
    private ExamineType examineType;
    @DBAnno.DBField(dbField="PED_ERROR_TYPE", tranWith="transErrorType", dbType=Integer.class, appType=ParaType.class)
    private ErrorType errorType;
    @DBAnno.DBField(dbField="PED_PARA_TYPE", tranWith="transParaType", dbType=Integer.class, appType=ParaType.class)
    private ParaType paraType;
    @DBAnno.DBField(dbField="PED_PARA_KEY")
    private String paraKey;
    @DBAnno.DBField(dbField="PED_RESTORE_STATUS", tranWith="transRestoreStatus", dbType=Integer.class, appType=RestoreStatus.class)
    private RestoreStatus restoreStatus = RestoreStatus.UNRESTORE;
    @DBAnno.DBField(dbField="PED_DESCRIPTION")
    private String description;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getInfoKey() {
        return this.infoKey;
    }

    public void setInfoKey(String infoKey) {
        this.infoKey = infoKey;
    }

    public ExamineType getExamineType() {
        return this.examineType;
    }

    public void setExamineType(ExamineType refuse) {
        this.examineType = refuse;
    }

    public ParaType getParaType() {
        return this.paraType;
    }

    public void setParaType(ParaType paraType) {
        this.paraType = paraType;
    }

    public RestoreStatus getRestoreStatus() {
        return this.restoreStatus;
    }

    public void setRestoreStatus(RestoreStatus restoreStatus) {
        this.restoreStatus = restoreStatus;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setParaKey(String paraKey) {
        this.paraKey = paraKey;
    }

    public String getParaKey() {
        return this.paraKey;
    }

    public ErrorType getErrorType() {
        return this.errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }
}

