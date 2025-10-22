/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.invest.investbill.bill.task;

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

public class InvestBillMetaDesignInitDataTask
implements CustomClassExecutor {
    @Transactional(rollbackFor={Exception.class})
    public void execute(DataSource dataSource) throws Exception {
        this.initFvchMetaDesign();
        this.initInvestMetaDesign();
        this.initInvestListMetaDesign();
    }

    private void initFvchMetaDesign() {
        try {
            String querySql = "select t.id from meta_info t where t.name = ?";
            List fvchIds = EntNativeSqlDefaultDao.getInstance().selectMap(querySql, Arrays.asList("FVCHBILL"));
            if (!CollectionUtils.isEmpty(fvchIds)) {
                String id = (String)((Map)fvchIds.get(0)).get("ID");
                String queryCountSql = "select count(1) from meta_design t where t.id = ?";
                int metaDesignCount = EntNativeSqlDefaultDao.getInstance().count(queryCountSql, Arrays.asList(id));
                if (metaDesignCount == 0) {
                    ClassPathResource resource = new ClassPathResource("config/vainvest/field/GC_FVCHBILLMODAL.fd.json");
                    InputStream inputStream = resource.getInputStream();
                    String fvchBillValue = IOUtils.toString(inputStream, "utf-8");
                    String sql = "insert into meta_design(id,designdata) values (?,?)";
                    EntNativeSqlDefaultDao.getInstance().execute(sql, Arrays.asList(id, fvchBillValue));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initInvestMetaDesign() {
        try {
            String querySql = "select t.id from meta_info t where t.name = ?";
            List investIds = EntNativeSqlDefaultDao.getInstance().selectMap(querySql, Arrays.asList("INVESTBILL"));
            if (!CollectionUtils.isEmpty(investIds)) {
                String id = (String)((Map)investIds.get(0)).get("ID");
                String queryCount = "select count(1) from meta_design t where t.id = ?";
                int metaDesignCount = EntNativeSqlDefaultDao.getInstance().count(queryCount, Arrays.asList(id));
                if (metaDesignCount == 0) {
                    ClassPathResource resource = new ClassPathResource("config/vainvest/field/GC_INVESTBILLMODAL.fd.json");
                    InputStream inputStream = resource.getInputStream();
                    String investBillValue = IOUtils.toString(inputStream, "utf-8");
                    String sql = "insert into meta_design(id,designdata) values (?,?)";
                    EntNativeSqlDefaultDao.getInstance().execute(sql, Arrays.asList(id, investBillValue));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initInvestListMetaDesign() {
        try {
            String querySql = "select t.id from meta_info t where t.name = ?";
            List investIds = EntNativeSqlDefaultDao.getInstance().selectMap(querySql, Arrays.asList("INVESTBILLLIST"));
            if (!CollectionUtils.isEmpty(investIds)) {
                String id = (String)((Map)investIds.get(0)).get("ID");
                String queryCount = "select count(1) from meta_design t where t.id = ?";
                int metaDesignCount = EntNativeSqlDefaultDao.getInstance().count(queryCount, Arrays.asList(id));
                if (metaDesignCount == 0) {
                    ClassPathResource resource = new ClassPathResource("config/vainvest/field/GC_INVESTBILLLIST.fd.json");
                    InputStream inputStream = resource.getInputStream();
                    String investBillListValue = IOUtils.toString(inputStream, "utf-8");
                    String sql = "insert into meta_design(id,designdata) values (?,?)";
                    EntNativeSqlDefaultDao.getInstance().execute(sql, Arrays.asList(id, investBillListValue));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

