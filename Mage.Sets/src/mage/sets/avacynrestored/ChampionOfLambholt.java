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
package mage.sets.avacynrestored;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersAnotherCreatureYourControlTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author noxx
 */
public class ChampionOfLambholt extends CardImpl<ChampionOfLambholt> {

    public ChampionOfLambholt(UUID ownerId) {
        super(ownerId, 171, "Champion of Lambholt", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Human");
        this.subtype.add("Warrior");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Creatures with power less than Champion of Lambholt's power can't block creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ChampionOfLambholtEffect()));

        // Whenever another creature enters the battlefield under your control, put a +1/+1 counter on Champion of Lambholt.
        this.addAbility(new EntersAnotherCreatureYourControlTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    public ChampionOfLambholt(final ChampionOfLambholt card) {
        super(card);
    }

    @Override
    public ChampionOfLambholt copy() {
        return new ChampionOfLambholt(this);
    }
}

class ChampionOfLambholtEffect extends RestrictionEffect<ChampionOfLambholtEffect> {

    ChampionOfLambholtEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures with power less than {this}'s power can't block creatures you control";
    }

    ChampionOfLambholtEffect(final ChampionOfLambholtEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (attacker != null && blocker != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null && attacker.getControllerId().equals(sourcePermanent.getControllerId())) {
                return blocker.getPower().getValue() >= sourcePermanent.getPower().getValue();
            }
        }
        return true;
    }

    @Override
    public ChampionOfLambholtEffect copy() {
        return new ChampionOfLambholtEffect(this);
    }
}
