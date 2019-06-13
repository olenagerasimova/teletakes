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

import java.io.IOException;
import org.telegram.telegrambots.api.methods.send.SendMessage;

/**
 * Bot.
 * @since 1.0
 */
public interface Bot {

    /**
     * Ctor.
     * @param msg Message to send
     * @throws IOException When smth went wrong
     */
    void send(SendMessage msg) throws IOException;

    /**
     * Fake implementation of {@link Bot}.
     */
    final class Fake implements Bot {

        /**
         * Was message sent?
         */
        private boolean sent;

        /**
         * Ctor.
         */
        public Fake() {
            this.sent = false;
        }

        @Override
        public void send(final SendMessage msg) {
            this.sent = true;
        }

        /**
         * Was message sent?
         * @return True if was false otherwise
         */
        public boolean sent() {
            return this.sent;
        }
    }
}
