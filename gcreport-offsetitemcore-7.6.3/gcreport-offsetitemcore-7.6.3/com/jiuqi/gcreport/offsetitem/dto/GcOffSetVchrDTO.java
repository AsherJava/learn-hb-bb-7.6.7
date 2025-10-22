/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.offsetitem.dto;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import java.util.List;
import javax.validation.constraints.NotNull;

public class GcOffSetVchrDTO {
    @NotNull
    private String mrecid;
    private String vchrCode;
    private boolean needDelete;
    private boolean allowMoreUnit;
    private String currentOrgType;
    private List<GcOffSetVchrItemDTO> items;
    private String consFormulaCalcType;

    public GcOffSetVchrDTO() {
        this.mrecid = UUIDOrderSnowUtils.newUUIDStr();
    }

    public GcOffSetVchrDTO(String mrecid) {
        this.mrecid = mrecid;
    }

    public static GcOffSetVchrDTO getNotNeedDeleteVchr(String mrecid) {
        GcOffSetVchrDTO offSetVchr = new GcOffSetVchrDTO();
        offSetVchr.mrecid = mrecid;
        return offSetVchr;
    }

    public String getMrecid() {
        return this.mrecid;
    }

    public void setMrecid(String mrecid) {
        this.setNeedDelete(true);
        this.mrecid = mrecid;
    }

    public void onlySetMrecid(String mrecid) {
        this.mrecid = mrecid;
    }

    public List<GcOffSetVchrItemDTO> getItems() {
        return this.items;
    }

    public void setItems(List<GcOffSetVchrItemDTO> items) {
        this.items = items;
    }

    public boolean isNeedDelete() {
        return this.needDelete;
    }

    public void setNeedDelete(boolean needDelete) {
        this.needDelete = needDelete;
    }

    public boolean isAllowMoreUnit() {
        return this.allowMoreUnit;
    }

    public void setAllowMoreUnit(boolean allowMoreUnit, String currentOrgType) {
        Assert.isTrue((!StringUtils.isEmpty((String)currentOrgType) ? 1 : 0) != 0, (String)"\u5141\u8bb8\u591a\u5bb6\u5355\u4f4d\u62b5\u9500\u65f6\uff0c\u5fc5\u987b\u8bbe\u7f6e\u5f53\u524d\u673a\u6784\u7c7b\u578b", (Object[])new Object[0]);
        if (GCOrgTypeEnum.NONE.getCode().equals(currentOrgType)) {
            throw new BusinessRuntimeException("\u5141\u8bb8\u591a\u5bb6\u5355\u4f4d\u62b5\u9500\u65f6,\u5f53\u524d\u673a\u6784\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a\u7c7b\u578b\uff01");
        }
        this.allowMoreUnit = allowMoreUnit;
        this.currentOrgType = currentOrgType;
    }

    public String getConsFormulaCalcType() {
        return this.consFormulaCalcType;
    }

    public void setConsFormulaCalcType(String consFormulaCalcType) {
        this.consFormulaCalcType = consFormulaCalcType;
    }

    public String getCurrentOrgType() {
        return this.currentOrgType;
    }

    public void setCurrentOrgType(String currentOrgType) {
        this.currentOrgType = currentOrgType;
    }

    public String getVchrCode() {
        return this.vchrCode;
    }

    public void setVchrCode(String vchrCode) {
        this.vchrCode = vchrCode;
    }
}

