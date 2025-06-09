package domain.participant.bet;

import domain.participant.Gamer;
import domain.participant.Player;
import java.util.LinkedHashMap;
import java.util.Map;

public class BettingRecord {

    private final Map<Gamer, BetAmount> bettingRecord;

    public BettingRecord() {
        this.bettingRecord = new LinkedHashMap<>();
    }

    public void betRegister(final Player player, final BetAmount amount) {
        bettingRecord.put(player, amount);
    }

    public BetAmount get(Player player) {
        return bettingRecord.get(player);
    }


}
