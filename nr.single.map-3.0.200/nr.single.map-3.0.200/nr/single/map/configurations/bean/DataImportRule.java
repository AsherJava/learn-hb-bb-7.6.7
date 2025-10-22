/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.bean;

import java.util.List;
import nr.single.map.configurations.bean.UnitState;

public class DataImportRule {
    private boolean enable;
    private List<UnitState> importData;
    private List<UnitState> unImportData;

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<UnitState> getImportData() {
        return this.importData;
    }

    public void setImportData(List<UnitState> importData) {
        this.importData = importData;
    }

    public List<UnitState> getUnImportData() {
        return this.unImportData;
    }

    public void setUnImportData(List<UnitState> unImportData) {
        this.unImportData = unImportData;
    }

    public static DataImportRule getInstance() {
        DataImportRule dataImportRule = new DataImportRule();
        dataImportRule.setEnable(false);
        return dataImportRule;
    }
}

