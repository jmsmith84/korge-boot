package containers

import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.addTo
import com.soywiz.korge.view.anchor
import program.IAssetManager
import program.LevelManager
import program.SoundManager

open class SpriteEntity(sprite: Sprite, assets: IAssetManager, soundManager: SoundManager, levelManager: LevelManager)
    : GameEntity(assets, soundManager, levelManager) {

    init {
        image = sprite
            .anchor(0,0)
            .addTo(this)
        image.smoothing = false
    }

    fun getSprite(): Sprite {
        return (image as Sprite)
    }
}
