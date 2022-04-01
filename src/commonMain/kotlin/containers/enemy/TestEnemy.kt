package containers.enemy

import com.soywiz.korge.component.attach
import com.soywiz.korge.view.Views
import com.soywiz.korge.view.filter.Convolute3Filter
import com.soywiz.korge.view.image
import com.soywiz.korma.geom.Point
import com.soywiz.korma.geom.XY
import components.collision.DestroyOffscreen
import components.collision.MovesWithTilemapCollision
import components.collision.MovesWithoutTilemapCollision
import components.movement.CircleMovement
import components.movement.ClampMovement
import components.movement.HorizontalSineMovement
import program.AssetManager
import program.LevelManager
import program.SoundManager

class TestEnemy(assets: AssetManager, soundManager: SoundManager, levelManager: LevelManager, views: Views, position: XY) :
    Enemy(assets, soundManager, levelManager, position) {

        init {
            image = image(assets.playerBitmap)
            image.smoothing = false
            image.filter = Convolute3Filter(Convolute3Filter.KERNEL_EDGE_DETECTION)

            //CircleMovement(this, pos).attach()
            HorizontalSineMovement(this, position.y).attach()
            ClampMovement(this, Point(5.0, 5.0)).attach()
            //MovesWithTilemapCollision(this, levelManager).attach()
            MovesWithoutTilemapCollision(this).attach()
            //DestroyOffscreen(this, views).attach()
        }
}
