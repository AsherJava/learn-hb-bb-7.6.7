/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.file.ini;

public class IniZBInfo {
    private String zBName = "";
    private String ptCode;
    private String periodStr;
    private String dim;
    private String tableName;
    private String zbGUID;

    public String getZBName() {
        return this.zBName;
    }

    public void setZBName(String zBName) {
        this.zBName = zBName;
    }

    public String getPeriodSer() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getDim() {
        return this.dim;
    }

    public void setDim(String dim) {
        this.dim = dim;
    }

    public String getPtCode() {
        return this.ptCode;
    }

    public void setPtCode(String ptCode) {
        this.ptCode = ptCode;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getZbGUID() {
        return this.zbGUID;
    }

    public void setZbGUID(String zbGUID) {
        this.zbGUID = zbGUID;
    }
}

