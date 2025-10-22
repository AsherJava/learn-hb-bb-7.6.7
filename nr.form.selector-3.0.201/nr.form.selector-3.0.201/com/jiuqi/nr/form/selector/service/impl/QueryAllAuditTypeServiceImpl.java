/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 */
package com.jiuqi.nr.form.selector.service.impl;

import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.form.selector.entity.OneAuditTypeData;
import com.jiuqi.nr.form.selector.service.IQueryAllAuditTypeService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryAllAuditTypeServiceImpl
implements IQueryAllAuditTypeService {
    @Autowired
    AuditTypeDefineService auditTypeDefineService;

    @Override
    public LinkedList<OneAuditTypeData> queryAllAuditType() throws Exception {
        List allAuditType = this.auditTypeDefineService.queryAllAuditType();
        LinkedList<OneAuditTypeData> returnAllAuditType = new LinkedList<OneAuditTypeData>();
        for (AuditType oneAuditType : allAuditType) {
            OneAuditTypeData oneAuditTypeData = new OneAuditTypeData(oneAuditType.getCode(), oneAuditType.getTitle());
            returnAllAuditType.add(oneAuditTypeData);
        }
        return returnAllAuditType;
    }
}

