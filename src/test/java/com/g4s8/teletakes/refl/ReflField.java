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
package com.g4s8.teletakes.refl;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Reflection field.
 *
 * @since 1.0
 */
public final class ReflField {

    /**
     * Target object (this).
     */
    private final Object target;

    /**
     * Field name.
     */
    private final String name;

    /**
     * Field of object.
     *
     * @param target Target object
     * @param name Field name
     */
    public ReflField(final Object target, final String name) {
        this.target = target;
        this.name = name;
    }

    /**
     * Write value to object field.
     *
     * @param value What to write
     * @throws IOException If fails
     */
    public void write(final Object value) throws IOException {
        try {
            final Field field = this.target.getClass()
                .getDeclaredField(this.name);
            final boolean accessible = field.isAccessible();
            field.setAccessible(true);
            field.set(this.target, value);
            field.setAccessible(accessible);
        } catch (final NoSuchFieldException err) {
            throw new IOException("Failed to find fild", err);
        } catch (final IllegalAccessException err) {
            throw new IOException("Failed to write to field", err);
        }
    }
}
