/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.jobs.dao;

import com.jiuqi.bi.core.jobs.extension.item.FolderItem;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FolderDao {
    private static final String T_TABLE = "META_DW_FOLDER";
    private static final String F_GUID = "MDF_GUID";
    private static final String MDS_GUID = "MDS_GUID";
    private static final String F_TITLE = "MDF_TITLE";
    private static final String F_TYPE = "MDF_TYPE";
    private static final String F_PARENT = "MDF_PARENT";
    private static final String F_ORDER = "MDF_ORDER";
    private static final String SQL_QUERY_FOLDERS = "SELECT MDF_GUID, MDF_TITLE, MDF_TYPE, MDF_PARENT, MDF_ORDER FROM META_DW_FOLDER WHERE MDF_PARENT =?  ORDER BY MDF_ORDER";
    private static final String SQL_QUERY_FOLDER = "SELECT MDF_GUID, MDF_TITLE, MDF_TYPE, MDF_PARENT, MDF_ORDER FROM META_DW_FOLDER WHERE MDF_GUID =? ";
    private static final String SQL_UPDATE_FOLDER = "UPDATE META_DW_FOLDER SET MDF_TITLE = ?, MDF_TYPE = ?, MDF_PARENT = ? WHERE MDF_GUID = ?";
    private static final String SQL_INSERT_FOLDER = "INSERT INTO META_DW_FOLDER(MDS_GUID, MDF_GUID, MDF_TITLE, MDF_TYPE, MDF_PARENT, MDF_ORDER) VALUES (?, ?, ?, ?, ?, ?)";

    private FolderDao() {
    }

    public static List<FolderItem> getFolders(Connection conn, String parentId) throws SQLException {
        ArrayList<FolderItem> folderItems = new ArrayList<FolderItem>();
        if (conn == null || StringUtils.isEmpty((String)parentId)) {
            return folderItems;
        }
        try (PreparedStatement ps = conn.prepareStatement(SQL_QUERY_FOLDERS);){
            ps.setString(1, parentId);
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    int index = 1;
                    FolderItem item = new FolderItem();
                    item.setGuid(rs.getString(index++));
                    item.setTitle(rs.getString(index++));
                    item.setType(rs.getString(index++));
                    item.setParentGuid(rs.getString(index++));
                    item.setOrder(rs.getString(index));
                    folderItems.add(item);
                }
            }
        }
        return folderItems;
    }

    public static FolderItem getFolder(Connection conn, String folderGuid) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_QUERY_FOLDER);){
            ps.setString(1, folderGuid);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next()) {
                    int index = 1;
                    FolderItem item = new FolderItem();
                    item.setGuid(rs.getString(index++));
                    item.setTitle(rs.getString(index++));
                    item.setType(rs.getString(index++));
                    item.setParentGuid(rs.getString(index++));
                    item.setOrder(rs.getString(index));
                    FolderItem folderItem = item;
                    return folderItem;
                }
            }
        }
        return null;
    }

    public static void addFolder(Connection conn, FolderNode folderBean) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT_FOLDER);){
            pstmt.setString(1, "COMJIUQIBISOLUTION00000000000000");
            pstmt.setString(2, folderBean.getGuid());
            pstmt.setString(3, folderBean.getTitle());
            pstmt.setString(4, folderBean.getType());
            pstmt.setString(5, folderBean.getParentGuid());
            pstmt.setString(6, OrderGenerator.newOrder());
            pstmt.executeUpdate();
        }
    }

    public static FolderItem getFoldersByTitle(Connection conn, String parentGuid, String title, String type) throws SQLException {
        String sqlQueryFoldersByParentTitleType = "SELECT MDF_GUID, MDF_TITLE, MDF_TYPE, MDF_PARENT, MDF_ORDER FROM META_DW_FOLDER WHERE ";
        StringBuilder sql = new StringBuilder(sqlQueryFoldersByParentTitleType);
        if (StringUtils.isEmpty((String)parentGuid)) {
            sql.append("(MDF_PARENT").append(" = '' OR ").append(F_PARENT).append(" IS NULL)");
        } else {
            sql.append(F_PARENT).append(" = ? ");
        }
        sql.append(" AND UPPER(").append(F_TITLE).append(") = ? ");
        sql.append(" AND ");
        if (StringUtils.isEmpty((String)type)) {
            sql.append("(MDF_TYPE").append(" = '' OR ").append(F_TYPE).append(" IS NULL)");
        } else {
            sql.append(F_TYPE).append(" = ? ");
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql.toString());){
            int i = 1;
            if (!StringUtils.isEmpty((String)parentGuid)) {
                pstmt.setString(i++, parentGuid);
            }
            if (!StringUtils.isEmpty((String)title)) {
                pstmt.setString(i++, title.toUpperCase());
            } else {
                pstmt.setString(i++, title);
            }
            if (!StringUtils.isEmpty((String)type)) {
                pstmt.setString(i++, type);
            }
            try (ResultSet rs = pstmt.executeQuery();){
                if (rs.next()) {
                    FolderItem folderBean = new FolderItem();
                    folderBean.setGuid(rs.getString(1));
                    folderBean.setTitle(rs.getString(2));
                    folderBean.setType(rs.getString(3));
                    folderBean.setParentGuid(rs.getString(4));
                    folderBean.setOrder(rs.getString(5));
                    FolderItem folderItem = folderBean;
                    return folderItem;
                }
            }
        }
        return null;
    }

    public static void modifyFolder(Connection conn, FolderNode folderBean) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE_FOLDER);){
            pstmt.setString(1, folderBean.getTitle());
            pstmt.setString(2, folderBean.getType());
            pstmt.setString(3, folderBean.getParentGuid());
            pstmt.setString(4, folderBean.getGuid());
            pstmt.executeUpdate();
        }
    }
}

