package example.cashcards;

import org.springframework.data.annotation.Id;

record CashCard(@Id Long id, Double amount, String owner) {
}

//public class CashCard {
//    public CashCard() {
//    }
//
//    public CashCard(Long id, Double amount) {
//    }
//}
