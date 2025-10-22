/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.portal.news2.service.impl;

import com.jiuqi.nr.portal.news2.impl.FileImpl;
import com.jiuqi.nr.portal.news2.service.IPortalFileDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class PortalFileDaoImpl
implements IPortalFileDao {
    private static final String TABLE_NAME = "PORTAL_FILE_DESIGN";
    private static final String TABLE_NAME_RUNNING = "PORTAL_FILE_RUNNING";
    private static final String ALL_FIELD_FIELD = "FILE_ID, FILE_MID, FILE_ORDER, FILE_TITLE, FILE_CONTENTTYPE, FILE_FILESIZE, FILE_DESCRIPTION, FILE_LINK, FILE_SHOWLATEST, FILE_TOP, FILE_HIDE, FILE_EXTENTION, FILE_DOWNLOADCOUNT, FILE_CREATETIME, FILE_UPDATETIME, FILE_PORTAL_ID, FILE_SHOWTITLE, FILE_STARTAUTH";
    private static final String FILE_ID = "FILE_ID";
    private static final String FILE_MID = "FILE_MID";
    private static final String FILE_ORDER = "FILE_ORDER";
    private static final String FILE_PORTAL_ID = "FILE_PORTAL_ID";
    @Autowired
    private JdbcTemplate template;

    @Override
    public Boolean deleteFileByMid(String mid, String portalId, String tableName) {
        tableName = "running".equals(tableName) ? TABLE_NAME_RUNNING : TABLE_NAME;
        int result = 0;
        String sql = String.format("delete from %s where %s=? and %s=?", tableName, FILE_MID, FILE_PORTAL_ID);
        Object[] args = new Object[]{mid, portalId};
        result = this.template.update(sql.toUpperCase(), args);
        return result > 0;
    }

    @Override
    public Boolean deleteFileByFileId(String fileId) {
        int result = 0;
        String sql = String.format("delete from %s where %s=?", TABLE_NAME, FILE_ID);
        Object[] args = new Object[]{fileId};
        result = this.template.update(sql.toUpperCase(), args);
        return result > 0;
    }

    @Override
    public Boolean publishFiles(String mid, String portalId) {
        this.deleteFileByMid(mid, portalId, "running");
        String sql = String.format("insert into %s (%s) select %s from %s where %s=? and %s=?", TABLE_NAME_RUNNING, ALL_FIELD_FIELD, ALL_FIELD_FIELD, TABLE_NAME, FILE_MID, FILE_PORTAL_ID);
        int result = this.template.update(sql.toUpperCase(), new Object[]{mid, portalId});
        return result >= 0;
    }

    @Override
    public Boolean modifyFileOrder(String id, Integer newOrder) {
        String sql = String.format("update %s set %s=? where %s=?", TABLE_NAME, FILE_ORDER, FILE_ID);
        int result = this.template.update(sql.toUpperCase(), new Object[]{newOrder, id});
        return result > 0;
    }

    @Override
    public FileImpl queryFileByFileId(String fileId) {
        String sql = String.format("select * from %s where %s=?", TABLE_NAME, FILE_ID);
        Object[] args = new Object[]{fileId};
        return (FileImpl)this.template.query(sql.toUpperCase(), args, rs -> {
            if (rs.next()) {
                return this.buildFileImpl(rs);
            }
            return null;
        });
    }

    @Override
    public Boolean insertFile(FileImpl impl) {
        int result = 0;
        String sql = String.format("insert into %s (%s) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", TABLE_NAME, ALL_FIELD_FIELD);
        Object[] args = new Object[]{impl.getId(), impl.getMid(), impl.getOrder() == null ? 0 : impl.getOrder(), impl.getTitle() == null ? "\u672a\u547d\u540d" : impl.getTitle(), impl.getContentType(), impl.getFileSize(), StringUtils.isEmpty(impl.getDescription()) ? " " : impl.getDescription(), impl.getLink(), impl.getShowLatest(), impl.getTop(), impl.getHide(), StringUtils.isEmpty(impl.getExtention()) ? " " : impl.getExtention(), impl.getDownLoadCount(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), impl.getPortalId(), impl.getShowTitle(), impl.getStartAuth()};
        result = this.template.update(sql.toUpperCase(), args);
        return result > 0;
    }

    @Override
    public Boolean insertFileRunning(FileImpl impl) {
        int result = 0;
        String sql = String.format("insert into %s (%s) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", TABLE_NAME_RUNNING, ALL_FIELD_FIELD);
        Object[] args = new Object[]{impl.getId(), impl.getMid(), impl.getOrder() == null ? Integer.MAX_VALUE : impl.getOrder(), impl.getTitle() == null ? "\u672a\u547d\u540d" : impl.getTitle(), impl.getContentType(), impl.getFileSize(), StringUtils.isEmpty(impl.getDescription()) ? " " : impl.getDescription(), impl.getLink(), impl.getShowLatest(), impl.getTop(), impl.getHide(), StringUtils.isEmpty(impl.getExtention()) ? " " : impl.getExtention(), impl.getDownLoadCount(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), impl.getPortalId(), impl.getShowTitle(), impl.getStartAuth()};
        result = this.template.update(sql.toUpperCase(), args);
        return result > 0;
    }

    @Override
    public Boolean updateFile(FileImpl impl) {
        int result = 0;
        String sql = String.format("update %s set %s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=? where %s=?", TABLE_NAME, FILE_MID, FILE_ORDER, "FILE_TITLE", "FILE_CONTENTTYPE", "FILE_FILESIZE", "FILE_DESCRIPTION", "FILE_LINK", "FILE_SHOWLATEST", "FILE_TOP", "FILE_HIDE", "FILE_EXTENTION", "FILE_DOWNLOADCOUNT", "FILE_UPDATETIME", FILE_PORTAL_ID, "FILE_SHOWTITLE", "FILE_STARTAUTH", FILE_ID);
        int i = 0;
        Object[] args = new Object[17];
        args[i++] = impl.getMid();
        args[i++] = impl.getOrder() == null ? Integer.MAX_VALUE : impl.getOrder();
        args[i++] = impl.getTitle() == null ? "\u672a\u547d\u540d" : impl.getTitle();
        args[i++] = impl.getContentType();
        args[i++] = impl.getFileSize();
        args[i++] = StringUtils.isEmpty(impl.getDescription()) ? " " : impl.getDescription();
        args[i++] = impl.getLink();
        args[i++] = impl.getShowLatest();
        args[i++] = impl.getTop();
        args[i++] = impl.getHide();
        args[i++] = StringUtils.isEmpty(impl.getExtention()) ? " " : impl.getExtention();
        args[i++] = impl.getDownLoadCount();
        args[i++] = Timestamp.valueOf(impl.getUpdateTime());
        args[i++] = impl.getPortalId();
        args[i++] = impl.getShowTitle();
        args[i++] = impl.getStartAuth() == null ? false : impl.getStartAuth();
        args[i++] = impl.getId();
        result = this.template.update(sql.toUpperCase(), args);
        return result > 0;
    }

    @Override
    public Boolean updateFileRunning(FileImpl impl) {
        int result = 0;
        String sql = String.format("update %s set %s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=?,%s=? where %s=?", TABLE_NAME_RUNNING, FILE_MID, FILE_ORDER, "FILE_TITLE", "FILE_CONTENTTYPE", "FILE_FILESIZE", "FILE_DESCRIPTION", "FILE_LINK", "FILE_SHOWLATEST", "FILE_TOP", "FILE_HIDE", "FILE_EXTENTION", "FILE_DOWNLOADCOUNT", "FILE_UPDATETIME", FILE_PORTAL_ID, "FILE_SHOWTITLE", "FILE_STARTAUTH", FILE_ID);
        int i = 0;
        Object[] args = new Object[17];
        args[i++] = impl.getMid();
        args[i++] = impl.getOrder() == null ? Integer.MAX_VALUE : impl.getOrder();
        args[i++] = impl.getTitle() == null ? "\u672a\u547d\u540d" : impl.getTitle();
        args[i++] = impl.getContentType();
        args[i++] = impl.getFileSize();
        args[i++] = StringUtils.isEmpty(impl.getDescription()) ? " " : impl.getDescription();
        args[i++] = impl.getLink();
        args[i++] = impl.getShowLatest();
        args[i++] = impl.getTop();
        args[i++] = impl.getHide();
        args[i++] = StringUtils.isEmpty(impl.getExtention()) ? " " : impl.getExtention();
        args[i++] = impl.getDownLoadCount();
        args[i++] = Timestamp.valueOf(impl.getUpdateTime());
        args[i++] = impl.getPortalId();
        args[i++] = impl.getShowTitle();
        args[i++] = impl.getStartAuth() == null ? false : impl.getStartAuth();
        args[i++] = impl.getId();
        result = this.template.update(sql.toUpperCase(), args);
        return result > 0;
    }

    FileImpl buildFileImpl(ResultSet rs) throws SQLException {
        int index = 1;
        FileImpl info = new FileImpl();
        info.setId(rs.getString(index));
        info.setMid(rs.getString(++index));
        info.setOrder(rs.getInt(++index));
        info.setTitle(rs.getString(++index));
        info.setContentType(rs.getString(++index));
        info.setFileSize(rs.getLong(++index));
        info.setDescription(rs.getString(++index));
        info.setLink(rs.getString(++index));
        info.setShowLatest(rs.getBoolean(++index));
        info.setTop(rs.getBoolean(++index));
        info.setHide(rs.getBoolean(++index));
        info.setExtention(rs.getString(++index));
        info.setDownLoadCount(rs.getInt(++index));
        info.setCreateTime(rs.getTimestamp(++index).toLocalDateTime());
        info.setUpdateTime(rs.getTimestamp(++index).toLocalDateTime());
        info.setPortalId(rs.getString(++index));
        info.setShowTitle(rs.getString(++index));
        info.setStartAuth(rs.getBoolean(++index));
        return info;
    }

    @Override
    public List<FileImpl> queryAllFile() {
        String sql = String.format("select FILE_ID, FILE_MID, FILE_ORDER, FILE_TITLE, FILE_CONTENTTYPE, FILE_FILESIZE, FILE_DESCRIPTION, FILE_LINK, FILE_SHOWLATEST, FILE_TOP, FILE_HIDE, FILE_EXTENTION, FILE_DOWNLOADCOUNT, FILE_CREATETIME, FILE_UPDATETIME, FILE_PORTAL_ID, FILE_SHOWTITLE, FILE_STARTAUTH \nfrom %s ", TABLE_NAME);
        List result = this.template.query(sql.toUpperCase(), (RowMapper)new FileRowMapper());
        return result;
    }

    @Override
    public List<FileImpl> queryFileByMidAndPortalId(String mid, String portalId, String type) {
        String sql = String.format("select FILE_ID, FILE_MID, FILE_ORDER, FILE_TITLE, FILE_CONTENTTYPE, FILE_FILESIZE, FILE_DESCRIPTION, FILE_LINK, FILE_SHOWLATEST, FILE_TOP, FILE_HIDE, FILE_EXTENTION, FILE_DOWNLOADCOUNT, FILE_CREATETIME, FILE_UPDATETIME, FILE_PORTAL_ID, FILE_SHOWTITLE, FILE_STARTAUTH \nfrom %s where %s=? and %s=?", "running".equalsIgnoreCase(type) ? TABLE_NAME_RUNNING : TABLE_NAME, FILE_MID, FILE_PORTAL_ID);
        List result = this.template.query(sql.toUpperCase(), new Object[]{mid, portalId}, (RowMapper)new FileRowMapper());
        return result;
    }

    @Override
    public Integer getMaxOrder() {
        String sql = String.format("select FILE_ORDER from %s order by FILE_ORDER desc", TABLE_NAME);
        return (Integer)this.template.query(sql.toUpperCase(), new Object[0], rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        });
    }

    class FileRowMapper
    implements RowMapper<FileImpl> {
        FileRowMapper() {
        }

        public FileImpl mapRow(ResultSet rs, int rowNum) throws SQLException {
            FileImpl fileImpl = PortalFileDaoImpl.this.buildFileImpl(rs);
            return fileImpl;
        }
    }
}

