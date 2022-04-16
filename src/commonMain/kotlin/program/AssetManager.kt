package program

import com.soywiz.korau.sound.Sound
import com.soywiz.korau.sound.readSound
import com.soywiz.korge.tiled.TiledMap
import com.soywiz.korge.tiled.TiledMapData
import com.soywiz.korge.tiled.readTiledMapData
import com.soywiz.korge.tiled.readTiledSet
import com.soywiz.korge.view.RectBase
import com.soywiz.korge.view.SpriteAnimation
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.font.Font
import com.soywiz.korim.font.readFont
import com.soywiz.korim.format.readBitmap
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korinject.InjectorAsyncDependency
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Point
import com.soywiz.korma.geom.PointInt

class AssetManager : InjectorAsyncDependency, IAssetManager {
    override lateinit var tileSets: MutableList<TiledMap.TiledTileset>
    override lateinit var levels: MutableMap<UShort, TiledMapData>
    override lateinit var music: MutableMap<UShort, Sound>

    override lateinit var mainFont: Font

    lateinit var playerDieSfx: Sound

    lateinit var playerBitmap: Bitmap
    lateinit var playerDeathAnimBitmap: Bitmap
    lateinit var playerWalkRightBitmap: Bitmap
    lateinit var playerWalkDownBitmap: Bitmap
    lateinit var playerWalkUpBitmap: Bitmap
    lateinit var testEnemyBitmap: Bitmap

    lateinit var playerIdleAnimation: SpriteAnimation
    lateinit var playerDeathAnimation: SpriteAnimation
    lateinit var playerWalkRightAnimation: SpriteAnimation
    lateinit var playerWalkLeftAnimation: SpriteAnimation
    lateinit var playerWalkUpAnimation: SpriteAnimation
    lateinit var playerWalkDownAnimation: SpriteAnimation

    override suspend fun init(injector: AsyncInjector) {
        val config = injector.get<Config>()
        val dirs = getResourceSubdirs(config)

        mainFont = resourcesVfs["${dirs["fonts"]}/go_mono.ttf"].readFont()

        playerBitmap = resourcesVfs["${dirs["graphics"]}/player_test.png"].readBitmap()
        playerDeathAnimBitmap = playerBitmap
        playerWalkRightBitmap = playerBitmap
        playerWalkDownBitmap = playerBitmap
        playerWalkUpBitmap = playerBitmap

        testEnemyBitmap = resourcesVfs["${dirs["graphics"]}/enemy_test.png"].readBitmap()

        playerDieSfx = resourcesVfs["${dirs["audio"]}/player_die.wav"].readSound()

        buildTiledMapData(dirs)
        buildSpriteAnimations()
    }

    private suspend fun buildTiledMapData(dirs: Map<String, String>) {
        tileSets = mutableListOf(
            resourcesVfs["${dirs["maps"]}/map_tiles.tsx"].readTiledSet()
        )

        levels = mutableMapOf()
        levels[1u] = resourcesVfs["${dirs["maps"]}/map001.tmx"].readTiledMapData()

        music = mutableMapOf()
    }

    private fun buildSpriteAnimations() {
        val playerImageSize = PointInt(playerBitmap.size.width.toInt(), playerBitmap.size.height.toInt())
        playerIdleAnimation = SpriteAnimation(
            spriteMap = playerBitmap,
            spriteWidth = playerImageSize.x,
            spriteHeight = playerImageSize.y,
            marginTop = 0,
            marginLeft = 0,
            columns = 1,
            rows = 1
        )
        playerWalkRightAnimation = playerIdleAnimation
        playerWalkLeftAnimation = playerIdleAnimation
        playerWalkUpAnimation = playerIdleAnimation
        playerWalkDownAnimation = playerIdleAnimation
        playerDeathAnimation = playerIdleAnimation
    }
}
