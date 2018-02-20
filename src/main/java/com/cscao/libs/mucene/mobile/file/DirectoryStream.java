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

import java.io.Closeable;
import java.util.Iterator;
import java.util.List;

public interface DirectoryStream<T> extends AutoCloseable, Closeable, Iterable<T> {
  static public class SimpleDirectoryStream<T> implements DirectoryStream<T> {
    List<T> paths;

    public SimpleDirectoryStream(List<T> paths) {
      this.paths = paths;
    }

    @Override
    public Iterator<T> iterator() {
      return paths.iterator();
    }

    @Override
    public void close() {
    }
  }
}
