/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.EnumLinkageSettingDefine;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DBAnno.DBTable(dbTable="sys_enumlinkage")
public class RunTimeEnumLinkageSettingDefineImpl
implements EnumLinkageSettingDefine {
    @DBAnno.DBField(dbField="el_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="el_title")
    private String title;
    @DBAnno.DBField(dbField="el_order")
    private String order;
    @DBAnno.DBField(dbField="el_veelion")
    private String version;
    @DBAnno.DBField(dbField="el_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="el_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, notUpdate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="el_master_link")
    private String masterLink;
    @DBAnno.DBField(dbField="el_slave_Links", set="setslaveLinksDB", get="getslaveLinksDB")
    private List<String> slaveLinks;
    @DBAnno.DBField(dbField="el_show_links", set="setshowLinksDB", get="getshowLinksDB")
    private List<String> showLinks;

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return this.version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public String getMasterLink() {
        return this.masterLink;
    }

    @Override
    public List<String> getSlaveLinks() {
        return this.slaveLinks;
    }

    @Override
    public List<String> getShowLinks() {
        return this.showLinks;
    }

    public String getSlaveLinksDB() {
        String aCode = "";
        if (this.slaveLinks != null) {
            for (int i = 0; i < this.slaveLinks.size(); ++i) {
                aCode = i > 0 ? aCode + ";" + this.slaveLinks.get(i).toString() : this.slaveLinks.get(i).toString();
            }
        }
        return aCode;
    }

    public void setSlaveLinksDB(String fields) {
        ArrayList<String> keys = new ArrayList<String>();
        if (fields != null) {
            String[] list = fields.split(";");
            for (int i = 0; i < list.length; ++i) {
                keys.add(list[i]);
            }
        }
        this.slaveLinks = keys;
    }

    public String getShowLinksDB() {
        String aCode = "";
        if (this.showLinks != null) {
            for (int i = 0; i < this.showLinks.size(); ++i) {
                aCode = i > 0 ? aCode + ";" + this.showLinks.get(i).toString() : this.showLinks.get(i).toString();
            }
        }
        return aCode;
    }

    public void setShowLinksDB(String fields) {
        ArrayList<String> keys = new ArrayList<String>();
        if (fields != null) {
            String[] list = fields.split(";");
            for (int i = 0; i < list.length; ++i) {
                keys.add(list[i]);
            }
        }
        this.showLinks = keys;
    }
}

