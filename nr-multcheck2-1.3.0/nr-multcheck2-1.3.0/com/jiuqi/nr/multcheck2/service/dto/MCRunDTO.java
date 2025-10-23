/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.service.dto;

import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.web.vo.MCRunVO;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeanUtils;

public class MCRunDTO
extends MCRunVO {
    Map<String, MultcheckScheme> schemeMap = new HashMap<String, MultcheckScheme>();

    public MCRunDTO() {
    }

    public MCRunDTO(MCRunVO vo, Map<String, MultcheckScheme> schemeMap) {
        BeanUtils.copyProperties(vo, this);
        this.schemeMap = schemeMap;
    }

    public Map<String, MultcheckScheme> getSchemeMap() {
        return this.schemeMap;
    }

    public void setSchemeMap(Map<String, MultcheckScheme> schemeMap) {
        this.schemeMap = schemeMap;
    }
}

