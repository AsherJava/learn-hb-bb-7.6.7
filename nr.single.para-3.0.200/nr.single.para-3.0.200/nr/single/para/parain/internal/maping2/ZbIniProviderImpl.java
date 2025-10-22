/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.definition.facade.UniversalFieldDefine
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.mapping2.bean.ZBMapping
 *  com.jiuqi.nr.mapping2.common.MappingErrorEnum
 *  com.jiuqi.nr.mapping2.dto.NrMappingSchemeDTO
 *  com.jiuqi.nr.mapping2.service.JIOConfigService
 *  com.jiuqi.nr.mapping2.service.ZBMappingService
 *  com.jiuqi.nr.mapping2.service.ZbIniProvider
 *  com.jiuqi.nr.mapping2.web.vo.Result
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  com.jiuqi.xlib.utils.StringUtil
 *  nr.single.map.configurations.bean.ZbMapping
 *  nr.single.map.configurations.file.ini.BufferIni
 *  nr.single.map.configurations.file.ini.Ident
 *  nr.single.map.configurations.file.ini.IniBuffer
 *  nr.single.map.configurations.file.ini.IniZBInfo
 *  nr.single.map.configurations.file.ini.MemStream
 *  nr.single.map.configurations.file.ini.Section
 *  nr.single.map.configurations.file.ini.Stream
 *  nr.single.map.configurations.file.ini.StreamIniBuffer
 *  nr.single.map.configurations.internal.bean.IllegalData
 *  nr.single.map.data.facade.SingleFileFieldInfo
 *  nr.single.map.data.facade.SingleFileFmdmInfo
 *  nr.single.map.data.facade.SingleFileMappingConfig
 *  nr.single.map.data.facade.SingleFileRegionInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 *  nr.single.map.data.internal.SingleFileFieldInfoImpl
 *  org.apache.commons.lang3.StringUtils
 */
package nr.single.para.parain.internal.maping2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.facade.UniversalFieldDefine;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.mapping2.bean.ZBMapping;
import com.jiuqi.nr.mapping2.common.MappingErrorEnum;
import com.jiuqi.nr.mapping2.dto.NrMappingSchemeDTO;
import com.jiuqi.nr.mapping2.service.JIOConfigService;
import com.jiuqi.nr.mapping2.service.ZBMappingService;
import com.jiuqi.nr.mapping2.service.ZbIniProvider;
import com.jiuqi.nr.mapping2.web.vo.Result;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import com.jiuqi.xlib.utils.StringUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import nr.single.map.configurations.bean.ZbMapping;
import nr.single.map.configurations.file.ini.BufferIni;
import nr.single.map.configurations.file.ini.Ident;
import nr.single.map.configurations.file.ini.IniBuffer;
import nr.single.map.configurations.file.ini.IniZBInfo;
import nr.single.map.configurations.file.ini.MemStream;
import nr.single.map.configurations.file.ini.Section;
import nr.single.map.configurations.file.ini.Stream;
import nr.single.map.configurations.file.ini.StreamIniBuffer;
import nr.single.map.configurations.internal.bean.IllegalData;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileMappingConfig;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.internal.SingleFileFieldInfoImpl;
import nr.single.para.parain.internal.maping2.JIOProviderImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZbIniProviderImpl
implements ZbIniProvider {
    private static final Logger logger = LoggerFactory.getLogger(JIOProviderImpl.class);
    private String formSchemeKey;
    @Autowired
    private IRuntimeFormService formService;
    @Autowired
    private IDataDefinitionRuntimeController iRunTimeCtrl;
    @Autowired
    private IRunTimeViewController runtimeCtrl;
    @Autowired
    private IFMDMAttributeService ifmdmAttributeService;
    @Autowired
    private IMappingSchemeService mapingSchemeService;
    @Autowired
    private ZBMappingService zbMappingService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private JIOConfigService mapingJIoServie;

    public Result uploadZbByINI(String msKey, String formCode, byte[] file) {
        logger.info("\u6620\u5c04\u6587\u4ef6ini\u5bfc\u5165");
        MappingScheme mappingScheme = this.mapingSchemeService.getSchemeByKey(msKey);
        NrMappingSchemeDTO nrMapingScheme = new NrMappingSchemeDTO(mappingScheme);
        this.formSchemeKey = nrMapingScheme.getFormScheme();
        if (StringUtils.isEmpty((CharSequence)this.formSchemeKey)) {
            return new Result(false, null, "\u672a\u7ed1\u5b9a\u4efb\u52a1\u548c\u62a5\u8868\u65b9\u6848");
        }
        this.parseFileMapping(msKey, file);
        return new Result(true, null, "");
    }

    public void parseFileMapping(String msKey, byte[] files) {
        HashMap<String, Map<String, IniZBInfo>> iniZBManager = new HashMap<String, Map<String, IniZBInfo>>();
        HashMap<String, HashMap<String, List<String>>> netDimIndex = new HashMap<String, HashMap<String, List<String>>>();
        IllegalData errorData = new IllegalData();
        try (ByteArrayInputStream zbMappingstream = new ByteArrayInputStream(files);){
            MemStream stream = new MemStream();
            stream.copyFrom((InputStream)zbMappingstream, (long)zbMappingstream.available());
            StreamIniBuffer aBuffer = new StreamIniBuffer((Stream)stream);
            BufferIni ini = new BufferIni((IniBuffer)aBuffer);
            ini.readInfo();
            int count = ini.count();
            ArrayList<String> secList = new ArrayList<String>();
            List<SingleFileFieldInfo> zbInfos = this.buildTotalZbInfos(msKey);
            Map<String, List<SingleFileFieldInfo>> formGroup = zbInfos.stream().filter(e -> e.getFormCode() != null).collect(Collectors.groupingBy(SingleFileFieldInfo::getFormCode));
            Map<String, List<SingleFileFieldInfo>> fieldGroup = zbInfos.stream().filter(e -> e.getFieldCode() != null).collect(Collectors.groupingBy(SingleFileFieldInfo::getFieldCode));
            for (int i = 0; i < count; ++i) {
                Map identMap;
                Section sec = ini.get(i);
                int secCount = sec.count();
                String sectionName = sec.name();
                if (0 == secCount || "TASK".equalsIgnoreCase(sectionName) || "BBLX".equalsIgnoreCase(sectionName)) continue;
                if (!formGroup.isEmpty() && formGroup.get(sectionName) == null) {
                    errorData.addErrorSingleForm("\u4e0d\u5b58\u5728\u7684\u5e73\u53f0\u62a5\u8868\uff01", String.valueOf(i), sectionName);
                    continue;
                }
                secList.add(sectionName);
                HashMap<String, IniZBInfo> iniSingleManager = new HashMap<String, IniZBInfo>();
                HashMap<String, List<String>> index = (HashMap<String, List<String>>)netDimIndex.get(sectionName);
                if (index == null) {
                    index = new HashMap<String, List<String>>();
                    netDimIndex.put(sectionName, index);
                }
                if (null == (identMap = sec.getIdentMap())) continue;
                for (Ident ident : identMap.values()) {
                    if (ident.getType() != 5) continue;
                    String value = sec.readString(ident.getName(), "");
                    if (fieldGroup.isEmpty()) {
                        this.insertZBInfo(ident.getName(), value, iniSingleManager, index);
                        continue;
                    }
                    if (fieldGroup.get(ident.getName()) == null) continue;
                    this.insertZBInfo(ident.getName(), value, iniSingleManager, index);
                }
                iniZBManager.put(sectionName, iniSingleManager);
            }
            List<ZbMapping> zbInfos2 = this.getZbMappingByINIFile(iniZBManager, errorData, this.formSchemeKey);
            this.saveZbinfos(msKey, zbInfos2);
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
    }

    private List<ZbMapping> getZbMappingByINIFile(Map<String, Map<String, IniZBInfo>> iniZBManager, IllegalData errorData, String formSchemeKey) {
        ArrayList<ZbMapping> zbMappings = new ArrayList<ZbMapping>();
        List formDefines = this.formService.queryFormDefinesByFormScheme(formSchemeKey);
        FormSchemeDefine formScheme = this.runtimeCtrl.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runtimeCtrl.queryTaskDefine(formScheme.getTaskKey());
        DataScheme dataScheme = this.dataSchemeSevice.getDataScheme(taskDefine.getDataScheme());
        Map<String, FormDefine> formGroup = formDefines.stream().collect(Collectors.toMap(FormDefine::getFormCode, e -> e));
        for (String formCode : iniZBManager.keySet()) {
            Map<String, IniZBInfo> v = iniZBManager.get(formCode);
            List<FieldDefine> allFieldsInForm = new ArrayList();
            ArrayList allLinksInRegion = new ArrayList();
            HashMap<String, DataRegionDefine> allRegionInForm = new HashMap<String, DataRegionDefine>();
            boolean isFMDM = false;
            FormDefine formDefine = formGroup.get(formCode);
            if (formDefine != null) {
                isFMDM = FormType.FORM_TYPE_NEWFMDM.equals((Object)formDefine.getFormType());
                try {
                    if (isFMDM) {
                        FormSchemeDefine formSchemeDefine = this.runtimeCtrl.getFormScheme(formDefine.getFormScheme());
                        Object dw = formSchemeDefine.getDw();
                        if (StringUtils.isEmpty((CharSequence)dw)) {
                            dw = taskDefine.getDw();
                        }
                        FMDMAttributeDTO fMDMAttributeDTO = new FMDMAttributeDTO();
                        fMDMAttributeDTO.setEntityId((String)dw);
                        fMDMAttributeDTO.setFormSchemeKey(formSchemeKey);
                        List attributes = this.ifmdmAttributeService.list(fMDMAttributeDTO);
                        for (IFMDMAttribute attribute : attributes) {
                            FieldDefine fieldDefine = this.iRunTimeCtrl.queryFieldDefine(attribute.getID());
                            if (fieldDefine == null) continue;
                            allFieldsInForm.add(fieldDefine);
                        }
                    } else {
                        List keysInForm = this.runtimeCtrl.getFieldKeysInForm(formDefine.getKey());
                        allFieldsInForm = this.iRunTimeCtrl.queryFieldDefinesInRange((Collection)keysInForm);
                    }
                    List allRegionsInForm = this.runtimeCtrl.getAllRegionsInForm(formDefine.getKey());
                    for (DataRegionDefine define : allRegionsInForm) {
                        allRegionInForm.put(define.getKey(), define);
                        List linksInRegion = this.runtimeCtrl.getAllLinksInRegion(define.getKey());
                        allLinksInRegion.addAll(linksInRegion);
                    }
                }
                catch (Exception e2) {
                    logger.error(e2.getMessage(), e2);
                }
            }
            Map<Object, Object> codeGroup = new HashMap();
            HashMap codeAliasGroup = new HashMap();
            HashMap codeTableGroup = new HashMap();
            if (allFieldsInForm != null) {
                codeGroup = allFieldsInForm.stream().collect(Collectors.groupingBy(UniversalFieldDefine::getCode));
                for (FieldDefine field : allFieldsInForm) {
                    DataTable dataTable = this.dataSchemeSevice.getDataTable(field.getOwnerTableKey());
                    String aCode = dataTable.getCode() + "[" + field.getCode() + "]";
                    List<FieldDefine> list = null;
                    if (codeTableGroup.containsKey(aCode)) {
                        list = (List)codeTableGroup.get(aCode);
                    } else {
                        list = new ArrayList();
                        codeTableGroup.put(aCode, list);
                    }
                    list.add(field);
                    if (!StringUtils.isNotEmpty((CharSequence)field.getAlias())) continue;
                    if (codeAliasGroup.containsKey(field.getAlias())) {
                        ((List)codeAliasGroup.get(field.getAlias())).add(field);
                        continue;
                    }
                    ArrayList<FieldDefine> list2 = new ArrayList<FieldDefine>();
                    list2.add(field);
                    codeAliasGroup.put(field.getAlias(), list2);
                }
            }
            Map<String, DataLinkDefine> linkMap = allLinksInRegion.stream().filter(e -> !StringUtils.isEmpty((CharSequence)e.getLinkExpression())).collect(Collectors.toMap(DataLinkDefine::getLinkExpression, l -> l, (l1, l2) -> l2));
            Map<Object, Object> finalCodeGroup = codeGroup;
            boolean finalIsFMDM = isFMDM;
            for (String code : v.keySet()) {
                DataLinkDefine linkDefines;
                String regionCode;
                String regionKey;
                String link;
                FieldType type;
                String fieldCode;
                String fieldKey;
                String tableCode;
                String netFormCode;
                IniZBInfo item;
                block36: {
                    item = v.get(code);
                    netFormCode = formCode;
                    tableCode = item.getTableName();
                    fieldKey = null;
                    fieldCode = null;
                    type = null;
                    link = null;
                    regionKey = null;
                    regionCode = null;
                    List fieldDefines = (List)finalCodeGroup.get(item.getZBName());
                    if (fieldDefines == null) {
                        String aCode = "";
                        if (StringUtils.isNotEmpty((CharSequence)dataScheme.getPrefix())) {
                            aCode = dataScheme.getPrefix() + "_" + formCode + "[" + item.getZBName() + "]";
                            fieldDefines = (List)codeTableGroup.get(aCode);
                        }
                    }
                    if (fieldDefines == null) {
                        fieldDefines = (List)finalCodeGroup.get(item.getPtCode());
                    }
                    TableDefine tableDefine = null;
                    try {
                        if (fieldDefines != null && fieldDefines.size() > 0) {
                            tableDefine = this.iRunTimeCtrl.queryTableDefine(((FieldDefine)fieldDefines.get(0)).getOwnerTableKey());
                        }
                    }
                    catch (Exception e1) {
                        logger.info(e1.getMessage());
                    }
                    if (StringUtils.isEmpty((CharSequence)tableCode)) {
                        if (fieldDefines != null && fieldDefines.size() > 0) {
                            if (tableDefine != null) {
                                tableCode = tableDefine.getCode();
                            }
                            fieldKey = ((FieldDefine)fieldDefines.get(0)).getKey();
                            fieldCode = ((FieldDefine)fieldDefines.get(0)).getCode();
                            type = ((FieldDefine)fieldDefines.get(0)).getType();
                        }
                    } else if (fieldDefines != null && fieldDefines.size() > 0 && tableDefine != null && tableCode.equals(tableDefine.getCode())) {
                        fieldKey = ((FieldDefine)fieldDefines.get(0)).getKey();
                        fieldCode = ((FieldDefine)fieldDefines.get(0)).getCode();
                        type = ((FieldDefine)fieldDefines.get(0)).getType();
                    } else if (tableCode.indexOf(95) != -1) {
                        netFormCode = tableCode.substring(tableCode.indexOf(95) + 1, tableCode.length());
                        try {
                            FieldDefine fieldDefine = null;
                            tableDefine = this.iRunTimeCtrl.queryTableDefineByCode(tableCode);
                            if (tableDefine != null) {
                                fieldDefine = this.iRunTimeCtrl.queryFieldByCodeInTable(item.getZBName(), tableDefine.getKey());
                            } else {
                                errorData.addErrorZb("\u4e0d\u5b58\u5728\u7684\u5b58\u50a8\u8868\uff01", formCode, item.getPtCode(), tableCode);
                            }
                            if (fieldDefine != null) {
                                fieldKey = fieldDefine.getKey();
                                fieldCode = fieldDefine.getCode();
                                type = fieldDefine.getType();
                                break block36;
                            }
                            errorData.addErrorZb("\u4e0d\u5b58\u5728\u7684\u6307\u6807\uff01", formCode, item.getPtCode(), item.getZBName());
                        }
                        catch (Exception e3) {
                            logger.error(e3.getMessage(), e3);
                        }
                    } else {
                        errorData.addErrorZb("\u4e0d\u5b58\u5728\u7684\u5b58\u50a8\u8868\uff01", formCode, item.getPtCode(), tableCode);
                    }
                }
                if ((linkDefines = finalIsFMDM ? linkMap.get(fieldCode) : linkMap.get(fieldKey)) != null) {
                    link = linkDefines.getKey();
                    regionKey = linkDefines.getRegionKey();
                    if (allRegionInForm.containsKey(regionKey)) {
                        // empty if block
                    }
                }
                SingleFileFieldInfo s = this.buildSingleInfo(netFormCode, item, tableCode, fieldKey, formCode, type, link);
                SingleFileFieldInfoImpl s1 = (SingleFileFieldInfoImpl)s;
                s1.setNetFieldCode(fieldCode);
                s1.setRegionKey(regionKey);
                s1.setRegionCode(regionCode);
                this.setFilterMappingData(zbMappings, netFormCode, s);
            }
        }
        return zbMappings;
    }

    private SingleFileFieldInfo buildSingleInfo(String netFormCode, IniZBInfo item, String tableCode, String fieldKey, String formCode, FieldType type, String link) {
        SingleFileFieldInfoImpl s = new SingleFileFieldInfoImpl();
        s.setNetFormCode(netFormCode);
        s.setNetFieldCode(item.getZBName());
        s.setNetDataLinkKey(link);
        s.setEnumCode(item.getZBName());
        s.setFieldType(type);
        s.setNetTableCode(tableCode);
        s.setNetFieldKey(fieldKey);
        s.setTableCode(tableCode);
        s.setFieldCode(item.getPtCode());
        s.setFormCode(formCode);
        return s;
    }

    private void setFilterMappingData(List<ZbMapping> zbMappings, String formCode, SingleFileFieldInfo s) {
        if (formCode == null || "".equals(formCode)) {
            return;
        }
        Map<String, List<ZbMapping>> map = zbMappings.stream().collect(Collectors.groupingBy(ZbMapping::getReportCode));
        ZbMapping zb = new ZbMapping();
        if (map.get(formCode) != null) {
            zb = map.get(formCode).get(0);
        } else {
            zb.setReportCode(formCode);
        }
        if (s.getNetFormCode() != null && !"".equals(s.getNetFieldCode())) {
            zb.putZbInfo(s.getNetFieldCode(), s);
        }
        if (map.get(formCode) == null) {
            zbMappings.add(zb);
        }
    }

    private void insertZBInfo(String key, String msg, Map<String, IniZBInfo> iniSingleMap, Map<String, List<String>> newZbIndex) {
        if (key.endsWith("_PeriodStr")) {
            int index = key.lastIndexOf(95);
            key = key.substring(0, index);
            IniZBInfo iniZBInfo = this.getIniZBInfo(key, iniSingleMap);
            iniZBInfo.setPeriodStr(msg);
        } else if (key.startsWith("Dim")) {
            key = key.substring(3);
            IniZBInfo iniZBInfo = this.getIniZBInfo(key, iniSingleMap);
            iniZBInfo.setDim(msg);
        } else {
            IniZBInfo iniZBInfo = this.getIniZBInfo(key, iniSingleMap);
            int stIdx = msg.indexOf(91);
            if (stIdx > 0) {
                String tableName = msg.substring(0, stIdx);
                iniZBInfo.setTableName(tableName);
                msg = msg.substring(stIdx + 1, msg.length() - 1);
            }
            iniZBInfo.setZBName(msg);
            List<String> ptZbList = newZbIndex.get(msg);
            if (ptZbList == null) {
                ptZbList = new ArrayList<String>();
                newZbIndex.put(msg, ptZbList);
            }
            if (!ptZbList.contains(key)) {
                ptZbList.add(key);
            }
        }
    }

    private IniZBInfo getIniZBInfo(String str, Map<String, IniZBInfo> iniSingleMap) {
        IniZBInfo iniZBInfo = iniSingleMap.get(str);
        if (null == iniZBInfo) {
            iniZBInfo = new IniZBInfo();
            iniZBInfo.setPtCode(str);
            iniSingleMap.put(str, iniZBInfo);
        }
        return iniZBInfo;
    }

    private List<SingleFileFieldInfo> buildTotalZbInfos(String msKey) {
        byte[] data;
        ArrayList<SingleFileMappingConfig> configInfos = new ArrayList<SingleFileMappingConfig>();
        if (this.mapingJIoServie.isJIO(msKey) && (data = this.mapingJIoServie.getJIOConfigByMs(msKey)) != null) {
            try {
                SingleFileMappingConfig config = this.getMappingConfig(data);
                if (config != null) {
                    configInfos.add(config);
                }
            }
            catch (JQException e) {
                logger.error(e.getMessage(), e);
            }
        }
        ArrayList<SingleFileFieldInfo> zbInfos = new ArrayList<SingleFileFieldInfo>();
        if (configInfos != null && configInfos.size() > 0) {
            for (SingleFileMappingConfig configInfo : configInfos) {
                SingleFileFmdmInfo fmdm = configInfo.getFmdmInfo();
                zbInfos.addAll(fmdm.getRegion().getFields());
                for (SingleFileTableInfo table : configInfo.getTableInfos()) {
                    zbInfos.addAll(table.getRegion().getFields());
                    if (table.getRegion().getSubRegions() == null || table.getRegion().getSubRegions().isEmpty()) continue;
                    for (SingleFileRegionInfo region : table.getRegion().getSubRegions()) {
                        zbInfos.addAll(region.getFields());
                    }
                }
            }
        }
        return zbInfos;
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

    private void saveZbinfos(String msKey, List<ZbMapping> zbInfos) {
        for (ZbMapping zbMapping : zbInfos) {
            String netFormCode = zbMapping.getReportCode();
            if (StringUtil.isEmpty((String)netFormCode)) continue;
            ArrayList<ZBMapping> newList = new ArrayList<ZBMapping>();
            for (SingleFileFieldInfo zb : zbMapping.getZbInfo().values()) {
                ZBMapping newItem = new ZBMapping();
                newItem.setKey(UUID.randomUUID().toString());
                newItem.setMsKey(msKey);
                newItem.setForm(netFormCode);
                newItem.setTable(zb.getNetTableCode());
                newItem.setZbCode(zb.getNetFieldCode());
                if (StringUtils.isNotEmpty((CharSequence)zb.getFormCode()) && zb.getFormCode().equalsIgnoreCase(zb.getNetFormCode())) {
                    newItem.setMapping(zb.getFormCode() + "[" + zb.getFieldCode() + "]");
                } else if (StringUtils.isNotEmpty((CharSequence)zb.getFormCode())) {
                    newItem.setMapping(zb.getFormCode() + "[" + zb.getFieldCode() + "]");
                } else {
                    newItem.setZbCode(zb.getFieldCode());
                }
                if (!StringUtils.isNotEmpty((CharSequence)zb.getNetTableCode())) continue;
                newList.add(newItem);
            }
            if (newList.isEmpty()) continue;
            this.zbMappingService.save(msKey, netFormCode, newList);
        }
    }
}

