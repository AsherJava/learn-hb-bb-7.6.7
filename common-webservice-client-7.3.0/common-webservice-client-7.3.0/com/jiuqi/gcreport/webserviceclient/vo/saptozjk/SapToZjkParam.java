/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.annotation.XmlAccessType
 *  javax.xml.bind.annotation.XmlAccessorType
 *  javax.xml.bind.annotation.XmlElement
 *  javax.xml.bind.annotation.XmlRootElement
 *  javax.xml.bind.annotation.XmlType
 */
package com.jiuqi.gcreport.webserviceclient.vo.saptozjk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={})
@XmlRootElement(name="SAP_TO_ZJK_DATA")
public class SapToZjkParam {
    @XmlElement(name="HEADER", required=true)
    protected HEADER header;
    @XmlElement(name="QUERYJSON", required=true)
    protected String queryJson;

    public HEADER getHeader() {
        return this.header;
    }

    public void setHeader(HEADER header) {
        this.header = header;
    }

    public String getQueryJson() {
        return this.queryJson;
    }

    public void setQueryJson(String queryJson) {
        this.queryJson = queryJson;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={""})
    public static class HEADER {
        @XmlElement(name="MSGTY")
        protected String msgType;
        @XmlElement(name="MSG")
        protected String msg;
        @XmlElement(name="ID")
        protected String id;
        @XmlElement(name="ROW")
        protected Integer row;
        @XmlElement(name="ROW_SUM")
        protected Integer rowSum;
        @XmlElement(name="PACKAGE")
        protected Integer packageNum;
        @XmlElement(name="PACKAGE_SUM")
        protected Integer packageSum;
        @XmlElement(name="TYPE")
        protected String type;

        public String getMsgType() {
            return this.msgType;
        }

        public void setMsgType(String msgType) {
            this.msgType = msgType;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getRowSum() {
            return this.rowSum;
        }

        public void setRowSum(Integer rowSum) {
            this.rowSum = rowSum;
        }

        public Integer getPackageNum() {
            return this.packageNum;
        }

        public void setPackageNum(Integer packageNum) {
            this.packageNum = packageNum;
        }

        public Integer getPackageSum() {
            return this.packageSum;
        }

        public void setPackageSum(Integer packageSum) {
            this.packageSum = packageSum;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getRow() {
            return this.row;
        }

        public void setRow(Integer row) {
            this.row = row;
        }
    }
}

