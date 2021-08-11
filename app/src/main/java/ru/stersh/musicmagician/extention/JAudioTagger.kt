package ru.stersh.musicmagician.extention

import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.Tag

fun Tag.getFieldOrEmpty(field: FieldKey): String {
    return if (this.hasField(field)) {
        this.getFirst(field)
    } else {
        ""
    }
}

fun Tag.getFieldOrNull(field: FieldKey): String? {
    return if (this.hasField(field)) {
        this.getFirst(field)
    } else {
        null
    }
}