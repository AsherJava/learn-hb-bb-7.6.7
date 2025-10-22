/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.domain;

import nr.single.para.upload.domain.BaseCompareDTO;

public class ZBMappingDTO
extends BaseCompareDTO {
    private String formCompareKey;
    private String ownTableCode;
    private String ownTableTitle;
    private String ownTableKey;
    private String singleMatchTitle;
    private Integer singleFloatingId;
    private String netMatchTitle;
    private int regionIndex;
    private int singlePosX;
    private int singlePosY;
    private int netPosX;
    private int netPosY;

    public String getFormCompareKey() {
        return this.formCompareKey;
    }

    public void setFormCompareKey(String formCompareKey) {
        this.formCompareKey = formCompareKey;
    }

    public String getOwnTableCode() {
        return this.ownTableCode;
    }

    public void setOwnTableCode(String ownTableCode) {
        this.ownTableCode = ownTableCode;
    }

    public String getOwnTableTitle() {
        return this.ownTableTitle;
    }

    public void setOwnTableTitle(String ownTableTitle) {
        this.ownTableTitle = ownTableTitle;
    }

    public String getSingleMatchTitle() {
        return this.singleMatchTitle;
    }

    public String getOwnTableKey() {
        return this.ownTableKey;
    }

    public void setOwnTableKey(String ownTableKey) {
        this.ownTableKey = ownTableKey;
    }

    public void setSingleMatchTitle(String singleMatchTitle) {
        this.singleMatchTitle = singleMatchTitle;
    }

    public String getNetMatchTitle() {
        return this.netMatchTitle;
    }

    public void setNetMatchTitle(String netMatchTitle) {
        this.netMatchTitle = netMatchTitle;
    }

    public int getRegionIndex() {
        return this.regionIndex;
    }

    public void setRegionIndex(int regionIndex) {
        this.regionIndex = regionIndex;
    }

    public int getSinglePosX() {
        return this.singlePosX;
    }

    public void setSinglePosX(int singlePosX) {
        this.singlePosX = singlePosX;
    }

    public int getSinglePosY() {
        return this.singlePosY;
    }

    public void setSinglePosY(int singlePosY) {
        this.singlePosY = singlePosY;
    }

    public int getNetPosX() {
        return this.netPosX;
    }

    public void setNetPosX(int netPosX) {
        this.netPosX = netPosX;
    }

    public int getNetPosY() {
        return this.netPosY;
    }

    public void setNetPosY(int netPosY) {
        this.netPosY = netPosY;
    }

    public Integer getSingleFloatingId() {
        return this.singleFloatingId;
    }

    public void setSingleFloatingId(Integer singleFloatingId) {
        this.singleFloatingId = singleFloatingId;
    }
}

