package components.movement

import com.soywiz.klock.TimeSpan
import com.soywiz.korge.component.UpdateComponent
import containers.GameEntity
import utility.getDeltaScale
import kotlin.math.sin

class HorizontalSineMovement(
    override val view: GameEntity,
    private var baseY: Double,
    private var radius: Int = 50,
    private var speed: Double = 3.0,
    private var frequency: Double = 0.03
) : UpdateComponent {
    override fun update(dt: TimeSpan) {
        val delta = getDeltaScale(dt)

        view.move.x = speed * delta
        view.move.y = (baseY + radius * sin(view.x * frequency)) - view.y
    }
}
