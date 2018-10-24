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
import java.util.Map;
import org.xembly.Directives;
import org.xembly.ImpossibleModificationException;
import org.xembly.Xembler;

/**
 * Response with inline buttons.
 *
 * @since 1.0
 * @todo #1:30min Introduce new classes for inline buttons which may have
 *  either callback query or URL link and render them as XML differently
 *  see `response.xsd` for details.
 */
public final class RsInlineKeyboard implements TmResponse {

    /**
     * Response.
     */
    private final TmResponse origin;

    /**
     * Buttons layout.
     */
    private final Iterable<Iterable<Map.Entry<String, String>>> buttons;

    /**
     * Ctor.
     *
     * @param origin Origin response
     * @param buttons Buttons
     */
    public RsInlineKeyboard(final TmResponse origin,
        final Iterable<Iterable<Map.Entry<String, String>>> buttons) {
        this.origin = origin;
        this.buttons = buttons;
    }

    @Override
    public XML xml() throws IOException {
        final Directives dirs = new Directives()
            .xpath("/response")
            .addIf("message")
            .addIf("keyboard")
            .push()
            .xpath("/response/message/keyboard/*")
            .remove()
            .pop()
            .add("inline")
            .strict(1);
        for (final Iterable<Map.Entry<String, String>> row : this.buttons) {
            dirs.add("row");
            for (final Map.Entry<String, String> btn : row) {
                dirs.add("button")
                    .attr("callback", btn.getValue())
                    .set(btn.getKey())
                    .up();
            }
            dirs.up();
        }
        try {
            return new XMLDocument(
                new Xembler(dirs).apply(this.origin.xml().node())
            );
        } catch (final ImpossibleModificationException err) {
            throw new IOException("Failed to apply directives", err);
        }
    }
}
