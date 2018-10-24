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

import com.g4s8.teletakes.fb.TmFallback;
import com.g4s8.teletakes.rs.TmResponse;
import java.io.IOException;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Take with fallback.
 *
 * @since 1.0
 */
public final class TkFallback implements TmTake {

    /**
     * Origin take.
     */
    private final TmTake tke;

    /**
     * Fallback.
     */
    private final TmFallback fbck;

    /**
     * Use fallback on origin take error.
     *
     * @param tke Origin take
     * @param fbck Fallback
     */
    public TkFallback(final TmTake tke, final TmFallback fbck) {
        this.tke = tke;
        this.fbck = fbck;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public TmResponse act(final Update upd) throws IOException {
        TmResponse resp;
        try {
            resp = this.tke.act(upd);
        } catch (final TmException err) {
            resp = err;
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Exception err) {
            resp = this.fbck.handle(upd, err).orElseThrow(
                () -> new IOException("Take failed, fallback not found", err)
            );
        }
        return resp;
    }
}
