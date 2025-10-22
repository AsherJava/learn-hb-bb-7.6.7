/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.dataentry.model.DimensionObj;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class BatchCheckStatistics {
    private int checkUnitNums = 0;
    private int checkFormNums = 0;
    private List<Integer> checkType = new ArrayList<Integer>();
    private HashMap<Integer, String> checkTypeTitle = new HashMap();
    private HashMap<Integer, Integer> checkTypeItemNums = new HashMap();
    private HashMap<Integer, Set<String>> checkTypeUnitNums = new HashMap();
    private String formulaSchemeTitle;
    private String unitCorporateTitle;
    private List<DimensionObj> dimList;
    private String taskTitle;
    private String dateTitle;

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getDateTitle() {
        return this.dateTitle;
    }

    public void setDateTitle(String dateTitle) {
        this.dateTitle = dateTitle;
    }

    public List<DimensionObj> getDimList() {
        return this.dimList;
    }

    public void setDimList(List<DimensionObj> dimList) {
        this.dimList = dimList;
    }

    public String getUnitCorporateTitle() {
        return this.unitCorporateTitle;
    }

    public void setUnitCorporateTitle(String unitCorporateTitle) {
        this.unitCorporateTitle = unitCorporateTitle;
    }

    public String getFormulaSchemeTitle() {
        return this.formulaSchemeTitle;
    }

    public void setFormulaSchemeTitle(String formulaSchemeTitle) {
        this.formulaSchemeTitle = formulaSchemeTitle;
    }

    public int getCheckUnitNums() {
        return this.checkUnitNums;
    }

    public void setCheckUnitNums(int checkUnitNums) {
        this.checkUnitNums = checkUnitNums;
    }

    public int getCheckFormNums() {
        return this.checkFormNums;
    }

    public void setCheckFormNums(int checkFormNums) {
        this.checkFormNums = checkFormNums;
    }

    public List<Integer> getCheckType() {
        return this.checkType;
    }

    public void setCheckType(List<Integer> checkType) {
        this.checkType = checkType;
    }

    public HashMap<Integer, String> getCheckTypeTitle() {
        return this.checkTypeTitle;
    }

    public void setCheckTypeTitle(HashMap<Integer, String> checkTypeTitle) {
        this.checkTypeTitle = checkTypeTitle;
    }

    public HashMap<Integer, Integer> getCheckTypeItemNums() {
        return this.checkTypeItemNums;
    }

    public void setCheckTypeItemNums(HashMap<Integer, Integer> checkTypeItemNums) {
        this.checkTypeItemNums = checkTypeItemNums;
    }

    public HashMap<Integer, Set<String>> getCheckTypeUnitNums() {
        return this.checkTypeUnitNums;
    }

    public void setCheckTypeUnitNums(HashMap<Integer, Set<String>> checkTypeUnitNums) {
        this.checkTypeUnitNums = checkTypeUnitNums;
    }
}

