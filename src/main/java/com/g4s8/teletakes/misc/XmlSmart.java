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
package com.g4s8.teletakes.misc;

import com.jcabi.xml.XML;
import java.util.List;
import java.util.Optional;
import javax.xml.namespace.NamespaceContext;
import org.w3c.dom.Node;

/**
 * Smart XML.
 *
 * @since 1.0
 */
public final class XmlSmart implements XML {

    /**
     * Origin XML.
     */
    private final XML origin;

    /**
     * Wrap XML with smarter one.
     *
     * @param origin XML to wrap
     */
    public XmlSmart(final XML origin) {
        this.origin = origin;
    }

    @Override
    public List<String> xpath(final String xpath) {
        return this.origin.xpath(xpath);
    }

    @Override
    public List<XML> nodes(final String xpath) {
        return this.origin.nodes(xpath);
    }

    @Override
    public XML registerNs(final String namespace, final Object obj) {
        return this.origin.registerNs(namespace, obj);
    }

    @Override
    public XML merge(final NamespaceContext ctx) {
        return this.origin.merge(ctx);
    }

    @Override
    public Node node() {
        return this.origin.node();
    }

    /**
     * Optional text by xpath.
     *
     * @param xpath Xpath
     * @return Optional node
     */
    public Optional<String> optXpath(final String xpath) {
        final List<String> nodes = this.xpath(xpath);
        final Optional<String> opt;
        if (nodes.isEmpty()) {
            opt = Optional.empty();
        } else {
            opt = Optional.of(nodes.get(0));
        }
        return opt;
    }

    /**
     * Optional node by xpath.
     *
     * @param xpath Xpath
     * @return Optional node
     */
    public Optional<XML> optNode(final String xpath) {
        final List<XML> nodes = this.nodes(xpath);
        final Optional<XML> opt;
        if (nodes.isEmpty()) {
            opt = Optional.empty();
        } else {
            opt = Optional.of(nodes.get(0));
        }
        return opt;
    }
}
