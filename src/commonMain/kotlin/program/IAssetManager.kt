package program

import com.soywiz.korge.tiled.TiledMap
import com.soywiz.korge.view.SpriteAnimation
import com.soywiz.korim.font.Font

interface IAssetManager {
    var defaultFont: Font
    var levels: MutableMap<UShort, TiledMap>
    var playerIdleAnimation: SpriteAnimation

    fun getResourceSubdirs(config: Config): Map<String, String> {
        return mapOf(
            Pair("maps", config.get("resourceDirMaps")),
            Pair("fonts", config.get("resourceDirFonts")),
            Pair("audio", config.get("resourceDirAudio")),
            Pair("graphics", config.get("resourceDirGraphics")),
            Pair("particles", config.get("resourceDirParticles")),
        )
    }
}
