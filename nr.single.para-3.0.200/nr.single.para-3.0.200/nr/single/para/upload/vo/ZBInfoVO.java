/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.vo;

import java.util.ArrayList;
import java.util.List;
import nr.single.para.upload.domain.ZBInfoDTO;

public class ZBInfoVO
extends ZBInfoDTO {
    private String tableKey;
    private String code;
    private int ownerTableType;

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getOwnerTableType() {
        return this.ownerTableType;
    }

    public void setOwnerTableType(int ownerTableType) {
        this.ownerTableType = ownerTableType;
    }

    public static List<ZBInfoVO> getListInstance(List<ZBInfoDTO> zbInfoDTOS) {
        ArrayList<ZBInfoVO> zbInfoVOS = new ArrayList<ZBInfoVO>(zbInfoDTOS.size());
        for (ZBInfoDTO zbInfoDTO : zbInfoDTOS) {
            ZBInfoVO vo = new ZBInfoVO();
            vo.setValue(zbInfoDTO.getValue());
            vo.setTitle(zbInfoDTO.getTitle());
            zbInfoVOS.add(vo);
        }
        return zbInfoVOS;
    }
}

