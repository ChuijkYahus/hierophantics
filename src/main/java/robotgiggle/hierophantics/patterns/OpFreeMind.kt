package robotgiggle.hierophantics.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.casting.iota.ListIota
import robotgiggle.hierophantics.HierophanticsAPI
import robotgiggle.hierophantics.iotas.MindReferenceIota
import robotgiggle.hierophantics.iotas.getMindReference
import robotgiggle.hierophantics.mishaps.*
import net.minecraft.server.network.ServerPlayerEntity

class OpFreeMind : ConstMediaAction {
    override val argc = 1
	override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val caster = env.castingEntity
		val mindRef = args.getMindReference(0, argc)

		if (caster == null || caster !is ServerPlayerEntity || mindRef.host != caster) {
			throw NotYourMindMishap()
		}

		val state = HierophanticsAPI.getPlayerState(caster)
		if (!state.hasMind(mindRef.name)) {
			throw MindFreedMishap()
		}
		if (state.disabled) {
			throw MindsDisabledMishap("free")
		}
		
		state.freeMind(mindRef.name)
		return listOf()
	}
}