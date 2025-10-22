/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.formtype.common.UnitNature
 *  com.jiuqi.nr.formtype.facade.FormTypeDataDefine
 *  com.jiuqi.nr.formtype.facade.FormTypeDefine
 *  com.jiuqi.nr.formtype.facade.FormTypeGroupDefine
 *  com.jiuqi.nr.formtype.service.IFormTypeGroupService
 *  com.jiuqi.nr.formtype.service.IFormTypeService
 *  com.jiuqi.nr.single.core.para.parser.eoums.DataInfo
 *  com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel
 *  com.jiuqi.nr.single.core.para.parser.table.FMRepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.TableTypeType
 *  com.jiuqi.util.OrderGenerator
 *  nr.single.map.data.facade.SingleFileEnumInfo
 *  nr.single.map.data.facade.SingleFileEnumItem
 */
package nr.single.para.parain.internal.service3;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import com.jiuqi.nr.formtype.facade.FormTypeGroupDefine;
import com.jiuqi.nr.formtype.service.IFormTypeGroupService;
import com.jiuqi.nr.formtype.service.IFormTypeService;
import com.jiuqi.nr.single.core.para.parser.eoums.DataInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.para.parser.table.TableTypeType;
import com.jiuqi.util.OrderGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nr.single.map.data.facade.SingleFileEnumInfo;
import nr.single.map.data.facade.SingleFileEnumItem;
import nr.single.para.compare.bean.ParaImportInfoResult;
import nr.single.para.compare.definition.CompareDataEnumDTO;
import nr.single.para.compare.definition.CompareDataEnumItemDTO;
import nr.single.para.compare.definition.ISingleCompareDataEnumItemService;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.service.IEnumBBLXDefineImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnumBBLXDefineImportServiceImpl
implements IEnumBBLXDefineImportService {
    private static final Logger log = LoggerFactory.getLogger(EnumBBLXDefineImportServiceImpl.class);
    @Autowired
    private IFormTypeService formTypeService;
    @Autowired
    private IFormTypeGroupService formTypeGroupService;
    @Autowired
    private ISingleCompareDataEnumItemService enumItemService;

    @Override
    public void importEnumBBLXDefine(TaskImportContext importContext) throws Exception {
        DesignTaskDefine task = importContext.getTaskDefine();
        if (null == task) {
            throw new Exception("\u8be5\u4efb\u52a1\u4e0d\u5b58\u5728\uff0c\u5148\u5bfc\u5165!");
        }
        String tenantName = null;
        if (NpContextHolder.getContext() != null) {
            tenantName = NpContextHolder.getContext().getTenant();
        }
        double startPos = importContext.getCurProgress();
        importContext.getMapScheme().getEnumInfos().clear();
        Map enums = importContext.getParaInfo().getEnunList();
        EnumsItemModel bblxEnum = (EnumsItemModel)enums.get("BBLX");
        this.importEnumBBLXDefine(importContext, bblxEnum, null);
    }

    @Override
    public void importEnumBBLXDefine(TaskImportContext importContext, EnumsItemModel bblxEnum, CompareDataEnumDTO enumCompare) throws Exception {
        DesignDataScheme dataScheme = importContext.getDataScheme();
        DesignFormSchemeDefine formScheme = importContext.getSchemeInfoCache().getFormScheme();
        if (null == formScheme) {
            throw new Exception("\u8be5\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728\uff0c\u5148\u5bfc\u5165!");
        }
        String fileFlag = "";
        if (StringUtils.isNotEmpty((String)dataScheme.getPrefix())) {
            fileFlag = "_" + dataScheme.getPrefix().toUpperCase();
        }
        String bblxCode = "MD_BBLX" + fileFlag;
        String bblxTitle = bblxEnum.getTitle();
        if (enumCompare != null) {
            if (enumCompare.getUpdateType() == CompareUpdateType.UPDATE_NEW) {
                bblxCode = enumCompare.getNetCode();
                bblxTitle = enumCompare.getNetTitle();
            } else {
                if (enumCompare.getUpdateType() == CompareUpdateType.UPDATE_IGNORE) {
                    return;
                }
                if (enumCompare.getUpdateType() == CompareUpdateType.UPDATE_OVER) {
                    bblxCode = enumCompare.getNetCode();
                    bblxTitle = enumCompare.getNetTitle();
                } else {
                    if (enumCompare.getUpdateType() == CompareUpdateType.UPDATE_UNOVER) {
                        return;
                    }
                    if (enumCompare.getUpdateType() == CompareUpdateType.UPDATE_APPOINT) {
                        bblxCode = enumCompare.getNetCode();
                        bblxTitle = enumCompare.getNetTitle();
                    }
                }
            }
        }
        List groups = this.formTypeGroupService.queryByParentId("--");
        FormTypeGroupDefine reportGroup = null;
        FormTypeGroupDefine group = null;
        if (groups != null) {
            Map<String, FormTypeGroupDefine> groupDic = groups.stream().collect(Collectors.toMap(e -> e.getTitle(), e -> e, (v1, v2) -> v1));
            reportGroup = groupDic.get("\u62a5\u8868");
        }
        if (reportGroup == null) {
            reportGroup = this.formTypeGroupService.createFormTypeGroup();
            reportGroup.setCode("NR_GROUP");
            reportGroup.setTitle("\u62a5\u8868");
            reportGroup.setGroupId("--");
            this.formTypeGroupService.insertFormTypeGroup(reportGroup);
        } else {
            List groups2 = this.formTypeGroupService.queryByParentId(reportGroup.getId());
            if (groups2 != null) {
                Map<String, FormTypeGroupDefine> groupDic2 = groups2.stream().collect(Collectors.toMap(e -> e.getTitle(), e -> e, (v1, v2) -> v1));
                group = groupDic2.get(dataScheme.getTitle());
            }
        }
        if (group == null) {
            group = this.formTypeGroupService.createFormTypeGroup();
            String aCode = "NR_DT_" + dataScheme.getCode();
            if (aCode.length() > 20) {
                aCode = aCode.substring(0, 20);
            }
            group.setCode(aCode);
            group.setTitle(dataScheme.getTitle());
            group.setGroupId(reportGroup.getId());
            this.formTypeGroupService.insertFormTypeGroup(group);
        }
        ParaImportInfoResult enumItemsLog = null;
        HashMap<String, CompareDataEnumItemDTO> oldEnumItemDic = new HashMap<String, CompareDataEnumItemDTO>();
        if (enumCompare != null) {
            CompareDataEnumItemDTO enumItemQueryParam = new CompareDataEnumItemDTO();
            enumItemQueryParam.setInfoKey(enumCompare.getInfoKey());
            enumItemQueryParam.setEnumCompareKey(enumCompare.getKey());
            enumItemQueryParam.setDataType(CompareDataType.DATA_ENUMITEM);
            List<CompareDataEnumItemDTO> oldEnumItemList = this.enumItemService.list(enumItemQueryParam);
            for (CompareDataEnumItemDTO oldData : oldEnumItemList) {
                oldEnumItemDic.put(oldData.getSingleCode(), oldData);
            }
            if (enumCompare != null && importContext.getImportResult() != null) {
                enumItemsLog = importContext.getImportResult().getLogInfo(CompareDataType.DATA_ENUMITEM, "enumItems", "\u679a\u4e3e\u9879");
            }
        }
        FormTypeDefine formType = null;
        List formTypes = this.formTypeService.queryAllFormType();
        Map<String, FormTypeDefine> formTypeDic = formTypes.stream().collect(Collectors.toMap(e -> e.getCode(), e -> e, (v1, v2) -> v1));
        if (formTypeDic.containsKey(bblxCode)) {
            formType = formTypeDic.get(bblxCode);
            formType.setTitle(bblxTitle);
            this.formTypeService.updateFormType(formType);
        } else {
            formType = this.formTypeService.createFormType();
            formType.setCode(bblxCode);
            formType.setTitle(bblxTitle);
            formType.setDesc(dataScheme.getTitle() + bblxEnum.getTitle());
            formType.setOrder(OrderGenerator.newOrder());
            formType.setGroupId(group.getId());
            this.formTypeService.insertFormType(formType);
        }
        List bblxDatas = this.formTypeService.queryFormTypeDatas(bblxCode);
        Map<String, FormTypeDataDefine> bblxDataDic = bblxDatas.stream().collect(Collectors.toMap(e -> e.getCode(), e -> e, (v1, v2) -> v1));
        SingleFileEnumInfo mapEnum = this.UpdateEnumMap(importContext, bblxEnum, bblxCode, formType.getId());
        ArrayList<FormTypeDataDefine> insertdefines = new ArrayList<FormTypeDataDefine>();
        ArrayList<FormTypeDataDefine> updatedefines = new ArrayList<FormTypeDataDefine>();
        FMRepInfo fmdmRep = importContext.getParaInfo().getFmRepInfo();
        for (DataInfo dataInfo : bblxEnum.getItemDataList()) {
            FormTypeDataDefine dataDefine = null;
            String code = dataInfo.getCode();
            String netItemCode = dataInfo.getCode();
            String netItemTitle = dataInfo.getName();
            CompareDataEnumItemDTO enumItemCompare = null;
            if (oldEnumItemDic.containsKey(dataInfo.getCode())) {
                enumItemCompare = (CompareDataEnumItemDTO)oldEnumItemDic.get(dataInfo.getCode());
                if (enumItemCompare.getUpdateType() == CompareUpdateType.UPDATE_NEW) {
                    if (StringUtils.isNotEmpty((String)enumItemCompare.getNetCode())) {
                        netItemCode = enumItemCompare.getNetCode();
                    }
                    if (StringUtils.isNotEmpty((String)enumItemCompare.getNetCode())) {
                        netItemTitle = enumItemCompare.getNetTitle();
                    }
                } else if (enumItemCompare.getUpdateType() == CompareUpdateType.UPDATE_KEEP) {
                    if (StringUtils.isNotEmpty((String)enumItemCompare.getNetCode())) {
                        netItemCode = enumItemCompare.getNetCode();
                    }
                    if (StringUtils.isNotEmpty((String)enumItemCompare.getNetCode())) {
                        netItemTitle = enumItemCompare.getNetTitle();
                    }
                } else if (enumItemCompare.getUpdateType() == CompareUpdateType.UPDATE_RECODE) {
                    if (StringUtils.isNotEmpty((String)enumItemCompare.getNetCode())) {
                        netItemCode = enumItemCompare.getNetCode();
                    }
                    if (StringUtils.isNotEmpty((String)enumItemCompare.getNetCode())) {
                        netItemTitle = enumItemCompare.getNetTitle();
                    }
                } else if (enumItemCompare.getUpdateType() == CompareUpdateType.UPDATA_USENET) {
                    if (StringUtils.isNotEmpty((String)enumItemCompare.getNetCode())) {
                        netItemCode = enumItemCompare.getNetCode();
                    }
                    if (StringUtils.isNotEmpty((String)enumItemCompare.getNetCode())) {
                        netItemTitle = enumItemCompare.getNetTitle();
                    }
                } else if (enumItemCompare.getUpdateType() == CompareUpdateType.UPDATA_USESINGLE) {
                    // empty if block
                }
                ParaImportInfoResult enumItemLog = new ParaImportInfoResult();
                enumItemLog.copyForm(enumItemCompare);
                enumItemLog.setCode("BBLX_" + netItemCode);
                enumItemLog.setSuccess(true);
                enumItemLog.setParentCompareKey(enumCompare.getKey());
                enumItemsLog.addItem(enumItemLog);
            }
            if (StringUtils.isEmpty((String)netItemTitle)) {
                netItemTitle = netItemCode;
            }
            if (bblxDataDic.containsKey(netItemCode)) {
                dataDefine = bblxDataDic.get(netItemCode);
                dataDefine.setName(netItemTitle);
                updatedefines.add(dataDefine);
            } else {
                UnitNature unitNature = UnitNature.JCFHB;
                if (fmdmRep != null) {
                    TableTypeType tableType = fmdmRep.returnTableType(code);
                    unitNature = this.getUnitNature(tableType);
                }
                dataDefine = this.formTypeService.createFormTypeData();
                dataDefine.setCode(netItemCode);
                dataDefine.setName(netItemTitle);
                dataDefine.setUnitNatrue(unitNature);
                dataDefine.setFormTypeCode(bblxCode);
                insertdefines.add(dataDefine);
            }
            SingleFileEnumItem mapItem = mapEnum.getNewEnumItem();
            mapItem.setItemCode(dataInfo.getCode());
            mapItem.setItemTitle(dataInfo.getName());
            mapItem.setNetItemCode(netItemCode);
            mapItem.setNetItemTitle(netItemTitle);
            mapEnum.getEnumItems().add(mapItem);
        }
        if (insertdefines.size() > 0) {
            try {
                this.formTypeService.insertFormTypeData(insertdefines);
            }
            catch (JQException ex) {
                for (FormTypeDataDefine bblType : insertdefines) {
                    if (!enumItemsLog.getCodeFinder().containsKey("BBLX_" + bblType.getCode())) continue;
                    enumItemsLog.getCodeFinder().get("BBLX_" + bblType.getCode()).setSuccess(false);
                }
                log.error("\u5bfc\u5165\u62a5\u8868\u7c7b\u578b\u65b0\u589e\u5931\u8d25\uff1a" + ex.getMessage(), ex);
            }
        }
        if (updatedefines.size() > 0) {
            try {
                this.formTypeService.updateFormTypeData(updatedefines);
            }
            catch (JQException ex) {
                for (FormTypeDataDefine bblType : updatedefines) {
                    if (!enumItemsLog.getCodeFinder().containsKey("BBLX_" + bblType.getCode())) continue;
                    enumItemsLog.getCodeFinder().get("BBLX_" + bblType.getCode()).setSuccess(false);
                }
                log.error("\u5bfc\u5165\u62a5\u8868\u7c7b\u578b\u65b0\u589e\u5931\u8d25\uff1a" + ex.getMessage(), ex);
            }
        }
        log.info("\u62a5\u8868\u7c7b\u578b\u5bfc\u5165\u5b8c\u6210\uff1a" + bblxCode);
    }

    private SingleFileEnumInfo UpdateEnumMap(TaskImportContext importContext, EnumsItemModel singleEnum, String netEnumCode, String netEnumKey) {
        List enumList = importContext.getMapScheme().getEnumInfos();
        SingleFileEnumInfo mapEnum = importContext.getMapScheme().getNewEnumInfo();
        mapEnum.setEnumCode(singleEnum.getCode());
        mapEnum.setEnumTitle(singleEnum.getTitle());
        mapEnum.setNetTableCode(netEnumCode);
        mapEnum.setNetTableKey(netEnumKey);
        enumList.add(mapEnum);
        return mapEnum;
    }

    private UnitNature getUnitNature(TableTypeType tableType) {
        UnitNature unitNature = UnitNature.JCFHB;
        if (tableType == TableTypeType.TTT_JCFH) {
            unitNature = UnitNature.JCFHB;
        } else if (tableType == TableTypeType.TTT_JCHB) {
            unitNature = UnitNature.JCFHB;
        } else if (tableType == TableTypeType.TTT_JTCE) {
            unitNature = UnitNature.JTCEB;
        } else if (tableType == TableTypeType.TTT_JTHZ) {
            unitNature = UnitNature.JTHZB;
        } else if (tableType == TableTypeType.TTT_BZHZ) {
            unitNature = UnitNature.BZHZB;
        }
        return unitNature;
    }
}

