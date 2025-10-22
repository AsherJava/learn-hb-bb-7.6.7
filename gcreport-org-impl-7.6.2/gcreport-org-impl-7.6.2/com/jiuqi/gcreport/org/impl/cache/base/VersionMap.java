/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 */
package com.jiuqi.gcreport.org.impl.cache.base;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import java.util.Date;
import java.util.HashMap;

public class VersionMap
extends HashMap<VersionMapPutKey, OrgVersionVO> {
    private static final long serialVersionUID = 1L;

    public OrgVersionVO put(OrgVersionVO value) {
        return super.put(new VersionMapPutKey(value), value);
    }

    @Override
    public OrgVersionVO get(Object key) {
        return (OrgVersionVO)super.get(new VersionMapGetKey(key));
    }

    class VersionMapPutKey {
        public Date validtime;
        private Date invalidtime;
        private String title;

        public VersionMapPutKey(OrgVersionVO v) {
            this.invalidtime = v.getInvalidTime();
            this.validtime = v.getValidTime();
            this.title = v.getTitle();
        }

        public Date getInvalidtime() {
            return this.invalidtime;
        }

        public String getTitle() {
            return this.title;
        }

        public Date getValidtime() {
            return this.validtime;
        }

        public void setInvalidtime(Date invalidtime) {
            this.invalidtime = invalidtime;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setValidtime(Date validtime) {
            this.validtime = validtime;
        }

        public boolean equals(Object obj) {
            if (obj instanceof VersionMapGetKey) {
                VersionMapGetKey o = (VersionMapGetKey)obj;
                if (!StringUtils.isNull((String)this.getTitle()) && this.getTitle().equals(((VersionMapPutKey)obj).getTitle())) {
                    return true;
                }
                if (o.getVerDate() != null && this.getInvalidtime() != null && this.getValidtime() != null && !o.getVerDate().before(this.getValidtime()) && o.getVerDate().before(this.getInvalidtime())) {
                    return true;
                }
            }
            return super.equals(obj);
        }

        public int hashCode() {
            return 0;
        }
    }

    class VersionMapGetKey {
        public Date verDate;
        private String title;

        public VersionMapGetKey(Object key) {
            if (key != null) {
                if (key instanceof Date) {
                    this.verDate = (Date)key;
                } else {
                    this.title = key.toString();
                }
            }
        }

        public String getTitle() {
            return this.title;
        }

        public Date getVerDate() {
            return this.verDate;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setVerDate(Date verDate) {
            this.verDate = verDate;
        }

        public boolean equals(Object obj) {
            if (obj instanceof VersionMapPutKey) {
                VersionMapPutKey o = (VersionMapPutKey)obj;
                if (!StringUtils.isNull((String)this.getTitle()) && this.getTitle().equals(((VersionMapPutKey)obj).getTitle())) {
                    return true;
                }
                if (this.getVerDate() != null && o.getInvalidtime() != null && o.getValidtime() != null && !this.getVerDate().before(o.getValidtime()) && this.getVerDate().before(o.getInvalidtime())) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return 0;
        }
    }
}

