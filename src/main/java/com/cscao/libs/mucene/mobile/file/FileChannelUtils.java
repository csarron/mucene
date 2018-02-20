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


import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;

public class FileChannelUtils {
  public static FileChannel open(Path path, OpenOption... options) throws IOException {
    List<OpenOption> optionList = Arrays.asList(options);

    if (optionList.size() == 1 && optionList.contains(StandardOpenOption.READ)) {
      RandomAccessFile raf = new RandomAccessFile(path.toFile(), "r");
      return raf.getChannel();
    } else if (optionList.contains(StandardOpenOption.WRITE)) {
      if (Files.notExists(path) && optionList.contains(StandardOpenOption.CREATE)) {
        Files.createFile(path);
      }

      RandomAccessFile raf = new RandomAccessFile(path.toFile(), "rw");
      return raf.getChannel();
    }


    throw new IOException("Unknown options: " + Arrays.toString(options));
  }
}
