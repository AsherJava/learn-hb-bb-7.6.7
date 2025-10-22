/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.formsetting.api.FormSettingClient
 *  com.jiuqi.gcreport.formsetting.vo.FormSettingVO
 *  com.jiuqi.gcreport.formsetting.vo.SettingVO
 *  com.jiuqi.gcreport.inputdata.util.InputDataNameProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.formsetting.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.formsetting.api.FormSettingClient;
import com.jiuqi.gcreport.formsetting.service.FormSettingService;
import com.jiuqi.gcreport.formsetting.vo.FormSettingVO;
import com.jiuqi.gcreport.formsetting.vo.SettingVO;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Primary
@RestController
public class FormSettingController
implements FormSettingClient {
    private final FormSettingService settingService;
    private final InputDataNameProvider inputDataNameProvider;
    private final IDataDefinitionRuntimeController runtimeController;

    public FormSettingController(FormSettingService settingService, InputDataNameProvider inputDataNameProvider, IDataDefinitionRuntimeController runtimeController) {
        this.settingService = settingService;
        this.inputDataNameProvider = inputDataNameProvider;
        this.runtimeController = runtimeController;
    }

    public BusinessResponseEntity<String> create(@RequestBody FormSettingVO formV) {
        return BusinessResponseEntity.ok((Object)this.settingService.createForm(formV));
    }

    public BusinessResponseEntity<String> updateSetting(@RequestBody SettingVO setting) {
        this.settingService.setForm(setting);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f\u3002");
    }

    public BusinessResponseEntity<TableDefine> updateSetting(String fieldId) {
        return BusinessResponseEntity.ok((Object)this.settingService.queryOwnTable(fieldId));
    }

    public BusinessResponseEntity<String> getInputDataNameByTaskId(String taskId) {
        return BusinessResponseEntity.ok((Object)this.inputDataNameProvider.getTableCodeByTaskId(taskId));
    }

    public BusinessResponseEntity<Map<String, Object>> queryAllFieldDefine(String tabloCode) {
        try {
            DesignDataTable dataTable = ((IDesignDataSchemeService)SpringContextUtils.getBean(IDesignDataSchemeService.class)).getDataTableByCode(tabloCode);
            Assert.isNotNull((Object)dataTable, (String)("\u672a\u627e\u5230\u8868\u3010" + tabloCode + "\u3011\u7684\u8868\u5b9a\u4e49\u3002"), (Object[])new Object[0]);
            List fields = ((IDesignDataSchemeService)SpringContextUtils.getBean(IDesignDataSchemeService.class)).getDataFieldByTable(dataTable.getKey());
            HashMap retMap = new HashMap(16);
            retMap.put("allFields", fields);
            retMap.put("tableDefine", dataTable);
            return BusinessResponseEntity.ok(retMap);
        }
        catch (Exception e) {
            return BusinessResponseEntity.error((Throwable)e);
        }
    }
}

