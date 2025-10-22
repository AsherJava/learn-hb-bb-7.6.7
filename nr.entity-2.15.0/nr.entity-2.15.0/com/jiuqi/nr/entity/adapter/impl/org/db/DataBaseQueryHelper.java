/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.OrgIdentityService
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 */
package com.jiuqi.nr.entity.adapter.impl.org.db;

import com.jiuqi.np.authz2.service.OrgIdentityService;
import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.entity.adapter.impl.org.auth.AuthItem;
import com.jiuqi.nr.entity.adapter.impl.org.auth.dao.AuthQueryDao;
import com.jiuqi.nr.entity.adapter.impl.org.formula.ExpressionParser;
import com.jiuqi.nr.entity.adapter.impl.org.formula.Lexer;
import com.jiuqi.va.domain.org.OrgDataOption;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class DataBaseQueryHelper {
    private final OrgIdentityService orgIdentityService;
    private final AuthQueryDao authQueryDao;
    private final UserService<User> userService;
    private List<AuthItem> ruleAuth;
    private Set<String> detailCodes;
    private Set<String> rejectCodes;
    private Collection<String> grantedOrg;
    private String orgCode;
    private final ContextUser user;

    public DataBaseQueryHelper(UserService<User> userService, OrgIdentityService orgIdentityService, AuthQueryDao authQueryDao) {
        this.orgIdentityService = orgIdentityService;
        this.authQueryDao = authQueryDao;
        this.user = NpContextHolder.getContext().getUser();
        this.userService = userService;
    }

    public String getOrgCode(String identityId) {
        if (this.orgCode == null) {
            if (this.user.getId().equals(identityId)) {
                this.orgCode = this.user.getOrgCode();
            } else if (this.userService != null) {
                this.userService.find(identityId).ifPresent(e -> {
                    this.orgCode = e.getOrgCode();
                });
            }
        }
        return this.orgCode;
    }

    public SqlBuildDTO buildAuthSql(String entityId, OrgDataOption.AuthType authType, String identityId, String alias) {
        SqlBuildDTO sqlBuildDTO = new SqlBuildDTO();
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> arg = new ArrayList<Object>();
        this.getOrgAuthItems(entityId, identityId, authType);
        String ruleSql = this.buildRuleAuthSql(identityId, arg, alias);
        String detailSql = this.buildDetailSql(arg, alias);
        String rejectSql = this.buildRejectSql(arg, alias);
        boolean hasRule = StringUtils.hasText(ruleSql);
        boolean hasDetail = StringUtils.hasText(detailSql);
        boolean hasReject = StringUtils.hasText(rejectSql);
        if (hasRule || hasDetail) {
            sql.append(" ( ");
            if (hasRule) {
                sql.append(ruleSql);
            }
            if (hasRule && hasDetail) {
                sql.append(" OR ");
            }
            if (hasDetail) {
                sql.append(detailSql);
            }
            sql.append(" ) ");
        }
        if (hasReject) {
            if (hasRule || hasDetail) {
                sql.append(" AND (");
            }
            sql.append(rejectSql);
            if (hasRule || hasDetail) {
                sql.append(" ) ");
            }
        }
        sqlBuildDTO.sql = sql;
        sqlBuildDTO.arg = arg;
        return sqlBuildDTO;
    }

    private String buildRuleAuthSql(String identityId, List<Object> arg, String alias) {
        StringBuilder sql = new StringBuilder();
        if (!CollectionUtils.isEmpty(this.ruleAuth)) {
            for (AuthItem authItem : this.ruleAuth) {
                String orgname;
                switch (orgname = authItem.getOrgname()) {
                    case "rule_all": {
                        sql.append(" 1 = 1 ");
                        return sql.toString();
                    }
                    case "rule_belong": {
                        this.appendORCondition(sql, "CODE = ?", this.getOrgCode(identityId), arg, alias);
                        break;
                    }
                    case "rule_direct_children": {
                        this.appendORCondition(sql, "PARENTCODE = ?", this.getOrgCode(identityId), arg, alias);
                        break;
                    }
                    case "rule_all_children": {
                        this.appendORCondition(sql, "PARENTS LIKE ?", "%" + this.getOrgCode(identityId) + "/%", arg, alias);
                        break;
                    }
                    case "rule_both": {
                        this.getGrantedOrg(identityId).forEach(code -> this.appendORCondition(sql, "CODE = ?", code, arg, alias));
                        break;
                    }
                    case "rule_both_direct_childre": {
                        this.getGrantedOrg(identityId).forEach(code -> this.appendORCondition(sql, "PARENTCODE = ?", code, arg, alias));
                        break;
                    }
                    case "rule_both_all_children": {
                        this.getGrantedOrg(identityId).forEach(code -> this.appendORCondition(sql, "PARENTS LIKE ?", "%" + code + "/%", arg, alias));
                    }
                }
            }
            this.trimTrailing(sql, "OR");
        }
        return sql.toString();
    }

    private String buildRejectSql(List<Object> arg, String alias) {
        StringBuilder sql = new StringBuilder();
        if (!CollectionUtils.isEmpty(this.rejectCodes)) {
            this.rejectCodes.forEach(code -> this.appendANDCondition(sql, "CODE != ?", code, arg, alias));
            this.trimTrailing(sql, "AND");
        }
        return sql.toString();
    }

    private String buildDetailSql(List<Object> arg, String alias) {
        StringBuilder sql = new StringBuilder();
        if (!CollectionUtils.isEmpty(this.detailCodes)) {
            this.detailCodes.forEach(code -> this.appendORCondition(sql, "CODE = ?", code, arg, alias));
            this.trimTrailing(sql, "OR");
        }
        return sql.toString();
    }

    private void appendORCondition(StringBuilder sql, String condition, Object value, List<Object> arg, String alias) {
        if (StringUtils.hasText(alias)) {
            sql.append(alias).append(".");
        }
        sql.append(condition).append(" OR ");
        arg.add(value);
    }

    private void appendANDCondition(StringBuilder sql, String condition, Object value, List<Object> arg, String alias) {
        if (StringUtils.hasText(alias)) {
            sql.append(alias).append(".");
        }
        sql.append(condition).append(" AND ");
        arg.add(value);
    }

    private void getOrgAuthItems(String entityId, String identityId, OrgDataOption.AuthType authType) {
        ContextExtension orgAuthCache = NpContextHolder.getContext().getExtension("ORG_AUTH_CACHE");
        Object ruleAuth = orgAuthCache.get("RULE_AUTH");
        Object rejectCache = orgAuthCache.get("REJECT_CODE");
        Object detailCache = orgAuthCache.get("DETAIL_CODE");
        boolean hasCache = false;
        if (rejectCache != null) {
            hasCache = true;
            this.rejectCodes = (Set)rejectCache;
        }
        if (detailCache != null) {
            hasCache = true;
            this.detailCodes = (Set)detailCache;
        }
        if (ruleAuth != null) {
            hasCache = true;
            this.ruleAuth = (List)ruleAuth;
        }
        if (hasCache) {
            return;
        }
        List<AuthItem> authItems = this.authQueryDao.listAuthItem(authType, entityId, identityId);
        Map<Integer, List<AuthItem>> listMap = authItems.stream().collect(Collectors.groupingBy(AuthItem::getAuthtype));
        List<AuthItem> orgRuleAuth = listMap.get(0);
        if (orgRuleAuth != null) {
            this.ruleAuth = orgRuleAuth;
            orgAuthCache.put("RULE_AUTH", (Serializable)((Object)orgRuleAuth));
        } else {
            orgAuthCache.put("RULE_AUTH", new ArrayList(0));
        }
        List<AuthItem> detailAuth = listMap.get(1);
        if (detailAuth != null) {
            this.rejectCodes = detailAuth.stream().filter(e -> e.getAuthflag() == 2).map(AuthItem::getOrgname).collect(Collectors.toSet());
            this.detailCodes = detailAuth.stream().filter(e -> e.getAuthflag() == 1).map(AuthItem::getOrgname).collect(Collectors.toSet());
            orgAuthCache.put("REJECT_CODE", (Serializable)((Object)this.rejectCodes));
            orgAuthCache.put("DETAIL_CODE", (Serializable)((Object)this.detailCodes));
        } else {
            orgAuthCache.put("REJECT_CODE", new HashSet(0));
            orgAuthCache.put("DETAIL_CODE", new HashSet(0));
        }
    }

    public void buildCondition(Date version, StringBuilder condition, List<Object> arg, String alias) {
        condition.append(" AND ");
        if (StringUtils.hasText(alias)) {
            condition.append(alias).append(".");
        }
        condition.append("VALIDTIME <= ? ");
        condition.append(" AND ");
        if (StringUtils.hasText(alias)) {
            condition.append(alias).append(".");
        }
        condition.append("INVALIDTIME > ? ");
        condition.append(" AND ");
        if (StringUtils.hasText(alias)) {
            condition.append(alias).append(".");
        }
        condition.append("STOPFLAG = 0 ");
        condition.append(" AND ");
        if (StringUtils.hasText(alias)) {
            condition.append(alias).append(".");
        }
        condition.append("RECOVERYFLAG = 0 ");
        arg.add(version);
        arg.add(version);
    }

    public void buildSqlExpression(String expression, StringBuilder sql, String alias) {
        if (!StringUtils.hasText(expression)) {
            return;
        }
        boolean isValid = false;
        try {
            Lexer lexer = new Lexer(expression);
            ExpressionParser parser = new ExpressionParser(lexer);
            isValid = parser.parseExpression();
        }
        catch (Exception lexer) {
            // empty catch block
        }
        if (isValid) {
            sql.append(" AND ");
            String trim = expression.trim();
            sql.append(trim);
        }
    }

    public void buildOrderBy(StringBuilder condition, String alias) {
        condition.append(" ORDER BY ");
        if (StringUtils.hasText(alias)) {
            condition.append(alias).append(".");
        }
        condition.append("ORDINAL, ");
        if (StringUtils.hasText(alias)) {
            condition.append(alias).append(".");
        }
        condition.append("NAME");
    }

    private Collection<String> getGrantedOrg(String identityId) {
        if (this.grantedOrg == null) {
            this.grantedOrg = this.orgIdentityService.getGrantedOrg(identityId);
        }
        return this.grantedOrg;
    }

    public void trimTrailing(StringBuilder sql, String concat) {
        int pos = sql.lastIndexOf(concat);
        if (pos == sql.length() - (concat.length() + 1)) {
            sql.delete(pos, sql.length());
        }
    }

    public List<AuthItem> getRuleAuth() {
        return this.ruleAuth;
    }

    public Set<String> getDetailCodes() {
        return this.detailCodes;
    }

    public Set<String> getRejectCodes() {
        return this.rejectCodes;
    }

    public class SqlBuildDTO {
        StringBuilder sql;
        List<Object> arg;

        public StringBuilder getSql() {
            return this.sql;
        }

        public List<Object> getArg() {
            return this.arg;
        }
    }
}

