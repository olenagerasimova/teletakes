/*
 * MIT License
 *
 * Copyright (c) 2018 g4s8
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.g4s8.teletakes.rs;

import com.jcabi.xml.StrictXML;
import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import com.jcabi.xml.XSD;
import com.jcabi.xml.XSDDocument;
import java.io.IOException;
import org.cactoos.scalar.IoCheckedScalar;
import org.xembly.Directive;
import org.xembly.Directives;
import org.xembly.Xembler;

/**
 * XML directives as response.
 *
 * @since 1.0
 */
public final class RsDirectives implements TmResponse {

    /**
     * Schema.
     */
    private static final XSD SCHEMA = XSDDocument.make(
        RsDirectives.class.getResource("response.xsd")
    );

    /**
     * Directives.
     */
    private final Iterable<Directive> dirs;

    /**
     * Origin response.
     */
    private final TmResponse origin;

    /**
     * Ctor.
     *
     * @param dirs Directives
     */
    public RsDirectives(final Iterable<Directive> dirs) {
        this(new RsEmpty(), dirs);
    }

    /**
     * DIrectives with origin response.
     *
     * @param origin Origin response
     * @param dirs Directives to apply
     */
    public RsDirectives(final TmResponse origin,
        final Iterable<Directive> dirs) {
        this.origin = origin;
        this.dirs = dirs;
    }

    @Override
    public XML xml() throws IOException {
        return new StrictXML(
            new XMLDocument(
                new IoCheckedScalar<>(
                    () -> new Xembler(
                        new Directives()
                            .addIf("response")
                            .strict(1)
                            .append(this.dirs)
                    ).apply(this.origin.xml().node())
                ).value()
            ),
            RsDirectives.SCHEMA
        );
    }
}
