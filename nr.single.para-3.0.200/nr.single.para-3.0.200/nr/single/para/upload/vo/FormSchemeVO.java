/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.vo;

import java.util.ArrayList;
import java.util.List;
import nr.single.para.upload.domain.CommonParamDTO;

public class FormSchemeVO
extends CommonParamDTO {
    private String formPeriod;
    private String toPeriod;
    private List<String> periodList;

    public String getFormPeriod() {
        return this.formPeriod;
    }

    public void setFormPeriod(String formPeriod) {
        this.formPeriod = formPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public List<String> getPeriodList() {
        if (this.periodList == null) {
            this.periodList = new ArrayList<String>();
        }
        return this.periodList;
    }

    public void setPeriodList(List<String> periodList) {
        this.periodList = periodList;
    }
}

