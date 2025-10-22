/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.domain;

import java.time.Instant;
import nr.single.para.compare.definition.common.CompareStatusType;

public class ParamImportInfoDTO {
    private String key;
    private String code;
    private String title;
    private String singleTaskCode;
    private String singleFileFlag;
    private String singleTaskYear;
    private String singleTaskTitle;
    private String netTaskKey;
    private String netTaskTitle;
    private String formSchemeKey;
    private String formSchemeTitle;
    private CompareStatusType importStatus;
    private Instant updateTime;
    private String logFileKey;
    private String message;
    private boolean importBaseParam;
    private boolean importFormula;
    private boolean importPrint;
    private boolean importQuery;
    private boolean coverParam;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public String getSingleTaskCode() {
        return this.singleTaskCode;
    }

    public void setSingleTaskCode(String singleTaskCode) {
        this.singleTaskCode = singleTaskCode;
    }

    public String getSingleTaskTitle() {
        return this.singleTaskTitle;
    }

    public void setSingleTaskTitle(String singleTaskTitle) {
        this.singleTaskTitle = singleTaskTitle;
    }

    public String getNetTaskKey() {
        return this.netTaskKey;
    }

    public void setNetTaskKey(String netTaskKey) {
        this.netTaskKey = netTaskKey;
    }

    public String getNetTaskTitle() {
        return this.netTaskTitle;
    }

    public void setNetTaskTitle(String netTaskTitle) {
        this.netTaskTitle = netTaskTitle;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public CompareStatusType getImportStatus() {
        return this.importStatus;
    }

    public void setImportStatus(CompareStatusType importStatus) {
        this.importStatus = importStatus;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getLogFileKey() {
        return this.logFileKey;
    }

    public void setLogFileKey(String logFileKey) {
        this.logFileKey = logFileKey;
    }

    public String getSingleTaskYear() {
        return this.singleTaskYear;
    }

    public void setSingleTaskYear(String singleTaskYear) {
        this.singleTaskYear = singleTaskYear;
    }

    public String getSingleFileFlag() {
        return this.singleFileFlag;
    }

    public void setSingleFileFlag(String singleFileFlag) {
        this.singleFileFlag = singleFileFlag;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isImportBaseParam() {
        return this.importBaseParam;
    }

    public void setImportBaseParam(boolean importBaseParam) {
        this.importBaseParam = importBaseParam;
    }

    public boolean isImportFormula() {
        return this.importFormula;
    }

    public void setImportFormula(boolean importFormula) {
        this.importFormula = importFormula;
    }

    public boolean isImportPrint() {
        return this.importPrint;
    }

    public void setImportPrint(boolean importPrint) {
        this.importPrint = importPrint;
    }

    public boolean isImportQuery() {
        return this.importQuery;
    }

    public void setImportQuery(boolean importQuery) {
        this.importQuery = importQuery;
    }

    public boolean isCoverParam() {
        return this.coverParam;
    }

    public void setCoverParam(boolean coverParam) {
        this.coverParam = coverParam;
    }
}

