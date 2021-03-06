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

package mage.server.tournament;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import mage.cards.Sets;
import mage.game.tournament.Tournament;
import mage.game.tournament.TournamentOptions;
import mage.game.tournament.TournamentType;
import mage.view.TournamentTypeView;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentFactory {
    private static final TournamentFactory INSTANCE = new TournamentFactory();
    private static final Logger logger = Logger.getLogger(TournamentFactory.class);

    private Map<String, Class<Tournament>> tournaments = new HashMap<String, Class<Tournament>>();
    private Map<String, TournamentType> tournamentTypes = new HashMap<String, TournamentType>();
    private List<TournamentTypeView> tournamentTypeViews = new ArrayList<TournamentTypeView>();

    public static TournamentFactory getInstance() {
        return INSTANCE;
    }

    private TournamentFactory() {}

    public Tournament createTournament(String tournamentType, TournamentOptions options) {

        Tournament tournament;
        Constructor<Tournament> con;
        try {
            con = tournaments.get(tournamentType).getConstructor(new Class[]{TournamentOptions.class});
            tournament = con.newInstance(new Object[] {options});
            // transfer set information, create short info string for included sets
            tournament.setTournamentType(tournamentTypes.get(tournamentType));
            Map<String,Integer> setInfo = new LinkedHashMap<String,Integer>();
            for (String setCode: options.getLimitedOptions().getSetCodes()) {
                tournament.getSets().add(Sets.findSet(setCode));
                int count = setInfo.containsKey(setCode) ? setInfo.get(setCode) : 0;
                setInfo.put(setCode, count + 1);
            }
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String,Integer> entry:setInfo.entrySet()) {
                sb.append(entry.getValue().toString()).append("x").append(entry.getKey()).append(" ");
            }
            tournament.setSetsFormatedShort(sb.toString());
        } catch (Exception ex) {
            logger.fatal("TournamentFactory error ", ex);
            return null;
        }
        logger.debug("Tournament created: " + tournamentType); // + game.getId().toString());

        return tournament;
    }

    public List<TournamentTypeView> getTournamentTypes() {
        return tournamentTypeViews;
    }


    public void addTournamentType(String name, TournamentType tournamentType, Class tournament) {
        if (tournament != null) {
            this.tournaments.put(name, tournament);
            this.tournamentTypes.put(name, tournamentType);
            this.tournamentTypeViews.add(new TournamentTypeView(tournamentType));
        }
    }

}