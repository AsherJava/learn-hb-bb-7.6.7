/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.bd.bill.domain;

import java.util.List;

public class MapInfoDTO {
    public String tablename;
    public String tabletitle;
    public String srctablename;
    public String srctabletitle;
    public boolean master;
    public List<Filedmap> filedmaps;

    public String getTablename() {
        return this.tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getTabletitle() {
        return this.tabletitle;
    }

    public void setTabletitle(String tabletitle) {
        this.tabletitle = tabletitle;
    }

    public String getSrctablename() {
        return this.srctablename;
    }

    public void setSrctablename(String srctablename) {
        this.srctablename = srctablename;
    }

    public String getSrctabletitle() {
        return this.srctabletitle;
    }

    public void setSrctabletitle(String srctabletitle) {
        this.srctabletitle = srctabletitle;
    }

    public boolean isMaster() {
        return this.master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public List<Filedmap> getFiledmaps() {
        return this.filedmaps;
    }

    public void setFiledmaps(List<Filedmap> filedmaps) {
        this.filedmaps = filedmaps;
    }

    public static class Filedmap {
        public String columntype;
        public String filedname;
        public String filedtitle;
        public String srccolumntype;
        public String srcfiledname;
        public String srcfiledtitle;

        public String getColumntype() {
            return this.columntype;
        }

        public void setColumntype(String columntype) {
            this.columntype = columntype;
        }

        public String getFiledname() {
            return this.filedname;
        }

        public void setFiledname(String filedname) {
            this.filedname = filedname;
        }

        public String getFiledtitle() {
            return this.filedtitle;
        }

        public void setFiledtitle(String filedtitle) {
            this.filedtitle = filedtitle;
        }

        public String getSrccolumntype() {
            return this.srccolumntype;
        }

        public void setSrccolumntype(String srccolumntype) {
            this.srccolumntype = srccolumntype;
        }

        public String getSrcfiledname() {
            return this.srcfiledname;
        }

        public void setSrcfiledname(String srcfiledname) {
            this.srcfiledname = srcfiledname;
        }

        public String getSrcfiledtitle() {
            return this.srcfiledtitle;
        }

        public void setSrcfiledtitle(String srcfiledtitle) {
            this.srcfiledtitle = srcfiledtitle;
        }
    }
}

