/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DataLinkColumn
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.jtable.params.input.FormulaCheckDesBatchSaveInfo
 *  com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo
 *  com.jiuqi.nr.jtable.service.IFormulaCheckDesService
 *  javax.annotation.Resource
 *  nr.single.map.configurations.bean.CompleteUser
 *  nr.single.map.data.DataChkInfo
 *  nr.single.map.data.MatchFormula
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.exception.SingleDataException
 *  nr.single.map.data.facade.SingleFileFormulaItem
 *  nr.single.map.data.service.SingleDimissionServcie
 */
package nr.single.client.internal.service.upload;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesBatchSaveInfo;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo;
import com.jiuqi.nr.jtable.service.IFormulaCheckDesService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import nr.single.client.internal.service.upload.UploadJioDataUtil;
import nr.single.client.service.ISingleDataCheckService;
import nr.single.client.service.upload.IUploadDataCheckResult;
import nr.single.map.configurations.bean.CompleteUser;
import nr.single.map.data.DataChkInfo;
import nr.single.map.data.MatchFormula;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.exception.SingleDataException;
import nr.single.map.data.facade.SingleFileFormulaItem;
import nr.single.map.data.service.SingleDimissionServcie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadDataCheckResultImpl
implements IUploadDataCheckResult {
    private static final Logger logger = LoggerFactory.getLogger(UploadDataCheckResultImpl.class);
    @Autowired
    private IFormulaRunTimeController formulaController;
    @Autowired
    private IRunTimeViewController viewController;
    @Resource
    private IFormulaCheckDesService formulaCheckService;
    @Autowired
    private ISingleDataCheckService singleDataCheckService;
    @Autowired
    private SingleDimissionServcie singleDimService;
    private static final String CKD_USERKEY_JIO = "99668134-31a3-4325-8875-7da01f0e8620";
    private static final String CKD_USERTITLE_JIO = "JIO\u5bfc\u5165";

    @Override
    public void ImportCheckDataFromCache(TaskDataContext context, String taskDataPath, Map<String, DimensionValue> dimensionSet, String formKey, Map<String, Map<String, List<Map<String, DimensionValue>>>> zdmFloatCodeDims, AsyncTaskMonitor asyncTaskMonitor) throws SingleDataException {
        Map<String, DimensionValue> dimensionSet3;
        if (context.getCheckInfos() == null || context.getCheckInfos().size() == 0) {
            return;
        }
        FormulaCheckDesBatchSaveInfo formulaCheckDesBatchSaveInfo = new FormulaCheckDesBatchSaveInfo();
        FormulaCheckDesQueryInfo formulaCheckInfo = new FormulaCheckDesQueryInfo();
        formulaCheckInfo.setFormSchemeKey(context.getFormSchemeKey());
        List fmlSchemeDefins = this.formulaController.getAllFormulaSchemeDefinesByFormScheme(context.getFormSchemeKey());
        if (null == fmlSchemeDefins || fmlSchemeDefins.size() <= 0) {
            return;
        }
        HashMap<String, FormulaSchemeDefine> fmlSchemeDic = new HashMap<String, FormulaSchemeDefine>();
        HashMap<String, FormulaSchemeDefine> fmlSchemeKeyDic = new HashMap<String, FormulaSchemeDefine>();
        StringBuilder sp = new StringBuilder();
        FormulaSchemeDefine defFormulaScheme = null;
        for (FormulaSchemeDefine formulaScheme : fmlSchemeDefins) {
            if (FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT != formulaScheme.getFormulaSchemeType()) continue;
            fmlSchemeKeyDic.put(formulaScheme.getKey(), formulaScheme);
            if (formulaScheme.isDefault() && FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT == formulaScheme.getFormulaSchemeType()) {
                defFormulaScheme = formulaScheme;
            }
            if (fmlSchemeDic.containsKey(formulaScheme.getTitle())) {
                logger.info("\u5b58\u5728\u540c\u540d\u516c\u5f0f\u65b9\u6848\uff0c\u8bf7\u68c0\u67e5\uff1a" + formulaScheme.getTitle());
            } else {
                fmlSchemeDic.put(formulaScheme.getTitle(), formulaScheme);
            }
            if (sp.length() > 0) {
                sp.append(";");
            }
            sp.append(formulaScheme.getKey());
        }
        if (defFormulaScheme != null && !fmlSchemeDic.containsKey("\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848")) {
            fmlSchemeDic.put("\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848", defFormulaScheme);
        }
        if (StringUtils.isNotEmpty((String)formKey)) {
            formulaCheckInfo.setFormKey(formKey);
        }
        if ((dimensionSet3 = UploadJioDataUtil.getNewDimensionSet(dimensionSet)).containsKey(context.getEntityCompanyType())) {
            dimensionSet3.get(context.getEntityCompanyType()).setValue("");
        }
        if (context.getDimEntityCache().getEntitySingleDimValues().size() > 0) {
            this.singleDimService.setDimensionByUnitSingleDim(context, dimensionSet3);
        }
        formulaCheckInfo.setDimensionSet(dimensionSet3);
        formulaCheckDesBatchSaveInfo.setQueryInfo(formulaCheckInfo);
        Map singleCheckInfos = context.getCheckInfos();
        double addProgress = 0.0;
        if (singleCheckInfos.size() > 0) {
            addProgress = context.getNextProgressLen() / (double)singleCheckInfos.size();
        }
        Map<String, Map<String, SingleFileFormulaItem>> singleFormulasMap = this.getSingleFormulasMap(context);
        Map<String, Map<String, Map<String, SingleFileFormulaItem>>> singleFormulasSchemeMap = this.getSingleFormulaSchemeMap(context, fmlSchemeKeyDic);
        Map singleImportCheckInfos = context.getCheckInfos();
        CompleteUser completeUser = this.getImportUserType(context);
        HashMap<String, String> formCodeMaps = new HashMap<String, String>();
        HashMap<String, Map<String, IParsedExpression>> allFormulaCache = new HashMap<String, Map<String, IParsedExpression>>();
        for (String fmlScheme : singleImportCheckInfos.keySet()) {
            Map schemeChecks = (Map)singleImportCheckInfos.get(fmlScheme);
            if (null != asyncTaskMonitor && StringUtils.isEmpty((String)formKey) && context.getProgress() + addProgress < 1.0) {
                context.setProgress(context.getProgress() + addProgress);
                asyncTaskMonitor.progressAndMessage(context.getProgress(), "\u51fa\u9519\u8bf4\u660e\u5bfc\u5165");
            }
            FormulaSchemeDefine formulaScheme2 = null;
            formulaScheme2 = StringUtils.isEmpty((String)fmlScheme) ? (FormulaSchemeDefine)fmlSchemeDic.get("\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848") : (FormulaSchemeDefine)fmlSchemeDic.get(fmlScheme);
            if (formulaScheme2 == null) continue;
            this.importSchenmeFormula(context, dimensionSet3, formKey, zdmFloatCodeDims, fmlScheme, formulaScheme2.getKey(), formulaCheckInfo, schemeChecks, singleFormulasMap, singleFormulasSchemeMap, formCodeMaps, fmlSchemeDic, allFormulaCache, formulaCheckDesBatchSaveInfo, completeUser);
        }
    }

    private void importSchenmeFormula(TaskDataContext context, Map<String, DimensionValue> dimensionSet, String formKey, Map<String, Map<String, List<Map<String, DimensionValue>>>> zdmFloatCodeDims, String singleFmlScheme, String formulaSchemeKey, FormulaCheckDesQueryInfo formulaCheckInfo, Map<String, List<DataChkInfo>> schemeChecks, Map<String, Map<String, SingleFileFormulaItem>> singleFormulasMap, Map<String, Map<String, Map<String, SingleFileFormulaItem>>> singleFormulasSchemeMap, Map<String, String> formCodeMaps, Map<String, FormulaSchemeDefine> fmlSchemeDic, Map<String, Map<String, IParsedExpression>> allFormulaCache, FormulaCheckDesBatchSaveInfo formulaCheckDesBatchSaveInfo, CompleteUser completeUser) throws SingleDataException {
        boolean hasOrderField = context.getMapingCache().getHasOrderField();
        List checkFormulas = this.formulaController.getCheckFormulasInScheme(formulaSchemeKey);
        HashMap schemeCheckFormulaDic = new HashMap();
        HashMap<String, FormulaDefine> checkFormulaDic = new HashMap<String, FormulaDefine>();
        for (FormulaDefine fml : checkFormulas) {
            checkFormulaDic.put(fml.getFormKey() + "_" + fml.getCode(), fml);
        }
        schemeCheckFormulaDic.put(formulaSchemeKey, checkFormulaDic);
        formulaCheckInfo.setFormulaSchemeKey(formulaSchemeKey);
        for (String tableCode : schemeChecks.keySet()) {
            List<DataChkInfo> singleCheckInfoList = schemeChecks.get(tableCode);
            String curformKey = null;
            String netFormCode = tableCode;
            ArrayList<FormulaCheckDesInfo> desInfos = new ArrayList<FormulaCheckDesInfo>();
            for (DataChkInfo singlecCheckInfo : singleCheckInfoList) {
                HashMap dimensionSet5;
                Map<String, DimensionValue> dimensionSet4;
                List<Map<String, DimensionValue>> dimensionSetList;
                HashMap dimensionSet52;
                Map dimensionSet42;
                List dimensionSetList2;
                List orderDimensionSetList2;
                List orderDimensionSetList;
                Object dataNode;
                String findKey;
                int idPos;
                int colNum;
                String formulaCode;
                String zdm = singlecCheckInfo.getErrorZDM();
                Map<String, List<Map<String, DimensionValue>>> floatCodeDims = null;
                if (null != zdmFloatCodeDims) {
                    if (!zdmFloatCodeDims.containsKey(zdm)) continue;
                    floatCodeDims = zdmFloatCodeDims.get(zdm);
                }
                netFormCode = singlecCheckInfo.getTableFlag();
                String formCode = singlecCheckInfo.getTableFlag();
                String singleFormulaCode = formulaCode = singlecCheckInfo.getFormulaFlag();
                String formulaExp = null;
                int rowNum = singlecCheckInfo.getRowNum();
                if (rowNum == 0) {
                    rowNum = -1;
                }
                if ((colNum = singlecCheckInfo.getColNum()) == 0) {
                    colNum = -1;
                }
                if (StringUtils.isEmpty((String)formulaCode) && StringUtils.isNotEmpty((String)singlecCheckInfo.getFomulaExp()) && (idPos = (formulaExp = singlecCheckInfo.getFomulaExp()).indexOf("\uff0e")) > 0) {
                    formulaCode = formulaExp.substring(0, idPos);
                    formulaExp = formulaExp.substring(idPos + "\uff0e".length(), formulaExp.length());
                }
                if (StringUtils.isEmpty((String)formCode)) {
                    formCode = "Sys_TableMiddle";
                }
                String netFmlScheme = singleFmlScheme;
                if (StringUtils.isNotEmpty((String)formulaCode)) {
                    SingleFileFormulaItem singleFomula;
                    Map<String, SingleFileFormulaItem> singleFormFormulas;
                    if (singleFormulasSchemeMap.containsKey(singleFmlScheme)) {
                        SingleFileFormulaItem singleFomula2;
                        Map<String, SingleFileFormulaItem> singleFormFormulas2;
                        Map<String, Map<String, SingleFileFormulaItem>> SchemeFmlMap = singleFormulasSchemeMap.get(singleFmlScheme);
                        if (SchemeFmlMap.containsKey(formCode) && (singleFormFormulas2 = SchemeFmlMap.get(formCode)).containsKey(formulaCode) && StringUtils.isNotEmpty((String)(singleFomula2 = singleFormFormulas2.get(formulaCode)).getNetFormulaCode())) {
                            formulaCode = singleFomula2.getNetFormulaCode();
                            netFormCode = singleFomula2.getNetFormCode();
                            netFmlScheme = singleFomula2.getNetSchemeName();
                            if (StringUtils.isEmpty((String)netFmlScheme)) {
                                netFmlScheme = "\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848";
                            }
                        }
                    } else if (singleFormulasMap.containsKey(formCode) && (singleFormFormulas = singleFormulasMap.get(formCode)).containsKey(formulaCode) && StringUtils.isNotEmpty((String)(singleFomula = singleFormFormulas.get(formulaCode)).getNetFormulaCode())) {
                        formulaCode = singleFomula.getNetFormulaCode();
                        netFormCode = singleFomula.getNetFormCode();
                    }
                }
                if (!formCodeMaps.containsKey(netFormCode)) {
                    if ("Sys_TableMiddle".equals(netFormCode)) {
                        curformKey = "00000000-0000-0000-0000-000000000000";
                    } else if (StringUtils.isEmpty((String)netFormCode)) {
                        curformKey = "00000000-0000-0000-0000-000000000000";
                    } else {
                        FormDefine formDefine = null;
                        try {
                            formDefine = this.viewController.queryFormByCodeInScheme(context.getFormSchemeKey(), netFormCode);
                        }
                        catch (Exception e2) {
                            logger.error(e2.getMessage(), e2);
                            throw new SingleDataException(e2.getMessage(), (Throwable)e2);
                        }
                        if (null != formDefine) {
                            curformKey = formDefine.getKey();
                            formCodeMaps.put(netFormCode, formDefine.getKey());
                        }
                    }
                } else {
                    curformKey = formCodeMaps.get(netFormCode);
                }
                if (!"00000000-0000-0000-0000-000000000000".equalsIgnoreCase(curformKey) && StringUtils.isNotEmpty((String)formKey) && StringUtils.isNotEmpty((String)curformKey) && !formKey.equalsIgnoreCase(curformKey)) continue;
                String zdmKey = null;
                if (context.getEntityZdmKeyMap().containsKey(zdm)) {
                    zdmKey = (String)context.getEntityZdmKeyMap().get(zdm);
                }
                if (StringUtils.isEmpty(zdmKey)) continue;
                FormulaSchemeDefine formulaScheme = null;
                formulaScheme = StringUtils.isEmpty((String)singlecCheckInfo.getFmlScheme()) ? fmlSchemeDic.get("\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848") : fmlSchemeDic.get(singlecCheckInfo.getFmlScheme());
                if (StringUtils.isNotEmpty((String)netFmlScheme) && !netFmlScheme.equalsIgnoreCase(singlecCheckInfo.getFmlScheme()) && fmlSchemeDic.containsKey(netFmlScheme)) {
                    formulaScheme = fmlSchemeDic.get(netFmlScheme);
                }
                if (formulaScheme == null) continue;
                if (StringUtils.isNotEmpty((String)formulaCode) && StringUtils.isNotEmpty((String)formulaExp)) {
                    try {
                        FormulaDefine fml = (FormulaDefine)checkFormulaDic.get(curformKey + "_" + formulaCode);
                        if (fml != null) {
                            MatchFormula match = new MatchFormula();
                            List tarList = match.getExps(formulaExp);
                            List list = match.getExps(fml.getExpression());
                            int fmlRow = match.calCulateRowStar(tarList, list);
                            int fmlCol = match.calCulateColStar(tarList, list);
                            if (fmlRow > 0 || fmlCol > 0) {
                                rowNum = fmlRow;
                                colNum = fmlCol;
                            }
                        }
                    }
                    catch (Exception e3) {
                        logger.info("\u4ece\u8868\u8fbe\u5f0f\u91cc\u63d0\u53d6\u884c\u5217\u53f7\u51fa\u9519:" + formulaExp + "," + e3.getMessage());
                    }
                }
                Map<Object, Object> curFormulaCache = null;
                if (allFormulaCache.containsKey(formulaScheme.getKey() + ";" + curformKey)) {
                    curFormulaCache = allFormulaCache.get(formulaScheme.getKey() + ";" + curformKey);
                } else {
                    curFormulaCache = new HashMap();
                    List parsedExpressions = null;
                    parsedExpressions = StringUtils.isNotEmpty((String)curformKey) ? this.formulaController.getParsedExpressionByForm(formulaScheme.getKey(), curformKey, DataEngineConsts.FormulaType.CHECK) : this.formulaController.getParsedExpressionBetweenTable(formulaScheme.getKey(), DataEngineConsts.FormulaType.CHECK);
                    if (parsedExpressions != null) {
                        for (IParsedExpression expression : parsedExpressions) {
                            String code = expression.getSource().getCode();
                            IExpression exp = expression.getRealExpression();
                            if (exp != null && (exp.getWildcardRow() > 0 || exp.getWildcardCol() > 0)) {
                                curFormulaCache.put(code + "_" + exp.getWildcardCol() + "_" + exp.getWildcardRow(), expression);
                                if (curFormulaCache.containsKey(code)) continue;
                                curFormulaCache.put(code, expression);
                                continue;
                            }
                            curFormulaCache.put(code, expression);
                        }
                    }
                    allFormulaCache.put(formulaScheme.getKey() + ";" + curformKey, curFormulaCache);
                }
                if (!curFormulaCache.containsKey(formulaCode) && !curFormulaCache.containsKey(formulaCode + "_" + colNum + "_" + rowNum)) continue;
                String formulaKey = null;
                IParsedExpression expression = null;
                expression = rowNum > 0 || colNum > 0 ? (curFormulaCache.containsKey(findKey = formulaCode + "_" + colNum + "_" + rowNum) ? (IParsedExpression)curFormulaCache.get(findKey) : (IParsedExpression)curFormulaCache.get(formulaCode)) : (IParsedExpression)curFormulaCache.get(formulaCode);
                if (expression != null) {
                    String fmulExp;
                    formulaKey = expression.getKey();
                    if ("00000000-0000-0000-0000-000000000000".equalsIgnoreCase(curformKey) && StringUtils.isNotEmpty((String)formKey)) {
                        HashSet<String> bjFormulaForms = new HashSet<String>();
                        for (IASTNode node : expression.getRealExpression()) {
                            DataLinkColumn column;
                            if (!(node instanceof DynamicDataNode) || (column = (dataNode = (DynamicDataNode)node).getDataLink()) == null || column.getReportInfo() == null) continue;
                            String formulaFormKey = column.getReportInfo().getReportKey();
                            bjFormulaForms.add(formulaFormKey);
                        }
                        if (!bjFormulaForms.contains(formKey)) continue;
                    }
                    if (expression.getSource() != null && StringUtils.isNotEmpty((String)(fmulExp = expression.getSource().getFormula()))) {
                        singlecCheckInfo.setLinkFormula(fmulExp.contains("@"));
                        singlecCheckInfo.setOwnerTables(this.singleDataCheckService.analFormulaExpToTables(fmulExp, netFormCode, curformKey, context.getFormSchemeKey(), true));
                    }
                }
                if (StringUtils.isEmpty(formulaKey)) {
                    logger.info("\u51fa\u932f\u8aac\u660e\u627e\u4e0d\u5230\u516c\u5f0f\uff1a" + formulaCode);
                }
                FormulaCheckDesInfo checkInfo = new FormulaCheckDesInfo();
                Map<String, DimensionValue> dimensionSet2 = UploadJioDataUtil.getNewDimensionSet(dimensionSet);
                if (context.getDimEntityCache().getEntitySingleDimList().size() > 0 && context.getDimEntityCache().getEntitySingleDimList().containsKey(zdmKey)) {
                    Map singleUnitDim = (Map)context.getDimEntityCache().getEntitySingleDimList().get(zdmKey);
                    dataNode = context.getDimEntityCache().getEntitySingleDims().iterator();
                    while (dataNode.hasNext()) {
                        String dimName = (String)dataNode.next();
                        DimensionValue setValue = dimensionSet2.get(dimName);
                        DimensionValue getValue = (DimensionValue)singleUnitDim.get(dimName);
                        if (getValue == null || setValue == null) continue;
                        setValue.setValue(getValue.getValue());
                    }
                }
                checkInfo.setDimensionSet(dimensionSet2);
                ((DimensionValue)checkInfo.getDimensionSet().get(context.getEntityCompanyType())).setValue(context.getEntityMasterCodeBykey(zdmKey));
                if (StringUtils.isNotEmpty((String)curformKey)) {
                    checkInfo.setFormKey(curformKey);
                } else {
                    checkInfo.setFormKey("00000000-0000-0000-0000-000000000000");
                }
                checkInfo.setFormSchemeKey(context.getFormSchemeKey());
                checkInfo.setFormulaSchemeKey(formulaScheme.getKey());
                checkInfo.setFormulaKey(formulaKey);
                checkInfo.getDescriptionInfo().setDescription(singlecCheckInfo.getEorrorHint());
                checkInfo.setFormulaCode(formulaCode);
                checkInfo.setGlobRow(rowNum);
                checkInfo.setGlobCol(colNum);
                if (completeUser == CompleteUser.EMPTY) {
                    checkInfo.getDescriptionInfo().setUserId(CKD_USERKEY_JIO);
                    checkInfo.getDescriptionInfo().setUserTitle(CKD_USERTITLE_JIO);
                }
                boolean relaceRecordKey = false;
                if (singlecCheckInfo.getOwnerTables() != null && singlecCheckInfo.getOwnerTables().size() > 1) {
                    relaceRecordKey = true;
                }
                String floatCode = singlecCheckInfo.getFloatFlag();
                String floatOrder = singlecCheckInfo.getFloatOrder();
                String floatUnitCode = zdm + "_" + floatCode + "_" + floatOrder;
                if (StringUtils.isNotEmpty((String)floatCode)) {
                    if (null != floatCodeDims && floatCodeDims.containsKey(floatUnitCode)) {
                        List<Map<String, DimensionValue>> dimensionSetList22 = floatCodeDims.get(floatUnitCode);
                        if (null == dimensionSetList22 || dimensionSetList22.size() != 1) continue;
                        Map<String, DimensionValue> dimensionSet422 = dimensionSetList22.get(0);
                        HashMap<String, DimensionValue> dimensionSet522 = new HashMap<String, DimensionValue>();
                        for (Map.Entry<String, DimensionValue> entry : dimensionSet422.entrySet()) {
                            if (relaceRecordKey && "RECORDKEY".equalsIgnoreCase(entry.getKey())) continue;
                            dimensionSet522.put(entry.getKey(), entry.getValue());
                        }
                        checkInfo.setDimensionSet(dimensionSet522);
                        desInfos.add(checkInfo);
                        continue;
                    }
                    if (null == floatCodeDims || floatCodeDims.size() <= 0) continue;
                    orderDimensionSetList = floatCodeDims.entrySet().stream().filter(e -> ((String)e.getKey()).startsWith(zdm + "_" + floatCode + "_")).collect(Collectors.toList());
                    if (orderDimensionSetList.isEmpty()) {
                        orderDimensionSetList2 = floatCodeDims.entrySet().stream().filter(e -> ((String)e.getKey()).startsWith((zdm + "_" + floatCode + "_").toLowerCase())).collect(Collectors.toList());
                        orderDimensionSetList = orderDimensionSetList2;
                    }
                    if (orderDimensionSetList.size() != 1 || (dimensionSetList2 = (List)((Map.Entry)orderDimensionSetList.get(0)).getValue()).size() != 1) continue;
                    dimensionSet42 = (Map)dimensionSetList2.get(0);
                    dimensionSet52 = new HashMap();
                    for (Map.Entry entry : dimensionSet42.entrySet()) {
                        if (relaceRecordKey && "RECORDKEY".equalsIgnoreCase((String)entry.getKey())) continue;
                        dimensionSet52.put(entry.getKey(), entry.getValue());
                    }
                    checkInfo.setDimensionSet((Map)dimensionSet52);
                    desInfos.add(checkInfo);
                    continue;
                }
                if (null != floatCodeDims && hasOrderField) {
                    if (!StringUtils.isNotEmpty((String)floatOrder)) continue;
                    floatUnitCode = zdm + "_" + floatOrder;
                    if (floatCodeDims.containsKey(floatUnitCode)) {
                        dimensionSetList = floatCodeDims.get(floatUnitCode);
                        if (null == dimensionSetList || dimensionSetList.size() != 1) continue;
                        dimensionSet4 = dimensionSetList.get(0);
                        dimensionSet5 = new HashMap();
                        for (Map.Entry entry : dimensionSet4.entrySet()) {
                            if (relaceRecordKey && "RECORDKEY".equalsIgnoreCase((String)entry.getKey())) continue;
                            dimensionSet5.put(entry.getKey(), entry.getValue());
                        }
                        checkInfo.setDimensionSet(dimensionSet5);
                        desInfos.add(checkInfo);
                        continue;
                    }
                    if (null == floatCodeDims || floatCodeDims.size() <= 0) continue;
                    orderDimensionSetList = floatCodeDims.entrySet().stream().filter(e -> ((String)e.getKey()).startsWith(zdm + "_")).collect(Collectors.toList());
                    if (orderDimensionSetList.isEmpty()) {
                        orderDimensionSetList2 = floatCodeDims.entrySet().stream().filter(e -> ((String)e.getKey()).startsWith((zdm + "_").toUpperCase())).collect(Collectors.toList());
                        orderDimensionSetList = orderDimensionSetList2;
                    }
                    if (orderDimensionSetList.size() <= 0 || (dimensionSetList2 = (List)((Map.Entry)orderDimensionSetList.get(0)).getValue()).size() != 1) continue;
                    dimensionSet42 = (Map)dimensionSetList2.get(0);
                    dimensionSet52 = new HashMap();
                    for (Map.Entry entry : dimensionSet42.entrySet()) {
                        if (relaceRecordKey && "RECORDKEY".equalsIgnoreCase((String)entry.getKey())) continue;
                        dimensionSet52.put(entry.getKey(), entry.getValue());
                    }
                    checkInfo.setDimensionSet(dimensionSet52);
                    desInfos.add(checkInfo);
                    continue;
                }
                if (null != floatCodeDims) {
                    floatUnitCode = StringUtils.isNotEmpty((String)floatOrder) ? zdm + "_" + floatOrder : zdm + "_0";
                    if (floatCodeDims.containsKey(floatUnitCode)) {
                        dimensionSetList = floatCodeDims.get(floatUnitCode);
                        if (null == dimensionSetList || dimensionSetList.size() != 1) continue;
                        dimensionSet4 = dimensionSetList.get(0);
                        dimensionSet5 = new HashMap();
                        for (Map.Entry<String, DimensionValue> entry : dimensionSet4.entrySet()) {
                            if (relaceRecordKey && "RECORDKEY".equalsIgnoreCase(entry.getKey())) continue;
                            dimensionSet5.put(entry.getKey(), entry.getValue());
                        }
                        checkInfo.setDimensionSet(dimensionSet5);
                        desInfos.add(checkInfo);
                        continue;
                    }
                    desInfos.add(checkInfo);
                    continue;
                }
                desInfos.add(checkInfo);
            }
            Map<String, Map<String, List<FormulaCheckDesInfo>>> schemeDesMaps = this.getNewDesInfoMap(desInfos);
            for (String netFmlSchemeKey : schemeDesMaps.keySet()) {
                formulaCheckInfo.setFormulaSchemeKey(netFmlSchemeKey);
                Map<String, List<FormulaCheckDesInfo>> schemeDesMap = schemeDesMaps.get(netFmlSchemeKey);
                for (String importFormKey : schemeDesMap.keySet()) {
                    List<FormulaCheckDesInfo> formDes = schemeDesMap.get(importFormKey);
                    this.commitData(formulaCheckDesBatchSaveInfo, curformKey, formDes, formulaCheckInfo);
                }
            }
        }
    }

    private Map<String, Map<String, List<FormulaCheckDesInfo>>> getNewDesInfoMap(List<FormulaCheckDesInfo> desInfos) {
        HashMap<String, Map<String, List<FormulaCheckDesInfo>>> schemeDesMaps = new HashMap<String, Map<String, List<FormulaCheckDesInfo>>>();
        for (FormulaCheckDesInfo checkInfo : desInfos) {
            ArrayList<FormulaCheckDesInfo> formDes;
            HashMap<String, ArrayList<FormulaCheckDesInfo>> schemeDesMap = (HashMap<String, ArrayList<FormulaCheckDesInfo>>)schemeDesMaps.get(checkInfo.getFormulaSchemeKey());
            if (schemeDesMap == null) {
                schemeDesMap = new HashMap<String, ArrayList<FormulaCheckDesInfo>>();
                schemeDesMaps.put(checkInfo.getFormulaSchemeKey(), schemeDesMap);
            }
            if ((formDes = (ArrayList<FormulaCheckDesInfo>)schemeDesMap.get(checkInfo.getFormKey())) == null) {
                formDes = new ArrayList<FormulaCheckDesInfo>();
                schemeDesMap.put(checkInfo.getFormKey(), formDes);
            }
            formDes.add(checkInfo);
        }
        return schemeDesMaps;
    }

    private void commitData(FormulaCheckDesBatchSaveInfo formulaCheckDesBatchSaveInfo, String formKey, List<FormulaCheckDesInfo> desInfos, FormulaCheckDesQueryInfo formulaCheckInfo) {
        if (desInfos.size() > 0) {
            formulaCheckDesBatchSaveInfo.setDesInfos(desInfos);
            if (StringUtils.isNotEmpty((String)formKey)) {
                String oldFormKey = formulaCheckInfo.getFormKey();
                ArrayList<FormulaCheckDesInfo> bnDesInfos = new ArrayList<FormulaCheckDesInfo>();
                ArrayList<FormulaCheckDesInfo> bjDesInfos = new ArrayList<FormulaCheckDesInfo>();
                ArrayList<FormulaCheckDesInfo> otherDesInfos = new ArrayList<FormulaCheckDesInfo>();
                this.splictToDatas(desInfos, oldFormKey, bnDesInfos, bjDesInfos, otherDesInfos);
                if (bnDesInfos.size() > 0) {
                    formulaCheckInfo.setFormKey(formKey);
                    formulaCheckDesBatchSaveInfo.setDesInfos(bnDesInfos);
                    this.formulaCheckService.batchSaveFormulaCheckDes(formulaCheckDesBatchSaveInfo);
                    formulaCheckInfo.setFormKey(oldFormKey);
                }
                if (bjDesInfos.size() > 0) {
                    formulaCheckInfo.setFormKey("00000000-0000-0000-0000-000000000000");
                    formulaCheckDesBatchSaveInfo.setDesInfos(bjDesInfos);
                    this.formulaCheckService.batchSaveFormulaCheckDes(formulaCheckDesBatchSaveInfo);
                    formulaCheckInfo.setFormKey(oldFormKey);
                }
                if (otherDesInfos.size() > 0) {
                    formulaCheckInfo.setFormKey(oldFormKey);
                    formulaCheckDesBatchSaveInfo.setDesInfos(otherDesInfos);
                    this.formulaCheckService.batchSaveFormulaCheckDes(formulaCheckDesBatchSaveInfo);
                }
            } else {
                this.formulaCheckService.batchSaveFormulaCheckDes(formulaCheckDesBatchSaveInfo);
            }
        }
    }

    private void splictToDatas(List<FormulaCheckDesInfo> desInfos, String formKey, List<FormulaCheckDesInfo> bnDesInfos, List<FormulaCheckDesInfo> bjDesInfos, List<FormulaCheckDesInfo> otherDesInfos) {
        for (FormulaCheckDesInfo checkInfo : desInfos) {
            if (StringUtils.isNotEmpty((String)formKey) && formKey.equalsIgnoreCase(checkInfo.getFormKey())) {
                bnDesInfos.add(checkInfo);
                continue;
            }
            if ("00000000-0000-0000-0000-000000000000".equalsIgnoreCase(checkInfo.getFormKey())) {
                bjDesInfos.add(checkInfo);
                continue;
            }
            otherDesInfos.add(checkInfo);
        }
    }

    private CompleteUser getImportUserType(TaskDataContext context) {
        CompleteUser completeUser = CompleteUser.IMPORTUSER;
        if (context.getMapingCache().getMapConfig() != null && context.getMapingCache().getMapConfig().getMapping() != null && context.getMapingCache().getMapConfig().getConfig() != null && context.getMapingCache().getMapConfig().getConfig().getCompleteUser() != null) {
            completeUser = context.getMapingCache().getMapConfig().getConfig().getCompleteUser();
        }
        return completeUser;
    }

    private Map<String, Map<String, List<DataChkInfo>>> getSingleImportCheckInfos(TaskDataContext context, Map<String, Map<String, Map<String, SingleFileFormulaItem>>> singleFormulasSchemeMap, Map<String, FormulaSchemeDefine> fmlSchemeKeyDic) {
        Map singleCheckInfos = context.getCheckInfos();
        HashMap<String, Map<String, List<DataChkInfo>>> singleImportCheckInfos = new HashMap<String, Map<String, List<DataChkInfo>>>();
        if (!singleFormulasSchemeMap.isEmpty()) {
            Map schemeChecks;
            ArrayList<DataChkInfo> allCheckInfos = new ArrayList<DataChkInfo>();
            for (String fmlScheme : singleCheckInfos.keySet()) {
                schemeChecks = (Map)singleCheckInfos.get(fmlScheme);
                for (String tableCode : schemeChecks.keySet()) {
                    List singleCheckInfoList = (List)schemeChecks.get(tableCode);
                    Iterator iterator = singleCheckInfoList.iterator();
                    while (iterator.hasNext()) {
                        DataChkInfo checkInfo = (DataChkInfo)iterator.next();
                        allCheckInfos.add(checkInfo);
                    }
                }
            }
            for (String fmlScheme : singleCheckInfos.keySet()) {
                schemeChecks = (Map)singleCheckInfos.get(fmlScheme);
                HashMap<String, List> schemeChecksNew = (HashMap<String, List>)singleImportCheckInfos.get(fmlScheme);
                if (schemeChecksNew == null) {
                    schemeChecksNew = new HashMap<String, List>();
                    singleImportCheckInfos.put(fmlScheme, schemeChecksNew);
                }
                if (singleFormulasSchemeMap.containsKey(fmlScheme)) {
                    Map<String, Map<String, SingleFileFormulaItem>> pappingSchemeFormuls = singleFormulasSchemeMap.get(fmlScheme);
                    for (String tableCode : schemeChecks.keySet()) {
                        List singleCheckInfoList = (List)schemeChecks.get(tableCode);
                        ArrayList<DataChkInfo> singleCheckInfoListNew = (ArrayList<DataChkInfo>)schemeChecksNew.get(tableCode);
                        if (singleCheckInfoListNew == null) {
                            singleCheckInfoListNew = new ArrayList<DataChkInfo>();
                            schemeChecksNew.put(tableCode, singleCheckInfoListNew);
                        }
                        Map<String, SingleFileFormulaItem> mappingTableFormulas = pappingSchemeFormuls.get(tableCode);
                        for (DataChkInfo checkInfo : singleCheckInfoList) {
                            if (mappingTableFormulas != null && mappingTableFormulas.containsKey(checkInfo.getFormulaFlag())) {
                                SingleFileFormulaItem mappingFormula = mappingTableFormulas.get(checkInfo.getFormulaFlag());
                                if (fmlSchemeKeyDic.containsKey(mappingFormula.getNetSchemeKey())) {
                                    ArrayList<DataChkInfo> singleCheckInfoListNew2;
                                    HashMap schemeChecksNew2;
                                    FormulaSchemeDefine netFormulaScheme = fmlSchemeKeyDic.get(mappingFormula.getNetSchemeKey());
                                    String newFmlSchemeName = netFormulaScheme.getTitle();
                                    if (netFormulaScheme.isDefault()) {
                                        newFmlSchemeName = "\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848";
                                    }
                                    if ((schemeChecksNew2 = (HashMap)singleImportCheckInfos.get(newFmlSchemeName)) == null) {
                                        schemeChecksNew2 = new HashMap();
                                        singleImportCheckInfos.put(newFmlSchemeName, schemeChecksNew2);
                                    }
                                    if ((singleCheckInfoListNew2 = (ArrayList<DataChkInfo>)schemeChecksNew2.get(tableCode)) == null) {
                                        singleCheckInfoListNew2 = new ArrayList<DataChkInfo>();
                                        schemeChecksNew2.put(tableCode, singleCheckInfoListNew2);
                                    }
                                    DataChkInfo checkInfoNew = new DataChkInfo();
                                    checkInfoNew.setFmlScheme(newFmlSchemeName);
                                    checkInfoNew.setErrorZDM(checkInfo.getErrorZDM());
                                    checkInfoNew.setTableFlag(checkInfo.getTableFlag());
                                    checkInfoNew.setFomulaExp(checkInfo.getFomulaExp());
                                    checkInfoNew.setEorrorHint(checkInfo.getEorrorHint());
                                    checkInfoNew.setOwnerTables(checkInfo.getOwnerTables());
                                    checkInfoNew.setLinkFormula(checkInfo.isLinkFormula());
                                    checkInfoNew.setFormulaFlag(checkInfo.getFormulaFlag());
                                    checkInfoNew.setRowNum(checkInfo.getRowNum());
                                    checkInfoNew.setColNum(checkInfo.getColNum());
                                    checkInfoNew.setFloatRecKey(checkInfo.getFloatRecKey());
                                    checkInfoNew.setMaped(checkInfo.isMaped());
                                    checkInfoNew.setFloatData(checkInfo.isFloatData());
                                    checkInfoNew.setFloatFlagItems(checkInfo.getFloatFlagItems());
                                    checkInfoNew.setNetFloatOrder(checkInfo.getNetFloatOrder());
                                    checkInfoNew.setFloatFlag(checkInfo.getFloatFlag());
                                    checkInfoNew.setFloatingId(checkInfo.getFloatingId());
                                    checkInfoNew.setFloatOrder(checkInfo.getFloatOrder());
                                    singleCheckInfoListNew2.add(checkInfoNew);
                                    continue;
                                }
                                singleCheckInfoListNew.add(checkInfo);
                                continue;
                            }
                            singleCheckInfoListNew.add(checkInfo);
                        }
                    }
                    continue;
                }
                for (String tableCode : schemeChecks.keySet()) {
                    List singleCheckInfoList = (List)schemeChecks.get(tableCode);
                    schemeChecksNew.put(tableCode, singleCheckInfoList);
                }
            }
        } else if (!singleCheckInfos.isEmpty()) {
            singleImportCheckInfos.putAll(singleCheckInfos);
        }
        return singleImportCheckInfos;
    }

    private Map<String, Map<String, Map<String, SingleFileFormulaItem>>> getSingleFormulaSchemeMap(TaskDataContext context, Map<String, FormulaSchemeDefine> fmlSchemeKeyDic) {
        HashMap<String, Map<String, Map<String, SingleFileFormulaItem>>> singleFormulaSchemeMap = new HashMap<String, Map<String, Map<String, SingleFileFormulaItem>>>();
        if (context.getMapingCache().getMapConfig() != null && context.getMapingCache().getMapConfig().getFormulaInfos() != null) {
            for (SingleFileFormulaItem singleFomula : context.getMapingCache().getMapConfig().getFormulaInfos()) {
                HashMap<String, Map<String, SingleFileFormulaItem>> singleFormulasMap;
                String singleFmlSchme = singleFomula.getSingleSchemeName();
                if (StringUtils.isEmpty((String)singleFmlSchme)) {
                    FormulaSchemeDefine formlaSchme;
                    singleFmlSchme = StringUtils.isNotEmpty((String)singleFomula.getNetSchemeKey()) && fmlSchemeKeyDic.containsKey(singleFomula.getNetSchemeKey()) ? ((formlaSchme = fmlSchemeKeyDic.get(singleFomula.getNetSchemeKey())).isDefault() && FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT == formlaSchme.getFormulaSchemeType() ? "\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848" : formlaSchme.getTitle()) : singleFomula.getNetSchemeName();
                }
                if ((singleFormulasMap = (HashMap<String, Map<String, SingleFileFormulaItem>>)singleFormulaSchemeMap.get(singleFmlSchme)) == null) {
                    singleFormulasMap = new HashMap<String, Map<String, SingleFileFormulaItem>>();
                    singleFormulaSchemeMap.put(singleFmlSchme, singleFormulasMap);
                }
                this.getSingleFormulasMap2(singleFomula, singleFormulasMap);
            }
        }
        return singleFormulaSchemeMap;
    }

    private Map<String, Map<String, SingleFileFormulaItem>> getSingleFormulasMap(TaskDataContext context) {
        HashMap<String, Map<String, SingleFileFormulaItem>> singleFormulasMap = new HashMap<String, Map<String, SingleFileFormulaItem>>();
        if (context.getMapingCache().getMapConfig() != null && context.getMapingCache().getMapConfig().getFormulaInfos() != null) {
            for (SingleFileFormulaItem singleFomula : context.getMapingCache().getMapConfig().getFormulaInfos()) {
                this.getSingleFormulasMap2(singleFomula, singleFormulasMap);
            }
        }
        return singleFormulasMap;
    }

    private void getSingleFormulasMap2(SingleFileFormulaItem singleFomula, Map<String, Map<String, SingleFileFormulaItem>> singleFormulasMap) {
        Map<Object, Object> singleFormFormulas = null;
        String formulaCode = singleFomula.getSingleFormulaCode();
        String mapFormCode = "";
        mapFormCode = StringUtils.isNotEmpty((String)singleFomula.getNetFormCode()) ? singleFomula.getNetFormCode() : "Sys_TableMiddle";
        if (StringUtils.isNotEmpty((String)formulaCode)) {
            int id1 = formulaCode.indexOf("[");
            int id2 = formulaCode.indexOf("]");
            if (id1 > 0 && id2 > 0) {
                String mapTableCode = formulaCode.substring(0, id1);
                formulaCode = formulaCode.substring(id1 + 1, id2);
                if (StringUtils.isNotEmpty((String)mapTableCode)) {
                    mapFormCode = mapTableCode;
                }
                singleFomula.setSingleTableCode(mapFormCode);
            }
            if (singleFormulasMap.containsKey(mapFormCode)) {
                singleFormFormulas = singleFormulasMap.get(mapFormCode);
            } else {
                singleFormFormulas = new HashMap();
                singleFormulasMap.put(mapFormCode, singleFormFormulas);
            }
            if (!singleFormFormulas.containsKey(formulaCode)) {
                singleFormFormulas.put(formulaCode, singleFomula);
            }
        }
    }
}

