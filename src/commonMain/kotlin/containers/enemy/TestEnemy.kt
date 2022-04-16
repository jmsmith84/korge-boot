package containers.enemy

import com.soywiz.korge.view.Sprite
import program.AssetManager
import program.LevelManager
import program.SoundManager

class TestEnemy(assets: AssetManager, soundManager: SoundManager, levelManager: LevelManager)
    : SpriteEnemy(Sprite(assets.testEnemyBitmap), soundManager, levelManager) {

    }
