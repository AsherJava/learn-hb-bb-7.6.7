/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.adjustvchr.client.dao.AdjustVchrClientDao
 *  com.jiuqi.dc.adjustvchr.client.domain.AdjustVchrIdListDTO
 *  com.jiuqi.dc.adjustvchr.client.domain.AdjustVoucherDTO
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVchrCopyDTO
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherSaveDTO
 *  com.jiuqi.dc.adjustvchr.client.service.AdjustVoucherClientService
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrBaseDataVO
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrDeleteVO
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrItemVO
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO
 *  com.jiuqi.dc.adjustvchr.impl.entity.AdjustVchrItemEO
 *  com.jiuqi.dc.adjustvchr.impl.entity.AdjustVoucherEO
 *  com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum
 *  com.jiuqi.dc.base.common.env.EnvCenter
 *  com.jiuqi.dc.base.common.utils.BeanCopyUtil
 *  com.jiuqi.dc.base.common.utils.DataCenterUtil
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.np.log.LogHelper
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.dc.adjustvchr.impl.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.adjustvchr.client.dao.AdjustVchrClientDao;
import com.jiuqi.dc.adjustvchr.client.domain.AdjustVchrIdListDTO;
import com.jiuqi.dc.adjustvchr.client.domain.AdjustVoucherDTO;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVchrCopyDTO;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherSaveDTO;
import com.jiuqi.dc.adjustvchr.client.service.AdjustVoucherClientService;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrBaseDataVO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrDeleteVO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrItemVO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO;
import com.jiuqi.dc.adjustvchr.impl.dao.AdjustVchrDao;
import com.jiuqi.dc.adjustvchr.impl.entity.AdjustVchrItemEO;
import com.jiuqi.dc.adjustvchr.impl.entity.AdjustVoucherEO;
import com.jiuqi.dc.adjustvchr.impl.enums.AdjustVchrExecutePeriodEnum;
import com.jiuqi.dc.adjustvchr.impl.enums.AdjustVchrTypeEnum;
import com.jiuqi.dc.adjustvchr.impl.monitor.IAdjustVoucherItemMonitor;
import com.jiuqi.dc.adjustvchr.impl.service.AdjustVoucherService;
import com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum;
import com.jiuqi.dc.base.common.env.EnvCenter;
import com.jiuqi.dc.base.common.utils.BeanCopyUtil;
import com.jiuqi.dc.base.common.utils.DataCenterUtil;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.np.log.LogHelper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class AdjustVoucherServiceImpl
implements AdjustVoucherService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private AdjustVchrDao adjustVchrDao;
    @Autowired
    private AdjustVchrClientDao clientDao;
    @Autowired(required=false)
    private List<IAdjustVoucherItemMonitor> adjustVoucherItemMonitors;
    @Autowired
    private AdjustVoucherClientService clientService;
    private static final Map<String, String> convertCurrencyMap = new HashMap<String, String>();

    private List<AdjustVoucherDTO> find(AdjustVoucherVO voucherVO, List<DimensionVO> assistDimList, List<String> convertAmountCols) {
        List<AdjustVoucherVO> voucherVOList = this.adjustVchrDao.listByGroupId(voucherVO.getGroupId());
        if (CollectionUtils.isEmpty(voucherVOList)) {
            return null;
        }
        ArrayList<AdjustVoucherDTO> resultList = new ArrayList<AdjustVoucherDTO>();
        for (AdjustVoucherVO adjustVoucherVO : voucherVOList) {
            AdjustVchrIdListDTO itemParam = new AdjustVchrIdListDTO();
            ArrayList<String> vchrIds = new ArrayList<String>();
            vchrIds.add(adjustVoucherVO.getId());
            itemParam.setVchrIds(vchrIds);
            itemParam.setAssistDims(assistDimList);
            itemParam.setConvertAmountCols(convertAmountCols);
            List itemDatas = this.clientDao.listByVchrId(itemParam);
            ArrayList<AdjustVchrItemVO> items = new ArrayList<AdjustVchrItemVO>();
            for (AdjustVchrItemEO itemData : itemDatas) {
                AdjustVchrItemVO oldItem = (AdjustVchrItemVO)BeanCopyUtil.copyObj(AdjustVchrItemVO.class, (Object)itemData);
                items.add(oldItem);
            }
            AdjustVoucherDTO voucher = new AdjustVoucherDTO();
            voucher.setMasterVO(adjustVoucherVO);
            voucher.setItemVOList(items);
            resultList.add(voucher);
        }
        return resultList;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String save(AdjustVoucherSaveDTO param) {
        String title;
        List vouchers;
        if (param.getDeletedVchrs() != null && !param.getDeletedVchrs().isEmpty()) {
            AdjustVchrDeleteVO adjustVchrDeleteVO = new AdjustVchrDeleteVO();
            adjustVchrDeleteVO.setDelVchrIds(param.getDeletedVchrs());
            this.batchDelete(adjustVchrDeleteVO);
        }
        if ((vouchers = param.getVouchers()) == null || vouchers.isEmpty()) {
            return null;
        }
        List assistDimList = this.dimensionService.findAllDimFieldsVOByTableName("DC_ADJUSTVCHRITEM");
        ArrayList<AdjustVoucherDTO> insertVouchers = new ArrayList<AdjustVoucherDTO>();
        ArrayList<AdjustVoucherDTO> updateVouchers = new ArrayList<AdjustVoucherDTO>();
        List<String> convertAmountCols = this.getConvertAmountColumns();
        for (AdjustVoucherVO voucherVO : vouchers) {
            if (StringUtils.isEmpty((String)voucherVO.getId())) {
                this.handleInsertVoucher(voucherVO, insertVouchers, convertAmountCols, assistDimList);
                continue;
            }
            this.handleUpdateVoucher(param.getInfluenceLaterFlag(), voucherVO, updateVouchers, convertAmountCols, assistDimList);
        }
        HashMap<String, String> groupMap = new HashMap<String, String>();
        for (AdjustVoucherDTO voucher : insertVouchers) {
            String newVchrNum = "";
            if (groupMap.get(voucher.getMasterEO().getGroupId()) == null) {
                newVchrNum = this.clientService.getMaxVchrNum(voucher.getMasterEO().getUnitCode(), voucher.getMasterEO().getAcctYear());
                groupMap.put(voucher.getMasterEO().getGroupId(), newVchrNum);
            } else {
                newVchrNum = (String)groupMap.get(voucher.getMasterEO().getGroupId());
            }
            voucher.getMasterEO().setVchrNum(newVchrNum);
            voucher.getMasterEO().setVchrSrcType(AdjustVchrTypeEnum.HANDLE_ADJUST.getCode());
            this.adjustVchrDao.insertVoucher(voucher.getMasterEO());
            for (AdjustVchrItemEO item : voucher.getItemEOList()) {
                item.setVchrNum(newVchrNum);
                item.setVchrSrcType(AdjustVchrTypeEnum.HANDLE_ADJUST.getCode());
            }
            this.batchInsetAdjustVchrItem(voucher.getItemEOList());
        }
        for (AdjustVoucherDTO voucher : updateVouchers) {
            this.adjustVchrDao.updateVoucher(voucher.getMasterEO());
            this.deleteVchrItemByVchrId(Collections.singletonList(voucher.getMasterEO().getId()));
            this.batchInsetAdjustVchrItem(voucher.getItemEOList());
        }
        for (AdjustVoucherDTO voucher : insertVouchers) {
            title = String.format("\u65b0\u589e-\u7ec4\u7ec7\u673a\u6784%1$s-\u7f16\u53f7%2$s", voucher.getMasterEO().getUnitCode(), voucher.getMasterEO().getVchrNum());
            LogHelper.info((String)DcFunctionModuleEnum.ADJUSTVCHR.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)voucher.getMasterEO()));
        }
        for (AdjustVoucherDTO voucher : updateVouchers) {
            title = String.format("\u4fee\u6539-\u7ec4\u7ec7\u673a\u6784%1$s-\u7f16\u53f7%2$s", voucher.getMasterEO().getUnitCode(), voucher.getMasterEO().getVchrNum());
            LogHelper.info((String)DcFunctionModuleEnum.ADJUSTVCHR.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)voucher.getMasterEO()));
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean batchDelete(AdjustVchrDeleteVO adjustVchrDeleteVO) {
        List<String> groupIdList = this.adjustVchrDao.listGroupIdById(adjustVchrDeleteVO.getDelVchrIds());
        List<AdjustVoucherVO> groupData = this.adjustVchrDao.listByGroupIdList(groupIdList);
        int count = 0;
        ArrayList<AdjustVoucherVO> deleteData = new ArrayList<AdjustVoucherVO>();
        ArrayList<String> delVchrIds = new ArrayList<String>();
        String newGroupId = UUIDUtils.newUUIDStr();
        for (AdjustVoucherVO voucherVO : groupData) {
            AdjustVoucherEO saveEO = new AdjustVoucherEO();
            BeanUtils.copyProperties(voucherVO, saveEO);
            int end = Integer.parseInt(voucherVO.getAffectPeriodEnd().substring(0, voucherVO.getAffectPeriodEnd().length() - 1));
            if (end < adjustVchrDeleteVO.getCurrentPeriod()) continue;
            if (voucherVO.getAcctPeriod() < adjustVchrDeleteVO.getCurrentPeriod()) {
                saveEO.setAffectPeriodEnd(adjustVchrDeleteVO.getCurrentPeriod() - 1 + adjustVchrDeleteVO.getPeriodType());
                saveEO.setModifyUser(EnvCenter.getContextUserName());
                saveEO.setModifyTime(new Date());
                this.adjustVchrDao.updateVoucher(saveEO);
                continue;
            }
            if (voucherVO.getAcctPeriod().equals(adjustVchrDeleteVO.getCurrentPeriod()) || AdjustVchrExecutePeriodEnum.CURRENT_TO_END_PERIOD.getCode().equals(adjustVchrDeleteVO.getDeleteRange())) {
                deleteData.add(voucherVO);
                delVchrIds.add(voucherVO.getId());
                continue;
            }
            saveEO.setAffectPeriodStart(adjustVchrDeleteVO.getCurrentPeriod() + 1 + adjustVchrDeleteVO.getPeriodType());
            saveEO.setGroupId(newGroupId);
            saveEO.setModifyUser(EnvCenter.getContextUserName());
            saveEO.setModifyTime(new Date());
            this.adjustVchrDao.updateVoucher(saveEO);
        }
        count = this.adjustVchrDao.delVoucherByVchrIdList(delVchrIds);
        return this.deleteAdjustVchr(delVchrIds, deleteData, count);
    }

    private Boolean deleteAdjustVchr(List<String> delVchrIds, List<AdjustVoucherVO> deleteData, Integer deleteCount) {
        if (deleteCount > 0) {
            this.deleteVchrItemByVchrId(delVchrIds);
            for (AdjustVoucherVO voucher : deleteData) {
                String title = String.format("\u5220\u9664-\u7ec4\u7ec7\u673a\u6784%1$s-\u7f16\u53f7%2$s", voucher.getUnitCode(), voucher.getVchrNum());
                LogHelper.info((String)DcFunctionModuleEnum.ADJUSTVCHR.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)voucher));
            }
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void copy(AdjustVchrCopyDTO copyData) {
        int acctYear = copyData.getAcctYear();
        String periodType = copyData.getPeriodType();
        String affectPeriodStart = copyData.getAffectPeriodStart();
        String affectPeriodEnd = copyData.getAffectPeriodEnd();
        List srcAdjustVchrList = copyData.getSrcVouchers();
        int acctPeriod = copyData.getAcctPeriod();
        List assistDimList = this.dimensionService.findAllDimFieldsVOByTableName("DC_ADJUSTVCHRITEM");
        ArrayList<AdjustVoucherDTO> insertVouchers = new ArrayList<AdjustVoucherDTO>();
        for (AdjustVoucherVO srcAdjustVchr : srcAdjustVchrList) {
            boolean isYear = "N".equals(srcAdjustVchr.getPeriodType());
            srcAdjustVchr.setAcctYear(Integer.valueOf(acctYear));
            srcAdjustVchr.setPeriodType(periodType);
            srcAdjustVchr.setAffectPeriodStart(isYear ? acctYear + periodType : affectPeriodStart);
            srcAdjustVchr.setAffectPeriodEnd(isYear ? acctYear + periodType : affectPeriodEnd);
            srcAdjustVchr.setAcctPeriod(Integer.valueOf(isYear ? 1 : acctPeriod));
            srcAdjustVchr.setGroupId(UUIDUtils.newUUIDStr());
            this.handleInsertVoucher(srcAdjustVchr, insertVouchers, this.getConvertAmountColumns(), assistDimList);
        }
        for (AdjustVoucherDTO voucher : insertVouchers) {
            String newVchrNum = this.clientService.getMaxVchrNum(voucher.getMasterEO().getUnitCode(), voucher.getMasterEO().getAcctYear());
            voucher.getMasterEO().setVchrNum(newVchrNum);
            voucher.getMasterEO().setGroupId(UUIDUtils.newUUIDStr());
            this.adjustVchrDao.insertVoucher(voucher.getMasterEO());
            for (AdjustVchrItemEO item : voucher.getItemEOList()) {
                item.setVchrNum(newVchrNum);
                item.setAcctYear(Integer.valueOf(acctYear));
            }
            this.batchInsetAdjustVchrItem(voucher.getItemEOList());
        }
        for (AdjustVoucherDTO voucher : insertVouchers) {
            String title = String.format("\u590d\u5236-\u7ec4\u7ec7\u673a\u6784%1$s-\u7f16\u53f7%2$s", voucher.getMasterEO().getUnitCode(), voucher.getMasterEO().getVchrNum());
            LogHelper.info((String)DcFunctionModuleEnum.ADJUSTVCHR.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)voucher));
        }
    }

    private void handleInsertVoucher(AdjustVoucherVO voucherVO, List<AdjustVoucherDTO> insertVouchers, List<String> convertAmountCols, List<DimensionVO> assistDims) {
        String affectPeriodStart = voucherVO.getAffectPeriodStart();
        String affectPeriodEnd = voucherVO.getAffectPeriodEnd();
        voucherVO.setGroupId(UUIDUtils.newUUIDStr());
        int acctStartPeriod = Integer.parseInt(affectPeriodStart.substring(0, affectPeriodStart.length() - 1));
        int acctEndPeriod = Integer.parseInt(affectPeriodEnd.substring(0, affectPeriodEnd.length() - 1));
        for (int period = acctStartPeriod; period <= acctEndPeriod; ++period) {
            AdjustVoucherEO masterEO = new AdjustVoucherEO();
            BeanUtils.copyProperties(voucherVO, masterEO);
            boolean isYear = "N".equals(voucherVO.getPeriodType());
            if (isYear) {
                masterEO.setAcctYear(Integer.valueOf(period));
            }
            masterEO.setCreator(EnvCenter.getContextUserName());
            masterEO.setCreateTime(new Date());
            masterEO.setAdjustTypeCode(voucherVO.getAdjustTypeCode());
            masterEO.setId(UUIDUtils.newUUIDStr());
            masterEO.setVer(Long.valueOf(0L));
            masterEO.setAcctPeriod(Integer.valueOf(isYear ? 1 : period));
            masterEO.setVchrSrcType(AdjustVchrTypeEnum.HANDLE_ADJUST.getCode());
            AdjustVoucherDTO newVoucher = new AdjustVoucherDTO();
            newVoucher.setMasterEO(masterEO);
            ArrayList<AdjustVchrItemEO> newItemList = new ArrayList<AdjustVchrItemEO>();
            String finCurrCode = DataCenterUtil.getFinCurrCode((String)voucherVO.getUnitCode());
            for (AdjustVchrItemVO itemVO : voucherVO.getItems()) {
                Map tempConvertAmount;
                Map<String, Object> dimMap = this.assistBaseDataVo2Code(itemVO, assistDims);
                Map map = tempConvertAmount = itemVO.getConvertAmount() != null ? itemVO.getConvertAmount() : new HashMap();
                if (convertCurrencyMap.containsKey(finCurrCode)) {
                    String debitTitle = finCurrCode + "DEBIT";
                    String creditTitle = finCurrCode + "CREDIT";
                    boolean debitNotEmptyFlag = !ObjectUtils.isEmpty(itemVO.getDebit()) && BigDecimal.ZERO.compareTo(itemVO.getDebit()) != 0;
                    tempConvertAmount.put(debitNotEmptyFlag ? debitTitle : creditTitle, debitNotEmptyFlag ? itemVO.getDebit() : itemVO.getCredit());
                }
                itemVO.setConvertAmount(tempConvertAmount);
                AdjustVchrItemEO newItem = new AdjustVchrItemEO();
                BeanUtils.copyProperties(itemVO, newItem);
                if ("N".equals(voucherVO.getPeriodType())) {
                    newItem.setAcctYear(Integer.valueOf(period));
                }
                newItem.setId(UUIDUtils.newUUIDStr());
                newItem.setVer(Long.valueOf(0L));
                newItem.setItemOrder(itemVO.getItemOrder());
                newItem.setAcctYear(masterEO.getAcctYear());
                newItem.setAcctPeriod(Integer.valueOf(isYear ? 1 : period));
                newItem.setAssistDatas(dimMap);
                newItem.setAssistDims(assistDims);
                newItem.setVchrId(masterEO.getId());
                newItem.setPeriodType(masterEO.getPeriodType());
                newItem.setAmountDefaultValue();
                newItem.setConvertAmountDefaultValue(convertAmountCols);
                newItem.setAdjustTypeCode(voucherVO.getAdjustTypeCode());
                newItemList.add(newItem);
            }
            newVoucher.setItemEOList(newItemList);
            insertVouchers.add(newVoucher);
        }
    }

    private void handleUpdateVoucher(String influenceLaterFlag, AdjustVoucherVO voucherVO, List<AdjustVoucherDTO> updateVouchers, List<String> convertAmountCols, List<DimensionVO> assistDimList) {
        List<AdjustVoucherDTO> initialVchrList = this.find(voucherVO, assistDimList, convertAmountCols);
        if (CollectionUtils.isEmpty(initialVchrList)) {
            return;
        }
        String finCurrCode = DataCenterUtil.getFinCurrCode((String)voucherVO.getUnitCode());
        for (AdjustVchrItemVO itemVO : voucherVO.getItems()) {
            Map tempConvertAmount;
            Map map = tempConvertAmount = itemVO.getConvertAmount() != null ? itemVO.getConvertAmount() : new HashMap();
            if (convertCurrencyMap.containsKey(finCurrCode)) {
                String debitTitle = finCurrCode + "DEBIT";
                String creditTitle = finCurrCode + "CREDIT";
                boolean debitNotEmptyFlag = !ObjectUtils.isEmpty(itemVO.getDebit()) && BigDecimal.ZERO.compareTo(itemVO.getDebit()) != 0;
                tempConvertAmount.put(debitNotEmptyFlag ? debitTitle : creditTitle, debitNotEmptyFlag ? itemVO.getDebit() : itemVO.getCredit());
            }
            itemVO.setConvertAmount(tempConvertAmount);
        }
        int currentPeriod = voucherVO.getCurrentPeriod();
        boolean isCurrentEnd = AdjustVchrExecutePeriodEnum.CURRENT_TO_END_PERIOD.getCode().equals(influenceLaterFlag);
        String groupId1 = UUIDUtils.newUUIDStr();
        String groupId2 = UUIDUtils.newUUIDStr();
        for (AdjustVoucherDTO initialVchr : initialVchrList) {
            AdjustVchrItemEO newItem;
            AdjustVoucherEO newMaster = new AdjustVoucherEO();
            BeanUtils.copyProperties(initialVchr.getMasterVO(), newMaster);
            newMaster.setModifyUser(EnvCenter.getContextUserName());
            newMaster.setModifyTime(new Date());
            AdjustVoucherDTO newVoucher = new AdjustVoucherDTO();
            ArrayList<AdjustVchrItemEO> newItemEOList = new ArrayList<AdjustVchrItemEO>();
            int acctPeriodEnd = isCurrentEnd ? Integer.parseInt(voucherVO.getAffectPeriodEnd().substring(0, voucherVO.getAffectPeriodEnd().length() - 1)) : voucherVO.getCurrentPeriod();
            boolean isYear = "N".equals(voucherVO.getPeriodType());
            int newPeriod = isYear ? newMaster.getAcctYear() : newMaster.getAcctPeriod();
            if (newPeriod < currentPeriod) {
                newMaster.setAffectPeriodEnd(currentPeriod - 1 + newMaster.getPeriodType());
                for (AdjustVchrItemVO itemVO : initialVchr.getItemVOList()) {
                    newItem = new AdjustVchrItemEO();
                    BeanUtils.copyProperties(itemVO, newItem);
                    newItem.setVchrSrcType(AdjustVchrTypeEnum.HANDLE_ADJUST.getCode());
                    newItem.setAcctPeriod(Integer.valueOf(isYear ? 1 : newMaster.getAcctPeriod()));
                    newItem.setAdjustTypeCode(initialVchr.getMasterVO().getAdjustTypeCode());
                    newItem.setPeriodType(initialVchr.getMasterVO().getPeriodType());
                    newItem.setAssistDims(assistDimList);
                    newItem.setAssistDatas(itemVO.getAssistDatas());
                    newItem.setAmountDefaultValue();
                    newItem.setConvertAmountDefaultValue(convertAmountCols);
                    newItemEOList.add(newItem);
                }
            } else if (newPeriod <= acctPeriodEnd) {
                newMaster.setAffectPeriodStart(currentPeriod + newMaster.getPeriodType());
                newMaster.setAffectPeriodEnd(acctPeriodEnd + newMaster.getPeriodType());
                newMaster.setGroupId(groupId1);
                for (AdjustVchrItemVO itemVO : voucherVO.getItems()) {
                    newItem = new AdjustVchrItemEO();
                    BeanUtils.copyProperties(itemVO, newItem);
                    Map<String, Object> dimMap = this.assistBaseDataVo2Code(itemVO, assistDimList);
                    newItem.setId(UUIDUtils.newUUIDStr());
                    newItem.setVer(Long.valueOf(0L));
                    newItem.setVchrId(newMaster.getId());
                    newItem.setVchrSrcType(AdjustVchrTypeEnum.HANDLE_ADJUST.getCode());
                    newItem.setAcctPeriod(Integer.valueOf(isYear ? 1 : newMaster.getAcctPeriod()));
                    newItem.setAdjustTypeCode(initialVchr.getMasterVO().getAdjustTypeCode());
                    newItem.setPeriodType(initialVchr.getMasterVO().getPeriodType());
                    newItem.setAssistDims(assistDimList);
                    newItem.setAssistDatas(dimMap);
                    newItem.setAmountDefaultValue();
                    newItem.setConvertAmountDefaultValue(convertAmountCols);
                    newItemEOList.add(newItem);
                }
            } else {
                newMaster.setAffectPeriodStart(acctPeriodEnd + 1 + voucherVO.getPeriodType());
                newMaster.setGroupId(groupId2);
                for (AdjustVchrItemVO itemVO : initialVchr.getItemVOList()) {
                    newItem = new AdjustVchrItemEO();
                    BeanUtils.copyProperties(itemVO, newItem);
                    newItem.setId(UUIDUtils.newUUIDStr());
                    newItem.setVer(Long.valueOf(0L));
                    newItem.setVchrSrcType(AdjustVchrTypeEnum.HANDLE_ADJUST.getCode());
                    newItem.setAcctPeriod(Integer.valueOf(isYear ? 1 : newMaster.getAcctPeriod()));
                    newItem.setAdjustTypeCode(initialVchr.getMasterVO().getAdjustTypeCode());
                    newItem.setPeriodType(initialVchr.getMasterVO().getPeriodType());
                    newItem.setAssistDims(assistDimList);
                    newItem.setAssistDatas(itemVO.getAssistDatas());
                    newItem.setAmountDefaultValue();
                    newItem.setConvertAmountDefaultValue(convertAmountCols);
                    newItemEOList.add(newItem);
                }
            }
            newVoucher.setMasterEO(newMaster);
            newVoucher.setItemEOList(newItemEOList);
            updateVouchers.add(newVoucher);
        }
    }

    private Map<String, Object> assistBaseDataVo2Code(AdjustVchrItemVO itemVO, List<DimensionVO> assistDims) {
        if (itemVO.getAssistDatas() == null || itemVO.getAssistDatas().isEmpty()) {
            return new HashMap<String, Object>();
        }
        HashMap<String, Object> dimMap = new HashMap<String, Object>();
        for (DimensionVO assistDim : assistDims) {
            if (!itemVO.getAssistDatas().containsKey(assistDim.getCode()) || itemVO.getAssistDatas().get(assistDim.getCode()) == null) continue;
            if (StringUtils.isEmpty((String)assistDim.getReferField())) {
                dimMap.put(assistDim.getCode(), itemVO.getAssistDatas().get(assistDim.getCode()));
                continue;
            }
            AdjustVchrBaseDataVO basedata = (AdjustVchrBaseDataVO)JsonUtils.readValue((String)JsonUtils.writeValueAsString(itemVO.getAssistDatas().get(assistDim.getCode())), AdjustVchrBaseDataVO.class);
            dimMap.put(assistDim.getCode(), basedata.getCode());
        }
        return dimMap;
    }

    private List<String> getConvertAmountColumns() {
        String[] convertAmountColArr = new String[]{"CNYDEBIT", "CNYCREDIT", "HKDDEBIT", "HKDCREDIT", "USDDEBIT", "USDCREDIT"};
        return Arrays.asList(convertAmountColArr);
    }

    private void deleteVchrItemByVchrId(List<String> vchrIdList) {
        this.monitorExecute(monitor -> monitor.beforeDelete(vchrIdList));
        this.adjustVchrDao.delItemByVchrIdList(vchrIdList);
        this.monitorExecute(monitor -> monitor.afterDelete(vchrIdList));
    }

    private void batchInsetAdjustVchrItem(List<AdjustVchrItemEO> items) {
        this.monitorExecute(monitor -> monitor.beforeSave(items));
        this.adjustVchrDao.batchInsertItem(items);
        this.monitorExecute(monitor -> monitor.afterSave(items));
    }

    void monitorExecute(Consumer<IAdjustVoucherItemMonitor> function) {
        if (CollectionUtils.isEmpty(this.adjustVoucherItemMonitors)) {
            return;
        }
        for (IAdjustVoucherItemMonitor adjustVoucherItemMonitor : this.adjustVoucherItemMonitors) {
            try {
                function.accept(adjustVoucherItemMonitor);
            }
            catch (Exception e) {
                this.logger.error("\u8c03\u6574\u51ed\u8bc1\u76d1\u542c\u5668\u3010" + adjustVoucherItemMonitor.monitorName() + "\u3011\u6267\u884c\u5f02\u5e38:", e);
            }
        }
    }

    static {
        convertCurrencyMap.put("CNY", "\u4eba\u6c11\u5e01");
        convertCurrencyMap.put("HKD", "\u6e2f\u5e01");
        convertCurrencyMap.put("USD", "\u7f8e\u5143");
    }
}

