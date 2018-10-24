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
package com.g4s8.teletakes.fk;

import com.g4s8.teletakes.rs.TmResponse;
import com.g4s8.teletakes.tk.TmTake;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Fork by message text.
 *
 * @since 1.0
 */
public final class FkMessage implements TmFork {

    /**
     * Text pattern.
     */
    private final Pattern ptn;

    /**
     * Take.
     */
    private final TmTake tke;

    /**
     * Ctor.
     *
     * @param ptn Text pattern string
     * @param tke Take
     */
    public FkMessage(final String ptn, final TmTake tke) {
        this(Pattern.compile(ptn), tke);
    }

    /**
     * Ctor.
     *
     * @param ptn Text pattern
     * @param tke Take
     */
    public FkMessage(final Pattern ptn, final TmTake tke) {
        this.ptn = ptn;
        this.tke = tke;
    }

    @Override
    public Optional<TmResponse> route(final Update update) throws IOException {
        final Optional<TmResponse> opt;
        if (update.hasMessage() && update.getMessage().hasText()
            && this.ptn.matcher(update.getMessage().getText()).matches()) {
            opt = Optional.of(this.tke.act(update));
        } else {
            opt = Optional.empty();
        }
        return opt;
    }
}
