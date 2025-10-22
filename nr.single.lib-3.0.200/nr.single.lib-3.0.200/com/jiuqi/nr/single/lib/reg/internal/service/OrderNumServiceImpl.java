/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.lib.reg.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.lib.reg.service.OrderNumService;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderNumServiceImpl
implements OrderNumService {
    private static final String JQGG_NBDY = "JQ-NBDY";
    private static final String JQGG_NB = "JQ-NB";
    private static final String JQGG_JXPJ = "JQ-JXPJ-";
    private static final String JQGG_JXPJ_UPDATE = "JQ-JXPJ-UPDATE";
    private static final String ORDERCHARS = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final String NEWORDERCHARS = "3456789ABCDEFGHJKLMNPQRSTUVWXY";
    private static final SecureRandom SEEDS = new SecureRandom();

    @Override
    public String getNewAuthorizationCode(String productCode) {
        return this.getNewAuthorizationCode(productCode, System.currentTimeMillis());
    }

    @Override
    public String getNewAuthorizationCode(String productCode, long maxSeq) {
        if (StringUtils.isEmpty((String)productCode)) {
            return null;
        }
        if (productCode.startsWith(JQGG_NB) || productCode.startsWith(JQGG_JXPJ) && !productCode.startsWith(JQGG_JXPJ_UPDATE)) {
            return this.getNewPlatformAuthorizationCode(maxSeq, productCode);
        }
        long seq = maxSeq;
        String result = this.getNewIdentity(seq, 8, 5, ORDERCHARS);
        result = result.substring(0, 2) + "-" + result.substring(2, result.length());
        return result;
    }

    @Override
    public List<String> getNewAuthorizationCodes(String productCode, long maxSeq, int count) {
        if (StringUtils.isEmpty((String)productCode)) {
            return null;
        }
        if (productCode.startsWith(JQGG_NB) || productCode.startsWith(JQGG_JXPJ) && !productCode.startsWith(JQGG_JXPJ_UPDATE)) {
            return this.getNewPlatformAuthorizationCodes(productCode, maxSeq, count);
        }
        ArrayList<String> list = new ArrayList<String>();
        for (long seq = maxSeq; seq < maxSeq + (long)count; ++seq) {
            String result = this.getNewIdentity(seq, 8, 5, ORDERCHARS);
            result = result.substring(0, 2) + "-" + result.substring(2, result.length());
            list.add(result);
        }
        return list;
    }

    private List<String> getNewPlatformAuthorizationCodes(String productionCode, long maxSeq, int count) {
        ArrayList<String> list = new ArrayList<String>();
        for (long curSeq = maxSeq; curSeq < maxSeq + (long)count; ++curSeq) {
            list.add(this.getNewPlatformAuthorizationCode(curSeq, productionCode));
        }
        return list;
    }

    private String getNewPlatformAuthorizationCode(long seq, String productionCode) {
        StringBuilder code = new StringBuilder();
        if (productionCode.startsWith(JQGG_JXPJ)) {
            String helf = this.getNewIdentity(seq, 5, 5, NEWORDERCHARS);
            while ("J".equalsIgnoreCase(helf.substring(1, 1))) {
                helf = helf.substring(0, 1) + NEWORDERCHARS.charAt(this.getRandom().nextInt(NEWORDERCHARS.length())) + helf.substring(2);
            }
            code.append(helf + "-" + this.getNewIdentity(seq, 8, 5, NEWORDERCHARS));
        } else {
            code.append(NEWORDERCHARS.charAt(this.getRandom().nextInt(NEWORDERCHARS.length())));
            if (productionCode.startsWith(JQGG_NBDY)) {
                int index = this.getRandom().nextInt(NEWORDERCHARS.length());
                char second = NEWORDERCHARS.charAt(index) == 'J' ? NEWORDERCHARS.charAt(++index) : NEWORDERCHARS.charAt(index);
                code.append(second);
            } else {
                code.append("J");
            }
            code.append(this.getNewIdentity(seq, 12, 9, NEWORDERCHARS));
        }
        return code.toString();
    }

    private String getNewIdentity(long seq, int length, int randomCount, String orderChars) {
        long serial = this.getRandom().nextInt((int)Math.pow(10.0, randomCount));
        String numCode = String.format("%d%d", seq, serial);
        if (numCode.length() > 18) {
            numCode = numCode.substring(0, 18);
        }
        char[] buffer = new char[length];
        int index = length - 1;
        for (long orderSerial = Long.parseLong(numCode); orderSerial > 0L && index >= 0; orderSerial /= (long)orderChars.length()) {
            buffer[index--] = ORDERCHARS.charAt((int)(orderSerial % (long)ORDERCHARS.length()));
        }
        while (index >= 0) {
            buffer[index--] = 48;
        }
        return new String(buffer);
    }

    private SecureRandom getRandom() {
        SEEDS.setSeed((int)new Date().getTime());
        long seed = (int)new Date().getTime();
        seed ^= Thread.currentThread().getId();
        SecureRandom random = new SecureRandom();
        random.setSeed(seed ^= SEEDS.nextLong());
        return random;
    }

    public static void main(Object[] args) {
        OrderNumServiceImpl test = new OrderNumServiceImpl();
        test.getNewAuthorizationCode("JQ-NB-001");
    }
}

