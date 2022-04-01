package program

import com.soywiz.korge.scene.Module
import com.soywiz.korim.color.Colors
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.*
import containers.player.Player
import scenes.GameScene

open class DefaultAppModule(override val title: String = "KorGE Boot Game", windowScale: Double = 2.0) : Module() {
    private val virtualScreenSize = SizeInt(Size(Point(320, 360)))

    override val mainScene = GameScene::class
    override val windowSize = virtualScreenSize * windowScale
    override val size = virtualScreenSize
    override val fullscreen = false
    override val clipBorders = true
    override val scaleMode = ScaleMode.FIT

    override suspend fun AsyncInjector.configure() {
        mapSingleton { AssetManager() }
        mapSingleton { SoundManager() }
        mapSingleton { Config() }
        mapSingleton { LevelManager(get()) }

        mapPrototype { GameScene() }
        mapPrototype { Player(get(), get(), get(), get()) }
    }
}
