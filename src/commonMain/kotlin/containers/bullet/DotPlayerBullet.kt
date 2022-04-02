package containers.bullet

import com.soywiz.korge.view.RectBase
import com.soywiz.korma.geom.XY
import program.IAssetManager
import program.LevelManager
import program.SoundManager

class DotPlayerBullet(
    assets: IAssetManager,
    soundManager: SoundManager,
    levelManager: LevelManager,
    bulletRect: RectBase,
    spawn: XY,
    target: XY,
    shotSpeed: Double = 200.0,
) : DotBullet(assets, soundManager, levelManager, bulletRect, spawn, target, shotSpeed), PlayerBullet
