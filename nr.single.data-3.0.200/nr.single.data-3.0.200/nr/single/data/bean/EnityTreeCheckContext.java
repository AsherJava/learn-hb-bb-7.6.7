/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.single.core.para.parser.table.FMRepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.TableTypeType
 */
package nr.single.data.bean;

import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.para.parser.table.TableTypeType;
import java.util.Map;

public class EnityTreeCheckContext {
    private FMRepInfo fmdmInfo = null;
    private Map<String, IEntityAttribute> fieldMap = null;
    public static final String JCHB_BBLX = "JCHB";
    public static final String JCHB_BBLX2 = "2345";

    public final String getDWDMField() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.getDWDMFieldName();
        }
        return "QYDM";
    }

    public final String getDWDMField2() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.getDWDMFieldName();
        }
        return "DWDM";
    }

    public final String getDWMCField() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.getDWMCFieldName();
        }
        return "title";
    }

    public String getBBLXField() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.getBBLXField();
        }
        return "BBLX";
    }

    public String getXJHSField() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.getXJHSField();
        }
        return "XJHS";
    }

    public String getSJDMField() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.getSJDMField();
        }
        return "SJDM";
    }

    public String getZBDMField() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.getZBDMField();
        }
        return "JTDM";
    }

    public String getSNDMField() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.getSNDMField();
        }
        return "SNDM";
    }

    public String getXBYSField() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.getXBYSField();
        }
        return "XBYS";
    }

    public String getPeriodField() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.getPeriodField();
        }
        return "";
    }

    public String getLevelField() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.getLevelField();
        }
        return "LEVEL";
    }

    public boolean getDoJTTreeMaintain() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.getDoJTTreeMaintain();
        }
        if (this.fieldMap != null) {
            return this.fieldMap.containsKey(this.getZBDMField()) && this.fieldMap.containsKey(this.getSJDMField());
        }
        return true;
    }

    public String getBZHZBBLX() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.returnTableTypeCode(TableTypeType.TTT_BZHZ);
        }
        return "7";
    }

    public String getYBHZBBLX() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.returnTableTypeCode(TableTypeType.TTT_YBHZ);
        }
        return "H";
    }

    public String getBZCEBBLX() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.returnTableTypeCode(TableTypeType.TTT_BZCE);
        }
        return "BZCE";
    }

    public String getJCFHBBLX() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.returnTableTypeCode(TableTypeType.TTT_JCFH);
        }
        return "0";
    }

    public String getJTHZBBLX() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.returnTableTypeCode(TableTypeType.TTT_JTHZ);
        }
        return "9";
    }

    public String getJTCEBBLX() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.returnTableTypeCode(TableTypeType.TTT_JTCE);
        }
        return "1";
    }

    public String getFJBBLX() {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.returnTableTypeCode(TableTypeType.TTT_FJ);
        }
        return "FJ";
    }

    public boolean isJCHBBLX(String bblx) {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.returnTableType(bblx) == TableTypeType.TTT_JCHB;
        }
        return JCHB_BBLX.equalsIgnoreCase(bblx) || JCHB_BBLX2.contains(bblx);
    }

    public boolean isFJBBLX(String bblx) {
        if (this.fmdmInfo != null) {
            return this.fmdmInfo.returnTableType(bblx) == TableTypeType.TTT_FJ;
        }
        return "FJ".equalsIgnoreCase(bblx);
    }

    public FMRepInfo getFmdmInfo() {
        return this.fmdmInfo;
    }

    public Map<String, IEntityAttribute> getFieldMap() {
        return this.fieldMap;
    }

    public void setFmdmInfo(FMRepInfo fmdmInfo) {
        this.fmdmInfo = fmdmInfo;
    }

    public void setFieldMap(Map<String, IEntityAttribute> fieldMap) {
        this.fieldMap = fieldMap;
    }
}

