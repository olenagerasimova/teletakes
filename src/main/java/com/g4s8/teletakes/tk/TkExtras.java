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
package com.g4s8.teletakes.tk;

import com.g4s8.teletakes.rs.RsWithExtras;
import com.g4s8.teletakes.rs.TmResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Take with extras.
 *
 * @since 1.0
 */
public final class TkExtras implements TmTake {

    /**
     * Origin take.
     */
    private final TmTake origin;

    /**
     * Extras.
     */
    private final Map<String, String> extras;

    /**
     * Send empty response with extras.
     *
     * @param key Extra key
     * @param val Extra value
     */
    public TkExtras(final String key, final String val) {
        this(new TkEmpty(), key, val);
    }

    /**
     * Send empty response with extras.
     *
     * @param extras Extras to send
     */
    public TkExtras(final Map<String, String> extras) {
        this(new TkEmpty(), extras);
    }

    /**
     * Add single extra to origin take response.
     *
     * @param origin Origin take
     * @param key Extra key
     * @param val Extra val
     */
    public TkExtras(final TmTake origin, final String key,
        final String val) {
        this(origin, Collections.singletonMap(key, val));
    }

    /**
     * Add extras to origin take response.
     *
     * @param origin Origin take
     * @param extras Extras to add
     */
    public TkExtras(final TmTake origin,
        final Map<String, String> extras) {
        this.origin = origin;
        this.extras = Collections.unmodifiableMap(extras);
    }

    @Override
    public TmResponse act(final Update upd) throws IOException {
        return new RsWithExtras(
            this.origin.act(upd),
            this.extras
        );
    }
}
