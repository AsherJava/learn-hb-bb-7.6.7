/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringTokenEnumeration
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.io.service.impl;

import com.jiuqi.bi.util.StringTokenEnumeration;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.io.dataset.IRegionDataSet;
import com.jiuqi.nr.io.dataset.impl.RegionDataSet;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.params.output.ImportInformations;
import com.jiuqi.nr.io.sb.dataset.impl.SBRegionDataSet;
import com.jiuqi.nr.io.service.FileImportService;
import com.jiuqi.nr.io.tz.dataset.AbstractRegionDataSet;
import com.jiuqi.nr.io.util.ExtUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="TxtFileImportServiceImpl")
public class TxtFileImportServiceImpl
implements FileImportService {
    private static final Logger log = LoggerFactory.getLogger(TxtFileImportServiceImpl.class);
    private static final String MODULTXT = "TXT\u5bfc\u5165";
    @Autowired
    private IRunTimeViewController viewController;
    @Resource
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private IEntityMetaService iEntityMetaService;

    @Override
    public Map<String, Object> dealFileData(File file, TableContext tableContext) throws Exception {
        int regionTop;
        String formCode;
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("TXT\u5bfc\u5165\u670d\u52a1", OperLevel.USER_OPER);
        FormSchemeDefine formScheme = this.viewController.getFormScheme(tableContext.getFormSchemeKey());
        IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(formScheme.getDw());
        LogDimensionCollection logDimension = null;
        try {
            logDimension = new LogDimensionCollection();
            logDimension.setDw(formScheme.getDw(), new String[]{tableContext.getDimensionSet().getValue(queryEntity.getDimensionName()).toString()});
            logDimension.setPeriod(formScheme.getDateTime(), tableContext.getDimensionSet().getValue("DATATIME").toString());
        }
        catch (Exception e1) {
            log.error("\u6784\u5efa\u65e5\u5fd7\u7ef4\u5ea6\u51fa\u9519");
        }
        logHelper.info(formScheme.getTaskKey(), logDimension, "TXT\u5bfc\u5165\u5f00\u59cb", MODULTXT);
        if (tableContext.getTaskKey() == null) {
            tableContext.setTaskKey(formScheme.getTaskKey());
        }
        HashMap<String, Object> results = new HashMap<String, Object>();
        tableContext.setPwd(file.getAbsolutePath().replace(file.getName(), ""));
        String fileName = ExtUtil.trimEnd(file.getName(), ".txt");
        int lastIndexOfConst = fileName.lastIndexOf("_F");
        if (lastIndexOfConst == -1) {
            formCode = fileName;
            regionTop = 1;
        } else {
            FormDefine formDefine = this.getFormDefine(tableContext, fileName);
            if (Objects.nonNull(formDefine)) {
                formCode = fileName;
                regionTop = 1;
            } else {
                formCode = fileName.substring(0, lastIndexOfConst);
                regionTop = Integer.parseInt(fileName.substring(lastIndexOfConst + "_F".length()));
            }
        }
        List<RegionData> formRegions = this.getFormRegions(tableContext, formCode);
        if (null == formRegions) {
            results.put("msg", "error");
            ImportInformations error = new ImportInformations(null, null, null, String.format("\u672a\u627e\u5230\u5bf9\u5e94\u6587\u4ef6\uff1a%s \u7684\u62a5\u8868\u3002", file.getName()), "");
            ArrayList<ImportInformations> errorInfo = new ArrayList<ImportInformations>();
            errorInfo.add(error);
            results.put("errorInfo", errorInfo);
            results.put("msg", "error");
            logHelper.error(formScheme.getTaskKey(), logDimension, "TXT\u5bfc\u5165\u5f02\u5e38", error.getMessage());
            return results;
        }
        RegionData region = null;
        for (RegionData regionData : formRegions) {
            if (regionData.getRegionTop() != regionTop) continue;
            region = regionData;
            break;
        }
        if (null == region) {
            results.put("msg", "error");
            return results;
        }
        ArrayList<ExportFieldDefine> fields = new ArrayList<ExportFieldDefine>();
        Object success = null;
        Object error = null;
        Object importPara = null;
        int lineNum = 0;
        String unitCode = "";
        IRegionDataSet unitCodeUtil = null;
        Map<String, Set<String>> unImport = new HashMap<String, Set<String>>();
        try (FileReader fr = new FileReader(file.getAbsolutePath());){
            BufferedReader bf = new BufferedReader(fr);
            Object object = null;
            try {
                String str = bf.readLine();
                if (str != null) {
                    for (String s : tableContext.getSplitGather()) {
                        str = str.replace(s, tableContext.getSplit());
                    }
                    StringTokenizer tokenF = new StringTokenizer(str, tableContext.getSplit());
                    while (tokenF.hasMoreElements()) {
                        ExportFieldDefine efd = new ExportFieldDefine();
                        efd.setCode(tokenF.nextToken());
                        fields.add(efd);
                    }
                }
                FormDefine formDefine = this.getFormDefine(tableContext, formCode);
                AbstractRegionDataSet regionDataSet = null;
                regionDataSet = null != formDefine && formDefine.getFormType() == FormType.FORM_TYPE_ACCOUNT && region.getRegionTop() > 1 ? new SBRegionDataSet(tableContext, region, fields) : new RegionDataSet(tableContext, region, fields);
                unitCodeUtil = regionDataSet;
                while ((str = bf.readLine()) != null) {
                    for (String s : tableContext.getSplitGather()) {
                        str = str.replace(s, tableContext.getSplit());
                    }
                    StringTokenEnumeration tokenEnm = new StringTokenEnumeration(str, tableContext.getSplit());
                    ArrayList<Object> listData = new ArrayList<Object>();
                    while (tokenEnm.hasMoreElements()) {
                        listData.add(tokenEnm.nextElement());
                    }
                    unitCode = listData.get(0).toString();
                    regionDataSet.importDatas(listData);
                    ++lineNum;
                }
                regionDataSet.commit();
            }
            catch (Throwable str) {
                object = str;
                throw str;
            }
            finally {
                if (bf != null) {
                    if (object != null) {
                        try {
                            bf.close();
                        }
                        catch (Throwable str) {
                            ((Throwable)object).addSuppressed(str);
                        }
                    } else {
                        bf.close();
                    }
                }
            }
        }
        catch (Exception e) {
            log.info("\u8bfbtxt\u6587\u4ef6\u51fa\u9519 {}", (Object)e.getMessage(), (Object)e);
            results.put("lineNum", lineNum);
            if (unitCodeUtil != null) {
                String title = unitCodeUtil.getCodeTitle(unitCode);
                unitCode = title == null ? unitCode : title;
                results.put("unitCode", unitCode);
                unImport = this.getImportInfo(unitCodeUtil, unImport);
                int dbDataCount = unitCodeUtil.getDbDataCount();
                results.put("dbDataCount", dbDataCount);
                this.buildErrInfoNormal(results, region, unImport);
                this.buildErrInfo(results, region, e);
            }
            logHelper.error(formScheme.getTaskKey(), logDimension, "TXT\u5bfc\u5165\u5f02\u5e38", "\u62a5\u8868\uff1a" + formCode + ",\u8bfbtxt\u6587\u4ef6\u51fa\u9519 :" + e.getMessage());
        }
        if (null != unitCodeUtil) {
            unImport = this.getImportInfo(unitCodeUtil, unImport);
        }
        this.buildErrInfoNormal(results, region, unImport);
        Set<String> unitSuccess = unImport.get("unit_success");
        if (unitSuccess != null && !unitSuccess.isEmpty()) {
            Set<String> inexistence = unImport.get("unit_inexistence");
            Set<String> noAccess = unImport.get("unit_noaccess");
            if (inexistence != null && !inexistence.isEmpty()) {
                unitSuccess.removeAll(inexistence);
            }
            if (noAccess != null && !noAccess.isEmpty()) {
                unitSuccess.removeAll(noAccess);
            }
            for (String item : unitSuccess) {
                ImportInformations successs = new ImportInformations(region.getFormKey(), region.getFormCode(), region.getTitle(), "\u5bfc\u5165\u6210\u529f", item);
                List successInfo = null;
                successInfo = results.get("successInfo") == null ? new ArrayList() : (List)results.get("successInfo");
                successInfo.add(successs);
                results.put("successInfo", successInfo);
                results.put("msg", "success");
            }
        }
        try {
            if (results.containsKey("unit_success")) {
                logDimension.setDw(formScheme.getDw(), (String[])((Set)results.get("unit_success")).toArray());
                logHelper.info(formScheme.getTaskKey(), logDimension, "TXT\u5bfc\u5165\u5b8c\u6210", "TXT\u5bfc\u5165\u5b8c\u6210");
            } else {
                logHelper.info(formScheme.getTaskKey(), logDimension, "TXT\u5bfc\u5165\u5b8c\u6210", "TXT\u5bfc\u5165\u5b8c\u6210");
            }
        }
        catch (Exception e) {
            log.error("\u6784\u9020\u4e1a\u52a1\u65e5\u5fd7\u51fa\u9519");
        }
        return results;
    }

    private Map<String, Set<String>> getImportInfo(IRegionDataSet regionDataSet, Map<String, Set<String>> unImport) throws Exception {
        if (unImport != null) {
            Set<String> inexistence = unImport.get("unit_inexistence");
            Set<String> noaccess = unImport.get("unit_noaccess");
            Set<String> success = unImport.get("unit_success");
            Set<String> regionNoAccess = unImport.get("region_nosuccess");
            Map<String, Set<String>> unImport2 = regionDataSet.getUnImport();
            Set<String> inexistence2 = unImport2.get("unit_inexistence");
            Set<String> noaccess2 = unImport2.get("unit_noaccess");
            Set<String> success2 = unImport2.get("unit_success");
            Set<String> regionNoAccess2 = unImport.get("region_nosuccess");
            if (inexistence != null && inexistence2 != null) {
                inexistence2.addAll(inexistence);
            } else if (inexistence2 == null) {
                inexistence2 = inexistence;
            }
            if (noaccess != null && noaccess2 != null) {
                noaccess2.addAll(noaccess);
            } else if (noaccess2 == null) {
                noaccess2 = noaccess;
            }
            if (success2 != null && success != null) {
                success2.addAll(success);
            } else if (success2 == null) {
                success2 = success;
            }
            if (regionNoAccess2 != null && regionNoAccess != null) {
                regionNoAccess2.addAll(regionNoAccess);
            } else if (regionNoAccess2 == null) {
                regionNoAccess2 = regionNoAccess;
            }
            return unImport2;
        }
        return regionDataSet.getUnImport();
    }

    private void buildErrInfoNormal(Map<String, Object> results, RegionData region, Map<String, Set<String>> unImport) {
        Set<String> set;
        Set<String> noAccess;
        Set<String> inexistence = unImport.get("unit_inexistence");
        if (inexistence != null && !inexistence.isEmpty()) {
            for (String string : inexistence) {
                ImportInformations errorss = new ImportInformations(region.getFormKey(), region.getFormCode(), region.getTitle(), "\u6ca1\u6709\u8be5\u5355\u4f4dcode\u5bf9\u5e94\u7684\u4e3b\u4f53\u6570\u636e\uff0c\u4e0d\u6267\u884c\u5bfc\u5165\u64cd\u4f5c!", string);
                List errorInfo = null;
                errorInfo = results.get("errorInfo") == null ? new ArrayList() : (List)results.get("errorInfo");
                errorInfo.add(errorss);
                results.put("errorInfo", errorInfo);
                results.put("msg", "error");
            }
        }
        if ((noAccess = unImport.get("unit_noaccess")) != null && !noAccess.isEmpty()) {
            for (String item : noAccess) {
                ImportInformations errorss = new ImportInformations(region.getFormKey(), region.getFormCode(), region.getTitle(), "\u8be5\u5355\u4f4d\u6ca1\u6709\u5bfc\u5165\u6743\u9650\uff0c\u4e0d\u6267\u884c\u5bfc\u5165\u64cd\u4f5c!", item);
                List errorInfo = null;
                errorInfo = results.get("errorInfo") == null ? new ArrayList() : (List)results.get("errorss");
                errorInfo.add(errorss);
                results.put("errorInfo", errorInfo);
                results.put("msg", "error");
            }
        }
        if ((set = unImport.get("region_nosuccess")) != null && !set.isEmpty()) {
            for (String item : set) {
                ImportInformations errorss = new ImportInformations(region.getFormKey(), region.getFormCode(), region.getTitle(), "\u8be5\u5355\u4f4d\u5b58\u5728\u4e0e\u9ed8\u8ba4\u7ef4\u5ea6\u503c\u4e0d\u5339\u914d\u7684\u6570\u636e\uff0c\u4e0d\u6267\u884c\u5bfc\u5165\u64cd\u4f5c!", item);
                List errorInfo = null;
                errorInfo = results.get("errorInfo") == null ? new ArrayList() : (List)results.get("errorss");
                errorInfo.add(errorss);
                results.put("errorInfo", errorInfo);
                results.put("msg", "error");
            }
        }
    }

    private List<RegionData> getFormRegions(TableContext tableContext, String formCode) {
        FormDefine formDefine;
        block18: {
            formDefine = null;
            String separatorMessage = this.iNvwaSystemOptionService.get("nr-data-entry-export", "SEPARATOR_CODE");
            String separator = " ";
            if (separatorMessage.equals("1")) {
                separator = "_";
            } else if (separatorMessage.equals("2")) {
                separator = "&";
            }
            String[] formCodeArr = null;
            if (formCode.contains(separator)) {
                formCodeArr = formCode.split(separator);
            }
            try {
                if (null == tableContext.getFormSchemeKey() && null != tableContext.getTaskKey()) {
                    List schemeByTask = this.viewController.queryFormSchemeByTask(tableContext.getTaskKey());
                    Iterator iterator = schemeByTask.iterator();
                    while (iterator.hasNext()) {
                        FormSchemeDefine fsd = (FormSchemeDefine)iterator.next();
                        if (formCodeArr != null && formCodeArr.length > 1) {
                            for (String formCodeInfo : formCodeArr) {
                                formDefine = this.viewController.queryFormByCodeInScheme(fsd.getKey(), formCodeInfo);
                                if (formDefine == null) {
                                    continue;
                                }
                                break;
                            }
                        } else {
                            formDefine = this.viewController.queryFormByCodeInScheme(fsd.getKey(), formCode);
                        }
                        if (null == formDefine) continue;
                        tableContext.setFormSchemeKey(fsd.getKey());
                        break block18;
                    }
                    break block18;
                }
                if (formCodeArr != null && formCodeArr.length > 1) {
                    for (String formCodeInfo : formCodeArr) {
                        formDefine = this.viewController.queryFormByCodeInScheme(tableContext.getFormSchemeKey(), formCodeInfo);
                        if (formDefine == null) {
                            continue;
                        }
                        break block18;
                    }
                    break block18;
                }
                formDefine = this.viewController.queryFormByCodeInScheme(tableContext.getFormSchemeKey(), formCode);
            }
            catch (Exception e) {
                log.info("\u901a\u8fc7\u8868\u5355\u4ee3\u7801\u67e5\u627e\u6307\u5b9a\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u8868\u5355\u51fa\u9519 {}", (Object)formCode, (Object)e);
            }
        }
        if (null == formDefine) {
            return null;
        }
        List allRegions = this.viewController.getAllRegionsInForm(formDefine.getKey());
        ArrayList<RegionData> regions = new ArrayList<RegionData>();
        for (DataRegionDefine dataRegionDefine : allRegions) {
            RegionData regionData = new RegionData();
            regionData.initialize(dataRegionDefine);
            regions.add(regionData);
        }
        return regions;
    }

    private FormDefine getFormDefine(TableContext tableContext, String formCode) {
        block4: {
            FormDefine formDefine = null;
            try {
                if (null == tableContext.getFormSchemeKey() && null != tableContext.getTaskKey()) {
                    List schemeByTask = this.viewController.queryFormSchemeByTask(tableContext.getTaskKey());
                    for (FormSchemeDefine fsd : schemeByTask) {
                        formDefine = this.viewController.queryFormByCodeInScheme(fsd.getKey(), formCode);
                        if (null == formDefine) continue;
                        return formDefine;
                    }
                    break block4;
                }
                return this.viewController.queryFormByCodeInScheme(tableContext.getFormSchemeKey(), formCode);
            }
            catch (Exception e) {
                log.info("\u901a\u8fc7\u8868\u5355\u4ee3\u7801\u67e5\u627e\u6307\u5b9a\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u8868\u5355\u51fa\u9519 {}", (Object)formCode, (Object)e);
            }
        }
        return null;
    }

    private void buildErrInfo(Map<String, Object> results, RegionData regionData, Exception e) {
        int dbDataCount = Integer.parseInt(results.get("dbDataCount").toString());
        int lineNum = Integer.parseInt(results.get("lineNum").toString());
        String errMessage = e.getMessage() + (e.getCause() != null ? e.getCause().getMessage() : "");
        ImportInformations error = new ImportInformations(regionData.getFormKey(), regionData.getFormCode(), regionData.getTitle(), "\u7b2c" + (lineNum - 1000 > 0 ? lineNum - 1000 : 0) + "\u5230" + lineNum + "\u884c," + errMessage + "(\u5efa\u8bae\u5b9a\u4f4d\uff1a" + dbDataCount + "\u884c)", (String)results.get("unitCode"));
        List errorInfo = null;
        errorInfo = results.get("errorInfo") == null ? new ArrayList() : (List)results.get("errorInfo");
        errorInfo.add(error);
        results.put("errorInfo", errorInfo);
        results.put("msg", "error");
    }
}

