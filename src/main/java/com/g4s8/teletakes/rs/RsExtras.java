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

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.cactoos.Scalar;

/**
 * Extra value from response.
 *
 * @since 1.0
 */
public final class RsExtras implements Scalar<String> {

    /**
     * Response.
     */
    private final TmResponse res;

    /**
     * Extra key.
     */
    private final String key;

    /**
     * Default value.
     */
    private final Optional<String> def;

    /**
     * Extra value from response or exception if not found.
     *
     * @param res Response
     * @param key Item key
     */
    public RsExtras(final TmResponse res, final String key) {
        this(res, key, Optional.empty());
    }

    /**
     * Extra value from response or default value if not found.
     *
     * @param res Response
     * @param key Item key
     * @param def Default value
     */
    public RsExtras(final TmResponse res, final String key,
        final String def) {
        this(res, key, Optional.of(def));
    }

    /**
     * Extra value from response by key or optional default value.
     *
     * @param res Response
     * @param key Key
     * @param def Optional default value
     */
    public RsExtras(final TmResponse res, final String key,
        final Optional<String> def) {
        this.res = res;
        this.key = key;
        this.def = def;
    }

    @Override
    @SuppressWarnings("PMD.UseCollectionIsEmpty")
    public String value() throws Exception {
        final List<String> xpath = this.res.xml().xpath(
            String.format(
                "/response/extras/item[@key='%s']/text()", this.key
            )
        );
        final String val;
        if (xpath.size() > 0) {
            val = xpath.get(0);
        } else if (this.def.isPresent()) {
            val = this.def.get();
        } else {
            throw new IOException(
                String.format(
                    "Extra item with key '%s' does not exist", this.key
                )
            );
        }
        return val;
    }
}
