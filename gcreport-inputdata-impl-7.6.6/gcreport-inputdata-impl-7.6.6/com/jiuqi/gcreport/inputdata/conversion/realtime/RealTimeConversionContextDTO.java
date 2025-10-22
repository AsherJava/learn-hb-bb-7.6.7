/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DoubleKeyMap
 *  com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.inputdata.conversion.realtime;

import com.jiuqi.common.base.util.DoubleKeyMap;
import com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;
import java.util.Map;

public class RealTimeConversionContextDTO {
    private String rateSchemeCode;
    private String periodStr;
    private TableModelDefine tableDefine;
    private TableModelRunInfo tableModelRunInfo;
    private DataEntryContext dataEntryContext;
    private String tableName;
    private DoubleKeyMap<String, String, List<GcConversionIndexRateInfo>> currToConversionCurrToRateMap;
    private Map<String, String> filedDefineKeyToColumnCodeMap;

    public Map<String, String> getFiledDefineKeyToColumnCodeMap() {
        return this.filedDefineKeyToColumnCodeMap;
    }

    public void setFiledDefineKeyToColumnCodeMap(Map<String, String> filedDefineKeyToColumnCodeMap) {
        this.filedDefineKeyToColumnCodeMap = filedDefineKeyToColumnCodeMap;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DataEntryContext getDataEntryContext() {
        return this.dataEntryContext;
    }

    public void setDataEntryContext(DataEntryContext dataEntryContext) {
        this.dataEntryContext = dataEntryContext;
    }

    public TableModelDefine getTableDefine() {
        return this.tableDefine;
    }

    public void setTableDefine(TableModelDefine tableDefine) {
        this.tableDefine = tableDefine;
    }

    public String getRateSchemeCode() {
        return this.rateSchemeCode;
    }

    public void setRateSchemeCode(String rateSchemeCode) {
        this.rateSchemeCode = rateSchemeCode;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public TableModelRunInfo getTableModelRunInfo() {
        return this.tableModelRunInfo;
    }

    public void setTableModelRunInfo(TableModelRunInfo tableModelRunInfo) {
        this.tableModelRunInfo = tableModelRunInfo;
    }

    public DoubleKeyMap<String, String, List<GcConversionIndexRateInfo>> getCurrToConversionCurrToRateMap() {
        return this.currToConversionCurrToRateMap;
    }

    public void setCurrToConversionCurrToRateMap(DoubleKeyMap<String, String, List<GcConversionIndexRateInfo>> currToConversionCurrToRateMap) {
        this.currToConversionCurrToRateMap = currToConversionCurrToRateMap;
    }

    public String toString() {
        return "RealTimeConversionContextDTO{rateSchemeCode='" + this.rateSchemeCode + '\'' + ", periodStr='" + this.periodStr + '\'' + ", tableDefine=" + this.tableDefine + ", tableModelRunInfo=" + this.tableModelRunInfo + ", dataEntryContext=" + this.dataEntryContext + ", tableName='" + this.tableName + '\'' + ", currToConversionCurrToRateMap=" + this.currToConversionCurrToRateMap + ", filedDefineKeyToColumnCodeMap=" + this.filedDefineKeyToColumnCodeMap + '}';
    }
}

