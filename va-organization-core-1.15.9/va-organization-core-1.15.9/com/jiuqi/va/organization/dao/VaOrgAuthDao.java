/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.common.CustomOptMapper
 *  org.apache.ibatis.annotations.DeleteProvider
 *  org.apache.ibatis.annotations.InsertProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.organization.dao;

import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.CustomOptMapper;
import com.jiuqi.va.organization.domain.OrgAuthDO;
import com.jiuqi.va.organization.domain.OrgAuthDTO;
import com.jiuqi.va.organization.domain.OrgAuthItem;
import com.jiuqi.va.organization.service.OrgAuthService;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface VaOrgAuthDao
extends CustomOptMapper {
    @SelectProvider(type=SqlProvider.class, method="select")
    public List<OrgAuthDO> select(OrgAuthDTO var1);

    @InsertProvider(type=SqlProvider.class, method="insert")
    public int insert(OrgAuthDO var1);

    @UpdateProvider(type=SqlProvider.class, method="update")
    public int update(OrgAuthDO var1);

    @DeleteProvider(type=SqlProvider.class, method="delete")
    public int delete(OrgAuthDO var1);

    @SelectProvider(type=SqlProvider.class, method="listAuthItem")
    public List<OrgAuthItem> listAuthItem(OrgAuthDTO var1);

    @SelectProvider(type=SqlProvider.class, method="listOrgCode")
    public Set<String> listOrgCode(OrgAuthDTO var1);

    @SelectProvider(type=SqlProvider.class, method="listBizName")
    public Set<String> listBizName(OrgAuthDTO var1);

    public static class SqlProvider {
        public String select(OrgAuthDTO param) {
            SQL sql = new SQL();
            sql.SELECT("ID as \"id\", BIZTYPE as \"biztype\", BIZNAME as \"bizname\", AUTHTYPE as \"authtype\", ORGCATEGORY as \"orgcategory\", ORGNAME as \"orgname\",ATMANAGE as \"atmanage\", ATACCESS as \"ataccess\", ATWRITE as \"atwrite\", ATEDIT as \"atedit\",ATREPORT as \"atreport\", ATSUBMIT as \"atsubmit\",ATAPPROVAL as \"atapproval\"");
            OrgAuthService authService = (OrgAuthService)ApplicationContextRegister.getBean(OrgAuthService.class);
            List<String> authTypeExtendNameList = authService.getAuthTypeExtendName();
            if (authTypeExtendNameList != null) {
                String authName = null;
                for (String authTypeName : authTypeExtendNameList) {
                    authName = ("AT" + authTypeName).toUpperCase();
                    sql.SELECT(authName + " as \"" + authName.toLowerCase() + "\"");
                }
            }
            sql.FROM("AUTH_ORG_RIGHT");
            if (param.getAuthtype() != null) {
                sql.WHERE("AUTHTYPE = #{authtype, jdbcType=NUMERIC}");
            }
            if (param.getBiztype() != null) {
                sql.WHERE("BIZTYPE = #{biztype, jdbcType=NUMERIC}");
            }
            if (param.getBizname() != null) {
                sql.WHERE("BIZNAME = #{bizname, jdbcType=VARCHAR}");
            }
            if (param.getOrgcategory() != null) {
                sql.WHERE("ORGCATEGORY = #{orgcategory, jdbcType=VARCHAR}");
            }
            if (param.getOrgname() != null) {
                sql.WHERE("ORGNAME = #{orgname, jdbcType=VARCHAR}");
            }
            if (param.getSqlCondition() != null) {
                sql.WHERE(param.getSqlCondition());
            }
            return sql.toString();
        }

        public String insert() {
            SQL sql = new SQL();
            sql.INSERT_INTO("AUTH_ORG_RIGHT");
            sql.INTO_COLUMNS(new String[]{"ID", "BIZTYPE", "BIZNAME", "AUTHTYPE", "ORGCATEGORY", "ORGNAME", "ATMANAGE", "ATACCESS", "ATWRITE", "ATEDIT", "ATREPORT", "ATSUBMIT", "ATAPPROVAL"});
            sql.INTO_VALUES(new String[]{"#{id, jdbcType=VARCHAR}", "#{biztype, jdbcType=NUMERIC}", "#{bizname, jdbcType=VARCHAR}", "#{authtype, jdbcType=NUMERIC}", "#{orgcategory, jdbcType=VARCHAR}", "#{orgname, jdbcType=VARCHAR}", "#{atmanage, jdbcType=NUMERIC}", "#{ataccess, jdbcType=NUMERIC}", "#{atwrite, jdbcType=NUMERIC}", "#{atedit, jdbcType=NUMERIC}", "#{atreport, jdbcType=NUMERIC}", "#{atsubmit, jdbcType=NUMERIC}", "#{atapproval, jdbcType=NUMERIC}"});
            OrgAuthService authService = (OrgAuthService)ApplicationContextRegister.getBean(OrgAuthService.class);
            List<String> authTypeExtendNameList = authService.getAuthTypeExtendName();
            if (authTypeExtendNameList != null) {
                String authName = null;
                for (String authTypeName : authTypeExtendNameList) {
                    authName = ("AT" + authTypeName).toUpperCase();
                    sql.INTO_COLUMNS(new String[]{authName});
                    sql.INTO_VALUES(new String[]{"#{" + authName.toLowerCase() + ",jdbcType=NUMERIC}"});
                }
            }
            return sql.toString();
        }

        public String update() {
            SQL sql = new SQL();
            sql.UPDATE("AUTH_ORG_RIGHT");
            sql.SET("BIZTYPE = #{biztype, jdbcType=NUMERIC}");
            sql.SET("BIZNAME = #{bizname, jdbcType=VARCHAR}");
            sql.SET("AUTHTYPE = #{authtype, jdbcType=NUMERIC}");
            sql.SET("ORGCATEGORY = #{orgcategory, jdbcType=VARCHAR}");
            sql.SET("ORGNAME = #{orgname, jdbcType=VARCHAR}");
            sql.SET("ATMANAGE = #{atmanage, jdbcType=NUMERIC}");
            sql.SET("ATACCESS = #{ataccess, jdbcType=NUMERIC}");
            sql.SET("ATWRITE = #{atwrite, jdbcType=NUMERIC}");
            sql.SET("ATEDIT = #{atedit, jdbcType=NUMERIC}");
            sql.SET("ATREPORT = #{atreport, jdbcType=NUMERIC}");
            sql.SET("ATSUBMIT = #{atsubmit, jdbcType=NUMERIC}");
            sql.SET("ATAPPROVAL = #{atapproval, jdbcType=NUMERIC}");
            OrgAuthService authService = (OrgAuthService)ApplicationContextRegister.getBean(OrgAuthService.class);
            List<String> authTypeExtendNameList = authService.getAuthTypeExtendName();
            if (authTypeExtendNameList != null) {
                String authName = null;
                for (String authTypeName : authTypeExtendNameList) {
                    authName = ("AT" + authTypeName).toUpperCase();
                    sql.SET(authName + " = #{" + authName.toLowerCase() + ",jdbcType=NUMERIC}");
                }
            }
            sql.WHERE("ID = #{id, jdbcType=VARCHAR}");
            return sql.toString();
        }

        public String delete(OrgAuthDO param) {
            SQL sql = new SQL();
            sql.DELETE_FROM("AUTH_ORG_RIGHT");
            if (param.getId() != null) {
                sql.WHERE("ID = #{id, jdbcType=VARCHAR}");
            } else {
                if (param.getAuthtype() != null) {
                    sql.WHERE("AUTHTYPE = #{authtype, jdbcType=NUMERIC}");
                }
                if (param.getBiztype() != null) {
                    sql.WHERE("BIZTYPE = #{biztype, jdbcType=NUMERIC}");
                }
                if (param.getBizname() != null) {
                    sql.WHERE("BIZNAME = #{bizname, jdbcType=VARCHAR}");
                }
                if (param.getOrgcategory() != null) {
                    sql.WHERE("ORGCATEGORY = #{orgcategory, jdbcType=VARCHAR}");
                }
                if (param.getOrgname() != null) {
                    sql.WHERE("ORGNAME = #{orgname, jdbcType=VARCHAR}");
                }
            }
            return sql.toString();
        }

        public String listAuthItem(OrgAuthDTO param) {
            String authItem = ("AT" + param.getOrgDataDTO().getAuthType().toString()).toUpperCase();
            SQL sql = new SQL();
            sql.SELECT("BIZTYPE as \"biztype\", BIZNAME as \"bizname\", AUTHTYPE as \"authtype\", ORGNAME as \"orgname\"," + authItem + " as \"authflag\"");
            sql.FROM("AUTH_ORG_RIGHT");
            sql.WHERE(authItem + " in(1,2)");
            sql.WHERE(param.getSqlCondition());
            return sql.toString();
        }

        public String listOrgCode(OrgAuthDTO param) {
            SQL sql = new SQL();
            sql.SELECT("ORGNAME as \"orgname\"");
            sql.FROM("AUTH_ORG_RIGHT");
            sql.WHERE(param.getSqlCondition());
            return sql.toString();
        }

        public String listBizName(OrgAuthDTO param) {
            SQL sql = new SQL();
            sql.SELECT("BIZNAME as \"bizname\"");
            sql.FROM("AUTH_ORG_RIGHT");
            sql.WHERE(param.getSqlCondition());
            return sql.toString();
        }
    }
}

