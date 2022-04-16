package containers.player

import com.soywiz.klock.milliseconds
import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.addTo
import com.soywiz.korge.view.onCollision
import com.soywiz.korma.geom.Point
import components.collision.MovesWithTilemapCollision
import components.input.HorizontalMoveInput
import components.input.VerticalMoveInput
import components.movement.ClampMovement
import components.movement.HasFacing
import components.movement.MoveDirection
import containers.SpriteEntity
import containers.enemy.Enemy
import containers.item.GamePickup
import program.AssetManager
import program.LevelManager
import program.Log
import program.SoundManager

const val PLAYER_NAME = "PLAYER"

open class Player(
    private val assets: AssetManager,
    soundManager: SoundManager,
    levelManager: LevelManager,
) : SpriteEntity(Sprite(assets.playerBitmap), soundManager, levelManager) {
    private var facingComponent: HasFacing = HasFacing(this)
    private val initialHp = hp

    var isDead = false

    init {
        name = PLAYER_NAME
        addComponents()
        onCollision {
            when (it) {
                is Enemy -> damage()
                is GamePickup -> it.removeFromParent()
            }
        }
    }

    private fun addComponents() {
        addComponent(HorizontalMoveInput(this, assets.playerWalkLeftAnimation, assets.playerWalkRightAnimation))
        addComponent(VerticalMoveInput(this, assets.playerWalkUpAnimation, assets.playerWalkDownAnimation))
        addComponent(ClampMovement(this, Point(2.0, 2.0)))
        addComponent(facingComponent)
        addComponent(MovesWithTilemapCollision(this, levelManager))
    }

    override fun kill() {
        if (isDead) return
        isDead = true

        getSprite().playAnimation(assets.playerDeathAnimation, 40.milliseconds)
        getSprite().onAnimationCompleted {
            Log().info { "player removed after death" }
            image.visible = false
        }
        soundManager.asyncPlaySound(assets.playerDieSfx)
    }

    override fun reset() {
        super.reset()
        hp = initialHp
        isDead = false
        image.visible = true
        Log().info { "player reset" }
    }

    fun getFacing(): MoveDirection {
        return facingComponent.getFacing()
    }

    fun resetSpriteImage() {
        getSprite().playAnimation(assets.playerIdleAnimation)
    }

    fun fire() {
        TODO("Not yet implemented")
    }
}

