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
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.ScalarHasValue;

/**
 * Test case for {@link RsExtras}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class RsExtrasTest {

    @Test
    @DisplayName("can find item by key")
    public void findExtrasItem() throws Exception {
        final String key = "xxtr";
        final String val = "ybgvunfio";
        MatcherAssert.assertThat(
            new RsExtras(new RsWithExtras(key, val), key),
            new ScalarHasValue<>(val)
        );
    }

    @Test
    @DisplayName("should use default valud if item is not present")
    public void useDefaultValueIfNotPresent() throws Exception {
        final String key = "xtra";
        final String def = "32rgtb";
        MatcherAssert.assertThat(
            new RsExtras(new RsEmpty(), key, def),
            new ScalarHasValue<>(def)
        );
    }

    @Test
    @DisplayName("should throw exception if item is not present")
    public void throwsExceptionIfNotPresent() throws Exception {
        Assertions.assertThrows(
            IOException.class,
            new RsExtras(new RsEmpty(), "none")::value
        );
    }
}
