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

import java.io.File;
import java.io.IOException;

public class Path {
  File file;

  Path(String path) {
    file = new File(path);
  }

  Path(File file) {
    this.file = file;
  }

  public File toFile() {
    return file;
  }

  public Path toRealPath() throws IOException {
    // We assume case sensitivity and other representations don't matter.
    return this;
  }

  public Path getFileName() {
    return new Path(file.getName());
  }

  public String toString() {
    return file.toString();
  }

  public Path resolve(String other) {
    if (other.isEmpty()) {
      return this;
    }

    File otherFile = new File(other);
    if (otherFile.isAbsolute()) {
      return new Path(otherFile);
    }

    return new Path(new File(this.file, other));
  }

  public boolean isAbsolute() {
    return file.isAbsolute();
  }

  public Path toAbsolutePath() {
    return new Path(file.getAbsoluteFile());
  }

  public Path getParent() {
    return new Path(file.getParent());
  }
}
