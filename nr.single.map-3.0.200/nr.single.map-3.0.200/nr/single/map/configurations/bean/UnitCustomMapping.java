/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package nr.single.map.configurations.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import nr.single.map.configurations.deserializer.UnitCustomMappingDeserializer;

@JsonDeserialize(using=UnitCustomMappingDeserializer.class)
public class UnitCustomMapping
implements Serializable {
    public static final String NETKEY = "netUnitKey";
    public static final String NETNAME = "netUnitName";
    public static final String NETCODE = "netUnitCode";
    public static final String BBLX_CODE = "bblx";
    public static final String SINGLECODE = "singleUnitCode";
    private static final long serialVersionUID = 8680737127133035030L;
    private String netUnitKey;
    private String netUnitName;
    private String netUnitCode;
    private String bblx;
    private String singleUnitCode;
    private String singleParentUnitCode;
    private String importIndex;

    public UnitCustomMapping() {
    }

    public UnitCustomMapping(String netUnitKey, String netUnitName, String netUnitCode, String bblx, String singleUnitCode, String importIndex) {
        this.netUnitKey = netUnitKey;
        this.netUnitName = netUnitName;
        this.netUnitCode = netUnitCode;
        this.bblx = bblx;
        this.singleUnitCode = singleUnitCode;
        this.importIndex = importIndex;
    }

    public String getNetUnitKey() {
        return this.netUnitKey;
    }

    public void setNetUnitKey(String netUnitKey) {
        this.netUnitKey = netUnitKey;
    }

    public String getNetUnitName() {
        return this.netUnitName;
    }

    public void setNetUnitName(String netUnitName) {
        this.netUnitName = netUnitName;
    }

    public String getNetUnitCode() {
        return this.netUnitCode;
    }

    public void setNetUnitCode(String netUnitCode) {
        this.netUnitCode = netUnitCode;
    }

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
    }

    public String getSingleUnitCode() {
        return this.singleUnitCode;
    }

    public void setSingleUnitCode(String singleUnitCode) {
        this.singleUnitCode = singleUnitCode;
    }

    public String getImportIndex() {
        return this.importIndex;
    }

    public void setImportIndex(String importIndex) {
        this.importIndex = importIndex;
    }

    public String getSingleParentUnitCode() {
        return this.singleParentUnitCode;
    }

    public void setSingleParentUnitCode(String singleParentUnitCode) {
        this.singleParentUnitCode = singleParentUnitCode;
    }
}

