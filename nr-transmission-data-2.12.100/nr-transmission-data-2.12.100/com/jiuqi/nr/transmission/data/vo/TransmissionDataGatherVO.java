/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.vo;

import com.jiuqi.nr.transmission.data.api.ITransmissionDataGather;
import java.util.HashSet;

public class TransmissionDataGatherVO {
    public static final String[] DEFAULT_DATA = new String[]{"BUSINESS_DATA", "FMDM_DATA", "FORMULA_CHECK", "HSHD_DESCRIBE", "EXP_ERR_DES"};
    public static final HashSet DEFAULT_DATA_SET = new HashSet();
    private String title;
    private String code;
    private boolean checked;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public static TransmissionDataGatherVO syncSchemeParamVoToDto(ITransmissionDataGather transmissionDataGather) {
        TransmissionDataGatherVO transmissionDataGatherVO = new TransmissionDataGatherVO();
        transmissionDataGatherVO.setCode(transmissionDataGather.getCode());
        transmissionDataGatherVO.setTitle(transmissionDataGather.getTitle());
        if (DEFAULT_DATA_SET.contains(transmissionDataGather.getCode())) {
            transmissionDataGatherVO.setChecked(true);
        }
        return transmissionDataGatherVO;
    }

    static {
        for (int i = 0; i < DEFAULT_DATA.length; ++i) {
            DEFAULT_DATA_SET.add(DEFAULT_DATA[i]);
        }
    }
}

