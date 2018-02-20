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

public enum StandardOpenOption implements OpenOption {
  /**
   * Open for read access.
   */
  READ,

  /**
   * Open for write access.
   */
  WRITE,

  /**
   * If the file is opened for {@link #WRITE} access then bytes will be written
   * to the end of the file rather than the beginning.
   *
   * <p> If the file is opened for write access by other programs, then it
   * is file system specific if writing to the end of the file is atomic.
   */
  APPEND,

  /**
   * If the file already exists and it is opened for {@link #WRITE}
   * access, then its length is truncated to 0. This option is ignored
   * if the file is opened only for {@link #READ} access.
   */
  TRUNCATE_EXISTING,

  /**
   * Create a new file if it does not exist.
   * This option is ignored if the {@link #CREATE_NEW} option is also set.
   * The check for the existence of the file and the creation of the file
   * if it does not exist is atomic with respect to other file system
   * operations.
   */
  CREATE,

  /**
   * Create a new file, failing if the file already exists.
   * The check for the existence of the file and the creation of the file
   * if it does not exist is atomic with respect to other file system
   * operations.
   */
  CREATE_NEW,

  DELETE_ON_CLOSE,

  /**
   * Sparse file. When used with the {@link #CREATE_NEW} option then this
   * option provides a <em>hint</em> that the new file will be sparse. The
   * option is ignored when the file system does not support the creation of
   * sparse files.
   */
  SPARSE,

  /**
   * Requires that every update to the file's content or metadata be written
   * synchronously to the underlying storage device.
   *
   * @see <a href="package-summary.html#integrity">Synchronized I/O file integrity</a>
   */
  SYNC,

  /**
   * Requires that every update to the file's content be written
   * synchronously to the underlying storage device.
   *
   * @see <a href="package-summary.html#integrity">Synchronized I/O file integrity</a>
   */
  DSYNC
}
