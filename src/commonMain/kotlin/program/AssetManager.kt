package program

import com.soywiz.korau.sound.Sound
import com.soywiz.korge.particle.ParticleEmitter
import com.soywiz.korge.tiled.TiledMap
import com.soywiz.korge.tiled.readTiledMap
import com.soywiz.korge.view.SolidRect
import com.soywiz.korge.view.SpriteAnimation
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.color.Colors
import com.soywiz.korim.font.Font
import com.soywiz.korim.font.readFont
import com.soywiz.korim.format.readBitmap
import com.soywiz.korinject.AsyncDependency
import com.soywiz.korinject.injector
import com.soywiz.korio.file.std.resourcesVfs

class AssetManager : AsyncDependency, IAssetManager {
    override lateinit var levels: MutableMap<UShort, TiledMap>
    override lateinit var defaultFont: Font

    lateinit var music01: Sound
    lateinit var pickupSfx: Sound

    lateinit var playerBitmap: Bitmap
    lateinit var playerDeathBitmap: Bitmap
    lateinit var playerWalkBitmap: Bitmap

    lateinit var gemBitmap: Bitmap

    lateinit var bulletRect: SolidRect
    lateinit var enemyBulletRect: SolidRect

    lateinit var starbeamParticle: ParticleEmitter

    override lateinit var playerIdleAnimation: SpriteAnimation
    lateinit var playerWalkRightAnimation: SpriteAnimation
    lateinit var playerWalkLeftAnimation: SpriteAnimation

    override suspend fun init() {
        val config = injector().get<Config>()
        val dirs = getResourceSubdirs(config)

        bulletRect = SolidRect(4, 4, Colors.WHITE)
        enemyBulletRect = SolidRect(4, 4, Colors.YELLOW)

        defaultFont = resourcesVfs["${dirs["fonts"]}/pressStart2p.ttf"].readFont()

        levels = mutableMapOf()
        levels[1u] = resourcesVfs["${dirs["maps"]}/level001.tmx"].readTiledMap()

        playerBitmap = resourcesVfs["${dirs["graphics"]}/player01.png"].readBitmap()
        playerWalkBitmap = resourcesVfs["${dirs["graphics"]}/playerwalk-right.png"].readBitmap()

        //starbeamParticle = resourcesVfs["${particlesDir}/starbeam/particle.pex"].readParticleEmitter()
        //playerDeathBitmap = resourcesVfs["${graphicsDir}/handship_dead.png"].readBitmap()
        //gemBitmap = resourcesVfs["${graphicsDir}/gem01.png"].readBitmap()

        //pickupSfx = resourcesVfs["${soundsDir}/pickup01.wav"].readSound()
        //music01 = resourcesVfs["${soundsDir}/bgm01.mp3"].readMusic()

        buildSpriteAnimations()
    }

    private fun buildSpriteAnimations() {
        playerIdleAnimation = SpriteAnimation(
            spriteMap = playerBitmap,
            spriteWidth = 16,
            spriteHeight = 16,
            marginTop = 0,
            marginLeft = 0,
            columns = 1,
            rows = 1
        )
        playerWalkRightAnimation = SpriteAnimation(
            spriteMap = playerWalkBitmap,
            spriteWidth = 16,
            spriteHeight = 16,
            marginTop = 0,
            marginLeft = 0,
            columns = 3,
            rows = 1
        )
        playerWalkLeftAnimation = SpriteAnimation(
            spriteMap = playerWalkBitmap.clone().flipX(),
            spriteWidth = 16,
            spriteHeight = 16,
            marginTop = 0,
            marginLeft = 0,
            columns = 3,
            rows = 1
        )
    }
}
