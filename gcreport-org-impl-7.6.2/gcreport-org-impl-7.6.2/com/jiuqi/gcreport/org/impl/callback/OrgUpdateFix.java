/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 *  com.jiuqi.va.mapper.domain.TableColumnDO
 *  com.jiuqi.va.organization.dao.VaOrgCategoryDao
 *  com.jiuqi.va.organization.service.OrgCategoryService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.org.impl.callback;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.org.impl.init.GcOrgInitService;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.mapper.domain.TableColumnDO;
import com.jiuqi.va.organization.dao.VaOrgCategoryDao;
import com.jiuqi.va.organization.service.OrgCategoryService;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrgUpdateFix
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(OrgUpdateFix.class);

    public void execute(DataSource dataSource) {
        GcOrgInitService gcOrgInitService = (GcOrgInitService)SpringContextUtils.getBean(GcOrgInitService.class);
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
        String zbString = "{\"id\":\"25630113-65ae-46c2-a293-77e1a306fed9\", \"name\":\"GCPARENTS\",  \"title\":\"\u7236\u7ea7\u8def\u5f84(GC)\", \"datatype\":2, \"precision\":610 ,\"decimal\":0, \"relatetype\":1, \"requiredflag\":0,\"tenantName\": \"__default_tenant__\", \"uniqueflag\": 0,\"ordernum\": 29}";
        JDialectUtil jDialectUtil = JDialectUtil.getInstance();
        JTableModel jtm = new JTableModel("__default_tenant__", "GC_UNITCODE_LENGTH");
        jtm.column("VARIABLELENGTH").INTEGER(new Integer[]{1});
        try {
            jDialectUtil.addColumn(jtm);
        }
        catch (JTableException e) {
            logger.error("\u65b0\u5efa\u662f\u5426\u5141\u8bb8\u4e0d\u5b9a\u957f\u5b57\u6bb5\u5931\u8d25", e);
        }
        jdbcTemplate.update("update MD_ORG set ONLINEFLAG = 0 where ONLINEFLAG is null");
        JTableModel orgJtm = new JTableModel("__default_tenant__", "MD_ORG");
        orgJtm.column("ONLINEFLAG").INTEGER(new Integer[]{1}).setNullable(Boolean.valueOf(true));
        try {
            jDialectUtil.modifyColumn(orgJtm);
        }
        catch (JTableException e) {
            logger.error("\u4fee\u6539md_org\u4e0a\u7ebf\u6807\u8bc6\u5b57\u6bb5\u4e3a\uff1a\u5141\u8bb8\u4e3a\u7a7a, \u5931\u8d25", e);
        }
        gcOrgInitService.init(false, "__default_tenant__");
        List list = jdbcTemplate.queryForList("select c.name from MD_ORG_CATEGORY c");
        List orgTypes = list.stream().map(o -> (String)o.get("name")).collect(Collectors.toList());
        ZB zb = (ZB)JsonUtils.readValue((String)zbString, ZB.class);
        for (String orgType : orgTypes) {
            Optional<TableColumnDO> any;
            List tableColumns = jDialectUtil.getTableColumns("__default_tenant__", orgType);
            if (!"md_org".equalsIgnoreCase(orgType) && (any = tableColumns.stream().filter(tableColumnDO -> tableColumnDO.getColumnName().equalsIgnoreCase("orgid")).findAny()).isPresent()) {
                jdbcTemplate.update("update " + orgType + " set orgid = code where orgid is null");
                jdbcTemplate.update("update " + orgType + " set orgcode = code where orgcode is null");
            }
            OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
            orgCategoryDO.setTenantName("__default_tenant__");
            orgCategoryDO.setName(orgType);
            OrgCategoryService orgCategoryService = (OrgCategoryService)SpringContextUtils.getBean(OrgCategoryService.class);
            OrgCategoryDO oldOrgCategoryDO = orgCategoryService.get(orgCategoryDO);
            ZB oldZ = oldOrgCategoryDO.getZbByName(zb.getName());
            ZB orgcode = oldOrgCategoryDO.getZbByName("ORGCODE");
            logger.warn("\u4fee\u590d\u5355\u4f4d\u7c7b\u578b\uff1a" + orgType + " : \u53bb\u9664\u5b9a\u4e49\u4e2d\u4fdd\u7559\u7684 orgcode");
            List zbs = oldOrgCategoryDO.getZbs();
            oldOrgCategoryDO.getZbs().remove(orgcode);
            VaOrgCategoryDao orgCategoryDao = (VaOrgCategoryDao)SpringContextUtils.getBean(VaOrgCategoryDao.class);
            ZB zb2 = null;
            Iterator iterator = zbs.iterator();
            while (iterator.hasNext()) {
                zb2 = (ZB)iterator.next();
                if (zb2.getName().equalsIgnoreCase("ORGCODE")) {
                    iterator.remove();
                }
                if (zb2.getName().equalsIgnoreCase("orgid")) {
                    zb2.setRequiredflag(Integer.valueOf(0));
                }
                if (!zb2.getName().equalsIgnoreCase("onlineflag")) continue;
                zb2.setRequiredflag(Integer.valueOf(0));
            }
            oldOrgCategoryDO.setExtinfo(JsonUtils.writeValueAsString((Object)zbs));
            orgCategoryDao.updateByPrimaryKeySelective((Object)oldOrgCategoryDO);
            OrgCategoryClient bean = (OrgCategoryClient)SpringContextUtils.getBean(OrgCategoryClient.class);
            bean.syncCache(orgCategoryDO);
            oldOrgCategoryDO.syncZb(zb);
            R update = orgCategoryService.update(oldOrgCategoryDO);
            if (update.getCode() != 0) {
                logger.error(orgType + "\u6dfb\u52a0gcparents\u5b57\u6bb5\u5931\u8d25\uff01\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
                continue;
            }
            jdbcTemplate.execute("update " + orgType + " set gcparents = parents where gcparents is null ");
        }
    }
}

