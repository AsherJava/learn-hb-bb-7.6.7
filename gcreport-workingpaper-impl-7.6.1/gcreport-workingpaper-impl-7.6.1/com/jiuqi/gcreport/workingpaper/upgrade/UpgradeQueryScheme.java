/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.queryscheme.eo.QuerySchemeEO
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.workingpaper.upgrade;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.queryscheme.eo.QuerySchemeEO;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class UpgradeQueryScheme
implements CustomClassExecutor {
    public void execute(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
        List<QuerySchemeEO> querySchemeList = this.listQuerySchemes(jdbcTemplate);
        List<QuerySchemeEO> querySchemeOptionDataList = this.listQuerySchemesOptionData(jdbcTemplate);
        this.processOptionData(querySchemeList, true, jdbcTemplate);
        this.processOptionData(querySchemeOptionDataList, false, jdbcTemplate);
    }

    private void processOptionData(List<QuerySchemeEO> list, Boolean flag, JdbcTemplate jdbcTemplate) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (QuerySchemeEO eo : list) {
            String optionData = eo.getOptionData();
            if (StringUtils.isEmpty((String)optionData) || optionData.contains("code") && optionData.contains("title")) continue;
            ArrayList<OrgCodeAndTitle> orgList = new ArrayList<OrgCodeAndTitle>();
            Pattern pattern = Pattern.compile("\"(.*?)\"");
            Matcher matcher = pattern.matcher(optionData);
            while (matcher.find()) {
                String orgCode = matcher.group(1);
                this.queryOrgByCode(orgList, orgCode, jdbcTemplate);
            }
            String newOptionData = "[";
            for (OrgCodeAndTitle org : orgList) {
                newOptionData = newOptionData + "{\"code\":\"" + org.code + "\",";
                newOptionData = newOptionData + "\"title\":\"" + org.title + "\"},";
            }
            newOptionData = newOptionData.substring(0, newOptionData.length() - 1);
            newOptionData = newOptionData + "]";
            Object[] arg = new Object[]{newOptionData, eo.getId()};
            args.add(arg);
        }
        if (flag.booleanValue()) {
            this.updateQueryScheme(args, jdbcTemplate);
        } else {
            this.updateQuerySchemeOptionData(args, jdbcTemplate);
        }
    }

    private void updateQueryScheme(List<Object[]> args, JdbcTemplate jdbcTemplate) {
        String sql = "update gc_query_scheme set optionData = ?  where  id = ? ";
        jdbcTemplate.batchUpdate(sql, args);
    }

    private void updateQuerySchemeOptionData(List<Object[]> args, JdbcTemplate jdbcTemplate) {
        String sql = "update gc_query_scheme_optiondata set optionData = ?  where  id = ? ";
        jdbcTemplate.batchUpdate(sql, args);
    }

    private List<QuerySchemeEO> listQuerySchemes(JdbcTemplate jdbcTemplate) {
        String sql1 = "SELECT s.id, s.optionData FROM gc_query_scheme s JOIN nvwa_route r ON s.resourceid LIKE r.id||'%' AND r.app_name LIKE 'workpaper/%' AND s.STORETYPE = 1";
        List list = jdbcTemplate.query(sql1, (rs, rowNum) -> {
            QuerySchemeEO eo = new QuerySchemeEO();
            eo.setId(rs.getString(1));
            eo.setOptionData(rs.getString(2));
            return eo;
        });
        if (list == null) {
            return null;
        }
        return list;
    }

    private List<QuerySchemeEO> listQuerySchemesOptionData(JdbcTemplate jdbcTemplate) {
        String sql = "select s.id, s.optionData from GC_QUERY_SCHEME_OPTIONDATA s join (select t.id from GC_QUERY_SCHEME t join NVWA_ROUTE r on t.resourceid like r.id||'%' and r.app_name like 'workpaper/%' and t.STORETYPE = 0) z on s.ID = z.ID";
        List list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            QuerySchemeEO eo = new QuerySchemeEO();
            eo.setId(rs.getString(1));
            eo.setOptionData(rs.getString(2));
            return eo;
        });
        if (list == null) {
            return null;
        }
        return list;
    }

    private void queryOrgByCode(List<OrgCodeAndTitle> orgList, String orgCode, JdbcTemplate jdbcTemplate) {
        String sql = "select t.CODE, t.NAME from MD_ORG t where t.CODE = '" + orgCode + "'";
        jdbcTemplate.query(sql, (rs, rowNum) -> {
            orgList.add(new OrgCodeAndTitle(orgCode, rs.getString(2)));
            return orgList;
        });
    }

    class OrgCodeAndTitle {
        private String code;
        private String title;

        public OrgCodeAndTitle(String code, String title) {
            this.code = code;
            this.title = title;
        }
    }
}

