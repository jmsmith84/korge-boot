package containers.bullet

import com.soywiz.korge.view.RectBase
import com.soywiz.korma.geom.XY
import program.LevelManager
import program.SoundManager

class DotEnemyBullet(
    bulletRect: RectBase,
    spawn: XY,
    target: XY,
    soundManager: SoundManager,
    levelManager: LevelManager,
    shotSpeed: Double = 100.0
) : DotBullet(bulletRect, spawn, target, soundManager, levelManager, shotSpeed), EnemyBullet
