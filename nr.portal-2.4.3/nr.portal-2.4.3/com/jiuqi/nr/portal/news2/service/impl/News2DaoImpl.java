/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.portal.news2.service.impl;

import com.jiuqi.nr.portal.news2.impl.NewsAbstractInfo;
import com.jiuqi.nr.portal.news2.impl.NewsImpl;
import com.jiuqi.nr.portal.news2.service.INews2Dao;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class News2DaoImpl
implements INews2Dao {
    private static final String ALL_NEWS_FIELD = "NEWS_ID, NEWS_MID, NEWS_ORDER, NEWS_TITLE, NEWS_ABSTRACT, NEWS_POSTER,NEWS_CONTENT_REAL, NEWS_LINK, NEWS_SHOWLATEST, NEWS_TOP, NEWS_HIDE, NEWS_AUTOPOP,NEWS_EXTENTION, NEWS_CREATETIME, NEWS_VIEWCOUNT, NEWS_UPDATETIME, NEWS_PORTAL_ID, NEWS_STARTAUTH, NEWS_CONTENT";
    private static final String TABLE_NAME = "PORTAL_NEWS_DESIGN";
    private static final String TABLE_NAME_RUNNING = "PORTAL_NEWS_RUNNING";
    private static final String NEWS_ID = "NEWS_ID";
    private static final String NEWS_MID = "NEWS_MID";
    private static final String NEWS_ORDER = "NEWS_ORDER";
    private static final String NEWS_PORTAL_ID = "NEWS_PORTAL_ID";
    @Autowired
    private JdbcTemplate template;

    public static int nextInt(int seed) {
        return new SecureRandomTest().getRandom(seed);
    }

    public static int nextInt() {
        return new SecureRandomTest().getRandom();
    }

    @Override
    public Boolean insertNews(NewsImpl impl) {
        int result = 0;
        String sql = String.format("insert into %s (%s) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", TABLE_NAME, ALL_NEWS_FIELD);
        Object[] args = new Object[19];
        args[0] = impl.getId();
        args[1] = impl.getMid();
        int order = 0;
        if (impl.getOrder() == null) {
            order = News2DaoImpl.nextInt();
        }
        args[2] = impl.getOrder() == null ? order : impl.getOrder();
        args[3] = impl.getTitle() == null ? "\u672a\u547d\u540d" : impl.getTitle();
        args[4] = impl.getAbstractDesc() == null ? "" : impl.getAbstractDesc();
        args[5] = impl.getPoster();
        args[6] = StringUtils.isEmpty(impl.getContent()) ? " " : impl.getContent();
        args[7] = impl.getLink();
        args[8] = impl.getShowLatest();
        args[9] = impl.getTop();
        args[10] = impl.getHide();
        args[11] = impl.getAutoPop();
        args[12] = StringUtils.isEmpty(impl.getExtention()) ? " " : impl.getExtention();
        Date date = new Date();
        args[13] = date;
        args[14] = impl.getViewCount();
        args[15] = date;
        args[16] = impl.getPortalId();
        args[17] = impl.getStartAuth();
        args[18] = " ";
        result = this.template.update(sql.toUpperCase(), args);
        return result > 0;
    }

    @Override
    public Boolean insertNewsRunning(NewsImpl impl) {
        int result = 0;
        String sql = String.format("insert into %s (%s) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", TABLE_NAME_RUNNING, ALL_NEWS_FIELD);
        Object[] args = new Object[19];
        args[0] = impl.getId();
        args[1] = impl.getMid();
        int order = 0;
        if (impl.getOrder() == null) {
            order = News2DaoImpl.nextInt();
        }
        args[2] = impl.getOrder() == null ? order : impl.getOrder();
        args[3] = impl.getTitle() == null ? "\u672a\u547d\u540d" : impl.getTitle();
        args[4] = impl.getAbstractDesc() == null ? "" : impl.getAbstractDesc();
        args[5] = impl.getPoster();
        args[6] = StringUtils.isEmpty(impl.getContent()) ? " " : impl.getContent();
        args[7] = impl.getLink();
        args[8] = impl.getShowLatest();
        args[9] = impl.getTop();
        args[10] = impl.getHide();
        args[11] = impl.getAutoPop();
        args[12] = StringUtils.isEmpty(impl.getExtention()) ? " " : impl.getExtention();
        Date date = new Date();
        args[13] = date;
        args[14] = impl.getViewCount();
        args[15] = date;
        args[16] = impl.getPortalId();
        args[17] = impl.getStartAuth();
        args[18] = " ";
        result = this.template.update(sql.toUpperCase(), args);
        return result > 0;
    }

    @Override
    public Boolean updateNews(NewsImpl impl) {
        int result = 0;
        String sql = String.format("update %s set %s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=? where %s=?", TABLE_NAME, NEWS_MID, NEWS_ORDER, "NEWS_TITLE", "NEWS_ABSTRACT", "NEWS_POSTER", "NEWS_CONTENT_REAL", "NEWS_LINK", "NEWS_SHOWLATEST", "NEWS_TOP", "NEWS_HIDE", "NEWS_AUTOPOP", "NEWS_EXTENTION", "NEWS_VIEWCOUNT", "NEWS_UPDATETIME", "NEWS_STARTAUTH", NEWS_ID);
        Object[] args = new Object[16];
        int i = 0;
        args[i++] = impl.getMid();
        int order = 0;
        if (impl.getOrder() == null) {
            order = News2DaoImpl.nextInt();
        }
        args[i++] = impl.getOrder() == null ? order : impl.getOrder();
        args[i++] = impl.getTitle() == null ? "\u672a\u547d\u540d" : impl.getTitle();
        args[i++] = impl.getAbstractDesc() == null ? "" : impl.getAbstractDesc();
        args[i++] = impl.getPoster();
        args[i++] = StringUtils.isEmpty(impl.getContent()) ? " " : impl.getContent();
        args[i++] = impl.getLink();
        args[i++] = impl.getShowLatest();
        args[i++] = impl.getTop();
        args[i++] = impl.getHide();
        args[i++] = impl.getAutoPop();
        args[i++] = StringUtils.isEmpty(impl.getExtention()) ? " " : impl.getExtention();
        args[i++] = impl.getViewCount();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = impl.getUpdateTime() != null ? impl.getUpdateTime() : LocalDateTime.now();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        args[i++] = date;
        args[i++] = impl.getStartAuth();
        args[i++] = impl.getId();
        result = this.template.update(sql.toUpperCase(), args);
        return result > 0;
    }

    @Override
    public Boolean deleteNews(String id) {
        int result = 0;
        String sql = String.format("delete from %s where %s=?", TABLE_NAME, NEWS_ID);
        Object[] args = new Object[]{id};
        result = this.template.update(sql.toUpperCase(), args);
        return result >= 0;
    }

    @Override
    public Boolean deleteNewsByMid(String mid, String portalId) {
        int result = 0;
        String sql = String.format("delete from %s where %s=? and %s=?", TABLE_NAME, NEWS_MID, NEWS_PORTAL_ID);
        Object[] args = new Object[]{mid, portalId};
        result = this.template.update(sql.toUpperCase(), args);
        return result >= 0;
    }

    public Boolean deleteTableNewsByMid(String mid, String portalId, String tableName) {
        int result = 0;
        String sql = String.format("delete from %s where %s=? and %s=?", tableName, NEWS_MID, NEWS_PORTAL_ID);
        Object[] args = new Object[]{mid, portalId};
        result = this.template.update(sql.toUpperCase(), args);
        return result >= 0;
    }

    @Override
    public NewsImpl queryNews(String id, String type) {
        String sql = String.format("select NEWS_ID, NEWS_MID, NEWS_ORDER, NEWS_TITLE, NEWS_ABSTRACT, NEWS_POSTER,NEWS_CONTENT_REAL, NEWS_LINK, NEWS_SHOWLATEST, NEWS_TOP, NEWS_HIDE, NEWS_AUTOPOP,NEWS_EXTENTION, NEWS_CREATETIME, NEWS_VIEWCOUNT, NEWS_UPDATETIME, NEWS_PORTAL_ID, NEWS_STARTAUTH, NEWS_CONTENT from %s where %s=?", "running".equalsIgnoreCase(type) ? TABLE_NAME_RUNNING : TABLE_NAME, NEWS_ID);
        Object[] args = new Object[]{id};
        return (NewsImpl)this.template.query(sql.toUpperCase(), args, rs -> {
            if (rs.next()) {
                return this.buildINewsInfo(rs);
            }
            return null;
        });
    }

    NewsImpl buildINewsInfo(ResultSet rs) throws SQLException {
        int index = 1;
        NewsImpl info = new NewsImpl();
        info.setId(rs.getString(index));
        info.setMid(rs.getString(++index));
        info.setOrder(rs.getInt(++index));
        info.setTitle(rs.getString(++index));
        info.setAbstractDesc(rs.getString(++index));
        info.setPoster(rs.getString(++index));
        info.setContent(rs.getString(++index));
        info.setLink(rs.getString(++index));
        info.setShowLatest(rs.getBoolean(++index));
        info.setTop(rs.getBoolean(++index));
        info.setHide(rs.getBoolean(++index));
        info.setAutoPop(rs.getBoolean(++index));
        info.setExtention(rs.getString(++index));
        info.setCreateTime(rs.getTimestamp(++index).toLocalDateTime());
        info.setViewCount(rs.getInt(++index));
        info.setUpdateTime(rs.getTimestamp(++index).toLocalDateTime());
        info.setPortalId(rs.getString(++index));
        info.setStartAuth(rs.getBoolean(++index));
        return info;
    }

    @Override
    public Boolean publishNews(String mid, String portalId) {
        this.deleteTableNewsByMid(mid, portalId, TABLE_NAME_RUNNING);
        String sql = String.format("insert into %s (%s) select %s from %s where %s=? and %s=?", TABLE_NAME_RUNNING, ALL_NEWS_FIELD, ALL_NEWS_FIELD, TABLE_NAME, NEWS_MID, NEWS_PORTAL_ID);
        int result = this.template.update(sql.toUpperCase(), new Object[]{mid, portalId});
        return result > 0;
    }

    @Override
    public Boolean modifyNewsOrder(String id, Integer newOrder) {
        String sql = String.format("update %s set %s=? where %s=?", TABLE_NAME, NEWS_ORDER, NEWS_ID);
        int result = this.template.update(sql.toUpperCase(), new Object[]{newOrder, id});
        return result > 0;
    }

    @Override
    public List<NewsAbstractInfo> queryAllNews() {
        String sql = String.format("select NEWS_ID, NEWS_ORDER, NEWS_LINK, NEWS_TITLE, NEWS_ABSTRACT, NEWS_POSTER, NEWS_UPDATETIME,NEWS_TOP, NEWS_HIDE, NEWS_AUTOPOP,NEWS_SHOWLATEST, NEWS_STARTAUTH, NEWS_VIEWCOUNT, NEWS_EXTENTION from %s ", TABLE_NAME);
        List result = this.template.query(sql.toUpperCase(), (RowMapper)new NewsRowMapper());
        return result;
    }

    @Override
    public List<NewsAbstractInfo> queryNewsByMidAndPortalId(String mid, String portalId, String type) {
        String sql = String.format("select NEWS_ID, NEWS_ORDER, NEWS_LINK, NEWS_TITLE, NEWS_ABSTRACT, NEWS_POSTER, NEWS_UPDATETIME,NEWS_TOP, NEWS_HIDE, NEWS_AUTOPOP,NEWS_SHOWLATEST, NEWS_STARTAUTH, NEWS_VIEWCOUNT, NEWS_EXTENTION from %s where %s=? and %s=?", "running".equalsIgnoreCase(type) ? TABLE_NAME_RUNNING : TABLE_NAME, NEWS_MID, NEWS_PORTAL_ID);
        List result = this.template.query(sql.toUpperCase(), new Object[]{mid, portalId}, (RowMapper)new NewsRowMapper());
        return result;
    }

    @Override
    public Boolean modifyFileOrder(String id, Integer newOrder) {
        String sql = String.format("update %s set %s=? where %s=?", TABLE_NAME, NEWS_ORDER, NEWS_ID);
        int result = this.template.update(sql.toUpperCase(), new Object[]{newOrder, id});
        return result > 0;
    }

    @Override
    public Boolean updateNewsRunning(NewsImpl impl) {
        int result = 0;
        String sql = String.format("update %s set %s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=? where %s=?", TABLE_NAME_RUNNING, NEWS_MID, NEWS_ORDER, "NEWS_TITLE", "NEWS_ABSTRACT", "NEWS_POSTER", "NEWS_CONTENT_REAL", "NEWS_LINK", "NEWS_SHOWLATEST", "NEWS_TOP", "NEWS_HIDE", "NEWS_AUTOPOP", "NEWS_EXTENTION", "NEWS_VIEWCOUNT", "NEWS_UPDATETIME", "NEWS_STARTAUTH", NEWS_ID);
        Object[] args = new Object[16];
        int i = 0;
        args[i++] = impl.getMid();
        int order = 0;
        if (impl.getOrder() == null) {
            order = News2DaoImpl.nextInt();
        }
        args[i++] = impl.getOrder() == null ? order : impl.getOrder();
        args[i++] = impl.getTitle() == null ? "\u672a\u547d\u540d" : impl.getTitle();
        args[i++] = impl.getAbstractDesc() == null ? "" : impl.getAbstractDesc();
        args[i++] = impl.getPoster();
        args[i++] = StringUtils.isEmpty(impl.getContent()) ? " " : impl.getContent();
        args[i++] = impl.getLink();
        args[i++] = impl.getShowLatest();
        args[i++] = impl.getTop();
        args[i++] = impl.getHide();
        args[i++] = impl.getAutoPop();
        args[i++] = StringUtils.isEmpty(impl.getExtention()) ? " " : impl.getExtention();
        args[i++] = impl.getViewCount();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = impl.getUpdateTime() != null ? impl.getUpdateTime() : LocalDateTime.now();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        args[i++] = date;
        args[i++] = impl.getStartAuth();
        args[i++] = impl.getId();
        result = this.template.update(sql.toUpperCase(), args);
        return result > 0;
    }

    @Override
    public Boolean updateViewCount(int viewCount, String id, boolean design) {
        int result = 0;
        String sql = String.format("update %s set %s=? where %s=?", design ? TABLE_NAME : TABLE_NAME_RUNNING, "NEWS_VIEWCOUNT", NEWS_ID);
        Object[] args = new Object[2];
        int i = 0;
        args[i++] = viewCount;
        args[i] = id;
        result = this.template.update(sql.toUpperCase(), args);
        return result > 0;
    }

    @Override
    public Integer getMaxOrder() {
        String sql = String.format("select news_order from %s order by news_order desc", TABLE_NAME);
        return (Integer)this.template.query(sql.toUpperCase(), new Object[0], rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        });
    }

    class NewsRowMapper
    implements RowMapper<NewsAbstractInfo> {
        NewsRowMapper() {
        }

        public NewsAbstractInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            NewsAbstractInfo buildINewsInfo = this.buildNewsAbstractInfo(rs);
            return buildINewsInfo;
        }

        private NewsAbstractInfo buildNewsAbstractInfo(ResultSet rs) throws SQLException {
            int index = 1;
            NewsAbstractInfo info = new NewsAbstractInfo();
            info.setId(rs.getString(index));
            info.setOrder(rs.getInt(++index));
            info.setLink(rs.getString(++index));
            info.setTitle(rs.getString(++index));
            info.setAbstractDesc(rs.getString(++index));
            info.setPoster(rs.getString(++index));
            info.setUpdateTime(rs.getTimestamp(++index).toLocalDateTime());
            info.setTop(rs.getBoolean(++index));
            info.setHide(rs.getBoolean(++index));
            info.setAutoPop(rs.getBoolean(++index));
            info.setShowLatest(rs.getBoolean(++index));
            info.setStartAuth(rs.getBoolean(++index));
            info.setViewCount(rs.getInt(++index));
            info.setExtention(rs.getString(++index));
            return info;
        }
    }

    public static class SecureRandomTest {
        private static SecureRandom ran;

        public SecureRandomTest() {
            ran = new SecureRandom();
        }

        public int getRandom(int seed) {
            return ran.nextInt(seed);
        }

        public int getRandom() {
            return ran.nextInt();
        }
    }
}

