/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 *  com.jiuqi.bi.transfer.engine.FolderNode
 */
package com.jiuqi.bi.core.jobs.manager;

import com.jiuqi.bi.core.jobs.dao.FolderDao;
import com.jiuqi.bi.core.jobs.extension.item.FolderItem;
import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import com.jiuqi.bi.transfer.engine.FolderNode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FolderManager {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<FolderItem> getFolders(String parentGuid) throws SQLException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            List<FolderItem> list = FolderDao.getFolders(conn, parentGuid);
            return list;
        }
    }

    public FolderItem getFolder(String folderGuid) throws SQLException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            FolderItem folderItem = FolderDao.getFolder(conn, folderGuid);
            return folderItem;
        }
    }

    public List<FolderItem> getFolderPaths(String folderGuid) throws SQLException {
        ArrayList<FolderItem> path = new ArrayList<FolderItem>();
        FolderManager fm = new FolderManager();
        FolderItem folder = fm.getFolder(folderGuid);
        if (folder != null) {
            String parentGuid = folder.getParentGuid();
            while (folder != null && parentGuid != null) {
                path.add(folder);
                parentGuid = folder.getParentGuid();
                folder = fm.getFolder(parentGuid);
            }
            Collections.reverse(path);
        }
        return path;
    }

    public String addFolder(FolderNode folderBean) throws Exception {
        FolderItem existFolder = this.getFoldersByTitle(folderBean.getParentGuid(), folderBean.getTitle(), folderBean.getType());
        if (existFolder != null && !existFolder.getGuid().equals(folderBean.getGuid())) {
            return existFolder.getGuid();
        }
        String title = folderBean.getTitle().trim();
        if (title.endsWith(".")) {
            throw new Exception("\u6587\u4ef6\u5939\u540d\u79f0\u4e0d\u80fd\u4ee5.\u7ed3\u5c3e");
        }
        if (title != null && title.length() > 50) {
            throw new Exception("\u6587\u4ef6\u5939\u540d\u79f0\u4e0d\u80fd\u8d85\u8fc750\u5b57\u7b26");
        }
        Pattern pattern = Pattern.compile("[\\\\/:*?\"'<>&|]");
        Matcher matcher = pattern.matcher(title);
        if (matcher.find()) {
            throw new Exception("\u6587\u4ef6\u5939\u540d\u79f0\u4e0d\u80fd\u5305\u542b\\\u3001/\u3001:\u3001*\u3001?\u3001\"\u3001'\u3001<\u3001>\u3001|\u5b57\u7b26");
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            FolderDao.addFolder(conn, folderBean);
        }
        return folderBean.getGuid();
    }

    public FolderItem getFoldersByTitle(String parentGuid, String title, String type) throws Exception {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            FolderItem folderItem = FolderDao.getFoldersByTitle(conn, parentGuid, title, type);
            return folderItem;
        }
    }

    public void modifyFolder(FolderNode folderBean) throws Exception {
        FolderItem existFolder = this.getFoldersByTitle(folderBean.getParentGuid(), folderBean.getTitle(), folderBean.getType());
        if (existFolder != null && !existFolder.getGuid().equals(folderBean.getGuid())) {
            throw new Exception("\u540c\u4e00\u76ee\u5f55\u4e0b\u6587\u4ef6\u5939\u540d\u79f0\u4e0d\u5141\u8bb8\u91cd\u590d");
        }
        String title = folderBean.getTitle().trim();
        if (title.endsWith(".")) {
            throw new Exception("\u6587\u4ef6\u5939\u540d\u79f0\u4e0d\u80fd\u4ee5.\u7ed3\u5c3e");
        }
        if (title != null && title.length() > 50) {
            throw new Exception("\u6587\u4ef6\u5939\u540d\u79f0\u4e0d\u80fd\u8d85\u8fc750\u5b57\u7b26");
        }
        Pattern pattern = Pattern.compile("[^(\\\\)(/)(:)(*)(?)(\")(')(<)(>)(|)]{1,}");
        Matcher matcher = pattern.matcher(title);
        if (!matcher.matches()) {
            throw new Exception("\u6587\u4ef6\u5939\u540d\u79f0\u4e0d\u80fd\u5305\u542b\u5b57\u7b26\\\u3001/\u3001:\u3001*\u3001?\u3001\"\u3001'\u3001<\u3001>\u3001|\u5b57\u7b26");
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            FolderDao.modifyFolder(conn, folderBean);
        }
    }
}

