package containers.bullet

import com.soywiz.korma.geom.XY
import program.AssetManager
import program.LevelManager
import program.SoundManager

class DotPlayerBullet(
    assets: AssetManager,
    soundManager: SoundManager,
    levelManager: LevelManager,
    spawn: XY,
    target: XY,
    shotSpeed: Double = 200.0,
) : DotBullet(assets, soundManager, levelManager, spawn, target, shotSpeed), PlayerBullet
