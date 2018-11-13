/*
 * Copyright (c) 1998, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.dtdparser;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;

final class ExternalEntity extends EntityDecl {
    String systemId;    // resolved URI (not relative)
    String publicId;    // "-//xyz//....//en"
    String notation;

    public ExternalEntity(InputEntity in) {
    }

    public InputSource getInputSource(EntityResolver r)
            throws IOException, SAXException {

        InputSource retval;

        retval = r.resolveEntity(publicId, systemId);
        // SAX sez if null is returned, use the URI directly
        if (retval == null)
            retval = Resolver.createInputSource(new URL(systemId), false);
        return retval;
    }
}
