/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.apache.mina.integration.beans;

import java.beans.PropertyEditor;
import java.util.Map;
import java.util.Properties;

/**
 * A {@link PropertyEditor} which converts a {@link String} into
 * a {@link Properties} and vice versa.
 *
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Revision: 601229 $, $Date: 2007-12-05 08:13:18 +0100 (mer, 05 déc 2007) $
 */
public class PropertiesEditor extends MapEditor {
    
    public PropertiesEditor() {
        super(String.class, String.class);
        setTrimText(false);
    }

    @Override
    protected Map<Object, Object> newMap() {
        return new Properties();
    }
}
