/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.service.IDataEntryParamService
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo
 *  com.jiuqi.nr.jtable.service.IFormulaCheckDesService
 *  com.jiuqi.nr.single.core.dbf.DbfTableUtil
 *  com.jiuqi.nr.single.core.dbf.IDbfTable
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  javax.annotation.Resource
 *  nr.single.map.data.CheckDataUtil
 *  nr.single.map.data.DataChkInfo
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.facade.SingleFileFormulaItem
 *  nr.single.map.data.service.SingleDimissionServcie
 */
package nr.single.client.internal.service.export;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo;
import com.jiuqi.nr.jtable.service.IFormulaCheckDesService;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import nr.single.client.service.ISingleDataCheckService;
import nr.single.client.service.export.IExportDataCheckResult;
import nr.single.map.data.CheckDataUtil;
import nr.single.map.data.DataChkInfo;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.facade.SingleFileFormulaItem;
import nr.single.map.data.service.SingleDimissionServcie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExportDataCheckResultImpl
implements IExportDataCheckResult {
    private static final Logger log = LoggerFactory.getLogger(ExportDataCheckResultImpl.class);
    @Resource
    private IRunTimeViewController runtimeView;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    private IDataEntryParamService dataEntryParamService;
    @Resource
    private IFormulaCheckDesService formulaCheckService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private SingleDimissionServcie singleDimService;
    @Autowired
    private ISingleDataCheckService singleDataCheckService;

    @Override
    public void LoadNetCheckDataToCache(TaskDataContext context, String taskDataPath, Map<String, DimensionValue> dimensionSet, String formKey) throws Exception {
        FormulaCheckDesQueryInfo formulaCheckInfo = new FormulaCheckDesQueryInfo();
        formulaCheckInfo.setFormSchemeKey(context.getFormSchemeKey());
        List formulaSchemeDefines = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(context.getFormSchemeKey());
        StringBuilder sp = new StringBuilder();
        for (FormulaSchemeDefine formulaSc : formulaSchemeDefines) {
            if (FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT != formulaSc.getFormulaSchemeType()) continue;
            if (sp.length() > 0) {
                sp.append(";");
            }
            sp.append(formulaSc.getKey());
        }
        formulaCheckInfo.setFormulaSchemeKey(sp.toString());
        if (null != formKey) {
            formulaCheckInfo.setFormKey(formKey);
        }
        if (context.getDimEntityCache().getEntitySingleDimValues().size() > 0) {
            this.singleDimService.setDimensionByUnitSingleDim(context, dimensionSet);
        }
        formulaCheckInfo.setDimensionSet(dimensionSet);
        List checkInfos = this.formulaCheckService.queryFormulaCheckDes(formulaCheckInfo);
        Map checkInfoCache = context.getCheckInfos();
        List<DataChkInfo> formCheckInfoList = null;
        Map schemeChecks = null;
        if (checkInfos.size() <= 0) {
            return;
        }
        Map<String, Map<String, Map<String, SingleFileFormulaItem>>> singleFormulaSchemeMap = this.getSingleFormulaSchemeMap(context);
        HashMap<String, String> formCodeMaps = new HashMap<String, String>();
        HashMap<String, FormDefine> formDefineMaps = new HashMap<String, FormDefine>();
        context.setHasCheckInfo(checkInfos.size() > 0);
        for (FormulaCheckDesInfo checkInfo : checkInfos) {
            Map<String, SingleFileFormulaItem> singleFormFormulas;
            FormulaSchemeDefine fmlSchemeDefine;
            String fmlSchemeKey;
            String zdm;
            Map<String, Map<String, SingleFileFormulaItem>> singleFormulasMap = singleFormulaSchemeMap.get(checkInfo.getFormulaSchemeKey());
            if (singleFormulasMap == null) {
                singleFormulasMap = new HashMap<String, Map<String, SingleFileFormulaItem>>();
            }
            String zmdkey = ((DimensionValue)checkInfo.getDimensionSet().get(context.getEntityCompanyType())).getValue();
            if (!context.getDownloadEntityKeyZdmMap().containsKey(zmdkey) || StringUtils.isEmpty((String)(zdm = (String)context.getDownloadEntityKeyZdmMap().get(zmdkey)))) continue;
            if (context.getDimEntityCache().getEntitySingleDimValues().size() > 0) {
                Map singleUnitDims = (Map)context.getDimEntityCache().getEntitySingleDimList().get(zmdkey);
                boolean isCanUse = true;
                for (String dimName : checkInfo.getDimensionSet().keySet()) {
                    if (!singleUnitDims.containsKey(dimName)) continue;
                    DimensionValue singleDimValue = (DimensionValue)singleUnitDims.get(dimName);
                    DimensionValue curdimValue = (DimensionValue)checkInfo.getDimensionSet().get(dimName);
                    if (singleDimValue.getValue().equalsIgnoreCase(curdimValue.getValue())) continue;
                    isCanUse = false;
                    break;
                }
                if (!isCanUse) continue;
            }
            String formCode = "";
            String mapFormCode = "Sys_TableMiddle";
            FormDefine formDefine = null;
            if (StringUtils.isNotEmpty((String)checkInfo.getFormKey())) {
                if (!formCodeMaps.containsKey(checkInfo.getFormKey())) {
                    formDefine = this.runtimeView.queryFormById(checkInfo.getFormKey());
                    if (formDefine != null) {
                        formCode = formDefine.getFormCode();
                    }
                    formCodeMaps.put(checkInfo.getFormKey(), formCode);
                    formDefineMaps.put(checkInfo.getFormKey(), formDefine);
                } else {
                    formCode = (String)formCodeMaps.get(checkInfo.getFormKey());
                    formDefine = (FormDefine)formDefineMaps.get(checkInfo.getFormKey());
                }
                if (StringUtils.isNotEmpty((String)formCode)) {
                    mapFormCode = formCode;
                }
            }
            if (StringUtils.isEmpty((String)(fmlSchemeKey = checkInfo.getFormulaSchemeKey())) || (fmlSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(fmlSchemeKey)) == null) continue;
            String fmlScheme = fmlSchemeDefine.getTitle();
            if (fmlSchemeDefine.isDefault()) {
                fmlScheme = "\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848";
            }
            SingleFileFormulaItem singleFomulaMap = null;
            if (StringUtils.isNotEmpty((String)checkInfo.getFormulaCode()) && singleFormulasMap.containsKey(mapFormCode) && (singleFormFormulas = singleFormulasMap.get(mapFormCode)).containsKey(checkInfo.getFormulaCode()) && StringUtils.isNotEmpty((String)(singleFomulaMap = singleFormFormulas.get(checkInfo.getFormulaCode())).getSingleSchemeName())) {
                fmlScheme = singleFomulaMap.getSingleSchemeName();
            }
            if (checkInfoCache.containsKey(fmlScheme)) {
                schemeChecks = (Map)checkInfoCache.get(fmlScheme);
            } else {
                schemeChecks = new HashMap();
                checkInfoCache.put(fmlScheme, schemeChecks);
            }
            if (schemeChecks.containsKey(formCode)) {
                formCheckInfoList = (List)schemeChecks.get(formCode);
            } else {
                formCheckInfoList = new ArrayList();
                schemeChecks.put(formCode, formCheckInfoList);
            }
            DataChkInfo singleCheckInfo = new DataChkInfo();
            singleCheckInfo.setErrorZDM(zdm);
            singleCheckInfo.setTableFlag(formCode);
            singleCheckInfo.setFomulaExp("");
            singleCheckInfo.setEorrorHint(checkInfo.getDescriptionInfo().getDescription());
            String formulaCode = checkInfo.getFormulaCode();
            if (singleFomulaMap != null && StringUtils.isNotEmpty((String)singleFomulaMap.getSingleFormulaCode())) {
                formulaCode = singleFomulaMap.getSingleFormulaCode();
                String mapTableCode = "";
                int id1 = formulaCode.indexOf("[");
                int id2 = formulaCode.indexOf("]");
                if (id1 > 0 || id2 > 0) {
                    mapTableCode = formulaCode.substring(0, id1);
                    formulaCode = formulaCode.substring(id1 + 1, id2);
                    if (StringUtils.isNotEmpty((String)mapTableCode)) {
                        singleCheckInfo.setTableFlag(mapTableCode);
                    }
                }
            }
            if (StringUtils.isNotEmpty((String)checkInfo.getFormulaKey())) {
                FormulaDefine formula = this.formulaRunTimeController.queryFormulaDefine(checkInfo.getFormulaKey());
                if (formula == null) {
                    formula = this.formulaRunTimeController.findFormulaDefine(checkInfo.getFormulaCode(), checkInfo.getFormulaSchemeKey());
                }
                if (formula != null && StringUtils.isNotEmpty((String)formula.getExpression())) {
                    singleCheckInfo.setOwnerTables(this.singleDataCheckService.analFormulaExpToTables(formula.getExpression(), formCode, null, context.getFormSchemeKey(), true));
                    singleCheckInfo.setLinkFormula(formula.getExpression().contains("@"));
                }
            }
            singleCheckInfo.setFormulaFlag(formulaCode);
            singleCheckInfo.setRowNum(checkInfo.getGlobRow() < 0 ? 0 : checkInfo.getGlobRow());
            singleCheckInfo.setColNum(checkInfo.getGlobCol() < 0 ? 0 : checkInfo.getGlobCol());
            if ("\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848".equalsIgnoreCase(fmlScheme)) {
                singleCheckInfo.setFmlScheme("");
            } else {
                singleCheckInfo.setFmlScheme(fmlScheme);
            }
            String floatCode = "";
            if (StringUtils.isNotEmpty((String)checkInfo.getFloatId())) {
                String[] dims;
                for (String dim : dims = checkInfo.getFloatId().split(";")) {
                    String[] dimValues = dim.split(":");
                    if (dimValues.length != 2) continue;
                    if ("ID".equalsIgnoreCase(dimValues[0])) {
                        if ("null".equalsIgnoreCase(dimValues[1])) continue;
                        singleCheckInfo.setFloatRecKey(dimValues[1]);
                        singleCheckInfo.setMaped(false);
                        singleCheckInfo.setFloatData(true);
                        singleCheckInfo.setNetFloatOrder(dimValues[1]);
                        continue;
                    }
                    if ("VERSIONID".equalsIgnoreCase(dimValues[0]) || "CKR_BATCH_ID".equalsIgnoreCase(dimValues[0]) || "null".equalsIgnoreCase(dimValues[1])) continue;
                    if (StringUtils.isEmpty((String)singleCheckInfo.getFloatFlag())) {
                        singleCheckInfo.setFloatFlag(dimValues[1]);
                    } else {
                        singleCheckInfo.setFloatFlag(singleCheckInfo.getFloatFlag() + dimValues[1]);
                    }
                    singleCheckInfo.getFloatFlagItems().put(dimValues[0], dimValues[1]);
                    singleCheckInfo.setMaped(true);
                    singleCheckInfo.setFloatData(true);
                }
            } else {
                for (String dimName : checkInfo.getDimensionSet().keySet()) {
                    DimensionValue dimValue = (DimensionValue)checkInfo.getDimensionSet().get(dimName);
                    if (dimName.equalsIgnoreCase(context.getEntityCompanyType()) || dimName.equalsIgnoreCase(context.getEntityDateType()) || dimName.equalsIgnoreCase("RECORDKEY") || dimName.equalsIgnoreCase("VERSIONID")) continue;
                    floatCode = floatCode + dimValue.getValue();
                }
            }
            formCheckInfoList.add(singleCheckInfo);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void exportCheckDataFromCache(TaskDataContext context, String taskDataPath, String formKey) throws Exception {
        Map checkInfos = context.getCheckInfos();
        if (checkInfos.size() <= 0) {
            return;
        }
        String netFormCode = "";
        if (StringUtils.isNotEmpty((String)formKey)) {
            FormDefine formDefine = this.runtimeView.queryFormById(formKey);
            if (!"00000000-0000-0000-0000-000000000000".equalsIgnoreCase(formKey)) {
                if (formDefine != null) {
                    netFormCode = formDefine.getFormCode();
                } else {
                    return;
                }
            }
        }
        boolean hasOrderField = context.getMapingCache().getHasOrderField();
        if (!CheckDataUtil.getCheckDBFFileExist((String)taskDataPath)) {
            CheckDataUtil.CreateCheckDBF((TaskDataContext)context, (String)taskDataPath, (int)context.getMapingCache().getZdmLength(), (boolean)hasOrderField);
        }
        IDbfTable checkDbf = DbfTableUtil.getDbfTable((String)CheckDataUtil.getCheckDBFFileName((String)taskDataPath));
        try {
            context.setHasCheckInfo(true);
            for (String aFmlScheme : checkInfos.keySet()) {
                Map fmlSchemeInfos = (Map)checkInfos.get(aFmlScheme);
                for (String aformCode : fmlSchemeInfos.keySet()) {
                    if (StringUtils.isEmpty((String)aformCode) && StringUtils.isNotEmpty((String)netFormCode) || StringUtils.isNotEmpty((String)aformCode) && !aformCode.equalsIgnoreCase(netFormCode)) continue;
                    List formCheckInfoList = (List)fmlSchemeInfos.get(aformCode);
                    ArrayList<DataChkInfo> formCheckInfoList2 = new ArrayList<DataChkInfo>();
                    if (hasOrderField) {
                        formCheckInfoList2.addAll(formCheckInfoList);
                    } else {
                        HashMap<String, DataChkInfo> checkInfoMap = new HashMap<String, DataChkInfo>();
                        for (DataChkInfo checkInfo : formCheckInfoList) {
                            if (!checkInfo.isFloatData()) {
                                formCheckInfoList2.add(checkInfo);
                                continue;
                            }
                            String aKey = checkInfo.getKeyCode();
                            if (checkInfoMap.containsKey(aKey)) {
                                DataChkInfo newCheckInfo = (DataChkInfo)checkInfoMap.get(aKey);
                                if (!StringUtils.isNotEmpty((String)checkInfo.getEorrorHint())) continue;
                                if (StringUtils.isEmpty((String)newCheckInfo.getEorrorHint())) {
                                    newCheckInfo.setEorrorHint(checkInfo.getEorrorHint());
                                    continue;
                                }
                                if (checkInfo.getEorrorHint().contains(checkInfo.getEorrorHint())) {
                                    newCheckInfo.setEorrorHint(checkInfo.getEorrorHint());
                                    continue;
                                }
                                if (newCheckInfo.getEorrorHint().contains(checkInfo.getEorrorHint())) continue;
                                newCheckInfo.setEorrorHint(newCheckInfo.getEorrorHint() + ";" + checkInfo.getEorrorHint());
                                continue;
                            }
                            checkInfoMap.put(aKey, checkInfo);
                            formCheckInfoList2.add(checkInfo);
                        }
                    }
                    for (DataChkInfo checkInfo : formCheckInfoList2) {
                        String zdm = checkInfo.getErrorZDM();
                        String formCode = checkInfo.getTableFlag();
                        if ("Sys_TableMiddle".equalsIgnoreCase(formCode)) {
                            formCode = "";
                        }
                        if (StringUtils.isEmpty((String)zdm)) continue;
                        DataRow dbfRow = checkDbf.getTable().newRow();
                        dbfRow.setValue("ErrorZDM".toUpperCase(), (Object)zdm);
                        dbfRow.setValue("TableFlag".toUpperCase(), (Object)formCode);
                        dbfRow.setValue("FormulaExp".toUpperCase(), (Object)"");
                        dbfRow.setValue("ErrorHint".toUpperCase(), (Object)checkInfo.getEorrorHint());
                        dbfRow.setValue("FomulaFlag".toUpperCase(), (Object)checkInfo.getFormulaFlag());
                        dbfRow.setValue("RowNum".toUpperCase(), (Object)(checkInfo.getRowNum() < 0 ? 0 : checkInfo.getRowNum()));
                        dbfRow.setValue("ColNum".toUpperCase(), (Object)(checkInfo.getColNum() < 0 ? 0 : checkInfo.getColNum()));
                        String floatCode = checkInfo.getFloatFlag();
                        dbfRow.setValue("FloatFlag".toUpperCase(), (Object)floatCode);
                        if (hasOrderField) {
                            String floatOrder = checkInfo.getFloatOrder();
                            dbfRow.setValue("FloatOrder".toUpperCase(), (Object)floatOrder);
                        }
                        dbfRow.setValue("FloatingId".toUpperCase(), (Object)(checkInfo.getFloatingId() < 0 ? 0 : checkInfo.getFloatingId()));
                        dbfRow.setValue("FmlScheme".toUpperCase(), (Object)checkInfo.getFmlScheme());
                        checkDbf.getTable().getRows().add((Object)dbfRow);
                    }
                }
            }
            checkDbf.saveData();
        }
        finally {
            checkDbf.close();
            if (!context.isHasCheckInfo()) {
                // empty if block
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void exportCheckData(TaskDataContext context, String taskDataPath, Map<String, DimensionValue> dimensionSet, String formKey) throws Exception {
        FormulaCheckDesQueryInfo formulaCheckInfo = new FormulaCheckDesQueryInfo();
        formulaCheckInfo.setFormSchemeKey(context.getFormSchemeKey());
        FormulaSchemeDefine formulaScheme = this.formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(context.getFormSchemeKey());
        formulaCheckInfo.setFormulaSchemeKey(formulaScheme.getKey());
        if (null != formKey) {
            formulaCheckInfo.setFormKey(formKey.toString());
        }
        formulaCheckInfo.setDimensionSet(dimensionSet);
        List checkInfos = this.formulaCheckService.queryFormulaCheckDes(formulaCheckInfo);
        if (checkInfos.size() <= 0) {
            return;
        }
        if (!CheckDataUtil.getCheckDBFFileExist((String)taskDataPath)) {
            boolean hasOrder = context.getMapingCache().getMapConfig() != null && context.getMapingCache().getMapConfig().getTaskInfo() != null && StringUtils.isNotEmpty((String)context.getMapingCache().getMapConfig().getTaskInfo().getSingleFloatOrderFiled());
            CheckDataUtil.CreateCheckDBF((TaskDataContext)context, (String)taskDataPath, (int)context.getMapingCache().getZdmLength(), (boolean)hasOrder);
        }
        IDbfTable checkDbf = DbfTableUtil.getDbfTable((String)CheckDataUtil.getCheckDBFFileName((String)taskDataPath));
        try {
            HashMap<String, String> formCodeMaps = new HashMap<String, String>();
            context.setHasCheckInfo(checkInfos.size() > 0);
            for (FormulaCheckDesInfo checkInfo : checkInfos) {
                String zmdkey = ((DimensionValue)checkInfo.getDimensionSet().get(context.getEntityCompanyType())).getValue();
                String zdm = (String)context.getEntityKeyZdmMap().get(zmdkey);
                String formCode = "";
                if (StringUtils.isNotEmpty((String)checkInfo.getFormKey())) {
                    if (!formCodeMaps.containsKey(checkInfo.getFormKey())) {
                        FormDefine formDefine = this.runtimeView.queryFormById(checkInfo.getFormKey());
                        if (formDefine != null) {
                            formCode = formDefine.getFormCode();
                        }
                        formCodeMaps.put(checkInfo.getFormKey(), formCode);
                    } else {
                        formCode = (String)formCodeMaps.get(checkInfo.getFormKey());
                    }
                }
                if (StringUtils.isEmpty((String)zdm)) continue;
                DataRow dbfRow = checkDbf.getTable().newRow();
                dbfRow.setValue("ErrorZDM".toUpperCase(), (Object)zdm);
                dbfRow.setValue("TableFlag".toUpperCase(), (Object)formCode);
                dbfRow.setValue("FormulaExp".toUpperCase(), (Object)"");
                dbfRow.setValue("ErrorHint".toUpperCase(), (Object)checkInfo.getDescriptionInfo().getDescription());
                dbfRow.setValue("FomulaFlag".toUpperCase(), (Object)checkInfo.getFormulaCode());
                dbfRow.setValue("RowNum".toUpperCase(), (Object)(checkInfo.getGlobRow() < 0 ? 0 : checkInfo.getGlobRow()));
                dbfRow.setValue("ColNum".toUpperCase(), (Object)(checkInfo.getGlobCol() < 0 ? 0 : checkInfo.getGlobCol()));
                String floatCode = "";
                for (String dimName : checkInfo.getDimensionSet().keySet()) {
                    DimensionValue dimValue = (DimensionValue)checkInfo.getDimensionSet().get(dimName);
                    if (dimName.equalsIgnoreCase(context.getEntityCompanyType()) || dimName.equalsIgnoreCase(context.getEntityDateType()) || dimName.equalsIgnoreCase("RECORDKEY") || dimName.equalsIgnoreCase("VERSIONID")) continue;
                    floatCode = floatCode + dimValue.getValue();
                }
                dbfRow.setValue("FloatingId".toUpperCase(), (Object)checkInfo.getFloatId());
                dbfRow.setValue("FmlScheme".toUpperCase(), (Object)checkInfo.getFormSchemeKey());
                checkDbf.getTable().getRows().add((Object)dbfRow);
            }
            checkDbf.saveData();
        }
        finally {
            checkDbf.close();
            if (!context.isHasCheckInfo()) {
                // empty if block
            }
        }
    }

    private Map<String, Map<String, Map<String, SingleFileFormulaItem>>> getSingleFormulaSchemeMap(TaskDataContext context) {
        HashMap<String, Map<String, Map<String, SingleFileFormulaItem>>> singleFormulaSchemeMap = new HashMap<String, Map<String, Map<String, SingleFileFormulaItem>>>();
        if (context.getMapingCache().getMapConfig() != null && context.getMapingCache().getMapConfig().getFormulaInfos() != null) {
            for (SingleFileFormulaItem singleFomula : context.getMapingCache().getMapConfig().getFormulaInfos()) {
                HashMap singleFormulasMap;
                Map<String, SingleFileFormulaItem> singleFormFormulas = null;
                String aNetFormCode = "";
                if (StringUtils.isNotEmpty((String)singleFomula.getNetFormCode())) {
                    aNetFormCode = singleFomula.getNetFormCode();
                }
                if ((singleFormulasMap = (HashMap)singleFormulaSchemeMap.get(singleFomula.getNetSchemeKey())) == null) {
                    singleFormulasMap = new HashMap();
                    singleFormulaSchemeMap.put(singleFomula.getNetSchemeKey(), singleFormulasMap);
                }
                if (singleFormulasMap.containsKey(aNetFormCode)) {
                    singleFormFormulas = (Map)singleFormulasMap.get(aNetFormCode);
                } else {
                    singleFormFormulas = new HashMap();
                    singleFormulasMap.put(aNetFormCode, singleFormFormulas);
                }
                singleFormFormulas.put(singleFomula.getNetFormulaCode(), singleFomula);
            }
        }
        return singleFormulaSchemeMap;
    }
}

