/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.dataanalyze.vo;

public class OrgStateVO {
    private boolean orgRes;
    private boolean fmlRes;
    private boolean errorRes;
    private boolean legalRes;
    private static final int i_res = 1;
    private static final int i_fml = 2;
    private static final int i_error = 4;
    private static final int i_error_legal = 8;

    public OrgStateVO() {
    }

    public OrgStateVO(String res) {
        int ires = Integer.parseInt(res);
        this.orgRes = (ires & 1) != 0;
        this.fmlRes = (ires & 2) != 0;
        this.errorRes = (ires & 4) != 0;
        this.legalRes = (ires & 8) != 0;
    }

    public String getRes() {
        int ires = 0;
        if (this.orgRes) {
            ires |= 1;
        }
        if (this.fmlRes) {
            ires |= 2;
        }
        if (this.errorRes) {
            ires |= 4;
        }
        if (this.legalRes) {
            ires |= 8;
        }
        return String.valueOf(ires);
    }

    public boolean isFmlRes() {
        return this.fmlRes;
    }

    public void setFmlRes(boolean fmlRes) {
        this.fmlRes = fmlRes;
    }

    public boolean isErrorRes() {
        return this.errorRes;
    }

    public void setErrorRes(boolean errorRes) {
        this.errorRes = errorRes;
    }

    public boolean isOrgRes() {
        return this.orgRes;
    }

    public void setOrgRes(boolean orgRes) {
        this.orgRes = orgRes;
    }

    public boolean isLegalRes() {
        return this.legalRes;
    }

    public void setLegalRes(boolean legalRes) {
        this.legalRes = legalRes;
    }

    public static void main(String[] args) {
        System.out.println("i_res=1");
        System.out.println("i_fml=2");
        System.out.println("i_error=4");
        System.out.println("-----------------\u5141\u8bb8\u586b\u5199\u8bf4\u660e--------------------------");
        OrgStateVO s = new OrgStateVO();
        s.setFmlRes(false);
        s.setErrorRes(false);
        s.setLegalRes(false);
        s.setOrgRes(false);
        System.out.println(Integer.toBinaryString(Integer.valueOf(s.getRes())) + "\u5ba1\u6838\u5931\u8d25\uff0c\u6ca1\u6709\u8bf4\u660e\uff0c\u4e0d\u5408\u89c4\uff0c\u7ed3\u679c\u5931\u8d25\uff1a" + s.getRes());
        s = new OrgStateVO();
        s.setFmlRes(false);
        s.setErrorRes(true);
        s.setLegalRes(false);
        s.setOrgRes(false);
        System.out.println(Integer.toBinaryString(Integer.valueOf(s.getRes())) + "\u5ba1\u6838\u5931\u8d25\uff0c\u5b58\u5728\u8bf4\u660e\uff0c\u4e0d\u5408\u89c4\uff0c\u7ed3\u679c\u5931\u8d25\uff1a" + s.getRes());
        s = new OrgStateVO();
        s.setFmlRes(false);
        s.setErrorRes(true);
        s.setLegalRes(true);
        s.setOrgRes(true);
        System.out.println(Integer.toBinaryString(Integer.valueOf(s.getRes())) + "\u5ba1\u6838\u5931\u8d25\uff0c\u5b58\u5728\u8bf4\u660e\uff0c\u5408\u89c4\uff0c\u7ed3\u679c\u6210\u529f\uff1a" + s.getRes());
        s = new OrgStateVO();
        s.setFmlRes(true);
        s.setOrgRes(true);
        System.out.println(Integer.toBinaryString(Integer.valueOf(s.getRes())) + "\u5ba1\u6838\u6210\u529f\uff0c\u6ca1\u6709\u8bf4\u660e\uff0c\u4e0d\u5408\u89c4\uff0c\u7ed3\u679c\u6210\u529f\uff1a" + s.getRes());
        System.out.println("-----------------\u5fc5\u987b\u5ba1\u6838\u901a\u8fc7--------------------------");
        s = new OrgStateVO();
        s.setFmlRes(false);
        s.setOrgRes(false);
        System.out.println(Integer.toBinaryString(Integer.valueOf(s.getRes())) + "\u5ba1\u6838\u5931\u8d25\uff0c\u7ed3\u679c\u5931\u8d25\uff1a" + s.getRes());
        s = new OrgStateVO();
        s.setFmlRes(true);
        s.setOrgRes(true);
        System.out.println(Integer.toBinaryString(Integer.valueOf(s.getRes())) + "\u5ba1\u6838\u6210\u529f\uff0c\u7ed3\u679c\u6210\u529f\uff1a" + s.getRes());
        System.out.println("-----------------\u7ed3\u679c\u5230\u5bf9\u8c61--------------------------");
        OrgStateVO x = new OrgStateVO("0");
        System.out.print(Integer.toBinaryString(Integer.valueOf(x.getRes())) + (x.isOrgRes() ? "\u7ed3\u679c\u901a\u8fc7" : "\u7ed3\u679c\u5931\u8d25"));
        System.out.print(x.isFmlRes() ? " \uff0c\u5ba1\u6838\u901a\u8fc7" : "\uff0c\u5ba1\u6838\u5931\u8d25");
        System.out.print(x.isErrorRes() ? "\uff0c\u5b58\u5728\u8bf4\u660e" : "\uff0c\u6ca1\u6709\u8bf4\u660e");
        System.out.println(x.isLegalRes() ? "\uff0c\u8bf4\u660e\u5408\u89c40" : "\uff0c\u8bf4\u660e\u4e0d\u5408\u89c40");
        x = new OrgStateVO("3");
        System.out.print(Integer.toBinaryString(Integer.valueOf(x.getRes())) + (x.isOrgRes() ? "\u7ed3\u679c\u901a\u8fc7" : "\u7ed3\u679c\u5931\u8d25"));
        System.out.print(x.isFmlRes() ? " \uff0c\u5ba1\u6838\u901a\u8fc7" : "\uff0c\u5ba1\u6838\u5931\u8d25");
        System.out.print(x.isErrorRes() ? "\uff0c\u5b58\u5728\u8bf4\u660e" : "\uff0c\u6ca1\u6709\u8bf4\u660e");
        System.out.println(x.isLegalRes() ? "\uff0c\u8bf4\u660e\u5408\u89c43" : "\uff0c\u8bf4\u660e\u4e0d\u5408\u89c43");
        x = new OrgStateVO("5");
        System.out.print(Integer.toBinaryString(Integer.valueOf(x.getRes())) + (x.isOrgRes() ? "\u7ed3\u679c\u901a\u8fc7" : "\u7ed3\u679c\u5931\u8d25"));
        System.out.print(x.isFmlRes() ? " \uff0c\u5ba1\u6838\u901a\u8fc7" : "\uff0c\u5ba1\u6838\u5931\u8d25");
        System.out.print(x.isErrorRes() ? "\uff0c\u5b58\u5728\u8bf4\u660e" : "\uff0c\u6ca1\u6709\u8bf4\u660e");
        System.out.println(x.isLegalRes() ? "\uff0c\u8bf4\u660e\u5408\u89c45" : "\uff0c\u8bf4\u660e\u4e0d\u5408\u89c45");
        x = new OrgStateVO("7");
        System.out.print(Integer.toBinaryString(Integer.valueOf(x.getRes())) + (x.isOrgRes() ? "\u7ed3\u679c\u901a\u8fc7" : "\u7ed3\u679c\u5931\u8d25"));
        System.out.print(x.isFmlRes() ? " \uff0c\u5ba1\u6838\u901a\u8fc7" : "\uff0c\u5ba1\u6838\u5931\u8d25");
        System.out.print(x.isErrorRes() ? "\uff0c\u5b58\u5728\u8bf4\u660e" : "\uff0c\u6ca1\u6709\u8bf4\u660e");
        System.out.println(x.isLegalRes() ? "\uff0c\u8bf4\u660e\u5408\u89c47" : "\uff0c\u8bf4\u660e\u4e0d\u5408\u89c47");
        x = new OrgStateVO("4");
        System.out.print(Integer.toBinaryString(Integer.valueOf(x.getRes())) + (x.isOrgRes() ? "\u7ed3\u679c\u901a\u8fc7" : "\u7ed3\u679c\u5931\u8d25"));
        System.out.print(x.isFmlRes() ? " \uff0c\u5ba1\u6838\u901a\u8fc7" : "\uff0c\u5ba1\u6838\u5931\u8d25");
        System.out.print(x.isErrorRes() ? "\uff0c\u5b58\u5728\u8bf4\u660e" : "\uff0c\u6ca1\u6709\u8bf4\u660e");
        System.out.println(x.isLegalRes() ? "\uff0c\u8bf4\u660e\u5408\u89c44" : "\uff0c\u8bf4\u660e\u4e0d\u5408\u89c44");
        x = new OrgStateVO("13");
        System.out.print(Integer.toBinaryString(Integer.valueOf(x.getRes())) + (x.isOrgRes() ? "\u7ed3\u679c\u901a\u8fc7" : "\u7ed3\u679c\u5931\u8d25"));
        System.out.print(x.isFmlRes() ? " \uff0c\u5ba1\u6838\u901a\u8fc7" : " \uff0c\u5ba1\u6838\u5931\u8d25");
        System.out.print(x.isErrorRes() ? "\uff0c\u5b58\u5728\u8bf4\u660e" : " \uff0c\u6ca1\u6709\u8bf4\u660e");
        System.out.print(x.isLegalRes() ? "\uff0c\u8bf4\u660e\u5408\u89c413" : " \uff0c\u8bf4\u660e\u4e0d\u5408\u89c413");
    }
}

