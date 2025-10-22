/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.jtable.params.base.DecimalLinkData;
import com.jiuqi.nr.jtable.params.base.EnumLink;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.ErrorLinkData;
import com.jiuqi.nr.jtable.params.base.FileLinkData;
import com.jiuqi.nr.jtable.params.base.FormulaLinkData;
import com.jiuqi.nr.jtable.params.base.IntgeterLinkData;
import com.jiuqi.nr.jtable.params.base.LinkData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApiModel(value="LinkSimpleData", description="\u94fe\u63a5\u5c5e\u6027")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class LinkSimpleData {
    private static final Logger logger = LoggerFactory.getLogger(LinkSimpleData.class);
    @ApiModelProperty(value="\u94fe\u63a5id", name="id")
    private String id;
    @ApiModelProperty(value="\u94fe\u63a5\u6240\u5728\u533a\u57dfkey", name="rKey")
    private String rKey;
    @ApiModelProperty(value="\u94fe\u63a5\u6240\u5728\u533a\u57df\u7c7b\u578b", name="rType")
    private int rType;
    @ApiModelProperty(value="\u94fe\u63a5\u552f\u4e00code", name="code")
    private String code;
    @ApiModelProperty(value="\u94fe\u63a5\u8868\u683c\u5217\u5750\u6807", name="c")
    private int c;
    @ApiModelProperty(value="\u94fe\u63a5\u8868\u683c\u6a2a\u5750\u6807", name="r")
    private int r;
    @ApiModelProperty(value="\u94fe\u63a5\u516c\u5f0f\u5217\u5750\u6807", name="mc")
    private int mc;
    @ApiModelProperty(value="\u94fe\u63a5\u516c\u5f0f\u6a2a\u5750\u6807", name="mr")
    private int mr;
    @ApiModelProperty(value="\u94fe\u63a5\u683c\u9ed8\u8ba4\u503c", name="dva")
    private String dva;
    @ApiModelProperty(value="\u94fe\u63a5\u683c\u5f0f\u5316\u5b57\u7b26\u4e32", name="format")
    private String format;
    @ApiModelProperty(value="\u94fe\u63a5\u81ea\u5b9a\u4e49\u683c\u5f0f\u5316\u5b57\u7b26\u4e32", name="cformat")
    private String cformat;
    @ApiModelProperty(value="\u94fe\u63a5\u5173\u8054\u4e3b\u4f53", name="ekey")
    private String ekey;
    @ApiModelProperty(value="\u94fe\u63a5\u5173\u8054\u4e3b\u4f53\u6807\u9898", name="etitle")
    private String etitle;
    @ApiModelProperty(value="\u94fe\u63a5\u5173\u8054\u6307\u6807key", name="zbid")
    private String zbid;
    @ApiModelProperty(value="\u94fe\u63a5\u5173\u8054\u6307\u6807code", name="zbcode")
    private String zbcode;
    @ApiModelProperty(value="\u94fe\u63a5\u5173\u8054\u6307\u6807title", name="zbtitle")
    private String zbtitle;
    @ApiModelProperty(value="\u94fe\u63a5\u5173\u8054\u6307\u6807\u63cf\u8ff0", name="zbdesc")
    private String zbdesc;
    @ApiModelProperty(value="\u94fe\u63a5\u5173\u8054\u6307\u6807type", name="zbtype")
    private int zbtype;
    @ApiModelProperty(value="\u94fe\u63a5\u5173\u8054\u6307\u6807\u6c47\u603b\u7c7b\u578b", name="zbgather")
    private int zbgather;
    @ApiModelProperty(value="\u662f\u5426\u4e3a\u8131\u654f\u6307\u6807", name="isDataMask")
    private boolean dataMask;
    @ApiModelProperty(value="\u94fe\u63a5\u5173\u8054\u6307\u6807\u662f\u5426\u53ef\u4e3a\u7a7a", name="nullable")
    private boolean nullable;
    @ApiModelProperty(value="\u94fe\u63a5\u7ed1\u5b9a\u516c\u5f0f", name="formula")
    private String formula;
    @ApiModelProperty(value="\u94fe\u63a5\u5173\u8054\u679a\u4e3e\u5b57\u5178\u5217\u5c55\u793a\u4f4d\u7f6emap", name="enPos")
    private Map<String, String> enPos;
    @ApiModelProperty(value="\u94fe\u63a5\u5173\u8054\u679a\u4e3e\u5b57\u5178title\u5217\u5c55\u793a\u4f4d\u7f6e", name="titleField")
    private String titleField;
    @ApiModelProperty(value="\u94fe\u63a5\u679a\u4e3e\u8054\u52a8\u5217\u8868", name="enumLinks")
    private EnumLink enumLinks;
    @ApiModelProperty(value="\u94fe\u63a5\u662f\u5426\u53ea\u8bfb", name="read")
    private boolean read = false;
    @ApiModelProperty(value="\u94fe\u63a5\u53ea\u8bfb\u539f\u56e0", name="readReasion")
    private String readReasion;
    @ApiModelProperty(value="\u5168\u8def\u5f84\u6307\u6807name", name="pname")
    private String pname;
    @ApiModelProperty(value="\u5173\u8054\u679a\u4e3e\u663e\u793a\u5b57\u6bb5\u5217\u8868", name="capnames")
    private List<String> capnames;
    @ApiModelProperty(value="\u5173\u8054\u679a\u4e3e\u4e0b\u62c9\u5b57\u6bb5\u5217\u8868", name="dropnames")
    private List<String> dropnames;
    @ApiModelProperty(value="\u5173\u8054\u679a\u4e3e\u4e3b\u4f53\u89c6\u56fe\u7684\u6811\u5f62\u7ed3\u6784\u5b57\u7b26\u4e32", name="treeStruct")
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private String treeStruct;
    @ApiModelProperty(value="\u5173\u8054\u679a\u4e3e\u4e3b\u4f53\u89c6\u56fe\u7684\u6811\u5f62\u6700\u5927\u6df1\u5ea6", name="maxDepth")
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private int maxDepth;
    private boolean codeMatch;
    @ApiModelProperty(value="\u6587\u4ef6\u7c7b\u578b\u5217\u8868", name="ftypes")
    private List<String> ftypes;
    @ApiModelProperty(value="\u6700\u5927\u6587\u4ef6\u5927\u5c0f", name="fsize")
    private float fsize;
    @ApiModelProperty(value="\u6700\u5c0f\u6587\u4ef6\u5927\u5c0f", name="minSize")
    private float fminSize;
    @ApiModelProperty(value="\u6587\u4ef6\u6700\u5927\u6570\u91cf", name="fnum")
    private int fnum;
    @ApiModelProperty(value="\u9644\u4ef6\u6a21\u677fgroupkey", name="ftkey")
    private String ftkey;
    @ApiModelProperty(value="\u94fe\u63a5\u9519\u8bef\u4fe1\u606f", name="errors")
    private String errors;
    @ApiModelProperty(value="\u6570\u503c\u53ca\u6574\u6570\u6307\u6807\u7684\u683c\u5f0f\u5316\u7c7b\u578b", name="formatType")
    private Integer formatType;

    public LinkSimpleData(LinkData link) {
        this.id = link.getKey();
        this.code = link.getUniqueCode();
        this.c = link.getCol();
        this.r = link.getRow();
        this.mc = link.getDataCol();
        this.mr = link.getDataRow();
        this.dva = link.getDefaultValue();
        this.rKey = link.getRegionKey();
        this.rType = link.getRegionType();
        if (link instanceof EnumLinkData) {
            EnumLinkData enumLink = (EnumLinkData)link;
            try {
                this.ekey = enumLink.getEntityKey();
                this.etitle = enumLink.getEntityTitle();
                this.enumLinks = enumLink.getEnumLink();
                this.pname = enumLink.getPname();
                if (enumLink.getCapnames() != null && !enumLink.getCapnames().isEmpty()) {
                    this.capnames = new ArrayList<String>();
                    this.capnames.addAll(enumLink.getCapnames());
                }
                if (enumLink.getDropnames() != null && !enumLink.getDropnames().isEmpty()) {
                    this.dropnames = new ArrayList<String>();
                    this.dropnames.addAll(enumLink.getDropnames());
                }
                this.enPos = enumLink.getEnumFieldPosMap();
                this.treeStruct = enumLink.getTreeStruct();
                this.maxDepth = enumLink.getMaxDepth();
                this.codeMatch = enumLink.isCodeMatch();
                this.titleField = enumLink.getTitleField();
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        this.format = link.getStyle();
        this.cformat = link.getCstyle();
        this.zbid = link.getZbid();
        this.zbcode = link.getZbcode();
        this.zbtitle = link.getZbtitle();
        this.zbdesc = link.getZbdesc();
        this.zbtype = link.getType();
        this.zbgather = link.getZbgather();
        this.nullable = link.isNullable();
        this.dataMask = link.isDataMask();
        if (link instanceof FileLinkData) {
            FileLinkData fileLink = (FileLinkData)link;
            this.ftypes = fileLink.getTypes();
            this.fnum = fileLink.getNum();
            this.fsize = fileLink.getSize();
            this.fminSize = fileLink.getMinSize();
            this.ftkey = fileLink.getTemplateKey();
        }
        if (link instanceof FormulaLinkData) {
            FormulaLinkData formulaLink = (FormulaLinkData)link;
            this.formula = formulaLink.getFormula();
        }
        if (link instanceof ErrorLinkData) {
            ErrorLinkData errorLink = (ErrorLinkData)link;
            this.errors = errorLink.getError();
        }
        if (link instanceof DecimalLinkData) {
            DecimalLinkData decimalLinkData = (DecimalLinkData)link;
            this.formatType = decimalLinkData.getFormatType();
        }
        if (link instanceof IntgeterLinkData) {
            IntgeterLinkData intgeterLinkData = (IntgeterLinkData)link;
            this.formatType = intgeterLinkData.getFormatType();
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getrKey() {
        return this.rKey;
    }

    public void setrKey(String rKey) {
        this.rKey = rKey;
    }

    public int getrType() {
        return this.rType;
    }

    public void setrType(int rType) {
        this.rType = rType;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getC() {
        return this.c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int getR() {
        return this.r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getMc() {
        return this.mc;
    }

    public void setMc(int mc) {
        this.mc = mc;
    }

    public int getMr() {
        return this.mr;
    }

    public void setMr(int mr) {
        this.mr = mr;
    }

    public String getDva() {
        return this.dva;
    }

    public void setDva(String dva) {
        this.dva = dva;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCformat() {
        return this.cformat;
    }

    public void setCformat(String cformat) {
        this.cformat = cformat;
    }

    public String getEkey() {
        return this.ekey;
    }

    public void setEkey(String ekey) {
        this.ekey = ekey;
    }

    public String getEtitle() {
        return this.etitle;
    }

    public void setEtitle(String etitle) {
        this.etitle = etitle;
    }

    public String getZbid() {
        return this.zbid;
    }

    public void setZbid(String zbid) {
        this.zbid = zbid;
    }

    public String getZbcode() {
        return this.zbcode;
    }

    public void setZbcode(String zbcode) {
        this.zbcode = zbcode;
    }

    public String getZbtitle() {
        return this.zbtitle;
    }

    public void setZbtitle(String zbtitle) {
        this.zbtitle = zbtitle;
    }

    public int getZbtype() {
        return this.zbtype;
    }

    public void setZbtype(int zbtype) {
        this.zbtype = zbtype;
    }

    public int getZbgather() {
        return this.zbgather;
    }

    public void setZbgather(int zbgather) {
        this.zbgather = zbgather;
    }

    public boolean isNullable() {
        return this.nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isDataMask() {
        return this.dataMask;
    }

    public void setDataMask(boolean dataMask) {
        this.dataMask = dataMask;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getPname() {
        return this.pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public List<String> getCapnames() {
        return this.capnames;
    }

    public void setCapnames(List<String> capnames) {
        this.capnames = capnames;
    }

    public List<String> getDropnames() {
        return this.dropnames;
    }

    public void setDropnames(List<String> dropnames) {
        this.dropnames = dropnames;
    }

    public String getTreeStruct() {
        return this.treeStruct;
    }

    public void setTreeStruct(String treeStruct) {
        this.treeStruct = treeStruct;
    }

    public int getMaxDepth() {
        return this.maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public String getTitleField() {
        return this.titleField;
    }

    public void setTitleField(String titleField) {
        this.titleField = titleField;
    }

    public List<String> getFtypes() {
        return this.ftypes;
    }

    public void setFtypes(List<String> ftypes) {
        this.ftypes = ftypes;
    }

    public float getFsize() {
        return this.fsize;
    }

    public void setFsize(float fsize) {
        this.fsize = fsize;
    }

    public float getFminSize() {
        return this.fminSize;
    }

    public void setFminSize(float fminSize) {
        this.fminSize = fminSize;
    }

    public int getFnum() {
        return this.fnum;
    }

    public void setFnum(int fnum) {
        this.fnum = fnum;
    }

    public String getErrors() {
        return this.errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public Map<String, String> getEnPos() {
        return this.enPos;
    }

    public void setEnPos(Map<String, String> enPos) {
        this.enPos = enPos;
    }

    public EnumLink getEnumLinks() {
        return this.enumLinks;
    }

    public void setEnumLinks(EnumLink enumLinks) {
        this.enumLinks = enumLinks;
    }

    public boolean isRead() {
        return this.read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getReadReasion() {
        return this.readReasion;
    }

    public void setReadReasion(String readReasion) {
        this.readReasion = readReasion;
    }

    public String getZbdesc() {
        return this.zbdesc;
    }

    public void setZbdesc(String zbdesc) {
        this.zbdesc = zbdesc;
    }

    public boolean isCodeMatch() {
        return this.codeMatch;
    }

    public void setCodeMatch(boolean codeMatch) {
        this.codeMatch = codeMatch;
    }

    public Integer getFormatType() {
        return this.formatType;
    }

    public void setFormatType(Integer formatType) {
        this.formatType = formatType;
    }

    public String getFtkey() {
        return this.ftkey;
    }

    public void setFtkey(String ftkey) {
        this.ftkey = ftkey;
    }
}

