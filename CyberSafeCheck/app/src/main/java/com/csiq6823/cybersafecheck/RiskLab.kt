package com.csiq6823.cybersafecheck

import java.util.UUID
object RiskLab {

    private val riskItems: MutableList<RiskItem> = mutableListOf(
        RiskItem(
            id = UUID.randomUUID(),
            category = RiskCategory.PASSWORDS,
            question = "I reuse the same password on more than one site",
            explanation = "If one of those sites is ever breached, attackers " +
                "will try the same email/password pair everywhere else you " +
                "have an account - a technique called credential stuffing. " +
                "A password manager makes unique passwords painless."
        ),
        RiskItem(
            id = UUID.randomUUID(),
            category = RiskCategory.PASSWORDS,
            question = "My passwords are based on words or dates that relate to me",
            explanation = "Pet names, birthdays, and favourite teams are the " +
                "first things attackers guess, especially once they've " +
                "seen your public social media profile."
        ),
        RiskItem(
            id = UUID.randomUUID(),
            category = RiskCategory.PASSWORDS,
            question = "I don't use two-factor authentication on my important accounts",
            explanation = "Two-factor authentication (2FA) means a stolen " +
                "password alone isn't enough to get into your account - " +
                "the attacker also needs your phone or authenticator app."
        ),
        RiskItem(
            id = UUID.randomUUID(),
            category = RiskCategory.SOCIAL_MEDIA,
            question = "I've accepted a friend request from someone I don't know",
            explanation = "Fake profiles are often used to research targets, " +
                "spread scam links, or build trust before asking for money " +
                "or personal information."
        ),
        RiskItem(
            id = UUID.randomUUID(),
            category = RiskCategory.SOCIAL_MEDIA,
            question = "My social media profile is fully public, not friends-only",
            explanation = "A public profile lets anyone see your daily " +
                "routine, location tags, and personal details, which can be " +
                "used for stalking, impersonation, or targeted scams."
        ),
        RiskItem(
            id = UUID.randomUUID(),
            category = RiskCategory.SOCIAL_MEDIA,
            question = "I post about being away from home before or during a trip",
            explanation = "Real-time location posts can tell people exactly " +
                "when your home is empty."
        ),
        RiskItem(
            id = UUID.randomUUID(),
            category = RiskCategory.SCAMS,
            question = "I've clicked a link in an unexpected email or SMS without checking the sender",
            explanation = "Phishing links imitate banks, delivery companies " +
                "and universities to steal login details. Checking the " +
                "actual sender address before clicking stops most of these."
        ),
        RiskItem(
            id = UUID.randomUUID(),
            category = RiskCategory.SCAMS,
            question = "I've entered my card details on a site after clicking an ad or DM link",
            explanation = "Scam storefronts and \"too good to be true\" deals " +
                "advertised through social media DMs are a common way card " +
                "details get stolen."
        ),
        RiskItem(
            id = UUID.randomUUID(),
            category = RiskCategory.CYBERBULLYING,
            question = "I've received repeated unwanted or threatening messages online",
            explanation = "Persistent unwanted contact is a form of " +
                "cyberbullying/harassment. Keeping screenshots as evidence " +
                "and reporting it (Milestone 4) matters even if it feels minor."
        ),
        RiskItem(
            id = UUID.randomUUID(),
            category = RiskCategory.CYBERBULLYING,
            question = "I've seen embarrassing content about myself shared without my consent",
            explanation = "Non-consensual sharing of photos, screenshots or " +
                "rumours can escalate quickly. Most platforms have a " +
                "reporting flow, and evidence (screenshots) helps if it needs " +
                "to be escalated further."
        )
    )

    fun getRiskItems(): List<RiskItem> = riskItems

    fun getRiskItem(id: UUID): RiskItem? = riskItems.firstOrNull { it.id == id }

    fun updateFlagged(id: UUID, flagged: Boolean) {
        getRiskItem(id)?.isFlagged = flagged
    }
}
