package components.input

import com.soywiz.klock.TimeSpan
import com.soywiz.korev.Key
import com.soywiz.korge.component.UpdateComponent
import com.soywiz.korge.input.Input
import com.soywiz.korge.view.SpriteAnimation
import com.soywiz.korma.math.clamp
import containers.SpriteEntity

class VerticalMoveInput(
    override val view: SpriteEntity,
    private val input: Input,
    private val upAnimation: SpriteAnimation? = null,
    private val downAnimation: SpriteAnimation? = null
) : UpdateComponent {
    override fun update(dt: TimeSpan) {
        if (input.keys[Key.UP]) {
            view.move.y -= 1.0
            if (upAnimation !== null) view.getSprite().playAnimationLooped(upAnimation)
        } else if (input.keys[Key.DOWN]) {
            view.move.y += 1.0
            if (downAnimation !== null) view.getSprite().playAnimationLooped(downAnimation)
        } else {
            view.move.y = 0.0
        }
    }
}
