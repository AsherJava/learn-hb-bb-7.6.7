/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xlib.utils.StringUtils
 */
package nr.single.para.parain.util;

import com.jiuqi.xlib.utils.StringUtils;

public class SingleSchemePeriodObj {
    public static final String PERIOD_SPLIT = "-";
    private String scheme;
    private String start;
    private String end;

    public SingleSchemePeriodObj() {
    }

    public SingleSchemePeriodObj(String scheme, String start, String end) {
        this.scheme = scheme;
        this.start = start;
        this.end = end;
    }

    public SingleSchemePeriodObj(String scheme, String periodRange) {
        this.scheme = scheme;
        if (StringUtils.hasLength((String)periodRange)) {
            String[] split = periodRange.split(PERIOD_SPLIT);
            this.start = split[0];
            this.end = split[1];
        }
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public SingleSchemePeriodObj copy() {
        SingleSchemePeriodObj obj = new SingleSchemePeriodObj();
        obj.setScheme(this.scheme);
        obj.setStart(this.start);
        obj.setEnd(this.end);
        return obj;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.end == null ? 0 : this.end.hashCode());
        result = 31 * result + (this.scheme == null ? 0 : this.scheme.hashCode());
        result = 31 * result + (this.start == null ? 0 : this.start.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        SingleSchemePeriodObj other = (SingleSchemePeriodObj)obj;
        if (this.end == null ? other.end != null : !this.end.equals(other.end)) {
            return false;
        }
        if (this.scheme == null ? other.scheme != null : !this.scheme.equals(other.scheme)) {
            return false;
        }
        return !(this.start == null ? other.start != null : !this.start.equals(other.start));
    }

    public String toString() {
        return "SchemePeriodObj [scheme=" + this.scheme + ", start=" + this.start + ", end=" + this.end + "]";
    }
}

