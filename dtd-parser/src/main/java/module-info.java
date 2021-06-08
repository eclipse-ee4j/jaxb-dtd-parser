/*
 * Copyright (c) 2017, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/**
 * SAX-like API for parsing XML DTDs.
 */
module com.sun.xml.dtdparser {

    requires java.xml;
    requires java.logging;

    exports com.sun.xml.dtdparser;
}
