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
package com.g4s8.teletakes.rq;

import java.io.IOException;
import org.cactoos.Scalar;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Request message.
 *
 * @since 1.0
 */
public final class RqMessage implements Scalar<Message> {

    /**
     * Update.
     */
    private final Update upd;

    /**
     * Ctor.
     *
     * @param upd Update
     */
    public RqMessage(final Update upd) {
        this.upd = upd;
    }

    @Override
    public Message value() throws Exception {
        final Message msg;
        if (this.upd.hasMessage()) {
            msg = this.upd.getMessage();
        } else if (this.upd.hasCallbackQuery()) {
            msg = this.upd.getCallbackQuery().getMessage();
        } else {
            throw new IOException("Can't find message in update");
        }
        return msg;
    }
}
