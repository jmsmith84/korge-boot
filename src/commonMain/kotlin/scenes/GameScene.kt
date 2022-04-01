package scenes

import com.soywiz.korev.Key
import com.soywiz.korge.input.keys
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tiled.TiledMapView
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.position
import com.soywiz.korma.geom.Point
import containers.GameEntity
import containers.enemy.TestEnemy
import containers.player.Player
import program.*

open class GameScene : Scene() {
    private lateinit var assets: AssetManager
    private lateinit var soundManager: SoundManager
    private lateinit var config: Config
    private lateinit var mapView: TiledMapView
    private lateinit var player: Player
    private lateinit var levelManager: LevelManager

    override suspend fun Container.sceneInit() {
        config = injector.get()
        assets = injector.get()
        soundManager = injector.get()
        levelManager = injector.get()

        Log.setLevel(config.getLogLevel())
        views.gameWindow.fullscreen = config.getFullscreenOnStart()

        levelManager.setNewMap(1u, this)
        mapView = levelManager.getCurrentMapView()

        player = injector.get()
        resetGame()

        mapView.addChild(player)
        mapView.addChild(TestEnemy(injector.get(), injector.get(), injector.get(), injector.get(), Point(50, 50)))

        keys.down {
            when (it.key) {
                Key.ESCAPE -> exitGame()
                //Key.R -> resetGame()
                Key.Z -> player.jump()
                else -> Unit
            }
        }
    }

    protected fun exitGame() {
        views.gameWindow.close()
    }

    protected fun resetGame() {
        mapView.x = 0.0
        mapView.y = 0.0
        GameState.score = 0

        mapView.forEachChild {
            if (it is GameEntity && it !is Player) {
                it.removeFromParent()
            }
        }
        player.reset()

        val objectLayer = levelManager.getCurrentMap().objectLayers[0]
        objectLayer.opacity = 0.0
        objectLayer.objectsByType["player"]?.forEach {
            player.position(it.x, it.y)
        }

        Log().debug { "Player spawn @ ${player.pos}" }
    }

    override suspend fun Container.sceneMain() {
        addUpdater {
            Log().info { player.move }
            GameState.hiScore = if (GameState.score > GameState.hiScore) GameState.score else GameState.hiScore
        }
    }
}
