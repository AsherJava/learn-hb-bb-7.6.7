/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.organization.service.impl.help.OrgDataParamService
 */
package com.jiuqi.gcreport.org.impl.vacustomize;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.organization.service.impl.help.OrgDataParamService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component(value="com.jiuqi.gcreport.org.impl.vacustomize.GcCustomizeOrgDataParamService")
@Primary
public class GcCustomizeOrgDataParamService
extends OrgDataParamService {
    public R checkRelated(OrgDTO orgDTO) {
        return R.ok();
    }
}

