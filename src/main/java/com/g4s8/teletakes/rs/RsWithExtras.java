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

import java.util.Collections;
import java.util.Map;
import org.cactoos.scalar.Folded;
import org.xembly.Directives;

/**
 * Response with extras.
 *
 * @since 1.0
 */
public final class RsWithExtras extends TmResponse.Wrap {

    /**
     * Extras.
     *
     * @param extras Extras map
     */
    public RsWithExtras(final Map<String, String> extras) {
        this(new RsEmpty(), extras);
    }

    /**
     * Extra item.
     *
     * @param key Extra key
     * @param val Extra value
     */
    public RsWithExtras(final String key, final String val) {
        this(new RsEmpty(), key, val);
    }

    /**
     * Origin response with one extra item.
     *
     * @param res Origin response
     * @param key Extra key
     * @param val Extra value
     */
    public RsWithExtras(final TmResponse res, final String key,
        final String val) {
        this(res, Collections.singletonMap(key, val));
    }

    /**
     * Origin response with extras.
     *
     * @param res Origin response
     * @param extras Extras
     */
    public RsWithExtras(final TmResponse res,
        final Map<String, String> extras) {
        super(
            // @checkstyle IndentationCheck (1 line)
            () -> new RsDirectives(
                res,
                new Directives()
                    .xpath("/response")
                    .addIf("extras")
                    .strict(1)
                    .append(
                        new Folded<>(
                            new Directives(),
                            (dirs, item) -> dirs.push()
                                .xpath(
                                    String.format(
                                        "item[@key='%s']", item.getKey()
                                    )
                                ).remove()
                                .pop()
                                .add("item")
                                .attr("key", item.getKey())
                                .set(item.getValue()),
                            extras.entrySet()
                        ).value()
                    )
            )
        );
    }
}
