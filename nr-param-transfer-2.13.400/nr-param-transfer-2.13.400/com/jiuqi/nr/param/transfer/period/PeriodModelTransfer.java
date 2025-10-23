/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.model.MetaExportModel
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.period.cache.ClearCache
 *  com.jiuqi.nr.period.common.rest.PeriodY13Obj
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl
 *  com.jiuqi.nr.period.service.PeriodDataService
 *  com.jiuqi.nr.period.service.PeriodService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.param.transfer.period;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.model.MetaExportModel;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.param.transfer.period.PeriodEntityDTO;
import com.jiuqi.nr.param.transfer.period.PeriodRowDTO;
import com.jiuqi.nr.param.transfer.period.PeriodTransferDTO;
import com.jiuqi.nr.period.cache.ClearCache;
import com.jiuqi.nr.period.common.rest.PeriodY13Obj;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl;
import com.jiuqi.nr.period.service.PeriodDataService;
import com.jiuqi.nr.period.service.PeriodService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PeriodModelTransfer
implements IModelTransfer {
    private static final Logger logger = LoggerFactory.getLogger(PeriodModelTransfer.class);
    @Autowired
    private PeriodService periodService;
    @Autowired
    private PeriodDataService periodDataService;
    @Autowired
    private ClearCache clearCache;
    public static final ObjectMapper MAPPER = new ObjectMapper();

    private void importPeriod13(PeriodTransferDTO periodTransferDTO) throws Exception {
        PeriodEntityDTO periodEntityDTO = periodTransferDTO.getPeriodEntity();
        if (periodEntityDTO.getPeriodType() == PeriodType.MONTH && PeriodUtils.isPeriod13((String)periodEntityDTO.getCode(), (PeriodType)periodEntityDTO.getPeriodType())) {
            String key = periodEntityDTO.getKey();
            IPeriodEntity period = this.periodService.queryPeriodByKey(key);
            List<PeriodRowDTO> periodRows = periodTransferDTO.getPeriodRows();
            int yearStartDate = periodRows.get(0).getYear();
            int yearEndDate = periodRows.get(0).getYear();
            boolean period0 = false;
            boolean period13 = false;
            int periodNum = 12;
            for (PeriodRowDTO periodRow : periodRows) {
                yearStartDate = periodRow.getYear() < yearStartDate ? periodRow.getYear() : yearStartDate;
                yearEndDate = periodRow.getYear() > yearEndDate ? periodRow.getYear() : yearEndDate;
                String substring = periodRow.getCode().substring(periodRow.getCode().length() - 4);
                int i = Integer.parseInt(substring);
                period0 = !period0 && i == 0 ? true : period0;
                period13 = !period13 && i >= 13 ? true : period13;
                periodNum = i > periodNum ? i : periodNum;
            }
            PeriodY13Obj periodY13Obj = this.getPeriodY13Obj(periodEntityDTO.getCode(), periodEntityDTO.getTitle(), yearStartDate + "", yearEndDate + "", period0, period13, periodNum);
            List periodDataDefines = new ArrayList();
            if (period == null) {
                periodDataDefines = this.periodService.extendPeriodY13Data((IPeriodEntity)periodEntityDTO, periodY13Obj, true);
                this.periodService.insertPeriodY13((IPeriodEntity)periodEntityDTO, NpContextHolder.getContext().getLocale().getLanguage());
            } else {
                periodDataDefines = this.periodService.extendPeriodY13Data(period, periodY13Obj, false);
                this.periodService.updatePeriodY13((IPeriodEntity)periodEntityDTO, NpContextHolder.getContext().getLocale().getLanguage());
            }
            ArrayList<PeriodRowDTO> insertPeriodRowDTO = new ArrayList<PeriodRowDTO>();
            ArrayList<PeriodRowDTO> updatePeriodRowDTO = new ArrayList<PeriodRowDTO>();
            List existPeriodRows = this.periodDataService.queryPeriodDataByPeriodCode(key);
            Map<Object, Object> existPeriodRowCodes = new HashMap();
            if (existPeriodRows.size() > 0) {
                existPeriodRowCodes = existPeriodRows.stream().collect(Collectors.toMap(IPeriodRow::getCode, e -> e));
            }
            Set insertCodes = periodDataDefines.stream().map(PeriodDataDefineImpl::getCode).collect(Collectors.toSet());
            for (PeriodRowDTO periodRow : periodRows) {
                if (insertCodes.contains(periodRow.getCode()) && null == existPeriodRowCodes.get(periodRow.getCode())) {
                    insertPeriodRowDTO.add(periodRow);
                    continue;
                }
                if (null == existPeriodRowCodes.get(periodRow.getCode())) continue;
                periodRow.setKey(((IPeriodRow)existPeriodRowCodes.get(periodRow.getCode())).getKey());
                updatePeriodRowDTO.add(periodRow);
            }
            for (PeriodRowDTO periodRowDTO : updatePeriodRowDTO) {
                this.periodDataService.updatePeriod13DataLanguage((IPeriodRow)periodRowDTO, key, NpContextHolder.getContext().getLocale().getLanguage());
            }
            for (PeriodRowDTO periodRowDTO : insertPeriodRowDTO) {
                this.periodDataService.insertCustomPeriod((IPeriodRow)periodRowDTO, key);
            }
            this.periodService.updateEntityInfo(key);
            this.clearCache.clearCache();
        }
    }

    private PeriodY13Obj getPeriodY13Obj(String code, String title, String yearStart, String yearEnd, boolean period0, boolean period13, int periodNum) {
        PeriodY13Obj periodY13Obj = new PeriodY13Obj();
        periodY13Obj.setCode(code);
        periodY13Obj.setTitle(title);
        periodY13Obj.setYearStart(yearStart);
        periodY13Obj.setYearEnd(yearEnd);
        periodY13Obj.setPeriod0(period0);
        periodY13Obj.setPeriod13(period13);
        periodY13Obj.setPeriodNum(periodNum);
        return periodY13Obj;
    }

    @Transactional(rollbackFor={Exception.class})
    public void importModel(IImportContext iImportContext, byte[] bytes) throws TransferException {
        if (bytes == null) {
            return;
        }
        try {
            PeriodTransferDTO periodTransferDTO = (PeriodTransferDTO)MAPPER.readValue(bytes, PeriodTransferDTO.class);
            PeriodEntityDTO periodEntityDTO = periodTransferDTO.getPeriodEntity();
            if (periodEntityDTO == null) {
                return;
            }
            if (periodEntityDTO.getPeriodType() == PeriodType.MONTH && PeriodUtils.isPeriod13((String)periodEntityDTO.getCode(), (PeriodType)periodEntityDTO.getPeriodType())) {
                this.importPeriod13(periodTransferDTO);
                return;
            }
            String key = periodEntityDTO.getKey();
            IPeriodEntity period = this.periodService.queryPeriodByKey(key);
            this.oldResourceExistMessage(period != null, "\u65f6\u671f", period != null ? period.getTitle() : "");
            if (period == null) {
                this.periodService.insertCustomPeriod((IPeriodEntity)periodEntityDTO);
            } else {
                this.periodService.updateCustomPeriod((IPeriodEntity)periodEntityDTO);
            }
            List<PeriodRowDTO> periodRows = periodTransferDTO.getPeriodRows();
            List oldPeriodRows = this.periodDataService.queryPeriodDataByPeriodCode(key);
            this.importLinkMessage("\u6587\u4ef6\u91cc\u65f6\u671f\u4e3b\u4f53key\uff1a" + key + " \u7684\u5df2\u6709\u65f6\u671f\u6570\u636e", oldPeriodRows.stream().map(IPeriodRow::getKey).collect(Collectors.toList()));
            Map<String, IPeriodRow> oldMap = oldPeriodRows.stream().collect(Collectors.toMap(IPeriodRow::getKey, r -> r));
            for (PeriodRowDTO periodRow : periodRows) {
                if (oldMap.containsKey(periodRow.getKey())) {
                    this.periodDataService.updateCustomPeriod((IPeriodRow)periodRow, key);
                } else {
                    this.periodDataService.insertCustomPeriod((IPeriodRow)periodRow, key);
                }
                oldMap.remove(periodRow.getKey());
            }
            ArrayList<String> deletes = new ArrayList<String>(oldMap.keySet());
            this.importLinkMessage(key + " \u8981\u5220\u9664\u7684\u65f6\u671f\u6570\u636e", deletes);
            if (!deletes.isEmpty()) {
                this.periodDataService.deleteCustomPeriodDatas(deletes, key);
            }
            this.periodService.updateEntityInfo(key);
            this.clearCache.clearCache();
        }
        catch (IOException e) {
            throw new TransferException("\u62a5\u8868\u81ea\u5b9a\u4e49\u65f6\u671f\u6570\u636e\u89e3\u6790\u5931\u8d25", (Throwable)e);
        }
        catch (Exception e) {
            throw new TransferException("\u62a5\u8868\u81ea\u5b9a\u4e49\u65f6\u671f\u6570\u636e\u5bfc\u5165\u5931\u8d25", (Throwable)e);
        }
    }

    public MetaExportModel exportModel(IExportContext iExportContext, String id) throws TransferException {
        List periodRows;
        IPeriodEntity period;
        try {
            period = this.periodService.queryPeriodByKey(id);
        }
        catch (Exception e) {
            throw new TransferException("\u672a\u627e\u5230\u62a5\u8868\u81ea\u5b9a\u4e49\u65f6\u671f", (Throwable)e);
        }
        if (period == null) {
            throw new TransferException("\u672a\u627e\u5230\u62a5\u8868\u81ea\u5b9a\u4e49\u65f6\u671f");
        }
        PeriodEntityDTO periodEntityDTO = new PeriodEntityDTO();
        BeanUtils.copyProperties(period, periodEntityDTO);
        try {
            periodRows = this.periodDataService.queryPeriodDataByPeriodCode(id);
        }
        catch (Exception e) {
            throw new TransferException("\u672a\u627e\u5230\u62a5\u8868\u81ea\u5b9a\u4e49\u65f6\u671f\u7684\u6570\u636e", (Throwable)e);
        }
        ArrayList<PeriodRowDTO> rows = new ArrayList<PeriodRowDTO>();
        for (IPeriodRow periodRow : periodRows) {
            PeriodRowDTO periodRowDTO = new PeriodRowDTO();
            BeanUtils.copyProperties(periodRow, periodRowDTO);
            rows.add(periodRowDTO);
        }
        PeriodTransferDTO periodTransferDTO = new PeriodTransferDTO();
        periodTransferDTO.setPeriodEntity(periodEntityDTO);
        periodTransferDTO.setPeriodRows(rows);
        try {
            MetaExportModel meta = new MetaExportModel();
            meta.setData(MAPPER.writeValueAsBytes((Object)periodTransferDTO));
            return meta;
        }
        catch (IOException e) {
            throw new TransferException("\u62a5\u8868\u81ea\u5b9a\u4e49\u65f6\u671f\u6570\u636e\u6253\u5305\u5931\u8d25", (Throwable)e);
        }
    }

    private void importLinkMessage(String resourceTypeName, Collection resource) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("%s\u6709\uff1a", resourceTypeName) + resource);
        }
    }

    private void oldResourceExistMessage(boolean oldResourceNotEmpty, String resourceTypeName, String messageTitle) {
        if (logger.isDebugEnabled()) {
            if (oldResourceNotEmpty) {
                logger.debug(String.format("\u6839\u636e\u5165\u53c2\u7684key\u67e5\u8be2%s\u5b58\u5728\uff0c\u5bfc\u5165\u8d70\u66f4\u65b0\uff0c\u5176title\u662f\uff1a %s", resourceTypeName, messageTitle));
            } else {
                logger.debug(String.format("\u6839\u636e\u5165\u53c2\u7684key\u67e5\u8be2%s\u4e0d\u5b58\u5728\uff0c\u5bfc\u5165\u8d70\u65b0\u589e\uff01", resourceTypeName));
            }
        }
    }
}

