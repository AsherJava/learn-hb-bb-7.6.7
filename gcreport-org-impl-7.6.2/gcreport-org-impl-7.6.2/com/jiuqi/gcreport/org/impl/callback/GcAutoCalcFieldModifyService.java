/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.va.domain.org.OrgDTO
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.org.impl.callback;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.va.domain.org.OrgDTO;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class GcAutoCalcFieldModifyService {
    private static final Logger logger = LoggerFactory.getLogger(GcAutoCalcFieldModifyService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updateGcParents(String tableName, String gcParents, OrgDTO newOrgDTO) {
        int update;
        String updateSql = "update " + tableName + " set gcparents = ? where id = ?";
        try {
            update = this.jdbcTemplate.update(updateSql, new Object[]{gcParents, UUIDUtils.toString36((UUID)newOrgDTO.getId())});
        }
        catch (Exception e) {
            logger.error("\u7ec4\u7ec7\u673a\u6784\u4fee\u6539gcparents\u5b57\u6bb5\u5931\u8d25: " + newOrgDTO.getCode(), e);
            throw new BusinessRuntimeException("\u7ec4\u7ec7\u673a\u6784\u4fee\u6539gcparents\u5b57\u6bb5\u5931\u8d25: " + newOrgDTO.getCode());
        }
        if (update == 0) {
            logger.info("\u7ec4\u7ec7\u673a\u6784\u4fee\u6539gcparents\u5b57\u6bb5\u5931\u8d25\uff0c\u5c06\u5728\u5237\u65b0\u7f13\u5b58\u65f6\u4fee\u590d\uff0c\u5355\u4f4did\uff1a{}", (Object)newOrgDTO.getId());
        }
    }
}

