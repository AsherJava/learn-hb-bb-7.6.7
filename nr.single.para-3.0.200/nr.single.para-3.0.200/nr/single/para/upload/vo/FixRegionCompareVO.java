/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.vo;

import java.util.ArrayList;
import java.util.List;
import nr.single.para.upload.domain.ZBMappingDTO;
import nr.single.para.upload.vo.FloatRegionCompareVO;

public class FixRegionCompareVO
extends FloatRegionCompareVO {
    private List<ZBMappingDTO> updateFields;

    public List<ZBMappingDTO> getUpdateFields() {
        if (this.updateFields == null) {
            this.updateFields = new ArrayList<ZBMappingDTO>();
        }
        return this.updateFields;
    }

    public void setUpdateFields(List<ZBMappingDTO> updateFields) {
        this.updateFields = updateFields;
    }
}

