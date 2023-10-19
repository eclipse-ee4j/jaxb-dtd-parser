/*
 * Copyright (c) 1998, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.dtdparser;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * do-nothing implementation of DTDEventHandler.
 */
public class DTDHandlerBase implements DTDEventListener {

    /**
     * Constructs a DTDHandlerBase.
     */
    public DTDHandlerBase() {}

    @Override
    public void processingInstruction(String target, String data)
            throws SAXException {
    }

    @Override
    public void setDocumentLocator(Locator loc) {
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        throw e;
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        throw e;
    }

    @Override
    public void warning(SAXParseException err) throws SAXException {
    }

    @Override
    public void notationDecl(String name, String publicId, String systemId) throws SAXException {
    }

    @Override
    public void unparsedEntityDecl(String name, String publicId,
                                   String systemId, String notationName) throws SAXException {
    }

    @Override
    public void endDTD() throws SAXException {
    }

    @Override
    public void externalGeneralEntityDecl(String n, String p, String s) throws SAXException {
    }

    @Override
    public void internalGeneralEntityDecl(String n, String v) throws SAXException {
    }

    @Override
    public void externalParameterEntityDecl(String n, String p, String s) throws SAXException {
    }

    @Override
    public void internalParameterEntityDecl(String n, String v) throws SAXException {
    }

    @Override
    public void startDTD(InputEntity in) throws SAXException {
    }

    @Override
    public void comment(String n) throws SAXException {
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
    }

    @Override
    public void startCDATA() throws SAXException {
    }

    @Override
    public void endCDATA() throws SAXException {
    }


    @Override
    public void startContentModel(String elementName, short contentModelType) throws SAXException {
    }

    @Override
    public void endContentModel(String elementName, short contentModelType) throws SAXException {
    }

    @Override
    public void attributeDecl(String elementName, String attributeName, String attributeType,
                              String[] enumeration, short attributeUse, String defaultValue) throws SAXException {
    }

    @Override
    public void childElement(String elementName, short occurrence) throws SAXException {
    }

    @Override
    public void mixedElement(String elementName) throws SAXException {
    }

    @Override
    public void startModelGroup() throws SAXException {
    }

    @Override
    public void endModelGroup(short occurrence) throws SAXException {
    }

    @Override
    public void connector(short connectorType) throws SAXException {
    }
}

