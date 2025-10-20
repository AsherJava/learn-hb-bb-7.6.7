/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.apache.commons.io.IOUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.common.billbasedopsorg.bill.task;

import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

public class GcBillPushOrgsMetaDesignInitDataTask
implements CustomClassExecutor {
    private transient Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional(rollbackFor={Exception.class})
    public void execute(DataSource dataSource) throws Exception {
        this.initBillPushOrgMetaDesign();
    }

    private void initBillPushOrgMetaDesign() {
        try {
            String querySql = "select t.id from meta_info t where t.name = ?";
            List metaInfoIds = EntNativeSqlDefaultDao.getInstance().selectMap(querySql, Arrays.asList("GC_BILLPUSHORGS"));
            if (!CollectionUtils.isEmpty(metaInfoIds)) {
                String metaInfoId = (String)((Map)metaInfoIds.get(0)).get("ID");
                String queryCountSql = "select count(1) from meta_design t where t.id = ?";
                int metaDesignCount = EntNativeSqlDefaultDao.getInstance().count(queryCountSql, Arrays.asList(metaInfoId));
                if (metaDesignCount == 0) {
                    ClassPathResource resource = new ClassPathResource("config/initbilldata/GC_BILLPUSHORGSMODAL.fd.json");
                    InputStream inputStream = resource.getInputStream();
                    String designdata = IOUtils.toString((InputStream)inputStream, (String)"utf-8");
                    String sql = "insert into meta_design(id,designdata) values (?,?)";
                    EntNativeSqlDefaultDao.getInstance().execute(sql, Arrays.asList(metaInfoId, designdata));
                }
            }
        }
        catch (Exception e) {
            this.logger.error("\u521d\u59cb\u5316\u751f\u6210\u7ec4\u7ec7\u673a\u6784\u5355\u636e\u8bbe\u8ba1\u754c\u9762\u5931\u8d25" + e.getMessage(), e);
        }
    }
}

