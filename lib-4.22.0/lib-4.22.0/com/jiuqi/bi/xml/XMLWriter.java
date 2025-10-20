/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.xml;

import com.jiuqi.bi.xml.XMLException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

public class XMLWriter {
    private String version = "1.0";
    private String encoding = "GBK";
    private String dateFormat = "yyyy-MM-dd";
    private String blank = "";
    private boolean format = true;
    private boolean flag = false;
    private boolean sign = false;
    private boolean beginLock = false;
    private boolean attributeLock = true;
    private boolean valueLock = true;
    private OutputStream out = null;
    private Stack<String> tags = new Stack();
    private int bufferSize = 4096;
    private StringBuffer stringBuffer = new StringBuffer();

    public XMLWriter(OutputStream out) throws XMLException {
        this.out = out;
        this.init();
    }

    public XMLWriter(OutputStream out, String encoding) throws XMLException {
        this.out = out;
        this.encoding = encoding;
        this.init();
    }

    public XMLWriter(OutputStream out, String encoding, String version) throws XMLException {
        this.out = out;
        this.encoding = encoding;
        this.version = version;
        this.init();
    }

    private void init() throws XMLException {
        this.write("<?xml version=\"");
        this.write(this.version);
        this.write("\" encoding=\"");
        this.write(this.encoding);
        this.write("\"?");
    }

    public void beginNode(String name) throws XMLException {
        if (this.beginLock) {
            throw new XMLException("\u5f00\u59cb\u8282\u70b9<" + name + ">\u7f6e\u4e8e\u975e\u6cd5\u4f4d\u7f6e");
        }
        if (this.format) {
            this.write(">\n");
            this.write(this.blank);
            this.write("<");
            this.write(name);
            this.blank = this.blank + "  ";
        } else {
            this.write("><");
            this.write(name);
        }
        this.valueLock = false;
        this.attributeLock = false;
        this.flag = true;
        this.sign = false;
        this.tags.push(name);
    }

    public void setAttribute(String name) throws XMLException {
        this.setAttribute(name, "");
    }

    public void setAttribute(String name, int value) throws XMLException {
        this.setAttribute(name, String.valueOf(value));
    }

    public void setAttribute(String name, byte value) throws XMLException {
        this.setAttribute(name, String.valueOf(value));
    }

    public void setAttribute(String name, short value) throws XMLException {
        this.setAttribute(name, String.valueOf(value));
    }

    public void setAttribute(String name, long value) throws XMLException {
        this.setAttribute(name, String.valueOf(value));
    }

    public void setAttribute(String name, float value) throws XMLException {
        this.setAttribute(name, String.valueOf(value));
    }

    public void setAttribute(String name, double value) throws XMLException {
        this.setAttribute(name, String.valueOf(value));
    }

    public void setAttribute(String name, char value) throws XMLException {
        this.setAttribute(name, String.valueOf(value));
    }

    public void setAttribute(String name, boolean value) throws XMLException {
        this.setAttribute(name, String.valueOf(value));
    }

    public void setAttribute(String name, Date value) throws XMLException {
        SimpleDateFormat df = new SimpleDateFormat(this.dateFormat);
        this.setAttribute(name, df.format(value));
    }

    public void setAttribute(String name, Object value) throws XMLException {
        this.setAttribute(name, value.toString());
    }

    public void setAttribute(String name, String value) throws XMLException {
        if (this.attributeLock) {
            throw new XMLException("\u8282\u70b9\u5c5e\u6027 " + name + "=\"" + value + "\" \u7f6e\u4e8e\u975e\u6cd5\u4f4d\u7f6e");
        }
        this.write(" ");
        this.write(name);
        this.write("=\"");
        this.writeXml(value);
        this.write("\"");
        this.beginLock = false;
        this.valueLock = false;
    }

    public void setValue() throws XMLException {
        this.setValue("");
    }

    public void setValue(int value) throws XMLException {
        this.setValue(String.valueOf(value));
    }

    public void setValue(byte value) throws XMLException {
        this.setValue(String.valueOf(value));
    }

    public void setValue(short value) throws XMLException {
        this.setValue(String.valueOf(value));
    }

    public void setValue(long value) throws XMLException {
        this.setValue(String.valueOf(value));
    }

    public void setValue(float value) throws XMLException {
        this.setValue(String.valueOf(value));
    }

    public void setValue(double value) throws XMLException {
        this.setValue(String.valueOf(value));
    }

    public void setValue(char value) throws XMLException {
        this.setValue(String.valueOf(value));
    }

    public void setValue(boolean value) throws XMLException {
        this.setValue(String.valueOf(value));
    }

    public void setValue(Date value) throws XMLException {
        SimpleDateFormat df = new SimpleDateFormat(this.dateFormat);
        this.setValue(df.format(value));
    }

    public void setValue(Object value) throws XMLException {
        this.setValue(value.toString());
    }

    public void setValue(String value) throws XMLException {
        if (this.valueLock) {
            throw new XMLException("\u8282\u70b9\u5185\u5bb9 " + value + " \u7f6e\u4e8e\u975e\u6cd5\u4f4d\u7f6e");
        }
        this.write(">");
        this.writeXml(value);
        this.beginLock = true;
        this.valueLock = true;
        this.attributeLock = true;
        this.sign = true;
    }

    public void setContent(String content) throws XMLException {
        if (this.valueLock) {
            throw new XMLException("\u8282\u70b9\u5185\u5bb9 " + content + " \u7f6e\u4e8e\u975e\u6cd5\u4f4d\u7f6e");
        }
        this.write(">");
        this.write("<![CDATA[");
        this.write(content);
        this.write("]]>");
        this.beginLock = true;
        this.valueLock = true;
        this.attributeLock = true;
        this.sign = true;
    }

    public void writeContentNode(String name, String content) throws XMLException {
        this.beginNode(name);
        this.setContent(content);
        this.endNode();
    }

    public void endNode() throws XMLException {
        if (this.tags.isEmpty()) {
            throw new XMLException("\u6b64\u7ed3\u675f\u8282\u70b9\u4f4d\u7f6e\u4e0d\u5408\u6cd5\uff0c\u5176\u5bf9\u5e94\u5339\u914d\u7684\u5f00\u59cb\u7ed3\u70b9\u672a\u521b\u5efa");
        }
        if (this.format) {
            this.blank = this.blank.substring(0, this.blank.length() - 2);
        }
        String name = this.tags.pop();
        if (this.flag) {
            if (this.sign) {
                this.write("</");
                this.write(name);
            } else {
                this.write("/");
            }
        } else {
            this.write(">\n");
            this.write(this.blank);
            this.write("</");
            this.write(name);
        }
        if (this.tags.empty()) {
            this.writeEnd(">");
        }
        this.flag = false;
        this.valueLock = true;
        this.beginLock = false;
        this.attributeLock = true;
    }

    public void writeNode(String name) throws XMLException {
        this.beginNode(name);
        this.endNode();
    }

    public void writeNode(String name, String value) throws XMLException {
        this.beginNode(name);
        this.setValue(value);
        this.endNode();
    }

    private void write(String str) throws XMLException {
        if (null == str) {
            throw new XMLException("\u4e0d\u5141\u8bb8\u5728XML\u4e2d\u5199\u5165NULL\u503c");
        }
        this.stringBuffer.append(str);
        if (this.stringBuffer.length() > this.bufferSize) {
            this.flush();
        }
    }

    private void writeXml(String str) throws XMLException {
        if (null == str) {
            throw new XMLException("\u4e0d\u5141\u8bb8\u5728XML\u4e2d\u5199\u5165NULL\u503c");
        }
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == '\"') {
                this.write("&quot;");
                continue;
            }
            if (str.charAt(i) == '\'') {
                this.write("&apos;");
                continue;
            }
            if (str.charAt(i) == '&') {
                this.write("&amp;");
                continue;
            }
            if (str.charAt(i) == '<') {
                this.write("&lt;");
                continue;
            }
            if (str.charAt(i) == '>') {
                this.write("&gt;");
                continue;
            }
            this.write(str.substring(i, i + 1));
        }
    }

    private void writeEnd(String str) throws XMLException {
        this.stringBuffer.append(str);
        this.flush();
    }

    public void flush() throws XMLException {
        try {
            this.out.write(this.stringBuffer.toString().getBytes(this.encoding));
            this.stringBuffer.delete(0, this.stringBuffer.length());
        }
        catch (IOException e) {
            throw new XMLException(e);
        }
    }

    public boolean isFormat() {
        return this.format;
    }

    public void setFormat(boolean format) {
        this.format = format;
    }

    public String getDateFormat() {
        return this.dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }
}

