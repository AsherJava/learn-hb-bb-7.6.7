/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.basedata.service.BaseDataDefineService
 *  com.jiuqi.va.basedata.service.BaseDataService
 *  com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.NotNull
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.common.subject.impl.subject.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.subject.impl.subject.annotation.ParamCheck;
import com.jiuqi.common.subject.impl.subject.data.SubjectTreeNodeType;
import com.jiuqi.common.subject.impl.subject.data.SubjectUtil;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.dto.TreeDTO;
import com.jiuqi.common.subject.impl.subject.enums.StopFlagEnum;
import com.jiuqi.common.subject.impl.subject.enums.SubjectClassEnum;
import com.jiuqi.common.subject.impl.subject.event.SubjectChangeEvent;
import com.jiuqi.common.subject.impl.subject.service.SubjectMergeFlagService;
import com.jiuqi.common.subject.impl.subject.service.SubjectService;
import com.jiuqi.common.subject.impl.subject.service.impl.SubjectCacheProvider;
import com.jiuqi.common.subject.impl.subject.utils.SubjectCheckerGather;
import com.jiuqi.common.subject.impl.subject.vo.SubjectInitVO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.basedata.service.BaseDataDefineService;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubjectServiceImpl
implements SubjectService {
    @Autowired
    private BaseDataDefineService defineService;
    @Autowired
    private BaseDataService baseDataService;
    @Autowired
    private BaseDataCacheService baseDataCacheService;
    @Autowired
    private SubjectCacheProvider cacheProvider;
    @Autowired
    private SubjectCheckerGather checkerGather;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private INvwaSystemOptionService sysOptionService;
    @Autowired
    private ApplicationContext applicationContext;
    @Value(value="${jiuqi.gcreport.dimension.master:true}")
    private Boolean masterFlag;
    private final List<SubjectMergeFlagService> mergeFlagServices;
    private static final String ID = "id";
    private static final String VER = "ver";
    private static final String NODE_TYPE = "nodetype";

    @Autowired(required=false)
    public SubjectServiceImpl(List<SubjectMergeFlagService> mergeFlagServices) {
        this.mergeFlagServices = mergeFlagServices;
    }

    @Override
    public SubjectInitVO getSubjectInitInfo() {
        SubjectInitVO subjectInitVO = new SubjectInitVO();
        subjectInitVO.setMasterFlag(this.masterFlag);
        try {
            subjectInitVO.setFinancialCubesFlag("1".equals(this.sysOptionService.findValueById("FINANCIAL_CUBES_ENABLE")));
        }
        catch (Exception e) {
            subjectInitVO.setFinancialCubesFlag(false);
        }
        return subjectInitVO;
    }

    @Override
    public Boolean syncCache() {
        this.cacheProvider.syncCache();
        return true;
    }

    @Override
    public BaseDataDefineDO findDefineByName(BaseDataDefineDTO dto) {
        dto.setName("MD_ACCTSUBJECT");
        return this.defineService.get(dto);
    }

    @Override
    public List<SubjectDTO> list() {
        return this.cacheProvider.list();
    }

    @Override
    public List<SubjectDTO> list(BaseDataDTO dto) {
        return this.cacheProvider.list().stream().filter(item -> this.filterByCondi(dto, (SubjectDTO)item)).collect(Collectors.toList());
    }

    private boolean filterByCondi(BaseDataDTO dto, SubjectDTO item) {
        if (!(StringUtils.isEmpty((String)dto.getSearchKey()) || item.getCode().contains(dto.getSearchKey()) || item.getName().contains(dto.getSearchKey()))) {
            return false;
        }
        String generalType = (String)dto.get((Object)"generaltype");
        if (!StringUtils.isEmpty((String)generalType) && !generalType.equals(item.getGeneralType())) {
            return false;
        }
        if (!StringUtils.isEmpty((String)dto.getCode()) && !item.getCode().startsWith(dto.getCode())) {
            return false;
        }
        if (!StringUtils.isEmpty((String)dto.getParentcode())) {
            String parents = "/".concat(dto.getParentcode()).concat("/");
            String subjectParents = "/".concat(item.getParents());
            if (!item.getCode().equals(dto.getParentcode()) && !subjectParents.contains(parents)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public PageVO<SubjectDTO> pagination(BaseDataDTO dto) {
        if (!dto.isPagination()) {
            List<SubjectDTO> list = this.list(dto);
            return new PageVO(list, list.size());
        }
        Assert.isTrue((dto.getOffset() >= 0 ? 1 : 0) != 0);
        Assert.isTrue((dto.getLimit() > 0 ? 1 : 0) != 0);
        List filteredList = this.cacheProvider.list().stream().filter(item -> this.filterByCondi(dto, (SubjectDTO)item)).collect(Collectors.toList());
        List list = filteredList.stream().skip(dto.getOffset() * dto.getLimit()).limit(dto.getLimit()).collect(Collectors.toList());
        return new PageVO(list, filteredList.size());
    }

    private TreeDTO getDefaultNode() {
        TreeDTO defaultRootNode = new TreeDTO();
        defaultRootNode.setId("root");
        defaultRootNode.setCode("root");
        defaultRootNode.setName("\u79d1\u76ee");
        defaultRootNode.setTitle("\u79d1\u76ee");
        defaultRootNode.setParentId("-");
        defaultRootNode.setNodeType(SubjectTreeNodeType.ROOT.getCode());
        HashMap<String, Object> attributes = new HashMap<String, Object>(2);
        attributes.put(ID, defaultRootNode.getId());
        attributes.put(NODE_TYPE, SubjectTreeNodeType.ROOT.getCode());
        defaultRootNode.setAttributes(attributes);
        return defaultRootNode;
    }

    @Override
    public List<TreeDTO> tree(BaseDataDTO dto) {
        TreeDTO rootNode;
        SubjectTreeNodeType filterType;
        Map<String, BaseDataDO> subjectClassMap = SubjectUtil.getGeneralTypeMap();
        if (StringUtils.isEmpty((String)dto.getCode()) || dto.getCode().equals("root")) {
            filterType = SubjectTreeNodeType.ROOT;
            rootNode = this.getDefaultNode();
        } else if (subjectClassMap.get(dto.getCode()) != null) {
            filterType = SubjectTreeNodeType.SUBJECT_CLASS;
            rootNode = this.buildSubjClassNode(subjectClassMap.get(dto.getCode()), "root");
        } else {
            filterType = SubjectTreeNodeType.SUBJECT;
            SubjectDTO subject = this.findByCode(dto.getCode());
            rootNode = subject == null ? this.getDefaultNode() : this.buildSubjectNode(subject);
        }
        ArrayList subjects = this.cacheProvider.list().stream().filter(item -> {
            switch (filterType) {
                case ROOT: {
                    return true;
                }
                case SUBJECT_CLASS: {
                    return rootNode.getCode().equals(item.getGeneralType());
                }
            }
            return item.getCode().startsWith(rootNode.getCode()) && !item.getCode().equals(dto.getCode());
        }).collect(Collectors.toList());
        if (subjects == null) {
            subjects = CollectionUtils.newArrayList();
        }
        return this.buildTree(subjects, rootNode);
    }

    @Override
    public List<TreeDTO> buildTreeBySubjectCodes(BaseDataDTO dto) {
        if (dto.getBaseDataCodes() == null || dto.getBaseDataCodes().isEmpty()) {
            return this.buildTree(CollectionUtils.newArrayList());
        }
        List subjectCodes = dto.getBaseDataCodes();
        List<SubjectDTO> subjects = this.cacheProvider.list().stream().filter(item -> {
            for (String subjectCode : subjectCodes) {
                if (item.getCode().equals(subjectCode)) {
                    item.setParentcode(item.getGeneralType());
                    return true;
                }
                if (!item.getCode().startsWith(subjectCode)) continue;
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        return this.buildTree(subjects);
    }

    @Override
    public List<TreeDTO> buildTree(List<SubjectDTO> subjects) {
        return this.buildTree(subjects, this.getDefaultNode());
    }

    @Override
    public List<TreeDTO> buildTree(List<SubjectDTO> subjects, TreeDTO rootNode) {
        ArrayList treeList = CollectionUtils.newArrayList();
        TreeDTO root = rootNode == null ? this.getDefaultNode() : rootNode;
        ArrayList<TreeDTO> nodes = new ArrayList<TreeDTO>(subjects.size() + SubjectClassEnum.values().length);
        for (SubjectDTO subject2 : subjects) {
            nodes.add(this.buildSubjectNode(subject2));
        }
        Map<String, TreeDTO> treeNodeMap = nodes.stream().collect(Collectors.toMap(TreeDTO::getCode, item -> item, (k1, k2) -> k2));
        nodes.stream().forEach(subject -> {
            TreeDTO parentNode = (TreeDTO)treeNodeMap.get(((TreeDTO)treeNodeMap.get(subject.getCode())).getParentCode());
            if (parentNode == null) {
                subject.setParentCode(root.getCode());
                root.addChild((TreeDTO)subject);
            } else {
                parentNode.setLeaf(false);
                parentNode.addChild((TreeDTO)subject);
            }
        });
        treeList.add(root);
        return treeList;
    }

    private TreeDTO buildSubjClassNode(BaseDataDO subjClass, String parentCode) {
        TreeDTO subjClassNode = new TreeDTO();
        subjClassNode.setId(subjClass.getId().toString());
        subjClassNode.setCode(subjClass.getCode());
        subjClassNode.setName(subjClass.getName());
        subjClassNode.setTitle(subjClass.getName());
        subjClassNode.setParentCode(parentCode);
        subjClassNode.setLeaf(true);
        subjClassNode.setNodeType(SubjectTreeNodeType.SUBJECT_CLASS.getCode());
        HashMap<String, Object> attributes = new HashMap<String, Object>(2);
        attributes.put(ID, subjClass.getId().toString());
        attributes.put(NODE_TYPE, SubjectTreeNodeType.SUBJECT_CLASS.getCode());
        subjClassNode.setAttributes(attributes);
        return subjClassNode;
    }

    private TreeDTO buildSubjectNode(SubjectDTO subject) {
        TreeDTO node = new TreeDTO();
        node.setId(subject.getId().toString());
        node.setCode(subject.getCode());
        node.setName(subject.getName());
        node.setTitle(SubjectUtil.getNodeLabel(subject.getCode(), subject.getName()));
        node.setParentCode("-".equals(subject.getParentcode()) ? subject.getGeneralType() : subject.getParentcode());
        node.setLeaf(true);
        node.setNodeType(SubjectTreeNodeType.SUBJECT.getCode());
        HashMap<String, Object> attributes = new HashMap<String, Object>(10);
        attributes.put(ID, subject.getId());
        attributes.put(VER, subject.getVer());
        attributes.put(NODE_TYPE, SubjectTreeNodeType.SUBJECT.getCode());
        attributes.put("orient", subject.getOrient());
        attributes.put("generaltype", subject.getGeneralType());
        attributes.put("currency", subject.getCurrency());
        attributes.put("asstype", subject.getAssType());
        attributes.put("PARENTCODE", subject.getParentcode());
        attributes.put("PARENTS", subject.getParents());
        attributes.put("STOPFLAG", subject.getStopflag());
        node.setAttributes(attributes);
        return node;
    }

    @Override
    @ParamCheck
    public BaseDataDO getById(@NotNull(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") UUID id) {
        SubjectDTO baseData = this.findById(id);
        if (baseData == null) {
            throw new BusinessRuntimeException(String.format("\u6807\u8bc6\u3010%1$s\u3011\u7684\u6570\u636e\u4e0d\u5b58\u5728", id));
        }
        return baseData;
    }

    @Override
    @ParamCheck
    public SubjectDTO findById(@NotNull(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") UUID id) {
        return this.findBaseDataById(id);
    }

    @Override
    @ParamCheck
    public SubjectDTO getByCode(@NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") String code) {
        SubjectDTO baseData = this.findByCode(code);
        if (baseData == null) {
            throw new BusinessRuntimeException(String.format("\u4ee3\u7801\u3010%1$s\u3011\u7684\u6570\u636e\u4e0d\u5b58\u5728", code));
        }
        return baseData;
    }

    @Override
    @ParamCheck
    public SubjectDTO findByCode(@NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") String code) {
        return this.findBaseDataByCode(code);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean create(SubjectDTO dto) {
        this.checkerGather.getCheckers().forEach(checker -> checker.doCreateCheck(dto));
        dto.setTableName("MD_ACCTSUBJECT");
        dto.setStopflag(StopFlagEnum.START.getCode());
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        for (Map.Entry entry : dto.entrySet()) {
            baseDataDTO.put((String)entry.getKey(), entry.getValue());
        }
        R r = this.baseDataService.add(baseDataDTO);
        if (r.getCode() != 0) {
            throw new BusinessRuntimeException(r.getMsg());
        }
        this.syncCache();
        String title = StringUtils.join((Object[])new String[]{"\u65b0\u589e", dto.getCode(), dto.getName()}, (String)"-");
        LogHelper.info((String)"\u5408\u5e76\u4ea7\u54c1\u7ebf-\u79d1\u76ee", (String)title, (String)JsonUtils.writeValueAsString((Object)dto));
        return true;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean modify(SubjectDTO dto) {
        this.checkerGather.getCheckers().forEach(checker -> checker.doModifyCheck(dto));
        dto.setTableName("MD_ACCTSUBJECT");
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        for (Map.Entry entry : dto.entrySet()) {
            baseDataDTO.put((String)entry.getKey(), entry.getValue());
        }
        R r = this.baseDataService.update(baseDataDTO);
        if (r.getCode() != 0) {
            throw new BusinessRuntimeException(r.getMsg());
        }
        this.syncCache();
        String title = StringUtils.join((Object[])new String[]{"\u4fee\u6539", dto.getCode(), dto.getName()}, (String)"-");
        LogHelper.info((String)"\u5408\u5e76\u4ea7\u54c1\u7ebf-\u79d1\u76ee", (String)title, (String)JsonUtils.writeValueAsString((Object)dto));
        this.sendChangeEvent(dto);
        return true;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean delete(SubjectDTO dto) {
        Assert.isNotNull((Object)dto.getId());
        Assert.isNotNull((Object)dto.getVer());
        this.checkerGather.getCheckers().forEach(checker -> checker.doDeleteCheck(dto));
        if (!CollectionUtils.isEmpty(this.mergeFlagServices) && !StringUtils.isEmpty((String)this.mergeFlagServices.get(0).getSubjectMergeFlag(dto.getCode()))) {
            String msg = this.mergeFlagServices.get(0).getSubjectMergeFlag(dto.getCode());
            throw new BusinessRuntimeException("\u79d1\u76ee\u3010" + dto.getCode() + "\u3011\u5728" + msg + "\u4f53\u7cfb\u4e2d\u5df2\u5f15\u7528\uff0c\u4e0d\u5141\u8bb8\u5220\u9664");
        }
        SubjectDTO savedData = this.findBaseDataById(dto.getId(), dto.getVer());
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName("MD_ACCTSUBJECT");
        param.setId(dto.getId());
        param.setVer(dto.getVer());
        R r = this.baseDataService.remove(param);
        if (r.getCode() != 0) {
            throw new BusinessRuntimeException(r.getMsg());
        }
        r = this.baseDataService.clearRecovery(param);
        if (r.getCode() != 0) {
            throw new BusinessRuntimeException(r.getMsg());
        }
        this.syncCache();
        BaseDataDTO cacheSyncParam = new BaseDataDTO();
        cacheSyncParam.setTenantName("__default_tenant__");
        cacheSyncParam.setTableName("MD_ACCTSUBJECT");
        String title = StringUtils.join((Object[])new String[]{"\u5220\u9664", savedData.getCode(), savedData.getName()}, (String)"-");
        LogHelper.info((String)"\u5408\u5e76\u4ea7\u54c1\u7ebf-\u79d1\u76ee", (String)title, (String)JsonUtils.writeValueAsString((Object)savedData));
        return true;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean stop(SubjectDTO dto) {
        dto.setStopflag(StopFlagEnum.STOP.getCode());
        return this.changeStopState(dto);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean start(SubjectDTO dto) {
        dto.setStopflag(StopFlagEnum.START.getCode());
        return this.changeStopState(dto);
    }

    @Override
    public List<DimensionVO> getAllPublishedDim() {
        return this.dimensionService.loadAllDimensions();
    }

    @Override
    public Boolean batchCreate(List<SubjectDTO> dtoList) {
        if (dtoList.isEmpty()) {
            return true;
        }
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName("MD_ACCTSUBJECT");
        ArrayList<BaseDataDO> saveResultList = new ArrayList<BaseDataDO>();
        for (SubjectDTO subjectDTO : dtoList) {
            BaseDataDO baseDataDO = new BaseDataDO();
            baseDataDO.putAll((Map)((Object)subjectDTO));
            saveResultList.add(baseDataDO);
        }
        BaseDataBatchOptDTO optDTO = new BaseDataBatchOptDTO();
        optDTO.setQueryParam(param);
        optDTO.setDataList(saveResultList);
        try {
            this.baseDataService.batchAdd(optDTO);
            return true;
        }
        catch (Exception e) {
            throw new RuntimeException("\u79d1\u76ee\u6279\u91cf\u65b0\u589e\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage());
        }
    }

    @Override
    public Boolean batchModify(List<SubjectDTO> dtoList) {
        if (dtoList.isEmpty()) {
            return true;
        }
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName("MD_ACCTSUBJECT");
        ArrayList<BaseDataDO> saveResultList = new ArrayList<BaseDataDO>();
        for (SubjectDTO subjectDTO : dtoList) {
            BaseDataDO baseDataDO = new BaseDataDO();
            baseDataDO.putAll((Map)((Object)subjectDTO));
            saveResultList.add(baseDataDO);
        }
        BaseDataBatchOptDTO optDTO = new BaseDataBatchOptDTO();
        optDTO.setQueryParam(param);
        optDTO.setDataList(saveResultList);
        try {
            this.baseDataService.batchUpdate(optDTO);
            return true;
        }
        catch (Exception e) {
            throw new RuntimeException("\u79d1\u76ee\u6279\u91cf\u66f4\u65b0\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage());
        }
    }

    private Boolean changeStopState(SubjectDTO dto) {
        Assert.isNotNull((Object)dto.getId());
        Assert.isNotNull((Object)dto.getVer());
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName("MD_ACCTSUBJECT");
        param.setId(dto.getId());
        param.setVer(dto.getVer());
        param.setStopflag(dto.getStopflag());
        param.setUpdateChildItemStopFlag(dto.getStopChildItem().booleanValue());
        R r = this.baseDataService.stop(param);
        if (r.getCode() != 0) {
            throw new BusinessRuntimeException(r.getMsg());
        }
        this.syncCache();
        this.sendChangeEvent(dto);
        return true;
    }

    private SubjectDTO findBaseDataByCode(String code) {
        return this.findBaseDataByCode(code, null);
    }

    private SubjectDTO findBaseDataByCode(String code, BigDecimal ver) {
        if (ver == null) {
            return (SubjectDTO)this.cacheProvider.get(code);
        }
        return this.cacheProvider.list().stream().filter(item -> code.equals(item.getCode()) && ver.compareTo(item.getVer()) == 0).findFirst().orElse(null);
    }

    private SubjectDTO findBaseDataById(UUID id) {
        return this.findBaseDataById(id, null);
    }

    private SubjectDTO findBaseDataById(UUID id, BigDecimal ver) {
        return this.cacheProvider.list().stream().filter(item -> id.equals(item.getId()) && (ver == null || ver != null && ver.compareTo(item.getVer()) == 0)).findFirst().orElse(null);
    }

    private void checkMasterFlag() {
        if (!this.masterFlag.booleanValue()) {
            throw new BusinessRuntimeException("\u5f53\u524d\u670d\u52a1\u975e\u7ba1\u7406\u670d\u52a1\uff0c\u8bf7\u5230\u7ba1\u7406\u670d\u52a1\u8fdb\u884c\u64cd\u4f5c\u3002");
        }
    }

    private void sendChangeEvent(SubjectDTO dto) {
        SubjectChangeEvent event = new SubjectChangeEvent();
        event.setCode(dto.getCode());
        event.setParentid(dto.getParentcode());
        event.setName(dto.getName());
        event.setAsstype(dto.getAssType());
        event.setGeneralType(dto.getGeneralType());
        event.setOrient(dto.getOrient());
        event.setStopFlag(dto.getStopflag());
        event.setStopChildItem(dto.getStopChildItem());
        event.setId(dto.getId());
        this.applicationContext.publishEvent(event);
    }
}

