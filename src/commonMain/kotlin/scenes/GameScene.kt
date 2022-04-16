package scenes

import com.soywiz.klock.seconds
import com.soywiz.korev.Key
import com.soywiz.korge.input.keys
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tiled.TiledMap
import com.soywiz.korge.view.*
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korma.geom.Point
import containers.GameEntity
import containers.enemy.TestEnemy
import containers.player.Player
import program.*

@Suppress("MemberVisibilityCanBePrivate")
open class GameScene : Scene() {
    protected lateinit var assets: AssetManager
    protected lateinit var soundManager: SoundManager
    protected lateinit var config: Config
    protected lateinit var player: Player
    protected lateinit var levelManager: LevelManager

    override suspend fun Container.sceneInit() {
        config = injector.get()
        assets = injector.get()
        soundManager = injector.get()
        levelManager = injector.get()
        player = injector.get()

        Log.setLevel(config.getLogLevel())
        views.gameWindow.fullscreen = config.getFullscreenOnStart()

        levelManager.setNewMap(1u, this)
        resetMapState()
        levelManager.getMapView().addChild(player)

        keys.down {
            when (it.key) {
                Key.ESCAPE -> exitToMenu()
                Key.R -> resetMapState()
                else -> {}
            }
        }

        setupUI()
    }

    private fun Container.setupUI() {
        text("LEVEL") {
            textSize = 18.0
            font = assets.mainFont
            position(10, views.virtualHeight - 40)
            addUpdater {
                text = levelManager.getLevelName()
            }
        }
        text("SCORE 0") {
            textSize = 18.0
            font = assets.mainFont
            position(10, views.virtualHeight - 20)
            addUpdater {
                text = "SCORE ${GameState.score}"
            }
        }
        text("HI 0") {
            textSize = 18.0
            font = assets.mainFont
            position(views.virtualWidth - 90, views.virtualHeight - 20)
            addUpdater {
                text = "HI ${GameState.hiScore}"
            }
        }
    }

    protected fun exitToMenu() {
        launchImmediately {
            sceneContainer.changeTo<MenuScene>()
        }
    }

    protected suspend fun resetMapState() {
        levelManager.getMapView().x = 0.0
        levelManager.getMapView().y = 0.0

        levelManager.getMapView().fastForEachChild {
            if (it is GameEntity && it !is Player) {
                it.removeFromParent()
            }
        }

        player.reset()
        getTiledMapObjects("player")?.forEach {
            player.position(it.x, it.y)
        }
        Log().debug { "Player spawn @ ${player.pos}" }

        getTiledMapObjects("test_enemy")?.forEach {
            val enemy = injector.get<TestEnemy>()
            enemy.position(it.x, it.y)
            levelManager.getMapView().addChild(enemy)
        }
    }

    protected fun getTiledMapObjects(type: String): List<TiledMap.Object>? {
        if (levelManager.getMap().objectLayers.isEmpty()) return null

        val objectLayer = levelManager.getMapObjects()
        return objectLayer.objectsByType[type]
    }

    override suspend fun Container.sceneMain() {
        addUpdater {
            GameState.hiScore = if (GameState.score > GameState.hiScore) GameState.score else GameState.hiScore
        }
        addFixedUpdater(1.0.seconds) {
            levelManager.getMapView().sortChildrenBy(
                Comparator { a, b ->
                    if (a is Player) {
                        if (b !is Player) return@Comparator 1
                        return@Comparator 0
                    } else if (b is Player) {
                        return@Comparator -1
                    }
                    return@Comparator 0
                }
            )
        }
    }
}
