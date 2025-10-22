/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.gcreport.common.systemoption.GcNormalOptionDeclare
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.TaskInfoVO
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionalItemValue
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 */
package com.jiuqi.gc.financialcubes.systemoption.group;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.gcreport.common.systemoption.GcNormalOptionDeclare;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.TaskInfoVO;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionalItemValue;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class GcFinancialCubesDeclare
extends GcNormalOptionDeclare {
    public String getId() {
        return "FINANCIAL_CUBES";
    }

    public String getTitle() {
        return "\u591a\u7ef4";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(this.getFinancialCubesMergeSummaryEnableOption());
        optionItems.add(this.getFinancialCubesSplitOption());
        optionItems.add(this.getFinancialCubesDescribeOption());
        List<ISystemOptionalValue> orgTypeOptionalValues = this.listFinancialCubesOrgTypeOptionalValues();
        optionItems.add(this.getFinancialCubesOrgTypeOption(FinancialCubesPeriodTypeEnum.Y, orgTypeOptionalValues));
        optionItems.add(this.getFinancialCubesOrgTypeOption(FinancialCubesPeriodTypeEnum.J, orgTypeOptionalValues));
        optionItems.add(this.getFinancialCubesOrgTypeOption(FinancialCubesPeriodTypeEnum.H, orgTypeOptionalValues));
        optionItems.add(this.getFinancialCubesOrgTypeOption(FinancialCubesPeriodTypeEnum.N, orgTypeOptionalValues));
        return optionItems;
    }

    private ISystemOptionItem getFinancialCubesMergeSummaryEnableOption() {
        SystemOptionItem systemOptionItem = new SystemOptionItem();
        systemOptionItem.setId("FINANCIAL_CUBES_MERGESUMMARY_ENABLE");
        systemOptionItem.setTitle("\u542f\u7528\u975e\u672b\u7ea7\u8ba1\u7b97");
        systemOptionItem.setDefaultValue("0");
        systemOptionItem.setEditMode(SystemOptionConst.EditMode.TRUE_FALSE);
        return systemOptionItem;
    }

    private ISystemOptionItem getFinancialCubesSplitOption() {
        SystemOptionItem systemOptionItem = new SystemOptionItem();
        systemOptionItem.setId("FinancialCubesSplitOption");
        systemOptionItem.setTitle("\u591a\u7ef4\u5e95\u7a3f\u5bf9\u5e94\u5355\u4f4d\u7c7b\u578b");
        systemOptionItem.setDefaultValue("");
        systemOptionItem.setEditMode(SystemOptionConst.EditMode.GROUP);
        return systemOptionItem;
    }

    private ISystemOptionItem getFinancialCubesDescribeOption() {
        SystemOptionItem systemOptionItem = new SystemOptionItem();
        systemOptionItem.setId("FinancialCubesDescribeOption");
        systemOptionItem.setTitle(null);
        systemOptionItem.setDefaultValue("");
        systemOptionItem.setEditMode(SystemOptionConst.EditMode.DESCRIBE);
        systemOptionItem.setDescribe("\u8bf4\u660e\uff1a\u8bbe\u7f6e\u4e0d\u540c\u65f6\u671f\u7c7b\u578b\u591a\u7ef4\u5e95\u7a3f\u5bf9\u5e94\u7684\u673a\u6784\u7c7b\u578b\uff0c\u7528\u4e8e\u591a\u7ef4\u5e95\u7a3f\u4e2d\u6309\u7167\u8bbe\u7f6e\u7684\u673a\u6784\u7c7b\u578b\u7ea7\u6b21\u6811\u5f62\u6c47\u603b\u751f\u6210\u5408\u5e76\u5355\u4f4d\u7684\u6570\u636e\u3002");
        return systemOptionItem;
    }

    private ISystemOptionItem getFinancialCubesOrgTypeOption(FinancialCubesPeriodTypeEnum typeEnum, List<ISystemOptionalValue> orgTypeOptionalValues) {
        SystemOptionItem systemOptionItem = new SystemOptionItem();
        systemOptionItem.setId("FINANCIAL_CUBES_ORG_TYPE" + typeEnum.getCode());
        systemOptionItem.setTitle(typeEnum.getName() + "\u591a\u7ef4\u5e95\u7a3f");
        systemOptionItem.setDefaultValue(null);
        systemOptionItem.setEditMode(SystemOptionConst.EditMode.DROP_DOWN);
        systemOptionItem.setOptionalValues(orgTypeOptionalValues);
        return systemOptionItem;
    }

    private List<ISystemOptionalValue> listFinancialCubesOrgTypeOptionalValues() {
        List allBoundTaskVOs;
        ConsolidatedTaskService consolidatedTaskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        try {
            allBoundTaskVOs = consolidatedTaskService.getAllBoundTaskVOs();
        }
        catch (Exception e) {
            return Collections.emptyList();
        }
        if (CollectionUtils.isEmpty((Collection)allBoundTaskVOs)) {
            return Collections.emptyList();
        }
        HashSet<String> orgTypeSet = new HashSet<String>();
        for (ConsolidatedTaskVO taskVO : allBoundTaskVOs) {
            TaskInfoVO inputTaskInfo = taskVO.getInputTaskInfo();
            if (inputTaskInfo != null && !StringUtils.isEmpty((String)inputTaskInfo.getUnitDefine())) {
                orgTypeSet.add(inputTaskInfo.getUnitDefine());
            }
            if (CollectionUtils.isEmpty((Collection)taskVO.getManageTaskInfos())) continue;
            for (TaskInfoVO manageTask : taskVO.getManageTaskInfos()) {
                if (manageTask == null || StringUtils.isEmpty((String)manageTask.getUnitDefine())) continue;
                orgTypeSet.add(manageTask.getUnitDefine());
            }
        }
        if (orgTypeSet.isEmpty()) {
            return Collections.emptyList();
        }
        OrgCategoryClient orgCategoryClient = (OrgCategoryClient)SpringContextUtils.getBean(OrgCategoryClient.class);
        List orgCategoryDOList = orgCategoryClient.list(new OrgCategoryDO()).getRows();
        return orgCategoryDOList.stream().filter(item -> orgTypeSet.contains(item.getName())).map(item -> {
            SystemOptionalItemValue systemOptionalItemValue = new SystemOptionalItemValue();
            systemOptionalItemValue.setTitle(item.getTitle());
            systemOptionalItemValue.setValue(item.getName());
            return systemOptionalItemValue;
        }).collect(Collectors.toList());
    }
}

