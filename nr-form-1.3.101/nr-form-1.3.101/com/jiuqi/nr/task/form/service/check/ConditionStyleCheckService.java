/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 */
package com.jiuqi.nr.task.form.service.check;

import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.form.dto.CheckResult;
import com.jiuqi.nr.task.form.dto.ConditionStyleDTO;
import com.jiuqi.nr.task.form.dto.DataCore;
import com.jiuqi.nr.task.form.dto.ErrorData;
import com.jiuqi.nr.task.form.dto.FormDesignerDTO;
import com.jiuqi.nr.task.form.dto.FormParamType;
import com.jiuqi.nr.task.form.link.dto.DataLinkSettingDTO;
import com.jiuqi.nr.task.form.service.IConditionStyleService;
import com.jiuqi.nr.task.form.service.IFormCheckService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class ConditionStyleCheckService
implements IFormCheckService {
    @Autowired
    private IConditionStyleService conditionStyleService;

    @Override
    public CheckResult doCheck(FormDesignerDTO formDesigner) {
        CheckResult checkResult = CheckResult.successResult();
        CheckResult perhapsCheck = this.checkConditionStyle(formDesigner.getForm().getKey(), formDesigner.getConditionStyle());
        if (perhapsCheck.isError()) {
            checkResult.addErrorData(perhapsCheck.getErrorData());
        }
        return checkResult;
    }

    @Override
    public CheckResult doCheck(String formKey) {
        return null;
    }

    private CheckResult checkConditionStyle(String formKey, List<ConditionStyleDTO> allConditionStyle) {
        CheckResult paramCheck;
        CheckResult checkResult = CheckResult.successResult();
        CheckResult statusCheck = this.checkConditionStyleStatus(formKey, allConditionStyle);
        if (statusCheck.isError()) {
            checkResult.addErrorData(statusCheck.getErrorData());
        }
        if ((paramCheck = this.checkConditionStyleParam(formKey, allConditionStyle)).isError()) {
            checkResult.addErrorData(paramCheck.getErrorData());
        }
        return checkResult;
    }

    private CheckResult checkConditionStyleStatus(String formKey, List<ConditionStyleDTO> allConditionStyle) {
        CheckResult checkResult = CheckResult.successResult();
        Set<Object> existKeys = new HashSet();
        List<ConditionStyleDTO> conditionStyleInDB = this.conditionStyleService.getByForm(formKey);
        if (!CollectionUtils.isEmpty(conditionStyleInDB)) {
            existKeys = conditionStyleInDB.stream().map(ConditionStyleDTO::getKey).collect(Collectors.toSet());
        }
        for (ConditionStyleDTO conditionStyle : allConditionStyle) {
            if (!Constants.DataStatus.DELETE.equals((Object)conditionStyle.getStatus()) && !Constants.DataStatus.MODIFY.equals((Object)conditionStyle.getStatus()) || existKeys.contains(conditionStyle.getKey())) continue;
            checkResult.addErrorData(this.error("\u6761\u4ef6\u6837\u5f0f\u4e0d\u5b58\u5728", conditionStyle));
        }
        return checkResult;
    }

    private CheckResult checkConditionStyleParam(String formKey, List<ConditionStyleDTO> allConditionStyle) {
        CheckResult checkResult = CheckResult.successResult();
        for (ConditionStyleDTO conditionStyle : allConditionStyle) {
            if (!StringUtils.hasText(conditionStyle.getFormKey())) {
                conditionStyle.setFormKey(formKey);
            }
            if (conditionStyle.getPosY() != 0 && conditionStyle.getPosX() != 0) continue;
            checkResult.addErrorData(this.error("\u6761\u4ef6\u6837\u5f0f\u5750\u6807\u4e0d\u6b63\u786e", conditionStyle));
        }
        return checkResult;
    }

    private void checkConditionStyle(List<DataLinkSettingDTO> links, List<ConditionStyleDTO> allConditionStyle) {
        if (!CollectionUtils.isEmpty(allConditionStyle)) {
            ArrayList<ConditionStyleDTO> deleteConditionStyle = new ArrayList<ConditionStyleDTO>();
            if (!CollectionUtils.isEmpty(links)) {
                Map<String, DataLinkSettingDTO> linkMap = links.stream().collect(Collectors.toMap(DataCore::getKey, v -> v));
                for (ConditionStyleDTO conditionalStyle : allConditionStyle) {
                    String linkKey = conditionalStyle.getLinkKey();
                    if (StringUtils.hasText(linkKey)) {
                        if (linkMap.keySet().contains(linkKey) && conditionalStyle.getStatus() == Constants.DataStatus.NONE) {
                            conditionalStyle.setPosX(linkMap.get(linkKey).getPosX());
                            conditionalStyle.setPosY(linkMap.get(linkKey).getPosY());
                            conditionalStyle.setState(3);
                            continue;
                        }
                        deleteConditionStyle.add(conditionalStyle);
                        continue;
                    }
                    deleteConditionStyle.add(conditionalStyle);
                }
            } else {
                deleteConditionStyle.addAll(deleteConditionStyle);
            }
            if (!CollectionUtils.isEmpty(deleteConditionStyle)) {
                for (ConditionStyleDTO deleteCs : deleteConditionStyle) {
                    deleteCs.setState(2);
                }
            }
        }
    }

    private ErrorData error(String message, ConditionStyleDTO conditionStyle) {
        ErrorData errorData = new ErrorData();
        errorData.setParamType(FormParamType.CONDITION_STYLE);
        errorData.setMessage(message);
        errorData.setKey(conditionStyle.getKey());
        return errorData;
    }
}

