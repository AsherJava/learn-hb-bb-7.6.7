/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusParam
 *  com.jiuqi.gc.financial.financialstatus.vo.FinancialUnitStatusVO
 *  com.jiuqi.gc.financial.status.enums.FinancialStatusEnum
 *  com.jiuqi.gc.financial.status.event.FinancialStatusChangeEventData
 *  com.jiuqi.gc.financial.status.event.FinancialStatusUnitChangeEvent
 *  com.jiuqi.gc.financialcubes.financialstatus.entity.FinancialUnitStatusEO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gc.financialcubes.relatedtransactionstatus.service.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusParam;
import com.jiuqi.gc.financial.financialstatus.vo.FinancialUnitStatusVO;
import com.jiuqi.gc.financial.status.enums.FinancialStatusEnum;
import com.jiuqi.gc.financial.status.event.FinancialStatusChangeEventData;
import com.jiuqi.gc.financial.status.event.FinancialStatusUnitChangeEvent;
import com.jiuqi.gc.financialcubes.financialstatus.entity.FinancialUnitStatusEO;
import com.jiuqi.gc.financialcubes.relatedtransactionstatus.dao.RelatedTransactionUnitStatusDao;
import com.jiuqi.gc.financialcubes.relatedtransactionstatus.service.RelatedTransactionUnitStatusService;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RelatedTransactionUnitStatusServiceImpl
implements RelatedTransactionUnitStatusService {
    @Autowired
    private RelatedTransactionUnitStatusDao unitStatusDao;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public PageInfo<FinancialUnitStatusVO> listOpenUnit(FinancialStatusParam param) {
        YearPeriodObject yp = new YearPeriodObject(null, param.getDataTime());
        Date date = yp.formatYP().getEndDate();
        String unitParentsString = this.getUnitParentsString(param, yp);
        List<FinancialUnitStatusEO> eoPageInfo = this.unitStatusDao.listOpenUnit(param, unitParentsString, date);
        if (CollectionUtils.isEmpty(eoPageInfo)) {
            return PageInfo.empty();
        }
        return this.getPageInfoAndSortedByOrg(param, yp, eoPageInfo);
    }

    @Override
    public PageInfo<FinancialUnitStatusVO> listCloseUnit(FinancialStatusParam param) {
        YearPeriodObject yp = new YearPeriodObject(null, param.getDataTime());
        Date date = yp.formatYP().getEndDate();
        String unitParentsString = this.getUnitParentsString(param, yp);
        List<FinancialUnitStatusEO> eoPageInfo = this.unitStatusDao.listCloseUnit(param, unitParentsString, date);
        if (CollectionUtils.isEmpty(eoPageInfo)) {
            return PageInfo.empty();
        }
        return this.getPageInfoAndSortedByOrg(param, yp, eoPageInfo);
    }

    @NotNull
    private PageInfo<FinancialUnitStatusVO> getPageInfoAndSortedByOrg(FinancialStatusParam param, YearPeriodObject yp, List<FinancialUnitStatusEO> eoPageInfo) {
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)param.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        List orgTree = tool.getOrgTree();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        this.getOrgSortMap(orgTree, map, 0);
        eoPageInfo.sort((o1, o2) -> {
            if (o1.getUnitCode().equals(o2.getUnitCode())) {
                return 0;
            }
            if (map.get(o1.getUnitCode()) == null) {
                return 1;
            }
            if (map.get(o2.getUnitCode()) == null) {
                return -1;
            }
            return (Integer)map.get(o1.getUnitCode()) - (Integer)map.get(o2.getUnitCode());
        });
        int first = (param.getPageNum() - 1) * param.getPageSize();
        int end = param.getPageSize() * param.getPageNum();
        end = Math.min(end, eoPageInfo.size());
        List<FinancialUnitStatusEO> result = eoPageInfo.subList(first, end);
        PageInfo of = PageInfo.of(result, (int)eoPageInfo.size());
        return this.converterToVO((PageInfo<FinancialUnitStatusEO>)of, param, yp);
    }

    private int getOrgSortMap(List<GcOrgCacheVO> orgTree, Map<String, Integer> orgSortMap, int index) {
        int i;
        for (i = 0; i < orgTree.size(); ++i) {
            orgSortMap.put(orgTree.get(i).getId(), i + index);
            if (CollectionUtils.isEmpty((Collection)orgTree.get(i).getChildren())) continue;
            index += i + 1;
            index = this.getOrgSortMap(orgTree.get(i).getChildren(), orgSortMap, index);
        }
        return index += i;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public String openUnit(FinancialStatusParam param) {
        ArrayList<String> unitCodes = new ArrayList<String>(param.getUnitCodes());
        ArrayList<String> singleUnitCodes = new ArrayList<String>();
        GcOrgCenterService gcOrgCenterService = GcOrgPublicTool.getInstance((String)param.getOrgType());
        for (String code2 : param.getUnitCodes()) {
            if (!gcOrgCenterService.getOrgByCode(code2).isLeaf()) continue;
            singleUnitCodes.add(code2);
            unitCodes.remove(code2);
        }
        String unitCodeTempGroupId = UUIDUtils.newUUIDStr();
        IdTemporaryTableUtils.insertTempStr((String)unitCodeTempGroupId, (Collection)param.getUnitCodes());
        try {
            List<FinancialUnitStatusEO> unitStatusEOS = this.unitStatusDao.selectByUnitCode(param, unitCodeTempGroupId, "RELATED_TRANSACTION", FinancialStatusEnum.CLOSE.getCode());
            List<Object> openedUnitCodes = new ArrayList();
            List<Object> closedUnitCodes = new ArrayList();
            if (CollectionUtils.isEmpty(unitStatusEOS)) {
                String allClosedUnitCodes = "\u5f53\u524d\u671f\u5355\u4f4d\u5168\u4e3a\u5df2\u5f00\u8d26\u5355\u4f4d\uff0c\u4e0d\u505a\u5f00\u8d26\u5904\u7406\uff01";
                return allClosedUnitCodes;
            }
            List allClosedUnitCodes = unitStatusEOS.stream().map(FinancialUnitStatusEO::getUnitCode).collect(Collectors.toList());
            openedUnitCodes = unitCodes.stream().filter(code -> !allClosedUnitCodes.contains(code)).collect(Collectors.toList());
            closedUnitCodes = unitCodes.stream().filter(allClosedUnitCodes::contains).collect(Collectors.toList());
            unitCodes.removeAll(openedUnitCodes);
            ArrayList<String> failUnitCodes = new ArrayList();
            if (!param.getDataTime().endsWith("12")) {
                List<FinancialUnitStatusEO> failUnitStatusEOs = this.unitStatusDao.selectUnitCodesByDataTime(param, unitCodeTempGroupId, "RELATED_TRANSACTION", FinancialStatusEnum.CLOSE.getCode());
                if (!CollectionUtils.isEmpty(failUnitStatusEOs)) {
                    List allUnitCodes = failUnitStatusEOs.stream().map(FinancialUnitStatusEO::getUnitCode).collect(Collectors.toList());
                    failUnitCodes = unitCodes.stream().filter(allUnitCodes::contains).collect(Collectors.toList());
                }
                unitCodes.removeAll(closedUnitCodes);
                closedUnitCodes.removeAll(failUnitCodes);
            }
            Iterator<FinancialUnitStatusEO> iterator = unitStatusEOS.iterator();
            while (iterator.hasNext()) {
                FinancialUnitStatusEO eo = iterator.next();
                if (CollectionUtils.isEmpty(failUnitCodes) || !failUnitCodes.contains(eo.getUnitCode())) continue;
                iterator.remove();
            }
            this.execute(param, unitCodes, unitStatusEOS, FinancialStatusEnum.OPEN.getCode());
            FinancialStatusChangeEventData eventData = new FinancialStatusChangeEventData("RELATED_TRANSACTION", param.getDataTime(), param.getPeriodType(), param.getUnitCodes(), FinancialStatusEnum.CLOSE.getCode());
            this.applicationContext.publishEvent((ApplicationEvent)new FinancialStatusUnitChangeEvent(eventData));
            int skipNuber = openedUnitCodes.size() + singleUnitCodes.size();
            if (param.getUnitCodes().size() == 1) {
                String unit = this.getUnit(gcOrgCenterService, (String)param.getUnitCodes().get(0));
                if (!CollectionUtils.isEmpty(failUnitCodes)) {
                    String string = String.format("\u3010%1$s\u3011\u5355\u4f4d\u5728\u3010%2$s\u3011\u4e4b\u540e\u5904\u4e8e\u5173\u8d26\u72b6\u6001\uff0c\u6545\u5f53\u524d\u671f\u5f00\u8d26\u5931\u8d25\uff01", unit, param.getDataTime());
                    return string;
                }
                if (openedUnitCodes.containsAll(param.getUnitCodes())) {
                    String string = String.format("\u3010%1$s\u3011\u5728\u5f53\u524d\u671f\u5df2\u5f00\u8d26\uff0c\u8df3\u8fc7\u5904\u7406\uff01", unit);
                    return string;
                }
                String string = String.format("\u3010%1$s\u3011\u5355\u4f4d\u5f00\u8d26\u6210\u529f\uff01", unit);
                return string;
            }
            this.recordOpenLog(param, singleUnitCodes, gcOrgCenterService, openedUnitCodes, closedUnitCodes, failUnitCodes);
            String string = String.format("\u5355\u4f4d\u5f00\u8d26\uff0c\u5171\u5904\u7406\uff1a%1$d\u4e2a\uff0c\u6210\u529f\uff1a%2$d\u4e2a\uff0c\u5931\u8d25\uff1a%3$d\u4e2a\uff0c\u8df3\u8fc7\uff1a%4$d\u4e2a", param.getUnitCodes().size(), unitStatusEOS.size(), failUnitCodes.size(), skipNuber);
            return string;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)unitCodeTempGroupId);
        }
    }

    private void recordOpenLog(FinancialStatusParam param, List<String> singleUnitCodes, GcOrgCenterService gcOrgCenterService, List<String> openedUnitCodes, List<String> closedUnitCodes, List<String> failUnitCodes) {
        ArrayList<String> singleUnitList = new ArrayList<String>();
        ArrayList<String> openedUnitList = new ArrayList<String>();
        ArrayList<String> closedUnitList = new ArrayList<String>();
        ArrayList<String> failUnitList = new ArrayList<String>();
        for (String code : param.getUnitCodes()) {
            if (singleUnitCodes.contains(code)) {
                singleUnitList.add(this.getUnit(gcOrgCenterService, code));
                continue;
            }
            if (openedUnitCodes.contains(code)) {
                openedUnitList.add(this.getUnit(gcOrgCenterService, code));
            }
            if (closedUnitCodes.contains(code)) {
                closedUnitList.add(this.getUnit(gcOrgCenterService, code));
            }
            if (!failUnitCodes.contains(code)) continue;
            failUnitList.add(this.getUnit(gcOrgCenterService, code));
        }
        StringBuilder log = new StringBuilder();
        log.append("\u5f00\u8d26\u6210\u529f\u7684\u5408\u5e76\u5355\u4f4d\uff1a").append(closedUnitList).append("\n");
        log.append("\u5df2\u7ecf\u5f00\u8d26\u7684\u4e0d\u9700\u8981\u5f00\u8d26\u7684\u5408\u5e76\u5355\u4f4d\uff1a").append(openedUnitList).append("\n");
        log.append("\u4ee5\u540e\u671f\u671f\u5173\u8d26\uff0c\u5f53\u671f\u4e0d\u80fd\u5f00\u8d26\u7684\u5355\u4f4d: ").append(failUnitList).append("\n");
        log.append("\u4e0d\u505a\u5f00\u8d26\u5904\u7406\u7684\u5355\u6237\u5355\u4f4d\uff1a").append(singleUnitList).append("\n");
        LogHelper.info((String)"\u5f00\u5173\u8d26\u7ba1\u7406--\u5173\u8054\u4ea4\u6613\u5f00\u5173\u8d26", (String)"\u5355\u4f4d\u5f00\u8d26", (String)log.toString());
    }

    @NotNull
    private String getUnit(GcOrgCenterService gcOrgCenterService, String code) {
        return code + "|" + gcOrgCenterService.getOrgByCode(code).getTitle();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public String closeUnit(FinancialStatusParam param) {
        ArrayList<String> unitCodes = new ArrayList<String>(param.getUnitCodes());
        ArrayList<String> singleUnitCodes = new ArrayList<String>();
        GcOrgCenterService gcOrgCenterService = GcOrgPublicTool.getInstance((String)param.getOrgType());
        for (String code : param.getUnitCodes()) {
            if (!gcOrgCenterService.getOrgByCode(code).isLeaf()) continue;
            singleUnitCodes.add(code);
            unitCodes.remove(code);
        }
        String unitCodeTempGroupId = UUIDUtils.newUUIDStr();
        IdTemporaryTableUtils.insertTempStr((String)unitCodeTempGroupId, (Collection)param.getUnitCodes());
        try {
            List<FinancialUnitStatusEO> failUnitStatusEOs;
            List<FinancialUnitStatusEO> unitStatusEOS = this.unitStatusDao.selectByUnitCode(param, unitCodeTempGroupId, "RELATED_TRANSACTION", FinancialStatusEnum.CLOSE.getCode());
            List<Object> closedUnitCodes = new ArrayList();
            if (unitStatusEOS != null) {
                List allClosedUnitCodes = unitStatusEOS.stream().map(FinancialUnitStatusEO::getUnitCode).collect(Collectors.toList());
                closedUnitCodes = unitCodes.stream().filter(allClosedUnitCodes::contains).collect(Collectors.toList());
                unitCodes.removeAll(closedUnitCodes);
            }
            ArrayList<String> failUnitCodes = new ArrayList();
            if (!param.getDataTime().endsWith("01") && (failUnitStatusEOs = this.unitStatusDao.selectUnitCodesByDataTime(param, unitCodeTempGroupId, "RELATED_TRANSACTION", FinancialStatusEnum.OPEN.getCode())) != null) {
                List allUnitCodes = failUnitStatusEOs.stream().map(FinancialUnitStatusEO::getUnitCode).collect(Collectors.toList());
                failUnitCodes = unitCodes.stream().filter(allUnitCodes::contains).collect(Collectors.toList());
                unitCodes.removeAll(failUnitCodes);
            }
            List<FinancialUnitStatusEO> opendUnitStatusEOs = this.unitStatusDao.selectByUnitCode(param, unitCodeTempGroupId, "RELATED_TRANSACTION", FinancialStatusEnum.OPEN.getCode());
            ArrayList<String> openedUnitCodes = new ArrayList();
            if (CollectionUtils.isEmpty(opendUnitStatusEOs)) {
                openedUnitCodes = opendUnitStatusEOs.stream().map(FinancialUnitStatusEO::getUnitCode).collect(Collectors.toList());
                unitCodes.removeAll(openedUnitCodes);
            }
            this.execute(param, unitCodes, opendUnitStatusEOs, FinancialStatusEnum.CLOSE.getCode());
            FinancialStatusChangeEventData eventData = new FinancialStatusChangeEventData("RELATED_TRANSACTION", param.getDataTime(), param.getPeriodType(), param.getUnitCodes(), FinancialStatusEnum.CLOSE.getCode());
            this.applicationContext.publishEvent((ApplicationEvent)new FinancialStatusUnitChangeEvent(eventData));
            int allNumber = param.getUnitCodes().size();
            int successNumber = unitCodes.size() + opendUnitStatusEOs.size();
            int skipNumber = closedUnitCodes.size() + singleUnitCodes.size();
            if (param.getUnitCodes().size() == 1) {
                String unit = this.getUnit(gcOrgCenterService, (String)param.getUnitCodes().get(0));
                if (!CollectionUtils.isEmpty(failUnitCodes)) {
                    String string = String.format("\u3010%1$s\u3011\u5355\u4f4d\u5728\u3010%2$s\u3011\u4e4b\u524d\u5904\u4e8e\u5f00\u8d26\u72b6\u6001\uff0c\u6545\u5f53\u524d\u671f\u5173\u8d26\u5931\u8d25\uff01", unit, param.getDataTime());
                    return string;
                }
                if (closedUnitCodes.containsAll(param.getUnitCodes())) {
                    String string = String.format("\u3010%1$s\u3011\u5728\u5f53\u524d\u671f\u5df2\u5173\u8d26\uff0c\u8df3\u8fc7\u5904\u7406\uff01", unit);
                    return string;
                }
                String string = String.format("\u3010%1$s\u3011\u5355\u4f4d\u5173\u8d26\u6210\u529f\uff01", unit);
                return string;
            }
            this.recordCloseLog(param, singleUnitCodes, gcOrgCenterService, unitCodes, openedUnitCodes, closedUnitCodes, failUnitCodes);
            String string = String.format("\u5355\u4f4d\u5173\u8d26\uff0c\u5171\u5904\u7406\uff1a%1$d\u4e2a\uff0c\u6210\u529f\uff1a%2$d\u4e2a\uff0c\u5931\u8d25\uff1a%3$d\u4e2a\uff0c\u8df3\u8fc7\uff1a%4$d\u4e2a", allNumber, successNumber, failUnitCodes.size(), skipNumber);
            return string;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)unitCodeTempGroupId);
        }
    }

    private void recordCloseLog(FinancialStatusParam param, List<String> singleUnitCodes, GcOrgCenterService gcOrgCenterService, List<String> unitCodes, List<String> openedUnitCodes, List<String> closedUnitCodes, List<String> failUnitCodes) {
        ArrayList<String> singleUnitList = new ArrayList<String>();
        ArrayList<String> newCloseUnit = new ArrayList<String>();
        ArrayList<String> openedUnitList = new ArrayList<String>();
        ArrayList<String> closedUnitList = new ArrayList<String>();
        ArrayList<String> failUnitList = new ArrayList<String>();
        for (String code : param.getUnitCodes()) {
            if (singleUnitCodes.contains(code)) {
                singleUnitList.add(this.getUnit(gcOrgCenterService, code));
                continue;
            }
            if (unitCodes.contains(code)) {
                newCloseUnit.add(this.getUnit(gcOrgCenterService, code));
            }
            if (openedUnitCodes.contains(code)) {
                openedUnitList.add(this.getUnit(gcOrgCenterService, code));
            }
            if (closedUnitCodes.contains(code)) {
                closedUnitList.add(this.getUnit(gcOrgCenterService, code));
            }
            if (!failUnitCodes.contains(code)) continue;
            failUnitList.add(this.getUnit(gcOrgCenterService, code));
        }
        StringBuilder log = new StringBuilder();
        log.append("\u7b2c\u4e00\u6b21\u5173\u8d26\u6210\u529f\u7684\u5408\u5e76\u5355\u4f4d\uff1a").append(newCloseUnit).append("\n");
        log.append("\u91cd\u65b0\u5173\u8d26\u7684\u5355\u4f4d\u6709\uff1a").append(openedUnitList).append("\n");
        log.append("\u5df2\u7ecf\u5173\u8d26\u7684\u4e0d\u9700\u8981\u5173\u8d26\u7684\u5408\u5e76\u5355\u4f4d\uff1a").append(closedUnitList).append("\n");
        log.append("\u4ee5\u524d\u671f\u5f00\u8d26\uff0c\u5f53\u671f\u4e0d\u80fd\u5173\u8d26\u7684\u5355\u4f4d\uff1a").append(failUnitList).append("\n");
        log.append("\u4e0d\u505a\u5173\u8d26\u5904\u7406\u7684\u5355\u6237\u5355\u4f4d\uff1a").append(singleUnitList).append("\n");
        LogHelper.info((String)"\u5f00\u5173\u8d26\u7ba1\u7406--\u5173\u8054\u4ea4\u6613\u5f00\u5173\u8d26", (String)"\u5355\u4f4d\u5173\u8d26", (String)log.toString());
    }

    public void execute(FinancialStatusParam param, List<String> newUnitCodes, List<FinancialUnitStatusEO> unitStatusEOS, String status) {
        if (unitStatusEOS != null) {
            for (FinancialUnitStatusEO eo : unitStatusEOS) {
                if (status.equals(FinancialStatusEnum.CLOSE.getCode())) {
                    eo.setStatus(FinancialStatusEnum.CLOSE.getCode());
                    eo.setInvalidTime(new Date());
                } else {
                    eo.setStatus(FinancialStatusEnum.OPEN.getCode());
                    eo.setValidTime(new Date());
                    eo.setInvalidTime(this.getInvalidTime());
                }
                eo.setCreateUser(NpContextHolder.getContext().getUser().getName());
                eo.setUpdateTime(new Date());
                eo.setModuleCode("RELATED_TRANSACTION");
                this.unitStatusDao.update((BaseEntity)eo);
            }
        }
        if (!CollectionUtils.isEmpty(newUnitCodes)) {
            for (String unitCode : newUnitCodes) {
                this.saveUnit(param, unitCode, status);
            }
        }
    }

    private void saveUnit(FinancialStatusParam param, String unitCode, String status) {
        param.setUnitCode(unitCode);
        FinancialUnitStatusEO newEo = new FinancialUnitStatusEO();
        newEo.setUnitCode(param.getUnitCode());
        newEo.setDataTime(param.getDataTime());
        if (status.equals(FinancialStatusEnum.OPEN.getCode())) {
            newEo.setStatus(FinancialStatusEnum.OPEN.getCode());
        } else {
            newEo.setStatus(FinancialStatusEnum.CLOSE.getCode());
        }
        newEo.setCreateUser(NpContextHolder.getContext().getUser().getName());
        newEo.setPeriodType(param.getPeriodType());
        newEo.setUpdateTime(new Date());
        newEo.setValidTime(new Date());
        newEo.setInvalidTime(this.getInvalidTime());
        newEo.setModuleCode("RELATED_TRANSACTION");
        this.unitStatusDao.save((DefaultTableEntity)newEo);
    }

    private Date getInvalidTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(1, 9999);
        calendar.set(2, 11);
        calendar.set(5, 31);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    private String getUnitParentsString(FinancialStatusParam param, YearPeriodObject yp) {
        if ("MD_ORG".equals(param.getOrgType())) {
            GcOrgBaseTool baseTool = GcOrgBaseTool.getInstance();
            return baseTool.getOrgByCode(param.getUnitCode()).getParents();
        }
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)param.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        return orgService.getOrgByCode(param.getUnitCode()).getParentStr();
    }

    private PageInfo<FinancialUnitStatusVO> converterToVO(PageInfo<FinancialUnitStatusEO> eoPageInfo, FinancialStatusParam param, YearPeriodObject yp) {
        if (eoPageInfo.getSize() <= 0) {
            return new PageInfo();
        }
        ArrayList<FinancialUnitStatusVO> voList = new ArrayList<FinancialUnitStatusVO>();
        for (FinancialUnitStatusEO eo : eoPageInfo.getList()) {
            FinancialUnitStatusVO vo = new FinancialUnitStatusVO();
            BeanUtils.copyProperties(eo, vo);
            String title = "MD_ORG".equals(param.getOrgType()) ? GcOrgBaseTool.getInstance().getOrgByCode(eo.getUnitCode()).getTitle() : GcOrgPublicTool.getInstance((String)param.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp).getOrgByCode(eo.getUnitCode()).getTitle();
            vo.setUnitTitle(eo.getUnitCode() + "|" + title);
            voList.add(vo);
        }
        return PageInfo.of(voList, (int)eoPageInfo.getPageNum(), (int)eoPageInfo.getPageSize(), (int)eoPageInfo.getSize());
    }
}

