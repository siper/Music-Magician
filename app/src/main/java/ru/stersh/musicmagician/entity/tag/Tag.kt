package ru.stersh.musicmagician.entity.tag

interface Tag : TagEntity {
    val provider: String
    val priority: Int
}