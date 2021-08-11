package ru.stersh.musicmagician.presentation.view.main

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MainView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun trackLibrary()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun albumLibrary()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showPermissionsError()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideBilling()
}