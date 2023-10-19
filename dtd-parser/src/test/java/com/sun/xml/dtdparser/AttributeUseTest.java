/*
 * Copyright (c) 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package com.sun.xml.dtdparser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class AttributeUseTest {
    Map<String, Short> attributes = new HashMap<>();

    public AttributeUseTest() throws Exception {
        StringBuilder dtd = new StringBuilder();
        dtd.append("  <!ELEMENT root EMPTY>");
        dtd.append("  <!ATTLIST root\n");
        dtd.append("    normal CDATA \"default\"");
        dtd.append("    implied CDATA #IMPLIED");
        dtd.append("    required CDATA #REQUIRED");
        dtd.append("    fixed CDATA #FIXED \"default\"");
        dtd.append("  >");

        DTDEventListener handler = new DTDHandlerBase() {
            @Override
            public void attributeDecl(String element, String name, String type, String[] enums, short use,
                    String defaultValue) throws SAXException {
                attributes.put(name, use);
            }
        };
        InputSource input = new InputSource(new StringReader(dtd.toString()));
        DTDParser parser = new DTDParser();
        parser.setDtdHandler(handler);
        parser.parse(input);
    }

    @Test
    public void testNormalAttribute() {
        Assert.assertEquals(DTDEventListener.USE_NORMAL, attributes.get("normal").shortValue());
    }

    @Test
    public void testImpliedAttribute() {
        Assert.assertEquals(DTDEventListener.USE_IMPLIED, attributes.get("implied").shortValue());
    }

    @Test
    public void testRequiredAttribute() {
        Assert.assertEquals(DTDEventListener.USE_REQUIRED, attributes.get("required").shortValue());
    }

    @Test
    public void testFixedAttribute() {
        Assert.assertEquals(DTDEventListener.USE_FIXED, attributes.get("fixed").shortValue());
    }
}
