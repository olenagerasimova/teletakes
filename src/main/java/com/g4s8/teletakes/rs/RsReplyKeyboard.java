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

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import java.io.IOException;
import org.cactoos.Text;
import org.cactoos.scalar.UncheckedScalar;
import org.xembly.Directives;
import org.xembly.ImpossibleModificationException;
import org.xembly.Xembler;

/**
 * Response with keyboard.
 *
 * @since 1.0
 */
public final class RsReplyKeyboard implements TmResponse {

    /**
     * Origin.
     */
    private final TmResponse origin;

    /**
     * Rows.
     */
    private final Iterable<Iterable<Text>> rows;

    /**
     * Ctor.
     *
     * @param origin Origin response
     * @param rows Keyboard rows
     */
    public RsReplyKeyboard(final TmResponse origin,
        final Iterable<Iterable<Text>> rows) {
        this.origin = origin;
        this.rows = rows;
    }

    @Override
    public XML xml() throws IOException {
        final Directives keyboard =
            new Directives()
                .xpath("/response")
                .addIf("message")
                .addIf("keyboard")
                .push()
                .xpath("/response/message/keyboard/*")
                .remove()
                .pop()
                .addIf("reply")
                .strict(1);
        for (final Iterable<Text> row : this.rows) {
            keyboard.add("row");
            for (final Text button : row) {
                keyboard.add("button")
                    .set(new UncheckedScalar<>(button::asString).value())
                    .up();
            }
            keyboard.up();
        }
        try {
            return new XMLDocument(
                new Xembler(keyboard).apply(this.origin.xml().node())
            );
        } catch (final ImpossibleModificationException err) {
            throw new IOException("Failed to apply directives", err);
        }
    }
}
