/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.application.NpApplication
 */
package com.jiuqi.np.definition.internal.update;

import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.update.UpdateDesignFd;
import com.jiuqi.np.definition.internal.update.UpdateFd;
import com.jiuqi.np.definition.internal.update.dao.UpdateDesignFdDao;
import com.jiuqi.np.definition.internal.update.dao.UpdateFdDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldUpdateProcessor {
    private static final Logger logger = LoggerFactory.getLogger(FieldUpdateProcessor.class);
    @Autowired
    private UpdateDesignFdDao updateDesignFdDao;
    @Autowired
    private UpdateFdDao updateFdDao;
    @Autowired
    private NpApplication npApplication;

    public void updateField() {
        this.npApplication.asyncRun(() -> {
            Logger logger = LoggerFactory.getLogger(FieldUpdateProcessor.class);
            try {
                long start = System.currentTimeMillis();
                HashMap<String, Object> map = new HashMap<String, Object>(12);
                List<UpdateDesignFd> list = this.updateDesignFdDao.list();
                long build = System.currentTimeMillis();
                logger.info("DES_FILED: \u6307\u6807\u663e\u793a\u683c\u5f0f\uff1a\u67e5\u5230" + list.size() + "\u6761\u6570\u636e\u8017\u65f6: " + (build - start) + " milliseconds");
                ArrayList<UpdateDesignFd> update = new ArrayList<UpdateDesignFd>();
                for (UpdateDesignFd fieldDefine : list) {
                    String showFormat;
                    if (FieldType.FIELD_TYPE_DECIMAL != fieldDefine.getType() && FieldType.FIELD_TYPE_FLOAT != fieldDefine.getType() || StringUtils.isEmpty(showFormat = fieldDefine.getShowFormat())) continue;
                    Object formatProperties = (FormatProperties)map.get(showFormat + fieldDefine.getFractionDigits());
                    if (formatProperties == null) {
                        formatProperties = this.getFormatProperties(showFormat, fieldDefine.getFractionDigits());
                        map.put(showFormat + fieldDefine.getFractionDigits(), formatProperties);
                    }
                    fieldDefine.setFormatProperties((FormatProperties)formatProperties);
                    update.add(fieldDefine);
                }
                list = null;
                long up = System.currentTimeMillis();
                List lists = FieldUpdateProcessor.splitList(update, 1000);
                for (List updateDesignFds : lists) {
                    this.updateDesignFdDao.update(updateDesignFds.toArray(new UpdateDesignFd[0]));
                    logger.info("DES_FILED: \u6b63\u5728\u5206\u6279\u5347\u7ea7" + updateDesignFds.size() + "\u6761\u6570\u636e\uff0c\u8bf7\u52ff\u5173\u95ed\u670d\u52a1");
                }
                long end = System.currentTimeMillis();
                logger.info("DES_FILED: \u8bbe\u8ba1\u671f\u6307\u6807\u663e\u793a\u683c\u5f0f\uff1a\u5165\u5e93\u8017\u65f6\uff1a" + (end - up) + " milliseconds");
                logger.info("DES_FILED: \u8bbe\u8ba1\u671f\u6307\u6807\u663e\u793a\u683c\u5f0f\uff1a\u5347\u7ea7\u7ed3\u675f,\u603b\u8017\u65f6\uff1a" + (end - start) + " milliseconds");
            }
            catch (Exception e) {
                logger.info("\u8bbe\u8ba1\u671f\u6307\u6807\u663e\u793a\u683c\u5f0f\u5347\u7ea7\u5931\u8d25");
                logger.error(e.getMessage(), e);
            }
        });
    }

    public void updateRunField() {
        this.npApplication.asyncRun(() -> {
            Logger logger = LoggerFactory.getLogger(FieldUpdateProcessor.class);
            try {
                long start = System.currentTimeMillis();
                HashMap<String, Object> map = new HashMap<String, Object>(12);
                List<UpdateFd> list = this.updateFdDao.list();
                long build = System.currentTimeMillis();
                logger.info("SYS_FILED: \u6307\u6807\u663e\u793a\u683c\u5f0f\uff1a\u67e5\u5230" + list.size() + "\u6761\u6570\u636e\u8017\u65f6: " + (build - start) + " milliseconds");
                ArrayList<UpdateFd> update = new ArrayList<UpdateFd>();
                for (UpdateFd fieldDefine : list) {
                    String showFormat;
                    if (FieldType.FIELD_TYPE_DECIMAL != fieldDefine.getType() && FieldType.FIELD_TYPE_FLOAT != fieldDefine.getType() || StringUtils.isEmpty(showFormat = fieldDefine.getShowFormat())) continue;
                    Object formatProperties = (FormatProperties)map.get(showFormat + fieldDefine.getFractionDigits());
                    if (formatProperties == null) {
                        formatProperties = this.getFormatProperties(showFormat, fieldDefine.getFractionDigits());
                        map.put(showFormat + fieldDefine.getFractionDigits(), formatProperties);
                    }
                    fieldDefine.setFormatProperties((FormatProperties)formatProperties);
                    update.add(fieldDefine);
                }
                list = null;
                List lists = FieldUpdateProcessor.splitList(update, 1000);
                long up = System.currentTimeMillis();
                for (List updateFds : lists) {
                    this.updateFdDao.update(updateFds.toArray(new Object[0]));
                    logger.info("SYS_FILED: \u6b63\u5728\u5206\u6279\u5347\u7ea7" + updateFds.size() + "\u6761\u6570\u636e\uff0c\u8bf7\u52ff\u5173\u95ed\u670d\u52a1");
                }
                long end = System.currentTimeMillis();
                logger.info("SYS_FILED: \u8bbe\u8ba1\u671f\u6307\u6807\u663e\u793a\u683c\u5f0f\uff1a\u5165\u5e93\u8017\u65f6\uff1a" + (end - up) + " milliseconds");
                logger.info("SYS_FILED: \u8bbe\u8ba1\u671f\u6307\u6807\u663e\u793a\u683c\u5f0f\uff1a\u5347\u7ea7\u7ed3\u675f,\u603b\u8017\u65f6\uff1a" + (end - start) + " milliseconds");
            }
            catch (Exception e) {
                logger.info("\u8fd0\u884c\u671f\u6307\u6807\u663e\u793a\u683c\u5f0f\u5347\u7ea7\u5931\u8d25");
                logger.error(e.getMessage(), e);
            }
        });
    }

    private FormatProperties getFormatProperties(String showFormat, int fractionDigits) {
        StringBuilder pattern;
        FormatProperties formatProperties = new FormatProperties();
        HashMap<String, String> properties = new HashMap<String, String>(2);
        properties.put("negativeStyle", "0");
        properties.put("fixMode", "0");
        formatProperties.setProperties(properties);
        StringBuilder digits = new StringBuilder(".");
        for (int i = 0; i < fractionDigits; ++i) {
            digits.append("0");
        }
        switch (showFormat) {
            case "@100": {
                pattern = new StringBuilder("#,##0");
                formatProperties.setFormatType(1);
                break;
            }
            case "@101": {
                pattern = new StringBuilder("0");
                formatProperties.setFormatType(1);
                break;
            }
            case "@102": {
                pattern = new StringBuilder("\u00a50");
                formatProperties.setFormatType(2);
                break;
            }
            case "@103": {
                pattern = new StringBuilder("\u00a5#,##0");
                formatProperties.setFormatType(2);
                break;
            }
            case "@104": {
                pattern = new StringBuilder("$0");
                formatProperties.setFormatType(2);
                break;
            }
            case "@105": {
                pattern = new StringBuilder("$#,##0");
                formatProperties.setFormatType(2);
                break;
            }
            case "@199": {
                pattern = new StringBuilder("0");
                formatProperties.setFormatType(4);
                properties.remove("negativeStyle");
                break;
            }
            default: {
                return null;
            }
        }
        if (digits.length() > 1) {
            pattern.append((CharSequence)digits);
        }
        if ("@199".equals(showFormat)) {
            pattern.append("%");
        }
        formatProperties.setPattern(pattern.toString());
        return formatProperties;
    }

    public static <E> List<List<E>> splitList(List<E> sourceList, int groupSize) {
        int length = sourceList.size();
        int num = (length + groupSize - 1) / groupSize;
        ArrayList<List<List<E>>> newList = new ArrayList<List<List<E>>>(num);
        for (int i = 0; i < num; ++i) {
            int fromIndex = i * groupSize;
            int toIndex = Math.min((i + 1) * groupSize, length);
            newList.add(sourceList.subList(fromIndex, toIndex));
        }
        return newList;
    }
}

