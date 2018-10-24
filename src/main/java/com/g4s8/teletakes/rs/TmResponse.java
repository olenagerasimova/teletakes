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

import com.jcabi.xml.XML;
import java.io.IOException;
import org.cactoos.Scalar;
import org.cactoos.scalar.IoCheckedScalar;
import org.cactoos.scalar.SolidScalar;

/**
 * XML view of response.
 *
 * @since 1.0
 */
public interface TmResponse {

    /**
     * Response as XML.
     *
     * @return XML view of response
     * @throws IOException If fails
     */
    XML xml() throws IOException;

    /**
     * Decorator for response.
     */
    abstract class Wrap implements TmResponse {

        /**
         * Origin response.
         */
        private final Scalar<TmResponse> origin;

        /**
         * Ctor.
         *
         * @param origin Origin response
         */
        protected Wrap(final TmResponse origin) {
            this(() -> origin);
        }

        /**
         * Ctor.
         *
         * @param origin Origin lazy response
         */
        protected Wrap(final Scalar<TmResponse> origin) {
            this.origin = new SolidScalar<>(origin);
        }

        @Override
        public final XML xml() throws IOException {
            return new IoCheckedScalar<>(this.origin).value().xml();
        }
    }
}
