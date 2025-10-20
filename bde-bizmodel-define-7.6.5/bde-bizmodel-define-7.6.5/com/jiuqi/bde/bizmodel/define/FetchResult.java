/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ColumnTypeEnum
 *  com.jiuqi.bde.common.constant.SignTypeEnum
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.common.base.util.NumberUtils
 */
package com.jiuqi.bde.bizmodel.define;

import com.jiuqi.bde.common.constant.ColumnTypeEnum;
import com.jiuqi.bde.common.constant.SignTypeEnum;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.common.base.util.NumberUtils;
import java.io.Serializable;
import java.math.BigDecimal;

public class FetchResult
implements Serializable {
    private static final long serialVersionUID = -289714133618085509L;
    private Integer floatOrder;
    private String fieldDefineId;
    private String fetchSettingId;
    private Integer sign;
    private String zbValueStr;
    private BigDecimal zbValueNum;
    private ColumnTypeEnum zbValueType;

    public FetchResult() {
    }

    public FetchResult(ExecuteSettingVO executeSettingVO) {
        this.floatOrder = executeSettingVO.getFloatOrder();
        this.fieldDefineId = executeSettingVO.getFieldDefineId();
        this.fetchSettingId = executeSettingVO.getId();
        this.sign = SignTypeEnum.typeof((String)executeSettingVO.getSign()).getOrient();
        this.zbValueStr = "";
        this.zbValueType = BdeCommonUtil.fieldDefineTypeIsNum((Integer)executeSettingVO.getFieldDefineType()) ? ColumnTypeEnum.NUMBER : ColumnTypeEnum.STRING;
        this.zbValueNum = BigDecimal.ZERO;
    }

    public void addZbValue(BigDecimal plusValue) {
        this.zbValueNum = NumberUtils.add((BigDecimal)this.zbValueNum, (BigDecimal[])new BigDecimal[]{plusValue});
    }

    public Integer getFloatOrder() {
        return this.floatOrder;
    }

    public void setFloatOrder(Integer floatOrder) {
        this.floatOrder = floatOrder;
    }

    public String getFieldDefineId() {
        return this.fieldDefineId;
    }

    public void setFieldDefineId(String fieldDefineId) {
        this.fieldDefineId = fieldDefineId;
    }

    public String getFetchSettingId() {
        return this.fetchSettingId;
    }

    public void setFetchSettingId(String fetchSettingId) {
        this.fetchSettingId = fetchSettingId;
    }

    public Integer getSign() {
        return this.sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public String getZbValue() {
        if (this.zbValueType == null) {
            return this.zbValueNum.toString();
        }
        return this.zbValueType == ColumnTypeEnum.STRING ? this.zbValueStr : this.zbValueNum.toString();
    }

    public void setZbValue(String zbValue) {
        this.zbValueStr = zbValue;
    }

    public void setZbValue(BigDecimal zbValue) {
        this.zbValueNum = zbValue;
    }

    public ColumnTypeEnum getZbValueType() {
        return this.zbValueType;
    }

    public void setZbValueType(ColumnTypeEnum zbValueType) {
        this.zbValueType = zbValueType;
    }

    public String toString() {
        return "FetchResult [floatOrder=" + this.floatOrder + ", fieldDefineId=" + this.fieldDefineId + ", fetchSettingId=" + this.fetchSettingId + ", sign=" + this.sign + ", zbValueStr=" + this.zbValueStr + ", zbValueNum=" + this.zbValueNum + ", zbValueType=" + this.zbValueType + "]";
    }
}

