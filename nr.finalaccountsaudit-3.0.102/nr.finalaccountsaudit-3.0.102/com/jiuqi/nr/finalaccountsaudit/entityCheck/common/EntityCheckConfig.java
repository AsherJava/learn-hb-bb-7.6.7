/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import java.io.Serializable;
import java.sql.Clob;
import java.sql.Timestamp;
import org.springframework.stereotype.Repository;

@Repository
@DBAnno.DBTable(dbTable="SYS_HSHD_CONFIG")
public class EntityCheckConfig
implements Serializable {
    @DBAnno.DBField(dbField="CONFIG_KEY", dbType=String.class, isPk=false)
    public String config_key;
    @DBAnno.DBField(dbField="TSK_KEY", dbType=String.class, isPk=false)
    public String tsk_key;
    @DBAnno.DBField(dbField="FC_KEY", dbType=String.class, isPk=false)
    public String fc_key;
    @DBAnno.DBField(dbField="TYPE", dbType=String.class, isPk=false)
    public String type;
    @DBAnno.DBField(dbField="TYPEST2", dbType=String.class, isPk=false)
    public String typest2;
    @DBAnno.DBField(dbField="DATA", tranWith="transBytes", dbType=Clob.class, isPk=false)
    public String data;
    @DBAnno.DBField(dbField="UPDATEDATE", dbType=Timestamp.class, isPk=false)
    public String updatedate;
    @DBAnno.DBField(dbField="STRDATA", dbType=String.class, isPk=false)
    public String strdata;

    public String getConfig_key() {
        return this.config_key;
    }

    public void setConfig_key(String config_key) {
        this.config_key = config_key;
    }

    public String getTsk_key() {
        return this.tsk_key;
    }

    public void setTsk_key(String tsk_key) {
        this.tsk_key = tsk_key;
    }

    public String getFc_key() {
        return this.fc_key;
    }

    public void setFc_key(String fc_key) {
        this.fc_key = fc_key;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypest2() {
        return this.typest2;
    }

    public void setTypest2(String typest2) {
        this.typest2 = typest2;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUpdatedate() {
        return this.updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getStrdata() {
        return this.strdata;
    }

    public void setStrdata(String strdata) {
        this.strdata = strdata;
    }
}

