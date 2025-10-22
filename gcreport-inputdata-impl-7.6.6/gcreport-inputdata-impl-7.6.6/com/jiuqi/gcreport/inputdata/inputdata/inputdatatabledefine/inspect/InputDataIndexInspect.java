/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gc.inspector.common.TaskStateEnum
 *  com.jiuqi.gc.inspector.domain.InspectResultVO
 *  com.jiuqi.gc.inspector.intf.InspectBaseItem
 *  com.jiuqi.gcreport.consolidatedsystem.service.impl.InputDataSchemeServiceImpl
 *  com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.inputdata.inputdata.inputdatatabledefine.inspect;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gc.inspector.common.TaskStateEnum;
import com.jiuqi.gc.inspector.domain.InspectResultVO;
import com.jiuqi.gc.inspector.intf.InspectBaseItem;
import com.jiuqi.gcreport.consolidatedsystem.service.impl.InputDataSchemeServiceImpl;
import com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.inputdata.inputdata.inputdatatabledefine.InputDataTableDefineService;
import com.jiuqi.gcreport.inputdata.inputdata.inputdatatabledefine.inspect.dto.InputDataInspectResultDTO;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InputDataIndexInspect
implements InspectBaseItem {
    @Autowired
    private InputDataTableDefineService inputDataTableDefineService;
    @Autowired
    private InputDataSchemeServiceImpl inputDataSchemeService;
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;

    public String getGroup() {
        return "inputdata-inspect";
    }

    public String getName() {
        return "inputdata-index";
    }

    public String getTitle() {
        return "\u5185\u90e8\u8868\u7d22\u5f15\u68c0\u67e5";
    }

    public InspectResultVO executeInspect(Map<String, Object> params) {
        InputDataInspectResultDTO inputDataInspectResultDTO = this.getInspectResult();
        return inputDataInspectResultDTO.getInspectResultVO();
    }

    public InspectResultVO executeFix(Map<String, Object> params) {
        InputDataInspectResultDTO inputDataInspectResultDTO = this.getInspectResult();
        Map<DesignDataTable, List<DBIndex>> dbIndexGroupByDataTable = inputDataInspectResultDTO.getDbIndexGroupByDataTable();
        for (Map.Entry<DesignDataTable, List<DBIndex>> entry : dbIndexGroupByDataTable.entrySet()) {
            DesignDataTable dataTable = entry.getKey();
            List<DBIndex> dbIndexList = entry.getValue();
            this.inputDataTableDefineService.addInputDataIndexes(dataTable, dbIndexList);
        }
        return inputDataInspectResultDTO.getInspectResultVO();
    }

    private InputDataInspectResultDTO getInspectResult() {
        List inputDataSchemes = this.inputDataSchemeService.listInputDataScheme();
        InputDataInspectResultDTO inputDataInspectResultDTO = new InputDataInspectResultDTO();
        HashMap<DesignDataTable, List<DBIndex>> dbIndexGroupByDataTable = new HashMap<DesignDataTable, List<DBIndex>>();
        StringBuffer message = new StringBuffer();
        ArrayList<Map> cols = new ArrayList<Map>();
        ArrayList rows = new ArrayList();
        cols.add(new JSONObject().put("title", (Object)"\u7d22\u5f15\u540d\u79f0").put("key", (Object)"indexName").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u8868\u540d").put("key", (Object)"tableName").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u7d22\u5f15\u5173\u8054\u5b57\u6bb5").put("key", (Object)"columnFields").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u7d22\u5f15\u7c7b\u578b").put("key", (Object)"indexType").toMap());
        for (InputDataSchemeVO inputDataScheme : inputDataSchemes) {
            DesignDataTable dataTable = this.iDesignDataSchemeService.getDataTable(inputDataScheme.getTableKey());
            if (null == dataTable) {
                message.append(String.format("\u6570\u636e\u65b9\u6848\u4e0e\u5185\u90e8\u8868\u5173\u8054\u5173\u7cfb\u4e2d\u7ed1\u5b9a\u7684\u6570\u636e\u8868\u4e0d\u5b58\u5728\uff0c\u8bf7\u4fee\u590d\u6570\u636e\u65b9\u6848\u4e0e\u5185\u90e8\u8868\u5173\u8054\u5173\u7cfb\uff0ctableKey[%s]\n", inputDataScheme.getTableKey()));
                continue;
            }
            DesignDataScheme dataScheme = this.iDesignDataSchemeService.getDataScheme(inputDataScheme.getDataSchemeKey());
            if (null == dataScheme) {
                message.append(String.format("\u6570\u636e\u65b9\u6848\u4e0e\u5185\u90e8\u8868\u5173\u8054\u5173\u7cfb\u4e2d\u7ed1\u5b9a\u7684\u6570\u636e\u65b9\u6848\u4e0d\u5b58\u5728\uff0cdataSchemeKey[%s]\n", inputDataScheme.getDataSchemeKey()));
                continue;
            }
            List<DBIndex> dbIndexList = this.inputDataTableDefineService.listNeedAddInputDataIndex(dataTable);
            if (CollectionUtils.isEmpty(dbIndexList)) continue;
            for (DBIndex dbIndex : dbIndexList) {
                HashMap<String, String> indexInfoGroup = new HashMap<String, String>();
                indexInfoGroup.put("indexName", dbIndex.name());
                indexInfoGroup.put("tableName", dataTable.getCode());
                indexInfoGroup.put("columnFields", dbIndex.columnsFields().toString());
                indexInfoGroup.put("indexType", dbIndex.type().getTitle());
                rows.add(indexInfoGroup);
            }
            dbIndexGroupByDataTable.put(dataTable, dbIndexList);
        }
        InspectResultVO inspectResult = new InspectResultVO();
        HashMap resultData = new HashMap();
        resultData.put("rows", rows);
        resultData.put("cols", cols);
        inspectResult.setResult(resultData);
        inspectResult.setMessage(message.toString());
        inspectResult.setTaskState(rows.size() > 0 ? TaskStateEnum.FAILED : TaskStateEnum.SUCCESS);
        inputDataInspectResultDTO.setDbIndexGroupByDataTable(dbIndexGroupByDataTable);
        inputDataInspectResultDTO.setInspectResultVO(inspectResult);
        return inputDataInspectResultDTO;
    }
}

