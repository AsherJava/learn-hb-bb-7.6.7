/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.parser.table.FieldDefs
 *  com.jiuqi.nr.single.core.para.parser.table.RepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.ReportTableType
 *  com.jiuqi.nr.single.core.para.parser.table.ReportZbCaptionUtil
 *  com.jiuqi.nr.single.core.para.parser.table.ZBInfo
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums$GridBorderStyle
 *  nr.single.map.configurations.file.ini.BufferIni
 *  nr.single.map.configurations.file.ini.IniBuffer
 *  nr.single.map.configurations.file.ini.MemStream
 *  nr.single.map.configurations.file.ini.Stream
 *  nr.single.map.configurations.file.ini.StreamIniBuffer
 */
package nr.single.para.compare.internal.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.table.FieldDefs;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ReportTableType;
import com.jiuqi.nr.single.core.para.parser.table.ReportZbCaptionUtil;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import nr.single.map.configurations.file.ini.BufferIni;
import nr.single.map.configurations.file.ini.IniBuffer;
import nr.single.map.configurations.file.ini.MemStream;
import nr.single.map.configurations.file.ini.Stream;
import nr.single.map.configurations.file.ini.StreamIniBuffer;
import nr.single.para.compare.bean.ParaCompareContext;
import nr.single.para.compare.bean.ParaCompareRegionResult;
import nr.single.para.compare.definition.CompareDataFieldDTO;
import nr.single.para.compare.definition.CompareDataFormDTO;
import nr.single.para.compare.definition.CompareMapFieldDTO;
import nr.single.para.compare.definition.ISingleCompareDataFieldService;
import nr.single.para.compare.definition.ISingleCompareDataFormService;
import nr.single.para.compare.definition.ISingleCompareMapFieldService;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareContextType;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import nr.single.para.compare.internal.system.SingleParaOptionsService;
import nr.single.para.compare.internal.util.CompareTypeMan;
import nr.single.para.compare.service.FormDefineCompareService;
import nr.single.para.parain.service.IParaImportCommonService;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormDefineCompareServiceImpl
implements FormDefineCompareService {
    private static final Logger log = LoggerFactory.getLogger(FormDefineCompareServiceImpl.class);
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private ISingleCompareDataFormService formCompareService;
    @Autowired
    private ISingleCompareDataFieldService fieldCompareService;
    @Autowired
    private IDesignDataSchemeService dataSchemeSevice;
    @Autowired
    private ISingleCompareMapFieldService mapFieldService;
    @Autowired
    private IParaImportCommonService paraImportService;
    @Autowired
    private SingleParaOptionsService paraOptionService;

    @Override
    public boolean compareFormsDefine(ParaCompareContext compareContext) throws Exception {
        boolean result = true;
        double startPos = compareContext.getCurProgress();
        String fileFlag = "";
        if (compareContext.getCache().getDataScheme() != null) {
            fileFlag = compareContext.getCache().getDataScheme().getPrefix();
        } else if (StringUtils.isNotEmpty((String)compareContext.getOption().getDataPrefix())) {
            fileFlag = compareContext.getOption().getDataPrefix();
        }
        CompareDataFormDTO formQueryParam = new CompareDataFormDTO();
        formQueryParam.setInfoKey(compareContext.getComapreResult().getCompareId());
        formQueryParam.setDataType(CompareDataType.DATA_FORM);
        List<CompareDataFormDTO> oldFormList = this.formCompareService.list(formQueryParam);
        HashMap<String, CompareDataFormDTO> oldFormDic = new HashMap<String, CompareDataFormDTO>();
        for (CompareDataFormDTO oldData : oldFormList) {
            oldFormDic.put(oldData.getSingleCode(), oldData);
        }
        this.analSingleFieldRepeat(compareContext);
        if (StringUtils.isNotEmpty((String)compareContext.getDataSchemeKey())) {
            ArrayList<String> dataSchemes = new ArrayList<String>();
            dataSchemes.add(compareContext.getDataSchemeKey());
            this.paraImportService.MakeTaskFieldsCacheByDataScheme(compareContext.getSchemeInfoCache(), dataSchemes);
        }
        HashMap<String, CompareTypeMan> dataParams = new HashMap<String, CompareTypeMan>();
        this.getDataParam(compareContext, dataParams, null);
        CompareTypeMan compareFormMan = (CompareTypeMan)dataParams.get("compareFormMan");
        if (this.paraOptionService.isFieldMatchOnlyMapping() && !compareContext.getParaInfo().isIniFieldMap()) {
            log.info("JIO\u6587\u4ef6\u4e2d\u65e0INI\u6620\u5c04\uff0c\u6309\u6307\u6807\u6807\u8bc6\u5339\u914d");
        }
        ArrayList<CompareDataFormDTO> addItems = new ArrayList<CompareDataFormDTO>();
        ArrayList<CompareDataFormDTO> updateItems = new ArrayList<CompareDataFormDTO>();
        int formCount = compareContext.getParaInfo().getRepInfos().size();
        double curProgress = startPos;
        double addProgrees = compareContext.getCurProgressLength() / (double)(formCount + 1);
        for (RepInfo rep : compareContext.getParaInfo().getRepInfos()) {
            String gridDataCode;
            if ("FMDM".equalsIgnoreCase(rep.getCode())) continue;
            compareContext.onProgress(Math.floor((curProgress += addProgrees) * 10000.0) / 10000.0, "\u6bd4\u8f83\u62a5\u8868\uff1a" + rep.getCode());
            log.info("\u6bd4\u8f83\u62a5\u8868\uff1a" + rep.getCode() + "," + rep.getTitle());
            String singleFormCode = rep.getCode();
            String singleFormTitle = rep.getTitle();
            CompareDataFormDTO dataItem = null;
            if (oldFormDic.containsKey(singleFormCode)) {
                dataItem = (CompareDataFormDTO)oldFormDic.get(singleFormCode);
                updateItems.add(dataItem);
            } else {
                dataItem = new CompareDataFormDTO();
                dataItem.setKey(UUID.randomUUID().toString());
                dataItem.setInfoKey(compareContext.getComapreResult().getCompareId());
                dataItem.setDataType(CompareDataType.DATA_FORM);
                if (this.paraOptionService.isFieldMatchOnlyMapping()) {
                    if (!compareContext.getParaInfo().isIniFieldMap()) {
                        dataItem.setCompareType(CompareContextType.CONTEXT_CODE);
                    } else if (compareContext.getParaInfo().isIniFieldMap() && !rep.isIniFieldMap()) {
                        log.info("\u62a5\u8868" + rep.getCode() + "\u65e0INI\u6620\u5c04\uff0c\u6309\u6307\u6807\u6807\u8bc6\u5339\u914d");
                        dataItem.setCompareType(CompareContextType.CONTEXT_CODE);
                    } else {
                        dataItem.setCompareType(CompareContextType.CONTEXT_INIMAP);
                    }
                } else if (this.paraOptionService.isFieldMatchOnlyCode()) {
                    dataItem.setCompareType(CompareContextType.CONTEXT_CODE);
                } else if (this.paraOptionService.isFieldMatchOnlyGlobalCode()) {
                    dataItem.setCompareType(CompareContextType.CONTEXT_GLOBALCODE);
                } else if (this.paraOptionService.isFieldMatchCodeFirstTitle()) {
                    dataItem.setCompareType(CompareContextType.CONTEXT_CODEFIRST_TITLE);
                } else if (this.paraOptionService.isFieldMatchTitleFirstCode()) {
                    dataItem.setCompareType(CompareContextType.CONTEXT_TITLEFIRST_CODE);
                } else if (compareContext.isUniqueField()) {
                    dataItem.setCompareType(CompareContextType.CONTEXT_CODE);
                } else if (!compareContext.getSingleRepeatRepField().isEmpty() && !compareContext.getSingleRepeatRepField().containsKey(singleFormCode)) {
                    log.info("\u62a5\u8868" + rep.getCode() + "\u7684\u6307\u6807\u6807\u8bc6\u5728JIO\u5185\u552f\u4e00\uff0c\u6309\u6307\u6807\u6807\u8bc6\u5339\u914d");
                    dataItem.setCompareType(CompareContextType.CONTEXT_CODE);
                } else {
                    dataItem.setCompareType(CompareContextType.CONTEXT_TITLE);
                }
                dataItem.setIniFieldMap(rep.isIniFieldMap());
                addItems.add(dataItem);
            }
            dataItem.setSingleCode(singleFormCode);
            dataItem.setSingleTitle(singleFormTitle);
            dataItem.setOrder(OrderGenerator.newOrder());
            if ("FMDM".equalsIgnoreCase(singleFormCode)) {
                compareContext.getParaInfo().getFMReportData();
            }
            if (rep.getTableType() == ReportTableType.RTT_BLOBTABLE || rep.getTableType() == ReportTableType.RTT_WORDTABLE) {
                compareContext.getParaInfo().getFJReportData(rep);
                gridDataCode = Convert.bytesToBase64((byte[])rep.getReportData().getGridBytes());
                dataItem.setSingleData(gridDataCode);
            } else {
                gridDataCode = Convert.bytesToBase64((byte[])rep.getReportData().getGridBytes());
                dataItem.setSingleData(gridDataCode);
            }
            Grid2Data singleGrid = null;
            if (null != dataItem && StringUtils.isNotEmpty((String)dataItem.getSingleData())) {
                GridCellData cell;
                int i;
                int regionRight;
                boolean isFloatAreaNull;
                byte[] gridDataBytes = Convert.base64ToBytes((CharSequence)dataItem.getSingleData());
                singleGrid = Grid2Data.bytesToGrid((byte[])gridDataBytes);
                if (rep.getTableType() == ReportTableType.RTT_ROWFLOATTABLE) {
                    for (FieldDefs subDef : rep.getDefs().getSubMbs()) {
                        isFloatAreaNull = subDef == null || subDef.getRegionInfo() == null || subDef.getRegionInfo().getMapArea() == null;
                        if (isFloatAreaNull) continue;
                        int regionBottomn = subDef.getRegionInfo().getMapArea().getFloatRangeEndNo();
                        int regionLef = 1;
                        regionRight = rep.getColCount() - 1;
                        for (i = regionLef; i <= regionRight; ++i) {
                            cell = singleGrid.getGridCellData(i, regionBottomn);
                            if (null == cell) continue;
                            cell.setBottomBorderStyle(GridEnums.GridBorderStyle.SOLID.getStyle());
                            cell.setBottomBorderColor(-1);
                        }
                    }
                } else if (rep.getTableType() == ReportTableType.RTT_COLFLOATTALBE) {
                    for (FieldDefs subDef : rep.getDefs().getSubMbs()) {
                        isFloatAreaNull = subDef == null || subDef.getRegionInfo() == null || subDef.getRegionInfo().getMapArea() == null;
                        if (isFloatAreaNull) continue;
                        int regionTop = 1;
                        int regionBottomn = rep.getRowCount() - 1;
                        regionRight = subDef.getRegionInfo().getMapArea().getFloatRangeEndNo();
                        for (i = regionTop; i <= regionBottomn; ++i) {
                            cell = singleGrid.getGridCellData(regionRight, i);
                            if (null == cell) continue;
                            cell.setBottomBorderStyle(GridEnums.GridBorderStyle.SOLID.getStyle());
                            cell.setBottomBorderColor(-1);
                        }
                    }
                }
            }
            if (compareFormMan.getNetCodeItems().containsKey(singleFormCode)) {
                dataItem.setUpdateType(CompareUpdateType.UPDATE_OVER);
                dataItem.setChangeType(CompareChangeType.CHANGE_FORMFIELDSAME);
                CompareDataDO data = compareFormMan.getNetCodeItems().get(singleFormCode);
                dataItem.setNetKey(data.getNetKey());
                dataItem.setNetTitle(data.getNetTitle());
                dataItem.setNetCode(data.getNetCode());
                dataItem.setNetData(data.getNetData());
                dataItem.setMatchKey(dataItem.getNetKey());
                Grid2Data netGrid = null;
                if (null != dataItem && StringUtils.isNotEmpty((String)dataItem.getNetData())) {
                    byte[] gridDataBytes = Convert.base64ToBytes((CharSequence)dataItem.getNetData());
                    netGrid = Grid2Data.bytesToGrid((byte[])gridDataBytes);
                }
                boolean formDataIsSame = this.compareFormDataIsSame(dataItem, singleGrid, netGrid);
                boolean formFieldisSame = this.compareFormFields(compareContext, rep, singleGrid, fileFlag, false, dataItem, dataParams);
                if (formDataIsSame) {
                    formDataIsSame = this.compareFormByFieldPos(compareContext, dataItem, rep);
                }
                if (formDataIsSame && formFieldisSame) {
                    dataItem.setChangeType(CompareChangeType.CHANGE_FORMFIELDSAME);
                    dataItem.setUpdateType(CompareUpdateType.UPDATE_IGNORE);
                } else if (formDataIsSame && !formFieldisSame) {
                    dataItem.setChangeType(CompareChangeType.CHANGE_FORMSAMENOFIELD);
                } else if (!formDataIsSame && formFieldisSame) {
                    dataItem.setChangeType(CompareChangeType.CHANGE_FIELDSAMENOFORM);
                } else if (!formDataIsSame && !formFieldisSame) {
                    dataItem.setChangeType(CompareChangeType.CHANGE_FORMFIELDNOSAME);
                }
                if (!formDataIsSame || !formFieldisSame) {
                    result = false;
                }
            } else {
                dataItem.setNetTitle(dataItem.getSingleTitle());
                dataItem.setNetCode(dataItem.getSingleCode());
                dataItem.setChangeType(CompareChangeType.CHANGE_NOEXIST);
                dataItem.setUpdateType(CompareUpdateType.UPDATE_NEW);
                boolean formFieldisSame = this.compareFormFields(compareContext, rep, singleGrid, fileFlag, true, dataItem, dataParams);
                if (!formFieldisSame) {
                    dataItem.setChangeType(CompareChangeType.CHANGE_FORMNOEXIST_FIELDNOSAME);
                    result = false;
                } else {
                    dataItem.setChangeType(CompareChangeType.CHANGE_FORMNOEXIST_FIELDSAME);
                }
            }
            if (addItems.size() > 20) {
                this.formCompareService.batchAdd(addItems);
                addItems.clear();
            }
            if (updateItems.size() <= 20) continue;
            this.formCompareService.batchUpdate(updateItems);
            updateItems.clear();
        }
        if (!addItems.isEmpty()) {
            this.formCompareService.batchAdd(addItems);
            addItems.clear();
        }
        if (!updateItems.isEmpty()) {
            this.formCompareService.batchUpdate(updateItems);
            updateItems.clear();
        }
        return result;
    }

    @Override
    public boolean compareFormsDefine(ParaCompareContext compareContext, String formCompareKey) throws Exception {
        ArrayList<String> formCompareKeys = new ArrayList<String>();
        formCompareKeys.add(formCompareKey);
        return this.compareFormsDefines(compareContext, formCompareKeys);
    }

    @Override
    public boolean compareFormsDefines(ParaCompareContext compareContext, List<String> formCompareKeys) throws Exception {
        boolean result = false;
        ArrayList<CompareDataFormDTO> midstoreFormList = new ArrayList<CompareDataFormDTO>();
        ArrayList<String> netFormKeys = new ArrayList<String>();
        for (String formCompareKey : formCompareKeys) {
            CompareDataFormDTO formQueryParam = new CompareDataFormDTO();
            formQueryParam.setInfoKey(compareContext.getComapreResult().getCompareId());
            formQueryParam.setDataType(CompareDataType.DATA_FORM);
            formQueryParam.setKey(formCompareKey);
            List<CompareDataFormDTO> oldFormList = this.formCompareService.list(formQueryParam);
            if (oldFormList == null || oldFormList.isEmpty()) continue;
            for (CompareDataFormDTO dto : oldFormList) {
                if (StringUtils.isNotEmpty((String)dto.getNetKey())) {
                    netFormKeys.add(dto.getNetKey());
                }
                midstoreFormList.add(dto);
            }
        }
        if (midstoreFormList.size() > 0) {
            result = true;
            this.analSingleFieldRepeat(compareContext);
            HashMap<String, CompareTypeMan> dataParams = new HashMap<String, CompareTypeMan>();
            this.getDataParam(compareContext, dataParams, netFormKeys);
            for (CompareDataFormDTO formCompare : midstoreFormList) {
                boolean isFormNew = true;
                if (StringUtils.isNotEmpty((String)formCompare.getNetKey())) {
                    DesignFormDefine formDeinfe = this.viewController.querySoftFormDefine(formCompare.getNetKey());
                    isFormNew = formDeinfe == null;
                }
                formCompare.setCompareType(compareContext.getOption().getItemCompareType());
                RepInfo rep = (RepInfo)compareContext.getParaInfo().getRepInfoCodeList().get(formCompare.getSingleCode());
                Grid2Data singleGrid = null;
                if (null != formCompare && StringUtils.isNotEmpty((String)formCompare.getSingleData())) {
                    byte[] gridDataBytes = Convert.base64ToBytes((CharSequence)formCompare.getSingleData());
                    singleGrid = Grid2Data.bytesToGrid((byte[])gridDataBytes);
                }
                String fileFlag = "";
                if (compareContext.getCache().getDataScheme() != null) {
                    fileFlag = compareContext.getCache().getDataScheme().getPrefix();
                } else if (StringUtils.isNotEmpty((String)compareContext.getOption().getDataPrefix())) {
                    fileFlag = compareContext.getOption().getDataPrefix();
                }
                boolean formFieldisSame = this.compareFormFields(compareContext, rep, singleGrid, fileFlag, isFormNew, formCompare, dataParams);
                boolean formDataIsSame = false;
                if (formCompare.getChangeType() == CompareChangeType.CHANGE_FORMFIELDSAME || formCompare.getChangeType() == CompareChangeType.CHANGE_FORMSAMENOFIELD) {
                    formDataIsSame = true;
                }
                if (!isFormNew) {
                    Grid2Data netGrid = null;
                    if (StringUtils.isNotEmpty((String)formCompare.getNetData())) {
                        byte[] gridDataBytes = Convert.base64ToBytes((CharSequence)formCompare.getNetData());
                        netGrid = Grid2Data.bytesToGrid((byte[])gridDataBytes);
                    }
                    if (formDataIsSame = this.compareFormDataIsSame(formCompare, singleGrid, netGrid)) {
                        formDataIsSame = this.compareFormByFieldPos(compareContext, formCompare, rep);
                    }
                    if (formDataIsSame && formFieldisSame) {
                        formCompare.setChangeType(CompareChangeType.CHANGE_FORMFIELDSAME);
                        formCompare.setUpdateType(CompareUpdateType.UPDATE_IGNORE);
                    } else if (formDataIsSame && !formFieldisSame) {
                        formCompare.setChangeType(CompareChangeType.CHANGE_FORMSAMENOFIELD);
                        formCompare.setUpdateType(CompareUpdateType.UPDATE_OVER);
                    } else if (!formDataIsSame && formFieldisSame) {
                        formCompare.setChangeType(CompareChangeType.CHANGE_FIELDSAMENOFORM);
                        formCompare.setUpdateType(CompareUpdateType.UPDATE_OVER);
                    } else if (!formDataIsSame && !formFieldisSame) {
                        formCompare.setChangeType(CompareChangeType.CHANGE_FORMFIELDNOSAME);
                        formCompare.setUpdateType(CompareUpdateType.UPDATE_OVER);
                    }
                } else {
                    formCompare.setChangeType(CompareChangeType.CHANGE_NOEXIST);
                    formCompare.setUpdateType(CompareUpdateType.UPDATE_NEW);
                    if (!formFieldisSame) {
                        formCompare.setChangeType(CompareChangeType.CHANGE_FORMNOEXIST_FIELDNOSAME);
                    } else {
                        formCompare.setChangeType(CompareChangeType.CHANGE_FORMNOEXIST_FIELDSAME);
                    }
                }
                if (!formFieldisSame) {
                    result = false;
                }
                this.formCompareService.update(formCompare);
            }
        }
        return result;
    }

    @Override
    public boolean compareFormRegionDefine(ParaCompareContext compareContext, String formCompareKey, int singleFloatingId, String newTableKey) throws Exception {
        boolean result = true;
        if (StringUtils.isEmpty((String)newTableKey)) {
            return result;
        }
        CompareDataFormDTO formQueryParam = new CompareDataFormDTO();
        formQueryParam.setInfoKey(compareContext.getComapreResult().getCompareId());
        formQueryParam.setDataType(CompareDataType.DATA_FORM);
        formQueryParam.setKey(formCompareKey);
        List<CompareDataFormDTO> oldFormList = this.formCompareService.list(formQueryParam);
        if (oldFormList.size() > 0) {
            CompareDataFormDTO formCompare = oldFormList.get(0);
            boolean isFormNew = true;
            if (StringUtils.isNotEmpty((String)formCompare.getNetKey())) {
                DesignFormDefine formDeinfe = this.viewController.querySoftFormDefine(formCompare.getNetKey());
                boolean bl = isFormNew = formDeinfe == null;
            }
            if (formCompare.getCompareType() == null) {
                formCompare.setCompareType(CompareContextType.CONTEXT_TITLE);
            }
            RepInfo rep = (RepInfo)compareContext.getParaInfo().getRepInfoCodeList().get(formCompare.getSingleCode());
            Grid2Data singleGrid = null;
            if (null != formCompare && StringUtils.isNotEmpty((String)formCompare.getSingleData())) {
                byte[] gridDataBytes = Convert.base64ToBytes((CharSequence)formCompare.getSingleData());
                singleGrid = Grid2Data.bytesToGrid((byte[])gridDataBytes);
            }
            String fileFlag = "";
            if (compareContext.getCache().getDataScheme() != null) {
                fileFlag = compareContext.getCache().getDataScheme().getPrefix();
            } else if (StringUtils.isNotEmpty((String)compareContext.getOption().getDataPrefix())) {
                fileFlag = compareContext.getOption().getDataPrefix();
            }
            HashMap<String, CompareTypeMan> dataParams = new HashMap<String, CompareTypeMan>();
            ArrayList<String> netformKeys = null;
            if (StringUtils.isNotEmpty((String)formCompare.getNetKey())) {
                netformKeys = new ArrayList<String>();
                netformKeys.add(formCompare.getNetKey());
            }
            this.getDataParam(compareContext, dataParams, netformKeys);
            if (singleFloatingId <= 0) {
                ReportZbCaptionUtil zbUtil = new ReportZbCaptionUtil();
                Map fieldMatchTitles = null;
                fieldMatchTitles = singleGrid != null ? zbUtil.getRepFieldCaptions(rep, singleGrid, compareContext.getOption().isUseFormLevel()) : new HashMap();
                HashMap<String, DesignDataLinkDefine> oldLinkDic = null;
                if (!isFormNew) {
                    List oldlinkList = compareContext.getSchemeInfoCache().getFormLinkCache().get(formCompare.getNetKey());
                    if (oldlinkList == null) {
                        oldlinkList = this.viewController.getAllLinksInForm(formCompare.getNetKey());
                        compareContext.getSchemeInfoCache().getFormLinkCache().put(formCompare.getNetKey(), oldlinkList);
                    }
                    oldLinkDic = oldlinkList.stream().collect(Collectors.toMap(e -> e.getLinkExpression(), e -> e, (k1, k2) -> k2));
                } else {
                    oldLinkDic = new HashMap();
                }
                HashMap<String, DesignDataLinkDefine> posXYLinks = new HashMap<String, DesignDataLinkDefine>();
                if (oldLinkDic.size() > 0) {
                    for (DesignDataLinkDefine link : oldLinkDic.values()) {
                        posXYLinks.put(String.format("%d_%d", link.getPosX(), link.getPosY()), link);
                    }
                }
                ArrayList<ZBInfo> zbs = new ArrayList<ZBInfo>();
                String singleFieldCodes = compareContext.getOption().getVariableMap().get("singleFieldCodes");
                if (StringUtils.isNotEmpty((String)singleFieldCodes)) {
                    HashSet<String> singleFieldDic = new HashSet<String>();
                    String[] singleFieldCodeList = singleFieldCodes.split(",");
                    for (String code : singleFieldCodeList) {
                        singleFieldDic.add(code);
                    }
                    for (ZBInfo zb : rep.getDefs().getZbsNoZDM()) {
                        if (!singleFieldDic.contains(zb.getFieldName())) continue;
                        zbs.add(zb);
                    }
                }
                DesignDataTable dataTable = this.dataSchemeSevice.getDataTable(newTableKey);
                result = this.compareRegionFieldsIsSame(compareContext, rep.getDefs(), zbs, fileFlag, isFormNew, formCompare, fieldMatchTitles, oldLinkDic, dataParams, dataTable);
            } else {
                result = this.compareFormFloatRegionFields(compareContext, rep, singleGrid, fileFlag, isFormNew, formCompare, dataParams, singleFloatingId, newTableKey);
            }
        }
        return result;
    }

    private boolean compareFormFields(ParaCompareContext compareContext, RepInfo rep, Grid2Data singleGrid, String fileFlag, boolean isFormNew, CompareDataFormDTO formCompare, Map<String, CompareTypeMan> dataParams) throws Exception {
        ReportZbCaptionUtil zbUtil = new ReportZbCaptionUtil();
        Map fieldMatchTitles = null;
        fieldMatchTitles = singleGrid != null ? zbUtil.getRepFieldCaptions(rep, singleGrid, compareContext.getOption().isUseFormLevel()) : new HashMap();
        Map<String, DesignDataLinkDefine> oldLinkDic = null;
        if (!isFormNew) {
            List oldlinkList = compareContext.getSchemeInfoCache().getFormLinkCache().get(formCompare.getNetKey());
            if (oldlinkList == null) {
                oldlinkList = this.viewController.getAllLinksInForm(formCompare.getNetKey());
                compareContext.getSchemeInfoCache().getFormLinkCache().put(formCompare.getNetKey(), oldlinkList);
            }
            oldLinkDic = oldlinkList.stream().collect(Collectors.toMap(e -> e.getLinkExpression(), e -> e, (k1, k2) -> k2));
        } else {
            oldLinkDic = new HashMap<String, DesignDataLinkDefine>();
        }
        boolean fieldIsSame = this.compareRegionFieldIsSame(compareContext, rep.getDefs(), fileFlag, isFormNew, formCompare, fieldMatchTitles, oldLinkDic, dataParams);
        if (rep.getDefs().getSubMbs().size() > 0) {
            boolean regionFieldSame = this.compareRegionFloatFieldIsSame(compareContext, rep, fileFlag, isFormNew, formCompare, fieldMatchTitles, oldLinkDic, dataParams);
            boolean bl = fieldIsSame = fieldIsSame && regionFieldSame;
        }
        if (!fieldIsSame || isFormNew || oldLinkDic == null || oldLinkDic.size() > 0) {
            // empty if block
        }
        if (!fieldIsSame) {
            log.debug("\u6307\u6807\u4e0d\u76f8\u540c:" + rep.getCode());
        }
        return fieldIsSame;
    }

    private boolean compareFormFloatRegionFields(ParaCompareContext compareContext, RepInfo rep, Grid2Data singleGrid, String fileFlag, boolean isFormNew, CompareDataFormDTO formCompare, Map<String, CompareTypeMan> dataParams, int singleFloatingId, String newTableKey) throws Exception {
        ReportZbCaptionUtil zbUtil = new ReportZbCaptionUtil();
        Map fieldMatchTitles = null;
        fieldMatchTitles = singleGrid != null ? zbUtil.getRepFieldCaptions(rep, singleGrid, compareContext.getOption().isUseFormLevel()) : new HashMap();
        HashMap<String, DesignDataLinkDefine> oldLinkDic = null;
        if (!isFormNew) {
            List oldlinkList = compareContext.getSchemeInfoCache().getFormLinkCache().get(formCompare.getNetKey());
            if (oldlinkList == null) {
                oldlinkList = this.viewController.getAllLinksInForm(formCompare.getNetKey());
                compareContext.getSchemeInfoCache().getFormLinkCache().put(formCompare.getNetKey(), oldlinkList);
            }
            oldLinkDic = oldlinkList.stream().collect(Collectors.toMap(e -> e.getLinkExpression(), e -> e, (k1, k2) -> k2));
        } else {
            oldLinkDic = new HashMap();
        }
        HashMap<String, DesignDataLinkDefine> posXYLinks = new HashMap<String, DesignDataLinkDefine>();
        if (oldLinkDic.size() > 0) {
            for (DesignDataLinkDefine link : oldLinkDic.values()) {
                posXYLinks.put(String.format("%d_%d", link.getPosX(), link.getPosY()), link);
            }
        }
        DesignDataTable dataTable = this.dataSchemeSevice.getDataTable(newTableKey);
        ArrayList<CompareDataFieldDTO> addItems = new ArrayList<CompareDataFieldDTO>();
        ArrayList<CompareDataFieldDTO> updateItems = new ArrayList<CompareDataFieldDTO>();
        ArrayList<CompareMapFieldDTO> addMapFields = new ArrayList<CompareMapFieldDTO>();
        boolean fieldIsSame = true;
        if (rep.getDefs().getSubMbs().size() > 0) {
            int aIndex = 1;
            for (int i = 0; i < rep.getDefs().getSubMbs().size(); ++i) {
                FieldDefs subDef = (FieldDefs)rep.getDefs().getSubMbs().get(i);
                aIndex = i + 1;
                if (subDef.getRegionInfo().getMapArea().getFloatRangeStartNo() != singleFloatingId) continue;
                fieldIsSame = this.compareRegionFloatRowFieldsIsSame(compareContext, rep, fileFlag, isFormNew, formCompare, fieldMatchTitles, oldLinkDic, dataParams, posXYLinks, addItems, updateItems, addMapFields, dataTable, subDef, aIndex);
                break;
            }
        }
        if (addItems.size() > 0) {
            this.fieldCompareService.batchAdd(addItems);
        }
        if (updateItems.size() > 0) {
            this.fieldCompareService.batchUpdate(updateItems);
        }
        if (addMapFields.size() > 0) {
            this.mapFieldService.batchAdd(addMapFields);
        }
        return fieldIsSame;
    }

    private boolean compareFormLinks(RepInfo rep, Map<String, DesignDataLinkDefine> oldLinkDic) {
        Object posCode;
        boolean fieldIsSame = true;
        HashMap<Object, ZBInfo> singlePosXYs = new HashMap<Object, ZBInfo>();
        for (ZBInfo zb : rep.getDefs().getZbsNoZDM()) {
            if (zb.getGridPos()[0] <= 0 || zb.getGridPos()[1] <= 0) continue;
            posCode = String.format("%d_%d", zb.getGridPos()[0], zb.getGridPos()[1]);
            singlePosXYs.put(posCode, zb);
        }
        if (rep.getDefs().getSubMbs().size() > 0) {
            for (int i = 0; i < rep.getDefs().getSubMbs().size(); ++i) {
                FieldDefs subDef = (FieldDefs)rep.getDefs().getSubMbs().get(i);
                for (ZBInfo zb : subDef.getZbsNoZDM()) {
                    if (zb.getGridPos()[0] <= 0 || zb.getGridPos()[1] <= 0) continue;
                    String posCode2 = String.format("%d_%d", zb.getGridPos()[0], zb.getGridPos()[1]);
                    singlePosXYs.put(posCode2, zb);
                }
            }
        }
        for (DesignDataLinkDefine link : oldLinkDic.values()) {
            if (link.getPosX() <= 0 || link.getPosY() <= 0) continue;
            posCode = String.format("%d_%d", link.getPosX(), link.getPosY());
            if (singlePosXYs.containsKey(posCode)) {
                ZBInfo zb;
                zb = (ZBInfo)singlePosXYs.get(posCode);
                if (zb.getNumPos()[0] == 0 || zb.getNumPos()[1] == 0 || zb.getNumPos()[0] == link.getColNum() && zb.getNumPos()[1] == link.getRowNum() || zb.getHLNumEmpty()) continue;
                fieldIsSame = false;
                log.info("\u8868" + rep.getCode() + ",\u5355\u673a\u7248\u5355\u5143\u683c\uff1a" + (String)posCode + ",\u5176\u903b\u8f91\u6307\u6807\u8ddf\u7f51\u62a5\u4e0d\u4e00\u81f4");
                continue;
            }
            fieldIsSame = false;
            log.info("\u8868" + rep.getCode() + ",\u5355\u673a\u7248\u7269\u7406\u5750\u6807\u4e0d\u5b58\u5728\u6307\u6807\uff1a" + (String)posCode);
        }
        return fieldIsSame;
    }

    private boolean compareRegionFieldIsSame(ParaCompareContext compareContext, FieldDefs def, String fileFlag, boolean isFormNew, CompareDataFormDTO formCompare, Map<String, String> fieldMatchTitles, Map<String, DesignDataLinkDefine> oldLinkDic, Map<String, CompareTypeMan> dataParams) throws SingleCompareException, JQException {
        return this.compareRegionFieldsIsSame(compareContext, def, def.getZbsNoZDM(), fileFlag, isFormNew, formCompare, fieldMatchTitles, oldLinkDic, dataParams, null);
    }

    private boolean compareRegionFieldsIsSame(ParaCompareContext compareContext, FieldDefs def, List<ZBInfo> zbs, String fileFlag, boolean isFormNew, CompareDataFormDTO formCompare, Map<String, String> fieldMatchTitles, Map<String, DesignDataLinkDefine> oldLinkDic, Map<String, CompareTypeMan> dataParams, DesignDataTable dataTable) throws SingleCompareException, JQException {
        boolean fieldIsSame = true;
        CompareTypeMan compareFieldMan = null;
        if (dataTable == null) {
            compareFieldMan = dataParams.get("compareFieldMan");
        } else {
            CompareTypeMan compareMatchMan = dataParams.get("compareMatchMan");
            compareFieldMan = new CompareTypeMan();
            List tableFields = this.dataSchemeSevice.getDataFieldByTable(dataTable.getKey());
            for (Object data : tableFields) {
                CompareDataDO compareDataDO = compareFieldMan.addNetItem(data.getCode(), data.getTitle(), data.getKey());
                compareDataDO.setObjectValue("tableKey", data.getDataTableKey());
                compareDataDO.setObjectValue("alias", data.getAlias());
                compareDataDO.setObjectValue("dataField", data);
                compareDataDO.setObjectValue("dataTable", dataTable);
                if (dataTable.getDataTableType() == DataTableType.TABLE) {
                    compareFieldMan.addFixItem(data.getCode(), compareDataDO);
                }
                if (StringUtils.isNotEmpty((String)data.getAlias())) {
                    compareFieldMan.addAlisItem(data.getAlias(), compareDataDO);
                }
                if (!compareMatchMan.getNetKeyItems().containsKey(data.getKey())) continue;
                CompareDataDO cMatchData = compareMatchMan.getNetKeyItems().get(data.getKey());
                compareFieldMan.addMatchItem(cMatchData.getNetTitle(), compareDataDO);
            }
        }
        CompareDataFieldDTO fieldQueryParam = new CompareDataFieldDTO();
        fieldQueryParam.setInfoKey(compareContext.getComapreResult().getCompareId());
        fieldQueryParam.setDataType(CompareDataType.DATA_FIELD);
        fieldQueryParam.setFormCompareKey(formCompare.getKey());
        fieldQueryParam.setSingleFloatingId(-1);
        List<CompareDataFieldDTO> oldFieldList = this.fieldCompareService.list(fieldQueryParam);
        HashMap<String, CompareDataFieldDTO> oldFieldDic = new HashMap<String, CompareDataFieldDTO>();
        for (CompareDataFieldDTO compareDataFieldDTO : oldFieldList) {
            oldFieldDic.put(compareDataFieldDTO.getSingleCode(), compareDataFieldDTO);
        }
        HashMap<String, DesignDataLinkDefine> posXYLinks = new HashMap<String, DesignDataLinkDefine>();
        if (oldLinkDic.size() > 0) {
            for (DesignDataLinkDefine link : oldLinkDic.values()) {
                posXYLinks.put(String.format("%d_%d", link.getPosX(), link.getPosY()), link);
            }
        }
        ArrayList<CompareDataFieldDTO> arrayList = new ArrayList<CompareDataFieldDTO>();
        ArrayList<CompareDataFieldDTO> updateItems = new ArrayList<CompareDataFieldDTO>();
        ArrayList<CompareMapFieldDTO> addMapFields = new ArrayList<CompareMapFieldDTO>();
        ArrayList<String> zbMatchTitles = new ArrayList<String>();
        for (ZBInfo zb : zbs) {
            String zbMatchTitle1;
            if ("SYS_ORDER".equalsIgnoreCase(zb.getFieldName())) continue;
            String zbMatchTitle = zb.getZbTitle();
            zbMatchTitles.clear();
            if (zb.getGridPos() != null) {
                String zbTitle1 = this.getFieldMatchTilte(fieldMatchTitles, zb.getGridPos()[0], zb.getGridPos()[1]);
                if (StringUtils.isNotEmpty((String)zbTitle1)) {
                    if (zbTitle1.length() > 2000) {
                        zbTitle1 = zbTitle1.substring(zbTitle1.length() - 2000, zbTitle1.length());
                    }
                    zbMatchTitle = zbTitle1;
                    zbMatchTitles.add(zbMatchTitle);
                }
                if (compareContext.getOption().isFieldContainForm()) {
                    zbMatchTitle1 = formCompare.getSingleTitle() + "_" + zbMatchTitle;
                    zbMatchTitles.add(zbMatchTitle1);
                }
            }
            if (StringUtils.isEmpty((String)zbMatchTitle)) {
                zbMatchTitle = zb.getFieldName();
            }
            CompareDataFieldDTO dataItem = null;
            if (oldFieldDic.containsKey(zb.getFieldName())) {
                dataItem = (CompareDataFieldDTO)oldFieldDic.get(zb.getFieldName());
                updateItems.add(dataItem);
            } else {
                dataItem = new CompareDataFieldDTO();
                dataItem.setKey(UUID.randomUUID().toString());
                dataItem.setSingleFloatingId(-1);
                dataItem.setSingleFloatingIndex(-1);
                arrayList.add(dataItem);
            }
            this.compareField(compareContext, formCompare, fieldMatchTitles, dataParams, oldLinkDic, oldFieldDic, zbMatchTitle, zbMatchTitles, zb, dataItem, compareFieldMan, null, addMapFields, true, def);
            if (compareContext.getOption().isFieldContainForm()) {
                zbMatchTitle1 = formCompare.getSingleTitle() + "_" + zbMatchTitle;
                dataItem.setSingleMatchTitle(zbMatchTitle1);
            }
            if (dataItem.getChangeType() == CompareChangeType.CHANGE_NOEXIST) {
                fieldIsSame = false;
            }
            if (!StringUtils.isNotEmpty((String)dataItem.getNetTitle()) || dataItem.getNetTitle().length() <= 1000) continue;
            dataItem.setNetTitle(dataItem.getNetTitle().substring(0, 1000));
        }
        ParaCompareRegionResult regionCompares = this.addComparenFieldsToCache(compareContext, formCompare, -1, dataTable, arrayList, updateItems);
        if (dataTable == null) {
            if (arrayList.size() > 0) {
                this.fieldCompareService.batchAdd(arrayList);
            }
            if (updateItems.size() > 0) {
                this.fieldCompareService.batchUpdate(updateItems);
            }
            if (addMapFields.size() > 0) {
                this.mapFieldService.batchAdd(addMapFields);
            }
        } else {
            compareContext.getComapreResult().getCompareRegions().add(regionCompares);
        }
        return fieldIsSame;
    }

    private boolean compareRegionFloatFieldIsSame(ParaCompareContext compareContext, RepInfo rep, String fileFlag, boolean isFormNew, CompareDataFormDTO formCompare, Map<String, String> fieldMatchTitles, Map<String, DesignDataLinkDefine> oldLinkDic, Map<String, CompareTypeMan> dataParams) throws SingleCompareException {
        boolean fieldIsSame = true;
        CompareTypeMan compareTableMan = dataParams.get("compareTableMan");
        CompareTypeMan compareFieldMan = dataParams.get("compareFieldMan");
        HashMap<String, DesignDataLinkDefine> posXYLinks = new HashMap<String, DesignDataLinkDefine>();
        if (oldLinkDic.size() > 0) {
            for (DesignDataLinkDefine link : oldLinkDic.values()) {
                posXYLinks.put(String.format("%d_%d", link.getPosX(), link.getPosY()), link);
            }
        }
        ArrayList<CompareDataFieldDTO> floatAddItems = new ArrayList<CompareDataFieldDTO>();
        ArrayList<CompareDataFieldDTO> floatUpdateItems = new ArrayList<CompareDataFieldDTO>();
        ArrayList<CompareMapFieldDTO> floatAddMapFields = new ArrayList<CompareMapFieldDTO>();
        int aIndex = 1;
        for (int i = 0; i < rep.getDefs().getSubMbs().size(); ++i) {
            boolean isSame;
            DesignDataTable cTable;
            ArrayList<CompareDataFieldDTO> addItems = new ArrayList<CompareDataFieldDTO>();
            ArrayList<CompareDataFieldDTO> updateItems = new ArrayList<CompareDataFieldDTO>();
            ArrayList<CompareMapFieldDTO> addMapFields = new ArrayList<CompareMapFieldDTO>();
            FieldDefs subDef = (FieldDefs)rep.getDefs().getSubMbs().get(i);
            aIndex = i + 1;
            String regionTitle = rep.getTitle() + "\u7b2c" + aIndex + "\u6570\u636e\u533a\u57df";
            String regionTitle1 = rep.getTitle() + subDef.getRegionInfo().getMapArea().getFloatRangeStartNo() + "\u6570\u636e\u533a\u57df";
            String startCode = formCompare.getSingleCode();
            if (StringUtils.isNotEmpty((String)compareContext.getOption().getDataPrefix())) {
                startCode = compareContext.getOption().getDataPrefix() + "_" + startCode;
            }
            startCode = startCode + "_F";
            String curTableCode = startCode + String.valueOf(subDef.getRegionInfo().getMapArea().getFloatRangeStartNo());
            DesignDataTable dataTable = null;
            CompareDataDO cTableData = null;
            if (this.paraOptionService.isFieldMatchOnlyMapping()) {
                block2: for (ZBInfo zb : subDef.getZbsNoZDM()) {
                    if (StringUtils.isNotEmpty((String)zb.getMappingTalbe()) && compareTableMan.getNetCodeItems().containsKey(zb.getMappingTalbe())) {
                        cTableData = compareTableMan.getNetCodeItems().get(zb.getMappingTalbe());
                        dataTable = (DesignDataTable)cTableData.getObjectValue("dataTable");
                        break;
                    }
                    if (!StringUtils.isEmpty((String)zb.getMappingTalbe()) || !StringUtils.isNotEmpty((String)zb.getMappingCode())) continue;
                    List<CompareDataDO> cList = null;
                    if (compareTableMan.getNetNewTitleItemDic().containsKey(regionTitle)) {
                        cList = compareTableMan.getNetNewTitleItemDic().get(regionTitle);
                    } else if (compareTableMan.getNetNewTitleItemDic().containsKey(regionTitle1)) {
                        cList = compareTableMan.getNetNewTitleItemDic().get(regionTitle1);
                    }
                    if (cList == null) continue;
                    HashMap<String, CompareDataDO> floatFindDic = new HashMap<String, CompareDataDO>();
                    for (CompareDataDO cData : cList) {
                        DesignDataTable cTable2 = (DesignDataTable)cData.getObjectValue("dataTable");
                        if (cTable2.getDataTableType() != DataTableType.DETAIL) continue;
                        floatFindDic.put(cTable2.getCode(), cData);
                    }
                    if (!compareFieldMan.getNetCodeItemDic().containsKey(zb.getMappingCode())) continue;
                    List<CompareDataDO> cFieldList = compareFieldMan.getNetCodeItemDic().get(zb.getMappingCode());
                    for (CompareDataDO cField : cFieldList) {
                        cTable = (DesignDataTable)cField.getObjectValue("dataTable");
                        if (!floatFindDic.containsKey(cTable.getCode())) continue;
                        cTableData = (CompareDataDO)floatFindDic.get(cTable.getCode());
                        dataTable = (DesignDataTable)cTableData.getObjectValue("dataTable");
                        continue block2;
                    }
                }
            }
            if (dataTable == null) {
                boolean isCompareTitle = true;
                if (compareContext.getOption() != null && compareContext.getOption().getFloatRegionCompareType() != null && compareContext.getOption().getFloatRegionCompareType() == CompareContextType.CONTEXT_CODE) {
                    isCompareTitle = false;
                }
                List<CompareDataDO> cList = null;
                if (isCompareTitle) {
                    if (compareTableMan.getNetTitleItemDic().containsKey(regionTitle)) {
                        cList = compareTableMan.getNetTitleItemDic().get(regionTitle);
                    } else if (compareTableMan.getNetTitleItemDic().containsKey(regionTitle1)) {
                        cList = compareTableMan.getNetTitleItemDic().get(regionTitle1);
                    }
                    if (cList == null) {
                        if (compareTableMan.getNetNewTitleItemDic().containsKey(regionTitle)) {
                            cList = compareTableMan.getNetNewTitleItemDic().get(regionTitle);
                        } else if (compareTableMan.getNetNewTitleItemDic().containsKey(regionTitle1)) {
                            cList = compareTableMan.getNetNewTitleItemDic().get(regionTitle1);
                        }
                    }
                } else if (compareTableMan.getNetCodeItemDic().containsKey(curTableCode)) {
                    cList = compareTableMan.getNetCodeItemDic().get(curTableCode);
                } else {
                    ArrayList<CompareDataDO> cListNew1 = new ArrayList<CompareDataDO>();
                    ArrayList<CompareDataDO> cListNew2 = new ArrayList<CompareDataDO>();
                    ArrayList<CompareDataDO> cListNew3 = new ArrayList<CompareDataDO>();
                    for (CompareDataDO cData : compareTableMan.getNetCodeItems().values()) {
                        cTable = (DesignDataTable)cData.getObjectValue("dataTable");
                        if (StringUtils.isNotEmpty((String)curTableCode) && curTableCode.equalsIgnoreCase(cTable.getCode())) {
                            cListNew1.add(cData);
                            continue;
                        }
                        if (StringUtils.isNotEmpty((String)cTable.getCode()) && cTable.getCode().startsWith(curTableCode)) {
                            cListNew2.add(cData);
                            continue;
                        }
                        if (!StringUtils.isNotEmpty((String)cTable.getCode()) || !cTable.getCode().startsWith(startCode)) continue;
                        cListNew3.add(cData);
                    }
                    if (!cListNew1.isEmpty()) {
                        cList = cListNew1;
                    } else if (!cListNew2.isEmpty()) {
                        cList = cListNew2;
                    } else if (!cListNew3.isEmpty()) {
                        cList = cListNew3;
                    }
                }
                if (cList != null) {
                    CompareDataDO cTableData1 = null;
                    CompareDataDO cTableData2 = null;
                    CompareDataDO cTableData3 = null;
                    for (CompareDataDO cData : cList) {
                        cTable = (DesignDataTable)cData.getObjectValue("dataTable");
                        if (StringUtils.isNotEmpty((String)curTableCode) && curTableCode.equalsIgnoreCase(cTable.getCode())) {
                            cTableData1 = cData;
                            continue;
                        }
                        if (StringUtils.isNotEmpty((String)cTable.getCode()) && cTable.getCode().startsWith(curTableCode)) {
                            if (cTableData2 != null) continue;
                            cTableData2 = cData;
                            continue;
                        }
                        if (!StringUtils.isNotEmpty((String)cTable.getCode()) || !cTable.getCode().startsWith(startCode) || cTableData3 != null) continue;
                        cTableData3 = cData;
                    }
                    if (cTableData1 != null) {
                        cTableData = cTableData1;
                    } else if (cTableData2 != null) {
                        cTableData = cTableData2;
                    } else if (cTableData3 != null) {
                        cTableData = cTableData3;
                    }
                    if (cTableData != null) {
                        dataTable = (DesignDataTable)cTableData.getObjectValue("dataTable");
                    }
                    if (cTableData == null && cList != null && cList.size() > 0) {
                        cTableData = cList.get(0);
                        dataTable = (DesignDataTable)cTableData.getObjectValue("dataTable");
                    }
                }
            }
            if (!(isSame = this.compareRegionFloatRowFieldsIsSame(compareContext, rep, fileFlag, isFormNew, formCompare, fieldMatchTitles, oldLinkDic, dataParams, posXYLinks, addItems, updateItems, addMapFields, dataTable, subDef, aIndex))) {
                fieldIsSame = false;
            }
            if (!addItems.isEmpty()) {
                floatAddItems.addAll(addItems);
            }
            if (!updateItems.isEmpty()) {
                floatUpdateItems.addAll(updateItems);
            }
            if (!addMapFields.isEmpty()) {
                floatAddMapFields.addAll(addMapFields);
            }
            this.addComparenFieldsToCache(compareContext, formCompare, subDef.getRegionInfo().getMapArea().getFloatRangeStartNo(), dataTable, addItems, updateItems);
        }
        if (floatAddItems.size() > 0) {
            this.fieldCompareService.batchAdd(floatAddItems);
        }
        if (floatUpdateItems.size() > 0) {
            this.fieldCompareService.batchUpdate(floatUpdateItems);
        }
        if (floatAddMapFields.size() > 0) {
            this.mapFieldService.batchAdd(floatAddMapFields);
        }
        return fieldIsSame;
    }

    private boolean compareRegionFloatRowFieldsIsSame(ParaCompareContext compareContext, RepInfo rep, String fileFlag, boolean isFormNew, CompareDataFormDTO formCompare, Map<String, String> fieldMatchTitles, Map<String, DesignDataLinkDefine> oldLinkDic, Map<String, CompareTypeMan> dataParams, Map<String, DesignDataLinkDefine> posXYLinks, List<CompareDataFieldDTO> addItems, List<CompareDataFieldDTO> updateItems, List<CompareMapFieldDTO> addMapFields, DesignDataTable dataTable, FieldDefs subDef, int aIndex) throws SingleCompareException {
        boolean fieldIsSame = true;
        CompareTypeMan compareFieldMan = dataParams.get("compareFieldMan");
        CompareTypeMan compareMatchMan = dataParams.get("compareMatchMan");
        CompareTypeMan floatDataFieldsMan = new CompareTypeMan();
        if (dataTable != null) {
            List tableFields = this.dataSchemeSevice.getDataFieldByTable(dataTable.getKey());
            for (DesignDataField data : tableFields) {
                CompareDataDO cData = floatDataFieldsMan.addNetItem(data.getCode(), data.getTitle(), data.getKey());
                cData.setObjectValue("tableKey", data.getDataTableKey());
                cData.setObjectValue("alias", data.getAlias());
                cData.setObjectValue("dataField", data);
                cData.setObjectValue("dataTable", dataTable);
                if (dataTable.getDataTableType() == DataTableType.TABLE) {
                    floatDataFieldsMan.addFixItem(data.getCode(), cData);
                }
                if (StringUtils.isNotEmpty((String)data.getAlias())) {
                    floatDataFieldsMan.addAlisItem(data.getAlias(), cData);
                }
                if (!compareMatchMan.getNetKeyItems().containsKey(data.getKey())) continue;
                CompareDataDO cMatchData = compareMatchMan.getNetKeyItems().get(data.getKey());
                floatDataFieldsMan.addMatchItem(cMatchData.getNetTitle(), cData);
            }
        }
        CompareDataFieldDTO fieldQueryParam = new CompareDataFieldDTO();
        fieldQueryParam.setInfoKey(compareContext.getComapreResult().getCompareId());
        fieldQueryParam.setDataType(CompareDataType.DATA_FIELD);
        fieldQueryParam.setFormCompareKey(formCompare.getKey());
        fieldQueryParam.setSingleFloatingId(subDef.getRegionInfo().getMapArea().getFloatRangeStartNo());
        List<CompareDataFieldDTO> oldFieldList = this.fieldCompareService.list(fieldQueryParam);
        Map<String, CompareDataFieldDTO> oldFieldDic = oldFieldList.stream().collect(Collectors.toMap(e -> e.getSingleCode(), e -> e));
        ArrayList<String> zbMatchTitles = new ArrayList<String>();
        for (ZBInfo zb : subDef.getZbsNoZDM()) {
            String zbMatchTitle1;
            if ("SYS_ORDER".equalsIgnoreCase(zb.getFieldName())) continue;
            zbMatchTitles.clear();
            String zbMatchTitle = zb.getZbTitle();
            if (zb.getGridPos() != null) {
                String zbTitle1 = this.getFieldMatchTilte(fieldMatchTitles, zb.getGridPos()[0], zb.getGridPos()[1]);
                if (StringUtils.isNotEmpty((String)zbTitle1)) {
                    if (zbTitle1.length() > 2000) {
                        zbTitle1 = zbTitle1.substring(zbTitle1.length() - 2000, zbTitle1.length());
                    }
                    zbMatchTitle = zbTitle1;
                    zbMatchTitles.add(zbMatchTitle);
                }
                if (compareContext.getOption().isFieldContainForm()) {
                    zbMatchTitle1 = formCompare.getSingleTitle() + "_" + zbMatchTitle;
                    zbMatchTitles.add(zbMatchTitle1);
                }
            }
            if (StringUtils.isEmpty((String)zbMatchTitle)) {
                zbMatchTitle = zb.getFieldName();
            }
            CompareDataFieldDTO dataItem = null;
            if (oldFieldDic.containsKey(zb.getFieldName())) {
                dataItem = oldFieldDic.get(zb.getFieldName());
                updateItems.add(dataItem);
            } else {
                dataItem = new CompareDataFieldDTO();
                dataItem.setKey(UUID.randomUUID().toString());
                dataItem.setSingleFloatingId(subDef.getRegionInfo().getMapArea().getFloatRangeStartNo());
                dataItem.setSingleFloatingIndex(aIndex);
                addItems.add(dataItem);
            }
            this.compareField(compareContext, formCompare, fieldMatchTitles, dataParams, oldLinkDic, oldFieldDic, zbMatchTitle, zbMatchTitles, zb, dataItem, compareFieldMan, floatDataFieldsMan, addMapFields, false, subDef);
            if (compareContext.getOption().isFieldContainForm()) {
                zbMatchTitle1 = formCompare.getSingleTitle() + "_" + zbMatchTitle;
                dataItem.setSingleMatchTitle(zbMatchTitle1);
            }
            if (dataItem.getChangeType() != CompareChangeType.CHANGE_NOEXIST) continue;
            fieldIsSame = false;
        }
        return fieldIsSame;
    }

    private void compareField(ParaCompareContext compareContext, CompareDataFormDTO formCompare, Map<String, String> fieldMatchTitles, Map<String, CompareTypeMan> dataParams, Map<String, DesignDataLinkDefine> oldLinkDic, Map<String, CompareDataFieldDTO> oldFieldDic, String zbMatchTitle, List<String> zbMatchTitles, ZBInfo zb, CompareDataFieldDTO dataItem, CompareTypeMan compareFieldManScheme, CompareTypeMan floatDataFieldsMan, List<CompareMapFieldDTO> addMapFields, boolean isFixed, FieldDefs def) {
        String fileFlag = "";
        if (compareContext.getCache().getDataScheme() != null) {
            fileFlag = compareContext.getCache().getDataScheme().getPrefix();
        } else if (StringUtils.isNotEmpty((String)compareContext.getOption().getDataPrefix())) {
            fileFlag = compareContext.getOption().getDataPrefix();
        }
        CompareContextType compareType = formCompare.getCompareType();
        if (compareType == null) {
            compareType = this.paraOptionService.isFieldMatchOnlyCode() ? CompareContextType.CONTEXT_CODE : (this.paraOptionService.isFieldMatchOnlyMapping() ? CompareContextType.CONTEXT_INIMAP : (this.paraOptionService.isFieldMatchOnlyGlobalCode() ? CompareContextType.CONTEXT_GLOBALCODE : (this.paraOptionService.isFieldMatchCodeFirstTitle() ? CompareContextType.CONTEXT_CODEFIRST_TITLE : (this.paraOptionService.isFieldMatchTitleFirstCode() ? CompareContextType.CONTEXT_TITLEFIRST_CODE : (compareContext.isUniqueField() ? CompareContextType.CONTEXT_CODE : (!compareContext.getSingleRepeatRepField().isEmpty() && !compareContext.getSingleRepeatRepField().containsKey(formCompare.getSingleCode()) ? CompareContextType.CONTEXT_CODE : CompareContextType.CONTEXT_TITLE))))));
            formCompare.setCompareType(compareType);
        }
        CompareTypeMan compareFieldMan = compareFieldManScheme;
        if (!isFixed) {
            compareFieldMan = floatDataFieldsMan;
        }
        CompareTypeMan compareTableMan = dataParams.get("compareTableMan");
        dataItem.setSingleCode(zb.getFieldName());
        dataItem.setSingleTitle(zb.getZbTitle());
        dataItem.setSingleMatchTitle(zbMatchTitle);
        dataItem.setFormCompareKey(formCompare.getKey());
        dataItem.setInfoKey(compareContext.getComapreResult().getCompareId());
        dataItem.setOrder(OrderGenerator.newOrder());
        if (zb.getGridPos() != null) {
            dataItem.setSinglePosX(zb.getGridPos()[0]);
            dataItem.setSinglePosY(zb.getGridPos()[1]);
        }
        String curTableTitle = formCompare.getSingleTitle() + "\u56fa\u5b9a\u6570\u636e\u533a\u57df";
        String curTableCode = formCompare.getSingleCode();
        if (StringUtils.isNotEmpty((String)compareContext.getOption().getDataPrefix())) {
            curTableCode = compareContext.getOption().getDataPrefix() + "_" + curTableCode;
        }
        if (!isFixed) {
            curTableTitle = formCompare.getSingleTitle() + String.valueOf(def.getRegionInfo().getMapArea().getFloatRangeStartNo()) + "\u6570\u636e\u533a\u57df";
            curTableCode = curTableCode + "_F" + String.valueOf(def.getRegionInfo().getMapArea().getFloatRangeStartNo());
        }
        CompareDataDO matchData = null;
        List<CompareDataDO> matchTitleDataList = null;
        String zbMatchTitle1 = formCompare.getSingleTitle() + "_" + zbMatchTitle;
        if (compareFieldMan.getNetMatchItemDic().containsKey(zbMatchTitle1)) {
            matchTitleDataList = compareFieldMan.getNetMatchItemDic().get(zbMatchTitle1);
        } else if (compareFieldMan.getNetMatchItemDic().containsKey(zbMatchTitle)) {
            matchTitleDataList = compareFieldMan.getNetMatchItemDic().get(zbMatchTitle);
        } else {
            for (String matchTitle : zbMatchTitles) {
                if (!compareFieldMan.getNetMatchItemDic().containsKey(matchTitle)) continue;
                matchTitleDataList = compareFieldMan.getNetMatchItemDic().get(matchTitle);
                break;
            }
        }
        List<CompareDataDO> matchTitleDataList2 = this.getMatchFieldListByType(matchTitleDataList, isFixed);
        List<CompareDataDO> matchTitleDataList3 = this.getMatchFieldListByTableTitle(matchTitleDataList2, curTableTitle, formCompare.getSingleTitle());
        List<CompareDataDO> matchTitleDataList5 = this.getMatchFieldListByTableCode(matchTitleDataList2, curTableCode, formCompare.getSingleCode());
        List<CompareDataDO> matchTitleDataList6 = this.getMatchFieldListByTableTitle(matchTitleDataList5, curTableTitle, formCompare.getSingleTitle());
        if (compareContext.getCompareDataType() == CompareDataType.DATA_REGION || compareType == CompareContextType.CONTEXT_GLOBALCODE) {
            matchTitleDataList3 = new ArrayList<CompareDataDO>();
            matchTitleDataList3.addAll(matchTitleDataList2);
            matchTitleDataList5 = new ArrayList<CompareDataDO>();
            matchTitleDataList5.addAll(matchTitleDataList2);
            matchTitleDataList6 = new ArrayList<CompareDataDO>();
            matchTitleDataList6.addAll(matchTitleDataList2);
        }
        ArrayList<CompareDataDO> matchTitleDataList4 = new ArrayList<CompareDataDO>();
        if (matchTitleDataList6.size() > 0) {
            matchTitleDataList4.addAll(matchTitleDataList6);
        } else if (matchTitleDataList3.size() > 0) {
            matchTitleDataList4.addAll(matchTitleDataList3);
        } else if (matchTitleDataList5.size() > 0) {
            matchTitleDataList4.addAll(matchTitleDataList5);
        }
        String zbMatchCode = dataItem.getSingleCode();
        if (compareType == CompareContextType.CONTEXT_INIMAP && StringUtils.isNotEmpty((String)zb.getMappingCode())) {
            zbMatchCode = zb.getMappingCode();
        }
        List<CompareDataDO> matchCodeDataList = null;
        if (compareType == CompareContextType.CONTEXT_GLOBALCODE) {
            if (compareFieldMan.getNetCodeItemDic().containsKey(zbMatchCode)) {
                matchCodeDataList = compareFieldMan.getNetCodeItemDic().get(zbMatchCode);
            } else if (compareFieldMan.getNetAlisItemDic().containsKey(zbMatchCode)) {
                matchCodeDataList = compareFieldMan.getNetAlisItemDic().get(zbMatchCode);
            }
        } else if (compareFieldMan.getNetAlisItemDic().containsKey(zbMatchCode)) {
            matchCodeDataList = compareFieldMan.getNetAlisItemDic().get(zbMatchCode);
        } else if (compareFieldMan.getNetCodeItemDic().containsKey(zbMatchCode)) {
            matchCodeDataList = compareFieldMan.getNetCodeItemDic().get(zbMatchCode);
        }
        List<CompareDataDO> matchCodeDataList2 = this.getMatchFieldListByType(matchCodeDataList, isFixed);
        List<CompareDataDO> matchCodeDataList3 = this.getMatchFieldListByTableTitle(matchCodeDataList2, curTableTitle, formCompare.getSingleTitle());
        List<CompareDataDO> matchCodeDataList5 = this.getMatchFieldListByTableCode(matchCodeDataList2, curTableCode, formCompare.getSingleCode());
        List<CompareDataDO> matchCodeDataList6 = this.getMatchFieldListByTableCode(matchCodeDataList3, curTableCode, formCompare.getSingleCode());
        if (compareContext.getCompareDataType() == CompareDataType.DATA_REGION || compareType == CompareContextType.CONTEXT_GLOBALCODE) {
            matchCodeDataList3 = new ArrayList<CompareDataDO>();
            matchCodeDataList3.addAll(matchCodeDataList2);
            matchCodeDataList5 = new ArrayList<CompareDataDO>();
            matchCodeDataList5.addAll(matchCodeDataList2);
            matchCodeDataList6 = new ArrayList<CompareDataDO>();
            matchCodeDataList6.addAll(matchCodeDataList2);
        }
        ArrayList<CompareDataDO> matchCodeDataList4 = new ArrayList<CompareDataDO>();
        if (isFixed) {
            if (matchCodeDataList6.size() > 0) {
                matchCodeDataList4.addAll(matchCodeDataList6);
            } else if (matchCodeDataList3.size() > 0) {
                matchCodeDataList4.addAll(matchCodeDataList3);
            } else if (matchCodeDataList5.size() > 0) {
                matchCodeDataList4.addAll(matchCodeDataList5);
            } else if (compareContext.isUniqueField() && matchCodeDataList2.size() > 0) {
                matchCodeDataList4.addAll(matchCodeDataList2);
            } else if (!compareContext.getSingleRepeatRepField().isEmpty() && !compareContext.getSingleRepeatRepField().containsKey(formCompare.getSingleCode()) && matchCodeDataList2.size() > 0) {
                matchCodeDataList4.addAll(matchCodeDataList2);
            }
        } else if (matchCodeDataList6.size() > 0) {
            matchCodeDataList4.addAll(matchCodeDataList6);
        } else if (matchCodeDataList3.size() > 0) {
            matchCodeDataList4.addAll(matchCodeDataList3);
        } else if (matchCodeDataList5.size() > 0) {
            matchCodeDataList4.addAll(matchCodeDataList5);
        }
        if (matchCodeDataList4.size() > 0 && matchTitleDataList4.size() > 0) {
            ArrayList<CompareDataDO> sameTitleAndCodes = new ArrayList<CompareDataDO>();
            for (CompareDataDO data : matchCodeDataList4) {
                for (CompareDataDO data2 : matchTitleDataList4) {
                    if (!data.getNetKey().equalsIgnoreCase(data2.getNetKey())) continue;
                    sameTitleAndCodes.add(data);
                }
            }
            if (sameTitleAndCodes.size() > 0) {
                matchData = sameTitleAndCodes.size() == 1 ? (CompareDataDO)sameTitleAndCodes.get(0) : (CompareDataDO)sameTitleAndCodes.get(0);
                dataItem.setChangeType(CompareChangeType.CHANGE_FLAGTITLESAME);
                dataItem.setUpdateType(CompareUpdateType.UPDATA_USENET);
            }
            if (matchData == null) {
                if (compareType == CompareContextType.CONTEXT_CODE || compareType == CompareContextType.CONTEXT_INIMAP || compareType == CompareContextType.CONTEXT_GLOBALCODE || compareType == CompareContextType.CONTEXT_CODEFIRST_TITLE) {
                    matchData = (CompareDataDO)matchCodeDataList4.get(0);
                    dataItem.setChangeType(CompareChangeType.CHANGE_FLAGSAMENOTITLE);
                } else {
                    matchData = (CompareDataDO)matchTitleDataList4.get(0);
                    dataItem.setChangeType(CompareChangeType.CHANGE_TITLESAMENOFLAG);
                }
                dataItem.setUpdateType(CompareUpdateType.UPDATA_USENET);
            }
        } else if (matchTitleDataList4.size() > 0) {
            if (compareType == CompareContextType.CONTEXT_CODE || compareType == CompareContextType.CONTEXT_INIMAP || compareType == CompareContextType.CONTEXT_GLOBALCODE) {
                dataItem.setChangeType(CompareChangeType.CHANGE_NOEXIST);
                dataItem.setUpdateType(CompareUpdateType.UPDATE_NEW);
                matchData = null;
            } else {
                dataItem.setChangeType(CompareChangeType.CHANGE_TITLESAMENOFLAG);
                dataItem.setUpdateType(CompareUpdateType.UPDATA_USENET);
                matchData = (CompareDataDO)matchTitleDataList4.get(0);
            }
        } else if (matchCodeDataList4.size() > 0) {
            if (compareType == CompareContextType.CONTEXT_CODE || compareType == CompareContextType.CONTEXT_INIMAP || compareType == CompareContextType.CONTEXT_GLOBALCODE || compareType == CompareContextType.CONTEXT_CODEFIRST_TITLE || compareType == CompareContextType.CONTEXT_TITLEFIRST_CODE) {
                dataItem.setChangeType(CompareChangeType.CHANGE_FLAGSAMENOTITLE);
                dataItem.setUpdateType(CompareUpdateType.UPDATA_USENET);
                matchData = matchCodeDataList6 != null && matchCodeDataList6.size() > 0 ? matchCodeDataList6.get(0) : (CompareDataDO)matchCodeDataList4.get(0);
            } else {
                dataItem.setChangeType(CompareChangeType.CHANGE_NOEXIST);
                dataItem.setUpdateType(CompareUpdateType.UPDATE_NEW);
                matchData = null;
            }
        } else {
            dataItem.setChangeType(CompareChangeType.CHANGE_NOEXIST);
            dataItem.setUpdateType(CompareUpdateType.UPDATE_NEW);
            matchData = null;
        }
        DesignDataField dataField = null;
        DesignDataTable dataTable = null;
        if (compareType != CompareContextType.CONTEXT_NOMAP && matchData != null) {
            String netFieldKey = null;
            String netFieldCode = null;
            String netFieldTitle = null;
            String matchTitle = null;
            if (matchData != null) {
                netFieldKey = matchData.getNetKey();
                netFieldCode = matchData.getNetCode();
                netFieldTitle = matchData.getNetTitle();
                matchTitle = (String)matchData.getObjectValue("matchTitle");
            }
            dataItem.setNetMatchTitle(matchTitle);
            if (compareFieldMan.getNetKeyItems().containsKey(netFieldKey)) {
                CompareDataDO cFieldData = compareFieldMan.getNetKeyItems().get(netFieldKey);
                dataField = (DesignDataField)cFieldData.getObjectValue("dataField");
                if (dataField != null) {
                    netFieldTitle = dataField.getTitle();
                    if (compareTableMan.getNetKeyItems().containsKey(dataField.getDataTableKey())) {
                        dataTable = (DesignDataTable)cFieldData.getObjectValue("dataTable");
                    }
                }
                if (oldLinkDic.containsKey(netFieldKey)) {
                    DesignDataLinkDefine cLink = oldLinkDic.get(netFieldKey);
                    dataItem.setNetLinkKey(cLink.getKey());
                    dataItem.setNetRegionKey(cLink.getRegionKey());
                    dataItem.setNetPosX(cLink.getPosX());
                    dataItem.setNetPosY(cLink.getPosY());
                }
            }
            dataItem.setNetCode(netFieldCode);
            dataItem.setNetKey(netFieldKey);
            dataItem.setNetTitle(netFieldTitle);
            dataItem.setNetFormKey(formCompare.getNetKey());
            dataItem.setMatchKey(dataItem.getNetKey());
            if (dataItem.getChangeType() == CompareChangeType.CHANGE_TITLESAMENOFLAG) {
                String fieldAlias;
                if (dataField != null && StringUtils.isNotEmpty((String)(fieldAlias = this.getFieldAlis(dataField, dataTable, fileFlag))) && fieldAlias.equalsIgnoreCase(zb.getFieldName())) {
                    dataItem.setChangeType(CompareChangeType.CHANGE_TITLESAMENOFLAG);
                }
            } else if (dataItem.getChangeType() == CompareChangeType.CHANGE_FLAGSAMENOTITLE && dataField != null) {
                if (StringUtils.isNotEmpty((String)dataItem.getNetMatchTitle())) {
                    if (dataItem.getNetMatchTitle().equalsIgnoreCase(dataItem.getSingleMatchTitle())) {
                        dataItem.setChangeType(CompareChangeType.CHANGE_FLAGTITLESAME);
                    }
                } else if (StringUtils.isNotEmpty((String)dataItem.getNetTitle()) && dataItem.getNetTitle().equalsIgnoreCase(dataItem.getSingleMatchTitle())) {
                    dataItem.setChangeType(CompareChangeType.CHANGE_FLAGTITLESAME);
                }
            }
        } else {
            dataItem.setChangeType(CompareChangeType.CHANGE_NOEXIST);
            dataItem.setUpdateType(CompareUpdateType.UPDATE_NEW);
            String netFieldCode = null;
            String netFieldTitle = null;
            netFieldTitle = StringUtils.isEmpty((String)zb.getZbTitle()) ? zbMatchTitle : zb.getZbTitle();
            if (isFixed) {
                netFieldCode = compareType == CompareContextType.CONTEXT_INIMAP && StringUtils.isNotEmpty((String)zb.getMappingCode()) ? zb.getMappingCode() : (compareType == CompareContextType.CONTEXT_GLOBALCODE ? zb.getFieldName() : (compareContext.isUniqueField() ? zb.getFieldName() : (!compareContext.getSingleRepeatRepField().isEmpty() && !compareContext.getSingleRepeatRepField().containsKey(formCompare.getSingleCode()) ? zb.getFieldName() : formCompare.getSingleCode() + "_" + zb.getFieldName())));
                if (compareType == CompareContextType.CONTEXT_GLOBALCODE) {
                    compareFieldMan.addNewItem(netFieldCode, dataItem);
                } else {
                    netFieldCode = this.getNewFieldCode(compareFieldMan, netFieldCode);
                    compareFieldMan.addNewItem(netFieldCode, dataItem);
                }
            } else {
                netFieldCode = compareType == CompareContextType.CONTEXT_INIMAP && StringUtils.isNotEmpty((String)zb.getMappingCode()) ? zb.getMappingCode() : zb.getFieldName();
                if (floatDataFieldsMan != null) {
                    netFieldCode = this.getNewFieldCode(floatDataFieldsMan, netFieldCode);
                    floatDataFieldsMan.addNewItem(netFieldCode, dataItem);
                }
            }
            dataItem.setNetCode(netFieldCode);
            dataItem.setNetTitle(netFieldTitle);
            dataItem.setNetMatchTitle(null);
            dataItem.setNetLinkKey(null);
            dataItem.setNetRegionKey(null);
            dataItem.setNetPosX(0);
            dataItem.setNetPosY(0);
            dataItem.setNetKey(null);
            dataItem.setNetFormKey(null);
            dataItem.setMatchKey(null);
        }
    }

    private List<CompareDataDO> getMatchFieldListByType(List<CompareDataDO> matchDataList, boolean isFixed) {
        ArrayList<CompareDataDO> matchDataList2 = new ArrayList<CompareDataDO>();
        if (matchDataList != null) {
            for (CompareDataDO data : matchDataList) {
                DesignDataTable dataTable = (DesignDataTable)data.getObjectValue("dataTable");
                if (isFixed && dataTable != null && dataTable.getDataTableType() == DataTableType.TABLE) {
                    matchDataList2.add(data);
                    continue;
                }
                if (isFixed || dataTable == null || dataTable.getDataTableType() != DataTableType.DETAIL) continue;
                matchDataList2.add(data);
            }
        }
        return matchDataList2;
    }

    private List<CompareDataDO> getMatchFieldListByTableTitle(List<CompareDataDO> matchDataList, String curTableTitle, String singleFormTile) {
        ArrayList<CompareDataDO> matchDataList3 = new ArrayList<CompareDataDO>();
        ArrayList<CompareDataDO> matchDataList31 = new ArrayList<CompareDataDO>();
        ArrayList<CompareDataDO> matchDataList32 = new ArrayList<CompareDataDO>();
        if (matchDataList != null) {
            for (CompareDataDO data : matchDataList) {
                DesignDataTable dataTable = (DesignDataTable)data.getObjectValue("dataTable");
                if (dataTable != null && curTableTitle.equalsIgnoreCase(dataTable.getTitle())) {
                    matchDataList3.add(data);
                    continue;
                }
                if (dataTable != null && dataTable.getTitle().startsWith(curTableTitle)) {
                    matchDataList31.add(data);
                    continue;
                }
                if (dataTable == null || !dataTable.getTitle().startsWith(singleFormTile)) continue;
                matchDataList32.add(data);
            }
        }
        if (matchDataList3.isEmpty() && !matchDataList31.isEmpty()) {
            matchDataList3.addAll(matchDataList31);
        }
        return matchDataList3;
    }

    private List<CompareDataDO> getMatchFieldListByTableCode(List<CompareDataDO> matchDataList, String curTableCode, String singleFormCode) {
        ArrayList<CompareDataDO> matchDataList4 = new ArrayList<CompareDataDO>();
        ArrayList<CompareDataDO> matchDataList41 = new ArrayList<CompareDataDO>();
        ArrayList<CompareDataDO> matchDataList42 = new ArrayList<CompareDataDO>();
        if (matchDataList != null) {
            for (CompareDataDO data : matchDataList) {
                DesignDataTable dataTable = (DesignDataTable)data.getObjectValue("dataTable");
                if (dataTable != null && curTableCode.equalsIgnoreCase(dataTable.getCode())) {
                    matchDataList4.add(data);
                    continue;
                }
                if (dataTable != null && dataTable.getDataTableType() == DataTableType.DETAIL && dataTable.getCode().endsWith(curTableCode)) {
                    matchDataList41.add(data);
                    continue;
                }
                if (dataTable == null || !dataTable.getCode().endsWith("_" + singleFormCode)) continue;
                matchDataList42.add(data);
            }
        }
        if (matchDataList4.isEmpty()) {
            if (!matchDataList41.isEmpty()) {
                matchDataList4.addAll(matchDataList41);
            } else if (!matchDataList42.isEmpty()) {
                matchDataList4.addAll(matchDataList42);
            }
        }
        return matchDataList4;
    }

    private String getFieldAlis(DesignDataField dataField, DesignDataTable dataTable, String fileFlag) {
        String fieldAlis = dataField.getAlias();
        if (StringUtils.isEmpty((String)fieldAlis)) {
            fieldAlis = dataField.getCode();
            if (dataTable != null) {
                if (fieldAlis.startsWith(dataTable.getCode() + "_")) {
                    fieldAlis = fieldAlis.substring(dataTable.getCode().length() + 1, fieldAlis.length());
                } else if (StringUtils.isNotEmpty((String)fileFlag) && dataTable.getCode().startsWith(fileFlag + "_")) {
                    String formCode = dataTable.getCode().substring(fileFlag.length() + 1, dataTable.getCode().length());
                    if (fieldAlis.startsWith(formCode)) {
                        fieldAlis = fieldAlis.substring(formCode.length() + 1, fieldAlis.length());
                    } else if (fieldAlis.startsWith(dataTable.getCode() + "_")) {
                        fieldAlis = fieldAlis.substring(dataTable.getCode().length() + 1, fieldAlis.length());
                    }
                }
            }
        }
        return fieldAlis;
    }

    private String getNewFieldCode(CompareTypeMan compareFieldMan, String fieldCode) {
        String netFieldCode = fieldCode;
        int value = 1;
        while (compareFieldMan.getNetCodeItemDic().containsKey(netFieldCode) || compareFieldMan.getNetNewCodeItemDic().containsKey(netFieldCode)) {
            netFieldCode = fieldCode + "_" + String.valueOf(value);
            ++value;
        }
        return netFieldCode;
    }

    private String getFieldMatchTilte(Map<String, String> fieldMatchTitles, int col, int row) {
        return fieldMatchTitles.get(String.valueOf(col) + "_" + String.valueOf(row));
    }

    private ParaCompareRegionResult addComparenFieldsToCache(ParaCompareContext compareContext, CompareDataFormDTO formCompare, int floatingId, DesignDataTable dataTable, List<CompareDataFieldDTO> addItems, List<CompareDataFieldDTO> updateItems) {
        ParaCompareRegionResult regionCompares = new ParaCompareRegionResult();
        regionCompares.setInfoKey(compareContext.getComapreResult().getCompareId());
        regionCompares.setCompareDataKey(formCompare.getKey());
        regionCompares.setSingleFloatingId(floatingId);
        if (dataTable != null) {
            regionCompares.setNewTableKey(dataTable.getKey());
        }
        if (!addItems.isEmpty()) {
            regionCompares.getAddFieldItems().addAll(addItems);
        }
        if (!updateItems.isEmpty()) {
            regionCompares.getUpdateItems().addAll(updateItems);
        }
        List<Object> formRegions = null;
        if (compareContext.getComapreResult().getCompareFormRegions().containsKey(formCompare.getSingleCode())) {
            formRegions = compareContext.getComapreResult().getCompareFormRegions().get(formCompare.getSingleCode());
        } else {
            formRegions = new ArrayList();
            compareContext.getComapreResult().getCompareFormRegions().put(formCompare.getSingleCode(), formRegions);
        }
        formRegions.add(regionCompares);
        return regionCompares;
    }

    private boolean compareFormDataIsSame(CompareDataFormDTO dataItem, Grid2Data singleGrid, Grid2Data netGrid) {
        boolean result = true;
        String msg = "";
        if (singleGrid != null && netGrid != null) {
            if (singleGrid.getColumnCount() != netGrid.getColumnCount()) {
                msg = "\u5217\u6570\u4e0d\u4e00\u6837";
                result = false;
            } else if (singleGrid.getRowCount() != netGrid.getRowCount()) {
                msg = "\u884c\u6570\u4e0d\u4e00\u6837";
                result = false;
            } else {
                block0: for (int i = 1; i < singleGrid.getColumnCount(); ++i) {
                    for (int j = 1; j < singleGrid.getRowCount(); ++j) {
                        boolean cellChange;
                        GridCellData singleCell = singleGrid.getGridCellData(i, j);
                        GridCellData netCell = netGrid.getGridCellData(i, j);
                        String singleCellText = singleCell.getEditText();
                        String netCellText = netCell.getEditText();
                        if (StringUtils.isNotEmpty((String)singleCellText)) {
                            if (!singleCellText.equalsIgnoreCase(netCellText)) {
                                msg = "\u5355\u5143\u683c\u6587\u672c\u4e0d\u4e00\u81f4";
                                result = false;
                                continue block0;
                            }
                        } else if (StringUtils.isNotEmpty((String)netCellText)) {
                            msg = "\u5355\u5143\u683c\u6587\u672c\u4e0d\u4e00\u81f4";
                            result = false;
                            continue block0;
                        }
                        if (!(cellChange = false) && singleCell.getBackGroundColor() != netCell.getBackGroundColor()) {
                            if (singleCell.isSilverHead()) {
                                if (!netCell.isSilverHead() && netCell.getBackGroundColor() != 0xF1F1F1) {
                                    cellChange = true;
                                }
                            } else if (singleCell.getBackGroundColor() != 0xFFFFFF || netCell.getBackGroundColor() != -1) {
                                cellChange = true;
                            }
                            if (cellChange) {
                                msg = "\u80cc\u666f\u8272\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!cellChange) {
                            boolean bl = cellChange = singleCell.getBackGroundStyle() != netCell.getBackGroundStyle();
                            if (cellChange) {
                                msg = "\u80cc\u666f\u6837\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!(cellChange || singleCell.getForeGroundColor() == 0 && netCell.getForeGroundColor() == -1)) {
                            boolean bl = cellChange = singleCell.getForeGroundColor() != netCell.getForeGroundColor();
                            if (cellChange) {
                                msg = "\u524d\u666f\u8272\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!cellChange) {
                            boolean bl = cellChange = singleCell.getCellStyleData().getFontSize() != netCell.getCellStyleData().getFontSize();
                            if (cellChange) {
                                msg = "\u5b57\u4f53\u5927\u5c0f\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!(cellChange || singleCell.getCellStyleData().getFontStyle() == netCell.getCellStyleData().getFontStyle() || singleCell.getCellStyleData().getFontStyle() == 0 && netCell.getCellStyleData().getFontStyle() == 1)) {
                            cellChange = true;
                            msg = "\u5b57\u4f53\u6837\u5f0f\u4e0d\u4e00\u81f4";
                        }
                        if (!cellChange) {
                            boolean bl = cellChange = singleCell.getCellStyleData().getBottomBorderColor() != netCell.getCellStyleData().getBottomBorderColor();
                            if (cellChange) {
                                msg = "\u4e0b\u8fb9\u6846\u989c\u8272\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!cellChange) {
                            boolean bl = cellChange = singleCell.getCellStyleData().getBottomBorderStyle() != netCell.getCellStyleData().getBottomBorderStyle();
                            if (cellChange) {
                                msg = "\u4e0b\u8fb9\u6846\u6837\u5f0f\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!cellChange) {
                            boolean bl = cellChange = singleCell.getCellStyleData().getRightBorderColor() != netCell.getCellStyleData().getRightBorderColor();
                            if (cellChange) {
                                msg = "\u53f3\u8fb9\u6846\u989c\u8272\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!cellChange) {
                            boolean bl = cellChange = singleCell.getCellStyleData().getRightBorderStyle() != netCell.getCellStyleData().getRightBorderStyle();
                            if (cellChange) {
                                msg = "\u53f3\u8fb9\u6846\u6837\u5f0f\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!cellChange) {
                            boolean bl = cellChange = singleCell.getCellStyleData().getInverseDiagonalBorderColor() != netCell.getCellStyleData().getInverseDiagonalBorderColor();
                            if (cellChange) {
                                msg = "\u53cd\u659c\u7ebf\u6846\u989c\u8272\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!cellChange) {
                            boolean bl = cellChange = singleCell.getCellStyleData().getInverseDiagonalBorderStyle() != netCell.getCellStyleData().getInverseDiagonalBorderStyle();
                            if (cellChange) {
                                msg = "\u53cd\u659c\u7ebf\u6846\u6837\u5f0f\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!cellChange) {
                            boolean bl = cellChange = singleCell.getCellStyleData().getDiagonalBorderColor() != netCell.getCellStyleData().getDiagonalBorderColor();
                            if (cellChange) {
                                msg = "\u659c\u7ebf\u6846\u989c\u8272\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!cellChange) {
                            boolean bl = cellChange = singleCell.getCellStyleData().getDiagonalBorderStyle() != netCell.getCellStyleData().getDiagonalBorderStyle();
                            if (cellChange) {
                                msg = "\u53cd\u659c\u7ebf\u6846\u6837\u5f0f\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!cellChange) {
                            boolean bl = cellChange = singleCell.getCellStyleData().getHorzAlign() != netCell.getCellStyleData().getHorzAlign();
                            if (cellChange) {
                                msg = "\u6c34\u5e73\u5bf9\u9f50\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!cellChange) {
                            boolean bl = cellChange = singleCell.getCellStyleData().getVertAlign() != netCell.getCellStyleData().getVertAlign();
                            if (cellChange) {
                                msg = "\u5782\u76f4\u5bf9\u9f50\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!cellChange) {
                            boolean bl = cellChange = singleCell.getCellStyleData().isEditable() != netCell.getCellStyleData().isEditable();
                            if (cellChange) {
                                msg = "\u662f\u5426\u53ef\u5199\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!cellChange && singleCell.getCellStyleData().isSilverHead() != netCell.getCellStyleData().isSilverHead()) {
                            if (singleCell.getCellStyleData().isSilverHead()) {
                                if (!netCell.getCellStyleData().isSilverHead() && netCell.getBackGroundColor() != 0xF1F1F1) {
                                    cellChange = true;
                                }
                            } else {
                                cellChange = true;
                            }
                            if (cellChange) {
                                msg = "\u662f\u5426\u8868\u5934\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!cellChange) {
                            boolean bl = cellChange = singleCell.getCellStyleData().getRowSpan() != netCell.getCellStyleData().getRowSpan();
                            if (cellChange) {
                                msg = "\u884c\u65b9\u5411\u662f\u5426\u5408\u5e76\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!cellChange) {
                            boolean bl = cellChange = singleCell.getCellStyleData().getColSpan() != netCell.getCellStyleData().getColSpan();
                            if (cellChange) {
                                msg = "\u5217\u65b9\u5411\u662f\u5426\u5408\u5e76\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!cellChange && StringUtils.isNotEmpty((String)singleCell.getCellStyleData().getFontName())) {
                            boolean bl = cellChange = !singleCell.getCellStyleData().getFontName().equalsIgnoreCase(netCell.getCellStyleData().getFontName());
                            if (cellChange) {
                                msg = "\u5b57\u4f53\u540d\u79f0\u4e0d\u4e00\u81f4";
                            }
                        }
                        if (!cellChange) continue;
                        if (StringUtils.isNotEmpty((String)msg)) {
                            msg = msg + ",\u884c\uff1a" + j + ",\u5217\uff1a" + i;
                        }
                        result = false;
                        continue block0;
                    }
                }
            }
        } else if (singleGrid != null || netGrid != null) {
            result = false;
        }
        if (!result) {
            log.info(dataItem.getSingleCode() + "\u8868\u6837\u4e0d\u4e00\u81f4\uff1a" + msg);
        }
        return result;
    }

    private boolean compareFormByFieldPos(ParaCompareContext compareContext, CompareDataFormDTO formCompare, RepInfo rep) {
        boolean result = true;
        HashMap<String, CompareDataFieldDTO> singleLinkPosDic = new HashMap<String, CompareDataFieldDTO>();
        List<ParaCompareRegionResult> formRegions = compareContext.getComapreResult().getCompareFormRegions().get(formCompare.getSingleCode());
        for (ParaCompareRegionResult paraCompareRegionResult : formRegions) {
            for (CompareDataFieldDTO field : paraCompareRegionResult.getAddFieldItems()) {
                singleLinkPosDic.put(field.getSinglePosX() + "_" + field.getSinglePosY(), field);
            }
            for (CompareDataFieldDTO field : paraCompareRegionResult.getUpdateItems()) {
                singleLinkPosDic.put(field.getSinglePosX() + "_" + field.getSinglePosY(), field);
            }
        }
        List linkList = null;
        if (StringUtils.isNotEmpty((String)formCompare.getNetKey())) {
            linkList = compareContext.getSchemeInfoCache().getFormLinkCache().get(formCompare.getNetKey());
            if (linkList == null) {
                linkList = this.viewController.getAllLinksInForm(formCompare.getNetKey());
                compareContext.getSchemeInfoCache().getFormLinkCache().put(formCompare.getNetKey(), linkList);
            }
            for (DesignDataLinkDefine link : linkList) {
                if (DataLinkType.DATA_LINK_TYPE_FIELD != link.getType()) continue;
                String posCode = link.getPosX() + "_" + link.getPosY();
                String fieldKey = link.getLinkExpression();
                if (singleLinkPosDic.containsKey(posCode) && StringUtils.isNotEmpty((String)fieldKey)) {
                    CompareDataFieldDTO singleField = (CompareDataFieldDTO)singleLinkPosDic.get(posCode);
                    if (fieldKey.equalsIgnoreCase(singleField.getNetKey())) continue;
                    result = false;
                    log.info("\u8868" + rep.getCode() + ",\u5355\u673a\u7248\u5355\u5143\u683c" + posCode + ",\u5176\u5339\u914d\u7684\u6307\u6807\u4e0e\u7f51\u62a5\u4e0d\u76f8\u540c");
                    break;
                }
                result = false;
                log.info("\u8868" + rep.getCode() + ",\u5355\u673a\u7248\u5355\u5143\u683c" + posCode + ",\u672a\u5b9a\u4e49\u6307\u6807\uff0c\u6216\u8005\u7f51\u62a5\u5355\u5143\u683c\u65e0\u5bf9\u5e94\u7684\u6307\u6807");
                break;
            }
            if (result) {
                Map<String, DesignDataLinkDefine> map = linkList.stream().collect(Collectors.toMap(e -> e.getLinkExpression(), e -> e, (k1, k2) -> k2));
                result = this.compareFormLinks(rep, map);
            }
        } else {
            result = false;
        }
        return result;
    }

    private void analSingleFieldRepeat(ParaCompareContext compareContext) {
        Map<String, Set<String>> singleRepeatRepFieldsDic = this.paraImportService.getReportRepeatFields(compareContext.getParaInfo().getFmRepInfo(), compareContext.getParaInfo().getRepInfos());
        compareContext.setSingleRepeatRepField(singleRepeatRepFieldsDic);
        compareContext.setUniqueField(singleRepeatRepFieldsDic != null && singleRepeatRepFieldsDic.isEmpty());
        if (compareContext.isUniqueField()) {
            log.info("JIO\u4efb\u52a1\u5185\u6307\u6807\u4ee3\u7801\u552f\u4e00");
        } else if (singleRepeatRepFieldsDic != null && !singleRepeatRepFieldsDic.isEmpty()) {
            StringBuilder sp = new StringBuilder();
            for (String formCode : singleRepeatRepFieldsDic.keySet()) {
                sp.append(formCode).append(",");
            }
            log.info("JIO\u4efb\u52a1\u62a5\u8868\u5b58\u5728\u91cd\u590d\u6807\u8bc6\u6307\u6807\uff1a" + sp.toString());
        }
    }

    private void getDataParam(ParaCompareContext compareContext, Map<String, CompareTypeMan> dataParams, List<String> netFormKeys) throws Exception {
        CompareDataFormDTO formQueryParam = new CompareDataFormDTO();
        formQueryParam.setInfoKey(compareContext.getComapreResult().getCompareId());
        formQueryParam.setDataType(CompareDataType.DATA_FORM);
        List<CompareDataFormDTO> oldFormList = this.formCompareService.list(formQueryParam);
        HashMap<String, CompareDataFormDTO> oldFormDic = new HashMap<String, CompareDataFormDTO>();
        for (CompareDataFormDTO compareDataFormDTO : oldFormList) {
            oldFormDic.put(compareDataFormDTO.getSingleCode(), compareDataFormDTO);
        }
        List<DesignFormDefine> netList = null;
        if (netFormKeys != null && !netFormKeys.isEmpty()) {
            netList = new ArrayList();
            for (String string : netFormKeys) {
                DesignFormDefine form = this.viewController.queryFormAndExtAttribute(string, 1);
                if (form == null) continue;
                netList.add(form);
            }
        } else if (StringUtils.isNotEmpty((String)compareContext.getFormSchemeKey())) {
            netList = this.viewController.queryAllFormDefinesByFormScheme(compareContext.getFormSchemeKey());
        }
        CompareTypeMan compareTypeMan = new CompareTypeMan();
        if (netList != null) {
            for (DesignFormDefine data : netList) {
                CompareDataDO cData = compareTypeMan.addNetItem(data.getFormCode(), data.getTitle(), data.getKey());
                String gridCode = Convert.bytesToBase64((byte[])data.getBinaryData());
                cData.setNetData(gridCode);
                cData.setObjectValue("formDefine", data);
            }
        }
        CompareTypeMan compareTypeMan2 = new CompareTypeMan();
        CompareTypeMan compareTableMan = new CompareTypeMan();
        List netTables = null;
        if (StringUtils.isNotEmpty((String)compareContext.getDataSchemeKey())) {
            netTables = this.dataSchemeSevice.getAllDataTable(compareContext.getDataSchemeKey());
        }
        if (netTables != null) {
            for (DesignDataTable dataTable : netTables) {
                String newTitle;
                CompareDataDO cTableData = compareTableMan.addNetItem(dataTable.getCode(), dataTable.getTitle(), dataTable.getKey());
                cTableData.setObjectValue("dataTable", dataTable);
                Object netFields = null;
                if (StringUtils.isNotEmpty((String)compareContext.getDataSchemeKey())) {
                    netFields = this.dataSchemeSevice.getDataFieldByTable(dataTable.getKey());
                }
                if (netFields != null) {
                    CompareTypeMan compareTableFieldMan = new CompareTypeMan();
                    cTableData.setObjectValue("tableFields", compareTableFieldMan);
                    Iterator iterator = netFields.iterator();
                    while (iterator.hasNext()) {
                        DesignDataField fieldData = (DesignDataField)iterator.next();
                        CompareDataDO cData = compareTypeMan2.addNetItem(fieldData.getCode(), fieldData.getTitle(), fieldData.getKey());
                        cData.setObjectValue("tableKey", fieldData.getDataTableKey());
                        cData.setObjectValue("alias", fieldData.getAlias());
                        cData.setObjectValue("dataField", fieldData);
                        cData.setObjectValue("dataTable", dataTable);
                        compareTableFieldMan.addNetItem(cData);
                        if (dataTable.getDataTableType() == DataTableType.TABLE) {
                            compareTypeMan2.addFixItem(fieldData.getCode(), cData);
                            compareTableFieldMan.addFixItem(fieldData.getCode(), cData);
                        }
                        if (!StringUtils.isNotEmpty((String)fieldData.getAlias())) continue;
                        compareTypeMan2.addAlisItem(fieldData.getAlias(), cData);
                        compareTableFieldMan.addAlisItem(fieldData.getAlias(), cData);
                    }
                }
                if (dataTable.getDataTableType() != DataTableType.DETAIL) continue;
                String lastNum = this.getLastNumber(dataTable.getTitle());
                if (StringUtils.isNotEmpty((String)lastNum)) {
                    newTitle = dataTable.getTitle();
                    newTitle = dataTable.getTitle().substring(0, newTitle.length() - lastNum.length());
                    compareTableMan.addNewTitleItem(newTitle, cTableData);
                    continue;
                }
                newTitle = dataTable.getTitle();
                compareTableMan.addNewTitleItem(newTitle, cTableData);
            }
        }
        CompareTypeMan compareMatchMan = new CompareTypeMan();
        CompareMapFieldDTO mapFieldParam = new CompareMapFieldDTO();
        List<Object> matchFieldList = null;
        if (StringUtils.isNotEmpty((String)compareContext.getDataSchemeKey())) {
            mapFieldParam.setDataSchemeKey(compareContext.getDataSchemeKey());
            matchFieldList = this.mapFieldService.list(mapFieldParam);
        } else {
            matchFieldList = new ArrayList();
        }
        for (CompareMapFieldDTO data : matchFieldList) {
            String netCode = "";
            DesignDataField dataField = null;
            DesignDataTable dataTable = null;
            if (!compareTypeMan2.getNetKeyItems().containsKey(data.getFieldKey())) continue;
            CompareDataDO oFieldData = compareTypeMan2.getNetKeyItems().get(data.getFieldKey());
            netCode = oFieldData.getNetCode();
            dataField = (DesignDataField)oFieldData.getObjectValue("dataField");
            dataTable = (DesignDataTable)oFieldData.getObjectValue("dataTable");
            compareTypeMan2.addMatchItem(data.getMatchTitle(), oFieldData);
            String netTitle = data.getMatchTitle();
            String netKey = data.getFieldKey();
            CompareDataDO cMatchData = compareMatchMan.addNetItem(netCode, netTitle, netKey);
            cMatchData.setObjectValue("key", data.getKey());
            if (dataField == null) continue;
            cMatchData.setObjectValue("dataField", dataField);
            cMatchData.setObjectValue("dataTable", dataTable);
        }
        ParaInfo paraInfo = compareContext.getParaInfo();
        if (paraInfo != null && StringUtils.isNotEmpty((String)paraInfo.getTaskDir())) {
            String filePath = compareContext.getParaInfo().getTaskDir() + "PARA" + File.separatorChar + "MapNexus.Ini";
            if (SinglePathUtil.getFileExists((String)filePath)) {
                paraInfo.setIniFieldMap(true);
                File file = new File(FilenameUtils.normalize(filePath));
                try (FileInputStream srcStream = new FileInputStream(file);){
                    MemStream stream = new MemStream();
                    stream.copyFrom((InputStream)srcStream, (long)((InputStream)srcStream).available());
                    StreamIniBuffer aBuffer = new StreamIniBuffer((Stream)stream);
                    BufferIni ini = new BufferIni((IniBuffer)aBuffer);
                    ini.readInfo();
                    for (RepInfo rep : paraInfo.getRepInfos()) {
                        boolean repIniMap = false;
                        int defCount = 0;
                        if (rep.getDefs().getSubMbs() != null) {
                            defCount = rep.getDefs().getSubMbs().size();
                        }
                        for (int fi = -1; fi < defCount; ++fi) {
                            FieldDefs defs = rep.getDefs();
                            if (fi > -1) {
                                defs = (FieldDefs)rep.getDefs().getSubMbs().get(fi);
                            }
                            for (ZBInfo zb : defs.getZbsNoZDM()) {
                                CompareDataDO cTableData;
                                CompareTypeMan compareTableFieldMan;
                                String singleZbName = zb.getFieldName();
                                if (rep.getTableType() == ReportTableType.RTT_BLOBTABLE) {
                                    singleZbName = rep.getCode() + "_F";
                                } else if (rep.getTableType() == ReportTableType.RTT_BLOBTABLE) {
                                    singleZbName = rep.getCode() + "_W";
                                }
                                String mapping = ini.readString(rep.getCode(), singleZbName, "");
                                if (StringUtils.isEmpty((String)mapping)) continue;
                                String netFieldCode = mapping;
                                String netTableCode = null;
                                int id1 = netFieldCode.indexOf(91);
                                int id2 = netFieldCode.indexOf(93);
                                if (id1 >= 0 && id2 >= 0 && id1 < id2) {
                                    netTableCode = mapping.substring(0, id1);
                                    netFieldCode = mapping.substring(id1 + 1, id2);
                                }
                                zb.setMappingCode(netFieldCode);
                                zb.setMappingTalbe(netTableCode);
                                repIniMap = true;
                                if (StringUtils.isEmpty((String)netTableCode)) {
                                    if (!compareTypeMan2.getNetCodeItemDic().containsKey(netFieldCode)) continue;
                                    List<CompareDataDO> netFields = compareTypeMan2.getNetCodeItemDic().get(netFieldCode);
                                    for (CompareDataDO oFieldData : netFields) {
                                        oFieldData.setObjectValue("MappingFieldCode", zb.getFieldName());
                                        oFieldData.setObjectValue("MappingTableCode", rep.getCode());
                                    }
                                    continue;
                                }
                                if (!compareTableMan.getNetCodeItems().containsKey(netTableCode) || !(compareTableFieldMan = (CompareTypeMan)(cTableData = compareTableMan.getNetCodeItems().get(netTableCode)).getObjectValue("tableFields")).getNetCodeItems().containsKey(netFieldCode)) continue;
                                CompareDataDO cFieldData = compareTableFieldMan.getNetCodeItems().get(netFieldCode);
                                cFieldData.setObjectValue("MappingFieldCode", zb.getFieldName());
                                cFieldData.setObjectValue("MappingTableCode", rep.getCode());
                            }
                        }
                        rep.setIniFieldMap(repIniMap);
                    }
                }
            }
            paraInfo.setIniFieldMap(false);
        }
        dataParams.put("compareFormMan", compareTypeMan);
        dataParams.put("compareTableMan", compareTableMan);
        dataParams.put("compareFieldMan", compareTypeMan2);
        dataParams.put("compareMatchMan", compareMatchMan);
    }

    @Override
    public void batchDelete(ParaCompareContext compareContext, String compareKey) throws Exception {
        CompareDataFieldDTO compareDataDTO = new CompareDataFieldDTO();
        compareDataDTO.setInfoKey(compareKey);
        this.fieldCompareService.delete(compareDataDTO);
        CompareDataFormDTO compareDataDTO2 = new CompareDataFormDTO();
        compareDataDTO2.setInfoKey(compareKey);
        this.formCompareService.delete(compareDataDTO2);
    }

    private String getLastNumber(String str) {
        String num = "";
        if (StringUtils.isEmpty((String)str)) {
            return num;
        }
        for (int i = str.length() - 1; i >= 0 && str.charAt(i) >= '0' && str.charAt(i) <= '9'; --i) {
            num = str.charAt(i) + num;
        }
        return num;
    }
}

