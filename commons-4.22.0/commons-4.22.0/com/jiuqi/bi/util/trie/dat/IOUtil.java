/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.trie.dat;

import com.jiuqi.bi.util.trie.dat.Services;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

class IOUtil {
    IOUtil() {
    }

    public static boolean saveObjectTo(Object o, String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(IOUtil.newOutputStream(path));){
            oos.writeObject(o);
        }
        catch (IOException e) {
            Services.logger.warning("\u5728\u4fdd\u5b58\u5bf9\u8c61" + o + "\u5230" + path + "\u65f6\u53d1\u751f\u5f02\u5e38" + e);
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Object readObjectFrom(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(IOUtil.newInputStream(path));){
            Object o;
            Object object = o = ois.readObject();
            return object;
        }
        catch (Exception e) {
            Services.logger.warning("\u5728\u4ece" + path + "\u8bfb\u53d6\u5bf9\u8c61\u65f6\u53d1\u751f\u5f02\u5e38" + e);
            return null;
        }
    }

    /*
     * Exception decompiling
     */
    public static byte[] readBytes(String path) {
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

    private static byte[] readBytesFromFileInputStream(FileChannel channel) throws IOException {
        int fileSize = (int)channel.size();
        ByteBuffer byteBuffer = ByteBuffer.allocate(fileSize);
        channel.read(byteBuffer);
        byteBuffer.flip();
        byte[] bytes = byteBuffer.array();
        byteBuffer.clear();
        return bytes;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String readTxt(String path) {
        if (path == null) {
            return null;
        }
        try (FileInputStream in = new FileInputStream(path);){
            byte[] fileContent = new byte[((InputStream)in).available()];
            IOUtil.readBytesFromOtherInputStream(in, fileContent);
            String string = new String(fileContent, Charset.forName("UTF-8"));
            return string;
        }
        catch (FileNotFoundException e) {
            Services.logger.warning("\u627e\u4e0d\u5230" + path + e);
            return null;
        }
        catch (IOException e) {
            Services.logger.warning("\u8bfb\u53d6" + path + "\u53d1\u751fIO\u5f02\u5e38" + e);
            return null;
        }
    }

    public static LinkedList<String[]> readCsv(String path) {
        LinkedList<String[]> resultList = new LinkedList<String[]>();
        LinkedList<String> lineList = IOUtil.readLineList(path);
        for (String line : lineList) {
            resultList.add(line.split(","));
        }
        return resultList;
    }

    public static boolean saveTxt(String path, String content) {
        try (FileOutputStream fos = new FileOutputStream(path);){
            FileChannel fc = fos.getChannel();
            fc.write(ByteBuffer.wrap(content.getBytes()));
        }
        catch (Exception e) {
            Services.logger.throwing("IOUtil", "saveTxt", e);
            Services.logger.warning("IOUtil saveTxt \u5230" + path + "\u5931\u8d25" + e.toString());
            return false;
        }
        return true;
    }

    public static boolean saveTxt(String path, StringBuilder content) {
        return IOUtil.saveTxt(path, content.toString());
    }

    public static <T> boolean saveCollectionToTxt(Collection<T> collection, String path) {
        StringBuilder sb = new StringBuilder();
        for (T o : collection) {
            sb.append(o);
            sb.append('\n');
        }
        return IOUtil.saveTxt(path, sb.toString());
    }

    public static String baseName(String path) {
        if (path == null || path.length() == 0) {
            return "";
        }
        path = path.replaceAll("[/\\\\]+", "/");
        int len = path.length();
        int upCount = 0;
        while (len > 0) {
            if (path.charAt(len - 1) == '/' && --len == 0) {
                return "";
            }
            int lastInd = path.lastIndexOf(47, len - 1);
            String fileName = path.substring(lastInd + 1, len);
            if (fileName.equals(".")) {
                --len;
                continue;
            }
            if (fileName.equals("..")) {
                len -= 2;
                ++upCount;
                continue;
            }
            if (upCount == 0) {
                return fileName;
            }
            --upCount;
            len -= fileName.length();
        }
        return "";
    }

    public static byte[] readBytesFromOtherInputStream(InputStream is) throws IOException {
        byte[] targetArray = new byte[is.available()];
        IOUtil.readBytesFromOtherInputStream(is, targetArray);
        is.close();
        return targetArray;
    }

    public static void readBytesFromOtherInputStream(InputStream is, byte[] targetArray) throws IOException {
        int len;
        for (int off = 0; (len = is.read(targetArray, off, targetArray.length - off)) != -1 && off < targetArray.length; off += len) {
        }
    }

    public static LinkedList<String> readLineList(String path) {
        LinkedList<String> result = new LinkedList<String>();
        String txt = IOUtil.readTxt(path);
        if (txt == null) {
            return result;
        }
        StringTokenizer tokenizer = new StringTokenizer(txt, "\n");
        while (tokenizer.hasMoreTokens()) {
            result.add(tokenizer.nextToken());
        }
        return result;
    }

    public static LinkedList<String> readLineListWithLessMemory(String path) {
        LinkedList<String> result = new LinkedList<String>();
        String line = null;
        try (BufferedReader bw = new BufferedReader(new InputStreamReader(IOUtil.newInputStream(path), "UTF-8"));){
            while ((line = bw.readLine()) != null) {
                result.add(line);
            }
        }
        catch (Exception e) {
            Services.logger.warning("\u52a0\u8f7d" + path + "\u5931\u8d25\uff0c" + e);
        }
        return result;
    }

    public static boolean saveMapToTxt(Map<Object, Object> map, String path) {
        return IOUtil.saveMapToTxt(map, path, "=");
    }

    public static boolean saveMapToTxt(Map<Object, Object> map, String path, String separator) {
        map = new TreeMap<Object, Object>(map);
        return IOUtil.saveEntrySetToTxt(map.entrySet(), path, separator);
    }

    public static boolean saveEntrySetToTxt(Set<Map.Entry<Object, Object>> entrySet, String path, String separator) {
        StringBuilder sbOut = new StringBuilder();
        for (Map.Entry<Object, Object> entry : entrySet) {
            sbOut.append(entry.getKey());
            sbOut.append(separator);
            sbOut.append(entry.getValue());
            sbOut.append('\n');
        }
        return IOUtil.saveTxt(path, sbOut.toString());
    }

    public static String dirname(String path) {
        int index = path.lastIndexOf(47);
        if (index == -1) {
            return path;
        }
        return path.substring(0, index + 1);
    }

    public static LineIterator readLine(String path) {
        return new LineIterator(path);
    }

    public static BufferedWriter newBufferedWriter(String path) throws IOException {
        return new BufferedWriter(new OutputStreamWriter(IOUtil.newOutputStream(path), "UTF-8"));
    }

    public static BufferedReader newBufferedReader(String path) throws IOException {
        return new BufferedReader(new InputStreamReader(IOUtil.newInputStream(path), "UTF-8"));
    }

    public static BufferedWriter newBufferedWriter(String path, boolean append) throws FileNotFoundException, UnsupportedEncodingException {
        return new BufferedWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(path, append), "UTF-8"));
    }

    public static InputStream newInputStream(String path) throws IOException {
        return new FileInputStream(path);
    }

    public static OutputStream newOutputStream(String path) throws IOException {
        return new FileOutputStream(path);
    }

    public static String getSuffix(String name, String delimiter) {
        return name.substring(name.lastIndexOf(delimiter) + 1);
    }

    public static void writeLine(BufferedWriter bw, String ... params) throws IOException {
        for (int i = 0; i < params.length - 1; ++i) {
            bw.write(params[i]);
            bw.write(9);
        }
        bw.write(params[params.length - 1]);
    }

    public static class LineIterator
    implements Iterator<String> {
        BufferedReader bw;
        String line;

        public LineIterator(String path) {
            try {
                this.bw = new BufferedReader(new InputStreamReader(IOUtil.newInputStream(path), "UTF-8"));
                this.line = this.bw.readLine();
            }
            catch (FileNotFoundException e) {
                Services.logger.warning("\u6587\u4ef6" + path + "\u4e0d\u5b58\u5728\uff0c\u63a5\u4e0b\u6765\u7684\u8c03\u7528\u4f1a\u8fd4\u56denull\n" + e.toString());
                this.bw = null;
            }
            catch (IOException e) {
                Services.logger.warning("\u5728\u8bfb\u53d6\u8fc7\u7a0b\u4e2d\u53d1\u751f\u9519\u8bef" + e.toString());
                this.bw = null;
            }
        }

        public void close() {
            if (this.bw == null) {
                return;
            }
            try {
                this.bw.close();
                this.bw = null;
            }
            catch (IOException e) {
                Services.logger.warning("\u5173\u95ed\u6587\u4ef6\u5931\u8d25" + e.toString());
            }
        }

        @Override
        public boolean hasNext() {
            if (this.bw == null) {
                return false;
            }
            if (this.line == null) {
                try {
                    this.bw.close();
                    this.bw = null;
                }
                catch (IOException e) {
                    Services.logger.warning("\u5173\u95ed\u6587\u4ef6\u5931\u8d25" + e.toString());
                }
                return false;
            }
            return true;
        }

        @Override
        public String next() {
            String preLine = this.line;
            try {
                if (this.bw != null) {
                    this.line = this.bw.readLine();
                    if (this.line == null && this.bw != null) {
                        try {
                            this.bw.close();
                            this.bw = null;
                        }
                        catch (IOException e) {
                            Services.logger.warning("\u5173\u95ed\u6587\u4ef6\u5931\u8d25" + e.toString());
                        }
                    }
                } else {
                    this.line = null;
                }
            }
            catch (IOException e) {
                Services.logger.warning("\u5728\u8bfb\u53d6\u8fc7\u7a0b\u4e2d\u53d1\u751f\u9519\u8bef" + e.toString());
            }
            return preLine;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("\u53ea\u8bfb\uff0c\u4e0d\u53ef\u5199\uff01");
        }
    }
}

