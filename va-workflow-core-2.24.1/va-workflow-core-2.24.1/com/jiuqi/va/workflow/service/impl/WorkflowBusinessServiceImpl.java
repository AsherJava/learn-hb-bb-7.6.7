/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.meta.MetaState
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDim
 *  com.jiuqi.va.domain.meta.MetaTreeInfoDTO
 *  com.jiuqi.va.domain.meta.MetaType
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDO
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDTO
 *  com.jiuqi.va.domain.workflow.business.ConditionGroupRow
 *  com.jiuqi.va.domain.workflow.business.ConditionRowAttr
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessPublishCheckResult
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessPublishItem
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessPublishVO
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessRelDesignDO
 *  com.jiuqi.va.feign.client.BussinessClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.service.impl;

import com.jiuqi.va.biz.intf.meta.MetaState;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaInfoDim;
import com.jiuqi.va.domain.meta.MetaTreeInfoDTO;
import com.jiuqi.va.domain.meta.MetaType;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDO;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDTO;
import com.jiuqi.va.domain.workflow.business.ConditionGroupRow;
import com.jiuqi.va.domain.workflow.business.ConditionRowAttr;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessPublishCheckResult;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessPublishItem;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessPublishVO;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessRelDesignDO;
import com.jiuqi.va.feign.client.BussinessClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.common.VaWorkflowAsyncTask;
import com.jiuqi.va.workflow.config.BizTypeConfig;
import com.jiuqi.va.workflow.constants.WorkflowBusinessRelationConst;
import com.jiuqi.va.workflow.dao.WorkflowBusinessDao;
import com.jiuqi.va.workflow.dao.WorkflowMetaDao;
import com.jiuqi.va.workflow.dao.business.WorkflowBusinessRelDesignDao;
import com.jiuqi.va.workflow.dao.business.WorkflowBusinessRelDraftDao;
import com.jiuqi.va.workflow.domain.BillWorkflowNodeVO;
import com.jiuqi.va.workflow.domain.BusinessWorkflowNodesInfoSaveProcess;
import com.jiuqi.va.workflow.domain.BusinessWorkflowTreeVO;
import com.jiuqi.va.workflow.domain.WorkflowOption;
import com.jiuqi.va.workflow.domain.workflowbusiness.WorkflowBusinessRelation;
import com.jiuqi.va.workflow.service.WorkflowBusinessPublishService;
import com.jiuqi.va.workflow.service.WorkflowBusinessSaveService;
import com.jiuqi.va.workflow.service.WorkflowBusinessService;
import com.jiuqi.va.workflow.service.WorkflowHelperService;
import com.jiuqi.va.workflow.service.conditiongroup.ConditionCheck;
import com.jiuqi.va.workflow.service.conditiongroup.ConditionGroupCheck;
import com.jiuqi.va.workflow.service.conditiongroup.ConditionGroupCombinationCheck;
import com.jiuqi.va.workflow.service.conditiongroup.ConditionGroupContext;
import com.jiuqi.va.workflow.service.conditiongroup.ConditionGroupRowCheck;
import com.jiuqi.va.workflow.service.conditiongroup.Relationship;
import com.jiuqi.va.workflow.strategy.distribute.DistributeStrategy;
import com.jiuqi.va.workflow.utils.VaWorkFlowDataUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowCacheUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowBusinessServiceImpl
implements WorkflowBusinessService,
InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(WorkflowBusinessServiceImpl.class);
    private static final String WORKFLOW = "WORKFLOW";
    private static final String KEY_UNIQUECODE = "uniqueCode";
    private static final String KEY_BIZ_DEFINE = "bizDefine";
    private static final String KEY_SEARCHTEXT = "searchText";
    private static final String KEY_NAME = "name";
    private static final String KEY_TITLE = "title";
    private static final String KEY_NODEID = "nodeId";
    private static final int INITIAL_CAPACITY = 16;
    private static final String KEY_BUSINESS_TYPE = "businesstype";
    private static final String BILL = "bill";
    @Autowired
    private WorkflowBusinessDao workflowBusinessDao;
    @Autowired
    private WorkflowBusinessRelDraftDao workflowBusinessRelDraftDao;
    @Autowired
    private WorkflowBusinessRelDesignDao workflowBusinessRelDesignDao;
    @Autowired
    private MetaDataClient metaDataClient;
    @Autowired
    private WorkflowMetaDao workflowMetaDao;
    @Autowired
    private BizTypeConfig bizTypeConfig;
    @Autowired
    private WorkflowHelperService workflowHelperService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private VaWorkflowAsyncTask vaWorkflowAsyncTask;
    @Autowired(required=false)
    private List<Relationship> relationships;
    @Autowired
    private ConditionGroupContext context;
    @Autowired
    private WorkflowBusinessPublishService workflowBusinessPublishService;
    @Autowired
    private WorkflowBusinessSaveService workflowBusinessSaveService;
    @Autowired(required=false)
    private List<DistributeStrategy> distributeStrategyList;
    private Map<String, Relationship> relationshipMap;
    private Map<Integer, DistributeStrategy> distributionMap;

    @Override
    public List<WorkflowBusinessDTO> list(WorkflowBusinessDTO workflowBusinessDTO) {
        ArrayList<WorkflowBusinessDTO> result = new ArrayList<WorkflowBusinessDTO>();
        List<WorkflowBusinessDO> workflowBusinessList = this.workflowBusinessDao.selectLatestList(workflowBusinessDTO);
        List<WorkflowBusinessDO> draftDOList = this.workflowBusinessRelDraftDao.selectLatestList(workflowBusinessDTO);
        this.overrideByDesignTimeData(workflowBusinessList, draftDOList, true);
        if (CollectionUtils.isEmpty(workflowBusinessList)) {
            return null;
        }
        workflowBusinessList = WorkflowBusinessServiceImpl.sortByDesignState(workflowBusinessList);
        String search = workflowBusinessDTO.getSearch();
        Map<String, MetaInfoDim> workflowDefineInfoMap = VaWorkFlowDataUtils.getAllWorkflowDefineInfoMap();
        HashMap<String, UserDO> userCacheMap = new HashMap<String, UserDO>(workflowBusinessList.size());
        for (WorkflowBusinessDO workflowBusinessDO : workflowBusinessList) {
            String workflowdefinekey = workflowBusinessDO.getWorkflowdefinekey();
            MetaInfoDim workflowDefineInfo = workflowDefineInfoMap.get(workflowdefinekey);
            if (workflowDefineInfo == null) continue;
            String workflowdefinetitle = workflowDefineInfo.getTitle();
            if (StringUtils.hasText(search) && workflowdefinetitle != null && !workflowdefinetitle.contains(search) && !workflowdefinetitle.contains(search.toUpperCase(Locale.ROOT)) && !workflowdefinetitle.contains(search.toLowerCase(Locale.ROOT))) continue;
            WorkflowBusinessDTO newWorkflowBusinessDTO = new WorkflowBusinessDTO();
            newWorkflowBusinessDTO.setDesignstate(Integer.valueOf(workflowBusinessDO.getDesignstate() == null ? 0 : workflowBusinessDO.getDesignstate()));
            newWorkflowBusinessDTO.setLockflag(workflowBusinessDO.getLockflag());
            newWorkflowBusinessDTO.setLockusername(VaWorkFlowDataUtils.getUserDoWithCache(workflowBusinessDO.getLockuser(), userCacheMap).getUsername());
            newWorkflowBusinessDTO.setWorkflowdefinekey(workflowdefinekey);
            newWorkflowBusinessDTO.setWorkflowdefinetitle(workflowdefinetitle);
            newWorkflowBusinessDTO.setBusinesscode(workflowBusinessDO.getBusinesscode());
            result.add(newWorkflowBusinessDTO);
        }
        return result;
    }

    private static List<WorkflowBusinessDO> sortByDesignState(List<WorkflowBusinessDO> workflowBusinessList) {
        Comparator comparator = (o1, o2) -> {
            Integer o1DesignState = o1.getDesignstate() == null ? 0 : o1.getDesignstate();
            Integer o2DesignState = o2.getDesignstate() == null ? 0 : o2.getDesignstate();
            if (o1DesignState != 0 && o2DesignState != 0) {
                if (o1DesignState.equals(o2DesignState)) {
                    return o1.getModifytime().compareTo(o2.getModifytime());
                }
                return o1DesignState.compareTo(o2DesignState);
            }
            return o2DesignState.compareTo(o1DesignState);
        };
        workflowBusinessList = workflowBusinessList.stream().sorted(comparator).collect(Collectors.toList());
        return workflowBusinessList;
    }

    private void overrideByDesignTimeData(List<WorkflowBusinessDO> runtimeDOList, List<WorkflowBusinessDO> draftDOList, boolean designateWorkflowDefineVersion) {
        String key;
        HashMap<String, WorkflowBusinessDO> draftDOMap = new HashMap<String, WorkflowBusinessDO>();
        for (WorkflowBusinessDO draftDO : draftDOList) {
            key = draftDO.getBusinesscode() + "_" + draftDO.getWorkflowdefinekey();
            if (!designateWorkflowDefineVersion) {
                key = draftDO.getBusinesscode() + "_" + draftDO.getWorkflowdefinekey() + "_" + draftDO.getWorkflowdefineversion();
            }
            draftDOMap.put(key, draftDO);
        }
        for (WorkflowBusinessDO runtimeDO : runtimeDOList) {
            WorkflowBusinessDO draftDO;
            key = runtimeDO.getBusinesscode() + "_" + runtimeDO.getWorkflowdefinekey();
            if (!designateWorkflowDefineVersion) {
                key = runtimeDO.getBusinesscode() + "_" + runtimeDO.getWorkflowdefinekey() + "_" + runtimeDO.getWorkflowdefineversion();
            }
            if ((draftDO = (WorkflowBusinessDO)draftDOMap.get(key)) != null && Objects.equals(draftDO.getWorkflowdefineversion(), runtimeDO.getWorkflowdefineversion()) && draftDO.getRelversion() >= runtimeDO.getRelversion()) {
                runtimeDO.setId(draftDO.getId());
                runtimeDO.setLockflag(draftDO.getLockflag());
                runtimeDO.setLockuser(draftDO.getLockuser());
                runtimeDO.setDesignstate(draftDO.getDesignstate());
                runtimeDO.setDesigndata(draftDO.getDesigndata());
                runtimeDO.setModifytime(draftDO.getModifytime());
            }
            draftDOMap.remove(key);
        }
        runtimeDOList.addAll(draftDOMap.values());
    }

    @Override
    public List<WorkflowBusinessDTO> businesslist(WorkflowBusinessDTO workflowBusinessDTO) {
        ArrayList<WorkflowBusinessDTO> result = new ArrayList<WorkflowBusinessDTO>();
        VaWorkflowUtils.verifyInParamLegal(workflowBusinessDTO.getWorkflowdefinekeys());
        List<WorkflowBusinessDO> workflowBusinessList = this.workflowBusinessDao.selectBusinessList(workflowBusinessDTO);
        List<WorkflowBusinessDO> draftDOList = this.workflowBusinessRelDraftDao.selectLatestList(workflowBusinessDTO);
        this.overrideByDesignTimeData(workflowBusinessList, draftDOList, true);
        if (CollectionUtils.isEmpty(workflowBusinessList)) {
            return null;
        }
        workflowBusinessList = WorkflowBusinessServiceImpl.sortByDesignState(workflowBusinessList);
        String search = workflowBusinessDTO.getSearch();
        Map<String, MetaInfoDim> allBillDefineInfoMap = VaWorkFlowDataUtils.getAllBillDefineInfoMap();
        HashMap<String, UserDO> userCacheMap = new HashMap<String, UserDO>(workflowBusinessList.size());
        for (WorkflowBusinessDO workflowBusinessDO : workflowBusinessList) {
            String businesstype = workflowBusinessDO.getBusinesstype();
            String businesscode = workflowBusinessDO.getBusinesscode();
            String businesstitle = null;
            if ("BILL".equalsIgnoreCase(businesstype)) {
                MetaInfoDim billDefineInfo = allBillDefineInfoMap.get(businesscode);
                if (billDefineInfo == null) continue;
                businesstitle = billDefineInfo.getTitle();
            } else {
                try {
                    BussinessClient bussinessClient = VaWorkflowUtils.getDynamicFeignClient(BussinessClient.class, this.bizTypeConfig, businesstype);
                    TenantDO tenantDO = new TenantDO();
                    tenantDO.setTraceId(Utils.getTraceId());
                    tenantDO.addExtInfo(KEY_BIZ_DEFINE, (Object)businesscode);
                    R bizTitle = bussinessClient.getBizTitle(tenantDO);
                    if (bizTitle.getCode() == 0) {
                        businesstitle = (String)bizTitle.get((Object)"bizDefineTitle");
                    }
                }
                catch (Exception e) {
                    businesstitle = businesscode;
                    log.error("\u83b7\u53d6{}\u4e1a\u52a1\u540d\u79f0\u5931\u8d25", (Object)businesscode, (Object)e);
                }
            }
            if (StringUtils.hasText(search) && businesstitle != null && !businesstitle.contains(search) && !businesstitle.contains(search.toUpperCase(Locale.ROOT)) && !businesstitle.contains(search.toLowerCase(Locale.ROOT))) continue;
            WorkflowBusinessDTO newWorkflowBusinessDTO = new WorkflowBusinessDTO();
            newWorkflowBusinessDTO.setWorkflowdefinekey(workflowBusinessDO.getWorkflowdefinekey());
            newWorkflowBusinessDTO.setBusinesscode(businesscode);
            newWorkflowBusinessDTO.setBusinesstitle(businesstitle);
            newWorkflowBusinessDTO.setDesignstate(Integer.valueOf(workflowBusinessDO.getDesignstate() == null ? 0 : workflowBusinessDO.getDesignstate()));
            newWorkflowBusinessDTO.setLockflag(workflowBusinessDO.getLockflag());
            newWorkflowBusinessDTO.setLockusername(VaWorkFlowDataUtils.getUserDoWithCache(workflowBusinessDO.getLockuser(), userCacheMap).getUsername());
            result.add(newWorkflowBusinessDTO);
        }
        return result;
    }

    @Override
    public List<BusinessWorkflowTreeVO> getBusinessWorkflowTree(TenantDO tenantDO) {
        Map extInfo = Optional.ofNullable(tenantDO.getExtInfo()).orElse(Collections.emptyMap());
        String type = extInfo.get("type") == null ? "" : extInfo.get("type").toString();
        String uniqueCode = extInfo.get(KEY_UNIQUECODE) == null ? "" : extInfo.get(KEY_UNIQUECODE).toString();
        ArrayList<BusinessWorkflowTreeVO> treeList = new ArrayList<BusinessWorkflowTreeVO>();
        List<MetaTreeInfoDTO> allMetaDatas = this.metaDataClient.getAllWorkflowMetas(new TenantDO()).getRows();
        List moduleMetaDatas = allMetaDatas.stream().filter(x -> MetaType.MODULE == x.getType()).collect(Collectors.toList());
        if (MetaType.MODULE.toString().equalsIgnoreCase(type) && StringUtils.hasText(uniqueCode)) {
            moduleMetaDatas = moduleMetaDatas.stream().filter(x -> uniqueCode.equals(x.getUniqueCode())).collect(Collectors.toList());
        }
        List<WorkflowBusinessDO> workflowBusiness = this.workflowBusinessDao.selectLatestList(new WorkflowBusinessDTO());
        List metaInfos = Optional.ofNullable(this.metaDataClient.getAllMetaInfoByMetaType(new TenantDO()).getRows()).orElse(Collections.emptyList());
        List workflowMetas = metaInfos.stream().filter(x -> WORKFLOW.equalsIgnoreCase(x.getMetaType())).collect(Collectors.toList());
        for (MetaTreeInfoDTO moduleMetaData : moduleMetaDatas) {
            BusinessWorkflowTreeVO treeVO = new BusinessWorkflowTreeVO();
            treeVO.setId(moduleMetaData.getType() + "#" + moduleMetaData.getUniqueCode());
            treeVO.setName(moduleMetaData.getName());
            treeVO.setTitle(moduleMetaData.getTitle());
            treeVO.setType(String.valueOf(MetaType.MODULE));
            treeVO.setUniqueCode(moduleMetaData.getUniqueCode());
            treeVO.setTenantName(moduleMetaData.getTenantName());
            ArrayList<BusinessWorkflowTreeVO> children = new ArrayList<BusinessWorkflowTreeVO>();
            if (MetaType.GROUP.toString().equalsIgnoreCase(type) && StringUtils.hasText(uniqueCode)) {
                MetaTreeInfoDTO groupMetaData = allMetaDatas.stream().filter(x -> MetaType.GROUP.toString().equalsIgnoreCase(type) && uniqueCode.equalsIgnoreCase(x.getUniqueCode())).findAny().orElse(null);
                if (Objects.isNull(groupMetaData) || !groupMetaData.getModuleName().equalsIgnoreCase(moduleMetaData.getName())) continue;
                allMetaDatas = this.dealGroupMetaDataFilter(groupMetaData, (List<? extends MetaTreeInfoDTO>)allMetaDatas, workflowMetas, workflowBusiness);
            }
            if (MetaType.METADATA.toString().equalsIgnoreCase(type) && StringUtils.hasText(uniqueCode)) {
                MetaTreeInfoDTO metaData = allMetaDatas.stream().filter(x -> MetaType.METADATA.toString().equalsIgnoreCase(type) && uniqueCode.equalsIgnoreCase(x.getUniqueCode())).findAny().orElse(null);
                if (Objects.isNull(metaData) || !metaData.getModuleName().equalsIgnoreCase(moduleMetaData.getName())) continue;
                allMetaDatas = this.dealGroupMetaDataFilter(metaData, allMetaDatas, workflowMetas, workflowBusiness);
            }
            List groupMetaDatas = allMetaDatas.stream().filter(x -> moduleMetaData.getName().equals(x.getModuleName()) && MetaType.GROUP == x.getType()).collect(Collectors.toList());
            List metaDatas = allMetaDatas.stream().filter(x -> moduleMetaData.getName().equals(x.getModuleName()) && MetaType.METADATA == x.getType()).collect(Collectors.toList());
            this.dealBusinessWorkflowTreeChildren(moduleMetaData, children, groupMetaDatas, metaDatas, workflowMetas, workflowBusiness);
            treeVO.setChildren(children);
            treeList.add(treeVO);
        }
        return treeList;
    }

    private List<MetaTreeInfoDTO> dealGroupMetaDataFilter(MetaTreeInfoDTO groupMetaData, List<? extends MetaTreeInfoDTO> allMetaDatas, List<? extends MetaInfoDim> workflowMetas, List<? extends WorkflowBusinessDO> workflowBusiness) {
        ArrayList<MetaTreeInfoDTO> list = new ArrayList<MetaTreeInfoDTO>();
        String moduleName = groupMetaData.getModuleName();
        MetaTreeInfoDTO moduleMetaData = allMetaDatas.stream().filter(x -> MetaType.MODULE == x.getType() && moduleName.equals(x.getName())).findAny().orElse(null);
        List groupMetaDatas = allMetaDatas.stream().filter(x -> Objects.nonNull(moduleMetaData) && moduleMetaData.getName().equals(x.getModuleName()) && MetaType.GROUP == x.getType()).collect(Collectors.toList());
        List metaDatas = allMetaDatas.stream().filter(x -> Objects.nonNull(moduleMetaData) && moduleMetaData.getName().equals(x.getModuleName()) && MetaType.METADATA == x.getType()).collect(Collectors.toList());
        ArrayList parentMetaDataList = new ArrayList();
        ArrayList childrenMetaDataList = new ArrayList();
        this.dealParentMetaData(parentMetaDataList, groupMetaData, allMetaDatas);
        this.dealTreeChildren(groupMetaData, childrenMetaDataList, groupMetaDatas, metaDatas, workflowMetas, workflowBusiness);
        list.add(moduleMetaData);
        list.add(groupMetaData);
        list.addAll(parentMetaDataList);
        list.addAll(childrenMetaDataList);
        return list;
    }

    private void dealTreeChildren(MetaTreeInfoDTO metaTreeInfoDTO, List<? super MetaTreeInfoDTO> children, List<? extends MetaTreeInfoDTO> groupMetas, List<? extends MetaTreeInfoDTO> metaDatas, List<? extends MetaInfoDim> workflowMetas, List<? extends WorkflowBusinessDO> workflowBusiness) {
        ArrayList tempChildren;
        if (MetaType.MODULE == metaTreeInfoDTO.getType()) {
            List rootGroups = groupMetas.stream().filter(x -> metaTreeInfoDTO.getName().equals(x.getModuleName()) && Objects.isNull(x.getParentName())).collect(Collectors.toList());
            for (MetaTreeInfoDTO rootGroup : rootGroups) {
                children.add((MetaTreeInfoDTO)rootGroup);
                tempChildren = new ArrayList();
                this.dealTreeChildren(rootGroup, tempChildren, groupMetas, metaDatas, workflowMetas, workflowBusiness);
                children.addAll(tempChildren);
            }
        }
        if (MetaType.GROUP == metaTreeInfoDTO.getType()) {
            List childGroups = groupMetas.stream().filter(x -> metaTreeInfoDTO.getName().equals(x.getParentName())).collect(Collectors.toList());
            for (Object childGroup : childGroups) {
                children.add((MetaTreeInfoDTO)childGroup);
                tempChildren = new ArrayList();
                this.dealTreeChildren((MetaTreeInfoDTO)childGroup, tempChildren, groupMetas, metaDatas, workflowMetas, workflowBusiness);
                children.addAll(tempChildren);
            }
            List metaInfos = metaDatas.stream().filter(x -> metaTreeInfoDTO.getName().equals(x.getGroupName())).collect(Collectors.toList());
            for (MetaTreeInfoDTO metaInfo : metaInfos) {
                children.add((MetaTreeInfoDTO)metaInfo);
                ArrayList tempChildren2 = new ArrayList();
                this.dealTreeChildren(metaInfo, tempChildren2, groupMetas, metaDatas, workflowMetas, workflowBusiness);
                children.addAll(tempChildren2);
            }
        }
        if (MetaType.METADATA == metaTreeInfoDTO.getType()) {
            List workflowBusinessList = workflowBusiness.stream().filter(x -> metaTreeInfoDTO.getUniqueCode().equals(x.getBusinesscode())).collect(Collectors.toList());
            for (WorkflowBusinessDO workflowBusinessDO : workflowBusinessList) {
                MetaInfoDim metaInfoDim = workflowMetas.stream().filter(x -> x.getUniqueCode().equals(workflowBusinessDO.getWorkflowdefinekey())).findAny().orElse(null);
                if (Objects.isNull(metaInfoDim)) continue;
                MetaTreeInfoDTO metaTree = new MetaTreeInfoDTO();
                metaTree.setId(metaInfoDim.getId());
                metaTree.setName(metaInfoDim.getName());
                metaTree.setTitle(metaInfoDim.getTitle());
                metaTree.setModuleName(metaInfoDim.getModuleName());
                metaTree.setUniqueCode(metaInfoDim.getUniqueCode());
                children.add((MetaTreeInfoDTO)metaTree);
            }
        }
    }

    @Override
    public PageVO<BillWorkflowNodeVO> listBillWorkflowNode(TenantDO tenantDO) {
        OptionItemVO optionItemVO;
        Map extInfo = Optional.ofNullable(tenantDO.getExtInfo()).orElse(Collections.emptyMap());
        String businessType = extInfo.get(KEY_BUSINESS_TYPE) == null ? BILL : extInfo.get(KEY_BUSINESS_TYPE).toString();
        String type = extInfo.get("type") == null ? "" : extInfo.get("type").toString();
        Object uniqueCodeObj = extInfo.get(KEY_UNIQUECODE);
        String searchText = extInfo.get(KEY_SEARCHTEXT) == null ? "" : extInfo.get(KEY_SEARCHTEXT).toString();
        int limit = extInfo.get("limit") == null ? 16 : (Integer)extInfo.get("limit");
        int offset = extInfo.get("offset") == null ? 0 : (Integer)extInfo.get("offset");
        boolean pagination = extInfo.get("pagination") != null && (Boolean)extInfo.get("pagination") != false;
        List allMetaDatas = this.metaDataClient.getAllWorkflowMetas(new TenantDO()).getRows();
        List workflowBusinessList = Optional.ofNullable(this.workflowBusinessDao.selectBusinessList(new WorkflowBusinessDTO())).orElse(Collections.emptyList()).stream().filter(x -> businessType.equalsIgnoreCase(x.getBusinesstype())).collect(Collectors.toList());
        UserLoginDTO userNow = ShiroUtil.getUser();
        OptionItemDTO optionParam = new OptionItemDTO();
        optionParam.setName("META001");
        List optionItemList = this.metaDataClient.listOption(optionParam);
        if (userNow.getMgrFlag().equals("normal") && optionItemList != null && !optionItemList.isEmpty() && (optionItemVO = (OptionItemVO)optionItemList.get(0)).getVal().equals("1")) {
            TenantDO tenantParam = new TenantDO();
            HashMap<String, Object> exParam = new HashMap<String, Object>();
            exParam.put("TenantName", ShiroUtil.getTenantName());
            exParam.put("Groupflag", 0);
            exParam.put("MetaType", "workflow");
            tenantParam.setExtInfo(exParam);
            Set userAuth = this.metaDataClient.checkUserAuth(tenantParam);
            HashMap hashMap = new HashMap(16);
            for (Object name : userAuth) {
                hashMap.put(name, 1);
            }
            ArrayList infoTemp = new ArrayList();
            for (WorkflowBusinessDO workflowBusinessDO : workflowBusinessList) {
                if (!hashMap.containsKey(workflowBusinessDO.getWorkflowdefinekey())) continue;
                infoTemp.add(workflowBusinessDO);
            }
            workflowBusinessList = infoTemp;
        }
        List metaInfos = this.metaDataClient.getAllMetaInfoByMetaType(new TenantDO()).getRows();
        if (uniqueCodeObj instanceof String) {
            MetaTreeInfoDTO moduleMetaData;
            String uniqueCode = uniqueCodeObj.toString();
            if (MetaType.MODULE.toString().equalsIgnoreCase(type) && StringUtils.hasText(uniqueCode) && Objects.nonNull(moduleMetaData = (MetaTreeInfoDTO)allMetaDatas.stream().filter(x -> uniqueCode.equals(x.getUniqueCode())).findAny().orElse(null)) && Objects.nonNull(moduleMetaData.getName())) {
                allMetaDatas = allMetaDatas.stream().filter(x -> moduleMetaData.getName().equalsIgnoreCase(x.getModuleName()) || Objects.isNull(x.getModuleName())).collect(Collectors.toList());
            }
            if (MetaType.METADATA.toString().equalsIgnoreCase(type) && StringUtils.hasText(uniqueCode)) {
                workflowBusinessList = workflowBusinessList.stream().filter(x -> uniqueCode.equalsIgnoreCase(x.getBusinesscode())).collect(Collectors.toList());
            }
            if (WORKFLOW.equalsIgnoreCase(type) && StringUtils.hasText(uniqueCode)) {
                Object bizDefine = extInfo.get(KEY_BIZ_DEFINE);
                workflowBusinessList = bizDefine == null ? workflowBusinessList.stream().filter(x -> uniqueCode.equalsIgnoreCase(x.getWorkflowdefinekey())).collect(Collectors.toList()) : workflowBusinessList.stream().filter(x -> uniqueCode.equalsIgnoreCase(x.getWorkflowdefinekey())).filter(x -> Objects.equals(bizDefine, x.getBusinesscode())).collect(Collectors.toList());
            }
        } else if (uniqueCodeObj instanceof List) {
            List finalUniqueCodeList = ((List)uniqueCodeObj).stream().map(String::toUpperCase).collect(Collectors.toList());
            workflowBusinessList = workflowBusinessList.stream().filter(x -> finalUniqueCodeList.contains(x.getBusinesscode().toUpperCase())).collect(Collectors.toList());
        }
        ArrayList<BillWorkflowNodeVO> tempBillWorkflowNodeList = new ArrayList<BillWorkflowNodeVO>();
        HashMap billCacheMap = new HashMap(16);
        HashMap workflowCacheMap = new HashMap(16);
        for (WorkflowBusinessDO workflowBusinessDO : workflowBusinessList) {
            String workflowdefinekey = workflowBusinessDO.getWorkflowdefinekey();
            Long workflowdefineversion = workflowBusinessDO.getWorkflowdefineversion();
            String billUniqueCode = workflowBusinessDO.getBusinesscode();
            List<Map<String, Object>> nodes = this.workflowHelperService.listNodesByVersionUniqueCode(workflowdefinekey, workflowdefineversion);
            if (StringUtils.hasText(searchText)) {
                ArrayList<Map<String, Object>> tempNodeList = new ArrayList<Map<String, Object>>();
                String[] searchTexts = searchText.trim().split(" ");
                Set<String> searchTextSet = Arrays.stream(searchTexts).collect(Collectors.toSet());
                List<Map<String, Object>> finalNodes = nodes;
                searchTextSet.forEach(searchKey -> {
                    List filterNodeList = finalNodes.stream().filter(x -> Objects.nonNull(x.get(KEY_NAME)) && x.get(KEY_NAME).toString().contains((CharSequence)searchKey)).collect(Collectors.toList());
                    tempNodeList.addAll(filterNodeList);
                });
                nodes = tempNodeList;
            }
            for (Map<String, Object> map : nodes) {
                BillWorkflowNodeVO billWorkflowNodeVO = new BillWorkflowNodeVO();
                billWorkflowNodeVO.setWorkflowName(workflowdefinekey);
                billWorkflowNodeVO.setBizName(billUniqueCode);
                billWorkflowNodeVO.setNodeName(String.valueOf(map.get(KEY_NAME)));
                billWorkflowNodeVO.setNodeId(String.valueOf(map.get(KEY_NODEID)));
                billWorkflowNodeVO.setType(businessType);
                String uniqueCode = uniqueCodeObj instanceof String ? uniqueCodeObj.toString() : workflowBusinessDO.getBusinesscode();
                if (!this.hasItemTitle(billWorkflowNodeVO, allMetaDatas, type, uniqueCode, billCacheMap, workflowCacheMap, metaInfos)) continue;
                tempBillWorkflowNodeList.add(billWorkflowNodeVO);
            }
        }
        int total = tempBillWorkflowNodeList.size();
        PageVO billWorkflowNodePage = new PageVO();
        if (CollectionUtils.isEmpty(tempBillWorkflowNodeList)) {
            billWorkflowNodePage.setRows(Collections.emptyList());
            billWorkflowNodePage.setTotal(0);
            return billWorkflowNodePage;
        }
        billWorkflowNodePage.setTotal(total);
        if (pagination) {
            List lists = VaWorkflowUtils.splitList(tempBillWorkflowNodeList, limit);
            int size = 0;
            for (List list : lists) {
                if ((size += list.size()) < offset + limit) continue;
                billWorkflowNodePage.setRows(list);
                return billWorkflowNodePage;
            }
            if (CollectionUtils.isEmpty(billWorkflowNodePage.getRows())) {
                billWorkflowNodePage.setRows(lists.get(lists.size() - 1));
            }
        } else {
            billWorkflowNodePage.setRows(tempBillWorkflowNodeList);
        }
        return billWorkflowNodePage;
    }

    @Override
    public R distribute(WorkflowBusinessDistributeDTO distributionDTO) {
        if (CollectionUtils.isEmpty(distributionDTO.getTargetObjectList())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        Locale locale = LocaleContextHolder.getLocale();
        UserLoginDTO user = ShiroUtil.getUser();
        this.vaWorkflowAsyncTask.execute(() -> {
            try {
                LocaleContextHolder.setLocale(locale);
                ShiroUtil.unbindUser();
                ShiroUtil.bindUser((UserLoginDTO)user);
                DistributeStrategy distributeStrategy = this.distributionMap.get(distributionDTO.getDistributeType());
                distributeStrategy.executeDistribute(distributionDTO);
            }
            catch (Exception e) {
                log.error("\u6267\u884c\u4e0b\u53d1\u5931\u8d25", e);
                String resultKey = distributionDTO.getResultKey();
                String result = VaWorkflowCacheUtils.getImportDataResult(resultKey);
                BusinessWorkflowNodesInfoSaveProcess processInfo = new BusinessWorkflowNodesInfoSaveProcess();
                if (StringUtils.hasText(result)) {
                    processInfo = (BusinessWorkflowNodesInfoSaveProcess)JSONUtil.parseObject((String)result, BusinessWorkflowNodesInfoSaveProcess.class);
                }
                processInfo.setFailedMessage("\u6267\u884c\u4e0b\u53d1\u5931\u8d25\uff0c" + e.getMessage());
                VaWorkflowCacheUtils.setImportDataResult(resultKey, JSONUtil.toJSONString((Object)processInfo));
            }
            finally {
                ShiroUtil.unbindUser();
            }
        });
        return R.ok();
    }

    @Override
    public R getDistributeResult(WorkflowBusinessDistributeDTO distributionDTO) {
        String resultKey = distributionDTO.getResultKey();
        if (!StringUtils.hasText(resultKey)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        String resString = VaWorkflowCacheUtils.getImportDataResult(resultKey);
        if (!StringUtils.hasText(resString)) {
            return R.error((int)2, (String)VaWorkFlowI18nUtils.getInfo("va.workflow.informationdoesnotexist"));
        }
        return R.ok().put("result", (Object)JSONUtil.parseObject((String)resString));
    }

    @Override
    public Set<String> extractBizFields(List<Map<String, Object>> workflows) {
        HashSet<String> reusltSet = new HashSet<String>();
        if (CollectionUtils.isEmpty(workflows)) {
            return reusltSet;
        }
        for (Map<String, Object> workflowMap : workflows) {
            List groupInfo;
            Map conditionView = (Map)workflowMap.get("conditionView");
            if (conditionView == null || CollectionUtils.isEmpty(groupInfo = (List)conditionView.get("groupInfo"))) continue;
            for (Map groupItem : groupInfo) {
                List groupRowList = (List)groupItem.get("info");
                if (CollectionUtils.isEmpty(groupRowList)) continue;
                for (Map rowItem : groupRowList) {
                    Map bizField = (Map)rowItem.get("bizField");
                    if (bizField == null) continue;
                    reusltSet.add((String)bizField.get(KEY_NAME));
                }
            }
        }
        return reusltSet;
    }

    @Override
    public Set<String> judgeSatisfyAdaptCondition(List<Map<String, Object>> workflows, Map<String, Object> bizFieldMap) {
        HashSet<String> reusltSet = new HashSet<String>();
        if (CollectionUtils.isEmpty(workflows)) {
            return reusltSet;
        }
        for (Map<String, Object> workflowMap : workflows) {
            Map conditionView = (Map)workflowMap.get("conditionView");
            String workflowdefinekey = (String)workflowMap.get("workflowdefinekey");
            if (conditionView == null) {
                reusltSet.add(workflowdefinekey);
                continue;
            }
            String groupRelation = (String)conditionView.get("groupRelation");
            List conditionGroupList = (List)conditionView.get("groupInfo");
            if (CollectionUtils.isEmpty(conditionGroupList) || !StringUtils.hasText(groupRelation)) {
                reusltSet.add(workflowdefinekey);
                continue;
            }
            if (!this.handleConditionGroup(conditionGroupList, bizFieldMap, groupRelation)) continue;
            reusltSet.add(workflowdefinekey);
        }
        return reusltSet;
    }

    private boolean handleConditionGroup(List<Map<String, Object>> conditionGroupList, Map<String, Object> bizFieldMap, String groupRelation) {
        ArrayList<ConditionGroupCheck> conditionGroupChecks = new ArrayList<ConditionGroupCheck>();
        for (Map<String, Object> groupItem : conditionGroupList) {
            List infos = (List)groupItem.get("info");
            if (CollectionUtils.isEmpty(infos)) continue;
            List conditionRows = JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)infos), ConditionGroupRow.class);
            ArrayList<ConditionCheck> conditionRowChecks = new ArrayList<ConditionCheck>();
            for (ConditionGroupRow conditionRow : conditionRows) {
                ConditionGroupRowCheck rowCheck = new ConditionGroupRowCheck(conditionRow, this.context);
                conditionRowChecks.add(rowCheck);
            }
            ConditionGroupCheck conditionGroupCheck = new ConditionGroupCheck(conditionRowChecks, this.relationshipMap.get(WorkflowOption.RelationType.AND.name()));
            conditionGroupChecks.add(conditionGroupCheck);
        }
        if (conditionGroupChecks.isEmpty()) {
            return true;
        }
        ConditionGroupCombinationCheck combination = new ConditionGroupCombinationCheck(conditionGroupChecks, this.relationshipMap.get(groupRelation));
        return combination.check(bizFieldMap);
    }

    private boolean hasItemTitle(BillWorkflowNodeVO billWorkflowNodeVO, List<? extends MetaTreeInfoDTO> allMetaDatas, String type, String uniqueCode, Map<? super String, String> billMap, Map<? super String, String> workflowMap, List<? extends MetaInfoDim> metaInfos) {
        boolean flag = false;
        String bizName = billWorkflowNodeVO.getBizName();
        String workflowName = billWorkflowNodeVO.getWorkflowName();
        Map<String, String> groupInfoMap = this.getGroupInfo(billWorkflowNodeVO.getBizName(), allMetaDatas, type, uniqueCode);
        billWorkflowNodeVO.setGroupName(groupInfoMap.get(KEY_NAME));
        billWorkflowNodeVO.setGroupTitle(groupInfoMap.get(KEY_TITLE));
        List metaDatas = allMetaDatas.stream().filter(x -> MetaType.METADATA == x.getType()).collect(Collectors.toList());
        List workflowMetas = metaInfos.stream().filter(x -> WORKFLOW.equalsIgnoreCase(x.getMetaType())).collect(Collectors.toList());
        if (Objects.isNull(groupInfoMap.get(KEY_NAME))) {
            return false;
        }
        if (Objects.isNull(billMap.get(bizName)) || !StringUtils.hasText(billMap.get(bizName))) {
            MetaTreeInfoDTO metaTreeInfoDTO = metaDatas.stream().filter(x -> bizName.equalsIgnoreCase(x.getUniqueCode())).findAny().orElse(null);
            if (Objects.nonNull(metaTreeInfoDTO)) {
                billMap.put(bizName, metaTreeInfoDTO.getTitle());
                billWorkflowNodeVO.setBizTitle(metaTreeInfoDTO.getTitle());
                flag = true;
            }
        } else {
            billWorkflowNodeVO.setBizTitle(billMap.get(bizName));
            flag = true;
        }
        if (Objects.isNull(workflowMap.get(workflowName)) || !StringUtils.hasText(workflowMap.get(workflowName))) {
            MetaInfoDim metaInfoDim = workflowMetas.stream().filter(x -> workflowName.equalsIgnoreCase(x.getUniqueCode())).findAny().orElse(null);
            if (Objects.nonNull(metaInfoDim)) {
                workflowMap.put(workflowName, metaInfoDim.getTitle());
                billWorkflowNodeVO.setWorkflowTitle(metaInfoDim.getTitle());
                flag = true;
            }
        } else {
            billWorkflowNodeVO.setWorkflowTitle(workflowMap.get(workflowName));
            flag = true;
        }
        return flag;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Map<String, String> getGroupInfo(String uniqueCode, List<? extends MetaTreeInfoDTO> allMetaDatas, String type, String code) {
        MetaTreeInfoDTO metaDataInfo = allMetaDatas.stream().filter(x -> uniqueCode.equalsIgnoreCase(x.getUniqueCode())).findAny().orElse(null);
        if (Objects.isNull(metaDataInfo)) {
            return Collections.emptyMap();
        }
        String moduleName = metaDataInfo.getModuleName();
        List metaDataList = allMetaDatas.stream().filter(x -> MetaType.MODULE == x.getType() || moduleName.equals(x.getModuleName())).collect(Collectors.toList());
        ArrayList<MetaTreeInfoDTO> parentMetaDataList = new ArrayList<MetaTreeInfoDTO>();
        parentMetaDataList.add(metaDataInfo);
        this.dealParentMetaData(parentMetaDataList, metaDataInfo, metaDataList);
        HashMap<String, String> map = new HashMap<String, String>();
        Collections.reverse(parentMetaDataList);
        List parentUniqueCodes = parentMetaDataList.stream().map(x -> x.getType() + "#" + x.getUniqueCode()).collect(Collectors.toList());
        List parentTitles = parentMetaDataList.stream().map(MetaTreeInfoDTO::getTitle).collect(Collectors.toList());
        if (MetaType.GROUP.toString().equalsIgnoreCase(type) && StringUtils.hasText(code)) {
            if (!parentUniqueCodes.contains(MetaType.GROUP + "#" + code)) return Collections.emptyMap();
            map.put(KEY_NAME, String.join((CharSequence)"/", parentUniqueCodes));
            map.put(KEY_TITLE, String.join((CharSequence)"/", parentTitles));
            return map;
        } else {
            map.put(KEY_NAME, String.join((CharSequence)"/", parentUniqueCodes));
            map.put(KEY_TITLE, String.join((CharSequence)"/", parentTitles));
        }
        return map;
    }

    private void dealParentMetaData(List<? super MetaTreeInfoDTO> parentMetaDataList, MetaTreeInfoDTO metaDataInfo, List<? extends MetaTreeInfoDTO> allMetaDatas) {
        MetaTreeInfoDTO groupMetaDataInfo;
        MetaType type = metaDataInfo.getType();
        String groupName = metaDataInfo.getGroupName();
        String parentName = metaDataInfo.getParentName();
        String moduleName = metaDataInfo.getModuleName();
        if (MetaType.METADATA == type && Objects.nonNull(groupMetaDataInfo = (MetaTreeInfoDTO)allMetaDatas.stream().filter(x -> MetaType.GROUP == x.getType() && metaDataInfo.getGroupName().equalsIgnoreCase(x.getName())).findAny().orElse(null))) {
            parentMetaDataList.add((MetaTreeInfoDTO)groupMetaDataInfo);
            this.dealParentMetaData(parentMetaDataList, groupMetaDataInfo, allMetaDatas);
        }
        if (MetaType.GROUP == type) {
            if (StringUtils.hasText(groupName)) {
                groupMetaDataInfo = allMetaDatas.stream().filter(x -> MetaType.GROUP == x.getType() && groupName.equalsIgnoreCase(x.getName())).findAny().orElse(null);
                if (Objects.nonNull(groupMetaDataInfo)) {
                    parentMetaDataList.add((MetaTreeInfoDTO)groupMetaDataInfo);
                    this.dealParentMetaData(parentMetaDataList, groupMetaDataInfo, allMetaDatas);
                }
            } else if (StringUtils.hasText(parentName)) {
                groupMetaDataInfo = allMetaDatas.stream().filter(x -> MetaType.GROUP == x.getType() && parentName.equalsIgnoreCase(x.getName())).findAny().orElse(null);
                if (Objects.nonNull(groupMetaDataInfo)) {
                    parentMetaDataList.add((MetaTreeInfoDTO)groupMetaDataInfo);
                    this.dealParentMetaData(parentMetaDataList, groupMetaDataInfo, allMetaDatas);
                }
            } else {
                MetaTreeInfoDTO moduleMetaDataInfo = allMetaDatas.stream().filter(x -> MetaType.MODULE == x.getType() && moduleName.equalsIgnoreCase(x.getName())).findAny().orElse(null);
                if (Objects.nonNull(moduleMetaDataInfo)) {
                    parentMetaDataList.add((MetaTreeInfoDTO)moduleMetaDataInfo);
                }
            }
        }
    }

    private void dealBusinessWorkflowTreeChildren(MetaTreeInfoDTO metaTreeInfoDTO, List<? super BusinessWorkflowTreeVO> children, List<? extends MetaTreeInfoDTO> groupMetas, List<? extends MetaTreeInfoDTO> metaDatas, List<? extends MetaInfoDim> workflowMetas, List<? extends WorkflowBusinessDO> workflowBusiness) {
        BusinessWorkflowTreeVO treeVO;
        ArrayList<BusinessWorkflowTreeVO> tempChildren;
        if (MetaType.MODULE == metaTreeInfoDTO.getType()) {
            List rootGroups = groupMetas.stream().filter(x -> metaTreeInfoDTO.getName().equals(x.getModuleName()) && ObjectUtils.isEmpty(x.getParentName())).collect(Collectors.toList());
            for (MetaTreeInfoDTO rootGroup : rootGroups) {
                BusinessWorkflowTreeVO treeVO2 = new BusinessWorkflowTreeVO();
                treeVO2.setId(rootGroup.getType() + "#" + rootGroup.getUniqueCode());
                treeVO2.setTenantName(rootGroup.getTenantName());
                treeVO2.setName(rootGroup.getName());
                treeVO2.setTitle(rootGroup.getTitle());
                treeVO2.setUniqueCode(rootGroup.getUniqueCode());
                treeVO2.setType(String.valueOf(rootGroup.getType()));
                treeVO2.setModuleName(rootGroup.getModuleName());
                treeVO2.setGroupName(rootGroup.getGroupName());
                tempChildren = new ArrayList();
                this.dealBusinessWorkflowTreeChildren(rootGroup, tempChildren, groupMetas, metaDatas, workflowMetas, workflowBusiness);
                treeVO2.setChildren(tempChildren);
                children.add(treeVO2);
            }
        }
        if (MetaType.GROUP == metaTreeInfoDTO.getType()) {
            List childGroups = groupMetas.stream().filter(x -> metaTreeInfoDTO.getName().equals(x.getParentName())).collect(Collectors.toList());
            for (Object childGroup : childGroups) {
                ArrayList<BusinessWorkflowTreeVO> tempChildren2 = new ArrayList<BusinessWorkflowTreeVO>();
                this.dealBusinessWorkflowTreeChildren((MetaTreeInfoDTO)childGroup, tempChildren2, groupMetas, metaDatas, workflowMetas, workflowBusiness);
                treeVO = new BusinessWorkflowTreeVO();
                treeVO.setId(childGroup.getType() + "#" + childGroup.getUniqueCode());
                treeVO.setTenantName(childGroup.getTenantName());
                treeVO.setName(childGroup.getName());
                treeVO.setTitle(childGroup.getTitle());
                treeVO.setUniqueCode(childGroup.getUniqueCode());
                treeVO.setType(String.valueOf(childGroup.getType()));
                treeVO.setModuleName(childGroup.getModuleName());
                treeVO.setGroupName(childGroup.getGroupName());
                treeVO.setChildren(tempChildren2);
                children.add(treeVO);
            }
            List metaInfos = metaDatas.stream().filter(x -> metaTreeInfoDTO.getName().equals(x.getGroupName())).collect(Collectors.toList());
            for (MetaTreeInfoDTO metaInfo : metaInfos) {
                tempChildren = new ArrayList<BusinessWorkflowTreeVO>();
                this.dealBusinessWorkflowTreeChildren(metaInfo, tempChildren, groupMetas, metaDatas, workflowMetas, workflowBusiness);
                BusinessWorkflowTreeVO treeVO3 = new BusinessWorkflowTreeVO();
                treeVO3.setId(metaInfo.getType() + "#" + metaInfo.getUniqueCode());
                treeVO3.setTenantName(metaInfo.getTenantName());
                treeVO3.setName(metaInfo.getName());
                treeVO3.setTitle(metaInfo.getTitle());
                treeVO3.setUniqueCode(metaInfo.getUniqueCode());
                treeVO3.setType(String.valueOf(metaInfo.getType()));
                treeVO3.setModuleName(metaInfo.getModuleName());
                treeVO3.setGroupName(metaInfo.getGroupName());
                treeVO3.setChildren(tempChildren);
                children.add(treeVO3);
            }
        }
        if (MetaType.METADATA == metaTreeInfoDTO.getType()) {
            List workflowBusinessList = workflowBusiness.stream().filter(x -> metaTreeInfoDTO.getUniqueCode().equals(x.getBusinesscode())).collect(Collectors.toList());
            for (WorkflowBusinessDO workflowBusinessDO : workflowBusinessList) {
                MetaInfoDim metaInfoDim = workflowMetas.stream().filter(x -> x.getUniqueCode().equals(workflowBusinessDO.getWorkflowdefinekey())).findAny().orElse(null);
                treeVO = new BusinessWorkflowTreeVO();
                treeVO.setId("WORKFLOW#" + workflowBusinessDO.getWorkflowdefinekey());
                treeVO.setTenantName(workflowBusinessDO.getTenantName());
                treeVO.setName(workflowBusinessDO.getWorkflowdefinekey());
                if (Objects.nonNull(metaInfoDim)) {
                    treeVO.setTitle(metaInfoDim.getTitle());
                }
                treeVO.setUniqueCode(workflowBusinessDO.getWorkflowdefinekey());
                treeVO.setType(WORKFLOW);
                treeVO.setModuleName(metaTreeInfoDTO.getModuleName());
                children.add(treeVO);
            }
        }
    }

    @Override
    public WorkflowBusinessDTO get(WorkflowBusinessDTO workflowBusinessDTO, boolean showTitle) {
        List<WorkflowBusinessDO> workflowBusinessList = !StringUtils.hasText(workflowBusinessDTO.getWorkflowdefinekey()) || workflowBusinessDTO.getWorkflowdefineversion() == null ? this.workflowBusinessDao.selectLatest(workflowBusinessDTO) : this.workflowBusinessDao.select(workflowBusinessDTO);
        if (workflowBusinessDTO.isDesignFlag()) {
            List<WorkflowBusinessDO> draftDOList = workflowBusinessDTO.getWorkflowdefineversion() == null ? this.workflowBusinessRelDraftDao.selectLatestList(workflowBusinessDTO) : this.workflowBusinessRelDraftDao.select(workflowBusinessDTO);
            this.overrideByDesignTimeData(workflowBusinessList, draftDOList, true);
        }
        if (CollectionUtils.isEmpty(workflowBusinessList)) {
            return null;
        }
        if (workflowBusinessDTO.isDesignFlag()) {
            workflowBusinessList = WorkflowBusinessServiceImpl.sortByDesignState(workflowBusinessList);
        }
        return this.createResult(workflowBusinessDTO, showTitle, workflowBusinessList, false);
    }

    @Override
    public WorkflowBusinessDTO getBusiness(WorkflowBusinessDTO workflowBusinessDTO, boolean showTitle) {
        Utils.setTraceId((String)workflowBusinessDTO.getTraceId());
        List<WorkflowBusinessDO> workflowBusinessList = workflowBusinessDTO.getWorkflowdefineversion() == null ? this.workflowBusinessDao.selectLatest(workflowBusinessDTO) : this.workflowBusinessDao.select(workflowBusinessDTO);
        return this.createBusinessResult(workflowBusinessDTO, showTitle, workflowBusinessList);
    }

    @Override
    public WorkflowBusinessDTO getVersions(WorkflowBusinessDTO workflowBusinessDTO, boolean showTitle) {
        List<WorkflowBusinessDO> workflowBusinessList = this.workflowBusinessDao.select(workflowBusinessDTO);
        List<WorkflowBusinessDO> draftDOList = this.workflowBusinessRelDraftDao.select(workflowBusinessDTO);
        this.overrideByDesignTimeData(workflowBusinessList, draftDOList, false);
        if (CollectionUtils.isEmpty(workflowBusinessList)) {
            return null;
        }
        return this.createResult(workflowBusinessDTO, showTitle, workflowBusinessList, false);
    }

    private WorkflowBusinessDTO createBusinessResult(WorkflowBusinessDTO workflowBusinessDTO, boolean showTitle, List<WorkflowBusinessDO> workflowBusinessList) {
        if (workflowBusinessDTO.isDesignFlag()) {
            List<WorkflowBusinessDO> draftDOList = this.workflowBusinessRelDraftDao.select(workflowBusinessDTO);
            this.overrideByDesignTimeData(workflowBusinessList, draftDOList, true);
        }
        if (CollectionUtils.isEmpty(workflowBusinessList)) {
            return null;
        }
        if (workflowBusinessDTO.isDesignFlag()) {
            workflowBusinessList = WorkflowBusinessServiceImpl.sortByDesignState(workflowBusinessList);
        }
        ArrayList businesses = new ArrayList();
        HashMap<String, UserDO> userCacheMap = new HashMap<String, UserDO>(workflowBusinessList.size());
        Map<String, MetaInfoDim> allBillDefineInfoMap = VaWorkFlowDataUtils.getAllBillDefineInfoMap();
        for (WorkflowBusinessDO workflowBusinessDO : workflowBusinessList) {
            String businesscode = workflowBusinessDO.getBusinesscode();
            String businesstype = workflowBusinessDO.getBusinesstype();
            String designdata = workflowBusinessDO.getDesigndata();
            String businesstitle = "";
            MetaInfoDim billDefineInfo = allBillDefineInfoMap.get(businesscode);
            if ("BILL".equalsIgnoreCase(businesstype) && billDefineInfo == null) continue;
            if (!StringUtils.hasText(designdata)) {
                designdata = workflowBusinessDO.getConfig();
            }
            HashMap<String, Object> businessJson = StringUtils.hasText(designdata) ? JSONUtil.parseMap((String)designdata) : new HashMap<String, Object>();
            businessJson.put("businesscode", businesscode);
            businessJson.put(KEY_BUSINESS_TYPE, businesstype);
            businessJson.put("designstate", workflowBusinessDO.getDesignstate() == null ? 0 : workflowBusinessDO.getDesignstate());
            businessJson.put("lockflag", workflowBusinessDO.getLockflag());
            businessJson.put("lockusername", VaWorkFlowDataUtils.getUserDoWithCache(workflowBusinessDO.getLockuser(), userCacheMap).getUsername());
            if (showTitle) {
                if ("BILL".equalsIgnoreCase(businesstype)) {
                    businessJson.put("group", billDefineInfo.getGroupName());
                    businessJson.put("moduleName", billDefineInfo.getModuleName());
                    businesstitle = billDefineInfo.getTitle();
                } else {
                    BussinessClient bussinessClient = VaWorkflowUtils.getDynamicFeignClient(BussinessClient.class, this.bizTypeConfig, businesstype);
                    TenantDO tenantDO = new TenantDO();
                    tenantDO.setTraceId(Utils.getTraceId());
                    tenantDO.addExtInfo(KEY_BIZ_DEFINE, (Object)businesscode);
                    R bizTitle = bussinessClient.getBizTitle(tenantDO);
                    if (bizTitle.getCode() == 0) {
                        businesstitle = (String)bizTitle.get((Object)"bizDefineTitle");
                    }
                }
                businessJson.put("businesstitle", businesstitle);
            }
            ArrayList jsonArray = new ArrayList();
            String unitcodeString = (String)businessJson.get("unitcode");
            if (StringUtils.hasText(unitcodeString)) {
                String[] unitcodes;
                for (String unitcode : unitcodes = unitcodeString.split(",")) {
                    HashMap<String, String> jsonObject = new HashMap<String, String>();
                    if (showTitle) {
                        jsonObject.put("unitcode", unitcode);
                        OrgDO orgData = Optional.ofNullable(VaWorkFlowDataUtils.getOrgData(workflowBusinessDTO.getTenantName(), unitcode)).orElse(new OrgDO());
                        jsonObject.put("unittitle", orgData.getName());
                        jsonArray.add(jsonObject);
                        continue;
                    }
                    jsonObject.put("unitcode", unitcode);
                    jsonArray.add(jsonObject);
                }
            }
            businessJson.put("unitcode", jsonArray);
            businesses.add(businessJson);
        }
        workflowBusinessDTO.setBusinesses(businesses);
        return workflowBusinessDTO;
    }

    private WorkflowBusinessDTO createResult(WorkflowBusinessDTO workflowBusinessDTO, boolean showTitle, List<WorkflowBusinessDO> workflowBusinessList, boolean showBizDefine) {
        ArrayList workflowMetaRelations = null;
        String traceId = workflowBusinessDTO.getTraceId();
        if (StringUtils.hasText(workflowBusinessDTO.getWorkflowdefinekey())) {
            TenantDO tenantDO = new TenantDO();
            tenantDO.addExtInfo("defineCode", (Object)workflowBusinessDTO.getWorkflowdefinekey());
            tenantDO.setTraceId(traceId);
            workflowMetaRelations = new ArrayList(Optional.ofNullable(this.workflowMetaDao.getWorkflowMetaRelation(tenantDO)).orElse(Collections.emptyList()));
        }
        ArrayList workflows = new ArrayList();
        HashMap<String, UserDO> userCacheMap = new HashMap<String, UserDO>(workflowBusinessList.size());
        Map<String, MetaInfoDim> workflowDefineInfoMap = VaWorkFlowDataUtils.getAllWorkflowDefineInfoMap();
        for (WorkflowBusinessDO workflowBusinessDO : workflowBusinessList) {
            String workflowdefinekey = workflowBusinessDO.getWorkflowdefinekey();
            String designdata = workflowBusinessDO.getDesigndata();
            MetaInfoDim workflowDefineInfo = workflowDefineInfoMap.get(workflowdefinekey);
            if (workflowDefineInfo == null) continue;
            if (!StringUtils.hasText(designdata)) {
                designdata = workflowBusinessDO.getConfig();
            }
            HashMap<String, Object> workflowInfo = StringUtils.hasText(designdata) ? JSONUtil.parseMap((String)designdata) : new HashMap<String, Object>();
            workflowInfo.put("workflowdefinekey", workflowBusinessDO.getWorkflowdefinekey());
            workflowInfo.put("workflowdefineversion", workflowBusinessDO.getWorkflowdefineversion());
            workflowInfo.put("designstate", workflowBusinessDO.getDesignstate() == null ? 0 : workflowBusinessDO.getDesignstate());
            workflowInfo.put("lockflag", workflowBusinessDO.getLockflag());
            workflowInfo.put("lockusername", VaWorkFlowDataUtils.getUserDoWithCache(workflowBusinessDO.getLockuser(), userCacheMap).getUsername());
            if (showBizDefine) {
                workflowInfo.put("businesscode", workflowBusinessDO.getBusinesscode());
                workflowInfo.put("businesstye", workflowBusinessDO.getBusinesstype());
                workflowInfo.put("moduleName", workflowDefineInfo.getModuleName());
            }
            if (showTitle) {
                workflowInfo.put("workflowdefinetitle", workflowDefineInfo.getTitle());
                workflowInfo.put("group", workflowDefineInfo.getGroupName());
                workflowInfo.put("moduleName", workflowDefineInfo.getModuleName());
            }
            if (workflowMetaRelations != null) {
                for (Map workflowMetaRelation : workflowMetaRelations) {
                    if (!workflowBusinessDO.getWorkflowdefineversion().toString().equals(workflowMetaRelation.get("METAVERSION").toString())) continue;
                    workflowInfo.put("simpleworkflowdefineversion", workflowMetaRelation.get("WORKFLOWDEFINEVERSION"));
                    break;
                }
            } else {
                TenantDO tenantDO = new TenantDO();
                tenantDO.setTraceId(traceId);
                tenantDO.addExtInfo("workflowDefineKey", (Object)workflowBusinessDO.getWorkflowdefinekey());
                tenantDO.addExtInfo("metaVersion", (Object)workflowBusinessDO.getWorkflowdefineversion());
                Integer version = this.workflowMetaDao.getworkflowDefineVersion(tenantDO);
                workflowInfo.put("simpleworkflowdefineversion", version);
            }
            ArrayList jsonArray = new ArrayList();
            ArrayList<String> unitcodeList = new ArrayList<String>();
            String unitcodeString = (String)workflowInfo.get("unitcode");
            if (StringUtils.hasText(unitcodeString)) {
                String[] unitcodes;
                for (String unitcode : unitcodes = unitcodeString.split(",")) {
                    if (showTitle) {
                        HashMap<String, String> jsonObject = new HashMap<String, String>();
                        jsonObject.put("unitcode", unitcode);
                        OrgDO orgData = Optional.ofNullable(VaWorkFlowDataUtils.getOrgData(workflowBusinessDTO.getTenantName(), unitcode)).orElse(new OrgDO());
                        jsonObject.put("unittitle", orgData.getName());
                        jsonArray.add(jsonObject);
                        continue;
                    }
                    unitcodeList.add(unitcode);
                }
            }
            if (CollectionUtils.isEmpty(jsonArray)) {
                workflowInfo.put("unitcode", unitcodeList);
            } else {
                workflowInfo.put("unitcode", jsonArray);
            }
            workflows.add(workflowInfo);
        }
        workflowBusinessDTO.setWorkflows(workflows);
        return workflowBusinessDTO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public R save(WorkflowBusinessDTO workflowBusinessDTO) {
        List workflows = workflowBusinessDTO.getWorkflows();
        String businessCode = workflowBusinessDTO.getBusinesscode();
        String bizType = workflowBusinessDTO.getBusinesstype();
        for (Map workflowInfo : workflows) {
            WorkflowBusinessDO updateDraftDO;
            String workflowDefineKey = (String)workflowInfo.get("workflowdefinekey");
            Long workflowDefineVersion = (Long)workflowInfo.get("workflowdefineversion");
            List unitcodeArray = (List)workflowInfo.get("unitcode");
            if (unitcodeArray != null && unitcodeArray.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < unitcodeArray.size(); ++i) {
                    Map unitJsonObject = (Map)unitcodeArray.get(i);
                    sb.append(unitJsonObject.get("unitcode"));
                    sb.append(",");
                }
                sb.setLength(sb.length() - 1);
                workflowInfo.put("unitcode", sb.toString());
            } else {
                workflowInfo.put("unitcode", "");
            }
            workflowInfo.remove("workflowdefinekey");
            workflowInfo.remove("workflowdefineversion");
            workflowInfo.remove("simpleworkflowdefineversion");
            workflowInfo.remove("workflowdefinetitle");
            Integer designstate = (Integer)workflowInfo.remove("designstate");
            workflowInfo.remove("lockflag");
            workflowInfo.remove("lockusername");
            String designData = JSONUtil.toJSONString((Object)workflowInfo);
            WorkflowBusinessDTO selectDTO = new WorkflowBusinessDTO();
            selectDTO.setBusinesscode(businessCode);
            selectDTO.setWorkflowdefinekey(workflowDefineKey);
            selectDTO.setWorkflowdefineversion(workflowDefineVersion);
            List<WorkflowBusinessDO> workflowBusinessDOList = this.workflowBusinessDao.select(selectDTO);
            String currentUserId = ShiroUtil.getUser().getId();
            Long version = System.currentTimeMillis();
            int designState = MetaState.APPENDED.getValue();
            int lockFlag = 0;
            if (!CollectionUtils.isEmpty(workflowBusinessDOList)) {
                WorkflowBusinessDO oldWorkflowBusinessDO = workflowBusinessDOList.get(0);
                lockFlag = oldWorkflowBusinessDO.getLockflag();
                if (lockFlag == 1 && !currentUserId.equals(oldWorkflowBusinessDO.getLockuser())) {
                    throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.hasbeenlocked") + "\uff1a" + this.getBindingRelationTitle(businessCode, workflowDefineKey));
                }
                version = oldWorkflowBusinessDO.getRelversion();
                designState = MetaState.MODIFIED.getValue();
            }
            if ((updateDraftDO = this.workflowBusinessRelDraftDao.selectOne((WorkflowBusinessDO)selectDTO)) == null) {
                int currentState;
                int n = currentState = designstate == null ? 0 : designstate;
                if (currentState == MetaState.MODIFIED.getValue()) {
                    throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.datahaschanged") + "\uff1a" + this.getBindingRelationTitle(businessCode, workflowDefineKey));
                }
                WorkflowBusinessDO insertDraftDO = new WorkflowBusinessDO();
                UUID newId = UUID.randomUUID();
                insertDraftDO.setId(newId);
                insertDraftDO.setBusinesscode(businessCode);
                insertDraftDO.setWorkflowdefinekey(workflowDefineKey);
                insertDraftDO.setWorkflowdefineversion(workflowDefineVersion);
                insertDraftDO.setModifytime(new Date());
                insertDraftDO.setRelversion(version);
                insertDraftDO.setDesignstate(Integer.valueOf(designState));
                insertDraftDO.setBusinesstype(bizType);
                insertDraftDO.setLockflag(Integer.valueOf(lockFlag));
                insertDraftDO.setModulename((String)workflowInfo.get("moduleName"));
                this.workflowBusinessRelDraftDao.insert(insertDraftDO);
                WorkflowBusinessRelDesignDO insertDesignDO = new WorkflowBusinessRelDesignDO();
                insertDesignDO.setId(newId);
                insertDesignDO.setDesignData(designData);
                this.workflowBusinessRelDesignDao.insert(insertDesignDO);
                continue;
            }
            if (updateDraftDO.getLockflag() == 1 && !currentUserId.equals(updateDraftDO.getLockuser())) {
                throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.hasbeenlocked") + "\uff1a" + this.getBindingRelationTitle(businessCode, workflowDefineKey));
            }
            updateDraftDO.setModifytime(new Date());
            this.workflowBusinessRelDraftDao.update(updateDraftDO);
            WorkflowBusinessRelDesignDO updateDesignDO = new WorkflowBusinessRelDesignDO();
            updateDesignDO.setId(updateDraftDO.getId());
            updateDesignDO.setDesignData(designData);
            this.workflowBusinessRelDesignDao.updateByPrimaryKey(updateDesignDO);
        }
        return R.ok();
    }

    @Override
    public R batchSave(WorkflowBusinessDTO workflowBusinessDTO) {
        List businessWorkflowMapList = workflowBusinessDTO.getWorkflows();
        if (CollectionUtils.isEmpty(businessWorkflowMapList)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        ArrayList saveResult = new ArrayList();
        String currentUserId = ShiroUtil.getUser().getId();
        for (Map businessWorkflowMap : businessWorkflowMapList) {
            HashMap<String, String> resultMap = new HashMap<String, String>(4);
            String result = VaWorkFlowI18nUtils.getInfo("va.workflow.success");
            String businessCode = (String)businessWorkflowMap.remove("businesscode");
            String workflowDefineKey = (String)businessWorkflowMap.remove("workflowdefinekey");
            resultMap.put("businesscode", businessCode);
            resultMap.put("workflowdefinekey", workflowDefineKey);
            try {
                List unitcodeArray = (List)businessWorkflowMap.get("unitcode");
                if (unitcodeArray != null && unitcodeArray.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < unitcodeArray.size(); ++i) {
                        Map unitJsonObject = (Map)unitcodeArray.get(i);
                        sb.append(unitJsonObject.get("unitcode"));
                        sb.append(",");
                    }
                    sb.setLength(sb.length() - 1);
                    businessWorkflowMap.put("unitcode", sb.toString());
                } else {
                    businessWorkflowMap.put("unitcode", "");
                }
                businessWorkflowMap.remove("simpleworkflowdefineversion");
                businessWorkflowMap.remove("workflowdefinetitle");
                businessWorkflowMap.remove("lockflag");
                businessWorkflowMap.remove("lockusername");
                Long workflowDefineVersion = (Long)businessWorkflowMap.remove("workflowdefineversion");
                Integer designstate = (Integer)businessWorkflowMap.remove("designstate");
                WorkflowBusinessDTO selectDTO = new WorkflowBusinessDTO();
                selectDTO.setBusinesscode(businessCode);
                selectDTO.setWorkflowdefinekey(workflowDefineKey);
                selectDTO.setWorkflowdefineversion(workflowDefineVersion);
                WorkflowBusinessDO workflowBusinessDO = designstate.intValue() == MetaState.DEPLOYED.getValue() ? this.workflowBusinessDao.selectOne(selectDTO) : this.workflowBusinessRelDraftDao.selectOne((WorkflowBusinessDO)selectDTO);
                if (workflowBusinessDO == null) {
                    throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.datahaschanged"));
                }
                Integer lockFlag = workflowBusinessDO.getLockflag();
                if (lockFlag == 1 && !currentUserId.equals(workflowBusinessDO.getLockuser())) {
                    throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.hasbeenlocked"));
                }
                workflowBusinessDO.setDesigndata(JSONUtil.toJSONString((Object)businessWorkflowMap));
                this.workflowBusinessSaveService.save(workflowBusinessDO);
            }
            catch (Exception e) {
                result = VaWorkFlowI18nUtils.getInfo("va.workflow.failed") + e.getMessage();
            }
            resultMap.put("result", result);
            saveResult.add(resultMap);
        }
        R r = R.ok();
        r.put("data", saveResult);
        return r;
    }

    private void deleteKeyFromRedisCache(String key, Object value) {
        if (Boolean.TRUE.equals(this.redisTemplate.hasKey((Object)key))) {
            this.redisTemplate.opsForSet().remove((Object)key, new Object[]{value});
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public R saveBusiness(WorkflowBusinessDTO workflowBusinessDTO) {
        List businesses = workflowBusinessDTO.getBusinesses();
        String workflowDefineKey = workflowBusinessDTO.getWorkflowdefinekey();
        for (Map businessesInfo : businesses) {
            WorkflowBusinessDO updateDraftDO;
            String businessCode = (String)businessesInfo.get("businesscode");
            workflowBusinessDTO.setBusinesscode(businessCode);
            List unitcodeArray = (List)businessesInfo.get("unitcode");
            if (unitcodeArray != null && !unitcodeArray.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < unitcodeArray.size(); ++i) {
                    Map unitJsonObject = (Map)unitcodeArray.get(i);
                    sb.append(unitJsonObject.get("unitcode"));
                    sb.append(",");
                }
                sb.setLength(sb.length() - 1);
                businessesInfo.put("unitcode", sb.toString());
            } else {
                businessesInfo.put("unitcode", "");
            }
            businessesInfo.remove("businesscode");
            businessesInfo.remove("businesstitle");
            Integer designstate = (Integer)businessesInfo.remove("designstate");
            businessesInfo.remove("lockflag");
            businessesInfo.remove("lockusername");
            String designData = JSONUtil.toJSONString((Object)businessesInfo);
            String currentUserId = ShiroUtil.getUser().getId();
            List<WorkflowBusinessDO> workflowBusinessDOList = this.workflowBusinessDao.select(workflowBusinessDTO);
            Long version = System.currentTimeMillis();
            int designState = MetaState.APPENDED.getValue();
            int lockFlag = 0;
            if (!CollectionUtils.isEmpty(workflowBusinessDOList)) {
                WorkflowBusinessDO oldWorkflowBusinessDO = workflowBusinessDOList.get(0);
                lockFlag = oldWorkflowBusinessDO.getLockflag();
                if (lockFlag == 1 && !currentUserId.equals(oldWorkflowBusinessDO.getLockuser())) {
                    throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.hasbeenlocked") + "\uff1a" + this.getBindingRelationTitle(businessCode, workflowDefineKey));
                }
                version = oldWorkflowBusinessDO.getRelversion();
                designState = MetaState.MODIFIED.getValue();
            }
            if ((updateDraftDO = this.workflowBusinessRelDraftDao.selectOne((WorkflowBusinessDO)workflowBusinessDTO)) == null) {
                int currentState;
                int n = currentState = designstate == null ? 0 : designstate;
                if (currentState == MetaState.MODIFIED.getValue()) {
                    throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.datahaschanged") + "\uff1a" + this.getBindingRelationTitle(businessCode, workflowDefineKey));
                }
                WorkflowBusinessDO insertDraftDO = new WorkflowBusinessDO();
                UUID newId = UUID.randomUUID();
                insertDraftDO.setId(newId);
                insertDraftDO.setBusinesscode(businessCode);
                insertDraftDO.setWorkflowdefinekey(workflowDefineKey);
                insertDraftDO.setWorkflowdefineversion(workflowBusinessDTO.getWorkflowdefineversion());
                insertDraftDO.setModifytime(new Date());
                insertDraftDO.setRelversion(version);
                insertDraftDO.setDesignstate(Integer.valueOf(designState));
                insertDraftDO.setLockflag(Integer.valueOf(lockFlag));
                insertDraftDO.setBusinesstype(((String)businessesInfo.get(KEY_BUSINESS_TYPE)).toUpperCase());
                insertDraftDO.setModulename((String)businessesInfo.get("moduleName"));
                this.workflowBusinessRelDraftDao.insert(insertDraftDO);
                WorkflowBusinessRelDesignDO insertDesignDO = new WorkflowBusinessRelDesignDO();
                insertDesignDO.setId(newId);
                insertDesignDO.setDesignData(designData);
                this.workflowBusinessRelDesignDao.insert(insertDesignDO);
                continue;
            }
            if (updateDraftDO.getLockflag() == 1 && !currentUserId.equals(updateDraftDO.getLockuser())) {
                throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.hasbeenlocked") + "\uff1a" + this.getBindingRelationTitle(businessCode, workflowDefineKey));
            }
            updateDraftDO.setModifytime(new Date());
            this.workflowBusinessRelDraftDao.update(updateDraftDO);
            WorkflowBusinessRelDesignDO updateDesignDO = new WorkflowBusinessRelDesignDO();
            updateDesignDO.setId(updateDraftDO.getId());
            updateDesignDO.setDesignData(designData);
            this.workflowBusinessRelDesignDao.updateByPrimaryKey(updateDesignDO);
        }
        return R.ok();
    }

    private String getBindingRelationTitle(String businessCode, String workflowDefineKey) {
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("defineCode", (Object)businessCode);
        R r = this.metaDataClient.findMetaInfoByDefineCode(tenantDO);
        String billTitle = r.getCode() == 0 ? (String)r.get((Object)KEY_TITLE) : businessCode;
        tenantDO.addExtInfo("defineCode", (Object)workflowDefineKey);
        r = this.metaDataClient.findMetaInfoByDefineCode(tenantDO);
        String workflowDefineKeyTitle = r.getCode() == 0 ? (String)r.get((Object)KEY_TITLE) : businessCode;
        return billTitle + "&&" + workflowDefineKeyTitle;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void delete(WorkflowBusinessDTO workflowBusinessDTO) {
        List<WorkflowBusinessDO> runtimeDOList = this.workflowBusinessDao.select(workflowBusinessDTO);
        String currentUser = ShiroUtil.getUser().getId();
        if (CollectionUtils.isEmpty(runtimeDOList)) {
            WorkflowBusinessDO draftDO = this.workflowBusinessRelDraftDao.selectOne((WorkflowBusinessDO)workflowBusinessDTO);
            if (draftDO == null) {
                throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.datahaschanged"));
            }
            Integer lockFlag = draftDO.getLockflag();
            String lockUser = draftDO.getLockuser();
            if (lockFlag == 1 && !lockUser.equals(currentUser)) {
                throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.hasbeenlocked"));
            }
            this.workflowBusinessRelDraftDao.delete(draftDO);
            WorkflowBusinessRelDesignDO deleteDO = new WorkflowBusinessRelDesignDO();
            deleteDO.setId(draftDO.getId());
            this.workflowBusinessRelDesignDao.delete(deleteDO);
        } else {
            WorkflowBusinessDO runtimeDO = runtimeDOList.get(0);
            Integer lockFlag = runtimeDO.getLockflag();
            String lockUser = runtimeDO.getLockuser();
            if (lockFlag == 1 && !lockUser.equals(currentUser)) {
                throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.hasbeenlocked"));
            }
            WorkflowBusinessDO draftDO = this.workflowBusinessRelDraftDao.selectOne((WorkflowBusinessDO)workflowBusinessDTO);
            if (draftDO == null) {
                WorkflowBusinessDO insertDO = runtimeDOList.get(0);
                UUID id = UUID.randomUUID();
                insertDO.setId(id);
                insertDO.setDesignstate(Integer.valueOf(MetaState.DELETED.getValue()));
                insertDO.setModifytime(new Date());
                this.workflowBusinessRelDraftDao.insert(insertDO);
                WorkflowBusinessRelDesignDO insertDesignDO = new WorkflowBusinessRelDesignDO();
                insertDesignDO.setId(id);
                String designData = insertDO.getDesigndata();
                if (!StringUtils.hasText(designData)) {
                    designData = insertDO.getConfig();
                }
                insertDesignDO.setDesignData(designData);
                this.workflowBusinessRelDesignDao.insert(insertDesignDO);
            } else {
                draftDO.setDesignstate(Integer.valueOf(MetaState.DELETED.getValue()));
                draftDO.setModifytime(new Date());
                this.workflowBusinessRelDraftDao.update(draftDO);
            }
        }
    }

    @Override
    public WorkflowBusinessPublishVO update(WorkflowBusinessDTO workflowBusinessDTO) {
        int designState;
        WorkflowBusinessPublishVO publishVO = new WorkflowBusinessPublishVO();
        ArrayList<Long> successList = new ArrayList<Long>();
        ArrayList failedList = new ArrayList();
        publishVO.setSuccessData(successList);
        publishVO.setFailedData(failedList);
        List versions = workflowBusinessDTO.getVersions();
        if (CollectionUtils.isEmpty(versions)) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        String redisKeyPrefix = "BILL_WORKFLOW_RELATION_VARIABLES:" + ShiroUtil.getTenantName() + ":";
        WorkflowBusinessDO currentDO = null;
        int n = designState = workflowBusinessDTO.getDesignstate() == null ? MetaState.DEPLOYED.getValue() : workflowBusinessDTO.getDesignstate().intValue();
        if (designState == MetaState.MODIFIED.getValue()) {
            currentDO = this.workflowBusinessRelDraftDao.selectOneWithDesignData((WorkflowBusinessDO)workflowBusinessDTO);
        } else if (designState == MetaState.DEPLOYED.getValue()) {
            currentDO = this.workflowBusinessDao.selectOneWithDesignData(workflowBusinessDTO);
        }
        if (currentDO == null) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.datahaschanged"));
        }
        if (!StringUtils.hasText(currentDO.getDesigndata())) {
            currentDO.setDesigndata(currentDO.getConfig());
        }
        for (Long version : versions) {
            WorkflowBusinessDO targetDO = null;
            try {
                targetDO = this.workflowBusinessPublishService.doUpdate(currentDO, version);
                successList.add(version);
            }
            catch (RuntimeException e) {
                HashMap<String, Object> failedMap = new HashMap<String, Object>(2);
                log.error("\u4e1a\u52a1\u4e0e\u5de5\u4f5c\u6d41\u7ed1\u5b9a\u66f4\u65b0\u5931\u8d25\uff0c", e);
                failedMap.put("msg", e.getMessage());
                failedMap.put("version", version);
                failedList.add(failedMap);
            }
            if (targetDO == null) continue;
            String redisKey = targetDO.getWorkflowdefinekey() + "-" + targetDO.getBusinesscode();
            String redisValue = String.valueOf(targetDO.getWorkflowdefineversion());
            this.deleteKeyFromRedisCache(redisKeyPrefix + redisKey, redisValue);
        }
        if (MetaState.MODIFIED.getValue() == designState && failedList.isEmpty()) {
            WorkflowBusinessRelDesignDO deleteDO = new WorkflowBusinessRelDesignDO();
            this.workflowBusinessRelDraftDao.delete(currentDO);
            deleteDO.setId(currentDO.getId());
            this.workflowBusinessRelDesignDao.delete(deleteDO);
        }
        return publishVO;
    }

    @Override
    public PageVO<WorkflowBusinessRelation> getBindingRelationByOrg(WorkflowBusinessDTO workflowBusinessDTO) {
        String search;
        ArrayList<WorkflowBusinessRelation> resultList = new ArrayList<WorkflowBusinessRelation>();
        boolean pagination = workflowBusinessDTO.isPagination();
        if (pagination) {
            workflowBusinessDTO.setPagination(false);
        }
        int limit = workflowBusinessDTO.getLimit();
        int offset = workflowBusinessDTO.getOffset();
        List<WorkflowBusinessDO> workflowBusinessDOS = this.workflowBusinessDao.selectLatest(workflowBusinessDTO);
        List<MetaTreeInfoDTO> metaTreeInfoList = this.getMetaTreeInfoList();
        Map<String, String> defineCodeTitleMap = metaTreeInfoList.stream().filter(item -> MetaType.METADATA == item.getType()).collect(Collectors.toMap(MetaTreeInfoDTO::getUniqueCode, MetaTreeInfoDTO::getTitle, (o1, o2) -> o1));
        String unitCode = workflowBusinessDTO.getUnitcode();
        if (StringUtils.hasText(unitCode)) {
            this.filterByOrg(workflowBusinessDOS, unitCode);
        }
        if (StringUtils.hasText(search = workflowBusinessDTO.getSearch())) {
            this.filterBySearch(workflowBusinessDOS, search.toUpperCase(), defineCodeTitleMap);
        }
        Map<String, MetaTreeInfoDTO> metaTreeInfoMap = metaTreeInfoList.stream().collect(Collectors.toMap(o -> o.getType().name() + "_" + o.getUniqueCode(), o -> o));
        int total = workflowBusinessDOS.size();
        int size = Math.min(offset + limit, total);
        for (int i = offset; i < size; ++i) {
            WorkflowBusinessDO workflowBusinessDO = workflowBusinessDOS.get(i);
            WorkflowBusinessRelation workflowBusinessRelation = new WorkflowBusinessRelation();
            String workflowDefineKey = workflowBusinessDO.getWorkflowdefinekey();
            workflowBusinessRelation.setWorkflowDefineKey(workflowDefineKey);
            workflowBusinessRelation.setWorkflowDefineTitle(defineCodeTitleMap.get(workflowDefineKey));
            workflowBusinessRelation.setWorkflowDefineVersion(workflowBusinessDO.getWorkflowdefineversion());
            String[] workflowDefineFullPath = this.getMetaInfoFullPath(WorkflowBusinessRelationConst.MetaTypeEnum.WORKFLOW.getCode(), workflowDefineKey, metaTreeInfoMap);
            workflowBusinessRelation.setWorkflowDefineFullPath(workflowDefineFullPath[0]);
            workflowBusinessRelation.setWorkflowDefineFullPathTitle(workflowDefineFullPath[1]);
            workflowBusinessRelation.setBizType(workflowBusinessDO.getBusinesstype());
            String businessCode = workflowBusinessDO.getBusinesscode();
            workflowBusinessRelation.setBizDefine(businessCode);
            resultList.add(workflowBusinessRelation);
        }
        return new PageVO(resultList, total, R.ok());
    }

    private void filterByOrg(List<WorkflowBusinessDO> workflowBusinessDOS, String unitCode) {
        workflowBusinessDOS.removeIf(workflowBusinessDO -> this.needToRemove((WorkflowBusinessDO)workflowBusinessDO, unitCode));
    }

    private boolean needToRemove(WorkflowBusinessDO workflowBusinessDO, String unitCode) {
        String designData = workflowBusinessDO.getDesigndata();
        if (!StringUtils.hasText(designData)) {
            designData = workflowBusinessDO.getConfig();
        }
        if (!StringUtils.hasText(designData)) {
            return true;
        }
        Map designDataMap = JSONUtil.parseMap((String)designData);
        Object conditionView = designDataMap.get("conditionView");
        if (conditionView == null) {
            String unitCodeStr = (String)designDataMap.get("unitcode");
            if (StringUtils.hasText(unitCodeStr)) {
                List<String> unitCodeList = Arrays.asList(unitCodeStr.split(","));
                return !unitCodeList.contains(unitCode);
            }
            return false;
        }
        Map conditionViewMap = (Map)conditionView;
        if (conditionViewMap.isEmpty()) {
            return false;
        }
        String groupRelation = (String)conditionViewMap.get("groupRelation");
        List groupInfo = (List)conditionViewMap.get("groupInfo");
        ArrayList<ConditionGroupCheck> conditionGroupChecks = new ArrayList<ConditionGroupCheck>();
        HashMap<String, String> bizFieldMap = new HashMap<String, String>();
        for (Map groupItem : groupInfo) {
            List groupRowList = (List)groupItem.get("info");
            ArrayList<ConditionCheck> conditionRowChecks = new ArrayList<ConditionCheck>();
            for (Map rowItem : groupRowList) {
                ConditionGroupRow groupRow = (ConditionGroupRow)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)rowItem), ConditionGroupRow.class);
                ConditionRowAttr dimension = groupRow.getDimension();
                if (dimension == null || !"MD_ORG".equals(dimension.getName())) continue;
                bizFieldMap.put(groupRow.getBizField().getName(), unitCode);
                ConditionGroupRowCheck rowCheck = new ConditionGroupRowCheck(groupRow, this.context);
                conditionRowChecks.add(rowCheck);
            }
            if (CollectionUtils.isEmpty(conditionRowChecks)) continue;
            ConditionGroupCheck conditionGroupCheck = new ConditionGroupCheck(conditionRowChecks, this.relationshipMap.get(WorkflowOption.RelationType.AND.name()));
            conditionGroupChecks.add(conditionGroupCheck);
        }
        if (conditionGroupChecks.isEmpty()) {
            return false;
        }
        ConditionGroupCombinationCheck combination = new ConditionGroupCombinationCheck(conditionGroupChecks, this.relationshipMap.get(groupRelation));
        return !combination.check(bizFieldMap);
    }

    private List<MetaTreeInfoDTO> getMetaTreeInfoList() {
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("metaType", (Object)WorkflowBusinessRelationConst.MetaTypeEnum.WORKFLOW.getName());
        List allWorkflowMetas = this.metaDataClient.getAllWorkflowMetas(tenantDO).getRows();
        List allBillMetas = this.metaDataClient.getAllMetas(tenantDO).getRows();
        allWorkflowMetas.addAll(allBillMetas);
        return allWorkflowMetas;
    }

    private void filterBySearch(List<WorkflowBusinessDO> workflowBusinessDOS, String searchText, Map<String, String> defineCodeTitleMap) {
        ArrayList<WorkflowBusinessDO> removeList = new ArrayList<WorkflowBusinessDO>();
        for (WorkflowBusinessDO workflowBusinessDO : workflowBusinessDOS) {
            String workflowDefineKey = workflowBusinessDO.getWorkflowdefinekey();
            String workflowDefineKeyTitle = defineCodeTitleMap.get(workflowDefineKey);
            String businessCode = workflowBusinessDO.getBusinesscode();
            String bizType = workflowBusinessDO.getBusinesstype();
            String businessCodeTitle = null;
            if ("BILL".equals(bizType)) {
                businessCodeTitle = defineCodeTitleMap.get(businessCode);
            } else {
                BussinessClient bussinessClient = VaWorkflowUtils.getDynamicFeignClient(BussinessClient.class, this.bizTypeConfig, bizType);
                TenantDO tenantDO = new TenantDO();
                tenantDO.setTraceId(Utils.getTraceId());
                tenantDO.addExtInfo(KEY_BIZ_DEFINE, (Object)businessCode);
                R bizTitle = bussinessClient.getBizTitle(tenantDO);
                if (bizTitle.getCode() == 0) {
                    businessCodeTitle = (String)bizTitle.get((Object)"bizDefineTitle");
                }
            }
            businessCodeTitle = businessCodeTitle == null ? "" : businessCodeTitle;
            workflowDefineKeyTitle = workflowDefineKeyTitle == null ? "" : workflowDefineKeyTitle;
            boolean containsFlag = workflowDefineKey.contains(searchText) || workflowDefineKeyTitle.contains(searchText) || businessCode.contains(searchText) || businessCodeTitle.contains(searchText);
            if (containsFlag) continue;
            removeList.add(workflowBusinessDO);
        }
        workflowBusinessDOS.removeAll(removeList);
    }

    private String[] getMetaInfoFullPath(String metaType, String uniqueKey, Map<String, MetaTreeInfoDTO> metaTreeInfoDTOMap) {
        String key = MetaType.METADATA.name() + "_" + uniqueKey;
        MetaTreeInfoDTO metaInfoDTO = metaTreeInfoDTOMap.get(key);
        ArrayList path = new ArrayList();
        while (metaInfoDTO != null && MetaType.MODULE != metaInfoDTO.getType()) {
            ArrayList<String> list = new ArrayList<String>();
            list.add(metaInfoDTO.getName());
            list.add(metaInfoDTO.getTitle());
            path.add(list);
            String moduleName = metaInfoDTO.getModuleName();
            String parentName = metaInfoDTO.getParentName();
            String groupName = metaInfoDTO.getGroupName();
            String tmpStr = "_" + moduleName + "_" + metaType + "_";
            if (StringUtils.hasText(groupName)) {
                key = MetaType.GROUP.name() + tmpStr + groupName;
            } else if (StringUtils.hasText(parentName)) {
                key = MetaType.GROUP.name() + tmpStr + parentName;
            } else {
                metaType = WorkflowBusinessRelationConst.MetaTypeEnum.WORKFLOW.getCode().equals(metaType) ? WorkflowBusinessRelationConst.MetaTypeEnum.WORKFLOW.name() : WorkflowBusinessRelationConst.MetaTypeEnum.BILL.getCode();
                key = MetaType.MODULE.name() + "_" + moduleName + "_" + metaType;
            }
            if ((metaInfoDTO = metaTreeInfoDTOMap.get(key)) != null) continue;
            break;
        }
        String name = metaInfoDTO == null ? uniqueKey : metaInfoDTO.getName();
        String title = metaInfoDTO == null ? uniqueKey : metaInfoDTO.getTitle();
        StringBuilder fullPath = new StringBuilder(name);
        StringBuilder fullPathTitle = new StringBuilder(title);
        for (int i = path.size() - 1; i >= 0; --i) {
            List list = (List)path.get(i);
            fullPath.append("/").append((String)list.get(0));
            fullPathTitle.append("/").append((String)list.get(1));
        }
        return new String[]{fullPath.toString(), fullPathTitle.toString()};
    }

    @Override
    public WorkflowBusinessPublishVO getWorkflowBusinessPublishData(WorkflowBusinessDTO workflowBusinessDTO) {
        WorkflowBusinessPublishVO workflowBusinessPublishVO = new WorkflowBusinessPublishVO();
        ArrayList<WorkflowBusinessPublishItem> publishItemList = new ArrayList<WorkflowBusinessPublishItem>();
        workflowBusinessPublishVO.setDeployData(publishItemList);
        List<WorkflowBusinessDO> workflowBusinessDOList = this.workflowBusinessRelDraftDao.select(workflowBusinessDTO);
        if (CollectionUtils.isEmpty(workflowBusinessDOList)) {
            return workflowBusinessPublishVO;
        }
        String currentUser = ShiroUtil.getUser().getId();
        workflowBusinessDOList.removeIf(item -> 1 == item.getLockflag() && !currentUser.equals(item.getLockuser()));
        List<MetaTreeInfoDTO> metaTreeInfoList = this.getMetaTreeInfoList();
        workflowBusinessDOList = WorkflowBusinessServiceImpl.sortByDesignState(workflowBusinessDOList);
        Map<String, MetaTreeInfoDTO> metaTreeInfoMap = metaTreeInfoList.stream().collect(Collectors.toMap(o -> o.getType().name() + "_" + o.getUniqueCode(), o -> o));
        for (WorkflowBusinessDO workflowBusinessDO : workflowBusinessDOList) {
            WorkflowBusinessPublishItem publishItem = new WorkflowBusinessPublishItem();
            publishItem.setId(workflowBusinessDO.getId());
            Long workflowDefineVersion = workflowBusinessDO.getWorkflowdefineversion();
            publishItem.setWorkflowDefineVersion(workflowDefineVersion);
            TenantDO tenantDO = new TenantDO();
            tenantDO.addExtInfo("workflowDefineKey", (Object)workflowBusinessDO.getWorkflowdefinekey());
            tenantDO.addExtInfo("metaVersion", (Object)workflowDefineVersion);
            publishItem.setSimpleWorkflowDefineVersion(this.workflowMetaDao.getworkflowDefineVersion(tenantDO));
            publishItem.setDesignState(workflowBusinessDO.getDesignstate());
            publishItem.setWorkflowDefineFullPathTitle(this.getWorkflowDefineFullPathTitle(workflowBusinessDO, metaTreeInfoMap));
            publishItem.setBizType(workflowBusinessDO.getBusinesstype());
            publishItem.setBizDefine(workflowBusinessDO.getBusinesscode());
            publishItemList.add(publishItem);
        }
        return workflowBusinessPublishVO;
    }

    @Override
    public WorkflowBusinessDTO getWorkflowBusinessUpdateData(WorkflowBusinessDTO workflowBusinessDTO) {
        List<WorkflowBusinessDO> workflowBusinessList = this.workflowBusinessDao.select(workflowBusinessDTO);
        String currentUser = ShiroUtil.getUser().getId();
        workflowBusinessList.removeIf(item -> 1 == item.getLockflag() && !currentUser.equals(item.getLockuser()));
        String traceId = workflowBusinessDTO.getTraceId();
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("defineCode", (Object)workflowBusinessDTO.getWorkflowdefinekey());
        tenantDO.setTraceId(traceId);
        ArrayList workflowMetaRelations = new ArrayList(Optional.ofNullable(this.workflowMetaDao.getWorkflowMetaRelation(tenantDO)).orElse(Collections.emptyList()));
        Map<String, Map> metaVersionMap = workflowMetaRelations.stream().collect(Collectors.toMap(o -> o.get("METAVERSION").toString(), o -> o));
        ArrayList workflows = new ArrayList();
        for (WorkflowBusinessDO workflowBusinessDO : workflowBusinessList) {
            Long workflowDefineVersion = workflowBusinessDO.getWorkflowdefineversion();
            HashMap<String, Long> workflowInfo = new HashMap<String, Long>();
            workflowInfo.put("workflowdefineversion", workflowDefineVersion);
            workflowInfo.put("simpleworkflowdefineversion", (Long)metaVersionMap.get(String.valueOf(workflowDefineVersion)).get("WORKFLOWDEFINEVERSION"));
            workflows.add(workflowInfo);
        }
        workflowBusinessDTO.setWorkflows(workflows);
        return workflowBusinessDTO;
    }

    private String getWorkflowDefineFullPathTitle(WorkflowBusinessDO businessDO, Map<String, MetaTreeInfoDTO> metaTreeInfoMap) {
        String workflowDefineKey = businessDO.getWorkflowdefinekey();
        String[] path = this.getMetaInfoFullPath(WorkflowBusinessRelationConst.MetaTypeEnum.WORKFLOW.getCode(), workflowDefineKey, metaTreeInfoMap);
        return path[1] + "(" + workflowDefineKey + ")";
    }

    @Override
    public WorkflowBusinessPublishCheckResult publishCheck(WorkflowBusinessDTO workflowBusinessDTO) {
        WorkflowBusinessPublishCheckResult checkResult = new WorkflowBusinessPublishCheckResult();
        ArrayList<String> notSetAdaptionList = new ArrayList<String>();
        checkResult.setNotSetAdaptionList(notSetAdaptionList);
        List deployData = workflowBusinessDTO.getDeployData();
        workflowBusinessDTO.setDeployIds(deployData.stream().map(o -> o.getId().toString()).collect(Collectors.toList()));
        List<WorkflowBusinessRelDesignDO> designDOList = this.workflowBusinessRelDesignDao.queryByIdList(workflowBusinessDTO);
        Map<String, WorkflowBusinessRelDesignDO> idMap = designDOList.stream().collect(Collectors.toMap(o -> o.getId().toString(), o -> o));
        for (WorkflowBusinessPublishItem publishItem : deployData) {
            Object conditionView;
            String unitCodeStr;
            String warnInfo = publishItem.getBizDefineFullPathTitle() + "&&" + publishItem.getWorkflowDefineFullPathTitle();
            WorkflowBusinessRelDesignDO designDO = idMap.get(publishItem.getId().toString());
            if (designDO == null) continue;
            String designData = designDO.getDesignData();
            if (!StringUtils.hasText(designData)) {
                notSetAdaptionList.add(warnInfo);
                continue;
            }
            boolean notSetAdaptionFlag = true;
            Map designDataMap = JSONUtil.parseMap((String)designData);
            Map adaptConditionMap = (Map)designDataMap.get("adaptcondition");
            if (adaptConditionMap != null && StringUtils.hasText((String)adaptConditionMap.get("expression"))) {
                notSetAdaptionFlag = false;
            }
            if (StringUtils.hasText(unitCodeStr = (String)designDataMap.get("unitcode"))) {
                notSetAdaptionFlag = false;
            }
            if ((conditionView = designDataMap.get("conditionView")) instanceof Map) {
                Map conditionViewMap = (Map)conditionView;
                List groupInfoList = (List)conditionViewMap.get("groupInfo");
                for (Map groupInfo : groupInfoList) {
                    List groupRowList = (List)groupInfo.get("info");
                    for (Map row : groupRowList) {
                        ConditionGroupRow groupRow = (ConditionGroupRow)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)row), ConditionGroupRow.class);
                        if (ObjectUtils.isEmpty(groupRow.getValidValue()) && ObjectUtils.isEmpty(groupRow.getExcludeValue())) continue;
                        notSetAdaptionFlag = false;
                    }
                }
            }
            if (!notSetAdaptionFlag) continue;
            notSetAdaptionList.add(warnInfo);
        }
        return checkResult;
    }

    @Override
    public WorkflowBusinessPublishVO publish(WorkflowBusinessDTO workflowBusinessDTO) {
        WorkflowBusinessPublishVO publishVO = new WorkflowBusinessPublishVO();
        ArrayList<String> successList = new ArrayList<String>();
        ArrayList failedList = new ArrayList();
        publishVO.setSuccessData(successList);
        publishVO.setFailedData(failedList);
        List deployData = workflowBusinessDTO.getDeployData();
        if (CollectionUtils.isEmpty(deployData)) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        String redisKeyPrefix = "BILL_WORKFLOW_RELATION_VARIABLES:" + ShiroUtil.getTenantName() + ":";
        for (WorkflowBusinessPublishItem publishItem : deployData) {
            String title = publishItem.getBizDefineFullPathTitle() + "&&" + publishItem.getWorkflowDefineFullPathTitle();
            WorkflowBusinessDO designDO = null;
            try {
                designDO = this.workflowBusinessPublishService.doPublish(publishItem);
                successList.add(title);
            }
            catch (Exception e) {
                HashMap<String, String> failedMap = new HashMap<String, String>(2);
                log.error("\u4e1a\u52a1\u4e0e\u5de5\u4f5c\u6d41\u7ed1\u5b9a\u53d1\u5e03\u5931\u8d25\uff0c", e);
                failedMap.put("msg", e.getMessage());
                failedMap.put(KEY_TITLE, title);
                failedList.add(failedMap);
            }
            if (designDO == null) continue;
            String redisKey = designDO.getWorkflowdefinekey() + "-" + designDO.getBusinesscode();
            String redisValue = String.valueOf(designDO.getWorkflowdefineversion());
            this.deleteKeyFromRedisCache(redisKeyPrefix + redisKey, redisValue);
        }
        return publishVO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void updateLockFlag(WorkflowBusinessDTO workflowBusinessDTO) {
        List<WorkflowBusinessDO> workflowBusinessDOList;
        WorkflowBusinessDO workflowBusinessDO;
        String businessCode = workflowBusinessDTO.getBusinesscode();
        String workflowDefineKey = workflowBusinessDTO.getWorkflowdefinekey();
        if (!StringUtils.hasText(businessCode) || !StringUtils.hasText(workflowDefineKey)) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        Integer lockFlag = workflowBusinessDTO.getLockflag();
        if (Objects.equals(lockFlag, (workflowBusinessDO = CollectionUtils.isEmpty(workflowBusinessDOList = this.workflowBusinessDao.select(workflowBusinessDTO)) ? this.workflowBusinessRelDraftDao.selectOne((WorkflowBusinessDO)workflowBusinessDTO) : workflowBusinessDOList.get(0)).getLockflag())) {
            if (lockFlag == 1) {
                throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.hasbeenlocked"));
            }
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.hasbeenunlocked"));
        }
        this.workflowBusinessDao.update((WorkflowBusinessDO)workflowBusinessDTO);
        workflowBusinessDTO.setModifytime(new Date());
        this.workflowBusinessRelDraftDao.update((WorkflowBusinessDO)workflowBusinessDTO);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void reset(WorkflowBusinessDTO workflowBusinessDTO) {
        WorkflowBusinessDO workflowBusinessDO = this.workflowBusinessRelDraftDao.selectOne((WorkflowBusinessDO)workflowBusinessDTO);
        if (workflowBusinessDO == null) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.datahaschanged"));
        }
        String currentUserId = ShiroUtil.getUser().getId();
        if (workflowBusinessDO.getLockflag() == 1 && !currentUserId.equals(workflowBusinessDO.getLockuser())) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.hasbeenlocked"));
        }
        this.workflowBusinessRelDraftDao.delete(workflowBusinessDO);
        WorkflowBusinessRelDesignDO deleteDO = new WorkflowBusinessRelDesignDO();
        deleteDO.setId(workflowBusinessDO.getId());
        this.workflowBusinessRelDesignDao.delete(deleteDO);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void publishWithoutDesign(WorkflowBusinessDTO workflowBusinessDTO) {
        List businesses = workflowBusinessDTO.getBusinesses();
        String workflowDefineKey = workflowBusinessDTO.getWorkflowdefinekey();
        for (Map businessesInfo : businesses) {
            String businessCode = (String)businessesInfo.get("businesscode");
            workflowBusinessDTO.setBusinesscode(businessCode);
            List unitcodeArray = (List)businessesInfo.get("unitcode");
            if (unitcodeArray != null && !unitcodeArray.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < unitcodeArray.size(); ++i) {
                    Map unitJsonObject = (Map)unitcodeArray.get(i);
                    sb.append(unitJsonObject.get("unitcode"));
                    sb.append(",");
                }
                sb.setLength(sb.length() - 1);
                businessesInfo.put("unitcode", sb.toString());
            } else {
                businessesInfo.put("unitcode", "");
            }
            businessesInfo.remove("businesscode");
            businessesInfo.remove("businesstitle");
            businessesInfo.remove("designstate");
            businessesInfo.remove("lockflag");
            businessesInfo.remove("lockusername");
            String designData = JSONUtil.toJSONString((Object)businessesInfo);
            String currentUserId = ShiroUtil.getUser().getId();
            Long workflowdefineversion = workflowBusinessDTO.getWorkflowdefineversion();
            workflowBusinessDTO.setStopflag(Integer.valueOf(-1));
            List<WorkflowBusinessDO> list = this.workflowBusinessDao.select(workflowBusinessDTO);
            boolean addFlag = true;
            if (!CollectionUtils.isEmpty(list)) {
                for (WorkflowBusinessDO workflowBusinessDO : list) {
                    if (!Objects.equals(workflowdefineversion, workflowBusinessDO.getWorkflowdefineversion())) continue;
                    if (workflowBusinessDO.getStopflag() == 1) {
                        workflowBusinessDO.setWorkflowdefineversion(null);
                        workflowBusinessDO.setStopflag(Integer.valueOf(0));
                    } else if (workflowBusinessDO.getLockflag() == 1 && currentUserId.equals(workflowBusinessDO.getLockuser())) {
                        throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.hasbeenlocked") + "\uff1a" + this.getBindingRelationTitle(businessCode, workflowDefineKey));
                    }
                    workflowBusinessDO.setRelversion(Long.valueOf(System.currentTimeMillis()));
                    this.workflowBusinessDao.update(workflowBusinessDO);
                    WorkflowBusinessRelDesignDO designDO = new WorkflowBusinessRelDesignDO();
                    designDO.setId(workflowBusinessDO.getId());
                    WorkflowBusinessRelDesignDO selectDO = (WorkflowBusinessRelDesignDO)this.workflowBusinessRelDesignDao.selectOne(designDO);
                    designDO.setDesignData(designData);
                    if (selectDO == null) {
                        this.workflowBusinessRelDesignDao.insert(designDO);
                    } else {
                        this.workflowBusinessRelDesignDao.updateByPrimaryKey(designDO);
                    }
                    addFlag = false;
                    break;
                }
            }
            if (addFlag) {
                WorkflowBusinessDO insertDO = new WorkflowBusinessDO();
                UUID newId = UUID.randomUUID();
                insertDO.setId(newId);
                insertDO.setBusinesscode(businessCode);
                insertDO.setWorkflowdefinekey(workflowDefineKey);
                insertDO.setWorkflowdefineversion(workflowBusinessDTO.getWorkflowdefineversion());
                insertDO.setRelversion(Long.valueOf(System.currentTimeMillis()));
                insertDO.setDesignstate(Integer.valueOf(0));
                insertDO.setLockflag(Integer.valueOf(0));
                insertDO.setBusinesstype(((String)businessesInfo.get(KEY_BUSINESS_TYPE)).toUpperCase());
                insertDO.setModulename((String)businessesInfo.get("moduleName"));
                this.workflowBusinessDao.add(insertDO);
                WorkflowBusinessRelDesignDO insertDesignDO = new WorkflowBusinessRelDesignDO();
                insertDesignDO.setId(newId);
                insertDesignDO.setDesignData(designData);
                this.workflowBusinessRelDesignDao.insert(insertDesignDO);
            }
            String defineKey = workflowDefineKey + "-" + businessCode;
            this.deleteKeyFromRedisCache("BILL_WORKFLOW_RELATION_VARIABLES:" + ShiroUtil.getTenantName() + ":" + defineKey, String.valueOf(workflowdefineversion));
        }
    }

    @Override
    public List<Map<String, Object>> batchGetBusinessWorkflowBindingInfo(WorkflowBusinessDTO workflowBusinessDTO) {
        List businessesWorkflowList = workflowBusinessDTO.getBusinessesWorkflows();
        ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        if (CollectionUtils.isEmpty(businessesWorkflowList)) {
            return resultList;
        }
        List<WorkflowBusinessDO> runtimeInfoList = this.workflowBusinessDao.selectWBListLatest(workflowBusinessDTO);
        List<WorkflowBusinessDO> designInfoList = this.workflowBusinessRelDraftDao.selectWBListLatest(workflowBusinessDTO);
        this.overrideByDesignTimeData(runtimeInfoList, designInfoList, true);
        if (CollectionUtils.isEmpty(runtimeInfoList)) {
            return resultList;
        }
        return this.createResult(workflowBusinessDTO, workflowBusinessDTO.isShowTitle(), runtimeInfoList, true).getWorkflows();
    }

    @Override
    public void afterPropertiesSet() {
        if (!CollectionUtils.isEmpty(this.relationships)) {
            this.relationshipMap = this.relationships.stream().collect(Collectors.toMap(Relationship::getName, obj -> obj));
        }
        if (!CollectionUtils.isEmpty(this.distributeStrategyList)) {
            this.distributionMap = this.distributeStrategyList.stream().collect(Collectors.toMap(DistributeStrategy::getDistributeType, o -> o));
        }
    }
}

