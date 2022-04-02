package containers.enemy

import com.soywiz.korge.view.BaseImage
import com.soywiz.korge.view.RectBase
import com.soywiz.korge.view.onCollision
import com.soywiz.korge.view.position
import com.soywiz.korma.geom.XY
import containers.GameEntity
import containers.bullet.PlayerBullet
import program.*

@Suppress("MemberVisibilityCanBePrivate")
abstract class Enemy(
    assets: IAssetManager,
    soundManager: SoundManager,
    levelManager: LevelManager,
    position: XY
) : GameEntity(assets, soundManager, levelManager) {
    protected open val value = 100

    open fun kill() {
        this.removeFromParent()
        GameState.score += value
    }

    init {
        position(position)
        onCollision {
            if (it is PlayerBullet) {
                it.removeFromParent()
                hp--
                if (hp == 0u) {
                    this@Enemy.kill()
                }
            }
        }

        Log().debug { "Enemy created @ $position" }
    }
}
