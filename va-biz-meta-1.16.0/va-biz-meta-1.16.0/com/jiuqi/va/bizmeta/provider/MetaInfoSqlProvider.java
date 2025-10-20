/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.bizmeta.provider;

import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDTO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoFilterDTO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoPageDTO;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class MetaInfoSqlProvider {
    public String getMetaInfoGroup(MetaInfoPageDTO pageDTO) {
        SQL sql = new SQL();
        sql.SELECT("distinct MODULENAME, METATYPE, GROUPNAME, UNIQUECODE");
        sql.FROM("META_INFO");
        if (StringUtils.hasText(pageDTO.getModule())) {
            sql.WHERE("MODULENAME=#{module}");
        }
        if (!StringUtils.isEmpty(pageDTO.getMetaType()) && !"all".equals(pageDTO.getMetaType())) {
            sql.WHERE("METATYPE=#{metaType}");
        }
        return sql.toString();
    }

    public String getMetaEditionGroup(MetaInfoPageDTO pageDTO) {
        SQL sql = new SQL();
        sql.SELECT("distinct MODULENAME, GROUPNAME, METATYPE, UNIQUECODE");
        sql.FROM("META_INFO_USER");
        if (StringUtils.hasText(pageDTO.getModule())) {
            sql.WHERE("MODULENAME=#{module}");
        }
        if (!StringUtils.isEmpty(pageDTO.getMetaType()) && !"all".equals(pageDTO.getMetaType())) {
            sql.WHERE("METATYPE=#{metaType}");
        }
        if (StringUtils.hasText(pageDTO.getUserName())) {
            sql.WHERE("USERNAME=#{userName}");
        }
        return sql.toString();
    }

    public String selectByGroupName(MetaInfoPageDTO pageDTO) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("META_INFO");
        sql.WHERE("MODULENAME=#{module}");
        if (!StringUtils.isEmpty(pageDTO.getMetaType()) && !"all".equals(pageDTO.getMetaType())) {
            sql.WHERE("METATYPE=#{metaType}");
        }
        if (pageDTO.getGroupNames() != null) {
            String nameParam = "";
            StringBuffer names = new StringBuffer();
            for (String name : pageDTO.getGroupNames()) {
                names.append("'").append(name).append("',");
            }
            nameParam = names.toString();
            if (!StringUtils.isEmpty(nameParam)) {
                nameParam = nameParam.substring(0, nameParam.length() - 1);
                sql.WHERE("GROUPNAME in (" + nameParam + ")");
            }
        }
        if (!StringUtils.isEmpty(pageDTO.getUniqueCode())) {
            ((SQL)sql.AND()).WHERE("UNIQUECODE = #{uniqueCode, jdbcType=VARCHAR}");
        } else if (!StringUtils.isEmpty(pageDTO.getParam())) {
            String likeSql = "concat(concat('%', upper(#{param, jdbcType=VARCHAR})), '%')";
            ((SQL)sql.AND()).WHERE("upper(NAME) like " + likeSql + " OR upper(TITLE) like " + likeSql);
        }
        sql.ORDER_BY("VERSIONNO DESC");
        return sql.toString();
    }

    public String selectEditionByGroupName(MetaInfoPageDTO pageDTO) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("META_INFO_USER");
        sql.WHERE("MODULENAME=#{module}");
        if (StringUtils.hasText(pageDTO.getUserName())) {
            sql.WHERE("USERNAME=#{userName}");
        }
        if (!StringUtils.isEmpty(pageDTO.getMetaType()) && !"all".equals(pageDTO.getMetaType())) {
            sql.WHERE("METATYPE=#{metaType}");
        }
        if (pageDTO.getGroupNames() != null) {
            String nameParam = "";
            StringBuffer names = new StringBuffer();
            for (String name : pageDTO.getGroupNames()) {
                names.append("'").append(name).append("',");
            }
            nameParam = names.toString();
            if (!StringUtils.isEmpty(nameParam)) {
                nameParam = nameParam.substring(0, nameParam.length() - 1);
                sql.WHERE("GROUPNAME in (" + nameParam + ")");
            }
        }
        if (!StringUtils.isEmpty(pageDTO.getParam())) {
            String likeSql = "concat(concat('%', upper(#{param, jdbcType=VARCHAR})), '%')";
            ((SQL)sql.AND()).WHERE("upper(NAME) like " + likeSql + " OR upper(TITLE) like " + likeSql);
        }
        sql.ORDER_BY("VERSIONNO DESC");
        return sql.toString();
    }

    public String deleteByGroup(MetaInfoEditionDO paramMetaInfoEdition) {
        SQL sql = new SQL();
        sql.DELETE_FROM("META_INFO_USER");
        sql.WHERE(new String[]{"MODULENAME=#{moduleName}", "METATYPE=#{metaType}", "USERNAME=#{userName}", "GROUPNAME=#{groupName}"});
        return sql.toString();
    }

    public String deleteGroupByName(MetaGroupDTO groupDTO) {
        SQL sql = new SQL();
        sql.DELETE_FROM("META_GROUP_USER");
        sql.WHERE(new String[]{"MODULENAME=#{moduleName}", "METATYPE=#{metaType}", "USERNAME=#{userName}"});
        if (groupDTO.getNameList() != null) {
            String nameParam = "";
            StringBuffer names = new StringBuffer();
            for (String name : groupDTO.getNameList()) {
                names.append("'").append(name).append("',");
            }
            nameParam = names.toString();
            if (!StringUtils.isEmpty(nameParam)) {
                nameParam = nameParam.substring(0, nameParam.length() - 1);
                sql.WHERE("NAME in (" + nameParam + ")");
            }
        }
        return sql.toString();
    }

    public String selectByIndexes(MetaInfoEditionDO metaInfoDO) {
        SQL sql = new SQL();
        sql.SELECT("count(*)");
        sql.FROM("META_INFO_USER");
        sql.WHERE(new String[]{"MODULENAME=#{moduleName}", "USERNAME=#{userName}", "NAME = #{name}", "METATYPE = #{metaType}"});
        return sql.toString();
    }

    public String getMetaListFilter(MetaInfoFilterDTO info) {
        SQL sql = new SQL();
        sql.SELECT("info.ID,info.VERSIONNO,info.ROWVERSION,info.NAME,info.TITLE,info.MODULENAME,info.METATYPE,info.GROUPNAME,info.MODELNAME,info.UNIQUECODE,design.DESIGNDATA");
        sql.FROM("META_INFO info");
        sql.LEFT_OUTER_JOIN("META_DESIGN design ON info.ID = design.ID");
        if (!StringUtils.isEmpty(info.getMetaType())) {
            sql.WHERE("info.METATYPE=#{metaType}");
        }
        if (!StringUtils.isEmpty(info.getModelName())) {
            sql.WHERE("info.MODELNAME=#{modelName}");
        }
        if (!StringUtils.isEmpty(info.getModuleName())) {
            sql.WHERE("info.MODULENAME=#{moduleName}");
        }
        if (info.getDefineCodes() != null && info.getDefineCodes().size() != 0) {
            sql.WHERE("info.UNIQUECODE in (" + StringUtils.collectionToDelimitedString(info.getDefineCodes(), ",", "'", "'") + ")");
        }
        return sql.toString();
    }

    public String getNotIncludedDesignMetaList(MetaInfoFilterDTO info) {
        SQL sql = new SQL();
        sql.SELECT("info.ID,info.VERSIONNO,info.ROWVERSION,info.NAME,info.TITLE,info.MODULENAME,info.METATYPE,info.GROUPNAME,info.MODELNAME,info.UNIQUECODE");
        sql.FROM("META_INFO info");
        if (!StringUtils.isEmpty(info.getMetaType())) {
            sql.WHERE("info.METATYPE=#{metaType}");
        }
        if (!StringUtils.isEmpty(info.getModelName())) {
            sql.WHERE("info.MODELNAME=#{modelName}");
        }
        if (!StringUtils.isEmpty(info.getModuleName())) {
            sql.WHERE("info.MODULENAME=#{moduleName}");
        }
        if (info.getDefineCodes() != null && info.getDefineCodes().size() != 0) {
            sql.WHERE("info.UNIQUECODE in (" + StringUtils.collectionToDelimitedString(info.getDefineCodes(), ",", "'", "'") + ")");
        }
        return sql.toString();
    }

    public String getMetaInfo(MetaInfoFilterDTO info) {
        SQL sql = new SQL();
        sql.SELECT("info.ID,info.VERSIONNO,info.ROWVERSION,info.NAME,info.TITLE,info.MODULENAME,info.METATYPE,info.GROUPNAME,info.MODELNAME,info.UNIQUECODE,design.DESIGNDATA");
        sql.FROM("META_INFO info");
        sql.LEFT_OUTER_JOIN("META_DESIGN design ON info.ID = design.ID");
        if (!StringUtils.isEmpty(info.getMetaType())) {
            sql.WHERE("info.METATYPE=#{metaType}");
        }
        if (!StringUtils.isEmpty(info.getName())) {
            sql.WHERE("info.NAME=#{name}");
        }
        if (!StringUtils.isEmpty(info.getModuleName())) {
            sql.WHERE("info.MODULENAME=#{moduleName}");
        }
        if (!StringUtils.isEmpty(info.getUniqueCode())) {
            sql.WHERE("info.UNIQUECODE=#{uniqueCode}");
        }
        return sql.toString();
    }

    public String findMaxVersion(MetaInfoDO infoDO) {
        SQL sql = new SQL();
        sql.SELECT("MAX(VERSIONNO)");
        sql.FROM("META_INFO_VERSION info");
        if (StringUtils.hasText(infoDO.getUniqueCode())) {
            sql.WHERE("info.UNIQUECODE=#{uniqueCode}");
        }
        return sql.toString();
    }
}

