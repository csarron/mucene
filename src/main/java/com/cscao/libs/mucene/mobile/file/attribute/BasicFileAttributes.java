package com.cscao.libs.mucene.mobile.file.attribute;

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

public class BasicFileAttributes {
  File file;

  public BasicFileAttributes(File file) {
    this.file = file;
  }

  BasicFileAttributes() {
    file = null;
  }

  /**
   * WARNING: This is NOT the actual creation time. This curretly works just because of how
   * SimpleFSLock works!!
   */
  public FileTime creationTime() {
    if (file != null) {
      return new FileTime(file.lastModified());
    }
    return new FileTime(0);
  }
}
