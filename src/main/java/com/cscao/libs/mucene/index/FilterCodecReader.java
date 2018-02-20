/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cscao.libs.mucene.index;


import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import com.cscao.libs.mucene.codecs.DocValuesProducer;
import com.cscao.libs.mucene.codecs.FieldsProducer;
import com.cscao.libs.mucene.codecs.NormsProducer;
import com.cscao.libs.mucene.codecs.PointsReader;
import com.cscao.libs.mucene.codecs.StoredFieldsReader;
import com.cscao.libs.mucene.codecs.TermVectorsReader;
import com.cscao.libs.mucene.util.Accountable;
import com.cscao.libs.mucene.util.Bits;

/** 
 * A <code>FilterCodecReader</code> contains another CodecReader, which it
 * uses as its basic source of data, possibly transforming the data along the
 * way or providing additional functionality.
 * <p><b>NOTE</b>: If this {@link FilterCodecReader} does not change the
 * content the contained reader, you could consider delegating calls to
 * {@link #getCoreCacheHelper()} and {@link #getReaderCacheHelper()}.
 */
public abstract class FilterCodecReader extends CodecReader {
  /** 
   * The underlying CodecReader instance. 
   */
  protected final CodecReader in;
  
  /**
   * Creates a new FilterCodecReader.
   * @param in the underlying CodecReader instance.
   */
  public FilterCodecReader(CodecReader in) {
    this.in = Objects.requireNonNull(in);
  }

  @Override
  public StoredFieldsReader getFieldsReader() {
    return in.getFieldsReader();
  }

  @Override
  public TermVectorsReader getTermVectorsReader() {
    return in.getTermVectorsReader();
  }

  @Override
  public NormsProducer getNormsReader() {
    return in.getNormsReader();
  }

  @Override
  public DocValuesProducer getDocValuesReader() {
    return in.getDocValuesReader();
  }

  @Override
  public FieldsProducer getPostingsReader() {
    return in.getPostingsReader();
  }

  @Override
  public Bits getLiveDocs() {
    return in.getLiveDocs();
  }

  @Override
  public FieldInfos getFieldInfos() {
    return in.getFieldInfos();
  }

  @Override
  public PointsReader getPointsReader() {
    return in.getPointsReader();
  }

  @Override
  public int numDocs() {
    return in.numDocs();
  }

  @Override
  public int maxDoc() {
    return in.maxDoc();
  }

  @Override
  public LeafMetaData getMetaData() {
    return in.getMetaData();
  }

  @Override
  protected void doClose() throws IOException {
    in.doClose();
  }

  @Override
  public long ramBytesUsed() {
    return in.ramBytesUsed();
  }

  @Override
  public Collection<Accountable> getChildResources() {
    return in.getChildResources();
  }

  @Override
  public void checkIntegrity() throws IOException {
    in.checkIntegrity();
  }

}
