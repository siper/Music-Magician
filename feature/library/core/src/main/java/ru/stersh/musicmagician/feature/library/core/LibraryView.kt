package ru.stersh.musicmagician.feature.library.core

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.stersh.musicmagician.data.core.entity.MediastoreItem

@StateStrategyType(SingleStateStrategy::class)
interface LibraryView<T> : MvpView {
    fun showProgress()
    fun showContent(items: List<T>)
    fun showStub()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun setSortOrder(order: Int)
}