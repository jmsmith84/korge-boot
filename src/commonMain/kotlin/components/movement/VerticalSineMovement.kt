package components.movement

import com.soywiz.klock.TimeSpan
import com.soywiz.korge.component.UpdateComponent
import com.soywiz.korge.view.View
import utility.getDeltaScale
import kotlin.math.sin

class VerticalSineMovement(
    override val view: View,
    private var baseX: Double,
    private var radius: Int = 50,
    private var speed: Double = 3.0,
    private var frequency: Double = 0.03
) : UpdateComponent {
    override fun update(dt: TimeSpan) {
        val delta = getDeltaScale(dt)
        view.x = baseX + radius * sin(view.y * frequency)
        view.y += speed * delta
    }
}
