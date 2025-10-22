/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nvwa.definition.interval.anno.DBAnno$DBField
 *  com.jiuqi.nvwa.definition.interval.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.query.dataset.defines;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.query.dataset.defines.DataSetConst;
import com.jiuqi.nr.query.dataset.deserializer.DataSetDefineDeserializer;
import com.jiuqi.nr.query.dataset.serializer.DataSetSerializer;
import com.jiuqi.nvwa.definition.interval.anno.DBAnno;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import org.springframework.util.Assert;

@DBAnno.DBTable(dbTable="SYS_DATASET")
@JsonSerialize(using=DataSetSerializer.class)
@JsonDeserialize(using=DataSetDefineDeserializer.class)
public class DataSetDefine {
    public static final String DATASETDEFINE_ID = "id";
    public static final String DATASETDEFINE_NAME = "name";
    public static final String DATASETDEFINE_TITLE = "title";
    public static final String DATASETDEFINE_PARENT = "parent";
    public static final String DATASETDEFINE_TYPE = "type";
    public static final String DATASETDEFINE_ORDER = "order";
    public static final String DATASETDEFINE_MODEL = "model";
    public static final String DATASETDEFINE_UPDATETIME = "updatetime";
    public static final String DATASETDEFINE_CREATOR = "creator";
    @DBAnno.DBField(dbField="DS_ID", dbType=String.class, isPk=true)
    private String id;
    @DBAnno.DBField(dbField="DS_NAME", dbType=String.class, isPk=false)
    private String name;
    @DBAnno.DBField(dbField="DS_TITLE", dbType=String.class, isPk=false)
    private String title;
    @DBAnno.DBField(dbField="DS_PARENT", dbType=String.class, isPk=false)
    private String parent;
    @DBAnno.DBField(dbField="DS_TYPE", dbType=String.class, isPk=false)
    private String type;
    @DBAnno.DBField(dbField="DS_ORDER", dbType=String.class, isPk=false, isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="DS_MODEL", dbType=Clob.class, appType=String.class)
    private String model;
    @DBAnno.DBField(dbField="UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updatetime;
    @DBAnno.DBField(dbField="DS_CREATOR", dbType=String.class, isPk=false)
    private String creator = DataSetConst.getCreator();

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setModel(Clob model) {
        String clobString;
        this.model = clobString = this.ClobToString(model);
    }

    /*
     * Exception decompiling
     */
    private String ClobToString(Clob clob) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public void setProperty(String key, Object value) {
        Assert.notNull((Object)key, "'key' must not be null");
        switch (key.toLowerCase()) {
            case "ds_id": {
                this.setId(String.valueOf(value));
                break;
            }
            case "ds_name": {
                this.setName(String.valueOf(value));
                break;
            }
            case "ds_title": {
                this.setTitle(String.valueOf(value));
                break;
            }
            case "ds_parent": {
                this.setParent(String.valueOf(value));
                break;
            }
            case "ds_type": {
                this.setType(String.valueOf(value));
                break;
            }
            case "ds_order": {
                this.setOrder(String.valueOf(value));
                break;
            }
            case "updatetime": {
                Timestamp timestamp = (Timestamp)value;
                this.setUpdatetime(new Date(timestamp.getTime()));
                break;
            }
            default: {
                throw new RuntimeException("No such field '" + key + "' in '" + this.getClass() + "'");
            }
        }
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}

