/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.abilities.effects.common;

import java.util.UUID;
import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class LoseLifeAllEffect extends OneShotEffect<LoseLifeAllEffect> {

    private DynamicValue amount;

    public LoseLifeAllEffect(int amount) {
        this(new StaticValue(amount));
    }

    public LoseLifeAllEffect(DynamicValue amount) {
        super(Constants.Outcome.Damage);
        this.amount = amount;
        staticText = "Each player loses " + amount + " life";
    }

    public LoseLifeAllEffect(final LoseLifeAllEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId: game.getPlayer(source.getControllerId()).getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.loseLife(amount.calculate(game, source), game);
            }
        }
        return true;
    }

    @Override
    public LoseLifeAllEffect copy() {
        return new LoseLifeAllEffect(this);
    }
}