/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.splittable.service;

import com.jiuqi.nr.splittable.web.LinkAndFieldDTO;
import com.jiuqi.nr.splittable.web.LinkAndFieldVO;
import java.util.List;

public interface AuditService {
    public List<LinkAndFieldVO> getLinkAndFieldInSubArea(List<LinkAndFieldDTO> var1);
}

