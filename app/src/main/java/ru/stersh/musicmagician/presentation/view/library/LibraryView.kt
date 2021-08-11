package ru.stersh.musicmagician.presentation.view.library

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.stersh.musicmagician.entity.mediastore.MediastoreItem

@StateStrategyType(SingleStateStrategy::class)
interface LibraryView : MvpView {
    fun showProgress()
    fun showContent(items: List<MediastoreItem>)
    fun showStub()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun setSortOrder(order: Int)
}