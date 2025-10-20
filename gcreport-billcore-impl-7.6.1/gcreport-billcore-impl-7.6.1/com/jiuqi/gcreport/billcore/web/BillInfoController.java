/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  com.jiuqi.gcreport.unionrule.api.BillInfoClient
 *  com.jiuqi.gcreport.unionrule.vo.SelectFloatLineOptionTreeVO
 *  com.jiuqi.va.bizmeta.common.consts.MetaTypeEnum
 *  com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDO
 *  com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoPageDTO
 *  com.jiuqi.va.bizmeta.service.impl.MetaGroupService
 *  com.jiuqi.va.bizmeta.service.impl.MetaInfoService
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.billcore.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.billcore.dto.BillInfoDTO;
import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.unionrule.api.BillInfoClient;
import com.jiuqi.gcreport.unionrule.vo.SelectFloatLineOptionTreeVO;
import com.jiuqi.va.bizmeta.common.consts.MetaTypeEnum;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoPageDTO;
import com.jiuqi.va.bizmeta.service.impl.MetaGroupService;
import com.jiuqi.va.bizmeta.service.impl.MetaInfoService;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="gcBillInfoApi")
public class BillInfoController
implements BillInfoClient {
    private MetaInfoService metaInfoService;
    private MetaGroupService metaGroupService;
    @Value(value="${biz-module.modules[0].name:GCBILL}")
    private String moduleName;

    public BillInfoController(MetaInfoService metaInfoService, MetaGroupService metaGroupService) {
        this.metaInfoService = metaInfoService;
        this.metaGroupService = metaGroupService;
    }

    public BusinessResponseEntity<List<SelectFloatLineOptionTreeVO>> getBillDefineTree() {
        return BusinessResponseEntity.ok(this.buildBillDefineTree());
    }

    public BusinessResponseEntity<List<SelectOptionVO>> getBillDefines() {
        MetaInfoPageDTO filter = new MetaInfoPageDTO();
        filter.setModule(this.moduleName);
        filter.setMetaType(MetaTypeEnum.BILL.getName());
        assert (this.metaInfoService != null);
        List allBillInfos = this.metaInfoService.getMetaList(filter);
        if (CollectionUtils.isEmpty(allBillInfos)) {
            return BusinessResponseEntity.ok(Collections.emptyList());
        }
        return BusinessResponseEntity.ok(allBillInfos.stream().map(billInfo -> {
            SelectOptionVO selectOption = new SelectOptionVO();
            selectOption.setValue((Object)billInfo.getUniqueCode());
            selectOption.setLabel(billInfo.getTitle());
            return selectOption;
        }).collect(Collectors.toList()));
    }

    private List<SelectFloatLineOptionTreeVO> buildBillDefineTree() {
        MetaInfoPageDTO filter = new MetaInfoPageDTO();
        filter.setModule(this.moduleName);
        filter.setMetaType(MetaTypeEnum.BILL.getName());
        assert (this.metaInfoService != null);
        List allBillInfos = this.metaInfoService.getMetaList(filter);
        if (CollectionUtils.isEmpty(allBillInfos)) {
            return Collections.emptyList();
        }
        List<BillInfoDTO> twoLevelBillInfos = this.buildTwoLevelByBills(allBillInfos);
        List<BillInfoDTO> billInfos = this.buildParentLevelByGroup(twoLevelBillInfos);
        return billInfos.stream().map(BillInfoDTO::converToSelectOptionTree).collect(Collectors.toList());
    }

    private List<BillInfoDTO> buildTwoLevelByBills(List<MetaInfoDTO> billInfos) {
        return billInfos.stream().map(metaInfo -> {
            BillInfoDTO billInfo = new BillInfoDTO();
            BeanUtils.copyProperties(metaInfo, billInfo);
            billInfo.setParentName(metaInfo.getGroupName());
            return billInfo;
        }).collect(Collectors.groupingBy(this::getGroupKey)).entrySet().stream().map(entry -> {
            GroupKey groupKey = (GroupKey)entry.getKey();
            MetaGroupDO metaGroup = this.getMetaGroup(groupKey);
            BillInfoDTO groupInfo = this.converMetaGroup2BillInfo(metaGroup);
            ((List)entry.getValue()).forEach(billInfo -> billInfo.setParentId(UUIDUtils.toString36((UUID)metaGroup.getId())));
            groupInfo.setChildren((List)entry.getValue());
            return groupInfo;
        }).collect(Collectors.toList());
    }

    private MetaGroupDO getMetaGroup(GroupKey groupKey) {
        assert (this.metaGroupService != null);
        return this.metaGroupService.findGroup(groupKey.getModuleName(), groupKey.getMetaType(), groupKey.getName());
    }

    private List<BillInfoDTO> buildParentLevelByGroup(List<BillInfoDTO> billInfos) {
        HashMap groupKeyAndGroupMapping = new HashMap();
        return billInfos.stream().map(billInfo -> this.buildParentLevel((BillInfoDTO)billInfo, groupKeyAndGroupMapping)).collect(Collectors.toList());
    }

    private BillInfoDTO buildParentLevel(BillInfoDTO billInfo, Map<GroupKey, BillInfoDTO> groupKeyAndGroupMapping) {
        if (StringUtils.isEmpty((String)billInfo.getParentName())) {
            return billInfo;
        }
        GroupKey parentGroupKey = new GroupKey();
        parentGroupKey.setModuleName(billInfo.getModuleName());
        parentGroupKey.setMetaType(billInfo.getMetaType());
        parentGroupKey.setName(billInfo.getParentName());
        BillInfoDTO parent = groupKeyAndGroupMapping.computeIfAbsent(parentGroupKey, groupKey -> {
            MetaGroupDO parentMetaGroup = this.getMetaGroup((GroupKey)groupKey);
            if (parentMetaGroup == null) {
                return null;
            }
            BillInfoDTO parentBillInfo = this.converMetaGroup2BillInfo(parentMetaGroup);
            parentBillInfo.setChildren(new ArrayList<BillInfoDTO>());
            return parentBillInfo;
        });
        if (parent == null) {
            return billInfo;
        }
        parent.getChildren().add(billInfo);
        return this.buildParentLevel(billInfo, groupKeyAndGroupMapping);
    }

    private BillInfoDTO converMetaGroup2BillInfo(MetaGroupDO metaGroup) {
        BillInfoDTO billInfo = new BillInfoDTO();
        billInfo.setId(UUIDUtils.toString36((UUID)metaGroup.getId()));
        billInfo.setName(metaGroup.getName());
        billInfo.setTitle(metaGroup.getTitle());
        billInfo.setModuleName(metaGroup.getModuleName());
        billInfo.setMetaType(metaGroup.getMetaType());
        billInfo.setParentName(metaGroup.getParentName());
        return billInfo;
    }

    private GroupKey getGroupKey(BillInfoDTO metaInfo) {
        GroupKey groupKey = new GroupKey();
        groupKey.setModuleName(metaInfo.getModuleName());
        groupKey.setMetaType(metaInfo.getMetaType());
        groupKey.setName(metaInfo.getParentName());
        return groupKey;
    }

    class GroupKey {
        private String moduleName;
        private String metaType;
        private String name;

        GroupKey() {
        }

        String getModuleName() {
            return this.moduleName;
        }

        void setModuleName(String moduleName) {
            this.moduleName = moduleName;
        }

        String getMetaType() {
            return this.metaType;
        }

        void setMetaType(String metaType) {
            this.metaType = metaType;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            GroupKey groupKey = (GroupKey)o;
            return Objects.equals(this.moduleName, groupKey.moduleName) && Objects.equals(this.metaType, groupKey.metaType) && Objects.equals(this.name, groupKey.name);
        }

        public int hashCode() {
            return Objects.hash(this.moduleName, this.metaType, this.name);
        }
    }
}

