/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 */
package com.jiuqi.nr.task.form.formio.dto;

import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import java.util.ArrayList;
import java.util.List;

public class ImportBaseDataDTO {
    private BaseDataDefineDTO baseDataDefine;
    private List<BaseDataDTO> baseData = new ArrayList<BaseDataDTO>();

    public BaseDataDefineDTO getBaseDataDefine() {
        return this.baseDataDefine;
    }

    public void setBaseDataDefine(BaseDataDefineDTO baseDataDefine) {
        this.baseDataDefine = baseDataDefine;
    }

    public List<BaseDataDTO> getBaseData() {
        return this.baseData;
    }

    public void setBaseData(List<BaseDataDTO> baseData) {
        this.baseData = baseData;
    }

    public static class BaseDataDTOMap
    extends BaseDataDTO {
        public int hashCode() {
            return this.getName().hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            return this.getName().equals(((BaseDataDTO)obj).getName());
        }
    }

    public static class BaseDataDefineDTOMap
    extends BaseDataDefineDTO {
        public int hashCode() {
            return this.getName().hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            return this.getName().equals(((BaseDataDefineDTO)obj).getName());
        }
    }
}

