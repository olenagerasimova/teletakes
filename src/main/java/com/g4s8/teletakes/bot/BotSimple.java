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

import com.g4s8.teletakes.misc.XmlSmart;
import com.g4s8.teletakes.tk.TmTake;
import com.jcabi.log.Logger;
import com.jcabi.xml.XML;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.cactoos.func.UncheckedProc;
import org.cactoos.list.Mapped;
import org.cactoos.scalar.Folded;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

/**
 * Simple bot.
 *
 * @since 1.0
 * @todo #1:30min Refactor this class and split into few smaller,
 *  also remove all checkstyle and PMD suppressions.
 */
public final class BotSimple extends TelegramLongPollingBot {

    /**
     * Take.
     */
    private final TmTake take;

    /**
     * Bot name.
     */
    private final String name;

    /**
     * Bot token.
     */
    private final String token;

    /**
     * Thread pool.
     */
    private final ExecutorService threads;

    /**
     * Simple bot for take.
     *
     * @param take Take
     * @param name Bot name
     * @param token Bot token
     */
    public BotSimple(final TmTake take, final String name,
        final String token) {
        super();
        this.take = take;
        this.name = name;
        this.token = token;
        this.threads = Executors.newCachedThreadPool();
    }

    @Override
    public void onUpdateReceived(final Update update) {
        this.threads.submit(new Act(this.take, this, update)).isDone();
    }

    @Override
    public String getBotUsername() {
        return this.name;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

    /**
     * Chat ID for update.
     *
     * @param update Update
     * @return Chat id number
     */
    private static long chatId(final Update update) {
        final long chat;
        if (update.hasMessage()) {
            chat = update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            chat = update.getCallbackQuery().getMessage().getChatId();
        } else {
            throw new UnsupportedOperationException("No chat id");
        }
        return chat;
    }

    /**
     * Single update act.
     *
     * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
     * @checkstyle CyclomaticComplexityCheck (200 lines)
     * @checkstyle NestedIfDepthCheck (200 lines)
     * @checkstyle LineLengthCheck (200 lines)
     * @checkstyle ExecutableStatementCountCheck (200 lines)
     */
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    private static final class Act implements Runnable {

        /**
         * Take.
         */
        private final TmTake take;

        /**
         * Bot.
         */
        private final BotSimple bot;

        /**
         * Update.
         */
        private final Update update;

        /**
         * Ctor.
         *
         * @param take Take
         * @param bot Bot
         * @param update Update
         */
        private Act(final TmTake take, final BotSimple bot,
            final Update update) {
            this.take = take;
            this.bot = bot;
            this.update = update;
        }

        @Override
        public void run() {
            try {
                final XmlSmart xml =
                    new XmlSmart(this.take.act(this.update).xml());
                xml.optNode("/response/message")
                    .ifPresent(this::sendMessage);
                xml.optNode("/response/delete/message")
                    .ifPresent(this::deleteMessage);
            } catch (final IOException err) {
                Logger.error(
                    this,
                    "Failed to process request: %[exception]s", err
                );
            } catch (final UncheckedIOException err) {
                Logger.error(
                    this, "Failed to send response: %[exception]s", err
                );
            }
        }

        /**
         * Send message.
         *
         * @param xmsg XML message
         */
        private void sendMessage(final XML xmsg) {
            final XmlSmart smart = new XmlSmart(xmsg);
            final SendMessage message = new SendMessage()
                .setChatId(chatId(this.update))
                .setText(smart.optXpath("text/text()")
                    .orElseThrow(
                        () -> new UncheckedIOException(
                            new IOException("No message text provided")
                        )
                    )
                );
            smart.optXpath("text/@kind")
                // @checkstyle AvoidInlineConditionalsCheck (1 line)
                .map(kind -> "markdown".equals(kind) ? "Markdown" : kind)
                .ifPresent(message::setParseMode);
            final Optional<XML> xkb = smart.optNode("keyboard");
            xkb.flatMap(xml -> new XmlSmart(xml).optNode("reply"))
                .ifPresent(
                    xreply -> message.setReplyMarkup(
                        new ReplyKeyboardMarkup()
                            .setResizeKeyboard(true)
                            .setKeyboard(
                                new Mapped<>(
                                    row -> new Folded<>(
                                        new KeyboardRow(),
                                        (kbr, btn) -> {
                                            kbr.add(new KeyboardButton(btn));
                                            return kbr;
                                        },
                                        row.xpath("button/text()")
                                    ).value(),
                                    xreply.nodes("row")
                                )
                            )
                    )
                );
            xkb.flatMap(xml -> new XmlSmart(xml).optNode("inline"))
                .ifPresent(
                    xinline -> message.setReplyMarkup(
                        new InlineKeyboardMarkup()
                            .setKeyboard(
                                new Mapped<>(
                                    row -> new Mapped<>(
                                        xbtn -> {
                                            final InlineKeyboardButton btn = new InlineKeyboardButton();
                                            final List<String> xcb = xbtn.xpath("@callback");
                                            if (!xcb.isEmpty()) {
                                                btn.setCallbackData(xcb.get(0));
                                            }
                                            final List<String> xurl = xbtn.xpath("@url");
                                            if (!xurl.isEmpty()) {
                                                btn.setUrl(xurl.get(0));
                                            }
                                            btn.setText(xbtn.xpath("text()").get(0));
                                            return btn;
                                        },
                                        row.nodes("button")
                                    ),
                                    xinline.nodes("row")
                                )
                            )
                    )
                );
            new UncheckedProc<>(none -> this.bot.sendApiMethod(message))
                .exec(null);
        }

        /**
         * Delete message.
         *
         * @param node XML
         */
        private void deleteMessage(final XML node) {
            new UncheckedProc<>(
                none -> this.bot.sendApiMethod(
                    new DeleteMessage(
                        chatId(this.update),
                        Integer.valueOf(node.xpath("text()").get(0))
                    )
                )
            ).exec(null);
        }
    }
}
