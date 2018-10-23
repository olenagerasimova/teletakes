# Teletakes
True Object-Oriented Telegram Framework with same design as in https://www.takes.org/

## Examples

### Quick start
This example will answer for "hello" message with "Hello %username%" message:
```java
 new TelegramBotsApi().registerBot(
    new BotSimple(
      new TkFork(
        new FkMessage(
            "hello",
            req -> new RsText(
                new FormattedText(
                    "Hello %s", new RqUser(req).value().getFirstName()
                )
            )
        )
      )
    )
 );
 ```
 
 ### Handle commands
 This take answers for `/version` command:
 ```java
 new TkFork(
    new FkCommand(
      "/version"
      new TkText("1.0")
    )
)
```

### Show keyboard
This take shows keyboard on user Telegram client:
```java
new TkFixed(
    new RsReplyKeyboard(
        new RsText("Hello"),
        new ListOf<Iterable<Text>>(
            new ListOf<>(
                new TextOf("Menu"),
                new TextOf("Settings")
            )
        )
    )
)
```

### Inline buttons
Telegram message can include "inline buttons" with callbacks, where callback is a strings which will be sent
sent to a bot on button click. This take will show a button and handle its clicks:
```java
new TkFork(
    new FkMessage(
        Pattern.compile(".*"),
        new TkFixed(
            new RsInlineKeyboard(
                new RsText("inline buttons"),
                new ListOf<Iterable<Map.Entry<String, String>>>(
                    new ListOf<Map.Entry<String, String>>(
                        new MapEntry<>("Confirm", "click:on-confirm"),
                        new MapEntry<>("Cancel", "click:on-cancel")
                    )
                )
            )
        )
    ),
    new FkCallbackQuery(
        "click:on-confirm",
        new TkText("Confirmed")
    ),
    new FkCallbackQuery(
        "click:on-cancel",
        new TkText("Canceled")
    )
)
```
