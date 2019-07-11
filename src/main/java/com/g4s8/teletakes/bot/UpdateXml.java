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
package com.g4s8.teletakes.bot;

import com.g4s8.teletakes.upd.TmUpdate;
import com.jcabi.xml.XML;
import java.io.IOException;
import org.cactoos.Scalar;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.telegram.telegrambots.api.objects.Update;
import org.xembly.Directives;

/**
 * Update xml structure.
 * @since 2.0
 */
class UpdateXml implements Scalar<TmUpdate> {

    /**
     * Telegram update.
     */
    private final Update upd;

    /**
     * Ctro.
     * @param upd Update from telegram
     */
    public UpdateXml(final Update upd) {
        this.upd = upd;
    }


    @Override
    public TmUpdate value() throws Exception {
        return new TmUpdate() {
            @Override
            public XML xml() throws IOException {
                final Directives dirs = new Directives()
                    .add("update")
                    .add("text").set(msg())
                    .add("user")
                        ;
                if (upd.hasMessage()) {
                    dirs.add("message")
                        .add(
                            new MapOf<Object, Object>(
                                new MapEntry<>("id", upd.getMessage().getMessageId()),
                                new MapEntry<>("chat_id", upd.getMessage().getChatId())
                            )
                        )
                        .add("chat")
                            .add(
                                new MapOf<Object, Object>(
                                    new MapEntry<>("id", upd.getMessage().getChatId())
                                )
                            );
                }


                return null;
            }
        };
    }

    private String msg() {
        final String res;
        if (this.upd.hasMessage() && upd.getMessage().hasText()) {
            res = this.upd.getMessage().getText();
        } else if (this.upd.hasCallbackQuery()) {
            res = this.upd.getCallbackQuery().getMessage().getText();
        } else {
            res = "";
        }
        return res;
    }
}
