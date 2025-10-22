/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceItem
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$ResourceType
 *  com.jiuqi.va.organization.exchange.auth.OrgRuleResourceCategory
 */
package com.jiuqi.gcreport.listedcompanyauthz.rule;

import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceItem;
import com.jiuqi.nvwa.authority.util.AuthorityConst;
import com.jiuqi.va.organization.exchange.auth.OrgRuleResourceCategory;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ListedCompanyOrgRuleResourceCategory
extends OrgRuleResourceCategory {
    public static final String RULE_ALL_TITLE = "\u6240\u6709\u673a\u6784";
    public static final String RULE_BELONG_TITLE = "\u6240\u5c5e\u673a\u6784";
    public static final String RULE_BELONG_DIRECT_CHILDREN_TITLE = "\u6240\u5c5e\u673a\u6784\u7684\u76f4\u63a5\u4e0b\u7ea7";
    public static final String RULE_BELONG_ALL_CHILDREN_TITLE = "\u6240\u5c5e\u673a\u6784\u7684\u6240\u6709\u4e0b\u7ea7";
    public static final String RULE_BOTH_TITLE = "\u517c\u7ba1\u673a\u6784";
    public static final String RULE_BOTH_DIRECT_CHILDREN_TITLE = "\u517c\u7ba1\u673a\u6784\u7684\u76f4\u63a5\u4e0b\u7ea7";
    public static final String RULE_BOTH_ALL_CHILDREN_TITLE = "\u517c\u7ba1\u673a\u6784\u7684\u6240\u6709\u4e0b\u7ea7";
    private static final long serialVersionUID = 1L;

    public int getSeq() {
        return super.getSeq() - 1;
    }

    public String getId() {
        return super.getId();
    }

    public String getTitle() {
        return super.getTitle() + "(\u54cd\u5e94\u4e0a\u5e02\u516c\u53f8\u89c4\u5219)";
    }

    public List<Resource> getRootResources(String granteeId) {
        return this.getResources(null, "-");
    }

    public List<Resource> getRootResources(String granteeId, Object params) {
        return this.getResources(params, "-");
    }

    private List<Resource> getResources(Object params, String parentcode) {
        ResourceItem rs;
        ArrayList<Resource> rsList = new ArrayList<Resource>();
        if ("-".equals(parentcode) || "rule_all".equals(parentcode)) {
            rs = new ResourceItem();
            rs.setId("rule_all");
            rs.setTitle(RULE_ALL_TITLE);
            rs.setResourceType(AuthorityConst.ResourceType.RESOURCE);
            rsList.add((Resource)rs);
        }
        if ("-".equals(parentcode) || "rule_belong".equals(parentcode)) {
            rs = new ResourceItem();
            rs.setId("rule_belong");
            rs.setTitle(RULE_BELONG_TITLE);
            rs.setResourceType(AuthorityConst.ResourceType.RESOURCE);
            rsList.add((Resource)rs);
        }
        if ("-".equals(parentcode) || "rule_direct_children".equals(parentcode)) {
            rs = new ResourceItem();
            rs.setId("rule_direct_children");
            rs.setTitle(RULE_BELONG_DIRECT_CHILDREN_TITLE);
            rs.setResourceType(AuthorityConst.ResourceType.RESOURCE);
            rsList.add((Resource)rs);
        }
        if ("-".equals(parentcode) || "rule_all_children".equals(parentcode)) {
            rs = new ResourceItem();
            rs.setId("rule_all_children");
            rs.setTitle(RULE_BELONG_ALL_CHILDREN_TITLE);
            rs.setResourceType(AuthorityConst.ResourceType.RESOURCE);
            rsList.add((Resource)rs);
        }
        if ("-".equals(parentcode) || "rule_both".equals(parentcode)) {
            rs = new ResourceItem();
            rs.setId("rule_both");
            rs.setTitle(RULE_BOTH_TITLE);
            rs.setResourceType(AuthorityConst.ResourceType.RESOURCE);
            rsList.add((Resource)rs);
        }
        if ("-".equals(parentcode) || "rule_both_direct_childre".equals(parentcode)) {
            rs = new ResourceItem();
            rs.setId("rule_both_direct_childre");
            rs.setTitle(RULE_BOTH_DIRECT_CHILDREN_TITLE);
            rs.setResourceType(AuthorityConst.ResourceType.RESOURCE);
            rsList.add((Resource)rs);
        }
        if ("-".equals(parentcode) || "rule_both_all_children".equals(parentcode)) {
            rs = new ResourceItem();
            rs.setId("rule_both_all_children");
            rs.setTitle(RULE_BOTH_ALL_CHILDREN_TITLE);
            rs.setResourceType(AuthorityConst.ResourceType.RESOURCE);
            rsList.add((Resource)rs);
        }
        return rsList;
    }
}

