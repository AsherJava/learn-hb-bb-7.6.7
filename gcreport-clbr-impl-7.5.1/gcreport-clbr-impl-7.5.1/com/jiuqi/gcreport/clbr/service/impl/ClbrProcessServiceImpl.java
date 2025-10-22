/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillTypeEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrBussinessMode
 *  com.jiuqi.gcreport.clbr.enums.ClbrProcessListTypeEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrTabEnum
 *  com.jiuqi.gcreport.clbr.vo.ClbrBillCheckVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrBillVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrNotConfirmBillVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.transfer.vo.TransferColumnVo
 *  com.jiuqi.np.authz2.Namable
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.clbr.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.clbr.converter.ClbrBillConverter;
import com.jiuqi.gcreport.clbr.dao.ClbrBillCheckDao;
import com.jiuqi.gcreport.clbr.dao.ClbrBillDao;
import com.jiuqi.gcreport.clbr.entity.ClbrBillCheckEO;
import com.jiuqi.gcreport.clbr.entity.ClbrBillEO;
import com.jiuqi.gcreport.clbr.enums.ClbrBillTypeEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrBussinessMode;
import com.jiuqi.gcreport.clbr.enums.ClbrProcessListTypeEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrTabEnum;
import com.jiuqi.gcreport.clbr.service.ClbrProcessService;
import com.jiuqi.gcreport.clbr.service.ClbrReceiveSettingService;
import com.jiuqi.gcreport.clbr.service.ClbrTranferService;
import com.jiuqi.gcreport.clbr.util.ClbrTypePenetrationControlUtils;
import com.jiuqi.gcreport.clbr.vo.ClbrBillCheckVO;
import com.jiuqi.gcreport.clbr.vo.ClbrBillVO;
import com.jiuqi.gcreport.clbr.vo.ClbrNotConfirmBillVO;
import com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.transfer.vo.TransferColumnVo;
import com.jiuqi.np.authz2.Namable;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClbrProcessServiceImpl
implements ClbrProcessService {
    @Autowired
    private ClbrBillDao clbrBillDao;
    @Autowired
    private ClbrBillCheckDao clbrBillCheckDao;
    @Autowired
    private ClbrTranferService clbrTranferService;
    @Autowired
    private ClbrReceiveSettingService clbrReceiveSettingService;
    @Autowired
    private RoleService roleService;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ClbrNotConfirmBillVO> queryNotConfirm(ClbrProcessCondition clbrProcessCondition, ClbrProcessListTypeEnum clbrProcessListTypeEnum) {
        PageInfo<ClbrBillEO> pageInfo;
        if (ClbrProcessListTypeEnum.INITIATE_PART.equals((Object)clbrProcessListTypeEnum)) {
            pageInfo = this.clbrBillDao.listNotConfirmInitiatorPageByCondition(clbrProcessCondition);
        } else {
            if (ClbrBussinessMode.BUSINESS_MODE.getCode().equals(clbrProcessCondition.getMode())) {
                List roleList = this.roleService.getByIdentity(NpContextHolder.getContext().getIdentityId());
                List roleCodes = roleList.stream().map(Namable::getName).collect(Collectors.toList());
                String roleCodeStr = String.join((CharSequence)",", roleCodes);
                boolean exist = this.clbrReceiveSettingService.exist();
                if (exist) {
                    Map<String, Set<String>> receiveClbrType2Relations = this.clbrReceiveSettingService.getReceiveClbrType2Relations(NpContextHolder.getContext().getUserName(), roleCodeStr);
                    if (receiveClbrType2Relations.isEmpty()) {
                        return PageInfo.empty();
                    }
                    clbrProcessCondition.setReceiveClbrType2Relations(receiveClbrType2Relations);
                } else {
                    clbrProcessCondition.setReceiveClbrType2Relations(new HashMap());
                }
            }
            pageInfo = this.clbrBillDao.listNotConfirmReceiverPageByCondition(clbrProcessCondition);
        }
        ClbrTypePenetrationControlUtils.validPenetrationControl(pageInfo.getList());
        List list = pageInfo.getList().stream().map(entity -> this.converClbrNotConfirmBillVO((ClbrBillEO)((Object)entity))).collect(Collectors.toList());
        return PageInfo.of(list, (int)pageInfo.getPageNum(), (int)pageInfo.getPageSize(), (int)pageInfo.getSize());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ClbrBillCheckVO> queryConfirm(ClbrProcessCondition clbrProcessCondition, String tabFlag) {
        clbrProcessCondition.setTabFlag(ClbrTabEnum.getEnumByCode((String)tabFlag));
        if (ClbrBussinessMode.BUSINESS_MODE.getCode().equals(clbrProcessCondition.getMode()) && ClbrTabEnum.PROCESS_INITIATOR_NOT_CONFIRM.equals((Object)clbrProcessCondition.getTabFlag()) && StringUtils.isEmpty((String)clbrProcessCondition.getSysCode())) {
            List roleList = this.roleService.getByIdentity(NpContextHolder.getContext().getIdentityId());
            List roleCodes = roleList.stream().map(Namable::getName).collect(Collectors.toList());
            String roleCodeStr = String.join((CharSequence)",", roleCodes);
            boolean exist = this.clbrReceiveSettingService.exist();
            if (exist) {
                Map<String, Set<String>> receiveClbrType2Relations = this.clbrReceiveSettingService.getReceiveClbrType2Relations(NpContextHolder.getContext().getUserName(), roleCodeStr);
                if (receiveClbrType2Relations.isEmpty()) {
                    return PageInfo.empty();
                }
                clbrProcessCondition.setReceiveClbrType2Relations(receiveClbrType2Relations);
            } else {
                clbrProcessCondition.setReceiveClbrType2Relations(new HashMap());
            }
            clbrProcessCondition.setUserName(NpContextHolder.getContext().getUserName());
        }
        PageInfo<ClbrBillCheckEO> pageInfo = this.clbrBillCheckDao.listConfirmPageByCondition(clbrProcessCondition);
        return this.convertClbrConfirmBill(pageInfo);
    }

    @Override
    public ClbrBillVO getConfirmByClbrBillCode(String clbrBillCode) {
        ClbrBillEO clbrBillEO = this.clbrBillDao.getClbrBillByClbrBillCode(clbrBillCode);
        if (clbrBillEO == null) {
            throw new BusinessRuntimeException("\u6839\u636e\u5355\u636e\u7f16\u53f7\u672a\u627e\u5230\u534f\u540c\u786e\u8ba4\u4fe1\u606f[\u5355\u636e\u7f16\u53f7\uff1a" + clbrBillCode + "]");
        }
        return ClbrBillConverter.convertEO2VO(clbrBillEO);
    }

    @Override
    public PageInfo<ClbrBillVO> queryByClbrCode(ClbrProcessCondition clbrProcessCondition, ClbrBillTypeEnum clbrBillTypeEnum) {
        PageInfo<ClbrBillEO> clbrBills = this.clbrBillDao.listClbrBillByClbrCodeAndClbrType(clbrProcessCondition, clbrBillTypeEnum);
        return ClbrBillConverter.convertEO2VO(clbrBills);
    }

    @Override
    public void cancalByAmountCheckIds(Set<String> clbrAmountCheckIds) {
    }

    @Override
    public List<TransferColumnVo> queryAllShowFields(String tabFlag) {
        ClbrTabEnum tabEnum = ClbrTabEnum.getEnumByCode((String)tabFlag);
        if (Objects.isNull(tabEnum)) {
            throw new BusinessRuntimeException("\u6b64\u9875\u7b7e\u4e0d\u652f\u6301\u5217\u9009");
        }
        switch (tabEnum) {
            case GENERATE_NOT_CONFIRM: {
                return this.clbrTranferService.getGenerateNotConfirmAllField();
            }
            case GENERATE_CONFIRM: {
                return this.clbrTranferService.getGenerateConfirmAllField();
            }
            case PROCESS_NOT_CONFIRM: {
                return this.clbrTranferService.getProcessNotConfirmAllField();
            }
            case PROCESS_CONFIRM: {
                return this.clbrTranferService.getProcessConfirmAllField();
            }
            case PROCESS_INITIATOR_NOT_CONFIRM: {
                return this.clbrTranferService.getProcessInitiatorNotConfirmAllField();
            }
            case PROCESS_RECEIVER_NOT_CONFIRM: {
                return this.clbrTranferService.getProcessReceiverNotConfirmAllField();
            }
            case DATAQUERY_TOTAL: {
                return this.clbrTranferService.getDataQueryTotalAllField();
            }
            case DATAQUERY_CONFIRM: {
                return this.clbrTranferService.getDataQueryConfirmAllField();
            }
            case DATAQUERY_PART_CONFIRM: {
                return this.clbrTranferService.getDataQueryPartConfirmAllField();
            }
            case DATAQUERY_NOT_CONFIRM: {
                return this.clbrTranferService.getDataQueryNotConfirmAllField();
            }
            case DATAQUERY_REJECT: {
                return this.clbrTranferService.getDataQueryRejectAllField();
            }
            case ARBITRATION: {
                return this.clbrTranferService.getDataQueryArbitrationField();
            }
        }
        return new ArrayList<TransferColumnVo>();
    }

    private ClbrNotConfirmBillVO converClbrNotConfirmBillVO(ClbrBillEO clbrBill) {
        GcBaseData oppRelationBaseData;
        GcBaseData relationBaseData;
        GcBaseData clbrTypeBaseData;
        ClbrNotConfirmBillVO notConfirmBillVO = new ClbrNotConfirmBillVO();
        BeanUtils.copyProperties((Object)clbrBill, notConfirmBillVO);
        HashMap extendFields = new HashMap(clbrBill.getFields());
        notConfirmBillVO.setExtendInfo(extendFields);
        if (!StringUtils.isEmpty((String)clbrBill.getClbrType()) && (clbrTypeBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CLBRTYPE", clbrBill.getClbrType())) != null) {
            notConfirmBillVO.getExtendInfo().put("CLBRTYPE", clbrTypeBaseData.getTitle());
        }
        if (!StringUtils.isEmpty((String)clbrBill.getOppClbrType())) {
            String[] oppClbrTypeArray = clbrBill.getOppClbrType().split(",");
            StringBuilder oppClbrTypeTitle = new StringBuilder();
            for (String oppClbrTypeCode : oppClbrTypeArray) {
                GcBaseData oppClbrTypeBaseData = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CLBRTYPE", oppClbrTypeCode);
                if (oppClbrTypeBaseData == null) continue;
                oppClbrTypeTitle.append(oppClbrTypeBaseData.getTitle()).append(",");
            }
            notConfirmBillVO.getExtendInfo().put("OPPCLBRTYPE", oppClbrTypeTitle.substring(0, oppClbrTypeTitle.length() > 0 ? oppClbrTypeTitle.length() - 1 : 0));
        }
        if (!StringUtils.isEmpty((String)clbrBill.getRelation()) && (relationBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_RELATION", clbrBill.getRelation())) != null) {
            notConfirmBillVO.getExtendInfo().put("RELATION", relationBaseData.getTitle());
        }
        if (!StringUtils.isEmpty((String)clbrBill.getOppRelation()) && (oppRelationBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_RELATION", clbrBill.getOppRelation())) != null) {
            notConfirmBillVO.getExtendInfo().put("OPPRELATION", oppRelationBaseData.getTitle());
        }
        if (!StringUtils.isEmpty((String)clbrBill.getThisRelation()) && (oppRelationBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_RELATION", clbrBill.getThisRelation())) != null) {
            notConfirmBillVO.getExtendInfo().put("THISRELATION", oppRelationBaseData.getTitle());
        }
        if (!StringUtils.isEmpty((String)clbrBill.getThatRelation()) && (oppRelationBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_RELATION", clbrBill.getThatRelation())) != null) {
            notConfirmBillVO.getExtendInfo().put("THATRELATION", oppRelationBaseData.getTitle());
        }
        return notConfirmBillVO;
    }

    @Override
    public PageInfo<ClbrBillCheckVO> convertClbrConfirmBill(PageInfo<ClbrBillCheckEO> pageInfo) {
        List results = pageInfo.getList();
        List<String> groupIds = results.stream().map(ClbrBillCheckEO::getGroupId).collect(Collectors.toList());
        List<ClbrBillCheckEO> amountCheckEOS = this.clbrBillCheckDao.listByGroupIds(groupIds);
        Map groupId2CheckBillMap = amountCheckEOS.stream().collect(Collectors.groupingBy(ClbrBillCheckEO::getGroupId, Collectors.mapping(Function.identity(), Collectors.toList())));
        ArrayList<Map.Entry<String, List<Map.Entry>>> groupId2CheckBillEntryList = new ArrayList<Map.Entry<String, List<Map.Entry>>>(groupId2CheckBillMap.entrySet());
        Collections.sort(groupId2CheckBillEntryList, (o2, o1) -> ((ClbrBillCheckEO)((Object)((Object)((List)o1.getValue()).get(0)))).getClbrTime().compareTo(((ClbrBillCheckEO)((Object)((Object)((List)o2.getValue()).get(0)))).getClbrTime()));
        LinkedHashMap<String, List> groupId2CheckBillLinkedMap = new LinkedHashMap<String, List>(72);
        groupId2CheckBillEntryList.forEach(billEntry -> groupId2CheckBillLinkedMap.put((String)billEntry.getKey(), (List)billEntry.getValue()));
        Set<String> billIds = amountCheckEOS.stream().map(ClbrBillCheckEO::getBillId).collect(Collectors.toSet());
        List<ClbrBillEO> clbrBillEOS = this.clbrBillDao.listByIds(billIds);
        ClbrTypePenetrationControlUtils.validPenetrationControl(clbrBillEOS);
        Map clbrBillEOMap = clbrBillEOS.stream().collect(Collectors.toMap(DefaultTableEntity::getId, Function.identity()));
        Integer[] entryIndex = new Integer[]{0};
        ArrayList records = new ArrayList();
        groupId2CheckBillLinkedMap.forEach((groupId, amountCheckEOs) -> {
            Map billId2CheckBillMap = amountCheckEOs.stream().collect(Collectors.groupingBy(ClbrBillCheckEO::getBillId, Collectors.mapping(Function.identity(), Collectors.toList())));
            ArrayList finalList = new ArrayList();
            billId2CheckBillMap.forEach((billId, checkEos) -> {
                if (checkEos.size() > 1) {
                    double sumClbrAmount = checkEos.stream().mapToDouble(ClbrBillCheckEO::getClbrAmount).sum();
                    ClbrBillCheckEO clbrBillCheckEO = (ClbrBillCheckEO)((Object)((Object)((Object)checkEos.get(0))));
                    clbrBillCheckEO.setClbrAmount(sumClbrAmount);
                    finalList.add(clbrBillCheckEO);
                } else {
                    finalList.addAll(checkEos);
                }
            });
            Integer n = entryIndex[0];
            entryIndex[0] = entryIndex[0] + 1;
            Integer n2 = entryIndex[0];
            int rowSpan = finalList.size();
            for (ClbrBillCheckEO amountCheckEO : finalList) {
                ClbrBillCheckVO initiatorConfirmBill = new ClbrBillCheckVO();
                BeanUtils.copyProperties((Object)amountCheckEO, initiatorConfirmBill);
                initiatorConfirmBill.setCreateTime(DateUtils.format((Date)amountCheckEO.getCreateTime()));
                initiatorConfirmBill.setClbrTime(DateUtils.format((Date)amountCheckEO.getClbrTime()));
                initiatorConfirmBill.setRowspan(Integer.valueOf(rowSpan));
                initiatorConfirmBill.setIndex(entryIndex[0]);
                initiatorConfirmBill.setNoverifyAmount(((ClbrBillEO)((Object)((Object)clbrBillEOMap.get(amountCheckEO.getBillId())))).getNoverifyAmount());
                initiatorConfirmBill.setClbrBillType(Objects.requireNonNull(ClbrBillTypeEnum.getEnumByCode((Integer)((ClbrBillEO)((Object)((Object)clbrBillEOMap.get(amountCheckEO.getBillId())))).getClbrBillType())).getTitle());
                HashMap<String, Object> extendFields = new HashMap<String, Object>(((ClbrBillEO)((Object)((Object)clbrBillEOMap.get(amountCheckEO.getBillId())))).getFields());
                extendFields.put("CLBRAMOUNT", amountCheckEO.getClbrAmount());
                extendFields.put("CREATETIME", DateUtils.format((Date)amountCheckEO.getCreateTime()));
                extendFields.put("CLBRTIME", DateUtils.format((Date)amountCheckEO.getClbrTime()));
                initiatorConfirmBill.setExtendInfo(extendFields);
                this.setTitles(initiatorConfirmBill);
                records.add(initiatorConfirmBill);
            }
        });
        return PageInfo.of(records, (int)pageInfo.getSize());
    }

    private void setTitles(ClbrBillCheckVO record) {
        GcBaseData clbrTypeBdo;
        String clbrType;
        GcBaseData relationBaseData;
        String thatRelation;
        GcBaseData relationBaseData2;
        String thisRelation;
        GcBaseData relationBaseData3;
        String oppRelation;
        GcBaseData relationBaseData4;
        String relation = record.getRelation();
        if (!StringUtils.isEmpty((String)relation) && (relationBaseData4 = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_RELATION", relation)) != null) {
            record.getExtendInfo().put("RELATION", relationBaseData4.getTitle());
        }
        if (!StringUtils.isEmpty((String)(oppRelation = record.getOppRelation())) && (relationBaseData3 = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_RELATION", oppRelation)) != null) {
            record.getExtendInfo().put("OPPRELATION", relationBaseData3.getTitle());
        }
        if (!StringUtils.isEmpty((String)(thisRelation = record.getThisRelation())) && (relationBaseData2 = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_RELATION", thisRelation)) != null) {
            record.getExtendInfo().put("THISRELATION", relationBaseData2.getTitle());
        }
        if (!StringUtils.isEmpty((String)(thatRelation = record.getThatRelation())) && (relationBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_RELATION", thatRelation)) != null) {
            record.getExtendInfo().put("THATRELATION", relationBaseData.getTitle());
        }
        if (!StringUtils.isEmpty((String)(clbrType = record.getClbrType())) && (clbrTypeBdo = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CLBRTYPE", clbrType)) != null) {
            record.getExtendInfo().put("CLBRTYPE", clbrTypeBdo.getTitle());
        }
    }
}

