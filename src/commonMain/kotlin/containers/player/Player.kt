package containers.player

import com.soywiz.korge.component.attach
import com.soywiz.korge.view.*
import com.soywiz.korio.dynamic.dyn
import com.soywiz.korma.geom.Point
import com.soywiz.korma.geom.centerX
import com.soywiz.korma.geom.centerY
import com.soywiz.korma.geom.shape.Shape2d
import components.collision.MovesWithTilemapCollision
import components.input.HorizontalMoveInput
import components.input.ShootBulletsInput
import components.movement.ClampMovement
import components.movement.HasGravity
import components.movement.MoveDirection
import containers.SpriteEntity
import containers.bullet.DotPlayerBullet
import containers.bullet.EnemyBullet
import containers.enemy.Enemy
import program.AssetManager
import program.GameState
import program.LevelManager
import program.SoundManager
import utility.timer.SimpleTimer
import kotlin.time.Duration.Companion.seconds

const val PLAYER_NAME = "PLAYER"

open class Player(
    sprite: Sprite,
    assets: AssetManager,
    soundManager: SoundManager,
    levelManager: LevelManager,
    var maxSpeed: Double = 2.0
) : SpriteEntity(sprite, assets, soundManager, levelManager) {
    private val initialHp = hp
    private val shootTimer = SimpleTimer(this, 0.33.seconds).attach().start()
    private val jumpTimer = SimpleTimer(this, 0.4.seconds).attach()
    var isDead = false
    var shootDirection = MoveDirection.UP

    init {
        name = PLAYER_NAME

        //sprite.bounds(4.0,1.0,13.0,15.0)
        //hitShape2d = Shape2d.Rectangle(0, 0, 12, 12)
        HorizontalMoveInput(
            this,
            assets.playerWalkLeftAnimation,
            assets.playerWalkRightAnimation
        ).attach()
        ClampMovement(this, Point(maxSpeed, maxSpeed)).attach()
        MovesWithTilemapCollision(this, levelManager).attach()
        HasGravity(this, maxSpeed).attach()
        ShootBulletsInput(this).attach()

        onCollision {
            if (it is Enemy || it is EnemyBullet) {
                damage()
            }
        }
        addUpdater {
            if (!isDead) GameState.timeAlive += it.dyn.toDouble() else return@addUpdater

            if (isJumping()) {
                move.y = -2.0
            }

            if (move.x < 0.1 && move.x > -0.1) {
                getSprite().playAnimation(assets.playerIdleAnimation)
            }
        }
    }

    open fun damage(value: UInt = 1u) {
        hp -= value
        if (hp == 0u) kill()
    }

    open fun kill() {
        isDead = true
        GameState.timeAlive = 0.0
        removeFromParent()
    }

    override fun reset() {
        super.reset()
        hp = initialHp
        isDead = false
    }

    open fun jump() {
        if (!isJumping() && isTouchingGround()) {
            jumpTimer.restart()
        }
    }

    open fun fire() {
        if (shootTimer.isFinished()) {
            var spawn = Point.Zero
            var target = Point.Zero
            when (shootDirection) {
                MoveDirection.UP -> {
                    spawn = Point(getCurrentBounds().centerX, getCurrentBounds().top - 4)
                    target = Point(getCurrentBounds().centerX, getCurrentBounds().top - 100)
                }
                MoveDirection.DOWN -> {
                    spawn = Point(getCurrentBounds().centerX, getCurrentBounds().bottom + 4)
                    target = Point(getCurrentBounds().centerX, getCurrentBounds().bottom + 100)
                }
                MoveDirection.LEFT -> {
                    spawn = Point(getCurrentBounds().left, getCurrentBounds().centerY)
                    target = Point(getCurrentBounds().left - 100, getCurrentBounds().centerY)
                }
                MoveDirection.RIGHT -> {
                    spawn = Point(getCurrentBounds().right, getCurrentBounds().centerY)
                    target = Point(getCurrentBounds().right + 100, getCurrentBounds().centerY)
                }
            }
            val bullet = DotPlayerBullet(assets, soundManager, levelManager, spawn, target)
            levelManager.getCurrentMapView().addChild(bullet)
            shootTimer.restart()
        }
    }

    fun isJumping(): Boolean {
        return jumpTimer.isRunning()
    }
}
