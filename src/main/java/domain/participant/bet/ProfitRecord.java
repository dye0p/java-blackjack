package domain.participant.bet;

import domain.participant.Dealer;
import domain.participant.Gamer;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProfitRecord {
    //TODO 원시값 포장
    private static final long SET_PROFIT = 0L;

    private final Map<Gamer, Long> profitRecord;

    public ProfitRecord() {
        this.profitRecord = new LinkedHashMap<>();
        this.profitRecord.put(new Dealer(), SET_PROFIT);
    }

    public void initialize(Gamer gamer) {
        profitRecord.put(gamer, SET_PROFIT);
    }

    public void update(final Gamer gamer, final long profit) {
        profitRecord.put(gamer, profit);
    }

    public long get(final Gamer gamer) {
        return profitRecord.get(gamer);
    }

    public Map<Gamer, Long> profitRecord() {
        return new LinkedHashMap<>(profitRecord);
    }

}
