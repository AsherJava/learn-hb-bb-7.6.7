/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 */
package com.jiuqi.nr.fmdm.system.options;

import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.fmdm.system.IFMDMAuditOptions;
import com.jiuqi.nr.fmdm.system.dto.FMDMOptionsDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FMDMAuditTypeOptions
implements IFMDMAuditOptions {
    private static final Logger logs = LoggerFactory.getLogger(FMDMAuditTypeOptions.class);
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;

    @Override
    public List<FMDMOptionsDTO> getAllAuditOptions() {
        List<FMDMOptionsDTO> optionsDTOS = new ArrayList<FMDMOptionsDTO>();
        try {
            List auditTypes = this.auditTypeDefineService.queryAllAuditType();
            if (!CollectionUtils.isEmpty(auditTypes)) {
                optionsDTOS = auditTypes.stream().map(e -> {
                    FMDMOptionsDTO optionsDTO = new FMDMOptionsDTO();
                    optionsDTO.setKey(e.getCode());
                    optionsDTO.setTitle(e.getTitle());
                    return optionsDTO;
                }).collect(Collectors.toList());
            }
        }
        catch (Exception e2) {
            logs.error("\u67e5\u8be2\u516c\u5f0f\u7c7b\u578b\u9519\u8bef", e2);
        }
        return optionsDTOS;
    }
}

