/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.formulatracking.facade.FormulaTrackDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaTrackService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.object.BatchSqlUpdate
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.data.logic.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.data.logic.exeception.LogicMappingException;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.internal.service.IReviseCKDRECIDService;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.data.logic.web.async.RefreshCkdAsyncTaskExecutor;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.formulatracking.facade.FormulaTrackDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaTrackService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/ckd"})
public class CkdController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IReviseCKDRECIDService reviseCKDRECIDService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ICheckErrorDescriptionService checkErrorDescriptionService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IRuntimeFormulaTrackService formulaTrackService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    private static final Logger logger = LoggerFactory.getLogger(CkdController.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value={"/fix-ckd-formulacode/{formSchemeCode}"})
    public void fixCkdFmlCode(@PathVariable String formSchemeCode) {
        try {
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormschemeByCode(formSchemeCode);
            if (formSchemeDefine == null) {
                throw new LogicMappingException("\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728" + formSchemeCode);
            }
            Map<String, Map<String, String>> asdf = this.getTaskParamChange();
            HashMap<String, ParamUp> fcParamCache = new HashMap<String, ParamUp>();
            String ckdTableName = CheckTableNameUtil.getCKDTableName(formSchemeDefine.getFormSchemeCode());
            String sql = String.format("SELECT %s,%s FROM %s", "CKD_RECID", "CKD_FORMULACODE", ckdTableName);
            List maps = this.jdbcTemplate.queryForList(sql);
            if (!CollectionUtils.isEmpty(maps)) {
                BatchSqlUpdate batchSqlUpdate = new BatchSqlUpdate(Objects.requireNonNull(this.jdbcTemplate.getDataSource()), String.format("update %s set %s=? where %s=?", ckdTableName, "CKD_FORMULACODE", "CKD_RECID"));
                batchSqlUpdate.setTypes(new int[]{12, 12});
                HashMap ckdFmlMap = new HashMap();
                maps.forEach(o -> ckdFmlMap.put(String.valueOf(o.get("CKD_RECID")), String.valueOf(o.get("CKD_FORMULACODE"))));
                List<FormulaObj> allFml = this.getAllFml();
                List<DataLinkObj> allDl = this.getAllDl();
                Set curFcFml = allFml.stream().filter(o -> o.getFormSchemeKey().equals(formSchemeDefine.getKey())).map(FormulaObj::getKey).collect(Collectors.toSet());
                Set curFcDl = allDl.stream().filter(o -> o.getFormSchemeKey().equals(formSchemeDefine.getKey())).map(DataLinkObj::getUniqueCode).collect(Collectors.toSet());
                Map fmlMap = allFml.stream().collect(Collectors.toMap(FormulaObj::getKey, Function.identity(), (o1, o2) -> o2));
                Map dlMap = allDl.stream().collect(Collectors.toMap(DataLinkObj::getUniqueCode, Function.identity(), (o1, o2) -> o2));
                Map dlKeyMap = allDl.stream().collect(Collectors.toMap(DataLinkObj::getKey, Function.identity(), (o1, o2) -> o2));
                HashMap updateData = new HashMap();
                for (Map.Entry e : ckdFmlMap.entrySet()) {
                    DataLinkObj dataLinkObj;
                    ParamUp paramUp;
                    String dlCode;
                    FormulaObj formulaObj;
                    ParamUp paramUp2;
                    String fmlCode = (String)e.getValue();
                    if (fmlCode == null || fmlCode.length() < 36) continue;
                    String fmlKey = fmlCode.substring(0, 36);
                    String changeFmlCode = fmlCode;
                    if (!curFcFml.contains(fmlKey) && fmlMap.containsKey(fmlKey) && (paramUp2 = this.getParamUp(asdf, fcParamCache, (formulaObj = (FormulaObj)fmlMap.get(fmlKey)).getFormSchemeKey())) != null) {
                        Map<String, NodeRelation> fmlOldKeyMap;
                        Map<String, NodeRelation> fmlNewKeyMap = paramUp2.getNewKeyMap().get((Object)NodeRelationEnum.FORMULA);
                        String oldKey = fmlNewKeyMap != null && fmlNewKeyMap.containsKey(formulaObj.getKey()) ? fmlNewKeyMap.get(formulaObj.getKey()).getOldKey() : formulaObj.getKey();
                        ParamUp paramUp1 = this.getParamUp(asdf, fcParamCache, formSchemeDefine.getKey());
                        if (paramUp1 != null && (fmlOldKeyMap = paramUp1.getOldKeyMap().get((Object)NodeRelationEnum.FORMULA)) != null && fmlOldKeyMap.containsKey(oldKey)) {
                            String newKey = fmlOldKeyMap.get(oldKey).getNewKey();
                            changeFmlCode = changeFmlCode.replace(fmlKey, newKey);
                        }
                    }
                    if (fmlCode.length() > 39 && !curFcDl.contains(dlCode = fmlCode.substring(39)) && dlMap.containsKey(dlCode) && (paramUp = this.getParamUp(asdf, fcParamCache, (dataLinkObj = (DataLinkObj)dlMap.get(dlCode)).getFormSchemeKey())) != null) {
                        String newKey;
                        Map<String, NodeRelation> dlOldKeyMap;
                        Map<String, NodeRelation> dlNewKeyMap = paramUp.getNewKeyMap().get((Object)NodeRelationEnum.LINK);
                        String oldKey = dlNewKeyMap != null && dlNewKeyMap.containsKey(dataLinkObj.getKey()) ? dlNewKeyMap.get(dataLinkObj.getKey()).getOldKey() : dataLinkObj.getKey();
                        ParamUp paramUp1 = this.getParamUp(asdf, fcParamCache, formSchemeDefine.getKey());
                        if (paramUp1 != null && (dlOldKeyMap = paramUp1.getOldKeyMap().get((Object)NodeRelationEnum.LINK)) != null && dlOldKeyMap.containsKey(oldKey) && dlKeyMap.containsKey(newKey = dlOldKeyMap.get(oldKey).getNewKey())) {
                            changeFmlCode = changeFmlCode.replace(dlCode, ((DataLinkObj)dlKeyMap.get(newKey)).getUniqueCode());
                        }
                    }
                    if (changeFmlCode.equals(fmlCode)) continue;
                    updateData.put(e.getKey(), changeFmlCode);
                }
                if (!CollectionUtils.isEmpty(updateData)) {
                    for (Map.Entry e : updateData.entrySet()) {
                        batchSqlUpdate.update((String)e.getValue(), (String)e.getKey());
                    }
                    batchSqlUpdate.flush();
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @GetMapping(value={"/fix-ckd-dimstr/{formSchemeCode}"})
    public void fixCkdDimStr(@PathVariable String formSchemeCode) {
        String entityUpgrade = "SELECT T1.ET_NAME AS C1,T2.TD_TABLE_NAME AS C2,T1.ET_ENTITY_ID AS C3 FROM NR_ENTITY_UPGRADE_TABLE T1 INNER JOIN SYS_TABLEDEFINE T2 ON T1.ET_TD_KEY=T2.TD_KEY";
        List maps1 = this.jdbcTemplate.queryForList(entityUpgrade);
        if (!CollectionUtils.isEmpty(maps1)) {
            HashMap<String, String> tableMap = new HashMap<String, String>();
            HashMap<String, EntityUp> entityUpMap = new HashMap<String, EntityUp>();
            for (Map e : maps1) {
                String c1 = String.valueOf(e.get("C1"));
                String c2 = String.valueOf(e.get("C2"));
                String c3 = String.valueOf(e.get("C3"));
                tableMap.put(c2, c1);
                logger.info("\u539ftableName{}-\u73b0tableName{}", (Object)c2, (Object)c1);
                EntityUp entityUp = new EntityUp(c2, c1, c3);
                entityUpMap.put(c3, entityUp);
            }
            HashMap<String, DataSchemeInfo> dsCache = new HashMap<String, DataSchemeInfo>();
            FormSchemeDefine formschemeByCode = this.runTimeViewController.getFormschemeByCode(formSchemeCode);
            TaskDefine task = this.runTimeViewController.queryTaskDefine(formschemeByCode.getTaskKey());
            String dataScheme = task.getDataScheme();
            DataSchemeInfo dataSchemeInfo = this.getDataSchemeInfo(dataScheme, dsCache);
            this.fixDimStrByFmScheme(tableMap, entityUpMap, dataSchemeInfo, formschemeByCode);
        }
    }

    @GetMapping(value={"/fix-ckd-recid/{formSchemeCode}"})
    public void fixCkdId(@PathVariable String formSchemeCode) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormschemeByCode(formSchemeCode);
        try {
            this.reviseCKDRECIDService.revise(formScheme);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @GetMapping(value={"/fix-all-ckd-dimstr"})
    public void fixAllDimStr() {
        String entityUpgrade = "SELECT T1.ET_NAME AS C1,T2.TD_TABLE_NAME AS C2,T1.ET_ENTITY_ID AS C3 FROM NR_ENTITY_UPGRADE_TABLE T1 INNER JOIN SYS_TABLEDEFINE T2 ON T1.ET_TD_KEY=T2.TD_KEY";
        List maps1 = this.jdbcTemplate.queryForList(entityUpgrade);
        if (!CollectionUtils.isEmpty(maps1)) {
            HashMap<String, String> tableMap = new HashMap<String, String>();
            HashMap<String, EntityUp> entityUpMap = new HashMap<String, EntityUp>();
            for (Map e : maps1) {
                String c1 = String.valueOf(e.get("C1"));
                String c2 = String.valueOf(e.get("C2"));
                String c3 = String.valueOf(e.get("C3"));
                tableMap.put(c2, c1);
                logger.info("\u539ftableName{}-\u73b0tableName{}", (Object)c2, (Object)c1);
                EntityUp entityUp = new EntityUp(c2, c1, c3);
                entityUpMap.put(c3, entityUp);
            }
            HashMap<String, DataSchemeInfo> dsCache = new HashMap<String, DataSchemeInfo>();
            List allTaskDefines = this.runTimeViewController.getAllTaskDefines();
            for (TaskDefine task : allTaskDefines) {
                try {
                    String dataScheme = task.getDataScheme();
                    DataSchemeInfo dataSchemeInfo = this.getDataSchemeInfo(dataScheme, dsCache);
                    List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(task.getKey());
                    for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                        this.fixDimStrByFmScheme(tableMap, entityUpMap, dataSchemeInfo, formSchemeDefine);
                    }
                }
                catch (Exception e) {
                    logger.error(String.format("\u4efb\u52a1%s\u51fa\u9519\u8bf4\u660e\u4fee\u590d\u5f02\u5e38:%s", task.getTaskCode(), e.getMessage()), e);
                }
            }
        }
    }

    @GetMapping(value={"/fix-all-ckd-recid"})
    public void fixAllCkdId() {
        this.checkErrorDescriptionService.reviseCheckDesKey();
    }

    @PostMapping(value={"/refresh-ckd"})
    public AsyncTaskInfo refreshCkd(@RequestBody String taskKey) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(taskKey);
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)taskKey));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new RefreshCkdAsyncTaskExecutor());
        String asyncTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo, "ASYNC_TASK_REFRESH_CKD");
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asyncTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    private Map<String, Map<String, String>> getTaskParamChange() {
        List query = this.jdbcTemplate.query("select * from NR_TASK_PARAM_CHANGE", (rs, num) -> {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("formScheme", rs.getString("L_NODEKEY"));
            map.put("type", rs.getString("L_TYPE"));
            map.put("description", rs.getString("L_DATA"));
            return map;
        });
        HashMap<String, Map<String, String>> asdf = new HashMap<String, Map<String, String>>();
        for (Map map : query) {
            asdf.put((String)map.get("formScheme"), map);
        }
        return asdf;
    }

    private DataSchemeInfo getDataSchemeInfo(String dataScheme, Map<String, DataSchemeInfo> dsCache) {
        if (dsCache.containsKey(dataScheme)) {
            return dsCache.get(dataScheme);
        }
        DataSchemeInfo dataSchemeInfo = new DataSchemeInfo();
        List dataTableByScheme = this.dataSchemeService.getAllDataTable(dataScheme);
        List collect = dataTableByScheme.stream().filter(o -> o.getDataTableType() == DataTableType.DETAIL).collect(Collectors.toList());
        for (DataTable dataTable : collect) {
            Object pubDimField22;
            List dimFields = this.dataSchemeService.getDataFieldByTableKeyAndKind(dataTable.getKey(), new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM});
            if (CollectionUtils.isEmpty(dimFields) || dimFields.size() < 2) continue;
            String[] bizKeys = dataTable.getBizKeys();
            List<DataField> sortedBizField = this.sortBizField(bizKeys, dimFields);
            List pubDimFields = this.dataSchemeService.getDataFieldByTableKeyAndKind(dataTable.getKey(), new DataFieldKind[]{DataFieldKind.PUBLIC_FIELD_DIM});
            HashSet<String> pubDimName = new HashSet<String>();
            for (Object pubDimField22 : pubDimFields) {
                IEntityDefine iEntityDefine;
                String refDataEntityKey = pubDimField22.getRefDataEntityKey();
                if (!StringUtils.hasText(refDataEntityKey) || (iEntityDefine = this.entityMetaService.queryEntity(refDataEntityKey)) == null) continue;
                pubDimName.add(iEntityDefine.getDimensionName());
            }
            HashSet<String> toDel = new HashSet<String>();
            pubDimField22 = sortedBizField.iterator();
            while (pubDimField22.hasNext()) {
                String dimensionName;
                DataField dataField = (DataField)pubDimField22.next();
                String string = dataField.getRefDataEntityKey();
                if (!StringUtils.hasText(string) || !"MD_ORG".equals(dimensionName = this.entityMetaService.getDimensionName(string))) continue;
                Map<String, List<String>> orgFieldCodes = dataSchemeInfo.getOrgFieldCodes();
                if (orgFieldCodes.containsKey(dataTable.getKey())) {
                    orgFieldCodes.get(dataTable.getKey()).add(dataField.getCode());
                } else {
                    ArrayList<String> codes = new ArrayList<String>();
                    codes.add(dataField.getCode());
                    orgFieldCodes.put(dataTable.getKey(), codes);
                }
                toDel.add(dataField.getKey());
            }
            sortedBizField.removeIf(o -> toDel.contains(o.getKey()));
            Map<String, List<DataField>> collect3 = sortedBizField.stream().filter(o -> StringUtils.hasText(o.getRefDataEntityKey()) && pubDimName.contains(this.entityMetaService.getDimensionName(o.getRefDataEntityKey()))).collect(Collectors.groupingBy(DataField::getRefDataEntityKey));
            for (Map.Entry entry : collect3.entrySet()) {
                HashMap map = new HashMap();
                map.put(entry.getKey(), ((List)entry.getValue()).stream().map(Basic::getCode).collect(Collectors.toList()));
                dataSchemeInfo.getPubRefEntityDimFields().put(dataTable.getKey(), map);
            }
            sortedBizField.removeIf(o -> collect3.containsKey(o.getRefDataEntityKey()));
            Map<String, List<DataField>> collect1 = sortedBizField.stream().filter(o -> StringUtils.hasText(o.getRefDataEntityKey())).collect(Collectors.groupingBy(DataField::getRefDataEntityKey));
            for (Map.Entry<String, List<DataField>> entry : collect1.entrySet()) {
                if (entry.getValue().size() <= 1) continue;
                HashMap map = new HashMap();
                map.put(entry.getKey(), entry.getValue().stream().map(Basic::getCode).collect(Collectors.toList()));
                dataSchemeInfo.getSameRefEntityDimFields().put(dataTable.getKey(), map);
            }
        }
        dsCache.put(dataScheme, dataSchemeInfo);
        return dataSchemeInfo;
    }

    private void fixDimStrByFmScheme(Map<String, String> tableMap, Map<String, EntityUp> entityUpMap, DataSchemeInfo dataSchemeInfo, FormSchemeDefine formSchemeDefine) {
        String ckdTableName = CheckTableNameUtil.getCKDTableName(formSchemeDefine.getFormSchemeCode());
        try {
            String sql = String.format("SELECT %s,%s,%s FROM %s", "CKD_RECID", "CKD_DIMSTR", "CKD_FORMULACODE", ckdTableName);
            List ckds = this.jdbcTemplate.query(sql, (rs, rowNum) -> {
                CheckDesObj checkDesObj = new CheckDesObj();
                checkDesObj.setRecordId(rs.getString("CKD_RECID"));
                checkDesObj.setFloatId(rs.getString("CKD_DIMSTR"));
                checkDesObj.setFormulaExpressionKey(rs.getString("CKD_FORMULACODE"));
                return checkDesObj;
            });
            if (!CollectionUtils.isEmpty(ckds)) {
                Map<String, List<CheckDesObj>> mapByFml = ckds.stream().collect(Collectors.groupingBy(CheckDesObj::getFormulaExpressionKey));
                List allFormulaSchemeDefinesByFormScheme = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeDefine.getKey());
                ArrayList allFmlTracksInFS = new ArrayList();
                allFormulaSchemeDefinesByFormScheme.forEach(o -> allFmlTracksInFS.addAll(new ArrayList(this.formulaTrackService.getFormulaTrackByScheme(o.getKey()))));
                if (CollectionUtils.isEmpty(allFmlTracksInFS)) {
                    return;
                }
                Map<String, List<FormulaTrackDefine>> collect = allFmlTracksInFS.stream().collect(Collectors.groupingBy(FormulaTrackDefine::getExpressionKey));
                block2: for (Map.Entry<String, List<CheckDesObj>> entry : mapByFml.entrySet()) {
                    String expressionKey = entry.getKey();
                    List<CheckDesObj> value = entry.getValue();
                    if (!collect.containsKey(expressionKey)) continue;
                    List<FormulaTrackDefine> formulaTrackDefines = collect.get(expressionKey);
                    for (FormulaTrackDefine formulaTrackDefine : formulaTrackDefines) {
                        int i;
                        Map<String, List<String>> stringListMap;
                        String formulaFieldKey = formulaTrackDefine.getFormulaFieldKey();
                        DataField dataField = this.dataSchemeService.getDataField(formulaFieldKey);
                        if (dataField == null || !dataSchemeInfo.getOrgFieldCodes().containsKey(dataField.getDataTableKey()) && !dataSchemeInfo.getPubRefEntityDimFields().containsKey(dataField.getDataTableKey()) && !dataSchemeInfo.getSameRefEntityDimFields().containsKey(dataField.getDataTableKey())) continue;
                        if (dataSchemeInfo.getOrgFieldCodes().containsKey(dataField.getDataTableKey())) {
                            List<String> orgFieldCodes = dataSchemeInfo.getOrgFieldCodes().get(dataField.getDataTableKey());
                            for (CheckDesObj checkDesObj : value) {
                                for (int i2 = 0; i2 < orgFieldCodes.size(); ++i2) {
                                    if (checkDesObj.getFloatId().contains("MD_ORG_CODE_" + (i2 + 1) + ":")) {
                                        checkDesObj.setFloatId(checkDesObj.getFloatId().replace("MD_ORG_CODE_" + (i2 + 1) + ":", orgFieldCodes.get(i2) + ":"));
                                        continue;
                                    }
                                    if (!checkDesObj.getFloatId().contains("MD_ORG_ID_" + (i2 + 1) + ":")) continue;
                                    checkDesObj.setFloatId(checkDesObj.getFloatId().replace("MD_ORG_ID_" + (i2 + 1) + ":", orgFieldCodes.get(i2) + ":"));
                                }
                            }
                        }
                        if (dataSchemeInfo.getPubRefEntityDimFields().containsKey(dataField.getDataTableKey())) {
                            stringListMap = dataSchemeInfo.getPubRefEntityDimFields().get(dataField.getDataTableKey());
                            for (Map.Entry entry2 : stringListMap.entrySet()) {
                                EntityUp entityUp = entityUpMap.get(entry2.getKey());
                                for (CheckDesObj checkDesObj : value) {
                                    for (i = 0; i < ((List)entry2.getValue()).size(); ++i) {
                                        if (checkDesObj.getFloatId().contains(entityUp.getOldTableName() + "_CODE_" + (i + 1) + ":")) {
                                            checkDesObj.setFloatId(checkDesObj.getFloatId().replace(entityUp.getOldTableName() + "_CODE_" + (i + 1) + ":", (String)((List)entry2.getValue()).get(i) + ":"));
                                            continue;
                                        }
                                        if (!checkDesObj.getFloatId().contains(entityUp.getOldTableName() + "_ID_" + (i + 1) + ":")) continue;
                                        checkDesObj.setFloatId(checkDesObj.getFloatId().replace(entityUp.getOldTableName() + "_ID_" + (i + 1) + ":", (String)((List)entry2.getValue()).get(i) + ":"));
                                    }
                                }
                            }
                        }
                        if (!dataSchemeInfo.getSameRefEntityDimFields().containsKey(dataField.getDataTableKey())) continue block2;
                        stringListMap = dataSchemeInfo.getSameRefEntityDimFields().get(dataField.getDataTableKey());
                        for (Map.Entry entry3 : stringListMap.entrySet()) {
                            if (((List)entry3.getValue()).size() <= 1) continue;
                            EntityUp entityUp = entityUpMap.get(entry3.getKey());
                            for (CheckDesObj checkDesObj : value) {
                                for (i = 0; i < ((List)entry3.getValue()).size(); ++i) {
                                    if (i == 0) {
                                        if (checkDesObj.getFloatId().contains(entityUp.getOldTableName() + "_CODE:")) {
                                            checkDesObj.setFloatId(checkDesObj.getFloatId().replace(entityUp.getOldTableName() + "_CODE:", (String)((List)entry3.getValue()).get(i) + ":"));
                                            continue;
                                        }
                                        if (!checkDesObj.getFloatId().contains(entityUp.getOldTableName() + "_ID:")) continue;
                                        checkDesObj.setFloatId(checkDesObj.getFloatId().replace(entityUp.getOldTableName() + "_ID:", (String)((List)entry3.getValue()).get(i) + ":"));
                                        continue;
                                    }
                                    if (checkDesObj.getFloatId().contains(entityUp.getOldTableName() + "_CODE_" + i + ":")) {
                                        checkDesObj.setFloatId(checkDesObj.getFloatId().replace(entityUp.getOldTableName() + "_CODE_" + i + ":", (String)((List)entry3.getValue()).get(i) + ":"));
                                        continue;
                                    }
                                    if (!checkDesObj.getFloatId().contains(entityUp.getOldTableName() + "_ID_" + i + ":")) continue;
                                    checkDesObj.setFloatId(checkDesObj.getFloatId().replace(entityUp.getOldTableName() + "_ID_" + i + ":", (String)((List)entry3.getValue()).get(i) + ":"));
                                }
                            }
                        }
                        continue block2;
                    }
                }
                ArrayList saveObjs = new ArrayList();
                mapByFml.values().forEach(saveObjs::addAll);
                BatchSqlUpdate batchSqlUpdate = new BatchSqlUpdate(Objects.requireNonNull(this.jdbcTemplate.getDataSource()), String.format("update %s set %s=? where %s=?", ckdTableName, "CKD_DIMSTR", "CKD_RECID"));
                batchSqlUpdate.setTypes(new int[]{-9, -9});
                for (CheckDesObj saveObj : saveObjs) {
                    batchSqlUpdate.update(saveObj.getFloatId(), saveObj.getRecordId());
                }
                batchSqlUpdate.flush();
                this.normalDimStrUp(ckdTableName, tableMap);
            }
            logger.info(String.format("\u62a5\u8868\u65b9\u6848%s\u51fa\u9519\u8bf4\u660e\u4fee\u590d\u6210\u529f", formSchemeDefine.getFormSchemeCode()));
        }
        catch (Exception e) {
            logger.error(String.format("\u62a5\u8868\u65b9\u6848%s\u51fa\u9519\u8bf4\u660e\u4fee\u590d\u5f02\u5e38:%s", formSchemeDefine.getFormSchemeCode(), e.getMessage()), e);
        }
    }

    private void normalDimStrUp(String ckdTableName, Map<String, String> tableMap) {
        try {
            String sql = String.format("SELECT %s,%s FROM %s", "CKD_RECID", "CKD_DIMSTR", ckdTableName);
            List maps = this.jdbcTemplate.queryForList(sql);
            if (!CollectionUtils.isEmpty(maps)) {
                BatchSqlUpdate batchSqlUpdate = new BatchSqlUpdate(Objects.requireNonNull(this.jdbcTemplate.getDataSource()), String.format("update %s set %s=? where %s=?", ckdTableName, "CKD_DIMSTR", "CKD_RECID"));
                batchSqlUpdate.setTypes(new int[]{-9, -9});
                for (Map map : maps) {
                    String[] dims;
                    String dimStr = String.valueOf(map.get("CKD_DIMSTR"));
                    HashMap<String, String> changeDimNames = new HashMap<String, String>();
                    for (String dim : dims = dimStr.split(";")) {
                        String oriTableNameY;
                        String newName;
                        String[] dimValues = dim.split(":");
                        if (dimValues.length != 2) continue;
                        String dimName = dimValues[0];
                        if (tableMap.containsKey(dimName)) {
                            String newName2 = tableMap.get(dimName);
                            if (newName2.equals(dimName)) continue;
                            changeDimNames.put(dimName, newName2);
                            continue;
                        }
                        if (dimName.endsWith("_CODE")) {
                            String oriTableNameX = dimName.substring(0, dimName.lastIndexOf("_CODE"));
                            if (!tableMap.containsKey(oriTableNameX) || (newName = tableMap.get(oriTableNameX)).equals(dimName)) continue;
                            changeDimNames.put(dimName, newName);
                            continue;
                        }
                        if (!dimName.endsWith("_ID") || !tableMap.containsKey(oriTableNameY = dimName.substring(0, dimName.lastIndexOf("_ID"))) || (newName = tableMap.get(oriTableNameY)).equals(dimName)) continue;
                        changeDimNames.put(dimName, newName);
                    }
                    if (changeDimNames.size() <= 0) continue;
                    String recid = String.valueOf(map.get("CKD_RECID"));
                    for (Map.Entry entry : changeDimNames.entrySet()) {
                        dimStr = dimStr.replace((String)entry.getKey() + ":", (String)entry.getValue() + ":");
                        logger.info("\u6570\u636e\u884c{}dimstr\u5b57\u6bb5{}\u66f4\u65b0\u4e3a{}", recid, entry.getKey(), entry.getValue());
                    }
                    batchSqlUpdate.update(dimStr, recid);
                }
                batchSqlUpdate.flush();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private List<DataField> sortBizField(String[] bizKeys, List<DataField> fields) {
        ArrayList<DataField> result = new ArrayList<DataField>();
        Map collect = fields.stream().collect(Collectors.toMap(Basic::getKey, Function.identity(), (o1, o2) -> o2));
        for (String bizKey : bizKeys) {
            if (!collect.containsKey(bizKey)) continue;
            result.add((DataField)collect.get(bizKey));
        }
        return result;
    }

    private ParamUp getParamUp(Map<String, Map<String, String>> asdf, Map<String, ParamUp> fcParamCache, String formSchemeKey) {
        if (fcParamCache.containsKey(formSchemeKey)) {
            return fcParamCache.get(formSchemeKey);
        }
        if (asdf.get(formSchemeKey) == null) {
            logger.error("\u5347\u7ea7\u8bb0\u5f55\u4e2d\u4e0d\u5b58\u5728\u62a5\u8868\u65b9\u6848{}", (Object)formSchemeKey);
            fcParamCache.put(formSchemeKey, null);
            return null;
        }
        String description = asdf.get(formSchemeKey).get("description");
        List nodeRelationList = new ArrayList();
        if (StringUtils.hasLength(description)) {
            try {
                nodeRelationList = (List)objectMapper.readValue(description, (TypeReference)new TypeReference<List<NodeRelation>>(){});
            }
            catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
                fcParamCache.put(formSchemeKey, null);
                return null;
            }
        }
        ParamUp param = new ParamUp();
        for (NodeRelation node : nodeRelationList) {
            HashMap<String, NodeRelation> typeMap;
            if (null == param.getOldKeyMap().get((Object)node.getNodeType())) {
                typeMap = new HashMap<String, NodeRelation>();
                typeMap.put(node.getOldKey(), node);
                param.getOldKeyMap().put(node.getNodeType(), typeMap);
            } else {
                param.getOldKeyMap().get((Object)node.getNodeType()).put(node.getOldKey(), node);
            }
            if (null == param.getNewKeyMap().get((Object)node.getNodeType())) {
                typeMap = new HashMap();
                typeMap.put(node.getNewKey(), node);
                param.getNewKeyMap().put(node.getNodeType(), typeMap);
                continue;
            }
            param.getNewKeyMap().get((Object)node.getNodeType()).put(node.getNewKey(), node);
        }
        fcParamCache.put(formSchemeKey, param);
        return param;
    }

    private List<FormulaObj> getAllFml() {
        String sql = "select t3.FL_KEY AS C1, t1.FC_KEY AS C2 from nr_param_formscheme t1 inner join NR_PARAM_FORMULASCHEME t2 on t1.FC_KEY = t2.FS_FORM_SCHEME_KEY inner join NR_PARAM_FORMULA t3 on t2.FS_KEY = t3.FL_SCHEME_KEY ";
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
            FormulaObj formulaObj = new FormulaObj();
            formulaObj.setKey(rs.getString("C1"));
            formulaObj.setFormSchemeKey(rs.getString("C2"));
            return formulaObj;
        });
    }

    private List<DataLinkObj> getAllDl() {
        String sql = "select t4.DL_KEY AS C1, t4.DL_UNIQUE_CODE AS C2 ,t1.FC_KEY AS C3 from NR_PARAM_FORMSCHEME t1 inner join NR_PARAM_FORM t2 on t1.FC_KEY = t2.FM_FORMSCHEME inner join NR_PARAM_DATAREGION t3 on t2.FM_KEY = t3.DR_FORM_KEY inner join NR_PARAM_DATALINK t4 on t3.DR_KEY = t4.DL_REGION_KEY ";
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
            DataLinkObj dataLinkObj = new DataLinkObj();
            dataLinkObj.setKey(rs.getString("C1"));
            dataLinkObj.setUniqueCode(rs.getString("C2"));
            dataLinkObj.setFormSchemeKey(rs.getString("C3"));
            return dataLinkObj;
        });
    }

    private static class DataLinkObj {
        private String key;
        private String uniqueCode;
        private String formSchemeKey;

        private DataLinkObj() {
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getUniqueCode() {
            return this.uniqueCode;
        }

        public void setUniqueCode(String uniqueCode) {
            this.uniqueCode = uniqueCode;
        }

        public String getFormSchemeKey() {
            return this.formSchemeKey;
        }

        public void setFormSchemeKey(String formSchemeKey) {
            this.formSchemeKey = formSchemeKey;
        }
    }

    private static class FormulaObj {
        private String key;
        private String formSchemeKey;

        private FormulaObj() {
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getFormSchemeKey() {
            return this.formSchemeKey;
        }

        public void setFormSchemeKey(String formSchemeKey) {
            this.formSchemeKey = formSchemeKey;
        }
    }

    private static enum NodeRelationEnum {
        TASKLINK("\u5173\u8054\u4efb\u52a1", 40),
        TASKGROUPLINK("\u4efb\u52a1\u5206\u7ec4\u5173\u7cfb", 50),
        FORMULAVARIABLE("\u516c\u5f0f\u53d8\u91cf", 60),
        LINKAGE("\u679a\u4e3e\u8054\u52a8", 70),
        SCHEMEPERIOD("\u65b9\u6848\u65f6\u671f\u5173\u8054", 80),
        GROUPLINK("\u5206\u7ec4\u5173\u8054\u5173\u7cfb", 90),
        TASKOPTION("\u4efb\u52a1\u9009\u9879", 100),
        BIGDATA("\u4e8c\u8fdb\u5236\u6570\u636e", 101),
        TASKGROUP("\u4efb\u52a1\u5206\u7ec4", 110),
        FORMSCHEME("\u62a5\u8868\u65b9\u6848", 120),
        TASK("\u4efb\u52a1", 121),
        GROUP("\u62a5\u8868\u5206\u7ec4", 130),
        FORM("\u62a5\u8868", 140),
        REGION("\u6570\u636e\u533a\u57df", 160),
        REGIONSETTING("\u533a\u57df\u8bbe\u7f6e", 162),
        LINK("\u94fe\u63a5", 170),
        FORMULA("\u516c\u5f0f", 250),
        FORMULASCHEME("\u516c\u5f0f\u65b9\u6848", 210),
        PRINTSCHEME("\u6253\u5370\u65b9\u6848", 200),
        PRINTTEMPLATE("\u6253\u5370\u6a21\u677f", 190),
        TASKCODE("\u4efb\u52a1\u6807\u8bc6", 122),
        DATASCHEME("\u6570\u636e\u65b9\u6848", 300),
        TASKAUTH("\u53c2\u6570\u6743\u9650", 400),
        MAPPINGSCHEME("\u6620\u5c04\u65b9\u6848", 500),
        LANGUAGETITLE("\u591a\u8bed\u8a00\u540d\u79f0", 600),
        FORMCOPYINFO("\u62a5\u8868\u590d\u5236\u4fe1\u606f", 700),
        SCHEMEPERIODMERGE("\u65b9\u6848\u65f6\u671f\u5173\u8054\u5408\u5e76\u63d2\u5165", 800);

        private String name;
        private int type;
        private static NodeRelationEnum[] TYPES;

        public static NodeRelationEnum fromType(int type) {
            NodeRelationEnum rs = TASK;
            for (NodeRelationEnum e : TYPES) {
                if (e.type != type) continue;
                rs = e;
            }
            return rs;
        }

        public String getName() {
            return this.name;
        }

        public int getType() {
            return this.type;
        }

        private NodeRelationEnum(String name, int type) {
            this.name = name;
            this.type = type;
        }

        static {
            TYPES = new NodeRelationEnum[]{TASK, FORMSCHEME, GROUP, FORM, REGION, REGIONSETTING, LINK, FORMULA, FORMULASCHEME, PRINTSCHEME, PRINTTEMPLATE};
        }
    }

    private static class NodeRelation {
        private String oldKey;
        private NodeRelationEnum nodeType;
        private String newKey;

        public String getOldKey() {
            return this.oldKey;
        }

        public void setOldKey(String oldKey) {
            this.oldKey = oldKey;
        }

        public NodeRelationEnum getNodeType() {
            return this.nodeType;
        }

        public void setNodeType(NodeRelationEnum nodeType) {
            this.nodeType = nodeType;
        }

        public String getNewKey() {
            return this.newKey;
        }

        public void setNewKey(String newKey) {
            this.newKey = newKey;
        }

        public NodeRelation(String oldKey, NodeRelationEnum nodeType, String newKey) {
            this.oldKey = oldKey;
            this.nodeType = nodeType;
            this.newKey = newKey;
        }

        public NodeRelation() {
        }

        public NodeRelation(String oldKey, int nodeType, String newKey) {
            this.oldKey = oldKey;
            this.nodeType = NodeRelationEnum.fromType(nodeType);
            this.newKey = newKey;
        }

        public String toString() {
            return "NodeRelation [oldKey=" + this.oldKey + ", nodeType=" + (Object)((Object)this.nodeType) + ", newKey=" + this.newKey + "]";
        }
    }

    private static class ParamUp {
        private final Map<NodeRelationEnum, Map<String, NodeRelation>> newKeyMap = new HashMap<NodeRelationEnum, Map<String, NodeRelation>>();
        private final Map<NodeRelationEnum, Map<String, NodeRelation>> oldKeyMap = new HashMap<NodeRelationEnum, Map<String, NodeRelation>>();

        private ParamUp() {
        }

        public Map<NodeRelationEnum, Map<String, NodeRelation>> getNewKeyMap() {
            return this.newKeyMap;
        }

        public Map<NodeRelationEnum, Map<String, NodeRelation>> getOldKeyMap() {
            return this.oldKeyMap;
        }
    }

    private static class EntityUp {
        private final String oldTableName;
        private final String newTableName;
        private final String entityId;

        public EntityUp(String oldTableName, String newTableName, String entityId) {
            this.oldTableName = oldTableName;
            this.newTableName = newTableName;
            this.entityId = entityId;
        }

        public String getOldTableName() {
            return this.oldTableName;
        }

        public String getNewTableName() {
            return this.newTableName;
        }

        public String getEntityId() {
            return this.entityId;
        }
    }

    private static class DataSchemeInfo {
        private final Map<String, Map<String, List<String>>> pubRefEntityDimFields = new HashMap<String, Map<String, List<String>>>();
        private final Map<String, Map<String, List<String>>> sameRefEntityDimFields = new HashMap<String, Map<String, List<String>>>();
        private final Map<String, List<String>> orgFieldCodes = new HashMap<String, List<String>>();

        private DataSchemeInfo() {
        }

        public Map<String, Map<String, List<String>>> getPubRefEntityDimFields() {
            return this.pubRefEntityDimFields;
        }

        public Map<String, Map<String, List<String>>> getSameRefEntityDimFields() {
            return this.sameRefEntityDimFields;
        }

        public Map<String, List<String>> getOrgFieldCodes() {
            return this.orgFieldCodes;
        }
    }
}

