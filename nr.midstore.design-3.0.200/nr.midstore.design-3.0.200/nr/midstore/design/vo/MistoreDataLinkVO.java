/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.DataLinkType
 */
package nr.midstore.design.vo;

import com.jiuqi.nr.definition.common.DataLinkType;
import nr.midstore.design.domain.CommonParamDTO;
import nr.midstore.design.vo.MistoreFieldVO;

public class MistoreDataLinkVO
extends CommonParamDTO {
    private int posX;
    private int posY;
    private int colNum;
    private int rowNum;
    private DataLinkType type;
    private String linkExpression;
    private MistoreFieldVO field;
    private String regionKey;
    private String formKey;

    public int getColNum() {
        return this.colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public int getRowNum() {
        return this.rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String getLinkExpression() {
        return this.linkExpression;
    }

    public void setLinkExpression(String linkExpression) {
        this.linkExpression = linkExpression;
    }

    public MistoreFieldVO getField() {
        return this.field;
    }

    public void setField(MistoreFieldVO field) {
        this.field = field;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public DataLinkType getType() {
        return this.type;
    }

    public void setType(DataLinkType type) {
        this.type = type;
    }
}

