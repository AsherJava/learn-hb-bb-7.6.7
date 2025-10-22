/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 */
package nr.single.para.compare.internal.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import nr.single.para.compare.bean.ParaCompareContext;
import nr.single.para.compare.definition.CompareDataFormulaDTO;
import nr.single.para.compare.definition.CompareDataFormulaFormDTO;
import nr.single.para.compare.definition.CompareDataFormulaSchemeDTO;
import nr.single.para.compare.definition.ISingleCompareDataFormulaFormService;
import nr.single.para.compare.definition.ISingleCompareDataFormulaScemeService;
import nr.single.para.compare.definition.ISingleCompareDataFormulaService;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.compare.service.FormulaDefineCompareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormulaDefineCompareServiceImpl
implements FormulaDefineCompareService {
    private static final Logger log = LoggerFactory.getLogger(FormulaDefineCompareServiceImpl.class);
    @Autowired
    private ISingleCompareDataFormulaScemeService FormulaSchemeService;
    @Autowired
    private ISingleCompareDataFormulaFormService formulaFormService;
    @Autowired
    private ISingleCompareDataFormulaService formulaCompareService;
    @Autowired
    private IFormulaDesignTimeController FormulaController;

    @Override
    public boolean compareFormulaDefine(ParaCompareContext compareContext) throws Exception {
        boolean result = true;
        CompareDataFormulaSchemeDTO formulaSchemeQueryParam = new CompareDataFormulaSchemeDTO();
        formulaSchemeQueryParam.setInfoKey(compareContext.getComapreResult().getCompareId());
        formulaSchemeQueryParam.setDataType(CompareDataType.DATA_FORMULA_SCHEME);
        List<CompareDataFormulaSchemeDTO> oldFormulaSchemeList = this.FormulaSchemeService.list(formulaSchemeQueryParam);
        Map<String, CompareDataFormulaSchemeDTO> oldFormulaSchemeDic = oldFormulaSchemeList.stream().collect(Collectors.toMap(e -> e.getSingleTitle(), e -> e));
        List formulaSchemes = this.FormulaController.getAllFormulaSchemeDefinesByFormScheme(compareContext.getFormSchemeKey());
        HashMap<String, DesignFormulaSchemeDefine> formulaSchemeDic = new HashMap<String, DesignFormulaSchemeDefine>();
        for (DesignFormulaSchemeDefine scheme : formulaSchemes) {
            formulaSchemeDic.put(scheme.getTitle(), scheme);
        }
        ArrayList<CompareDataFormulaSchemeDTO> addSchemeItems = new ArrayList<CompareDataFormulaSchemeDTO>();
        ArrayList<CompareDataFormulaSchemeDTO> updateSchemeItems = new ArrayList<CompareDataFormulaSchemeDTO>();
        Map formulaMgr = compareContext.getParaInfo().getFmlMgr();
        double startPos = compareContext.getCurProgress();
        double progreeLen = compareContext.getCurProgressLength() / (double)formulaMgr.size();
        for (Map.Entry entry : formulaMgr.entrySet()) {
            String formulaSchemeName = (String)entry.getKey();
            Map schemeFormMap = (Map)entry.getValue();
            DesignFormulaSchemeDefine netFormulaScheme = null;
            if (formulaSchemeDic.containsKey(formulaSchemeName)) {
                netFormulaScheme = (DesignFormulaSchemeDefine)formulaSchemeDic.get(formulaSchemeName);
            }
            CompareDataFormulaSchemeDTO formulaSchemeCompare = null;
            if (oldFormulaSchemeDic.containsKey(formulaSchemeName)) {
                formulaSchemeCompare = oldFormulaSchemeDic.get(formulaSchemeName);
                updateSchemeItems.add(formulaSchemeCompare);
            } else {
                formulaSchemeCompare = new CompareDataFormulaSchemeDTO();
                formulaSchemeCompare.setKey(UUID.randomUUID().toString());
                formulaSchemeCompare.setInfoKey(compareContext.getComapreResult().getCompareId());
                formulaSchemeCompare.setDataType(CompareDataType.DATA_FORMULA_SCHEME);
                formulaSchemeCompare.setSingleTitle(formulaSchemeName);
                addSchemeItems.add(formulaSchemeCompare);
            }
            formulaSchemeCompare.setOrder(OrderGenerator.newOrder());
            if (netFormulaScheme != null) {
                formulaSchemeCompare.setNetKey(netFormulaScheme.getKey());
                formulaSchemeCompare.setNetTitle(netFormulaScheme.getTitle());
                formulaSchemeCompare.setChangeType(CompareChangeType.CHANGE_FLAGTITLESAME);
                formulaSchemeCompare.setUpdateType(CompareUpdateType.UPDATE_OVER);
                formulaSchemeCompare.setMatchKey(formulaSchemeCompare.getNetKey());
            } else {
                formulaSchemeCompare.setChangeType(CompareChangeType.CHANGE_NOEXIST);
                formulaSchemeCompare.setUpdateType(CompareUpdateType.UPDATE_NEW);
                formulaSchemeCompare.setNetTitle(formulaSchemeName);
            }
            compareContext.onProgress(startPos += progreeLen, "\u6bd4\u8f83\u516c\u5f0f\u65b9\u6848:" + formulaSchemeName + "");
            ArrayList<CompareDataFormulaFormDTO> addFormItems = new ArrayList<CompareDataFormulaFormDTO>();
            ArrayList<CompareDataFormulaFormDTO> updateFormItems = new ArrayList<CompareDataFormulaFormDTO>();
            HashMap<String, CompareDataFormulaFormDTO> oldFormulaFormCompares = new HashMap<String, CompareDataFormulaFormDTO>();
            List<CompareDataFormulaFormDTO> formFormulas = this.formulaFormService.listByScheme(compareContext.getComapreResult().getCompareId(), formulaSchemeCompare.getKey());
            if (formFormulas != null) {
                for (CompareDataFormulaFormDTO compareDataFormulaFormDTO : formFormulas) {
                    oldFormulaFormCompares.put(compareDataFormulaFormDTO.getSingleCode(), compareDataFormulaFormDTO);
                }
            }
            for (Map.Entry entry2 : schemeFormMap.entrySet()) {
                String formCode = (String)entry2.getKey();
                CompareDataFormulaFormDTO formItem = (CompareDataFormulaFormDTO)oldFormulaFormCompares.get(formCode);
                boolean isNewFormulaForm = false;
                if (formItem == null) {
                    formItem = new CompareDataFormulaFormDTO();
                    formItem.setKey(UUID.randomUUID().toString());
                    isNewFormulaForm = true;
                }
                formItem.setSingleCode(formCode);
                formItem.setChangeType(CompareChangeType.CHANGE_FLAGTITLESAME);
                formItem.setUpdateType(CompareUpdateType.UPDATE_OVER);
                formItem.setInfoKey(compareContext.getComapreResult().getCompareId());
                formItem.setFmlSchemeCompareKey(formulaSchemeCompare.getKey());
                if (isNewFormulaForm) {
                    addFormItems.add(formItem);
                    continue;
                }
                updateFormItems.add(formItem);
            }
            if (!addFormItems.isEmpty()) {
                this.formulaFormService.batchAdd(addFormItems);
            }
            if (updateFormItems.isEmpty()) continue;
            this.formulaFormService.batchAdd(updateFormItems);
        }
        if (addSchemeItems.size() > 0) {
            this.FormulaSchemeService.batchAdd(addSchemeItems);
        }
        if (updateSchemeItems.size() > 0) {
            this.FormulaSchemeService.batchUpdate(updateSchemeItems);
        }
        return result;
    }

    @Override
    public void batchDelete(ParaCompareContext compareContext, String compareKey) throws Exception {
        CompareDataFormulaSchemeDTO compareDataDTO = new CompareDataFormulaSchemeDTO();
        compareDataDTO.setInfoKey(compareKey);
        this.FormulaSchemeService.delete(compareDataDTO);
        CompareDataFormulaFormDTO formulaFormDTO = new CompareDataFormulaFormDTO();
        formulaFormDTO.setInfoKey(compareKey);
        this.formulaFormService.delete(formulaFormDTO);
        CompareDataFormulaDTO formulaDTO = new CompareDataFormulaDTO();
        formulaDTO.setInfoKey(compareKey);
        this.formulaCompareService.delete(formulaDTO);
    }
}

