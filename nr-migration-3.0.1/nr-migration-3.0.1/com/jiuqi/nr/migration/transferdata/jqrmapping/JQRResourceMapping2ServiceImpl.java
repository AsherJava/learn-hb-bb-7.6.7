/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.jqrmapping;

import com.jiuqi.nr.migration.transferdata.jqrmapping.JQRResourceMapping2DO;
import com.jiuqi.nr.migration.transferdata.jqrmapping.JQRResourceMapping2Dao;
import com.jiuqi.nr.migration.transferdata.jqrmapping.JQRResourceMapping2Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JQRResourceMapping2ServiceImpl
implements JQRResourceMapping2Service {
    @Autowired
    private JQRResourceMapping2Dao dao;

    @Override
    public void batchInsertOrUpdateJqrCustomMappings(List<JQRResourceMapping2DO> jdo, String type) {
        if ("insert".equals(type)) {
            this.dao.add(jdo);
        } else {
            this.dao.updateJqrMapping(jdo);
        }
    }

    @Override
    public List<JQRResourceMapping2DO> findByMSJqrCustom(String msKey) {
        return this.dao.findByMS(msKey);
    }

    @Override
    public String getTableNameMapping(String msKey) {
        List<JQRResourceMapping2DO> byMS = this.dao.findByMS(msKey);
        for (JQRResourceMapping2DO mapping : byMS) {
            if (!"tableName".equals(mapping.getNrCode())) continue;
            return mapping.getJqrMapping();
        }
        return null;
    }

    @Override
    public String getSolutionNameMapping(String msKey) {
        List<JQRResourceMapping2DO> byMS = this.dao.findByMS(msKey);
        for (JQRResourceMapping2DO mapping : byMS) {
            if (!"solutionName".equals(mapping.getNrCode())) continue;
            return mapping.getJqrMapping();
        }
        return null;
    }

    @Override
    public String getVersionMapping(String msKey) {
        List<JQRResourceMapping2DO> byMS = this.dao.findByMS(msKey);
        for (JQRResourceMapping2DO mapping : byMS) {
            if (!"version".equals(mapping.getNrCode())) continue;
            return mapping.getJqrMapping();
        }
        return null;
    }

    @Override
    public void deleteMappingByMSKey(String msKey) {
        this.dao.delete(msKey);
    }
}

