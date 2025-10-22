/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.entity.common.Utils
 *  com.jiuqi.nr.mapping2.bean.FormulaMapping
 *  com.jiuqi.nr.mapping2.bean.MappingConfig
 *  com.jiuqi.nr.mapping2.bean.PeriodMapping
 *  com.jiuqi.nr.mapping2.bean.ZBMapping
 *  com.jiuqi.nr.mapping2.bean.config.UpdateWay
 *  com.jiuqi.nr.mapping2.common.CompleteUser
 *  com.jiuqi.nr.mapping2.common.MappingErrorEnum
 *  com.jiuqi.nr.mapping2.common.NrMappingUtil
 *  com.jiuqi.nr.mapping2.dto.ImpExpRule
 *  com.jiuqi.nr.mapping2.dto.JIOContent
 *  com.jiuqi.nr.mapping2.dto.NrMappingSchemeDTO
 *  com.jiuqi.nr.mapping2.service.FormulaMappingService
 *  com.jiuqi.nr.mapping2.service.JIOConfigService
 *  com.jiuqi.nr.mapping2.service.PeriodMappingService
 *  com.jiuqi.nr.mapping2.service.ZBMappingService
 *  com.jiuqi.nr.mapping2.util.OSSUtils
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping
 *  com.jiuqi.nvwa.mapping.bean.BaseDataMapping
 *  com.jiuqi.nvwa.mapping.bean.MappingGroup
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.service.IBaseDataMappingService
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  nr.single.map.data.facade.SingleFileEnumInfo
 *  nr.single.map.data.facade.SingleFileEnumItem
 *  nr.single.map.data.facade.SingleFileFieldInfo
 *  nr.single.map.data.facade.SingleFileFmdmInfo
 *  nr.single.map.data.facade.SingleFileFormulaInfo
 *  nr.single.map.data.facade.SingleFileFormulaItem
 *  nr.single.map.data.facade.SingleFileMappingConfig
 *  nr.single.map.data.facade.SingleFileRegionInfo
 *  nr.single.map.data.facade.SingleFileTableFormulaInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 *  nr.single.map.data.facade.SingleFileTaskInfo
 *  nr.single.map.data.facade.SingleMapFormSchemeDefine
 *  nr.single.map.data.internal.SingleFileFmdmInfoImpl
 *  nr.single.map.data.internal.SingleFileTableInfoImpl
 *  nr.single.map.data.internal.SingleFileTaskInfoImpl
 */
package nr.single.para.parain.internal.maping2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.entity.common.Utils;
import com.jiuqi.nr.mapping2.bean.FormulaMapping;
import com.jiuqi.nr.mapping2.bean.MappingConfig;
import com.jiuqi.nr.mapping2.bean.PeriodMapping;
import com.jiuqi.nr.mapping2.bean.ZBMapping;
import com.jiuqi.nr.mapping2.bean.config.UpdateWay;
import com.jiuqi.nr.mapping2.common.CompleteUser;
import com.jiuqi.nr.mapping2.common.MappingErrorEnum;
import com.jiuqi.nr.mapping2.common.NrMappingUtil;
import com.jiuqi.nr.mapping2.dto.ImpExpRule;
import com.jiuqi.nr.mapping2.dto.JIOContent;
import com.jiuqi.nr.mapping2.dto.NrMappingSchemeDTO;
import com.jiuqi.nr.mapping2.service.FormulaMappingService;
import com.jiuqi.nr.mapping2.service.JIOConfigService;
import com.jiuqi.nr.mapping2.service.PeriodMappingService;
import com.jiuqi.nr.mapping2.service.ZBMappingService;
import com.jiuqi.nr.mapping2.util.OSSUtils;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping;
import com.jiuqi.nvwa.mapping.bean.BaseDataMapping;
import com.jiuqi.nvwa.mapping.bean.MappingGroup;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.service.IBaseDataMappingService;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import nr.single.map.data.facade.SingleFileEnumInfo;
import nr.single.map.data.facade.SingleFileEnumItem;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileFormulaInfo;
import nr.single.map.data.facade.SingleFileFormulaItem;
import nr.single.map.data.facade.SingleFileMappingConfig;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.facade.SingleFileTableFormulaInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.facade.SingleFileTaskInfo;
import nr.single.map.data.facade.SingleMapFormSchemeDefine;
import nr.single.map.data.internal.SingleFileFmdmInfoImpl;
import nr.single.map.data.internal.SingleFileTableInfoImpl;
import nr.single.map.data.internal.SingleFileTaskInfoImpl;
import nr.single.para.compare.definition.CompareDataEnumDTO;
import nr.single.para.compare.definition.CompareDataFormDTO;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.maping2.ITaskFileMapingTansService;
import nr.single.para.parain.maping2.SingleMappingTransOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskFileMapingTransServiceImpl
implements ITaskFileMapingTansService {
    private static final Logger logger = LoggerFactory.getLogger(TaskFileMapingTransServiceImpl.class);
    @Autowired
    private ZBMappingService zbMappingService;
    @Autowired
    private PeriodMappingService periodMapingService;
    @Autowired
    private FormulaMappingService formulaMappingService;
    @Autowired
    private IBaseDataMappingService baseDataMappingService;
    @Autowired
    private IMappingSchemeService mapingSchemeService;
    @Autowired
    private JIOConfigService mapingJIoServie;

    @Override
    public void splictJioConifg(TaskImportContext importContext, String file) throws Exception {
        try {
            logger.info("\u751f\u6210\u65b0\u7684\u6620\u5c04\u5173\u7cfb\uff0c\u5f00\u59cb");
            File jioFile2 = new File(SinglePathUtil.normalize((String)file));
            MemStream mm = new MemStream();
            mm.loadFromFile(SinglePathUtil.normalize((String)file));
            byte[] fileData = mm.getBytes();
            List allMapingSchemes = this.mapingSchemeService.getAllSchemes();
            Map<String, MappingScheme> oldConfigMaps = allMapingSchemes.stream().collect(Collectors.toMap(MappingScheme::getCode, t -> t, (u1, u2) -> u2));
            MappingScheme oldCurConfigInfo = this.findMappingScheme2(importContext, allMapingSchemes, oldConfigMaps);
            if (oldCurConfigInfo == null) {
                String reportGroupKey = this.getMappingGroupKey("\u62a5\u8868", "00000000000000000000000000000000");
                String taskGroupKey = this.getMappingGroupKey(importContext.getTaskDefine().getTitle(), reportGroupKey);
                String groupKey = this.getMappingGroupKey(importContext.getSchemeInfoCache().getFormScheme().getTitle(), taskGroupKey);
                List mapingSchemesInGroup = this.mapingSchemeService.getSchemesByGroup(groupKey);
                Map<String, MappingScheme> oldTitleConfigMaps = mapingSchemesInGroup.stream().collect(Collectors.toMap(MappingScheme::getTitle, t -> t, (u1, u2) -> u2));
                String aTitle = importContext.getParaInfo().getTaskName();
                int num = 1;
                while (oldTitleConfigMaps.containsKey(aTitle)) {
                    aTitle = importContext.getParaInfo().getTaskName() + "_" + String.valueOf(num);
                    ++num;
                }
                String aCode = importContext.getParaInfo().getPrefix() + "";
                num = 1;
                while (oldConfigMaps.containsKey(aCode)) {
                    aCode = importContext.getParaInfo().getPrefix() + "_" + String.valueOf(num);
                    ++num;
                }
                MappingScheme newMappingScheme = new MappingScheme();
                newMappingScheme.setKey(UUID.randomUUID().toString());
                newMappingScheme.setCode(aCode);
                newMappingScheme.setTitle(aTitle);
                newMappingScheme.setGroup(groupKey);
                NrMappingSchemeDTO nrMappingScheme = new NrMappingSchemeDTO(newMappingScheme);
                nrMappingScheme.setTask(importContext.getTaskKey());
                nrMappingScheme.setFormScheme(importContext.getFormSchemeKey());
                nrMappingScheme.setSource(NrMappingUtil.getJioSources());
                nrMappingScheme.setOrgName(Utils.getId((String)importContext.getEntityId()));
                nrMappingScheme.setUpdateTime(new Date());
                nrMappingScheme.setType("JIO");
                MappingScheme newMappingScheme2 = NrMappingUtil.parseNRMappingToNvwa((NrMappingSchemeDTO)nrMappingScheme);
                this.mapingSchemeService.addScheme(newMappingScheme2);
                oldCurConfigInfo = newMappingScheme;
            }
            String msKey = oldCurConfigInfo.getKey();
            String fileName = jioFile2.getName();
            SingleMappingTransOption tranOption = new SingleMappingTransOption();
            tranOption.selectAll();
            if (importContext.getImportOption() != null) {
                tranOption.setUpdateZb(importContext.getImportOption().isUploadForm());
                tranOption.setUpdateFormula(importContext.getImportOption().isUploadFormula());
                tranOption.setUpdateBaseData(importContext.getImportOption().isUploadEnum());
                tranOption.setUpdatePeriod(importContext.getImportOption().isUploadTask());
                tranOption.setUpdateConfig(importContext.getImportOption().isUploadTask());
            }
            if (importContext.getImportResult() != null) {
                importContext.getImportResult().setMapSchemeKey(msKey);
            }
            this.copyAndUploadJioConfig(importContext, msKey, fileData, fileName, tranOption, true);
            logger.info("\u751f\u6210\u65b0\u7684\u6620\u5c04\u5173\u7cfb\uff0c\u5b8c\u6210");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public MappingScheme findMappingScheme(TaskImportContext importContext) {
        List allMapingSchemes = this.mapingSchemeService.getAllSchemes();
        Map<String, MappingScheme> oldConfigMaps = allMapingSchemes.stream().collect(Collectors.toMap(MappingScheme::getCode, t -> t, (u1, u2) -> u2));
        return this.findMappingScheme2(importContext, allMapingSchemes, oldConfigMaps);
    }

    private MappingScheme findMappingScheme2(TaskImportContext importContext, List<MappingScheme> allMapingSchemes, Map<String, MappingScheme> oldConfigMap) {
        List<MappingScheme> oldConfigs = this.getMappingByTaskForm(allMapingSchemes, importContext.getTaskKey(), importContext.getFormSchemeKey());
        ArrayList<MappingScheme> oldConfigInfos = new ArrayList<MappingScheme>();
        HashMap<String, MappingScheme> oldConfigTitleMaps = new HashMap<String, MappingScheme>();
        MappingScheme oldCurConfigInfo = null;
        for (MappingScheme oldConfig : oldConfigs) {
            oldConfigTitleMaps.put(oldConfig.getTitle(), oldConfig);
            JIOContent jioContent = null;
            try {
                jioContent = this.mapingJIoServie.getJIOContentByMs(oldConfig.getKey());
            }
            catch (Exception e) {
                jioContent = null;
            }
            if (jioContent != null && StringUtils.isNotEmpty((String)jioContent.getTaskCode()) && jioContent.getTaskCode().equalsIgnoreCase(importContext.getParaInfo().getPrefix()) && jioContent.isAutoGenerate()) {
                oldConfigInfos.add(oldConfig);
                oldCurConfigInfo = oldConfig;
                break;
            }
            if (oldConfig == null || !oldConfig.getCode().startsWith(importContext.getParaInfo().getPrefix())) continue;
            oldConfigInfos.add(oldConfig);
            oldCurConfigInfo = oldConfig;
            break;
        }
        if (oldCurConfigInfo == null && oldConfigInfos.size() > 0) {
            oldCurConfigInfo = (MappingScheme)oldConfigInfos.get(0);
        }
        return oldCurConfigInfo;
    }

    private String getMappingGroupKey(String groupTitle, String parentKey) throws JQException {
        List groups;
        String groupKey = null;
        Map<Object, Object> oldGroupMaps = null;
        if (StringUtils.isEmpty((String)parentKey)) {
            groups = this.mapingSchemeService.getAllGroups();
            oldGroupMaps = new HashMap();
            for (MappingGroup group : groups) {
                if (!StringUtils.isEmpty((String)group.getParent())) continue;
                oldGroupMaps.put(group.getTitle(), group);
            }
        } else {
            groups = this.mapingSchemeService.getGroupsByParent(parentKey);
            oldGroupMaps = groups.stream().collect(Collectors.toMap(MappingGroup::getTitle, t -> t, (u1, u2) -> u2));
        }
        if (oldGroupMaps.containsKey(groupTitle)) {
            groupKey = ((MappingGroup)oldGroupMaps.get(groupTitle)).getKey();
        } else {
            MappingGroup group = new MappingGroup();
            group.setKey(UUID.randomUUID().toString());
            group.setParent(parentKey);
            group.setTitle(groupTitle);
            this.mapingSchemeService.addGroup(group);
            groupKey = group.getKey();
        }
        return groupKey;
    }

    private List<MappingScheme> getMappingByTaskForm(List<MappingScheme> allMapingSchemes, String taskKey, String formSchemeKey) {
        ArrayList<MappingScheme> mapingSchemes = new ArrayList<MappingScheme>();
        for (MappingScheme mappingScheme : allMapingSchemes) {
            NrMappingSchemeDTO nrMapingScheme = new NrMappingSchemeDTO(mappingScheme);
            if (!"JIO".equalsIgnoreCase(nrMapingScheme.getType()) || !StringUtils.isNotEmpty((String)taskKey) || !taskKey.equalsIgnoreCase(nrMapingScheme.getTask()) || !StringUtils.isNotEmpty((String)formSchemeKey) || !formSchemeKey.equalsIgnoreCase(nrMapingScheme.getFormScheme())) continue;
            mapingSchemes.add(mappingScheme);
        }
        return mapingSchemes;
    }

    @Override
    public void copyAndUploadJioConfig(TaskImportContext importContext, String msKey, byte[] fileData, String fileName, SingleMappingTransOption option, boolean autoGenerate) throws Exception {
        String fileKey = UUID.randomUUID().toString();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData);
        OSSUtils.upload((String)fileKey, (InputStream)inputStream, (long)fileData.length);
        this.copyAndUploadJioConfigByFileKey(importContext, msKey, fileKey, fileName, option, autoGenerate);
    }

    @Override
    public void copyAndUploadJioConfigByFileKey(TaskImportContext importContext, String msKey, String fileKey, String fileName, SingleMappingTransOption option, boolean autoGenerate) throws Exception {
        SingleFileMappingConfig mappingConfig = this.copyJIoMapConfig(importContext, msKey);
        if (option.isUpdateZb()) {
            this.updateZbMapping(importContext, mappingConfig, msKey);
        }
        if (option.isUpdateFormula()) {
            this.updateFormulaMaping(importContext, msKey);
        }
        if (option.isUpdateBaseData()) {
            this.updateEnumMaping(importContext, msKey);
        }
        if (option.isUpdatePeriod()) {
            this.updatePeriodMaping(importContext, msKey);
        }
        if (option.isUpdateConfig()) {
            this.updateRuleMaping(importContext, msKey);
        }
        JIOContent jioContent = new JIOContent();
        jioContent.setParaVersionNum(importContext.getParaInfo().getTaskVerion());
        jioContent.setFileCode(importContext.getParaInfo().getFileFlag());
        jioContent.setTaskCode(importContext.getParaInfo().getPrefix());
        jioContent.setTaskName(importContext.getParaInfo().getTaskName());
        jioContent.setFileName(fileName);
        jioContent.setAutoGenerate(autoGenerate);
        jioContent.setUploadTime(new Date());
        byte[] configData = this.getMappingConfigByte(mappingConfig);
        this.mapingJIoServie.saveJIO(msKey, fileKey, configData, jioContent);
    }

    private SingleFileMappingConfig copyJIoMapConfig(TaskImportContext importContext, String msKey) throws JQException {
        byte[] configData = null;
        try {
            configData = this.mapingJIoServie.getJIOConfigByMs(msKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        SingleFileMappingConfig oldMappingConfig = this.getMappingConfig(configData);
        HashMap<String, SingleFileTableInfo> oldTableMap = new HashMap<String, SingleFileTableInfo>();
        if (oldMappingConfig != null) {
            for (SingleFileTableInfo oldTable : oldMappingConfig.getTableInfos()) {
                oldTableMap.put(oldTable.getSingleTableCode(), oldTable);
            }
        }
        SingleFileMappingConfig mappingConfig = new SingleFileMappingConfig();
        SingleMapFormSchemeDefine mapScheme = importContext.getMapScheme();
        SingleFileTaskInfoImpl taskInfo = new SingleFileTaskInfoImpl();
        taskInfo.copyFrom(mapScheme.getTaskInfo());
        mappingConfig.setTaskInfo((SingleFileTaskInfo)taskInfo);
        ArrayList<SingleFileTableInfoImpl> tableInfos = new ArrayList<SingleFileTableInfoImpl>();
        for (int i = -1; i < mapScheme.getTableInfos().size(); ++i) {
            CompareDataFormDTO formCapare;
            if (i == -1) {
                SingleFileFmdmInfo fmdminfo = mapScheme.getFmdmInfo();
                SingleFileFmdmInfoImpl fmdmInfoNew = new SingleFileFmdmInfoImpl();
                fmdmInfoNew.copyFrom((SingleFileTableInfo)fmdminfo);
                mappingConfig.setFmdmInfo((SingleFileFmdmInfo)fmdmInfoNew);
                continue;
            }
            SingleFileTableInfo tableInfo = (SingleFileTableInfo)mapScheme.getTableInfos().get(i);
            if (importContext.getCompareFormDic().containsKey(tableInfo.getSingleTableCode()) && ((formCapare = importContext.getCompareFormDic().get(tableInfo.getSingleTableCode())).getUpdateType() == CompareUpdateType.UPDATE_IGNORE || formCapare.getUpdateType() == CompareUpdateType.UPDATE_UNOVER)) {
                if (!oldTableMap.containsKey(tableInfo.getSingleTableCode())) continue;
                tableInfo = (SingleFileTableInfo)oldTableMap.get(tableInfo.getSingleTableCode());
            }
            SingleFileTableInfoImpl tableInfoNew = new SingleFileTableInfoImpl();
            tableInfoNew.copyFrom(tableInfo);
            tableInfos.add(tableInfoNew);
        }
        mappingConfig.setTableInfos(tableInfos);
        return mappingConfig;
    }

    private void updateZbMapping(TaskImportContext importContext, SingleFileMappingConfig config, String msKey) {
        for (int i = -1; i < config.getTableInfos().size(); ++i) {
            CompareDataFormDTO formCapare;
            SingleFileFmdmInfo tableMap = null;
            if (i == -1) {
                SingleFileFmdmInfo fmdminfo;
                tableMap = fmdminfo = config.getFmdmInfo();
            } else {
                SingleFileTableInfo tableInfo = (SingleFileTableInfo)config.getTableInfos().get(i);
                tableMap = tableInfo;
            }
            if (importContext.getCompareFormDic().containsKey(tableMap.getSingleTableCode()) && ((formCapare = importContext.getCompareFormDic().get(tableMap.getSingleTableCode())).getUpdateType() == CompareUpdateType.UPDATE_IGNORE || formCapare.getUpdateType() == CompareUpdateType.UPDATE_UNOVER)) continue;
            ArrayList<ZBMapping> zbMappingList = new ArrayList<ZBMapping>();
            SingleFileFmdmInfo tInfo = tableMap;
            for (SingleFileFieldInfo field : tInfo.getRegion().getFields()) {
                if (StringUtils.isEmpty((String)field.getNetTableCode()) || StringUtils.isEmpty((String)tableMap.getNetFormCode())) continue;
                ZBMapping zbMaping = new ZBMapping();
                zbMaping.setKey(UUID.randomUUID().toString());
                zbMaping.setMsKey(msKey);
                zbMaping.setForm(tableMap.getNetFormCode());
                zbMaping.setTable(field.getNetTableCode());
                zbMaping.setZbCode(field.getNetFieldCode());
                if (StringUtils.isNotEmpty((String)field.getFormCode()) && field.getFormCode().equalsIgnoreCase(field.getNetFormCode())) {
                    zbMaping.setMapping(field.getFormCode() + "[" + field.getFieldCode() + "]");
                } else if (StringUtils.isNotEmpty((String)field.getFormCode())) {
                    zbMaping.setMapping(field.getFormCode() + "[" + field.getFieldCode() + "]");
                } else {
                    zbMaping.setZbCode(field.getFieldCode());
                }
                zbMaping.setRegionCode(field.getRegionCode());
                zbMappingList.add(zbMaping);
            }
            for (SingleFileRegionInfo subRegion : tInfo.getRegion().getSubRegions()) {
                for (SingleFileFieldInfo field : subRegion.getFields()) {
                    if (StringUtils.isEmpty((String)field.getNetTableCode()) || StringUtils.isEmpty((String)tableMap.getNetFormCode())) continue;
                    ZBMapping zbMaping = new ZBMapping();
                    zbMaping.setKey(UUID.randomUUID().toString());
                    zbMaping.setMsKey(msKey);
                    zbMaping.setForm(tableMap.getNetFormCode());
                    zbMaping.setTable(field.getNetTableCode());
                    zbMaping.setZbCode(field.getNetFieldCode());
                    if (StringUtils.isNotEmpty((String)field.getFormCode()) && field.getFormCode().equalsIgnoreCase(field.getNetFormCode())) {
                        zbMaping.setMapping(field.getFormCode() + "[" + field.getFieldCode() + "]");
                    } else if (StringUtils.isNotEmpty((String)field.getFormCode())) {
                        zbMaping.setMapping(field.getFormCode() + "[" + field.getFieldCode() + "]");
                    } else {
                        zbMaping.setZbCode(field.getFieldCode());
                    }
                    zbMaping.setRegionCode(field.getRegionCode());
                    zbMappingList.add(zbMaping);
                }
            }
            if (zbMappingList.size() <= 0) continue;
            this.zbMappingService.save(msKey, tableMap.getNetFormCode(), zbMappingList);
        }
    }

    private void updateFormulaMaping(TaskImportContext importContext, String msKey) {
        SingleMapFormSchemeDefine mapScheme = importContext.getMapScheme();
        for (SingleFileFormulaInfo info : mapScheme.getFormulaInfos()) {
            this.updateFormulaMapingByScheme(importContext, msKey, info);
        }
    }

    private void updateFormulaMapingByScheme(TaskImportContext importContext, String msKey, SingleFileFormulaInfo info) {
        String formulaSchemeKey = info.getNetSchemeKey();
        for (String tablekey : info.getTableFormulaInfos().keySet()) {
            SingleFileTableFormulaInfo value = (SingleFileTableFormulaInfo)info.getTableFormulaInfos().get(tablekey);
            String netFormCode = value.getNetFormCode();
            if (StringUtils.isEmpty((String)netFormCode)) {
                netFormCode = "FORMULA-MAPPING-BETWEEN";
            }
            ArrayList<FormulaMapping> mps = new ArrayList<FormulaMapping>();
            for (String k : value.getFormulaItems().keySet()) {
                SingleFileFormulaItem v = (SingleFileFormulaItem)value.getFormulaItems().get(k);
                if (StringUtils.isEmpty((String)v.getNetFormulaCode())) continue;
                FormulaMapping item = new FormulaMapping();
                item.setKey(UUID.randomUUID().toString());
                item.setMappingScheme(msKey);
                if (StringUtils.isNotEmpty((String)formulaSchemeKey)) {
                    item.setFormulaScheme(formulaSchemeKey);
                } else if (StringUtils.isNotEmpty((String)v.getNetSchemeKey())) {
                    item.setFormulaScheme(v.getNetSchemeKey());
                    if (StringUtils.isEmpty((String)formulaSchemeKey)) {
                        formulaSchemeKey = v.getNetSchemeKey();
                    }
                } else {
                    logger.info("\u516c\u5f0f\u5f02\u5e38");
                    continue;
                }
                item.setFormCode(netFormCode);
                item.setFormulaCode(v.getNetFormulaCode());
                item.setmFormulaScheme(info.getSingleSchemeName());
                item.setmFormulaCode(v.getSingleFormulaCode());
                mps.add(item);
            }
            if (mps.isEmpty()) continue;
            this.formulaMappingService.save(msKey, formulaSchemeKey, netFormCode, mps);
        }
    }

    private void updateEnumMaping(TaskImportContext importContext, String msKey) {
        ArrayList<SingleFileEnumInfo> ignoreList = new ArrayList<SingleFileEnumInfo>();
        ArrayList<SingleFileEnumInfo> importList = new ArrayList<SingleFileEnumInfo>();
        ArrayList<String> importBaseCodes = new ArrayList<String>();
        HashSet<String> repeatChecks = new HashSet<String>();
        SingleMapFormSchemeDefine mapScheme = importContext.getMapScheme();
        for (SingleFileEnumInfo info : mapScheme.getEnumInfos()) {
            CompareDataEnumDTO enumCapare;
            if (StringUtils.isEmpty((String)info.getNetTableCode()) || repeatChecks.contains(info.getNetTableCode())) continue;
            repeatChecks.add(info.getNetTableCode());
            if (importContext.getCompareEnumDic().containsKey(info.getEnumCode()) && ((enumCapare = importContext.getCompareEnumDic().get(info.getEnumCode())).getUpdateType() == CompareUpdateType.UPDATE_IGNORE || enumCapare.getUpdateType() == CompareUpdateType.UPDATE_UNOVER)) {
                ignoreList.add(info);
                continue;
            }
            importList.add(info);
            importBaseCodes.add(info.getNetTableCode());
        }
        if (ignoreList.isEmpty()) {
            this.baseDataMappingService.deleteByMSKey(msKey);
        } else {
            this.baseDataMappingService.batchDelBaseDatas(msKey, importBaseCodes);
        }
        ArrayList<BaseDataMapping> bds = new ArrayList<BaseDataMapping>();
        for (SingleFileEnumInfo info : importList) {
            CompareDataEnumDTO enumCapare;
            BaseDataMapping bdMaping = new BaseDataMapping();
            bdMaping.setKey(UUID.randomUUID().toString());
            bdMaping.setBaseDataCode(info.getNetTableCode());
            bdMaping.setMappingCode(info.getEnumCode());
            bdMaping.setMappingTitle(info.getEnumTitle());
            bdMaping.setMappingScheme(msKey);
            bds.add(bdMaping);
            if (importContext.getCompareEnumDic().containsKey(info.getEnumCode()) && ((enumCapare = importContext.getCompareEnumDic().get(info.getEnumCode())).getUpdateType() == CompareUpdateType.UPDATE_IGNORE || enumCapare.getUpdateType() == CompareUpdateType.UPDATE_UNOVER)) continue;
            ArrayList<BaseDataItemMapping> list = new ArrayList<BaseDataItemMapping>();
            for (SingleFileEnumItem item : info.getEnumItems()) {
                if (StringUtils.isEmpty((String)item.getNetItemCode())) continue;
                BaseDataItemMapping itemMaping = new BaseDataItemMapping();
                itemMaping.setKey(UUID.randomUUID().toString());
                itemMaping.setBaseDataCode(bdMaping.getBaseDataCode());
                itemMaping.setBaseDataItemCode(item.getNetItemCode());
                itemMaping.setMappingCode(item.getItemCode());
                itemMaping.setMappingTitle(item.getItemTitle());
                itemMaping.setMappingScheme(msKey);
                list.add(itemMaping);
            }
            if (list.isEmpty()) continue;
            this.baseDataMappingService.saveBaseDataItemMappings(msKey, bdMaping.getBaseDataCode(), list);
        }
        if (!bds.isEmpty()) {
            this.baseDataMappingService.addBaseDataMapping(msKey, bds);
        }
    }

    private void updatePeriodMaping(TaskImportContext importContext, String msKey) {
        SingleMapFormSchemeDefine mapScheme = importContext.getMapScheme();
        SingleFileEnumInfo taskEnumNew = mapScheme.getTaskPeriodEnum();
        ArrayList<PeriodMapping> mappings = new ArrayList<PeriodMapping>();
        for (SingleFileEnumItem item : taskEnumNew.getEnumItems()) {
            if (StringUtils.isEmpty((String)item.getNetItemCode())) continue;
            PeriodMapping itemMaping = new PeriodMapping();
            itemMaping.setKey(UUID.randomUUID().toString());
            itemMaping.setMsKey(msKey);
            itemMaping.setPeriod(item.getNetItemCode());
            itemMaping.setMapping(item.getItemCode());
            mappings.add(itemMaping);
        }
        if (!mappings.isEmpty()) {
            this.periodMapingService.saveByMS(msKey, mappings);
        }
    }

    private void updateRuleMaping(TaskImportContext importContext, String msKey) throws JQException {
        MappingConfig config = null;
        try {
            config = this.mapingJIoServie.queryMappingConfig(msKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.info("\u65e7\u6620\u5c04\u7684\u914d\u7f6e\u5b58\u5728\u95ee\u9898\uff0c\u5c06\u7528\u65b0\u7684\u8986\u76d6");
            config = null;
        }
        if (config == null) {
            config = new MappingConfig();
            UpdateWay unitUpdateWay = new UpdateWay();
            unitUpdateWay.setCreate(false);
            unitUpdateWay.setUpdate(false);
            config.setUnitUpdateWay(unitUpdateWay);
            config.setCompleteUser(CompleteUser.IMPORTUSER);
            config.setCheckParent(false);
            config.setUploadReport(false);
            config.setUploadEntityAndData(false);
        }
        ArrayList<ImpExpRule> exportRule = new ArrayList<ImpExpRule>();
        ArrayList<ImpExpRule> importRule = new ArrayList<ImpExpRule>();
        for (String fieldCode : importContext.getMapScheme().getFmdmInfo().getZdmFieldCodes()) {
            if (fieldCode.equalsIgnoreCase(importContext.getMapScheme().getFmdmInfo().getPeriodField())) continue;
            ImpExpRule iRule = new ImpExpRule();
            iRule.setNetCode(fieldCode);
            iRule.setSingleCode(fieldCode);
            importRule.add(iRule);
            ImpExpRule eRule = new ImpExpRule();
            eRule.setNetCode(fieldCode);
            eRule.setSingleCode(fieldCode);
            exportRule.add(eRule);
        }
        config.setExportRule(exportRule);
        config.setImportRule(importRule);
        this.mapingJIoServie.updateMappingConfig(msKey, config);
    }

    private byte[] getMappingConfigByte(SingleFileMappingConfig config) throws JQException {
        if (null == config) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsBytes((Object)config);
        }
        catch (JsonProcessingException e) {
            throw new JQException((ErrorEnum)MappingErrorEnum.MAPPING_001);
        }
    }

    private SingleFileMappingConfig getMappingConfig(byte[] data) throws JQException {
        if (null == data) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return (SingleFileMappingConfig)objectMapper.readValue(data, SingleFileMappingConfig.class);
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)MappingErrorEnum.MAPPING_001);
        }
    }
}

