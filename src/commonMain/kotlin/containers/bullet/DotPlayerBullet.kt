package containers.bullet

import com.soywiz.korge.view.RectBase
import com.soywiz.korma.geom.XY
import program.LevelManager
import program.SoundManager

class DotPlayerBullet(
    bulletRect: RectBase,
    spawn: XY,
    target: XY,
    soundManager: SoundManager,
    levelManager: LevelManager,
    shotSpeed: Double = 200.0,
) : DotBullet(bulletRect, spawn, target, soundManager, levelManager, shotSpeed), PlayerBullet
