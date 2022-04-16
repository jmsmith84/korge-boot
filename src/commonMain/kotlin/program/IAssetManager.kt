package program

import com.soywiz.korau.sound.Sound
import com.soywiz.korge.tiled.TiledMap
import com.soywiz.korge.tiled.TiledMapData
import com.soywiz.korim.font.Font

interface IAssetManager {
    var tileSets: MutableList<TiledMap.TiledTileset>
    var levels: MutableMap<UShort, TiledMapData>
    var music: MutableMap<UShort, Sound>

    var mainFont: Font

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
