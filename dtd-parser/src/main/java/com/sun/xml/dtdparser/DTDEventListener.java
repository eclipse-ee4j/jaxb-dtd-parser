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

import java.util.EventListener;

/**
 * All DTD parsing events are signaled through this interface.
 */
public interface DTDEventListener extends EventListener {

    void setDocumentLocator(Locator loc);

    /**
     * Receive notification of a Processing Instruction.
     * Processing instructions contain information meaningful
     * to the application.
     *
     * @param target The target of the processing instruction
     *               which should have meaning to the application.
     * @param data   The instruction itself which should contain
     *               valid XML characters.
     * @throws SAXException for errors
     */
    void processingInstruction(String target, String data)
            throws SAXException;

    /**
     * Receive notification of a Notation Declaration.
     * Notation declarations are used by elements and entities
     * for identifying embedded non-XML data.
     *
     * @param name     The notation name, referred to by entities and
     *                 elements.
     * @param publicId The public identifier
     * @param systemId The system identifier
     * @throws SAXException for errors
     */
    void notationDecl(String name, String publicId, String systemId)
            throws SAXException;

    /**
     * Receive notification of an unparsed entity declaration.
     * Unparsed entities are non-XML data.
     *
     * @param name         The name of the unparsed entity.
     * @param publicId     The public identifier
     * @param systemId     The system identifier
     * @param notationName The associated notation
     * @throws SAXException for errors
     */
    void unparsedEntityDecl(String name, String publicId,
                            String systemId, String notationName)
            throws SAXException;

    /**
     * Receive notification of a internal general entity declaration event.
     *
     * @param name  The internal general entity name.
     * @param value The value of the entity, which may include unexpanded
     *              entity references.  Character references will have been
     *              expanded.
     * @throws SAXException for errors
     * @see #externalGeneralEntityDecl(String, String, String)
     */
    void internalGeneralEntityDecl(String name, String value)
            throws SAXException;

    /**
     * Receive notification of an external parsed general entity
     * declaration event.
     *
     * <p>If a system identifier is present, and it is a relative URL, the
     * parser will have resolved it fully before passing it through this
     * method to a listener.</p>
     *
     * @param name     The entity name.
     * @param publicId The entity's public identifier, or null if
     *                 none was given.
     * @param systemId The entity's system identifier.
     * @throws SAXException for errors
     * @see #unparsedEntityDecl(String, String, String, String)
     */
    void externalGeneralEntityDecl(String name, String publicId,
                                   String systemId)
            throws SAXException;

    /**
     * Receive notification of a internal parameter entity declaration
     * event.
     *
     * @param name  The internal parameter entity name.
     * @param value The value of the entity, which may include unexpanded
     *              entity references.  Character references will have been
     *              expanded.
     * @throws SAXException for errors
     * @see #externalParameterEntityDecl(String, String, String)
     */
    void internalParameterEntityDecl(String name, String value)
            throws SAXException;

    /**
     * Receive notification of an external parameter entity declaration
     * event.
     *
     * <p>If a system identifier is present, and it is a relative URL, the
     * parser will have resolved it fully before passing it through this
     * method to a listener.</p>
     *
     * @param name     The parameter entity name.
     * @param publicId The entity's public identifier, or null if
     *                 none was given.
     * @param systemId The entity's system identifier.
     * @throws SAXException for errors
     * @see #unparsedEntityDecl(String, String, String, String)
     */
    void externalParameterEntityDecl(String name, String publicId,
                                     String systemId)
            throws SAXException;

    /**
     * Receive notification of the beginning of the DTD.
     *
     * @param in Current input entity.
     * @throws SAXException for errors
     * @see #endDTD()
     */
    void startDTD(InputEntity in)
            throws SAXException;

    /**
     * Receive notification of the end of a DTD.  The parser will invoke
     * this method only once.
     *
     * @throws SAXException for errors
     * @see #startDTD(InputEntity)
     */
    void endDTD()
            throws SAXException;

    /**
     * Receive notification that a comment has been read.
     *
     * <P> Note that processing instructions are the mechanism designed
     * to hold information for consumption by applications, not comments.
     * XML systems may rely on applications being able to access information
     * found in processing instructions; this is not true of comments, which
     * are typically discarded.
     *
     * @param text the text within the comment delimiters.
     * @throws SAXException for errors
     */
    void comment(String text)
            throws SAXException;

    /**
     * Receive notification of character data.
     *
     * <p>The Parser will call this method to report each chunk of
     * character data.  SAX parsers may return all contiguous character
     * data in a single chunk, or they may split it into several
     * chunks; however, all of the characters in any single event
     * must come from the same external entity, so that the Locator
     * provides useful information.</p>
     *
     * <p>The application must not attempt to read from the array
     * outside of the specified range.</p>
     *
     * <p>Note that some parsers will report whitespace using the
     * ignorableWhitespace() method rather than this one (validating
     * parsers must do so).</p>
     *
     * @param ch     The characters from the DTD.
     * @param start  The start position in the array.
     * @param length The number of characters to read from the array.
     * @throws SAXException for errors
     * @see #ignorableWhitespace(char[], int, int)
     */
    void characters(char[] ch, int start, int length)
            throws SAXException;


    /**
     * Receive notification of ignorable whitespace in element content.
     * 
     * <p>Validating Parsers must use this method to report each chunk
     * of ignorable whitespace (see the W3C XML 1.0 recommendation,
     * section 2.10): non-validating parsers may also use this method
     * if they are capable of parsing and using content models.</p>
     *
     * <p>SAX parsers may return all contiguous whitespace in a single
     * chunk, or they may split it into several chunks; however, all of
     * the characters in any single event must come from the same
     * external entity, so that the Locator provides useful
     * information.</p>
     *
     * <p>The application must not attempt to read from the array
     * outside of the specified range.</p>
     *
     * @param ch     The characters from the DTD.
     * @param start  The start position in the array.
     * @param length The number of characters to read from the array.
     * @throws SAXException for errors
     * @see #characters(char[], int, int)
     */
    void ignorableWhitespace(char[] ch, int start, int length)
            throws SAXException;

    /**
     * Receive notification that a CDATA section is beginning.  Data in a
     * CDATA section is reported through the appropriate event, either
     * <em>characters()</em> or <em>ignorableWhitespace</em>.
     *
     * @throws SAXException for errors
     * @see #endCDATA()
     */
    void startCDATA() throws SAXException;


    /**
     * Receive notification that the CDATA section finished.
     *
     * @throws SAXException for errors
     * @see #startCDATA()
     */
    void endCDATA() throws SAXException;


    void fatalError(SAXParseException e)
            throws SAXException;

    void error(SAXParseException e) throws SAXException;

    void warning(SAXParseException err) throws SAXException;

    short CONTENT_MODEL_EMPTY = 0;
    short CONTENT_MODEL_ANY = 1;
    short CONTENT_MODEL_MIXED = 2;
    short CONTENT_MODEL_CHILDREN = 3;

    /**
     * receives notification that parsing of content model is beginning.
     *
     * @param elementName      name of the element whose content model is going to be defined.
     * @param contentModelType {@link #CONTENT_MODEL_EMPTY}
     *                         this element has EMPTY content model. This notification
     *                         will be immediately followed by the corresponding endContentModel.
     *                         {@link #CONTENT_MODEL_ANY}
     *                         this element has ANY content model. This notification
     *                         will be immediately followed by the corresponding endContentModel.
     *                         {@link #CONTENT_MODEL_MIXED}
     *                         this element has mixed content model. #PCDATA will not be reported.
     *                         each child element will be reported by mixedElement method.
     *                         {@link #CONTENT_MODEL_CHILDREN}
     *                         this element has child content model. The actual content model will
     *                         be reported by childElement, startModelGroup, endModelGroup, and
     *                         connector methods. Possible call sequences are:
     *                         <p>
     *                         START := MODEL_GROUP
     *                         MODEL_GROUP := startModelGroup TOKEN (connector TOKEN)* endModelGroup
     *                         TOKEN := childElement
     *                         | MODEL_GROUP
     * @throws SAXException for errors
     */
    void startContentModel(String elementName, short contentModelType) throws SAXException;

    /**
     * receives notification that parsing of content model is finished.
     * @throws SAXException for errors
     */
    void endContentModel(String elementName, short contentModelType) throws SAXException;

    short USE_NORMAL = 0;
    short USE_IMPLIED = 1;
    short USE_FIXED = 2;
    short USE_REQUIRED = 3;

    /**
     * For each entry in an ATTLIST declaration,
     * this event will be fired.
     *
     * <p>
     * DTD allows the same attributes to be declared more than
     * once, and in that case the first one wins. I think
     * this method will be only fired for the first one,
     * but I need to check.
     * @throws SAXException for errors
     */
    void attributeDecl(String elementName, String attributeName, String attributeType,
                       String[] enumeration, short attributeUse, String defaultValue) throws SAXException;

    void childElement(String elementName, short occurrence) throws SAXException;

    /**
     * Receives notification of child element of mixed content model. This method is called for each child element.
     *
     * @throws SAXException for errors
     * @see #startContentModel(String, short)
     */
    void mixedElement(String elementName) throws SAXException;

    void startModelGroup() throws SAXException;

    void endModelGroup(short occurrence) throws SAXException;

    short CHOICE = 0;
    short SEQUENCE = 1;

    /**
     * Connectors in one model group is guaranteed to be the same.
     *
     * <p>
     * IOW, you'll never see an event sequence like (a|b,c)
     * @throws SAXException for errors
     */
    void connector(short connectorType) throws SAXException;

    short OCCURENCE_ZERO_OR_MORE = 0;
    short OCCURENCE_ONE_OR_MORE = 1;
    short OCCURENCE_ZERO_OR_ONE = 2;
    short OCCURENCE_ONCE = 3;
}
