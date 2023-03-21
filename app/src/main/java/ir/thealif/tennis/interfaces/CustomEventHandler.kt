package ir.thealif.tennis.interfaces

import android.view.MenuItem
import ir.thealif.tennis.models.PlayerModel

interface CustomEventHandler {
    fun onMenuItemClicked(item: MenuItem?, player: PlayerModel?)
}