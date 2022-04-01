package components.input

import com.soywiz.klock.TimeSpan
import com.soywiz.korev.Key
import com.soywiz.korge.component.UpdateComponent
import com.soywiz.korge.input.Input
import containers.player.Player

class ShootBulletsInput(
    override val view: Player,
    private val input: Input,
    val canHoldFireDown: Boolean = true
) : UpdateComponent {
    override fun update(dt: TimeSpan) {
        if ((canHoldFireDown && input.keys.pressing(Key.X))
            || (!canHoldFireDown && input.keys.justPressed(Key.X))
        ) {
            view.fire()
        }
    }
}
