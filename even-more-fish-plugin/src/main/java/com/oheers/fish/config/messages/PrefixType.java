package com.Austin-W-Music.fish.config.messages;

import com.Austin-W-Music.fish.DeepFishing;
import com.Austin-W-Music.fish.api.adapter.AbstractMessage;

public enum PrefixType {

    NONE(null, null),
    ADMIN("prefix-admin", "&c[&7Deep&9Fishing&c] "),
    DEFAULT("prefix-regular", "&a[&7Deep&9Fishing&a] "),
    ERROR("prefix-error", "&c[&7Deep&9Fishing&c] ");

    private final String id, normal;

    /**
     * This contains the id and normal reference to the prefixes. These can be obtained through the getPrefix() method.
     *
     * @param id     The config id for the prefix.
     * @param normal The default values for the prefix.
     */
    PrefixType(final String id, final String normal) {
        this.id = id;
        this.normal = normal;
    }

    /**
     * Gives the associated prefix colour + the default plugin prefix by creating two Message objects and concatenating them.
     * If the PrefixType is NONE, then just "" is returned.
     *
     * @return The unformatted prefix, unless the type is NONE.
     */
    public AbstractMessage getPrefix() {
        if (id == null) {
            return DeepFishing.getAdapter().createMessage("");
        } else {
            AbstractMessage message = DeepFishing.getAdapter().createMessage(Messages.getInstance().getConfig().getString(id, normal));
            message.appendString("&r");
            return message;
        }
    }
}
