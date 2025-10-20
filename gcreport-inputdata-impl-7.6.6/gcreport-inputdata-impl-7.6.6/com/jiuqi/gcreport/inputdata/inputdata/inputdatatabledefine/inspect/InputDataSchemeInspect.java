/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gc.inspector.common.TaskStateEnum
 *  com.jiuqi.gc.inspector.domain.InspectResultVO
 *  com.jiuqi.gc.inspector.intf.InspectBaseItem
 *  com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService
 *  com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeCacheService
 *  com.jiuqi.gcreport.consolidatedsystem.service.impl.InputDataSchemeServiceImpl
 *  com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.inputdata.inputdata.inputdatatabledefine.inspect;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gc.inspector.common.TaskStateEnum;
import com.jiuqi.gc.inspector.domain.InspectResultVO;
import com.jiuqi.gc.inspector.intf.InspectBaseItem;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.impl.InputDataSchemeServiceImpl;
import com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO;
import com.jiuqi.gcreport.inputdata.inputdata.inputdatatabledefine.InputDataTableDefineService;
import com.jiuqi.gcreport.inputdata.inputdata.inputdatatabledefine.inspect.dto.InputDataInspectResultDTO;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InputDataSchemeInspect
implements InspectBaseItem {
    @Autowired
    private InputDataSchemeServiceImpl inputDataSchemeService;
    @Autowired
    private ConsolidatedSystemService consolidatedSystemService;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private InputDataTableDefineService inputDataTableDefineService;
    @Autowired
    private InputDataSchemeCacheService inputDataSchemeCacheService;

    public String getGroup() {
        return "inputdata-inspect";
    }

    public String getName() {
        return "inputdata-datascheme";
    }

    public String getTitle() {
        return "\u6570\u636e\u65b9\u6848\u4e0e\u5185\u90e8\u8868\u5173\u8054\u5173\u7cfb\u68c0\u67e5";
    }

    public InspectResultVO executeInspect(Map<String, Object> params) {
        InputDataInspectResultDTO inputDataInspectResultDTO = this.getInspectResult();
        return inputDataInspectResultDTO.getInspectResultVO();
    }

    public InspectResultVO executeFix(Map<String, Object> params) {
        InputDataInspectResultDTO inputDataInspectResultDTO = this.getInspectResult();
        List<String> dataSchemeKeys = inputDataInspectResultDTO.getDataSchemeKeys();
        this.inputDataSchemeCacheService.clearAllCache();
        if (CollectionUtils.isEmpty(dataSchemeKeys)) {
            return inputDataInspectResultDTO.getInspectResultVO();
        }
        for (String dataSchemeKey : dataSchemeKeys) {
            InputDataSchemeVO inputDataScheme = this.inputDataSchemeService.getInputDataSchemeByDataSchemeKey(dataSchemeKey);
            if (null != inputDataScheme) {
                this.inputDataSchemeService.deleteInputDataSchemeByDataSchemeKey(dataSchemeKey);
            }
            this.inputDataTableDefineService.createInputDataTableByDataSchemeKey(dataSchemeKey);
        }
        return inputDataInspectResultDTO.getInspectResultVO();
    }

    private InputDataInspectResultDTO getInspectResult() {
        List consolidatedSystems = this.consolidatedSystemService.getConsolidatedSystemEOS();
        InputDataInspectResultDTO inputDataInspectResultDTO = new InputDataInspectResultDTO();
        InspectResultVO inspectResult = new InspectResultVO();
        if (CollectionUtils.isEmpty((Collection)consolidatedSystems)) {
            inspectResult.setMessage("\u672a\u914d\u7f6e\u5408\u5e76\u4f53\u7cfb\uff0c\u4e0d\u9700\u8981\u8fdb\u884c\u4fee\u590d\u3002");
            inspectResult.setTaskState(TaskStateEnum.SUCCESS);
            inputDataInspectResultDTO.setInspectResultVO(inspectResult);
            return inputDataInspectResultDTO;
        }
        ArrayList<String> dataSchemeKeys = new ArrayList<String>();
        ArrayList<Map> cols = new ArrayList<Map>();
        ArrayList rows = new ArrayList();
        cols.add(new JSONObject().put("title", (Object)"\u5408\u5e76\u4f53\u7cfb").put("key", (Object)"systemName").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u6570\u636e\u65b9\u6848").put("key", (Object)"dataScheme").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u5408\u5e76\u4f53\u7cfb").put("key", (Object)"systemName").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u4fe1\u606f").put("key", (Object)"messageInfo").toMap());
        for (ConsolidatedSystemEO consolidatedSystem : consolidatedSystems) {
            String dataSchemeKey = consolidatedSystem.getDataSchemeKey();
            HashMap<String, String> messageInfo = new HashMap<String, String>();
            if (StringUtils.isEmpty((String)dataSchemeKey)) {
                messageInfo.put("systemName", consolidatedSystem.getSystemName());
                messageInfo.put("dataSchemeKey", dataSchemeKey);
                messageInfo.put("messageInfo", "\u5408\u5e76\u4f53\u7cfb\u672a\u7ed1\u5b9a\u7684\u6570\u636e\u65b9\u6848");
                rows.add(messageInfo);
                continue;
            }
            DataScheme dataScheme = this.iRuntimeDataSchemeService.getDataScheme(dataSchemeKey);
            if (null == dataScheme) {
                messageInfo.put("dataScheme", consolidatedSystem.getSystemName());
                messageInfo.put("dataSchemeKey", dataSchemeKey);
                messageInfo.put("messageInfo", "\u5408\u5e76\u4f53\u7cfb\u7ed1\u5b9a\u7684\u6570\u636e\u65b9\u6848\u4e0d\u5b58\u5728");
                rows.add(messageInfo);
                continue;
            }
            InputDataSchemeVO inputDataScheme = this.inputDataSchemeService.getInputDataSchemeByDataSchemeKey(dataSchemeKey);
            if (null == inputDataScheme && null != dataScheme) {
                messageInfo.put("systemName", consolidatedSystem.getSystemName());
                messageInfo.put("dataScheme", dataScheme.getTitle());
                messageInfo.put("messageInfo", String.format("\u6570\u636e\u65b9\u6848\u672a\u7ed1\u5b9a\u5185\u90e8\u8868\u4fee\u590d", new Object[0]));
                dataSchemeKeys.add(dataSchemeKey);
                rows.add(messageInfo);
                continue;
            }
            DataTable dataTable = this.iRuntimeDataSchemeService.getDataTable(inputDataScheme.getTableKey());
            if (null != dataTable) continue;
            messageInfo.put("systemName", consolidatedSystem.getSystemName());
            messageInfo.put("dataScheme", dataScheme.getTitle());
            messageInfo.put("messageInfo", String.format("\u5185\u90e8\u8868\u4e0e\u6570\u636e\u65b9\u6848\u5173\u8054\u5173\u7cfb\u4e2d\u83b7\u53d6\u6570\u636e\u8868\u4e3a\u7a7a\uff0cdataTableKey: [%s] \uff0ctableCode:[%s]", inputDataScheme.getTableKey(), inputDataScheme.getTableCode()));
            dataSchemeKeys.add(dataSchemeKey);
            rows.add(messageInfo);
        }
        HashMap resultData = new HashMap();
        resultData.put("rows", rows);
        resultData.put("cols", cols);
        inspectResult.setResult(resultData);
        inspectResult.setTaskState(rows.size() > 0 ? TaskStateEnum.FAILED : TaskStateEnum.SUCCESS);
        inputDataInspectResultDTO.setInspectResultVO(inspectResult);
        inputDataInspectResultDTO.setDataSchemeKeys(dataSchemeKeys);
        return inputDataInspectResultDTO;
    }
}

