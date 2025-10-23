/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.mapping2.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.mapping2.bean.ZBMapping;
import com.jiuqi.nr.mapping2.common.MappingErrorEnum;
import com.jiuqi.nr.mapping2.common.ZBTypeEnum;
import com.jiuqi.nr.mapping2.service.JIOConfigService;
import com.jiuqi.nr.mapping2.service.ZBMappingImpExpService;
import com.jiuqi.nr.mapping2.service.ZBMappingService;
import com.jiuqi.nr.mapping2.web.dto.RegionDTO;
import com.jiuqi.nr.mapping2.web.dto.SaveMapping;
import com.jiuqi.nr.mapping2.web.vo.ReportFormTree;
import com.jiuqi.nr.mapping2.web.vo.Result;
import com.jiuqi.nr.mapping2.web.vo.ZBMappingVO;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/api/mapping2/zb"})
@Api(tags={"\u6307\u6807\u6620\u5c04"})
public class ZBMappingController {
    protected final Logger logger = LoggerFactory.getLogger(ZBMappingController.class);
    @Autowired
    private IRunTimeViewController runtimeCtrl;
    @Autowired
    private IDesignTimeViewController designTime;
    @Autowired
    private ZBMappingService zBMappingService;
    @Autowired
    private ZBMappingImpExpService zbMappingImpExpService;
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeCtrl;
    @Autowired
    private IFMDMAttributeService ifmdmAttributeService;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    JIOConfigService jioService;
    @Autowired
    IMappingSchemeService service;

    @GetMapping(value={"/query-form-tree/{taskKey}/{formSchemeKey}"})
    @ApiOperation(value="\u6784\u9020\u62a5\u8868\u6811\u5f62")
    public Result buildFormTree(@PathVariable String taskKey, @PathVariable String formSchemeKey) {
        Result res = new Result();
        TaskDefine task = this.runtimeCtrl.queryTaskDefine(taskKey);
        if (Objects.isNull(task)) {
            res.setSuccess(false);
            DesignTaskDefine designTask = this.designTime.queryTaskDefine(taskKey);
            if (Objects.isNull(designTask)) {
                res.setMessage(MappingErrorEnum.MAPPING_003.getMessage());
            } else {
                res.setMessage(MappingErrorEnum.MAPPING_002.getMessage());
            }
            return res;
        }
        FormSchemeDefine formScheme = this.runtimeCtrl.getFormScheme(formSchemeKey);
        if (Objects.isNull(formScheme)) {
            res.setSuccess(false);
            DesignFormSchemeDefine designForm = this.designTime.queryFormSchemeDefine(formSchemeKey);
            if (Objects.isNull(designForm)) {
                res.setMessage(MappingErrorEnum.MAPPING_0033.getMessage());
            } else {
                res.setMessage(MappingErrorEnum.MAPPING_0022.getMessage());
            }
            return res;
        }
        ArrayList tree = new ArrayList();
        AtomicBoolean flag = new AtomicBoolean(false);
        List<FormGroupDefine> groupDefines = this.runtimeCtrl.queryRootGroupsByFormScheme(formSchemeKey).stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
        groupDefines.forEach(item -> {
            ReportFormTree node = new ReportFormTree();
            node.setId(item.getKey());
            node.setTitle(item.getTitle());
            node.setCode(item.getCode());
            node.setType("NODE_TYPE_GROUP");
            List forms = new ArrayList();
            try {
                forms = this.runtimeCtrl.getAllFormsInGroup(item.getKey(), true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            node.setExpand(forms.size() > 0);
            ArrayList<ReportFormTree> childs = new ArrayList<ReportFormTree>();
            forms.forEach(f -> {
                ReportFormTree children = new ReportFormTree();
                children.setId(f.getKey());
                children.setTitle(f.getTitle());
                children.setCode(f.getFormCode());
                children.setType("NODE_TYPE_FORM");
                children.setExpand(false);
                children.setFormType(f.getFormType().getValue());
                childs.add(children);
            });
            node.setChildren(childs);
            if (childs.size() > 0 && !flag.get()) {
                ((ReportFormTree)childs.get(0)).setSelected(true);
                flag.set(true);
            }
            tree.add(node);
        });
        res.setSuccess(true);
        res.setData(tree);
        return res;
    }

    @GetMapping(value={"/query-form-data/{msKey}/{formSchemeKey}/{formCode}"})
    @ApiOperation(value="\u83b7\u53d6\u6620\u5c04\u548c\u8868\u6837\u6570\u636e")
    public Map<String, Object> getFormData(@PathVariable String msKey, @PathVariable String formSchemeKey, @PathVariable String formCode) throws Exception {
        FormDefine form = this.runtimeCtrl.queryFormByCodeInScheme(formSchemeKey, formCode);
        String formKey = form.getKey();
        boolean isFMDM = FormType.FORM_TYPE_NEWFMDM.equals((Object)form.getFormType());
        List<ZBMapping> zbMappings = this.zBMappingService.findByMSAndForm(msKey, formCode);
        ArrayList<FieldDefine> runtimeFields = new ArrayList();
        ArrayList<IFMDMAttribute> attributes = new ArrayList();
        if (isFMDM) {
            FormSchemeDefine formSchemeDefine = this.runtimeCtrl.getFormScheme(form.getFormScheme());
            TaskDefine taskDefine = this.runtimeCtrl.queryTaskDefine(formSchemeDefine.getTaskKey());
            FMDMAttributeDTO fMDMAttributeDTO = new FMDMAttributeDTO();
            fMDMAttributeDTO.setEntityId(taskDefine.getDw());
            fMDMAttributeDTO.setFormSchemeKey(formSchemeDefine.getKey());
            attributes = this.ifmdmAttributeService.list(fMDMAttributeDTO);
        } else {
            List keys = this.runtimeCtrl.getFieldKeysInForm(formKey);
            runtimeFields = this.dataRuntimeCtrl.queryFieldDefinesInRange((Collection)keys);
        }
        List allLinks = this.runTimeAuthViewController.getAllLinksInForm(formKey);
        List<DataLinkDefine> sortLinks = allLinks.stream().sorted((o1, o2) -> {
            if (o1.getPosX() > o2.getPosX()) {
                return 1;
            }
            if (o1.getPosX() == o2.getPosX()) {
                if (o1.getPosY() > o2.getPosY()) {
                    return 1;
                }
                if (o1.getPosY() == o2.getPosY()) {
                    return 0;
                }
            }
            return -1;
        }).collect(Collectors.toList());
        ArrayList<ZBMappingVO> formFields = new ArrayList<ZBMappingVO>();
        Grid2Data gridData = this.getGridData(formKey);
        String originFormDataStr = this.getFormDataString(gridData);
        boolean extendRegion = false;
        if (isFMDM) {
            this.buildFMDM(attributes, sortLinks, form, zbMappings, formFields);
        } else {
            extendRegion = this.buildForm(runtimeFields, sortLinks, form, zbMappings, formFields, gridData);
        }
        String fromDataStr = this.getFormDataString(gridData);
        HashMap<String, Object> map = new HashMap<String, Object>();
        List historyFields = zbMappings.stream().map(zbMapping -> {
            ZBMappingVO zbVO = new ZBMappingVO((ZBMapping)zbMapping);
            zbVO.setRegionCode(zbMapping.getRegionCode());
            zbVO.setIsHistory(true);
            return zbVO;
        }).collect(Collectors.toList());
        ArrayList sameZBMappings = new ArrayList();
        Map<String, List<ZBMappingVO>> rzGroups = formFields.stream().collect(Collectors.groupingBy(e -> e.getRegionCode() + "@" + e.getZbCode()));
        for (Map.Entry<String, List<ZBMappingVO>> entry : rzGroups.entrySet()) {
            if (entry.getValue().size() <= 1) continue;
            sameZBMappings.addAll(entry.getValue());
        }
        Set zbCodeSet = rzGroups.keySet().stream().map(e -> e.split("@")[1]).collect(Collectors.toSet());
        MappingScheme scheme = this.service.getSchemeByKey(msKey);
        map.put("originGriddata", originFormDataStr);
        map.put("griddata", fromDataStr);
        map.put("links", sortLinks);
        map.put("mappings", formFields);
        map.put("sameZBMappings", sameZBMappings);
        map.put("historyMappings", historyFields);
        map.put("jio", this.jioService.isJIOScheme(scheme));
        map.put("extendRegion", extendRegion);
        map.put("regionCode", zbCodeSet.size() < rzGroups.size());
        return map;
    }

    private String getFormDataString(Grid2Data gridData) throws JsonProcessingException {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
        module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule((Module)module);
        String fromDataStr = mapper.writeValueAsString((Object)gridData);
        return fromDataStr;
    }

    private boolean buildForm(List<FieldDefine> runtimeFields, List<DataLinkDefine> sortLinks, FormDefine form, List<ZBMapping> zbMappings, List<ZBMappingVO> formFields, Grid2Data gridData) throws Exception {
        List<String> enums;
        boolean needOffset = false;
        boolean rowFloat = true;
        int offset = 0;
        String tag = ";";
        List regions = this.runTimeAuthViewController.getAllRegionsInForm(form.getKey());
        Collections.sort(regions, (r1, r2) -> {
            if (r1.getRegionTop() != r2.getRegionTop()) {
                return r1.getRegionTop() - r2.getRegionTop();
            }
            return r1.getRegionLeft() - r2.getRegionLeft();
        });
        Date DATE_VERSION_FOR_ALL = new GregorianCalendar(9000, 0, 1).getTime();
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setQueryVersionDate(DATE_VERSION_FOR_ALL);
        entityQuery.setAuthorityOperations(AuthorityType.None);
        entityQuery.setIgnoreViewFilter(true);
        entityQuery.sorted(true);
        RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
        entityQuery.setEntityView((EntityViewDefine)entityViewDefine);
        ExecutorContext queryContext = new ExecutorContext(this.dataRuntimeCtrl);
        HashMap<String, RegionDTO> extendRegions = new HashMap<String, RegionDTO>();
        ArrayList<Object> extendRegionList = new ArrayList<Object>();
        for (DataRegionDefine r : regions) {
            RegionSettingDefine regionSetting;
            boolean bl = rowFloat = r.getRegionKind().getValue() == DataRegionKind.DATA_REGION_ROW_LIST.getValue();
            if (r.getRegionKind().getValue() != DataRegionKind.DATA_REGION_ROW_LIST.getValue() && r.getRegionKind().getValue() != DataRegionKind.DATA_REGION_COLUMN_LIST.getValue() || (regionSetting = this.runTimeAuthViewController.getRegionSetting(r.getKey())) == null || !StringUtils.hasText(regionSetting.getDictionaryFillLinks())) continue;
            needOffset = true;
            RegionDTO rDTO = new RegionDTO();
            extendRegions.put(r.getKey(), rDTO);
            extendRegionList.add(rDTO);
            rDTO.region = r;
            rDTO.rowFloat = rowFloat;
            rDTO.beginNum = rowFloat ? r.getRegionTop() : r.getRegionLeft();
            String[] fillLinks = regionSetting.getDictionaryFillLinks().split(tag);
            List fillFields = this.dataRuntimeCtrl.queryFieldDefines(Arrays.asList(fillLinks));
            int singleOffset = 0;
            enums = new ArrayList<String>();
            HashMap<String, Integer> extendZbs = new HashMap<String, Integer>();
            HashMap<String, List<IEntityRow>> extendEnums = new HashMap<String, List<IEntityRow>>();
            rDTO.extendZbs = extendZbs;
            rDTO.extendEnums = extendEnums;
            rDTO.enums = enums;
            int size = fillFields.size();
            for (int f = 0; f < size; ++f) {
                FieldDefine field = (FieldDefine)fillFields.get(f);
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(field.getEntityKey());
                if (entityDefine == null) continue;
                entityViewDefine.setEntityId(field.getEntityKey());
                IEntityTable entityTable = entityQuery.executeReader((IContext)queryContext);
                List rows = entityTable.getAllRows();
                extendEnums.put(entityDefine.getCode(), rows);
                enums.add(entityDefine.getCode());
                extendZbs.put(field.getCode(), f);
                if (singleOffset == 0) {
                    singleOffset = rows.size();
                    continue;
                }
                singleOffset *= rows.size();
            }
            rDTO.singleOffset = singleOffset;
            rDTO.offset = offset += --singleOffset;
        }
        Map<String, FieldDefine> zbMap = runtimeFields.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, zb -> zb, (key1, key2) -> key2));
        Map<String, ZBMapping> mappingMap = zbMappings.stream().collect(Collectors.toMap(m -> m.getZbCode() + "@" + m.getRegionCode(), m -> m, (oldValue, newValue) -> oldValue));
        HashSet<String> removeKeys = new HashSet<String>();
        for (DataLinkDefine link : sortLinks) {
            FieldDefine zb2 = zbMap.get(link.getLinkExpression());
            if (zb2 == null) {
                this.logger.warn("\u6307\u6807\u6620\u5c04\uff1a\uff1a\u62a5\u8868[" + form.getFormCode() + "]" + form.getTitle() + "\u7684\u94fe\u63a5[" + link.getKey() + "]\u65e0\u6cd5\u627e\u5230\u6307\u6807[" + link.getLinkExpression() + "]");
                continue;
            }
            if (extendRegions.containsKey(link.getRegionKey())) {
                RegionDTO regionDTO = (RegionDTO)extendRegions.get(link.getRegionKey());
                enums = regionDTO.enums;
                Map<String, List<IEntityRow>> extendEnums = regionDTO.extendEnums;
                int size = enums.size();
                ArrayList[] args = new ArrayList[size];
                for (int i = 0; i < size; ++i) {
                    args[i] = extendEnums.get(enums.get(i));
                }
                List<List<IEntityRow>> cartEnumList = this.cartesianProduct(args);
                for (int n = 0; n < cartEnumList.size(); ++n) {
                    List<IEntityRow> rows = cartEnumList.get(n);
                    ZBMappingVO infoVO = new ZBMappingVO();
                    String zbCode = null;
                    if (regionDTO.extendZbs.containsKey(zb2.getCode())) {
                        zbCode = zb2.getCode();
                        IEntityRow iEntityRow = rows.get(regionDTO.extendZbs.get(zb2.getCode()));
                        infoVO.setMapping(iEntityRow.getCode() + "|" + iEntityRow.getTitle());
                        infoVO.setZbType(ZBTypeEnum.ZB_EXTENDED_EXTEND_ENUM);
                    } else {
                        zbCode = zb2.getCode() + "@";
                        for (int i = 0; i < size; ++i) {
                            zbCode = zbCode + enums.get(i) + "|" + rows.get(i).getCode() + ";";
                        }
                        if (zbCode.endsWith(";")) {
                            zbCode = zbCode.substring(0, zbCode.length() - 1);
                        }
                        infoVO.setZbType(ZBTypeEnum.ZB_EXTENDED_EXTEND_OTHER);
                    }
                    this.buildXY(needOffset, rowFloat, extendRegions, link, infoVO);
                    if (rowFloat) {
                        infoVO.setRow(infoVO.getRow() + n);
                    } else {
                        infoVO.setCol(infoVO.getCol() + n);
                    }
                    ZBMapping mapping = mappingMap.get(zbCode + "@" + this.getRegionCode(link));
                    if (mapping != null && !regionDTO.extendZbs.containsKey(zb2.getCode())) {
                        BeanUtils.copyProperties(mapping, infoVO);
                        formFields.add(infoVO);
                        removeKeys.add(zbCode + "@" + this.getRegionCode(link));
                        continue;
                    }
                    infoVO.setZbCode(zbCode);
                    this.buildMappingAttribute(form, formFields, link, zb2, infoVO);
                }
                ZBMappingVO infoVO = new ZBMappingVO();
                infoVO.setCol(link.getPosX());
                infoVO.setRow(link.getPosY());
                infoVO.setZbType(ZBTypeEnum.ZB_FOLDED_EXTEND);
                ZBMapping mapping = mappingMap.get(zb2.getCode() + "@" + this.getRegionCode(link));
                if (mapping != null) {
                    BeanUtils.copyProperties(mapping, infoVO);
                    removeKeys.add(zb2.getCode() + "@" + this.getRegionCode(link));
                    formFields.add(infoVO);
                    continue;
                }
                infoVO.setZbCode(zb2.getCode());
                this.buildMappingAttribute(form, formFields, link, zb2, infoVO);
                continue;
            }
            ZBMappingVO infoVO = new ZBMappingVO();
            infoVO.setZbType(ZBTypeEnum.ZB_NORMAL);
            this.buildXY(needOffset, rowFloat, extendRegions, link, infoVO);
            ZBMapping mapping = mappingMap.get(zb2.getCode() + "@" + this.getRegionCode(link));
            if (mapping != null) {
                BeanUtils.copyProperties(mapping, infoVO);
                formFields.add(infoVO);
                removeKeys.add(zb2.getCode() + "@" + this.getRegionCode(link));
                continue;
            }
            infoVO.setZbCode(zb2.getCode());
            this.buildMappingAttribute(form, formFields, link, zb2, infoVO);
        }
        if (needOffset) {
            for (int i = extendRegionList.size() - 1; i >= 0; --i) {
                RegionDTO dto = (RegionDTO)extendRegionList.get(i);
                if (rowFloat) {
                    gridData.insertRows(dto.beginNum, dto.singleOffset, dto.beginNum);
                    continue;
                }
                gridData.insertColumns(dto.beginNum, dto.singleOffset, dto.beginNum);
            }
        }
        for (String key : removeKeys) {
            mappingMap.remove(key);
        }
        zbMappings.clear();
        zbMappings.addAll(mappingMap.values().stream().collect(Collectors.toList()));
        return extendRegions.size() > 0;
    }

    private void buildMappingAttribute(FormDefine form, List<ZBMappingVO> formFields, DataLinkDefine link, FieldDefine zb, ZBMappingVO infoVO) throws Exception {
        this.setRegionCode(link, infoVO);
        TableDefine tableDefine = this.dataRuntimeCtrl.queryTableDefine(zb.getOwnerTableKey());
        if (tableDefine != null) {
            infoVO.setTable(tableDefine.getCode());
        } else {
            infoVO.setTable(zb.getOwnerTableKey());
        }
        infoVO.setForm(form.getFormCode());
        formFields.add(infoVO);
    }

    private void setRegionCode(DataLinkDefine link, ZBMappingVO infoVO) {
        DataRegionDefine regionDefine = this.runtimeCtrl.queryDataRegionDefine(link.getRegionKey());
        infoVO.setRegionCode(regionDefine.getCode());
    }

    private String getRegionCode(DataLinkDefine link) {
        DataRegionDefine regionDefine = this.runtimeCtrl.queryDataRegionDefine(link.getRegionKey());
        return regionDefine.getCode();
    }

    private List<List<IEntityRow>> cartesianProduct(List<IEntityRow> ... lists) {
        ArrayList<List<IEntityRow>> resultList = new ArrayList<List<IEntityRow>>();
        if (lists == null || lists.length == 0) {
            resultList.add(new ArrayList());
            return resultList;
        }
        if (lists.length == 1) {
            for (IEntityRow element : lists[0]) {
                resultList.add(Arrays.asList(element));
            }
            return resultList;
        }
        List<IEntityRow> firstList = lists[0];
        List<List<IEntityRow>> remainingLists = this.cartesianProduct(Arrays.copyOfRange(lists, 1, lists.length));
        for (IEntityRow firstElement : firstList) {
            for (List<IEntityRow> remainingPair : remainingLists) {
                ArrayList<IEntityRow> pair = new ArrayList<IEntityRow>(remainingPair);
                pair.add(0, firstElement);
                resultList.add(pair);
            }
        }
        return resultList;
    }

    private void buildFMDM(List<IFMDMAttribute> finalAttributes, List<DataLinkDefine> sortLinks, FormDefine form, List<ZBMapping> zbMappings, List<ZBMappingVO> formFields) {
        for (DataLinkDefine link : sortLinks) {
            ZBMappingVO infoVO = new ZBMappingVO();
            infoVO.setCol(link.getPosX());
            infoVO.setRow(link.getPosY());
            this.setRegionCode(link, infoVO);
            Optional<IFMDMAttribute> findAttribute = null;
            findAttribute = link.getType().getValue() == DataLinkType.DATA_LINK_TYPE_FMDM.getValue() ? finalAttributes.stream().filter(e -> e.getCode().equals(link.getLinkExpression())).findFirst() : finalAttributes.stream().filter(e -> e.getZBKey().equals(link.getLinkExpression())).findFirst();
            if (!findAttribute.isPresent()) continue;
            IFMDMAttribute attribute = findAttribute.get();
            Optional<ZBMapping> zbMapping = zbMappings.stream().filter(e -> e.getZbCode().equals(attribute.getCode())).findFirst();
            if (zbMapping.isPresent()) {
                zbMappings.remove(zbMapping.get());
                BeanUtils.copyProperties(zbMapping.get(), infoVO);
                formFields.add(infoVO);
                continue;
            }
            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(attribute.getTableID());
            String tableCode = tableModelDefine != null ? tableModelDefine.getCode() : attribute.getTableID();
            infoVO.setZbCode(attribute.getCode());
            infoVO.setTable(tableCode);
            infoVO.setForm(form.getFormCode());
            formFields.add(infoVO);
        }
    }

    private void buildXY(boolean needOffset, boolean rowFloat, Map<String, RegionDTO> extendRegions, DataLinkDefine link, ZBMappingVO infoVO) {
        int x = link.getPosX();
        int y = link.getPosY();
        if (needOffset) {
            int comparaNum = y;
            if (!rowFloat) {
                comparaNum = x;
            }
            for (Map.Entry<String, RegionDTO> entry : extendRegions.entrySet()) {
                String regionKey = entry.getKey();
                RegionDTO dto = entry.getValue();
                if (link.getRegionKey().equals(regionKey) || comparaNum <= dto.beginNum) continue;
                if (rowFloat) {
                    y += dto.offset;
                    continue;
                }
                x += dto.offset;
            }
        }
        infoVO.setCol(x);
        infoVO.setRow(y);
    }

    @PostMapping(value={"/save"})
    @ApiOperation(value="\u4fdd\u5b58\u6307\u6807\u6620\u5c04")
    public void save(@Valid @RequestBody SaveMapping saveMapping) throws Exception {
        Map<String, List<String>> invalidMapping = saveMapping.getInvalidMapping();
        if (!CollectionUtils.isEmpty(invalidMapping)) {
            ArrayList<String> invalidList = new ArrayList<String>();
            for (List<String> list : invalidMapping.values()) {
                if (CollectionUtils.isEmpty(list)) continue;
                invalidList.addAll(list);
            }
            this.zBMappingService.deleteByKeys(invalidList);
        }
        this.zBMappingService.save(saveMapping.getMappingSchemeId(), saveMapping.getFormCode(), saveMapping.getZbMappings());
    }

    @GetMapping(value={"/clear/{msKey}/{formCode}"})
    @ApiOperation(value="\u6e05\u7a7a\u5f53\u524d\u8868\u7684\u6620\u5c04")
    public void getFormData(@PathVariable String msKey, @PathVariable String formCode) {
        this.zBMappingService.clearByMSAndForm(msKey, formCode);
    }

    @PostMapping(value={"/import/{msKey}/{formSchemeKey}/{formCode}"})
    @ApiOperation(value="\u5bfc\u5165\u6570\u636e")
    public Result importData(@PathVariable String msKey, @PathVariable String formSchemeKey, @PathVariable String formCode, @RequestParam(value="file") MultipartFile file, HttpServletRequest request) {
        return this.zbMappingImpExpService.importMapping(msKey, formSchemeKey, formCode, file);
    }

    @PostMapping(value={"/export/{msKey}/{formSchemeKey}"})
    @ApiOperation(value="\u5bfc\u51faexcel")
    public void export(@PathVariable String msKey, @PathVariable String formSchemeKey, @RequestBody List<String> formCodes, HttpServletResponse response) throws Exception {
        this.zbMappingImpExpService.export(msKey, formSchemeKey, formCodes, response);
    }

    @PostMapping(value={"/check/{formSchemeKey}"})
    @ApiOperation(value="\u6821\u9a8c\u5f53\u524d\u65b9\u6848\u4e0b\u5176\u4ed6\u8868\u662f\u5426\u5b58\u5728\u6307\u6807\u6620\u5c04")
    public Map checkMapping(@PathVariable String formSchemeKey, @Valid @RequestBody ZBMapping mapping) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        formSchemeKey = HtmlUtils.cleanUrlXSS((String)formSchemeKey);
        boolean sameMapping = false;
        List<ZBMapping> list = this.zBMappingService.findByMSAndMapping(mapping.getMsKey(), mapping.getMapping());
        ArrayList<String> invalidList = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        block0: for (ZBMapping zm : list) {
            String formCode = zm.getForm();
            if (mapping.getForm().equals(formCode) || mapping.getTable().equals(zm.getTable()) && mapping.getZbCode().equals(zm.getZbCode())) continue;
            FormDefine formDefine = this.runtimeCtrl.queryFormByCodeInScheme(formSchemeKey, formCode);
            if (formDefine == null) {
                invalidList.add(zm.getKey());
                continue;
            }
            DataRegionDefine dataRegion = this.runtimeCtrl.getDataRegion(zm.getRegionCode(), formDefine.getKey(), formSchemeKey);
            if (dataRegion == null) {
                invalidList.add(zm.getKey());
                continue;
            }
            String regionKey = dataRegion.getKey();
            List keys = this.runtimeCtrl.getFieldKeysInRegion(regionKey);
            for (String key : keys) {
                FieldDefine fieldDefine = this.runtimeCtrl.queryFieldDefine(key);
                if (fieldDefine == null || !fieldDefine.getCode().equals(zm.getZbCode())) continue;
                sb.append("\u4e0e\u62a5\u8868\uff1a").append(formDefine.getTitle()).append("\u3010").append(formDefine.getFormCode()).append("\u3011\u4e0a\u7684\u6307\u6807\uff1a").append(fieldDefine.getTitle()).append("\u3010").append(fieldDefine.getCode()).append("\u3011\u6620\u5c04\u503c\u76f8\u540c");
                if (sameMapping) break block0;
                sameMapping = true;
                break block0;
            }
            invalidList.add(zm.getKey());
        }
        map.put("sameMapping", sameMapping);
        map.put("msg", sb.toString());
        map.put("invalidList", invalidList);
        return map;
    }

    @PostMapping(value={"/batch/check/{formSchemeKey}"})
    @ApiOperation(value="\u6821\u9a8c\u5f53\u524d\u65b9\u6848\u4e0b\u5176\u4ed6\u8868\u662f\u5426\u5b58\u5728\u6307\u6807\u6620\u5c04")
    public Map<String, Object> batchCheckMapping(@PathVariable String formSchemeKey, @RequestBody List<ZBMappingVO> mappings) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (CollectionUtils.isEmpty(mappings) || mappings.get(0) == null) {
            return map;
        }
        StringBuilder sb = new StringBuilder();
        boolean sameMapping = false;
        HashMap<String, List<String>> invalidMap = new HashMap<String, List<String>>();
        HashMap cache = new HashMap();
        HashMap<String, FormDefine> cFormMap = new HashMap<String, FormDefine>();
        List<ZBMapping> list = this.zBMappingService.findByMSAndMapping(mappings.get(0).getMsKey(), mappings.stream().map(e -> e.getMapping()).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(list)) {
            map.put("sameMapping", sameMapping);
            map.put("pasteMappings", mappings);
            return map;
        }
        Map<String, List<ZBMapping>> mMap = list.stream().collect(Collectors.groupingBy(ZBMapping::getMapping));
        Iterator<ZBMappingVO> iterator = mappings.iterator();
        while (iterator.hasNext()) {
            ZBMappingVO zmv = iterator.next();
            String form = zmv.getForm();
            String table = zmv.getTable();
            String zb = zmv.getZbCode();
            String key = table + "@" + zb;
            List<ZBMapping> sameList = mMap.get(zmv.getMapping());
            if (CollectionUtils.isEmpty(sameList)) continue;
            for (ZBMapping szm : sameList) {
                HashMap<String, String> zbCache;
                if (form.equals(szm.getForm()) || table.equals(szm.getTable()) && zb.equals(szm.getZbCode())) continue;
                HashMap regionCache = (HashMap)cache.get(szm.getForm());
                if (regionCache == null) {
                    FormDefine formDefine = this.runtimeCtrl.queryFormByCodeInScheme(formSchemeKey, szm.getForm());
                    if (formDefine == null) {
                        this.buildInvalidMap(invalidMap, key, szm);
                        cache.put(szm.getForm(), new HashMap());
                        continue;
                    }
                    regionCache = new HashMap();
                    cache.put(szm.getForm(), regionCache);
                    cFormMap.put(formDefine.getFormCode(), formDefine);
                }
                if ((zbCache = (HashMap<String, String>)regionCache.get(szm.getRegionCode())) == null) {
                    if (cFormMap.get(szm.getForm()) == null) {
                        this.buildInvalidMap(invalidMap, key, szm);
                        continue;
                    }
                    DataRegionDefine dataRegion = this.runtimeCtrl.getDataRegion(szm.getRegionCode(), ((FormDefine)cFormMap.get(szm.getForm())).getKey(), formSchemeKey);
                    if (dataRegion == null) {
                        this.buildInvalidMap(invalidMap, key, szm);
                        regionCache.put(szm.getRegionCode(), new HashMap());
                        continue;
                    }
                    zbCache = new HashMap<String, String>();
                    regionCache.put(szm.getRegionCode(), zbCache);
                    List keys = this.runtimeCtrl.getFieldKeysInRegion(dataRegion.getKey());
                    for (String key1 : keys) {
                        FieldDefine fieldDefine = this.runtimeCtrl.queryFieldDefine(key1);
                        if (fieldDefine == null) continue;
                        zbCache.put(fieldDefine.getCode(), fieldDefine.getTitle());
                    }
                }
                if (zbCache.containsKey(szm.getZbCode())) {
                    sb.append("\u4e0e\u62a5\u8868\uff1a").append(((FormDefine)cFormMap.get(szm.getForm())).getTitle()).append("\u3010").append(((FormDefine)cFormMap.get(szm.getForm())).getFormCode()).append("\u3011\u4e0a\u7684\u6307\u6807\uff1a").append((String)zbCache.get(szm.getZbCode())).append("\u3010").append(szm.getZbCode()).append("\u3011\u6620\u5c04\u503c\u76f8\u540c").append("\n");
                    iterator.remove();
                    if (sameMapping) continue;
                    sameMapping = true;
                    continue;
                }
                this.buildInvalidMap(invalidMap, key, szm);
            }
        }
        map.put("sameMapping", sameMapping);
        map.put("msg", sb.toString());
        map.put("pasteMappings", mappings);
        map.put("invalidMap", invalidMap);
        return map;
    }

    private void buildInvalidMap(Map<String, List<String>> invalidMap, String key, ZBMapping szm) {
        if (!invalidMap.containsKey(key)) {
            invalidMap.put(key, new ArrayList());
        }
        invalidMap.get(key).add(szm.getKey());
    }

    @Deprecated
    private boolean fmdmField(boolean isFMDM, DataLinkDefine link) {
        return isFMDM && link.getType() == DataLinkType.DATA_LINK_TYPE_FMDM;
    }

    private Grid2Data getGridData(String formKey) {
        BigDataDefine formData = this.runTimeAuthViewController.getReportDataFromForm(formKey);
        if (formData != null) {
            return Grid2Data.bytesToGrid((byte[])formData.getData());
        }
        Grid2Data griddata = new Grid2Data();
        griddata.setRowCount(10);
        griddata.setColumnCount(10);
        return griddata;
    }
}

