/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.dataentry.paramInfo.DimensionValueFormInfo;
import com.jiuqi.nr.dataentry.paramInfo.NoAccessFormInfo;
import com.jiuqi.nr.dataentry.paramInfo.NoAccessUtilInfo;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BatchDimensionValueFormInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private JtableContext jtableContext;
    private List<String> forms;
    private Consts.FormAccessLevel formAccessLevel;
    private List<DimensionValueFormInfo> accessFormInfos = new ArrayList<DimensionValueFormInfo>();
    private List<NoAccessFormInfo> noAccessReasons = new ArrayList<NoAccessFormInfo>();
    private List<NoAccessUtilInfo> noAccessUtilReasons = new ArrayList<NoAccessUtilInfo>();

    public JtableContext getJtableContext() {
        return this.jtableContext;
    }

    public void setJtableContext(JtableContext jtableContext) {
        this.jtableContext = jtableContext;
    }

    public List<String> getForms() {
        return this.forms;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }

    public Consts.FormAccessLevel getFormAccessLevel() {
        return this.formAccessLevel;
    }

    public void setFormAccessLevel(Consts.FormAccessLevel formAccessLevel) {
        this.formAccessLevel = formAccessLevel;
    }

    public List<DimensionValueFormInfo> getAccessFormInfos() {
        return this.accessFormInfos;
    }

    public void setAccessFormInfos(List<DimensionValueFormInfo> accessFormInfos) {
        this.accessFormInfos = accessFormInfos;
    }

    public List<NoAccessFormInfo> getNoAccessReasons() {
        return this.noAccessReasons;
    }

    public void setNoAccessReasons(List<NoAccessFormInfo> noAccessReasons) {
        this.noAccessReasons = noAccessReasons;
    }

    public List<NoAccessUtilInfo> getNoAccessUtilReasons() {
        return this.noAccessUtilReasons;
    }

    public void setNoAccessUtilReasons(List<NoAccessUtilInfo> noAccessUtilReasons) {
        this.noAccessUtilReasons = noAccessUtilReasons;
    }
}

