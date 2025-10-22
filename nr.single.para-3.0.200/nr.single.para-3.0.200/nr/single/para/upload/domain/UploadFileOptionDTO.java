/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.domain;

public class UploadFileOptionDTO {
    private String Key;
    private Boolean importBaseParam;
    private Boolean importFormula;
    private Boolean importPrint;
    private Boolean importQuery;
    private Boolean coverParam;

    public String getKey() {
        return this.Key;
    }

    public void setKey(String key) {
        this.Key = key;
    }

    public Boolean getImportBaseParam() {
        return this.importBaseParam;
    }

    public void setImportBaseParam(Boolean importBaseParam) {
        this.importBaseParam = importBaseParam;
    }

    public Boolean getImportFormula() {
        return this.importFormula;
    }

    public void setImportFormula(Boolean importFormula) {
        this.importFormula = importFormula;
    }

    public Boolean getImportPrint() {
        return this.importPrint;
    }

    public void setImportPrint(Boolean importPrint) {
        this.importPrint = importPrint;
    }

    public Boolean getImportQuery() {
        return this.importQuery;
    }

    public void setImportQuery(Boolean importQuery) {
        this.importQuery = importQuery;
    }

    public Boolean getCoverParam() {
        return this.coverParam;
    }

    public void setCoverParam(Boolean coverParam) {
        this.coverParam = coverParam;
    }
}

