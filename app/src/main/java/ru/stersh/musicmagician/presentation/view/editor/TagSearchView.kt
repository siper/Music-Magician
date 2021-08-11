package ru.stersh.musicmagician.presentation.view.editor

import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.stersh.musicmagician.entity.tag.TagEntity

@StateStrategyType(SingleStateStrategy::class)
interface TagSearchView : MvpView {
    fun showProgress()
    fun showContent(tags: List<TagEntity>)
    fun showStub()
}