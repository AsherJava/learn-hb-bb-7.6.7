/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.exc.StreamReadException
 *  com.fasterxml.jackson.databind.DatabindException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.mapping2.bean.FormulaMapping
 *  com.jiuqi.nr.mapping2.bean.MappingConfig
 *  com.jiuqi.nr.mapping2.bean.PeriodMapping
 *  com.jiuqi.nr.mapping2.bean.ZBMapping
 *  com.jiuqi.nr.mapping2.common.CompleteUser
 *  com.jiuqi.nr.mapping2.dto.ImpExpRule
 *  com.jiuqi.nr.mapping2.dto.JIOContent
 *  com.jiuqi.nr.mapping2.dto.NrMappingSchemeDTO
 *  com.jiuqi.nr.mapping2.service.FormulaMappingService
 *  com.jiuqi.nr.mapping2.service.JIOConfigService
 *  com.jiuqi.nr.mapping2.service.PeriodMappingService
 *  com.jiuqi.nr.mapping2.service.ZBMappingService
 *  com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping
 *  com.jiuqi.nvwa.mapping.bean.BaseDataMapping
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.bean.OrgMapping
 *  com.jiuqi.nvwa.mapping.service.IBaseDataMappingService
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  com.jiuqi.nvwa.mapping.service.IOrgMappingService
 *  org.apache.commons.lang3.StringUtils
 */
package nr.single.map.data.internal.service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.mapping2.bean.FormulaMapping;
import com.jiuqi.nr.mapping2.bean.PeriodMapping;
import com.jiuqi.nr.mapping2.bean.ZBMapping;
import com.jiuqi.nr.mapping2.common.CompleteUser;
import com.jiuqi.nr.mapping2.dto.ImpExpRule;
import com.jiuqi.nr.mapping2.dto.JIOContent;
import com.jiuqi.nr.mapping2.dto.NrMappingSchemeDTO;
import com.jiuqi.nr.mapping2.service.FormulaMappingService;
import com.jiuqi.nr.mapping2.service.JIOConfigService;
import com.jiuqi.nr.mapping2.service.PeriodMappingService;
import com.jiuqi.nr.mapping2.service.ZBMappingService;
import com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping;
import com.jiuqi.nvwa.mapping.bean.BaseDataMapping;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.bean.OrgMapping;
import com.jiuqi.nvwa.mapping.service.IBaseDataMappingService;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import com.jiuqi.nvwa.mapping.service.IOrgMappingService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nr.single.map.configurations.bean.AutoAppendCode;
import nr.single.map.configurations.bean.DataImportRule;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.MappingConfig;
import nr.single.map.configurations.bean.RuleKind;
import nr.single.map.configurations.bean.RuleMap;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.configurations.bean.SingleMappingConfig;
import nr.single.map.configurations.bean.SkipUnit;
import nr.single.map.configurations.bean.TableConfig;
import nr.single.map.configurations.bean.UnitCustomMapping;
import nr.single.map.configurations.bean.UnitMapping;
import nr.single.map.configurations.bean.UnitState;
import nr.single.map.configurations.bean.UpdateWay;
import nr.single.map.configurations.service.MappingConfigService;
import nr.single.map.data.facade.SingleFileEnumInfo;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileFormulaItem;
import nr.single.map.data.facade.SingleFileMappingConfig;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.internal.SingleFileEnumInfoImpl;
import nr.single.map.data.internal.SingleFileEnumItemImpl;
import nr.single.map.data.internal.SingleFileFieldInfoImpl;
import nr.single.map.data.internal.SingleFileFmdmInfoImpl;
import nr.single.map.data.internal.SingleFileFormulaItemImpl;
import nr.single.map.data.internal.SingleFileTableInfoImpl;
import nr.single.map.data.internal.SingleFileTaskInfoImpl;
import nr.single.map.data.service.SingleMappingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleMappingServiceImpl
implements SingleMappingService {
    private static final Logger logger = LoggerFactory.getLogger(SingleMappingServiceImpl.class);
    @Autowired
    private MappingConfigService oldMappingConfigService;
    @Autowired
    private IOrgMappingService unitMappingService;
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
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IFormulaRunTimeController formulaController;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;

    @Override
    public List<SingleConfigInfo> getAllMappingInTask(String taskKey) {
        List<SingleConfigInfo> list2;
        ArrayList<SingleConfigInfo> list = new ArrayList<SingleConfigInfo>();
        List<NrMappingSchemeDTO> mappingSchemes = this.getMappingSchemesByTask(taskKey);
        if (mappingSchemes != null && mappingSchemes.size() > 0) {
            for (NrMappingSchemeDTO mapingScheme : mappingSchemes) {
                JIOContent jioContext;
                if (!this.mapingJIoServie.isJIO((MappingScheme)mapingScheme) || (jioContext = this.getJIOContentByMsKey(mapingScheme.getKey())) == null) continue;
                SingleConfigInfo configInfo = new SingleConfigInfo();
                configInfo.setConfigKey(mapingScheme.getKey());
                configInfo.setConfigName(mapingScheme.getTitle());
                configInfo.setConfigCode(mapingScheme.getCode());
                configInfo.setFileFlag(jioContext.getFileCode());
                configInfo.setTaskFlag(jioContext.getTaskCode());
                configInfo.setSchemeKey(mapingScheme.getFormScheme());
                configInfo.setTaskKey(mapingScheme.getTask());
                list.add(configInfo);
            }
        }
        if ((list2 = this.oldMappingConfigService.getAllMappingInTask(taskKey)) != null && list2.size() > 0) {
            list.addAll(list2);
        }
        return list;
    }

    @Override
    public ISingleMappingConfig getConfigByKey(String mappingKey) {
        return this.getConfigByKeyAndType(mappingKey, 0);
    }

    @Override
    public ISingleMappingConfig getConfigByKeyAndType(String mappingKey, int infoType) {
        ISingleMappingConfig mappingConfig = null;
        MappingScheme mapingScheme = this.mapingSchemeService.getSchemeByKey(mappingKey);
        if (mapingScheme != null && this.mapingJIoServie.isJIO(mapingScheme)) {
            mappingConfig = this.copyMapConfig(mapingScheme, infoType);
        }
        if (mappingConfig == null) {
            mappingConfig = this.oldMappingConfigService.getConfigByKey(mappingKey);
        }
        return mappingConfig;
    }

    @Override
    public List<SingleConfigInfo> getAllMappingInReport(String schemeKey) {
        List<SingleConfigInfo> list2;
        List<NrMappingSchemeDTO> mappingSchemes;
        ArrayList<SingleConfigInfo> list = new ArrayList<SingleConfigInfo>();
        FormSchemeDefine formScheme = this.viewController.getFormScheme(schemeKey);
        if (formScheme != null && (mappingSchemes = this.getMappingSchemesByFormScheme(formScheme.getTaskKey(), schemeKey)) != null && mappingSchemes.size() > 0) {
            for (MappingScheme mappingScheme : mappingSchemes) {
                JIOContent jioContext = this.getJIOContentByMsKey(mappingScheme.getKey());
                if (jioContext == null) continue;
                SingleConfigInfo configInfo = new SingleConfigInfo();
                configInfo.setConfigKey(mappingScheme.getKey());
                configInfo.setConfigName(mappingScheme.getTitle());
                configInfo.setConfigCode(mappingScheme.getCode());
                configInfo.setFileFlag(jioContext.getFileCode());
                configInfo.setTaskFlag(jioContext.getTaskCode());
                configInfo.setSchemeKey(schemeKey);
                configInfo.setTaskKey(formScheme.getTaskKey());
                list.add(configInfo);
            }
        }
        if ((list2 = this.oldMappingConfigService.getAllMappingInReport(schemeKey)) != null && list2.size() > 0) {
            list.addAll(list2);
        }
        return list;
    }

    @Override
    public List<SingleConfigInfo> getAllMapping() {
        List<SingleConfigInfo> list2;
        ArrayList<SingleConfigInfo> list = new ArrayList<SingleConfigInfo>();
        List mappingSchemes2 = this.mapingSchemeService.getAllSchemes();
        List<MappingScheme> mappingSchemes = this.getMappingSchemesByFiter(mappingSchemes2);
        if (mappingSchemes != null && mappingSchemes.size() > 0) {
            for (MappingScheme mapingScheme : mappingSchemes) {
                JIOContent jioContext;
                NrMappingSchemeDTO nrMapingScheme = new NrMappingSchemeDTO(mapingScheme);
                if (!StringUtils.isNotEmpty((CharSequence)nrMapingScheme.getTask()) || !this.mapingJIoServie.isJIO(mapingScheme) || (jioContext = this.getJIOContentByMsKey(mapingScheme.getKey())) == null) continue;
                SingleConfigInfo configInfo = new SingleConfigInfo();
                configInfo.setConfigKey(mapingScheme.getKey());
                configInfo.setConfigName(mapingScheme.getTitle());
                configInfo.setConfigCode(mapingScheme.getCode());
                configInfo.setFileFlag(jioContext.getFileCode());
                configInfo.setTaskFlag(jioContext.getTaskCode());
                configInfo.setSchemeKey(nrMapingScheme.getFormScheme());
                configInfo.setTaskKey(nrMapingScheme.getTask());
                list.add(configInfo);
            }
        }
        if ((list2 = this.oldMappingConfigService.getAllMapping()) != null && list2.size() > 0) {
            list.addAll(list2);
        }
        return list;
    }

    @Override
    public List<SingleConfigInfo> getConfigInSingleTask(String singleTask) {
        List<SingleConfigInfo> list2;
        ArrayList<SingleConfigInfo> list = new ArrayList<SingleConfigInfo>();
        List mappingSchemes2 = this.mapingSchemeService.getAllSchemes();
        List<MappingScheme> mappingSchemes = this.getMappingSchemesByFiter(mappingSchemes2);
        if (mappingSchemes != null && mappingSchemes.size() > 0) {
            for (MappingScheme mapingScheme : mappingSchemes) {
                JIOContent jioContext;
                NrMappingSchemeDTO nrMapingScheme = new NrMappingSchemeDTO(mapingScheme);
                if (!StringUtils.isNotEmpty((CharSequence)nrMapingScheme.getTask()) || !this.mapingJIoServie.isJIO(mapingScheme) || (jioContext = this.getJIOContentByMsKey(mapingScheme.getKey())) == null || !StringUtils.isNotEmpty((CharSequence)singleTask) || !singleTask.equalsIgnoreCase(jioContext.getTaskCode())) continue;
                SingleConfigInfo configInfo = new SingleConfigInfo();
                configInfo.setConfigKey(mapingScheme.getKey());
                configInfo.setConfigName(mapingScheme.getTitle());
                configInfo.setConfigCode(mapingScheme.getCode());
                configInfo.setFileFlag(jioContext.getFileCode());
                configInfo.setTaskFlag(jioContext.getTaskCode());
                configInfo.setSchemeKey(nrMapingScheme.getFormScheme());
                configInfo.setTaskKey(nrMapingScheme.getTask());
                list.add(configInfo);
            }
        }
        if ((list2 = this.oldMappingConfigService.getConfigInSingleTask(singleTask)) != null && list2.size() > 0) {
            list.addAll(list2);
        }
        return list;
    }

    @Override
    public SingleConfigInfo getMappingByKey(String mappingKey) {
        SingleConfigInfo configInfo = null;
        MappingScheme mapingScheme = this.mapingSchemeService.getSchemeByKey(mappingKey);
        if (mapingScheme != null && this.mapingJIoServie.isJIO(mapingScheme)) {
            NrMappingSchemeDTO nrMapingScheme = new NrMappingSchemeDTO(mapingScheme);
            JIOContent jioContext = this.getJIOContentByMsKey(mapingScheme.getKey());
            if (jioContext != null) {
                configInfo = new SingleConfigInfo();
                configInfo.setConfigKey(mapingScheme.getKey());
                configInfo.setConfigName(mapingScheme.getTitle());
                configInfo.setConfigCode(mapingScheme.getCode());
                configInfo.setFileFlag(jioContext.getFileCode());
                configInfo.setTaskFlag(jioContext.getTaskCode());
                configInfo.setSchemeKey(nrMapingScheme.getFormScheme());
                configInfo.setTaskKey(nrMapingScheme.getTask());
            }
        }
        if (configInfo == null) {
            configInfo = this.oldMappingConfigService.getMappingByKey(mappingKey);
        }
        return configInfo;
    }

    private List<NrMappingSchemeDTO> getMappingSchemesByTask(String taskkey) {
        ArrayList<NrMappingSchemeDTO> list = new ArrayList<NrMappingSchemeDTO>();
        List mappingSchemes2 = this.mapingSchemeService.getAllSchemes();
        List<MappingScheme> mappingSchemes = this.getMappingSchemesByFiter(mappingSchemes2);
        if (mappingSchemes != null && mappingSchemes.size() > 0) {
            for (MappingScheme mapingScheme : mappingSchemes) {
                NrMappingSchemeDTO nrSchemeDto = new NrMappingSchemeDTO(mapingScheme);
                if (!StringUtils.isNotEmpty((CharSequence)nrSchemeDto.getTask()) || !nrSchemeDto.getTask().equalsIgnoreCase(taskkey)) continue;
                list.add(nrSchemeDto);
            }
        }
        return list;
    }

    private List<NrMappingSchemeDTO> getMappingSchemesByFormScheme(String taskkey, String formSchemeKey) {
        ArrayList<NrMappingSchemeDTO> list = new ArrayList<NrMappingSchemeDTO>();
        List mappingSchemes2 = this.mapingSchemeService.getAllSchemes();
        List<MappingScheme> mappingSchemes = this.getMappingSchemesByFiter(mappingSchemes2);
        if (mappingSchemes != null && mappingSchemes.size() > 0) {
            for (MappingScheme mapingScheme : mappingSchemes) {
                NrMappingSchemeDTO nrSchemeDto = new NrMappingSchemeDTO(mapingScheme);
                if (!"JIO".equalsIgnoreCase(nrSchemeDto.getType()) || !StringUtils.isNotEmpty((CharSequence)nrSchemeDto.getTask()) || !nrSchemeDto.getTask().equalsIgnoreCase(taskkey) || !StringUtils.isNotEmpty((CharSequence)nrSchemeDto.getFormScheme()) || !nrSchemeDto.getFormScheme().equalsIgnoreCase(formSchemeKey)) continue;
                list.add(nrSchemeDto);
            }
        }
        return list;
    }

    private List<MappingScheme> getMappingSchemesByFiter(List<MappingScheme> mappingSchemes) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String contextId = dsContext.getContextEntityId();
        if (StringUtils.isNotEmpty((CharSequence)contextId) && mappingSchemes != null && !mappingSchemes.isEmpty()) {
            String orgName = EntityUtils.getId((String)contextId);
            ArrayList<MappingScheme> mappingSchemesNew = new ArrayList<MappingScheme>();
            for (MappingScheme maping : mappingSchemes) {
                if (!StringUtils.isNotEmpty((CharSequence)orgName) || !orgName.equalsIgnoreCase(maping.getOrgName())) continue;
                mappingSchemesNew.add(maping);
            }
            return mappingSchemesNew;
        }
        return mappingSchemes;
    }

    private JIOContent getJIOContentByMsKey(String mappingKey) {
        JIOContent jioContext = null;
        try {
            jioContext = this.mapingJIoServie.getJIOContentByMs(mappingKey);
        }
        catch (Exception e) {
            jioContext = null;
            logger.error(e.getMessage(), e);
        }
        return jioContext;
    }

    private SingleMappingConfig copyMapConfig(MappingScheme mapingScheme, int infoType) {
        SingleMappingConfig config = new SingleMappingConfig();
        try {
            NrMappingSchemeDTO nrMapingScheme = new NrMappingSchemeDTO(mapingScheme);
            String msKey = mapingScheme.getKey();
            String formSchemeKey = nrMapingScheme.getFormScheme();
            if (infoType == 0) {
                Iterator<SingleFileFieldInfo> fieldNew;
                byte[] jioConfig = this.mapingJIoServie.getJIOConfigByMs(msKey);
                SingleFileMappingConfig mapScheme = null;
                try {
                    mapScheme = this.getMappingConfig(jioConfig);
                }
                catch (Exception e) {
                    logger.error("\u8bfb\u53d6\u6620\u5c04\u51fa\u9519\uff1a" + e.getMessage(), e);
                    return config;
                }
                config.setSchemeKey(nrMapingScheme.getFormScheme());
                config.setTaskKey(nrMapingScheme.getTask());
                TableConfig tableConfig = new TableConfig();
                config.setTableConfig(tableConfig);
                SingleFileTaskInfoImpl taskInfo = new SingleFileTaskInfoImpl();
                taskInfo.copyFrom(mapScheme.getTaskInfo());
                config.setTaskInfo(taskInfo);
                List formDefines = this.viewController.queryAllFormDefinesByFormScheme(nrMapingScheme.getFormScheme());
                ArrayList<SingleFileFormulaItem> items = new ArrayList<SingleFileFormulaItem>();
                List formulaSchemes = this.formulaController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
                for (FormulaSchemeDefine formulaScheme : formulaSchemes) {
                    FormulaSchemeType aType = formulaScheme.getFormulaSchemeType();
                    if (aType != FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT) continue;
                    Map reportFormulaMap = this.formulaMappingService.findByMSFormula(msKey, formulaScheme.getKey());
                    List formluaMappingsBJ = (List)reportFormulaMap.get("FORMULA-MAPPING-BETWEEN");
                    if (formluaMappingsBJ != null && !formluaMappingsBJ.isEmpty()) {
                        for (FormulaMapping aMaping : formluaMappingsBJ) {
                            if (StringUtils.isEmpty((CharSequence)aMaping.getmFormulaCode())) continue;
                            SingleFileFormulaItemImpl item = new SingleFileFormulaItemImpl();
                            item.setNetSchemeKey(aMaping.getFormulaScheme());
                            item.setNetSchemeName(formulaScheme.getTitle());
                            item.setNetFormCode("Sys_TableMiddle");
                            item.setNetFormKey(null);
                            item.setNetFormulaKey(null);
                            item.setNetFormulaCode(aMaping.getFormulaCode());
                            item.setSingleSchemeName(aMaping.getmFormulaScheme());
                            item.setSingleTableCode(null);
                            item.setSingleFormulaCode(aMaping.getmFormulaCode());
                            items.add(item);
                        }
                    }
                    for (FormDefine form : formDefines) {
                        List formluaMappings = (List)reportFormulaMap.get(form.getFormCode());
                        if (formluaMappings == null) continue;
                        for (FormulaMapping aMaping : formluaMappings) {
                            if (StringUtils.isEmpty((CharSequence)aMaping.getmFormulaCode())) continue;
                            SingleFileFormulaItemImpl item = new SingleFileFormulaItemImpl();
                            item.setNetSchemeKey(aMaping.getFormulaScheme());
                            item.setNetSchemeName(formulaScheme.getTitle());
                            item.setNetFormCode(aMaping.getFormCode());
                            item.setNetFormKey(form.getKey());
                            item.setNetFormulaKey(null);
                            item.setNetFormulaCode(aMaping.getFormulaCode());
                            item.setSingleSchemeName(aMaping.getmFormulaScheme());
                            item.setSingleTableCode(null);
                            item.setSingleFormulaCode(aMaping.getmFormulaCode());
                            items.add(item);
                        }
                    }
                }
                config.setFormulaInfos(items);
                ArrayList<SingleFileTableInfo> tableInfos = new ArrayList<SingleFileTableInfo>();
                HashMap<String, Object> singleFields = new HashMap<String, Object>();
                for (int i = -1; i < mapScheme.getTableInfos().size(); ++i) {
                    Object tableMap = null;
                    if (i == -1) {
                        SingleFileFmdmInfo fmdminfo = mapScheme.getFmdmInfo();
                        SingleFileFmdmInfoImpl fmdmInfoNew = new SingleFileFmdmInfoImpl();
                        fmdmInfoNew.copyFrom(fmdminfo);
                        tableInfos.add(fmdmInfoNew);
                        tableMap = fmdminfo;
                    } else {
                        SingleFileTableInfo tableInfo = mapScheme.getTableInfos().get(i);
                        Iterator<SingleFileRegionInfo> tableInfoNew = new SingleFileTableInfoImpl();
                        tableInfoNew.copyFrom(tableInfo);
                        tableInfos.add((SingleFileTableInfo)((Object)tableInfoNew));
                        tableMap = tableInfoNew;
                    }
                    Object tInfo = tableMap;
                    for (SingleFileFieldInfo field : tInfo.getRegion().getFields()) {
                        fieldNew = new SingleFileFieldInfoImpl();
                        fieldNew.copyFrom(field);
                        fieldNew.setNetFormCode(tInfo.getNetFormCode());
                        singleFields.put(tableMap.getSingleTableCode() + "[" + fieldNew.getFieldCode() + "]", fieldNew);
                    }
                    for (SingleFileRegionInfo subRegion : tInfo.getRegion().getSubRegions()) {
                        for (SingleFileFieldInfo field : subRegion.getFields()) {
                            SingleFileFieldInfoImpl fieldNew2 = new SingleFileFieldInfoImpl();
                            fieldNew2.copyFrom(field);
                            fieldNew2.setNetFormCode(tInfo.getNetFormCode());
                            singleFields.put(tableMap.getSingleTableCode() + "[" + fieldNew2.getFieldCode() + "]", fieldNew2);
                        }
                    }
                }
                config.setTableInfos(tableInfos);
                ArrayList<SingleFileFieldInfo> fieldInfos = new ArrayList<SingleFileFieldInfo>();
                for (FormDefine form : formDefines) {
                    List formRegions = this.viewController.getAllRegionsInForm(form.getKey());
                    HashMap<String, DataRegionDefine> formRegionCodes = new HashMap<String, DataRegionDefine>();
                    fieldNew = formRegions.iterator();
                    while (fieldNew.hasNext()) {
                        DataRegionDefine region = (DataRegionDefine)fieldNew.next();
                        formRegionCodes.put(region.getCode(), region);
                    }
                    List zbMappings = this.zbMappingService.findByMSAndForm(msKey, form.getFormCode());
                    for (ZBMapping zbMapping : zbMappings) {
                        if (StringUtils.isEmpty((CharSequence)zbMapping.getMapping())) continue;
                        SingleFileFieldInfo oldItem = null;
                        SingleFileFieldInfoImpl item = new SingleFileFieldInfoImpl();
                        if (singleFields.containsKey(zbMapping.getMapping())) {
                            oldItem = (SingleFileFieldInfo)singleFields.get(zbMapping.getMapping());
                        } else if (singleFields.containsKey(form.getFormCode() + "[" + zbMapping.getMapping() + "]")) {
                            oldItem = (SingleFileFieldInfo)singleFields.get(form.getFormCode() + "[" + zbMapping.getMapping() + "]");
                        }
                        if (oldItem != null) {
                            item.copyFrom(oldItem);
                        } else {
                            String fieldCode = zbMapping.getMapping();
                            String formCode = "";
                            int id1 = fieldCode.indexOf("[");
                            int id2 = fieldCode.indexOf("]");
                            if (id1 > 0 && id2 > 0) {
                                formCode = fieldCode.substring(0, id1);
                                fieldCode = fieldCode.substring(id1 + 1, id2);
                            }
                            item.setFieldCode(fieldCode);
                            item.setFormCode(formCode);
                            item.setTableCode(formCode);
                        }
                        item.setNetFormCode(form.getFormCode());
                        if (StringUtils.isNotEmpty((CharSequence)zbMapping.getRegionCode()) && formRegionCodes.containsKey(zbMapping.getRegionCode())) {
                            DataRegionDefine region = (DataRegionDefine)formRegionCodes.get(zbMapping.getRegionCode());
                            item.setRegionKey(region.getKey());
                        }
                        item.setRegionCode(zbMapping.getRegionCode());
                        item.setNetDataLinkKey(null);
                        item.setNetTableCode(zbMapping.getTable());
                        item.setNetFieldCode(zbMapping.getZbCode());
                        fieldInfos.add(item);
                    }
                }
                config.setZbFields(fieldInfos);
                List baseList = this.baseDataMappingService.getBaseDataMapping(msKey);
                HashMap<String, BaseDataMapping> baseDic = new HashMap<String, BaseDataMapping>();
                for (BaseDataMapping baseData : baseList) {
                    baseDic.put(baseData.getBaseDataCode(), baseData);
                }
                LinkedHashMap<String, SingleFileEnumInfo> enumInfos = new LinkedHashMap<String, SingleFileEnumInfo>();
                LinkedHashMap<String, SingleFileEnumInfo> netEnumInfos = new LinkedHashMap<String, SingleFileEnumInfo>();
                List baseItems = this.baseDataMappingService.getAllBaseDataItem(msKey);
                for (BaseDataItemMapping baseItem : baseItems) {
                    SingleFileEnumInfo baseEnum = null;
                    if (netEnumInfos.containsKey(baseItem.getBaseDataCode())) {
                        baseEnum = (SingleFileEnumInfo)netEnumInfos.get(baseItem.getBaseDataCode());
                    } else {
                        baseEnum = new SingleFileEnumInfoImpl();
                        baseEnum.setNetTableCode(baseItem.getBaseDataCode());
                        if (baseDic.containsKey(baseItem.getBaseDataCode())) {
                            BaseDataMapping baseDataMaping = (BaseDataMapping)baseDic.get(baseItem.getBaseDataCode());
                            baseEnum.setEnumCode(baseDataMaping.getMappingCode());
                            baseEnum.setEnumTitle(baseDataMaping.getMappingTitle());
                            if (StringUtils.isEmpty((CharSequence)baseDataMaping.getMappingCode())) {
                                logger.info("\u679a\u4e3e\u6620\u5c04\u6ca1\u6709\u5bf9\u5e94\u7684\u5355\u673a\u7248\u6807\u8bc6\uff1a" + baseDataMaping.getBaseDataCode());
                                baseEnum.setEnumCode(baseItem.getBaseDataCode());
                            }
                        } else {
                            logger.info("\u679a\u4e3e\u6620\u5c04\u6ca1\u6709\u5bf9\u5e94\u7684\u5355\u673a\u7248\u6807\u8bc6\uff1a" + baseItem.getBaseDataCode());
                            baseEnum.setEnumCode(baseItem.getBaseDataCode());
                        }
                        enumInfos.put(baseEnum.getEnumCode(), baseEnum);
                        netEnumInfos.put(baseEnum.getNetTableCode(), baseEnum);
                    }
                    SingleFileEnumItemImpl newItem = new SingleFileEnumItemImpl();
                    newItem.setItemCode(baseItem.getMappingCode());
                    newItem.setItemTitle(baseItem.getMappingTitle());
                    newItem.setNetItemCode(baseItem.getBaseDataItemCode());
                    newItem.setNetItemTitle(null);
                    baseEnum.getEnumItems().add(newItem);
                }
                config.setEnumInfos(enumInfos);
                UnitMapping mapping = new UnitMapping();
                ArrayList<UnitCustomMapping> unitInfos = new ArrayList<UnitCustomMapping>();
                List unitMappings = this.unitMappingService.getOrgMappingByMS(msKey);
                for (OrgMapping unitMapping : unitMappings) {
                    UnitCustomMapping item = new UnitCustomMapping();
                    item.setBblx(null);
                    item.setImportIndex(null);
                    item.setNetUnitKey(unitMapping.getCode());
                    item.setNetUnitName(null);
                    item.setSingleUnitCode(unitMapping.getMapping());
                    item.setSingleParentUnitCode(unitMapping.getParentMapping());
                    unitInfos.add(item);
                }
                mapping.setUnitInfos(unitInfos);
                HashMap<String, String> periodMappingList = new HashMap<String, String>();
                List periodMappings = this.periodMapingService.findByMS(msKey);
                for (PeriodMapping periodMapping : periodMappings) {
                    periodMappingList.put(periodMapping.getMapping(), periodMapping.getPeriod());
                }
                mapping.setPeriodMapping(periodMappingList);
                config.setMapping(mapping);
            }
            if (infoType == 0 || infoType == 1) {
                this.copyJioOtherConfig(msKey, config);
            }
        }
        catch (Exception e) {
            logger.error("\u8bfb\u53d6\u6620\u5c04\u51fa\u9519\uff1a" + e.getMessage(), e);
            return null;
        }
        return config;
    }

    private void copyJioOtherConfig(String msKey, SingleMappingConfig config) {
        ArrayList<RuleMap> mapRule = new ArrayList<RuleMap>();
        com.jiuqi.nr.mapping2.bean.MappingConfig config2 = null;
        try {
            UpdateWay updateWay;
            List exportRules;
            config2 = this.mapingJIoServie.queryMappingConfig(msKey);
            List importRules = config2.getImportRule();
            if (importRules != null) {
                for (ImpExpRule oldRule : importRules) {
                    String singleCode = oldRule.getSingleCode();
                    String netFieldCode = oldRule.getNetCode();
                    RuleMap map1 = new RuleMap(RuleKind.UNIT_MAP_IMPORT, singleCode, netFieldCode);
                    mapRule.add(map1);
                }
            }
            if ((exportRules = config2.getExportRule()) != null) {
                for (ImpExpRule oldRule : exportRules) {
                    String singleCode = oldRule.getSingleCode();
                    String netFieldCode = oldRule.getNetCode();
                    RuleMap map1 = new RuleMap(RuleKind.UNIT_MAP_EXPORT, singleCode, netFieldCode);
                    mapRule.add(map1);
                }
            }
            config.setMapRule(mapRule);
            MappingConfig configInfo = new MappingConfig();
            config.setConfig(configInfo);
            ArrayList<String> arithmetic = new ArrayList<String>();
            if (config2.isUploadCalc()) {
                for (String aCode : config2.getCalcFSKeys()) {
                    arithmetic.add(aCode);
                }
            }
            configInfo.setArithmetic(arithmetic);
            ArrayList<String> examine = new ArrayList<String>();
            if (config2.isUploadCheck()) {
                examine.addAll(config2.getCheckFSKeys());
            }
            configInfo.setExamine(examine);
            configInfo.setUploadStatus(config2.isUploadReport());
            configInfo.setForceUpload(config2.isForceReport());
            configInfo.setUploadEntityAndData(config2.isUploadEntityAndData());
            configInfo.setCheckParent(config2.isCheckParent());
            configInfo.setConfigParentNode(config2.isConfigParentNode());
            configInfo.setImpByUnitAllCover(config2.isImpByUnitAllCover());
            if (CompleteUser.EMPTY == config2.getCompleteUser()) {
                configInfo.setCompleteUser(nr.single.map.configurations.bean.CompleteUser.EMPTY);
            } else if (CompleteUser.IMPORTUSER == config2.getCompleteUser()) {
                configInfo.setCompleteUser(nr.single.map.configurations.bean.CompleteUser.IMPORTUSER);
            }
            if (config2.getUnitUpdateWay() != null) {
                updateWay = new UpdateWay();
                updateWay.setIncrement(config2.getUnitUpdateWay().isCreate());
                updateWay.setUpdate(config2.getUnitUpdateWay().isUpdate());
                ArrayList<String> ignoreAttribute = new ArrayList<String>();
                if (config2.getUnitUpdateWay().getIgnoreAttribute() != null) {
                    ignoreAttribute.addAll(config2.getUnitUpdateWay().getIgnoreAttribute());
                }
                updateWay.setIgnoreAttribute(ignoreAttribute);
                configInfo.setUnitUpdateWay(updateWay);
            } else {
                updateWay = new UpdateWay();
                updateWay.setIncrement(true);
                updateWay.setUpdate(false);
                configInfo.setUnitUpdateWay(updateWay);
            }
            if (config2.getSkipUnit() != null) {
                SkipUnit skipUnit = new SkipUnit();
                skipUnit.setFormula(config2.getSkipUnit().getFormula());
                ArrayList<String> unitKeys = new ArrayList<String>();
                if (config2.getSkipUnit().getUnitKey() != null) {
                    unitKeys.addAll(config2.getSkipUnit().getUnitKey());
                }
                skipUnit.setUnitKey(unitKeys);
                configInfo.setSkipUnit(skipUnit);
            }
            if (config2.getAutoAppendCode() != null) {
                AutoAppendCode autoAppendCode = new AutoAppendCode();
                autoAppendCode.setAppendCodeZb(config2.getAutoAppendCode().getAppendCodeZb());
                HashMap<String, String> codeMappings = new HashMap<String, String>();
                codeMappings.putAll(config2.getAutoAppendCode().getCodeMapping());
                autoAppendCode.setCodeMapping(codeMappings);
                autoAppendCode.setAutoAppendCode(StringUtils.isNotEmpty((CharSequence)autoAppendCode.getAppendCodeZb()) && !codeMappings.isEmpty());
                configInfo.setAutoAppendCode(autoAppendCode);
            }
            DataImportRule dataImportRule = new DataImportRule();
            if (config2.getCoverDataStates() != null && config2.getCoverDataStates().size() > 0) {
                dataImportRule.setEnable(true);
                ArrayList<UnitState> importDatas = new ArrayList<UnitState>();
                ArrayList<UnitState> unImportDatas = new ArrayList<UnitState>();
                for (String stateCode : config2.getCoverDataStates()) {
                    UnitState unitState = UnitState.UN_UPLOAD;
                    if ("UN_UPLOAD".equalsIgnoreCase(stateCode)) {
                        unitState = UnitState.UN_UPLOAD;
                    } else if ("SUBMIT".equalsIgnoreCase(stateCode)) {
                        unitState = UnitState.SUBMIT;
                    } else if ("UPLOAD".equalsIgnoreCase(stateCode)) {
                        unitState = UnitState.UPLOAD;
                    } else if ("REJECT".equalsIgnoreCase(stateCode)) {
                        unitState = UnitState.REJECT;
                    } else {
                        if (!"CONFIRM".equalsIgnoreCase(stateCode)) continue;
                        unitState = UnitState.CONFIRM;
                    }
                    importDatas.add(unitState);
                }
                if (!importDatas.contains((Object)UnitState.UN_UPLOAD)) {
                    unImportDatas.add(UnitState.UN_UPLOAD);
                }
                if (!importDatas.contains((Object)UnitState.SUBMIT)) {
                    unImportDatas.add(UnitState.SUBMIT);
                }
                if (!importDatas.contains((Object)UnitState.UPLOAD)) {
                    unImportDatas.add(UnitState.UPLOAD);
                }
                if (!importDatas.contains((Object)UnitState.REJECT)) {
                    unImportDatas.add(UnitState.REJECT);
                }
                if (!importDatas.contains((Object)UnitState.CONFIRM)) {
                    unImportDatas.add(UnitState.CONFIRM);
                }
                dataImportRule.setImportData(importDatas);
                dataImportRule.setUnImportData(unImportDatas);
            } else {
                dataImportRule.setEnable(false);
            }
            configInfo.setDataImportRule(dataImportRule);
        }
        catch (JQException e) {
            logger.error("\u8bfb\u53d6\u5bfc\u5165\u5bfc\u51fa\u89c4\u5219\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
    }

    private SingleFileMappingConfig getMappingConfig(byte[] data) throws StreamReadException, DatabindException, IOException {
        if (null == data) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return (SingleFileMappingConfig)objectMapper.readValue(data, SingleFileMappingConfig.class);
    }

    @Override
    public void saveEntityUnitCustomMapping(String mappingKey, List<UnitCustomMapping> unitInfos) {
        SingleConfigInfo configInfo = this.oldMappingConfigService.getMappingByKey(mappingKey);
        if (configInfo != null) {
            this.oldMappingConfigService.saveEntityUnitCustomMapping(mappingKey, unitInfos);
        }
    }
}

