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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Log error.
 *
 * @since 1.0
 */
public final class FbSlf4j implements TmFallback {

    /**
     * Logger.
     */
    @SuppressWarnings("PMD.LoggerIsNotStaticFinal")
    private final Logger logger;

    /**
     * Fallback with default logger.
     */
    public FbSlf4j() {
        this(LoggerFactory.getLogger(FbSlf4j.class));
    }

    /**
     * Fallback with logger.
     *
     * @param logger Slf4j logger
     */
    public FbSlf4j(final Logger logger) {
        this.logger = logger;
    }

    @Override
    public Optional<TmResponse> handle(final Update upd, final Throwable err) {
        this.logger.error(
            String.format("Update %s failed", upd.toString()),
            err.toString()
        );
        return Optional.empty();
    }
}
