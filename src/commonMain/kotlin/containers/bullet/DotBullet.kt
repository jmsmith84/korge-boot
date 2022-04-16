package containers.bullet

import com.soywiz.korge.view.RectBase
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.position
import com.soywiz.korma.geom.Angle
import com.soywiz.korma.geom.XY
import com.soywiz.korma.geom.cos
import com.soywiz.korma.geom.sin
import containers.GameEntity
import program.LevelManager
import program.SoundManager
import utility.getDeltaScale

open class DotBullet(
    bulletRect: RectBase,
    spawn: XY,
    target: XY,
    soundManager: SoundManager,
    levelManager: LevelManager,
    private val shotSpeed: Double = 200.0
) : GameEntity(bulletRect, soundManager, levelManager), Bullet {
    init {
        val angle = Angle.between(spawn, target)
        position(spawn)
        addUpdater {
            val delta = getDeltaScale(it)
            x += cos(angle) * it.seconds * shotSpeed
            y += sin(angle) * it.seconds * shotSpeed

            pos.x += x * delta
            pos.y += y * delta
        }
    }
}
