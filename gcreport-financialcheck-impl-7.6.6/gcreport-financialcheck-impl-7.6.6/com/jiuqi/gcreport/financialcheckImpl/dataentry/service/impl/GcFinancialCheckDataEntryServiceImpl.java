/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputConditionVO
 *  com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputVO
 *  com.jiuqi.gcreport.financialcheckapi.dataentry.vo.GcRelatedItemVO
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.FinancialCheckQueryTypeEnum
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.VchrSrcWayEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.RelatedItemSaveParam
 *  com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.item.service.impl.GcRelatedItemCommandServiceImpl
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.service.GcFinancialCheckDataEntryService;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.sql.GcFinancialCheckDataEntryQuerySQL;
import com.jiuqi.gcreport.financialcheckImpl.util.GcRelatedItemConvertor;
import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputConditionVO;
import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputVO;
import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.GcRelatedItemVO;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.check.enums.FinancialCheckQueryTypeEnum;
import com.jiuqi.gcreport.financialcheckcore.check.enums.VchrSrcWayEnum;
import com.jiuqi.gcreport.financialcheckcore.item.RelatedItemSaveParam;
import com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.item.service.impl.GcRelatedItemCommandServiceImpl;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class GcFinancialCheckDataEntryServiceImpl
implements GcFinancialCheckDataEntryService {
    private static final Double ZERO = 0.0;
    @Autowired
    private GcRelatedItemDao gcRelatedItemDao;
    @Autowired
    private GcRelatedItemConvertor convertor;
    @Autowired
    private GcRelatedItemCommandServiceImpl gcRelatedItemCommandService;

    @Override
    public DataInputVO query(DataInputConditionVO condition) {
        DataInputVO vo;
        if (!this.checkCondition(condition)) {
            return new DataInputVO();
        }
        FinancialCheckQueryTypeEnum queryType = FinancialCheckQueryTypeEnum.fromName((String)condition.getQueryType());
        switch (queryType) {
            case DATAINPUT_ALL: {
                vo = this.queryAllItem(condition);
                break;
            }
            case DATAINPUT_CHECKED: {
                vo = this.queryCheckedVchr(condition);
                break;
            }
            case DATAINPUT_UNCHECK: {
                vo = this.queryUncheckVchr(condition);
                break;
            }
            case DATAINPUT_DFDATA: {
                vo = this.queryOppUncheckVchr(condition);
                break;
            }
            default: {
                vo = new DataInputVO();
            }
        }
        return vo;
    }

    private boolean checkCondition(DataInputConditionVO condition) {
        return condition.getUnitId() != null && condition.getAcctYear() != null && condition.getAcctPeriod() != null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private DataInputVO queryAllItem(DataInputConditionVO condition) {
        DataInputVO vo = new DataInputVO();
        GcFinancialCheckDataEntryQuerySQL sqlBuilder = new GcFinancialCheckDataEntryQuerySQL(condition);
        try {
            List allItemCount = this.gcRelatedItemDao.selectFirstList(Integer.class, sqlBuilder.allItemCountSQL().toString(), new Object[0]);
            if (!allItemCount.isEmpty() && allItemCount.get(0) != null) {
                vo.setAllVchrTotal((Integer)allItemCount.get(0));
            }
            int offset = condition.getAllVchrStartPosition();
            int rowCount = condition.getAllVchrPageSize();
            List allItemData = rowCount == Integer.MAX_VALUE ? this.gcRelatedItemDao.selectEntity(sqlBuilder.allVchrSQL().toString(), new Object[0]) : this.gcRelatedItemDao.selectEntityByPaging(sqlBuilder.allVchrSQL().toString(), offset, offset + rowCount, new Object[0]);
            List<GcRelatedItemVO> allVchrDatas = this.convertor.converE2V(allItemData);
            vo.setAllVchrData(allVchrDatas);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)sqlBuilder.getLocalGroupId());
            IdTemporaryTableUtils.deteteByGroupId((String)sqlBuilder.getOppGroupId());
        }
        return vo;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private DataInputVO queryCheckedVchr(DataInputConditionVO condition) {
        DataInputVO vo = new DataInputVO();
        GcFinancialCheckDataEntryQuerySQL sqlBuilder = new GcFinancialCheckDataEntryQuerySQL(condition);
        try {
            List checkedVchrCount = this.gcRelatedItemDao.selectFirstList(Integer.class, sqlBuilder.checkedVchrCountSQL().toString(), new Object[0]);
            if (!checkedVchrCount.isEmpty() && checkedVchrCount.get(0) != null) {
                vo.setCheckedVchrTotal((Integer)checkedVchrCount.get(0));
            }
            int offset = condition.getcheckedStartPosition();
            int rowCount = condition.getCheckedPageSize();
            List checkedVchr = rowCount == Integer.MAX_VALUE ? this.gcRelatedItemDao.selectEntity(sqlBuilder.checkedVchrSQL().toString(), new Object[0]) : this.gcRelatedItemDao.selectEntityByPaging(sqlBuilder.checkedVchrSQL().toString(), offset, offset + rowCount, new Object[0]);
            List<GcRelatedItemVO> checkedVchrData = this.convertor.converE2V(checkedVchr);
            vo.setCheckedVchrdData(checkedVchrData);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)sqlBuilder.getLocalGroupId());
            IdTemporaryTableUtils.deteteByGroupId((String)sqlBuilder.getOppGroupId());
        }
        return vo;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private DataInputVO queryOppUncheckVchr(DataInputConditionVO condition) {
        DataInputVO vo = new DataInputVO();
        GcFinancialCheckDataEntryQuerySQL sqlBuilder = new GcFinancialCheckDataEntryQuerySQL(condition);
        try {
            List oppUncheckVchrCount = this.gcRelatedItemDao.selectFirstList(Integer.class, sqlBuilder.uncheckVchrCountSQL(false).toString(), new Object[0]);
            if (!oppUncheckVchrCount.isEmpty() && oppUncheckVchrCount.get(0) != null) {
                vo.setOppUncheckedTotal((Integer)oppUncheckVchrCount.get(0));
            }
            int offset = condition.getOppUncheckedStartPosition();
            int rowCount = condition.getOppUncheckedPageSize();
            List oppUncheckVchr = rowCount == Integer.MAX_VALUE ? this.gcRelatedItemDao.selectEntity(sqlBuilder.uncheckVchrSQL(false).toString(), new Object[0]) : this.gcRelatedItemDao.selectEntityByPaging(sqlBuilder.uncheckVchrSQL(false).toString(), offset, offset + rowCount, new Object[0]);
            List<GcRelatedItemVO> oppUncheckVchrData = this.convertor.converE2V(oppUncheckVchr);
            vo.setOppUncheckedData(oppUncheckVchrData);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)sqlBuilder.getLocalGroupId());
            IdTemporaryTableUtils.deteteByGroupId((String)sqlBuilder.getOppGroupId());
        }
        return vo;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private DataInputVO queryUncheckVchr(DataInputConditionVO condition) {
        List<GcRelatedItemVO> uncheckVchrData;
        DataInputVO vo = new DataInputVO();
        GcFinancialCheckDataEntryQuerySQL sqlBuilder = new GcFinancialCheckDataEntryQuerySQL(condition);
        try {
            List uncheckVchrCount = this.gcRelatedItemDao.selectFirstList(Integer.class, sqlBuilder.uncheckVchrCountSQL(true).toString(), new Object[0]);
            if (!uncheckVchrCount.isEmpty() && uncheckVchrCount.get(0) != null) {
                vo.setUncheckVchrTotal((Integer)uncheckVchrCount.get(0));
            }
            int offset = condition.getUncheckedStartPosition();
            int rowCount = condition.getUncheckedPageSize();
            List uncheckVchr = rowCount == Integer.MAX_VALUE ? this.gcRelatedItemDao.selectEntity(sqlBuilder.uncheckVchrSQL(true).toString(), new Object[0]) : this.gcRelatedItemDao.selectEntityByPaging(sqlBuilder.uncheckVchrSQL(true).toString(), offset, offset + rowCount, new Object[0]);
            uncheckVchrData = this.convertor.converE2V(uncheckVchr);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)sqlBuilder.getLocalGroupId());
            IdTemporaryTableUtils.deteteByGroupId((String)sqlBuilder.getOppGroupId());
        }
        vo.setUncheckVchrData(uncheckVchrData);
        return vo;
    }

    private String getOrgVer(Integer acctYear, Integer acctPeriod) {
        String dateStr = String.format("%04d", acctYear) + String.format("%02d", acctPeriod) + "01";
        return dateStr;
    }

    @Override
    @OuterTransaction
    public void saveRelatedItems(List<GcRelatedItemVO> itemVoS) {
        if (CollectionUtils.isEmpty(itemVoS)) {
            return;
        }
        List existIds = itemVoS.stream().filter(item -> StringUtils.hasText(item.getId())).map(GcRelatedItemVO::getId).collect(Collectors.toList());
        ArrayList needUpdateItems = new ArrayList();
        ArrayList needAddItems = new ArrayList();
        itemVoS.forEach(item -> {
            GcRelatedItemEO itemEO = this.convertor.convertVO2EO((GcRelatedItemVO)item);
            this.buildDefault(itemEO);
            if (existIds.contains(item.getId())) {
                needUpdateItems.add(itemEO);
            } else {
                String s = this.saveCheck(itemEO, FinancialCheckConfigUtils.getCheckOrgType());
                if (StringUtils.hasText(s)) {
                    throw new BusinessRuntimeException(s);
                }
                needAddItems.add(itemEO);
            }
        });
        RelatedItemSaveParam relatedItemSaveParam = new RelatedItemSaveParam(needAddItems, needUpdateItems, new ArrayList());
        this.gcRelatedItemCommandService.batchSave(relatedItemSaveParam);
    }

    private GcRelatedItemEO buildDefault(GcRelatedItemEO item) {
        if (item.getInputWay() == null) {
            item.setInputWay(VchrSrcWayEnum.BATCHINPUT.name());
            item.setCreateTime(new Date());
            item.setRecver(new Long(0L));
            item.setChkState(CheckStateEnum.UNCHECKED.name());
        }
        if (item.getDebit() == null) {
            item.setDebit(Double.valueOf(0.0));
        }
        if (item.getDebitOrig() == null) {
            item.setDebitOrig(Double.valueOf(0.0));
        }
        if (item.getCredit() == null) {
            item.setCredit(Double.valueOf(0.0));
        }
        if (item.getCreditOrig() == null) {
            item.setCreditOrig(Double.valueOf(0.0));
        }
        if (item.getChkAmtC() == null) {
            item.setChkAmtC(Double.valueOf(0.0));
        }
        if (item.getChkAmtD() == null) {
            item.setChkAmtD(Double.valueOf(0.0));
        }
        if (item.getChkState() == null) {
            item.setChkState(CheckStateEnum.UNCHECKED.name());
        }
        if (!StringUtils.hasText(item.getGcNumber())) {
            item.setGcNumber("SystemDefault");
        }
        if (!StringUtils.hasText(item.getId())) {
            item.setId(UUIDUtils.newUUIDStr());
        }
        return item;
    }

    private String saveCheck(GcRelatedItemEO item, String orgType) {
        if (StringUtils.isEmpty(item.getUnitId())) {
            return "\u672c\u65b9\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a\u3002";
        }
        if (StringUtils.isEmpty(item.getOppUnitId())) {
            return "\u5bf9\u65b9\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a\u3002";
        }
        if (StringUtils.isEmpty(item.getSubjectCode())) {
            return "\u79d1\u76ee\u4e0d\u80fd\u4e3a\u7a7a\u3002";
        }
        if (StringUtils.isEmpty(item.getOriginalCurr())) {
            return "\u5e01\u79cd(\u539f\u5e01)\u4e0d\u80fd\u4e3a\u7a7a\u3002";
        }
        if (StringUtils.isEmpty(item.getCurrency())) {
            return "\u5e01\u79cd(\u672c\u4f4d\u5e01)\u4e0d\u80fd\u4e3a\u7a7a\u3002";
        }
        if (Objects.equals(ZERO, item.getDebit()) && Objects.equals(ZERO, item.getCredit())) {
            return "\u672c\u4f4d\u5e01\u91d1\u989d\u4e0d\u80fd\u90fd\u4e3a\u96f6\u6216\u7a7a\u3002";
        }
        if (Objects.equals(ZERO, item.getDebitOrig()) && Objects.equals(ZERO, item.getCreditOrig())) {
            return "\u539f\u5e01\u91d1\u989d\u4e0d\u80fd\u90fd\u4e3a\u96f6\u6216\u7a7a\u3002";
        }
        YearPeriodObject yp = new YearPeriodObject(item.getAcctYear().intValue(), item.getAcctPeriod() == 0 ? 1 : item.getAcctPeriod());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO localOrg = tool.getOrgByCode(item.getUnitId());
        if (Objects.isNull(localOrg)) {
            return "\u672c\u65b9\u5355\u4f4d" + item.getUnitId() + "\u5728\u5173\u8054\u4ea4\u6613\u6811\u5f62\u4e0a\u4e0d\u5b58\u5728\u3002";
        }
        GcOrgCacheVO oppOrg = tool.getOrgByCode(item.getOppUnitId());
        if (Objects.isNull(oppOrg)) {
            return "\u5bf9\u65b9\u5355\u4f4d" + item.getOppUnitId() + "\u5728\u5173\u8054\u4ea4\u6613\u6811\u5f62\u4e0a\u4e0d\u5b58\u5728\u3002";
        }
        GcOrgCacheVO mergeOrg = tool.getCommonUnit(localOrg, oppOrg);
        if (Objects.isNull(mergeOrg)) {
            return MessageFormat.format("\u672c\u5bf9\u65b9\u5355\u4f4d\uff1a{0} \u3001{1} \u6ca1\u6709\u5171\u540c\u4e0a\u7ea7", localOrg.getTitle(), oppOrg.getTitle());
        }
        return "";
    }

    @Override
    public List<GcRelatedItemVO> listByIds(List<String> ids) {
        List itemEOS = this.gcRelatedItemDao.queryByIds(ids);
        return this.convertor.converE2V(itemEOS);
    }
}

