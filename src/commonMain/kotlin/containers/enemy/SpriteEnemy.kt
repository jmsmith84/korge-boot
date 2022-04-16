package containers.enemy

import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.position
import com.soywiz.korma.geom.IPoint
import containers.SpriteEntity
import program.GameState
import program.LevelManager
import program.Log
import program.SoundManager

@Suppress("MemberVisibilityCanBePrivate")
abstract class SpriteEnemy(
    sprite: Sprite,
    soundManager: SoundManager,
    levelManager: LevelManager
) : SpriteEntity(sprite, soundManager, levelManager), Enemy {
    protected open val value = 100u

    override fun kill() {
        this.removeFromParent()
        GameState.score += value
    }
}
