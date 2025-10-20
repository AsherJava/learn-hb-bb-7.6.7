/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 */
package com.jiuqi.gcreport.offsetitem.util;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.offsetitem.dto.NumberRange;
import com.jiuqi.gcreport.offsetitem.dto.OffsetVchrCodeDTO;
import com.jiuqi.gcreport.offsetitem.service.OffsetVchrCodeService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class OffsetVchrCodeUtil {
    private static final String PREFIX = "DX";
    private static final int SERIAL_DEFAULT_LENGTH = 6;
    private static final ConcurrentHashMap<String, AtomicInteger> WAITING_THREADS = new ConcurrentHashMap();
    private static final ConcurrentHashMap<String, NumberRange> ACQUIRED_SERIAL_NUMBERS = new ConcurrentHashMap();

    public static final String createVchrCode(OffsetVchrCodeDTO vchrCodeDTO) {
        StringBuffer vchrCode = new StringBuffer(PREFIX);
        vchrCode.append(OffsetVchrCodeUtil.getPeriod(vchrCodeDTO));
        String dimensions = vchrCode.toString();
        String flowNumber = OffsetVchrCodeUtil.fillFlow(OffsetVchrCodeUtil.getSerialNumber(dimensions), 0);
        vchrCode.append(flowNumber);
        return vchrCode.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static long getSerialNumber(String dim) {
        AtomicInteger atomic = WAITING_THREADS.computeIfAbsent(dim, k -> new AtomicInteger(0));
        atomic.incrementAndGet();
        OffsetVchrCodeService vchrCodeService = (OffsetVchrCodeService)SpringContextUtils.getBean(OffsetVchrCodeService.class);
        AtomicInteger atomicInteger = atomic;
        synchronized (atomicInteger) {
            long end;
            NumberRange range = ACQUIRED_SERIAL_NUMBERS.getOrDefault(dim, null);
            if (range != null && range.getCount() > 0) {
                NumberRange decrementRange = range.decrement();
                ACQUIRED_SERIAL_NUMBERS.put(dim, decrementRange);
                return decrementRange.getStart();
            }
            int n = atomic.getAndSet(0);
            try {
                end = vchrCodeService.updateFlowNumberByDimensions(dim, n);
            }
            catch (Exception e) {
                end = vchrCodeService.reUpdateFlowNumberByDimensions(dim, n);
            }
            if (n == 1) {
                return end;
            }
            long start = end - (long)n + 1L;
            NumberRange decrementRange = new NumberRange(start, n - 1);
            ACQUIRED_SERIAL_NUMBERS.put(dim, decrementRange);
            return start;
        }
    }

    private static String fillFlow(Long flowNumber, int flow) {
        if (flow == 0) {
            flow = 6;
        }
        return String.format("%0" + flow + "d", flowNumber);
    }

    private static String getPeriod(OffsetVchrCodeDTO vchrCodeDTO) {
        int periodType = vchrCodeDTO.getPeriodType();
        int acctYear = vchrCodeDTO.getAcctYear() % 100;
        int acctPeriod = vchrCodeDTO.getAcctPeriod();
        switch (periodType) {
            case 4: {
                return String.format("%02d%02d", acctYear, acctPeriod);
            }
            case 3: {
                return String.format("%02dJ%02d", acctYear, acctPeriod);
            }
            case 2: {
                return String.format("%02dH%02d", acctYear, acctPeriod);
            }
            case 1: {
                return String.format("%02dN", acctYear);
            }
            case 0: {
                return String.format("%02d00", acctYear);
            }
        }
        throw new IllegalArgumentException("Unsupported report type.");
    }
}

