package ru.stersh.musicmagician.presentation.view.editor

import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.stersh.musicmagician.data.server.core.entity.TagEntity

@StateStrategyType(SingleStateStrategy::class)
interface TagSearchView : MvpView {
    fun showProgress()
    fun showContent(tags: List<ru.stersh.musicmagician.data.server.core.entity.TagEntity>)
    fun showStub()
}