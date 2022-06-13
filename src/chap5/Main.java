package chap5;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) {

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
        );

        // 1. 2011년의 모든 트랜잭션을 찾아서 값을 오름차순으로 정렬
        List<Transaction> tr2011 = transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());

        System.out.println(tr2011);
        System.out.println("=================");

        // 2. 거래자가 근무하는 모든 도시를 중복없이 나열하시오.
        List<String> cities = transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .collect(toList());

        System.out.println(cities);
        System.out.println("=================");

        // 3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬.
        List<Trader> cambridge = transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(toList());

        System.out.println(cambridge);
        System.out.println("=================");

        // 4. 모든 거래자의 이름을 알파벳순으로 정렬하여 반환하시오.
        String traderName = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .collect(joining(" "));

        System.out.println(traderName);
        System.out.println("=================");

        // 5. 밀라노에 거래자가 있는가?
        boolean milan = transactions.stream()
                .anyMatch(o -> o.getTrader().getCity().equals("Milan"));

        System.out.println(milan);
        System.out.println("=================");

        // 6. 케임브리지에 거주하는 거래자의 모든 트랜잭션 값을 출력하시오.
        transactions.stream()
                .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
                .map(Transaction::getValue)
                .forEach(System.out :: println);
        System.out.println("=================");

        // 7. 전체 트랜잭션 중 최대값은 얼마인가?
        Optional<Integer> maxValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);

        System.out.println(maxValue.get().intValue());
        System.out.println("=================");

        // 8. 전체 트랜잭션 중 최소값은 얼마인가?
        Optional<Transaction> minValue = transactions.stream()
                .min(comparing(Transaction::getValue));

        System.out.println(minValue.get().getValue());
        System.out.println("=================");

        // 9. 2011년도 전체 트랜잭션의 총합
        int sum = transactions.stream()
                .filter(t -> t.getYear() == 2012)
                .mapToInt(Transaction::getValue)
                .sum();

        System.out.println(sum);
    }
}
