/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DataChkInfo {
    private String errorZDM;
    private String tableFlag;
    private String fomulaExp;
    private String floatFlag;
    private String floatOrder;
    private String eorrorHint;
    private String formulaFlag;
    private int rowNum;
    private int colNum;
    private int floatingId;
    private String fmlScheme;
    private String netFloatOrder;
    private String floatRecKey;
    private boolean isMaped = false;
    private boolean isFloatData = false;
    private Map<String, String> floatFlagItems = null;
    private Set<String> ownerTables;
    private boolean linkFormula = false;

    public String getErrorZDM() {
        return this.errorZDM;
    }

    public void setErrorZDM(String errorZDM) {
        this.errorZDM = errorZDM;
    }

    public String getTableFlag() {
        return this.tableFlag;
    }

    public void setTableFlag(String tableFlag) {
        this.tableFlag = tableFlag;
    }

    public String getFomulaExp() {
        return this.fomulaExp;
    }

    public void setFomulaExp(String fomulaExp) {
        this.fomulaExp = fomulaExp;
    }

    public String getFloatFlag() {
        return this.floatFlag;
    }

    public void setFloatFlag(String floatFlag) {
        this.floatFlag = floatFlag;
    }

    public String getEorrorHint() {
        return this.eorrorHint;
    }

    public void setEorrorHint(String eorrorHint) {
        this.eorrorHint = eorrorHint;
    }

    public String getFormulaFlag() {
        return this.formulaFlag;
    }

    public void setFormulaFlag(String formulaFlag) {
        this.formulaFlag = formulaFlag;
    }

    public int getRowNum() {
        return this.rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getColNum() {
        return this.colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public String getFloatRecKey() {
        return this.floatRecKey;
    }

    public void setFloatRecKey(String floatRecKey) {
        this.floatRecKey = floatRecKey;
    }

    public boolean isMaped() {
        return this.isMaped;
    }

    public void setMaped(boolean isMaped) {
        this.isMaped = isMaped;
    }

    public boolean isFloatData() {
        return this.isFloatData;
    }

    public void setFloatData(boolean isFloatData) {
        this.isFloatData = isFloatData;
    }

    public String getKeyCode() {
        return this.tableFlag + '-' + this.errorZDM + '-' + this.formulaFlag + '-' + this.rowNum + '-' + this.colNum + '-' + this.floatFlag + '-' + this.floatOrder + '-' + this.fmlScheme;
    }

    public String getFloatOrder() {
        return this.floatOrder;
    }

    public void setFloatOrder(String floatOrder) {
        this.floatOrder = floatOrder;
    }

    public String getNetFloatOrder() {
        return this.netFloatOrder;
    }

    public void setNetFloatOrder(String netFloatOrder) {
        this.netFloatOrder = netFloatOrder;
    }

    public int getFloatingId() {
        return this.floatingId;
    }

    public void setFloatingId(int floatingId) {
        this.floatingId = floatingId;
    }

    public String getFmlScheme() {
        return this.fmlScheme;
    }

    public void setFmlScheme(String fmlScheme) {
        this.fmlScheme = fmlScheme;
    }

    public Map<String, String> getFloatFlagItems() {
        if (this.floatFlagItems == null) {
            this.floatFlagItems = new HashMap<String, String>();
        }
        return this.floatFlagItems;
    }

    public void setFloatFlagItems(Map<String, String> floatFlagItems) {
        this.floatFlagItems = floatFlagItems;
    }

    public Set<String> getOwnerTables() {
        if (this.ownerTables == null) {
            this.ownerTables = new HashSet<String>();
        }
        return this.ownerTables;
    }

    public void setOwnerTables(Set<String> ownerTables) {
        this.ownerTables = ownerTables;
    }

    public boolean isLinkFormula() {
        return this.linkFormula;
    }

    public void setLinkFormula(boolean linkFormula) {
        this.linkFormula = linkFormula;
    }
}

