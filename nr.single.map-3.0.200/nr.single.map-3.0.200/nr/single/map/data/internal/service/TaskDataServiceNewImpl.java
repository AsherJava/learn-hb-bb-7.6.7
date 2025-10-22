/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  javax.annotation.Resource
 */
package nr.single.map.data.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.facade.ISingleMapNrController;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.internal.SingleFileFmdmInfoImpl;
import nr.single.map.data.service.SingleMappingService;
import nr.single.map.data.service.TaskDataService;
import nr.single.map.data.util.SingleMapEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskDataServiceNewImpl
implements TaskDataService {
    private static final Logger logger = LoggerFactory.getLogger(TaskDataServiceNewImpl.class);
    @Autowired
    private SingleMappingService mappingConfigService;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private ISingleMapNrController singleParaMapController;
    @Resource
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private SingleMapEntityUtil mapEntityUtil;
    @Autowired
    private IFMDMDataService fmdmDataService;

    @Override
    public TaskDataContext getNewContext() {
        return new TaskDataContext();
    }

    @Override
    public TaskDataContext getNewAndinitContext(String TaskKey, String FormSchemeKey, String configKey) {
        TaskDataContext context = this.getNewContext();
        this.initContext(context, TaskKey, FormSchemeKey, configKey);
        return context;
    }

    @Override
    public void initContext(TaskDataContext context, String TaskKey, String FormSchemeKey, String configKey) {
        context.setTaskKey(TaskKey);
        context.setFormSchemeKey(FormSchemeKey);
        context.setConfigKey(configKey);
        TaskDefine task = null;
        try {
            task = this.runTimeAuthViewController.queryTaskDefine(TaskKey);
            if (null != task) {
                context.setDataSchemeKey(task.getDataScheme());
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        if (StringUtils.isNotEmpty((String)context.getConfigKey())) {
            ISingleMappingConfig mapConfig = this.mappingConfigService.getConfigByKey(context.getConfigKey().toString());
            context.getMapingCache().setMapConfig(mapConfig);
            if (null != mapConfig) {
                Map<String, Map<String, SingleFileFieldInfo>> singleFieldMaps = this.getTableFieldMap(mapConfig.getZbFields(), true);
                context.getMapingCache().setSingleFieldMap(singleFieldMaps);
                Map<String, Map<String, SingleFileFieldInfo>> netFieldMaps = this.getTableFieldMap(mapConfig.getZbFields(), false);
                context.getMapingCache().setNetFieldMap(netFieldMaps);
                Map<String, Map<String, List<SingleFileFieldInfo>>> singleFieldListMaps = this.getTableFieldListMap(mapConfig.getZbFields(), true);
                context.getMapingCache().setSingleFieldListMap(singleFieldListMaps);
                Map<String, Map<String, List<SingleFileFieldInfo>>> netFieldListMaps = this.getTableFieldListMap(mapConfig.getZbFields(), false);
                context.getMapingCache().setNetFieldListMap(netFieldListMaps);
                SingleFileTableInfo fmdmTable = null;
                SingleFileTableInfo fmdmTable2 = null;
                LinkedHashMap<String, SingleFileTableInfo> singleTableMap = new LinkedHashMap<String, SingleFileTableInfo>();
                HashMap<String, SingleFileFieldInfo> singleFieldMaps2 = new HashMap<String, SingleFileFieldInfo>();
                for (SingleFileTableInfo t : mapConfig.getTableInfos()) {
                    if ("FMDM".equalsIgnoreCase(t.getSingleTableCode())) {
                        if (t instanceof SingleFileFmdmInfo || t instanceof SingleFileFmdmInfoImpl) {
                            fmdmTable = t;
                            singleTableMap.put(t.getSingleTableCode(), t);
                        } else {
                            fmdmTable2 = t;
                            logger.info("\u5b58\u5728\u9519\u8bef\u7684\u6620\u5c04\u5173\u7cfb\uff1a" + t.getSingleTableCode());
                        }
                    } else {
                        singleTableMap.put(t.getSingleTableCode(), t);
                    }
                    if (t.getRegion().getFields().size() > 0) {
                        for (SingleFileFieldInfo field : t.getRegion().getFields()) {
                            singleFieldMaps2.put(t.getSingleTableCode() + "[" + field.getFieldCode() + "]", field);
                        }
                    }
                    if (t.getRegion().getSubRegions().size() <= 0) continue;
                    for (SingleFileRegionInfo region : t.getRegion().getSubRegions()) {
                        for (SingleFileFieldInfo field : region.getFields()) {
                            singleFieldMaps2.put(t.getSingleTableCode() + "[" + field.getFieldCode() + "]", field);
                        }
                    }
                }
                if (!singleTableMap.containsKey("FMDM") && fmdmTable2 != null) {
                    singleTableMap.put(fmdmTable2.getSingleTableCode(), fmdmTable2);
                }
                for (SingleFileFieldInfo field : mapConfig.getZbFields()) {
                    String aCode = field.getTableCode() + "[" + field.getFieldCode() + "]";
                    if (!singleFieldMaps2.containsKey(aCode)) continue;
                    SingleFileFieldInfo field2 = (SingleFileFieldInfo)singleFieldMaps2.get(aCode);
                    if (StringUtils.isEmpty((String)field.getEnumCode()) && StringUtils.isNotEmpty((String)field2.getEnumCode())) {
                        field.setEnumCode(field2.getEnumCode());
                    }
                    if (field2.getFieldType() == field.getFieldType()) continue;
                    field.setFieldType(field2.getFieldType());
                }
                context.getMapingCache().setSingleTableMap(singleTableMap);
                context.setCurrentTaskTitle(mapConfig.getTaskInfo().getSingleTaskTitle());
            }
        }
        if (StringUtils.isEmpty((String)context.getCurrentTaskTitle()) && task != null) {
            context.setCurrentTaskTitle(task.getTitle());
        }
    }

    @Override
    public String getNetPeriodCode(TaskDataContext context, String singlePeriodCode) {
        return this.mapEntityUtil.getNetPeriodCode(context, singlePeriodCode);
    }

    @Override
    public String getSinglePeriodCode(TaskDataContext context, String netPeriodCode, int singlePeriodLen) {
        return this.mapEntityUtil.getSinglePeriodCode(context, netPeriodCode, singlePeriodLen);
    }

    @Override
    public String getNetCompanyKey(TaskDataContext context, String singleCorpCode) {
        int idNb;
        String result = singleCorpCode;
        if (StringUtils.isNotEmpty((String)singleCorpCode) && (idNb = singleCorpCode.indexOf("NBCORP@")) >= 0) {
            String corpCode = singleCorpCode.substring(idNb + "NBCORP@".length(), singleCorpCode.length());
            String[] corpArray = corpCode.split(";");
            String corpCodes = "";
            for (int i = 0; i < corpArray.length; ++i) {
                String aCorpCode = corpArray[i];
                if (context.getEntityZdmKeyMap().containsKey(aCorpCode)) {
                    aCorpCode = context.getEntityZdmKeyMap().get(aCorpCode).toString();
                }
                if (StringUtils.isNotEmpty((String)corpCodes)) {
                    corpCodes = corpCodes + ";";
                }
                corpCodes = corpCodes + aCorpCode;
                if (i != 0 || aCorpCode.length() <= 32) continue;
                context.setCurrentEntintyKey(aCorpCode);
            }
            result = corpCodes;
        }
        return result;
    }

    @Override
    public List<SingleConfigInfo> queryMappingInSchemeByTask(String formSchemeKey, String taskFlag, String fileFlag) {
        ArrayList<SingleConfigInfo> resultList = new ArrayList<SingleConfigInfo>();
        ArrayList<SingleConfigInfo> matchTwoList = new ArrayList<SingleConfigInfo>();
        ArrayList<SingleConfigInfo> matchTaskList = new ArrayList<SingleConfigInfo>();
        ArrayList<SingleConfigInfo> matchFileList = new ArrayList<SingleConfigInfo>();
        if (StringUtils.isNotEmpty((String)taskFlag)) {
            List<SingleConfigInfo> allMappingInReport = null;
            allMappingInReport = StringUtils.isNotEmpty((String)formSchemeKey) ? this.mappingConfigService.getAllMappingInReport(formSchemeKey) : this.mappingConfigService.getConfigInSingleTask(taskFlag);
            if (null == allMappingInReport) {
                return resultList;
            }
            for (SingleConfigInfo singleConfigInfo : allMappingInReport) {
                boolean fileMatch;
                boolean taskMatch = StringUtils.isNotEmpty((String)taskFlag) && taskFlag.equals(singleConfigInfo.getTaskFlag());
                boolean bl = fileMatch = StringUtils.isNotEmpty((String)fileFlag) && fileFlag.equals(singleConfigInfo.getFileFlag());
                if (taskMatch && fileMatch) {
                    matchTwoList.add(singleConfigInfo);
                    continue;
                }
                if (taskMatch) {
                    matchTaskList.add(singleConfigInfo);
                    continue;
                }
                if (!fileMatch) continue;
                matchFileList.add(singleConfigInfo);
            }
            if (matchTwoList.size() > 0) {
                resultList.addAll(matchTwoList);
            } else if (matchTaskList.size() > 0) {
                resultList.addAll(matchTaskList);
            }
        }
        return resultList;
    }

    @Override
    public List<SingleConfigInfo> queryMappingByTask(String taskFlag, String fileFlag) {
        return this.queryMappingInSchemeByTask("", taskFlag, fileFlag);
    }

    @Override
    public List<SingleConfigInfo> queryMappingInScheme(String formSchemeKey) {
        List<SingleConfigInfo> allMappingInReport = this.mappingConfigService.getAllMappingInReport(formSchemeKey);
        return allMappingInReport;
    }

    @Override
    public SingleConfigInfo queryMapping(String configKey) {
        return this.mappingConfigService.getMappingByKey(configKey);
    }

    @Override
    public void MapSingleEnityData(TaskDataContext context) throws Exception {
        this.mapEntityUtil.MapSingleEnityData(context);
    }

    @Override
    public void makeExportEnityList(TaskDataContext context, List<String> corpList) {
        LinkedHashMap<String, String> downloadEntityKeyZdmMap = new LinkedHashMap<String, String>();
        context.setDownloadEntityKeyZdmMap(downloadEntityKeyZdmMap);
        for (String cropCode : corpList) {
            if (context.getEntityKeyZdmMap().containsKey(cropCode)) {
                downloadEntityKeyZdmMap.put(cropCode, context.getEntityKeyZdmMap().get(cropCode));
                continue;
            }
            DataEntityInfo entityInfo = context.getEntityCache().findEntityByCode(cropCode);
            if (null == entityInfo) continue;
            downloadEntityKeyZdmMap.put(cropCode, entityInfo.getSingleZdm());
        }
    }

    @Override
    public List<String> queryFilterUnits(TaskDataContext context, String filterFormula) {
        ArrayList<String> unitKeys = new ArrayList<String>();
        try {
            FMDMDataDTO queryParam = new FMDMDataDTO();
            queryParam.setFormSchemeKey(context.getFormSchemeKey());
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            String period = context.getNetPeriodCode();
            if (StringUtils.isNotEmpty((String)context.getMapNetPeriodCode())) {
                period = context.getMapNetPeriodCode();
            }
            dimensionValueSet.setValue("DATATIME", (Object)period);
            dimensionValueSet.clearValue(context.getEntityCompanyType());
            queryParam.setDimensionValueSet(dimensionValueSet);
            List queryRes = this.fmdmDataService.list(queryParam);
            for (int i = 0; i < queryRes.size(); ++i) {
                IFMDMData entityRow = (IFMDMData)queryRes.get(i);
                String zdmKey = entityRow.getValue("code").getAsString();
                unitKeys.add(zdmKey);
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return unitKeys;
    }

    private Map<String, Map<String, SingleFileFieldInfo>> getTableFieldMap(List<SingleFileFieldInfo> fields, boolean isSingle) {
        return this.getTableFieldMapEx(fields, isSingle, true);
    }

    private Map<String, Map<String, List<SingleFileFieldInfo>>> getTableFieldListMap(List<SingleFileFieldInfo> fields, boolean isSingle) {
        return this.getTableFieldListMapEx(fields, isSingle, true);
    }

    private Map<String, Map<String, SingleFileFieldInfo>> getTableFieldMapEx(List<SingleFileFieldInfo> fields, boolean isSingle, boolean hasTable) {
        HashMap<String, Map<String, SingleFileFieldInfo>> tableDic = new HashMap<String, Map<String, SingleFileFieldInfo>>();
        for (SingleFileFieldInfo field : fields) {
            boolean isExist;
            boolean bl = isExist = StringUtils.isNotEmpty((String)field.getFieldCode()) && (StringUtils.isNotEmpty((String)field.getNetFieldCode()) || StringUtils.isNotEmpty((String)field.getNetDataLinkKey()));
            if (!isExist) continue;
            Map<String, SingleFileFieldInfo> tableFields = null;
            String tableCode = field.getFormCode();
            String fieldCode = field.getFieldCode();
            String fieldFlag = tableCode + "." + fieldCode;
            if (!isSingle) {
                tableCode = field.getNetFormCode();
                fieldCode = field.getNetFieldCode();
                fieldFlag = field.getNetTableCode() + "." + fieldCode;
            } else if (StringUtils.isEmpty((String)tableCode)) {
                tableCode = field.getTableCode();
                fieldFlag = tableCode + "." + fieldCode;
            }
            if (!hasTable) {
                fieldFlag = fieldCode;
            }
            if (!StringUtils.isNotEmpty((String)tableCode) || !StringUtils.isNotEmpty((String)fieldFlag) || !isExist) continue;
            if (tableDic.containsKey(tableCode)) {
                tableFields = (Map)tableDic.get(tableCode);
            } else {
                tableFields = new HashMap();
                tableDic.put(tableCode, tableFields);
            }
            tableFields.put(fieldFlag, field);
        }
        return tableDic;
    }

    private Map<String, Map<String, List<SingleFileFieldInfo>>> getTableFieldListMapEx(List<SingleFileFieldInfo> fields, boolean isSingle, boolean hasTable) {
        HashMap<String, Map<String, List<SingleFileFieldInfo>>> tableDic = new HashMap<String, Map<String, List<SingleFileFieldInfo>>>();
        for (SingleFileFieldInfo field : fields) {
            boolean isExist;
            boolean bl = isExist = StringUtils.isNotEmpty((String)field.getFieldCode()) && StringUtils.isNotEmpty((String)field.getNetFieldCode());
            if (!isExist) continue;
            Map<String, ArrayList<SingleFileFieldInfo>> tableFields = null;
            String tableCode = field.getFormCode();
            String fieldCode = field.getFieldCode();
            String fieldFlag = tableCode + "." + fieldCode;
            if (!isSingle) {
                tableCode = field.getNetFormCode();
                fieldCode = field.getNetFieldCode();
                fieldFlag = field.getNetTableCode() + "." + fieldCode;
            } else if (StringUtils.isEmpty((String)tableCode)) {
                tableCode = field.getTableCode();
                fieldFlag = tableCode + "." + fieldCode;
            }
            if (!hasTable) {
                fieldFlag = fieldCode;
            }
            if (!StringUtils.isNotEmpty((String)tableCode) || !StringUtils.isNotEmpty((String)fieldFlag) || !isExist) continue;
            if (tableDic.containsKey(tableCode)) {
                tableFields = (Map)tableDic.get(tableCode);
            } else {
                tableFields = new HashMap();
                tableDic.put(tableCode, tableFields);
            }
            ArrayList<SingleFileFieldInfo> fieldList = (ArrayList<SingleFileFieldInfo>)tableFields.get(fieldFlag);
            if (fieldList == null) {
                fieldList = new ArrayList<SingleFileFieldInfo>();
            }
            fieldList.add(field);
            tableFields.put(fieldFlag, fieldList);
        }
        return tableDic;
    }

    @Override
    public List<String> getAuthEntityData(TaskDataContext context, String peroidCode) {
        String newPeroidCode = peroidCode;
        if (StringUtils.isNotEmpty((String)context.getMapNetPeriodCode())) {
            newPeroidCode = context.getMapNetPeriodCode();
        } else if (StringUtils.isNotEmpty((String)context.getNetPeriodCode())) {
            newPeroidCode = context.getNetPeriodCode();
        }
        return this.mapEntityUtil.queryEntityDataKeys(context.getDwEntityId(), newPeroidCode, context.getFormSchemeKey());
    }
}

