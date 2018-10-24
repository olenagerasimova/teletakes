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

import com.jcabi.matchers.XhtmlMatchers;
import org.cactoos.iterable.IterableOf;
import org.cactoos.map.MapEntry;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link RsInlineKeyboard}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class RsInlineKeyboardTest {

    @Test
    @DisplayName("RsInlineKeyboard can render XML")
    public void renderXml() throws Exception {
        MatcherAssert.assertThat(
            new RsInlineKeyboard(
                new RsEmpty(),
                new IterableOf<>(
                    new IterableOf<>(
                        new MapEntry<>("btn1", "cb1"),
                        new MapEntry<>("btn2", "cb2")
                    ),
                    new IterableOf<>(new MapEntry<>("btn3", "cb3"))
                )
            ).xml(),
            XhtmlMatchers.hasXPaths(
                // @checkstyle LineLengthCheck (3 lines)
                "/response/message/keyboard/inline/row[position() = 1]/button[position() = 1 and text() = 'btn1' and @callback = 'cb1']",
                "/response/message/keyboard/inline/row[position() = 1]/button[position() = 2 and text() = 'btn2' and @callback = 'cb2']",
                "/response/message/keyboard/inline/row[position() = 2]/button[position() = 1 and text() = 'btn3' and @callback = 'cb3']"
            )
        );
    }
}
