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
package com.g4s8.teletakes.fb;

import com.g4s8.teletakes.rs.TmResponse;
import java.util.Optional;
import org.cactoos.iterable.IterableOf;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Fallback chain.
 *
 * @since 1.0
 */
public final class FbChain implements TmFallback {

    /**
     * Fallbacks.
     */
    private final Iterable<TmFallback> fbcks;

    /**
     * Chain of fallbacks.
     *
     * @param fbcks Fallbacks
     */
    public FbChain(final TmFallback... fbcks) {
        this(new IterableOf<>(fbcks));
    }

    /**
     * Chain of fallbacks.
     *
     * @param fbcks Fallbacks
     */
    public FbChain(final Iterable<TmFallback> fbcks) {
        this.fbcks = fbcks;
    }

    @Override
    public Optional<TmResponse> handle(final Update upd, final Throwable err) {
        TmResponse resp = null;
        for (final TmFallback fbck : this.fbcks) {
            final Optional<TmResponse> opt = fbck.handle(upd, err);
            if (opt.isPresent()) {
                resp = opt.get();
                break;
            }
        }
        return Optional.ofNullable(resp);
    }
}
