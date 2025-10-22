/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.internal.StringUtil
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.portal.news.dao.impl;

import com.jiuqi.nr.portal.news.INews;
import com.jiuqi.nr.portal.news.bean.NewsItem;
import com.jiuqi.nr.portal.news.dao.INewsDao;
import io.netty.util.internal.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class NewsDaoImpl
implements INewsDao {
    private static final String TABLE_NAME = "sys_portal_news";
    private static final String FILED_NAME_ALL = "news_id,mid,title,content,showLatest,stick,push,hide,newsOrder,createTime,abstract,poster,link_";
    private static final String NEWS_ID = "news_id";
    private static final String MID = "mid";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String SHOWLATEST = "showLatest";
    private static final String STICK = "stick";
    private static final String PUSH = "push";
    private static final String HIDE = "hide";
    private static final String NEWSORDER = "newsOrder";
    private static final String CREATETIME = "createTime";
    private static final String ABSTRACT = "abstract";
    private static final String POSTER = "poster";
    private static final String LING = "link_";
    @Autowired
    private JdbcTemplate template;

    @Override
    public int insert(INews item) {
        int result = 0;
        String sql = String.format("insert into %s (%s) values(?,?,?,?,?,?,?,?,?,?,?,?,?)", TABLE_NAME, FILED_NAME_ALL);
        Object[] args = new Object[]{item.getID(), item.getMId(), item.getTitle() == null ? "" : item.getTitle(), item.getContent() == null ? "" : item.getContent(), item.showLatest(), item.stick(), item.push(), item.hide(), item.getNewsOrder(), new Timestamp(item.getCreateTime().getTime()), item.getAbstact() == null ? "" : item.getAbstact(), item.getPoster() == null ? "" : item.getPoster(), item.getLink() == null ? "" : item.getLink()};
        result = this.template.update(sql.toUpperCase(), args);
        return result;
    }

    @Override
    public int deleteNewsItem(String uuid) {
        int result = 0;
        String sql = String.format("delete from %s where %s=?", TABLE_NAME, NEWS_ID);
        Object[] args = new Object[]{uuid.toString()};
        result = this.template.update(sql.toUpperCase(), args);
        return result;
    }

    @Override
    public INews queryNewsItem(String uuid) {
        String sql = String.format("select * from %s where %s=?", TABLE_NAME, NEWS_ID);
        Object[] args = new Object[]{uuid};
        return (INews)this.template.query(sql.toUpperCase(), args, rs -> {
            if (rs.next()) {
                return this.buildINewsInfo(rs);
            }
            return null;
        });
    }

    INews buildINewsInfo(ResultSet rs) throws SQLException {
        int index = 1;
        NewsItem info = new NewsItem();
        info.setID(rs.getString(index));
        info.setMId(rs.getString(++index));
        info.setTitle(rs.getString(++index));
        info.setContent(rs.getString(++index));
        info.setShowLatest(rs.getBoolean(++index));
        info.setStick(rs.getBoolean(++index));
        info.setPush(rs.getBoolean(++index));
        info.setHide(rs.getBoolean(++index));
        info.setNewsOrder(rs.getInt(++index));
        Timestamp timestamp = rs.getTimestamp(++index);
        info.setCreateTime(timestamp);
        info.setAbstact(rs.getString(++index));
        info.setPoster(rs.getString(++index));
        info.setLink(rs.getString(++index));
        return info;
    }

    @Override
    public int updateItem(NewsItem item) {
        int result = 0;
        String sql = String.format("update %s set %s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=? where %s=?", TABLE_NAME, MID, TITLE, CONTENT, SHOWLATEST, STICK, PUSH, HIDE, NEWSORDER, CREATETIME, ABSTRACT, POSTER, LING, NEWS_ID);
        Object[] args = new Object[]{item.getMId(), item.getTitle(), item.getContent(), item.showLatest(), item.stick(), item.push(), item.hide(), item.getNewsOrder(), item.getCreateTime(), item.getAbstact(), item.getPoster(), item.getLink(), item.getID().toString()};
        result = this.template.update(sql.toUpperCase(), args);
        return result;
    }

    @Override
    public INews[] queryNewsList(String mid) {
        INews[] items = new INews[]{};
        if (StringUtil.isNullOrEmpty((String)mid)) {
            return items;
        }
        String sql = String.format("select %s from %s where %s=?", FILED_NAME_ALL, TABLE_NAME, MID);
        List query = this.template.query(sql.toUpperCase(), new Object[]{mid}, (RowMapper)new NewsRowMapper());
        return query.toArray(items);
    }

    @Override
    public INews[] queryAllNewsList() {
        INews[] items = new INews[]{};
        String sql = String.format("select %s from %s ", FILED_NAME_ALL, TABLE_NAME);
        List query = this.template.query(sql.toUpperCase(), (RowMapper)new NewsRowMapper());
        return query.toArray(items);
    }

    class NewsRowMapper
    implements RowMapper<INews> {
        NewsRowMapper() {
        }

        public INews mapRow(ResultSet rs, int rowNum) throws SQLException {
            INews buildINewsInfo = NewsDaoImpl.this.buildINewsInfo(rs);
            return buildINewsInfo;
        }
    }
}

