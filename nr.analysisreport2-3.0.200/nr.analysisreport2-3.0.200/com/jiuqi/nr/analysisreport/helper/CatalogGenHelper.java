/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.util.Base64
 *  org.jsoup.Jsoup
 *  org.jsoup.nodes.Document
 *  org.jsoup.nodes.Element
 *  org.jsoup.select.Elements
 */
package com.jiuqi.nr.analysisreport.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportCatalogItem;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportChapterDefine;
import com.jiuqi.nr.analysisreport.chapter.common.CatalogLevel;
import com.jiuqi.nr.analysisreport.chapter.service.IChapterService;
import com.jiuqi.nr.analysisreport.chapter.util.TransUtil;
import com.jiuqi.nr.analysisreport.facade.AnalyBigdataDefine;
import com.jiuqi.nr.analysisreport.internal.service.AnalyBigDataService;
import com.jiuqi.util.Base64;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class CatalogGenHelper {
    public final Logger logger = LoggerFactory.getLogger(CatalogGenHelper.class);
    public static final String CANDIDATE_TAGS = "h1,h2,h3,h4";
    public static final String NEED_APPEND_TAGS = "h2,h3,h4";
    public static final String TAG_NAME = "h";

    public void genChapterCatalog(Object param) {
        if (param instanceof ReportChapterDefine) {
            try {
                this.generateChapterCatalog((ReportChapterDefine)param);
            }
            catch (Exception e) {
                this.logger.error("\u751f\u6210\u7ae0\u8282\u76ee\u5f55\u5931\u8d25", e);
            }
        } else if (param instanceof AnalyBigdataDefine) {
            try {
                this.generateBigDataCatalog((AnalyBigdataDefine)param);
            }
            catch (Exception e) {
                this.logger.error("\u751f\u6210\u7ae0\u8282\u7248\u672c\u6570\u636e\u76ee\u5f55\u5931\u8d25", e);
            }
        }
    }

    public void generateBigDataCatalog(AnalyBigdataDefine bigdataDefine) throws Exception {
        AnalyBigDataService analyBigDataService = (AnalyBigDataService)SpringBeanUtils.getBean(AnalyBigDataService.class);
        Date cataUpdateTime = bigdataDefine.getCatalogUpdatetime();
        this.analyAndMarkCatalog(bigdataDefine);
        bigdataDefine.setCatalogUpdatetime(cataUpdateTime);
        analyBigDataService.updateBigData(bigdataDefine);
    }

    public void generateChapterCatalog(ReportChapterDefine chapterDefine) throws Exception {
        IChapterService chapterService = (IChapterService)BeanUtils.getBean(IChapterService.class);
        Date cataUpdateTime = chapterDefine.getArcUpdatetime();
        this.analyAndMarkCatalog(chapterDefine);
        chapterDefine.setCatalogUpdatetime(cataUpdateTime);
        chapterService.updateChapter(TransUtil.transDtoToVo(chapterDefine));
    }

    public void analyAndMarkCatalog(Object param) throws JsonProcessingException {
        String data = "";
        String chapterId = "";
        if (param instanceof ReportChapterDefine) {
            data = new String(Base64.base64ToByteArray((String)((ReportChapterDefine)param).getArcData()));
            chapterId = ((ReportChapterDefine)param).getArcKey();
        } else if (param instanceof AnalyBigdataDefine) {
            data = ((AnalyBigdataDefine)param).getBigData();
            chapterId = ((AnalyBigdataDefine)param).getArcKey();
        }
        Document doc = Jsoup.parseBodyFragment((String)data);
        Element body = doc.body();
        Elements elements = body.getAllElements();
        ArrayList<ReportCatalogItem> catalogs = new ArrayList<ReportCatalogItem>();
        for (int i = 0; i < elements.size(); ++i) {
            Element element = (Element)elements.get(i);
            if (CANDIDATE_TAGS.indexOf(element.tagName()) < 0 || !StringUtils.hasLength(element.html().trim())) continue;
            element.attr("id", UUID.randomUUID().toString());
            ReportCatalogItem catalogItem = new ReportCatalogItem();
            catalogItem.setTitle(element.text());
            catalogItem.setChapterId(chapterId);
            catalogItem.setTagName(element.tagName());
            catalogItem.setId(element.id());
            catalogs.add(catalogItem);
        }
        ObjectMapper mapper = new ObjectMapper();
        if (param instanceof AnalyBigdataDefine) {
            AnalyBigdataDefine bigdataDefine = (AnalyBigdataDefine)param;
            bigdataDefine.setBigData(body.toString());
            bigdataDefine.setCatalog(mapper.writeValueAsString(catalogs));
        } else {
            ReportChapterDefine chapterDefine = (ReportChapterDefine)param;
            chapterDefine.setArcData(Base64.byteArrayToBase64((byte[])body.children().toString().getBytes(StandardCharsets.UTF_8)));
            chapterDefine.setCatalog(mapper.writeValueAsString(catalogs));
        }
    }

    public void calculateIndentation(ReportCatalogItem catalogItem) {
        if (!NEED_APPEND_TAGS.contains(catalogItem.getTagName())) {
            return;
        }
        int size = catalogItem.getPath().size();
        String tagName = catalogItem.getTagName();
        if (!tagName.contains(size + "")) {
            Integer tagValue = Integer.valueOf(tagName.replace(TAG_NAME, ""));
            catalogItem.setIndentationNum(tagValue - size);
        }
    }

    public void genParentIdCatalogs(List<ReportCatalogItem> cataLogTrees, Map<String, List<ReportCatalogItem>> parentIdCatalogs) {
        if (!CollectionUtils.isEmpty(cataLogTrees)) {
            for (int i = 0; i < cataLogTrees.size(); ++i) {
                ReportCatalogItem cataLog = cataLogTrees.get(i);
                List<ReportCatalogItem> childrens = cataLog.getChildren();
                cataLog.setChildren(null);
                parentIdCatalogs.putIfAbsent(cataLog.getParentId(), new LinkedList());
                parentIdCatalogs.get(cataLog.getParentId()).add(cataLog);
                this.genParentIdCatalogs(childrens, parentIdCatalogs);
            }
        }
    }

    public void append2CatalogTree(ReportCatalogItem cataLog, List<ReportCatalogItem> cataLogTrees, String parentId) {
        if (!CollectionUtils.isEmpty(cataLogTrees)) {
            ReportCatalogItem lastCatalog = cataLogTrees.get(cataLogTrees.size() - 1);
            if (CatalogLevel.valueOf(lastCatalog.getTagName()).getLevel() < CatalogLevel.valueOf(cataLog.getTagName()).getLevel()) {
                cataLog.getPath().add(lastCatalog.getId());
                lastCatalog.setLeaf(false);
                if (CollectionUtils.isEmpty(lastCatalog.getChildren())) {
                    lastCatalog.setChildren(new ArrayList<ReportCatalogItem>());
                    this.appendChild(cataLog, lastCatalog.getId(), lastCatalog.getChildren());
                } else {
                    this.append2CatalogTree(cataLog, lastCatalog.getChildren(), lastCatalog.getId());
                }
            } else {
                this.appendChild(cataLog, parentId, cataLogTrees);
            }
        } else {
            this.appendChild(cataLog, parentId, cataLogTrees);
        }
    }

    public void buildCatalogTree(ReportCatalogItem cataLog, List<ReportCatalogItem> cataLogTrees) {
        this.append2CatalogTree(cataLog, cataLogTrees, "top");
    }

    public void appendChild(ReportCatalogItem cataLog, String parentId, List<ReportCatalogItem> cataLogTrees) {
        cataLog.setParentId(parentId);
        this.calculateIndentation(cataLog);
        cataLogTrees.add(cataLog);
    }

    public synchronized void addMark(Element element) {
        element.attr("id", UUID.randomUUID().toString());
    }

    public List<ReportCatalogItem> genTemplateCatalog(List<List<ReportCatalogItem>> chaptersCatalog) {
        if (CollectionUtils.isEmpty(chaptersCatalog)) {
            return new ArrayList<ReportCatalogItem>();
        }
        if (chaptersCatalog.size() == 1) {
            return chaptersCatalog.get(0);
        }
        List<ReportCatalogItem> rootCatalogs = chaptersCatalog.get(0);
        for (int i = 1; i < chaptersCatalog.size(); ++i) {
            List<ReportCatalogItem> catalogItems = chaptersCatalog.get(i);
            for (int j = 0; j < catalogItems.size(); ++j) {
                this.append2CatalogTree(catalogItems.get(i), rootCatalogs, "top");
            }
        }
        return rootCatalogs;
    }

    public void buildLocateCatalogTree(List<ReportCatalogItem> catalogItems, Set<String> path) {
        if (CollectionUtils.isEmpty(catalogItems)) {
            return;
        }
        for (int i = 0; i < catalogItems.size(); ++i) {
            ReportCatalogItem catalogItem = catalogItems.get(i);
            if (!path.contains(catalogItem.getId())) {
                catalogItem.setChildren(new ArrayList<ReportCatalogItem>());
                continue;
            }
            List<ReportCatalogItem> children = catalogItem.getChildren();
            this.buildLocateCatalogTree(children, path);
        }
    }
}

