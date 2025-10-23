/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 */
package com.jiuqi.nr.task.form.service.check;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.form.dto.CheckResult;
import com.jiuqi.nr.task.form.dto.ErrorData;
import com.jiuqi.nr.task.form.dto.FormDesignerDTO;
import com.jiuqi.nr.task.form.dto.FormParamType;
import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import com.jiuqi.nr.task.form.ext.face.ConfigExt;
import com.jiuqi.nr.task.form.region.dto.DataRegionSettingDTO;
import com.jiuqi.nr.task.form.service.IConfigExtCheckService;
import com.jiuqi.nr.task.form.service.IFormCheckService;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class RegionCheckService
implements IFormCheckService {
    @Autowired
    private IConfigExtCheckService configExtCheckService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;

    private List<ConfigDTO> getConfigExtList(List<DataRegionSettingDTO> regionSetting) {
        return regionSetting.stream().filter(f -> f.getConfigData() != null).map(ConfigExt::getConfigData).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private CheckResult check(String formKey, List<DataRegionSettingDTO> datas) {
        CheckResult checkResult = CheckResult.successResult();
        List dataRegionDefines = this.designTimeViewController.listDataRegionByForm(formKey);
        boolean existFixRegion = dataRegionDefines.stream().anyMatch(r -> r.getRegionKind().getValue() == DataRegionKind.DATA_REGION_SIMPLE.getValue());
        Set regionKeys = dataRegionDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        for (DataRegionSettingDTO region : datas) {
            if (existFixRegion && DataRegionKind.DATA_REGION_SIMPLE.getValue() == region.getRegionKind().intValue() && Constants.DataStatus.NEW.equals((Object)region.getStatus())) {
                ErrorData errorData = this.error("\u56fa\u5b9a\u533a\u57df\u91cd\u590d\uff0c\u53ea\u80fd\u5b58\u5728\u4e00\u4e2a", region);
                checkResult.addErrorData(errorData);
            }
            if (DataRegionKind.DATA_REGION_SIMPLE.equals((Object)DataRegionKind.forValue((int)region.getRegionKind())) && Constants.DataStatus.DELETE.equals((Object)region.getStatus())) {
                checkResult.addErrorData(this.error("\u56fa\u5b9a\u533a\u57df\u7981\u6b62\u5220\u9664", region));
            }
            if (region.getRegionLeft() > region.getRegionRight()) {
                checkResult.addErrorData(this.error("\u533a\u57df\u5de6\u8fb9\u754c\u5927\u4e8e\u53f3\u8fb9\u754c", region));
            }
            if (region.getRegionTop() > region.getRegionBottom()) {
                checkResult.addErrorData(this.error("\u533a\u57df\u4e0a\u8fb9\u754c\u5927\u4e8e\u4e0b\u8fb9\u754c", region));
            }
            if (!Constants.DataStatus.MODIFY.equals((Object)region.getStatus()) || regionKeys.contains(region.getKey())) continue;
            checkResult.addErrorData(this.error("\u4fee\u6539\u7684\u533a\u57df\u4e0d\u5b58\u5728", region));
        }
        return checkResult;
    }

    private ErrorData error(String message, DataRegionSettingDTO region) {
        ErrorData errorData = new ErrorData();
        errorData.setParamType(FormParamType.REGION);
        errorData.setMessage(message);
        errorData.setKey(region.getKey());
        errorData.setTitle(region.getTitle());
        return errorData;
    }

    @Override
    public CheckResult doCheck(FormDesignerDTO formDesigner) {
        String formKey = formDesigner.getForm().getKey();
        List<DataRegionSettingDTO> regionSetting = formDesigner.getRegionSetting();
        if (CollectionUtils.isEmpty(regionSetting)) {
            return CheckResult.successResult();
        }
        CheckResult check = this.check(formKey, regionSetting);
        CheckResult checkExt = this.configExtCheckService.checkRegionConfigs(formKey, this.getConfigExtList(regionSetting));
        if (checkExt.isError()) {
            check.addErrorData(checkExt.getErrorData());
        }
        return check;
    }

    @Override
    public CheckResult doCheck(String formKey) {
        return null;
    }
}

