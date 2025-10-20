/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.PageDTO
 */
package com.jiuqi.va.biz.domain.commrule;

import com.jiuqi.va.biz.domain.commrule.BizCommonRuleDO;
import com.jiuqi.va.biz.domain.commrule.BizRuleDTO;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.mapper.domain.PageDTO;
import java.util.List;
import java.util.Map;

public class BizCommonRuleVO
extends BizCommonRuleDO
implements PageDTO {
    private List<BizRuleDTO> rules;
    private List<String> rulenames;
    private String biztype;
    private String definecode;
    private String definetitle;
    private ModelDefine modelDefine;
    private List<Integer> updateindex;
    private int limit;
    private int offset;
    private boolean pagination;
    private boolean curUser;
    private Map<String, Object> queryParam;

    public List<BizRuleDTO> getRules() {
        return this.rules;
    }

    public void setRules(List<BizRuleDTO> rules) {
        this.rules = rules;
    }

    public List<String> getRulenames() {
        return this.rulenames;
    }

    public void setRulenames(List<String> rulenames) {
        this.rulenames = rulenames;
    }

    @Override
    public String getBiztype() {
        return this.biztype;
    }

    @Override
    public void setBiztype(String biztype) {
        this.biztype = biztype;
    }

    @Override
    public String getDefinecode() {
        return this.definecode;
    }

    @Override
    public void setDefinecode(String definecode) {
        this.definecode = definecode;
    }

    @Override
    public String getDefinetitle() {
        return this.definetitle;
    }

    @Override
    public void setDefinetitle(String definetitle) {
        this.definetitle = definetitle;
    }

    public ModelDefine getModelDefine() {
        return this.modelDefine;
    }

    public void setModelDefine(ModelDefine modelDefine) {
        this.modelDefine = modelDefine;
    }

    public boolean isPagination() {
        return this.pagination;
    }

    public int getOffset() {
        return this.offset;
    }

    public int getLimit() {
        return this.limit;
    }

    public boolean isCurUser() {
        return this.curUser;
    }

    public void setCurUser(boolean curUser) {
        this.curUser = curUser;
    }

    public Map<String, Object> getQueryParam() {
        return this.queryParam;
    }

    public void setQueryParam(Map<String, Object> queryParam) {
        this.queryParam = queryParam;
    }

    public List<Integer> getUpdateindex() {
        return this.updateindex;
    }

    public void setUpdateindex(List<Integer> updateindex) {
        this.updateindex = updateindex;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setPagination(boolean pagination) {
        this.pagination = pagination;
    }
}

