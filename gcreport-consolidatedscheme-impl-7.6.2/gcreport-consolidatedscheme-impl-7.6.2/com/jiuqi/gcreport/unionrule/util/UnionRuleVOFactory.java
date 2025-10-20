/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool
 *  com.jiuqi.gcreport.unionrule.constant.RuleConst
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 */
package com.jiuqi.gcreport.unionrule.util;

import com.google.common.collect.Lists;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool;
import com.jiuqi.gcreport.unionrule.base.RuleManagerFactory;
import com.jiuqi.gcreport.unionrule.base.RuleType;
import com.jiuqi.gcreport.unionrule.base.UnionRuleManager;
import com.jiuqi.gcreport.unionrule.constant.RuleConst;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class UnionRuleVOFactory {
    private UnionRuleEO unionRuleEntity;
    private boolean noSetting;

    private UnionRuleVOFactory(UnionRuleEO unionRuleEntity) {
        this.unionRuleEntity = unionRuleEntity;
    }

    public static UnionRuleVO newInstanceByEntity(UnionRuleEO unionRuleEntity) {
        UnionRuleVOFactory unionRuleVOFactory = new UnionRuleVOFactory(unionRuleEntity);
        return unionRuleVOFactory.convert();
    }

    public static UnionRuleVO newNoSettingInstanceByEntity(UnionRuleEO unionRuleEntity) {
        UnionRuleVOFactory unionRuleVOFactory = new UnionRuleVOFactory(unionRuleEntity);
        unionRuleVOFactory.noSetting = true;
        return unionRuleVOFactory.convert();
    }

    private UnionRuleVO convert() {
        if (Objects.isNull((Object)this.unionRuleEntity)) {
            return null;
        }
        if (!Objects.equals(1, this.unionRuleEntity.getLeafFlag())) {
            if (this.unionRuleEntity.getParentId().equals(RuleConst.ROOT_PARENT_ID)) {
                this.unionRuleEntity.setRuleType("root");
            } else {
                this.unionRuleEntity.setRuleType("group");
            }
        }
        UnionRuleVO ruleVO = new UnionRuleVO();
        BeanUtils.copyProperties((Object)this.unionRuleEntity, ruleVO);
        ruleVO.setLeafFlag(Boolean.valueOf(Objects.equals(this.unionRuleEntity.getLeafFlag(), 1)));
        ruleVO.setStartFlag(Boolean.valueOf(Objects.equals(this.unionRuleEntity.getStartFlag(), 1)));
        ruleVO.setInitTypeFlag(Boolean.valueOf(Objects.equals(this.unionRuleEntity.getInitTypeFlag(), 1)));
        ruleVO.setEnableToleranceFlag(Boolean.valueOf(Objects.equals(this.unionRuleEntity.getEnableToleranceFlag(), 1)));
        if (!Objects.equals(1, this.unionRuleEntity.getLeafFlag()) && !StringUtils.isEmpty((String)this.unionRuleEntity.getRuleType())) {
            String root = "root";
            String group = "group";
            if (root.equalsIgnoreCase(this.unionRuleEntity.getRuleType())) {
                ruleVO.setRuleTypeDescription("\u6839\u8282\u70b9");
            } else if (group.equalsIgnoreCase(this.unionRuleEntity.getRuleType())) {
                ruleVO.setRuleTypeDescription("\u5206\u7ec4");
            }
        } else {
            if (!StringUtils.isEmpty((String)this.unionRuleEntity.getRuleType())) {
                RuleManagerFactory ruleManagerFactory = (RuleManagerFactory)SpringContextUtils.getBean(RuleManagerFactory.class);
                RuleType ruleType = ruleManagerFactory.getRuleType(this.unionRuleEntity.getRuleType());
                if (Objects.isNull(ruleType)) {
                    throw new BusinessRuntimeException("\u672a\u627e\u5230" + this.unionRuleEntity.getRuleType() + "\u89c4\u5219\u7c7b\u578b");
                }
                ruleVO.setRuleTypeDescription(ruleType.name());
                if (!this.noSetting) {
                    UnionRuleManager ruleManager = ruleManagerFactory.getUnionRuleManager(this.unionRuleEntity.getRuleType());
                    ruleVO.setJsonString(ruleManager.getRuleHandler().convertJsonStringWhenEO2VO(this.unionRuleEntity));
                }
            }
            ruleVO.setBusinessTypeCode(GcBaseDataCenterTool.getInstance().convertGcBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_GCBUSINESSTYPE", this.unionRuleEntity.getBusinessTypeCode())));
            ruleVO.setApplyGcUnits(StringUtils.isEmpty((String)this.unionRuleEntity.getApplyGcUnits()) ? Collections.emptyList() : Arrays.stream(this.unionRuleEntity.getApplyGcUnits().split(",")).collect(Collectors.toList()));
            ArrayList units = new ArrayList();
            ruleVO.getApplyGcUnits().forEach(unitCode -> {
                OrgToJsonVO unit = GcOrgBaseTool.getInstance().getOrgByCode(unitCode);
                if (Objects.nonNull(unit)) {
                    HashMap<String, String> orgMap = new HashMap<String, String>();
                    orgMap.put("code", (String)unitCode);
                    orgMap.put("title", unit.getTitle());
                    units.add(orgMap);
                }
            });
            ruleVO.setApplyGcUnitMap(units);
        }
        ruleVO.setChildren((List)Lists.newArrayList());
        return ruleVO;
    }
}

