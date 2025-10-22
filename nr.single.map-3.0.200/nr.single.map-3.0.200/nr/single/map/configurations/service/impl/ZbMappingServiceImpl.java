/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.definition.facade.UniversalFieldDefine
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  org.apache.commons.lang3.StringUtils
 */
package nr.single.map.configurations.service.impl;

import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.facade.UniversalFieldDefine;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.ZbMapping;
import nr.single.map.configurations.dao.ConfigDao;
import nr.single.map.configurations.file.ini.BufferIni;
import nr.single.map.configurations.file.ini.Ident;
import nr.single.map.configurations.file.ini.IniZBInfo;
import nr.single.map.configurations.file.ini.MemStream;
import nr.single.map.configurations.file.ini.Section;
import nr.single.map.configurations.file.ini.StreamException;
import nr.single.map.configurations.file.ini.StreamIniBuffer;
import nr.single.map.configurations.internal.bean.IllegalData;
import nr.single.map.configurations.service.ParseMapping;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.internal.SingleFileFieldInfoImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ZbMappingServiceImpl
extends ParseMapping {
    private static final Logger logger = LoggerFactory.getLogger(ZbMappingServiceImpl.class);
    private boolean parsed = false;
    @Autowired
    private IRuntimeFormService formService;
    @Autowired
    private IDataDefinitionRuntimeController IRunTimeCtrl;
    @Autowired
    private IRunTimeViewController runtimeCtrl;
    @Autowired
    private ConfigDao configDao;
    @Autowired
    private IFMDMAttributeService ifmdmAttributeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    private IllegalData errorData;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int parseFileMapping(byte[] files) {
        HashMap<String, Map<String, IniZBInfo>> iniZBManager = new HashMap<String, Map<String, IniZBInfo>>();
        HashMap<String, HashMap<String, List<String>>> netDimIndex = new HashMap<String, HashMap<String, List<String>>>();
        ByteArrayInputStream zbMappingstream = new ByteArrayInputStream(files);
        this.errorData = new IllegalData();
        try {
            MemStream stream = new MemStream();
            stream.copyFrom(zbMappingstream, (long)zbMappingstream.available());
            StreamIniBuffer aBuffer = new StreamIniBuffer(stream);
            BufferIni ini = new BufferIni(aBuffer);
            ini.readInfo();
            int count = ini.count();
            ArrayList<String> secList = new ArrayList<String>();
            List<SingleFileFieldInfo> zbInfos = this.buildTotalZbInfos();
            Map<String, List<SingleFileFieldInfo>> formGroup = zbInfos.stream().filter(e -> e.getFormCode() != null).collect(Collectors.groupingBy(SingleFileFieldInfo::getFormCode));
            Map<String, List<SingleFileFieldInfo>> fieldGroup = zbInfos.stream().filter(e -> e.getFieldCode() != null).collect(Collectors.groupingBy(SingleFileFieldInfo::getFieldCode));
            for (int i = 0; i < count; ++i) {
                Map<String, Ident> identMap;
                Section sec = ini.get(i);
                int secCount = sec.count();
                String sectionName = sec.name();
                if (0 == secCount || "TASK".equalsIgnoreCase(sectionName) || "BBLX".equalsIgnoreCase(sectionName)) continue;
                if (formGroup.get(sectionName) == null) {
                    this.errorData.addErrorSingleForm("\u4e0d\u5b58\u5728\u7684\u5e73\u53f0\u62a5\u8868\uff01", String.valueOf(i), sectionName);
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
                    if (fieldGroup.get(ident.getName()) == null) continue;
                    this.insertZBInfo(ident.getName(), value, iniSingleManager, index);
                }
                iniZBManager.put(sectionName, iniSingleManager);
            }
            this.parsed = true;
            this.setZbInfo(this.getZbMappingByINIFile(iniZBManager));
        }
        catch (IOException e2) {
            logger.error(e2.getMessage(), e2);
        }
        catch (StreamException e3) {
            logger.error(e3.getMessage(), e3);
        }
        finally {
            if (null != zbMappingstream) {
                try {
                    zbMappingstream.close();
                }
                catch (IOException e4) {
                    logger.error(e4.getMessage(), e4);
                }
            }
        }
        return this.getZbInfo().size();
    }

    @Override
    public void convertFromConfig(ISingleMappingConfig config) {
        List<SingleFileFieldInfo> zbFields = config.getZbFields();
        ArrayList<ZbMapping> mappings = new ArrayList<ZbMapping>();
        zbFields.forEach(e -> {
            String formCode = e.getNetFormCode();
            this.setFilterMappingData((List<ZbMapping>)mappings, formCode, (SingleFileFieldInfo)e);
        });
        this.setZbInfo(mappings);
    }

    @Override
    public String buildTextFile() {
        return null;
    }

    @Override
    public IllegalData getErrorData() {
        return this.errorData;
    }

    private List<ZbMapping> getZbMappingByINIFile(Map<String, Map<String, IniZBInfo>> iniZBManager) {
        ArrayList<ZbMapping> zbMappings = new ArrayList<ZbMapping>();
        List formDefines = this.formService.queryFormDefinesByFormScheme(this.getSchemeKey());
        Map<String, FormDefine> formGroup = formDefines.stream().collect(Collectors.toMap(FormDefine::getFormCode, e -> e));
        iniZBManager.forEach((formCode, v) -> {
            List<FieldDefine> allFieldsInForm = new ArrayList();
            ArrayList allLinksInRegion = new ArrayList();
            boolean isFMDM = false;
            FormDefine formDefine = (FormDefine)formGroup.get(formCode);
            if (formDefine != null) {
                isFMDM = FormType.FORM_TYPE_NEWFMDM.equals((Object)formDefine.getFormType());
                try {
                    if (isFMDM) {
                        FormSchemeDefine formSchemeDefine = this.runtimeCtrl.getFormScheme(formDefine.getFormScheme());
                        Object dw = formSchemeDefine.getDw();
                        TaskDefine taskDefine = this.runtimeCtrl.queryTaskDefine(formSchemeDefine.getTaskKey());
                        if (StringUtils.isEmpty((CharSequence)dw)) {
                            dw = taskDefine.getDw();
                        }
                        FMDMAttributeDTO fMDMAttributeDTO = new FMDMAttributeDTO();
                        fMDMAttributeDTO.setEntityId((String)dw);
                        fMDMAttributeDTO.setFormSchemeKey(this.getSchemeKey());
                        List attributes = this.ifmdmAttributeService.list(fMDMAttributeDTO);
                        for (IFMDMAttribute attribute : attributes) {
                            FieldDefine fieldDefine = this.IRunTimeCtrl.queryFieldDefine(attribute.getID());
                            allFieldsInForm.add(fieldDefine);
                        }
                    } else {
                        List keysInForm = this.runtimeCtrl.getFieldKeysInForm(formDefine.getKey());
                        allFieldsInForm = this.IRunTimeCtrl.queryFieldDefinesInRange((Collection)keysInForm);
                    }
                    List allRegionsInForm = this.runtimeCtrl.getAllRegionsInForm(formDefine.getKey());
                    for (DataRegionDefine define : allRegionsInForm) {
                        List linksInRegion = this.runtimeCtrl.getAllLinksInRegion(define.getKey());
                        allLinksInRegion.addAll(linksInRegion);
                    }
                }
                catch (Exception e2) {
                    logger.error(e2.getMessage(), e2);
                }
            }
            Map<Object, Object> codeGroup = new HashMap();
            if (allFieldsInForm != null) {
                codeGroup = allFieldsInForm.stream().collect(Collectors.groupingBy(UniversalFieldDefine::getCode));
            }
            Map<String, DataLinkDefine> linkMap = allLinksInRegion.stream().filter(e -> !StringUtils.isEmpty((CharSequence)e.getLinkExpression())).collect(Collectors.toMap(DataLinkDefine::getLinkExpression, l -> l, (l1, l2) -> l2));
            Map<Object, Object> finalCodeGroup = codeGroup;
            boolean finalIsFMDM = isFMDM;
            v.forEach((code, item) -> {
                DataLinkDefine linkDefines;
                String link;
                FieldType type;
                String fieldCode;
                String fieldKey;
                String tableCode;
                String netFormCode;
                block17: {
                    netFormCode = formCode;
                    tableCode = item.getTableName();
                    fieldKey = null;
                    fieldCode = null;
                    type = null;
                    link = null;
                    List fieldDefines = (List)finalCodeGroup.get(item.getZBName());
                    TableDefine tableDefine = null;
                    try {
                        if (fieldDefines.size() > 0) {
                            tableDefine = this.IRunTimeCtrl.queryTableDefine(((FieldDefine)fieldDefines.get(0)).getOwnerTableKey());
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
                    } else if (tableCode.indexOf("_") != -1) {
                        netFormCode = tableCode.substring(tableCode.indexOf("_") + 1, tableCode.length());
                        try {
                            FieldDefine fieldDefine = null;
                            tableDefine = this.IRunTimeCtrl.queryTableDefineByCode(tableCode);
                            if (tableDefine != null) {
                                fieldDefine = this.IRunTimeCtrl.queryFieldByCodeInTable(item.getZBName(), tableDefine.getKey());
                            } else {
                                this.errorData.addErrorZb("\u4e0d\u5b58\u5728\u7684\u5b58\u50a8\u8868\uff01", (String)formCode, item.getPtCode(), tableCode);
                            }
                            if (fieldDefine != null) {
                                fieldKey = fieldDefine.getKey();
                                fieldCode = fieldDefine.getCode();
                                type = fieldDefine.getType();
                                break block17;
                            }
                            this.errorData.addErrorZb("\u4e0d\u5b58\u5728\u7684\u6307\u6807\uff01", (String)formCode, item.getPtCode(), item.getZBName());
                        }
                        catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    } else {
                        this.errorData.addErrorZb("\u4e0d\u5b58\u5728\u7684\u5b58\u50a8\u8868\uff01", (String)formCode, item.getPtCode(), tableCode);
                    }
                }
                if ((linkDefines = finalIsFMDM ? (DataLinkDefine)linkMap.get(fieldCode) : (DataLinkDefine)linkMap.get(fieldKey)) != null) {
                    link = linkDefines.getKey();
                }
                this.setFilterMappingData(zbMappings, netFormCode, this.buildSingleInfo(netFormCode, (IniZBInfo)item, tableCode, fieldKey, (String)formCode, type, link));
            });
        });
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
            int index = key.lastIndexOf("_");
            key = key.substring(0, index);
            IniZBInfo iniZBInfo = this.getIniZBInfo(key, iniSingleMap);
            iniZBInfo.setPeriodStr(msg);
        } else if (key.startsWith("Dim")) {
            key = key.substring(3);
            IniZBInfo iniZBInfo = this.getIniZBInfo(key, iniSingleMap);
            iniZBInfo.setDim(msg);
        } else {
            IniZBInfo iniZBInfo = this.getIniZBInfo(key, iniSingleMap);
            int stIdx = msg.indexOf("[");
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

    private List<SingleFileFieldInfo> buildTotalZbInfos() {
        List<ISingleMappingConfig> configInfos = this.configDao.getConfigByScheme(this.getSchemeKey());
        ArrayList<SingleFileFieldInfo> zbInfos = new ArrayList<SingleFileFieldInfo>();
        if (configInfos != null && configInfos.size() > 0) {
            for (ISingleMappingConfig configInfo : configInfos) {
                List<SingleFileFieldInfo> zbFields = configInfo.getZbFields();
                if (zbFields == null || zbFields.size() <= 0) continue;
                zbInfos.addAll(zbFields);
            }
        }
        return zbInfos;
    }
}

