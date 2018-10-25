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

import com.g4s8.teletakes.refl.ReflField;
import com.g4s8.teletakes.tk.TkEmpty;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.regex.Pattern;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.api.objects.EntityType;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.MessageEntity;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Test case for {@link FkCommand}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class FkCommandTest {

    @Test
    @DisplayName("FkCommand can match by patetrn")
    public void matchByPattern() throws Exception {
        MatcherAssert.assertThat(
            new FkCommand(
                Pattern.compile("^foo\\s\\d+"),
                new TkEmpty()
            ).route(command("foo 42")),
            Matchers.not(Matchers.equalTo(Optional.empty()))
        );
    }

    private static Update command(final String command) throws IOException {
        final Message message = new Message();
        new ReflField(message, "text").write(command);
        final MessageEntity entity = new MessageEntity();
        new ReflField(entity, "offset").write(0);
        new ReflField(entity, "type").write(EntityType.BOTCOMMAND);
        new ReflField(message, "entities")
            .write(Collections.singletonList(entity));
        final Update update = new Update();
        new ReflField(update, "message").write(message);
        return update;
    }
}
