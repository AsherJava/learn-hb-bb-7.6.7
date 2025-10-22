/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  com.jiuqi.bi.database.metadata.LogicTable
 *  com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException
 *  com.jiuqi.nr.query.datascheme.extend.DataTableFolder
 *  com.jiuqi.nr.query.datascheme.extend.DataTableNode
 *  com.jiuqi.nr.query.datascheme.extend.IDataTableMetaDataProvider
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaDataSourceGroup
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.dto.NvwaDataSourceInfoDto
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceNotFoundException
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.IDataSourceInfoFilterService
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.IDynamicDataSourceManager
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.bql.extend;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.DataTableFolder;
import com.jiuqi.nr.query.datascheme.extend.DataTableNode;
import com.jiuqi.nr.query.datascheme.extend.IDataTableMetaDataProvider;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaDataSourceGroup;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.dto.NvwaDataSourceInfoDto;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceNotFoundException;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.IDataSourceInfoFilterService;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.IDynamicDataSourceManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NvwaDataSourceMetaDataProvider
implements IDataTableMetaDataProvider {
    private static final Logger logger = LoggerFactory.getLogger(NvwaDataSourceMetaDataProvider.class);
    private static final String SYSTEM_DATASOURCE_ROOT_KEY = "system_datasource_group_root";
    private static final String SYSTEM_DATASOURCE_ROOT_TITLE = "\u7cfb\u7edf\u6570\u636e\u6e90";
    private static final String EXT_DATASOURCE_ROOT_KEY = "ext_datasource_group_root";
    private static final String EXT_DATASOURCE_ROOT_TITLE = "\u5916\u90e8\u6570\u636e\u6e90";
    private static final Collator collator = Collator.getInstance(Locale.SIMPLIFIED_CHINESE);
    @Autowired
    private IDynamicDataSourceManager dynamicDataSourceManager;
    private final DataTableFolderComparator comparator = new DataTableFolderComparator();
    @Autowired
    private IDataSourceInfoFilterService dataSourceInfoFilterService;
    @Autowired
    private DynamicDataSource dynamicDataSource;
    @Autowired
    private DataSource dataSource;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<DataTableNode> getTables(String parent) throws DataTableAdaptException {
        ArrayList<DataTableNode> result = new ArrayList<DataTableNode>();
        if (parent == null) {
            return result;
        }
        try (Connection conn = this.getConn(parent);){
            if (conn == null) {
                ArrayList<DataTableNode> arrayList = result;
                return arrayList;
            }
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            ISQLMetadata metadata = database.createMetadata(conn);
            List tables = metadata.getUserTables();
            tables.forEach(table -> result.add(this.makeTableNode(parent + "." + table.getName(), table.getName(), table.getDescription())));
            return result;
        }
        catch (Exception e) {
            throw new DataTableAdaptException(e.getMessage(), e);
        }
    }

    public List<DataTableFolder> getTableFolders(String parent) throws DataTableAdaptException {
        ArrayList<DataTableFolder> result = new ArrayList<DataTableFolder>();
        try {
            List dataSourceInfos = this.dataSourceInfoFilterService.doFilter(this.dynamicDataSourceManager.listAllDataSourceInfos());
            List<NvwaDataSourceInfoDto> extDataSourceInfos = dataSourceInfos.stream().filter(info -> info.getGroup() != NvwaDataSourceGroup.SYSTEM_DATASOURCE_GROUP).collect(Collectors.toList());
            if (StringUtils.isEmpty((CharSequence)parent)) {
                result.add(this.makeFolderNode(NvwaDataSourceGroup.SYSTEM_DATASOURCE_GROUP.getDsGroupGuid(), NvwaDataSourceGroup.SYSTEM_DATASOURCE_GROUP.getDsGroupTitle()));
                if (!extDataSourceInfos.isEmpty()) {
                    result.add(this.makeFolderNode(EXT_DATASOURCE_ROOT_KEY, EXT_DATASOURCE_ROOT_TITLE));
                }
            } else if (parent.equals(EXT_DATASOURCE_ROOT_KEY)) {
                HashMap groups = new HashMap();
                extDataSourceInfos.forEach(info -> {
                    NvwaDataSourceGroup group = info.getGroup();
                    if (group != null && !groups.containsKey(group.getDsGroupGuid())) {
                        groups.put(group.getDsGroupGuid(), group);
                    }
                });
                groups.values().forEach(group -> result.add(this.makeFolderNode(group.getDsGroupGuid(), group.getDsGroupTitle())));
                Collections.sort(result, this.comparator);
            } else {
                dataSourceInfos.forEach(info -> {
                    if (info.getGroup().getDsGroupGuid().equals(parent)) {
                        result.add(this.makeFolderNode(info.getKey(), info.getTitle()));
                    }
                });
                Collections.sort(result, this.comparator);
            }
        }
        catch (Exception e) {
            throw new DataTableAdaptException(e.getMessage(), e);
        }
        return result;
    }

    public List<String> getPath(String tableCodeKey) throws DataTableAdaptException {
        ArrayList<String> path = new ArrayList<String>();
        try {
            String[] strs = tableCodeKey.split("\\.");
            if (strs.length != 2) {
                return path;
            }
            String dataSourceKey = strs[0];
            NvwaDataSourceInfoDto info = this.dynamicDataSourceManager.getDataSourceInfo(dataSourceKey);
            path.add(info.getKey());
            NvwaDataSourceGroup group = info.getGroup();
            path.add(group.getDsGroupGuid());
            if (group != NvwaDataSourceGroup.SYSTEM_DATASOURCE_GROUP) {
                path.add(EXT_DATASOURCE_ROOT_KEY);
            }
            Collections.reverse(path);
        }
        catch (Exception e) {
            throw new DataTableAdaptException(e.getMessage(), e);
        }
        return path;
    }

    public List<String> getTitlePath(String tableCodeKey) throws DataTableAdaptException {
        ArrayList<String> path = new ArrayList<String>();
        try {
            String[] strs = tableCodeKey.split("\\.");
            if (strs.length != 2) {
                return path;
            }
            String dataSourceKey = strs[0];
            NvwaDataSourceInfoDto info = this.dynamicDataSourceManager.getDataSourceInfo(dataSourceKey);
            path.add(info.getTitle());
            NvwaDataSourceGroup group = info.getGroup();
            path.add(group.getDsGroupTitle());
            if (group != NvwaDataSourceGroup.SYSTEM_DATASOURCE_GROUP) {
                path.add(EXT_DATASOURCE_ROOT_TITLE);
            }
            Collections.reverse(path);
        }
        catch (Exception e) {
            throw new DataTableAdaptException(e.getMessage(), e);
        }
        return path;
    }

    public List<DataTableNode> search(String keyStr) throws DataTableAdaptException {
        return this.search(null, keyStr);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<DataTableNode> search(String parent, String keyStr) throws DataTableAdaptException {
        ArrayList<DataTableNode> result = new ArrayList<DataTableNode>();
        String dataSourceKey = parent;
        if (StringUtils.isEmpty((CharSequence)dataSourceKey)) {
            dataSourceKey = "nrDataSource";
        }
        try (Connection conn = this.getConn(dataSourceKey);){
            if (conn == null) {
                ArrayList<DataTableNode> arrayList = result;
                return arrayList;
            }
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            ISQLMetadata metadata = database.createMetadata(conn);
            List tables = metadata.searchAllTables(keyStr, metadata.getDefaultSchema());
            Iterator iterator = tables.iterator();
            while (iterator.hasNext()) {
                LogicTable table = (LogicTable)iterator.next();
                result.add(this.makeTableNode(dataSourceKey + "." + table.getName(), table.getName(), table.getDescription()));
            }
            return result;
        }
        catch (Exception e) {
            throw new DataTableAdaptException(e.getMessage(), e);
        }
    }

    private Connection getConn(String datasourceKey) throws SQLException {
        try {
            if ("nrDataSource".equalsIgnoreCase(datasourceKey)) {
                return this.dataSource.getConnection();
            }
            try {
                NvwaDataSourceInfoDto info = this.dynamicDataSourceManager.getDataSourceInfo(datasourceKey);
                if (info != null) {
                    DataSource dataSource = this.dynamicDataSource.getDataSource(datasourceKey);
                    return dataSource.getConnection();
                }
            }
            catch (DataSourceNotFoundException info) {}
        }
        catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            throw new SQLException("\u6570\u636e\u6e90\u8fde\u63a5\u5931\u8d25, \u8fde\u63a5\u4e0d\u53ef\u7528", ex);
        }
        return null;
    }

    private DataTableNode makeTableNode(String guid, String name, String title) {
        DataTableNode node = new DataTableNode();
        node.setName(name);
        node.setKey(guid);
        node.setTitle(title);
        node.setType(title);
        return node;
    }

    private DataTableFolder makeFolderNode(String guid, String title) {
        DataTableFolder folder = new DataTableFolder();
        folder.setId(guid);
        folder.setTitle(title);
        folder.setType("nvwaDataSource");
        return folder;
    }

    private class DataTableFolderComparator
    implements Comparator<DataTableFolder> {
        private DataTableFolderComparator() {
        }

        @Override
        public int compare(DataTableFolder o1, DataTableFolder o2) {
            if ("_default".equals(o1.getId())) {
                return -1;
            }
            if ("_default".equals(o2.getId())) {
                return 1;
            }
            return collator.compare(o1.getTitle(), o2.getTitle());
        }
    }
}

