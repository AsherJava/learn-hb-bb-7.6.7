/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.params.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CSVRange
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, FormRange> forms;

    public Map<String, FormRange> getForms() {
        return this.forms;
    }

    public void setForms(Map<String, FormRange> forms) {
        this.forms = forms;
    }

    public CSVRange(Map<String, FormRange> forms) {
        this.forms = forms;
    }

    private List<String> searchDwInForm(String form) {
        if (this.forms.get(form) == null) {
            return null;
        }
        return this.forms.get(form).getDwList();
    }

    public List<String> searchDwInForm(String formCode, String formKey) {
        List<String> searchDwInForm = this.searchDwInForm(formCode);
        if (searchDwInForm == null) {
            searchDwInForm = this.searchDwInForm(formKey);
        }
        if (searchDwInForm == null) {
            searchDwInForm = new ArrayList<String>();
        }
        return searchDwInForm;
    }

    private List<String> searchDwInAdjust(String form, String adjust) {
        if (this.forms.get(form) == null) {
            return null;
        }
        return this.forms.get(form).getAdjustDwList().get(adjust);
    }

    public List<String> searchAdjustInForm(String formCode, String formKey, String adjust) {
        List<String> searchAdjustInForm = this.searchDwInAdjust(formCode, adjust);
        if (searchAdjustInForm == null) {
            searchAdjustInForm = this.searchDwInAdjust(formKey, adjust);
        }
        return searchAdjustInForm;
    }

    public boolean contains(String form) {
        return this.forms.get(form) != null;
    }

    public static class FormRange
    implements Serializable {
        private static final long serialVersionUID = 1L;
        public static String EmpAdjust = "E";
        private Map<String, List<String>> adjustDwList;
        @Deprecated
        private List<String> adjusts;

        public Map<String, List<String>> getAdjustDwList() {
            return this.adjustDwList;
        }

        public void setAdjustDwList(Map<String, List<String>> adjustDwList) {
            this.adjustDwList = adjustDwList;
        }

        @Deprecated
        public List<String> getAdjusts() {
            return this.adjusts;
        }

        @Deprecated
        public void setAdjusts(List<String> adjusts) {
            this.adjusts = adjusts;
        }

        public List<String> getDwList() {
            return this.adjustDwList.get(EmpAdjust);
        }

        public boolean isAdjust() {
            return this.adjustDwList.get(EmpAdjust) == null;
        }
    }
}

