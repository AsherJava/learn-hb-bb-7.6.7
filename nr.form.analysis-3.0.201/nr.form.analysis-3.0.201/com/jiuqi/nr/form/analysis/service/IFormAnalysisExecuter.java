/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.form.analysis.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.form.analysis.dto.AnalysisParamDTO;
import java.util.function.Consumer;

public interface IFormAnalysisExecuter {
    public static final String ASYNCTASK_NAME = "ASYNCTASK_FORMANALYSIS_BATCHANALYSIS";

    public void execute(AnalysisParamDTO var1, Consumer<FormAnalysisProgress> var2);

    public static class FormAnalysisProgress {
        private double anaCount = 0.0;
        private double anaIndex = 0.0;
        private DimensionValueSet dimValue;
        private String formCode;
        private FormAnalysisState state = FormAnalysisState.PREPARE;
        private String message = "\u6b63\u5728\u6784\u5efa\u5206\u6790\u73af\u5883";

        public FormAnalysisProgress setActiveProgress(double activeIndex, DimensionValueSet dimValue, String formCode) {
            this.state = FormAnalysisState.ANALYSISING;
            this.anaIndex = activeIndex;
            this.dimValue = dimValue;
            this.formCode = formCode;
            return this;
        }

        public FormAnalysisProgress analysising(int anaCount) {
            this.state = FormAnalysisState.ANALYSISING;
            this.anaCount = anaCount;
            return this;
        }

        public FormAnalysisProgress fail(String message) {
            this.state = FormAnalysisState.FAIL;
            this.message = message;
            return this;
        }

        public FormAnalysisProgress success(String message) {
            this.state = FormAnalysisState.SUCCESS;
            this.message = message;
            return this;
        }

        public double getProgress() {
            if (0.0 == this.anaCount) {
                return 0.0;
            }
            return this.anaIndex / this.anaCount;
        }

        public DimensionValueSet getDimValue() {
            return this.dimValue;
        }

        public String getFormCode() {
            return this.formCode;
        }

        public boolean isFail() {
            return this.state == FormAnalysisState.FAIL;
        }

        public boolean isSuccess() {
            return this.state == FormAnalysisState.SUCCESS;
        }

        public FormAnalysisState getState() {
            return this.state;
        }

        public String getMessage() {
            switch (this.state) {
                case ANALYSISING: {
                    if (null == this.dimValue || null == this.formCode) {
                        return "\u6b63\u5728\u5206\u6790";
                    }
                    return String.format("\u6b63\u5728\u5206\u6790\uff1a\u7ef4\u5ea6\u503c[%s], \u62a5\u8868[%s].", this.dimValue, this.formCode);
                }
            }
            return this.message;
        }
    }

    public static enum FormAnalysisState {
        PREPARE,
        ANALYSISING,
        SUCCESS,
        FAIL;

    }
}

