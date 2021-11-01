package ru.stersh.musicmagician.feature.library.track.ui

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.stersh.musicmagician.data.core.entity.MediastoreItem

@moxy.viewstate.strategy.StateStrategyType(moxy.viewstate.strategy.SingleStateStrategy::class)
interface LibraryView : moxy.MvpView {
    fun showProgress()
    fun showContent(items: List<ru.stersh.musicmagician.data.core.entity.MediastoreItem>)
    fun showStub()
    @moxy.viewstate.strategy.StateStrategyType(moxy.viewstate.strategy.OneExecutionStateStrategy::class)
    fun setSortOrder(order: Int)
}