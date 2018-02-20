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

/**
 * Codecs API: API for customization of the encoding and structure of the index.
 * 
 * <p>
 *   The Codec API allows you to customise the way the following pieces of index information are stored:
 * <ul>
 *   <li>Postings lists - see {@link com.cscao.libs.mucene.codecs.PostingsFormat}</li>
 *   <li>DocValues - see {@link com.cscao.libs.mucene.codecs.DocValuesFormat}</li>
 *   <li>Stored fields - see {@link com.cscao.libs.mucene.codecs.StoredFieldsFormat}</li>
 *   <li>Term vectors - see {@link com.cscao.libs.mucene.codecs.TermVectorsFormat}</li>
 *   <li>Points - see {@link com.cscao.libs.mucene.codecs.PointsFormat}</li>
 *   <li>FieldInfos - see {@link com.cscao.libs.mucene.codecs.FieldInfosFormat}</li>
 *   <li>SegmentInfo - see {@link com.cscao.libs.mucene.codecs.SegmentInfoFormat}</li>
 *   <li>Norms - see {@link com.cscao.libs.mucene.codecs.NormsFormat}</li>
 *   <li>Live documents - see {@link com.cscao.libs.mucene.codecs.LiveDocsFormat}</li>
 * </ul>
 *  
 *   For some concrete implementations beyond Lucene's official index format, see
 *   the <a href="{@docRoot}/../codecs/overview-summary.html">Codecs module</a>.
 * 
 * <p>
 *   Codecs are identified by name through the Java Service Provider Interface.  To create your own codec, extend
 *   {@link com.cscao.libs.mucene.codecs.Codec} and pass the new codec's name to the super() constructor:
 * <pre class="prettyprint">
 * public class MyCodec extends Codec {
 * 
 *     public MyCodec() {
 *         super("MyCodecName");
 *     }
 * 
 *     ...
 * }
 * </pre>
 * You will need to register the Codec class so that the {@link java.util.ServiceLoader ServiceLoader} can find it, by including a
 * META-INF/services/com.cscao.libs.mucene.codecs.Codec file on your classpath that contains the package-qualified
 * name of your codec.
 * 
 * <p>
 *   If you just want to customise the {@link com.cscao.libs.mucene.codecs.PostingsFormat}, or use different postings
 *   formats for different fields, then you can register your custom postings format in the same way (in
 *   META-INF/services/com.cscao.libs.mucene.codecs.PostingsFormat), and then extend the default
 *   codec and override
 *   {@code com.cscao.libs.mucene.codecs.luceneMN.LuceneMNCodec#getPostingsFormatForField(String)} to return your custom
 *   postings format.
 * </p>
 * <p>
 *   Similarly, if you just want to customise the {@link com.cscao.libs.mucene.codecs.DocValuesFormat} per-field, have
 *   a look at {@code LuceneMNCodec.getDocValuesFormatForField(String)}.
 * </p>
 */
package com.cscao.libs.mucene.codecs;
