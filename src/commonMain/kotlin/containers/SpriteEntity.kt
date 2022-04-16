package containers

import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.anchor
import program.LevelManager
import program.Settings
import program.SoundManager

open class SpriteEntity(sprite: Sprite, soundManager: SoundManager, levelManager: LevelManager)
    : GameEntity(sprite, soundManager, levelManager) {

    init {
        image.anchor(0,0)
        image.smoothing = Settings.spriteSmoothing
    }

    fun getSprite(): Sprite {
        return (image as Sprite)
    }
}
