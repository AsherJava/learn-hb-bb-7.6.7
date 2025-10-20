/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.gcreport.clbr.util;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import java.security.SecureRandom;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClbrUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(ClbrUtils.class);
    public static final String TB_MD_REALTION = "MD_RELATION";
    public static final String TB_MD_CLBRTYPE = "MD_CLBRTYPE";
    public static final String PENETRATIONCONTROL = "PENETRATIONCONTROL";
    public static final String TB_MD_CLBRSYSTEM = "MD_CLBRSYSTEM";
    public static final String TB_MD_SYSMAPPING = "MD_SYSMAPPING";
    public static final String SEPARATOR = ",";
    public static final String ROOT_PARENT_ID = UUIDUtils.emptyUUIDStr();
    public static final String ROOT_NAME = "\u6240\u6709\u534f\u540c\u65b9\u6848";
    public static final Integer SELECTED_SCHEME_TREE = 1;
    public static final String SRCIID_SEPARATOR = "&";
    public static final String GCREPORT_CLBR_RIGID = "GCREPORT_CLBR_RIGID";
    public static final String GCREPORT_CLBR = "GCREPORT_CLBR";

    public static String newClbrCode() {
        String yyyyMMddHHmmssSSS = DateUtils.format((Date)new Date(), (String)"yyyyMMddHHmmssSSS");
        SecureRandom secureRandom = new SecureRandom();
        String random = String.format("%02d", secureRandom.nextInt(100));
        return "XT" + yyyyMMddHHmmssSSS + random;
    }
}

