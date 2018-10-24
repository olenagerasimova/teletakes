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
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

/**
 * Request from user.
 *
 * @since 1.0
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public final class RqUser implements Scalar<User> {

    /**
     * Request.
     */
    private final Update upd;

    /**
     * Ctor.
     *
     * @param upd Request
     */
    public RqUser(final Update upd) {
        this.upd = upd;
    }

    @Override
    public User value() throws IOException {
        final User user;
        if (this.upd.hasMessage()) {
            user = this.upd.getMessage().getFrom();
        } else if (this.upd.hasCallbackQuery()) {
            user = this.upd.getCallbackQuery().getFrom();
        } else if (this.upd.hasChannelPost()) {
            user = this.upd.getChannelPost().getFrom();
        } else if (this.upd.hasChosenInlineQuery()) {
            user = this.upd.getChosenInlineQuery().getFrom();
        } else if (this.upd.hasEditedChannelPost()) {
            user = this.upd.getEditedChannelPost().getFrom();
        } else if (this.upd.hasEditedMessage()) {
            user = this.upd.getEditedMessage().getFrom();
        } else if (this.upd.hasInlineQuery()) {
            user = this.upd.getInlineQuery().getFrom();
        } else if (this.upd.hasPreCheckoutQuery()) {
            user = this.upd.getPreCheckoutQuery().getFrom();
        } else if (this.upd.hasShippingQuery()) {
            user = this.upd.getShippingQuery().getFrom();
        } else {
            throw new IOException("Can't find request user");
        }
        return user;
    }
}
