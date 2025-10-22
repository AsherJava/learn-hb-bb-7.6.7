/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.parain.maping2;

public class SingleMappingTransOption {
    private boolean updateZb;
    private boolean updateBaseData;
    private boolean updateFormula;
    private boolean updatePeriod;
    private boolean updateConfig;

    public boolean isUpdateZb() {
        return this.updateZb;
    }

    public void setUpdateZb(boolean updateZb) {
        this.updateZb = updateZb;
    }

    public boolean isUpdateBaseData() {
        return this.updateBaseData;
    }

    public void setUpdateBaseData(boolean updateBaseData) {
        this.updateBaseData = updateBaseData;
    }

    public boolean isUpdateFormula() {
        return this.updateFormula;
    }

    public void setUpdateFormula(boolean updateFormula) {
        this.updateFormula = updateFormula;
    }

    public boolean isUpdatePeriod() {
        return this.updatePeriod;
    }

    public void setUpdatePeriod(boolean updatePeriod) {
        this.updatePeriod = updatePeriod;
    }

    public void selectAll() {
        this.updateZb = true;
        this.updateBaseData = true;
        this.updateFormula = true;
        this.updatePeriod = true;
        this.updateConfig = true;
    }

    public boolean isUpdateConfig() {
        return this.updateConfig;
    }

    public void setUpdateConfig(boolean updateConfig) {
        this.updateConfig = updateConfig;
    }
}

