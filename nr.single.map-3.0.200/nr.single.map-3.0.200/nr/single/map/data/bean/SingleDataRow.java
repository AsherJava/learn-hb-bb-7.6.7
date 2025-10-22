/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 */
package nr.single.map.data.bean;

import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.util.LinkedHashMap;
import java.util.Map;

public class SingleDataRow {
    private String zdm;
    private String fjd;
    private String period;
    private String dwdm;
    private String bblx;
    private String dwmc;
    private DataRow dataRow;
    private String mapExpValue;
    private String mapFieldValue;
    private String zdmWithOutPeriod;
    private Map<String, String> fieldValues;

    public String getZdm() {
        return this.zdm;
    }

    public void setZdm(String zdm) {
        this.zdm = zdm;
    }

    public String getFjd() {
        return this.fjd;
    }

    public void setFjd(String fjd) {
        this.fjd = fjd;
    }

    public DataRow getDataRow() {
        return this.dataRow;
    }

    public void setDataRow(DataRow dataRow) {
        this.dataRow = dataRow;
    }

    public String getMapExpValue() {
        return this.mapExpValue;
    }

    public void setMapExpValue(String mapExpValue) {
        this.mapExpValue = mapExpValue;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getMapFieldValue() {
        return this.mapFieldValue;
    }

    public void setMapFieldValue(String mapFieldValue) {
        this.mapFieldValue = mapFieldValue;
    }

    public String getDwdm() {
        return this.dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
    }

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
    }

    public String getDwmc() {
        return this.dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public Map<String, String> getFieldValues() {
        if (this.fieldValues == null) {
            this.fieldValues = new LinkedHashMap<String, String>();
        }
        return this.fieldValues;
    }

    public void setFieldValues(Map<String, String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public String getZdmWithOutPeriod() {
        return this.zdmWithOutPeriod;
    }

    public void setZdmWithOutPeriod(String zdmWithOutPeriod) {
        this.zdmWithOutPeriod = zdmWithOutPeriod;
    }
}

