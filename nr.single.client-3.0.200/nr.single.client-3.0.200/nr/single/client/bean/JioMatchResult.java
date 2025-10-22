/*
 * Decompiled with CFR 0.152.
 */
package nr.single.client.bean;

import java.io.Serializable;

public class JioMatchResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String code;
    private String configKey;
    private String singleTaskFlag;
    private String singleFileFlag;
    private String singleTaskYear;
    private String singleTaskPeriod;
    private boolean autoCalc;
    private boolean hasData;
    private boolean hasParam;
    private String calcSchemeKeys;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfigKey() {
        return this.configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getSingleTaskFlag() {
        return this.singleTaskFlag;
    }

    public void setSingleTaskFlag(String singleTaskFlag) {
        this.singleTaskFlag = singleTaskFlag;
    }

    public String getSingleFileFlag() {
        return this.singleFileFlag;
    }

    public void setSingleFileFlag(String singleFileFlag) {
        this.singleFileFlag = singleFileFlag;
    }

    public String getSingleTaskYear() {
        return this.singleTaskYear;
    }

    public void setSingleTaskYear(String singleTaskYear) {
        this.singleTaskYear = singleTaskYear;
    }

    public String getSingleTaskPeriod() {
        return this.singleTaskPeriod;
    }

    public void setSingleTaskPeriod(String singleTaskPeriod) {
        this.singleTaskPeriod = singleTaskPeriod;
    }

    public boolean isHasData() {
        return this.hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    public boolean isHasParam() {
        return this.hasParam;
    }

    public void setHasParam(boolean hasParam) {
        this.hasParam = hasParam;
    }

    public boolean isAutoCalc() {
        return this.autoCalc;
    }

    public void setAutoCalc(boolean autoCalc) {
        this.autoCalc = autoCalc;
    }

    public String getCalcSchemeKeys() {
        return this.calcSchemeKeys;
    }

    public void setCalcSchemeKeys(String calcSchemeKeys) {
        this.calcSchemeKeys = calcSchemeKeys;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

