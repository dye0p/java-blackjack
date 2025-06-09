package domain.participant.bet;

import domain.match.MatchResult;
import domain.participant.Dealer;
import domain.participant.Gamer;
import domain.participant.Player;
import java.util.List;
import java.util.Map;

public class BetSystem {

    private final BettingRecord bettingRecord;
    private final ProfitRecord profitRecord;

    public BetSystem(final BettingRecord bettingRecord, final ProfitRecord profitRecord) {
        this.bettingRecord = bettingRecord;
        this.profitRecord = profitRecord;
    }

    public void betting(final Player player, final long betAmount) {
        bettingRecord.betRegister(player, BetAmount.from(betAmount));
        profitRecord.initialize(player);
    }

    public Map<Gamer, Long> calculateProfit(final Dealer dealer, final List<Player> players) {
        for (Player player : players) {
            MatchResult dealerMatchResult = dealer.getMatchResult(player);
            calculatePlayerWin(dealer, player, dealerMatchResult);
            calculateDealerWin(dealer, player, dealerMatchResult);
            calculatePlayerBlackjackWin(dealer, player, dealerMatchResult);
        }

        return profitRecord.profitRecord();
    }

    private void calculatePlayerBlackjackWin(final Dealer dealer, final Player player,
                                             final MatchResult dealerMatchResult) {
        if (isPlayerBlackjack(dealerMatchResult)) {
            BetAmount betAmount = bettingRecord.get(player);
            Long dealerProfit = profitRecord.get(dealer);

            Long blackjackProfit = betAmount.calculateBlackjackProfit();

            profitRecord.update(player, betAmount.plus(blackjackProfit));
            profitRecord.update(dealer, dealerProfit - blackjackProfit);
        }
    }

    private boolean isPlayerBlackjack(final MatchResult dealerMatchResult) {
        return getDealerMatchResult(dealerMatchResult).equals(MatchResult.DEALER_BLACKJACK_LOSE);
    }

    private MatchResult getDealerMatchResult(final MatchResult dealerMatchResult) {
        return dealerMatchResult;
    }

    private void calculateDealerWin(final Dealer dealer, final Player player, final MatchResult dealerMatchResult) {
        if (isDealerWin(dealerMatchResult)) {
            BetAmount betAmount = bettingRecord.get(player);
            long dealerProfit = profitRecord.get(dealer);
            long playerProfit = profitRecord.get(player);

            profitRecord.update(player, playerProfit - (betAmount.getValue()));
            profitRecord.update(dealer, dealerProfit + (betAmount.getValue()));
        }
    }

    private boolean isDealerWin(final MatchResult dealerMatchResult) {
        return dealerMatchResult.equals(MatchResult.DEALER_WIN);
    }

    private void calculatePlayerWin(final Dealer dealer, final Player player, final MatchResult dealerMatchResult) {
        if (isDealerLose(dealerMatchResult)) {
            BetAmount betAmount = bettingRecord.get(player);
            long playerProfit = profitRecord.get(player);
            long dealerProfit = profitRecord.get(dealer);

            profitRecord.update(player, betAmount.plus(playerProfit));
            profitRecord.update(dealer, dealerProfit - betAmount.getValue());
        }
    }

    private boolean isDealerLose(final MatchResult dealerMatchResult) {
        return dealerMatchResult.equals(MatchResult.DEALER_LOSE);
    }

}
