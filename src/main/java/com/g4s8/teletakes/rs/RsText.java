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

import org.cactoos.Text;
import org.cactoos.scalar.IoCheckedScalar;
import org.cactoos.text.TextOf;
import org.xembly.Directives;

/**
 * Plain text as response.
 *
 * @since 1.0
 */
public final class RsText extends TmResponse.Wrap {

    /**
     * Ctor.
     *
     * @param text Response text
     */
    public RsText(final String text) {
        this(new TextOf(text));
    }

    /**
     * Ctor.
     *
     * @param text Response text
     */
    public RsText(final Text text) {
        super(
            // @checkstyle IndentationCheck (1 line)
            () -> new RsDirectives(
                new Directives()
                    .xpath("/response")
                    .addIf("message")
                    .strict(1)
                    .addIf("text")
                    .strict(1)
                    .attr("kind", "plain")
                    .set(new IoCheckedScalar<>(text::asString).value())
            )
        );
    }
}
