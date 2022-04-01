package containers.bullet

import com.soywiz.korma.geom.XY
import program.AssetManager
import program.LevelManager
import program.SoundManager

class DotEnemyBullet(
    assets: AssetManager,
    soundManager: SoundManager,
    levelManager: LevelManager,
    spawn: XY,
    target: XY,
    shotSpeed: Double = 100.0
) : DotBullet(assets, soundManager, levelManager, spawn, target, shotSpeed), EnemyBullet {
    init {
        addChild(assets.enemyBulletRect.clone())
    }
}
