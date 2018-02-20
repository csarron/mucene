package com.cscao.libs.mucene.mobile.file;

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.cscao.libs.mucene.mobile.file.attribute.BasicFileAttributes;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Files {
    public static Path createDirectories(Path dir) throws IOException {
        if (exists(dir)) {
            if (isDirectory(dir)) {
                return dir;
            } else {
                // Note: This does not throw FileAlreadyExistsException like Java 7's nio.
                throw new IOException("Path is not a directory: " + dir);
            }
        }

        boolean success = dir.toFile().mkdirs();
        if (success) {
            return dir;
        }

        throw new IOException("Failed creating directory: " + dir);
    }

    public static SeekableByteChannel newByteChannel(Path path, StandardOpenOption mustBeReadOnly) throws IOException {
        return FileChannelUtils.open(path, mustBeReadOnly);
    }

    public static SeekableByteChannel newByteChannel(Path path, Set<OpenOption> options) throws IOException {
        OpenOption[] opts = new OpenOption[options.size()];
        options.toArray(opts);
        return FileChannelUtils.open(path, opts);
    }

    public static Path createFile(Path path) throws IOException {
        if (!path.toFile().createNewFile()) {
            throw new IOException("File cannot be created: " + path);
        }
        return path;
    }

    public static long size(Path path) throws IOException {
        return path.toFile().length();
    }

    public static boolean exists(Path path) {
        return path.file.exists();
    }

    public static boolean notExists(Path path) {
        return !exists(path);
    }

    public static boolean isDirectory(Path path) {
        return path.file.isDirectory();
    }

    public static boolean isWritable(Path path) {
        return path.file.canWrite();
    }

    public static OutputStream newOutputStream(Path path, OpenOption options[]) throws IOException {
        int len = options.length;
        Set<OpenOption> opts = new HashSet<>(len + 3);
        if (len == 0) {
            opts.add(StandardOpenOption.CREATE);
            opts.add(StandardOpenOption.TRUNCATE_EXISTING);
        } else {
            for (OpenOption opt : options) {
                if (opt == StandardOpenOption.READ)
                    throw new IllegalArgumentException("READ not allowed");
                opts.add(opt);
            }
        }
        opts.add(StandardOpenOption.WRITE);
        return Channels.newOutputStream(newByteChannel(path, opts));
    }

    public static FileOutputStream newOutputStream(Path path) throws IOException {
        return new FileOutputStream(path.toFile());
    }

    public static FileInputStream newInputStream(Path path) throws IOException {
        return new FileInputStream(path.toFile());
    }

    public static BufferedReader newBufferedReader(Path path, Charset charset) throws IOException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile()), charset));
    }

    public static DirectoryStream<Path> newDirectoryStream(Path dir) throws IOException {
        if (!Files.isDirectory(dir)) {
            throw new IOException("Not a directory: " + dir);
        }

        List<Path> paths = new ArrayList<Path>();
        File[] files = dir.toFile().listFiles();
        for (File file : files) {
            paths.add(new Path(file));
        }
        return new DirectoryStream.SimpleDirectoryStream<Path>(paths);
    }

    public static boolean deleteIfExists(Path path) throws IOException {
        if (exists(path)) {
            if (!path.toFile().delete()) {
                throw new IOException("Could not delete path: " + path);
            }
            return true;
        } else {
            return false;
        }
    }

    public static Path createTempFile(Path dir, String prefix, String suffix) throws IOException {
        return new Path(File.createTempFile(prefix, suffix, dir.toFile()));
    }

    public static Path copy(Path source, Path target, StandardCopyOption... ignored) throws IOException {
        if (isDirectory(source)) {
            throw new UnsupportedOperationException("Directory copy not supported in this implementation");
        }

        FileChannel srcChannel = null;
        FileChannel dstChannnel = null;
        try {
            srcChannel = newInputStream(source).getChannel();
            dstChannnel = newOutputStream(target).getChannel();

            long alreadyRead = 0;
            long size = srcChannel.size();
            while (alreadyRead < size) {
                alreadyRead += dstChannnel.transferFrom(srcChannel, alreadyRead, size - alreadyRead);
            }
        } finally {
            srcChannel.close();
            dstChannnel.close();
        }

        return target;
    }

    public static void delete(Path path) throws IOException {
        path.toFile().delete();
    }

    public static Path move(Path source, Path target, StandardCopyOption... ignored) throws IOException {
        if (source.toFile().renameTo(target.toFile())) {
            return target;
        }

        throw new IOException("Move from " + source + " to " + target + " failed");
    }

    public static Path walkFileTree(Path start, FileVisitor<? super Path> visitor) throws IOException {
        if (Files.isDirectory(start)) {
            visitor.preVisitDirectory(start, null);
            for (File child : start.toFile().listFiles()) {
                walkFileTree(new Path(child), visitor);
            }
            visitor.postVisitDirectory(start, null);
        } else {
            visitor.visitFile(start, null);
        }
        return start;
    }

    public static Path createTempDirectory(String prefix) throws IOException {
        File tmpFile = File.createTempFile(prefix, "");
        tmpFile.delete();
        tmpFile.mkdir();
        return new Path(tmpFile);
    }

    public static Path createTempDirectory(Path path, String prefix) throws IOException {
        File tmpFile = File.createTempFile(prefix, "", path.toFile());
        tmpFile.delete();
        tmpFile.mkdir();
        return new Path(tmpFile);
    }

    public static BasicFileAttributes readAttributes(Path path, Class<?> clz) throws NoSuchFileException {
        if (exists(path)) {
            return new BasicFileAttributes(path.toFile());
        }

        throw new NoSuchFileException();
    }
}
