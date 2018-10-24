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
import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link RsReplyKeyboard}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class RsReplyKeyboardTest {

    @Test
    @DisplayName("RsReplyKeyboard can render XML")
    public void renderXml() throws Exception {
        final String one = "one";
        final String two = "two";
        final String space = "--space--";
        MatcherAssert.assertThat(
            new RsReplyKeyboard(
                new RsEmpty(),
                new IterableOf<Iterable<Text>>(
                    new IterableOf<Text>(new TextOf(one), new TextOf(two)),
                    new IterableOf<Text>(new TextOf(space))
                )
            ).xml(),
            XhtmlMatchers.hasXPaths(
                // @checkstyle LineLengthCheck (3 lines)
                String.format("/response/message/keyboard/reply/row[position() = 1]/button[position() = 1 and text() = '%s']", one),
                String.format("/response/message/keyboard/reply/row[position() = 1]/button[position() = 2 and text() = '%s']", two),
                String.format("/response/message/keyboard/reply/row[position() = 2]/button[position() = 1 and text() = '%s']", space)
            )
        );
    }
}
