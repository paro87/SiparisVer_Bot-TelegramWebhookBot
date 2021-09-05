package com.paro.siparisverbot.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum Emojis {
    SHOPPING_TROLLEY(EmojiParser.parseToUnicode(":shopping_trolley:")),
    HOUSE_WITH_GARDEN(EmojiParser.parseToUnicode(":house_with_garden:")),
    INCOMING_ENVELOPE(EmojiParser.parseToUnicode(":incoming_envelope:")),
    CONVENIENCE_STORE(EmojiParser.parseToUnicode(":convenience_store:")),
    BUST_IN_SILHOUETTE(EmojiParser.parseToUnicode(":bust_in_silhouette:")),
    MOTORBIKE(EmojiParser.parseToUnicode(":motorbike:"));;

    private String emojiName;

    @Override
    public String toString() {
        return emojiName;
    }
}
