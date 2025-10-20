/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.nrextracteditctrl.dto.FormSchemeParamDTO
 *  com.jiuqi.gcreport.nrextracteditctrl.dto.FormSelection
 *  com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlCondi
 *  com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlDTO
 *  com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlSaveDTO
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.dataentry.bean.DataEntryInitParam
 *  com.jiuqi.nr.dataentry.bean.DataentryFormSchemeParam
 *  com.jiuqi.nr.dataentry.paramInfo.FormSchemeData
 *  com.jiuqi.nr.dataentry.tree.FormTree
 *  com.jiuqi.nr.dataentry.web.DataEntryExecuteController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  org.activiti.engine.impl.util.CollectionUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.nrextracteditctrl.service.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.nrextracteditctrl.dao.NrExtractEditCtrlDao;
import com.jiuqi.gcreport.nrextracteditctrl.dao.NrExtractEditCtrlItemDao;
import com.jiuqi.gcreport.nrextracteditctrl.dto.FormSchemeParamDTO;
import com.jiuqi.gcreport.nrextracteditctrl.dto.FormSelection;
import com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlCondi;
import com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlDTO;
import com.jiuqi.gcreport.nrextracteditctrl.dto.NrExtractEditCtrlSaveDTO;
import com.jiuqi.gcreport.nrextracteditctrl.entity.NrExtractEditCtrlEO;
import com.jiuqi.gcreport.nrextracteditctrl.entity.NrExtractEditCtrlItemEO;
import com.jiuqi.gcreport.nrextracteditctrl.service.NrExtractEditCtrlService;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.dataentry.bean.DataEntryInitParam;
import com.jiuqi.nr.dataentry.bean.DataentryFormSchemeParam;
import com.jiuqi.nr.dataentry.paramInfo.FormSchemeData;
import com.jiuqi.nr.dataentry.tree.FormTree;
import com.jiuqi.nr.dataentry.web.DataEntryExecuteController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.activiti.engine.impl.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NrExtractEditCtrlServiceImpl
implements NrExtractEditCtrlService {
    private static final Logger logger = LoggerFactory.getLogger(NrExtractEditCtrlServiceImpl.class);
    public static final String DIMENSION_MD_ORG = "MD_ORG";
    @Autowired
    private NrExtractEditCtrlDao nrExtractEditCtrlDao;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private IRunTimeViewController runTimeController;
    private final NedisCache nrExtractEditCache;
    @Autowired
    private DataEntryExecuteController dataEntryExecuteController;
    @Autowired
    private NrExtractEditCtrlItemDao nrExtractEditCtrlItemDao;
    private Map<String, String> formLinkMap = new HashMap<String, String>();

    public NrExtractEditCtrlServiceImpl(@Autowired NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("GC_NREXTRACTEDITCTRL_MANAGE");
        this.nrExtractEditCache = cacheManager.getCache("GC_NREXTRACTEDITCTRL");
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(NrExtractEditCtrlSaveDTO saveDTO) {
        NrExtractEditCtrlServiceImpl nrExtractEditCtrlService = (NrExtractEditCtrlServiceImpl)BeanUtil.getBean(NrExtractEditCtrlServiceImpl.class);
        NrExtractEditCtrlEO nrExtractEditCtrlEO = this.convertSaveDTOToEO(saveDTO);
        String uuidStr = UUIDUtils.newUUIDStr();
        nrExtractEditCtrlEO.setId(uuidStr);
        saveDTO.setId(uuidStr);
        nrExtractEditCtrlEO.setStopFlag(0);
        this.nrExtractEditCtrlDao.save(nrExtractEditCtrlEO);
        nrExtractEditCtrlService.editNrExtractionCtrlItem(saveDTO);
        this.nrExtractEditCtrlCacheClear();
    }

    public void editNrExtractionCtrlItem(NrExtractEditCtrlSaveDTO saveDTO) {
        boolean selectFormFlag = saveDTO.isSelectFormFlag();
        boolean selectLinkFlag = saveDTO.isSelectLinkFlag();
        if (selectFormFlag) {
            if (saveDTO.isAllFormFlag()) {
                NrExtractEditCtrlItemEO nrExtractEditCtrlItemEO = this.convertItemSaveDTOToEO(saveDTO, "*", "*");
                this.nrExtractEditCtrlItemDao.save(nrExtractEditCtrlItemEO);
            } else {
                List forms = saveDTO.getForms();
                for (String string : forms) {
                    NrExtractEditCtrlItemEO nrExtractEditCtrlItemEO = this.convertItemSaveDTOToEO(saveDTO, string, "*");
                    this.nrExtractEditCtrlItemDao.save(nrExtractEditCtrlItemEO);
                }
            }
        }
        if (selectLinkFlag) {
            Map links = saveDTO.getLinksMap();
            for (Map.Entry entry : links.entrySet()) {
                String formkey = (String)entry.getKey();
                List link = (List)entry.getValue();
                for (String linkkey : link) {
                    NrExtractEditCtrlItemEO nrExtractEditCtrlItemEO = this.convertItemSaveDTOToEO(saveDTO, formkey, linkkey);
                    this.nrExtractEditCtrlItemDao.save(nrExtractEditCtrlItemEO);
                }
            }
        }
    }

    private NrExtractEditCtrlEO convertSaveDTOToEO(NrExtractEditCtrlSaveDTO saveDTO) {
        NrExtractEditCtrlEO editCtrlEO = new NrExtractEditCtrlEO();
        editCtrlEO.setTaskKey(saveDTO.getTaskKey());
        editCtrlEO.setFormSchemeKey(saveDTO.getFormSchemeKey());
        boolean isAllOrg = saveDTO.isAllOrgFlag();
        if (isAllOrg) {
            editCtrlEO.setUnitCode("*");
        } else {
            Assert.isNotEmpty((Collection)saveDTO.getOrgs(), (String)"\u81ea\u5b9a\u4e49\u9009\u62e9\u5355\u4f4d\u81f3\u5c11\u9009\u4e2d\u4e00\u4e2a\u5355\u4f4d", (Object[])new Object[0]);
            List<OrgDO> orgDOS = this.queryOrgNames(saveDTO.getOrgs(), saveDTO.getOrgTypeCode(), saveDTO.getOrgTypeLastValidTime());
            List orgCodeList = orgDOS.stream().map(OrgDO::getCode).collect(Collectors.toList());
            String unitCodes = String.join((CharSequence)",", orgCodeList);
            editCtrlEO.setUnitCode(unitCodes);
        }
        editCtrlEO.setCreateTime(new Date());
        return editCtrlEO;
    }

    private NrExtractEditCtrlItemEO convertItemSaveDTOToEO(NrExtractEditCtrlSaveDTO saveDTO, String formkey, String linkkey) {
        NrExtractEditCtrlItemEO editCtrlItemEO = new NrExtractEditCtrlItemEO();
        editCtrlItemEO.setId(UUIDUtils.newUUIDStr());
        editCtrlItemEO.setFormKey(formkey);
        editCtrlItemEO.setLinkKey(linkkey);
        editCtrlItemEO.setEditCtrlConfId(saveDTO.getId());
        return editCtrlItemEO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void update(NrExtractEditCtrlSaveDTO saveDTO) {
        NrExtractEditCtrlEO nrExtractEditCtrlEO = this.convertSaveDTOToEO(saveDTO);
        nrExtractEditCtrlEO.setId(saveDTO.getId());
        nrExtractEditCtrlEO.setStopFlag(0);
        this.nrExtractEditCtrlDao.update(nrExtractEditCtrlEO);
        this.nrExtractEditCtrlItemDao.delete(nrExtractEditCtrlEO.getId());
        this.editNrExtractionCtrlItem(saveDTO);
        this.nrExtractEditCtrlCacheClear();
    }

    private List<OrgDO> queryOrgNames(List<String> orgCodes, String orgType, String formatPeriodStr) {
        Date period = null;
        if (!StringUtils.isEmpty((String)formatPeriodStr)) {
            period = PeriodUtils.getStartDateOfPeriod((String)formatPeriodStr, (boolean)false);
        }
        OrgDTO orgParam = new OrgDTO();
        orgParam.setOrgCodes(orgCodes);
        orgParam.setSyncOrgBaseInfo(Boolean.valueOf(false));
        orgParam.setVersionDate(period);
        orgParam.setRecoveryflag(Integer.valueOf(-1));
        orgParam.setExtInfo(new HashMap());
        orgParam.setStopflag(Integer.valueOf(-1));
        orgParam.setForceUpdateHistoryVersionData(Boolean.valueOf(true));
        orgParam.setCategoryname(orgType);
        orgParam.setAuthType(OrgDataOption.AuthType.NONE);
        orgParam.setQueryDataStructure(OrgDataOption.QueryDataStructure.BASIC);
        PageVO page = this.orgDataClient.list(orgParam);
        if (Objects.isNull(page) || page.getTotal() <= 0) {
            logger.error("\u6839\u636e\u5355\u4f4d\u53c2\u6570{}\u672a\u83b7\u53d6\u5230\u5355\u4f4d\u5bf9\u8c61", (Object)orgParam);
            return new ArrayList<OrgDO>();
        }
        return page.getRows();
    }

    @Override
    public void delete(String id) {
        this.nrExtractEditCtrlDao.delete(id);
        this.nrExtractEditCtrlItemDao.delete(id);
        this.nrExtractEditCtrlCacheClear();
    }

    @Override
    public void batchDelete(List<String> ids) {
        for (String id : ids) {
            this.delete(id);
        }
        this.nrExtractEditCtrlCacheClear();
    }

    @Override
    public void stop(String id) {
        this.nrExtractEditCtrlDao.stop(id);
        this.nrExtractEditCtrlCacheClear();
    }

    @Override
    public void batchStop(List<String> ids) {
        for (String id : ids) {
            this.stop(id);
        }
        this.nrExtractEditCtrlCacheClear();
    }

    @Override
    public void start(String id) {
        this.nrExtractEditCtrlDao.start(id);
        this.nrExtractEditCtrlCacheClear();
    }

    @Override
    public void batchStart(List<String> ids) {
        for (String id : ids) {
            this.start(id);
        }
        this.nrExtractEditCtrlCacheClear();
    }

    @Override
    public List<NrExtractEditCtrlDTO> queryAll() {
        return (List)this.nrExtractEditCache.get("ALL_NR_EXTRACT_EDIT_CTRL", this::getAllNrExtractEditCtrlDTO);
    }

    @Override
    public PageInfo<NrExtractEditCtrlDTO> queryPage(Integer page, Integer size) {
        List nrExtractEditCtrlDTOS = (List)this.nrExtractEditCache.get("ALL_NR_EXTRACT_EDIT_CTRL", this::getAllNrExtractEditCtrlDTO);
        if (CollectionUtils.isEmpty((Collection)nrExtractEditCtrlDTOS)) {
            return PageInfo.empty();
        }
        return PageInfo.of((List)nrExtractEditCtrlDTOS, (int)page, (int)size, (int)nrExtractEditCtrlDTOS.size());
    }

    private List<NrExtractEditCtrlDTO> getAllNrExtractEditCtrlDTO() {
        List<NrExtractEditCtrlEO> editCtrlEOList = this.nrExtractEditCtrlDao.queryAll();
        LinkedList<NrExtractEditCtrlDTO> accessDTOList = new LinkedList<NrExtractEditCtrlDTO>();
        Map<String, List<NrExtractEditCtrlEO>> groups = editCtrlEOList.stream().collect(Collectors.groupingBy(NrExtractEditCtrlEO::getId));
        for (Map.Entry<String, List<NrExtractEditCtrlEO>> entry : groups.entrySet()) {
            List<NrExtractEditCtrlEO> values = entry.getValue();
            NrExtractEditCtrlDTO nrExtractEditCtrlDTO = this.convertEditCtrlEO2DTO(values);
            if (!Objects.nonNull(nrExtractEditCtrlDTO)) continue;
            accessDTOList.add(nrExtractEditCtrlDTO);
        }
        return accessDTOList;
    }

    private NrExtractEditCtrlDTO convertEditCtrlEO2DTO(List<NrExtractEditCtrlEO> values) {
        if (CollectionUtils.isEmpty(values)) {
            return null;
        }
        NrExtractEditCtrlEO editCtrlEO = values.get(0);
        NrExtractEditCtrlDTO editCtrlDTO = new NrExtractEditCtrlDTO();
        BeanUtils.copyProperties(editCtrlEO, editCtrlDTO);
        TaskDefine taskDefine = this.runTimeController.queryTaskDefine(editCtrlEO.getTaskKey());
        if (Objects.isNull(taskDefine)) {
            logger.warn("\u8d22\u52a1\u63d0\u53d6\u6743\u9650\u63a7\u5236-\u6839\u636e\u4efb\u52a1key\u3010{}\u3011\u672a\u67e5\u8be2\u5230\u62a5\u8868\u65b9\u6848\u5bf9\u8c61", (Object)editCtrlEO.getTaskKey());
            editCtrlDTO.setTaskTitle("\u4efb\u52a1\u4e0d\u5b58\u5728");
            this.stop(editCtrlEO.getId());
            return editCtrlDTO;
        }
        editCtrlDTO.setTaskTitle(taskDefine.getTitle());
        String dw = taskDefine.getDw();
        editCtrlDTO.setOrgTypeCode(dw.substring(0, dw.indexOf("@")));
        DataEntryInitParam dataEntryInitParam = new DataEntryInitParam();
        dataEntryInitParam.setTaskKey(editCtrlEO.getTaskKey());
        List<FormSchemeParamDTO> schemeParamDTOS = this.queryAllFormSchemeByTaskId(editCtrlEO.getTaskKey());
        Map<String, FormSchemeParamDTO> formSchemeResultMap = schemeParamDTOS.stream().collect(Collectors.toMap(FormSchemeParamDTO::getKey, fsr -> fsr, (fsr1, fsr2) -> fsr1));
        FormSchemeParamDTO formSchemeParamDTO = formSchemeResultMap.get(editCtrlEO.getFormSchemeKey());
        if (Objects.isNull(formSchemeParamDTO)) {
            logger.warn("\u8d22\u52a1\u63d0\u53d6\u6743\u9650\u63a7\u5236-\u6839\u636e\u62a5\u8868\u65b9\u6848key\u3010{}\u3011\u672a\u67e5\u8be2\u5230\u62a5\u8868\u65b9\u6848\u5bf9\u8c61", (Object)editCtrlEO.getFormSchemeKey());
            editCtrlDTO.setFormSchemeTitle("\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728");
            this.stop(editCtrlEO.getId());
            this.buildUnitParam(editCtrlDTO, "", editCtrlEO.getUnitCode());
            this.buildFormAndLinkParam(editCtrlDTO, values);
            return editCtrlDTO;
        }
        editCtrlDTO.setFormSchemeTitle(formSchemeParamDTO.getTitle());
        editCtrlDTO.setOrgTypeLastValidTime(formSchemeParamDTO.getVaildPeriod());
        this.buildUnitParam(editCtrlDTO, formSchemeParamDTO.getVaildPeriod(), editCtrlEO.getUnitCode());
        this.buildFormAndLinkParam(editCtrlDTO, values);
        return editCtrlDTO;
    }

    private void buildUnitParam(NrExtractEditCtrlDTO extractEditCtrlDTO, String orgLastValidTime, String unitCodes) {
        if (StringUtils.isEmpty((String)unitCodes)) {
            logger.warn("\u8d22\u52a1\u63d0\u53d6\u6743\u9650\u63a7\u5236-\u62a5\u8868\u53d6\u6570\u6307\u6807\u7f16\u8f91\u6743\u9650\u5355\u4f4d\u914d\u7f6e\u5f02\u5e38\uff0c\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a");
            extractEditCtrlDTO.setAllOrgFlag(false);
            return;
        }
        if ("*".equals(unitCodes)) {
            extractEditCtrlDTO.setAllOrgFlag(true);
            extractEditCtrlDTO.setOrgNames("\u5168\u90e8");
            return;
        }
        List<String> unitCodeList = Arrays.stream(unitCodes.split(",")).collect(Collectors.toList());
        if (StringUtils.isEmpty((String)orgLastValidTime)) {
            extractEditCtrlDTO.setAllOrgFlag(false);
            extractEditCtrlDTO.setOrgs(unitCodeList);
            extractEditCtrlDTO.setOrgNames(unitCodes);
            return;
        }
        List<OrgDO> orgDOS = this.queryOrgNames(unitCodeList, extractEditCtrlDTO.getOrgTypeCode(), orgLastValidTime);
        List unitNames = orgDOS.stream().map(OrgDO::getName).collect(Collectors.toList());
        String unitNameStr = String.join((CharSequence)",", unitNames);
        extractEditCtrlDTO.setAllOrgFlag(false);
        extractEditCtrlDTO.setOrgs(unitCodeList);
        extractEditCtrlDTO.setOrgNames(unitNameStr);
    }

    private void buildFormAndLinkParam(NrExtractEditCtrlDTO extractEditCtrlDTO, List<NrExtractEditCtrlEO> values) {
        List<NrExtractEditCtrlEO> notNullLinks;
        extractEditCtrlDTO.setForms(new ArrayList());
        extractEditCtrlDTO.setLinksList(new ArrayList());
        List<Object> formKeyList = new ArrayList();
        ArrayList<NrExtractEditCtrlEO> linkKeyList = new ArrayList<NrExtractEditCtrlEO>();
        List allFormList = values.stream().filter(v -> "*".equals(v.getFormKey())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(allFormList)) {
            List selectFormList = values.stream().filter(v -> !"*".equals(v.getFormKey()) && "*".equals(v.getLinkKey())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(selectFormList)) {
                formKeyList = selectFormList.stream().map(NrExtractEditCtrlEO::getFormKey).collect(Collectors.toList());
            }
        } else {
            extractEditCtrlDTO.setAllFormFlag(true);
            extractEditCtrlDTO.setSelectFormFlag(true);
            extractEditCtrlDTO.setFormNames("\u5168\u90e8");
        }
        Map<String, List<NrExtractEditCtrlEO>> formGroup = values.stream().filter(f -> !StringUtils.isEmpty((String)f.getFormKey())).collect(Collectors.groupingBy(NrExtractEditCtrlEO::getFormKey));
        for (Map.Entry<String, List<NrExtractEditCtrlEO>> entry : formGroup.entrySet()) {
            List<NrExtractEditCtrlEO> value = entry.getValue();
            for (NrExtractEditCtrlEO nrExtractEditCtrlEO : value) {
                if ("*".equals(nrExtractEditCtrlEO.getFormKey()) || "*".equals(nrExtractEditCtrlEO.getLinkKey())) continue;
                linkKeyList.add(nrExtractEditCtrlEO);
                this.formLinkMap.put(nrExtractEditCtrlEO.getLinkKey(), entry.getKey());
            }
        }
        if (!CollectionUtils.isEmpty(formKeyList)) {
            List formDefines = this.runTimeController.queryFormsById(formKeyList);
            if (CollectionUtil.isEmpty((Collection)formDefines)) {
                extractEditCtrlDTO.setAllFormFlag(false);
                extractEditCtrlDTO.setFormNames("\u62a5\u8868\u4e0d\u5b58\u5728");
                return;
            }
            LinkedList<FormSelection> formList = new LinkedList<FormSelection>();
            List formNameList = formDefines.stream().map(IBaseMetaItem::getTitle).collect(Collectors.toList());
            String formNameStr = String.join((CharSequence)",", formNameList);
            for (FormDefine define : formDefines) {
                FormSelection formSelection = new FormSelection();
                List formGroupsByFormKey = this.runTimeController.getFormGroupsByFormKey(define.getKey());
                String formGroupKey = ((FormGroupDefine)formGroupsByFormKey.get(0)).getKey();
                formSelection.setFormKey(define.getKey());
                formSelection.setGroupKey(formGroupKey);
                formList.add(formSelection);
            }
            extractEditCtrlDTO.setAllFormFlag(false);
            extractEditCtrlDTO.setSelectFormFlag(true);
            extractEditCtrlDTO.setForms(formList);
            extractEditCtrlDTO.setFormNames(formNameStr);
        }
        if (!CollectionUtils.isEmpty(notNullLinks = linkKeyList.stream().filter(link -> !StringUtils.isEmpty((String)link.getFieldCode())).collect(Collectors.toList()))) {
            String linkNameStr = String.join((CharSequence)",", notNullLinks.stream().map(s -> "" + s.getFieldTitle() + "[" + s.getFieldCode() + "]").collect(Collectors.toList()));
            extractEditCtrlDTO.setLinkNames(linkNameStr);
            this.initLinks(extractEditCtrlDTO, notNullLinks);
            extractEditCtrlDTO.setSelectLinkFlag(true);
        }
    }

    private void initLinks(NrExtractEditCtrlDTO extractEditCtrlDTO, List<NrExtractEditCtrlEO> linkKeyList) {
        ArrayList list = new ArrayList();
        for (NrExtractEditCtrlEO detail : linkKeyList) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("taskId", extractEditCtrlDTO.getTaskKey());
            map.put("formschemeId", extractEditCtrlDTO.getFormSchemeKey());
            map.put("ishidden", false);
            map.put("dataLink", detail.getLinkKey());
            map.put("fieldcode", detail.getFieldCode());
            map.put("fieldTitle", detail.getFieldTitle());
            map.put("regionkey", detail.getRegionKey());
            list.add(map);
        }
        extractEditCtrlDTO.setLinksList(list);
    }

    @Override
    public List<NrExtractEditCtrlEO> queryByTaskIdAndSchemeKey(String taskId, String schemeId) {
        return (List)this.nrExtractEditCache.get("{TASKID}_{SCHEMEID}".replace("{TASKID}", taskId).replace("{SCHEMEID}", schemeId), () -> this.getByTaskAndScheme(taskId, schemeId));
    }

    @Override
    public List<FormSchemeParamDTO> queryAllFormSchemeByTaskId(String taskKey) {
        ArrayList<FormSchemeParamDTO> schemeParamList = new ArrayList<FormSchemeParamDTO>();
        DataentryFormSchemeParam param = new DataentryFormSchemeParam();
        param.setTaskId(taskKey);
        List formSchemeData = null;
        try {
            formSchemeData = this.dataEntryExecuteController.runtimeFormSchemeList(param);
        }
        catch (Exception e) {
            logger.error("\u6839\u636e\u4efb\u52a1" + taskKey + "\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u8fc7\u7a0b\u4e2d\u51fa\u73b0\u5f02\u5e38", e);
        }
        if (CollectionUtils.isEmpty((Collection)formSchemeData)) {
            return schemeParamList;
        }
        for (FormSchemeData scheme : formSchemeData) {
            FormSchemeParamDTO formSchemeParamDTO = new FormSchemeParamDTO();
            formSchemeParamDTO.setKey(scheme.getKey());
            formSchemeParamDTO.setTitle(scheme.getTitle());
            formSchemeParamDTO.setVaildPeriod(scheme.getToPeriod());
            for (EntityViewData entity : scheme.getEntitys()) {
                if (!DIMENSION_MD_ORG.equals(entity.getDimensionName())) continue;
                formSchemeParamDTO.setOrgTypeCode(entity.getTableName());
            }
            schemeParamList.add(formSchemeParamDTO);
        }
        return schemeParamList;
    }

    @Override
    public FormTree queryFormTreeByFormSchemeKey(String formSchemeKey) {
        return this.dataEntryExecuteController.getAllForms(formSchemeKey, null);
    }

    @Override
    public List<String> queryEditableLinkIdListInForm(NrExtractEditCtrlCondi nrExtractEditCtrlCondi, List<String> linkIdList) {
        if (StringUtils.isEmpty((String)nrExtractEditCtrlCondi.getTaskId()) || StringUtils.isEmpty((String)nrExtractEditCtrlCondi.getSchemeId()) || StringUtils.isEmpty((String)nrExtractEditCtrlCondi.getOrgId()) || StringUtils.isEmpty((String)nrExtractEditCtrlCondi.getFormId())) {
            return new ArrayList<String>();
        }
        List<NrExtractEditCtrlEO> nrExtractEditCtrlDTOS = this.queryByTaskIdAndSchemeKey(nrExtractEditCtrlCondi.getTaskId(), nrExtractEditCtrlCondi.getSchemeId());
        if (CollectionUtils.isEmpty(nrExtractEditCtrlDTOS)) {
            return new ArrayList<String>();
        }
        Map<String, List<NrExtractEditCtrlEO>> groups = nrExtractEditCtrlDTOS.stream().collect(Collectors.groupingBy(NrExtractEditCtrlEO::getId));
        boolean editAccess = false;
        ArrayList linksAll = new ArrayList();
        for (Map.Entry<String, List<NrExtractEditCtrlEO>> entry : groups.entrySet()) {
            List<NrExtractEditCtrlEO> value = entry.getValue();
            NrExtractEditCtrlEO editCtrlEO = value.get(0);
            boolean allForms = value.size() == 1 && "*".equals(editCtrlEO.getFormKey());
            boolean allOrgs = "*".equals(editCtrlEO.getUnitCode());
            ArrayList orgs = allOrgs ? new ArrayList() : Arrays.stream(editCtrlEO.getUnitCode().split(",")).collect(Collectors.toList());
            ArrayList forms = allForms ? new ArrayList() : value.stream().filter(nr -> "*".equals(nr.getLinkKey()) && !"*".equals(nr.getFormKey())).map(NrExtractEditCtrlEO::getFormKey).collect(Collectors.toList());
            ArrayList links = allForms ? new ArrayList() : value.stream().filter(nr -> !"*".equals(nr.getLinkKey()) && (allOrgs || orgs.contains(nrExtractEditCtrlCondi.getOrgId()))).map(NrExtractEditCtrlEO::getLinkKey).collect(Collectors.toList());
            linksAll.addAll(links);
            if (!allOrgs && (orgs == null || !orgs.contains(nrExtractEditCtrlCondi.getOrgId())) || !allForms && (forms == null || !forms.contains(nrExtractEditCtrlCondi.getFormId()))) continue;
            editAccess = true;
            break;
        }
        if (editAccess) {
            return linkIdList;
        }
        List<String> result = linkIdList.stream().filter(linksAll::contains).collect(Collectors.toList());
        return result;
    }

    private List<NrExtractEditCtrlEO> getByTaskAndScheme(String taskId, String schemeId) {
        return this.nrExtractEditCtrlDao.queryByTaskIdAndSchemeKey(taskId, schemeId);
    }

    @Override
    public void nrExtractEditCtrlCacheClear() {
        this.nrExtractEditCache.clear();
    }

    @Override
    public void nrExtractEditCtrlCacheEvict(String taskidAndSchemeId) {
        this.nrExtractEditCache.evict(taskidAndSchemeId);
    }
}

