package program

import com.soywiz.korinject.AsyncInjector
import containers.enemy.TestEnemy
import containers.player.Player
import scenes.GameScene
import scenes.MenuScene

object AppModule : DefaultAppModule() {
    override suspend fun AsyncInjector.configure() {
        mapSingleton { SoundManager() }
        mapSingleton { Config() }
        mapSingleton { LevelManager(get()) }
        mapSingleton { AssetManager() }

        mapPrototype { GameScene() }
        mapPrototype { MenuScene(title) }
        mapPrototype { Player(get(), get(), get()) }
        mapPrototype { TestEnemy(get(), get(), get()) }
    }
}

