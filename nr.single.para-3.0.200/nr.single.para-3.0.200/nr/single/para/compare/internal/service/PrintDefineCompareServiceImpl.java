/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine
 *  com.jiuqi.nr.definition.facade.print.WordLabelDefine
 *  com.jiuqi.nr.single.core.para.parser.print.GridPrintMan
 *  com.jiuqi.nr.single.core.para.parser.print.GridPrintTextData
 *  com.jiuqi.nr.single.core.para.parser.table.RepInfo
 */
package nr.single.para.compare.internal.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.single.core.para.parser.print.GridPrintMan;
import com.jiuqi.nr.single.core.para.parser.print.GridPrintTextData;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import nr.single.para.compare.bean.ParaCompareContext;
import nr.single.para.compare.definition.CompareDataPrintItemDTO;
import nr.single.para.compare.definition.CompareDataPrintSchemeDTO;
import nr.single.para.compare.definition.ISingleCompareDataPrintItemService;
import nr.single.para.compare.definition.ISingleCompareDataPrintScemeService;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import nr.single.para.compare.internal.util.ComparePrintUtil;
import nr.single.para.compare.internal.util.CompareTypeMan;
import nr.single.para.compare.service.PrintDefineCompareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintDefineCompareServiceImpl
implements PrintDefineCompareService {
    private static final Logger log = LoggerFactory.getLogger(PrintDefineCompareServiceImpl.class);
    @Autowired
    private ISingleCompareDataPrintItemService printDataService;
    @Autowired
    private ISingleCompareDataPrintScemeService printSchemeService;
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private IPrintDesignTimeController printController;

    @Override
    public boolean comparePrintDefine(ParaCompareContext compareContext) throws Exception {
        boolean result = true;
        CompareDataPrintSchemeDTO printSchemeQueryParam = new CompareDataPrintSchemeDTO();
        printSchemeQueryParam.setInfoKey(compareContext.getComapreResult().getCompareId());
        printSchemeQueryParam.setDataType(CompareDataType.DATA_PRINTTSCHEME);
        List<CompareDataPrintSchemeDTO> oldPrintSchemeList = this.printSchemeService.list(printSchemeQueryParam);
        Map<String, CompareDataPrintSchemeDTO> oldPrintSchemeDic = oldPrintSchemeList.stream().collect(Collectors.toMap(e -> e.getSingleTitle(), e -> e));
        CompareDataPrintItemDTO printQueryParam = new CompareDataPrintItemDTO();
        printQueryParam.setInfoKey(compareContext.getComapreResult().getCompareId());
        printQueryParam.setDataType(CompareDataType.DATA_PRINTTITEM);
        List<CompareDataPrintItemDTO> oldPrintItemList = this.printDataService.list(printQueryParam);
        Map<String, CompareDataPrintItemDTO> oldPrintItemDic = oldPrintItemList.stream().collect(Collectors.toMap(e -> e.getSingleCode(), e -> e));
        List printSchemes = this.printController.getAllPrintSchemeByFormScheme(compareContext.getFormSchemeKey());
        HashMap<String, DesignPrintTemplateSchemeDefine> printSchemeDic = new HashMap<String, DesignPrintTemplateSchemeDefine>();
        for (DesignPrintTemplateSchemeDefine scheme : printSchemes) {
            printSchemeDic.put(scheme.getTitle(), scheme);
        }
        List netList = this.viewController.queryAllSoftFormDefinesByFormScheme(compareContext.getFormSchemeKey());
        CompareTypeMan compareFormMan = new CompareTypeMan();
        if (netList != null) {
            for (DesignFormDefine data : netList) {
                CompareDataDO cData = compareFormMan.addNetItem(data.getFormCode(), data.getTitle(), data.getKey());
                if (data.getBinaryData() != null) {
                    cData.setNetData(new String(data.getBinaryData(), StandardCharsets.UTF_8));
                }
                cData.setObjectValue("formDefine", data);
            }
        }
        ArrayList<CompareDataPrintItemDTO> addItems = new ArrayList<CompareDataPrintItemDTO>();
        ArrayList<CompareDataPrintItemDTO> updateItems = new ArrayList<CompareDataPrintItemDTO>();
        ArrayList<CompareDataPrintSchemeDTO> addSchemeItems = new ArrayList<CompareDataPrintSchemeDTO>();
        ArrayList<CompareDataPrintSchemeDTO> updateSchemeItems = new ArrayList<CompareDataPrintSchemeDTO>();
        Map printMgr = compareContext.getParaInfo().getPrtMgr();
        double startPos = compareContext.getCurProgress();
        double progreeLen = compareContext.getCurProgressLength() / (double)printMgr.size();
        for (Map.Entry entry : printMgr.entrySet()) {
            String printSchemeName = (String)entry.getKey();
            Map printFormMap = (Map)entry.getValue();
            DesignPrintTemplateSchemeDefine netPrintScheme = null;
            if (printSchemeDic.containsKey(printSchemeName)) {
                netPrintScheme = (DesignPrintTemplateSchemeDefine)printSchemeDic.get(printSchemeName);
            }
            CompareDataPrintSchemeDTO printSchemeCompare = null;
            if (oldPrintSchemeDic.containsKey(printSchemeName)) {
                printSchemeCompare = oldPrintSchemeDic.get(printSchemeName);
                updateSchemeItems.add(printSchemeCompare);
            } else {
                printSchemeCompare = new CompareDataPrintSchemeDTO();
                printSchemeCompare.setKey(UUID.randomUUID().toString());
                printSchemeCompare.setInfoKey(compareContext.getComapreResult().getCompareId());
                printSchemeCompare.setDataType(CompareDataType.DATA_PRINTTSCHEME);
                printSchemeCompare.setSingleTitle(printSchemeName);
                addSchemeItems.add(printSchemeCompare);
            }
            printSchemeCompare.setOrder(OrderGenerator.newOrder());
            if (netPrintScheme != null) {
                printSchemeCompare.setNetKey(netPrintScheme.getKey());
                printSchemeCompare.setNetTitle(netPrintScheme.getTitle());
                printSchemeCompare.setChangeType(CompareChangeType.CHANGE_FLAGTITLESAME);
                printSchemeCompare.setUpdateType(CompareUpdateType.UPDATE_OVER);
                printSchemeCompare.setMatchKey(printSchemeCompare.getNetKey());
            } else {
                printSchemeCompare.setChangeType(CompareChangeType.CHANGE_NOEXIST);
                printSchemeCompare.setUpdateType(CompareUpdateType.UPDATE_NEW);
                printSchemeCompare.setNetTitle(printSchemeName);
            }
            for (RepInfo rep : compareContext.getParaInfo().getRepInfos()) {
                String singleFormCode = rep.getCode();
                if (!printFormMap.containsKey(singleFormCode)) continue;
                compareContext.onProgress(startPos += progreeLen / (double)printFormMap.size(), "\u6bd4\u8f03\u8868\u5355" + singleFormCode + "\u7684\u6253\u5370\u6a21\u677f");
                if ("SYS_HZFMDY".equalsIgnoreCase(singleFormCode)) continue;
                GridPrintMan singlePrintMan = (GridPrintMan)printFormMap.get(singleFormCode);
                this.setPrintItem(compareContext, singleFormCode, singlePrintMan, printSchemeName, netPrintScheme, compareFormMan, oldPrintItemDic, printSchemeCompare, addItems, updateItems);
            }
        }
        if (addSchemeItems.size() > 0) {
            this.printSchemeService.batchAdd(addSchemeItems);
        }
        if (updateSchemeItems.size() > 0) {
            this.printSchemeService.batchUpdate(updateSchemeItems);
        }
        if (addItems.size() > 0) {
            this.printDataService.batchAdd(addItems);
        }
        if (updateItems.size() > 0) {
            this.printDataService.batchUpdate(updateItems);
        }
        return result;
    }

    private void setPrintItem(ParaCompareContext compareContext, String singleFormCode, GridPrintMan singlePrintMan, String printSchemeName, DesignPrintTemplateSchemeDefine netPrintScheme, CompareTypeMan compareFormMan, Map<String, CompareDataPrintItemDTO> oldPrintItemDic, CompareDataPrintSchemeDTO printSchemeCompare, List<CompareDataPrintItemDTO> addItems, List<CompareDataPrintItemDTO> updateItems) throws Exception {
        RepInfo singleRep = (RepInfo)compareContext.getParaInfo().getRepInfoCodeList().get(singleFormCode);
        DesignFormDefine netForm = null;
        DesignPrintTemplateDefine netPrintTemplate = null;
        if (compareFormMan.getNetCodeItems().containsKey(singleFormCode)) {
            netForm = (DesignFormDefine)compareFormMan.getNetCodeItems().get(singleFormCode).getObjectValue("formDefine");
            if (netPrintScheme != null) {
                netPrintTemplate = this.printController.queryPrintTemplateDefineBySchemeAndForm(netPrintScheme.getKey(), netForm.getKey());
            }
        }
        boolean isNetPrintItemNew = null == netPrintTemplate;
        CompareDataPrintItemDTO printItem = null;
        String printCode = printSchemeName + "_" + singleFormCode;
        if (oldPrintItemDic.containsKey(printCode)) {
            printItem = oldPrintItemDic.get(printCode);
            updateItems.add(printItem);
        } else {
            printItem = new CompareDataPrintItemDTO();
            printItem.setKey(UUID.randomUUID().toString());
            printItem.setInfoKey(compareContext.getComapreResult().getCompareId());
            printItem.setDataType(CompareDataType.DATA_PRINTTITEM);
            printItem.setSingleCode(printCode);
            if (singleRep != null) {
                printItem.setSingleTitle(singleRep.getTitle() + "\u6253\u5370\u6a21\u677f");
                printItem.setSingleFormTitile(singleRep.getTitle());
            }
            printItem.setSinglePrintScheme(printSchemeName);
            printItem.setSingleFormCode(singleFormCode);
            addItems.add(printItem);
        }
        printItem.setOrder(OrderGenerator.newOrder());
        printItem.setNetCode(printCode);
        if (netForm != null) {
            printItem.setNetTitle(netForm.getTitle() + "\u6253\u5370\u6a21\u677f");
            printItem.setNetFormKey(netForm.getKey());
            printItem.setNetFormCode(singleFormCode);
            printItem.setNetFormTitle(netForm.getTitle());
        } else if (singleRep != null) {
            printItem.setNetTitle(singleRep.getTitle() + "\u6253\u5370\u6a21\u677f");
            printItem.setNetFormCode(singleFormCode);
            printItem.setNetFormTitle(singleRep.getTitle());
        }
        if (netPrintScheme != null) {
            printItem.setNetPrintSchemeKey(netPrintScheme.getKey());
        }
        printItem.setNetPrintScheme(printSchemeName);
        printItem.setSchemeCompareKey(printSchemeCompare.getKey());
        if (isNetPrintItemNew) {
            printItem.setChangeType(CompareChangeType.CHANGE_NOEXIST);
            printItem.setUpdateType(CompareUpdateType.UPDATE_NEW);
        } else {
            printItem.setNetKey(netPrintTemplate.getKey());
            printItem.setMatchKey(printItem.getNetKey());
            this.comparePrintItem(compareContext, singlePrintMan, netPrintTemplate, printItem);
        }
    }

    private boolean comparePrintItem(ParaCompareContext compareContext, GridPrintMan singlePrintMan, DesignPrintTemplateDefine netPrintTemplate, CompareDataPrintItemDTO printItem) throws Exception {
        PrintTemplateAttributeDefine netAttribute = this.printController.getPrintTemplateAttribute(netPrintTemplate);
        HashMap<String, WordLabelDefine> netLableDic = new HashMap<String, WordLabelDefine>();
        for (WordLabelDefine netLable : netAttribute.getWordLabels()) {
            netLableDic.put(netLable.getText(), netLable);
        }
        int count = singlePrintMan.getTextsDef().getCount();
        for (int i = 0; i < count; ++i) {
            GridPrintTextData textData = singlePrintMan.getTextsDef().getTextData(i);
            WordLabelDefine singledefine = this.printController.createWordLabelDefine();
            ComparePrintUtil.tranToWordLableDefine(textData, singledefine);
            WordLabelDefine netLable = null;
            if (!netLableDic.containsKey(singledefine.getText())) {
                printItem.setChangeType(CompareChangeType.CHANGE_PRINTATTRCHANGE);
                printItem.setUpdateType(CompareUpdateType.UPDATE_OVER);
                return false;
            }
            netLable = (WordLabelDefine)netLableDic.get(singledefine.getText());
            if (netLable != null) {
                if (singledefine.getHorizontalPos() == netLable.getHorizontalPos() && singledefine.getVerticalPos() == netLable.getVerticalPos() && singledefine.getElement() == netLable.getElement()) continue;
                printItem.setChangeType(CompareChangeType.CHANGE_PRINTATTRCHANGE);
                printItem.setUpdateType(CompareUpdateType.UPDATE_OVER);
                return false;
            }
            printItem.setChangeType(CompareChangeType.CHANGE_PRINTATTRCHANGE);
            printItem.setUpdateType(CompareUpdateType.UPDATE_OVER);
            return false;
        }
        printItem.setChangeType(CompareChangeType.CHANGE_PRINTATTRNOCHANGE);
        printItem.setUpdateType(CompareUpdateType.UPDATE_OVER);
        return true;
    }

    @Override
    public void batchDelete(ParaCompareContext compareContext, String compareKey) throws Exception {
        CompareDataPrintItemDTO compareDataDTO = new CompareDataPrintItemDTO();
        compareDataDTO.setInfoKey(compareKey);
        this.printDataService.delete(compareDataDTO);
    }
}

