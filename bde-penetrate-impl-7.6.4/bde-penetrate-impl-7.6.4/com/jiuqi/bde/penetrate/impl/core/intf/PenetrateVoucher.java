/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.penetrate.impl.core.intf;

import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.common.base.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class PenetrateVoucher
extends HashMap<String, Object> {
    private static final long serialVersionUID = 519819040245230322L;

    public Integer getRowType() {
        Object rowType = this.get("ROWTYPE");
        if (rowType != null) {
            if (rowType instanceof Integer) {
                return (Integer)rowType;
            }
            return Integer.parseInt(rowType.toString());
        }
        return null;
    }

    public void setRowType(Integer rowType) {
        this.put("ROWTYPE", rowType);
    }

    public Integer getAcctYear() {
        Object val = this.get("ACCTYEAR");
        if (val != null) {
            if (val instanceof Integer) {
                return (Integer)val;
            }
            return Integer.parseInt(val.toString());
        }
        return null;
    }

    public void setAcctYear(Integer val) {
        this.put("ACCTYEAR", val);
    }

    public Integer getAcctPeriod() {
        Object val = this.get("ACCTPERIOD");
        if (val != null) {
            if (val instanceof Integer) {
                return (Integer)val;
            }
            return Integer.parseInt(val.toString());
        }
        return null;
    }

    public void setAcctPeriod(Integer val) {
        this.put("ACCTPERIOD", val);
    }

    public Integer getAcctDay() {
        Object val = this.get("ACCTDAY");
        if (val != null) {
            if (val instanceof Integer) {
                return (Integer)val;
            }
            return Integer.parseInt(val.toString());
        }
        return null;
    }

    public void setAcctDay(Integer val) {
        this.put("ACCTDAY", val);
    }

    public String getSubjectCode() {
        return (String)this.get("SUBJECTCODE");
    }

    public void setSubjectCode(String subjectCode) {
        this.put("SUBJECTCODE", subjectCode);
    }

    public String getSubjectName() {
        return (String)this.get("SUBJECTNAME");
    }

    public void setSubjectName(String subjectName) {
        this.put("SUBJECTNAME", subjectName);
    }

    public String getDigest() {
        return (String)this.get("DIGEST");
    }

    public void setDigest(String subjectName) {
        this.put("DIGEST", subjectName);
    }

    public BigDecimal getDebit() {
        Object val = this.get("DEBIT");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setDebit(BigDecimal val) {
        this.put("DEBIT", val);
    }

    public BigDecimal getCredit() {
        Object val = this.get("CREDIT");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setCredit(BigDecimal val) {
        this.put("CREDIT", val);
    }

    public BigDecimal getOrgnd() {
        Object val = this.get("ORGND");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setOrgnd(BigDecimal val) {
        this.put("ORGND", val);
    }

    public BigDecimal getOrgnc() {
        Object val = this.get("ORGNC");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setOrgnc(BigDecimal val) {
        this.put("ORGNC", val);
    }

    public BigDecimal getYe() {
        Object val = this.get("YE");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setYe(BigDecimal val) {
        this.put("YE", val);
    }

    public Integer getYeOrient() {
        Object val = this.get("YE_ORIENT");
        if (val != null) {
            if (val instanceof Integer) {
                return (Integer)val;
            }
            return Integer.parseInt(val.toString());
        }
        return null;
    }

    public void setYeOrient(Integer val) {
        this.put("YE_ORIENT", val);
    }

    public BigDecimal getOrgnYe() {
        Object val = this.get("ORGNYE");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setOrgnYe(BigDecimal val) {
        this.put("ORGNYE", val);
    }

    public List<Dimension> getAssTypeList() {
        Object object = this.get("ASSTYPELIST");
        if (object == null) {
            return null;
        }
        if (object instanceof List) {
            return (List)object;
        }
        return CollectionUtils.newArrayList();
    }

    public void setAssTypeList(List<Dimension> assTypeList) {
        this.put("ASSTYPELIST", assTypeList);
    }
}

