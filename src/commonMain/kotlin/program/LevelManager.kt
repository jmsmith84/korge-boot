package program

import com.soywiz.korge.tiled.TiledMap
import com.soywiz.korge.tiled.TiledMapView
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addTo

class LevelManager(private val assets: AssetManager) {
    var mapView: TiledMapView? = null
    var smoothing = false
    private var currentLevel: UShort = 0u
    private var currentMap: TiledMap? = null

    fun setNewMap(level: UShort, container: Container, callback: TiledMapView.() -> Unit = {}): TiledMapView {
        currentLevel = level
        currentMap = assets.levels[level]
        mapView?.removeFromParent()
        return createMapView(container, callback)
    }

    fun getLevel(): UShort {
        return currentLevel
    }

    fun getCurrentMap(): TiledMap {
        if (currentMap === null) throw RuntimeException("Trying to get current level map when there isn't one.")
        return currentMap!!
    }

    fun getCurrentMapView(): TiledMapView {
        if (currentMap === null) throw RuntimeException("Trying to get current map view when there isn't one.")
        return mapView!!
    }

    private fun createMapView(
        container: Container,
        callback: TiledMapView.() -> Unit = {}
    ): TiledMapView {
        mapView = TiledMapView(getCurrentMap(), false, smoothing)
            .addTo(container, callback)
        return mapView!!
    }
}
