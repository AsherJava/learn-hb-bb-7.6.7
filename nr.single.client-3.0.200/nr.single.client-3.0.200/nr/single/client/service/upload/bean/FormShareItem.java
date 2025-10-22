/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package nr.single.client.service.upload.bean;

import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class FormShareItem {
    private String code;
    private Set<String> shareNetTables;
    private Set<String> shareSingleTables;
    private Map<String, FormDefine> forms;

    public void add(FormDefine form, Set<String> netTables, Set<String> singleTables) {
        if (!this.getForms().containsKey(form.getKey())) {
            this.getForms().put(form.getKey(), form);
        }
        if (netTables != null) {
            for (String netTableKey : netTables) {
                if (this.getShareNetTables().contains(netTableKey)) continue;
                this.getShareNetTables().add(netTableKey);
            }
        }
        if (singleTables != null) {
            for (String singleTableKey : singleTables) {
                if (this.getShareSingleTables().contains(singleTableKey)) continue;
                this.getShareSingleTables().add(singleTableKey);
            }
        }
    }

    public Set<String> getShareNetTables() {
        if (this.shareNetTables == null) {
            this.shareNetTables = new HashSet<String>();
        }
        return this.shareNetTables;
    }

    public void setShareNetTables(Set<String> shareNetTables) {
        this.shareNetTables = shareNetTables;
    }

    public Set<String> getShareSingleTables() {
        if (this.shareSingleTables == null) {
            this.shareSingleTables = new HashSet<String>();
        }
        return this.shareSingleTables;
    }

    public void setShareSingleTables(Set<String> shareSingleTables) {
        this.shareSingleTables = shareSingleTables;
    }

    public Map<String, FormDefine> getForms() {
        if (this.forms == null) {
            this.forms = new LinkedHashMap<String, FormDefine>();
        }
        return this.forms;
    }

    public void setForms(Map<String, FormDefine> forms) {
        this.forms = forms;
    }

    public void merge(FormShareItem item) {
        for (String tableFlag : item.getShareNetTables()) {
            if (this.getShareNetTables().contains(tableFlag)) continue;
            this.getShareNetTables().add(tableFlag);
        }
        for (String tableFlag : item.getShareSingleTables()) {
            if (this.getShareSingleTables().contains(tableFlag)) continue;
            this.getShareSingleTables().add(tableFlag);
        }
        for (String formKey : item.getForms().keySet()) {
            if (this.getForms().containsKey(formKey)) continue;
            this.getForms().put(formKey, item.getForms().get(formKey));
        }
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

