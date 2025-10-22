/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.clbrbill.dto;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.clbrbill.dto.ClbrBillMasterDTO;
import com.jiuqi.gcreport.clbrbill.dto.ClbrSrcBillDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.ObjectUtils;

public class ClbrPushDataDTO {
    private ClbrBillMasterDTO clbrBillMasterDTO;
    private ClbrSrcBillDTO clbrSrcBillDTO;
    private Map<String, List<Map<String, Object>>> extBillMap;

    public ClbrBillMasterDTO getClbrBillMasterDTO() {
        return this.clbrBillMasterDTO;
    }

    public void setClbrBillMasterDTO(ClbrBillMasterDTO clbrBillMasterDTO) {
        this.clbrBillMasterDTO = clbrBillMasterDTO;
    }

    public ClbrSrcBillDTO getClbrSrcBillDTO() {
        return this.clbrSrcBillDTO;
    }

    public void setClbrSrcBillDTO(ClbrSrcBillDTO clbrSrcBillDTO) {
        this.clbrSrcBillDTO = clbrSrcBillDTO;
    }

    public Map<String, List<Map<String, Object>>> getExtBillMap() {
        return this.extBillMap;
    }

    public void setExtBillMap(Map<String, List<Map<String, Object>>> extBillMap) {
        this.extBillMap = extBillMap;
    }

    public void putExtBillMap(String key, List<Map<String, Object>> value) {
        if (this.extBillMap == null) {
            this.extBillMap = new HashMap<String, List<Map<String, Object>>>();
        }
        if (StringUtils.isEmpty((String)key) || ObjectUtils.isEmpty(value)) {
            return;
        }
        if (this.extBillMap.containsKey(key)) {
            return;
        }
        this.extBillMap.put(key, value);
    }
}

