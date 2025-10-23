/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.task.form.formcopy.bean.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.task.form.formcopy.bean.IFormCopyDataModelInfo;

@DBAnno.DBTable(dbTable="NR_PARAM_COPY_DATAMODEL_INFO")
public class FormCopyDataModelInfoImpl
implements IFormCopyDataModelInfo {
    public static final String CLZ_FIELD_DATAFIELDKEY = "dataFieldKey";
    public static final String CLZ_FIELD_DATATABLEKEY = "dataTableKey";
    public static final String CLZ_FIELD_DATASCHEMEKEY = "dataSchemeKey";
    public static final String CLZ_FIELD_SRCDATAFIELDKEY = "srcDataFieldKey";
    public static final String CLZ_FIELD_SRCDATATABLEKEY = "srcDataTableKey";
    private static final long serialVersionUID = 4473461279360049551L;
    @DBAnno.DBField(dbField="CDMI_DF_KEY", isPk=true)
    private String dataFieldKey;
    @DBAnno.DBField(dbField="CDMI_DT_KEY")
    private String dataTableKey;
    @DBAnno.DBField(dbField="CDMI_DS_KEY")
    private String dataSchemeKey;
    @DBAnno.DBField(dbField="CDMI_SRC_DF_KEY")
    private String srcDataFieldKey;
    @DBAnno.DBField(dbField="CDMI_SRC_DT_KEY")
    private String srcDataTableKey;

    @Override
    public String getDataFieldKey() {
        return this.dataFieldKey;
    }

    @Override
    public void setDataFieldKey(String dataFieldKey) {
        this.dataFieldKey = dataFieldKey;
    }

    @Override
    public String getDataTableKey() {
        return this.dataTableKey;
    }

    @Override
    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    @Override
    public String getSrcDataFieldKey() {
        return this.srcDataFieldKey;
    }

    @Override
    public void setSrcDataFieldKey(String srcDataFieldKey) {
        this.srcDataFieldKey = srcDataFieldKey;
    }

    @Override
    public String getSrcDataTableKey() {
        return this.srcDataTableKey;
    }

    @Override
    public void setSrcDataTableKey(String srcDataTableKey) {
        this.srcDataTableKey = srcDataTableKey;
    }

    @Override
    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    @Override
    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }
}

