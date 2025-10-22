/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DiskDataUtils {
    private DiskDataUtils() {
    }

    public static Path path(String first, String ... more) {
        return Paths.get(first, more);
    }

    public static Path tempPath(String ... more) {
        return Paths.get(System.getProperty("java.io.tmpdir"), more);
    }

    public static String read(Path path) {
        if (Files.notExists(path, new LinkOption[0])) {
            return null;
        }
        try {
            return new String(Files.readAllBytes(path));
        }
        catch (IOException e) {
            throw new DiskDataIOException("\u78c1\u76d8\u6570\u636e\u8bfb\u53d6\u5931\u8d25", e);
        }
    }

    public static void write(Path path, String content) {
        Path directory = path.getParent();
        if (Files.notExists(directory, new LinkOption[0])) {
            try {
                Files.createDirectories(directory, new FileAttribute[0]);
            }
            catch (IOException e) {
                throw new DiskDataIOException("\u78c1\u76d8\u6570\u636e\u5199\u5165\u5931\u8d25", e);
            }
        }
        try {
            if (null != content) {
                Files.write(path, content.getBytes(), new OpenOption[0]);
            }
        }
        catch (IOException e) {
            throw new DiskDataIOException("\u78c1\u76d8\u6570\u636e\u5199\u5165\u5931\u8d25", e);
        }
        path.toFile().deleteOnExit();
        directory.toFile().deleteOnExit();
    }

    public static boolean exists(Path path) {
        return Files.exists(path, new LinkOption[0]);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Collection<Path> children(Path path) {
        if (Files.notExists(path, new LinkOption[0])) return Collections.emptySet();
        if (!Files.isDirectory(path, new LinkOption[0])) {
            return Collections.emptySet();
        }
        try (Stream<Path> walk = Files.walk(path, 1, new FileVisitOption[0]);){
            Collection collection = walk.collect(Collectors.toSet());
            return collection;
        }
        catch (IOException e) {
            throw new DiskDataIOException("\u78c1\u76d8\u6587\u4ef6\u67e5\u8be2\u5b50\u7ea7\u5931\u8d25", e);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int count(Path path) {
        if (Files.notExists(path, new LinkOption[0])) return 0;
        if (!Files.isDirectory(path, new LinkOption[0])) {
            return 0;
        }
        try (Stream<Path> children = Files.list(path);){
            int n = (int)children.count();
            return n;
        }
        catch (IOException e) {
            throw new DiskDataIOException("\u78c1\u76d8\u7f13\u5b58\u67e5\u8be2\u7f13\u5b58\u4e2a\u6570\u5931\u8d25", e);
        }
    }

    public static void delete(Path path) {
        if (Files.notExists(path, new LinkOption[0])) {
            return;
        }
        try (Stream<Path> walk = Files.walk(path, new FileVisitOption[0]);){
            walk.sorted((a, b) -> b.compareTo((Path)a)).forEach(child -> {
                try {
                    Files.deleteIfExists(child);
                }
                catch (IOException e) {
                    try {
                        Thread.sleep(1000L);
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                    try {
                        Files.deleteIfExists(child);
                    }
                    catch (IOException ie) {
                        throw new DiskDataIOException("\u78c1\u76d8\u6587\u4ef6\u5220\u9664\u5931\u8d25", ie);
                    }
                }
            });
        }
        catch (IOException e) {
            throw new DiskDataIOException("\u78c1\u76d8\u6587\u4ef6\u7edf\u8ba1\u5931\u8d25", e);
        }
    }

    public static void main(String[] args) {
        Thread[] threads;
        int j;
        int threadCount;
        int i;
        AtomicInteger time = new AtomicInteger(0);
        Runnable ssd = () -> {
            long start = System.currentTimeMillis();
            Path path = DiskDataUtils.tempPath("NR_FORMULA_JS", "a1661aa7-fd25-4cdd-be0b-04d19798b50c", "fbe1f49b-4bef-4995-a018-5e3e5930f348");
            String read = DiskDataUtils.read(path);
            long end = System.currentTimeMillis();
            time.addAndGet((int)(end - start));
        };
        Runnable hhd = () -> {
            long start = System.currentTimeMillis();
            Path path = DiskDataUtils.path("E:\\Temp\\NR_FORMULA_JS", "a1661aa7-fd25-4cdd-be0b-04d19798b50c", "fbe1f49b-4bef-4995-a018-5e3e5930f348");
            String read = DiskDataUtils.read(path);
            long end = System.currentTimeMillis();
            time.addAndGet((int)(end - start));
        };
        for (i = 0; i < 4; ++i) {
            time.set(0);
            threadCount = 0xA ^ i;
            for (j = 0; j < 10; ++j) {
                threads = new Thread[threadCount];
                for (int k = 0; k < threadCount; ++k) {
                    threads[j] = new Thread(hhd);
                }
                for (Thread thread : threads) {
                    thread.start();
                }
                for (Thread thread : threads) {
                    try {
                        thread.join();
                    }
                    catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            System.out.println("HHD" + threadCount + "\u5e76\u53d1\u4e0b\u7684\u5e73\u5747\u8bfb\u53d6\u8017\u65f6\uff1a" + time.get() / threadCount / 10 + "ms");
        }
        for (i = 0; i < 4; ++i) {
            time.set(0);
            threadCount = 0xA ^ i;
            for (j = 0; j < 10; ++j) {
                threads = new Thread[threadCount];
                for (int k = 0; k < threadCount; ++k) {
                    threads[j] = new Thread(ssd);
                }
                for (Thread thread : threads) {
                    thread.start();
                }
                for (Thread thread : threads) {
                    try {
                        thread.join();
                    }
                    catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            System.out.println("SSD" + threadCount + "\u5e76\u53d1\u4e0b\u7684\u5e73\u5747\u8bfb\u53d6\u8017\u65f6\uff1a" + time.get() / threadCount / 10 + "ms");
        }
    }

    public static class DiskDataIOException
    extends RuntimeException {
        public DiskDataIOException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

