/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package nr.midstore2.design.vo;

import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import nr.midstore2.design.domain.CommonParamDTO;

public class MistoreFieldVO
extends CommonParamDTO {
    private DataFieldType fieldType;
    private int precisiion;
    private int decimal;
    private String tableKey;
    private String tableCode;
    private String tableTitle;
    private int linkType;
    private String linkKey;

    public DataFieldType getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(DataFieldType fieldType) {
        this.fieldType = fieldType;
    }

    public int getPrecisiion() {
        return this.precisiion;
    }

    public void setPrecisiion(int precisiion) {
        this.precisiion = precisiion;
    }

    public int getDecimal() {
        return this.decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public int getLinkType() {
        return this.linkType;
    }

    public void setLinkType(int linkType) {
        this.linkType = linkType;
    }

    public String getLinkKey() {
        return this.linkKey;
    }

    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getTableTitle() {
        return this.tableTitle;
    }

    public void setTableTitle(String tableTitle) {
        this.tableTitle = tableTitle;
    }
}

