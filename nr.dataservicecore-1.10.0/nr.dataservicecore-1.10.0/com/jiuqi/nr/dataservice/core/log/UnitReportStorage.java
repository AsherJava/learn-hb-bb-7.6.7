/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.log.UnitReportLog
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.nr.dataservice.core.log;

import com.jiuqi.nr.common.log.UnitReportLog;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class UnitReportStorage
implements UnitReportLog {
    private IRunTimeViewController runTimeViewController;
    private final Map<String, Integer> unitToIndex = new HashMap<String, Integer>();
    private final Map<Integer, String> indexToUnit = new HashMap<Integer, String>();
    private final Map<String, Integer> reportToIndex = new HashMap<String, Integer>();
    private final Map<Integer, String> indexToReport = new HashMap<Integer, String>();
    private BitSet[] relationMatrix = new BitSet[16];
    private int currentUnitIndex = 0;
    private int currentReportIndex = 0;
    private Boolean traceForm;

    public void setRunTimeViewController(IRunTimeViewController runTimeViewController) {
        this.runTimeViewController = runTimeViewController;
    }

    public void addFormToUnit(String dwCode, String formKey) {
        if (this.traceForm == null) {
            this.traceForm = true;
        }
        if (!this.traceForm.booleanValue()) {
            throw new UnsupportedOperationException("\u4e0d\u652f\u6301\u540c\u65f6\u8bb0\u5f55\u8868\u5355\u548c\u5b58\u50a8\u8868");
        }
        this.addDwReport(dwCode, formKey);
    }

    public void addFormToUnit(String dwCode, List<String> formKeys) {
        for (String formKey : formKeys) {
            this.addFormToUnit(dwCode, formKey);
        }
    }

    public void addFormToUnit(List<String> dwCodes, String formKey) {
        for (String dwCode : dwCodes) {
            this.addFormToUnit(dwCode, formKey);
        }
    }

    private void addDwReport(String unit, String report) {
        int unitIdx = this.unitToIndex.computeIfAbsent(unit, k -> {
            int newIdx = this.currentUnitIndex++;
            this.indexToUnit.put(newIdx, unit);
            if (newIdx >= this.relationMatrix.length) {
                BitSet[] newMatrix = new BitSet[this.relationMatrix.length * 2];
                System.arraycopy(this.relationMatrix, 0, newMatrix, 0, this.relationMatrix.length);
                this.relationMatrix = newMatrix;
            }
            this.relationMatrix[newIdx] = new BitSet();
            return newIdx;
        });
        int reportIdx = this.reportToIndex.computeIfAbsent(report, k -> {
            int newIdx = this.currentReportIndex++;
            this.indexToReport.put(newIdx, report);
            return newIdx;
        });
        this.relationMatrix[unitIdx].set(reportIdx);
    }

    public void addTableToUnit(String dwCode, String tableCode) {
        if (this.traceForm == null) {
            this.traceForm = false;
        }
        if (this.traceForm.booleanValue()) {
            throw new UnsupportedOperationException("\u4e0d\u652f\u6301\u540c\u65f6\u8bb0\u5f55\u8868\u5355\u548c\u5b58\u50a8\u8868");
        }
        this.addDwReport(dwCode, tableCode);
    }

    public void addTableToUnit(String dwCode, List<String> tableCodes) {
        for (String tableCode : tableCodes) {
            this.addTableToUnit(dwCode, tableCode);
        }
    }

    public void addTableToUnit(List<String> dwCodes, String tableCode) {
        for (String dwCode : dwCodes) {
            this.addTableToUnit(dwCode, tableCode);
        }
    }

    public String[] getReportsForUnit(String unit) {
        Integer unitIdx = this.unitToIndex.get(unit);
        if (unitIdx == null || unitIdx >= this.relationMatrix.length || this.relationMatrix[unitIdx] == null) {
            return new String[0];
        }
        BitSet reportsBitSet = this.relationMatrix[unitIdx];
        String[] reports = new String[reportsBitSet.cardinality()];
        int index = 0;
        int i = reportsBitSet.nextSetBit(0);
        while (i >= 0) {
            reports[index++] = this.indexToReport.get(i);
            i = reportsBitSet.nextSetBit(i + 1);
        }
        return reports;
    }

    public boolean hasReport(String unit, String report) {
        Integer unitIdx = this.unitToIndex.get(unit);
        Integer reportIdx = this.reportToIndex.get(report);
        if (unitIdx == null || reportIdx == null || unitIdx >= this.relationMatrix.length || this.relationMatrix[unitIdx] == null) {
            return false;
        }
        return this.relationMatrix[unitIdx].get(reportIdx);
    }

    public String[] getUnitsUsingReport(String report) {
        Integer reportIdx = this.reportToIndex.get(report);
        if (reportIdx == null) {
            return new String[0];
        }
        int count = 0;
        for (int i = 0; i < this.currentUnitIndex; ++i) {
            if (this.relationMatrix[i] == null || !this.relationMatrix[i].get(reportIdx)) continue;
            ++count;
        }
        String[] units = new String[count];
        int index = 0;
        for (int i = 0; i < this.currentUnitIndex; ++i) {
            if (this.relationMatrix[i] == null || !this.relationMatrix[i].get(reportIdx)) continue;
            units[index++] = this.indexToUnit.get(i);
        }
        return units;
    }

    public String[] getUnits() {
        if (CollectionUtils.isEmpty(this.unitToIndex)) {
            return new String[0];
        }
        String[] units = new String[this.unitToIndex.size()];
        this.unitToIndex.keySet().toArray(units);
        return units;
    }

    public String toLog() {
        StringBuilder log;
        if (this.traceForm == null) {
            return "";
        }
        List sortedUnits = this.indexToUnit.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(Map.Entry::getValue).collect(Collectors.toList());
        List sortedReports = this.indexToReport.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(Map.Entry::getValue).collect(Collectors.toList());
        if (this.traceForm.booleanValue()) {
            List formDefines = Collections.emptyList();
            if (this.runTimeViewController != null) {
                formDefines = this.runTimeViewController.queryFormsById(sortedReports);
            }
            HashMap<String, String> form2Code = new HashMap<String, String>();
            for (Object formDefine : formDefines) {
                form2Code.put(formDefine.getKey(), formDefine.getFormCode());
            }
            ArrayList<String> formCodes = new ArrayList<String>();
            for (String sortedReport : sortedReports) {
                formCodes.add(form2Code.getOrDefault(sortedReport, sortedReport));
            }
            log = new StringBuilder("\u5355\u4f4d\\\u62a5\u8868\u6807\u8bc6\t");
            log.append(String.join((CharSequence)"\t", formCodes)).append("\n");
        } else {
            log = new StringBuilder("\u5355\u4f4d\\\u7269\u7406\u8868\u6807\u8bc6\t");
            log.append(String.join((CharSequence)"\t", sortedReports)).append("\n");
        }
        for (String unit : sortedUnits) {
            log.append(unit).append("\t");
            Integer unitIdx = this.unitToIndex.get(unit);
            BitSet unitReports = this.relationMatrix[unitIdx];
            for (String report : sortedReports) {
                Integer reportIdx = this.reportToIndex.get(report);
                log.append(unitReports != null && unitReports.get(reportIdx) ? "\u2713" : "").append("\t");
            }
            log.append("\n");
        }
        return log.toString();
    }

    public static void main(String[] args) {
        UnitReportStorage unitReportStorage = new UnitReportStorage();
        for (int i = 0; i < 1000; ++i) {
            for (int j = 0; j < 100; ++j) {
                unitReportStorage.addFormToUnit("dw" + i, "form" + j);
            }
        }
        System.out.println(unitReportStorage.toLog());
    }
}

