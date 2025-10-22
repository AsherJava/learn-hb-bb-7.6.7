/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  io.swagger.annotations.Api
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.portal.news2.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.portal.news2.impl.NewsAbstractInfo;
import com.jiuqi.nr.portal.news2.impl.NewsImpl;
import com.jiuqi.nr.portal.news2.service.INews2Service;
import com.jiuqi.nr.portal.news2.service.IQueryReadDao;
import com.jiuqi.nr.portal.news2.service.IQueryUnRead;
import com.jiuqi.nr.portal.news2.vo.NewsReturn;
import com.jiuqi.nr.portal.news2.vo.ResultObject;
import com.jiuqi.nr.portal.news2.vo.SaveReturn;
import io.swagger.annotations.Api;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags={"\u9996\u9875\u65b0\u95fb"})
@RestController
@RequestMapping(value={"/api/portal/news2"})
public class PortalNews2Controller {
    private static final Logger logger = LoggerFactory.getLogger(PortalNews2Controller.class);
    @Autowired
    private INews2Service news2Service;
    @Autowired
    private IQueryUnRead queryUnRead;
    @Autowired
    public IQueryReadDao queryReadDao;

    @PostMapping(value={"save"})
    public ResultObject saveNews(@RequestBody NewsImpl news) {
        ResultObject result = new ResultObject();
        try {
            String id = this.news2Service.addNews(news, true);
            SaveReturn info = new SaveReturn(id);
            result.setState(true);
            result.setData(info);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setMessage("\u4fdd\u5b58\u5931\u8d25");
            result.setState(false);
        }
        return result;
    }

    @PostMapping(value={"update-news"})
    public ResultObject updateNews(@RequestBody NewsImpl news) {
        ResultObject result = new ResultObject();
        try {
            Boolean modifyNews = this.news2Service.modifyNews(news);
            result.setState(modifyNews);
            if (!modifyNews.booleanValue()) {
                result.setMessage("\u64cd\u4f5c\u5931\u8d25\uff01");
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setMessage(e.getMessage());
            result.setState(false);
        }
        return result;
    }

    @PostMapping(value={"delete-news"})
    public ResultObject deleteNews(String id) {
        ResultObject result = new ResultObject();
        try {
            Boolean deleteNews = this.news2Service.deleteNews(id);
            result.setState(deleteNews);
            if (!deleteNews.booleanValue()) {
                result.setMessage("\u64cd\u4f5c\u5931\u8d25\uff01");
            }
        }
        catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setState(false);
        }
        return result;
    }

    @PostMapping(value={"delete-groupnews"})
    public ResultObject deleteNewsByMid(String mid, String portalId) {
        ResultObject result = new ResultObject();
        try {
            Boolean deleteNewsByMid = this.news2Service.deleteNewsByMid(mid, portalId);
            result.setState(deleteNewsByMid);
            if (!deleteNewsByMid.booleanValue()) {
                result.setMessage("\u64cd\u4f5c\u5931\u8d25\uff01");
            }
        }
        catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setState(false);
        }
        return result;
    }

    @PostMapping(value={"publish-news"})
    public ResultObject publishNews(String mid, String portalId) {
        ResultObject result = new ResultObject();
        try {
            Boolean publishNews = this.news2Service.publishNews(mid, portalId);
            result.setState(publishNews);
            if (!publishNews.booleanValue()) {
                result.setMessage("\u64cd\u4f5c\u5931\u8d25\uff01");
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setMessage(e.getMessage());
            result.setState(false);
        }
        return result;
    }

    @GetMapping(value={"find-news"})
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public NewsReturn findNewsById(String id, String type) {
        NewsReturn result = new NewsReturn();
        try {
            NewsImpl news = this.news2Service.queryNewsByIdForWeb(id, type);
            result.setNewsImpl(news);
            result.setState(true);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setMessage(e.getMessage());
            result.setState(false);
        }
        return result;
    }

    @GetMapping(value={"group-news"})
    public NewsAbstractInfo[] findNewsByMid(String mid, String portalId, String type) {
        List<NewsAbstractInfo> impls = this.news2Service.queryNewsByMidAndPortalId(mid, portalId, type);
        return impls.toArray(new NewsAbstractInfo[impls.size()]);
    }

    @PostMapping(value={"modify-order"})
    public ResultObject modifyFileOrder(String id, Integer newOrder) {
        ResultObject result = new ResultObject();
        try {
            Boolean saveFile = this.news2Service.modifyFileOrder(id, newOrder);
            result.setState(saveFile);
            if (!saveFile.booleanValue()) {
                result.setMessage("\u8bf7\u68c0\u67e5id\u662f\u5426\u5b58\u5728\uff01");
            }
        }
        catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setState(false);
        }
        return result;
    }

    @PostMapping(value={"change-order"})
    public ResultObject changeFileOrder(String id1, String id2) {
        ResultObject result = new ResultObject();
        try {
            Boolean saveFile = false;
            NewsImpl news1 = this.news2Service.queryNewsById(id1);
            NewsImpl news2 = this.news2Service.queryNewsById(id2);
            if (news1 == null || news2 == null) {
                result.setMessage("\u8bf7\u68c0\u67e5id\u662f\u5426\u5b58\u5728\uff01");
                result.setState(saveFile);
                return result;
            }
            Integer order1 = news1.getOrder();
            Integer order2 = news2.getOrder();
            Boolean modifyFileOrder = this.news2Service.modifyFileOrder(id1, order2);
            Boolean modifyFileOrder2 = this.news2Service.modifyFileOrder(id2, order1);
            saveFile = modifyFileOrder != false && modifyFileOrder2 != false;
            result.setState(saveFile);
            if (!saveFile.booleanValue()) {
                result.setMessage("\u8bf7\u68c0\u67e5id\u662f\u5426\u5b58\u5728\uff01");
            }
        }
        catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setState(false);
        }
        return result;
    }

    @GetMapping(value={"isExist"})
    public ResultObject isExistNews(String id, String type) {
        ResultObject result = new ResultObject();
        try {
            Boolean isExist = this.news2Service.queryNewsById(id, type) != null;
            result.setState(isExist);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setMessage(e.getMessage());
            result.setState(false);
        }
        return result;
    }

    @GetMapping(value={"getUnRead"})
    public List<String> getUnRead(String mid, String portalId, String type) {
        return this.queryUnRead.queryUnReadByMidAndPortalId(mid, portalId, type);
    }

    @PostMapping(value={"quickRead"})
    public Boolean quickRead(String mid, String portalId, String type) {
        return this.queryUnRead.quickRead(mid, portalId, type);
    }
}

