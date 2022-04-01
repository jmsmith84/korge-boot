import com.soywiz.korge.Korge
import program.DefaultAppModule

object AppModule : DefaultAppModule()

suspend fun main() = Korge(Korge.Config(module = AppModule))
