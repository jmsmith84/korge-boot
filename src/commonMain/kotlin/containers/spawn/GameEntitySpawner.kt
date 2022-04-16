package containers.spawn

import com.soywiz.korge.view.RectBase
import com.soywiz.korge.view.position
import com.soywiz.korma.geom.XY
import containers.GameEntity
import factories.GameEntityFactory
import program.LevelManager
import program.SoundManager

open class GameEntitySpawner(
    soundManager: SoundManager,
    levelManager: LevelManager,
    factory: GameEntityFactory,
    position: XY
) : GameEntity(RectBase(), soundManager, levelManager) {

    init {
        position(position)

        val entity = factory.create(pos.copy())
        levelManager.getMapView().addChild(entity)
    }
}
