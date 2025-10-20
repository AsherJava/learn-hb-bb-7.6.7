/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.apache.commons.io.IOUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.lease.leasebill.bill.task;

import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

public class LeaseBillMetaDesignInitDataTask
implements CustomClassExecutor {
    @Transactional(rollbackFor={Exception.class})
    public void execute(DataSource dataSource) throws Exception {
        this.initLessorListMetaDesign();
        this.initTenantryListMetaDesign();
        this.initLessorMetaDesign();
        this.initTenantryMetaDesign();
    }

    private void initLessorListMetaDesign() {
        try {
            String querySql = "select t.id from meta_info t where t.name = ?";
            List lessorIds = EntNativeSqlDefaultDao.getInstance().selectMap(querySql, Arrays.asList("GC_LESSORBILLLIST"));
            if (!CollectionUtils.isEmpty(lessorIds)) {
                String id = (String)((Map)lessorIds.get(0)).get("ID");
                String queryCount = "select count(1) from meta_design t where t.id = ?";
                int metaDesignCount = EntNativeSqlDefaultDao.getInstance().count(queryCount, Arrays.asList(id));
                if (metaDesignCount == 0) {
                    ClassPathResource resource = new ClassPathResource("config/vainvest/field/GC_LESSORBILLLIST.fd.json");
                    InputStream inputStream = resource.getInputStream();
                    String lessorBillListValue = IOUtils.toString((InputStream)inputStream, (String)"utf-8");
                    String sql = "insert into meta_design(id,designdata) values (?,?)";
                    EntNativeSqlDefaultDao.getInstance().execute(sql, Arrays.asList(id, lessorBillListValue));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTenantryListMetaDesign() {
        try {
            String querySql = "select t.id from meta_info t where t.name = ?";
            List tenantryIds = EntNativeSqlDefaultDao.getInstance().selectMap(querySql, Arrays.asList("GC_TENANTRYBILLLIST"));
            if (!CollectionUtils.isEmpty(tenantryIds)) {
                String id = (String)((Map)tenantryIds.get(0)).get("ID");
                String queryCount = "select count(1) from meta_design t where t.id = ?";
                int metaDesignCount = EntNativeSqlDefaultDao.getInstance().count(queryCount, Arrays.asList(id));
                if (metaDesignCount == 0) {
                    ClassPathResource resource = new ClassPathResource("config/vainvest/field/GC_TENANTRYBILLLIST.fd.json");
                    InputStream inputStream = resource.getInputStream();
                    String tenantryBillListValue = IOUtils.toString((InputStream)inputStream, (String)"utf-8");
                    String sql = "insert into meta_design(id,designdata) values (?,?)";
                    EntNativeSqlDefaultDao.getInstance().execute(sql, Arrays.asList(id, tenantryBillListValue));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initLessorMetaDesign() {
        try {
            String querySql = "select t.id from meta_info t where t.name = ?";
            List fvchIds = EntNativeSqlDefaultDao.getInstance().selectMap(querySql, Arrays.asList("GC_LESSORBILL"));
            if (!CollectionUtils.isEmpty(fvchIds)) {
                String id = (String)((Map)fvchIds.get(0)).get("ID");
                String queryCountSql = "select count(1) from meta_design t where t.id = ?";
                int metaDesignCount = EntNativeSqlDefaultDao.getInstance().count(queryCountSql, Arrays.asList(id));
                if (metaDesignCount == 0) {
                    ClassPathResource resource = new ClassPathResource("config/vainvest/field/GC_LESSORBILLMODAL.fd.json");
                    InputStream inputStream = resource.getInputStream();
                    String lessorBillValue = IOUtils.toString((InputStream)inputStream, (String)"utf-8");
                    String sql = "insert into meta_design(id,designdata) values (?,?)";
                    EntNativeSqlDefaultDao.getInstance().execute(sql, Arrays.asList(id, lessorBillValue));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTenantryMetaDesign() {
        try {
            String querySql = "select t.id from meta_info t where t.name = ?";
            List investIds = EntNativeSqlDefaultDao.getInstance().selectMap(querySql, Arrays.asList("GC_TENANTRYBILL"));
            if (!CollectionUtils.isEmpty(investIds)) {
                String id = (String)((Map)investIds.get(0)).get("ID");
                String queryCount = "select count(1) from meta_design t where t.id = ?";
                int metaDesignCount = EntNativeSqlDefaultDao.getInstance().count(queryCount, Arrays.asList(id));
                if (metaDesignCount == 0) {
                    ClassPathResource resource = new ClassPathResource("config/vainvest/field/GC_TENANTRYBILLMODAL.fd.json");
                    InputStream inputStream = resource.getInputStream();
                    String tenantryBillValue = IOUtils.toString((InputStream)inputStream, (String)"utf-8");
                    String sql = "insert into meta_design(id,designdata) values (?,?)";
                    EntNativeSqlDefaultDao.getInstance().execute(sql, Arrays.asList(id, tenantryBillValue));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

