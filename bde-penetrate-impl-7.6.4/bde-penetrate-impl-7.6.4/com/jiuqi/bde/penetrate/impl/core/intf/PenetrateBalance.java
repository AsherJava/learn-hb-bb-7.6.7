/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.penetrate.impl.core.intf;

import java.math.BigDecimal;
import java.util.HashMap;

public class PenetrateBalance
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

    public String getSumKey() {
        return (String)this.get("SUMKEY");
    }

    public void setSumKey(String sumKey) {
        this.put("SUMKEY", sumKey);
    }

    public String getSubjectCode() {
        return (String)this.get("SUBJECTCODE");
    }

    public void setSubjectCode(String subjectCode) {
        this.put("SUBJECTCODE", subjectCode);
    }

    public String getCashCode() {
        return (String)this.get("CASHCODE");
    }

    public void setCashCode(String cashCode) {
        this.put("CASHCODE", cashCode);
    }

    public String getCashName() {
        return (String)this.get("CASHNAME");
    }

    public void setCashName(String cashCode) {
        this.put("\u73b0\u6d41\u540d\u79f0", cashCode);
    }

    public String getSubjectName() {
        return (String)this.get("SUBJECTNAME");
    }

    public void setSubjectName(String subjectName) {
        this.put("SUBJECTNAME", subjectName);
    }

    public BigDecimal getNc() {
        Object val = this.get("NC");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setNc(BigDecimal val) {
        this.put("NC", val);
    }

    public String getNcOrient() {
        return (String)this.get("NCORIENT");
    }

    public void setNcOrient(String val) {
        this.put("NCORIENT", val);
    }

    public BigDecimal getOrgnNc() {
        Object val = this.get("ORGNNC");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setOrgnNc(BigDecimal val) {
        this.put("ORGNNC", val);
    }

    public BigDecimal getQc() {
        Object val = this.get("QC");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setQc(BigDecimal val) {
        this.put("QC", val);
    }

    public String getQcOrient() {
        return (String)this.get("QCORIENT");
    }

    public void setQcOrient(String val) {
        this.put("QCORIENT", val);
    }

    public BigDecimal getOrgnQc() {
        Object val = this.get("ORGNQC");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setOrgnQc(BigDecimal val) {
        this.put("ORGNQC", val);
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

    public BigDecimal getDsum() {
        Object val = this.get("DSUM");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setDsum(BigDecimal val) {
        this.put("DSUM", val);
    }

    public BigDecimal getCsum() {
        Object val = this.get("CSUM");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setCsum(BigDecimal val) {
        this.put("CSUM", val);
    }

    public BigDecimal getOrgnDsum() {
        Object val = this.get("ORGNDSUM");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setOrgnDsum(BigDecimal val) {
        this.put("ORGNDSUM", val);
    }

    public BigDecimal getOrgnCsum() {
        Object val = this.get("ORGNCSUM");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setOrgnCsum(BigDecimal val) {
        this.put("ORGNCSUM", val);
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

    public String getYeOrient() {
        return (String)this.get("YE_ORIENT");
    }

    public void setYeOrient(String val) {
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

    public BigDecimal getBqNum() {
        Object val = this.get("BQNUM");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setBqNum(BigDecimal val) {
        this.put("BQNUM", val);
    }

    public BigDecimal getLjNum() {
        Object val = this.get("LJNUM");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setLjNum(BigDecimal val) {
        this.put("LJNUM", val);
    }

    public BigDecimal getWbqNum() {
        Object val = this.get("WBQNUM");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setWbqNum(BigDecimal val) {
        this.put("WBQNUM", val);
    }

    public BigDecimal getWljNum() {
        Object val = this.get("WLJNUM");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setWljNum(BigDecimal val) {
        this.put("WLJNUM", val);
    }

    public BigDecimal getJnc() {
        Object val = this.get("JNC");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setJnc(BigDecimal val) {
        this.put("JNC", val);
    }

    public BigDecimal getDnc() {
        Object val = this.get("DNC");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setDnc(BigDecimal val) {
        this.put("DNC", val);
    }

    public BigDecimal getJyh() {
        Object val = this.get("JYH");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setJyh(BigDecimal val) {
        this.put("JYH", val);
    }

    public BigDecimal getDyh() {
        Object val = this.get("DYH");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setDyh(BigDecimal val) {
        this.put("DYH", val);
    }

    public BigDecimal getWjnc() {
        Object val = this.get("WJNC");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setWjnc(BigDecimal val) {
        this.put("WJNC", val);
    }

    public BigDecimal getWdnc() {
        Object val = this.get("WDNC");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setWdnc(BigDecimal val) {
        this.put("WDNC", val);
    }

    public BigDecimal getWjyh() {
        Object val = this.get("WJYH");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setWjyh(BigDecimal val) {
        this.put("WJYH", val);
    }

    public BigDecimal getWdyh() {
        Object val = this.get("WDYH");
        if (val != null) {
            if (val instanceof BigDecimal) {
                return (BigDecimal)val;
            }
            return new BigDecimal(val.toString());
        }
        return BigDecimal.ZERO;
    }

    public void setWdyh(BigDecimal val) {
        this.put("WDYH", val);
    }
}

