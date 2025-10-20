/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 */
package com.jiuqi.nr.definition.internal.update;

import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.definition.internal.update.UpdateDesignDl;
import com.jiuqi.nr.definition.internal.update.UpdateDl;
import com.jiuqi.nr.definition.internal.update.dao.UpdateDesignDlDao;
import com.jiuqi.nr.definition.internal.update.dao.UpdateDlDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LinkUpdateProcessor {
    @Autowired
    private UpdateDesignDlDao dao;
    @Autowired
    private UpdateDlDao updateDlDao;
    @Autowired
    private NpApplication npApplication;

    public void updateLink() {
        this.npApplication.asyncRun(() -> {
            long start = System.currentTimeMillis();
            Logger logger = LoggerFactory.getLogger(LinkUpdateProcessor.class);
            try {
                HashMap<String, FormatProperties> map = new HashMap<String, FormatProperties>(12);
                List<UpdateDesignDl> list = this.dao.list();
                long query = System.currentTimeMillis();
                logger.info("DES_LINK: \u94fe\u63a5\u663e\u793a\u683c\u5f0f\uff1a\u67e5\u5230" + list.size() + "\u6761\u6570\u636e\u8017\u65f6: " + (query - start) + " milliseconds");
                ArrayList<UpdateDesignDl> update = new ArrayList<UpdateDesignDl>();
                for (UpdateDesignDl link : list) {
                    String showFormat = link.getShowFormat();
                    if (StringUtils.isEmpty((String)showFormat)) continue;
                    FormatProperties formatProperties = (FormatProperties)map.get(showFormat + link.getFractionDigits());
                    if (formatProperties == null) {
                        formatProperties = this.getFormatProperties(showFormat, link.getFractionDigits());
                        map.put(showFormat + link.getFractionDigits(), formatProperties);
                    }
                    link.setFormatProperties(formatProperties);
                    update.add(link);
                }
                long build = System.currentTimeMillis();
                list = null;
                List lists = LinkUpdateProcessor.splitList(update, 1000);
                for (List updateDesignDls : lists) {
                    logger.info("DES_LINK: \u6b63\u5728\u5206\u6279\u5347\u7ea7" + updateDesignDls.size() + "\u6761\u6570\u636e\uff0c\u8bf7\u52ff\u5173\u95ed\u670d\u52a1");
                    this.dao.update(updateDesignDls.toArray(new UpdateDesignDl[0]));
                }
                long end = System.currentTimeMillis();
                logger.info("DES_LINK: \u6307\u6807\u663e\u793a\u683c\u5f0f\uff1a\u5165\u5e93\u8017\u65f6\uff1a" + (end - build) + " milliseconds");
                logger.info("DES_LINK: \u8bbe\u8ba1\u671f\u6307\u6807\u663e\u793a\u683c\u5f0f\uff1a\u5347\u7ea7\u7ed3\u675f,\u603b\u8017\u65f6\uff1a" + (end - start) + " milliseconds");
            }
            catch (Exception e) {
                logger.error("DES_LINK: \u5347\u7ea7\u5931\u8d25", e);
            }
        });
    }

    public void updateRunLink() {
        this.npApplication.asyncRun(() -> {
            long start = System.currentTimeMillis();
            HashMap<String, FormatProperties> map = new HashMap<String, FormatProperties>(12);
            Logger logger = LoggerFactory.getLogger(LinkUpdateProcessor.class);
            try {
                ArrayList<UpdateDl> update = new ArrayList<UpdateDl>();
                List<UpdateDl> list = this.updateDlDao.list();
                long query = System.currentTimeMillis();
                logger.info("SYS_LINK: \u94fe\u63a5\u663e\u793a\u683c\u5f0f\uff1a\u67e5\u5230" + list.size() + "\u6761\u6570\u636e\u8017\u65f6: " + (query - start) + " milliseconds");
                for (UpdateDl link : list) {
                    String showFormat = link.getShowFormat();
                    if (StringUtils.isEmpty((String)showFormat)) continue;
                    FormatProperties formatProperties = (FormatProperties)map.get(showFormat + link.getFractionDigits());
                    if (formatProperties == null) {
                        formatProperties = this.getFormatProperties(showFormat, link.getFractionDigits());
                        map.put(showFormat + link.getFractionDigits(), formatProperties);
                    }
                    link.setFormatProperties(formatProperties);
                    update.add(link);
                }
                list = null;
                long build = System.currentTimeMillis();
                List lists = LinkUpdateProcessor.splitList(update, 1000);
                for (List updateDls : lists) {
                    logger.info("DES_LINK: \u6b63\u5728\u5206\u6279\u5347\u7ea7" + updateDls.size() + "\u6761\u6570\u636e\uff0c\u8bf7\u52ff\u5173\u95ed\u670d\u52a1");
                    this.updateDlDao.update(updateDls.toArray(new Object[0]));
                }
                long end = System.currentTimeMillis();
                logger.info("SYS_LINK: \u6307\u6807\u663e\u793a\u683c\u5f0f\uff1a\u5165\u5e93\u8017\u65f6\uff1a" + (end - build) + " milliseconds");
                logger.info("SYS_LINK: \u8bbe\u8ba1\u671f\u6307\u6807\u663e\u793a\u683c\u5f0f\uff1a\u5347\u7ea7\u7ed3\u675f,\u603b\u8017\u65f6\uff1a" + (end - start) + " milliseconds");
            }
            catch (Exception e) {
                logger.error("SYS_LINK: \u5347\u7ea7\u5931\u8d25", e);
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
                formatProperties.setFormatType(Integer.valueOf(1));
                break;
            }
            case "@101": {
                pattern = new StringBuilder("0");
                formatProperties.setFormatType(Integer.valueOf(1));
                break;
            }
            case "@102": {
                pattern = new StringBuilder("\u00a50");
                formatProperties.setFormatType(Integer.valueOf(2));
                break;
            }
            case "@103": {
                pattern = new StringBuilder("\u00a5#,##0");
                formatProperties.setFormatType(Integer.valueOf(2));
                break;
            }
            case "@104": {
                pattern = new StringBuilder("$0");
                formatProperties.setFormatType(Integer.valueOf(2));
                break;
            }
            case "@105": {
                pattern = new StringBuilder("$#,##0");
                formatProperties.setFormatType(Integer.valueOf(2));
                break;
            }
            case "@199": {
                pattern = new StringBuilder("0");
                formatProperties.setFormatType(Integer.valueOf(4));
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

