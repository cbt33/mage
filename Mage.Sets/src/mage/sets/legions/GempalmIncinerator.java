/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.legions;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public class GempalmIncinerator extends CardImpl<GempalmIncinerator> {

    private static final FilterPermanent filter = new FilterPermanent("Goblins on the battlefield");
    static {
        filter.add(new SubtypePredicate("Goblin"));
    }
    
    public GempalmIncinerator(UUID ownerId) {
        super(ownerId, 94, "Gempalm Incinerator", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "LGN";
        this.subtype.add("Goblin");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Cycling {1}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{1}{R}")));
        // When you cycle Gempalm Incinerator, you may have it deal X damage to target creature, where X is the number of Goblins on the battlefield.
        Ability ability = new CycleTriggeredAbility(new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter)),true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public GempalmIncinerator(final GempalmIncinerator card) {
        super(card);
    }

    @Override
    public GempalmIncinerator copy() {
        return new GempalmIncinerator(this);
    }
}