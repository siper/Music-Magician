package ru.stersh.musicmagician.presentation.view.editor

import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface EditorView : MvpView {
    fun bindHeader(title: String, subtitle: String, albumart: String)
}