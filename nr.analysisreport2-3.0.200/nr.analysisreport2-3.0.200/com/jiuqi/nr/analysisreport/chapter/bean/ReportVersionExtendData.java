/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.analysisreport.chapter.bean;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import java.io.Serializable;
import java.sql.Clob;

@DBAnno.DBTable(dbTable="SYS_ANALYVERSION_EXTEND")
public class ReportVersionExtendData
implements Serializable {
    private static final long serialVersionUID = 536461854042388038L;
    @DBAnno.DBField(dbField="AV_KEY", isPk=true)
    private String avKey;
    @DBAnno.DBField(dbField="AV_EXT_DATA", dbType=Clob.class, appType=String.class)
    private String avExtData;

    public String getAvKey() {
        return this.avKey;
    }

    public void setAvKey(String avKey) {
        this.avKey = avKey;
    }

    public String getAvExtData() {
        return this.avExtData;
    }

    public void setAvExtData(String avExtData) {
        this.avExtData = avExtData;
    }
}

